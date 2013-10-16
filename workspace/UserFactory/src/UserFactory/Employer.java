package UserFactory;

import java.io.Serializable;

public class Employer implements Serializable {
	private String name;
	private String contact;
	private String password;
	
	public Employer(String name, String contact, String password) {
		this.name = name;
		this.contact = contact;
		this.password = password;
	}
	
	protected boolean authenticate (String password){
		return this.password.equals(password);
	}
	
	public boolean equals(Object o){
		if(o instanceof Employer){
			Employer e = (Employer) o;
			if(this.name.equals(e.name) && this.contact.equals(e.contact) && this.password.equals(e.password)){
				return true;
			}
		}
		return false;
	}
}
