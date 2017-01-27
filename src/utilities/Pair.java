package utilities;

import java.util.Objects;

/**
 * Basic pair of generic objects.
 * 
 * @author Benedict Wilkins
 *
 * @param <I>
 *          class of first object
 * @param <J>
 *          class of second object
 */
public class Pair<I, J> {

  private I o1;
  private J o2;

  /**
   * Constructor.
   * 
   * @param o1
   *          first object of this {@link Pair}
   * @param o2
   *          second object of this {@link Pair}
   */
  public Pair(I o1, J o2) {
    super();
    this.o1 = o1;
    this.o2 = o2;
  }

  public I getO1() {
    return o1;
  }

  public void setO1(I o1) {
    this.o1 = o1;
  }

  public J getO2() {
    return o2;
  }

  public void setO2(J o2) {
    this.o2 = o2;
  }

  @Override
  public String toString() {
    return "[" + o1.toString() + "," + o2.toString() + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(o1, o2);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!Pair.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    final Pair<?, ?> other = (Pair<?, ?>) obj;
    if ((this.o1 == null) ? (other.getO1() != null) : !this.o1.equals(other
        .getO1())) {
      return false;
    }
    if ((this.o2 == null) ? (other.getO2() != null) : !this.o2.equals(other
        .getO2())) {
      return false;
    }
    return true;
  }
}
