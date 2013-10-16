package UserFactory;

public class VisitorFactory extends UserFactory {

	protected Visitor createVisitor(String surname, String forename, String GUID,String password) {
		return new Visitor(surname,forename,GUID,password);
	}
	
}