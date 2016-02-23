//Swing program to draw the tree in the Panel. 
//Structure of the container window found in RedBlackTreeViewer.java

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

//Panel that contains the tree
public class RedBlackTreePanel extends JPanel {
    private BinaryTreeNode<?> tree;
    private int gridwidth;
    private int gridheight;

    //Map which stores the pixel values for all nodes in the tree
    private Map<BinaryTreeNode<?>, Point> coordinates =
        new HashMap<BinaryTreeNode<?>, Point>();

    //Saving the tree and the drawing parameters
    public RedBlackTreePanel(BinaryTreeNode<?> tree, int gridwidth, int gridheight) {
        this.tree = tree;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    //Redrawing the tree after insertion/deletion
    public void setTree(BinaryTreeNode<?> root) {
        tree = root;
        repaint();
    }

    //Draws the tree in the panel
    //Calculates the coordinates of each node by performing an inorder traversal
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        if (tree == null) {
            return;
        }

        tree.traverseInorder(new BinaryTreeNode.Visitor() {
            private int x = gridwidth;
            public void visit(BinaryTreeNode node) {
                coordinates.put(node, new Point(x, gridheight * (depth(node)+1)));
                x += gridwidth;
            }
        });

        tree.traversePostorder(new BinaryTreeNode.Visitor() {
            public void visit(BinaryTreeNode node) {
                String data = node.getData().toString();
                Point center = (Point)coordinates.get(node);
                if (node.getParent() != null) {
                    Point parentPoint = (Point)coordinates.get(node.getParent());
                    g.setColor(Color.black);
                    g.drawLine(center.x, center.y, parentPoint.x, parentPoint.y);
                }
                FontMetrics fm = g.getFontMetrics();
                Rectangle r = fm.getStringBounds(data, g).getBounds();
                r.setLocation(center.x - r.width/2, center.y - r.height/2);
                Color color = getNodeColor(node);
                Color textColor =
                    (color.getRed() + color.getBlue() + color.getGreen() < 382)
                    ? Color.white
                    : Color.black;
                g.setColor(color);
                g.fillRect(r.x - 2 , r.y - 2, r.width + 4, r.height + 4);
                g.setColor(textColor);
                g.drawString(data, r.x, r.y + r.height);
            }
        });
    }

    //Returns the node color
    Color getNodeColor(BinaryTreeNode<?> node) {
        try {
            Field field = node.getClass().getDeclaredField("color");
            return (Color)field.get(node);
        } catch (Exception e) {
            return Color.white;
        }
    }

    //Returns the depth of a node
    private int depth(BinaryTreeNode<?> node) {
        return (node.getParent() == null) ? 0 : 1 + depth(node.getParent());
    }
}
