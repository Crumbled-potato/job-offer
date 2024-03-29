package com.jobsys.system.api.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件信息
 *
 * @author PengFei Deng
 */
@Data
@ToString
public class SysFile {
    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

}
