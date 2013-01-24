package pages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * Servlet implementation class VaataRiigiAdminYksuseLiike
 */

public class AdminAlluvus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("liik") !=null) {
			int liik = Integer.parseInt(request.getParameter("liik"));
		
			List<RiigiAdminYksus> yksused = null;
			try {
				 
				yksused = vaataYksuseid(liik);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			request.setAttribute("yksused", yksused);
			
			List<RiigiAdminYksuseLiik> liigid = null;
			try {
				 liigid = vaataLiike();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			request.setAttribute("liigid", liigid);
			request.getRequestDispatcher("V3.jsp").forward(request, response);
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int riigi_admin_yksuse_liik_id = 1;
		if(request.getParameter("riigi_admin_yksyse_liik_id") !=null) {
			riigi_admin_yksuse_liik_id = Integer.parseInt(request.getParameter("riigi_admin_yksyse_liik_id"));
		}
		
		List<RiigiAdminYksuseLiik> liigid = null;
		try {
			 liigid = vaataLiike();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		request.setAttribute("liigid", liigid);
		
		List<RiigiAdminYksus> yksused = null;
		try {
			 
			yksused = vaataYksuseid(riigi_admin_yksuse_liik_id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		request.setAttribute("yksused", yksused);
		request.getRequestDispatcher("V3.jsp").forward(request, response);
		
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
			rset = stmt.executeQuery("select * from RIIGI_ADMIN_YKSUSE_LIIK "
					+ "where suletud is not null");

			while (rset.next()) {
				RiigiAdminYksuseLiik liik = new RiigiAdminYksuseLiik();
				liik.setRiigi_admin_yksuse_lik_id(rset.getInt(1));
				liik.setKood(rset.getString(2));
				liik.setNimetus(rset.getString(3));
				liik.setKommentaar(rset.getString(4));
				liik.setAlates(rset.getDate(5));
				liik.setKuni(rset.getDate(6));
				liik.setAvaja(rset.getString(7));
				liik.setAvatud(rset.getDate(8));
				liik.setMuutja(rset.getString(9));
				liik.setMuudetud(rset.getDate(10));
				liik.setSulgeja(rset.getString(11));
				liik.setSuletud(rset.getDate(12));
				
				
				liigid.add(liik);
			}

			return liigid;
			
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(conn);
		}
	}
		private List<RiigiAdminYksus> vaataYksuseid(int riigi_admin_yksyse_liik_id) throws SQLException {
			
			List<RiigiAdminYksus> yksused = new ArrayList<RiigiAdminYksus>();
			
			Connection conn = DriverManager
					.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");

			PreparedStatement ps = null;
			ResultSet rset = null;
			try {
				ps = conn.prepareStatement("select * from RIIGI_ADMIN_YKSUS "
						+ "where suletud is not null AND "
						+ "riigi_admin_yksyse_liik_id = ?");
				ps.setInt(1, riigi_admin_yksyse_liik_id);
				rset = ps.executeQuery();
				
				while (rset.next()) {
					RiigiAdminYksus yksus = new RiigiAdminYksus();
					yksus.setRiigi_admin_yksus_id(rset.getInt(1));
					yksus.setKood(rset.getString(2));
					yksus.setNimetus(rset.getString(3));
					yksus.setKommentaar(rset.getString(4));
					yksus.setRiigi_admin_yksuse_liik_id(rset.getInt(5));
					yksus.setAlates(rset.getDate(6));
					yksus.setKuni(rset.getDate(7));
					yksus.setAvaja(rset.getString(8));
					yksus.setAvatud(rset.getDate(9));
					yksus.setMuutja(rset.getString(10));
					yksus.setMuudetud(rset.getDate(11));
					yksus.setSulgeja(rset.getString(12));
					yksus.setSuletud(rset.getDate(13));
					
					
					
					
					yksused.add(yksus);
				}

				return yksused;
				
			} finally {
				DbUtils.closeQuietly(rset);
				DbUtils.closeQuietly(ps);
				DbUtils.closeQuietly(conn);
			}
	}

}