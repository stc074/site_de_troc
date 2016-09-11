<%@page import="classes.Datas"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:catch var="ex">
    <c:set var="tagTitle" value="Trocs et échanges avec le bon troc" scope="page"></c:set>
    <c:set var="tagDescription" value="Le bon troc - Site de trocs et d'échanges sur internet." scope="page"></c:set>
<c:if test="${requestScope.recherche!=null}">
    <c:set var="rec" value="${requestScope.recherche}" scope="page"></c:set>
    <c:set var="tagTitle" value="${rec.tagTitle}" scope="page"></c:set>
    <c:set var="tagDescription" value="${rec.tagDescription}" scope="page"></c:set>
</c:if>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${tagTitle}"></c:out></title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="date" content=""/>
<meta name="copyright" content=""/>
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
           <br/>
           <h1>Le bon troc - Annonces</h1>
           <c:if test="${rec!=null&&requestScope.listeRecherche!=null}">
               <c:set var="liste" value="${requestScope.listeRecherche}" scope="page"></c:set>
               <div id="form">
                   <fieldset>
                       <legend>Recherche </legend>
                       <form action="./annonces-de-troc.html#form" method="POST">
                           <label for="motsCles">Recherche :</label>
                           <input type="text" name="motsCles" id="motsCles" value="<c:out value="${rec.motsCles}"></c:out>" size="30" maxlength="300" />
                           <br/>
                           <input type="radio" name="type" id="type1" value="1"<c:if test="${rec.type==1}"> checked="checked"</c:if> onclick="javascript:window.location.href='./annonces-1-1.html#form';" />
                                  <label for="type1">&rarr;Je recherche&nbsp;</label>
                           <input type="radio" name="type" id="type2" value="2"<c:if test="${rec.type==2}"> checked="checked"</c:if> onclick="javascript:window.location.href='./annonces-1-2.html#form';" />
                                  <label for="type2">&rarr;Je propose&nbsp;</label>
                           <br/>
                           <label for="idCategorie">Catégorie :</label>
                           <select name="idCategorie" id="idCategorie" onchange="javascript:window.location.href='./annonces-2-'+this.value+'.html#form';">
                               <option value="0"<c:if test="${rec.idCategorie==0}"> selected="selected"</c:if>>Toutes</option>
                               <c:forEach var="i" begin="0" end="${liste.nbCategories-1}" step="1">
                                   <option value="<c:out value="${liste.idCategories[i]}"></c:out>"<c:if test="${rec.idCategorie==liste.idCategories[i]}"> selected="selected"</c:if>><c:out value="${liste.categories[i]}"></c:out></option>
                               </c:forEach>
                           </select>
                               <label for="idSousCategorie">&nbsp;Sous-Catégorie :</label>
                               <select name="idSousCategorie" id="idSousCategorie" onchange="javascript:window.location.href='./annonces-3-'+this.value+'.html#form';">
                                   <option value="0"<c:if test="${rec.idSousCategorie==0}"> selected="selected"</c:if>>Toutes</option>
                                   <c:if test="${rec.nbSousCategories>0}">
                                       <c:forEach var="i" begin="0" end="${rec.nbSousCategories-1}" step="1">
                                           <option value="<c:out value="${rec.idSousCategories[i]}"></c:out>"<c:if test="${rec.idSousCategorie==rec.idSousCategories[i]}"> selected="selected"</c:if>><c:out value="${rec.sousCategories[i]}"></c:out></option>
                                       </c:forEach>
                                   </c:if>
                               </select>
                                   <br/>
                                   <span>Localisation &rarr; </span>
                                   <label for="idRegion">Région :</label>
                                   <select name="idRegion" id="idRegion" onchange="javascript:window.location.href='./annonces-4-'+this.value+'.html#form';">
                                       <option value="0"<c:if test="${rec.idRegion==0}"> selected="selected"</c:if>>Toutes</option>
                                       <c:if test="${liste.nbRegions>0}">
                                           <c:forEach var="i" begin="0" end="${liste.nbRegions-1}" step="1">
                                               <option value="<c:out value="${liste.idRegions[i]}"></c:out>"<c:if test="${rec.idRegion==liste.idRegions[i]}"> selected="selected"</c:if>><c:out value="${liste.regions[i]}"></c:out></option>
                                           </c:forEach>
                                       </c:if>
                                   </select>
                                       <label for="idDepartement">Département :</label>
                                       <select name="idDepartement" id="idDepartement" onchange="javascript:window.location.href='./annonces-5-'+this.value+'.html#form';">
                                           <option value="0"<c:if test="${rec.idDepartement==0}"> selected="selected"</c:if>>Tous</option>
                                           <c:if test="${rec.nbDepartements>0}">
                                               <c:forEach var="i" begin="0" end="${rec.nbDepartements-1}" step="1">
                                                   <option value="<c:out value="${rec.idsDepartements[i]}"></c:out>"<c:if test="${rec.idDepartement==rec.idsDepartements[i]}"> selected="selected"</c:if>><c:out value="${rec.departements[i]}"></c:out></option>
                                               </c:forEach>
                                           </c:if>
                                       </select>
                                           <label for="idCommune">Commune :</label>
                                           <select name="idCommune" id="idCommune" onchange="javascript:window.location.href='./annonces-6-'+this.value+'.html#form';">
                                               <option value="0"<c:if test="${rec.idCommune==0}"> selected="selected"</c:if>>Toutes</option>
                                               <c:if test="${rec.nbCommunes>0}">
                                                   <c:forEach var="i" begin="0" end="${rec.nbCommunes-1}" step="1">
                                                       <option value="<c:out value="${rec.idsCommunes[i]}"></c:out>"<c:if test="${rec.idCommune==rec.idsCommunes[i]}"> selected="selected"</c:if>><c:out value="${rec.communes[i]}"></c:out></option>
                                                   </c:forEach>
                                               </c:if>
                                           </select>
                                               <br/>
                                               <input type="submit" value="Rechercher" name="kermit" />
                                               <input type="submit" value="Vider le formulaire" name="reset" />
                       </form>
                   </fieldset>
               </div>
                                               <c:if test="${rec.nbAnnoncesPage==0}">
                                                   <br/>
                                                   <div class="cadre2">
                                                       <div class="info">Désolé, aucune annonce pour cette recherche.</div>
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
                                               </c:if>
                                               <c:if test="${rec.nbAnnoncesPage>0}">
                                                   <h2><c:out value="${rec.nbAnnonces}"></c:out> annonces pour cette recherche</h2>
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
                                                                             <p></p>
                                                  <div class="listeAnnoncePub">
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
                                               <c:forEach var="i" begin="0" end="${rec.nbAnnoncesPage-1}" step="1">
                                                   <c:if test="${rec.nbAnnoncesPage>5&&i==rec.nbAnnoncesPage2}">
                  <div class="listeAnnoncePub">
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
                                                   </c:if>
                                                                      <div class="listeAnnonce" onclick="javascript:window.location.href='${rec.urls[i]}#annonce';">
                                                                         <div class="listeAnnonceGauche">
                                                                             ${rec.codesImg[i]}
                                                                         </div>
                                                                         <div class="listeAnnonceDroite">
                                                                             <h1>
                                                                                 <a href="${rec.urls[i]}#annonce" title="${rec.titres[i]}">${rec.titres[i]}</a>
                                                                             </h1>
                                                                                 <p>
                                                                                 ${rec.lignes1[i]}
                                                                                 <br/>
                                                                                 ${rec.lignes2[i]}
                                                                                 <br/>
                                                                                 <br/>
                                                                                 ${rec.lignes3[i]}
                                                                                 <br/>
                                                                                 </p>
                                                                         </div>
                                                                     </div>
                                                                             <br/>
                                               </c:forEach>
                                                                            <div class="pages">
                                                                                 <span>Pages d'annonces :</span>
                                                                                 <c:forEach var="i" begin="${rec.prem}" end="${rec.der}" step="1">
                                                                                     <c:choose>
                                                                                         <c:when test="${rec.page==i}">
                                                                                             <span>[<span class="clign"><c:out value="${i+1}"></c:out></span>]</span>
                                                                                         </c:when>
                                                                                         <c:otherwise>
                                                                                             <span>[<a href="./annonces-7-${i}.html#form" title="PAGE <c:out value="${i+1}"></c:out>"><c:out value="${i+1}"></c:out></a>]</span>
                                                                                         </c:otherwise>
                                                                                     </c:choose>
                                                                                 </c:forEach>
                                                                             </div>
                                                                             <p></p>
                                                                    <div class="listeAnnoncePub">
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
                                               </c:if>
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