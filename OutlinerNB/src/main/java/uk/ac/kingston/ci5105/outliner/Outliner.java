package uk.ac.kingston.ci5105.outliner;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Outliner.java is the main entry point of the Outliner Application
 * It instantiates and initialises the Section and Tree model
 * It uses anonymous classes to implement the view 
 * @author Petroc Brown K2124745
 */
public class Outliner {

    private static boolean showTags = false;
    private static boolean showPriority = false;
    private static boolean showDate = false;

    public static boolean isShowTags() {
        return showTags;
    }
    
    public static boolean isShowPriority() {
        return showPriority;
    }
    
    public static boolean isShowDate() {
        System.out.println("pass");
        return showDate;
    }
    

    /**
     * @param args the command line arguments
     * This class starts when the application starts
     * It's job is to initialise the model and the view
     * It uses anonymous function for the controller.
     */
    public static void main(String[] args) {
        // link list
        JFrame frame = new JFrame("Outliner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        JPanel panel = new JPanel();

        Section root = new Section("Root", "root", 0, "01/01/2022");
        Tree tree = new Tree(root);
        tree.setCellRenderer(new CustomTreeCellRenderer());
        ArrayList<Section> t = root.getTasks();
        for (Section ta : t) {
            System.out.println(ta.getTitle());

        }
        /**
         * Anonymous function
         * Adds the show tags buttons
         * Uses action event from checkbox to change toString() functionality
         */
        JCheckBox showTagsbutton = new JCheckBox("show tags");
        showTagsbutton.addActionListener((ActionEvent e) -> {
            if (showTagsbutton.isSelected()) {
                showTags = true;
                tree.updateUI();
            } else {
                showTags = false;
                tree.updateUI();
            }
        });
        JCheckBox showPriorityButton = new JCheckBox("show priority");
        showPriorityButton.addActionListener((ActionEvent e) -> {
            if (showPriorityButton.isSelected()) {
                showPriority = true;
                tree.updateUI();
            } else {
                showPriority = false;
                tree.updateUI();
            }
        });
        
        JCheckBox showDateButton = new JCheckBox("show date");
        showDateButton.addActionListener((ActionEvent e) -> {
            System.out.println("pass2");
            if (showDateButton.isSelected()) {
                showDate = true;
                tree.updateUI();
            } else {
                showDate = false;
                tree.updateUI();
            }
        });
         /**
         * Anonymous function
         * Adds the Create new section button
         * Uses action event from button to create a new window with a form
         */
        JButton newTask = new JButton("Create new Section");
        newTask.addActionListener((ActionEvent e) -> {
            JFrame frame1 = new JFrame();
            JPanel panel1 = new JPanel();
            JLabel titleLabel = new JLabel("Title:");
            JLabel tagsLabel = new JLabel("Tags:");
            JLabel priorityLabel = new JLabel("Priority:");
            JLabel dateLabel = new JLabel("Date:");
            JTextField tf1 = new JTextField(20);
            JTextField tf2 = new JTextField(20);
            JTextField tf3 = new JTextField(20);
            JTextField tf4 = new JTextField(20);
            /**
            * Anonymous function
            * Adds the add section button
            * Uses action event to take the inputs from the form and creates a 
            *   new section instance
            */
            JButton button = new JButton("add section");
            button.addActionListener((ActionEvent e1) -> {
                try{
                String title = tf1.getText();
                String tags = tf2.getText();
                String priority = tf3.getText();
                String date = tf4.getText();
                Section node = (Section) tree.getLastSelectedPathComponent();
                node.getTasks().add(new Section(title, tags, Integer.parseInt(priority), date));
                tree.updateUI();
                } catch (NullPointerException ex){
                String title = tf1.getText();
                String tags = tf2.getText();
                String priority = tf3.getText();
                String date = tf4.getText();
                root.addTask(new Section(title, tags, Integer.parseInt(priority), date));
                tree.updateUI();
                }
            });
            panel1.add(titleLabel);
            panel1.add(tf1);
            panel1.add(tagsLabel);
            panel1.add(tf2);
            panel1.add(priorityLabel);
            panel1.add(tf3);
            panel1.add(dateLabel);
            panel1.add(tf4);
            panel1.add(button);
            frame1.add(panel1);
            frame1.setTitle("Create New Section");
            frame1.setSize(300, 200);
            frame1.setLocationRelativeTo(null);
            frame1.setVisible(true);
        });
         /**
         * Anonymous function
         * Adds the Edit section button
         * Uses action event from button to create a new window with a form
         */
        JButton edit = new JButton("Edit section");
        edit.addActionListener((ActionEvent e) -> {
            try{
            Section task = (Section) tree.getLastSelectedPathComponent();
            JFrame frame1 = new JFrame();
            JLabel titleLabel = new JLabel("Title:");
            JLabel tagsLabel = new JLabel("Tags:");
            JLabel priorityLabel = new JLabel("Priority:");
            JLabel dateLabel = new JLabel("Date:");
            JTextField title = new JTextField(task.getTitle(),20);
            JTextField tags = new JTextField(task.getTags(),20);
            JTextField priority = new JTextField(String.valueOf(task.getPriority()),20);
            JTextField date = new JTextField(task.getDate(),20);
            JCheckBox completed = new JCheckBox("Completed", task.isCompleted());
            /**
            * Anonymous function
            * Adds the edit section button
            * Uses action event to take the inputs from the form and edits an 
            *   existing section instance
            */
            JButton button = new JButton("edit section");
            button.addActionListener((ActionEvent e1) -> {
                task.setTitle(title.getText());
                task.setTags(tags.getText());
                task.setPriority(Integer.parseInt(priority.getText()));
                task.setDate(date.getText());
                task.setCompleted(completed.isSelected());
                tree.updateUI();
            });
            JPanel panel1 = new JPanel();
            panel1.add(titleLabel);
            panel1.add(title);
            panel1.add(tagsLabel);
            panel1.add(tags);
            panel1.add(priorityLabel);
            panel1.add(priority);
            panel1.add(dateLabel);
            panel1.add(date);
            panel1.add(completed);
            panel1.add(button);


            frame1.add(panel1);
            frame1.setTitle("Edit Section");
            frame1.setSize(300, 200);
            frame1.setLocationRelativeTo(null);
            frame1.setVisible(true);
            } catch (NullPointerException ex){
                JFrame frame1 = new JFrame();
                frame1.setLayout(new FlowLayout()); 
                frame1.setSize(300, 150);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
                JLabel error = new JLabel("ERROR");
                JLabel message = new JLabel("Please select a section to edit");
                error.setFont(new Font(error.getFont().getFontName(),Font.PLAIN,18));
                frame1.add(error);
                frame1.add(message);
                
            }
        });
         /**
         * Anonymous function
         * Adds the Delete section button
         * Uses action event from button to remove the section from its parents
         *  tasks Array List
         */
        JButton delete = new JButton("Delete Section");
        delete.addActionListener((ActionEvent e) -> {
        try{    
            if ((root.getTasks().indexOf(tree.getLastSelectedPathComponent()) != -1)){
                Section child = (Section) tree.getLastSelectedPathComponent();
                root.getTasks().remove(child);
            } else {
                  Section child = (Section) tree.getLastSelectedPathComponent();      
                Section parent = root.getParent(child);
                parent.getTasks().remove(child);
            }
        } catch (NullPointerException ex){
                JFrame frame1 = new JFrame();
                frame1.setLayout(new FlowLayout()); 
                frame1.setSize(300, 150);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
                JLabel error = new JLabel("ERROR");
                JLabel message = new JLabel("Please select a section to delete");
                error.setFont(new Font(error.getFont().getFontName(),Font.PLAIN,18));
                frame1.add(error);
                frame1.add(message);
        }
        tree.updateUI();
        });
         /**
         * Anonymous function
         * Adds the UP button
         * Uses action event from button to either move its place in its parents
         *  Tasks array list or move it up one level depending its location in
         *  the Tasks array list
         */
        JButton up = new JButton("\u2191");
        up.addActionListener((ActionEvent e) -> {
            Section child = (Section) tree.getLastSelectedPathComponent();
            System.out.println(tree.getLastSelectedPathComponent());
            if (child != null) {
                try{
                
                Section parent = root.getParent(child);
                int index = parent.getTasks().indexOf(child);
                if (index > 0) {
                    Section prevChild = parent.getTasks().get(index - 1);
                    parent.getTasks().set(index - 1, child);
                    parent.getTasks().set(index, prevChild);

                } 
                } catch (NullPointerException ex){
                    int index = root.getTasks().indexOf(child);
                    if (index > 0) {
                    Section prevChild = root.getTasks().get(index - 1);
                    root.getTasks().set(index - 1, child);
                    root.getTasks().set(index, prevChild);

                } 
                }
                tree.updateUI();
            }
        });
        /**
         * Anonymous function
         * Adds the DOWN button
         * Uses action event from button to either move its place in its parents
         *  Tasks array list or move it down one level depending its location in
         *  the Tasks array list
         */
        JButton down = new JButton("\u2193");
        down.addActionListener((ActionEvent e) -> {
            Section child = (Section) tree.getLastSelectedPathComponent();

            if (child != null) {
                try{
                Section parent = root.getParent(child);
                int index = parent.getTasks().indexOf(child);

                if (index < parent.getTasks().size() - 1) {
                    Section nextChild = parent.getTasks().get(index + 1);
                    parent.getTasks().set(index + 1, child);
                    parent.getTasks().set(index, nextChild);
                    tree.updateUI();
                } 
                } catch (NullPointerException ex){
                        int index = root.getTasks().indexOf(child);
                    if (index < root.getTasks().size() - 1) {
                    Section nextChild = root.getTasks().get(index + 1);
                    root.getTasks().set(index + 1, child);
                    root.getTasks().set(index, nextChild);
                    tree.updateUI();
                        }
                }
            }
        });
        JButton left = new JButton("\u2190");
        left.addActionListener((ActionEvent e) -> {
            Section child = (Section) tree.getLastSelectedPathComponent();
                Section parent = root.getParent(child);
                try {
                        root.getParent(parent).getTasks().add(child);
                } catch (NullPointerException ex) {
                    if (!root.getTasks().contains(child)) {
                            root.getTasks().add(child); 
                        }
                    }
                parent.getTasks().remove(child);
                tree.updateUI();
        });
        JButton right = new JButton("\u2192");
        right.addActionListener((ActionEvent e) -> {
            Section sec = (Section) tree.getLastSelectedPathComponent();

            tree.updateUI();
            if (root.getTasks().indexOf(tree.getLastSelectedPathComponent()) != -1){
                if (root.getTasks().indexOf(sec) > 0){
                    root.getTasks().get(root.getTasks().indexOf(sec)-1).addTask(sec);
                    root.getTasks().remove(sec);
            }
            } else {
                Section parent = root.getParent(sec);
                if (parent.getTasks().indexOf(sec) > 0){
                    parent.getTasks().get(parent.getTasks().indexOf(sec)-1).addTask(sec);
                    parent.getTasks().remove(sec);
            }
            }
            tree.updateUI();
        });
        /**
         * Anonymous function
         * Adds the Import button
         * Uses action event from button to load a file chooser which allows
         *  user to choose a file to import
         * File is converted from JSON to my section model
         */
        JButton imp = new JButton("Import");
        imp.addActionListener((ActionEvent e) -> {
            JFrame frame1 = new JFrame();
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(frame1);
            if (result == JFileChooser.APPROVE_OPTION) {
                ArrayList<Section> x = root.getTasks();
                x.clear();
                root.setTasks(x);
                File selectedFile = chooser.getSelectedFile();
                try ( Reader reader = new FileReader(selectedFile)) {
                    JSONObject json = new JSONObject(new JSONTokener(reader));
                    Section rootTask = Section.parseTask(json);
                    for (Section child : rootTask.getTasks()){
                        root.addTask(child);
                    }
                    //root.addTask(rootTask);
                    tree.updateUI();

                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                }

            }
            //for 
        });
        /**
         * Anonymous function
         * Adds the Save button
         * Uses action event from button to open a file chooser which allows
         *  user to choose a file to save a tree
         * File is converted from section model to JSON
         */
        JButton save = new JButton("Save");
        save.addActionListener((ActionEvent e) -> {
            JFileChooser saveChooser = new JFileChooser();
            int saveResult = saveChooser.showSaveDialog(panel);
            if (saveResult == JFileChooser.APPROVE_OPTION) {
                File selectedFile = saveChooser.getSelectedFile();
                try {
                    Section rootTask = root;
                    JSONObject json = rootTask.toJSON();
                    FileWriter writer = new FileWriter(selectedFile);
                    writer.write(json.toString());
                    writer.flush();
                    writer.close();
                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
        /**
         * Anonymous function
         * Adds the Completed button
         * Uses action event from button to set the completion satus of the section
         */        
        JButton completed = new JButton("Completed");
        completed.addActionListener((ActionEvent e) -> {
            Section task = (Section) tree.getLastSelectedPathComponent();
            if (task != null) {
                task.setCompleted(true);
            }
            tree.updateUI();
        });
       
        /**
         * Adding all created components to the frame and panel to be viewed 
         */ 
        JPanel directionPanel = new JPanel(new GridLayout(3, 3));
        JPanel showPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new GridLayout(2,2));
        JPanel importSavePanel = new JPanel();
        showPanel.setPreferredSize(new Dimension(300, 30));
        importSavePanel.setPreferredSize(new Dimension(150, 40));
        buttonPanel.setPreferredSize(new Dimension(300, 50));
        directionPanel.setPreferredSize(new Dimension(150, 80));
        directionPanel.add(new JPanel());
        directionPanel.add(up);
        directionPanel.add(new JPanel());
        directionPanel.add(left);
        directionPanel.add(new JPanel());
        directionPanel.add(right);
        directionPanel.add(new JPanel());
        directionPanel.add(down);
        showPanel.add(showTagsbutton);
        showPanel.add(showPriorityButton);
        showPanel.add(showDateButton);
        buttonPanel.add(newTask);
        importSavePanel.add(imp);
        importSavePanel.add(save);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        buttonPanel.add(completed);
        panel.add(showPanel);
        panel.add(importSavePanel);
        panel.add(buttonPanel);
        panel.add(directionPanel);
        panel.add(tree);
        tree.updateUI();
        frame.add(panel);
        frame.setVisible(true);
    }
}
