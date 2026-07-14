package org.example;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Student {

    @NotNull(message = "ID is required")
    private int id;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "course cannot be empty")
    private String course;

    public Student(){

    }

    public Student(int id,String name , String course){
        this.id=id;
        this.name=name;
        this.course=course;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public void setCourse(String course){
        this.course=course;
    }
    public String getCourse(){
        return course;
    }

}
