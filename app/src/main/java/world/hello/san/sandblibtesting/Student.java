package world.hello.san.sandblibtesting;


import android.graphics.Bitmap;

import san.db.handler.SanDbResult;
import san.db.handler.SetColumnAttr;

public class Student extends SanDbResult<Student>{

    private int id;
    private String name;
    private String course,fee,fatherName;

    Bitmap student_photo;

    public Student() {
    }

    public Student(String name, String course, String fee, String fatherName) {
        this.name = name;
        this.course = course;
        this.fee = fee;
        this.fatherName = fatherName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }


}
