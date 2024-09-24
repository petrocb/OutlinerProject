package uk.ac.kingston.ci5105.outliner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Section.java is the main model file of the Outliner Application
 * All Sections, sub sections, sub sub sections are really just sections
 *  this allows for the structure to be infinitely deep.
 * @author Petroc Brown K2124745
 */
public class Section {
    //title or name of a section
    private String title;
    
    // a string where the user can store tags and other info about a section
    private String tags;

    // an int where the user can define how import a section is
    private int priority;
    
    // where the sub sections are stored for this section
    private ArrayList<Section> tasks;
    
    // boolean hold if a section has been completed or not
    private boolean completed;
    
    //to store the due date of a section
        private String date;

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(String date) {
        this.date = date;
    }


    /**
    * Constructor for a section 
     * @param title title or name of a section
     * @param tags an int where the user can define how import a section is
     * @param priority an int where the user can define how import a section is
    */
    public Section(String title, String tags, int priority, String date) {
        this.title = title;
        this.tags = tags;
        this.priority = priority;
        this.date = date;
        this.completed = false;
        tasks = new ArrayList<>();
    }
    /**
     * Get the value of completed
     * A boolean that indicates if the Task has been completed
     * @return boolean  value of completed
     */
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Set the value of completed
     * A boolean that indicates if the Task has been completed
     * @param completed boolean completed new value of completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    /**
     * Add a section to the Tasks array list
     * @param child The Section that will be added
     */
    public void addTask(Section child){
        this.tasks.add(child);
    }
    /**
     * returns all the sections in the Tasks arrayList
     */
    public ArrayList<Section> getTasks() {
        return tasks;
    }
    /**
     * returns all the sections in the tasks arrayList
     * @param tasks 
     */
    public void setTasks(ArrayList<Section> tasks) {
        this.tasks = tasks;
    }
    /**
     * returns the priority of a section
     * @return 
     */
    public int getPriority() {
        return priority;
    }
    /**
     * sets the priority of a section
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
    /**
     * gets the tags of a section
     * @return 
     */
    public String getTags() {
        return tags;
    }
    /**
     * sets the tags of a section
     * @param tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }
    /**
     * gets the title of a section
     * @return 
     */
    public String getTitle() {
        return title;
    }
    /**
     * sets the title of a section
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
     /**
     * recursive funtion
     * gets the section in which this section is stored in
     * @param child
     * @return 
     */
    public Section getParent(Section child) {
        for (Section x : this.tasks) {
            if (x.tasks.indexOf(child) != -1) {
                return x;
            } else {
                Section parent = x.getParent(child);
                if (parent != null) {
                    return parent;
                }
            }
        }
        return null;
    }
    
    
    
//    public static boolean getShowTags(){ return showTags; }
//    public static void setShowTags(boolean showTags){ Item.showTags = showTags;} 
//    
//    public static boolean getShowPriority(){ return showPriority; }
//    public static void setShowPriority(boolean showPriority){ Item.showPriority = showPriority;} 
//
//    @Override
//    public String toString() {
//        if (showTags && showPriority){return title + ": "+ tags + " "+ priority;}
//        else if (showTags) {
//            return title + ": " + tags;
//        } 
//        else if (showPriority) {return title + ": " + priority;}
//        else {
//            return title;
//        }
//        
//    }
     /**
     * override
     * function to control how tasks are displayed as strings
     * @return 
     */
    @Override
    public String toString() {
        if (this.title.equals("Root")){
            return "";
        }
        String str = title;
        
        if (Outliner.isShowTags()){
            str = str + ", " + tags;
        }
        if (Outliner.isShowPriority()){
            str = str + ", " + priority;
        }
        
        if (Outliner.isShowDate()){
            str = str + ", " + date;
        }
        return str;
    }

     /**
     * pareses a section as a json file to be loaded
     * @param json
     * @return 
     */    
    public static Section parseTask(JSONObject json) {
    String title = json.getString("title");
    String tags = json.optString("tags", "");
    int priority = json.getInt("priority");
    String date = json.getString("date");
    JSONArray taskArray = json.optJSONArray("tasks");
    Section task = new Section(title, tags, priority, date);
    if (taskArray != null) {
        for (int i = 0; i < taskArray.length(); i++) {
            JSONObject childJson = taskArray.getJSONObject(i);
            Section childTask = parseTask(childJson);
            task.addTask(childTask);
        }
    }
    return task;
}
     /**
      * translates the tree in to a json objects
     * @return 
     */   
        public JSONObject toJSON(){
            JSONObject json = new JSONObject();
            json.put("title", this.title);
            json.put("tags", this.tags);
            json.put("tasks", this.tasks);
            json.put("priority", this.priority);
            json.put("date", this.date);
            return json;
        }
}

