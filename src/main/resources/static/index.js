//Ready funksjon blir kjørt når nettsiden lastes inn, for å få filmene med engang i dropdown-menyen.
$(function(){
    hentAlleFilmer();
});


//Lager et objekt for som inneholder inputfelt-verdiene (POJO)
function registrer() {
    const billettOrdre =
        {
            film: $("#valgtFilm").val(),
            antall: $("#antall").val(),
            fornavn: $("#fornavn").val(),
            etternavn: $("#etternavn").val(),
            telefonnummer: $("#telefonnummer").val(),
            epost: $("#epost").val(),
        }

    if (validerBillettOrdre()) {
        //lagre er punkt på server vi skal sende til, billettOrdre er objekt vi sender, function som utføres når vi får svar fra server
        //Vi skal sende dette objektet til server. Vi bruker POST AJAX-kall. POST fordi vi skal sende info til databasen. GET når man skal få data fra databasen.
        $.post("/lagre", billettOrdre, function () {
            hentAlle();
        });
        window.location.href = 'OrdreOversikt.html'; //Redirigerer brukeren til ny side med ordreoversikten
    }
}
