function signalerAbus(idAnnonce) {
    option=0;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./signaler-abus-"+idAnnonce+".html");
    }
}
function changeCategorie(idCategorie) {
    option=3;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./change-categorie-"+idCategorie+".html");
    }
}
function changeCategorieContre(idCategorieContre) {
    option=4;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./change-categorie-contre-"+idCategorieContre+".html");
    }
}
function changeRegion(idRegion) {
    option=1;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./change-region-"+idRegion+".html");
    }
}
function changeDepartement(idDepartement) {
    option=2;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./change-departement-"+idDepartement+".html");
    }
}
function sendReq(xhr, file) {
    xhr.onreadystatechange=traiteReponse;
    xhr.open("GET", file, true);
    xhr.send(null);
}
function traiteReponse() {
    if(xhr.readyState==4&&(xhr.status==200||xhr.status==0)) {
        switch(option) {
            case 0:
            alert(xhr.responseText);
            break;
            case 1:
                document.getElementById("innerDepartements").innerHTML=xhr.responseText;
                break;
            case 2:
                document.getElementById("innerCommunes").innerHTML=xhr.responseText;
                break;
            case 3:
                document.getElementById("innerSousCategories").innerHTML=xhr.responseText;
                break;
            case 4:
                document.getElementById("innerSousCategoriesContre").innerHTML=xhr.responseText;
                break;
        }
    }
}
function getXMLHttpRequest() {
    var xhr=null;
    if(window.XMLHttpRequest||window.ActiveXObject) {
        if(window.ActiveXObject) {
            try {
                xhr=new ActiveXObject("Msxml2.XMLHTTP");
            } catch(e) {
                xhr=new ActibeXObject("Microsoft.XMLHTTP");
            }
        } else {
            xhr=new XMLHttpRequest();
        }
    } else {
        return null;
    }
    return xhr;
}
