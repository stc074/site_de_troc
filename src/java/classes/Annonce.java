/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Annonce extends Objet {
    
    private Membre membre;
    private String titre;
    private String contenu;
    private String titreContre;
    private String contenuContre;
    private long idCategorie;
    private long idSousCategorie;
    private long idCategorieContre;
    private long idSousCategorieContre;
    private String captcha;
    private long id;
    private String uri;
    private long timestamp;
    private String[] extensions;
    private String pseudo;
    private String region;
    private String departement;
    private String codePostal;
    private String commune;
    private String categorie;
    private String sousCategorie;
    private String tagTitle;
    private String tagDescription;
    private String categorieContre;
    private String sousCategorieContre;

    public Annonce(Membre membre) {
        this.membre=membre;
        this.titre="";
        this.contenu="";
        this.titreContre="";
        this.contenuContre="";
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.idCategorieContre=0;
        this.idSousCategorieContre=0;
        this.extensions=new String[5];
    }

    public Annonce(long idAnnonce) {
        this.id=idAnnonce;
        this.extensions=new String[5];
    }

    public Annonce() {
        super();
    }

    public void getPosts1(HttpServletRequest request) {
        this.titre=request.getParameter("titre");
        this.titre=Objet.codeHTML(this.titre);
        this.contenu=request.getParameter("contenu");
        this.contenu=Objet.codeHTML2(this.contenu);
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
        this.titreContre=request.getParameter("titreContre");
        this.titreContre=Objet.codeHTML(this.titreContre);
        this.contenuContre=request.getParameter("contenuContre");
        this.contenuContre=Objet.codeHTML2(this.contenuContre);
        this.idCategorieContre=Long.parseLong(request.getParameter("idCategorieContre"));
        this.idSousCategorieContre=Long.parseLong(request.getParameter("idSousCategorieContre"));
        this.captcha=request.getParameter("captcha").toLowerCase();
        this.captcha=Objet.codeHTML(this.captcha);
    }
    public void verifPosts1(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            if (this.titre.length() == 0) {
                this.setErrorMsg("Champ TITRE DE CE QUE VOUS PROPOSEZ vide.<br/>");
            } else if (this.titre.length() < 2) {
                this.setErrorMsg("Champ TITRE DE CE QUE VOUS PROPOSEZ trop court.<br/>");
            } else if (this.titre.length() > 80) {
                this.setErrorMsg("Champ TITRE DE CE QUE VOUS PROPOSEZ trop long.<br/>");
            }
            if (this.contenu.length() == 0) {
                this.setErrorMsg("Champ DESCRIPTION DE CE QUE VOUS PROPOSEZ vide.<br/>");
            } else if (this.contenu.length() < 5) {
                this.setErrorMsg("Champ DESCRIPTION DE CE QUE VOUS PROPOSEZ trop court.<br/>");
            } else if (this.contenu.length() > 5000) {
                this.setErrorMsg("Champ DESCRIPTION DE CE QUE VOUS PROPOSEZ trop long.<br/>");
            }
            if (this.idCategorie == 0) {
                this.setErrorMsg("Choisissez une CATÉGORIE pour ce que vous proposez SVP.<br/>");
            }
            if (this.idSousCategorie == 0) {
                this.setErrorMsg("Choisissez une SOUS-CATÉGORIE pour ce que vous proposez SVP.<br/>");
            }
            if (this.titreContre.length() == 0) {
                this.setErrorMsg("Champ TITRE DE CE QUE VOUS VOULEZ EN ÉCHANGE vide.<br/>");
            } else if (this.titreContre.length() < 2) {
                this.setErrorMsg("Champ TITRE DE CE QUE VOUS VOULEZ EN ÉCHANGE trop court.<br/>");
            } else if (this.titreContre.length() > 80) {
                this.setErrorMsg("Champ TITRE DE CE QUE VOUS VOULEZ EN ÉCHANGE trop long.<br/>");
            }
            if (this.contenuContre.length() == 0) {
                this.setErrorMsg("Champ DESCRIPTION DE CE QUE VOUS VOULEZ EN ÉCHANGE vide.<br/>");
            } else if (this.contenuContre.length() < 5) {
                this.setErrorMsg("Champ DESCRIPTION DE CE QUE VOUS VOULEZ EN ÉCHANGE trop court.<br/>");
            } else if (this.contenuContre.length() > 5000) {
                this.setErrorMsg("Champ DESCRIPTION DE CE QUE VOUS VOULEZ EN ÉCHANGE trop long.<br/>");
            }
            if (this.idCategorieContre == 0) {
                this.setErrorMsg("Veuillez choisir une CATÉGORIE pour ce que vous voulez en échange SVP.<br/>");
            }
            if (this.idSousCategorieContre == 0) {
                this.setErrorMsg("Veuillez choisir une SOUS-CATÉGORIE pour ce que vous voulez en échange SVP.<br/>");
            }
            if (this.captcha.length() == 0) {
                this.setErrorMsg("Champ CODE ANTI-ROBOT vide.<br/>");
            } else if (this.captcha.length() > 5) {
                this.setErrorMsg("Champ CODE ANTI-ROBOT trop long.<br/>");
            } else if (session.getAttribute("captcha") == null) {
                this.setErrorMsg("Session CODE ANTI-ROBOT dépassée.<br/>");
            } else if (!session.getAttribute("captcha").toString().equals(Objet.getEncoded(this.captcha))) {
                this.setErrorMsg("Mauvais CODE ANTI-ROBOT.<br/>");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

    public void enregDepot(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
        HttpSession session=request.getSession(true);
        try {
            Objet.getConnection();
            String query="INSERT INTO table_annonces (id_membre,titre,contenu,id_categorie,id_sous_categorie,titre_contre,contenu_contre,id_categorie_contre,id_sous_categorie_contre,timestamp,last_timestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            prepare.setString(2, this.titre);
            prepare.setString(3, this.contenu);
            prepare.setLong(4, this.idCategorie);
            prepare.setLong(5, this.idSousCategorie);
            prepare.setString(6, this.titreContre);
            prepare.setString(7, this.contenuContre);
            prepare.setLong(8, this.idCategorieContre);
            prepare.setLong(9, this.idSousCategorieContre);
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis();
            prepare.setLong(10, ts);
            prepare.setLong(11, ts);
            prepare.executeUpdate();
            prepare.close();
            query="SELECT LAST_INSERT_ID() AS idAnnonce FROM table_annonces WHERE id_membre=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
                this.setId(result.getLong("idAnnonce"));
            session.setAttribute("captcha", null);
            session.setAttribute("idAnnonce", this.getId());
            result.close();
            prepare.close();
            query="UPDATE table_index SET etat=? WHERE id=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setInt(1, 1);
            prepare.setInt(2, 1);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            this.uri="annonce-"+this.getId()+"-troque-"+Objet.encodeTitre(this.titre)+"-contre-"+Objet.encodeTitre(this.titreContre)+".html";
            Mail mail=new Mail(this.membre.getEmail(), this.membre.getPseudo(), "Annonce publiée !");
            mail.initMailAnnonce1(this.membre.getPseudo(), this.titre+" contre "+this.titreContre, this.getUri(), this.getId());
            mail.send();
            this.setTest(1);
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
        }
    }

    public void initEditInfos(long idAnnonce) {
        try {
            this.setId(idAnnonce);
            Objet.getConnection();
            String query="SELECT titre,contenu,id_categorie,id_sous_categorie,titre_contre,contenu_contre,id_categorie_contre,id_sous_categorie_contre,timestamp FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.titre=result.getString("titre");
            this.contenu=result.getString("contenu");
            this.idCategorie=result.getLong("id_categorie");
            this.idSousCategorie=result.getLong("id_sous_categorie");
            this.titreContre=result.getString("titre_contre");
            this.contenuContre=result.getString("contenu_contre");
            this.idCategorieContre=result.getLong("id_categorie_contre");
            this.idSousCategorieContre=result.getLong("id_sous_categorie_contre");
            this.timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }

    public void enregEdit(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
        try {
            HttpSession session = request.getSession(true);
            Objet.getConnection();
            String query="UPDATE table_annonces SET titre=?,contenu=?,id_categorie=?,id_sous_categorie=?,titre_contre=?,contenu_contre=?,id_categorie_contre=?,id_sous_categorie_contre=?,timestamp=?,last_timestamp=? WHERE id=? AND id_membre=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setString(1, this.titre);
            prepare.setString(2, this.contenu);
            prepare.setLong(3, this.idCategorie);
            prepare.setLong(4, this.idSousCategorie);
            prepare.setString(5, this.titreContre);
            prepare.setString(6, this.contenuContre);
            prepare.setLong(7, this.idCategorieContre);
            prepare.setLong(8, this.idSousCategorieContre);
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis();
            prepare.setLong(9, ts);
            prepare.setLong(10, ts);
            prepare.setLong(11, this.id);
            prepare.setLong(12, this.membre.getId());
            prepare.executeUpdate();
            prepare.close();
            query="UPDATE table_index SET etat=? WHERE id=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setInt(1, 1);
            prepare.setInt(2, 1);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            session.setAttribute("captcha", null);
            session.setAttribute("idAnnonce", this.id);
            this.uri="troque-"+Objet.encodeTitre(this.titre)+"-contre-"+Objet.encodeTitre(this.titreContre)+".html";
            Mail mail=new Mail(this.membre.getEmail(), this.membre.getPseudo(), "Annonce modifiée !");
            mail.initMailAnnonceModif1(this.membre.getPseudo(), this.titre+" contre "+this.titreContre, this.uri, this.id);
            mail.send();
            this.setTest(1);
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
        }
    }

    public void initInfos() {
        try {
            Objet.getConnection();
            /*String query="UPDATE table_annonces SET last_timestamp=? WHERE id=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis();
            prepare.setLong(1, ts);
            prepare.setLong(2, this.id);
            prepare.executeUpdate();
            prepare.close();*/
            String query="SELECT t1.titre,t1.contenu,t1.titre_contre,t1.contenu_contre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.region,t4.departement,t5.code_postal,t5.commune,t6.categorie,t7.sous_categorie,t8.categorie AS categorieContre,t9.sous_categorie AS sousCategorieContre FROM table_annonces AS t1,table_membres AS t2,table_regions AS t3,table_departements AS t4,table_communes AS t5,table_categories AS t6,table_sous_categories AS t7,table_categories AS t8,table_sous_categories AS t9 WHERE t1.id=? AND t2.id=t1.id_membre AND t3.id_region=t2.id_region AND t4.id_departement=t2.id_departement AND t5.id=t2.id_commune AND t6.id=t1.id_categorie AND t7.id=t1.id_sous_categorie AND t8.id=t1.id_categorie_contre AND t9.id=t1.id_sous_categorie_contre LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.titre=result.getString("titre");
            this.contenu=result.getString("contenu");
            this.titreContre=result.getString("titre_contre");
            this.contenuContre=result.getString("contenu_contre");
            this.extensions[0]=result.getString("extension1");
            this.extensions[1]=result.getString("extension2");
            this.extensions[2]=result.getString("extension3");
            this.extensions[3]=result.getString("extension4");
            this.extensions[4]=result.getString("extension5");
            this.timestamp=result.getLong("timestamp");
            this.pseudo=result.getString("pseudo");
            this.region=result.getString("region");
            this.departement=result.getString("departement");
            this.codePostal=result.getString("code_postal");
            this.commune=result.getString("commune");
            this.categorie=result.getString("categorie");
            this.sousCategorie=result.getString("sous_categorie");
            this.categorieContre=result.getString("categorieContre");
            this.sousCategorieContre=result.getString("sousCategorieContre");
            result.close();
            prepare.close();
            this.tagTitle="Troque "+this.titre+" contre "+this.titreContre;
            this.tagDescription="Troc et echange avec le bon troc - Troque "+this.titre+" contre "+this.titreContre+".";
            this.uri="/annonce-"+this.id+"-troque-"+Objet.encodeTitre(this.titre)+"-contre-"+Objet.encodeTitre(this.titreContre)+".html";
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }

    public void initMessageInfos() {
        try {
            Objet.getConnection();
            String query="SELECT titre,titre_contre FROM table_annonces WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.titre="Troque "+result.getString("titre")+" contre "+result.getString("titre_contre");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }

    public void initMessageInfos2() throws SQLException {
           String query="SELECT titre,titre_contre FROM table_annonces WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.titre="Troque "+result.getString("titre")+" contre "+result.getString("titre_contre");
            result.close();
            prepare.close();
    }

    public void effaceAnnonce(long idAnnonce) {
        try {
            this.id=idAnnonce;
            Objet.getConnection();
            String query="SELECT extension1,extension2,extension3,extension4,extension5 FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.extensions[0]=result.getString("extension1");
            this.extensions[1]=result.getString("extension2");
            this.extensions[2]=result.getString("extension3");
            this.extensions[3]=result.getString("extension4");
            this.extensions[4]=result.getString("extension5");
            result.close();
            prepare.close();
            for(int i=1; i<=5; i++) {
                String extension=this.extensions[i-1];
                if(extension.length()>0) {
                    String filename=Datas.DIR+"photos/"+this.id+"_"+i+extension;
                    String filenameMini1=Datas.DIR+"photos/mini1_"+this.id+"_"+i+extension;
                    String filenameMini2=Datas.DIR+"photos/mini2_"+this.id+"_"+i+extension;
                    File file=new File(filename);
                    File fileMini1=new File(filenameMini1);
                    File fileMini2=new File(filenameMini2);
                    if(file.exists()) {
                        file.delete();
                    }
                    if(fileMini1.exists()) {
                        fileMini1.delete();
                    }
                    if(fileMini2.exists()) {
                        fileMini2.delete();
                    }
                }
            }
            query="DELETE FROM table_messages WHERE id_annonce=? AND (id_membre_expediteur=? OR id_membre_destinataire=?)";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membre.getId());
            prepare.setLong(3, this.membre.getId());
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_annonces WHERE id=? AND id_membre=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membre.getId());
            prepare.executeUpdate();
            prepare.close();
            query="UPDATE table_index SET etat=? WHERE id=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setInt(1, 1);
            prepare.setInt(2, 1);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void effaceOlds() {
        try {
            Objet.getConnection();
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis()-(long)(1000.0*60.0*60.0*24.0*30.0*6.0);
            String query="SELECT id,id_membre FROM table_annonces WHERE last_timestamp<?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, ts);
            ResultSet result=prepare.executeQuery();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                long idMembre=result.getLong("id_membre");
                effaceAnnonce2(idAnnonce, idMembre);
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void effaceAnnonce2(long idAnnonce, long idMembre) throws SQLException {
        String query="SELECT extension1,extension2,extension3,extension4,extension5 FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        ResultSet result=prepare.executeQuery();
        result.next();
        String exts[]=new String[5];
        exts[0]=result.getString("extension1");
        exts[1]=result.getString("extension2");
        exts[2]=result.getString("extension3");
        exts[3]=result.getString("extension4");
        exts[4]=result.getString("extension5");
        result.close();
        prepare.close();
        for(int i=1; i<=5; i++) {
            String extension=exts[i-1];
            if(extension.length()>0) {
                String filename=Datas.DIR+"photos/"+idAnnonce+"_"+i+extension;
                String filenameMini1=Datas.DIR+"photos/mini1_"+idAnnonce+"_"+i+extension;
                String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+"_"+i+extension;
                File file=new File(filename);
                File fileMini1=new File(filenameMini1);
                File fileMini2=new File(filenameMini2);
                if(file.exists()) {
                    file.delete();
                }
                if(fileMini1.exists()) {
                    fileMini1.delete();
                }
                if(fileMini2.exists()) {
                    fileMini2.delete();
                }
            }
        }
        query="DELETE FROM table_messages WHERE id_annonce=? AND (id_membre_expediteur=? OR id_membre_destinataire=?)";
        prepare=Objet.getConn().prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        prepare.setLong(3, idMembre);
        prepare.executeUpdate();
        prepare.close();
        query="DELETE FROM table_annonces WHERE id=? AND id_membre=?";
        prepare=Objet.getConn().prepareStatement(query);
        prepare.setLong(1, idAnnonce);
        prepare.setLong(2, idMembre);
        prepare.executeUpdate();
        prepare.close();
        query="UPDATE table_index SET etat=? WHERE id=?";
        prepare=Objet.getConn().prepareStatement(query);
        prepare.setInt(1, 1);
        prepare.setInt(2, 1);
        prepare.executeUpdate();
        prepare.close();
    }

    @Override
    public void blank() {
        super.blank();
        this.membre=null;
        this.titre="";
        this.contenu="";
        this.titreContre="";
        this.contenuContre="";
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.idCategorieContre=0;
        this.idSousCategorieContre=0;
    }
    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * @return the titreContre
     */
    public String getTitreContre() {
        return titreContre;
    }

    /**
     * @return the contenuContre
     */
    public String getContenuContre() {
        return contenuContre;
    }

    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the idSousCategorie
     */
    public long getIdSousCategorie() {
        return idSousCategorie;
    }

    /**
     * @return the idCategorieContre
     */
    public long getIdCategorieContre() {
        return idCategorieContre;
    }

    /**
     * @return the idSousCategorieContre
     */
    public long getIdSousCategorieContre() {
        return idSousCategorieContre;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the extensions
     */
    public String[] getExtensions() {
        return extensions;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @return the departement
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * @return the codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @return the commune
     */
    public String getCommune() {
        return commune;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
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
     * @return the categorieContre
     */
    public String getCategorieContre() {
        return categorieContre;
    }

    /**
     * @return the sousCategorieContre
     */
    public String getSousCategorieContre() {
        return sousCategorieContre;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}
