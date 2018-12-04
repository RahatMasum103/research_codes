/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeansuav;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author APU
 */
public class KMeansUAV {

    /**
     * @param args the command line arguments
     */
    public static HashMap<Double, Integer> h_pi = new HashMap<Double, Integer>();

    public static HashMap<Double, List<Integer>> k_set = new HashMap<Double, List<Integer>>();
    public static HashMap<Double, Integer> line_weight = new HashMap<Double, Integer>();
//    public static List<Integer> line_set = null;
//new ArrayList<Integer>();

    public static int count1, count2, count3;
    public static double d[];
    public static double k[][];
    public static double tempk[][];
    public static double m[];
    public static double diff[];
    public static int n, p;
    public static int bus;

    public static double lw[][];
    
    public static final int seg_length = 10;

    static void input() throws FileNotFoundException {

        String fName = null;

        switch (bus) {
            case 5:
                fName = "input_PI_bus_5.csv.txt";//  
                break;
            case 14:
                fName = "input_PI_bus_14.csv.txt";//                
                break;
            case 30:
                fName = "input_PI_bus_30.csv.txt";
                break;
            case 57:
                fName = "input_PI_bus_57.csv.txt";
                break;
            case 118:
                fName = "input_PI_bus_118.csv.txt";
                break;
                
            case 300:
                fName = "input_PI_bus_300.csv.txt";
                break;
            default:
                System.out.println("Bus topology not found");
                break;
        }

        File file = new File(fName);
        //Scanner sc = new Scanner(file);
        try {
            Scanner input = new Scanner(file);
            //String line;
            int count = 0;
            n = input.nextInt();
            d = new double[n];
            for (int j = 0; j < n; j++) {

                d[j] = input.nextDouble();
                h_pi.put(d[j], (j + 1));
            }
            for (int j = 0; j < n; j++) {
                System.out.println("values_" + j + " " + d[j]);
            }
            input.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(KMeansUAV.class.getName()).log(Level.SEVERE, null, ex);
        }

//        int V = sc.nextInt();
//        while (sc.hasNextLine()) 
//        {
//            n++;
//            System.out.println(sc.nextLine());
//        }
    }

    static void cluster_output() {

//        line_cluster = new double[bus][2];
//        
//        for(int c = 0; c< bus;c++)
//        {
//          line_cluster[c][0]=;
//          line_cluster[c][1]=;
//        }
//        for (Map.Entry m : line_cluster.entrySet()) {
//            System.out.println(m.getValue() + " " + m.getKey());
//        }
        
//        for(int a=0;a<n;a++)
//        {
//            System.out.println("lwww......."+(int)lw[a][0]+" "+lw[a][1]);
//        }
        FileWriter fw , f_xt, fWeightIn;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
        try {
            TreeMap<Double, List<Integer>> sorted = new TreeMap<>(k_set);
//        ArrayList<Double> sortedKeys = new ArrayList<Double>(k_set.keySet());
//         
//        Collections.sort(sortedKeys); 
        int score = 1;
        f_xt = new FileWriter("cluster_"+bus+".txt");
        
        f_xt.write("# cluster_centroid, line_set"+"\n");

        for (Map.Entry m : sorted.entrySet()) {
//            for (Double m : sortedKeys) {
//            System.out.println(k_set.get(m));
            System.out.println("Key = " + m.getKey() + ", Value = " + m.getValue());
            line_weight.put((double)m.getKey(),score);
            score++;
            f_xt.write( m.getKey()+"  "+m.getValue()+"\n");
            
//            List<String> L = new ArrayList<String>(m.getValue().toString());
//for(int a=0;a<m.getValue();a++)
//{
//    
//}

//            for(int i=0;i<L.size();i++)
//            {
//                System.out.println("line_set"+L.get(i));
//            }
        }
        f_xt.close();
            
            
            fWeightIn = new FileWriter("E:\\TTU\\Research\\Z3\\UAV\\UAV\\UAV\\bin\\x64\\Debug\\Input\\input_"+bus+"_line_weight_"+sdf.format(cal.getTime()) + ".txt");
            fw = new FileWriter("line_weight_" + bus + ".txt");
           
            //fw.write(("# total_line," + " " + "total_points" + "\n"));
            //fw.write(line_num + " " + (point_count - 1) + "\n");

            //fw.write(("#total_line"+ "\n"));
//            
//          for (Map.Entry m : sorted.entrySet()) {
////            for (Double m : sortedKeys) {
////            System.out.println(k_set.get(m));
////            System.out.println("Key = " + m.getKey() + ", Value = " + m.getValue());
//            fw.write( m.getKey()+"  "+m.getValue()+"\n");
            fw.write(("# line_no, line_weight" + "\n"));
            //fWeightIn.write(("# line_properties" +"\n"+"# line_critical_weight, segment_length" + "\n"));
            fWeightIn.write(("# Line Critical Weights \n"));
            for (int a = 0; a < n; a++) {
                System.out.println("lwww......." + (int) lw[a][0] + " " + lw[a][1]+" "+line_weight.get(lw[a][1]));
                fw.write((int) lw[a][0] + " " + line_weight.get(lw[a][1])+ "\n");
                //fWeightIn.write(line_weight.get(lw[a][1])+" "+ seg_length+ "\n");
                fWeightIn.write(line_weight.get(lw[a][1])+" ");
            }
            fWeightIn.write("\n");

            fWeightIn.close();
            fw.close();
//
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    static void Kmean() {
        /* Initialising arrays */
        k = new double[p][n];
        tempk = new double[p][n];
        m = new double[p];
        diff = new double[p];

        /* Initializing m */
        for (int i = 0; i < p; i++) {
            m[i] = d[i];
        }

        int t = 0;
        int flag = 0;
        do {
            for (int i = 0; i < p; i++) {
                for (int j = 0; j < n; j++) {
                    k[i][j] = -1;
                }
            }

            for (int i = 0; i < n; i++) // for loop will cal cal_diff(int) for every element.
            {
                t = diff_calculate(d[i]);
                if (t == 0) {
                    k[t][count1++] = d[i];
                } else if (t == 1) {
                    k[t][count2++] = d[i];
                } else if (t == 2) {
                    k[t][count3++] = d[i];
                }
            }

            mean(); // call to method which will calculate mean at this step.
            flag = check(); // check if terminating condition is satisfied.

            if (flag != 1) /*Take backup of k in tempk so that you can check for equivalence in next step*/ {
                for (int i = 0; i < p; i++) {
                    for (int j = 0; j < n; ++j) {
                        tempk[i][j] = k[i][j];
                    }
                }
            }

            System.out.println("\n\nAt this step");
            System.out.println("\nValue of clusters");

            for (int i = 0; i < p; i++) {
                System.out.print("K" + (i + 1) + "{ ");

                for (int j = 0; k[i][j] != -1 && j < n - 1; ++j) {
                    System.out.print(k[i][j] + " ");
                }
                System.out.println("}");
            }//end of for loop

            System.out.println("\nValue of m ");

            for (int i = 0; i < p; i++) {
                System.out.print("m" + (i + 1) + "=" + m[i] + "  ");

            }

            count1 = 0;
            count2 = 0;
            count3 = 0;
        } while (flag == 0);

        System.out.println("\n\n\nThe Final Clusters By Kmeans are as follows: ");
        lw = new double[n][2];
        for (int i = 0; i < p; i++) {
            System.out.print("K" + (i + 1) + "{ ");
            List<Integer> line_set = new ArrayList<Integer>();

            for (int j = 0; k[i][j] != -1 && j < n - 1; ++j) {
                System.out.print(k[i][j] + " ");
                System.out.print("[" + h_pi.get(k[i][j]) + "], ");

//                line_cluster.put(m[i], h_pi.get(k[i][j]));
                line_set.add(h_pi.get(k[i][j]));

            }
            for (int a = 0; a < line_set.size(); a++) {
                lw[line_set.get(a) - 1][0] = line_set.get(a);
                lw[line_set.get(a) - 1][1] = m[i];
//                System.out.println("aaaaaa....."+line_set.get(a));
            }
            k_set.put(m[i], line_set);
            System.out.println("}");
        }
    }

    static int diff_calculate(double a) // This method will determine the cluster in which an element go at a particular step.
    {
        //int temp1 = 0;
        for (int i = 0; i < p; ++i) {
            if (a > m[i]) {
                diff[i] = a - m[i];
            } else {
                diff[i] = m[i] - a;
            }
        }
        int val = 0;
        double temp = diff[0];
        for (int i = 0; i < p; ++i) {
            if (diff[i] < temp) {
                temp = diff[i];
                val = i;
            }
        }//end of for loop
        return val;
    }

    static void mean() // This method will determine intermediate mean values
    {
        for (int i = 0; i < p; ++i) {
            m[i] = 0; // initializing means to 0
        }
        int cnt = 0;
        for (int i = 0; i < p; ++i) {
            cnt = 0;
            for (int j = 0; j < n - 1; ++j) {
                if (k[i][j] != -1) {
                    m[i] += k[i][j];
                    ++cnt;
                }
            }
            m[i] = m[i] / cnt;
        }
    }

    static int check() // This checks if previous k ie. tempk and current k are same.Used as terminating case.
    {
        for (int i = 0; i < p; ++i) {
            for (int j = 0; j < n; ++j) {
                if (tempk[i][j] != k[i][j]) {
                    return 0;
                }
            }
        }
        return 1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Scanner sb = new Scanner(System.in);
        System.out.println("Number of Bus");
        bus = sb.nextInt();

        while (bus != 5 && bus != 14 && bus != 30 && bus != 57 && bus != 118 && bus!=300) {
            System.out.println("Enter CORRECT bus number");
            bus = sb.nextInt();
        }
// test purpose
//        while (n != 4) {
//            System.out.println("Enter CORRECT bus number");
//            n = sc.nextInt();
//        }

//        sb.close();
        input();

//        Scanner scr = new Scanner(System.in);
//
//        /* Accepting number of elements */
//        System.out.println("Enter the number of elements ");
//        n = scr.nextInt();
//        d = new double[n];
//
//        /* Accepting elements */
//        System.out.println("Enter " + n + " elements: ");
//        for (int i = 0; i < n; ++i) {
//            d[i] = scr.nextDouble();
//        }

        /* Accepting num of clusters */
        System.out.println("Enter the number of clusters: ");
        p = sb.nextInt();

        sb.close();
        Kmean();
        cluster_output();

//        sb.close();
    }

}
