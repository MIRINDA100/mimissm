package com.dayou.service.impl;

import com.dayou.mapper.ProductInfoMapper;
import com.dayou.pojo.ProductInfo;
import com.dayou.pojo.ProductInfoExample;
import com.dayou.pojo.vo.ProductVo;
import com.dayou.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.prism.impl.Disposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-19 22:23
 */

@Service
public class ProductInfoServiceImpl  implements ProductInfoService {

    //切记: 业务逻辑层一定有数据访问层的对象
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }


    //select * from product_info limit 起始记录数=((当前页-1)*每页的条数),每页取几条
    //select * from product_info limit 10,5
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {

        //分页插件使用PageHelper工具类完成分页设置
        PageHelper.startPage(pageNum,pageSize);

        //进行PageInfo的数据封装
        //进行有条件的查询操作,必须创建PageductInfoExample对象
        ProductInfoExample productInfoExample = new ProductInfoExample();
        //设置排序,按主键降序排序
        //select * from product_info order by p_id desc
        productInfoExample.setOrderByClause("p_id desc");
        //设置完排序后,取集合,一定要在取集合之前,设置PageHelper.startPage(pageNum,pageSize)
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(productInfoExample);
        //将查询到的集合封装进PageInfo对象
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(productInfos);
        return pageInfo;


    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getByID(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int updateById(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public int deleteById(Integer pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] pids) {
        return productInfoMapper.deleteBatch(pids);
    }


    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductVo vo, int pageSize) {
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectConditionSplitPage(vo);
        return new PageInfo<>(list);
    }


}









