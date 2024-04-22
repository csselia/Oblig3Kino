function hentAlleFilmer(){
    //GET kall til endepunktet hentFilmer. Forespørselen håndteres på serversiden som kommer til å svare med å utføre en funksjon
    //som henter filmene fra databasen. FilmObjektene har samme attributter som Film.Java. Filmobjektene returneres i en liste.
    //og gjennomgår funksjonen formaterFilmer som skriver ut filmene i en dropdown-meny.
    $.get("/hentFilmer", function (filmer){
        formaterFilmer(filmer);
        console.log(filmer);
    });
}
//Denne funksjonen tar imot filmer som parameter.
function formaterFilmer(filmer){
    //Bygge dropdown-menyen som så skal settes inn i div #Film i index.html
    let ut ="<select id='valgtFilm'>"
    let forrigeFilm = "";
    ut+="<option>Velg film</option>";
    //Første alternativ i dropdown-menyen bare så brukeren vet. De andre inputfeltene har title så når man legger
    //musa over så kommer det en melding som gir brukeren en pekepinn på hva man skal skrive i inputfeltene

    //Forløkke som itererer gjennom listen av filmer. Inni løkken sjekker vi om filmnavnet er det samme som forrige
    //film slik at samme film ikke legges til flere ganger i dropdown-menyen
    for(const film of filmer){
        if(film.tittel != forrigeFilm){
            ut+="<option>" + film.tittel + "</option>"; //Skriver ut filmene som option i dropdown-meny
        }
        forrigeFilm = film.tittel; //variabelen forrigeFilm oppdateres med gjeldende film slik at det kan
        //sammenliknes med neste film i løkken.
    }
    ut+= "</select>"; //fullføre dropdown-menyen
    $("#Film").html(ut); //Setter HTML-innholdet til elementet med id Film til ut-variabelen med JQuery.
    console.log(ut);
}
