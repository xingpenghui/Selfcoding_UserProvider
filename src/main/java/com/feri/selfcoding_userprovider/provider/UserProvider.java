package com.feri.selfcoding_userprovider.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feri.common.constant.SystemConst;
import com.feri.common.token.TokenUtil;
import com.feri.common.util.JedisUtil;
import com.feri.common.util.ResultUtil;
import com.feri.common.vo.ResultVO;
import com.feri.dao.user.UserDao;
import com.feri.domain.user.User;
import com.feri.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 *@Author feri
 *@Date Created in 2019/1/15 09:56
 */
@Service("userprovider")
public class UserProvider implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JedisUtil jedisUtil;
    @Override
    public ResultVO save(User user) {
      return ResultUtil.exec(userDao.insert(user),user);
    }

    @Override
    public ResultVO checkPhone(String phone) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        User user=userDao.selectOne(queryWrapper);
        if(user!=null){
            return ResultUtil.execERROR();
        }else{
            return ResultUtil.execOK();
        }
    }

    @Override
    public ResultVO login(String phone, String password) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        User user=userDao.selectOne(queryWrapper);
        //验证用户是否存在
        if(user!=null){
            //校验密码
            if(Objects.equals(user.getPassword(),password)){
                //1、生成Token
                String token=TokenUtil.createToken(user);
                //2、将Token到Redis
                jedisUtil.save("usertokens","user:"+token,user.getPhone());
                //3、将Token到前端
                return  ResultUtil.execOK(token);
            }
        }
        return ResultUtil.execERROR();
    }

    @Override
    public ResultVO loginout(String token) {
        if(jedisUtil.checkFiled(SystemConst.TOKENMAP,"user:"+token)){
            jedisUtil.del(SystemConst.TOKENMAP,"user:"+token);
        }
        return ResultUtil.execOK();
    }
}
