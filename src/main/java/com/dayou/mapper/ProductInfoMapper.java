package com.dayou.mapper;


import com.dayou.pojo.ProductInfo;
import com.dayou.pojo.ProductInfoExample;
import com.dayou.pojo.vo.ProductVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoMapper {
    int countByExample(ProductInfoExample example);

    int deleteByExample(ProductInfoExample example);

    int deleteByPrimaryKey(Integer pId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    List<ProductInfo> selectByExample(ProductInfoExample example);

    ProductInfo selectByPrimaryKey(Integer pId);

    int updateByExampleSelective(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByExample(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    int deleteBatch(String[] pids);

//    List<PageInfo> selectConditionSplitPage(ProductVo vo);

    //实现多条件查询
    public List<ProductInfo> selectConditionSplitPage(ProductVo vo);
//
//    //批量删除
//    public int deleteBatch(String[] pids);
}