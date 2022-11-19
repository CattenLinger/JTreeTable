/*
 * www.javagl.de - JTreeTable
 *
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */
package de.javagl.treetable.test;

import de.javagl.treetable.AbstractTreeTableModel;
import de.javagl.treetable.TreeTableModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Methods to create simple {@link TreeTableModel} instances
 */
public class ExampleTreeTableModels {
    static class ExampleValueNode<T>{
        public List<ExampleValueNode<T>> children = new LinkedList<>();
        public T value;

        public ExampleValueNode(T value) {
            this.value = value;
        }

        public static ExampleValueNode<String> createExample() {
            ExampleValueNode<String> root = new ExampleValueNode<>("root");
            root.children.add(new ExampleValueNode<>("child 1"));
            root.children.add(new ExampleValueNode<>("child 2"));
            ExampleValueNode<String> child3 = new ExampleValueNode<>("child 3");
            child3.children.add(new ExampleValueNode<>("child 1"));
            child3.children.add(new ExampleValueNode<>("child 2"));
            child3.children.add(new ExampleValueNode<>("child 3"));
            root.children.add(child3);
            return root;
        }
    }

    static class ExampleComplexTreeModel extends AbstractTreeTableModel<ExampleValueNode<String>> {
        private final String[] columnNames = new String[] { "Title", "Description" };
        private final Class<?>[] columnClasses = new Class[] { TreeTableModel.class, String.class };

        /**
         * Default constructor
         *
         * @param root The root node of the tree
         */
        protected ExampleComplexTreeModel(ExampleValueNode<String> root) {
            super(root);
        }


        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int column) {
            return columnClasses[column];
        }

        @Override
        public Object getValueAt(ExampleValueNode<String> node, int column) {
            switch (column) {
                case 0: return node.value;
                case 1: return "column " + column;
                default: throw new IndexOutOfBoundsException();
            }
        }

        @Override
        protected int childCountOf(ExampleValueNode<String> parent) {
            return parent.children.size();
        }

        @Override
        protected ExampleValueNode<String> childOf(int index, ExampleValueNode<String> parent) {
            return parent.children.get(index);
        }
    };

    public static TreeTableModel<ExampleValueNode<String>> createComplex() {
        ExampleValueNode<String> tree = ExampleValueNode.createExample();
        return new ExampleComplexTreeModel(tree);
    }

    static class ExampleSimpleTreeModel extends AbstractTreeTableModel<String> {
        /**
         * Default constructor
         *
         * @param root The root node of the tree
         */
        protected ExampleSimpleTreeModel(String root) {
            super(root);
        }

        @Override
        protected int childCountOf(String parent) {
            if (parent.startsWith("root")) return 3;
            if (parent.startsWith("child")) return 2;
            return 0;
        }

        @Override
        protected String childOf(int index, String parent) {
            if (parent.startsWith("root")) return "child " + index;
            if (parent.startsWith("child")) return "leaf " + index;
            return null;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(String node, int column) {
            return node + ", column " + column;
        }

        @Override
        public String getColumnName(int column) {
            return "column" + column;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            if (column == 0) {
                return TreeTableModel.class;
            }
            return Object.class;
        }
    }

    /**
     * Create a simple dummy {@link TreeTableModel}
     *
     * @return The {@link TreeTableModel}
     */
    public static TreeTableModel<String> createSimple() {
        return new ExampleSimpleTreeModel("root");
    }
}
