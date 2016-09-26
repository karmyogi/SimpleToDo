package com.intellix.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import static android.os.Build.ID;

/**
 * Created by intellix on 9/26/2016.
 */

@Table(name = "Tasks")
public class TaskModel extends Model {

    @Column(name = "TaskName")
    public String name;


    public TaskModel() {
        super();
    }

    public TaskModel(String name) {
        super();
        this.name = name;

    }

    public static void deleteTask(String name)
    {
        new Delete().from(TaskModel.class).where("TaskName = ?", name).execute();
    }

    public static void insertTask(String name)
    {
        TaskModel t = new TaskModel();
        t.name = name;
        t.save();
    }

    public static void updateTask(String currentValue, String newValue) {

        deleteTask(currentValue);
        insertTask(newValue);
    }

    public static List<TaskModel> getAll() {
        // This is how you execute a query
        return new Select()
                .from(TaskModel.class)
                .orderBy("TaskName ASC")
                .execute();
    }
}
