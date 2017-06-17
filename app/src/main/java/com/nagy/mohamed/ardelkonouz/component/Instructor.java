package com.nagy.mohamed.ardelkonouz.component;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class Instructor {
    private String m_name;
    private Byte m_cv;
    private String m_address;
    private Double m_totalSalary;
    private String m_mobile;
    private Short m_qualification;
    private Short m_age;
    private HashMap<String, List<Course>> m_courses;

    public Instructor(String name, Byte cv, String address, Double totalSalary,
                      String mobile, Short qualification, Short age,
                      @Nullable  List<Course> courses){
        m_name = name;
        m_cv = cv;
        m_address = address;
        m_totalSalary = totalSalary;
        m_mobile = mobile;
        m_qualification = qualification;
        m_age = age;

        if(courses != null){
            m_courses = new HashMap<>();
            m_courses.put("Courses", courses);
        }
    }

    public String getM_name() {
        return m_name;
    }

    public Byte getM_cv() {
        return m_cv;
    }

    public HashMap<String, List<Course>> getM_courses() {
        return m_courses;
    }

    public Double getM_totalSalary() {
        return m_totalSalary;
    }

    public Short getM_age() {
        return m_age;
    }

    public Short getM_qualification() {
        return m_qualification;
    }

    public String getM_address() {
        return m_address;
    }

    public String getM_mobile() {
        return m_mobile;
    }
}
