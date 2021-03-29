package com.ruoyi.develop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件信息对象 sys_file_info
 * 
 * @author ruoyi
 * @date 2020-12-29
 */
public class SysFileInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件id */
    private Long fileId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String showName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** $column.columnComment */
    @Excel(name = "文件路径")
    private String realName;

    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setShowName(String showName) 
    {
        this.showName = showName;
    }

    public String getShowName() 
    {
        return showName;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("showName", getShowName())
            .append("filePath", getFilePath())
            .append("realName", getRealName())
            .toString();
    }
}
