<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc - Mon Compte</title>
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
           <h1>Le bon troc - Mon compte</h1>
           <%
           try {
               if(request.getAttribute("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <div class="cadre2">
                           <div class="info">Vous devez être connecter pour pouvoir accéder à votre compte.</div>
                           <br/>
                           <div><a href="./inscription.html" title="S'INSCRIRE" rel="nofollow">S'INSCRIRE</a></div>
                       </div>
                       <%
                       break;
                       case 2: %>
                       <br/>
                       <div class="cadre2">
                           <div><a href="./infos-personnelles.html" title="INFOS PERSOS" rel="nofollow">INFOS PERSONNELLES</a></div>
                       </div>
                       <br/>
                       <div class="cadre2">
                           <div>
                               <a href="./mes-annonces.html" title="MES ANNONCES" rel="nofollow">MES ANNONCES</a>
                           </div>
                       </div>
                       <br/>
                       <div class="cadre2">
                           <div>
                               <a href="./messagerie.html" title="MA MESSAGERIE" rel="nofollow">MA MESSAGERIE</a>
                           </div>
                       </div>
                       <br/>
                       <div class="cadre2">
                           <div>
                               <a href="./supprimer-compte.html" title="SUPPRIMER MON COMPTE" rel="nofollow">SUPPRIMER MON COMPTE</a>
                               <span>&nbsp;&rarr;DÉFINITIF</span>
                           </div>
                       </div>
                       <%
                       break;
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
