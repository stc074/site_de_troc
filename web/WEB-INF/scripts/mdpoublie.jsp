<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc</title>
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
           <h1>Mot de passe oublié</h1>
           <h2>Vous avez oublié votre mot de passe ...</h2>
           <div class="info">... Ce n'est pas grave nous allons vous en renvoyer un autre.</div>
           <br/>
           <%
           try {
               if(request.getAttribute("membre")!=null) {
                   Membre membre=(Membre)request.getAttribute("membre");
                   if(membre.getTest()==0) {
                   %>
           <div id="form">
               <%
               if(request.getParameter("kermit")!=null&&membre.getErrorMsg().length()>0) { %>
               <div class="erreur">
                   <div>Erreur(s) :</div>
                   <br/>
                   <div><%= membre.getErrorMsg() %></div>
               </div>
               <br/>
               <% } %>
               <fieldset>
                   <legend>Email de votre compte</legend>
                   <form action="./mdp-oublie.html" method="POST">
                       <div>Saisissez l'ADRESSE EMAIL de votre compte :</div>
                       <input type="text" name="email" value="<%= membre.getEmail()%>" size="40" maxlength="200" />
                       <br/>
                       <br/>
                       <div class="captcha"></div>
                       <div class="captchaDroite">
                           <span>&rarr;Copiez le code SVP&rarr;</span>
                           <input type="text" name="captcha" value="" size="5" maxlength="5" />
                       </div>
                       <br/>
                       <br/>
                       <input type="submit" value="Valider" name="kermit" />
                       <br/>
                   </form>
               </fieldset>
           </div>
                       <%
                       } else if(membre.getTest()==1) { %>
                       <br/>
                       <div class="info">Un nouveau mot de passe a été envoyé à l'adresse : <%= membre.getEmail() %>.</div>
                       <br/>
                       <%
                       membre.blank();
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
