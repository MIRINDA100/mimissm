package com.dayou.listener;

import com.dayou.pojo.ProductType;
import com.dayou.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-20 11:41
 */
@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手动从spring容器中取出ProductTypeServiceImpl的对象
         ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productTypeService.getAll();
        //放入全局应用作用域中,供新增页面,修改页面,前台的查询功能提供全部商品类别集合
        servletContextEvent.getServletContext().setAttribute("typeList" ,typeList);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
