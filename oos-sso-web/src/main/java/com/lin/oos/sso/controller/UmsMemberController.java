package com.lin.oos.sso.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lin.oos.pojo.UmsMember;
import com.lin.oos.service.UmsMemberService;
import com.lin.oos.vo.OosResult;
import io.micrometer.core.instrument.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class UmsMemberController {

    @Value("${OOS_USER_TOKEN}")
    private String OOS_USER_TOKEN;

    @Value("${OOS_USER_KEY}")
    private String OOS_USER_KEY;

    @Value("${OOS_USER_EXPIRE_TIME}")
    private Integer OOS_USER_EXPIRE_TIME;

    @Reference
    private UmsMemberService umsMemberService;


    @Autowired
    StringRedisTemplate redisTemplate;

    ValueOperations<String, String> stringRedis;

    @PostConstruct
    public void init() {
        stringRedis = redisTemplate.opsForValue();
    }

    @RequestMapping("index")
    @ResponseBody
    public List<UmsMember> indexTest() {

        List<UmsMember> umsMembers = umsMemberService.selectAll();

        return umsMembers;
    }


    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param response
     * @return
     */
    @PostMapping("/login.do")
    @ResponseBody
    public OosResult login(String username, String password, HttpServletResponse response) {

        List<UmsMember> umsMembers = umsMemberService.login(username, password);


        //第二步：如果成功，将数据写入到Redis，并写入Cookie，再返回成功状态
        if (umsMembers != null && umsMembers.size() > 0) {
            UmsMember user = umsMembers.get(0);
            //需求：TOKEN使用UUID的随机。
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(OOS_USER_TOKEN, token);
            cookie.setPath("/");
            response.addCookie(cookie);

            //将数据写入到Redis里面

            String userStr = JSON.toJSONString(user);


            /**
             * 设置 key 的值为 value
             * 其它规则与 set(K key, V value)一样
             * @param key 不能为空
             * @param value 设置的值
             * @param timeout 设置过期的时间
             * @param unit 时间单位。不能为空
             * @see <a href="http://redis.io/commands/setex">Redis Documentation: SETEX</a>
            void set(K key, V value, long timeout, TimeUnit unit);*/


            stringRedis.set(OOS_USER_KEY + ":" + token, userStr, OOS_USER_EXPIRE_TIME, TimeUnit.SECONDS);


            return OosResult.build(1, "登录成功");

        }
        return OosResult.build(2, "帐号或密码错误");


    }


    @PostMapping("/umsMember/checkUsername.do")
    @ResponseBody
    public boolean checkUsername(String username) {

        List<UmsMember> umsMembers = umsMemberService.selectByUsername(username);

        if (umsMembers.size() == 0) {
            return true;
        }


        return false;
    }


    @PostMapping("/register.do")
    @ResponseBody
    public OosResult register(UmsMember umsMember) {
        try {
            umsMember.setMemberLevel(0);
            umsMember.setStatus(1);
            return umsMemberService.register(umsMember);
        } catch (Exception e) {
            e.printStackTrace();
            return OosResult.build(2, "注册失败");
        }



    }


    /**
     * 登录校验
     *
     * @param token
     * @return
     */
    @RequestMapping("/token/{token}")
    @ResponseBody
    public OosResult checkLogin(@PathVariable("token") String token) {


        //通过TOKEN校验Redis里面是否有对应的用户信息
        String jsonData = stringRedis.get(OOS_USER_KEY + ":" + token);
        if (StringUtils.isNotBlank(jsonData)) {
            //校验后重新计算超时时间
            stringRedis.set(OOS_USER_KEY + ":" + token, jsonData, OOS_USER_EXPIRE_TIME, TimeUnit.SECONDS);
            return OosResult.ok(JSON.parseObject(jsonData, UmsMember.class));
        }
        return OosResult.build(400, "用户未登录或者登陆失效，请重新登陆");


    }


}
