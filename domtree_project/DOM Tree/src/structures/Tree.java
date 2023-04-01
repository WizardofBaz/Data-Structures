package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		/** COMPLETE THIS METHOD **/
		root = new TagNode ("html", null, null);
		Stack<TagNode> tagStack = new Stack <TagNode> ();
		tagStack.push(root);
		TagNode body = new TagNode ("body", null, null);
		root.firstChild = body;
		tagStack.push(body);
		sc.nextLine();
		sc.nextLine();
		while (sc.hasNextLine() == true) {
			String current = sc.nextLine();
			if (current.charAt(0) == '<' && current.charAt(1) == '/') {
				tagStack.pop();
				continue;
			}
			if (current.equals("<p>") || current.equals("<em>") || current.equals("<b>") || current.equals("<table>")|| current.equals("<tr>")|| current.equals("<td>")|| current.equals("<ol>")|| current.equals("<ul>")|| current.equals("<li>")) {
				current = current.substring(1, current.length()-1);
				TagNode newNode = new TagNode (current,null,null);
				if (tagStack.peek().firstChild == null) {
					tagStack.peek().firstChild = newNode;
				} else {
					TagNode ptr = tagStack.peek().firstChild;
					while (ptr.sibling != null) {
						ptr = ptr.sibling;
					}
					ptr.sibling = newNode;
				}
				tagStack.push(newNode);
			
			}else {
				
				TagNode newNode = new TagNode (current,null,null);
				TagNode topStack = tagStack.peek();
				if (topStack.firstChild == null) {
					topStack.firstChild = newNode;
				} else {
					TagNode ptr = topStack.firstChild;
					while (ptr.sibling != null) {
						ptr= ptr.sibling;
					}
					ptr.sibling = newNode;
				}
					
			}
				
		}
	}

	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) { //gotta fix
		/** COMPLETE THIS METHOD **/
		if ((oldTag.equals("p") || oldTag.equals("b") || oldTag.equals("em"))
				&& (newTag.equals("p") || newTag.equals("b") || newTag.equals("em"))){
			replacer(oldTag, newTag, root);
		}
		else if ((oldTag.equals("ol") || oldTag.equals("ul")) && (newTag.equals("ol") || newTag.equals("ul"))){
			replacer(oldTag, newTag, root);
		}
		else {
			return;
		}
	}
		
		private static void replacer(String oldT, String newT, TagNode theRoot){
			
			//the base case
			if (theRoot == null){
				return;
			}
			if (theRoot.tag.equals(oldT) && theRoot.firstChild != null){
				theRoot.tag = newT;
			}
			replacer(oldT, newT, theRoot.firstChild);
			replacer(oldT, newT, theRoot.sibling);
			return;
		}

	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {    //not sure what the problem is
		/** COMPLETE THIS METHOD **/
		TagNode curr = new TagNode(null, null, null);										
		TagNode temp;
		
		
		curr = boldRower(root);
		if (curr == null) {
			//if no table in html
			System.out.println("No table found");
			return;
		}
		
		
		curr = curr.firstChild;
		
		//traversal
		for(int i = 1; i < row; i++) {
			curr = curr.sibling;
		} 
		
		//traversal
		for (temp = curr.firstChild; temp != null; temp = temp.sibling) {
			temp.firstChild = new TagNode("b", temp.firstChild, null);
			
		}

	}
	
	private TagNode boldRower(TagNode curr) { //ask professor about this one
		// Base Case
		if (curr == null)
			return null; 
		
		TagNode nodetemp = null;
		String strtemp = curr.tag;
		
		if(strtemp.equals("table")) { 
			nodetemp = curr; 
			return nodetemp;
		} 
		
		// Traverse the tree
		if(nodetemp == null) {
			nodetemp = boldRower(curr.firstChild);
		}
		
		if(nodetemp == null) { 
			nodetemp = boldRower(curr.sibling);
		} 
		
		
		
		return nodetemp;
	}
	
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) { //I think this works
		/** COMPLETE THIS METHOD **/

		if(tag.equalsIgnoreCase("p") || tag.equalsIgnoreCase("em") || tag.equalsIgnoreCase("b")){ // first set of stuff
			remover2(root,root.firstChild,tag);
		}
		if(tag.equalsIgnoreCase("ol") || tag.equalsIgnoreCase("ul")){ // second set of stuff
			remover3(root,root.firstChild,tag);
		}
	}
	
	private void remover(TagNode node) {
		//base case
		if (node == null)
			return;
		else if (node.tag.equalsIgnoreCase("li")) //can't remove li
			node.tag = "p";
		remover(node.sibling);
	}
	
	private void remover2(TagNode prev, TagNode curr, String tag){
		//base case
		if(curr==null || prev == null){
			return;
		}
		String RT = curr.tag;
		if(RT.equalsIgnoreCase(tag) && curr.firstChild != null){
			if(prev.firstChild==curr){
				prev.firstChild.tag = curr.firstChild.tag;
				if(curr.firstChild.sibling == null){	
					curr.firstChild = null;
				}
				else{
					curr.firstChild = curr.firstChild.sibling;
					TagNode temp = curr.sibling;
					TagNode temp2 = curr.firstChild;
					curr.firstChild = null;
					curr.sibling = temp2;
					TagNode ptr = curr.sibling;
					while(ptr.sibling != null){
						ptr = ptr.sibling;
					}
					ptr.sibling = temp;
				}
			}
			if(prev.sibling == curr){
				curr.tag = curr.firstChild.tag;
				if(curr.firstChild.sibling == null){
					curr.firstChild = null;
				}
				else{
					curr.firstChild = curr.firstChild.sibling;
					TagNode temp = curr.sibling;
					TagNode temp2 = curr.firstChild;
					curr.firstChild = null;
					curr.sibling = temp2;
					TagNode ptr = curr.sibling;
					while(ptr.sibling != null){
						ptr = ptr.sibling;
					}
					ptr.sibling = temp;
				}
			}	
		}

		//recursion
		remover2(curr,curr.firstChild,tag);
		remover2(curr,curr.sibling,tag);
	}
	
	private void remover3(TagNode prev, TagNode curr, String tag){
		if(curr==null || prev == null){
			return;
		}
		String CT = curr.tag;
		if(CT.equalsIgnoreCase(tag) && curr.firstChild != null){
			if(prev.sibling == curr){
				remover(curr.firstChild);
				prev.sibling = curr.firstChild;
			}
			if(prev.firstChild == curr){
				remover(curr.firstChild);
				TagNode temp = curr.sibling;
				TagNode ptr = curr.firstChild;
				while(ptr.sibling != null){
					ptr = ptr.sibling;
				}
				prev.firstChild = curr.firstChild;
				ptr.sibling = temp;
			}	
		}
		//recursion
		remover3(curr,curr.firstChild,tag);
		remover3(curr,curr.sibling,tag);
	}
	
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */

	
	public void addTag(String word, String tag) { //crazy
		/** COMPLETE THIS METHOD **/
		adder(root,root.firstChild,word,tag);
	}
	
	private void adder(TagNode prev, TagNode curr, String word, String tag){
		if(curr==null || prev == null){
			return;
		}
		String CT = curr.tag;
		if(moreThanOneWord(CT) == false){
			if(CT.charAt(CT.length()-1) == '!' || CT.charAt(CT.length()-1) == '.' || CT.charAt(CT.length()-1) == ',' || 
					CT.charAt(CT.length()-1) == '?' || CT.charAt(CT.length()-1) == ':' || CT.charAt(CT.length()-1) == ';'){
				CT = CT.substring(0, CT.length()-1);
			}
			//need to ask professor about this
			if(CT.equalsIgnoreCase(word) && curr.firstChild == null){
				TagNode newNode = new TagNode(tag,curr,null);
				if(prev.firstChild == curr){
					prev.firstChild = newNode;
				}
				if(prev.sibling == curr){
					prev.sibling = newNode;
				}		
			}
		}
		else if (moreThanOneWord(CT) == true){
			String grammer = "";
			String temp = CT;
			String temp2 = "";
			boolean punk = false;
			String[] arr = CT.split(" ");
			int spot = 0;
			for(int i = 0; i<arr.length; i++){
				if(arr[i].toLowerCase().contains(word)){
					spot = i;
					break;
				}
			}
			
			//all depends on where the word is!

			if(arr[spot].charAt(arr[spot].length()-1) == '!' || arr[spot].charAt(arr[spot].length()-1) == '.' || arr[spot].charAt(arr[spot].length()-1) == ',' || 
					arr[spot].charAt(arr[spot].length()-1) == '?' || arr[spot].charAt(arr[spot].length()-1) == ':' || arr[spot].charAt(arr[spot].length()-1) == ';'){
				temp2 = arr[spot];
				grammer = arr[spot].substring(arr[spot].length()-1);
				punk = true;
				arr[spot] = arr[spot].substring(0, arr[spot].length()-1);
			}
			if(arr[spot].equalsIgnoreCase(word) || temp2.equalsIgnoreCase(word)){
				if(spot > 0 && spot < arr.length-1){ 
					if(punk == true){
						TagNode secondHalf = new TagNode(temp.substring(temp.indexOf(word) + word.length()+1),null,null);
						TagNode newNode = new TagNode(arr[spot] + grammer,null,null);
						TagNode tagNode = new TagNode(tag,newNode,secondHalf);
						TagNode firstHalf = new TagNode(temp.substring(0, temp.indexOf(arr[spot])),null,tagNode);
						if(prev.firstChild == curr){
							prev.firstChild = firstHalf;
						}
						if(prev.sibling == curr){
							prev.sibling = firstHalf;
						}
					}
					else{
						TagNode secondHalf = new TagNode(temp.substring(temp.indexOf(word) + word.length()),null,null);
						TagNode newNode = new TagNode(arr[spot],null,null);
						TagNode tagNode = new TagNode(tag,newNode,secondHalf);
						TagNode firstHalf = new TagNode(temp.substring(0, temp.indexOf(arr[spot])),null,tagNode);
						if(prev.firstChild == curr){
							prev.firstChild = firstHalf;
						}
						if(prev.sibling == curr){
							prev.sibling = firstHalf;
						}
					}

				}
				if(spot == 0){ 
					if(punk == true){
					TagNode newNode = new TagNode(arr[spot] + grammer,null,null);
					TagNode secondHalf = new TagNode(temp.substring(temp.indexOf(word) + word.length()+2),null,null);
					TagNode tagNode = new TagNode(tag,newNode,secondHalf);

					if(prev.firstChild == curr){
						prev.firstChild = tagNode;
					}
					if(prev.sibling == curr){
						prev.sibling = tagNode;
					}
					}
					else{
						TagNode newNode = new TagNode(arr[spot],null,null);
						TagNode secondHalf = new TagNode(temp.substring(temp.indexOf(word) + word.length()+1),null,null);
						TagNode tagNode = new TagNode(tag,newNode,secondHalf);

						if(prev.firstChild == curr){
							prev.firstChild = tagNode;
						}
						if(prev.sibling == curr){
							prev.sibling = tagNode;
						}	
						
					}
				}
				if(spot == arr.length-1){ 
					if(punk == true){
					TagNode newNode = new TagNode(arr[spot]+ grammer,null,null);
					TagNode tagNode = new TagNode(tag,newNode,null);
					TagNode firstHalf = new TagNode(temp.substring(0, temp.indexOf(arr[spot])),null,tagNode);

					if(prev.firstChild == curr){
						prev.firstChild = firstHalf;
					}
					if(prev.sibling == curr){
						prev.sibling = firstHalf;
					}
					}
					else{
						TagNode newNode = new TagNode(arr[spot],null,null);
						TagNode tagNode = new TagNode(tag,newNode,null);
						TagNode firstHalf = new TagNode(temp.substring(0, temp.indexOf(arr[spot])),null,tagNode);

						if(prev.firstChild == curr){
							prev.firstChild = firstHalf;
						}
						if(prev.sibling == curr){
							prev.sibling = firstHalf;
						}
						
					}

				}
			}
		}
	
	adder(curr,curr.firstChild,word,tag);
	adder(curr,curr.sibling,word,tag);

}

	private boolean moreThanOneWord(String x){
		String[] y = x.split(" ");
		if(y.length > 1){
			return true;
		}
		else{
			return false;
		}
	}
		
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
