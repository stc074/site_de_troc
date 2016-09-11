<%@page import="classes.ListeAnnonces"%>
<%@page import="classes.ListeCategories"%>
<%@page import="java.io.File"%>
<%@page import="classes.Img"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc gratuit avec le bon troc</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Troc et échange avec le bon troc - Annonces gratuites sur toute la France." />
<meta charset="UTF-8">
<meta name="google-site-verification" content="y0TCkoa8kzYFJHjmxDnV-7f-EoyuGy_fDkFBb8vLqkk" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
</head>
    <body>
<div id="fb-root"></div>
<script>
    (function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/fr_FR/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
        <%@include file="./connexion.jsp" %>
        <%@include file="./haut.jsp" %>
        <section>
           <%
           try {
               if(request.getAttribute("listeCategories")!=null&&request.getAttribute("listeAnnonces")!=null) {
                   ListeCategories listeCategories=(ListeCategories)request.getAttribute("listeCategories");
                   ListeAnnonces listeAnnonces=(ListeAnnonces)request.getAttribute("listeAnnonces");
               %>
           <div>
                   <nav>
                   <ul class="listeCategories">
                       <li>CATÉGORIE</li>
                       <%
                       for(int i=0; i<listeCategories.getCategories().length; i++) {
                           String categorie=listeCategories.getCategories()[i];
                           String uri=listeCategories.getUris()[i];
                           %>
                           <li class="cat" onclick="javascript:window.location.href='<%= uri %>';">
                               <a href="<%= uri %>" title="TROC <%= categorie %>"><%= categorie %></a>
                           </li>
                               <%
                       }
                       %>
                   </ul>
                   </nav>
                    <div class="colonneDroite">
                        <header>
                                  <h1>Troquez avec le bon troc !</h1>
                                  <div class="info">Troqueurs, troqueuses, </div>
                                  <p>Bienvenue sur Le bon troc, le site grâce auquel vous pourrez échangez gratuitement en ligne.</p>
                                  <h2>Je veux troquer !</h2>
                                  <p>Pas de problème déposez en 2 minutes votre annonce en <a href="./deposer-annonce-1.html" title="DÉPOSER UNE ANNONCE" rel="nofollow">CLIQUANT ICI</a>.</p>
           <ul class="reseauxSoc">
               <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
               <li>
                   <div class="fb-like" data-send="true" data-layout="button_count" data-width="450" data-show-faces="true"></div>
               </li>
           </ul>
                                  <p></p>
                                  <div class="cadre2">
                                      <div>Nombre d'annonces : <%= listeAnnonces.getNbAnnonces() %>.</div>
                                      <div>Nombre d'inscrit(e)s : <%= listeAnnonces.getNbMembres() %>.</div>
                                  </div>
                        </header>
                                  <h2>Les dernières annonces</h2>
                                      <div class="listeAnnoncePub2">
 <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
                                      </div>
           <p></p>
                                  <p>
                                  <%
                                  for(int i=0; i<listeAnnonces.getTitres().length; i++) {
                                    if(listeAnnonces.getTitres()[i]!=null) {
                                        if(i==Math.floor(listeAnnonces.getTitres().length/2)) { %>
                                      <div class="listeAnnoncePub2">
 <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
                                      </div>
           <p></p>
           <%
                     }
                                      %>
                                      <div class="listeAnnonce2" onclick="javascript:window.location.href='<%= listeAnnonces.getUris()[i] %>#annonce';">
                                          <div class="listeAnnonceGauche">
                                              <%
                                              String extension=listeAnnonces.getExtensions()[i];
                                              if(extension.length()>0) {
                                                  %>
                                                  <img src="./photo-mini-1-<%= listeAnnonces.getIds()[i]%>-<%= listeAnnonces.getIndexs()[i]%><%= extension%>" width="<%= listeAnnonces.getLargeurs()[i]%>" height="<%= listeAnnonces.getHauteurs()[i]%>" alt="miniature"/>
                                                  <%
                                              } else { %>
                                              <img src="./GFXs/miniature.png" width="100" height="100" alt="miniature"/>
                                              <% } %>
                                          </div>
                                          <div class="listeAnnonceDroite">
                                              <h1>
                                                  <a href="<%= listeAnnonces.getUris()[i] %>#annonce" title="<%= listeAnnonces.getTitres()[i] %>"><%= listeAnnonces.getTitres()[i] %></a>
                                              </h1>
                                                  <p>
                                                  <%= listeAnnonces.getLigne1s()[i] %>
                                                  <br/>
                                                  <%= listeAnnonces.getLigne2s()[i] %>
                                                  <br/>
                                                  <br/>
                                                  <%= listeAnnonces.getLigne3s()[i] %>
                                                  <br/>
                                                  </p>
                                          </div>
                                      </div>
                                          <br/>
                                      <%
                                                                           }
                                  }
                                  %>
                                      <div class="listeAnnoncePub2">
 <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
                                      </div>
           <p></p>
                                  <br/>
                                  <div class="lienAnnonces">
                                      <a href="./annonces.html" title="TOUTES LES ANNONCES">TOUTES LES ANNONCES</a>
                                  </div>
                                  <p></p>
           <p></p>
                   </div>
           </div>
                   <%
                                     }
                   } catch(Exception ex) { %>
                   <br/>
                   <div class="erreur">
                       <div>Erreur :</div>
                       <br/>
                       <div><%= ex.getMessage() %></div>
                   </div>
                   <br/>
                   <% } %>
        </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
