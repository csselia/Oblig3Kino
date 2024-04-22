package oslomet.webprog.oblig3kino;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


//Markerer at det er et repository ved å skrive:
@Repository
public class FilmRepository {
    //Hente knytningen til databasen ved å skrive Autowired
    @Autowired
    private JdbcTemplate dbFilm;
    private Logger logger = LoggerFactory.getLogger(Bestilling.class);
    //Oppretter loggerinstans for å logge meldinger/feilmeldinger i koden

    public List<Film> hentFilmerFraDatabase() {
        String filmSQL = "SELECT Tittel FROM Filmer";
        /*dbFilm er objekt av typen JdbcTemplate som er en del av Spring Framework. Den tar inn
        to argumenter, SQL spørringen og RowMapper.
        BeanProperty er en RowMapper som mapper kolonnenavnene i resultatsettet til JAVA-objektene.
        Film.class forteller BeanPropertyRowMapper hvilken type objekt som forventes, slik at den kan
        opprette instanser av Film-klassen og sette attributtene (se i Film.java) basert på kolonnene i resultatsettet
         */
        try{
            List<Film> FilmListe = dbFilm.query(filmSQL, new BeanPropertyRowMapper(Film.class));
            return FilmListe;
        }
        catch(Exception e){
            logger.error("Feil i hent filmer fra database " + e);
            return null;
        } //Legger in feilhåndtering så klienten får svar hvis metoden for å hente filmer fra databasen mislykkes.
    }
}