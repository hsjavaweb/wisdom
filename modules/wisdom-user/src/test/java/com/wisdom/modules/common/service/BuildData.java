package com.wisdom.modules.common.service;

import java.io.FileReader;
import java.nio.charset.Charset;

import com.wisdom.framework.core.util.id.DefaultIdGenerator;
import com.wisdom.framework.core.util.id.IdGenerator;
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
        merchantsUsers.setUserName(DefaultIdGenerator.defaultIdGenerator.getNextId());
        merchantsUsers.setEmail("hyberbin@qq.com");
        merchantsUsers.setAdminType(3);
        merchantsUsers.setRoleId(3);
        merchantsUsers.setMercId("000000");
        merchantsUsers.setIpLimit("*");
        merchantsUsers.setLastLoginIp("127.0.0.1");
        merchantsUsers.setDelFlag(0);
        merchantsUsers.setPassWord(SecureUtil.MD5String("{123456}hyberbin", "UTF-8"));
        merchantsUsersService.insert(merchantsUsers);
    }

    @Test
    public void insertMerchant() throws BusinessException {
        Merchant merchant = new Merchant();
        merchant.setLegalName("张三");
        merchant.setMercId(DefaultIdGenerator.defaultIdGenerator.getNextId());
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
        String basePath=BuildData.class.getResource("/").getPath();
        basePath=basePath.substring(0,basePath.indexOf("modules"));
        runner.runScript(new FileReader(basePath+"wisdom.sql"));
        runner.closeConnection();
        build();
    }

}
