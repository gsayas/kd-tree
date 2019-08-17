import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.stream.StreamSupport;
import org.junit.Assert;
import org.junit.Test;

public class KdTreeTest {

  @Test
  public void testIsEmpty() {
    KdTree tree = new KdTree();
    Assert.assertTrue(tree.isEmpty());
    tree.insert(new Point2D(1,1));
    Assert.assertFalse(tree.isEmpty());
  }

  @Test
  public void testSize() {
    KdTree tree = new KdTree();
    Assert.assertEquals(0, tree.size());
    tree.insert(new Point2D(1,1));
    Assert.assertEquals(1, tree.size());
    tree.insert(new Point2D(1,1));
    Assert.assertEquals(1, tree.size());
    tree.insert(new Point2D(1,2));
    Assert.assertEquals(2, tree.size());
  }

  @Test
  public void testContains() {

    KdTree tree = new KdTree();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 2);
    Point2D p3 = new Point2D(1, 3);

    assertFalse(tree.contains(p1));

    tree.insert(p1);
    assertTrue(tree.contains(p1));
    assertTrue(tree.contains(p2));

    assertFalse(tree.contains(p3));
    tree.insert(p3);
    assertTrue(tree.contains(p3));
  }

  @Test
  public void testRange() {

    KdTree tree = new KdTree();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 3);
    tree.insert(p1);
    tree.insert(p2);

    RectHV rect = new RectHV(1, 1, 1, 2);
    assertEquals(1, StreamSupport.stream(tree.range(rect).spliterator(), false).count());
  }
}
