package com.liuming.mej2ee.web.springbatch.processor;

import com.liuming.mej2ee.data.dao.ProductDao;
import com.liuming.mej2ee.data.model.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuming on 2017/9/11.
 */
public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product process(Product product) throws Exception {

        Product product1 = productDao.selectProductById(product.getId());

        if (product1 != null) {
            product.setQuantity(product.getQuantity() + product1.getQuantity());
        }

        return product;
    }
}
