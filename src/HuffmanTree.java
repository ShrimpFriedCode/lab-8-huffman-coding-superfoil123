import jdk.nashorn.internal.objects.NativeFloat32Array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

/*
 * 
 * A HuffmanTree represents a variable-length code such that the shorter the
 * bit pattern associated with a character, the more frequently that character
 * appears in the text to be encoded.
 */

public class HuffmanTree {
  
  class Node {
    protected char key; //char
    protected int priority; //count
    protected Node left, right;
    
    public Node(int priority, char key) {
      this(priority, key, null, null);
    }
    
    public Node(int priority, Node left, Node right) {
      this(priority, '\0', left, right);
    }
    
    public Node(int priority, char key, Node left, Node right) {
      this.key = key;
      this.priority = priority;
      this.left = left;
      this.right = right;
    }
    
    public boolean isLeaf() {
      return left == null && right == null;
    }
    
    public String toString() {
      return String.format("%s:%f", key, priority);
    }
  }
  
  protected Node root;
  
  /*
   * Creates a HuffmanTree from the given frequencies of letters in the
   * alphabet using the algorithm described in lecture.
   */
  public HuffmanTree(FrequencyTable charFreqs) {
    Comparator<Node> comparator = (x, y) -> {
      /*
       *  x comes before y if x's count is less than y's count
       **/
      if(x.priority < y.priority){
        return -1;
      }
      else{
        return 1;
      }
    };
    
    PriorityQueue<Node> forest = new Heap<Node>(comparator);

    /*
     * Start by populating forest with leaves.
     */

    //populate with leaves
    Set<Character> s = charFreqs.keySet();
    for(char c : s){
      forest.insert(new Node(charFreqs.get(c), c));
    }//done, working, tested

    while(forest.size() != 1){

      Node n1 = forest.delete();
      Node n2 = forest.delete();

      Node combined = new Node((n1.priority + n2.priority), n1, n2);
      forest.insert(combined);

    }
    //dwt

    root = forest.delete();
    //dwt

  }


  
  /*
   * 
   * Returns the character associated with the prefix of bits.
   * 
   * @throws DecodeException if bits does not match a character in the tree.
   */
  public char decodeChar(String bits) throws DecodeException {
    ArrayList<Integer> path = new ArrayList<Integer>();

    for(int i = 0; i < bits.length(); i++){//convert string into path we will take on tree
      path.add(Integer.parseInt(String.valueOf(bits.charAt(i))));
    }//dwt

    Node curr = root;

    for(int i : path){
      if(i == 0){//if 0, go left
        curr = curr.left;
      }
      else{//must be , go right
        curr = curr.right;
      }
    }//dw

    if(curr.key == '\0'){
      throw new DecodeException(bits);
    }
    else{
      return curr.key;
    }//dw

  }
    
  /*
   * 
   * Returns the bit string associated with the given character. Must
   * search the tree for a leaf containing the character. Every left
   * turn corresponds to a 0 in the code. Every right turn corresponds
   * to a 1. This function is used by CodeBook to populate the map.
   * 
   * @throws EncodeException if the character does not appear in the tree.
   */
  public String lookup(char ch) throws EncodeException{

    String ret = lookHelper(ch, root, "");

    if(ret == null){
      throw new EncodeException(ch);
    }
    else{
      return ret;
    }

  }

  public String lookHelper(char c, Node n, String path){

    String ret = "";

    if(!n.isLeaf()){
      if((ret = lookHelper(c, n.left, path + '0')) == null){
        ret = lookHelper(c, n.right, path + '1');
      }
    }
    else{
      if(c == n.key){
        ret = path;
      }
      else{
        ret = null;
      }
    }
    return ret;
  }

}

