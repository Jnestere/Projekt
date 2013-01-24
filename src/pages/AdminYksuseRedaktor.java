package pages;

import base.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Servlet implementation class VaataRiigiAdminYksusi
 */

public class AdminYksuseRedaktor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int ID = 1;
		if(request.getParameter("riigi_admin_yksus_id") !=null) {
			ID = Integer.parseInt(request.getParameter("riigi_admin_yksus_id"));
		}
				
		List<RiigiAdminYksus> yksused = null;
		try {
			 yksused = vaataYksuseid(ID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		request.setAttribute("yksused", yksused);
		//request.getRequestDispatcher("V2.jsp").forward(request, response);
		
		List<AdminAlluvusDAO> alluvused = null;
		try {
			 alluvused = vaataAlluvusi(ID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		request.setAttribute("alluvused", alluvused);
		request.getRequestDispatcher("V2.jsp").forward(request, response);
		
		
	}

	public void init() throws ServletException {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<RiigiAdminYksus> vaataYksuseid(int ID) throws SQLException {
		
		List<RiigiAdminYksus> yksused = new ArrayList<RiigiAdminYksus>();
		//int liik_id = 0;
		
		Connection conn = DriverManager
				.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");

		PreparedStatement ps = null;
		ResultSet rset = null;
		try {

			ps = conn.prepareStatement(" select "
					+ "kood, nimetus, komentaar, riigi_admin_yksyse_liik_id,  "
					+ "( select admin_alluvus_id from "
					+ "ADMIN_ALLUV where " 
					+ "ADMIN_ALLUV.alluv_yksus_id = ?"
					+ "AND suletud is not null )"
					+ "from RIIGI_ADMIN_YKSUS where RIIGI_ADMIN_YKSUS_ID = ?");
					
			
			
			
			
			ps.setInt(1, ID);
			ps.setInt(2, ID);
			rset = ps.executeQuery();
			RiigiAdminYksus yksus = new RiigiAdminYksus();
			
			while (rset.next()) {
				yksus.setRiigi_admin_yksus_id(ID);
				yksus.setKood(rset.getString(1));
				yksus.setNimetus(rset.getString(2));
				yksus.setKommentaar(rset.getString(3));
				yksus.setRiigi_admin_yksuse_liik_id(rset.getInt(4));
				yksus.setRiigi_admin_yksuse_liik(rset.getString(5));
				
				yksused.add(yksus);
			}

			return yksused;

			
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(conn);
		}
	}
	
private List<AdminAlluvusDAO> vaataAlluvusi(int ID) throws SQLException {
		
		List<AdminAlluvusDAO> alluvused = new ArrayList<AdminAlluvusDAO>();
		
		Connection conn = DriverManager
				.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");

		PreparedStatement ps = null;
		ResultSet rset = null;
		ResultSet rsetAlluvused = null;
		PreparedStatement psAlluvused = null;
		try {
			ps = conn.prepareStatement("select alluv_yksus_ID, "
					+ "(select nimetus from RIIGI_ADMIN_YKSUS "
					+ "where RIIGI_ADMIN_YKSUS.riigi_admin_yksus_id = "
					+ "ADMIN_ALLUV.alluv_yksus_ID) "
					+ "from ADMIN_ALLUV "
					+ "where admin_alluvus_id = ?"
					+ "AND suletud is not null");

			ps.setInt(1, ID);
			rset = ps.executeQuery();
			while (rset.next()) {
				AdminAlluvusDAO alluvus = new AdminAlluvusDAO();
				psAlluvused = conn
						.prepareStatement("select riigi_admin_yksuse_lik_id, nimetus from RIIGI_ADMIN_YKSUSE_LIIK where riigi_admin_yksuse_lik_id = ?");
				psAlluvused.setInt(1, rset.getInt(1));
				rsetAlluvused = psAlluvused.executeQuery();
			while (rset.next()) {
				
				alluvus.setAlluv_yksus_ID(rset.getInt(1));
				alluvus.setRiigi_admin_yksuse_nimi(rset.getString(2));
				alluvused.add(alluvus);
			}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(rsetAlluvused);
			DbUtils.closeQuietly(psAlluvused);
			DbUtils.closeQuietly(conn);
		}
		return alluvused;
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		if(request.getParameter("alluv") != null) {
			int alluv = Integer.parseInt(request.getParameter("alluv"));
			out.println("alluv " + alluv);
			int ylev = Integer.parseInt(request.getParameter("ylev"));
			out.println("ylev " + ylev);
			
			try {
				conn = DriverManager
						.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");
									
			  ps = conn.prepareStatement("update ADMIN_ALLUV "
						 + "set suletud = TODAY, "
						 + "sulgeja = 'admin' "
						 + "where admin_alluvus_id = ? "
						 + "AND alluv_yksus_ID = ?");
			 ps.setInt(1, ylev);
			 ps.setInt(2, alluv);
			 
			 int rowCount = ps.executeUpdate();
			 System.out.println(rowCount + " rows updated!");
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
			 DbUtils.closeQuietly(ps);
			 DbUtils.closeQuietly(conn);
			 }
			String redirectURL = "AdminYksuseRedaktor?ID=" + ylev;
		    response.sendRedirect(redirectURL);
		}
		else {
			int id = Integer.parseInt(request.getParameter("riigi_admin_yksus_id"));
			String kood = request.getParameter("kood");
			String nimetus = request.getParameter("nimetus");
			String kommentaar = request.getParameter("komentaar");
			int alluv_yksus_ID = Integer.parseInt(request.getParameter("allub"));
	
			
			out.println(id + "<br>" + kood + "<br>"
					+ nimetus + "<br>" + kommentaar + "<br>"	+ alluv_yksus_ID);
	
			try {
				conn = DriverManager
						.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");
									
			  ps = conn.prepareStatement("update RIIGI_ADMIN_YKSUS "
					 + "set kood = ?, "
					 + "nimetus = ?, "
					 + "komentaar = ?, "
					 + "muutuja = ?, "
					 + "muudetud = TODAY "
					 + "where riigi_admin_yksus_id = ?");
			 ps.setString(1, kood);
			 ps.setString(2, nimetus);
			 ps.setString(3, kommentaar);
			 ps.setString(4, "admin");
			 ps.setInt(5, id);
			 
			 int rowCount = ps.executeUpdate();
			 System.out.println(rowCount + " rows updated!");
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
			 DbUtils.closeQuietly(ps);
			 DbUtils.closeQuietly(conn);
			 }
			
			String redirectURL = "AdminYksuseRedaktor?ID=" + id;
		    response.sendRedirect(redirectURL);
		}
	}
}