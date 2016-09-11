/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author pj
 */
public class AnnoncePhotos extends Objet {
    private Membre membre;
    private long id;
    private String titre;
    private String titreContre;
    private String[] extensions;

    public AnnoncePhotos(Membre membre) {
        super();
        this.membre=membre;
    }

    public void initInfos(long idAnnonce) {
        this.id=idAnnonce;
        try {
            Objet.getConnection();
            String query="SELECT titre,titre_contre,extension1,extension2,extension3,extension4,extension5 FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.titre=result.getString("titre");
            this.titreContre=result.getString("titre_contre");
            this.extensions=new String[5];
            this.extensions[0]=result.getString("extension1");
            this.extensions[1]=result.getString("extension2");
            this.extensions[2]=result.getString("extension3");
            this.extensions[3]=result.getString("extension4");
            this.extensions[4]=result.getString("extension5");
            result.close();
            prepare.close();
            this.titre+=" contre "+this.titreContre;
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        }
    }

    public void getPosts(HttpServletRequest request) {
        this.setTest(1);
        try {
            Objet.getConnection();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(Datas.MAXUPLOADSIZE);
            factory.setRepository(new File("home/temp"));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(Datas.MAXUPLOADSIZE);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while(iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if(!item.isFormField()) {
                String name=item.getFieldName();
                if((name.equals("1")||name.equals("2")||name.equals("3")||name.equals("4")||name.equals("5"))&&item.getSize()>0) {
                    String query="SELECT extension"+name+" FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
                    PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.id);
                    prepare.setLong(2, this.membre.getId());
                    ResultSet result=prepare.executeQuery();
                    result.next();
                    String extension=result.getString("extension"+name);
                    String filename=Datas.DIR+"photos/"+this.id+"_"+name+extension;
                    String filenameMini1=Datas.DIR+"photos/mini1_"+this.id+"_"+name+extension;
                    String filenameMini2=Datas.DIR+"photos/mini2_"+this.id+"_"+name+extension;
                    File file=new File(filename);
                    File fileMini1=new File(filenameMini1);
                    File fileMini2=new File(filenameMini2);
                    if(file.exists())
                        file.delete();
                    if(fileMini1.exists())
                        fileMini1.delete();
                    if(fileMini2.exists())
                        fileMini2.delete();
                    extension=(item.getName().substring(item.getName().lastIndexOf("."))).toLowerCase();
                    if(!Objet.testExtension(extension))
                        this.setErrorMsg("Mauvaise EXTENSION DE FICHIER (uniquement PNG, GIF, JPG OU JPEG).<br/>");
                    if(this.getErrorMsg().length()==0) {
                        filename=Datas.DIR+"photos/"+this.id+"_"+name+extension;
                        filenameMini1=Datas.DIR+"photos/mini1_"+this.id+"_"+name+extension;
                        filenameMini2=Datas.DIR+"photos/mini2_"+this.id+"_"+name+extension;
                        file=new File(filename);
                        fileMini1=new File(filenameMini1);
                        fileMini2=new File(filenameMini2);
                        item.write(file);
                        Img img=new Img();
                        img.resizeWidth(file, fileMini1, Datas.MINI1LARG);
                        img.resizeHeight(file, fileMini2, Datas.MINI2HAUT);
                        img.getSize(file);
                        if(img.getWidth()>Datas.MAXLARGPHOTO)
                            img.resizeWidth(file, file, Datas.MAXLARGPHOTO);
                        img.getSize(file);
                        if(img.getHeight()>Datas.MAXHAUTPHOTO)
                            img.resizeHeight(file, file, Datas.MAXHAUTPHOTO);
                        if(file.exists()&&fileMini1.exists()&&fileMini2.exists()) {
                            int index=Integer.parseInt(name);
                            this.extensions[index-1]=extension;
                            query="UPDATE table_annonces SET extension"+name+"=? WHERE id=? AND id_membre=?";
                            prepare=Objet.getConn().prepareStatement(query);
                            prepare.setString(1, extension);
                            prepare.setLong(2, this.id);
                            prepare.setLong(3, this.membre.getId());
                            prepare.executeUpdate();
                            prepare.close();
                        }
                    }
                }
            }
            }
            Objet.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(AnnoncePhotos.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the extensions
     */
    public String[] getExtensions() {
        return extensions;
    }

}
