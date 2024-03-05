package com.jobsys.system.api.factory;

import com.jobsys.common.core.domain.R;
import com.jobsys.system.api.RemoteFileService;
import com.jobsys.system.api.domain.SysFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件服务降级处理
 * 
 * @author PengFei Deng
 */
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteFileService create(Throwable throwable)
    {
        log.error("文件服务调用失败:{}", throwable.getMessage());
        return file -> R.fail("上传文件失败:" + throwable.getMessage());
    }
}
