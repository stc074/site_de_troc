/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Categorie extends Objet {
    private String categorie;
    private long idCategorie;
    private String sousCategorie;
    private int page;
    private String tagTitle;
    private String tagDescription;
    private long idSousCategorie;
    
    public Categorie() {
        this.categorie="";
        this.idCategorie=0;
        this.sousCategorie="";
    }

    public void getPostsCategorie(HttpServletRequest request) {
        try {
            Objet.getConnection();
            this.categorie=request.getParameter("categorie");
            if(this.getCategorie().length()==0) {
                this.setErrorMsg("Champ CATÉGORIE vide.<br/>");
            }
            else if(this.getCategorie().length()>100) {
                this.setErrorMsg("Champ CATÉGORIE trop long.<br/>");
            }
            else {
                String query="SELECT COUNT(id) AS nb FROM table_categories WHERE categorie=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.getCategorie());
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb!=0) {
                    this.setErrorMsg("La CATÉGORIE \""+this.getCategorie()+"\" existe déjà.<br/>");
                }
                result.close();
                prepare.close();
            }
            if(this.getErrorMsg().length()==0) {
                String query="INSERT INTO table_categories (categorie) VALUES (?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.getCategorie());
                prepare.executeUpdate();
                prepare.close();
                this.setTest(1);
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

     public void getsSousCategorie(HttpServletRequest request) {
        if(request.getParameter("idCategorie")!=null) {
             this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
         }
    }
    public void initCategorie() {
        this.categorie="";
        if(this.idCategorie!=0) {
            try {
                Objet.getConnection();
                String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.categorie=result.getString("categorie");
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getPostsSousCategorie(HttpServletRequest request) {
        try {
            Objet.getConnection();
            this.idCategorie = Long.parseLong(request.getParameter("idCategorie"));
            this.sousCategorie = request.getParameter("sousCategorie");
            if (this.idCategorie == 0) {
                this.setErrorMsg("Choisissez une CATÉGORIE SVP.<br/>");
            }
            if (this.sousCategorie.length() == 0) {
                this.setErrorMsg("Champ SOUS-CATÉGORIE vide.<br/>");
            } else if (this.sousCategorie.length() > 100) {
                this.setErrorMsg("Champ SOUS-CATÉGORIE trop long.<br/>");
            } else if (this.getErrorMsg().length() == 0) {
                String query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=? AND sous_categorie=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb!=0) {
                    this.setErrorMsg("Désolé, cette SOUS-CATÉGORIE existe déjà.<br/>");
                }
                result.close();
                prepare.close();
            }
            if(this.getErrorMsg().length()==0) {
                String query="INSERT INTO table_sous_categories (id_categorie,sous_categorie) VALUES (?,?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                prepare.executeUpdate();
                prepare.close();
                this.setTest(1);
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

    public boolean testCategorie() {
        try {
            boolean flag = false;
            if(this.idCategorie!=0) {
            Objet.getConnection();
            String query="SELECT categorie FROM table_categories WHERE id=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdCategorie());
            ResultSet result=prepare.executeQuery();
            flag=result.next();
            if(flag) {
                    this.categorie=result.getString("categorie");
                }
            result.close();
            prepare.close();
            Objet.closeConnection();
            }
            return flag;
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void initTagsCat() {
        this.tagTitle="Troc et echange avec le bon troc";
        this.tagDescription="Troc et echange avec le bon troc - Troquez vos objets !";
        if(this.categorie.length()>0) {
            this.tagTitle="Troc et echange - "+categorie;
            this.tagDescription="Trocs et échanges avec le bon troc - "+categorie+".";
        }
    }

    public void getGetsCat(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", this.page);
        }
        if(request.getParameter("idCategorie")!=null) {
            this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
            session.setAttribute("idCategorie", this.idCategorie);
            this.page=0;
            session.setAttribute("page", null);
        }
    }

    public void initFieldsCat(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.idCategorie=0;
        if(session.getAttribute("idCategorie")!=null) {
            this.idCategorie=Long.parseLong(session.getAttribute("idCategorie").toString());
        }
        this.page=0;
        if(session.getAttribute("page")!=null) {
            this.page=Integer.parseInt(session.getAttribute("page").toString());
        }
    }

    public void initFieldsSousCat(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.idSousCategorie=0;
        if(session.getAttribute("idSousCategorie")!=null) {
            this.idSousCategorie=Long.parseLong(session.getAttribute("idSousCategorie").toString());
        }
        this.page=0;
        if(session.getAttribute("page")!=null) {
            this.page=Integer.parseInt(session.getAttribute("page").toString());
        }
    }

    public void getGetsSousCat(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("idSousCategorie")!=null) {
            this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
            session.setAttribute("idSousCategorie", this.getIdSousCategorie());
            this.page=0;
            session.setAttribute("page", null);
        }
        if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", this.page);
        }
    }

    public boolean testSousCategorie() {
        boolean flag=false;
        if(this.getIdSousCategorie()!=0) {
            try {
                Objet.getConnection();
                String query="SELECT t1.sous_categorie,t1.id_categorie,t2.categorie FROM table_sous_categories AS t1,table_categories AS t2 WHERE t1.id=? AND t2.id=t1.id_categorie LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.getIdSousCategorie());
                ResultSet result=prepare.executeQuery();
                flag=result.next();
                if(flag) {
                    this.sousCategorie=result.getString("sous_categorie");
                    this.idCategorie=result.getLong("id_categorie");
                    this.categorie=result.getString("categorie");
                }
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                //this.setErrorMsg(ex.getMessage());
                return false;
            }
        }
        return flag;
    }

    public void initTagsSousCat() {
        this.tagTitle="Troc et échange avec le bon troc";
        this.tagDescription="Troc et échange avec le bon troc.";
        if(this.categorie.length()>0&&this.sousCategorie.length()>0) {
            this.tagTitle="Troc et échange - "+this.categorie+" - "+this.sousCategorie;
            this.tagDescription="Troc et échange avec le bon troc - "+this.categorie+" - "+this.sousCategorie+".";
        }
    }

    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the tagTitle
     */
    public String getTagTitle() {
        return tagTitle;
    }

    /**
     * @return the tagDescription
     */
    public String getTagDescription() {
        return tagDescription;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * @return the idSousCategorie
     */
    public long getIdSousCategorie() {
        return idSousCategorie;
    }

}
