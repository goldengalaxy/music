package de.unidue.inf.is;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.utils.DBUtil;



/**
 * Das könnte die Eintrittsseite sein.
 */
public final class MusicServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
  
	final String databaseToCheck = "try";
        boolean databaseExists = DBUtil.checkDatabaseExists(databaseToCheck);

        request.setAttribute("db2name", databaseToCheck);

        if (databaseExists) {
            request.setAttribute("db2exists", "vorhanden! Supi!");
        }
        else {
            request.setAttribute("db2exists", "nicht vorhanden :-(");
        }

        request.getRequestDispatcher("music_start.ftl").forward(request, response);
        
        ResultSet rs=DBUtil.LoadOnQuery("SELECT name from künstler order by kid desc fetch first 3 rows only");
   
        try {
			while(rs.next()){
				int i=1;
				request.setAttribute("k"+Integer.toString(i), rs.toString());
				i++;
				
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
     
}
