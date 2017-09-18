package com.liuming.mej2ee.data.model;


import java.math.BigDecimal;

/**
 * 商品实体类
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 * Description:
 */

public class Product {

    /** id */
    private int id;

    /** 名称 */
    private String name;

    /** 描述 */
    private String description;

    /** 数量 */
    private int quantity;

    private BigDecimal test;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTest() {
        return test;
    }

    public void setTest(BigDecimal test) {
        this.test = test;
    }
}