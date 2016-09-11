/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pj
 */
public class CGU extends Objet {

    private long id;
    private String texte;
    private long timestamp;

    public CGU() {
        super();
    }

    public void initInfos() {
        try {
            Objet.getConnection();
            String query="SELECT id,texte,timestamp FROM table_cgu LIMIT 0,1";
            Statement state=Objet.getConn().createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.id=result.getLong("id");
            this.texte=result.getString("texte");
            this.timestamp=result.getLong("timestamp");
            result.close();
            state.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        }
    }

    public void getPosts(HttpServletRequest request) {
        this.texte=request.getParameter("texte");
        this.texte=Objet.codeHTML2(this.texte);
    }
    public void verifPosts() {
        if(this.texte.length()==0)
            this.setErrorMsg("Champ TEXTE vide.<br/>");
        if(this.texte.length()>5000)
            this.setErrorMsg("Champ TEXTE trop long.<br/>");
    }

    public void enregCgu() {
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="UPDATE table_cgu SET texte=?,timestamp=? WHERE id=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.texte);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(2, ts);
                prepare.setLong(3, this.id);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            }
        }
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the texte
     */
    public String getTexte() {
        return texte;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

}
