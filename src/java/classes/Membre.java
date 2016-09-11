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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Membre extends Objet {

    private String pseudo;
    private String email;
    private String motDePasse;
    private String motDePasse2;
    private String idRegion;
    private String idDepartement;
    private int idCommune;
    private String captcha;
    private long id;
    private String pseudo2;
    private long timestamp;
    private boolean flagMdp;

    public Membre() {
        super();
        this.pseudo="";
        this.email="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
    }

    public Membre(long idMembre) {
        super();
        this.id=idMembre;
        this.pseudo="";
        this.email="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
    }

    public void getPosts1(HttpServletRequest request) {
        this.pseudo=request.getParameter("pseudo");
        this.pseudo=Objet.codeHTML(this.pseudo);
        this.email=request.getParameter("email").toLowerCase();
        this.email=Objet.codeHTML(this.email);
        this.motDePasse=request.getParameter("motDePasse");
        this.motDePasse=Objet.codeHTML(this.motDePasse);
        this.motDePasse2=request.getParameter("motDePasse2");
        this.motDePasse2=Objet.codeHTML(this.motDePasse2);
        this.idRegion=request.getParameter("idRegion");
        this.idRegion=Objet.codeHTML(this.idRegion);
        this.idDepartement=request.getParameter("idDepartement");
        this.idDepartement=Objet.codeHTML(this.idDepartement);
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
        this.captcha=request.getParameter("captcha").toLowerCase();
        this.captcha=Objet.codeHTML(this.captcha);
    }
    public void verifPosts1(HttpServletRequest request) {
        try {
            Objet.getConnection();
            HttpSession session = request.getSession(true);
            Pattern p = Pattern.compile("[a-zA-Z0-9]+");
            Matcher m = p.matcher(this.pseudo);
            if (this.pseudo.length() == 0) {
                this.setErrorMsg("Champ PSEUDONYME vide.<br/>");
            } else if (this.pseudo.length() < 2) {
                this.setErrorMsg("Champ PSEUDONYME trop court.<br/>");
            } else if (this.pseudo.length() > 20) {
                this.setErrorMsg("Champ PSEUDONYME trop long.<br/>");
            } else if (m.matches() == false) {
                this.setErrorMsg("Champ PSEUDONYME non-valide (Caractères alphanumériques seulement).<br/>");
            } else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb!=0)
                    this.setErrorMsg("Désolé, ce PSEUDONYME est déjà pris.<br/>");
            }
            p=Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]+\\.[a-z]{2,4}$");
            m=p.matcher(this.email);
            if(this.email.length()==0)
                this.setErrorMsg("Champ ADRESSE EMAIL vide.<br/>");
            else if(this.email.length()>200)
                this.setErrorMsg("Champ EMAIL trop long.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ EMAIL non-valide.<br/>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE email=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb!=0)
                    this.setErrorMsg("Désolé, cette ADRESSE EMAIL est déjà associée à un compte.<br/>");
                result.close();
                prepare.close();
            }
            p=Pattern.compile("[a-zA-Z0-9]+");
            m=p.matcher(this.motDePasse);
            Matcher m2=p.matcher(this.motDePasse2);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("Champ MOT DE PASSE vide.<br/>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("Champ MOT DE PASSE trop court.<br/>");
            else if(this.motDePasse.length()>15)
                this.setErrorMsg("Champ MOT DE PASSE trop long.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ MOT DE PASSE non-valide(Caratères alphanumériques uniquement).<br/>");
            else if(this.motDePasse2.length() == 0)
                this.setErrorMsg("Champ CONFIRMATION vide.<br/>");
            else if(this.motDePasse2.length()<3)
                this.setErrorMsg("Champ CONFIRMATION trop court.<br/>");
            else if(this.motDePasse2.length()>15)
                this.setErrorMsg("Champ CONFIRMATION trop long.<br/>");
            else if(m2.matches()==false)
                this.setErrorMsg("Champ CONFIRMATION non-valide(Caratères alphanumériques uniquement).<br/>");
            else if(!this.motDePasse.equals(this.motDePasse2))
                this.setErrorMsg("Les 2 MOTS DE PASSE sont différents.<br/>");
            if(this.idRegion.equals("0"))
                this.setErrorMsg("Veuillez choisir votre RÉGION SVP.<br/>");
            if(this.idDepartement.equals("0"))
                this.setErrorMsg("Veuillez choisir votre DÉPARTEMENT SVP.<br/>");
            if(this.idCommune==0)
                this.setErrorMsg("Veuillez choisir votre COMMUNE SVP.<br/>");
            if(this.captcha.length()==0)
                this.setErrorMsg("Champ CODE ANTI-ROBOT vide.<br/>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("Champ CODE ANTI-ROBOT trop long.<br/>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("Session CODE ANTI-ROBOT dépassée.<br/>");
            else if(!session.getAttribute("captcha").toString().equals(Objet.getEncoded(this.captcha)))
                this.setErrorMsg("Mauvais CODE ANTI-ROBOT.<br/>");
            Objet.closeConnection();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        }
    }

    public void enregDatasInsc(HttpServletRequest request, HttpServletResponse response) {
        if(this.getErrorMsg().length()==0) {
            HttpSession session=request.getSession(true);
            try {
                Objet.getConnection();
                String query="INSERT INTO table_membres (pseudo,email,mot_de_passe,id_region,id_departement,id_commune,timestamp) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, this.email);
                prepare.setString(3, Objet.getEncoded(this.motDePasse));
                prepare.setString(4, this.idRegion);
                prepare.setString(5, this.idDepartement);
                prepare.setInt(6, this.idCommune);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(7, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMembre FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setId(result.getLong("idMembre"));
                result.close();
                prepare.close();
                Objet.closeConnection();
                Objet.setCookie(this.getId(), response);
                session.setAttribute("captcha", null);
                Mail mail=new Mail(this.email, this.pseudo, "Vous êtes inscrit(e) !");
                mail.initMailInscription(this.pseudo, this.email, this.motDePasse);
                mail.send();
                session.setAttribute("idMembre", this.id);
                session.setAttribute("pseudo", this.pseudo);
                this.setTest(1);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
            }
        }
    }

    public void testConnecte(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.setId(0);
        if(session.getAttribute("idMembre")!=null&&session.getAttribute("pseudo")!=null) {
            this.setId(Long.parseLong(session.getAttribute("idMembre").toString()));
            this.pseudo2=session.getAttribute("pseudo").toString();
            session.setAttribute("idMembre", this.getId());
            session.setAttribute("pseudo", this.getPseudo2());
        } else {
            Cookie cookies[]=request.getCookies();
            if(cookies!=null) {
            String cookieValue="";
            for(Cookie cookie:cookies) {
                if(cookie.getName().equals("troccook")) {
                    cookieValue=cookie.getValue();
                    break;
                }
            }
            if(cookieValue.length()>0) {
                try {
                    Objet.getConnection();
                    String query="SELECT id,pseudo FROM table_membres WHERE cookie_code=? LIMIT 0,1";
                    PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                    prepare.setString(1, cookieValue);
                    ResultSet result=prepare.executeQuery();
                    if(result.next()) {
                            this.setId(result.getLong("id"));
                        this.pseudo2=result.getString("pseudo");
                        session.setAttribute("idMembre", this.id);
                        session.setAttribute("pseudo", this.pseudo2);
                    }
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                } catch (NamingException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                        this.setId(0);
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                        this.setId(0);
                }
            }
        }
        }
    }
    public void getPostMdpOublie(HttpServletRequest request) {
        this.email=request.getParameter("email");
        this.email=Objet.codeHTML(this.email);
        this.captcha=request.getParameter("captcha");
        this.captcha=Objet.codeHTML(this.captcha);
    }

    public void verifPostMdpOublie(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        try {
            Objet.getConnection();
            Pattern p = Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]+\\.[a-z]{2,4}$");
            Matcher m = p.matcher(this.email);
            if (this.email.length() == 0) {
                this.setErrorMsg("Champ ADRESSE EMAIL vide.<br/>");
            } else if (this.email.length() > 200) {
                this.setErrorMsg("Champ ADRESSE EMAIL trop long.<br/>");
            } else if(m.matches()==false)
                this.setErrorMsg("Champ ADRESSE EMAIL non-valide.<br/>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE email=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb==0)
                    this.setErrorMsg("Désolé cette ADRESSE EMAIL ne figure pas dans notre liste.<br/>");
            }
            if(this.captcha.length()==0)
                this.setErrorMsg("Champ CODE ANTI-ROBOT vide.<br/>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("Champ CODE ANTI-ROBOT vide.<br/>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("Session CODE ANTI-ROBOT dépassée.<br/>");
            else if(!session.getAttribute("captcha").toString().equals(Objet.getEncoded(this.captcha)))
                this.setErrorMsg("Mauvais CODE ANTI-ROBOT.<br/>");
            Objet.closeConnection();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        }
    }

    public void envoieMdpOublie() {
        if(this.getErrorMsg().length()==0) {
            try {
                this.motDePasse = "";
                for (int i = 1; i <= 5; i++) {
                    this.motDePasse += Datas.arrayChars[(int) (((double)Datas.arrayChars.length-1)*Math.random())];
                }
                Objet.getConnection();
                String query="SELECT id,pseudo FROM table_membres WHERE email=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setId(result.getLong("id"));
                this.pseudo=result.getString("pseudo");
                result.close();
                prepare.close();
                query="UPDATE table_membres SET mot_de_passe=? WHERE id=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, Objet.getEncoded(this.motDePasse));
                prepare.setLong(2, this.id);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                Mail mail=new Mail(this.email, this.pseudo, "Nouveau mot de passe !");
                mail.initMailNouveauMdp(this.pseudo, this.email, this.motDePasse);
                mail.send();
                this.setTest(1);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
            }
        }
    }

    public void getPostsCnx(HttpServletRequest request) {
        this.pseudo=request.getParameter("pseudo");
        this.pseudo=Objet.codeHTML(this.pseudo);
        this.motDePasse=request.getParameter("motDePasse");
        this.motDePasse=Objet.codeHTML(this.motDePasse);
    }

    public void verifPostsCnx(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session=request.getSession(true);
        try {
            Objet.getConnection();
            Pattern p = Pattern.compile("[a-zA-Z0-9]+");
            Matcher m = p.matcher(this.pseudo);
            if (this.pseudo.length() == 0) {
                this.setErrorMsg("Champ PSEUDO vide.<br/>");
            } else if (this.pseudo.length() < 2) {
                this.setErrorMsg("Champ PSEUDO trop court.<br/>");
            } else if (this.pseudo.length() > 20) {
                this.setErrorMsg("Champ PSEUDO trop long.<br/>");
            } else if (m.matches() == false) {
                this.setErrorMsg("Champ PSEUDO non-valide.<br/>");
            } else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("PSEUDO inconnu.<br/>");
                result.close();
                prepare.close();
            }
            m=p.matcher(this.motDePasse);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("Champ MOT DE PASSE vide.<br/>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("Champ MOT DE PASSE trop court.<br/>");
            else if(this.motDePasse.length()>15)
                this.setErrorMsg("Champ MOT DE PASSE trop long.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ MOT DE PASSE non-valide.<br/>");
            else if(this.getErrorMsg().length()==0) {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("Mauvais MOT DE PASSE.<br/>");
                result.close();
                prepare.close();
            }
            if(this.getErrorMsg().length()==0) {
                String query="SELECT id FROM table_membres WHERE pseudo=? AND mot_de_passe=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setId(result.getLong("id"));
                result.close();
                prepare.close();
                session.setAttribute("idMembre", this.id);
                session.setAttribute("pseudo", this.pseudo);
                this.pseudo2=this.pseudo;
                Objet.setCookie(this.id, response);
            }
            Objet.closeConnection();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage());
        }
    }

    public void initInfos(long idMembre) {
        this.setId(idMembre);
        try {
            Objet.getConnection();
            String query="SELECT pseudo,email,id_region,id_departement,id_commune,timestamp FROM table_membres WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.pseudo=result.getString("pseudo");
            this.email=result.getString("email");
            this.idRegion=result.getString("id_region");
            this.idDepartement=result.getString("id_departement");
            this.idCommune=result.getInt("id_commune");
            this.timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }
    public void initInfos2(long idMembre) throws NamingException, SQLException {
        this.setId(idMembre);
            String query="SELECT pseudo,email,id_region,id_departement,id_commune,timestamp FROM table_membres WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.pseudo=result.getString("pseudo");
            this.email=result.getString("email");
            this.idRegion=result.getString("id_region");
            this.idDepartement=result.getString("id_departement");
            this.idCommune=result.getInt("id_commune");
            this.timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
    }

    public void getPostsInfosPersos(HttpServletRequest request) {
        this.motDePasse=request.getParameter("motDePasse");
        this.motDePasse=Objet.codeHTML(this.motDePasse);
        this.motDePasse2=request.getParameter("motDePasse2");
        this.motDePasse2=Objet.codeHTML(this.motDePasse2);
        this.idRegion=request.getParameter("idRegion");
        this.idRegion=Objet.codeHTML(this.idRegion);
        this.idDepartement=request.getParameter("idDepartement");
        this.idDepartement=Objet.codeHTML(this.idDepartement);
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
        this.captcha=request.getParameter("captcha").toLowerCase();
        this.captcha=Objet.codeHTML(this.captcha);
    }

    public void verifPostsInfosPersos(HttpServletRequest request) {
        try {
            this.flagMdp=false;
            HttpSession session = request.getSession(true);
            if (this.motDePasse.length() != 0) {
                this.flagMdp=true;
                Pattern p = Pattern.compile("[0-9a-zA-Z]+");
                Matcher m = p.matcher(this.motDePasse);
                Matcher m2 = p.matcher(this.motDePasse2);
                if (this.motDePasse.length() < 3) {
                    this.setErrorMsg("Champ NOUVEAU MOT DE PASSE trop court.<br/>");
                } else if (this.motDePasse.length() > 15) {
                    this.setErrorMsg("Champ NOUVEAU MOT DE PASSE trop long.<br/>");
                } else if (m.matches() == false) {
                    this.setErrorMsg("Champ NOUVEAU MOT DE PASSE invalide (Caractères alphanumériques uniquement).<br/>");
                } else if (this.motDePasse2.length() < 3) {
                    this.setErrorMsg("Champ CONFIRMATION trop court.<br/>");
                } else if (this.motDePasse2.length() > 15) {
                    this.setErrorMsg("Champ CONFIRMATION trop long.<br/>");
                } else if (m2.matches() == false) {
                    this.setErrorMsg("Champ CONFIRMATION invalide (Caractères alphanumériques uniquement).<br/>");
                }
            }
            if (this.idRegion.equals("0")) {
                this.setErrorMsg("Veuillez choisir votre RÉGION SVP.<br/>");
            }
            if (this.idDepartement.equals("0")) {
                this.setErrorMsg("Veuillez choisisr votre DÉPARTEMENT SVP.<br/>");
            }
            if (this.idCommune == 0) {
                this.setErrorMsg("Veuillez choisir votre COMMUNE SVP.<br/>");
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
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

    public void updateInfosPersos(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="";
                PreparedStatement prepare;
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                if(this.flagMdp) {
                    query="UPDATE table_membres SET mot_de_passe=?,id_region=?,id_departement=?,id_commune=?,timestamp=? WHERE id=?";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setString(1, Objet.getEncoded(this.motDePasse));
                    prepare.setString(2, this.idRegion);
                    prepare.setString(3, this.idDepartement);
                    prepare.setInt(4, this.idCommune);
                    prepare.setLong(5, ts);
                    prepare.setLong(6, this.id);
                    prepare.executeUpdate();
                    prepare.close();
                } else if(!this.flagMdp) {
                    query="UPDATE table_membres SET id_region=?,id_departement=?,id_commune=?,timestamp=? WHERE id=?";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setString(1, this.idRegion);
                    prepare.setString(2, this.idDepartement);
                    prepare.setInt(3, this.idCommune);
                    prepare.setLong(4, ts);
                    prepare.setLong(5, this.id);
                    prepare.executeUpdate();
                    prepare.close();
                }
                Objet.closeConnection();
                if(this.flagMdp) {
                    Mail mail=new Mail(this.email, this.pseudo, "Nouveau mot de passe !");
                    mail.initMailNouveauMdp2(this.pseudo, this.email, this.motDePasse);
                    mail.send();
                }
                this.setTest(1);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            }
        }
    }

    public void verifPostsCnx2(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session=request.getSession(true);
        try {
            Objet.getConnection();
            Pattern p=Pattern.compile("[a-zA-Z0-9]+");
            Matcher m=p.matcher(this.pseudo);
            if(this.pseudo.length()==0)
                this.setErrorMsg("Champ PSEUDONYME vide.<br/>");
            else if(this.pseudo.length()<2)
                this.setErrorMsg("Champ PSEUDONYME trop court.<br/>");
            else if(this.pseudo.length()>20)
                this.setErrorMsg("Champ PSEUDONYME trop long.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ PSEUDONYME non-valide (Caractères alphanumériques uniquement).<br/>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("Désolé, ce PSEUDONYME nous est inconnu.<br/>");
                result.close();
                prepare.close();
            }
            m=p.matcher(this.motDePasse);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("Champ MOT DE PASSE vide.<br/>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("Champ MOT DE PASSE trop court.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ MOT DE PASSE non-valide (Caractères alphanumériques uniquement).<br/>");
            else if(this.getErrorMsg().length()==0) {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("Mauvais MOT DE PASSE.<br/>");
                result.close();
                prepare.close();
            }
            if(this.getErrorMsg().length()==0) {
                String query="SELECT id,pseudo FROM table_membres WHERE pseudo=? AND mot_de_passe=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setId(result.getLong("id"));
                this.pseudo2=result.getString("pseudo");
                result.close();
                prepare.close();
                Objet.setCookie(id, response);
                session.setAttribute("idMembre", this.id);
                session.setAttribute("pseudo", this.pseudo2);
                this.setTest(1);
            }
            Objet.closeConnection();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

    public void enregDatasInsc2(HttpServletRequest request, HttpServletResponse response) {
        if(this.getErrorMsg().length()==0) {
            HttpSession session=request.getSession(true);
            try {
                Objet.getConnection();
                String query="INSERT INTO table_membres (pseudo,email,mot_de_passe,id_region,id_departement,id_commune,timestamp) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, this.email);
                prepare.setString(3, Objet.getEncoded(this.motDePasse));
                prepare.setString(4, this.idRegion);
                prepare.setString(5, this.idDepartement);
                prepare.setInt(6, this.idCommune);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(7, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMembre FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setId(result.getLong("idMembre"));
                session.setAttribute("idMembre", this.id);
                session.setAttribute("pseudo", this.pseudo);
                Objet.setCookie(this.id, response);
                Mail mail=new Mail(this.email, this.pseudo, "Inscription !");
                mail.initMailInscription(this.pseudo, this.email, this.motDePasse);
                mail.send();
                this.setTest(1);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            }
        }
    }

    public void supprimeCompte(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(true);
            Objet.getConnection();
            String query="SELECT id FROM table_annonces WHERE id_membre=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                Annonce annonce=new Annonce(idAnnonce);
                annonce.effaceAnnonce2(idAnnonce, this.id);
            }
            result.close();
            prepare.close();
            query="DELETE FROM table_membres WHERE id=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            this.setTest(1);
            session.setAttribute("idMembre", null);
            session.setAttribute("pseudo", null);
            Cookie cookie=new Cookie("troccook", "");
            response.addCookie(cookie);
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void blank() {
        super.blank();
        this.pseudo="";
        this.email="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
    }
    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
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
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the pseudo2
     */
    public String getPseudo2() {
        return pseudo2;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}
