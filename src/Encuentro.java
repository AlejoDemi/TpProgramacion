import java.time.LocalDate;
import java.util.ArrayList;

public class Encuentro {

    public static void send(String cuil, String cuilCont, LocalDate fecha) {
        Anses.notificar(fecha, cuil, cuilCont);
    }

    public static void check(String cuilCont) {
        ArrayList<String> notificaciones = Anses.checkNot(cuilCont);
        for (int i = 0; i < notificaciones.size(); i++) {
            String cuil2=notificaciones.get(i).substring(0,11);
            String fecha=notificaciones.get(i).substring(24);
            int decision=Scanner.getInt("Tuvo usted contacto con "+Ciudadano.getName(cuil2)+" de CUIL "+cuil2+
                    " en la fecha: " +fecha+"?\n1-Si\n2-No\n");
            Pantalla.clean();
            if(decision==1){
                Anses.contact(cuilCont,cuil2);
                Anses.checkContagio(cuilCont,cuil2);
            }else{
                Anses.rechazo(cuil2,cuilCont);
            }
        }

    }
}
