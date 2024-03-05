package com.jobsys.work.domain;

import com.jobsys.common.core.annotation.Excel;
import com.jobsys.common.core.web.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * userMsg对象 user_msg
 *
 * @author PengFei Deng
 * @date 2022-05-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserMsg extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Integer msgId;

    /**
     * $column.columnComment
     */

    private Long userId;

    /**
     * $column.columnComment
     */

    private String isRead;

    /**
     * $column.columnComment
     */
    private String msgContent;


}
