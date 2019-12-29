import java.util.*;


public class AVL {

  private Node root;

  public AVL(){
    root = null;
  }

//MARK: Tree Traverses
//----------------------------------------------------------------//
  public void inOrder(){
      inOrder(root);
  }

  private void inOrder(Node root){

    if(root.left != null)
      inOrder(root.left);

     System.out.print("\n" + root.data + " ");

    if(root.right != null)
      inOrder(root.right);
  }

  public void levelOrder(){

    Queue<Node> queue = new LinkedList<>();
    queue.add(root);

    while(!queue.isEmpty()){

      Node temp = queue.remove();

      System.out.print(temp.data + " ");

      if(temp.left != null){
        queue.add(temp.left);
      }

      if(temp.right != null){
        queue.add(temp.right);
      }
    }
  }

  //MARK: Tree Insertion
  //-----------------------------------------------------------------//

  public boolean insert(int data){

    Node newNode = new Node(data);
    boolean result = true;
    
    if(root == null)
       root =  newNode;
	else {
		try {
			root = insert(root, newNode);
		} catch (Exception e) {
			result = false;
		}
	}
    
    return result;
  }

  private Node insert(Node root,Node newNode) throws Exception{

    if(root.data == newNode.data){
       throw new Exception("Duplicates are NOT allow");
    }
    else if(newNode.data < root.data && root.left == null){
      root.left = newNode;
    }
    else if(newNode.data > root.data && root.right == null){
      root.right = newNode;
    }
    else if(root.left != null && newNode.data < root.data){  
      insert(root.left,newNode);
    }
   else if(root.right != null && newNode.data >  root.data){ 
      insert(root.right,newNode);
    }
    update(root);
    root =  balance(root);
    
    return root;
  }


  //MARK: Tree Deletion
  //-----------------------------------------------------------------//
  public void delete(int data){
   root = delete(root,data);
  }

  private Node delete(Node root, int data){

    if(root.left != null && data < root.data){ 			 //Travel left subtree
       root.left = delete(root.left,data);
    } else if(root.right != null && data > root.data){   //Travel right subtree
        root.right = delete(root.right,data);
    } else if(root.data == data) {						 //Found the node to delete. 
   
        if(root.left == null){ 			//Deleting node with right child
          root = root.right;
          
        } else if(root.right == null){  //Deleting node with left child
          root = root.left;
        } else { 						//Deleting node with two childs. 

          //Get replacement node

          //Because we are getting it from the left subtree we have to take largest value

          int replacement = getMax(root.left);
          root.data = replacement;
          root.left = delete(root.left,replacement);
        }
    }
    
    if(root != null) {
	    update(root);
	    root = balance(root);
    }
    
    return root;
  }

  //MARK: Min and Max
//--------------------------------------------------------------------------//
  public int getMax(){
    return getMax(root);
  }

  public int getMin(){
    return getMin(root);
  }

  private int getMax(Node root){

    Node current = root;

    while(current.right != null){
      current = current.right;
    }

    return current.data;
  }

  private int getMin(Node root){

    Node current = root;

    while(current.left != null){
      current = current.left;
    }

    return current.data;
  }


  //MARK: AVL Tree rotations and updates
  //------------------------------------------------------------------------//

  private void update(Node node){
	  

    int leftNodeH = node.left == null ? -1 : node.left.height;
    int rightNodeH = node.right == null ? -1: node.right.height;

    //Update this node height
    node.height = Math.max(leftNodeH, rightNodeH) + 1;
  }

  private Node balance(Node node){

    if(node.getBF() <= -2){ //Subtree is right heavy
        if(node.right.getBF() >= 0){
          node = rotateLeft(node);
        } else { //Perform double rotation R-L rotation
         Node tempNode =  rotateRight(node.right); 
         node.right = tempNode;
         node = rotateLeft(node);
        }
    }else if(node.getBF() >= 2){ //Subtree is left heavy
        if(node.left.getBF() >= 0){
           node = rotateRight(node);
        }else {
        	
          Node tempNode = rotateLeft(node.left);
          node.left = tempNode;
          node = rotateRight(node);
        }
    }
    
    return node;
  }


  //Rotate right =      .
  //                  .    .
  //                .        
  //              .

  private Node rotateRight(Node root){

    Node temp;

    if(root != null && root.left != null){
      
      temp = root.left;
      root.left = temp.right;
      temp.right = root;

      update(root);  //Update height and balance factor
      update(temp);  //Update height and balance factor

      root = temp;
    }else{
      System.out.println("Error! from left rotation");
    }
    
    return root;
  }

  //Rotate Left =       .
  //                  .    .
  //                         .
  //                           .

  private Node rotateLeft(Node root){

    Node temp;

    if(root != null && root.right != null){

      temp = root.right;
      root.right = temp.left;
      temp.left = root;

      update(root);
      update(temp);

      root = temp;
    }
    
    return root;
  }


//MARK: Node class for AVL creation
//-----------------------------------------------------------------//
  private class Node {

    int data;   
    int height; //Height of the node

    Node left;
    Node right;

    public Node(int newData){
      data = newData;
      height = 0;
      left = null;
      right = null;
    }

    public void display(){
      System.out.println("Data: " + data);
      System.out.println("h: " + height);
      System.out.println("l: " + left);
      System.out.println("r: " + right);
    }

    public int getBF(){
        int leftH = left == null ? -1: left.height;
        int rightH = right == null ? -1: right.height;

        return leftH-rightH;
    }
  }
}