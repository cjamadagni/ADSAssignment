
/* Constructing the swing application which will show all RB Tree Operations */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;


public class RedBlackTreeViewer extends JFrame {
    RedBlackTree tree = new RedBlackTree();

    JFrame frame = new JFrame("Red Black Tree");
    JTextField valueField = new JTextField(20);
    JPanel buttonPanel = new JPanel();
    RedBlackTreePanel panel = new RedBlackTreePanel(null, 40, 40);
    JScrollPane displayArea = new JScrollPane();
    JLabel messageLine = new JLabel();


    // Input is taken from the text field. Each button is an action listener
    // which performs a specific operation after being clicked.    
    private abstract class Operation implements ActionListener {
        public Operation(String label) {
            JButton button = new JButton(label);
            buttonPanel.add(button);
            button.addActionListener(this);
        }
        public void actionPerformed(ActionEvent event) {
            String value = valueField.getText();
            messageLine.setText("");
            try {execute(value);} catch (Exception e) {e.printStackTrace();}
            // Updating the picture and returning the cursor to the text field.
            panel.setTree(tree.getRoot());
            valueField.requestFocus();
            valueField.selectAll();
        }
        protected abstract void execute(String value);
    }

    //Constructing the viewer window
    public RedBlackTreeViewer() {
        JPanel valuePanel = new JPanel();
        valuePanel.add(new JLabel("Enter Value: "));
        valuePanel.add(valueField);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(0, 1));
        controlPanel.add(valuePanel);
        controlPanel.add(buttonPanel);

        // Panel Size. 
        panel.setPreferredSize(new Dimension(2068, 2068));
        panel.setBackground(Color.lightGray);
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        displayArea.setViewportView(panel);

        frame.setBackground(Color.blue);
        frame.getContentPane().add(controlPanel, "North");
        frame.getContentPane().add(displayArea, "Center");
        frame.getContentPane().add(messageLine, "South");
        frame.pack();

        new Operation("Insert") {
            protected void execute(String value) {
                tree.add(value);}};
        // new Operation("Add All") {
        //     protected void execute(String value) {
        //         for (String s: value.split("\\s+")) tree.add(s);}};
        new Operation("Search") {
            protected void execute(String value) {
                messageLine.setText("The value \"" + value + "\" is " +
                    (tree.contains(value) ? "" : "not ") + "found");}};
        new Operation("Delete") {
            protected void execute(String value) {
                tree.remove(value);
                if(!tree.contains(value)) {
                    messageLine.setText("Element not present in the tree");

                }
            }};


    }

    //Main application
    public static void main(String[] args) {
        RedBlackTreeViewer viewer = new RedBlackTreeViewer();
        viewer.frame.setSize(540, 480);
        viewer.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewer.frame.setVisible(true);
    }
}
