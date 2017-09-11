package com.liuming.mej2ee.web.springbatch.mapper;

import com.liuming.mej2ee.web.springbatch.entity.Product;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * <p>
 *     根据 CSV 文件中的字段集合构建 Product 对象
 * </p>
 *
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 * Description:
 */

public class ProductFieldSetMapper implements FieldSetMapper<Product> {

    @Override
    public Product mapFieldSet(FieldSet fieldSet) throws BindException {
        Product product = new Product();
        product.setId(fieldSet.readInt("id"));
        product.setName(fieldSet.readString("name"));
        product.setDescription(fieldSet.readString("description"));
        product.setQuantity(fieldSet.readInt("quantity"));
        return product;
    }
}