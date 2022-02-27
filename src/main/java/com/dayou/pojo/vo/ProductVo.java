package com.dayou.pojo.vo;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-26 12:07
 */
public class ProductVo {

    //封装所有页面上的查询条件
    private String pName;
    private Integer typeId;
    private Integer lPrice;
    private Integer hPrice;
    private Integer page=1;

    public ProductVo() {
    }

    public ProductVo(String pName, Integer typeId, Integer lPrice, Integer hPrice, Integer page) {
        this.pName = pName;
        this.typeId = typeId;
        this.lPrice = lPrice;
        this.hPrice = hPrice;
        this.page = page;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getlPrice() {
        return lPrice;
    }

    public void setlPrice(Integer lPrice) {
        this.lPrice = lPrice;
    }

    public Integer gethPrice() {
        return hPrice;
    }

    public void sethPrice(Integer hPrice) {
        this.hPrice = hPrice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ProductVo{" +
                "pName='" + pName + '\'' +
                ", typeId=" + typeId +
                ", lPrice=" + lPrice +
                ", hPrice=" + hPrice +
                ", page=" + page +
                '}';
    }
}
