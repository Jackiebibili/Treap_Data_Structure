public class TNode<E extends Comparable<E>> extends Node<E> {
   private Node<E> parent;
   private int weight;

   public TNode() {
      this(null);
   }

   public TNode(E load) {
      super(load);
      parent = null;
      weight = 0;
   }

   public TNode<E> getLeft() {
      return (TNode<E>) super.getLeft();
   }

   public TNode<E> getRight() {
      return (TNode<E>) super.getRight();
   }

   public void setParent(Node<E> node) {
      parent = node;
   }

   public TNode<E> getParent() {
      return (TNode<E>) parent;
   }

   public int getWeight() {
      return weight;
   }

   public void setWeight(int w) {
      weight = w;
   }

}
