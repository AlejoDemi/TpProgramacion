import java.io.*;
import java.util.ArrayList;

public class Anses {

    public static ArrayList<Ciudadano> listaCiudadanos() {
        ArrayList<String> data = createList("src\\archivos");
        boolean admin;
        boolean block;
        ArrayList<Ciudadano> ciudadanos = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            String[] datasplt = data.get(i).split("/", 6);
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
                ciudadanos.add(new Ciudadano(datasplt[0], datasplt[1], datasplt[2], admin, block,datasplt[5]));
            } catch (ArrayIndexOutOfBoundsException e) {
            }

        }
        return ciudadanos;
    }//crear lista de ciudadanos a partir de ListaS(String)

    public static void nuevoCiu(String data,ArrayList<Ciudadano>ciudadanos) {
        ArrayList<Ciudadano> ciudadanosA = listaAnses();
        String cuilnum = data.substring(0, 11);
        String celnum = data.substring(11);
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
                    RegistrarCiudadanoAuxiliar(ciudadanosA.get(i));
                }
            }
        }
    }//registrar nuevo ciudadano

    private static void RegistrarCiudadanoAuxiliar(Ciudadano a) {
        String name = a.nombre;
        String cuil = a.cuil;
        String cel = a.cel;
        String admin;
        String block;
        String ubicacion=a.ubicacion;
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
        String toAdd="\n"+name + "/" + cuil + "/" + cel + "/" + admin + "/" + block+"/"+ubicacion;

        añadir("src\\archivos",toAdd);

    }//agrega un nuevo ciudadano a la lista de registrados

    private static ArrayList<Ciudadano> listaAnses(){
        ArrayList<String> data;
        data = createList("src\\dataSet");
        boolean admin;
        boolean block;
        ArrayList<Ciudadano>ciudadanos=new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            String[] datasplt=data.get(i).split("/",6);

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
            ciudadanos.add(new Ciudadano(datasplt[0], datasplt[1], datasplt[2], admin, block,datasplt[5]));
        }
        return ciudadanos;
    }// Convierte el arreglo de String en ciudadanos(base anses)

    public static ArrayList<String> createList(String file){
        ArrayList<String> list=new ArrayList<>();
        try{
            FileReader fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                list.add(line);
                line=bufferedReader.readLine();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    public static void escribirLista(String file,ArrayList<String> lista){
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < lista.size(); i++) {
                if(lista.get(i)!=null) {
                    bufferedWriter.write(lista.get(i)+"\n");
                }
            }
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public static void añadir(String file,String toAdd){
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(toAdd);
            bufferedWriter.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }



}
