import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.stream.StreamSupport;
import org.junit.Test;

public class PointSETTest {

  @Test
  public void testIsEmpty() {

    PointSET set = new PointSET();

    assertTrue(set.isEmpty());
    set.insert(new Point2D(1.0, 1.0));
    assertFalse(set.isEmpty());

  }

  @Test
  public void testSize() {

    PointSET set = new PointSET();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 2);
    Point2D p3 = new Point2D(1, 3);

    set.insert(p1);
    assertEquals(1, set.size());

    set.insert(p1);
    assertEquals(1, set.size());

    set.insert(p2);
    assertEquals(1, set.size());

    set.insert(p3);
    assertEquals(2, set.size());

  }

  @Test
  public void testContains() {

    PointSET set = new PointSET();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 2);
    Point2D p3 = new Point2D(1, 3);

    set.insert(p1);
    assertTrue(set.contains(p1));

    //set.insert(p2);
    assertTrue(set.contains(p2));

    assertFalse(set.contains(p3));
  }

  @Test
  public void testRange() {

    PointSET set = new PointSET();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 3);
    set.insert(p1);
    set.insert(p2);

    RectHV rect = new RectHV(1, 1, 1, 2);
    assertEquals(1, StreamSupport.stream(set.range(rect).spliterator(), false).count());
  }

  @Test
  public void testNearest() {

    PointSET set = new PointSET();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 3);
    Point2D p3 = new Point2D(1, 4);
    Point2D p4 = new Point2D(1, 1.9);

    set.insert(p1);
    set.insert(p2);
    set.insert(p3);
    set.insert(p4);

    System.out.println(set.nearest(p1));
    assertTrue(p4.equals(set.nearest(p1)));
  }
}
