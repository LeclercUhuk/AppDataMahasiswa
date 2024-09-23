package com.example.appdatamahasiswa;

public class Student {
    private int id;
    private String name;
    private String nim;
    private double ipk;
    private String course;

    public Student(int id, String name, String nim, double ipk, String course) {
        this.id = id;
        this.name = name;
        this.nim = nim;
        this.ipk = ipk;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public double getIpk() {
        return ipk;
    }

    public String getCourse() {
        return course;
    }
}
