package com.wisdom.modules.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.wisdom.framework.core.annotation.ServiceBaseInfo;
import com.wisdom.framework.core.annotation.UpdateCache;
import com.wisdom.framework.core.annotation.UseCache;
import com.wisdom.framework.core.annotation.UseCaches;
import com.wisdom.framework.core.bean.LoginUser;
import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.service.UpdateUserLoginService;
import com.wisdom.framework.core.service.impl.BaseServiceImpl;
import com.wisdom.framework.core.util.SecureUtil;
import com.wisdom.modules.common.domain.MerchantMini;
import com.wisdom.modules.common.domain.MerchantsUsers;
import com.wisdom.modules.common.persistence.MerchantsUsersMapper;
import com.wisdom.modules.common.service.MerchantService;
import com.wisdom.modules.common.service.MerchantsUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author hyberbin on 2016/10/30.
 */
@Service("merchantsUsersService")
@ServiceBaseInfo(poType = MerchantsUsers.class, mapperType = MerchantsUsersMapper.class, uniqueKey = "userName", ignoreTests = "getByUniqueKey,batchRemove")
@UseCaches(expireSeconds = 5 * 60)
public class MerchantsUsersServiceImpl extends BaseServiceImpl<MerchantsUsers> implements MerchantsUsersService, UserDetailsService, UpdateUserLoginService {
    protected static final Logger log = LoggerFactory.getLogger(MerchantsUsersServiceImpl.class);

    @Autowired
    private MerchantsUsersMapper merchantsUsersMapper;

    @Autowired
    private MerchantService merchantService;

    @UpdateCache(keys = "#merchantsUsers.userName")
    @Override
    public int insert(MerchantsUsers merchantsUsers) throws BusinessException {
        try {
            if (merchantsUsers.getPassWord() != null) {
                merchantsUsers.setPassWord(SecureUtil.MD5String(merchantsUsers.getPassWord() + "{" + merchantsUsers.getUserName() + "}", "utf-8"));
            }
            return super.insert(merchantsUsers);
        } catch (DataAccessException e) {
            log.error("写入数据出错！{}", e, JSON.toJSONString(merchantsUsers));
            throw new BusinessException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log.error("找不到md5加密算法！", e);
            throw new BusinessException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码格式！utf-8", e);
            throw new BusinessException(e.getMessage());
        }

    }

    @UpdateCache(keys = "#merchantsUsers.userName")
    @Override
    public int update(MerchantsUsers merchantsUsers) throws BusinessException {
        try {
            if (merchantsUsers.getPassWord() != null) {
                merchantsUsers.setPassWord(SecureUtil.MD5String(merchantsUsers.getPassWord() + "{" + merchantsUsers.getUserName() + "}", "utf-8"));
            }
            return super.update(merchantsUsers);
        } catch (DataAccessException e) {
            log.error("更新数据出错！{}", e, JSON.toJSONString(merchantsUsers));
            throw new BusinessException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log.error("找不到md5加密算法！", e);
            throw new BusinessException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码格式！utf-8", e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public int getMercUserCountByRoleId(String roleId) throws BusinessException {
        try {
            return merchantsUsersMapper.getMercUserCountByRoleId(roleId);
        } catch (DataAccessException e) {
            log.error("获取数据出错！{}", e, roleId);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @UseCache(key = "#id")
    public MerchantsUsers getByUniqueKey(String id) throws BusinessException {
        try {
            MerchantsUsers merchantsUsersByName = super.getByUniqueKey(id);
            if (merchantsUsersByName != null) {
                merchantsUsersByName.setPassWord(null);
            }
            return merchantsUsersByName;
        } catch (DataAccessException e) {
            log.error("获取数据出错！{}", e, id);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 获取系统管理员和当前商户管理员的邮箱
     *
     * @param mercId
     * @return
     * @throws BusinessException
     */

    @Override
    @UseCache(cacheName = "merchantsUsersService.adminMail", key = "#mercId")
    public String getAdminMail(String mercId) throws BusinessException {
        try {
            return merchantsUsersMapper.getAdminMail(mercId);
        } catch (DataAccessException e) {
            log.error("获取系统管理员和当前商户管理员的邮箱出错！{}", e, mercId);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LoginUser userDetails = new LoginUser();
        try {
            MerchantsUsers user = merchantsUsersMapper.getByUniqueKey(s);
            if (user == null) {
                userDetails.setEnabled(true);
                return userDetails;
            }
            userDetails.setUsername(s);
            userDetails.setMercId(user.getMercId());
            userDetails.setRoleId(user.getRoleId());
            userDetails.setAdmType(user.getAdminType());
            userDetails.setEmail(user.getEmail());
            userDetails.setPassword(user.getPassWord());
            userDetails.setEnabled(true);
            userDetails.setIpLimit(getUserIpLimit(s));
            MerchantMini merchantMini = merchantService.getMerchantWholeInfoFormCache(user.getMercId());
            userDetails.setHost(merchantMini.getHost());
        } catch (Throwable e) {
            throw new UsernameNotFoundException("can not find user", e);
        }
        return userDetails;
    }

    @UpdateCache(keys = "#userName")
    public boolean addIp(String ip, String userName) throws BusinessException {
        if (ip != null) {
            MerchantsUsers user = getByUniqueKey(userName);
            if (user == null) {
                return false;
            } else {
                MerchantsUsers merchantsUsers = new MerchantsUsers();
                merchantsUsers.setUserName(userName);
                String ipLimit = user.getIpLimit() == null ? ";" : user.getIpLimit();
                ipLimit = ipLimit.endsWith(";") ? ipLimit + ip : ipLimit + ";" + ip;
                ipLimit = ipLimit.startsWith(";") ? ipLimit.substring(1) : ipLimit;
                if (ipLimit.length() > 300) {
                    ipLimit = ipLimit.substring(ipLimit.length() - 300);
                }
                merchantsUsers.setIpLimit(ipLimit);
                int i = update(merchantsUsers);
                return i == 1;
            }
        } else {
            return false;
        }
    }

    public String getUserIpLimit(String userName) {
        try {
            MerchantsUsers user = merchantsUsersMapper.getByUniqueKey(userName);
            if (user == null) {
                return "";
            } else {
                MerchantMini merchant = merchantService.getMerchantWholeInfoFormCache(user.getMercId());
                String ipLimit = user.getIpLimit() == null ? "" : user.getIpLimit();
                String merchantLimit = merchant.getIpLimit() == null ? "" : merchant.getIpLimit();
                ipLimit = ipLimit.endsWith(";") ? ipLimit + merchantLimit : ipLimit + ";" + merchantLimit;
                ipLimit = ipLimit.startsWith(";") ? ipLimit.substring(1) : ipLimit;
                return ipLimit;
            }
        } catch (Throwable e) {
            log.error("更新用户登录IP失败", e);
        }
        return "";
    }

    @UpdateCache(keys = "#userName")
    @Override
    public void updateLoginIp(String userName, String ip) throws BusinessException {
        try {
            MerchantsUsers user = merchantsUsersMapper.getByUniqueKey(userName);
            user.setLastLoginIp(ip);
            user.setLastLoginTime(new Date());
            merchantsUsersMapper.update(user);
        } catch (DataAccessException e) {
            log.error("更新用户登录IP失败", e);
            throw new BusinessException("更新用户登录IP失败");
        }
    }

    @UpdateCache(keys = "'*'")
    @Override
    public int batchRemoveByMercId(String mercId, String... ids) throws BusinessException {
        try {
            return merchantsUsersMapper.batchRemove(mercId, ids);
        } catch (DataAccessException e) {
            log.error("删除数据出错！mercId:{},ids{}", e, mercId, JSON.toJSONString(ids));
            throw new BusinessException(e.getMessage());
        }
    }
}

