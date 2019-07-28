import edu.princeton.cs.algs4.Point2D;
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
}
