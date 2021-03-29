package com.ruoyi.develop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件信息对象 sys_file_productwarehouse
 * 
 * @author xiefei
 * @date 2020-12-29
 */
public class SysFileProductwarehouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件id */
    private Long fileId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String realName;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String showName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 标准类别 */
    @Excel(name = "标准类别")
    private String categoriesName;

    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
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
    public void setCategoriesName(String categoriesName) 
    {
        this.categoriesName = categoriesName;
    }

    public String getCategoriesName() 
    {
        return categoriesName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("realName", getRealName())
            .append("showName", getShowName())
            .append("filePath", getFilePath())
            .append("categoriesName", getCategoriesName())
            .toString();
    }
}
