package uk.ac.kingston.ci5105.outliner;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
/**
 * 
 * @author Petroc Brown k2124745
 */
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
    /**
     * Returns the rendering component for the given cell in the JTree.
     * The text colour of the cell is set to green if the task it 
     *  represents is completed, or black if it is not.
     * @param tree the JTree component, represented as a JTree object
     * @param value the value of the cell to render, represented as an Object
     * @param sel
     * @param expanded
     * @param leaf
     * @param row
     * @param hasFocus
     * @return 
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);   
            if (value instanceof Section task) {   
                if (((Section) value).isCompleted() == true){
                    c.setForeground(Color.GREEN); 
                } else {
                    c.setForeground(Color.BLACK); 
                }
            }
        return c;
    }
}