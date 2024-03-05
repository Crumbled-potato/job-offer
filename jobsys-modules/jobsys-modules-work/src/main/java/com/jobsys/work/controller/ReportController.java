package com.jobsys.work.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jobsys.common.core.utils.DateUtils;
import com.jobsys.common.core.utils.poi.ExcelUtil;
import com.jobsys.common.core.web.controller.BaseController;
import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.common.core.web.page.TableDataInfo;
import com.jobsys.common.log.annotation.Log;
import com.jobsys.common.log.enums.BusinessType;
import com.jobsys.common.security.annotation.RequiresPermissions;
import com.jobsys.common.security.utils.SecurityUtils;
import com.jobsys.work.domain.ApplyJob;
import com.jobsys.work.domain.UserMsg;
import com.jobsys.work.service.IApplyJobService;
import com.jobsys.work.service.IUserMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobsys.work.domain.Report;
import com.jobsys.work.service.IReportService;


/**
 * reportController
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {
    @Autowired
    private IReportService reportService;

    @Autowired
    private IApplyJobService applyJobService;

    @Autowired
    private IUserMsgService userMsgService;

    /**
     * 查询report列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Report report) {
        startPage();
        List<Report> list = reportService.selectReportList(report);
        return getDataTable(list);
    }

    /**
     * 导出report列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Report report) {
        List<Report> list = reportService.selectReportList(report);
        ExcelUtil<Report> util = new ExcelUtil<Report>(Report.class);
        util.exportExcel(response, list, "report数据");
    }

    /**
     * 获取report详细信息
     */
    @GetMapping(value = "/{reportId}")
    public AjaxResult getInfo(@PathVariable("reportId") Integer reportId) {
        return AjaxResult.success(reportService.selectReportByReportId(reportId));
    }

    /**
     * 新增report
     */

    @PostMapping
    public AjaxResult add(@RequestBody Report report) {
        report.setCreateBy(SecurityUtils.getUsername());
        return toAjax(reportService.insertReport(report));
    }

    /**
     * 修改report
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Report report) {
        if ("1".equalsIgnoreCase(report.getRemark())) {
            Report select = reportService.selectReportByReportId(report.getReportId());
            applyJobService.changeState(report.getJobId(), "3");
            ApplyJob applyJob = applyJobService.selectApplyJobByJobId(report.getJobId());
            UserMsg userMsg = new UserMsg();
            userMsg.setIsRead("2");
            userMsg.setUserId(applyJob.getUserId());
            String msg = "您负责招聘职位的：" +
                    applyJob.getCompany().getComName() +
                    " 的 " +
                    applyJob.getJobName() +
                    " 经求职者在 " +
                    DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", select.getCreateTime()) +
                    " 举报投诉，投诉类型：" +
                    select.getType() +
                    "，具体原因：" +
                    select.getReason() +
                    "。经工作人员判定举报内容属实，现已对该职位做出下架处理！如果您有异议，请联系我们的工作人员！联系电话：18899998888 电子邮箱：8888899999@168.com";
            userMsg.setMsgContent(msg);
            int res = userMsgService.insertUserMsg(userMsg);
        }
        report.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(reportService.updateReport(report));
    }

    /**
     * 删除report
     */
    @DeleteMapping("/{reportIds}")
    public AjaxResult remove(@PathVariable Integer[] reportIds) {
        return toAjax(reportService.deleteReportByReportIds(reportIds));
    }
}
