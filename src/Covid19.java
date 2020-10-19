import java.time.LocalDate;
import java.util.ArrayList;

public class Covid19 {

    String cuil;
    int sintoma;

    public Covid19(String cuil, int sintoma){
        this.cuil=cuil;
        this.sintoma=sintoma;
    }

    public static ArrayList<String> sintomas(){
        ArrayList<String> sintomas=Anses.createList("src\\Sintomas");
        return  sintomas;
    }

    public void afectado(){
        ArrayList<String>sintomas=sintomas();
        String toAdd=this.cuil + "/" + sintomas.get(this.sintoma-1) + "\n";
        Anses.añadir("src\\Afectados",toAdd);
        checkCovid();
    }

    public void desafectado(){
        ArrayList<String> afectados=Anses.createList("src\\Afectados");
        ArrayList<String> sintomas=sintomas();
        String eleccion=sintomas.get(this.sintoma-1);

        for (int i = 0; i < afectados.size(); i++) {
            String[] datasplt = afectados.get(i).split("/", 2);
            if (datasplt[0].equals(this.cuil) && datasplt[1].equals(eleccion))
                afectados.remove(i);
        }

        Anses.escribirLista("src\\Afectados",afectados);
    }

    private void checkCovid() {
        ArrayList<String> afectados = Anses.createList("src\\Afectados");
        int x = 0;
        for (int i = 0; i < afectados.size(); i++) {
            String[] datasplt = afectados.get(i).split("/", 2);
            if (datasplt[0].equals(this.cuil)){
                    x++;
                }

            }
        if(x>1){

            String toAdd=this.cuil+"/"+ LocalDate.now()+"\n";
            Anses.añadir("src\\PositiveCovid",toAdd);
            cleanAfectado();
            System.out.println("Es muy probable que usted padezca Covid-19, tome las precauciones necesarias.\n" +
                    "Encontraras mas informacion en el siguiente link:\n" +
                    "https://www.who.int/es/emergencies/diseases/novel-coronavirus-2019/advice-for-public/q-a-coronaviruses#:~:text=sintomas");
        }
    }

    private  void cleanAfectado(){
        ArrayList<String> afectados=Anses.createList("src\\Afectados");

        for (int i = 0; i <afectados.size() ; i++) {
            String[] datasplt = afectados.get(i).split("/", 2);
            if (datasplt[0].equals(this.cuil)){
            afectados.set(i,null);
            }
        }
        Anses.escribirLista("src\\Afectados",afectados);

    }

    public static void saveSintom(ArrayList<String> sintomas){
        Anses.escribirLista("src\\Sintomas",sintomas);
    }

}
