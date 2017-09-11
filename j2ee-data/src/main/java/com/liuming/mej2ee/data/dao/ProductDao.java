package com.liuming.mej2ee.data.dao;

import com.liuming.mej2ee.data.model.Product;

/**
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 * Description:
 */

public interface ProductDao {

    int insertProduct(Product product);

    Product selectProductById(Integer id);

    int updateProductById(Product product);
}