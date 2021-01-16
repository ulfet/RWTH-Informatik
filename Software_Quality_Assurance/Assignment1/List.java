package de.rwth.swc.sqa.examples.readability;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public class List {

  private static final Item EOL = null;
  private final Item head;

  private List(final Item head) {
    this.head = head;
  }

  public static List of(final String... values) {
    Item head = EOL;
    if (values != null) {
      for (int i = values.length - 1; i >= 0; i--) {
        head = new Item(values[i], head);
      }
    }
    return new List(head);
  }

  /* Badly written code. It has even 3 bugs in it. */
//  public boolean containsValue(String value, int maxNodes) {
//    Item node = this.head;
//    do {
//      if(value().equals(node.value())) {
//        return true;
//      }
//      node = node.next();
//    } while(node != null && --maxNodes > 0);
//
//    return false;
//  }

  /* A better version, but still not good enough */
//  public boolean hasValue(String value, int maxNodes) {
//    Item node = this.head;
//    while (node != EOL && maxNodes-- > 0) {
//      if (node.value().equals(value)) {
//        return true;
//      }
//      node = node.next();
//    }
//    return false;
//  }

  /**
   * Search through the list for given value while considering only itemLimit.
   *
   * @param value search value
   * @param itemLimit number of items to consider
   * @return true, if value is in peek of this list
   */
  public boolean containsValue(final String value, final int itemLimit) {
    return this.itemsStream()
        .limit(itemLimit)
        .anyMatch(itemValueEqualsTo(value));
  }

  private Predicate<Item> itemValueEqualsTo(final String value) {
    return item -> item.value().equals(value);
  }

  private Stream<Item> itemsStream() {
    final Builder<Item> itemStreamBuilder = Stream.builder();
    Item node = this.head;
    while (!this.endOfList(node)) {
      itemStreamBuilder.add(node);
      node = node.next();
    }
    return itemStreamBuilder.build();
  }

  @Override
  public String toString() {
    final String itemsAsString = this.itemsStream()
        .map(Item::value)
        .collect(Collectors.joining(","));
    return '[' + itemsAsString + ']';
  }

  private boolean endOfList(Item node) {
    return node == EOL;
  }
}
