package com.jobsys.system.api.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author PengFei Deng
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLogininfor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long infoId;

    @Excel(name = "用户账号")
    private String userName;

    @Excel(name = "状态", readConverterExp = "0=成功,1=失败")
    private String status;

    @Excel(name = "地址")
    private String ipaddr;

    @Excel(name = "描述")
    private String msg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date accessTime;
}