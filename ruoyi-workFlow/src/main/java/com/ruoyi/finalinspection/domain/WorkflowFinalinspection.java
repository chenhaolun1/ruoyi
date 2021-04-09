package com.ruoyi.finalinspection.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 请求对象 workflow_finalinspection
 *
 * @author danny
 * @date 2020-10-28
 */
public class WorkflowFinalinspection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;

    /** 总结类型 */
    @Excel(name = "总结类型")
    private String type;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 原因 */
    @Excel(name = "原因")
    private String reason;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finalinspectionStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finalinspectionEndTime;


    private String instanceId;
    private String taskName;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String realName;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String showName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getFinalinspectionStartTime() {
        return finalinspectionStartTime;
    }

    public void setFinalinspectionStartTime(Date finalinspectionStartTime) {
        this.finalinspectionStartTime = finalinspectionStartTime;
    }

    public Date getFinalinspectionEndTime() {
        return finalinspectionEndTime;
    }

    public void setFinalinspectionEndTime(Date finalinspectionEndTime) {
        this.finalinspectionEndTime = finalinspectionEndTime;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("type", getType())
                .append("title", getTitle())
                .append("reason", getReason())
                .append("finalinspectionStartTime", getFinalinspectionStartTime())
                .append("finalinspectionEndTime", getFinalinspectionEndTime())
                .append("instanceId", getInstanceId())
                .append("state", getState())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("realName", getRealName())
                .append("showName", getShowName())
                .append("filePath", getFilePath())
                .toString();
    }
}
