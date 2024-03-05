package com.jobsys.work.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * business对象 business
 *
 * @author PengFei Deng
 * @date 2022-04-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Business extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long businessId;

    /**
     * 法人
     */
    @Excel(name = "法人")
    private String legal;

    /**
     * 注册资本
     */
    @Excel(name = "注册资本")
    private String capital;

    /**
     * 成立时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date setUpTime;

    /**
     * 企业类型
     */
    @Excel(name = "企业类型")
    private String comType;

    /**
     * 经营状态
     */
    @Excel(name = "经营状态")
    private String comState;

    /**
     * 注册地址
     */
    @Excel(name = "注册地址")
    private String address;

    /**
     * 统一社会信用代码
     */
    @Excel(name = "统一社会信用代码")
    private String comCode;

    /**
     * 公司经营范围
     */
    @Excel(name = "公司经营范围")
    private String comScope;

    /**
     * 审核阶段
     */
    @Excel(name = "审核阶段")
    private String checkState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    /**
     * 公司Id
     */
    @Excel(name = "公司Id")
    private Long comId;


}
