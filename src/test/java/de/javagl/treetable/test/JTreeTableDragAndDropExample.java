/*
 * www.javagl.de - JTreeTable
 *
 * Copyright (c) 2019 Marco Hutter - http://www.javagl.de
 */
package de.javagl.treetable.test;

import de.javagl.treetable.JTreeTable;
import de.javagl.treetable.TreeTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.IOException;

/**
 * A simple example application for the JTreeTable, showing how to
 * drag-and-drop the cell contents (as a string) to a label.
 */
public class JTreeTableDragAndDropExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JTreeTableDragAndDropExample::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new BorderLayout());

        TreeTableModel<String> treeTableModel = ExampleTreeTableModels.createSimple();
        JTreeTable<String> treeTable = new JTreeTable<>(treeTableModel);
        f.getContentPane().add(new JScrollPane(treeTable), BorderLayout.CENTER);

        installDragHandling(treeTable);

        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createTitledBorder("Drop here"));
        label.setPreferredSize(new Dimension(300, 300));
        f.getContentPane().add(label, BorderLayout.EAST);

        installDropHandling(label);

        f.setSize(800, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static void installDragHandling(JTable table) {
        table.setCellSelectionEnabled(true);
        DragGestureListener dragGestureListener = dge -> {
            Point cell = getSelectedCell(table);
            if (cell == null) {
                return;
            }
            int row = cell.x;
            int col = cell.y;

            // Start dragging the value at (row,col)
            Object value = table.getValueAt(row, col);
            String valueString = String.valueOf(value);
            Transferable transferable =
                    new StringSelection(valueString);
            dge.startDrag(null, transferable);
        };
        DragSource source = DragSource.getDefaultDragSource();
        source.createDefaultDragGestureRecognizer(table,
                DnDConstants.ACTION_COPY, dragGestureListener);
    }

    private static Point getSelectedCell(JTable table) {
        if (table.getSelectedRowCount() != 1) {
            return null;
        }
        if (table.getSelectedColumnCount() != 1) {
            return null;
        }
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        return new Point(row, col);
    }

    private static String extractString(Transferable transferable) {
        try {
            Object result =
                    transferable.getTransferData(DataFlavor.stringFlavor);
            return (String) result;
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void installDropHandling(JLabel label) {
        DropTargetListener dropTargetListener = new DropTargetListener() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                Transferable transferable = dtde.getTransferable();
                String resultString = extractString(transferable);
                label.setText(resultString);
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {
                // Nothing to do here
            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                // Nothing to do here
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
                // Nothing to do here
            }

            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                // Nothing to do here
            }
        };
        new DropTarget(label, dropTargetListener);
    }
}
