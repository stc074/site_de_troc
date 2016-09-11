<%@page import="javax.naming.NamingException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="java.util.Calendar"%>
<%@page import="classes.CGU"%>
<%!
private String query="";
private Statement state=null;
private ResultSet result=null;
private Calendar cal=null;
%>
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
<script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
</head>
    <body>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Administration - Liste des abus</h1>
            <%
            try {
        cal=Calendar.getInstance();
        Objet.getConnection();
        query="SELECT t1.id,t1.timestamp,t2.titre FROM table_abus AS t1,table_annonces AS t2 WHERE t2.id=t1.id_annonce ORDER BY t1.timestamp DESC";
        state=Objet.getConn().createStatement();
        result=state.executeQuery(query);
        int nbAbus=0;
        while(result.next()) {
            nbAbus++;
        long idAbus=result.getLong("id");
        long timestamp=result.getLong("timestamp");
        String titreAnnonce=result.getString("titre");
        cal.setTimeInMillis(timestamp);
        %>
        <div class="cadre2">
            <a href="./abus-<%= idAbus %>.html" rel="nofollow"><%= titreAnnonce %></a>
            <span>&rarr;Abus signalé, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</span>
        </div>
        <br/>
        <%
        }
        result.close();
        state.close();
        Objet.closeConnection();
        if(nbAbus==0) { %>
        <br/>
        <div class="cadre2">
            <div class="info">Aucun abus signalé !</div>
        </div>
        <br/>
        <%
        }
                }catch(Exception ex) { %>
                <div class="erreur">
                    <div>Erreur :</div>
                    <br/>
                    <div><%= ex.getMessage() %></div>
                </div>
                <% } %>
        </div>
    </body>
</html>
