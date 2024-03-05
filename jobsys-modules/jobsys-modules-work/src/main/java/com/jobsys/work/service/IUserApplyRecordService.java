package com.jobsys.work.service;

import java.util.List;

import com.jobsys.work.domain.UserApplyRecord;

/**
 * recordService接口
 *
 * @author PengFei Deng
 * @date 2022-04-26
 */
public interface IUserApplyRecordService {
    /**
     * 查询record
     *
     * @param recordId record主键
     * @return record
     */
    UserApplyRecord selectUserApplyRecordByRecordId(Integer recordId);

    /**
     * 查询record列表
     *
     * @param userApplyRecord record
     * @return record集合
     */
    List<UserApplyRecord> selectUserApplyRecordList(UserApplyRecord userApplyRecord);

    /**
     * 新增record
     *
     * @param userApplyRecord record
     * @return 结果
     */
    int insertUserApplyRecord(UserApplyRecord userApplyRecord);

    /**
     * 修改record
     *
     * @param userApplyRecord record
     * @return 结果
     */
    int updateUserApplyRecord(UserApplyRecord userApplyRecord);

    /**
     * 批量删除record
     *
     * @param recordIds 需要删除的record主键集合
     * @return 结果
     */
    int deleteUserApplyRecordByRecordIds(Integer[] recordIds);

    /**
     * 删除record信息
     *
     * @param recordId record主键
     * @return 结果
     */
    int deleteUserApplyRecordByRecordId(Integer recordId);
}
