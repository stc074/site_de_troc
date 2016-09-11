/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Messagerie extends Objet {
    private Membre membre;
    private int nbMsgRecusNonLus;
    private int nbMsgEnvoyesNonLus;

    public Messagerie(Membre membre) {
        super();
        this.membre=membre;
    }

    public Messagerie() {
        super();
    }

    public void calculNbRecus() {
        try {
            this.nbMsgRecusNonLus = 0;
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nbMsg FROM table_messages WHERE id_membre_destinataire=? AND etat='0'";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbMsgRecusNonLus=result.getInt("nbMsg");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
            this.getMembre().setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
            this.getMembre().setId(0);
        }
    }

    public void calculNbEnvoyes() {
        try {
            this.nbMsgEnvoyesNonLus = 0;
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nbMsg FROM table_messages WHERE id_membre_expediteur=? AND etat='0'";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbMsgEnvoyesNonLus=result.getInt("nbMsg");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
            this.getMembre().setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
            this.getMembre().setId(0);
        }
    }
    public void effaceMessageRecu(long idMessage) {
        try {
            Objet.getConnection();
            String query="UPDATE table_messages SET id_prec='0' WHERE id_prec=? AND id_membre_expediteur=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, this.membre.getId());
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_messages WHERE id=? AND id_membre_destinataire=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, this.membre.getId());
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void effaceMessageEnvoye(long idMessage) {
        try {
            Objet.getConnection();
            String query="UPDATE table_messages SET id_prec='0' WHERE id_prec=? AND id_membre_destinataire=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, this.membre.getId());
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_messages WHERE id=? AND id_membre_expediteur=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, this.membre.getId());
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void effaceVieux() {
        try {
            Objet.getConnection();
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis()-((long)(1000.0*60.0*60.0*24.0*30.0*6.0));
            //this.setErrorMsg(ts+"");
            String query="SELECT id FROM table_messages WHERE timestamp<?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, ts);
            ResultSet result=prepare.executeQuery();
            while(result.next()) {
                long idMessage=result.getLong("id");
                String query2="UPDATE table_messages SET id_prec='0' WHERE id_prec=?";
                PreparedStatement prepare2=Objet.getConn().prepareStatement(query2);
                prepare2.setLong(1, idMessage);
                prepare2.executeUpdate();
                prepare2.close();
                query2="DELETE FROM table_messages WHERE id=?";
                prepare2=Objet.getConn().prepareStatement(query2);
                prepare2.setLong(1, idMessage);
                prepare2.executeUpdate();
                prepare2.close();
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
            //this.setErrorMsg(ex.getMessage());
        }
    }

    /**
     * @return the membre
     */
    public Membre getMembre() {
        return membre;
    }

    /**
     * @return the nbMsgRecusNonLus
     */
    public int getNbMsgRecusNonLus() {
        return nbMsgRecusNonLus;
    }

    /**
     * @return the nbMsgEnvoyesNonLus
     */
    public int getNbMsgEnvoyesNonLus() {
        return nbMsgEnvoyesNonLus;
    }

}
