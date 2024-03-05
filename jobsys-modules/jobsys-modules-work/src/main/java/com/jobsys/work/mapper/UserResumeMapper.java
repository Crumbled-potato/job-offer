package com.jobsys.work.mapper;

import java.util.List;

import com.jobsys.work.domain.UserResume;

/**
 * resumeMapper接口
 *
 * @author PengFei Deng
 * @date 2022-04-25
 */
public interface UserResumeMapper {
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
     * 删除resume
     *
     * @param resumeId resume主键
     * @return 结果
     */
    int deleteUserResumeByResumeId(Integer resumeId);

    /**
     * 批量删除resume
     *
     * @param resumeIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteUserResumeByResumeIds(Integer[] resumeIds);
}
