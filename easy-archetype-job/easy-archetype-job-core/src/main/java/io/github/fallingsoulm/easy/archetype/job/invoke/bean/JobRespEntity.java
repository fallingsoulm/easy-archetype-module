package io.github.fallingsoulm.easy.archetype.job.invoke.bean;

import lombok.Data;

/**
 * 定时任务执行返回
 *
 * @author luyanan
 * @since 2021/6/25
 **/
@Data
public class JobRespEntity {

    /**
     * 成功
     *
     * @author luyan
     * @since 2021/6/25
     */
    public final static int SUCCESS = 200;

    /**
     * 进行中
     *
     * @author luyan
     * @since 2021/6/25
     */
    public final static int PROCESSING = 300;

    /**
     * 异常
     *
     * @author luyan
     * @since 2021/6/25
     */
    public final static int ERROR = 500;
    /**
     * 状态
     *
     * @author luyan
     * @since 2021/6/25
     */
    private int status;

    /**
     * 异常信息
     *
     * @author luyan
     * @since 2021/6/25
     */
    private String errorMsg;


    /**
     * 成功返回
     *
     * @return com.wblog.web.task.handler.JobRespEntity
     * @since 2021/6/25
     */
    public static JobRespEntity success() {
        JobRespEntity respEntity = new JobRespEntity();
        respEntity.setErrorMsg(null);
        respEntity.setStatus(SUCCESS);
        return respEntity;
    }


    /**
     * 异常返回
     *
     * @param errorMsg
     * @return com.wblog.web.task.handler.JobRespEntity
     * @since 2021/6/25
     */
    public static JobRespEntity error(String errorMsg) {
        JobRespEntity respEntity = new JobRespEntity();
        respEntity.setErrorMsg(errorMsg);
        respEntity.setStatus(ERROR);
        return respEntity;
    }

    public  static  JobRespEntity processing(){
        JobRespEntity respEntity = new JobRespEntity();
        respEntity.setErrorMsg(null);
        respEntity.setStatus(PROCESSING);
        return respEntity;
    }
}
