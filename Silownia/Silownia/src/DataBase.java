import java.awt.*;
import java.util.Date;

public class DataBase {
    int id;
    private String imie, nazwisko, numer, email, haslo;
    Date data;

    public DataBase(int ID, String imie, String nazwisko, String numer, String email, String haslo, Date data) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numer = numer;
        this.email = email;
        this.id = ID;
        this.haslo = haslo;
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", numer='" + numer + '\'' +
                ", email='" + email + '\'' +
                ", haslo='" + haslo + '\'' +
                ", Data ważności karnetu='" + data + '\'' +
                '}';
    }
}
