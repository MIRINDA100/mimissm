package com.dayou.controller;

import com.dayou.pojo.ProductInfo;
import com.dayou.pojo.vo.ProductVo;
import com.dayou.service.ProductInfoService;
import com.dayou.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.Line;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-19 22:28
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    public static final int  PAGE_SIZE = 5;

    //异步上传的图片的名称
    String saveFileName = "";


    //业务逻辑层的对象
    @Autowired
    ProductInfoService productInfoService;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    //显示第一页的五条数据
    @RequestMapping("/split")
    public String split(HttpServletRequest request){

        PageInfo pageInfo = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if(vo!=null){
            pageInfo = productInfoService.splitPageVo((ProductVo) vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else{
            //得到第一页数据
            pageInfo = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.setAttribute("info",pageInfo);
        return "product";
    }

    //ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductVo vo, HttpSession session){
        //获取当前page参数的页面的数据
//        PageInfo info = productInfoService.splitPage(page, PAGE_SIZE);
        PageInfo info = productInfoService.splitPageVo(vo, PAGE_SIZE);
        session.setAttribute("info",info);
    }

    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request,Model model){

        //提取生成文件名UUID+上传图片的后缀.jpg  .png
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path + File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
       //返回客户端JSON对象,封装图片的路径,为了在页面实现立即回显
        JSONObject object =new JSONObject();
        object.put("imgurl",saveFileName);

        return object.toString();


    }

    //  保存新增商品
    @RequestMapping("/save")
    public  String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        //info对象中有表单提交上来的5个数据,有异步ajax上来的图片名称数据,有上架时间的数据
        int num=-1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num > 0){
            request.setAttribute("msg","增加成功!");
        }else{
            request.setAttribute("msg","增加失败!");
        }
        //清空saveFileName变量中的内容,为了下次增加或修改的异步ajax的上传处理
        saveFileName="";

        //增加成功后,应重新访问数据库,所以跳转到分页显示的action上
        return  "forward:/prod/split.action";
    }

    //查询单个
    @RequestMapping("/one")
    public String one(int pid,ProductVo vo,Model model,HttpSession session){
        ProductInfo info = productInfoService.getByID(pid);
        model.addAttribute("prod",info);
        //将多条件及页码放入session中，更新处理结束后分页时读取条件和页码进行处理
        session.setAttribute("prodVo",vo);
        return "update";
    }

    //更新数据
    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        //如果异步ajax图片上传,则saveFileName里有图片的名称，
        // 如果没有则，saveFileName="",实体类info使用隐藏表单域提供上来的pImage原始图片的名称
        if( !"".equals(saveFileName)){
            info.setpImage(saveFileName);
        }

        int num=-1;

        try {
            num = productInfoService.updateById(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num >0){
            request.setAttribute("msg","更新成功!");
        }else{
            request.setAttribute("msg","更新失败!");
        }
        saveFileName = "";

        return  "forward:/prod/split.action";

    }

    //删除单个商品
    @RequestMapping("/delete")
    public String delete(Integer pid,HttpServletRequest request){
        int num = -1;
        try {
            num =productInfoService.deleteById(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","删除成功!");
        }else{
            request.setAttribute("msg","删除失败!");
        }
        //删除结束后跳到分页显示
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        //取得第一页的数据
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("deleteProdVo");
        if(vo!=null){
            info=productInfoService.splitPageVo((ProductVo) vo,PAGE_SIZE);
        }else {
            info=productInfoService.splitPage(1,PAGE_SIZE);
        }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }


    //批量删除选中的商品
    @RequestMapping("/deleteBatch")
    public String deletebatch(String str,HttpServletRequest request){
        String[] pids = str.split(",");
        System.out.println("批量删除");

        try {
            int num = productInfoService.deleteBatch(pids);
            if(num >0){
                request.setAttribute("msg","批量删除成功!");
            }else{
                request.setAttribute("msg","批量删除失败!!!!");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除!!!!");
        }
        System.out.println("数据层已完成");
        return "forward:/prod/deleteAjaxSplit.action";
    }

}
















