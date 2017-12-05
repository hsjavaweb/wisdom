package com.wisdom.modules.common.controller;

import com.wisdom.framework.core.annotation.ValidateBean;
import com.wisdom.framework.core.bean.LoginUser;
import com.wisdom.framework.core.bean.ResponseData;
import com.wisdom.framework.core.controller.BaseController;
import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.util.SecureUtil;
import com.wisdom.framework.core.data.Pager;
import com.wisdom.framework.core.data.PagerRequest;
import com.wisdom.modules.common.domain.MerchantsUsers;
import com.wisdom.modules.common.service.MerchantsUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hyberbin on 2016/10/30.
 */
@Controller
@RequestMapping("/api/merchantsUsers")
public class MerchantsUsersController extends BaseController {
    @Autowired
    private MerchantsUsersService merchantsUsersService;


    @RequestMapping(value = "/listMerchantsUsers")
    @ResponseBody
    public Object listMerchantsUsers(@RequestBody @ValidateBean PagerRequest pagerRequest) {
        ResponseData responseData = new ResponseData();
        try {
            Map data = pagerRequest.getData();
            LoginUser currentUser = LoginUser.getCurrentUser();
            if(3!=currentUser.getAdmType()){
                data.put("userName",currentUser.getUsername());
            }
            Pager pager = merchantsUsersService.listPager(pagerRequest.getStartPage(), pagerRequest.getPageLength(), pagerRequest.getData());
            responseData.setData(pager);
        } catch (BusinessException e) {
            log.error("查询出错！");
            responseData.setSuccess(false);
            responseData.setMsg("query error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/addMerchantsUsers")
    @ResponseBody
    public Object addMerchantsUsers(@RequestBody @ValidateBean MerchantsUsers merchantsUsers) {
        ResponseData responseData = new ResponseData();
        try {
            merchantsUsersService.insert(merchantsUsers);
        } catch (BusinessException e) {
            log.error("添加出错！");
            responseData.setSuccess(false);
            responseData.setMsg("add error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/updateMerchantsUsers")
    @ResponseBody
    public Object updateMerchantsUsers(@RequestBody MerchantsUsers merchantsUsers) {
        ResponseData responseData = new ResponseData();
        try {
            int i = merchantsUsersService.update(merchantsUsers);
            responseData.setMsg(i + "");
        } catch (BusinessException e) {
            log.error("修改出错！");
            responseData.setSuccess(false);
            responseData.setMsg("update error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/updateUserPassword")
    @ResponseBody
    public Object updateUserPassword(@RequestBody MerchantsUsers merchantsUsers) {
        ResponseData responseData = new ResponseData();
        try {
            UserDetails userDetails = merchantsUsersService.loadUserByUsername(merchantsUsers.getUserName());
            if(!userDetails.getPassword().equals(SecureUtil.MD5String(merchantsUsers.getOldPassWord()+"{"+merchantsUsers.getUserName()+"}","utf-8"))){
                responseData.setMsg("原密码错误！");
                responseData.setSuccess(false);
            }else{
                int i = merchantsUsersService.update(merchantsUsers);
                responseData.setMsg(i+"");
            }
        } catch (Exception e) {
            log.error("修改出错！");
            responseData.setSuccess(false);
            responseData.setMsg("update error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/delMerchantsUsers")
    @ResponseBody
    public Object delMerchantsUsers(@RequestParam String ids) {
        ResponseData responseData = new ResponseData();
        try {
            String mercId=null;
            LoginUser currentUser = LoginUser.getCurrentUser();
            if(3!=currentUser.getAdmType()){
                mercId = currentUser.getMercId();
            }
            int i = merchantsUsersService.batchRemoveByMercId(mercId,ids.split(","));
            responseData.setMsg("删除"+i+"条成功");
        } catch (BusinessException e) {
            log.error("删除出错！");
            responseData.setSuccess(false);
            responseData.setMsg("del error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/getById/{id}")
    @ResponseBody
    public Object getById(@PathVariable String id) {
        ResponseData responseData = new ResponseData();
        try {
            MerchantsUsers merchantsUsers = merchantsUsersService.getByUniqueKey(id);
            responseData.setData(merchantsUsers);
            responseData.setMsg("success");
        } catch (BusinessException e) {
            log.error("获取出错！");
            responseData.setSuccess(false);
            responseData.setMsg("get error!");
        }
        return responseData;
    }
}

