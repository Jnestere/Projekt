package adding;
import base.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Servlet implementation class LisaAdminAlluv
 */
public class LisaAdminAlluv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LisaAdminAlluv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		 System.out.println("lisa alluv @" + id);
		 
		 List<RiigiAdminYksus> yksused = null;
			try {
				 
				yksused = vaataYksuseid(id);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			request.setAttribute("yksused", yksused);
			request.getRequestDispatcher("LisaAdminAlluv.jsp").forward(request, response);
	}
	
private List<RiigiAdminYksus> vaataYksuseid(int id) throws SQLException {
		
		List<RiigiAdminYksus> yksused = new ArrayList<RiigiAdminYksus>();
		
		Connection conn = DriverManager
				.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");

		PreparedStatement ps = null;
		ResultSet rset = null;
		try {
			 ps = conn.prepareStatement("select * from RIIGI_ADMIN_YKSUS "
					+ "where riigi_admin_yksus_id = ? ");
							
			ps.setInt(1, id);
			
			rset = ps.executeQuery();
			RiigiAdminYksus yksus = new RiigiAdminYksus();
			
			while (rset.next()) {
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
			
				System.out.println(rset.getInt(1));
				
				yksused.add(yksus);
			}

			return yksused;
			
		} finally {
			DbUtils.closeQuietly(rset);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(conn);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String kood = request.getParameter("kood");
		String nimetus = request.getParameter("nimetus");
		String kommentaar = request.getParameter("komentaar");
		int id = Integer.parseInt(request.getParameter("id"));
		 
			   
		   {
			   System.out.println(request.getParameter("id") + " uued alluvad: ");
			   Connection conn = null;
			   PreparedStatement ps = null;
			   ResultSet rset = null;					
			
		    	  try {
	    		  conn = DriverManager
	    					.getConnection("jdbc:hsqldb:file:${user.home}/i377/Team01d/db;shutdown=true");

	    		  ps = conn.prepareStatement("INSERT INTO ADMIN_ALLUV("
				+ "avaja, avatud, muutuja, muudetud, sulgeja, suletud, alates, kuni, kommentaar, admin_alluvus_id, alluv_yksus_ID) VALUES("
				+ "'admin', TODAY, 'admin', TODAY, NULL, NULL, TODAY, NULL, NULL, ?, ?);");
				  ps.setInt(1, id);
				  ps.setString(2, kood);
				  ps.setString(3, nimetus);
				  ps.setString(4,kommentaar);
					
					 int rowCount = ps.executeUpdate();
					 System.out.println(rowCount + " rows updated!");
					} catch (SQLException e) {
						throw new RuntimeException(e);
					} finally {
					 DbUtils.closeQuietly(conn);
					 DbUtils.closeQuietly(ps);
					 DbUtils.closeQuietly(rset);
					 }
		      
		   }
		    System.out.println ("<b>none<b>");
		   String redirectURL = "AdminYksuseRedaktor?ID=" + id;
		   response.sendRedirect(redirectURL);
	}
}
