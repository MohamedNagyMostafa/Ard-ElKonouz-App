package com.nagy.mohamed.ardelkonouz.component;

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
    private Short m_eductionType;
    private Short m_studyYear;

    public Child(Long childId, String childName, String fatherName, String motherName,
                 String fatherMobile, String motherMobile, String mobileWhatsup,
                 String motherQualification, Short age, Short educationType,
                 Short studyYear){
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
        m_eductionType = educationType;
    }

    public Short getM_age() {
        return m_age;
    }

    public Long getM_childId() {
        return m_childId;
    }

    public Short getM_eductionType() {
        return m_eductionType;
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

}
