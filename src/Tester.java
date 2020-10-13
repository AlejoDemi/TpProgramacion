import java.time.LocalDate;
import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) {

        Pantalla.clean();
        ArrayList<Ciudadano>ciudadanos=Anses.lista();

        while(true){

            int registro=Pantalla.registro();
            String cuil;
            String cel;

            if(registro==1){
                String dato=Pantalla.nuevo();
                Anses.nuevoCiu(dato,ciudadanos);
                ciudadanos=Anses.lista();
                 cuil=dato.substring(0,11);
                 cel = dato.substring(11, dato.length());
            }else {
                String datos = Pantalla.inicio();
                 cuil = datos.substring(0, 11);
                 cel = datos.substring(11, datos.length());
            }

            int ciudadanonum = Ciudadano.checkData(cuil, cel, ciudadanos);
            if (ciudadanonum == 1) {
                int decision = Pantalla.Ciudadano();

                while (decision != 5) {
                    switch (decision) {
                        case 1:
                            String contCuil = Pantalla.contactoCuil();
                            LocalDate fecha = Pantalla.ContactoFecha();
                            Encuentro.send(cuil,contCuil,fecha);
                            decision = Pantalla.Ciudadano();
                            break;
                        case 2:
                            int nuevoSin = Pantalla.sintomaSI();
                            Anses.afectado(cuil,nuevoSin);
                            decision = Pantalla.Ciudadano();
                            break;
                        case 3:
                            int viejoSin = Pantalla.sintomaNO();
                            Anses.desafectado(cuil,viejoSin);
                            decision = Pantalla.Ciudadano();
                            break;
                        case 4:
                            Encuentro.check(cuil);
                            decision = Pantalla.Ciudadano();
                            break;
                        default:
                            decision = Pantalla.Ciudadano();
                            break;
                    }
                }
            } else if (ciudadanonum == 2) {
                int decision = Pantalla.pantallaAdmin();

                while (decision != 6) {
                    switch (decision) {
                        case 1:
                            String blockCiu = Pantalla.block();
                            Ciudadano.bloquear(blockCiu, ciudadanos);
                            decision = Pantalla.pantallaAdmin();
                            break;
                        case 2:
                            String desblockCiu = Pantalla.desblock();
                            Ciudadano.desbloquear(desblockCiu,ciudadanos);
                            decision = Pantalla.pantallaAdmin();
                            break;
                        case 3:
                            String sintomNuevo = Pantalla.newSintoma();
                            Ciudadano.addSintom(sintomNuevo);
                            decision = Pantalla.pantallaAdmin();
                            break;
                        case 4:
                            int sacarSintoma = Pantalla.removeSintoma();
                            Ciudadano.removeSintom(sacarSintoma);
                            decision = Pantalla.pantallaAdmin();
                            break;
                        case 5:
                            System.out.println("Datos");
                            decision = Pantalla.pantallaAdmin();
                            break;
                        default:
                            decision = Pantalla.Ciudadano();
                    }
                }
            }

        }
    }

}
