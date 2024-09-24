package uk.ac.kingston.ci5105.outliner;

import javax.swing.*;
/**
 * Extends JTree
 * @author Petroc Brown K2124745
 */
public class Tree extends JTree {
    /**
    The Model object used to populate the tree structure and manage the task hierarchy.
    */
    Model model;
    
    /**
    Constructs a new Tree object with the given root task, represented as a Section object.
    The tree is populated with the task hierarchy using a Section object.
    @param task the root task of the tree hierarchy, represented as a Section object
    */
    public Tree(Section task) {
        super(new Model(task));
    }
}