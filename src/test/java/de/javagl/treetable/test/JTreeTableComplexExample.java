package de.javagl.treetable.test;

import de.javagl.treetable.JTreeTable;
import de.javagl.treetable.TreeTableModel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class JTreeTableComplexExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JTreeTableComplexExample::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreeTableModel<ExampleTreeTableModels.ExampleValueNode<String>> treeTableModel = ExampleTreeTableModels.createComplex();
        JTreeTable<ExampleTreeTableModels.ExampleValueNode<String>> treeTable = new JTreeTable<>(treeTableModel);

        // We need to set a cell renderer to tree for rendering model correctly
        treeTable.getTree().setCellRenderer(new DefaultTreeCellRenderer() {
            @SuppressWarnings("unchecked")
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                setText(treeTableModel.getValueAt((ExampleTreeTableModels.ExampleValueNode<String>) value, 0).toString());
                return c;
            }
        });

        f.getContentPane().add(new JScrollPane(treeTable));

        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
