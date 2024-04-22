package oslomet.webprog.oblig3kino;

//Feltene her må matche Javascript objektet som ble lagd som het billettOrdre.
//Denne klassen Bestilling representerer strukturmodellen til bestillingsobjektene som sendes til og fra serveren.
public class Bestilling {
    private int id;
    private String Film;
    private String antall;
    private String fornavn;
    private String etternavn;
    private String telefonnummer;
    private String epost;

    //Lager en konstruktør slik at hver gang det opprettes en ny instans av klassen Bestilling
    //initialiseres attributtene med engang
    public Bestilling(int id, String antall, String fornavn, String etternavn, String telefonnummer, String epost){
        this.id = id;
        this.antall = antall;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.telefonnummer = telefonnummer;
        this.epost = epost;
    }

    //Tom konstruktør slik at Spring Boot kan opprette ny instanser av klassen uten spesifikke parametre.
    public Bestilling(){}

    //GET/SET-metoder for alle feltene i denne klassen for å gå tilgang til attributtene til et objekt og for å sette verdier


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilm(){ return Film; }
    public void setFilm(String Film) { this.Film = Film; }

    public String getAntall(){ return antall; }
    public void setAntall(String antall) { this.antall = antall; }


    public String getFornavn(){ return fornavn; }
    public void setFornavn(String fornavn) { this.fornavn = fornavn; }


    public String getEtternavn(){ return etternavn; }
    public void setEtternavn(String etternavn) { this.etternavn = etternavn; }


    public String getTelefonnummer(){ return telefonnummer; }
    public void setTelefonnummer(String telefonnummer) { this.telefonnummer = telefonnummer; }


    public String getEpost(){ return epost; }
    public void setEpost(String epost) { this.epost = epost; }

    //Nå skal det skrives en controller for denne løsningen, se BestillingController
}
