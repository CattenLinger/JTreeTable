package de.javagl.treetable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import java.awt.*;

/**
 * A TreeCellRenderer that displays a JTree.
 */
public class TreeTableCellRenderer<T> extends JTree implements TableCellRenderer {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -3458046584741784015L;

    private final JTreeTable<T> jTreeTable;
    /**
     * Last table/tree row asked to renderer.
     */
    private int visibleRow;

    /**
     * Creates a new TreeTableCellRenderer that displays the JTree
     * according to the given tree model
     *
     * @param model The tree model
     */
    TreeTableCellRenderer(JTreeTable<T> jTreeTable, TreeModel model) {
        super(model);
        this.jTreeTable = jTreeTable;
    }

    /**
     * updateUI is overridden to set the colors of the Tree's renderer to
     * match that of the table.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        // Make the tree's cell renderer use the table's cell selection
        // colors.
        TreeCellRenderer tcr = getCellRenderer();
        if (tcr instanceof DefaultTreeCellRenderer) {
            DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
            // For 1.1 uncomment this, 1.2 has a bug that will cause an
            // exception to be thrown if the border selection color is
            // null.
            // dtcr.setBorderSelectionColor(null);
            dtcr.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
            dtcr.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
        }
    }

    /**
     * Sets the row height of the tree, and forwards the row height to the
     * table.
     */
    @Override
    public void setRowHeight(int rowHeight) {
        if (rowHeight > 0) {
            super.setRowHeight(rowHeight);
            if (jTreeTable.getRowHeight() != rowHeight) {
                jTreeTable.setRowHeight(getRowHeight());
            }
        }
    }

    /**
     * This is overridden to set the height to match that of the JTable.
     */
    @Override
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, 0, w, jTreeTable.getHeight());
    }

    /**
     * Sublcassed to translate the graphics such that the last visible row
     * will be drawn at 0,0.
     */
    @Override
    public void paint(Graphics g) {
        g.translate(0, -visibleRow * getRowHeight());
        super.paint(g);
    }

    /**
     * TreeCellRenderer method. Overridden to update the visible row.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) setBackground(table.getSelectionBackground());
        else setBackground(table.getBackground());

        visibleRow = row;
        return this;
    }
}
