/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
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
public class Annonces1 extends Objet {
    private int page;
    private Connection con;
    private long idCategorie;
    private String tagTitle;
    private String tagDescription;
    private String[] urlsSousCat;
    private String[] titresSousCat;
    private int nbSousCategories;
    private String categorie;
    private int nbAnnoncesPage;
    private int nbAnnonces;
    private int nbPages;
    private String[] urls;
    private String[] titres;
    private String[] lignes1;
    private String[] lignes2;
    private String[] lignes3;
    private String[] codesImg;
    private int prem;
    private int der;
    private int nbAnnoncesPage2;
    private long idSousCategorie;
    private long[] idsSousCat;
    private String sousCategorie;
    private String uriParent;
    
    public Annonces1(long idCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.page=0;
    }

    public Annonces1(long idCategorie, long idSousCategorie) {
        super();
        this.page=0;
        this.idCategorie=idCategorie;
        this.idSousCategorie=idSousCategorie;
        this.uriParent="./";
    }
    
    public void initListeAnnoncesCat() {
        try {
            this.nbAnnoncesPage=0;
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nbAnnonces FROM table_annonces WHERE id_categorie=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            prepare.close();
            if(this.getNbAnnonces()>0) {
                this.nbPages=(int)(Math.ceil((double)this.getNbAnnonces()/(double)Datas.NBANNONCESPAGE));
                if(this.getNbAnnonces()<=Datas.NBANNONCESPAGE) {
                    this.nbAnnoncesPage=this.getNbAnnonces();
                }
                else if(this.page<(this.getNbPages()-1)) {
                    this.nbAnnoncesPage=Datas.NBANNONCESPAGE;
                }
                else if(this.page==(this.getNbPages()-1)) {
                    this.nbAnnoncesPage=this.getNbAnnonces()-((this.getNbPages()-1)*Datas.NBANNONCESPAGE);
                }
                this.nbAnnoncesPage2=(int)(Math.ceil((double)this.nbAnnoncesPage/(double)2));
                this.prem=this.page-5;
                if(this.prem<0) {
                    this.prem=0;
                }
                this.der=this.page+5;
                if(this.der>(this.nbPages-1)) {
                    this.der=this.nbPages-1;
                }
                this.urls=new String[this.getNbAnnoncesPage()];
                this.titres=new String[this.getNbAnnoncesPage()];
                this.codesImg=new String[this.getNbAnnoncesPage()];
                this.lignes1=new String[this.getNbAnnoncesPage()];
                this.lignes2=new String[this.getNbAnnoncesPage()];
                this.lignes3=new String[this.getNbAnnoncesPage()];
                query="SELECT t1.id,t1.titre,t1.titre_contre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1,table_membres AS t2,table_categories AS t3,table_sous_categories AS t4,table_regions AS t5,table_departements AS t6,table_communes AS t7 WHERE t1.id_categorie=? AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                result=prepare.executeQuery();
                Calendar cal=Calendar.getInstance();
                Img img=new Img();
                File fileMini1;
                int i=0;
                String[] exts=new String[5];
                while(result.next()) {
                    long idAnnonce=result.getLong("id");
                    String titre=result.getString("titre");
                    String titreContre=result.getString("titre_contre");
                    exts[0]=result.getString("extension1");
                    exts[1]=result.getString("extension2");
                    exts[2]=result.getString("extension3");
                    exts[3]=result.getString("extension4");
                    exts[4]=result.getString("extension5");
                    long timestamp=result.getLong("timestamp");
                    String pseudo=result.getString("pseudo");
                    String cat=result.getString("categorie");
                    String sousCat=result.getString("sous_categorie");
                    String region=result.getString("region");
                    String departement=result.getString("departement");
                    String commune=result.getString("commune");
                    String codePostal=result.getString("code_postal");
                    cal.setTimeInMillis(timestamp);
                    int nbPhotos=0;
                    for(int j=1; j<=5; j++) {
                        String ext=exts[j-1];
                        if(ext.length()>0&&nbPhotos==0) {
                            String filenameMini1=Datas.DIR+"photos/mini1_"+idAnnonce+"_"+j+ext;
                            fileMini1=new File(filenameMini1);
                            if(fileMini1.exists()) {
                                nbPhotos++;
                                try {
                                    img.getSize(fileMini1);
                                    int largeur=img.getWidth();
                                    int hauteur=img.getHeight();
                                    this.codesImg[i]="<img src=\"./photo-mini-1-"+idAnnonce+"-"+j+""+ext+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniature\"/>";
                                } catch (IOException ex) {
                                    Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
                                    this.codesImg[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                                }
                            }
                        }
                    }
                    if(nbPhotos==0) {
                        this.codesImg[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                    }
                    this.urls[i]="./annonce-"+idAnnonce+"-troque-"+Objet.encodeTitre(titre)+"-contre-"+Objet.encodeTitre(titreContre)+".html";
                    this.titres[i]="Troque "+titre+" contre "+titreContre;
                    this.lignes1[i]="Annonce déposée par "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    this.lignes2[i]=cat+"&nbsp;&rarr;&nbsp;"+sousCat+".";
                    this.lignes3[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
                this.idCategorie=0;
            }
        }
    }

    public void getGets(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            if(this.getPage()==0) {
                session.setAttribute("page", null);
            }
            else {
                session.setAttribute("page", getPage());
            }
        }
    }

    public void initValues(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        if(session.getAttribute("page")!=null) {
            this.page=Integer.parseInt(session.getAttribute("page").toString());
        }
    }

    public void initTagsCategorie() {
        try {
            this.tagTitle="Trocs et échanges avec le bon troc";
            this.tagDescription="Le bon troc - Toutes les annonces de trocs et d'échanges.";
            this.con=Objet.getConnection2();
            String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.categorie=result.getString("categorie");
            result.close();
            prepare.close();
            this.tagTitle="Trocs - "+this.categorie;
            this.tagDescription="Le bon troc - "+this.categorie+".";
            this.categorie=this.categorie.toUpperCase();
        } catch (NamingException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
                this.idCategorie=0;
            }
        }
    }

    public void initListeSousCat() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nbSousCategories FROM table_sous_categories WHERE id_categorie=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbSousCategories=result.getInt("nbSousCategories");
            result.close();
            prepare.close();
            if(this.getNbSousCategories()>0) {
                this.idsSousCat=new long[this.nbSousCategories];
                this.urlsSousCat=new String[this.getNbSousCategories()];
                this.titresSousCat=new String[this.getNbSousCategories()];
                query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC LIMIT 0,"+this.getNbSousCategories();
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                    long idSousCat=result.getLong("id");
                    String sousCat=result.getString("sous_categorie");
                    this.idsSousCat[i]=idSousCat;
                    this.urlsSousCat[i]="./troc-2-"+this.idCategorie+"-"+idSousCat+"-"+Objet.encodeTitre(sousCat)+".html";
                    this.titresSousCat[i]=sousCat.toUpperCase();
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        }
    }

    public void initTagsSousCategorie() {
        try {
            this.tagTitle="Trocs et échanges avec le bon troc";
            this.tagDescription="Le bon troc - Votre site de trocs et 'échanges.";
            this.con=Objet.getConnection2();
            String query="SELECT t1.sous_categorie,t2.categorie FROM table_sous_categories AS t1,table_categories AS t2 WHERE t1.id=? AND t1.id_categorie=? AND t2.id=t1.id_categorie LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idSousCategorie);
            prepare.setLong(2, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.sousCategorie=result.getString("sous_categorie");
            this.categorie=result.getString("categorie");
            result.close();
            prepare.close();
            this.tagTitle="Trocs  - "+this.categorie+" - "+this.getSousCategorie();
            this.tagDescription="Le bon troc - "+this.categorie+" - "+this.getSousCategorie()+".";
            this.uriParent="./troc-1-"+this.idCategorie+"-"+Objet.encodeTitre(this.categorie)+".html";
            this.categorie=this.categorie.toUpperCase();
            this.sousCategorie=this.sousCategorie.toUpperCase();
        } catch (NamingException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
                this.idCategorie=0;
            }
        }
    }

    public void initListeAnnoncesSousCat() {
        try {
            this.nbAnnoncesPage=0;
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nbAnnonces FROM table_annonces WHERE id_categorie=? AND id_sous_categorie=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            prepare.setLong(2, this.idSousCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            prepare.close();
            if(this.nbAnnonces>0) {
                this.nbPages=(int)(Math.ceil((double)this.nbAnnonces/(double)Datas.NBANNONCESPAGE));
                if(this.nbAnnonces<=Datas.NBANNONCESPAGE) {
                    this.nbAnnoncesPage=this.nbAnnonces;
                }
                else if(this.page<(this.nbPages-1)) {
                    this.nbAnnoncesPage=Datas.NBANNONCESPAGE;
                }
                else if(this.page==(this.nbPages-1)) {
                    this.nbAnnoncesPage=this.nbAnnonces-((this.nbPages-1)*Datas.NBANNONCESPAGE);
                }
                this.nbAnnoncesPage2=(int)(Math.ceil((double)this.nbAnnoncesPage/(double)2));
                this.prem=this.page-5;
                if(this.prem<0) {
                    this.prem=0;
                }
                this.der=this.page+5;
                if(this.der>(this.nbPages-1)) {
                    this.der=this.nbPages-1;
                }
                this.urls=new String[this.nbAnnoncesPage];
                this.titres=new String[this.nbAnnoncesPage];
                this.codesImg=new String[this.nbAnnoncesPage];
                this.lignes1=new String[this.nbAnnoncesPage];
                this.lignes2=new String[this.nbAnnoncesPage];
                this.lignes3=new String[this.nbAnnoncesPage];
                query="SELECT t1.id,t1.titre,t1.titre_contre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.region,t4.departement,t5.commune,t5.code_postal FROM table_annonces AS t1,table_membres AS t2,table_regions AS t3,table_departements AS t4,table_communes AS t5 WHERE t1.id_categorie=? AND t1.id_sous_categorie=? AND t2.id=t1.id_membre AND t3.id_region=t2.id_region AND t4.id_departement=t2.id_departement AND t5.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setLong(2, this.idSousCategorie);
                result=prepare.executeQuery();
                String[] exts=new String[5];
                Img img=new Img();
                Calendar cal=Calendar.getInstance();
                File fileMini1;
                int i=0;
                while(result.next()) {
                    long idAnnonce=result.getLong("id");
                    String titre=result.getString("titre");
                    String titreContre=result.getString("titre_contre");
                    exts[0]=result.getString("extension1");
                    exts[1]=result.getString("extension2");
                    exts[2]=result.getString("extension3");
                    exts[3]=result.getString("extension4");
                    exts[4]=result.getString("extension5");
                    long timestamp=result.getLong("timestamp");
                    String pseudo=result.getString("pseudo");
                    String region=result.getString("region");
                    String departement=result.getString("departement");
                    String commune=result.getString("commune");
                    String codePostal=result.getString("code_postal");
                    cal.setTimeInMillis(timestamp);
                    int nbPhotos=0;
                    for(int j=1; j<=5; j++) {
                        String ext=exts[j-1];
                        if(ext.length()>0&&nbPhotos==0) {
                            String filenameMini1=Datas.DIR+"photos/mini1_"+idAnnonce+"_"+j+""+ext;
                            fileMini1=new File(filenameMini1);
                            if(fileMini1.exists()) {
                                nbPhotos++;
                                try {
                                    img.getSize(fileMini1);
                                    int largeur=img.getWidth();
                                    int hauteur=img.getHeight();
                                    this.codesImg[i]="<img src=\"./photo-mini-1-"+idAnnonce+"-"+j+""+ext+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniature\"/>";
                                } catch (IOException ex) {
                                    Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
                                    this.codesImg[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                                }
                            }
                        }
                    }
                    if(nbPhotos==0) {
                        this.codesImg[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                    }
                    this.urls[i]="./annonce-"+idAnnonce+"-troque-"+Objet.encodeTitre(titre)+"-contre-"+Objet.encodeTitre(titreContre)+".html";
                    this.titres[i]="Troque "+titre+" contre "+titreContre;
                    this.lignes1[i]="Annonce de déposée par "+pseudo+" le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    this.lignes2[i]=this.categorie+"&nbsp;&rarr;&nbsp;"+this.sousCategorie+".";
                    this.lignes3[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Annonces1.class.getName()).log(Level.SEVERE, null, ex);
                this.idCategorie=0;
            }
        }
    }

    public void reset(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        session.setAttribute("page", null);
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
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
     * @return the urlsSousCat
     */
    public String[] getUrlsSousCat() {
        return urlsSousCat;
    }

    /**
     * @return the titresSousCat
     */
    public String[] getTitresSousCat() {
        return titresSousCat;
    }

    /**
     * @return the nbSousCategories
     */
    public int getNbSousCategories() {
        return nbSousCategories;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the nbAnnoncesPage
     */
    public int getNbAnnoncesPage() {
        return nbAnnoncesPage;
    }

    /**
     * @return the nbAnnonces
     */
    public int getNbAnnonces() {
        return nbAnnonces;
    }

    /**
     * @return the nbPages
     */
    public int getNbPages() {
        return nbPages;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

    /**
     * @return the titres
     */
    public String[] getTitres() {
        return titres;
    }

    /**
     * @return the lignes1
     */
    public String[] getLignes1() {
        return lignes1;
    }

    /**
     * @return the lignes2
     */
    public String[] getLignes2() {
        return lignes2;
    }

    /**
     * @return the lignes3
     */
    public String[] getLignes3() {
        return lignes3;
    }

    /**
     * @return the codesImg
     */
    public String[] getCodesImg() {
        return codesImg;
    }

    /**
     * @return the prem
     */
    public int getPrem() {
        return prem;
    }

    /**
     * @return the der
     */
    public int getDer() {
        return der;
    }

    /**
     * @return the nbAnnoncesPage2
     */
    public int getNbAnnoncesPage2() {
        return nbAnnoncesPage2;
    }

    /**
     * @return the idSousCategorie
     */
    public long getIdSousCategorie() {
        return idSousCategorie;
    }

    /**
     * @return the idsSousCat
     */
    public long[] getIdsSousCat() {
        return idsSousCat;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * @return the uriParent
     */
    public String getUriParent() {
        return uriParent;
    }

}
