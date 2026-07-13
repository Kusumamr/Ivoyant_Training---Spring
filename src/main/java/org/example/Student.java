package org.example;

public class Student {
    private int id;
    private String name;
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
