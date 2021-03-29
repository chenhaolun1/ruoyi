package com.ruoyi.develop.domain;

import com.ruoyi.common.annotation.Excel;

public class index {

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
}
