package com.geodor.springdataredispractice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.geodor.springdataredispractice.entity.Product;

@Repository
public class ProductDao {

    public static final String HASH_KEY = "Product";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> template;

    public Product save(Product product) {
        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Object> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public Product findProductById(int id) {
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProductById(int id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "product removed";
    }
}
