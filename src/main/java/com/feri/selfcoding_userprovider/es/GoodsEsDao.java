package com.feri.selfcoding_userprovider.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/1/19 14:39
 */
public interface GoodsEsDao extends ElasticsearchRepository<Goods,Integer> {

    List<Goods> getGoodsByNameLike(String name);
}
