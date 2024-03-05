package com.jobsys.work.service.impl;

import java.util.List;

import com.jobsys.common.core.utils.DateUtils;
import com.jobsys.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jobsys.work.mapper.UserMsgMapper;
import com.jobsys.work.domain.UserMsg;
import com.jobsys.work.service.IUserMsgService;

/**
 * userMsgService业务层处理
 *
 * @author PengFei Deng
 * @date 2022-05-02
 */
@Service
public class UserMsgServiceImpl implements IUserMsgService {
    @Autowired
    private UserMsgMapper userMsgMapper;

    @Override
    public AjaxResult getUnReadSum(Long userId) {
        Integer sum = userMsgMapper.getUnReadSum(userId);
        return AjaxResult.success(sum);
    }

    /**
     * 查询userMsg
     *
     * @param msgId userMsg主键
     * @return userMsg
     */
    @Override
    public UserMsg selectUserMsgByMsgId(Integer msgId) {
        return userMsgMapper.selectUserMsgByMsgId(msgId);
    }

    /**
     * 查询userMsg列表
     *
     * @param userMsg userMsg
     * @return userMsg
     */
    @Override
    public List<UserMsg> selectUserMsgList(UserMsg userMsg) {
        return userMsgMapper.selectUserMsgList(userMsg);
    }

    /**
     * 新增userMsg
     *
     * @param userMsg userMsg
     * @return 结果
     */
    @Override
    public int insertUserMsg(UserMsg userMsg) {
        userMsg.setCreateTime(DateUtils.getNowDate());
        return userMsgMapper.insertUserMsg(userMsg);
    }

    /**
     * 修改userMsg
     *
     * @param userMsg userMsg
     * @return 结果
     */
    @Override
    public int updateUserMsg(UserMsg userMsg) {
        userMsg.setUpdateTime(DateUtils.getNowDate());
        return userMsgMapper.updateUserMsg(userMsg);
    }

    /**
     * 批量删除userMsg
     *
     * @param msgIds 需要删除的userMsg主键
     * @return 结果
     */
    @Override
    public int deleteUserMsgByMsgIds(Integer[] msgIds) {
        return userMsgMapper.deleteUserMsgByMsgIds(msgIds);
    }

    /**
     * 删除userMsg信息
     *
     * @param msgId userMsg主键
     * @return 结果
     */
    @Override
    public int deleteUserMsgByMsgId(Integer msgId) {
        return userMsgMapper.deleteUserMsgByMsgId(msgId);
    }
}
