
package oslomet.webprog.oblig3kino;
//Lager en klasse for Film.
public class Film {
    private int FNr;
    private String tittel;

    //Lager konstruktør
    public Film(String tittel) {

        this.FNr = FNr;
        this.tittel = tittel;
    }

    //Lager GET/SET-metoder for å få tak i attributtene og sette verdier
    public Film() {
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;}

    public int getFNr() {
        return FNr;
    }

    public void setFNr(int FNr) {
        this.FNr = FNr;}

}
