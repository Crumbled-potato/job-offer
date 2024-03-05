package com.jobsys.work.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * applyJob对象 apply_job
 *
 * @author PengFei Deng
 * @date 2022-03-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"handler"})
public class ApplyJob extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long jobId;

    /**
     * 状态
     */
    @NotNull
    @Excel(name = "状态")
    private Integer jobState;

    /**
     * 名称
     */
    @NotNull
    @Excel(name = "名称")
    private String jobName;

    /**
     * 工资
     */
    @NotNull
    @Excel(name = "工资")
    private String jobSalary;

    /**
     * 任职要求
     */
    @NotNull
    @Excel(name = "任职要求")
    private String jobReq;

    /**
     * 工作详情
     */
    @NotNull
    @Excel(name = "工作详情")
    private String jobDetail;

    /**
     * 学历要求
     */
    @NotNull
    @Excel(name = "学历要求")
    private String education;

    /**
     * 工作经验
     */
    @NotNull
    @Excel(name = "工作经验")
    private String expYear;

    /**
     * 工作城市
     */
    @NotNull
    @Excel(name = "工作城市")
    private String jobSite;

    /**
     * 发布人ID
     */
    @Excel(name = "发布人ID")
    private Long userId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long comId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creatTime;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String creatBy;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private String isDelete;

    //薪资范围 左 右
    private Double lowSalary;
    private Double heightSalary;

    /**
     * 职位关联的公司
     */
    private Company company;


    private Integer likeSum;


    private String jobType;
}
