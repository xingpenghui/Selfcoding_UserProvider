package com.feri.selfcoding_userprovider.es;

import com.feri.common.vo.ResultVO;

/**
 *@Author feri
 *@Date Created in 2019/1/19 14:41
 */
public interface GoodsEsService {
    //新增
    ResultVO save(Goods goods);
    //修改
    ResultVO update(Goods goods);
    //删除
    ResultVO delete(int id);
    //查询 单个
    ResultVO queryById(int id);
    //查询全部
    ResultVO queryAll();
    //模糊查询
    ResultVO queryNameLike(String name);

}
