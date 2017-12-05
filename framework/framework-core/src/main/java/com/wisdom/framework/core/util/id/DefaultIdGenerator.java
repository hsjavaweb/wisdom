package com.wisdom.framework.core.util.id;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author hyberbin on 2016/11/2.
 */
public class DefaultIdGenerator implements IdGenerator {

    public static final IdGenerator defaultIdGenerator=new DefaultIdGenerator();

    private String machineNo = System.getenv("SEQ_MACHINE_NO")==null?"1":System.getenv("SEQ_MACHINE_NO");
    private String lastDateTime = "";
    private int sequence = 1;

    @Override
    public synchronized String getNextId() {
        String format = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        if (lastDateTime.equals(format)) {
            sequence++;
            if(sequence>9999){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                return getNextId();
            }
        } else {
            lastDateTime = format;
            sequence = 1;
        }
        return String.format("%s%05d%s", format, sequence, machineNo);
    }

    public static void main(String[] args) throws InterruptedException {
        final DefaultIdGenerator generator = new DefaultIdGenerator();
        ExecutorService pool = Executors.newFixedThreadPool(100);
        final CountDownLatch latch = new CountDownLatch(1000*1000);
        for (int i = 0; i < 1000; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        String nextId = generator.getNextId();
                        System.out.println(nextId);
                        if(nextId.length()!=20){
                            System.err.println(nextId);
                        }
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
}
