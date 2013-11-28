
public class Utilities {
	
	public static double averageValueOfArray(double[] array){
		double total = 0;
		for(int i = 0; i < array.length; i++){
			total += array[i];
		}
		return total/((double) array.length);
	}
	
	public static double averageLogarithmicValueOfArray(double[] array){
		double total = 0;
		for(int i = 0; i < array.length; i++){
			if(array[i] > 0)
				total += Math.log(array[i]);
		}
		return total/((double) array.length);
	}
	
	public static void main(String[] args){
		double[] x1 = {0.0,1.0,2,3,4,5,6,7,8,9};
		double[] x2 = {1,1,1,1,1,1,1,1,1};
		double[] x3 = {1,2,1,2,1,2,1,2,1,2};
		
		System.out.println(averageValueOfArray(x1));
		System.out.println(averageValueOfArray(x2));
		System.out.println(averageValueOfArray(x3));
		
	}
}
