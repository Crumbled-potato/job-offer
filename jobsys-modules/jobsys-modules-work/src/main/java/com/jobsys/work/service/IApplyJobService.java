package com.jobsys.work.service;

import java.util.List;

import com.jobsys.work.domain.ApplyJob;

/**
 * applyJobService接口
 *
 * @author PengFei Deng
 * @date 2022-03-21
 */
public interface IApplyJobService {
    /**
     * 查询applyJob
     *
     * @param jobId applyJob主键
     * @return applyJob
     */
    ApplyJob selectApplyJobByJobId(Long jobId);

    /**
     * 查询applyJob列表
     *
     * @param applyJob applyJob
     * @return applyJob集合
     */
    List<ApplyJob> selectApplyJobList(ApplyJob applyJob);


    List<ApplyJob> listOrderBy(ApplyJob applyJob);

    /**
     * 新增applyJob
     *
     * @param applyJob applyJob
     * @return 结果
     */
    int insertApplyJob(ApplyJob applyJob);

    /**
     * 修改applyJob
     *
     * @param applyJob applyJob
     * @return 结果
     */
    int updateApplyJob(ApplyJob applyJob);

    /**
     * 批量删除applyJob
     *
     * @param jobIds 需要删除的applyJob主键集合
     * @return 结果
     */
    int deleteApplyJobByJobIds(Long[] jobIds);

    /**
     * 删除applyJob信息
     *
     * @param jobId applyJob主键
     * @return 结果
     */
    int deleteApplyJobByJobId(Long jobId);

    /**
     * 改变工作状态
     *
     * @param jobId 职位Id
     * @param state 改变的状态
     * @return int
     * @author PengFei Deng
     * @date 2022/3/23 20:28
     */
    int changeState(Long jobId, String state);
}
