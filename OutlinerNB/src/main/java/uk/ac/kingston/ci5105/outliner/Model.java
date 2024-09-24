package uk.ac.kingston.ci5105.outliner;

import java.util.ArrayList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Implements TreeModel
 * The tree uses a section as the root node
 * @author Petroc Brown k2124745
 */
public class Model implements TreeModel {
    
    private Section task;
    /**
     * Constructs the model with the root tasks
    */
    public Model(Section task){this.task = task;}
    /**
     * override
     * Constructs the model with the root tasks
     * @return the root task of the task hierarchy, represented as a Section object
    */    
    @Override
    public Object getRoot() {
        return task;
    }
    /**
     * override
     * @param parent the parent task, represented as an Object
     * @param index the index of the child task to retrieve
     * @return the child task at the given index of the parent task, represented as an Object
    */    
    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof Section task1) {
            ArrayList<Section> test = task1.getTasks();
            return test.get(index);
        }
        return null;
    }
    /**
     * override
     * @param parent the parent task, represented as an Object
     * @return the number of child tasks of the given parent task, represented as an int
    */    
    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof Section) {
            Section task = (Section) parent;
            return task.getTasks().size();
        }
        return 0;
    }
    /**
     * Returns whether the given node is a leaf node or not.
     * @param node the node to check, represented as an Object
     * @return true if the given node is a leaf node, false otherwise 
     */
    @Override
    public boolean isLeaf(Object node) {
        if (node instanceof Section task1){
        if (task1.getTasks().isEmpty()){
            return true;
        }
        }
        return false;
    }


    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        // not implemented
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        // not implemented
        return 0;
    }
    @Override
    public void addTreeModelListener(TreeModelListener l) {
        // not implemented
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        // not implemented
    }
}
