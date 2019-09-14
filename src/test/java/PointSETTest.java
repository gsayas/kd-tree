import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;
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
    //set.insert(p4);

    System.out.println(set.nearest(p4));
    assertTrue(p1.equals(set.nearest(p4)));
  }

  @Test
  public void testNearest2() {
    PointSET set = new PointSET();

    List<Point2D> points = new ArrayList<>();
    points.add( new Point2D(0.0, 0.25));
    points.add( new Point2D(0.25, 0.5));
    points.add( new Point2D(0.25, 0.0));
    points.add( new Point2D(0.75, 0.5));
    points.add( new Point2D(0.25, 0.75));
    points.add( new Point2D(0.0, 1.0));
    points.add( new Point2D(0.75, 0.25));
    points.add( new Point2D(0.5, 1.0));
    points.add( new Point2D(1.0, 0.5));

    points.stream().forEach(point -> set.insert(point));
    Point2D query = new Point2D(0.25, 0.75);
    Point2D expectedNearest = new Point2D(0.25, 0.75);

    System.out.println(set.nearest(query));
    assertTrue(expectedNearest.equals(set.nearest(query)));
  }

  @Test
  //Sep14 14:00pm
  public void testNearest3() {
    PointSET set = new PointSET();

    List<Point2D> points = new ArrayList<>();
    points.add( new Point2D(1.0, 0.25));
    points.add( new Point2D(0.375, 1.0));
    points.add( new Point2D(0.625, 1.0));
    points.add( new Point2D(1.0, 0.875));
    points.add( new Point2D(0.625, 0.125));
    points.add( new Point2D(0.625, 0.5));
    points.add( new Point2D(1.0, 0.25));
    points.add( new Point2D(0.25, 0.5));
    points.add( new Point2D(0.5, 0.0));
    points.add( new Point2D(0.125, 0.125));
    points.add( new Point2D(0.0, 0.125));
    points.add( new Point2D(0.375, 0.125));
    points.add( new Point2D(0.375, 0.375));
    points.add( new Point2D(0.25, 0.5));
    points.add( new Point2D(1.0, 0.0));

    points.stream().forEach(point -> set.insert(point));
    Point2D query = new Point2D(0.25, 0.0);
    Point2D expectedNearest = new Point2D(0.125, 0.125);

    System.out.println(set.nearest(query));
    assertTrue(expectedNearest.equals(set.nearest(query)));
  }
}
