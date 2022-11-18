/*
 * www.javagl.de - JTreeTable
 *
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */
package de.javagl.treetable.test;

import de.javagl.treetable.JTreeTable;
import de.javagl.treetable.TreeTableModel;

import javax.swing.*;

/**
 * A simple example application for the JTreeTable
 */
public class JTreeTableSimpleExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JTreeTableSimpleExample::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreeTableModel<String> treeTableModel = ExampleTreeTableModels.createSimple();
        JTreeTable<String> treeTable = new JTreeTable<>(treeTableModel);
        f.getContentPane().add(new JScrollPane(treeTable));

        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
