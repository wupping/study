package com.wuping.spring;

import com.jiedaibao.baseframework.common.drm.AppDrmNode;
import com.jiedaibao.baseframework.common.drm.DrmZookeeprClient;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerBean;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by wupingping on 16/6/16.
 */
public class Quartz {

    private static final Logger logger = LoggerFactory.getLogger(Quartz.class);

    @Resource
    private DrmZookeeprClient   drmZookeeprClient;

    @Resource
    private CronTriggerBean     cronTriggerBean;

    @Resource
    private Scheduler           scheduler;

    private String              cronTriggerConfig;

    public CronTriggerBean getCronTriggerBean() {
        return cronTriggerBean;
    }

    public void setCronTriggerBean(CronTriggerBean cronTriggerBean) {
        this.cronTriggerBean = cronTriggerBean;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setCronTriggerConfig(String cronTriggerConfig) {
        this.cronTriggerConfig = cronTriggerConfig;
        cronTriggerConfig = cronTriggerConfig.replace("_", " ");
        System.out.println(cronTriggerConfig);
        try {
            CronExpression cronExpression = new CronExpression(cronTriggerConfig);

            // this.cronTriggerBean.setCronExpression(cronTriggerConfig);
            this.cronTriggerBean.setCronExpression(cronExpression);
            scheduler.rescheduleJob(cronTriggerBean.getName(), cronTriggerBean.getGroup(), cronTriggerBean);
        } catch (Exception e) {
            logger.error("exception occus:", e);
        }
        System.out.println("get value of cronTriggerConfig");
    }

    public String getCronTriggerConfig() {
        return cronTriggerConfig;
    }

    public void setDrmZookeeprClient(DrmZookeeprClient drmZookeeprClient) {
        this.drmZookeeprClient = drmZookeeprClient;
    }

    public void init() {
        AppDrmNode logLevelNode = new AppDrmNode(this, "cronTriggerConfig", this.cronTriggerConfig);
        this.drmZookeeprClient.confRegist(logLevelNode, true);
    }

    public void run() {
        // System.out.println("Shedule run!");
        System.out.println(this.cronTriggerConfig);
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");// 读取bean.xml中的内容
        ctx.getBean("quartzJob");
        // Person p = ctx.getBean("person",Person.class);//创建bean的引用对象
        // System.out.println(p.getName());
        System.in.read();
    }
}
