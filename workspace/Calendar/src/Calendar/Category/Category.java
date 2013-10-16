package Calendar.Category;
import java.awt.Color;

public class Category {
	String title;
	Color color;
	
	
	public Category(String title, Color color) {
		super();
		this.title = title;
		this.color = color;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
 