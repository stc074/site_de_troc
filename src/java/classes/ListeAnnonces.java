/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class ListeAnnonces extends Objet {
    
    private long[] ids;
    private String[] uris;
    private String[] titres;
    private String[] ligne1s;
    private String[] ligne2s;
    private String[] ligne3s;
    private String[] extensions;
    private int[] indexs;
    private int[] largeurs;
    private int[] hauteurs;
    private int nbAnnonces;
    private int nbMembres;
    
    public void initListe() {
        try {
            this.nbAnnonces=0;
            this.nbMembres=0;
            this.ids=new long[Datas.NBANNONCESINDEX];
            this.uris=new String[Datas.NBANNONCESINDEX];
            this.titres=new String[Datas.NBANNONCESINDEX];
            this.ligne1s=new String[Datas.NBANNONCESINDEX];
            this.ligne2s=new String[Datas.NBANNONCESINDEX];
            this.ligne3s=new String[Datas.NBANNONCESINDEX];
            this.extensions=new String[Datas.NBANNONCESINDEX];
            this.indexs=new int[Datas.NBANNONCESINDEX];
            this.largeurs=new int[Datas.NBANNONCESINDEX];
            this.hauteurs=new int[Datas.NBANNONCESINDEX];
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nbAnnonces FROM table_annonces";
            Statement state=Objet.getConn().createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbAnnonces=result.getInt("nbannonces");
            result.close();
            state.close();
            query="SELECT COUNT(id) AS nbMembres FROM table_membres";
            state=Objet.getConn().createStatement();
            result=state.executeQuery(query);
            result.next();
            this.nbMembres=result.getInt("nbMembres");
            result.close();
            state.close();
            query="SELECT t1.id,t1.titre,t1.titre_contre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5, t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1,table_membres AS t2,table_categories AS t3,table_sous_categories AS t4,table_regions AS t5,table_departements AS t6,table_communes AS t7 WHERE t1.etat='1' AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT 0,"+Datas.NBANNONCESINDEX;
            state=Objet.getConn().createStatement();
            result=state.executeQuery(query);
            int i=0;
            Calendar cal=Calendar.getInstance();
            File fileMini1=null;
            Img img=new Img();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                String titre=result.getString("titre");
                String titreContre=result.getString("titre_contre");
                String[] exts=new String [5];
                exts[0]=result.getString("extension1");
                exts[1]=result.getString("extension2");
                exts[2]=result.getString("extension3");
                exts[3]=result.getString("extension4");
                exts[4]=result.getString("extension5");
                long timestamp=result.getLong("timestamp");
                String pseudo=result.getString("pseudo");
                String categorie=result.getString("categorie");
                String sousCategorie=result.getString("sous_categorie");
                String region=result.getString("region");
                String departement=result.getString("departement");
                String commune=result.getString("commune");
                String codePostal=result.getString("code_postal");
                cal.setTimeInMillis(timestamp);
                this.ids[i]=idAnnonce;
                this.uris[i]="./annonce-"+idAnnonce+"-troque-"+Objet.encodeTitre(titre)+"-contre-"+Objet.encodeTitre(titreContre)+".html";
                this.titres[i]="Troque "+titre+" contre "+titreContre;
                this.ligne1s[i]="Annonce déposée par "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+((cal.get(Calendar.MONTH))+1)+"-"+cal.get(Calendar.YEAR)+".";
                this.ligne2s[i]=categorie+"&rarr;"+sousCategorie+".";
                this.ligne3s[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                int nbPhotos=0;
                for(int j=1;j<=5;j++) {
                    String extension=exts[j-1];
                    if(extension.length()>0&&nbPhotos==0) {
                        String filenameMini1=Datas.DIR+"photos/mini1_"+idAnnonce+"_"+j+extension;
                        fileMini1=new File(filenameMini1);
                        if(fileMini1.exists()) {
                            nbPhotos++;
                            img.getSize(fileMini1);
                            this.extensions[i]=extension;
                            this.indexs[i]=j;
                            this.largeurs[i]=img.getWidth();
                            this.hauteurs[i]=img.getHeight();
                        }
                    }
                }
                if(nbPhotos==0) {
                    this.extensions[i]="";
                }
                i++;
            }
            result.close();
            state.close();
            query="UPDATE table_index SET etat=? WHERE id=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setInt(1, 0);
            prepare.setInt(2, 1);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
        } catch (IOException ex) {
            Logger.getLogger(ListeAnnonces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ListeAnnonces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListeAnnonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        }
    }

    public boolean testNelleAnnonce() {
        try {
            boolean flag=false;
            Objet.getConnection();
            String query="SELECT etat FROM table_index WHERE id=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setInt(1, 1);
            ResultSet result=prepare.executeQuery();
            result.next();
            int etat=result.getInt("etat");
            if(etat==1) {
                flag=true;
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
            return flag;
        } catch (NamingException ex) {
            Logger.getLogger(ListeAnnonces.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ListeAnnonces.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * @return the uris
     */
    public String[] getUris() {
        return uris;
    }

    /**
     * @return the titres
     */
    public String[] getTitres() {
        return titres;
    }

    /**
     * @return the ligne1s
     */
    public String[] getLigne1s() {
        return ligne1s;
    }

    /**
     * @return the ligne2s
     */
    public String[] getLigne2s() {
        return ligne2s;
    }

    /**
     * @return the ligne3s
     */
    public String[] getLigne3s() {
        return ligne3s;
    }

    /**
     * @return the extensions
     */
    public String[] getExtensions() {
        return extensions;
    }

    /**
     * @return the largeurs
     */
    public int[] getLargeurs() {
        return largeurs;
    }

    /**
     * @return the hauteurs
     */
    public int[] getHauteurs() {
        return hauteurs;
    }

    /**
     * @return the ids
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * @return the indexs
     */
    public int[] getIndexs() {
        return indexs;
    }

    /**
     * @return the nbAnnonces
     */
    public int getNbAnnonces() {
        return nbAnnonces;
    }

    /**
     * @return the nbMembres
     */
    public int getNbMembres() {
        return nbMembres;
    }
    
}
