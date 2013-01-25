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

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		List<RiigiAdminYksuseLiik> yksuseLiigid = null;
		try {
			yksuseLiigid = leiaYksuseLiigid(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		request.setAttribute("yksuseLiigid", yksuseLiigid);
		request.getRequestDispatcher("LisaVoimalikAlluv.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String alluv_id[] = request.getParameterValues("alluv_id");
		String kommentaar[] = request.getParameterValues("kommentaar");
		int id = Integer.parseInt(request.getParameter("id"));
		if (alluv_id != null) {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rset = null;
			for (int i = 0; i < alluv_id.length; i++) {
				try {
					conn = DriverManager.getConnection(connectionString);

					ps = conn
							.prepareStatement("INSERT INTO VOIMALIK_ALLUVUS("
									+ " avaja, avatud, muutuja, muudetud, sulgeja, suletud, riigi_admin_yksuse_lik, voimalik_alluv_lik_ID, kommentaar) VALUES("
									+ "'admin', TODAY, 'admin', TODAY, 'admin','9999-12-31', ?, ?, ?);");
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

	private List<RiigiAdminYksuseLiik> leiaYksuseLiigid(int id)
			throws SQLException {

		List<RiigiAdminYksuseLiik> yksuseLiigid = new ArrayList<RiigiAdminYksuseLiik>();

		Connection conn = DriverManager.getConnection(connectionString);

		PreparedStatement ps = null;
		ResultSet rset = null;
		try {
			ps = conn
					.prepareStatement("select * from RIIGI_ADMIN_YKSUSE_LIIK "
							+ "where riigi_admin_yksuse_lik_id != ?"
							+ " and riigi_admin_yksuse_lik_id not in "
							+ "(select voimalik_alluv_lik_ID from VOIMALIK_ALLUVUS where suletud is null)"
							+ "and riigi_admin_yksuse_lik_id not in "
							+ "(select riigi_admin_yksuse_lik from VOIMALIK_ALLUVUS where suletud is null)");

			ps.setInt(1, id);

			rset = ps.executeQuery();
			RiigiAdminYksuseLiik yksuseLiik = new RiigiAdminYksuseLiik();

			while (rset.next()) {
				yksuseLiik.setRiigi_admin_yksuse_lik_id(rset.getInt(1));
				yksuseLiik.setKood(rset.getString(2));
				yksuseLiik.setNimetus(rset.getString(3));
				yksuseLiik.setKommentaar(rset.getString(4));
				yksuseLiik.setAlates(rset.getDate(5));
				yksuseLiik.setKuni(rset.getDate(6));
				yksuseLiik.setAvaja(rset.getString(7));
				yksuseLiik.setAvatud(rset.getDate(8));
				yksuseLiik.setMuutja(rset.getString(9));
				yksuseLiik.setMuudetud(rset.getDate(10));
				yksuseLiik.setSulgeja(rset.getString(11));
				yksuseLiik.setSuletud(rset.getDate(12));
				
				System.out.println("ID = " + rset.getInt(1));

				yksuseLiigid.add(yksuseLiik);
			}

			return yksuseLiigid;

		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(conn);
		}
	}

}
