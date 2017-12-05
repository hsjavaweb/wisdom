package com.wisdom.modules.common.controller;

import com.wisdom.framework.core.annotation.ValidateBean;
import com.wisdom.framework.core.bean.LoginUser;
import com.wisdom.framework.core.bean.ResponseData;
import com.wisdom.framework.core.controller.BaseController;
import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.data.Pager;
import com.wisdom.framework.core.data.PagerRequest;
import com.wisdom.modules.common.domain.Merchant;
import com.wisdom.modules.common.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


/**
 * @author hyberbin on 2016/10/30.
 */
@Controller
@RequestMapping("/api/merchant")
public class MerchantController extends BaseController {
    @Autowired
    private MerchantService merchantService;


    @RequestMapping(value = "/listMerchant")
    @ResponseBody
    @PreAuthorize(LoginUser.ROLE_ADMIN)
    public Object listMerchant(@RequestBody @ValidateBean PagerRequest pagerRequest) {
        ResponseData responseData = new ResponseData();
        try {
            LoginUser currentUser = LoginUser.getCurrentUser();
            Map data = pagerRequest.getData();
            if(currentUser.getAdmType()!=3){
                data.put("mercId", currentUser.getMercId());
            }
            if(data!=null&&data.containsKey("mercName")){
                data.put("mercName","%"+data.get("mercName")+"%");
            }
            if(data!=null&&data.containsKey("mercName")){
                data.put("legalName","%"+data.get("legalName")+"%");
            }
            Pager pager = merchantService.listPager(pagerRequest.getStartPage(), pagerRequest.getPageLength(), data);
            responseData.setData(pager);
        } catch (BusinessException e) {
            log.error("查询出错！");
            responseData.setSuccess(false);
            responseData.setMsg("query error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/listMerchantCombox")
    @ResponseBody
    @PreAuthorize(LoginUser.ROLE_ADMIN)
    public Object listMerchantCombox() {
        ResponseData responseData = new ResponseData();
        try {
            responseData.setData(merchantService.list(Collections.EMPTY_MAP));
        } catch (BusinessException e) {
            log.error("查询出错！");
            responseData.setSuccess(false);
            responseData.setMsg("query error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/addMerchant")
    @ResponseBody
    @PreAuthorize(LoginUser.ROLE_SUPERADMIN)
    public Object addMerchant(@RequestBody @ValidateBean(exclude = {"mercId"}) Merchant merchant) {
        ResponseData responseData = new ResponseData();
        try {
            merchantService.insert(merchant);
        } catch (BusinessException e) {
            log.error("添加出错！");
            responseData.setSuccess(false);
            responseData.setMsg("add error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/updateMerchant")
    @ResponseBody
    @PreAuthorize(LoginUser.ROLE_ADMIN)
    public Object updateMerchant(@RequestBody Merchant merchant) {
        ResponseData responseData = new ResponseData();
        try {
            int i = merchantService.update(merchant);
            responseData.setMsg(i + "");
        } catch (BusinessException e) {
            log.error("修改出错！");
            responseData.setSuccess(false);
            responseData.setMsg("update error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/delMerchant")
    @ResponseBody
    @PreAuthorize(LoginUser.ROLE_SUPERADMIN)
    public Object delMerchant(@RequestParam String ids) {
        ResponseData responseData = new ResponseData();
        try {
            int i = merchantService.batchRemove(ids.split(","));
            responseData.setMsg(i + "");
        } catch (BusinessException e) {
            log.error("删除出错！");
            responseData.setSuccess(false);
            responseData.setMsg("del error!");
        }
        return responseData;
    }

    @RequestMapping(value = "/getById/{id}")
    @ResponseBody
    @PreAuthorize(LoginUser.ROLE_OPERATOR)
    public Object getById(@PathVariable String id) {
        ResponseData responseData = new ResponseData();
        try {
            Merchant merchant = merchantService.getByUniqueKey(id);
            responseData.setData(merchant);
            responseData.setMsg("success");
        } catch (BusinessException e) {
            log.error("获取出错！");
            responseData.setSuccess(false);
            responseData.setMsg("get error!");
        }
        return responseData;
    }

}
