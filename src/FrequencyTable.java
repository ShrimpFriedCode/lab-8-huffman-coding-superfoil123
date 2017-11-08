import java.util.HashMap;

public class FrequencyTable extends HashMap<Character, Integer> {
  /**
   * Constructs an empty table.
   */
  public FrequencyTable() {
    super();
  }
    
  /**
   *
   * Constructs a table of character counts from the given text string.
   */
  public FrequencyTable(String text) {
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      put(ch, get(ch) + 1);
    }
  }
  
  /**
   *
   * Returns the count associated with the given character. In the case that
   * there is no association of ch in the map, return 0.
   */
  @Override
  public Integer get(Object ch) {
    Integer ans = super.get(ch);
    if (ans == null)
      return 0;
    return ans;
  }
}
