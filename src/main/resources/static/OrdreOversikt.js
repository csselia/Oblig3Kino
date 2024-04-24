//Ready funksjon blir kjørt når nettsiden lastes inn.
$(function(){
    hentAlle();
    formaterData();
});

//Implementere hentAlle-funksjonen
function hentAlle(){
    //GET AJAX kall mot server. Access-punkt er /hentAlle. Sender ingen argumenter inn til server.
    //Kun funksjonen som kalles når serveren svarer som vi skal implementere.
    //Vi skal få et svar fra databasen, en liste med billetter
    //Listen skal sendes videre til en ny Javascriptfunksjon som heter formaterData
    $.get("/hentAlle", function (billetter){
        formaterData(billetter);
    });
}


//Vise billettene på siden. Header for hver type vi vil vise.
function formaterData(billetter){
    let ut="<table class='table table-striped'><tr><th>Valgt film</th><th>Antall</th><th>Fornavn</th><th>Etternavn</th><th>Telefonnummer</th><th>E-post</th><th></th><th></th></tr>";

//Itererer med en forløkke gjennom listen som heter billetter, som vi får inn som et argument.
    for(const billett of billetter){
        //legge til ny rad for hver registrert billett. Henter ut feltene fra input fra billettobjektet og skjøter ut på let ut.
        ut+= "<tr><td>" + billett.film + "</td><td>" + billett.antall + "</td><td>" + billett.fornavn + "</td><td>" + billett.etternavn + "</td><td>" + billett.telefonnummer + "</td><td>" + billett.epost + "</td>" +
            //Lager endre-og slette-knappene som skal dukke ved siden av billettene
            "<td> <button class='btn btn-primary' onclick='idTilEndring("+billett.id+")'>Endre</button></td>"+
            "<td> <button class='btn btn-danger' onclick='slettEnBestilling("+billett.id+")'>Slett</button></td>"+
            "</tr>";
    }
    ut+="</table>";
    //Skrive ut nye ut-strengen til div element billettene.
    $("#billettene").html(ut);}


function idTilEndring(id){
    window.location.href="/endring.html?"+id; //Denne nettsiden skal få inn id'en vi ønsker å endre
    //Og denne skal vi hente ut i endring.js
}


//Når slettEnBestilling trykkes på få vi inn en bestilling
function slettEnBestilling(id){
    const url = "/slettEnBestilling?id="+id;
    $.get(url, function(){
        window.location.href="/OrdreOversikt.html"
    });
}


//AJAX Get request til /slettAlle-endepunktet på serveren ved å bruke jQuery's Get metode. Hvis den er suksessfull,
//utføres funksjonen hentAlle. Så vi kaller på slettBillettenefunksjonen, serveren svarer med å kommunisere med databasen, utfører en
//sql setning for å slette alle radene i tabellen.
//Deretter kaller vi på hentAlle funksjonen for å oppdatere. hentAlle henter frem det oppdaterte billettregisteret.

function slettBillettene() {
    $.get( "/slettAlle", function() {
        window.location.href="/OrdreOversikt.html"
    });
}


