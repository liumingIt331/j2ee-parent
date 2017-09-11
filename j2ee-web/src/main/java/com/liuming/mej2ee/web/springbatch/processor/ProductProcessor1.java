package com.liuming.mej2ee.web.springbatch.processor;

import com.liuming.mej2ee.data.dao.ProductDao;
import com.liuming.mej2ee.data.model.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * peoduct的第二个processor
 * 用于过滤id = 5的数据
 *
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 * Description:
 */

public class ProductProcessor1 implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product product) throws Exception {

        //id=5的数据不进行后续的处理
        if (product.getId() == 5) {
            return null;
        }

        return product;
    }
}