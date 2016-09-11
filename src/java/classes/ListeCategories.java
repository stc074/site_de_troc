/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class ListeCategories extends Objet {
    
    private String[] uris;
    private String[] categories;
    
    public void initListe() {
        try {
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nbCategories FROM table_categories";
            Statement state=Objet.getConn().createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            int nbCategories=result.getInt("nbCategories");
            result.close();
            state.close();
            this.uris=new String[nbCategories];
            this.categories=new String[nbCategories];
            query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
            state=Objet.getConn().createStatement();
            result=state.executeQuery(query);
            int i=0;
            while(result.next()) {
                long idCategorie=result.getLong("id");
                String categorie=result.getString("categorie");
                String uri="./troc-1-"+idCategorie+"-"+Objet.encodeTitre(categorie)+".html";
                this.uris[i]=uri;
                this.categories[i]=categorie;
                i++;
            }
            result.close();
            state.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(ListeCategories.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(ListeCategories.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        }
    }

    /**
     * @return the uris
     */
    public String[] getUris() {
        return uris;
    }

    /**
     * @return the categories
     */
    public String[] getCategories() {
        return categories;
    }
}
