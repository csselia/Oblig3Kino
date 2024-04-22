
package oslomet.webprog.oblig3kino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Setter dekoratør. Når en klasse merkes med RestController blir den oppdaget av Spring Boot og gjøres til et
//REST-endepunkt. Den håndterer HTTP-forespørsler.
@RestController //Controller er serversiden som kommuniserer med repository som igjen kommuniserer med database
public class BestillingController {
    @Autowired //Hente knytningen til databasen
    private BestillingRepository rep;
    //rep er en instans av BestillingRepository, et grensesnitt som inneholder funksjoner som tillater interaksjon med
    //en database. Dette gjør det mulig for BestillingController å bruke metodene definert i BestillingRepository for å utføre
    //operasjoner mot databasen.

    private Logger logger = LoggerFactory.getLogger(BestillingRepository.class);
    //Oppretter loggerinstans for å logge meldinger/feilmeldinger i koden

    /*Forteller Spring Boot at når et POST-kall blir sendt til lagre-endepunktet skal denne metoden håndtere
    forespørselen.
    Etter at alle inputene har blitt validert, skal det registreres. Deretter pakkes og sendes objektet til lagre endepunktet med Post kall.
    lagre-endepunktet tar inn billett-objektet og funksjonen lagreBestilling blir kalt med billett-objektet som parameter.
    I repository vil billettobjektets input-felt hentes som verdier til en ny rad i tabellen Bestilling i databasen.

    Håndtering av mislykket lagring til databasen:
    Metoden tar inn to parametre, bestilling-objektet og HTTPServletResponse-objektet som skal brukes som svar tilbake til
    klienten. Metoden validerer først bestillingen, og hvis den er vellykket blir bestillingen lagret ved å kalle på
    lagreBestilling-metoden på BestillingRepository-objektet rep.
    Hvis valideringen mislykkes sendes en HTTP feilrespons, feilmelding 500. (200 er for vellykket forresten).
    IOException:Tillater applikasjonen å melde om feil, og vi fanger feilen med en try catch blokk i BestillingRepository
     */

    @PostMapping("/lagre")
    public void lagreBestilling(Bestilling bestilling, HttpServletResponse response) throws IOException {
        if (validerBestillingOK(bestilling)) {
            rep.lagreBestilling(bestilling);
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil");
        }
    }


    //GET = Hent data
    //Forteller Spring Boot at når et GET-kall blir sendt til endepunktet hentAlle, skal dette utføres.
    @GetMapping("/hentAlle")
    //Bruker BestillingRepository-objektet rep til å hente bestillingene fra databasen og returnere en liste med Bestillings-objektene
    //Setter også IOException som skal håndtere hvis det oppstår feil under forsøket på å hente alle bestillingene fra databasen
    public List<Bestilling> hentAlle(HttpServletResponse response) throws IOException {
        List<Bestilling> alleBestillinger = rep.hentBillettRegister();
        if (alleBestillinger == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
        return alleBestillinger;
    }


    /*
    Forteller Spring Boot at når et Get-kall utføres på dette endepunktet skal dette utføres. Ønsker å hente data
    Tar inn 2 parametre, indentifikasjons int id på bestillingen, og httpServletResponse-objekt som skal gi
    tilbakemelding til klient.
    Legger til IOException for å håndtere input/output feil. Se i repository.
    Lager et objekt fra Bestilling klassen og setter den lik rep, instans av BestillingRepository som skal utføre en
    SQL setning der det hentes en bestilling med spesifikk id.

     */
    @GetMapping("/henteEnBestilling")
    public Bestilling henteEnBestilling (int id, HttpServletResponse response) throws IOException
    {   Bestilling enBestilling = rep.henteBestillingMedId(id);
        //Hvis bestillingen ikke blir funnet i repository går den til errorhandling og det sendes feilmelding til klient
        if(enBestilling == null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return enBestilling;
        //Dersom bestillingen ble funnet, returneres den fra metoden
    }



    /*
    Først valideres bestillingen med metoden validerBestillingOK før noe kan endres.
    Hvis den er gyldig går den videre der den sjekkes
    for om rep.endreBestilling er false som vil indikere at det har oppstått en feil under forsøket på å endre bestillingen
    i databasen.Sender en feilkode ut. Dersom valideringen ikke er gyldig sendes det også en feilmelding.
    IOException, applikasjonen tillater at feilmelding fanges og behandles i BestillingRepository gjennom try and catch
     */

    @PostMapping("/endre")
    public void endre(Bestilling bestilling, HttpServletResponse response) throws IOException{

        if(validerBestillingOK(bestilling)) {
            //Sjekker at valideringene er ok før det går inn i repository og kaller på endreBestilling-funksjonen.
            //Dersom det oppstår en feil under endring av dette i databasen, sendes en feilmelding til klient
            if (!rep.endreBestilling(bestilling)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
            }
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil");
        }
    }


/*slettEnBestilling tar inn 2 parametre, int id til det som skal slettes, og HttpServletResponse-objekt som brukes til å
    sende svar til klienten.
 */
    @GetMapping("/slettEnBestilling")
    public void slettEnBestilling(int id, HttpServletResponse response)throws IOException {
        if (!rep.slettEnBestillingMedId(id))
        //Betingelse som sjekker om slettingen av bestillingen var vellykket eller ikke, og sender feilmelding.
        {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
    }


    /*Forteller Spring Boot at når et GET-kall blir sendt til endepunktet slettAlle så skal dette utføres.
    Bruker BestillingRepository-objektet rep til å utføre en funksjon (som du finner i BestillingRepository) til å slette
    alle bestillingene fra databasetabellen
    Også her legger vil det undersøkes om slettingen av alle billettene var vellyket i databasen, og hvis ikke sendes en feilmelding.
     */
    @GetMapping("/slettAlle")
    public void slettAlle(HttpServletResponse response) throws IOException{
        if(!rep.slettBillettRegister()){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
    }

    /*Denne metoden kalles FØR en bestilling blir lagret eller endret, for å sikre at det ikke lagres feil ting i databasen
    Når det oppstår en valideringsfeil vil funksjonen vår som er en boolean returnere en false
    og en feilmelding sendes tilbake til klienten ved hjelp av reponse.sendError-metoden. Feilmeldingen inneholder
    HTTP-status og en valideringsfeilmelding. Det skjer en validering på serversiden, og klienten vil bli varslet om hvis
    det oppstår valideringsfeil før handlingen blir utført.
     */
    private boolean validerBestillingOK(Bestilling bestilling){
        //Validere felt i input
        String regexFilm = "[0-9a-zA-ZøæåØÆÅ. \\-]{2,50}";
        String regexAntall = "[1-9]";
        String regexFornavn = "[a-zæøåA-ZÆØÅ. \\-]{2,30}";
        String regexEtternavn = "[a-zæøåA-ZÆØÅ. \\-]{2,30}";
        String regexTelefonnummer = "\\d{8}";
        String regexEpost = "\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})";

        //Validering av hvert bestillingsfelt og sammenlikner det med regex-uttrykkene
        boolean filmOK = bestilling.getFilm().matches(regexFilm);
        boolean antallOK = bestilling.getAntall().matches(regexAntall);
        boolean fornavnOK = bestilling.getFornavn().matches(regexFornavn);
        boolean etternavnOK = bestilling.getEtternavn().matches(regexEtternavn);
        boolean telefonnummerOK = bestilling.getTelefonnummer().matches(regexTelefonnummer);
        boolean epostOK = bestilling.getEpost().matches(regexEpost);

        //Hvis alle feltene består valideringen, returneres true.
        if (filmOK && antallOK && fornavnOK && etternavnOK && telefonnummerOK && epostOK ){
            return true;
        } //Hvis ikke vil det logges en feilmelding
        logger.error("Valideringsfeil");
        return false;
    }
}


/*Index.html lenker til funksjoner på JavaScript, JavaScript snakker med Java gjennom Controller
Controller Java snakker med Repository, og det er i Repository interaksjonen med databasen skjer. Repository snakker med
schema.sql.
 */