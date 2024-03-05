package com.jobsys.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 *
 * @author PengFei Deng
 */
public interface ISysFileService {
    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception 异常
     */
    String uploadFile(MultipartFile file) throws Exception;
}
