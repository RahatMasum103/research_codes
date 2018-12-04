/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputbusreader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.pow;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import pointtopology.PointTopology;

/**
 *
 * @author APU
 */
public class InputBusReader {

    public static Vector<LineInfo> line_no = new Vector<LineInfo>();
    //public static List<Double> X = new ArrayList<Double>();

    public static Double X[][] = null;    
    public static Double B[][] = null;
    
    public static int line_count;
    public static int max_seg;
    public static int nUav;

    public static final int f = 0;
    public static final int t = 1;
    public static final int r = 2;
    public static final int x = 3;

    /**
     * @param args the command line arguments
     */
    public static String input(int n) {
        System.out.println("input entered");

        String file = null;
        int bus_number = n;
        System.out.println("bus entered:" + bus_number);

        switch (bus_number) {
            case 5:
                file = "bus_5.csv";
                line_count = 6;
                X = new Double[bus_number][bus_number];
                B = new Double[bus_number][bus_number];
                break;            
            
            case 14:
                file = "bus_14.csv";
                line_count = 20;
                X = new Double[bus_number][bus_number];
                B = new Double[bus_number][bus_number];
                break;
            case 30:
                file = "bus_30.csv";
                line_count = 41;
                X = new Double[bus_number][bus_number];
                 B = new Double[bus_number][bus_number];
                break;
            case 57:
                file = "bus_57.csv";
                line_count = 80;
                X = new Double[bus_number][bus_number];
                 B = new Double[bus_number][bus_number];
                break;
            case 118:
                file = "bus_118.csv";
                line_count = 186;
                X = new Double[bus_number][bus_number];
                 B = new Double[bus_number][bus_number];
                break;
                
            case 300:
                file = "bus_300.csv";
                line_count = 411;
                X = new Double[bus_number][bus_number];
                 B = new Double[bus_number][bus_number];
                break;

            default:
                System.out.println("Bus topology not found");
                break;
        }

        return file;

    }

    public static void csvReader(String fileName, int bus_size) {

        String csvFile = fileName;

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int i = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] line_info = line.split(cvsSplitBy);
                //System.out.println("test..........."+line_info[0]);
                /*
                  if(line_info[f] == null)
                  {
                      System.out.println("null found");
                  }
                  else{
                  System.out.println("..........."+Integer.parseInt(line_info[f]));
                  }
                 */

                int from_bus = Integer.parseInt(line_info[f]);
                int to_bus = Integer.parseInt(line_info[t]);
                double x_reactance = Double.parseDouble(line_info[x]);
                double r_resistance = Double.parseDouble(line_info[r]);
                LineInfo in = new LineInfo(from_bus, to_bus, x_reactance,r_resistance);

                line_no.addElement(in);

                i++;

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

        for (int i = 0; i < line_no.size(); i++) {
            int m = line_no.get(i).fromBus;
            int n = line_no.get(i).toBus;
            double x_r = line_no.get(i).reactance;
            double r_s = line_no.get(i).resistance;

            X[m - 1][n - 1] = x_r;
            X[n - 1][m - 1] = x_r;
            
            double res =  pow(r_s,2);
            double reac = pow(x_r,2);
            B[m - 1][n - 1] = ((-1)*x_r )/(res + reac);
            B[n - 1][m - 1] = ((-1)*x_r )/(res + reac);

        }

        FileWriter writer;
        FileWriter fw;
        FileWriter fBusTopo; 

        try {
            /*
            writer = new FileWriter(csvFile + ".txt");
            for (int i = 0; i < X.size(); i++) {
                System.out.println("Line " + (i + 1) + " Reactance X (pu): " + X.get(i));
                writer.write((i + 1) + " " + X.get(i).toString() + "\n");
            }

            writer.close();
             */
            fBusTopo = new FileWriter("E:\\TTU\\Research\\Z3\\UAV\\UAV\\UAV\\bin\\x64\\Debug\\Input\\input_topo_"+csvFile + ".txt");
            fw = new FileWriter(csvFile + ".txt");
            fw.write(("# line_no," + " " + "from_bus," + " " + "to_bus," + " " + "reactance(X)" + "\n"));
            fBusTopo.write(("# Line_no," + " " + "From_bus," + " " + "To_bus," + "\n"));
            for (int i = 0; i < line_no.size(); i++) {
                System.out.println("Line " + (i + 1) + " Reactance X (pu): " + line_no.get(i).fromBus);
                fw.write((i + 1) + " " + line_no.get(i).fromBus + " " + line_no.get(i).toBus + " " + line_no.get(i).reactance + "\n");
                
                fBusTopo.write((i + 1) + " " + line_no.get(i).fromBus + " " + line_no.get(i).toBus + "\n");
            }
            fBusTopo.close();
            fw.close();

           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      
    }

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("main entered");
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of Bus");
        int n = sc.nextInt();

        while (n != 5 && n != 14 && n != 30 && n != 57 && n != 118 && n!=300) {
            System.out.println("Enter CORRECT bus number");
            n = sc.nextInt();
        }
        System.out.println("Number of max points for a line");
        max_seg = sc.nextInt();
        
         while (max_seg <2) {
            System.out.println("Enter CORRECT points greater than 2");
            max_seg = sc.nextInt();
        }
         
         System.out.println("Number of UAV");
         nUav = sc.nextInt();
           while (nUav <=0) {
            System.out.println("Enter CORRECT UAV number greater than 0");
            nUav = sc.nextInt();
        }
        sc.close();

        String fileName = input(n);
        csvReader(fileName, n);
        
        PointTopology pt = new PointTopology();
        pt.point_info(line_count, max_seg,nUav,n);

        for (int i = 0; i < n; i++) {
            System.out.print("\n");
            for (int j = 0; j < n; j++) {
                System.out.print(X[i][j] + " ");
            }
        }
    }

}
