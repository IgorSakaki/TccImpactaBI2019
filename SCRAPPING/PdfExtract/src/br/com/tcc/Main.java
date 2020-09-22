package br.com.tcc;

import br.com.tcc.Control.Extract;

public class Main  {
    public static void main(String[] args) {

        String path = "/home/sakaki/Documents/projetos/projetoimpactatcc/SCRAPPING/DOWNLOAD/";
        new Extract().extractText(path);

    }
}
