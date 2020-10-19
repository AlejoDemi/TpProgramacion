import java.time.LocalDate;


public class Encuentro {

    String cuil;
    String cuilCont;
    LocalDate fecha;

    public Encuentro(String cuil, String cuilCont, LocalDate fecha) {
        this.cuil = cuil;
        this.cuilCont = cuilCont;
        this.fecha = fecha;
    }

    public void send() {
        String toAdd = this.cuil + "/" + this.cuilCont + "/" + this.fecha + "\n";
        Anses.añadir("src\\Notificaciones", toAdd);
    }

}