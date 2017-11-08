import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * The keys in the heap must be stored in an array.
 * 
 * There may be duplicate keys in the heap.
 * 
 * The constructor takes an argument that specifies how objects in the 
 * heap are to be compared. This argument is a java.util.Comparator, 
 * which has a compare() method that has the same signature and behavior 
 * as the compareTo() method found in the Comparable interface. 
 * 
 * Here are some examples of a Comparator<String>:
 *    (s, t) -> s.compareTo(t);
 *    (s, t) -> t.length() - s.length();
 *    (s, t) -> t.toLowerCase().compareTo(s.toLowerCase());
 *    (s, t) -> s.length() <= 3 ? -1 : 1;  
 */

public class Heap<E> implements PriorityQueue<E> {
  private List<E> keys;
  private Comparator<E> comparator;
  
  /**
   *
   * Creates a heap whose elements are prioritized by the comparator.
   */
  public Heap(Comparator<E> comparator) {
    keys = new ArrayList<>();
    this.comparator = comparator;
  }

  /**
   * Returns the comparator on which the keys in this heap are prioritized.
   */
  public Comparator<E> comparator() {
    return comparator;
  }
  
  // DELETE FROM STARTER
  private boolean comesBefore(E e1, E e2) {
    return comparator.compare(e1, e2) < 0;
  }

  /**
   *
   * Returns the top of this heap. This will be the highest priority key. 
   * @throws NoSuchElementException if the heap is empty.
   */
  public E peek() {
    if (keys.isEmpty())
      throw new NoSuchElementException();
    return keys.get(0);
  }

  /**
   *
   * Inserts the given key into this heap.
   */
  public void insert(E key) {
    keys.add(key); 
    siftUp(size() - 1);
  }

  /**
   *
   * Removes and returns the highest priority key in this heap.
   * @throws NoSuchElementException if the heap is empty.
   */
  public E delete() {
    if (keys.isEmpty())
      throw new NoSuchElementException();
    int n = keys.size();
    E min = keys.get(0);
    keys.set(0, keys.get(n - 1));
    keys.remove(n - 1);
    siftDown(0);
    return min;
  }

  /**
   *
   * Restores the heap property by sifting the key at position p down
   * into the heap.
   */
  public void siftDown(int p) {
    int n = keys.size();
    int left = getLeft(p);
    while (left < n) {
      int right = getRight(p);
      int smallestChild = left;
      if (right < n && comesBefore(keys.get(right), keys.get(left)))
        smallestChild = right;
      if (comesBefore(keys.get(smallestChild), keys.get(p))) 
        swap(p, smallestChild);
      p = smallestChild;
      left = getLeft(p);
    }
  }
  
  /**
   *
   * Restores the heap property by sifting the key at position q up
   * into the heap. (Used by insert()).
   */
  public void siftUp(int q) {
    int p = getParent(q);
    E key = keys.get(q);
    while (q > 0 && !comesBefore(keys.get(p), key)) {
      swap(p, q);
      q = p;
      p = getParent(p);
    }
  }

  /**
   *
   * Exchanges the elements in the heap at the given indices in keys.
   */
  public void swap(int i, int j) {
    if (i != j) {
      E temp = keys.get(i);
      keys.set(i, keys.get(j));
      keys.set(j, temp);
    }
  }
  
  /**
   * Returns the number of keys in this heap.
   */
  public int size() {
    return keys.size();
  }

  /**
   * Returns a textual representation of this heap.
   */
  public String toString() {
    return keys.toString();
  }
  
  /**
   *
   * Returns the index of the left child of p.
   */
  public static int getLeft(int p) {
    return 2 * p + 1;
  }

  /**
   *
   * Returns the index of the right child of p.
   */
  public static int getRight(int p) {
    return 2 * (p + 1);
  }

  /**
   *
   * Returns the index of the parent of p.
   */
  public static int getParent(int p) {
    return (p - 1) / 2;
  }
}
