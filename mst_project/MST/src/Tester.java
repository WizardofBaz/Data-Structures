import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import apps.MST;
import apps.PartialTree;
import apps.PartialTreeList;
import structures.Graph;

public class Tester
{
   public static void main(String[] args) throws IOException
   {
       Scanner sc = new Scanner(System.in);
       System.out.print("Enter file name:");
       String file = sc.nextLine();
       Graph graph = new Graph(file);
       PartialTreeList ptl = MST.initialize(graph);
       ArrayList<PartialTree.Arc> a = MST.execute(ptl);
  //     graph.print();
       ptl.remove();
      
   }

}


