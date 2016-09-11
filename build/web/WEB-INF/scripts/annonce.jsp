<%@page import="java.io.File"%>
<%@page import="classes.Img"%>
<%@page import="java.util.Calendar"%>
<%@page import="classes.Datas"%>
<%@page import="classes.Annonce"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
try {
Annonce annonce=null;
String tagTitle="Troc et echange avec le bon troc";
String tagDescription="Troc et echange avec le bon troc - Annonces de trocs gratuites.";
if(request.getAttribute("annonce")!=null) {
    annonce=(Annonce)request.getAttribute("annonce");
    tagTitle=annonce.getTagTitle();
    tagDescription=annonce.getTagDescription();
    }
%>
<!DOCTYPE html>
<html>
<head>
<title><%= tagTitle %></title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="<%= tagDescription %>" />
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./scripts/scripts.js"></script>
<script src="./js-global/FancyZoom.js" type="text/javascript"></script>
<script src="./js-global/FancyZoomHTML.js" type="text/javascript"></script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
</head>
    <body onload="setupZoom()">
        <%@include file="./connexion.jsp" %>
        <%@include file="./haut.jsp" %>
        <section>
           <%
           if(request.getAttribute("info")!=null) {
               int info=Integer.parseInt(request.getAttribute("info").toString());
               switch(info) {
                   case 1: %>
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
               } else if(annonce!=null) {
                   //out.println(annonce.getErrorMsg());
    Calendar cal=Calendar.getInstance();
cal.setTimeInMillis(annonce.getTimestamp());
    %>
           <div id="annonce">
               <br/>
               <div class="cadre">
                   <br/>
               <div>
                   <%
                   String uriRetour="./annonces-de-troc.html#form";
                   if(session.getAttribute("uriRetour")!=null)
                       uriRetour=session.getAttribute("uriRetour").toString();
                   %>
                   <a href="<%= uriRetour %>" title="RETOUR À LA LISTE">RETOUR À LA LISTE</a>
               </div>
               <br/>
               <div>
                   <a href="#" title="SIGNALER UN ABUS" onclick="javascript:signalerAbus(<%= annonce.getId() %>);">SIGNALER UN ABUS</a>
               </div>
               <br/>
           <ul class="reseauxSoc2">
               <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
           </ul>
           <p></p>
              </div>
<br/>
            <div class="cadrePub">
               <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
           </div>
           <br/>
           <div class="cadre">
               <h1>Troque <%= annonce.getTitre() %> contre <%= annonce.getTitreContre() %></h1>
               <div class="info">Annonce déposée par <%= annonce.getPseudo() %> le, <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</div>
               <br/>
               <div>Localisation : <%= annonce.getCodePostal() %>-<%= annonce.getCommune() %>|<%= annonce.getRegion() %>-<%= annonce.getDepartement() %>.</div>
           </div>
           <br/>
           <div class="cadre3">
               <h2>Troque <%= annonce.getTitre() %></h2>
               <div><%= annonce.getCategorie() %>&rarr;<%= annonce.getSousCategorie() %>.</div>
               <%= annonce.getContenu() %>
           </div>
           <br/>
           <div class="cadre3">
               <h2>Contre <%= annonce.getTitreContre() %></h2>
               <div><%= annonce.getCategorieContre() %>&rarr;<%= annonce.getSousCategorieContre() %>.</div>
               <%= annonce.getContenuContre() %>
           </div>
           <br/>
            <div class="cadrePub">
               <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
           </div>
           <br/>
           <div class="cadre">
               <h2>Photos (Cliquez dessus pour voir en taille réelle)</h2>
               <div class="photosMini">
               <%
               int nbPhotos=0;
               Img img=new Img();
               for(int i=1;i<=5;i++) {
                   String extension=annonce.getExtensions()[i-1];
                   if(extension.length()>0) {
                       String filename=Datas.DIR+"photos/"+annonce.getId()+"_"+i+extension;
                       String filenameMini2=Datas.DIR+"photos/mini2_"+annonce.getId()+"_"+i+extension;
                       File file=new File(filename);
                       File fileMini2=new File(filenameMini2);
                       if(file.exists()&&fileMini2.exists()) {
                           nbPhotos++;
                           img.getSize(fileMini2);
                           int largeur=img.getWidth();
                           int hauteur=img.getHeight();
                           %>
                           <div class="mini">
                               <a href="./photo-<%= annonce.getId() %>-<%= i %><%= extension %>" title="<%= annonce.getTitre() %> contre <%= annonce.getTitreContre() %>" zoom="1">
                                   <img src="./photo-mini-2-<%= annonce.getId()%>-<%= i%><%= extension %>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
                               </a>
                           </div>
                                   <%
                                   }
                       }
                   }
               %>
                           </div>
               <%
               if(nbPhotos==0) { %>
               <br/>
               <div class="info">Aucune photo enregistrée pour cette annonce de troc.</div>
               <br/>
               <% } %>
           </div>
               <br/>
               <div class="cadre">
                   <h2>Contacter <%= annonce.getPseudo() %></h2>
                   <div>Pour contacter <%= annonce.getPseudo() %>(L'annonceur) <a href="./contacter-annonceur-<%= annonce.getId() %>.html" title="CONTACTER <%= annonce.getPseudo() %>">CLIQUEZ ICI</a></div>
               </div>
           <br/>
            <div class="cadrePub">
               <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
           </div>
           <br/>
           </div>
           <% } %>
        </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
    <%
    } catch(Exception ex) { %>
    <html>
        <head></head>
        <body>
            <div class="contenu">
                <br/>
                <div class="cadre2">
                    <div><%= ex.getMessage() %></div>
                </div>
            </div>
        </body>
    </html>
                <% } %>
