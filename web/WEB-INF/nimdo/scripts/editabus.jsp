<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.Objet"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="classes.CGU"%>
<%!
private Long idAbus;
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
            <h1>Administration - Abus</h1>
            <%
            try {
                if(request.getAttribute("info")!=null) {
                    int info=Integer.parseInt(request.getAttribute("info").toString());
                    switch(info) {
                        case 1: %>
                        <br/>
                        <div class="cadre2">
                            <div class="info">Abus inconnu !</div>
                        </div>
                        <br/>
                        <%
                        break;
                        }
                    } else if(request.getAttribute("idAbus")!=null) {
                        this.idAbus=Long.parseLong(request.getParameter("idAbus").toString());
                        Objet.getConnection();
                        String query="SELECT t1.timestamp,t2.titre,t2.contenu FROM table_abus AS t1,table_annonces AS t2 WHERE t1.id=? AND t2.id=t1.id_annonce LIMIT 0,1";
                        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                        prepare.setLong(1, this.idAbus);
                        ResultSet result=prepare.executeQuery();
                        result.next();
                        long timestamp=result.getLong("timestamp");
                        String titreAnnonce=result.getString("titre");
                        String contenuAnnonce=result.getString("contenu");
                        Calendar cal=Calendar.getInstance();
                        cal.setTimeInMillis(timestamp);
                        %>
                        <div class="cadre2">
                            <div>
                                <a href="./ignore-abus-<%= this.idAbus %>.html" title="IGNORER CET ABUS" rel="nofollow">IGNORER CET ABUS</a>
                            </div>
                            <div>
                                <a href="./efface-abus-<%= this.idAbus %>.html" title="ÉFFACER CETTE ANNONCE" rel="nofollow">ÉFFACER CETTE ANNONCE</a>
                            </div>
                            <br/>
                            <h2><%= titreAnnonce %></h2>
                            <div class="info">Abus signalé le, <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</div>
                            <br/>
                            <%= contenuAnnonce %>
                        </div>
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
