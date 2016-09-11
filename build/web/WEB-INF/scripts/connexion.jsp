<%@page import="classes.Membre"%>
<header>
<%
try {
    if(request.getAttribute("mbr")!=null) {
        Membre mbr=(Membre)request.getAttribute("mbr");
    %>
<div class="connexion">
    <div id="formCnx">
        <%
        if(request.getParameter("kermitCnx")!=null&&mbr.getErrorMsg().length()>0) { %>
        <div class="erreur">
            <div>Erreur(s)</div>
            <br/>
            <div><%= mbr.getErrorMsg() %></div>
        </div>
        <% } %>
        <form action="#formCnx" method="POST">
    <div>Votre PSEUDO :</div>
    <input type="text" name="pseudo" value="<%= mbr.getPseudo()%>" size="15" maxlength="20" />
    <div>Votre MOT DE PASSE :</div>
    <input type="password" name="motDePasse" value="" size="15" maxlength="15" />
    <br/>
    <input type="submit" value="Connexion" name="kermitCnx" />
        </form>
    </div>
    <div><a href="./mdp-oublie.html" title="MOT DE PASSE OUBLIÉ" rel="nofollow">MOT DE PASSE OUBLIÉ</a></div>
    <%
    if(mbr.getId()!=0) { %>
    <div>Membre connecté &rarr; <%= mbr.getPseudo2() %></div>
    <%
    } else { %>
    <div>Statut &rarr; déconnecté</div>
    <% } %>
</div>
    <div class="logo" onclick="javascript:window.location.href='<%= Datas.URLROOT %>';"></div>
<%
}
} catch(Exception ex) { %>
<div class="erreur">
    <div>Erreur :</div>
    <br/>
    <div><%= ex.getMessage() %></div>
</div>
<% } %>
</header>