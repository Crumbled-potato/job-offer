package com.jobsys.work.service.impl;

import java.util.List;

import com.jobsys.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jobsys.work.mapper.ApplyJobMapper;
import com.jobsys.work.domain.ApplyJob;
import com.jobsys.work.service.IApplyJobService;

/**
 * applyJobService业务层处理
 *
 * @author PengFei Deng
 * @date 2022-03-21
 */
@Service
public class ApplyJobServiceImpl implements IApplyJobService {
    @Autowired
    private ApplyJobMapper applyJobMapper;

    /**
     * 查询applyJob
     *
     * @param jobId applyJob主键
     * @return applyJob
     */
    @Override
    public ApplyJob selectApplyJobByJobId(Long jobId) {
        return applyJobMapper.selectApplyJobByJobId(jobId);
    }

    /**
     * 查询applyJob列表
     *
     * @param applyJob applyJob
     * @return applyJob
     */
    @Override
    public List<ApplyJob> selectApplyJobList(ApplyJob applyJob) {
        return applyJobMapper.selectApplyJobList(applyJob);
    }

    @Override
    public List<ApplyJob> listOrderBy(ApplyJob applyJob) {
        return applyJobMapper.listOrderBy(applyJob);
    }

    /**
     * 新增applyJob
     *
     * @param applyJob applyJob
     * @return 结果
     */
    @Override
    public int insertApplyJob(ApplyJob applyJob) {
        return applyJobMapper.insertApplyJob(applyJob);
    }

    /**
     * 修改applyJob
     *
     * @param applyJob applyJob
     * @return 结果
     */
    @Override
    public int updateApplyJob(ApplyJob applyJob) {
        applyJob.setUpdateTime(DateUtils.getNowDate());
        return applyJobMapper.updateApplyJob(applyJob);
    }

    /**
     * 批量删除applyJob
     *
     * @param jobIds 需要删除的applyJob主键
     * @return 结果
     */
    @Override
    public int deleteApplyJobByJobIds(Long[] jobIds) {
        return applyJobMapper.deleteApplyJobByJobIds(jobIds);
    }

    /**
     * 删除applyJob信息
     *
     * @param jobId applyJob主键
     * @return 结果
     */
    @Override
    public int deleteApplyJobByJobId(Long jobId) {
        return applyJobMapper.deleteApplyJobByJobId(jobId);
    }

    @Override
    public int changeState(Long jobId, String state) {
        return applyJobMapper.changeState(jobId, state);
    }
}
