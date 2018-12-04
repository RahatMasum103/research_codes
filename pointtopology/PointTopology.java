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
    public static int getRandomInteger(int maximum, int minimum) {
        int number = ((int) (Math.random() * (maximum - minimum))) + minimum;
        return number;
    }

    public static void point_info(int line_num, int max_seg) {
        Integer linePoint[][] = new Integer[line_num][max_seg];
        int point_count = 1;
        int temp = 1;
        for (int i = 0; i < line_num; i++) {

            int seg = getRandomInteger(max_seg, 2);
//            Random randomGenerator = new Random(); 
//            
//            int randomInt = randomGenerator.nextInt(5)+1;
            //if(randomInt == 0) continue;
            point_count = temp;
            //System.out.println("random: " + seg);
            int pointSet[] = new int[seg];
            for (int p = 0; p < seg; p++) {
                linePoint[i][p] = point_count;

                temp = point_count;
                //pointSet[p]=point_count;
                ++point_count;
            }

        }
        
        FileWriter fw;
        try {
            fw = new FileWriter("L_" + line_num + "_P_" + (point_count - 1) + ".txt");
            fw.write(("# total_line," + " " + "total_points" + "\n"));
            fw.write(line_num + " " + (point_count - 1) + "\n");
            
            fw.write(("#point_set"+ "\n"));
            for (int i = 0; i < line_num; i++) {
                //System.out.print("Line_"+(i+1)+": ");
                for (int p = 0; p < max_seg; p++) {
                    if (linePoint[i][p] != null) {
                        //System.out.print(linePoint[i][p] + " ");
//                        fw.write((i+1)+" "+linePoint[i][p] + " ");
                        fw.write(linePoint[i][p] + " ");
                    }                    
                }
                fw.write("\n");
            }
            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println("Total Point: " + (point_count - 1));
    }

    public static void main(String[] args) {
        // TODO code application logic here
//        int point_count = 1;
//        int temp =1;
//        linePoint = new Integer[20][5];
        
          point_info(14, 4);

    }
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
