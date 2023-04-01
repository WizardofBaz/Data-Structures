package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {
    		
    		/* COMPLETE THIS METHOD */
    	
    	//should never throw exception ^
    	
    	Node obj = null;
    	//obj is null
        if(rear == null) 
          {
             throw new NoSuchElementException();
          }
          else
          {
             size--;
             //need to subtract
             //gets rid of junk at the front of the list
             if(rear.next == rear)
             {
                 obj = rear;
                 rear = null;
                 return obj.tree;
             }
             else
             {
                 obj = rear.next;
                 rear.next = rear.next.next;
                 return obj.tree;
                 
                 //don't know about this one ^
             }
         }
     }

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    		/* COMPLETE THIS METHOD */
    	
    	PartialTree result = null;
        boolean check = false;
//here!
     

        if(rear == null)
        {
            throw new NoSuchElementException();
        }
      
        Node ptr = rear.next;
      
        Node prev = rear;
        
        //GOTTA ASK THE PHAM
      
        int i = 0;
      
        result = remover(i, vertex, ptr, check, result, prev);

        return result;

}
    
    private PartialTree remover(int leb,Vertex vert,Node ptr, boolean check,PartialTree result,Node prev)
    {
    	
    	//gotta ask pham about this one too. only returning whole graph for some reason...
    	
            do
             {
                 if(garbageTruck(vert, ptr.tree,check) == true)
                 {
                     if(ptr == rear)
                     {
                         result = ptr.tree;
                       
                         prev.next = rear.next;
                       
                         rear = prev;
                       
                         size--;
                       
                         return result;
                     }
                       else
                     {
                         result = ptr.tree;
                       
                         prev.next = ptr.next;
                       
                         size--;
                       
                         return result;
                     }
         
                 }
                    prev = ptr;
            
                    ptr = ptr.next;
         
                    leb++;
                   
             }while(leb < size);
          
            return result;
        }
    
    private boolean garbageTruck(Vertex vertex, PartialTree Tree, boolean check)
    {
    	//because it traverses lol ^
    	
        while(vertex != null)
        {
        	//go through and it'll be null at the end
        	
            if(vertex == Tree.getRoot())
            {
                check = true;
                return true;
            }
            if(vertex.equals(vertex.parent))
            {
                return false;
            }
      
            vertex = vertex.parent;
        }
        return false;
    }
    		

    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
   
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}
