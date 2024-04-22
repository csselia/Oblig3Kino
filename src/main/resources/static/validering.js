
//Funksjon tar imot etternavn som parameter.
//Sjekker at etternavnet er innenfor kravene:
//bokstaver fra a til z , inkludert æ,ø,å i både små og store bokstaver.
//Lengden på etternavnet må være mellom 2 og 30 tegn.
function validerEtternavn(etternavn){
    const regexp = /^[a-zæøåA-ZÆØÅ. \-]{2,30}$/;
    const ok = regexp.test(etternavn); //Tester mot etternavn
    //Hvis etternavnet ikke er ok, så vil det vises en feilmelding.
    if(!ok){
        $("#feilEtternavn").html("Etternavn må bestå av 2 til 20 bokstaver");
        return false;
    }
    //betyr  gyldig i henhold til det regulære uttrykket. Feilmeldingen slettes.
    else{
        $("#feilEtternavn").html("");
        return true;
    }
}

function validerFornavn(fornavn){
    const regexp = /^[a-zæøåA-ZÆØÅ. \-]{2,30}$/;
    const ok = regexp.test(fornavn); //Tester mot fornavn.
    //Hvis fornavnet ikke er ok, så vil det vises en feilmelding.
    if(!ok){
        $("#feilFornavn").html("Fornavn må bestå av 2 til 20 bokstaver");
        return false;
    }
    //betyr  gyldig i henhold til det regulære uttrykket. Feilmeldingen slettes.
    else{
        $("#feilFornavn").html("");
        return true;
    }
}

function validerAntall(antall){
    const regexp =  /^[1-9]+$/;
    const ok = regexp.test(antall); //Tester mot antall
    //Hvis antallet ikke er ok, så vil det vises en feilmelding.
    if(!ok){
        $("#feilAntall").html("Tast inn et gyldig antall");
        return false;
    }
    //betyr  gyldig i henhold til det regulære uttrykket. Feilmeldingen slettes.
    else{
        $("#feilAntall").html("");
        return true;
    }
}


function validerTlf(telefonnummer){
    const regexp = /^\d{8}$/;
    const ok = regexp.test(telefonnummer); //Tester mot telefonnummer
    //Hvis telefonnummeret ikke er ok, så vil det vises en feilmelding.
    if(!ok){
        $("#feilTlf").html("Tast inn et gyldig telefonnummer");
        return false;
    }
    //betyr  gyldig i henhold til det regulære uttrykket. Feilmeldingen slettes.
    else{
        $("#feilTlf").html("");
        return true;
    }
}

function validerEpost(epost){
    const regexp = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
    const ok = regexp.test(epost); //Tester mot epost
    //Hvis epost ikke er ok, så vil det vises en feilmelding.
    if(!ok){
        $("#feilEpost").html("Tast inn en gyldig E-postadresse");
        return false;
    }
    //betyr  gyldig i henhold til det regulære uttrykket. Feilmeldingen slettes.
    else{
        $("#feilEpost").html("");
        return true;
    }
}

function validerFilm() {
    //Henter tekst fra det valgte alternativet i dropdown menyen og setter den lik variabelen valgtFilm.
    //Utfører deretter validering på valgtFilm.
    const valgtFilm = $("#Film option:selected").text();

        if (valgtFilm === "Velg film") {
        $("#feilFilm").html("Du har ikke valgt film");
        return false;
    } else {
        $("#feilFilm").html("");
        return true;
    }
}


//Lager en funksjon som henter innverdiene og sjekker at det kun er når inputverdiene (har gjennomgått
//valideringsfunksjonene) at informasjonen kan registreres.
function validerBillettOrdre(){
    const fornavnOK = validerFornavn($("#fornavn").val());
    const etternavnOK = validerEtternavn($("#etternavn").val());
    const antallOK = validerAntall($("#antall").val());
    const tlfOK = validerTlf($("#telefonnummer").val());
    const epostOk = validerEpost($("#epost").val());
    const filmok = validerFilm($("#Film").val());

    const alleFelterErFyltUtRiktig = fornavnOK && etternavnOK && antallOK && tlfOK && epostOk && filmok;

    if(alleFelterErFyltUtRiktig)
    {
        $("#feilmelding").html(""); // Fjern feilmeldingen
        return true; //Alle felter er fylt ut riktig
    }
    else {
        $("#feilmelding").html("Alle felter må fylles ut!").css("color", "red");
        return false; //Alle felter er ikke fylt ut
    }
}
