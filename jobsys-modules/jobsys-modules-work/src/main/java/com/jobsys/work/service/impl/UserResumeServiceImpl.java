package com.jobsys.work.service.impl;

import com.jobsys.common.core.utils.DateUtils;
import com.jobsys.work.domain.UserResume;
import com.jobsys.work.mapper.UserResumeMapper;
import com.jobsys.work.service.IUserResumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * resumeService业务层处理
 *
 * @author PengFei Deng
 * @date 2022-04-25
 */
@Service
public class UserResumeServiceImpl implements IUserResumeService {
    @Autowired
    private UserResumeMapper userResumeMapper;

    /**
     * 查询resume
     *
     * @param resumeId resume主键
     * @return resume
     */
    @Override
    public UserResume selectUserResumeByResumeId(Integer resumeId) {
        return userResumeMapper.selectUserResumeByResumeId(resumeId);
    }

    /**
     * 查询resume列表
     *
     * @param userResume resume
     * @return resume
     */
    @Override
    public List<UserResume> selectUserResumeList(UserResume userResume) {
        return userResumeMapper.selectUserResumeList(userResume);
    }

    /**
     * 新增resume
     *
     * @param userResume resume
     * @return 结果
     */
    @Override
    public int insertUserResume(UserResume userResume) {
        userResume.setCreateTime(DateUtils.getNowDate());
        return userResumeMapper.insertUserResume(userResume);
    }

    /**
     * 修改resume
     *
     * @param userResume resume
     * @return 结果
     */
    @Override
    public int updateUserResume(UserResume userResume) {
        userResume.setUpdateTime(DateUtils.getNowDate());
        return userResumeMapper.updateUserResume(userResume);
    }

    /**
     * 批量删除resume
     *
     * @param resumeIds 需要删除的resume主键
     * @return 结果
     */
    @Override
    public int deleteUserResumeByResumeIds(Integer[] resumeIds) {
        return userResumeMapper.deleteUserResumeByResumeIds(resumeIds);
    }

    /**
     * 删除resume信息
     *
     * @param resumeId resume主键
     * @return 结果
     */
    @Override
    public int deleteUserResumeByResumeId(Integer resumeId) {
        return userResumeMapper.deleteUserResumeByResumeId(resumeId);
    }
}
