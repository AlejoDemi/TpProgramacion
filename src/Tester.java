import java.time.LocalDate;
import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) {

        Pantalla.clean();
        ArrayList<Ciudadano>ciudadanos=Anses.listaCiudadanos();

        while(true){

            int registro=Pantalla.registro();
            String cuil;
            String cel;

            if(registro==1){
                String dato=Pantalla.nuevo();
                Anses.nuevoCiu(dato,ciudadanos);
                ciudadanos=Anses.listaCiudadanos();
                 cuil=dato.substring(0,11);
                 cel = dato.substring(11);
            }else {
                String datos = Pantalla.inicio();
                 cuil = datos.substring(0, 11);
                 cel = datos.substring(11);
            }

            int ciudadanonum = Ciudadano.checkData(cuil, cel, ciudadanos);
            if (ciudadanonum == 1) {
                int decision = Pantalla.Ciudadano();

                while (decision != 5) {
                    switch (decision) {
                        case 1:
                            String contCuil = Pantalla.contactoCuil();
                            LocalDate fecha = Pantalla.ContactoFecha();
                            Encuentro encuentro=new Encuentro(cuil,contCuil,fecha);
                            encuentro.send();
                            decision = Pantalla.Ciudadano();
                            break;
                        case 2:
                            int nuevoSin = Pantalla.sintomaSI();
                            Covid19 paciente=new Covid19(cuil,nuevoSin);
                            paciente.afectado();
                            decision = Pantalla.Ciudadano();
                            break;
                        case 3:
                            int viejoSin = Pantalla.sintomaNO();
                            Covid19 paciente1=new Covid19(cuil,viejoSin);
                            paciente1.desafectado();
                            decision = Pantalla.Ciudadano();
                            break;
                        case 4:
                            Contacto contacto=new Contacto(cuil);
                            contacto.checkContact();
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

