package com.dayou.service.impl;

import com.dayou.mapper.ProductTypeMapper;
import com.dayou.pojo.ProductType;
import com.dayou.pojo.ProductTypeExample;
import com.dayou.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-20 11:34
 */
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    //数据访问层的创建
    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
