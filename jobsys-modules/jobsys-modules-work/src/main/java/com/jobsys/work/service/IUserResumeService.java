package com.jobsys.work.service;

import java.util.List;

import com.jobsys.work.domain.UserResume;

/**
 * resumeService接口
 *
 * @author PengFei Deng
 * @date 2022-04-25
 */
public interface IUserResumeService {
    /**
     * 查询resume
     *
     * @param resumeId resume主键
     * @return resume
     */
    UserResume selectUserResumeByResumeId(Integer resumeId);

    /**
     * 查询resume列表
     *
     * @param userResume resume
     * @return resume集合
     */
    List<UserResume> selectUserResumeList(UserResume userResume);

    /**
     * 新增resume
     *
     * @param userResume resume
     * @return 结果
     */
    int insertUserResume(UserResume userResume);

    /**
     * 修改resume
     *
     * @param userResume resume
     * @return 结果
     */
    int updateUserResume(UserResume userResume);

    /**
     * 批量删除resume
     *
     * @param resumeIds 需要删除的resume主键集合
     * @return 结果
     */
    int deleteUserResumeByResumeIds(Integer[] resumeIds);

    /**
     * 删除resume信息
     *
     * @param resumeId resume主键
     * @return 结果
     */
    int deleteUserResumeByResumeId(Integer resumeId);
}
