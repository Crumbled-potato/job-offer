package com.jobsys.work.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 公司对象 company
 *
 * @author PengFei Deng
 * @date 2022-03-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"handler"})
public class Company extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long comId;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String comName;

    /**
     * 公司图片
     */
    @Excel(name = "公司图片")
    private String comImg;

    /**
     * 公司简介
     */
    @Excel(name = "公司简介")
    private String comInfo;

    /**
     * 公司类型
     */
    @Excel(name = "公司类型")
    private String comType;

    /**
     * 是否上市
     */
    @Excel(name = "是否上市")
    private String isMarket;

    /**
     * 公司类型电话
     */
    @Excel(name = "公司类型电话")
    private String comTel;

    /**
     * 公司规模人数
     */
    @Excel(name = "公司规模人数")
    private String comScale;

    /**
     * 公司工作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "公司工作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String workTime;

    /**
     * 公司处于省份
     */
    @Excel(name = "公司处于省份")
    private String province;

    /**
     * 公司处于地级市
     */
    @Excel(name = "公司处于地级市")
    private String property;

    private String district;

    /**
     * 公司详细地址
     */
    @Excel(name = "公司详细地址")
    private String address;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private String isDelete;


    private List<String> workTime1;

    /**
     * 该公司包含的招聘职位
     */
    private List<ApplyJob> applyJobList;

    /**
     * 公司拥有者Id
     */
    private Long userId;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("comId", getComId())
                .append("comName", getComName())
                .append("comImg", getComImg())
                .append("comInfo", getComInfo())
                .append("comType", getComType())
                .append("isMarket", getIsMarket())
                .append("comTel", getComTel())
                .append("comScale", getComScale())
                .append("workTime", getWorkTime())
                .append("province", getProvince())
                .append("property", getProperty())
                .append("address", getAddress())
                .append("isDelete", getIsDelete())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
