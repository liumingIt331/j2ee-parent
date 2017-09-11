package com.liuming.mej2ee.web.springbatch.writer;

import com.liuming.mej2ee.web.springbatch.entity.Product;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 * Description:
 */

public class ProductItemWriter implements ItemWriter<Product> {

    /**
     * 查询数据
     */
    private static final String SELECT_PRODUCT = "select * from product where id = ?";
    /**
     * 插入数据
     */
    private static final String INSERT_PRODUCT = "insert into product (id, name, description, quantity) values (?, ?, ?, ?)";
    /**
     * 根据id更新数据
     */
    private static final String UPDATE_PRODUCT = "update product set name = ?, description = ?, quantity = ? where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends Product> products) throws Exception {

        for (Product product : products) {

            List<Product> productList = jdbcTemplate.query(SELECT_PRODUCT, new Object[]{product.getId()}, new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product p = new Product();
                    p.setId(rs.getInt(1));
                    p.setName(rs.getString(2));
                    p.setDescription(rs.getString(3));
                    p.setQuantity(rs.getInt(4));
                    return p;
                }
            });

            if (productList != null && productList.size() > 0) {
                jdbcTemplate.update(UPDATE_PRODUCT, product.getName(), product.getDescription(), product.getQuantity(), product.getId());
            } else {
                jdbcTemplate.update(INSERT_PRODUCT, product.getId(), product.getName(), product.getDescription(), product.getQuantity());
            }

        }
    }
}