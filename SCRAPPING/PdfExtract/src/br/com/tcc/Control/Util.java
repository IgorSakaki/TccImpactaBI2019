package br.com.tcc.Control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Util {

    public void criarDiretorio(String path){
        File diretórioWindows=new File(path);
        diretórioWindows.mkdirs();
    }

    public void addFile(String texto,String pathDestino) {

        try {
            FileWriter fw = new FileWriter(pathDestino, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(texto);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
