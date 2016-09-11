<%@page import="java.io.File"%>
<%@page import="classes.Img"%>
<%@page import="classes.AnnoncePhotos"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc - Deposer une annonce - Photos</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Troc et echange avec le bon troc - Deposer vos annonce de troc d'echange - consultez les annonces de troc, d'echange." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
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
           <br/>
<div>
<a name="fb_share" type="button_count" share_url="<%= Datas.URLROOT %>">Cliquez pour partager !!!</a><script src="http://static.ak.fbcdn.net/connect.php/js/FB.Share" type="text/javascript"></script>
</div>
           <br/>
           <g:plusone></g:plusone>
           <br/>
           <h1>Le bon troc - Deposer une annonce - Photos</h1>
           <%
           try {
               if(request.getAttribute("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <br/>
                       <div class="info">Vous devez vous identifier pour pouvoir uploader des photos.</div>
                       <br/>
                       <div>
                           <a href="./deposer-annonce-1.html" title="S'IDENTIFIER" rel="nofollow">S'IDENTIFIER</a>
                       </div>
                       <br/>
                       <%
                       break;
                       case 2: %>
                       <br/>
                       <div class="info">Annonce inconnue !</div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("annonce")!=null) {
                       AnnoncePhotos annonce=(AnnoncePhotos)request.getAttribute("annonce");
                       %>
                       <h2><%= annonce.getTitre() %></h2>
                       <br/>
                       <h2>Photos pour cette annonce</h2>
                       <div class="cadre3">
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
                                       <a href="./photo-<%= annonce.getId() %>-<%= i %><%= extension %>" title="<%= annonce.getTitre() %>" zoom="1">
                                       <img src="./photo-mini-2-<%= annonce.getId()%>-<%= i%><%= extension %>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
                                       </a>
                                   </div>
                                       <%
                                       }
                               }
                           }
                       if(nbPhotos==0) { %>
                       <br/>
                       <div class="info">Aucune photo encore enregistrée pour cette annonce.</div>
                       <br/>
                       <%
                       }
                       %>
                      </div>
                       <br/>
                       <div class="info">Si vous voulez uploader des photos, utilisez le formulaire ci-dessous :</div>
                       <br/>
                       <p>Note : En cas de gros fichier uploadez les photos les unes après les autres pour éviter les problèmes de mémoire.</p>
                       <div id="form">
                           <%
                           if(annonce.getTest()==1&&annonce.getErrorMsg().length()>0) { %>
                           <div class="erreur">
                               <div>Erreur(s) :</div>
                               <br/>
                               <div><%= annonce.getErrorMsg() %></div>
                           </div>
                           <br/>
                           <% } %>
                           <fieldset>
                               <legend>Upload de photos :</legend>
                               <form action="./deposer-annonce-3.html#form" method="POST" enctype="multipart/form-data">
                                   <div>Photo N° 1 :</div>
                                   <input type="file" name="1" value="" />
                                   <div>Photo N° 2 :</div>
                                   <input type="file" name="2" value="" />
                                   <div>Photo N° 3 :</div>
                                   <input type="file" name="3" value="" />
                                   <div>Photo N° 4 :</div>
                                   <input type="file" name="4" value="" />
                                   <div>Photo N° 5 :</div>
                                   <input type="file" name="5" value="" />
                                   <br/>
                                   <input type="submit" value="Valider" name="kermit" />
                                   <br/>
                               </form>
                           </fieldset>
                       </div>
                       <%
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
