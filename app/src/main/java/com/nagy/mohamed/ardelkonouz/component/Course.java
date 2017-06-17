package com.nagy.mohamed.ardelkonouz.component;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class Course {
    private String m_name;
    private Double m_hours;
    private Double m_cost;
    private Boolean m_availablePosition;
    private Long m_startDate;
    private Long m_endDate;
    private Short m_startAge;
    private Short m_endAge;
    private Integer m_level;
    private Double m_salaryPerChild;
    private HashMap<String, List<Child>> m_children;

    public Course(String name, Double hours, Double cost, Boolean availablePosition,
                  Long startDate, Long endDate, Integer level,
                  Short startAge, Short endAge, Double salaryPerChild,
                  @Nullable List<Child> children){
        m_name = name;
        m_hours = hours;
        m_availablePosition = availablePosition;
        m_cost = cost;
        m_startDate = startDate;
        m_endDate = endDate;
        m_level = level;
        m_startAge = startAge;
        m_endAge = endAge;
        m_salaryPerChild = salaryPerChild;

        if(children != null){
            m_children = new HashMap<>();
            m_children.put("Children", children);
        }
    }

    public Boolean getM_availablePosition() {
        return m_availablePosition;
    }

    public HashMap<String, List<Child>> getM_children() {
        return m_children;
    }

    public Double getM_cost() {
        return m_cost;
    }

    public Double getM_hours() {
        return m_hours;
    }

    public Integer getM_level() {
        return m_level;
    }

    public Long getM_endDate() {
        return m_endDate;
    }

    public Long getM_startDate() {
        return m_startDate;
    }

    public String getM_name() {
        return m_name;
    }

    public Short getM_endAge() {
        return m_endAge;
    }

    public Short getM_startAge() {
        return m_startAge;
    }

    public Double getM_salaryPerChild() {
        return m_salaryPerChild;
    }
}
