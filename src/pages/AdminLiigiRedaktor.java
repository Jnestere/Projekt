package pages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import base.*;
import adding.LisaAdminLiik;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;
 
public class AdminLiigiRedaktor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String connectionString = "jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true";
 
	public AdminLiigiRedaktor() {
		super();
	}

	public void init() throws ServletException {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<RiigiAdminYksuseLiik> AdminLiigid = null;
		List<RiigiAdminYksuseLiik> alluvused = null;
		List<RiigiAdminYksuseLiik> ylemused = null;

		int ID = 1;
		if (request.getParameter("ID") != null) {
			ID = Integer.parseInt(request.getParameter("ID"));
		}
		try {
			AdminLiigid = naitaYksuseLiike(ID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		request.setAttribute("AdminLiigid", AdminLiigid);

		try {
			alluvused = naitaAlluvusi(ID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		request.setAttribute("alluvused", alluvused);
		try {
			ylemused = naitaYlemusi(ID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		request.setAttribute("ylemused", ylemused);
		request.getRequestDispatcher("AdminLiigiRedaktor.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("alluv") != null) {
			int alluv = Integer.parseInt(request.getParameter("alluv"));
			int ylemus = Integer.parseInt(request.getParameter("ylemus"));

			
			
			try {
				conn = DriverManager
						.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");
									
			  ps = conn.prepareStatement("delete from VOIMALIK_ALLUVUS "
						+ "where riigi_admin_yksuse_lik  = ? "
						+ "AND  voimalik_alluv_lik_id = ? ");
			 ps.setInt(1, ylemus);
			 ps.setInt(2, alluv);
			 
			 int rowCount = ps.executeUpdate();
			 System.out.println(rowCount + " rows deleted!");
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
			 DbUtils.closeQuietly(ps);
			 DbUtils.closeQuietly(conn);
			 }
			String redirectURL = "AdminLiigiRedaktor?ID=" + ylemus;
		    response.sendRedirect(redirectURL);
		
		} else {
			if (request.getParameter("liik") != null) {
				
				int alluv = Integer.parseInt(request.getParameter("alluv"));
				int ylemus = Integer.parseInt(request.getParameter("ylemus"));

				
				
				try {
					conn = DriverManager
							.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");
										
				  ps = conn.prepareStatement("delete from VOIMALIK_ALLUVUS "
							+ "where riigi_admin_yksuse_lik  = ? "
							+ "AND  voimalik_alluv_lik_id = ? ");
				 ps.setInt(1, ylemus);
				 ps.setInt(2, alluv);
				 
				 int rowCount = ps.executeUpdate();
				 System.out.println(rowCount + " rows deleted!");
				} catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
				 DbUtils.closeQuietly(ps);
				 DbUtils.closeQuietly(conn);
				 }
				String redirectURL = "AdminLiigiRedaktor?ID=" + ylemus;
			    response.sendRedirect(redirectURL);
			}
			else{
			
			int riigiID = Integer.parseInt(request.getParameter("riigi_admin_yksuse_lik_id"));
			String kood = request.getParameter("kood");
			String nimetus = request.getParameter("nimetus");
			String kommentaar = request.getParameter("kommentaar");
			try {
				conn = DriverManager.getConnection(connectionString);

				ps = conn.prepareStatement("update RIIGI_ADMIN_YKSUSE_LIIK "
						+ "set kood = ?, " + "nimetus = ?, "
						+ "kommentaar = ?, " + "muutuja = ?, "
						+ "muudetud = TODAY "
						+ "where riigi_admin_yksuse_lik_id = ?");
				ps.setString(1, kood);
				ps.setString(2, nimetus);
				ps.setString(3, kommentaar);
				ps.setString(4, "ADMIN");
				ps.setInt(5, riigiID);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				DbUtils.closeQuietly(ps);
				DbUtils.closeQuietly(conn);
			}
			String redirectURL = "Main?ID=" + riigiID;
			response.sendRedirect(redirectURL);
			}
		}
	}

	private List<RiigiAdminYksuseLiik> naitaYksuseLiike(int ID)
			throws SQLException {

		List<RiigiAdminYksuseLiik> AdminLiigid = new ArrayList<RiigiAdminYksuseLiik>();
		Connection conn = DriverManager.getConnection(connectionString);

		ResultSet rset = null;
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("select riigi_admin_yksuse_lik_id, kood, nimetus, kommentaar "
							+ "from RIIGI_ADMIN_YKSUSE_LIIK where riigi_admin_yksuse_lik_id = ?");
			ps.setInt(1, ID);
			rset = ps.executeQuery();
			RiigiAdminYksuseLiik riigiAdminYksuseLiik = new RiigiAdminYksuseLiik();
			while (rset.next()) {
				riigiAdminYksuseLiik.setRiigi_admin_yksuse_lik_id(rset.getInt(1));
				riigiAdminYksuseLiik.setKood(rset.getString(2));
				riigiAdminYksuseLiik.setNimetus(rset.getString(3));
				riigiAdminYksuseLiik.setKommentaar(rset.getString(4));
				AdminLiigid.add(riigiAdminYksuseLiik);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(conn);
		}
		return AdminLiigid;
	}

	private List<RiigiAdminYksuseLiik> naitaAlluvusi(int ID) throws SQLException {

		List<RiigiAdminYksuseLiik> alluvused = new ArrayList<RiigiAdminYksuseLiik>();
		Connection conn = DriverManager.getConnection(connectionString);

		ResultSet rset = null;
		PreparedStatement ps = null;
		ResultSet rsetAlluvused = null;
		PreparedStatement psAlluvused = null;
		try {
			ps = conn
					.prepareStatement("select voimalik_alluv_lik_ID from VOIMALIK_ALLUVUS where riigi_admin_yksuse_lik =?");

			ps.setInt(1, ID);
			rset = ps.executeQuery();
			
			while (rset.next()) {
				RiigiAdminYksuseLiik riigiAdminYksuseLiik = new RiigiAdminYksuseLiik();
				psAlluvused = conn
						.prepareStatement("select riigi_admin_yksuse_lik_id, nimetus from RIIGI_ADMIN_YKSUSE_LIIK where riigi_admin_yksuse_lik_id = ?");
				psAlluvused.setInt(1, rset.getInt(1));
				rsetAlluvused = psAlluvused.executeQuery();
				while (rsetAlluvused.next()) {
					riigiAdminYksuseLiik.setRiigi_admin_yksuse_lik_id(rsetAlluvused.getInt(1));
					riigiAdminYksuseLiik.setNimetus(rsetAlluvused.getString(2));
					alluvused.add(riigiAdminYksuseLiik);
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

	private List<RiigiAdminYksuseLiik> naitaYlemusi(int ID) throws SQLException {
		List<RiigiAdminYksuseLiik> ylemused = new ArrayList<RiigiAdminYksuseLiik>();
		Connection conn = DriverManager.getConnection(connectionString);

		ResultSet rset = null;
		PreparedStatement ps = null;
		try {
			ResultSet rsetAlluvused = null;
			PreparedStatement psAlluvused = null;
			ps = conn
					.prepareStatement("select riigi_admin_yksuse_lik from VOIMALIK_ALLUVUS where voimalik_alluv_lik_id=?");

			ps.setInt(1, ID);
			rset = ps.executeQuery();
			RiigiAdminYksuseLiik riigiAdminYksuseLiik = new RiigiAdminYksuseLiik();
			while (rset.next()) {
				psAlluvused = conn
						.prepareStatement("select riigi_admin_yksuse_lik_id, nimetus from RIIGI_ADMIN_YKSUSE_LIIK where riigi_admin_yksuse_lik_id = ?");
				psAlluvused.setInt(1, rset.getInt(1));
				rsetAlluvused = psAlluvused.executeQuery();
				while (rsetAlluvused.next()) {
					riigiAdminYksuseLiik.setRiigi_admin_yksuse_lik_id(rsetAlluvused.getInt(1));
					riigiAdminYksuseLiik.setNimetus(rsetAlluvused.getString(2));
					ylemused.add(riigiAdminYksuseLiik);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(conn);
		}
		return ylemused;
	}
}
