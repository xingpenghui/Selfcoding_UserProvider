package com.feri.selfcoding_userprovider.provider;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feri.common.constant.SystemConst;
import com.feri.common.token.LoginToken;
import com.feri.common.util.Base64Util;
import com.feri.common.util.EncryptUtil;
import com.feri.common.util.JedisUtil;
import com.feri.common.util.ResultUtil;
import com.feri.common.vo.ResultVO;
import com.feri.dao.user.UserLogDao;
import com.feri.dao.user.UserShellDao;
import com.feri.dao.user.UserWalletDao;
import com.feri.dao.user.UsersigninDao;
import com.feri.domain.user.Userlog;
import com.feri.domain.user.Usershell;
import com.feri.domain.user.Usersignin;
import com.feri.domain.user.Userwallet;
import com.feri.service.user.UserSigninService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 *@Author feri
 *@Date Created in 2019/1/16 14:58
 */
@Service("usersigninprovider")
public class UserSigninProvider implements UserSigninService {
    @Autowired
    private UserShellDao userShellDao;
    @Autowired
    private UsersigninDao usersigninDao;
    @Autowired
    private UserLogDao userLogDao;
    @Autowired
    private UserWalletDao userWalletDao;
    @Autowired
    private JedisUtil jedisUtil;
    @Override
    @Transactional
    public ResultVO signin(String token) {
        //验证连续的天数签到
        LoginToken loginToken= JSON.parseObject(EncryptUtil.AESDec(Base64Util.base64Dec(SystemConst.TOKENKEY),token),LoginToken.class);
        Calendar now=Calendar.getInstance();
        Calendar lastTime=Calendar.getInstance();
        lastTime.setTime(usersigninDao.selectSign(loginToken.getId()));
        int money=0;
        Random random=new Random();
        int days=0;
        if(now.get(Calendar.DAY_OF_YEAR)-lastTime.get(Calendar.DAY_OF_YEAR)>1){
            days=1;
            //断签
            //修改Redis的计算为1
            jedisUtil.save(SystemConst.SIGNINMAP,loginToken.getId()+"","1");
            //生成学贝
            money=random.nextInt(5)+1;
        }else{
            days=Integer.parseInt(jedisUtil.getMap(SystemConst.SIGNINMAP,loginToken.getId()+""));
            if(days==365){
                money=random.nextInt(5)+1+1000;
            }else if(days%30==0){
                money=(random.nextInt(5)+1)*5;
            }else if(days%5==0){
                money=(random.nextInt(5)+1)*2;
            }else{
                money=random.nextInt(5)+1;
            }
            //更新连续签到的天数
            jedisUtil.save(SystemConst.SIGNINMAP,loginToken.getId()+"",(days+1)+"");
        }
        //签到表
        Usersignin usersignin=new Usersignin();
        usersignin.setShell(money);
        usersignin.setSignintime(LocalDateTime.now());
        usersignin.setUid(loginToken.getId());
        //用户学贝流水表
        Usershell usershell=new Usershell();
        usershell.setUid(loginToken.getId());
        usershell.setShell(money);
        usershell.setStartdate(LocalDate.now());
        usershell.setType(SystemConst.SHELLTYPE1);
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH,SystemConst.SHELLMONTHS);
        usershell.setEnddate(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
       //用户钱包
        QueryWrapper<Userwallet> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",loginToken.getId());
        Userwallet userwallet=userWalletDao.selectOne(queryWrapper);
        userwallet.setTotalshell(userwallet.getTotalshell()+money);
        //用户流水
        Userlog userlog=new Userlog();
        userlog.setUid(loginToken.getId());
        userlog.setContent(loginToken.getId()+"用户连续"+days+"天签到，今日获取："+money);
        userlog.setType(1);
        userlog.setCreatetime(LocalDateTime.now());

        userLogDao.insert(userlog);
        userShellDao.insert(usershell);
        usersigninDao.insert(usersignin);
        userWalletDao.updateById(userwallet);

        return ResultUtil.execOK(money);
    }

    @Override
    public ResultVO checkSign(String token) {
        LoginToken loginToken= JSON.parseObject(EncryptUtil.AESDec(Base64Util.base64Dec(SystemConst.TOKENKEY),token),LoginToken.class);

        Date lastTime=usersigninDao.selectSign(loginToken.getId());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(sdf.format(lastTime).equals(sdf.format(new Date()))){
            return ResultUtil.execERROR();
        }else{
            return ResultUtil.execOK();
        }
    }

    @Override
    public ResultVO queryList(String token, int count) {
        LoginToken loginToken= JSON.parseObject(EncryptUtil.AESDec(Base64Util.base64Dec(SystemConst.TOKENKEY),token),LoginToken.class);
        return ResultUtil.execOK(usersigninDao.selectSignList(loginToken.getId(),count));
    }
}
