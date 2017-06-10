package com.nagy.mohamed.ardelkonouz.component;

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

    public Instructor(String name, Byte cv, String address, Double totalSalary,
                      String mobile, Short qualification, Short age){
        m_name = name;
        m_cv = cv;
        m_address = address;
        m_totalSalary = totalSalary;
        m_mobile = mobile;
        m_qualification = qualification;
        m_age = age;
    }

    public String getM_name() {
        return m_name;
    }

    public Byte getM_cv() {
        return m_cv;
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
