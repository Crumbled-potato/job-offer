package com.jobsys.work.domain;

import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * report对象 report
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Report extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer reportId;

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
     * 举报人Id
     */
    @Excel(name = "举报人Id")
    private String userName;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String userDescribe;

    /**
     * 举报人联系方式
     */
    @Excel(name = "举报人联系方式")
    private String phone;

    /**
     * 举报类型
     */
    @Excel(name = "举报类型")
    private String type;

    /**
     * 原因
     */
    @Excel(name = "原因")
    private String reason;

    private String remark;

}
