<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Annonce"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Troc et echange avec le bon troc - Deposer une annonce - Contenu</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Troc et echange avec le bon troc - Deposer vos annonce de troc d'echange - consultez les annonces de troc, d'echange." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./scripts/scripts.js"></script>
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
           <h1>Le bon troc - Deposer une annonce - Contenu</h1>
           <%
           try {
               if(request.getAttribute("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <br/>
                       <div class="info">Vous devez vous identifier avant de publier une annonce.</div>
                       <br/>
                       <div>
                           <a href="./deposer-annonce-1.html" title="S'IDENTIFIER" rel="nofollow">S'IDENTIFIER</a>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("annonce")!=null) {
                       Annonce annonce=(Annonce)request.getAttribute("annonce");
                       if(annonce.getTest()==0) {
                       %>
                       <br/>
                       <div class="info">Utilisez le formulaire ci-dessous pour remplir le contenu de votre annonce :</div>
                       <br/>
                       <div id="form">
                           <%
                           if(request.getParameter("kermit")!=null&&annonce.getErrorMsg().length()>0) { %>
                           <div class="erreur">
                               <div>Erreur(s) :</div>
                               <br/>
                               <div><%= annonce.getErrorMsg() %></div>
                           </div>
                           <br/>
                           <% } %>
                           <fieldset>
                               <legend>Contenu de mon annonce :</legend>
                               <form action="./deposer-annonce-2.html#form" method="POST">
                                   <div>
                                       <span>Catégorie de ce que je propose :</span>
                                       <select name="idCategorie" onchange="javascript:changeCategorie(this.value);">
                                           <option value="0"<% if(annonce.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                           <%
                                           Objet.getConnection();
                                           String query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
                                           Statement state=Objet.getConn().createStatement();
                                           ResultSet result=state.executeQuery(query);
                                           while(result.next()) {
                                               long idCategorie=result.getLong("id");
                                               String categorie=result.getString("categorie");
                                               %>
                                               <option value="<%= idCategorie %>"<% if(annonce.getIdCategorie()==idCategorie) out.print(" selected=\"selected\""); %>><%= categorie %></option>
                                               <%
                                               }
                                           result.close();
                                           state.close();
                                           %>
                                       </select>
                                   </div>
                                       <div id="innerSousCategories">
                                           <span>Sous-catégorie de ce que je propose :</span>
                                           <select name="idSousCategorie">
                                               <option value="0"<% if(annonce.getIdSousCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                               <%
                                               if(annonce.getIdCategorie()!=0) {
                                                   query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie";
                                                   PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                                   prepare.setLong(1, annonce.getIdCategorie());
                                                   result=prepare.executeQuery();
                                                   while(result.next()) {
                                                       long idSousCategorie=result.getLong("id");
                                                       String sousCategorie=result.getString("sous_categorie");
                                                       %>
                                                       <option value="<%= idSousCategorie %>"<% if(annonce.getIdSousCategorie()==idSousCategorie) out.print(" selected=\"selected\""); %>><%= sousCategorie %></option>
                                                       <%
                                                       }
                                                   result.close();
                                                   prepare.close();
                                                   }
                                           %>
                                           </select>
                                       </div>
                                           <br/>
                                  <div>Titre de ce que je propose :</div>
                                   <input type="text" name="titre" value="<%= annonce.getTitre()%>" size="30" maxlength="80" />
                                   <div>Plus longue description de ce que je propose :</div>
                                   <textarea name="contenu" id="contenu" rows="4" cols="20"><%= annonce.getContenu()%></textarea>
                                   <br/>
                                            <div>
                                               <span>Catégorie de ce que je veux en échange :</span>
                                               <select name="idCategorieContre" onchange="javascript:changeCategorieContre(this.value);">
                                                   <option value="0"<% if(annonce.getIdCategorieContre()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                   <%
                                                   query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
                                                   state=Objet.getConn().createStatement();
                                                   result=state.executeQuery(query);
                                                   while(result.next()) {
                                                       long idCategorieContre=result.getLong("id");
                                                       String categorieContre=result.getString("categorie");
                                                       %>
                                                       <option value="<%= idCategorieContre %>"<% if(annonce.getIdCategorieContre()==idCategorieContre) out.print(" selected=\"selected\""); %>><%= categorieContre %></option>
                                                       <%
                                                       }
                                                   result.close();
                                                   state.close();
                                                   %>
                                               </select>
                                           </div>
                                               <div id="innerSousCategoriesContre">
                                                   <span>Sous-catégorie de ce que je veux en échange :</span>
                                                   <select name="idSousCategorieContre">
                                                       <option value="0"<% if(annonce.getIdSousCategorieContre()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                       <%
                                                       if(annonce.getIdCategorieContre()!=0) {
                                                           query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                                                           PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                                           prepare.setLong(1, annonce.getIdCategorieContre());
                                                           result=prepare.executeQuery();
                                                           while(result.next()) {
                                                               long idSousCategorieContre=result.getLong("id");
                                                               String sousCategorieContre=result.getString("sous_categorie");
                                                               %>
                                                               <option value="<%= idSousCategorieContre %>"<% if(annonce.getIdSousCategorieContre()==idSousCategorieContre) out.print(" selected=\"selected\""); %>><%= sousCategorieContre %></option>
                                                               <%
                                                               }
                                                           result.close();
                                                           prepare.close();
                                                           }
                                                   %>
                                                   </select>
                                               </div>
                                                   <br/>
                                           <div>Titre de ce que je veux en échange :</div>
                                           <input type="text" name="titreContre" value="<%= annonce.getTitreContre()%>" size="30" maxlength="80" />
                                           <div>Longue description de ce que je veux en échange :</div>
                                           <textarea name="contenuContre" id="contenuContre" rows="4" cols="20"><%= annonce.getContenuContre()%></textarea>
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
                                    CKEDITOR.replace( 'contenu' );
                                    CKEDITOR.replace( 'contenuContre' );
                            </script>
                         </div>
                       <%
                                    Objet.closeConnection();
                                    } else if(annonce.getTest()==1) {
                                        annonce.blank();
                                        %>
                                        <script type="text/javascript">
                                            window.location.href="./deposer-annonce-3.html";
                                        </script>
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
