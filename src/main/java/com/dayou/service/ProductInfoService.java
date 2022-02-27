package com.dayou.service;

import com.dayou.pojo.ProductInfo;
import com.dayou.pojo.vo.ProductVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-19 22:21
 */
public interface ProductInfoService {

    //显示全部商品(本分页)
    List<ProductInfo> getAll();

    //实现分页功能
    PageInfo splitPage(int pageNum ,int pageSize);

    //增加商品
    int save(ProductInfo info);

    //按主键id查询商品
    ProductInfo getByID(int pid);

    //更新商品
    int updateById(ProductInfo info);

    //按主键id删除商品
    int deleteById(Integer pid);

    int deleteBatch(String[] pids);

    //多条件查询分页
    PageInfo splitPageVo(ProductVo vo, int pageSize);
}
