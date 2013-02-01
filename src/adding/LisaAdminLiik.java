package adding;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import base.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Servlet implementation class LisaVoimalikAlluv
 */
public class LisaAdminLiik extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String connectionString = "jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LisaAdminLiik() {
		super();
	}

	public void init() throws ServletException {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		List<RiigiAdminYksuseLiik> ylemused = null;
		try {
			ylemused = naitaYlemusi(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		request.setAttribute("ylemused", ylemused);
		
		List<RiigiAdminYksuseLiik> yksuseLiigid = null;
		try {
			yksuseLiigid = leiaYksuseLiigid(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		request.setAttribute("yksuseLiigid", yksuseLiigid);
		request.getRequestDispatcher("LisaAdminLiik.jsp").forward(request,
				response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String alluv_id[] = request.getParameterValues("alluv_id");
		String kommentaar[] = request.getParameterValues("kommentaar");
		int id = Integer.parseInt(request.getParameter("id"));
		if (alluv_id != null) {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rset = null;
			for (int i = 0; i < alluv_id.length + 1; i++) {
				try {
					conn = DriverManager.getConnection(connectionString);

					ps = conn
							.prepareStatement("INSERT INTO VOIMALIK_ALLUVUS("
									+ " avaja, avatud, muutuja, muudetud, sulgeja, suletud, riigi_admin_yksuse_lik, voimalik_alluv_lik_ID, kommentaar) VALUES("
									+ "'admin', TODAY, 'admin', TODAY, 'admin', '2012-02-02', ?, ?, ?);");
					ps.setInt(1, id);
					ps.setInt(2, Integer.parseInt(alluv_id[i]));
					ps.setString(3, kommentaar[i]);
					ps.executeUpdate();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
					DbUtils.closeQuietly(conn);
					DbUtils.closeQuietly(ps);
					DbUtils.closeQuietly(rset);
				}
			}
		} 
		String redirectURL = "AdminLiigiRedaktor?ID=" + id;
		response.sendRedirect(redirectURL);

	}

	private List<RiigiAdminYksuseLiik> leiaYksuseLiigid(int ID) throws SQLException {
		List<RiigiAdminYksuseLiik> ylemused = new ArrayList<RiigiAdminYksuseLiik>();
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
					ylemused.add(riigiAdminYksuseLiik);
				}
				return ylemused;
				
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(conn);
		}
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