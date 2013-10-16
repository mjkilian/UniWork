package Calendar.Category;

import java.util.ArrayList;


public class CategorySet  {
	private static final long serialVersionUID = 1L; //for eclipse 
	private int catPos = 0;
	private Category cats[];
	
	public CategorySet(int i){
		cats = new Category[i];
	}
	
	public boolean add(Category c){
		if(catPos < cats.length ){
			cats[catPos] = c;
			catPos++;
			return true;
		}else{
			return false;
		}
	}
	
	public Category get(int i){
		return cats[i];
	}

	
	
}
