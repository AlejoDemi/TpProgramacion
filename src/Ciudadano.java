import java.util.ArrayList;

public class Ciudadano {

    String nombre;
    String cuil;
    String cel;
    boolean admin;
    boolean block;
    String ubicacion;

    public Ciudadano(String nombre, String cuil, String cel, boolean admin, boolean block,String ubicacion) {
        this.nombre = nombre;
        this.cuil = cuil;
        this.cel = cel;
        this.admin = admin;
        this.block = block;
        this.ubicacion=ubicacion;
    }

    public static int checkData(String cuilnum, String celnum, ArrayList<Ciudadano> ciudadanos) {
        for (int i = 0; i < ciudadanos.size(); i++) {
            if ((ciudadanos.get(i)).cuil.equals(cuilnum) && (ciudadanos.get(i)).cel.equals(celnum)) {
                if (ciudadanos.get(i).admin) {
                    System.out.println("Bienvenido/a," + ciudadanos.get(i).nombre);
                    int decision = Pantalla.inicioAdmin();
                    if (decision == 1) {
                        return 2;
                    } else {
                        return 1;
                    }
                } else if (ciudadanos.get(i).block) {
                    System.out.println("Usted se encuentra temporalmente bloqueado/a");
                    return -1;
                } else {
                    System.out.println("Bienvenido," + ciudadanos.get(i).nombre);
                    return 1;
                }
            }
        }
        return -1;

    }

    public static void bloquear(String dato, ArrayList<Ciudadano> ciudadanos) {
        if (dato.length() == 11) {
            for (int i = 0; i < ciudadanos.size(); i++) {
                if ((ciudadanos.get(i)).cuil.equals(dato)) {
                    if (!ciudadanos.get(i).admin) {
                        ciudadanos.get(i).block=true;
                    }
                }
            }
            Anses.save(ciudadanos);
        } else if (dato.length() == 10) {
            for (int i = 0; i < ciudadanos.size(); i++) {
                if ((ciudadanos.get(i)).cel.equals(dato)) {
                    if (!ciudadanos.get(i).admin) {
                        ciudadanos.get(i).block=true;
                    }
                }
            }
            Anses.save(ciudadanos);
        }
    }

    public static void desbloquear(String dato, ArrayList<Ciudadano>ciudadanos){
        if (dato.length() == 11) {
            for (int i = 0; i < ciudadanos.size(); i++) {
                if ((ciudadanos.get(i)).cuil.equals(dato)) {
                    if (!ciudadanos.get(i).admin) {
                        ciudadanos.get(i).block=false;
                    }
                }
            }
            Anses.save(ciudadanos);

        } else if (dato.length() == 10) {
            for (int i = 0; i < ciudadanos.size(); i++) {
                if ((ciudadanos.get(i)).cel.equals(dato)) {
                    if (!ciudadanos.get(i).admin) {
                        ciudadanos.get(i).block=false;
                    }
                }
            }
            Anses.save(ciudadanos);
        }
    }

    public static void addSintom(String sintoma){
        ArrayList<String> sintomas=Anses.sintomas();
        sintomas.add(sintoma);
        Anses.saveSintom(sintomas);
    }

    public static void removeSintom(int sintoma){
        ArrayList<String> sintomas=Anses.sintomas();
        try {
            sintomas.remove(sintoma - 1);
            Anses.saveSintom(sintomas);
        }catch(IndexOutOfBoundsException e){

        }
    }

    public static String getName(String cuil){
        ArrayList<Ciudadano>ciudadanos=Anses.lista();
        for (int i = 0; i < ciudadanos.size(); i++) {
            if(ciudadanos.get(i).cuil.equals(cuil)){
                return ciudadanos.get(i).nombre;
            }
        }
        return "-1";
    }

}
