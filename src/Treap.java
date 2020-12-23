public class Treap<E extends Comparable<E>> extends BSTree<E> {
   public Treap() {
      super();
   }

   public Treap(Node<E> root) {
      super(root);
   }

   @Override
   public Node<E> createNewNode(E payload) {
      return new TNode<E>(payload);
   }

   @Override
   public void insert(E payload) {
      // insert the node to the BStree as a leaf node
      TNode<E> newNode = (TNode<E>) createNewNode(payload);
      if (root == null) {
         // none of elements
         root = newNode;
         newNode.setParent(null);
      } else {
         insert(newNode, root, null);
      }
      // assign a random priority
      newNode.setWeight(getRandomWeight());
      // percolate up the tree based on the priority
      percolateUpTree(newNode);
   }

   @Override
   public Node<E> delete(E payload) {
      TNode<E> current = (TNode<E>) search(payload);
      if (current != null) {
         // assign negative infinite priority
         current.setWeight(Integer.MIN_VALUE);
         // percolate down to the leaf node
         percolateDownTree(current);
         TNode<E> parent = current.getParent();
         if (parent.getLeft() == current) {
            parent.setLeft(null);
         } else {
            parent.setRight(null);
         }
         current.setParent(null);
         return current;
      } else {
         return null;
      }
   }

   private int getRandomWeight() {
      return (int) (Math.random() * Integer.MAX_VALUE);
   }

   @Override
   protected void insert(Node<E> newNode, Node<E> node, Node<E> parent) {
      if (node == null) {
         if (newNode.compareTo(parent) < 0) {
            parent.setLeft(newNode);
         } else {
            parent.setRight(newNode);
         }
         // set the parent of the new node
         ((TNode<E>) newNode).setParent(parent);
      } else if (newNode.compareTo(node) < 0) {
         insert(newNode, node.getLeft(), node);
      } else {
         insert(newNode, node.getRight(), node);
      }

   }

   private void percolateUpTree(TNode<E> node) {
      TNode<E> parent = node.getParent();
      while (parent != null) {
         if (compare(parent, node) < 0) {
            // rotate
            if (parent.getLeft() == node) {
               // left child
               rotateRight(parent);
            } else {
               rotateLeft(parent);
            }
            parent = node.getParent();
         } else {
            return;
         }
      }
   }

   private void percolateDownTree(TNode<E> node) {
      TNode<E> child = node.getLeft();
      while (child != null) {
         TNode<E> maxChild = node;
         if (compare(maxChild, child) < 0) {
            maxChild = child;
         }
         TNode<E> rightChild = node.getRight();
         if (compare(maxChild, rightChild) < 0) {
            maxChild = rightChild;
         }

         if (maxChild != node) {
            // rotate
            if (node.getLeft() == maxChild) {
               // rotate right
               rotateRight(node);
            } else {
               // rotate left
               rotateLeft(node);
            }
            child = node.getLeft();
         } else {
            return;
         }
      }
   }

   private int compare(TNode<E> n1, TNode<E> n2) {
      return n1.getWeight() - n2.getWeight();
   }

   private void rotateLeft(TNode<E> pivot) {
      TNode<E> parent = pivot.getParent();
      TNode<E> right = pivot.getRight();
      TNode<E> rightLeftNode = right.getLeft();
      if (parent == null) {
         // pivot is the root
         root = right;
      } else {
         if (parent.getLeft() == pivot) {
            // pivot is left child
            parent.setLeft(right);
         } else {
            // right child
            parent.setRight(right);
         }
      }
      right.setParent(parent);

      // left child of the new pivot
      right.setLeft(pivot);
      pivot.setParent(right);
      pivot.setRight(rightLeftNode);
      if (rightLeftNode != null)
         rightLeftNode.setParent(pivot);
   }

   private void rotateRight(TNode<E> pivot) {
      TNode<E> parent = pivot.getParent();
      TNode<E> left = pivot.getLeft();
      TNode<E> leftRightNode = left.getRight();

      if (parent == null) {
         // pivot is the root
         root = left;
      } else {
         if (parent.getLeft() == pivot) {
            parent.setLeft(left);
         } else {
            parent.setRight(left);
         }
      }
      left.setParent(parent);

      // right child of new pivot
      left.setRight(pivot);
      pivot.setParent(left);
      pivot.setLeft(leftRightNode);
      if (leftRightNode != null)
         leftRightNode.setParent(pivot);
   }
}
