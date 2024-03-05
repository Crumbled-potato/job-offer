package com.jobsys.system.api.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 操作日志记录表 oper_log
 *
 * @author PengFei Deng
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysOperLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "操作序号", cellType = Excel.ColumnType.NUMERIC)
    private Long operId;

    @Excel(name = "操作模块")
    private String title;

    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer businessType;

    /**
     * 业务类型数组
     */
    private Integer[] businessTypes;

    @Excel(name = "请求方法")
    private String method;

    @Excel(name = "请求方式")
    private String requestMethod;

    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    private Integer operatorType;

    @Excel(name = "操作人员")
    private String operName;

    @Excel(name = "部门名称")
    private String deptName;

    @Excel(name = "请求地址")
    private String operUrl;

    @Excel(name = "操作地址")
    private String operIp;

    @Excel(name = "请求参数")
    private String operParam;

    @Excel(name = "返回参数")
    private String jsonResult;

    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private Integer status;

    @Excel(name = "错误消息")
    private String errorMsg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

}
