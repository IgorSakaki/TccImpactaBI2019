package br.com.tcc.Control;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Extract {

    public void extractText(String pathOrigem) {

        File fileRaiz = new File(pathOrigem);
        String pathDestino = pathOrigem + "EXTRAIDO/";
        new Util().criarDiretorio(pathDestino);

        try {
            for (File raiz : fileRaiz.listFiles()) {
                if (!raiz.isFile()) {
                    String pathFiles = raiz.getAbsolutePath();

                    if(!pathFiles.contains("PROCESSADO") && !pathFiles.contains("EXTRAIDO")) {
                        File fileAno = new File(raiz.getAbsolutePath());
                        for (File ano : fileAno.listFiles()) {
                            File fileMes = new File(ano.getAbsolutePath());
                            for (File mes : fileMes.listFiles()) {
                                File fileDia = new File(mes.getAbsolutePath());
                                for (File dia : fileDia.listFiles()) {
                                    File fileCaderno = new File((dia.getAbsolutePath()));
                                    for (File caderno : fileCaderno.listFiles()) {

                                        String[] split = caderno.getAbsolutePath().split("/");

                                        String arqDia = split[split.length - 3];
                                        String arqMes = split[split.length - 4];
                                        String arqAno = split[split.length - 5];
                                        String arqCaderno = split[split.length - 2] + ".txt";
                                        String nomeTxt = arqAno + "_" + arqMes + "_" + arqDia + "_" + arqCaderno;

                                        System.out.println(nomeTxt);
                                        processFile(caderno.getAbsolutePath(), pathDestino + nomeTxt);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void processFile(String absolutPathOrigem, String pathDestino) {

        List<String> textExtracted = new ArrayList<>();
        System.out.println("Processando: " + absolutPathOrigem);

        try {

            File file = new File(absolutPathOrigem);

            PDFTextStripperByArea stripperArea = new PDFTextStripperByArea();
            stripperArea.setSortByPosition( true );

            //Rectangle title = new Rectangle(0,0,500,50);
            Rectangle col1 = new Rectangle( 35, 50, 185, 1500 );
            Rectangle col2 = new Rectangle( 210, 50, 185, 1500 );
            Rectangle col3 = new Rectangle( 385, 50, 185, 1500 );
            Rectangle col4 = new Rectangle( 570, 50, 185, 1500 );

            //stripperArea.addRegion( "title", title );
            stripperArea.addRegion( "col1", col1 );
            stripperArea.addRegion( "col2", col2 );
            stripperArea.addRegion( "col3", col3 );
            stripperArea.addRegion( "col4", col4 );

            PDDocument documento = PDDocument.load(file);
            PDPage page = documento.getPage(0);
            stripperArea.extractRegions( page );

            //textExtracted.add( stripperArea.getTextForRegion( "title" ));
            textExtracted.add( stripperArea.getTextForRegion( "col1" ));
            textExtracted.add( stripperArea.getTextForRegion( "col2" ));
            textExtracted.add( stripperArea.getTextForRegion( "col3" ));
            textExtracted.add( stripperArea.getTextForRegion( "col4" ));

            documento.close();

        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }

        for(String linha : textExtracted){
            new Util().addFile(linha,pathDestino);
        }
    }
}
