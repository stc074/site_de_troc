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
public class Abus extends Objet {
    private long idAnnonce;
    private long id;
    public Abus() {
        super();
    }
    public void signalerAbus(long idAnnonce) {
        try {
            this.idAnnonce = idAnnonce;
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nbAbus FROM table_abus WHERE id_annonce=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            ResultSet result=prepare.executeQuery();
            result.next();
            int nbAbus=result.getInt("nbAbus");
            result.close();
            prepare.close();
            if(nbAbus==0) {
                query="INSERT INTO table_abus (id_annonce,timestamp) VALUES (?,?)";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(2, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idAbus FROM table_abus WHERE id_annonce=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("idAbus");
                result.close();
                prepare.close();
                Mail mail=new Mail(Datas.EMAILADMIN, "ADMINISTRATION", "Un nouvel abus signalé !");
                mail.initMailAbus(this.id);
                mail.send();
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ignoreAbus(long idAbus) {
        try {
            this.id = idAbus;
            Objet.getConnection();
            String query="DELETE FROM table_abus WHERE id=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void effaceAbus(long idAbus) {
        try {
            this.id = idAbus;
            Objet.getConnection();
            String query="SELECT t1.id_annonce,t2.id_membre FROM table_abus AS t1,table_annonces AS t2 WHERE t1.id=? AND t2.id=t1.id_annonce LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idAnnonce=result.getLong("id_annonce");
            long idMembre=result.getLong("id_membre");
            result.close();
            prepare.close();
            Objet.closeConnection();
            Membre membre=new Membre(idMembre);
            Annonce annonce=new Annonce(membre);
            annonce.effaceAnnonce(this.idAnnonce);
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        }
    }
}
