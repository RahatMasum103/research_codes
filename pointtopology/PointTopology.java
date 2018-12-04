/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointtopology;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author APU
 */
public class PointTopology {

    /**
     * @param args the command line arguments
     */
    public static final int TIME_STEP = 40;
    public static final int SEG_LEN = 1;
    public static final int THRES_TIME = 20;
    public static final int SUR_SCORE = 80;
    public static int line_bus[][];
    //public static int count = 0;

    public static int getRandomInteger(int maximum, int minimum) {
        int number = ((int) (Math.random() * (maximum - minimum))) + minimum;
        return number;
    }

    public static String input_bus_topo(int n) {
        String file = null;
        int bus_number = n;
        System.out.println("bus entered:" + bus_number);

        switch (n) {
            case 5:
                file = "bus_5.csv";
                break;
            case 14:
                file = "bus_14.csv";
                break;
            case 30:
                file = "bus_30.csv";
                break;
            case 57:
                file = "bus_57.csv";
                break;
            case 118:
                file = "bus_118.csv";
                break;
            case 300:
                file = "bus_300.csv";
                break;
            default:
                System.out.println("Bus topology not found");
                break;
        }

        return file;

    }

    public static void csvReaderBus(String fName) {
        String csvFile = fName;

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

                int from_bus = Integer.parseInt(line_info[0]);
                int to_bus = Integer.parseInt(line_info[1]);
                line_bus[i][0] = from_bus;
                line_bus[i][1] = to_bus;
                i++;

            }

//            for(int a=0; a<i; a++)
//            {
//                for (int b=0; b<2;b++)
//                {
//                    System.out.print(line_bus[a][b]+" ");
//                }
//                System.out.println("");
//            }
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

    }

    public static void point_info(int line_num, int max_seg, int nU, int bus) {

        line_bus = new int[line_num][2];
        String fileName = input_bus_topo(bus);
        csvReaderBus(fileName);
        Integer linePoint[][] = new Integer[line_num][max_seg];

        Integer uav[][] = new Integer[nU][5];
        int p_count = bus;
        //int point_count = 1;
        //int temp = 1;

        int p_link_count = 0;

        for (int i = 0; i < line_num; i++) {

            int seg = getRandomInteger(max_seg, 3);
//            Random randomGenerator = new Random(); 
//            
//            int randomInt = randomGenerator.nextInt(5)+1;
            //if(randomInt == 0) continue;

            p_link_count += (seg - 1);
            //point_count = temp;
            //System.out.println("random: " + seg);
            //int pointSet[] = new int[seg];
            linePoint[i][0] = line_bus[i][0];
            linePoint[i][seg - 1] = line_bus[i][1];

            for (int p = 1; p < seg - 1; p++) {

                //linePoint[i][p] = point_count;
                linePoint[i][p] = ++p_count;

                //temp = point_count;
                //pointSet[p]=point_count;
                //++point_count;
            }

        }

        Integer point_link[][] = new Integer[p_link_count][2];

        int inc = 0;
        for (int l = 0; l < line_num; l++) {
            for (int p = 0; p < max_seg - 1; p++) {
                if (linePoint[l][p + 1] != null) {
                    point_link[inc][0] = linePoint[l][p];
                    point_link[inc][1] = linePoint[l][p + 1];
                    ++inc;
                }
            }

        }
        System.out.println("link found...." + inc);

        FileWriter fw;
        try {
            fw = new FileWriter("E:\\TTU\\Research\\Z3\\UAV\\UAV\\UAV\\bin\\x64\\Debug\\Input\\input_L_" + line_num + "_P_" + (p_count) + ".txt");
            //fw.write(("# total_line," + " " + "total_points," + " " +"total_UAVs," +" " + "total_links," + " time_steps"+"\n"));

            fw.write(("# Number of Buses," + " " + "Lines," + " " + "Points," + " " + "and Segments/Links," + " " + "Segment Length in Steps," + " " + "Number of UAVs," + " " + "Total Time Units/Steps" + " " + "\n"));
            fw.write(bus + " " + line_num + " " + (p_count) + " " + (p_link_count) + " " + SEG_LEN + " " + nU + " " + TIME_STEP + "\n");
            fw.write("\n");
            fw.write(("# Line Point Set" + "\n"));
            for (int i = 0; i < line_num; i++) {
                //System.out.print("Line_"+(i+1)+": ");
                for (int p = 0; p < max_seg; p++) {
                    if (linePoint[i][p] != null) {
//                        System.out.print(linePoint[i][p] + " ");
//                        fw.write((i+1)+" "+linePoint[i][p] + " ");
                        fw.write(linePoint[i][p] + " ");
                    }
                }
                fw.write("\n");
            }
            fw.write("\n");

            fw.write(("# Point Links # What is about a corner point/substation that connect more than two lines" + "\n"));

            for (int a = 0; a < p_link_count; a++) {
                for (int b = 0; b < 2; b++) {
                    fw.write(point_link[a][b] + " ");
                }
                fw.write("\n");
            }
//            for (int i = 1; i < point_count-1; i++) {
//                //System.out.print("Line_"+(i+1)+": ");
//                 fw.write(i + " " + (i+1));                  
//                
//                fw.write("\n");
//            }
 fw.write("\n");
            /*
# UAV properties
# Initial Point, Stored Fuel, Fuel Capacity, Mileage (Fuel/Step), Hovering Cost (Fuel/Step)
# A segment can have a distance, while different UAVs can take different time steps 
# (i.e., different Fuel/Step and different Steps/Segment) to fly a segment
             */
            fw.write("# UAV properties\n");
            fw.write("# A segment can have a distance, while different UAVs can take different time steps \n");
            fw.write("# (i.e., different Fuel/Step and different Steps/Segment) to fly a segment\n");            
            fw.write("# Initial Point, Stored Fuel, Fuel Capacity, Mileage (Fuel/Step), Hovering Cost (Fuel/Step)\n");
            //fw.write(("# placed_point, fuel_capacity, mileage(step/fuel), base_fuel" + "\n"));
            for (int i = 0; i < nU; i++) {
                uav[i][0] = getRandomInteger(1,5);
                uav[i][1] = 100;
                uav[i][2] = 1000;
                uav[i][3] = getRandomInteger(10, 3);
                uav[i][4] = 2;
                fw.write(uav[i][0] + " " + uav[i][1] + " " + uav[i][2] + " " + uav[i][3] + " " +uav[i][4]+" " + "\n");
            }
            fw.write("\n");
            
            fw.write("# Distance from a Point to the Base (or a Refueling) Station\n");
            fw.write("# The base station's point number will be the last after the total points\n");
            for(int p=0;p<p_count;p++)
            {
                fw.write(getRandomInteger(8, 4)+" ");
            }            
            fw.write("\n\n");
            
            fw.write("# Threshold Time between two Consecutive Visits to a Point (under Surveillance)\n");
            fw.write(THRES_TIME+" ");            
            fw.write("\n\n");
            
            fw.write("# Resiliency Requirements\n");
            fw.write("# Visits by k different UAVs within a Threshold Time between k Consecutive Visits to a Point\n");
            fw.write(2 + " " +THRES_TIME*2);            
            fw.write("\n\n");
            
            fw.write("# Minimum Criticality Scores under Continuous Surveillance and Resilient Surveillance\n");
            fw.write(SUR_SCORE+" "+SUR_SCORE/2);
            fw.write("\n");
            
            
            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println("Total Point: " + (point_count - 1));
    }

//    public static void main(String[] args) {
//        // TODO code application logic here
////        int point_count = 1;
////        int temp =1;
////        linePoint = new Integer[20][5];
//        
//          point_info(14, 4);
//
//    }
//    BufferedReader br = null;
//    String line = "";
//    String cvsSplitBy = " ";
    /*
        try {

            
            br = new BufferedReader(new FileReader("input.txt"));
            int i = 1;
            int point_count = 20;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] line_info = line.split(cvsSplitBy);
                //System.out.println("test..........."+line_info[0]);
              
                int fromBus = Integer.parseInt(line_info[0]);
                int toBus = Integer.parseInt(line_info[1]);
                int point [];
                
                if(i%2 == 0)
                {
                    point = new int[3];
                    for(int k=0;k<3;k++)
                    {
                        point[k] = point_count++;
                    }
                }
                else
                {
                    point = new int[2];
                    for(int k=0;k<2;k++)
                    {
                        point[k] = point_count++;
                    }
                }
                System.out.println("numbers"+point_count);
                
                System.out.println("bus"+ fromBus + " "+toBus);
                
                
//                int random_point = getRandomInteger(20,10);
//                System.out.println("between: "+random_point);
//                point.add(fromBus);


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
     */
//            Random rn = new Random();
//        int n = 50 - 21 + 1;
//        for(int i=1;i<=20;i++)
//        
//        {
////            System.out.println("point: "+ getRandomInteger(50, 21));
//        
//        int random_point = rn.nextInt() % n;
////                int random_point = 50 + (int)(Math.random() * 21);
//        System.out.println("ran"+ random_point);
//        } 
//             Random rn = new Random();
//        int n = 50 - 21 + 1;
//        int i = rn.nextInt() % n;
//        int random_point = 50 + (int)(Math.random() * 21);
//        System.out.println("ran"+ random_point);
}
