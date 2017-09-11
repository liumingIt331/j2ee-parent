package com.liuming.mej2ee.data.model;


import lombok.Data;

/**
 * 商品实体类
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 * Description:
 */

@Data
public class Product {

    /** id */
    private int id;

    /** 名称 */
    private String name;

    /** 描述 */
    private String description;

    /** 重量 */
    private int quantity;
}