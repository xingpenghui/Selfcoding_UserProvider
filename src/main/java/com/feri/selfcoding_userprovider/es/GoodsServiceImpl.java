package com.feri.selfcoding_userprovider.es;

import com.feri.common.util.ResultUtil;
import com.feri.common.vo.ResultVO;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2019/1/19 14:43
 */
@Service
public class GoodsServiceImpl implements GoodsEsService{
    @Autowired
    private GoodsEsDao dao;
    @Override
    public ResultVO save(Goods goods) {
        return ResultUtil.exec(dao.index(goods)!=null?1:0,null);
    }

    @Override
    public ResultVO update(Goods goods) {
        return ResultUtil.exec(dao.index(goods)!=null?1:0,null);
    }

    @Override
    public ResultVO delete(int id) {
        dao.deleteById(id);
        return ResultUtil.execOK();
    }

    @Override
    public ResultVO queryById(int id) {
        return ResultUtil.execOK(dao.findById(id).get());
    }

    @Override
    public ResultVO queryAll() {
        return ResultUtil.execOK(dao.findAll());
    }

    @Override
    public ResultVO queryNameLike(String name) {
        //return ResultUtil.execOK(dao.search(QueryBuilders.wildcardQuery("name","*"+name+"*")));
        return ResultUtil.execOK(dao.getGoodsByNameLike(name));
    }
}
