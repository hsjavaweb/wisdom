package com.wisdom.modules.common.service;

import java.io.FileReader;
import java.nio.charset.Charset;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wisdom.framework.core.context.SpringContextUtil;
import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.util.SecureUtil;
import com.wisdom.modules.common.domain.Merchant;
import com.wisdom.modules.common.domain.MerchantsUsers;


/**
 * @author hyberbin on 2016/11/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:test/applicationContext-test-conf.xml",
        "classpath*:test/applicationContext-mq.xml",
        "classpath:com/wisdom/modules/common/applicationContext-dao.xml"
})
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class BuildData {
    //private static final String HOST="http://pay.qiinc.cc";
    private static final String      HOST = "http://localhost:8080";

    @Autowired
    protected MerchantsUsersService  merchantsUsersService;

    @Autowired
    protected MerchantService        merchantService;



    @Test
    public void insertMerchantsUsers() throws Exception {
        MerchantsUsers merchantsUsers = new MerchantsUsers();
        merchantsUsers.setUserName("hyberbin");
        merchantsUsers.setEmail("hyberbin@qq.com");
        merchantsUsers.setAdminType(3);
        merchantsUsers.setRoleId(3);
        merchantsUsers.setMercId("000000");
        merchantsUsers.setIpLimit("*");
        merchantsUsers.setLastLoginIp("127.0.0.1");
        merchantsUsers.setDelFlag(0);
        merchantsUsers.setPassWord(SecureUtil.MD5String("{123456}hyberbin", "UTF-8"));
        merchantsUsersService.insert(merchantsUsers);
        merchantsUsers.setUserName("harry");
        merchantsUsers.setEmail("hyberbin@qq.com");
        merchantsUsers.setPassWord(SecureUtil.MD5String("{123456}harry", "UTF-8"));
        merchantsUsersService.insert(merchantsUsers);
        merchantsUsers = new MerchantsUsers();
        merchantsUsers.setUserName("hyberbin1");
        merchantsUsers.setEmail("hyberbin@qq.com");
        merchantsUsers.setAdminType(1);
        merchantsUsers.setMercId("000001");
        merchantsUsers.setIpLimit("*");
        merchantsUsers.setLastLoginIp("127.0.0.1");
        merchantsUsers.setDelFlag(0);
        merchantsUsers.setPassWord(SecureUtil.MD5String("{123456}hyberbin1", "UTF-8"));
        merchantsUsersService.insert(merchantsUsers);
        merchantsUsers = new MerchantsUsers();
        merchantsUsers.setEmail("hyberbin@qq.com");
        merchantsUsers.setUserName("hyberbin2");
        merchantsUsers.setAdminType(2);
        merchantsUsers.setMercId("000001");
        merchantsUsers.setIpLimit("*");
        merchantsUsers.setLastLoginIp("127.0.0.1");
        merchantsUsers.setDelFlag(0);
        merchantsUsers.setPassWord(SecureUtil.MD5String("{123456}hyberbin2", "UTF-8"));
        merchantsUsersService.insert(merchantsUsers);
    }

    @Test
    public void insertMerchant() throws BusinessException {
        Merchant merchant = new Merchant();
        merchant.setLegalName("黄迎兵");
        merchant.setMercId("000001");
        merchant.setMercKey("123456");
        merchant.setMercSafeKey("123456");
        merchant.setMobile("11213123131");
        merchant.setPhone("12131231");
        merchant.setMercName("测试商户");
        merchant.setDelFlag(0);
        merchant.setPayRate(0f);
        merchant.setPayRate2(0f);
        merchant.setCashRate(0f);
        merchant.setRefundRate(0f);
        merchant.setChargeBackRate(0f);
        merchant.setRemitRate(0f);
        merchant.setIpLimit("*");
        merchant.setHost("*");
        merchantService.insert(merchant);
        merchant.setMercId("000000");
        merchant.setMercName("系统商户");
        merchant.setPayRate(0f);
        merchant.setRemitRate(0f);
        merchantService.insert(merchant);
    }

    @Test
    public void build() throws Exception {
        insertMerchant();
        insertMerchantsUsers();
    }

    @Test
    public void rebuildDb() throws Exception {
        DriverManagerDataSource dataSource = SpringContextUtil.getBean("common");
        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        Resources.setCharset(Charset.forName("GBK")); //设置字符集,不然中文乱码插入错误
        runner.setLogWriter(null);//设置是否输出日志
        runner.runScript(new FileReader("/Users/hyberbin/jplus/wisdom/wisdom.sql"));
        runner.closeConnection();
        build();
    }

}
