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
            <h1>Administration - Ajouter une catégorie</h1>
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
                        <div class="info">Catégorie : "<%= categorie.getCategorie() %>" enregistrée !</div>
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
                            <legend>Categorie</legend>
                            <form action="./ajouter-categorie.html#form" method="POST">
                                <div>Categorie :</div>
                                <input type="text" name="categorie" value="" size="40" maxlength="100" />
                                <br/>
                                <input type="submit" value="Valider" name="kermit" />
                            </form>
                        </fieldset>
                    </div>
                    <h2>Catégories enregistrées :</h2>
                    <%
                    Objet.getConnection();
                    String query="SELECT categorie FROM table_categories ORDER BY categorie ASC";
                    Statement state=Objet.getConn().createStatement();
                    ResultSet result=state.executeQuery(query);
                    int nbCategories=0;
                    while(result.next()) {
                        nbCategories++;
                        String cat=result.getString("categorie");
                        %>
                        <div><%= cat %></div>
                        <br/>
                        <%
                        }
                    result.close();
                    state.close();
                    if(nbCategories==0) { %>
                    <br/>
                    <div class="info">Aucune catégorie enregistrée.</div>
                    <br/>
                    <%
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
