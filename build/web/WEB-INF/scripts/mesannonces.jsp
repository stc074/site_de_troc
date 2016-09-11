<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc - Mes annonces</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Troc et echange avec le bon troc - Deposer vos annonce de troc d'echange - consultez les annonces de troc, d'echange." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
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
           <h1>Le bon troc - Mes annonces</h1>
           <%
           try {
               if(request.getParameter("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <br/>
                       <div class="cadre2">
                       <div class="info">Vous devez être connecté pour pouvoir consulter vos annonces.</div>
                       <br/>
                       <div>
                           <a href="./inscription.html" title="S'INSCRIRE">S'INSCRIRE</a>
                       </div>
                       <br/>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("idMembre")!=null) {
                       long idMembre=Long.parseLong(request.getAttribute("idMembre").toString());
                       Objet.getConnection();
                       String query="SELECT id,titre,titre_contre,timestamp FROM table_annonces WHERE id_membre=? ORDER BY timestamp DESC";
                       PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                       prepare.setLong(1, idMembre);
                       ResultSet result=prepare.executeQuery();
                       int nbAnnonces=0;
                       Calendar cal=Calendar.getInstance();
                       while(result.next()) {
                           nbAnnonces++;
                           long idAnnonce=result.getLong("id");
                           String titre=result.getString("titre");
                           String titreContre=result.getString("titre_contre");
                           long timestamp=result.getLong("timestamp");
                           cal.setTimeInMillis(timestamp);
                           titre+=" contre "+titreContre;
                           %>
                           <div class="cadre2">
                               <div>
                                   <a href="./edit-annonce-<%= idAnnonce %>.html" title="ÉDITER CETTE ANNONCE" rel="nofollow"><%= titre %></a>
                                   <span>&nbsp;&rarr;Mise en ligne le, <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %></span>
                                   <span>&nbsp;&rArr;<a href="./efface-annonce-<%= idAnnonce %>.html" title="ÉFFACER CETTE ANNONCE" rel="nofollow">ÉFFACER</a></span>
                               </div>
                           </div>
                               <br/>
                               <%
                               }
                       result.close();
                       prepare.close();
                       if(nbAnnonces==0) { %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Aucune annonce enregistrée.</div>
                           <br/>
                       </div>
                       <br/>
                       <%
                       }
                       Objet.closeConnection();
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
