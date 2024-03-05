package com.jobsys.work.service.impl;

import com.jobsys.common.core.utils.DateUtils;
import com.jobsys.work.domain.Report;
import com.jobsys.work.mapper.ReportMapper;
import com.jobsys.work.service.IApplyJobService;
import com.jobsys.work.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * reportService业务层处理
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
@Service
public class ReportServiceImpl implements IReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private IApplyJobService applyJobService;

    /**
     * 查询report
     *
     * @param reportId report主键
     * @return report
     */
    @Override
    public Report selectReportByReportId(Integer reportId) {
        return reportMapper.selectReportByReportId(reportId);
    }

    /**
     * 查询report列表
     *
     * @param report report
     * @return report
     */
    @Override
    public List<Report> selectReportList(Report report) {
        return reportMapper.selectReportList(report);
    }

    /**
     * 新增report
     *
     * @param report report
     * @return 结果
     */
    @Override
    public int insertReport(Report report) {
        report.setCreateTime(DateUtils.getNowDate());
        return reportMapper.insertReport(report);
    }

    /**
     * 修改report
     *
     * @param report report
     * @return 结果
     */
    @Override
    @Transactional
    public int updateReport(Report report) {
        report.setUpdateTime(DateUtils.getNowDate());
        if (report.getRemark() != null && "1".equalsIgnoreCase(report.getRemark())) {
            applyJobService.changeState(report.getJobId(), "3");
        }
        return reportMapper.updateReport(report);
    }

    /**
     * 批量删除report
     *
     * @param reportIds 需要删除的report主键
     * @return 结果
     */
    @Override
    public int deleteReportByReportIds(Integer[] reportIds) {
        return reportMapper.deleteReportByReportIds(reportIds);
    }

    /**
     * 删除report信息
     *
     * @param reportId report主键
     * @return 结果
     */
    @Override
    public int deleteReportByReportId(Integer reportId) {
        return reportMapper.deleteReportByReportId(reportId);
    }
}
