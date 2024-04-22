$(function(){
    henteEnBestilling();
    hentAlleFilmer();
});

//Få tak i billetten så feltene er fylt ut med det vi valgte i inputs da vi bestilte billetten.
function henteEnBestilling(){
    //id'en skal lagres i en const
    const id = window.location.search.substring(1); //Da får vi med alt fra og med spørsmålstegnet.
    //Vi skriver 1 der for å kun få tallet, altså id'en
    const url = "/henteEnBestilling?id=" + id;
    //henteEnBestilling defineres på serversiden, og vi skal sende inn id'en inn.

    $.get(url,function(enBestilling){   //Vi skal få enBestilling tilbake, og fra den skal vi fylle ut alle feltene i endring.html, inkludert skjult id.
        $("#id").val(enBestilling.id);
        $("#Film").val(enBestilling.Film);
        $("#antall").val(enBestilling.antall);
        $("#fornavn").val(enBestilling.fornavn);
        $("#etternavn").val(enBestilling.etternavn);
        $("#telefonnummer").val(enBestilling.telefonnummer);
        $("#epost").val(enBestilling.epost);
    });
}


//Lager et objekt for som inneholder inputfelt-verdiene (POJO)
function endreBillett() {
    const billettOrdre =
        {
            id: $("#id").val(),
            Film: $("#valgtFilm").val(),
            antall: $("#antall").val(),
            fornavn: $("#fornavn").val(),
            etternavn: $("#etternavn").val(),
            telefonnummer: $("#telefonnummer").val(),
            epost: $("#epost").val(),
        };

    if (validerBillettOrdre()) {
        //endre er punkt på server vi skal sende til, billettOrdre er objekt vi sender, function når vi får svar fra server
        //Vi skal sende dette objektet til server. Vi bruker POST AJAX-kall.
        $.post("/endre", billettOrdre, function () {
            window.location.href = "/OrdreOversikt.html";
        });
        //Tidligere i oblig 2, resettet vi skjemaet, men nå sendes brukeren bare tilbake til ordreoversikt-siden,
        //så skjemaet tømmes "automatisk".
    }
}
