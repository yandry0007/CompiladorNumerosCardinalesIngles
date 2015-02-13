/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package archivojflex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import jflex.Main;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Yandry
 */
public class ArchivoJFLEX {

    public final static int GENERAR = 1;
    public final static int EJECUTAR = 2;
    public final static int SALIR = 3;
    public final static TreeMap<Integer,String> numeros = new TreeMap <Integer,String>();

    /**
     * Es un menu para elegir entre generar el analizador lexico y sintactico, o
     * ejecutarlos sobre un archivo de pruebas.
     *
     */
    public static void main(String[] args) {
        numeros.put(1,"first");
        numeros.put(2,"second");
        numeros.put(3,"third");
        numeros.put(4,"fourth");
        numeros.put(5,"fiveth");
        numeros.put(6,"sixth");
        numeros.put(7,"seventh");
        numeros.put(8,"eighth");
        numeros.put(9,"nineth");
        numeros.put(10,"tenth");
      
        java.util.Scanner in = new Scanner(System.in);
        int valor = 0;
        do {
            System.out.println("\n ELIJA UNA OPCION: ");
            System.out.println("[1] GENERAR");
            System.out.println("[2] EJECUTAR");
            System.out.println("[3] SALIR");
            System.out.print("Opcion: ");
            valor = in.nextInt();
            switch (valor) {
                /*  Generamos el analizador lexico y sintactico.
                 lcalc.flex contiene la definicion del analizador lexico
                 ycalc.cup contiene la definicion del analizador sintactico
                 */
                case GENERAR: {
                    System.out.println("\n*** Generando ***\n");
                    String archLexico = "";
                    String archSintactico = "";
                    if (args.length > 0) {
                        System.out.println("\n*** Procesando archivos custom ***\n");
                        archLexico = args[0];
                        archSintactico = args[1];
                    } else {
                        System.out.println("\n*** Procesando archivo default ***\n");
                        archLexico = "lexico.flex";
                        archSintactico = "analizadorsintactico.cup";
                    }
                    String[] lexico = {archLexico};
                    String[] analizadorsintactico = {"-parser", "AnalizadorSintactico", archSintactico};
                    jflex.Main.main(lexico);
                    try {
                        java_cup.Main.main(analizadorsintactico);
                    } catch (Exception ex) {
                        Logger.getLogger(ArchivoJFLEX.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //movemos los archivos generados
                    boolean mvAL = moverArch("AnalizadorLexico.java");
                    boolean mvAS = moverArch("AnalizadorSintactico.java");
                    boolean mvSym= moverArch("sym.java");
                    if(mvAL && mvAS && mvSym){
                        System.exit(0);
                    }
                    System.out.println("Generado!");
                    break;
                }
                case EJECUTAR: {
                    /*  Ejecutamos el analizador lexico y sintactico
                     sobre un archivo de pruebas. 
                    */ 
                    String[] archivoPrueba = {"entrada.txt"};
                    AnalizadorSintactico.main(archivoPrueba);
                    break;
                }
                case SALIR: {
                    System.out.println("PROGRAMA FINALIZADO!");
                    break;
                }
                default: {
                    System.out.println("Opcion no valida!");
                    break;
                }
            }
        } while (valor != 3);
    }
//Metodo para mover los archivos generados en caso que ya esten creados
    public static boolean moverArch(String archNombre) {
        boolean efectuado = false;
        File arch = new File(archNombre);
        if (arch.exists()) {
            System.out.println("\n*** Moviendo " + arch + " \n***");
            Path currentRelativePath = Paths.get("");
            String nuevoDir = currentRelativePath.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "archivojflex" + File.separator + arch.getName();
            File archViejo = new File(nuevoDir);
            archViejo.delete();
            if (arch.renameTo(new File(nuevoDir))) {
                System.out.println("\n*** Generado " + archNombre + "***\n");
                efectuado = true;
            } else {
                System.out.println("\n*** No movido " + archNombre + " ***\n");
            }

        } else {
            System.out.println("\n*** Codigo no existente ***\n");
        }
        return efectuado;
    }
}    

