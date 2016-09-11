<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc - Deposer une annonce - Identification</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Troc et echange avec le bon troc - Deposer vos annonce de troc d'echange - consultez les annonces de troc, d'echange." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./scripts/scripts.js"></script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
</head>
    <body>
        <%@include file="./connexion.jsp" %>
        <%@include file="./haut.jsp" %>
        <section>
           <br/>
<div>
<a name="fb_share" type="button_count" share_url="<%= Datas.URLROOT %>">Cliquez pour partager !!!</a><script src="http://static.ak.fbcdn.net/connect.php/js/FB.Share" type="text/javascript"></script>
</div>
           <br/>
           <g:plusone></g:plusone>
           <br/>
           <h1>Le bon troc - Deposer une annonce - Identification</h1>
           <%
           try {
               if(request.getAttribute("membre")!=null) {
                   Membre membre=(Membre)request.getAttribute("membre");
                   if(membre.getTest()==0) {
                   if(membre.getId()!=0) { %>
                   <br/>
                   <div class="cadre2">
                       <div>
                           <span>Statut &rarr; connecté &rarr; <%= membre.getPseudo2() %></span>
                           <a href="./deposer-annonce-2.html" title="C'EST MOI !" rel="nofollow">C'EST MOI !</a>
                       </div>
                   </div>
                           <br/>
                           <%
                           }
                   %>
                   <div id="form1">
                       <%
                       if(request.getParameter("kermit1")!=null&&membre.getErrorMsg().length()>0) { %>
                       <div class="erreur">
                           <div>Erreur(s) :</div>
                           <br/>
                           <div><%= membre.getErrorMsg() %></div>
                       </div>
                       <br/>
                       <% } %>
                       <fieldset>
                           <legend>Je possède un compte : Je me connecte</legend>
                           <form action="./deposer-annonce-1.html#form1" method="POST">
                               <div>Mon PSEUDONYME :</div>
                               <input type="text" name="pseudo" value="<%= membre.getPseudo()%>" size="15" maxlength="20" />
                               <div>Mon MOT DE PASSE :</div>
                               <input type="password" name="motDePasse" value="" size="15" maxlength="15" />
                               <br/>
                               <input type="submit" value="Valider" name="kermit1" />
                           </form>
                               <div><a href="./mdp-oublie.html" title="MOT DE PASSE OUBLIÉ" rel="nofollow">MOT DE PASSE OUBLIÉ</a></div>
                       </fieldset>
                   </div>
                               <br/>
                               <div id="form2">
                                   <%
                                   if(request.getParameter("kermit2")!=null&&membre.getErrorMsg().length()>0) { %>
                                   <div class="erreur">
                                       <div>Erreur(s) :</div>
                                       <br/>
                                       <div><%= membre.getErrorMsg() %></div>
                                   </div>
                                   <br/>
                                   <% } %>
                                   <fieldset>
                                       <legend>Je n'ai pas de compte : Je m'inscris</legend>
                                       <form action="./deposer-annonce-1.html#form2" method="POST">
                                           <div>Mon PSEUDONYME [Caractères alphanumériques]:</div>
                                           <input type="text" name="pseudo" value="<%= membre.getPseudo()%>" size="15" maxlength="20" />
                                           <div>Mon ADRESSE EMAIL :</div>
                                           <input type="text" name="email" value="<%= membre.getEmail()%>" size="40" maxlength="200" />
                                           <div>Mon MOT DE PASSE [Caractères alphanumériques]:</div>
                                           <input type="password" name="motDePasse" value="" size="15" maxlength="15" />
                                           <div>Confirmation de mon MOT DE PASSE :</div>
                                           <input type="password" name="motDePasse2" value="" size="15" maxlength="15" />
                                           <div>Votre localisation :</div>
                                           <div>
                                               <span>Votre région :</span>
                                               <select name="idRegion" onchange="javascript:changeRegion(this.value);">
                                                   <option value="0"<% if(membre.getIdRegion().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                   <%
                                                   Objet.getConnection();
                                                   String query="SELECT id_region,region FROM table_regions ORDER BY region ASC";
                                                   Statement state=Objet.getConn().createStatement();
                                                   ResultSet result=state.executeQuery(query);
                                                   while(result.next()) {
                                                       String idRegion=result.getString("id_region");
                                                       String region=result.getString("region");
                                                       %>
                                                       <option value="<%= idRegion %>"<% if(membre.getIdRegion().equals(idRegion)) out.print(" selected=\"selected\""); %>><%= region %></option>
                                                       <%
                                                       }
                                                   result.close();
                                                   state.close();
                                                   %>
                                               </select>
                                           </div>
                                               <div id="innerDepartements">
                                                   <span>Votre département :</span>
                                                   <select name="idDepartement" onchange="javascript:changeDepartement(this.value);">
                                                       <option value="0"<% if(membre.getIdDepartement().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                       <%
                                                       if(!membre.getIdRegion().equals("0")) {
                                                           query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement";
                                                           PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                                           prepare.setString(1, membre.getIdRegion());
                                                           result=prepare.executeQuery();
                                                           while(result.next()) {
                                                               String idDepartement=result.getString("id_departement");
                                                               String departement=result.getString("departement");
                                                               %>
                                                               <option value="<%= idDepartement %>"<% if(membre.getIdDepartement().equals(idDepartement)) out.print(" selected=\"selected\""); %>><%= idDepartement %>-<%= departement %></option>
                                                               <%
                                                               }
                                                           result.close();
                                                           prepare.close();
                                                           }
                                                   %>
                                                   </select>
                                                   <div id="innerCommunes">
                                                       <span>Votre commune :</span>
                                                       <select name="idCommune">
                                                           <option value="0"<% if(membre.getIdCommune()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                           <%
                                                           if(!membre.getIdRegion().equals("0")&&!membre.getIdDepartement().equals("0")) {
                                                               query="SELECT id,code_postal,commune FROM table_communes WHERE id_region=? AND id_departement=? ORDER BY commune ASC";
                                                               PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                                               prepare.setString(1, membre.getIdRegion());
                                                               prepare.setString(2, membre.getIdDepartement());
                                                               result=prepare.executeQuery();
                                                               while(result.next()) {
                                                                   int idCommune=result.getInt("id");
                                                                   String codePostal=result.getString("code_postal");
                                                                   String commune=result.getString("commune");
                                                                   %>
                                                                   <option value="<%= idCommune %>"<% if(membre.getIdCommune()==idCommune) out.print(" selected=\"selected\""); %>><%= codePostal %>-<%= commune %></option>
                                                                   <%
                                                                   }
                                                               result.close();
                                                               prepare.close();
                                                               }
                                                   %>
                                                       </select>
                                                   </div>
                                               </div>
                                                       <br/>
                                                       <div class="captcha"></div>
                                                       <div class="captchaDroite">
                                                           <span>&rarr;Copiez le code SVP&rarr;</span>
                                                           <input type="text" name="captcha" value="" size="5" maxlength="5" />
                                                       </div>
                                                       <br/>
                                                       <br/>
                                                       <input type="submit" value="Valider" name="kermit2" />
                                                       <br/>
                                       </form>
                                   </fieldset>
                               </div>
                               <%
                               Objet.closeConnection();
                               } else if(membre.getTest()==1) {
                                   membre.blank();
                                   %>
                                   <script type="text/javascript">
                                       window.location.href="./deposer-annonce-2.html";
                                   </script>
                                   <%
                                   }
                   }
               } catch(Exception ex) { %>
               <div class="erreur">
                   <div>Erreur :</div>
                   <br/>
                   <div><%= ex.getMessage() %></div>
               </div>
               <% } %>
        </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
