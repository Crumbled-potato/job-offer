package com.jobsys.work.service.impl;

import java.util.List;

import com.jobsys.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jobsys.work.mapper.UserApplyRecordMapper;
import com.jobsys.work.domain.UserApplyRecord;
import com.jobsys.work.service.IUserApplyRecordService;

/**
 * recordService业务层处理
 *
 * @author PengFei Deng
 * @date 2022-04-26
 */
@Service
public class UserApplyRecordServiceImpl implements IUserApplyRecordService {
    @Autowired
    private UserApplyRecordMapper userApplyRecordMapper;

    /**
     * 查询record
     *
     * @param recordId record主键
     * @return record
     */
    @Override
    public UserApplyRecord selectUserApplyRecordByRecordId(Integer recordId) {
        return userApplyRecordMapper.selectUserApplyRecordByRecordId(recordId);
    }

    /**
     * 查询record列表
     *
     * @param userApplyRecord record
     * @return record
     */
    @Override
    public List<UserApplyRecord> selectUserApplyRecordList(UserApplyRecord userApplyRecord) {
        return userApplyRecordMapper.selectUserApplyRecordList(userApplyRecord);
    }

    /**
     * 新增record
     *
     * @param userApplyRecord record
     * @return 结果
     */
    @Override
    public int insertUserApplyRecord(UserApplyRecord userApplyRecord) {
        return userApplyRecordMapper.insertUserApplyRecord(userApplyRecord);
    }

    /**
     * 修改record
     *
     * @param userApplyRecord record
     * @return 结果
     */
    @Override
    public int updateUserApplyRecord(UserApplyRecord userApplyRecord) {
        userApplyRecord.setUpdateTime(DateUtils.getNowDate());
        return userApplyRecordMapper.updateUserApplyRecord(userApplyRecord);
    }

    /**
     * 批量删除record
     *
     * @param recordIds 需要删除的record主键
     * @return 结果
     */
    @Override
    public int deleteUserApplyRecordByRecordIds(Integer[] recordIds) {
        return userApplyRecordMapper.deleteUserApplyRecordByRecordIds(recordIds);
    }

    /**
     * 删除record信息
     *
     * @param recordId record主键
     * @return 结果
     */
    @Override
    public int deleteUserApplyRecordByRecordId(Integer recordId) {
        return userApplyRecordMapper.deleteUserApplyRecordByRecordId(recordId);
    }
}
