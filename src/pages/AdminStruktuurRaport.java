package pages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import base.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;


/**
 * Servlet implementation class V4
 */

public class AdminStruktuurRaport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<RiigiAdminYksuseLiik> liigid = null;
		try {
			 liigid = vaataLiike();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		request.setAttribute("liigid", liigid);
		request.getRequestDispatcher("StruktuuriVaade.jsp").forward(request, response);
		
	}

	public void init() throws ServletException {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<RiigiAdminYksuseLiik> vaataLiike() throws SQLException {
		
		List<RiigiAdminYksuseLiik> liigid = new ArrayList<RiigiAdminYksuseLiik>();
		
		Connection conn = DriverManager
				.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");

		Statement stmt = null;
		ResultSet rset = null;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery("select riigi_admin_yksuse_lik_id, nimetus, "
					+ " (select riigi_admin_yksuse_lik from"
					+ " VOIMALIK_ALLUVUS where voimalik_alluv_lik_ID ="
					+ " RIIGI_ADMIN_YKSUSE_LIIK.riigi_admin_yksuse_lik_id) as alluv"
					+ " from RIIGI_ADMIN_YKSUSE_LIIK"
					+ " ");

			while (rset.next()) {
				RiigiAdminYksuseLiik liik = new RiigiAdminYksuseLiik();
				System.out.println(rset.getInt(1) + ", " + rset.getString(2) + ", " + rset.getInt(3));
				liik.setRiigi_admin_yksuse_lik_id(rset.getInt(1));
				liik.setNimetus(rset.getString(2));
				liik.setAlluv_id(rset.getInt(3));
				liigid.add(liik);
			}

			return liigid;
			
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(conn);
		}
	}
}