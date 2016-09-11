<%@page import="classes.Message"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc - Contacter un annonceur</title>
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
           <h1>Le bon troc - Contacter un annonceur</h1>
           <%
           try {
               if(request.getAttribute("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Vous devez être connecté pour pouvoir contacter un annonceur.</div>
                           <br/>
                           <div>
                               <a href="./inscription.html" title="S'INSCRIRE" rel="nofollow">S'INSCRIRE</a>
                           </div>
                           <br/>
                       </div>
                       <br/>
                       <%
                       break;
                       case 2: %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Annonce inconnue !</div>
                           <br/>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("message")!=null) {
                       Message message=(Message)request.getAttribute("message");
                       if(message.getTest()==0) {
                       %>
                       <h2><%= message.getAnnonce().getTitre() %> par <%= message.getMembreDestinataire().getPseudo() %></h2>
                       <div class="info">Pour contacter <%= message.getMembreDestinataire().getPseudo() %>, utilisez le formulaire ci dessous :</div>
                       <br/>
                       <div id="form">
                           <%
                           if(request.getParameter("kermit")!=null&&message.getErrorMsg().length()>0) { %>
                           <div class="erreur">
                               <div>Erreur(s) :</div>
                               <br/>
                               <div><%= message.getErrorMsg() %></div>
                           </div>
                           <% } %>
                           <fieldset>
                               <legend>Formulaire de contact</legend>
                               <form action="./contacter-annonceur.html#form" method="POST">
                                   <input type="hidden" name="idAnnonce" value="<%= message.getAnnonce().getId()%>" />
                                   <div>Objet de votre message :</div>
                                   <input type="text" name="objetMsg" value="<%= message.getObjetMsg()%>" size="30" maxlength="40" />
                                   <div>Contenu de votre message :</div>
                                   <textarea name="contenuMsg" id="contenuMsg" rows="4" cols="20"><%= message.getContenuMsg()%></textarea>
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
                            <script type="text/javascript">
                                    CKEDITOR.replace( 'contenuMsg' );
                            </script>
                       </div>
                       <%
                       } else if(message.getTest()==1) { %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Message envoyé à <%= message.getMembreDestinataire().getPseudo() %>.</div>
                           <br/>
                       </div>
                           <br/>
                           <%
                           message.blank();
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
