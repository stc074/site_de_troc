<%@page import="java.util.Calendar"%>
<%@page import="classes.CGU"%>
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
            <h1>Administration - Éditer les cgu</h1>
            <%
            try {
                if(request.getAttribute("info")!=null) {
                    int info=Integer.parseInt(request.getAttribute("info").toString());
                    switch(info) {
                        case 1: %>
                        <br/>
                        <div class="cadre2">
                            <br/>
                            <div class="info">Infos inconnues !</div>
                            <br/>
                        </div>
                        <br/>
                        <%
                        break;
                        }
                    } else if(request.getAttribute("cgu")!=null) {
                        CGU cgu=(CGU)request.getAttribute("cgu");
                        Calendar cal=Calendar.getInstance();
                        cal.setTimeInMillis(cgu.getTimestamp());
                        %>
                        <br/>
                        <div class="info">Dernières modification, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</div>
                        <br/>
                        <%
                        if(cgu.getTest()==1) { %>
                        <div class="info">Modifications enregistrées !</div>
                        <br/>
                        <% } %>
                        <div id="form">
                            <%
                            if(request.getParameter("kermit")!=null&&cgu.getErrorMsg().length()>0) { %>
                            <div class="erreur">
                                <div>Erreur(s) :</div>
                                <br/>
                                <div><%= cgu.getErrorMsg() %></div>
                            </div>
                            <% } %>
                            <fieldset>
                                <legend>Texte CGU</legend>
                                <form action="./edit-cgu.html#form" method="POST">
                                    <div>Texte des cgu :</div>
                                    <textarea name="texte" id="texte" rows="4" cols="20"><%= cgu.getTexte()%></textarea>
                                    <br/>
                                    <input type="submit" value="Valider" name="kermit" />
                                </form>
                            </fieldset>
                            <script type="text/javascript">
                                    CKEDITOR.replace( 'texte' );
                            </script>
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
