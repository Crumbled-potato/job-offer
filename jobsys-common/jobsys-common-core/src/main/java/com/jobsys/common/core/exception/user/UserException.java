package com.jobsys.common.core.exception.user;


import com.jobsys.common.core.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author PengFei Deng
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
