/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Objet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class ChangeCategorieContre extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            try {
                Objet.getConnection();
                if(request.getParameter("idCategorieContre")!=null) {
                    long idCategorieContre=Long.parseLong(request.getParameter("idCategorieContre"));
                    out.println("<span>Sous-catégorie de ce que je veux en échange :</span>");
                    out.println("<select name=\"idSousCategorieContre\">");
                    out.println("<option value=\"0\" selected=\"selected\">Choisissez</option>");
                    if(idCategorieContre!=0) {
                        String query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                        prepare.setLong(1, idCategorieContre);
                        ResultSet result=prepare.executeQuery();
                        while(result.next()) {
                            long idSousCategorieContre=result.getLong("id");
                            String sousCategorieContre=result.getString("sous_categorie");
                            out.println("<option value=\""+idSousCategorieContre+"\">"+sousCategorieContre+"</option>");
                        }
                        result.close();
                        prepare.close();
                    }
                    out.println("</select>");
                }
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(ChangeCategorieContre.class.getName()).log(Level.SEVERE, null, ex);
                out.println("</select>");
            } catch (SQLException ex) {
                Logger.getLogger(ChangeCategorieContre.class.getName()).log(Level.SEVERE, null, ex);
                out.println("</select>");
            }

        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
