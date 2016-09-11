<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Categorie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>ADMINISTRATION</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="date" content=""/>
<meta name="copyright" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Troc et echange avec le bon troc - Deposer vos annonce de troc d'echange - consultez les annonces de troc, d'echange." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="../GFXs/favicon.png" />
<link href="../CSS/style.css" type="text/css" rel="stylesheet" />
</head>
    <body>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Administration - Ajouter une Sous-catégorie</h1>
            <%
            try {
                if(request.getAttribute("categorie")!=null) {
                    Categorie categorie=(Categorie)request.getAttribute("categorie");
                    %>
                    <br/>
                    <div id="form">
                        <%
                        if(categorie.getTest()==1) { %>
                        <br/>
                        <div class="info">Sous catégorie "<%= categorie.getSousCategorie() %>" enregistrée dans la catégorie "<%= categorie.getCategorie() %>" !!</div>
                        <br/>
                        <%
                        }
                        if(request.getParameter("kermit")!=null&&categorie.getErrorMsg().length()>0) { %>
                        <div class="erreur">
                            <div>Erreur(s) :</div>
                            <br/>
                            <div><%= categorie.getErrorMsg() %></div>
                        </div>
                        <% } %>
                        <fieldset>
                            <legend>Ajouter une sous-catégorie</legend>
                            <form action="./ajouter-sous-categorie.html#form" method="POST">
                                <div>
                                    <span>Catégorie :</span>
                                    <select name="idCategorie" onchange="javascript:window.location.href='./ajouter-sous-categorie-'+this.value+'.html#form';">
                                        <option value="0"<% if(categorie.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                        <%
                                        Objet.getConnection();
                                        String query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
                                        Statement state=Objet.getConn().createStatement();
                                        ResultSet result=state.executeQuery(query);
                                        while(result.next()) {
                                            long idCategorie=result.getLong("id");
                                            String cat=result.getString("categorie");
                                            %>
                                            <option value="<%= idCategorie %>"<% if(categorie.getIdCategorie()==idCategorie) out.print(" selected=\"selected\""); %>><%= cat %></option>
                                            <%
                                            }
                                        result.close();
                                        state.close();
                                        %>
                                    </select>
                                </div>
                                    <div>Sous catégorie :</div>
                                    <input type="text" name="sousCategorie" value="" size="40" maxlength="100" />
                                    <br/>
                                    <input type="submit" value="Valider" name="kermit" />
                            </form>
                        </fieldset>
                    </div>
                                        <%
                                        if(categorie.getIdCategorie()!=0) { %>
                                        <h2>Sous-catégories de la categorie : <%= categorie.getCategorie() %> :</h2>
                                        <%
                                        query="SELECT sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                                        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                        prepare.setLong(1, categorie.getIdCategorie());
                                        result=prepare.executeQuery();
                                        int nbSousCategories=0;
                                        while(result.next()) {
                                            nbSousCategories++;
                                            String sousCategorie=result.getString("sous_categorie");
                                            %>
                                            <div><%= sousCategorie %></div>
                                            <br/>
                                            <%
                                            }
                                        result.close();
                                        prepare.close();
                                        if(nbSousCategories==0) { %>
                                        <br/>
                                        <div class="info">Aucune sous catégorie dans "<%= categorie.getCategorie() %>" !</div>
                                        <br/>
                                        <%
                                        }
                                        }
                                        Objet.closeConnection();
                   }
                } catch(Exception ex) { %>
                <div class="erreur">
                    <div>Erreur :</div>
                    <br/>
                    <div><%= ex.getMessage() %></div>
                </div>
                <% } %>
        </div>
    </body>
</html>
