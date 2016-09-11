<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Messagerie"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc - Messages Envoyes</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Troc et echange avec le bon troc - Deposer vos annonce de troc d'echange - consultez les annonces de troc, d'echange." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
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
           <h1>Messages envoyés</h1>
           <%
           try {
               if(request.getAttribute("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Vous devez être connecté pour pouvoir consulter vos messages.</div>
                           <br/>
                           <div>
                               <a href="./inscription.html" title="S'INSCRIRE" rel="nofollow">S'INSCRIRE</a>
                           </div>
                           <br/>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("messagerie")!=null) {
                       Messagerie messagerie=(Messagerie)request.getAttribute("messagerie");
                       Objet.getConnection();
                       String query="SELECT t1.id,t1.objet,t1.timestamp,t1.etat,t2.pseudo FROM table_messages AS t1,table_membres AS t2 WHERE t1.id_membre_expediteur=? AND t2.id=t1.id_membre_destinataire ORDER BY t1.timestamp DESC";
                       PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                       prepare.setLong(1, messagerie.getMembre().getId());
                       ResultSet result=prepare.executeQuery();
                       int nbMessages=0;
                       Calendar cal=Calendar.getInstance();
                       while(result.next()) {
                           nbMessages++;
                           long idMessage=result.getLong("id");
                           String objet=result.getString("objet");
                           long timestamp=result.getLong("timestamp");
                           int etat=result.getInt("etat");
                           String pseudo=result.getString("pseudo");
                           cal.setTimeInMillis(timestamp);
                           %>
                           <div class="cadre2">
                               <div>
                                   <a href="./message-envoye-<%= idMessage %>.html" title="CONSULTER CE MESSAGE" rel="nofollow"><%= objet %></a>
                                   <%
                                   if(etat==0) { %>
                                   <span>[<span class="clign">Non consulté</span>]</span>
                                   <% } %>
                                   <span>&nbsp;Message envoyé à <%= pseudo %>, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.&nbsp;</span>
                                   <a href="./efface-message-2-<%= idMessage %>.html" title="ÉFFACER CE MESSAGE" rel="nofollow">ÉFFACER</a>
                               </div>
                           </div>
                               <br/>
                               <%
                               }
                       result.close();
                       prepare.close();
                       if(nbMessages==0) { %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Aucun message envoyé dans votre boite.</div>
                           <br/>
                       </div>
                       <br/>
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
