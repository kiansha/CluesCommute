package com.example.aakansha.pool.activity;

import java.io.Serializable;

/**
 * Created by aakansha on 6/20/2017.
 */

public class nearMeItem implements Serializable{

    String name;
    String employee_id;
    String designation;
    String address;

    public nearMeItem(String name, String employee_id, String designation, String address) {
        this.name = name;
        this.employee_id = employee_id;
        this.designation = designation;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getDesignation() {
        return designation;
    }

    public String getAddress() {
        return address;
    }
}
