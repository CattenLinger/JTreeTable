package de.javagl.treetable;

import javax.swing.*;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

/**
 * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel to
 * listen for changes in the ListSelectionModel it maintains. Once a change
 * in the ListSelectionModel happens, the paths are updated in the
 * DefaultTreeSelectionModel.
 */
class ListToTreeSelectionModelWrapper<T> extends DefaultTreeSelectionModel {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -3150534152238745922L;

    private final JTreeTable<T> jTreeTable;
    /**
     * Set to true when we are updating the ListSelectionModel.
     */
    protected boolean updatingListSelectionModel;

    /**
     * Default constructor
     */
    ListToTreeSelectionModelWrapper(JTreeTable<T> jTreeTable) {
        this.jTreeTable = jTreeTable;
        getListSelectionModel().addListSelectionListener(e -> updateSelectedPathsFromSelectedRows());
    }

    /**
     * Returns the list selection model. ListToTreeSelectionModelWrapper
     * listens for changes to this model and updates the selected paths
     * accordingly.
     *
     * @return The list selection model
     */
    ListSelectionModel getListSelectionModel() {
        return listSelectionModel;
    }

    /**
     * This is overridden to set <code>updatingListSelectionModel</code> and
     * message super. This is the only place DefaultTreeSelectionModel
     * alters the ListSelectionModel.
     */
    @Override
    public void resetRowSelection() {
        if (!updatingListSelectionModel) {
            updatingListSelectionModel = true;
            try {
                super.resetRowSelection();
            } finally {
                updatingListSelectionModel = false;
            }
        }
        // Notice how we don't message super if
        // updatingListSelectionModel is true. If
        // updatingListSelectionModel is true, it implies the
        // ListSelectionModel has already been updated and the
        // paths are the only thing that needs to be updated.
    }

    /**
     * If <code>updatingListSelectionModel</code> is false, this will reset
     * the selected paths from the selected rows in the list selection
     * model.
     */
    protected void updateSelectedPathsFromSelectedRows() {
        if (!updatingListSelectionModel) {
            updatingListSelectionModel = true;
            try {
                // This is way expensive, ListSelectionModel needs an
                // enumerator for iterating.
                int min = listSelectionModel.getMinSelectionIndex();
                int max = listSelectionModel.getMaxSelectionIndex();

                clearSelection();
                if (min != -1 && max != -1) {
                    for (int counter = min; counter <= max; counter++) {
                        if (listSelectionModel.isSelectedIndex(counter)) {
                            TreePath selPath = jTreeTable.tree.getPathForRow(counter);

                            if (selPath != null) {
                                addSelectionPath(selPath);
                            }
                        }
                    }
                }
            } finally {
                updatingListSelectionModel = false;
            }
        }
    }

}
