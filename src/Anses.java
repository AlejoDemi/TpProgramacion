import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Anses {

    public static ArrayList<Ciudadano> lista() {
        ArrayList<String> data = listaS();
        boolean admin;
        boolean block;
        ArrayList<Ciudadano> ciudadanos = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            String[] datasplt = data.get(i).split("/", 5);
            try {
                if ("true".equals(datasplt[3])) {
                    admin = true;
                } else {
                    admin = false;
                }
                if ("true".equals(datasplt[4])) {
                    block = true;
                } else {
                    block = false;
                }
                ciudadanos.add(new Ciudadano(datasplt[0], datasplt[1], datasplt[2], admin, block));
            } catch (ArrayIndexOutOfBoundsException e) {
            }

        }
        return ciudadanos;
    }//crear lista de ciudadanos a partir de ListaS(String)

    public static void nuevoCiu(String data,ArrayList<Ciudadano>ciudadanos) {
        ArrayList<Ciudadano> ciudadanosA = listaAnses();
        String cuilnum = data.substring(0, 11);
        String celnum = data.substring(11, data.length());
        int x = 1;
        for (int j = 0; j < ciudadanos.size(); j++) {
            if (cuilnum.equals(ciudadanos.get(j).cuil)) {
                System.out.println("Usted ya esta registrado/a,");
                x = 0;
            }
        }
        if (x == 1) {
            for (int i = 0; i < ciudadanosA.size(); i++) {
                if (cuilnum.equals(ciudadanosA.get(i).cuil) && celnum.equals(ciudadanosA.get(i).cel)) {
                    addCiu(ciudadanosA.get(i));
                }
            }
        }
    }//registrar nuevo ciudadano

    public static void save(ArrayList<Ciudadano>ciudadanos){
        try {
            FileWriter fileWriter = new FileWriter("src\\archivos");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String admin;
            String block;
            for (int i = 0; i < ciudadanos.size(); i++) {
                if(ciudadanos.get(i).admin){
                    admin="true";
                }else{
                    admin="false";
                }
                if (ciudadanos.get(i).block){
                    block="true";
                }else{
                    block="false";
                }
                String nombre=ciudadanos.get(i).nombre;
                String cuil=ciudadanos.get(i).cuil;
                String cel=ciudadanos.get(i).cel;

                bufferedWriter.write(nombre+"/"+cuil+"/"+cel+"/"+admin+"/"+block + "\n");
            }
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }//guarda los datos modificados(bloqueo)

    public static ArrayList<String> sintomas(){
        ArrayList<String> sintomas=new ArrayList<>();
        try{
            FileReader fileReader=new FileReader("src\\Sintomas");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                sintomas.add(line);
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return sintomas;

    }// crea la lista de sintomas a partir del archivo "sintomas"

    public static void saveSintom(ArrayList<String> sintomas){
        try {
            FileWriter fileWriter = new FileWriter("src\\sintomas");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < sintomas.size(); i++) {
                bufferedWriter.write(sintomas.get(i) + "\n");
            }
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }//Guarda los sintomas modificados

    public static void afectado(String cuil, int sintoma){
        ArrayList<String>sintomas=sintomas();
        try {
            FileWriter fileWriter = new FileWriter("src\\Afectados",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(cuil + "/" + sintomas.get(sintoma-1) + "\n");
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        checkCovid(cuil);
    }//agrega al archivo afectados el cuil del ciudadano, y el sintoma

    public static void desafectado(String cuil, int sintoma){
        ArrayList<String> afectados=new ArrayList<>();
        ArrayList<String> sintomas=sintomas();
        String sintoma2=sintomas.get(sintoma-1);
        try{
            FileReader fileReader=new FileReader("src\\Afectados");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while (line!=null){
                afectados.add(line);
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < afectados.size(); i++) {
            try {
                String cuil1 = afectados.get(i).substring(0, 11);
                String sintoma1 = afectados.get(i).substring(12, afectados.get(i).length());
                if (cuil.equals(cuil1) && sintoma1.equals(sintoma2)) {
                    afectados.remove(i);
                }
            } catch (StringIndexOutOfBoundsException e) {
            }
        }
        try{
            FileWriter fileWriter=new FileWriter("src\\Afectados");
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            for (int i = 0; i < afectados.size(); i++) {
                bufferedWriter.write(afectados.get(i)+"\n");
            }
            bufferedWriter.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }//retira el sintoma elegido de la lista de afectados

    public static void notificar(LocalDate fecha,String cuil,String cuilCont){
        try {
            FileWriter fileWriter = new FileWriter("src\\Notificaciones", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(cuil + "/" + cuilCont +"/"+ fecha + "\n");
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }//Notifica al usuario escribiendo en notificaciones

    public static ArrayList<String> checkNot(String cuil){
        ArrayList<String> notificaciones=new ArrayList<>();
        try{
            FileReader fileReader=new FileReader("src\\Notificaciones");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while(line != null) {
                try {
                    if ( line.substring(12, 23).equals(cuil)) {
                        notificaciones.add(line);
                    }
                } catch (StringIndexOutOfBoundsException e) { }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return notificaciones;
    }// Notifica al usuario del cuil correspondiente

    public static void contact(String cuilCont, String cuil){
        ArrayList<String>notificaciones=checkNot(cuilCont);
        String contacto="";
        for (int i = 0; i < notificaciones.size(); i++) {
            if (notificaciones.get(i).substring(0,11).equals(cuil) && notificaciones.get(i).substring(12,23).equals(cuilCont)){
                contacto=notificaciones.get(i);
                notificaciones.remove(i);
            }
        }
        try {
            FileWriter fileWriter = new FileWriter("src\\Notificaciones");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < notificaciones.size(); i++) {
                bufferedWriter.write(notificaciones.get(i)+"\n");
            }
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        try {
            FileWriter fileWriter = new FileWriter("src\\Contacto",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(contacto + "\n");
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }// Si el contacto es positivo, lo borra de notificaciones y envia a contactos

    public static void rechazo(String cuil,String cuilCont){
        ArrayList<String> notificaciones=checkNot(cuilCont);
        for (int i = 0; i < notificaciones.size(); i++) {
            if (notificaciones.get(i).substring(0,11).equals(cuil) && notificaciones.get(i).substring(12,23).equals(cuilCont)){
                notificaciones.remove(i);
            }
        }
        try {
            FileWriter fileWriter = new FileWriter("src\\Notificaciones");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < notificaciones.size(); i++) {
                bufferedWriter.write(notificaciones.get(i)+"\n");
            }
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter fileWriter = new FileWriter("src\\Rechazos",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(cuil + "\n");
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        checkBlock(cuil);

    }//Si el contacto es negativo, anota el cuil del rechazado en rechazos

    public static void checkBlock(String cuil){
       ArrayList<String> rechazos=new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("src\\Rechazos");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                rechazos.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        int x=0;
        for (int i = 0; i < rechazos.size(); i++) {
                if(rechazos.get(i).equals(cuil)){
                    x++;
                }
            }
        if(x>4){
            for (int i = 0; i < rechazos.size(); i++) {
                if(rechazos.get(i).equals(cuil)){
                    rechazos.set(i,null);
                }
                Ciudadano.bloquear(cuil,lista());
            }
        }

    }//chequea si tenes 5 rechazos y te bloquea

    public static void checkContagio(String cuilCont,String cuil){
        String contacto="";
        String covid="";
        try{
            FileReader fileReader=new FileReader("src\\Contacto");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while(line!= null){
               try {
                   if (line.substring(0, 11).equals(cuil) && line.substring(12, 23).equals(cuilCont)) {
                       contacto = line;
                       break;
                   } else if (line.substring(0, 11).equals(cuilCont) && line.substring(12, 23).equals(cuil)) {
                       contacto = line;
                       break;
                   }
               }catch(StringIndexOutOfBoundsException e){

               }
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileReader fileReader=new FileReader("src\\PositiveCovid");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while(line!= null){
               try {
                   if (line.substring(0, 11).equals(cuil) || line.substring(0, 11).equals(cuilCont)) {
                       covid = line;
                       break;
                   }
               }catch(StringIndexOutOfBoundsException e){

               }
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
          try {
              String fechaCont = contacto.substring(24);
              String fechaCovid = covid.substring(12);
              LocalDate contactDate = LocalDate.parse(fechaCont);
              LocalDate covidDate = LocalDate.parse(fechaCovid);
              Period period1 = Period.between(contactDate, covidDate);
              Period period2 = Period.between(covidDate, contactDate);
              if (period1.getDays() >= 0 && period1.getDays() < 3) {
                  addCovid(cuilCont, cuil);
                  System.out.println("Es muy probable que usted padezca Covid-19, tome las precauciones necesarias.\n" +
                          "Encontraras mas informacion en el siguiente link:\n" +
                          "https://www.who.int/es/emergencies/diseases/novel-coronavirus-2019/advice-for-public/q-a-coronaviruses#:~:text=sintomas");
              } else if (period2.getDays() >= 0 ) {
                  addCovid(cuilCont, cuil);
                  System.out.println("Es muy probable que usted padezca Covid-19, tome las precauciones necesarias.\n" +
                          "Encontraras mas informacion en el siguiente link:\n" +
                          "https://www.who.int/es/emergencies/diseases/novel-coronavirus-2019/advice-for-public/q-a-coronaviruses#:~:text=sintomas");
              }
          }catch(StringIndexOutOfBoundsException e){

          }


    }//revisa si el contacto emitio contagio

    private static void addCovid(String cuil1,String cuil2){
        String covid="";
        try{
            FileReader fileReader=new FileReader("src\\PositiveCovid");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while(line!= null){
                try {
                    if (line.substring(0, 11).equals(cuil1)) {
                        covid = cuil2;
                        break;
                    } else if (line.substring(0, 11).equals(cuil2)) {
                        covid = cuil1;
                        break;
                    }
                }catch(StringIndexOutOfBoundsException e){

                }
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        try {
            FileWriter fileWriter = new FileWriter("src\\PositiveCovid",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(covid+"/"+LocalDate.now()+"\n");
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }//En caso de haberse contagiado, diagnostica positivo a los que tuvieron contacto

    private static void checkCovid(String cuil) {
        ArrayList<String> afectados = new ArrayList<>();
        int x = 0;
        try {
            FileReader fileReader = new FileReader("src\\Afectados");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                afectados.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < afectados.size(); i++) {
            try {
                if (afectados.get(i).substring(0, 11).equals(cuil)) {
                    x++;
                }
            } catch (StringIndexOutOfBoundsException e) {

            }
        }
        if(x>1){
            positive(cuil);
            cleanAfectado(cuil);
            System.out.println("Es muy probable que usted padezca Covid-19, tome las precauciones necesarias.\n" +
                    "Encontraras mas informacion en el siguiente link:\n" +
                    "https://www.who.int/es/emergencies/diseases/novel-coronavirus-2019/advice-for-public/q-a-coronaviruses#:~:text=sintomas");
        }
    }//Revisa si el afectado llego a los 2 sintomas, mandando al archivo Positivo de ser asi

    private static void positive(String cuil){
        try {
            FileWriter fileWriter = new FileWriter("src\\PositiveCovid",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(cuil+"/"+LocalDate.now()+"\n");
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }//Metdodo auxiliar de check covid para mandar a Positivo el afectado con 3 sintomas

    private static void cleanAfectado(String cuil){
        ArrayList<String> afectados=new ArrayList<>();
        try{
            FileReader fileReader=new FileReader("src\\Afectados");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while (line!=null && !line.equals("")){
                afectados.add(line);
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        for (int i = 0; i <afectados.size() ; i++) {
           try{
                if(afectados.get(i).substring(0,11).equals(cuil)) {
                    afectados.set(i,null);
                }
            }catch(StringIndexOutOfBoundsException e){
           }
        }
        try {
            FileWriter fileWriter = new FileWriter("src\\Afectados");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < afectados.size(); i++) {
               if(afectados.get(i)!=null) {
                   bufferedWriter.write(afectados.get(i));
               }
            }
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }//quita de afectados los casos positivos

    private static void addCiu(Ciudadano a) {
        String name = a.nombre;
        String cuil = a.cuil;
        String cel = a.cel;
        String admin;
        String block;
        if (a.admin){
            admin ="true";
        }else {
            admin = "false";
        }
        if(a.block) {
            block = "true";
        }else {
            block = "false";
        }

            try {
                FileWriter fileWriter = new FileWriter("src\\archivos",true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("\n"+name + "/" + cuil + "/" + cel + "/" + admin + "/" + block);
                bufferedWriter.close();
            }catch(IOException e){
                System.out.println(e.getMessage());
        }

    }//agrega un nuevo ciudadano a la lista de registrados

    private static ArrayList<String> listaS(){
        ArrayList<String> ciudadanos=new ArrayList<>();
        try{
            FileReader fileReader=new FileReader("src\\archivos");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                ciudadanos.add(line);
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return ciudadanos;
    }//crea un arreglo de Strings de los ciudadanos ya registrados

    private static ArrayList<String> listaAnsesS(){
        ArrayList<String> ciudadanos=new ArrayList<>();
        try{
            FileReader fileReader=new FileReader("src\\dataSet");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                ciudadanos.add(line);
                line=bufferedReader.readLine();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return ciudadanos;
    }//recopila la info de la base de anses y crea un arreglo de String

    private static ArrayList<Ciudadano> listaAnses(){
        ArrayList<String> data=listaAnsesS();
        boolean admin;
        boolean block;
        ArrayList<Ciudadano>ciudadanos=new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            String[] datasplt=data.get(i).split("/",5);

            if("true".equals(datasplt[3])) {
                admin = true;
            }else{
                admin=false;
            }
            if("true".equals(datasplt[4])){
                block=true;
            }else{
                block=false;
            }
            ciudadanos.add(new Ciudadano(datasplt[0], datasplt[1], datasplt[2], admin, block));
        }
        return ciudadanos;
    }// Convierte el arreglo de String en ciudadanos(base anses)

}
