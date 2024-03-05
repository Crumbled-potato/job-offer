package com.jobsys.work.mapper;

import java.util.List;

import com.jobsys.work.domain.Report;

/**
 * reportMapper接口
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
public interface ReportMapper {
    /**
     * 查询report
     *
     * @param reportId report主键
     * @return report
     */
    Report selectReportByReportId(Integer reportId);

    /**
     * 查询report列表
     *
     * @param report report
     * @return report集合
     */
    List<Report> selectReportList(Report report);

    /**
     * 新增report
     *
     * @param report report
     * @return 结果
     */
    int insertReport(Report report);

    /**
     * 修改report
     *
     * @param report report
     * @return 结果
     */
    int updateReport(Report report);

    /**
     * 删除report
     *
     * @param reportId report主键
     * @return 结果
     */
    int deleteReportByReportId(Integer reportId);

    /**
     * 批量删除report
     *
     * @param reportIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteReportByReportIds(Integer[] reportIds);
}
