package com.nagy.mohamed.ardelkonouz.component;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class Child {

    private Long m_childId;
    private String m_fatherName;
    private String m_motherName;
    private String m_childName;
    private String m_fatherMobile;
    private String m_motherMobile;
    private String m_mobileWhatsup;
    private String m_motherQualification;
    private Short m_age;
    private Short m_education_stage;
    private Short m_studyYear;
    private Short m_traits;
    private Short m_freeTime;
    private Short m_handling;
    private Short m_birthOrder;
    private Short m_gender;
    private HashMap<String, List<Course>> m_courses;

    public Child(Long childId, String childName, String fatherName, String motherName,
                 String fatherMobile, String motherMobile, String mobileWhatsup,
                 String motherQualification, Short age, Short educationStage,
                 Short studyYear, Short traits, Short freeTime, Short handling,
                 Short birthOrder, Short gender, @Nullable  List<Course> courses){
        m_childId = childId;
        m_fatherName = fatherName;
        m_motherName = motherName;
        m_fatherMobile = fatherMobile;
        m_motherMobile = motherMobile;
        m_childName = childName;
        m_mobileWhatsup = mobileWhatsup;
        m_motherQualification = motherQualification;
        m_age = age;
        m_studyYear = studyYear;
        m_education_stage = educationStage;
        m_traits = traits;
        m_freeTime = freeTime;
        m_handling = handling;
        m_birthOrder = birthOrder;
        m_gender = gender;

        if(courses != null){
            m_courses = new HashMap<>();
            m_courses.put("Courses", courses);
        }

    }

    public Short getM_age() {
        return m_age;
    }

    public Long getM_childId() {
        return m_childId;
    }

    public Short getM_eductionStage() {
        return m_education_stage;
    }

    public Short getM_studyYear() {
        return m_studyYear;
    }

    public String getM_childName() {
        return m_childName;
    }

    public String getM_fatherMobile() {
        return m_fatherMobile;
    }

    public String getM_fatherName() {
        return m_fatherName;
    }

    public String getM_mobileWhatsup() {
        return m_mobileWhatsup;
    }

    public String getM_motherMobile() {
        return m_motherMobile;
    }

    public String getM_motherName() {
        return m_motherName;
    }

    public String getM_motherQualification() {
        return m_motherQualification;
    }

    public Short getM_birthOrder() {
        return m_birthOrder;
    }

    public HashMap<String, List<Course>> getM_courses() {
        return m_courses;
    }

    public Short getM_education_stage() {
        return m_education_stage;
    }

    public Short getM_freeTime() {
        return m_freeTime;
    }

    public Short getM_gender() {
        return m_gender;
    }

    public Short getM_traits() {
        return m_traits;
    }

    public Short getM_handling() {
        return m_handling;
    }
}
