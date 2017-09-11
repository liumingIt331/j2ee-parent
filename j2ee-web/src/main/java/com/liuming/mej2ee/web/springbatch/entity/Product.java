package com.liuming.mej2ee.web.springbatch.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class Product {

    private int id;

    private String name;

    private String description;

    private int quantity;
}