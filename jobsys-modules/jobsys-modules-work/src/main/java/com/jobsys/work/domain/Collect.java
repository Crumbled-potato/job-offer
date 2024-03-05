package com.jobsys.work.domain;

import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * collect对象 collect
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Collect extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer colletId;

    /**
     * 职位Id
     */
    @Excel(name = "职位Id")
    private Long jobId;

    /**
     * 公司Id
     */
    @Excel(name = "公司Id")
    private Long comId;

    /**
     * 用户Id
     */
    @Excel(name = "用户Id")
    private Long userId;

    /**
     * 用户账户
     */
    @Excel(name = "用户账户")
    private String userName;

    /**
     * 职位名称
     */
    @Excel(name = "职位名称")
    private String jobName;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String comName;

}
