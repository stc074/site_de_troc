<%@page import="classes.Datas"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:catch var="ex">
    <c:set var="tagTitle" value="Trocs et échanges avec Le bon troc" scope="page"></c:set>
    <c:set var="tagDescription" value="Le bon troc - trocs et échanges gratuits." scope="page"></c:set>
    <c:set var="an" value="${null}" scope="page"></c:set>
    <c:if test="${requestScope.annonces!=null}">
        <c:set var="an" value="${requestScope.annonces}" scope="page"></c:set>
        <c:set var="tagTitle" value="${an.tagTitle}" scope="page"></c:set>
        <c:set var="tagDescription" value="${an.tagDescription}" scope="page"></c:set>
    </c:if>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${tagTitle}"></c:out></title>
<meta name="generator" content="NETBEANS 7.01"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="<c:out value="${tagDescription}"></c:out>" />
<meta charset="UTF-8">
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
           <c:if test="${requestScope.info!=null}">
               <c:choose>
                   <c:when test="${requestScope.info==1}">
                       <br/>
                       <div class="cadre2">
                           <div class="info">Catégorie inconnue !</div>
                       </div>
                       <br/>
                   </c:when>
               </c:choose>
           </c:if>
                   <c:if test="${an!=null}">
                       <div>
                           <nav>
                               <ul class="listeCategories">
                                   <li class="parent" onclick="javascript:window.location.href='./';"><a href="./" title="TOUTES LES CATÉGORIES">TOUTES LES CATÉGORIES</a></li>
                                   <li><c:out value="${an.categorie}"></c:out></li>
                                   <c:if test="${an.nbSousCategories>0}">
                                       <c:forEach var="i" begin="0" end="${an.nbSousCategories-1}" step="1">
                                           <c:choose>
                                               <c:when test="${an.idSousCategorie==an.idsSousCat[i]}">
                                                   <li class="actuel" onclick="javascript:window.location.href='${an.urlsSousCat[i]}';">
                                                       <a href="${an.urlsSousCat[i]}" title="<c:out value="${an.titresSousCat[i]}"></c:out>"><c:out value="${an.titresSousCat[i]}"></c:out></a>
                                                   </li>
                                               </c:when>
                                               <c:otherwise>
                                                   <li class="cat" onclick="javascript:window.location.href='${an.urlsSousCat[i]}';">
                                                       <a href="${an.urlsSousCat[i]}" title="<c:out value="${an.titresSousCat[i]}"></c:out>"><c:out value="${an.titresSousCat[i]}"></c:out></a>
                                                   </li>
                                               </c:otherwise>
                                           </c:choose>
                                       </c:forEach>
                                   </c:if>
                               </ul>
                           </nav>
                                   <div class="colonneDroite">
                                       <c:choose>
                                           <c:when test="${an.nbAnnoncesPage==0}">
                                               <div class="cadre2">
                                                   <div class="info">Désolé, aucune annonce dans cette catégorie.</div>
                                               </div>
                                               <br/>
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
                                               <br/>
                                           </c:when>
                                           <c:when test="${an.nbAnnoncesPage>0}">
                                               <h2><c:out value="${an.nbAnnonces}"></c:out> annonces dans la catégorie <c:out value="${an.categorie}"></c:out>-<c:out value="${an.sousCategorie}"></c:out></h2>
            <p></p>
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
                                               <br/>
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
                                               <br/>
                                                <c:forEach var="i" begin="0" end="${an.nbAnnoncesPage-1}" step="1">
                                                    <c:if test="${an.nbAnnoncesPage>5&&i==an.nbAnnoncesPage2}">
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
                                               <br/>                                
                                                    </c:if>
                                                    <div class="listeAnnonce2" onclick="javascript:window.location.href='${an.urls[i]}#annonce';">
                                                       <div class="listeAnnonceGauche">
                                                           ${an.codesImg[i]}
                                                       </div>
                                                       <div class="listeAnnonceDroite">
                                                           <h1>
                                                               <a href="${an.urls[i]}#annonce" title="${an.titres[i]}">${an.titres[i]}</a>
                                                           </h1>
                                                               <p>
                                                               ${an.lignes1[i]}
                                                               <br/>
                                                               ${an.lignes2[i]}
                                                               <br/>
                                                               <br/>
                                                               ${an.lignes3[i]}
                                                               <br/>
                                                               </p>
                                                       </div>
                                                   </div>
                                                           <br/>
                                               </c:forEach>
                                               <div class="pages">
                                                   <span>Pages d'annonces : </span>
                                                   <c:forEach var="i" begin="${an.prem}" end="${an.der}" step="1">
                                                       <c:choose>
                                                           <c:when test="${an.page==i}">
                                                               <span>[<span class="clign"><c:out value="${i+1}"></c:out></span>]</span>
                                                           </c:when>
                                                           <c:otherwise>
                                                               <span>[<a href="./troc2-${an.idCategorie}-${an.idSousCategorie}-${i}.html" title="PAGE #<c:out value="${i+1}"></c:out>"><c:out value="${i+1}"></c:out></a>]</span>
                                                           </c:otherwise>
                                                       </c:choose>
                                                   </c:forEach>
                                               </div>
                                                <br/>
                                               <br/>
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
                                               <br/>
                                           </c:when>
                                       </c:choose>
                                   </div>
                       </div>
                   </c:if>
        </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
</c:catch>
<c:if test="${not empty ex}">
    <html>
        <head>
        <title>ERREUR !</title>
        </head>
        <body>
            <div class="contenu">
                <div class="erreur">
                    <div>Erreur :</div>
                    <br/>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </div>
        </body>
    </html>
</c:if>
