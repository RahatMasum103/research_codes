/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lodf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author APU
 */
public class LODF {

    /**
     * @param args the command line arguments
     */
    public static final int LINE_PROP = 3;

    public static Vector<LineInfo> line_no = new Vector<LineInfo>();
    public static String busFile = null;
    public static String xMatFile = null;
    public static String lineFlowFile = null;

    //public static Double lineReactance[][]=null;
    public static Double X[][] = null;
    public static Double PTDF[][] = null;
    public static Double LODF[][] = null;
    public static int lineCount;

    public static void input(int n) {
        System.out.println("input entered");

        //String busFile = null;
        int bus_number = n;
        System.out.println("bus entered:" + bus_number);

        switch (bus_number) {
            case 5:
                busFile = "bus_5.csv";
                xMatFile = "X_mat_5_bus.csv";
                lineCount = 7;
                X = new Double[bus_number][bus_number];
                PTDF = new Double[lineCount][lineCount];
                LODF = new Double[lineCount][lineCount];
                //lineReactance = new Double[lineCount][LINE_PROP];
                break;

            case 14:
                busFile = "bus_14.csv";
                xMatFile = "X_mat_14_bus.csv";
                lineCount = 20;
                X = new Double[bus_number][bus_number];
                PTDF = new Double[lineCount][lineCount];
                LODF = new Double[lineCount][lineCount];
                //lineReactance = new Double[lineCount][LINE_PROP];
                break;
            case 30:
                busFile = "bus_30.csv";
                xMatFile = "X_mat_30_bus.csv";
                lineCount = 41;
                X = new Double[bus_number][bus_number];
                PTDF = new Double[lineCount][lineCount];
                LODF = new Double[lineCount][lineCount];
                //lineReactance = new Double[lineCount][LINE_PROP];
                break;
            case 57:
                busFile = "bus_57.csv";
                xMatFile = "X_mat_57_bus.csv";
                lineCount = 80;
                X = new Double[bus_number][bus_number];
                PTDF = new Double[lineCount][lineCount];
                LODF = new Double[lineCount][lineCount];
                //lineReactance = new Double[lineCount][LINE_PROP];
                break;
            case 118:
                busFile = "bus_118.csv";
                xMatFile = "X_mat_118_bus.csv";
                lineCount = 186;
                X = new Double[bus_number][bus_number];
                PTDF = new Double[lineCount][lineCount];
                LODF = new Double[lineCount][lineCount];
                //lineReactance = new Double[lineCount][LINE_PROP];
                break;
            case 300:
                busFile = "bus_300.csv";
                xMatFile = "X_mat_300_bus.csv";
                lineCount = 411;
                X = new Double[bus_number][bus_number];
                PTDF = new Double[lineCount][lineCount];
                LODF = new Double[lineCount][lineCount];
                //lineReactance = new Double[lineCount][LINE_PROP];
                break;
            default:
                System.out.println("Bus topology not found");
                break;
        }
    }

    public static void csvReader(int bus_size) {

        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";

        try {
            String XFile = xMatFile;
            br = new BufferedReader(new FileReader(XFile));
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] line_info = line.split(csvSplitBy);
                for (int j = 0; j < bus_size; j++) {
                    X[i][j] = Double.parseDouble(line_info[j]);
                }
                //System.out.println("test..........." + line_info[3]);
                ++i;
            }

            String bFile = busFile;
            br = new BufferedReader(new FileReader(bFile));
            i = 0;
            while ((line = br.readLine()) != null) {
                String[] line_info = line.split(csvSplitBy);

                int from_bus = Integer.parseInt(line_info[0]);
                int to_bus = Integer.parseInt(line_info[1]);
                double x_reactance = Double.parseDouble(line_info[3]);
                double r_resistance = Double.parseDouble(line_info[2]);
                LineInfo in = new LineInfo(from_bus, to_bus, x_reactance, r_resistance);

                line_no.addElement(in);
                ++i;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("LINE INFORMATION");
        System.out.println("-----------------");
        for (int l = 0; l < line_no.size(); l++) {
            int f = line_no.get(l).fromBus;
            int t = line_no.get(l).toBus;
            double x_r = line_no.get(l).reactance;
            System.out.println(f + " " + t + " " + x_r);

        }

        System.out.println("X (REDUCED B) MATRIX");
        System.out.println("-----------------");
        for (int i = 0; i < bus_size; i++) {
            for (int j = 0; j < bus_size; j++) {
                System.out.print(X[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void PTDF() {
        for (int l = 0; l < lineCount; l++) {
            int f_i = (line_no.get(l).fromBus) - 1;
            int t_j = (line_no.get(l).toBus) - 1;
            double x_r_l = line_no.get(l).reactance;
            for (int k = 0; k < lineCount; k++) {
                if (l == k) {
                    PTDF[l][k] = 0.0;
                } 
                else 
                {
                    int f_s = (line_no.get(k).fromBus) - 1;
                    int t_r = (line_no.get(k).toBus) - 1;
                    double x_r_k = line_no.get(k).reactance;
                    double ptdf = ((X[f_i][f_s] - X[f_i][t_r]) - (X[t_j][f_s] - X[t_j][t_r])) / x_r_l;
                    PTDF[l][k] = ptdf;
                }
            }
        }
        
        DecimalFormat numberFormat = new DecimalFormat("#.0000");

        FileWriter fw;
        try {

            fw = new FileWriter("PTDF_" + lineCount + ".txt", true);
            fw.write(("bus: " + busFile + "\n"));
            for (int i = 0; i < lineCount; i++) {
                for (int j = 0; j < lineCount; j++) {
                    fw.write(PTDF[i][j] + " ");
                }
                fw.write("\n");
            }
            fw.write("\n");
            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       
        System.out.println("PTDF");
        System.out.println("-----------------");
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineCount; j++) {
                System.out.print(numberFormat.format(PTDF[i][j]) + " ");
            }
            System.out.println("");
        }
    }
    
    public static void LODF(int bus)
    {
        for (int l = 0; l < lineCount; l++) {
            for(int k=0; k<lineCount;k++)
            {
                if (l == k) LODF [l][k] = 0.0;
                else
                {
                    double lodf = PTDF[k][l] / (1 - PTDF[k][k]);
                    LODF[l][k]=lodf;
                }                
            }
        }
        
        DecimalFormat numberFormat = new DecimalFormat("0.0000");
        
        FileWriter fw;
        try {

            fw = new FileWriter("LODF_" + bus+"_"+lineCount + ".txt", true);
            //fw.write(("bus: " + busFile + "\n"));
            for (int i = 0; i < lineCount; i++) {
                for (int j = 0; j < lineCount; j++) {
                    fw.write(numberFormat.format(PTDF[i][j]) + " ");
                }
                fw.write("\n");
            }
            fw.write("\n");
            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        System.out.println("LODF");
        System.out.println("-----------------");
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineCount; j++) {
                //System.out.print(numberFormat.format(LODF[i][j]) + " ");
                System.out.print(LODF[i][j] + " ");
            }
            System.out.println("");
        }
        
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of Bus");
        int n = sc.nextInt();

        while (n != 5 && n != 14 && n != 30 && n != 57 && n != 118 && n != 300) {
            System.out.println("Enter CORRECT bus number");
            n = sc.nextInt();
        }

        sc.close();

        input(n);
        csvReader(n);
        PTDF();
        LODF(n);

    }

}
