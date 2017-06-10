package com.nagy.mohamed.ardelkonouz.component;

/**
 * Created by mohamednagy on 6/10/2017.
 */
public class Course {
    private String m_name;
    private Integer m_hours;
    private Double m_cost;
    private Boolean m_completed;
    private Long m_startDate;
    private Long m_endDate;
    private Short m_startAge;
    private Short m_endAge;
    private Integer m_level;

    public Course(String name, Integer hours, Double cost, Boolean completed,
                  Long startDate, Long endDate, Integer level,
                  Short startAge, Short endAge){
        m_name = name;
        m_hours = hours;
        m_completed = completed;
        m_cost = cost;
        m_startDate = startDate;
        m_endDate = endDate;
        m_level = level;
        m_startAge = startAge;
        m_endAge = endAge;
    }

    public Boolean getM_completed() {
        return m_completed;
    }

    public Double getM_cost() {
        return m_cost;
    }

    public Integer getM_hours() {
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

}
