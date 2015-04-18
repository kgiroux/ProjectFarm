package model.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class DbUtils {

	private static DataSource ds;
	
	private static DataSource getDataSource() throws NamingException {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		ds = (DataSource) envCtx.lookup("jdbc/projectfarm");
		System.out.println("ICI");
		return ds;

	}

	public static Connection getConnection() throws ClassNotFoundException,
			SQLException, NamingException {
		DataSource d = getDataSource();
		if(d!=null){
			System.out.println("GetConnection");
			return d.getConnection();
		}else{
			return null;
		}
	}

	public static void dropConnection(Connection con) throws SQLException {

		if (con != null) {
			con.close();
		}
	}

}
