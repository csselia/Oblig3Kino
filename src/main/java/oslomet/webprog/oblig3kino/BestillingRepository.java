package oslomet.webprog.oblig3kino;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


//Markerer at det er et repository ved å skrive:
@Repository //laget som kommuniserer med databasen
public class BestillingRepository {
    //Hente knytningen til databasen ved å skrive Autowired
    @Autowired
    private JdbcTemplate db;
    //Dette betyr at objektet db er mulig å bruke den til å få tilgang til databasen.
    //Det er ikke mulig å skrive new på denne klassen, men Spring gjør det allerede slik at objektet db er
    //tilgjengelig for oss i denne klassen

    private Logger logger = LoggerFactory.getLogger(Bestilling.class);
    //Oppretter loggerinstans for å logge meldinger/feilmeldinger i koden

    /*Funksjon for å lagre en bestilling. Parameter: forventer et objekt av klassen Bestilling.
    Vi lager en streng kalt sql og setter den lik sql utspørringen:
    Insert antyder til at vi skal legge inn data i en tabell kalt Bestilling.
     ? er plassholder for parametere av inputs.
    db er aksessobjektet vårt, er den som skal gi oss tilgang til databasen.
    Parameterne som blir sendt til update er attributtene film, antall, fornavn, etternavn, telefonnummer og e-post, .
    Dette hentes fra innBestillings-objektet med getter-metoder, vi henter inputene.
    update-metoden utfører SQL spørringen mot databasen, og erstatter plassholderne ? med inputverdiene fra innBestilings-objektet.
     */
    public boolean lagreBestilling(Bestilling innBestilling) {
        String sql = "INSERT INTO Bestilling(film, antall, fornavn, etternavn, telefonnummer, epost) VALUES(?, ?, ?, ?, ?, ?)";

        try {
            db.update(sql, innBestilling.getFilm(), innBestilling.getAntall(), innBestilling.getFornavn(), innBestilling.getEtternavn(), innBestilling.getTelefonnummer(), innBestilling.getEpost());
            return true;
        } catch (Exception e) {
            logger.error("Feil i lagre bestilling " + e);
            return false;
        }
    }
    //Når update metoden oppdaterer og lykkes vil metoden returnere true.
    //Hvis ikke, fanges den opp av en catch blokk som fanger exceptions, returnerer false og feilmelding til klient.


    /*Funksjon for å hente billett-registeret. Funksjonen vil returnere en liste av Bestillings-objekter. De har ingen parametre.
     SQL-spørring for å velge alle rader fra tabellen Bestilling og sortere det på etternavn. Spørringen lagres i en streng kalt sql.
    liste av Bestilling kalt billettRegister fylles opp med objekter hentet fra databasen ved hjelp av BeanPropertyRowMapper.
    BeanPropertyRowMapper er en klasse som tar radene med data fra en database og omdanner det til Java-objekter.
    Vi har en klasse som heter Bestilling som representerer en kino-billett-bestilling. BPRM tar hver rad fra resultatsettet og
    gjør den om til et bestillings-objekt.
    BRPM ser på resultatsettet fra databasen og sammenlikner det med attributtene i Bestilling-klassen, og når den finner samsvar
    eks. Film = Film, antall = antall, så setter den verdien fra kolonnen inn i den tilsvarende egenskapen i Bestillings-objektet.
    Disse bestillings-objektene legges deretter i billett-register-listen som blir returnert av denne funksjonen.

    //Kolonnene i tabellen heter det samme som attributtene i Bestilling. Vær derfor nøye med at det er likt.
     */
    public List<Bestilling> hentBillettRegister(){
        try{
            String sql = "SELECT * FROM Bestilling ORDER BY etternavn";
            List<Bestilling> billettRegister = db.query(sql, new BeanPropertyRowMapper(Bestilling.class));
            return billettRegister;
        }
        catch(Exception e){
            logger.error("Feil i hent billettregister " +e);
            return null;
        }
    }
    //Feilmelding logges hvis den ikke greier å hente billettregisteret.


    //Mulighet for å hente en billett
    public Bestilling henteBestillingMedId(int id){
        String sql = "SELECT * FROM Bestilling WHERE id=?"; //hvor id er det som sendes inn som argument til funksjonen
        try{ //Mapper radene fra resultatsettet til objekter av klassen Bestilling (sammenlikner med attributtene der).
            List<Bestilling> enBestilling = db.query(sql, new BeanPropertyRowMapper<>(Bestilling.class), id);
            return enBestilling.get(0);//Tar høyde for at vi kan få mer enn en rad som svar fra databasen, skriver 0 slik at vi bare referer til den første raden
        }
        catch(Exception e){
            logger.error("Feil i hent bestilling med id " + e);
            return null;
            //Bestillingen ble ikke funnet og den returnerer null
        }
    }




/*Try: Prøver å oppdatere databasen med db.update-metoden. Metoden utfører SQL setningen med de angitte parameterne.
    Hvis oppdatering er vellykket returnes true.
    Hvis oppdatering feiler vil koden hoppe over til catch blokken og feilen blir fanget og logget som en feilmelding.
 */
    public boolean endreBestilling(Bestilling bestilling){
        String sql = "UPDATE Bestilling SET Film=?, antall=?, fornavn=?, etternavn=?, telefonnummer=?, epost=? WHERE id=?";

       try{ db.update(sql, bestilling.getFilm(), bestilling.getAntall(), bestilling.getFornavn(),
               bestilling.getEtternavn(), bestilling.getTelefonnummer(), bestilling.getEpost(),bestilling.getId());
               /*Der id'en er det samme som på det objektet vi får inn i som argument i funksjonen. Det er spesifikt den id'en
                 knyttet til den billetten som skal endres på.
         /*
           Kjøre oppdateringen.
           //Hente ut alle verdiene fra BillettOrdre-objektet som vi får inn.
           */
           return true; //Hvis endrebestilling er vellyket, returnere true, hvis ikke feilmelding til klient og returnere false
       }
        catch(Exception e){
           logger.error("Feil i endre en bestilling " + e);
           return false;
        }
    }

    //Metode for å slette en spesifikk bestilling basert på ID. Innfører også her try and catch for feilhåndtering.
    public boolean slettEnBestillingMedId(int id) {
        String sql = "DELETE FROM Bestilling WHERE id=?";

        try{
            db.update(sql, id);
            return true;
        }
        catch(Exception e){
            logger.error("Feil i slett en bestilling " + e);
            return false;
        }
    }



    //SQL-spørring blir definert for å slette alle radene i tabellen Bestilling.
    //db-objektet gir oss tilgang til databasen og db har med seg en update-metode og sql forespørsen blir utført.
    public boolean slettBillettRegister(){
        String sql = "DELETE FROM Bestilling";
        //Utfører en try and catch, hvis sql setningen utføres oppdateres databasen og den returnerer true
        try{
            db.update(sql);
            return true;
        }
        catch (Exception e){ //Fanger opp feilen og logger feilmelding
            logger.error("Feil i slett billettregister " + e);
            return false;
        }
    }
}

