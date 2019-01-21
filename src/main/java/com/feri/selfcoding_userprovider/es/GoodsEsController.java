package com.feri.selfcoding_userprovider.es;

import com.feri.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author feri
 *@Date Created in 2019/1/19 14:47
 */
@RestController
public class GoodsEsController {
    @Autowired
    private GoodsEsService service;

    //新增
    @PostMapping("goodsesadd.do")
    public ResultVO add(Goods goods){
        System.out.println(goods.getName());
        return service.save(goods);
    }
    //修改
    @PostMapping("goodsesupdate.do")
    public ResultVO update(Goods goods){
        return service.update(goods);
    }
    //删除
    @GetMapping("goodsesdel.do")
    public ResultVO update(int id){
        return service.delete(id);
    }
    //查询单个
    @GetMapping("goodsesbyid.do")
    public ResultVO getId(int id){
        return service.queryById(id);
    }
    //查询全部
    @GetMapping("goodsesall.do")
    public ResultVO all(){
        return service.queryAll();
    }
    //模糊查询
    @GetMapping("goodseslike.do")
    public ResultVO like(String name){
        return service.queryNameLike(name);
    }





}
