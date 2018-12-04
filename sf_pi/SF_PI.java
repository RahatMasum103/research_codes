/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf_pi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.pow;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author APU
 */
public class SF_PI {

    /**
     * @param args the command line arguments
     */
    public static Vector<LineInfo> line_no = new Vector<LineInfo>();
    public static Vector<GenInfo> gen_no = new Vector<GenInfo>();

    public static Vector<FlowInfo> flow_no = new Vector<FlowInfo>();

    public static Vector<GenInfo> gen_input = new Vector<GenInfo>();
    public static Vector<LoadInfo> ld_no = new Vector<LoadInfo>();

    public static double Z[][];
    public static double S[][];
    public static double I[];
    public static double PI[];

    public static int G[];
    public static int L[];

    public static String busFile = null;
    public static String zMatFile = null;
    public static String sfFile = null;
    public static String gfFile = null;

//    public static int GEN_BUS = 2;
//    public static int LD_BUS;
//    public static int gen_num;
    public static double load_sum;
    public static double gen_sum;

    public static final int f = 0;
    public static final int t = 1;
    public static final int x = 3;

    public static final int pi_coEff = 7;

    public static final int POWER = 100;

    public static final int P_MAX = 1000;

    public static int getRandomInteger(int maximum, int minimum) {
        int number = ((int) (Math.random() * (maximum - minimum))) + minimum;
        return number;
    }

    public static void input(int n) {
        System.out.println("input entered");

        //String busFile = null;
        int bus_number = n;
        System.out.println("bus entered:" + bus_number);

        switch (bus_number) {
            case 5:
               busFile = "bus_5.csv";
//                zMatFile = "Z_MAT_bus_118.csv";
                //zMatFile = "z_bus_118.csv";
//                zMatFile = "Z_MAT_bus_118.txt";
                sfFile = "sf_bus_5_matlab.csv";
                gfFile = "gen_bus_5.csv";
//                LD_BUS = bus_number - 1;
//                line_count = 186;
//                X = new Double[bus_number][bus_number];
                break;

            case 14:
                busFile = "bus_14.csv";
//                zMatFile = "Z_MAT_bus_14.csv";
                zMatFile = "z_bus_14.csv";
                sfFile = "sf_bus_14_matlab.csv";

                gfFile = "gen_bus_14.csv";

//                   zMatFile = "Z_MAT_bus_14.txt";
//                LD_BUS = bus_number - 1;
//                line_count = 20;
//                X = new Double[bus_number][bus_number];
                break;
            case 30:
                busFile = "bus_30.csv";
//                zMatFile = "Z_MAT_bus_30.csv";
                zMatFile = "z_bus_30.csv";
//                zMatFile = "Z_MAT_bus_30.txt";
                sfFile = "sf_bus_30_matlab.csv";
                gfFile = "gen_bus_30.csv";
//                LD_BUS = bus_number - 1;
//                line_count = 41;
//                X = new Double[bus_number][bus_number];
                break;
            case 57:
                busFile = "bus_57.csv";
//                zMatFile = "Z_MAT_bus_57.csv";
                zMatFile = "z_bus_57.csv";
//                 zMatFile = "Z_MAT_bus_57.txt";
                sfFile = "sf_bus_57_matlab.csv";
                gfFile = "gen_bus_57.csv";
//                LD_BUS = bus_number - 1;
//                line_count = 80;
//                X = new Double[bus_number][bus_number];
                break;
            case 118:
                busFile = "bus_118.csv";
//                zMatFile = "Z_MAT_bus_118.csv";
                zMatFile = "z_bus_118.csv";
//                zMatFile = "Z_MAT_bus_118.txt";
                sfFile = "sf_bus_118_matlab.csv";
                gfFile = "gen_bus_118.csv";
//                LD_BUS = bus_number - 1;
//                line_count = 186;
//                X = new Double[bus_number][bus_number];
                break;
            case 300:
                busFile = "bus_300.csv";
//                zMatFile = "Z_MAT_bus_118.csv";
                //zMatFile = "z_bus_118.csv";
//                zMatFile = "Z_MAT_bus_118.txt";
                sfFile = "sf_bus_300_matlab.csv";
                gfFile = "gen_bus_300.csv";
//                LD_BUS = bus_number - 1;
//                line_count = 186;
//                X = new Double[bus_number][bus_number];
                break;

            default:
                System.out.println("Bus topology not found");
                break;
        }

    }

    public static void ShiftFactor(String bFile, String zFile, String sFile, String gFile, int n) {

//        String bFile = bFile;
//        String zFile = zFile;
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        String txtSplitBy = " ";
        //double x = 0.032;

        int m = 0;
        // line topology read
        try {
            //file = "bus_test.csv";

            br = new BufferedReader(new FileReader(bFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] line_info = line.split(csvSplitBy);

                int from_bus = Integer.parseInt(line_info[f]);
                int to_bus = Integer.parseInt(line_info[t]);
                double x_reactance = Double.parseDouble(line_info[x]);
                LineInfo in = new LineInfo(from_bus, to_bus, x_reactance);
                line_no.addElement(in);

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

        try {
            //file = "bus_test.csv";

            br = new BufferedReader(new FileReader(gFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] line_info = line.split(csvSplitBy);

                int bus_no = Integer.parseInt(line_info[0]);
//                double bus_flow = Double.parseDouble(line_info[1]);
//                double max_flow = Double.parseDouble(line_info[8]);

//                int load_no = n- bus_no;
//                LoadInfo lIn = new LoadInfo(load_no,0.0,0.0);
                GenInfo gIn = new GenInfo(bus_no, 0.0, 0.0);
                gen_input.addElement(gIn);
//                ld_no.addElement(lIn);

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


        int line_num = line_no.size();
        S = new double[line_num][n];
        try {
            //file = "Z_MAT_test.csv";
//            br = new BufferedReader(new FileReader(zFile));
//            //int i = 1;
//            while ((line = br.readLine()) != null) {
//                m++;
//            }
//            System.out.println("m*n " + m + " " + m);

            br = new BufferedReader(new FileReader(sFile));
            int i = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] line_info = line.split(csvSplitBy);
                S[0][0] = 0.0;
                for (int k = 1; k < n; k++) {
                    S[i][k] = Double.parseDouble(line_info[k - 1]);
                }
                //System.out.println("test..........." + line_info[3]);
                ++i;
                //m++;
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

        //double S = (Z[2][1]-Z[3][1])/x;
        for (int l = 0; l < line_num; l++) {
            System.out.print("SF for Line_" + (l + 1) + ": ");
            for (int b = 0; b < n; b++) {
                System.out.print(S[l][b] + " ");
            }
            System.out.println("");
        }


        /*
        FileWriter fw;
        try {
            fw = new FileWriter("SF_" + busFile + ".csv");
            //fw.write(("# total_line," + " " + "total_points" + "\n"));
            //fw.write(line_num + " " + (point_count - 1) + "\n");

            //fw.write(("#point_set"+ "\n"));
            for (int l = 0; l < line_num; l++) {
                //System.out.print("SF for Line_" + (l + 1) + ": ");
                for (int b = 0; b < n-1; b++) {
                    fw.write(df.format(S[l][b]) + " ");
                }
                fw.write("\n");
            }

            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         */
        int gen_num = gen_input.size();

        G = new int[gen_num];
        L = new int[n];

        int count = 1;
        for (int l = 0; l < n; l++) {
            L[l] = count;
            count++;
        }

        for (int g = 0; g < gen_num; g++) {

            G[g] = gen_input.get(g).bus_no;
            L[gen_input.get(g).bus_no - 1] = 0;
            System.out.println(gen_input.get(g).bus_no + " " + gen_input.get(g).gen_flow + " " + gen_input.get(g).max_flow);

        }

//        for(int l=0;l<n;l++)
//        {
////            LoadInfo lIn= null;
//            for(int g=0; g<gen_num;g++)
//            {
//                if(l+1 == G[g])
//                {
//                    L[l]=0;
////                    continue;
//                }
//                else
//                {
//                    L[l] = l+1;
////                  lIn = 
//                }                
//            }
//                                
////            ld_no.addElement(lIn);
//            
//        }
        for (int l = 0; l < n; l++) {
            if (L[l] == 0) {
                continue;
            } else {
                int ld_flow = getRandomInteger(80, 30);
//                int mx_flow = ld_flow + 20;
                LoadInfo lIn = new LoadInfo(L[l], ld_flow, 0.0);
                ld_no.addElement(lIn);
            }
        }

        int ld_num = ld_no.size();

        for (int l = 0; l < ld_num; l++) {
            System.out.println(ld_no.get(l).bus_no + " " + ld_no.get(l).load_flow);
//            + " " + ld_no.get(l).max_flow);
        }

        load_sum = 0;
        for (int l = 0; l < ld_num; l++) {
            load_sum = load_sum + ld_no.get(l).load_flow;

//            System.out.println(ld_no.get(l).bus_no + " " + ld_no.get(l).load_flow + " " + ld_no.get(l).max_flow);
        }

        System.out.println("load sum: " + load_sum);
        gen_sum = load_sum;

        double gd_flow = 0;
        System.out.println("gen sum: " + gen_sum);
//        int range = (int) gen_sum / gen_num;
//        int gd_rand = getRandomInteger(range, 0);
//        double gd_flow = gd_rand;

        for (int g = 0; g < gen_num; g++) {
//                double gd_flow = gen_sum - gd_rand;
//         
//            int range = (int) gen_sum/(gen_num-g);
//            int gd_rand = getRandomInteger(range, 0);
//            double gd_flow = gen_sum - gd_rand;

//            double gd_flow = gd_rand;
            if (g == (gen_num - 1)) {
                gd_flow = gen_sum;
                gen_sum = gen_sum - gd_flow;
            } else {
//                int range = (int) gen_sum / (gen_num-g+1);
                int gd_rand = getRandomInteger((int)gen_sum, 0);
                gd_flow = gd_rand / (gen_num-g+1);
//                gd_flow = getRandomInteger((int)gen_sum, 0);
                gen_sum = gen_sum - gd_flow;
//                gd_flow = gen_sum / (gen_num - g);
            }

            double mx_flow = gd_flow + 0;
            GenInfo gIn = new GenInfo(G[g], gd_flow, 0.0);
            gen_no.addElement(gIn);
//            gen_sum = gen_sum - gd_flow;

//            gd_flow = gen_sum / (gen_num-g+1);
//                gd_flow = gd_flow
            System.out.println(gen_no.get(g).bus_no + " " + gen_no.get(g).gen_flow );
//            + " " + gen_no.get(g).max_flow);
        }
        System.out.println("gen sum: " + gen_sum);

    }

    public static void LineFlow() {
        DecimalFormat df = new DecimalFormat("####.##");
        int total_line = line_no.size();
        int total_gen = gen_no.size();
        int total_load = ld_no.size();
//        int total_line = 4;

        I = new double[total_line];
        PI = new double[total_line];

        for (int l = 0; l < total_line; l++) {
            double flow_sum_gen = 0;
            double flow_sum_ld = 0;
            for (int g = 0; g < total_gen; g++) {
                int bus_no = gen_no.get(g).bus_no - 1;
                double flow = gen_no.get(g).gen_flow;
                flow_sum_gen = flow_sum_gen + ((-flow) * (S[l][bus_no]));

            }

            for (int ld = 0; ld < total_load; ld++) {
                int bus_no = ld_no.get(ld).bus_no - 1;
                double flow = ld_no.get(ld).load_flow;
                flow_sum_ld = flow_sum_ld + (flow * (S[l][bus_no]));

            }
//            I[l] = POWER * (S[l][GEN_BUS - 1] - S[l][LD_BUS - 2]);
            I[l] = flow_sum_ld + flow_sum_gen;
            System.out.println("Line_flow_" + (l + 1) + ": " + I[l]);
//            int max_limit = getRandomInteger(100, 20);
//            double p_max = I[l] + max_limit;
            FlowInfo fIn = new FlowInfo((l+1), I[l]);
//            
            flow_no.addElement(fIn);
        }

        for (int l = 0; l < total_line; l++) {
            double pi_sum = 0.0;
            for (int k = 0; k < total_line; k++) {
                if (l == k) {
                    continue;
                } else {
//                    double max_cap =flow_no.get(k).max_cap;
//                    double flow = flow_no.get(k).line_flow;
                    if (I[k] < 0) {
//                        p_max = I[k]-10; 
                        I[k] = -I[k];

                    }
//                    flow = 
//                    else{
//                        p_max = I[k]+10; 
//                    }
                    int max_limit = getRandomInteger(100, 20);

                    double p_max = I[k] + max_limit;

//                    p_max = flow_no.get(k).max_cap;
//                    System.out.println("p:" + (I[k]/p_max));
                    pi_sum = pi_sum + pow((I[k] / p_max), (2 * pi_coEff));
//                    pi_sum = pi_sum + pow((flow / max_cap), (2 * pi_coEff));
//                    pi_sum = pi_sum + (I[k] / p_max);
//                    FlowInfo fIn = new FlowInfo((k + 1), I[k], p_max);
//                    flow_no.addElement(fIn);
                }
//                PI[l] = Double.parseDouble(df.format(pi_sum));
//System.out.println("p_s:"+pow((I[k] / I[k]+15.0), (2 * pi_coEff)));
//System.out.println("p_s:"+pi_sum);

            }
            PI[l] = (pi_sum);

            System.out.println("PI_" + (l + 1) + ": " + PI[l]);
        }
//
        FileWriter fw;
        FileWriter fWrite;
        try {

//            System.out.println(sdf.format(cal.getTime()));
            fw = new FileWriter("E:\\TTU\\Research\\KMeansUAV\\input_PI_" + busFile + ".txt");

            //fw.write(("# total_line," + " " + "total_points" + "\n"));
            //fw.write(line_num + " " + (point_count - 1) + "\n");
            //fw.write(("#total_line"+ "\n"));
            fw.write((total_line + "\n"));
            for (int l = 0; l < total_line; l++) {
                //System.out.print("SF for Line_" + (l + 1) + ": ");

                fw.write(PI[l] + "\n");
            }

            fw.close();

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
            fWrite = new FileWriter("E:\\TTU\\Research\\Z3\\UAV\\UAV\\UAV\\bin\\x64\\Debug\\Input\\flow_" + busFile + "_" + sdf.format(cal.getTime()) + ".txt");

            fWrite.write("# gen_bus_no, generation(MW)" + "\n");
            for (int g = 0; g < total_gen; g++) {

//            G[g] = gen_input.get(g).bus_no;
//            L[gen_input.get(g).bus_no - 1] = 0;
//            System.out.println(gen_input.get(g).bus_no + " " + gen_input.get(g).gen_flow + " " + gen_input.get(g).max_flow);
                fWrite.write(gen_no.get(g).bus_no + " " + gen_no.get(g).gen_flow + "\n");

            }
            fWrite.write("\n");

            fWrite.write("# load_bus_no, load(MW)" + "\n");
            for (int ld = 0; ld < total_load; ld++) {

//            G[g] = gen_input.get(g).bus_no;
//            L[gen_input.get(g).bus_no - 1] = 0;
//            System.out.println(gen_input.get(g).bus_no + " " + gen_input.get(g).gen_flow + " " + gen_input.get(g).max_flow);
                fWrite.write(ld_no.get(ld).bus_no + " " + ld_no.get(ld).load_flow + "\n");
            }
            fWrite.write("\n");

            fWrite.write("# line_no, flow(MW)" + "\n");
            for (int ln = 0; ln < total_line; ln++) {

//            G[g] = gen_input.get(g).bus_no;
//            L[gen_input.get(g).bus_no - 1] = 0;
//            System.out.println(gen_input.get(g).bus_no + " " + gen_input.get(g).gen_flow + " " + gen_input.get(g).max_flow);
                fWrite.write(flow_no.get(ln).line_no + " " + df.format(flow_no.get(ln).line_flow) +"\n");
            }
            fWrite.write("\n");
            
            fWrite.write("# line_no, PI_value" + "\n");
            for (int ln = 0; ln < total_line; ln++) {

//            G[g] = gen_input.get(g).bus_no;
//            L[gen_input.get(g).bus_no - 1] = 0;
//            System.out.println(gen_input.get(g).bus_no + " " + gen_input.get(g).gen_flow + " " + gen_input.get(g).max_flow);
                fWrite.write((ln + 1) + " " + PI[ln] +"\n");
            }
            fWrite.write("\n");
            
            

            fWrite.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here

        Scanner sc = new Scanner(System.in);
        System.out.println("Number of Bus");
        int n = sc.nextInt();

        while (n != 5 && n != 14 && n != 30 && n != 57 && n != 118 && n!=300) {
            System.out.println("Enter CORRECT bus number");
            n = sc.nextInt();
        }
// test purpose
//        while (n != 4) {
//            System.out.println("Enter CORRECT bus number");
//            n = sc.nextInt();
//        }

        sc.close();

        input(n);
        ShiftFactor(busFile, zMatFile, sfFile, gfFile, n);

        LineFlow();
    }

}
