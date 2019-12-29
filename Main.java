class Main {
  public static void main(String[] args) {

    AVL avl = new AVL();

    avl.insert(30);
    avl.insert(20);
    avl.insert(10);
    avl.insert(5);
    
    avl.levelOrder();
    
    System.out.println("Deleting 10");
    avl.delete(10);
    
    avl.levelOrder();
  }
}