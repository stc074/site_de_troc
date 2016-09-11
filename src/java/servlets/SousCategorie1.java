/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Annonces1;
import classes.Membre;
import classes.Objet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class SousCategorie1 extends HttpServlet {
   
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
            HttpSession session=Objet.initSession(request, response);
            Membre mbr=new Membre();
            mbr.testConnecte(request);
            if(request.getParameter("kermitCnx")!=null) {
                mbr.getPostsCnx(request);
                mbr.verifPostsCnx(request, response);
            }
            request.setAttribute("mbr", mbr);
            if(request.getParameter("idCategorie")==null||request.getParameter("idSousCategorie")==null)
                request.setAttribute("info", 1);
            else {
                long idCategorie=Long.parseLong(request.getParameter("idCategorie"));
                long idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
                Annonces1 annonces=new Annonces1(idCategorie, idSousCategorie);
                if(request.getParameter("reset")!=null)
                    annonces.reset(request);
                annonces.initListeSousCat();
                if(annonces.getIdCategorie()==0)
                    request.setAttribute("info", 1);
                else {
                    annonces.getGets(request);
                    annonces.initValues(request);
                    annonces.initTagsSousCategorie();
                    session.setAttribute("uriRetour", "./troc-2-"+annonces.getIdCategorie()+"-"+annonces.getIdSousCategorie()+"-"+annonces.getSousCategorie().toLowerCase()+".html");
                    if(annonces.getIdCategorie()==0)
                        request.setAttribute("info", 1);
                    else {
                        annonces.initListeAnnoncesSousCat();
                        if(annonces.getIdCategorie()==0)
                            request.setAttribute("info", 1);
                        else {
                            request.setAttribute("annonces", annonces);
                        }
                    }
            }
            }
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/souscategorie.jsp");
            dispatch.forward(request, response);
        } catch(Exception ex) {
            out.println(ex.getMessage());
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
