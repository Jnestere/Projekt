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
 
public class AdminYksuseRedaktor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String connectionString = "jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true";
 
	public AdminYksuseRedaktor() {
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
		List<RiigiAdminYksus> yksused = null;
		List<RiigiAdminYksus> alluvused = null;
		List<RiigiAdminYksus> ylemused = null;
		List<RiigiAdminYksus> liigid = null;
		List<RiigiAdminYksus> yksus = null;
		
		int ID = 1;
		if (request.getParameter("ID") != null) {
			ID = Integer.parseInt(request.getParameter("ID"));
		}
		try {
		yksused = naitaYksuseLiike(ID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		request.setAttribute("yksused", yksused);

		try {
			liigid = naitaLiike();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			request.setAttribute("liigid", liigid);

		
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
		request.getRequestDispatcher("AdminYksuseRedaktor.jsp").forward(request, response);
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
										
				  ps = conn.prepareStatement("delete from ADMIN_ALLUV "
							+ "where admin_alluvus_id = ? "
							+ "AND  alluv_yksus_id = ? ");
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
				String redirectURL = "AdminYksuseRedaktor?ID=" + ylemus;
			    response.sendRedirect(redirectURL);
			
			} else {
				
				int riigiID = Integer.parseInt(request.getParameter("riigi_admin_yksus_id"));
				String kood = request.getParameter("kood");
				String nimetus = request.getParameter("nimetus");
				String kommentaar = request.getParameter("kommentaar");
				try {
					conn = DriverManager.getConnection(connectionString);

					ps = conn.prepareStatement("update RIIGI_ADMIN_YKSUS "
							+ "set kood = ?, " + "nimetus = ?, "
							+ "komentaar = ?, " + "muutuja = ?, "
							+ "muudetud = TODAY "
							+ "where riigi_admin_yksus_id = ?");
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
	
	
	private List<RiigiAdminYksus> naitaYksuseLiike(int ID)
			throws SQLException {

		List<RiigiAdminYksus> AdminLiigid = new ArrayList<RiigiAdminYksus>();
		Connection conn = DriverManager.getConnection(connectionString);

		ResultSet rset = null;
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("select riigi_admin_yksus_id, kood, nimetus, komentaar "
							+ "from RIIGI_ADMIN_YKSUS where riigi_admin_yksus_id = ?");
			ps.setInt(1, ID);
			rset = ps.executeQuery();
			RiigiAdminYksus RiigiAdminYksus = new RiigiAdminYksus();
			while (rset.next()) {
				RiigiAdminYksus.setRiigi_admin_yksus_id(rset.getInt(1));
				RiigiAdminYksus.setKood(rset.getString(2));
				RiigiAdminYksus.setNimetus(rset.getString(3));
				RiigiAdminYksus.setKommentaar(rset.getString(4));
				AdminLiigid.add(RiigiAdminYksus);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(conn);
		}
		return AdminLiigid;
	}

	private List<RiigiAdminYksus> naitaAlluvusi(int ID) throws SQLException {

		List<RiigiAdminYksus> alluvused = new ArrayList<RiigiAdminYksus>();
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
				RiigiAdminYksus RiigiAdminYksus = new RiigiAdminYksus();
				psAlluvused = conn
						.prepareStatement("select riigi_admin_yksus_id, nimetus from RIIGI_ADMIN_YKSUS where riigi_admin_yksus_id = ?");
				psAlluvused.setInt(1, rset.getInt(1));
				rsetAlluvused = psAlluvused.executeQuery();
				while (rsetAlluvused.next()) {
					RiigiAdminYksus.setRiigi_admin_yksus_id(rsetAlluvused.getInt(1));
					RiigiAdminYksus.setNimetus(rsetAlluvused.getString(2));
					alluvused.add(RiigiAdminYksus);
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

	private List<RiigiAdminYksus> naitaLiike() throws SQLException {

		List<RiigiAdminYksus> naitaLiike = new ArrayList<RiigiAdminYksus>();
		Connection conn = DriverManager.getConnection(connectionString);

		Statement stmt = null;
		ResultSet rset = null;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery("select * "
							+ "from RIIGI_ADMIN_YKSUS where suletud is not null ");
			//ps.setInt(1, ID);
			while (rset.next()) {
			RiigiAdminYksus RiigiAdminYksus = new RiigiAdminYksus();
			
				RiigiAdminYksus.setRiigi_admin_yksuse_liik_id(rset.getInt(1));
				RiigiAdminYksus.setNimetus(rset.getString(2));
				naitaLiike.add(RiigiAdminYksus);
			}
			return naitaLiike;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(conn);
		}
	}		

	
	private List<RiigiAdminYksus> naitaYlemusi(int ID) throws SQLException {
		List<RiigiAdminYksus> ylemused = new ArrayList<RiigiAdminYksus>();
		Connection conn = DriverManager.getConnection(connectionString);

		ResultSet rset = null;
		PreparedStatement ps = null;
		try {
			ResultSet rsetAlluvused = null;
			PreparedStatement psAlluvused = null;
			ps = conn
					.prepareStatement("select admin_alluvus_id from ADMIN_ALLUV where alluv_yksus_id =?");

			ps.setInt(1, ID);
			rset = ps.executeQuery();
			RiigiAdminYksus RiigiAdminYksus = new RiigiAdminYksus();
			while (rset.next()) {
				psAlluvused = conn
						.prepareStatement("select riigi_admin_yksus_id, nimetus from RIIGI_ADMIN_YKSUS where riigi_admin_yksus_id = ?");
				psAlluvused.setInt(1, rset.getInt(1));
				rsetAlluvused = psAlluvused.executeQuery();
				while (rsetAlluvused.next()) {
					RiigiAdminYksus.setRiigi_admin_yksus_id(rsetAlluvused.getInt(1));
					RiigiAdminYksus.setNimetus(rsetAlluvused.getString(2));
					ylemused.add(RiigiAdminYksus);
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
	private List<RiigiAdminYksus> uuendaYksus(int riigi_admin_yksyse_liik_id) throws SQLException {
		
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
