package com.jobsys.file.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastDFS 文件存储
 *
 * @author PengFei Deng
 */
@Service
public class FastDfsSysFileServiceImpl implements ISysFileService {
    /**
     * 域名或本机访问地址
     */
    @Value("${fdfs.domain}")
    public String domain;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * FastDfs文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception 异常
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return domain + "/" + storePath.getFullPath();
    }
}
