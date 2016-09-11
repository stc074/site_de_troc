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
import java.sql.Statement;
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
public class Recherche extends Objet {

    private String motsCles;
    private int type;
    private long idCategorie;
    private long idSousCategorie;
    private String idRegion;
    private String idDepartement;
    private int idCommune;
    private String tagTitle;
    private String tagDescription;
    private String condition;
    private int page;
    private Connection con;
    private int nbAnnonces;
    private int nbPages;
    private int nbAnnoncesPage;
    private int nbAnnoncesPage2;
    private String[] urls;
    private String[] titres;
    private String[] lignes1;
    private String[] lignes2;
    private String[] lignes3;
    private String[] codesImg;
    private int prem;
    private int der;
    private int nbSousCategories;
    private long[] idSousCategories;
    private String[] sousCategories;
    private int nbDepartements;
    private String[] idsDepartements;
    private String[] departements;
    private int nbCommunes;
    private int[] idsCommunes;
    private String[] communes;

    public Recherche() {
        super();
        this.motsCles="";
        this.type=0;
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
        this.page=0;
    }

    public void reset(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        session.setAttribute("motsCles", null);
        session.setAttribute("type", null);
        session.setAttribute("idCategorie", null);
        session.setAttribute("idSousCategorie", null);
        session.setAttribute("idRegion", null);
        session.setAttribute("idDepartement", null);
        session.setAttribute("idCommune", null);
        session.setAttribute("page", null);
    }

    public void initFields(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.motsCles="";
        if(session.getAttribute("motsCles")!=null) {
            this.motsCles=Objet.codeHTML(session.getAttribute("motsCles").toString());
        }
        this.type=0;
        if(session.getAttribute("type")!=null) {
            this.type=Integer.parseInt(session.getAttribute("type").toString());
        }
        this.idCategorie=0;
        if(session.getAttribute("idCategorie")!=null) {
            this.idCategorie=Long.parseLong(session.getAttribute("idCategorie").toString());
        }
        this.idSousCategorie=0;
        if(session.getAttribute("idSousCategorie")!=null) {
            this.idSousCategorie=Long.parseLong(session.getAttribute("idSousCategorie").toString());
        }
        this.idRegion="0";
        if(session.getAttribute("idRegion")!=null) {
            this.idRegion=Objet.codeHTML(session.getAttribute("idRegion").toString());
        }
        this.idDepartement="0";
        if(session.getAttribute("idDepartement")!=null) {
            this.idDepartement=Objet.codeHTML(session.getAttribute("idDepartement").toString());
        }
        this.idCommune=0;
        if(session.getAttribute("idCommune")!=null) {
            this.idCommune=Integer.parseInt(session.getAttribute("idCommune").toString());
        }
        this.page=0;
        if(session.getAttribute("page")!=null) {
            this.page=Integer.parseInt(session.getAttribute("page").toString());
        }
    }

    public void getPosts(HttpServletRequest request) {
        this.motsCles=request.getParameter("motsCles");
        this.motsCles=Objet.codeHTML(this.motsCles);
        if(request.getParameter("type")!=null) {
            this.type=Integer.parseInt(request.getParameter("type"));
        }
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
        this.idRegion=request.getParameter("idRegion");
        this.idRegion=Objet.codeHTML(this.idRegion);
        this.idDepartement=request.getParameter("idDepartement");
        this.idDepartement=Objet.codeHTML(this.idDepartement);
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
    }
    public void verifPosts(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(this.motsCles.length()>0) {
            if(this.motsCles.length()>300) {
                this.motsCles="";
                session.setAttribute("motsCles", null);
            } else {
                session.setAttribute("motsCles", this.motsCles);
            }
        } else {
            session.setAttribute("motsCles", null);
        }
        if(this.type!=0) {
            session.setAttribute("type", this.type);
        }
        else {
            session.setAttribute("type", null);
        }
        if(this.idCategorie!=0) {
            session.setAttribute("idCategorie", this.idCategorie);
        }
        else {
            session.setAttribute("idCategorie", null);
        }
        if(this.idSousCategorie!=0) {
            session.setAttribute("idSousCategorie", this.idSousCategorie);
        }
        else {
            session.setAttribute("idSousCategorie", null);
        }
        if(!this.idRegion.equals("0")) {
            session.setAttribute("idRegion", this.idRegion);
        }
        else {
            session.setAttribute("idRegion", null);
        }
        if(!this.idDepartement.equals("0")) {
            session.setAttribute("idDepartement", this.idDepartement);
        }
        else {
            session.setAttribute("idDepartement", null);
        }
        if(this.idCommune!=0) {
            session.setAttribute("idCommune", this.idCommune);
        }
        else {
            session.setAttribute("idCommune", null);
        }
    }

    public void getGets(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("type")!=null) {
            this.type=Integer.parseInt(request.getParameter("type"));
            if(this.type!=0) {
                session.setAttribute("type", type);
            }
            else {
                session.setAttribute("type", null);
            }
            this.page=0;
            session.setAttribute("page", null);
        }
        else if(request.getParameter("idCategorie") != null) {
            this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
            if(this.idCategorie!=0) {
                session.setAttribute("idCategorie", this.idCategorie);
            }
            else {
                session.setAttribute("idCategorie", null);
            }
            this.idSousCategorie=0;
            session.setAttribute("idSousCategorie", null);
            this.page=0;
            session.setAttribute("page", null);
        }
        else if(request.getParameter("idSousCategorie")!=null) {
            this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
            if(this.idSousCategorie!=0) {
                session.setAttribute("idSousCategorie", this.idSousCategorie);
            }
            else {
                session.setAttribute("idSousCategorie", null);
            }
            this.page=0;
            session.setAttribute("page", null);
        }
        else if(request.getParameter("idRegion")!=null) {
            this.idRegion=request.getParameter("idRegion");
            this.idRegion=Objet.codeHTML(this.idRegion);
            if(!this.idRegion.equals("0")) {
                session.setAttribute("idRegion", this.idRegion);
            }
            else {
                session.setAttribute("idRegion", null);
            }
            this.idDepartement="0";
            session.setAttribute("idDepartement", null);
            this.idCommune=0;
            session.setAttribute("idCommune", null);
            this.page=0;
            session.setAttribute("page", null);
        }
        else if(request.getParameter("idDepartement")!=null) {
            this.idDepartement=request.getParameter("idDepartement");
            this.idDepartement=Objet.codeHTML(this.idDepartement);
            if(!this.idDepartement.equals("0")) {
                session.setAttribute("idDepartement", this.idDepartement);
            }
            else {
                session.setAttribute("idDepartement", null);
            }
            this.idCommune=0;
            session.setAttribute("idCommune", null);
            this.page=0;
            session.setAttribute("page", null);
        }
        else if(request.getParameter("idCommune")!=null) {
            this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
            if(this.idCommune!=0) {
                session.setAttribute("idCommune", this.idCommune);
            }
            else {
                session.setAttribute("idCommune", null);
            }
            this.page=0;
            session.setAttribute("page", null);
        }
        else if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", this.page);
        }
    }

    public void initTags(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            Objet.getConnection();
            this.tagTitle = "Troc et échange avec le bon troc";
            this.tagDescription = "Le bon troc site de trocs et d'échanges gratuits.";
            if (request.getParameter("idSousCategorie") != null && this.idSousCategorie != 0) {
                String query="SELECT t1.categorie,t2.id_categorie,t2.sous_categorie FROM table_categories AS t1,table_sous_categories AS t2 WHERE t1.id=t2.id_categorie AND t2.id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idSousCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                String categorie=result.getString("categorie");
                this.idCategorie=result.getLong("id_categorie");
                String sousCategorie=result.getString("sous_categorie");
                session.setAttribute("idCategorie", this.idCategorie);
                this.tagTitle="Troc - "+categorie+" - "+sousCategorie;
                this.tagDescription="Le bon troc - "+categorie+" - "+sousCategorie+".";
                result.close();
                prepare.close();
            } else if(request.getParameter("idCategorie")!=null&&this.idCategorie!=0) {
                String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                String categorie=result.getString("categorie");
                result.close();
                prepare.close();
                this.tagTitle="Troc - "+categorie;
                this.tagDescription="Le bon troc - "+categorie+".";
            } else if(request.getParameter("idCommune")!=null&&this.idCommune!=0) {
                String query="SELECT id_region,id_departement,code_postal,commune FROM table_communes WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.idCommune);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.idRegion=result.getString("id_region");
                this.idDepartement=result.getString("id_departement");
                String codePostal=result.getString("code_postal");
                String commune=result.getString("commune");
                session.setAttribute("idRegion", this.idRegion);
                session.setAttribute("idDepartement", this.idDepartement);
                result.close();
                prepare.close();
                this.tagTitle="Troc - "+codePostal+" - "+commune;
                this.tagDescription="Le bon troc - "+codePostal+" - "+commune+".";
            } else if(request.getParameter("idDepartement")!=null&&!this.idDepartement.equals("0")) {
                String query="SELECT id_region,departement FROM table_departements WHERE id_departement=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.idDepartement);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.idRegion=result.getString("id_region");
                String departement=result.getString("departement");
                session.setAttribute("idRegion", this.idRegion);
                result.close();
                prepare.close();
                this.tagTitle="Troc - "+this.idDepartement+" - "+departement;
                this.tagDescription="Le bon troc - "+this.idDepartement+" - "+departement+".";
            } else if(request.getParameter("idRegion")!=null&&!this.idRegion.equals("0")) {
                String query="SELECT region FROM table_regions WHERE id_region=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.idRegion);
                ResultSet result=prepare.executeQuery();
                result.next();
                String region=result.getString("region");
                result.close();
                prepare.close();
                this.tagTitle="Troc - Région "+region;
                this.tagDescription="Le bon troc - En région "+region+".";
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCondition() {
        this.condition=" WHERE t1.etat='1'";
        if(this.motsCles.length()>0) {
            String motsCles2=this.motsCles;
            for(String article:Datas.arrayArticles) {
                motsCles2=motsCles2.replaceAll(article, " ");
            }
            String array[]=motsCles2.split(" ");
            for(String mot:array) {
                switch(this.type) {
                    case 0:
                        this.condition+=" AND (t1.titre LIKE '%"+mot+"%' OR t1.titre_contre LIKE '%"+mot+"%' OR t1.contenu LIKE '%"+mot+"%' OR t1.contenu_contre LIKE '%"+mot+"%')";
                        break;
                    case 1:
                        this.condition+=" AND (t1.titre LIKE '%"+mot+"%' OR t1.contenu LIKE '%"+mot+"%')";
                        break;
                    case 2:
                        this.condition+=" AND (t1.titre_contre LIKE '%"+mot+"%' OR t1.contenu_contre LIKE '%"+mot+"%')";
                        break;
                }
            }
        }
        if(this.idSousCategorie!=0) {
            switch(this.type) {
                case 0:
                    this.condition+=" AND (t1.id_sous_categorie='"+this.idSousCategorie+"' OR t1.id_sous_categorie_contre='"+this.idSousCategorie+"')";
                    break;
                case 1:
                    this.condition+=" AND t1.id_sous_categorie='"+this.idSousCategorie+"'";
                    break;
                case 2:
                    this.condition+=" AND t1.id_sous_categorie_contre='"+this.idSousCategorie+"'";
                    break;
            }
        }
        else if(this.idCategorie != 0) {
            switch(this.type) {
                case 0:
                    this.condition+=" AND (t1.id_categorie='"+this.idCategorie+"' OR t1.id_categorie_contre='"+this.idCategorie+"')";
                    break;
                case 1:
                    this.condition+=" AND t1.id_categorie='"+this.idCategorie+"'";
                    break;
                case 2:
                    this.condition+=" AND t1.id_categorie_contre='"+this.idCategorie+"'";
                    break;
            }
        }
        if(this.idCommune!=0) {
            this.condition+=" AND t2.id_commune='"+this.idCommune+"'";
        }
        else if(!this.idDepartement.equals("0")) {
            this.condition+=" AND t2.id_departement='"+this.idDepartement+"'";
        }
        else if(!this.idRegion.equals("0")) {
            this.condition+=" AND t2.id_region='"+this.idRegion+"'";
        }
    }

    public void initListeAnnonces() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(t1.id) AS nbAnnonces FROM table_annonces AS t1,table_membres AS t2"+this.condition+" AND t2.id=t1.id_membre";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbAnnonces=result.getInt("nbannonces");
            result.close();
            this.nbPages=(int)(Math.ceil((double)this.getNbAnnonces()/(double)Datas.NBANNONCESPAGE));
            this.nbAnnoncesPage=0;
            if(this.nbAnnonces<=Datas.NBANNONCESPAGE) {
                this.nbAnnoncesPage=nbAnnonces;
            }
            else if(this.page<(this.nbPages-1)) {
                this.nbAnnoncesPage=Datas.NBANNONCESPAGE;
            }
            else if(this.page==(this.nbPages-1)) {
                this.nbAnnoncesPage=nbAnnonces-((this.nbPages-1)*Datas.NBANNONCESPAGE);
            }
            if(this.nbAnnoncesPage>0) {
                this.nbAnnoncesPage2=(int)(Math.ceil((double)this.nbAnnoncesPage/(double)2));
                this.prem=this.page-5;
                if(this.prem<0) {
                    this.prem=0;
                }
                this.der=this.page+5;
                if(this.der>(this.nbPages-1)) {
                    this.der=nbPages-1;
                }
                this.urls=new String[this.nbAnnoncesPage];
                this.titres=new String[this.nbAnnoncesPage];
                this.codesImg=new String[this.nbAnnoncesPage];
                this.lignes1=new String[this.nbAnnoncesPage];
                this.lignes2=new String[this.nbAnnoncesPage];
                this.lignes3=new String[this.nbAnnoncesPage];
                query="SELECT t1.id,t1.titre,t1.titre_contre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces AS t1,table_membres AS t2,table_categories AS t3,table_sous_categories AS t4,table_regions AS t5,table_departements AS t6,table_communes AS t7"+this.condition+" AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                result=state.executeQuery(query);
                int i=0;
                Calendar cal=Calendar.getInstance();
                Img img=new Img();
                File fileMini1;
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
                    String categorie=result.getString("categorie");
                    String sousCategorie=result.getString("sous_categorie");
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
                                try {
                                    nbPhotos++;
                                    img.getSize(fileMini1);
                                    int largeur=img.getWidth();
                                    int hauteur=img.getHeight();
                                    this.codesImg[i]="<img src=\"./photo-mini-1-"+idAnnonce+"-"+j+""+ext+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniature\"/>";
                                } catch (IOException ex) {
                                    Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    if(nbPhotos==0)
                        this.codesImg[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                    this.urls[i]="./annonce-"+idAnnonce+"-troque-"+Objet.encodeTitre(titre)+"-contre-"+Objet.encodeTitre(titreContre)+".html";
                    this.titres[i]="Troque "+titre+" contre "+titreContre;
                    this.lignes1[i]="Annonce déposée par "+pseudo+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    this.lignes2[i]=categorie+"&rarr;"+sousCategorie+".";
                    this.lignes3[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                    i++;
                }
            }
            state.close();
            result.close();
        } catch (NamingException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void initListeSousCategories(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.nbSousCategories=0;
        if(this.idCategorie!=0) {
            try {
                this.con=Objet.getConnection2();
                String query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.nbSousCategories=result.getInt("nb");
                result.close();
                prepare.close();
                if(this.nbSousCategories>0) {
                this.idSousCategories=new long[this.nbSousCategories];
                this.sousCategories=new String[this.nbSousCategories];
                query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                    this.idSousCategories[i]=result.getLong("id");
                    this.sousCategories[i]=result.getString("sous_categorie");
                    i++;
                }
                result.close();
                prepare.close();
                }
            } catch (NamingException ex) {
                Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                this.idSousCategorie=0;
            } catch (SQLException ex) {
                Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                this.idSousCategorie=0;
                session.setAttribute("idSousCategorie", null);
            } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                    this.idSousCategorie=0;
                    session.setAttribute("idSousCategorie", null);
                }
            }
        }
    }

    public void initLocal(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.nbDepartements=0;
        this.nbCommunes=0;
        if(!this.idRegion.equals("0")) {
            try {
                this.con=Objet.getConnection2();
                String query="SELECT COUNT(id) AS nbDepartements FROM table_departements WHERE id_region=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setString(1, this.idRegion);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.nbDepartements=result.getInt("nbDepartements");
                result.close();
                prepare.close();
                if(this.nbDepartements>0) {
                    this.idsDepartements=new String[this.nbDepartements];
                    this.departements=new String[this.nbDepartements];
                    query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement ASC LIMIT 0,"+this.nbDepartements;
                    prepare=this.con.prepareStatement(query);
                    prepare.setString(1, this.idRegion);
                    result=prepare.executeQuery();
                    int i=0;
                    while(result.next()) {
                        String idDep=result.getString("id_departement");
                        String departement=result.getString("departement");
                        this.idsDepartements[i]=idDep;
                        this.departements[i]=idDep+"-"+departement;
                        i++;
                    }
                    result.close();
                    prepare.close();
                    if(!this.idDepartement.equals("0")) {
                        query="SELECT COUNT(id) AS nbCommunes FROM table_communes WHERE id_departement=?";
                        prepare=this.con.prepareStatement(query);
                        prepare.setString(1, this.idDepartement);
                        result=prepare.executeQuery();
                        result.next();
                        this.nbCommunes=result.getInt("nbCommunes");
                        result.close();
                        prepare.close();
                        if(this.nbCommunes>0) {
                            this.idsCommunes=new int[this.nbCommunes];
                            this.communes=new String[this.nbCommunes];
                            query="SELECT id,commune,code_postal FROM table_communes WHERE id_region=? AND id_departement=? ORDER BY commune LIMIT 0,"+this.nbCommunes;
                            prepare=this.con.prepareStatement(query);
                            prepare.setString(1, this.idRegion);
                            prepare.setString(2, this.idDepartement);
                            result=prepare.executeQuery();
                            i=0;
                            while(result.next()) {
                                int idCom=result.getInt("id");
                                String codePostal=result.getString("code_postal");
                                String commune=result.getString("commune");
                                this.idsCommunes[i]=idCom;
                                this.communes[i]=codePostal+"-"+commune;
                                i++;
                            }
                            result.close();
                            prepare.close();
                        }
                    }
                }
            } catch (NamingException ex) {
                Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                this.idDepartement="0";
                session.setAttribute("idDepartement", null);
                this.idCommune=0;
                session.setAttribute("idCommune", null);
            } catch (SQLException ex) {
                Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                this.idDepartement="0";
                session.setAttribute("idDepartement", null);
                this.idCommune=0;
                session.setAttribute("idCommune", null);
                this.setErrorMsg(ex.getMessage());
            } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                    this.idDepartement="0";
                    this.idCommune=0;
                    session.setAttribute("idDepartement", null);
                    session.setAttribute("idCommune", null);
                }
            }
        }
    }

    /**
     * @return the motsCles
     */
    public String getMotsCles() {
        return motsCles;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
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
     * @return the idRegion
     */
    public String getIdRegion() {
        return idRegion;
    }

    /**
     * @return the idDepartement
     */
    public String getIdDepartement() {
        return idDepartement;
    }

    /**
     * @return the idCommune
     */
    public int getIdCommune() {
        return idCommune;
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
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
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
     * @return the nbAnnoncesPage
     */
    public int getNbAnnoncesPage() {
        return nbAnnoncesPage;
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
     * @return the nbSousCategories
     */
    public int getNbSousCategories() {
        return nbSousCategories;
    }

    /**
     * @return the idSousCategories
     */
    public long[] getIdSousCategories() {
        return idSousCategories;
    }

    /**
     * @return the sousCategories
     */
    public String[] getSousCategories() {
        return sousCategories;
    }

    /**
     * @return the nbDepartements
     */
    public int getNbDepartements() {
        return nbDepartements;
    }

    /**
     * @return the idsDepartements
     */
    public String[] getIdsDepartements() {
        return idsDepartements;
    }

    /**
     * @return the departements
     */
    public String[] getDepartements() {
        return departements;
    }

    /**
     * @return the nbCommunes
     */
    public int getNbCommunes() {
        return nbCommunes;
    }

    /**
     * @return the idsCommunes
     */
    public int[] getIdsCommunes() {
        return idsCommunes;
    }

    /**
     * @return the communes
     */
    public String[] getCommunes() {
        return communes;
    }

    /**
     * @return the nbAnnoncesPage2
     */
    public int getNbAnnoncesPage2() {
        return nbAnnoncesPage2;
    }

}
