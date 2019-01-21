package com.feri.selfcoding_userprovider.provider;

import com.feri.common.token.TokenUtil;
import com.feri.common.util.ResultUtil;
import com.feri.common.vo.ResultVO;
import com.feri.dao.user.UserdetailDao;
import com.feri.domain.user.Userdetial;
import com.feri.service.user.UserdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2019/1/17 15:17
 */
@Service("userdetailprovider")
public class UserDetailProvider implements UserdetailService {
    @Autowired
    private UserdetailDao userdetailDao;
    @Override
    public ResultVO update(String token, Userdetial userdetial) {
        userdetial.setUid(TokenUtil.parseToken(token).getId());
        return  ResultUtil.exec(userdetailDao.updateById(userdetial),userdetial);
    }

    @Override
    public ResultVO queryById(String token) {
        return ResultUtil.execOK(userdetailDao.selectById(TokenUtil.parseToken(token).getId()));
    }
}
