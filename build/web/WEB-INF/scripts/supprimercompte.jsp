<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc - Supprimer mon compte</title>
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
           <h1>Le bon troc - Supprimer mon compte</h1>
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
                       }
                   } else if(request.getAttribute("membre")!=null) {
                       Membre membre=(Membre)request.getAttribute("membre");
                       if(membre.getTest()==0) {
                           %>
                           <p>Si vous supprimez votre compte, toutes les données lui étant associées seront supprimées (Annonces & messages).</p>
                           <div class="info">Pour supprimer définitivement votre compte, cliquez sur le bouton ci-dessous :</div>
                           <br/>
                           <div id="form">
                               <fieldset>
                                   <legend>Supprimer mon compte</legend>
                                   <form action="./supprimer-compte.html#form" method="POST">
                                       <div>Pour supprimer votre compte, cliquez sur le bouton :</div>
                                       <input type="submit" value="Supprimer mon compte" name="kermit" />
                                   </form>
                               </fieldset>
                           </div>
                           <%
                           } else if(membre.getTest()==1) { %>
                           <br/>
                           <div class="cadre2">
                               <br/>
                               <div class="info">Votre compte et les données lui étant associées ont été supprimé.</div>
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
