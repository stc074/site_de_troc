/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

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
public class Message extends Objet {
    private Membre membreExpediteur;
    private long idMembreDestinataire;
    private Membre membreDestinataire;
    private Annonce annonce;
    private String objetMsg;
    private String contenuMsg;
    private String captcha;
    private long id;
    private long idAnnonce;
    private long idMembreExpediteur;
    private long idPrec;
    private String objet;
    private String contenu;
    private long timestamp;
    private int etat;
    private String objetPrec;
    private String contenuPrec;
    private long timestampPrec;

    public Message(Membre membre) {
        this.membreExpediteur=membre;
        this.objetMsg="";
        this.contenuMsg="";
    }

    public Message() {
        this.objetMsg="";
        this.contenuMsg="";
    }

    public Message(long idMessage) {
        this.id=idMessage;
        this.objetMsg="";
        this.contenuMsg="";
    }

    public void initContact(long idAnnonce) {
        try {
            this.annonce=new Annonce(idAnnonce);
            getAnnonce().initMessageInfos();
            Objet.getConnection();
            String query="SELECT id_membre FROM table_annonces WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.annonce.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idMembreDestinataire=result.getLong("id_membre");
            this.membreDestinataire=new Membre(this.idMembreDestinataire);
            this.getMembreDestinataire().initInfos(this.idMembreDestinataire);
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.getAnnonce().setId(0);
            this.membreDestinataire.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.getAnnonce().setId(0);
            this.membreDestinataire.setId(0);
        }
    }

    public void getPosts1(HttpServletRequest request) {
        this.objetMsg=request.getParameter("objetMsg");
        this.objetMsg=Objet.codeHTML(this.objetMsg);
        this.contenuMsg=request.getParameter("contenuMsg");
        this.contenuMsg=Objet.codeHTML2(this.contenuMsg);
        this.captcha=request.getParameter("captcha");
        this.captcha=Objet.codeHTML(this.captcha).toLowerCase();
    }

    public void verifPosts1(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            if (this.objetMsg.length() == 0) {
                this.setErrorMsg("Champ OBJET DU MESSAGE vide.<br/>");
            } else if (this.objetMsg.length() > 40) {
                this.setErrorMsg("Champ OBJET DU MESSAGE trop long.<br/>");
            }
            if (this.contenuMsg.length() == 0) {
                this.setErrorMsg("Champ CONTENU DU MESSAGE vide.<br/>");
            } else if (this.contenuMsg.length() > 3000) {
                this.setErrorMsg("Champ CONTENU DU MESSAGE trop long.<br/>");
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
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

    public void sendContact(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
        try {
            HttpSession session = request.getSession(true);
            Objet.getConnection();
            String query="INSERT INTO table_messages (id_annonce,id_membre_expediteur,id_membre_destinataire,objet,contenu,timestamp) VALUES (?,?,?,?,?,?)";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.annonce.getId());
            prepare.setLong(2, this.getMembreExpediteur().getId());
            prepare.setLong(3, this.membreDestinataire.getId());
            prepare.setString(4, this.objetMsg);
            prepare.setString(5, this.contenuMsg);
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis();
            prepare.setLong(6, ts);
            prepare.executeUpdate();
            prepare.close();
            query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_membre_expediteur=? AND id_membre_destinataire=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getMembreExpediteur().getId());
            prepare.setLong(2, this.membreDestinataire.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.id=result.getLong("idMessage");
            result.close();
            prepare.close();
            session.setAttribute("captcha", null);
            this.setTest(1);
            Mail mail=new Mail(this.getMembreExpediteur().getEmail(), this.getMembreExpediteur().getPseudo(), "Message envoyé !");
            mail.initMailMessage1(this.getMembreExpediteur().getPseudo(), this.membreDestinataire.getPseudo(), this.objetMsg, this.getId());
            mail.send();
            mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Un nouveau message !");
            mail.initMailMessage2(this.getMembreExpediteur().getPseudo(), this.membreDestinataire.getPseudo(), this.objetMsg, this.getId());
            mail.send();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
        }
    }

    public void initRecu(Membre membre) {
        try {
            Objet.getConnection();
            this.membreDestinataire = membre;
            this.membreDestinataire.initInfos2(this.membreDestinataire.getId());
            String query="SELECT id_annonce,id_prec,id_membre_expediteur,objet,contenu,timestamp,etat FROM table_messages WHERE id=? AND id_membre_destinataire=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.membreDestinataire.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idAnnonce=result.getLong("id_annonce");
            this.idPrec=result.getLong("id_prec");
            this.idMembreExpediteur=result.getLong("id_membre_expediteur");
            this.objet=result.getString("objet");
            this.contenu=result.getString("contenu");
            this.timestamp=result.getLong("timestamp");
            this.etat=result.getInt("etat");
            result.close();
            prepare.close();
            this.membreExpediteur=new Membre(this.idMembreExpediteur);
            this.getMembreExpediteur().initInfos2(this.idMembreExpediteur);
            this.annonce=new Annonce(this.idAnnonce);
            this.annonce.initMessageInfos2();
            if(this.getEtat()==0) {
                query="UPDATE table_messages SET etat='1' WHERE id=? AND id_membre_destinataire=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.id);
                prepare.setLong(2, this.membreDestinataire.getId());
                prepare.executeUpdate();
                prepare.close();
            }
            if(this.getIdPrec()!=0) {
                query="SELECT objet,contenu,timestamp FROM table_messages WHERE id=? AND id_membre_expediteur=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idPrec);
                prepare.setLong(2, this.membreDestinataire.getId());
                result=prepare.executeQuery();
                result.next();
                this.objetPrec=result.getString("objet");
                this.contenuPrec=result.getString("contenu");
                this.timestampPrec=result.getLong("timestamp");
                result.close();
                prepare.close();
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
            //this.setErrorMsg(ex.getMessage());
        }
    }

    public void sendRecu(HttpServletRequest request) {
        try {
            if(this.getErrorMsg().length()==0) {
            Objet.getConnection();
            HttpSession session=request.getSession(true);
            String query="INSERT INTO table_messages (id_annonce,id_prec,id_membre_expediteur,id_membre_destinataire,objet,contenu,timestamp) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.annonce.getId());
            prepare.setLong(2, this.id);
            prepare.setLong(3, this.membreDestinataire.getId());
            prepare.setLong(4, this.membreExpediteur.getId());
            prepare.setString(5, this.objetMsg);
            prepare.setString(6, this.contenuMsg);
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis();
            prepare.setLong(7, ts);
            prepare.executeUpdate();
            prepare.close();
            this.setTest(1);
            query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_membre_expediteur=? AND id_membre_destinataire=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.membreDestinataire.getId());
            prepare.setLong(2, this.membreExpediteur.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            long idMessage=result.getLong("idMessage");
            session.setAttribute("captcha", null);
            Mail mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Message envoyé !");
            mail.initMailMessage1(this.membreDestinataire.getPseudo(), this.membreExpediteur.getPseudo(), this.objetMsg, idMessage);
            mail.send();
            mail=new Mail(this.membreExpediteur.getEmail(), this.membreExpediteur.getPseudo(), "Nouveau message !");
            mail.initMailMessage2(this.membreDestinataire.getPseudo(), this.membreExpediteur.getPseudo(), this.objetMsg, idMessage);
            mail.send();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

    public void iniEnvoye(Membre membre) {
        try {
            Objet.getConnection();
            this.membreExpediteur = membre;
            membre.initInfos2(this.membreExpediteur.getId());
            String query="SELECT id_annonce,id_prec,id_membre_destinataire,objet,contenu,timestamp FROM table_messages WHERE id=? AND id_membre_expediteur=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membreExpediteur.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idAnnonce=result.getLong("id_annonce");
            this.idPrec=result.getLong("id_prec");
            this.idMembreDestinataire=result.getLong("id_membre_destinataire");
            this.objet=result.getString("objet");
            this.contenu=result.getString("contenu");
            this.timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
            this.annonce=new Annonce(this.idAnnonce);
            annonce.initMessageInfos2();
            this.membreDestinataire=new Membre(this.idMembreDestinataire);
            this.membreDestinataire.initInfos2(this.idMembreDestinataire);
            if(this.idPrec!=0) {
                query="SELECT objet,contenu,timestamp FROM table_messages WHERE id=? AND id_membre_destinataire=? LIMIT 0,1";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idPrec);
                prepare.setLong(2, this.membreExpediteur.getId());
                result=prepare.executeQuery();
                result.next();
                this.objetPrec=result.getString("objet");
                this.contenuPrec=result.getString("contenu");
                this.timestampPrec=result.getLong("timestamp");
                result.close();
                prepare.close();
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
            //this.setErrorMsg(ex.getMessage());
        }
    }

    public void sendEnvoye(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session = request.getSession(true);
                Objet.getConnection();
                String query="INSERT INTO table_messages (id_annonce,id_prec,id_membre_expediteur,id_membre_destinataire,objet,contenu,timestamp) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.annonce.getId());
                prepare.setLong(2, this.idPrec);
                prepare.setLong(3, this.membreExpediteur.getId());
                prepare.setLong(4, this.membreDestinataire.getId());
                prepare.setString(5, this.objetMsg);
                prepare.setString(6, this.contenuMsg);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(7, ts);
                prepare.executeUpdate();
                prepare.close();
                this.setTest(1);
                query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_membre_expediteur=? AND id_membre_destinataire=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.membreExpediteur.getId());
                prepare.setLong(2, this.membreDestinataire.getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                long idMessage=result.getLong("idMessage");
                result.close();
                prepare.close();
                Objet.closeConnection();
                session.setAttribute("captcha", null);
                Mail mail=new Mail(this.membreExpediteur.getEmail(), this.membreExpediteur.getPseudo(), "Message envoyé !");
                mail.initMailMessage1(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.objetMsg, idMessage);
                mail.send();
                mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Nouveau message !");
                mail.initMailMessage2(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.objetMsg, idMessage);
                mail.send();
            } catch (NamingException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            }
        }
    }

   @Override
    public void blank() {
        super.blank();
        this.membreDestinataire=null;
        this.membreExpediteur=null;
        this.objetMsg="";
        this.contenuMsg="";
    }
    /**
     * @return the membreDestinataire
     */
    public Membre getMembreDestinataire() {
        return membreDestinataire;
    }

    /**
     * @return the annonce
     */
    public Annonce getAnnonce() {
        return annonce;
    }

    /**
     * @return the objetMsg
     */
    public String getObjetMsg() {
        return objetMsg;
    }

    /**
     * @return the contenuMsg
     */
    public String getContenuMsg() {
        return contenuMsg;
    }

    /**
     * @return the membreExpediteur
     */
    public Membre getMembreExpediteur() {
        return membreExpediteur;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the idPrec
     */
    public long getIdPrec() {
        return idPrec;
    }

    /**
     * @return the objet
     */
    public String getObjet() {
        return objet;
    }

    /**
     * @return the contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the etat
     */
    public int getEtat() {
        return etat;
    }

    /**
     * @return the objetPrec
     */
    public String getObjetPrec() {
        return objetPrec;
    }

    /**
     * @return the contenuPrec
     */
    public String getContenuPrec() {
        return contenuPrec;
    }

    /**
     * @return the timestampPrec
     */
    public long getTimestampPrec() {
        return timestampPrec;
    }

}
