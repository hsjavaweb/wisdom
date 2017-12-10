package com.wisdom.modules.common.service;

import com.wisdom.framework.core.annotation.ServiceBaseInfo;
import com.wisdom.framework.core.context.SpringContextUtil;
import com.wisdom.framework.core.service.BaseService;
import com.wisdom.framework.core.util.AopTargetUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author hyberbin on 2017/8/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:test/applicationContext*.xml",
        "classpath:com/wisdom/modules/common/applicationContext-dao.xml"
})
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class BaseServiceTest {

    private void fillObject(final Object object){
        ReflectionUtils.doWithFields(object.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
               if(!Modifier.isFinal(field.getModifiers())&&
                       field.getDeclaringClass().equals(object.getClass())
                       ){
                   field.setAccessible(true);
                   field.set(object,getTypeValue(field.getType()));
               }
            }
        });
    }

    private Object  getTypeValue(Class type){
        if(Integer.class.equals(type)){
            return new Random().nextInt(10);
        }else if(Long.class.equals(type)){
            return Long.valueOf(new Random().nextInt(100));
        }else if(String.class.equals(type)){
            return  UUID.randomUUID().toString().substring(0,2);
        }else if(Date.class.equals(type)){
            return  new Date();
        }else if(Float.class.equals(type)){
            return  new Random().nextFloat();
        }else if(Double.class.equals(type)){
            return  Double.valueOf(new Random().nextFloat());
        }
        return null;
    }

    private Object getUniqueKey(Object po,final String fieldName) throws IllegalAccessException {
        final Field field = ReflectionUtils.findField(po.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(po);
    }

    private void setUniqueKey(Object po,final String fieldName,Object value) throws IllegalAccessException {
        final Field field = ReflectionUtils.findField(po.getClass(), fieldName);
        field.setAccessible(true);
        field.set(po,value);
    }

    @Test
    @Rollback(true)
    public void testBaseService() throws Exception {
        final Collection<BaseService> beans = SpringContextUtil.getBeans(BaseService.class);
        for(BaseService baseService:beans){
            final Object originService = AopTargetUtils.getTarget(baseService);
            System.out.println(originService.getClass().getSimpleName());
            final ServiceBaseInfo serviceBaseInfo = originService.getClass().getAnnotation(ServiceBaseInfo.class);
            final String ignores = serviceBaseInfo.ignoreTests();
            final Object po = serviceBaseInfo.poType().newInstance();
            fillObject(po);
            if(ignores.equals("*")) continue;
            if(!ignores.contains("insert")){
                final int insert = baseService.insert(po);
                Assert.assertTrue(insert==1);
            }

            final Object uniqueKey = getUniqueKey(po, serviceBaseInfo.uniqueKey());
            if(!ignores.contains("getByUniqueKey")){
                final Object byUniqueKey = baseService.getByUniqueKey(uniqueKey.toString());
                Assert.assertNotNull(byUniqueKey);
            }

            if(!ignores.contains("list")){
                final List list = baseService.list(Collections.EMPTY_MAP);
                Assert.assertTrue(list.size()>=0);
            }

            if(!ignores.contains("update")){
                fillObject(po);
                setUniqueKey(po,serviceBaseInfo.uniqueKey(),uniqueKey);
                final int update = baseService.update(po);
                Assert.assertTrue(update==1||update==0);
            }

            if(!ignores.contains("batchRemove")){
                final int i = baseService.batchRemove(uniqueKey.toString());
                Assert.assertTrue(i==1||i==0);
            }

        }
    }
}
