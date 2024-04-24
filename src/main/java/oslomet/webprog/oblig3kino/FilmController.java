package oslomet.webprog.oblig3kino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
public class FilmController {
    @Autowired
    private FilmRepository rep;

    /*Vi trenger en måte å hente filmregisteret fra server. Så vi trenger en ny mapping og den skal returnere
    en liste med film-objekter.
    GetMapping - annotasjon som forteller Spring Boot at når en slik Get-forespørsel blir sendt til hentFilmer,
    Skal denne metoden utføres. Forventer å få en liste av film-objekter tilbake.

    Legger også inn IOException for å håndtere feil i input/output så klienten får tilbakemelding når
    metoden for å hentefilmer fra databasen mislykkes.
     */
    @GetMapping("/hentFilmer")
    public List<Film> hentFilmer(HttpServletResponse response) throws IOException {
        List<Film> alleFilmer = rep.hentFilmerFraDatabase();
        if(alleFilmer == null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
        return alleFilmer;
    }
}