package de.rwth.swc.sqa.examples.readability;

public class Item {

  private final String value;
  private final Item next;

  Item(final String value, final Item next) {
    this.value = value;
    this.next = next;
  }

  public Item next() {
    return this.next;
  }

  public String value() {
    return this.value;
  }
}
