package com.jobsys.work.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * record对象 user_apply_record
 *
 * @author PengFei Deng
 * @date 2022-04-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserApplyRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Integer recordId;

    /**
     * 工作ID
     */
    @Excel(name = "工作ID")
    private Long jobId;

    /**
     * 工作名称
     */
    @Excel(name = "工作名称")
    private String jobName;

    /**
     * 求职ID
     */
    @Excel(name = "求职ID")
    private Long applyId;

    /**
     * 负责人ID
     */
    @Excel(name = "负责人ID")
    private Long takeId;

    /**
     * 类型 1投递 2邀约
     */
    @Excel(name = "类型 1投递 2邀约")
    private String type;

    /**
     * 求职这是否符合要求
     */
    @Excel(name = "求职这是否符合要求")
    private String isReq;

    /**
     * 是否面试
     */
    @Excel(name = "是否面试")
    private String isAgree;

    /**
     * 面试时间
     */
    @Excel(name = "面试时间")
    private String applyAddress;

    /**
     * 面试地点
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "面试地点", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String comName;

    /**
     * 公司Id
     */
    @Excel(name = "公司Id")
    private Long comId;

    private Long resumeId;


}
