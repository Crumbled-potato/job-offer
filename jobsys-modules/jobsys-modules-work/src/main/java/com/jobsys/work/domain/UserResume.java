package com.jobsys.work.domain;

import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * resume对象 user_resume
 *
 * @author PengFei Deng
 * @date 2022-04-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResume extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer resumeId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String userName;

    /**
     * 用户账户
     */
    @Excel(name = "用户账户")
    private Long userId;

    /**
     * 手机
     */
    @Excel(name = "手机")
    private String userPhone;

    /**
     * 详情
     */
    @Excel(name = "详情")
    private String resumeDetail;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String userEmail;

    /**
     * 城市
     */
    @Excel(name = "城市")
    private String userCity;

    /**
     * 学历
     */
    @Excel(name = "学历")
    private String education;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String userSex;

    /**
     * 出生日期
     */
    @Excel(name = "出生日期")
    private String userBirth;

    /**
     * 意向职位
     */
    @Excel(name = "意向职位")
    private String applyJob;

    /**
     * 意向行业
     */
    @Excel(name = "意向行业")
    private String applyType;

    /**
     * 简历地址
     */
    @Excel(name = "简历地址")
    private String resumeFile;

    /**
     * 专业技能
     */
    @Excel(name = "专业技能")
    private String major;

    /**
     * 教育经历
     */
    @Excel(name = "教育经历")
    private String eduExp;

    /**
     * 头像地址
     */
    @Excel(name = "头像地址")
    private String userImg;
}
