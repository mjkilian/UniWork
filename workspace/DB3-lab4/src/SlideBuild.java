import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SlideBuild {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String connString = "jdbc:oracle:thin:L3_12_1003819k/1003819@crooked.dcs.gla.ac.uk:1521:L3"; 
		
		Connection conn = null;
		try{
			 conn = DriverManager.getConnection(connString);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		Statement stmnt = null;
		try{
			stmnt = conn.createStatement();
		}catch(SQLException e){
			e.printStackTrace();
		}
	
		try {
			ResultSet rs = stmnt.executeQuery("SELECT Dog_Name " +
					"FROM " +
					"(SELECT name AS Owner_Name, ownerid " +
					"FROM OWNER) " +
					"NATURAL JOIN " +
					"(SELECT name AS Dog_Name,ownerid " +
					"FROM Dog ) " +
					"WHERE Dog_Name LIKE 'L%' OR Dog_Name LIKE 'W%' "
					);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}

}
