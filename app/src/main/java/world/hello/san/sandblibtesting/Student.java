package world.hello.san.sandblibtesting;


import android.graphics.Bitmap;

import san.db.handler.SanDbResult;
import san.db.handler.SetColumnAttr;

public class Student extends SanDbResult<Student>{
    @SetColumnAttr(setPrimary = true,setAutoIncrement = true)
    private int id;
    private String name;
    private String dob,age,fatherName;

    public Student() {
    }

    public Student(String name, String dob, String age, String fatherName) {
        this.name = name;
        this.dob = dob;
        this.age = age;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
}
