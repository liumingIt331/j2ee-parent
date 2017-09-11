package com.liuming.mej2ee.web.springbatch.writer;

import com.liuming.mej2ee.data.dao.ProductDao;
import com.liuming.mej2ee.data.model.Product;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by liuming on 2017/9/11.
 */
public class ProductItemWriter1 implements ItemWriter<Product> {

    @Autowired
    private ProductDao productDao;

    @Override
    public void write(List<? extends Product> list) throws Exception {
        System.out.println("解析得到的数据：" + list.toString());
        for (Product product : list) {
            Product product1 = productDao.selectProductById(product.getId());

            if (product1 != null) {
                System.out.println("更新数据：" + product.toString());
                productDao.updateProductById(product);
            } else {
                System.out.println("插入数据：" + product.toString());
                productDao.insertProduct(product);
            }
        }
    }
}
