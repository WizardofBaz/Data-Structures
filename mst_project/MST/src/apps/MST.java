package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		/* COMPLETE THIS METHOD */
		PartialTreeList list = new PartialTreeList();
		   Vertex[] vert= graph.vertices;
		   PartialTree tree;
		   boolean[] checked = new boolean[vert.length];
		   PartialTree.Arc arc = null;
		   int counter = 0;
		   //ask prof if this loop is right...
		   for(int i =0 ; i < vert.length;i++)
		   {
		       Vertex.Neighbor neigh = vert[i].neighbors;
		       tree = new PartialTree(vert[i]);
		       checked[i] = true;
		       System.out.println(vert[i]);
		       
		       //where the code will keep track. have to remove from here
		       MinHeap<PartialTree.Arc> P = tree.getArcs(); 
		      
		       while(neigh!= null)
		       {
		       arc = new PartialTree.Arc(vert[i], neigh.vertex,neigh.weight);
		       
		       //rt gotta fix

		       P.insert(arc);
		       P.siftDown(counter);
		       neigh = neigh.next;
		  

		       }
		       if(checked[i] == true)
		       {
		           list.append(tree);
		       }
		       counter++;

		      
		   }
		  
		       return list;
		  
		}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	
	
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		
		ArrayList<PartialTree.Arc> list = new ArrayList();  
	      
	       while(ptlist.size()>1)
	       {
	          
	           PartialTree sim = ptlist.remove(); 
	      
	           PartialTree.Arc embi = null;
	          
	           PartialTree.Arc arcs = sim.getArcs().getMin();
	          
	           Vertex v1 = arcs.v1;
	          
	           Vertex v2 = arcs.v2;
	      
	           //gotta make sure this one works here
	           checkerX2(sim,v1,v2,embi,arcs);
	         
	            embi = sim.getArcs().deleteMin(); 
	          
	         
	           System.out.println(embi);
	      
	         //get the tree with this... use little uzi
	           PartialTree PTY = ptlist.removeTreeContaining(embi.v2);           
	          
	            sim.merge(PTY);
	          
	            list.add(embi);
	          
	            ptlist.append(sim);
	          

	       }
	          
	       return list;
	   }
	//ttp!
	
	private static void checkerX2(PartialTree sim, Vertex v1,Vertex v2, PartialTree.Arc embi, PartialTree.Arc arcs)
	   {
	   
		//is it...
		while(comparer(v2, sim) == true)
	       {
	       embi = sim.getArcs().deleteMin();    
	      
	       arcs = sim.getArcs().getMin();
	       //needs work ^
	     
	       v1 = arcs.v1;
	     
	       v2 = arcs.v2;
	       }
	  
	   }
	
	  private static boolean comparer(Vertex v2, PartialTree sim)
	   {
	        while(v2 != null)
	        {
	            if(sim.getRoot() == v2)
	            {
	                return true;
	            }
	            //v2 = v2.parent or other way?
	            
	            if(v2.equals(v2.parent))
	            {
	                return false;
	            }
	            v2 = v2.parent;
	        }
	        return false;
	    }
	}