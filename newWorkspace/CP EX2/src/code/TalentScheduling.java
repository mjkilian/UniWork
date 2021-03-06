import java.io.*;
import java.util.*;
import static choco.Choco.*;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.solver.Solver;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.cp.solver.search.integer.varselector.StaticVarOrder;
import choco.cp.solver.search.integer.varselector.MinDomain;

public class TalentScheduling {	
   
    String name;
    int noScenes;     // number of scenes
    int noActors;     // number of actors
    int noSlots;      // same as number of scenes
    int [][] appears; // appears[i][j] = 1 iff actor i appears in scene j
    int [] cost;      // cost of an actor for a unit of time
    int [] duration;  // duration of scene
    Model model;
    Solver solver;
    IntegerVariable[][] arrived;   // arrived[i][j] = 1 iff actor i arrived at or before time j
    IntegerVariable[][] present;   // present[i][j] = 1 iff actor i has not departed at time j
    IntegerVariable[][] timTab;    // sequenced timetable of scenes
    IntegerVariable[][] holdDay;   // holDay[i][j] = 1 iff at time j actor i has arrived, is present but does not appear
    IntegerVariable[] slot;        // slot[i] = j iff scene j is scheduled in slot i
    IntegerVariable[] holdDayCost; // actor cost as sum of hold day durations times cost of that actor.
    IntegerVariable totalCost;     // cost of schedule
    int maxCost;                   // maximum allowed cost, therefore we have a decision problem

    TalentScheduling (String fname,int maxCost) throws IOException {
	Scanner sc  = new Scanner(new File(fname));
	name        = sc.next();                   // movie name
	noScenes    = sc.nextInt();                // number of scenes
	noSlots     = noScenes;                    // same as number of scenes
        noActors    = sc.nextInt();                // number of actors
	appears     = new int[noActors][noScenes]; // does actor appears in scene?
	cost        = new int[noActors];           // cost of actor per unit time
	duration    = new int[noScenes];           // duration of scene
	for (int i=0;i<noActors;i++){
	    for (int j=0;j<noScenes;j++)
		appears[i][j] = sc.nextInt();
	    cost[i] = sc.nextInt();
	}
	for (int i=0;i<noScenes;i++) duration[i] = sc.nextInt();
	sc.close();
	this.maxCost = maxCost;
    }
    //
    // read in a talent scheduling problem from a file
    //

    void build(){
	model       = new CPModel();                                     // the model, we need that!
	holdDayCost = new IntegerVariable[noActors];                     // hold day cost for actors [NOTE 1]
	slot        = makeIntVarArray("slot",noSlots,0,noSlots-1);       // the decison variables [NOTE 2]
	arrived     = makeIntVarArray("arrived",noActors,noScenes,0,1);  // has actor arrived? [NOTE 3]
	present     = makeIntVarArray("present",noActors,noSlots,0,1);   // has actor not yet left? [NOTE 4]
	holdDay     = makeIntVarArray("holdDay",noActors,noScenes,0,1);  // hold day [NOTE 5]
	timTab      = makeIntVarArray("timTab",noActors,noScenes,0,1);   // sequenced timetable [NOTE 6 to 10]
	solver      = new CPSolver();                                    // the solver, we need that [NOTE 11]

	//
	// NOTE 1: create constraint integer variables for hold costs
	// 
	int totalMaxHoldCost = 0;                 // the maximum total hold cost for the entire movie
	for (int actor=0;actor<noActors;actor++){
	    int maxHoldCost = 0;                  // the maximum hold cost for an actor
	    for (int scene=0;scene<noScenes;scene++)
		maxHoldCost = maxHoldCost + (1-appears[actor][scene])*duration[scene];
	    holdDayCost[actor] = makeIntVar("actCost_"+actor,0,maxHoldCost);
	    totalMaxHoldCost = totalMaxHoldCost + maxHoldCost*cost[actor];
	}		
	totalCost = makeIntVar("totaCost",0,Math.min(maxCost,totalMaxHoldCost)); // total cost
	
	//alldiff(slot)
	model.addConstraint(allDifferent(slot));
	// 
	// NOTE 2: define this constraint
	// - every slot has to take a different scene
	//    
	   
	//arrived[i]j] -> arrived[i][j+1]
	for (int actor=0;actor<noActors;actor++)
	    for (int timeSlot=0;timeSlot<noSlots-1;timeSlot++)
		model.addConstraint(implies(eq(arrived[actor][timeSlot],1),eq(arrived[actor][timeSlot+1],1)));
	//
	// NOTE 3: define this constraint
	// - for all actors i and for all time slots j
	//     if actor i has arrived in time slot j 
	//     then actor i has arrived in time slot j+1
	//

	//present[i][j] -> present[i][j-1]
	for (int actor=0;actor<noActors;actor++)
	    for (int timeSlot=noSlots-1;timeSlot>0;timeSlot--)
		model.addConstraint(implies(eq(present[actor][timeSlot],1),eq(present[actor][timeSlot -1],1)));
	//
	// NOTE 4: define this constraint
	// - for all actors i and for all time slots j
	//    if actor i is present at time j 
	//    then actor i is present at time j-1
	//

	for (int timeSlot=0;timeSlot<noSlots;timeSlot++){ // time slot
	    for (int actor=0;actor<noActors;actor++){     // actors
		for (int scene=0;scene<noScenes;scene++){ // scene
		    if(appears[actor][scene] == 1){ //if actor appears in scene 
		    	//if the scene is in the current timeslot, the actor is timetabled to that timeslot
		    	model.addConstraint(implies(eq(slot[timeSlot],scene),eq(timTab[actor][timeSlot],1)));
		    }else{
		    	//else the actor is not active during that timeslot
		    	model.addConstraint(implies(eq(slot[timeSlot],scene),eq(timTab[actor][timeSlot],0)));
		    }
		}}}
	
	// NOTE 5: define this constraint
	// for all time slots i and for all actors j and for all scenes k
	//   if in slot i we are shooting scene k 
	//   then in the timetable timTab[j][i] equals 1 if the actor i appears in scene k, zero otherwise
	//

	//appears[j][k] -> (slot[i] == k -> arrived[j][i])
	for (int timeSlot=0;timeSlot<noSlots;timeSlot++) // time slot
	    for (int actor=0;actor<noActors;actor++)     // actors
		for (int scene=0;scene<noScenes;scene++) // scene
		    if (appears[actor][scene] == 1)
			model.addConstraint(implies(eq(slot[timeSlot],scene),eq(arrived[actor][timeSlot],1)));
	//
	// NOTE 6: define this constraint
	// for all time slots i and for all actors j and for all scenes k
	//   if actor j appears in scene k
	//   then add the constraint that 
        //           if in slot i we are shooting scene k
	//           then actor j has arrived at time i
	//

	//appears[j][k] -> (slot[i] == k -> present[j][i] == 1)
	for (int timeSlot=0;timeSlot<noSlots;timeSlot++) // time slot
	    for (int actor=0;actor<noActors;actor++)     // actors
		for (int scene=0;scene<noScenes;scene++) // scene
		    if (appears[actor][scene] == 1)
			model.addConstraint(implies(eq(slot[timeSlot],scene),eq(present[actor][timeSlot],1)));
	//
	// NOTE 7: define this constraint
	// for all time slots i and for all actors j and for all scenes k
	//   if actor j appears in scene k
	//   then add the constraint that 
        //           if in slot i we are shooting scene k
	//           then actor j is present at time i
	//

	
	for (int timeSlot=0;timeSlot<noSlots;timeSlot++) // time slots
	    for (int actor=0;actor<noActors;actor++)     // actors
		for (int scene=0;scene<noScenes;scene++) // scenes
		    if (appears[actor][scene] == 0)
			model.addConstraint(implies( and( eq(arrived[actor][timeSlot],1), eq(present[actor][timeSlot],1), eq(slot[timeSlot],scene) ),
					eq(holdDay[actor][timeSlot],1) ));
			//(arrived[j,i] == 1 && present[j][i] ==1 && slot[i] ==k) -> holdDay[j][i] == 1
	//
	// NOTE 8: define this constraint
	// "It's a hold day for the actor if he has arrived and is present but doesn't appear in the scheduled scene."
	// for all time slots i and for all actors j and for all scenes k
	//   if actor j does not appears in scene k
	//   then add the constraint that 
	//         if actor j has arrived at time i and 
	//            actor j is present at time i and
	//            in slot i we are shooting scene k
	//         then the actor j has a hold day for scene k
	//

	for (int timeSlot=0;timeSlot<noSlots;timeSlot++) // time slots
	    for (int actor=0;actor<noActors;actor++)     // actors
		for (int scene=0;scene<noScenes;scene++) // scenes
		    if (appears[actor][scene] == 1)
		    	model.addConstraint(implies( and( eq(arrived[actor][timeSlot],1), eq(present[actor][timeSlot],1), eq(slot[timeSlot],scene) ),
						eq(holdDay[actor][timeSlot],0) ));
				//as for constraint 8, but with opposite result
	
	// NOTE 9: define this constraint
	// "It's not a hold day for the actor if he has arrived and is present and appears in the scheduled scene."
	// for all time slots i and for all actors j and for all scenes k
	//   if actor j does appear in scene k
	//   then add the constraint that 
	//         if actor j has arrived at time i and 
	//            actor j is present at time i and
	//            in slot i we are shooting scene k
	//         then the actor j does not have a hold day for scene k
	//

	model.addConstraint(lt(slot[0],slot[slot.length-1]));
	//
	// NOTE 10: optionally define this symmetry reduction constraint
	//  since we can reverse the schedule and get the same result
	//  we might insist that the scene scheduled into the first time slot
	//  is less than the scene scheduled into the last time slot
	//

	for (int actor=0;actor<noActors;actor++) 
	    model.addConstraint(eq(holdDayCost[actor],scalar(duration,holdDay[actor])));
	model.addConstraint(leq(scalar(cost,holdDayCost),totalCost));
	//
	// computes the cost of movie schedule as the cost of actors hold days times the actor costs
	//

	solver.read(model);
    }
    //
    // build the constraint model for talent scheduling problem read in from file
    //

    void solve() {
        solver.setVarIntSelector(NOTE 11);
	if (solver.solve().booleanValue()) displaySchedule(); else System.out.println(false);
    }
    //
    // NOTE 11: Using the slot variables as your decision variables
    //          define various variable ordering heuristics and perform an empirical study
    //

    void displayData(){
	System.out.println(name + " scenes: "+ noScenes +" actors: "+ noActors);
	for (int i=0;i<noActors;i++){
	    System.out.print(i);
	    for (int j=0;j<noScenes;j++) System.out.print(" "+ appears[i][j]);
	    System.out.println("  "+ cost[i]);
	}
	for (int i=0;i<noScenes;i++) System.out.print(duration[i] +" ");
	System.out.println("\n");
    }
    //
    // print out what was read in
    //

    void displaySchedule(){
	System.out.println("cost: "+ solver.getVar(totalCost).getVal());
	for (int i=0;i<noSlots;i++) 
	    System.out.print(solver.getVar(slot[i]).getVal() +" ");
	System.out.println();
    }
    //
    // print out the cost of the schedule and the sequence of the scenes
    //

    void displayModel(){
	System.out.println("\nThe constraint model");
	for (int actor=0;actor<noActors;actor++){
	    System.out.println("actor "+ actor +" hold day cost: "+ solver.getVar(holdDayCost[actor]).getVal() +
			       " * "+ cost[actor] +" = "+ (solver.getVar(holdDayCost[actor]).getVal() * cost[actor]));
	    System.out.print(actor +" timTab   ");
	    for (int time=0;time<noSlots;time++)
		System.out.print(solver.getVar(timTab[actor][time]).getVal() +" ");
	    System.out.println();
	    System.out.print(actor +" arrived  ");
	    for (int time=0;time<noSlots;time++)
		System.out.print(solver.getVar(arrived[actor][time]).getVal() +" ");
	    System.out.println();
	    System.out.print(actor +" present  ");
	    for (int time=0;time<noSlots;time++)
		System.out.print(solver.getVar(present[actor][time]).getVal() +" ");
	     System.out.println();
	     System.out.print(actor +" holdDay  ");
	     for (int time=0;time<noSlots;time++){
		 int scene = solver.getVar(slot[time]).getVal();
		 System.out.print(solver.getVar(holdDay[actor][scene]).getVal() * duration[scene] +" ");
	     }
	    System.out.println();
	}
	System.out.println("totalCost "+ solver.getVar(totalCost).getVal());
    }
    //
    // Print out the constraint model after solving
    // NOTE: only meaningful if model has been solved and has a solution
    //
		

    public static void main(String[] args) throws IOException {
	int maxCost = Integer.parseInt(args[1]);
	TalentScheduling ts = new TalentScheduling(args[0],maxCost);
	//ts.displayData(); 
	ts.build();
	ts.solve();
	ts.solver.printRuntimeSatistics();
	//ts.displayModel();
    }
}
