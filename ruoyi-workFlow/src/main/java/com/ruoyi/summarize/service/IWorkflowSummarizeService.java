package com.ruoyi.summarize.service;

import com.ruoyi.summarize.domain.WorkflowSummarize;

import java.util.List;

/**
 * 请假Service接口
 *
 * @author danny
 * @date 2020-10-28
 */
public interface IWorkflowSummarizeService
{
    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowSummarize selectWorkflowSummarizeById(String id);

    /**
     * 查询请假列表
     *
     * @param workflowSummarize 请假
     * @return 请假集合
     */
    public List<WorkflowSummarize> selectWorkflowSummarizeList(WorkflowSummarize workflowSummarize);

    /**
     * 查询请假列表
     *
     * @param workflowSummarize 请假
     * @return 请假集合
     */
    public List<WorkflowSummarize> selectWorkflowSummarizeAndTaskNameList(WorkflowSummarize workflowSummarize);

    /**
     * 新增请假
     *
     * @param workflowSummarize 请假
     * @return 结果
     */
    public int insertWorkflowSummarize(WorkflowSummarize workflowSummarize);

    /**
     * 修改请假
     *
     * @param workflowSummarize 请假
     * @return 结果
     */
    public int updateWorkflowSummarize(WorkflowSummarize workflowSummarize);

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    public int deleteWorkflowSummarizeByIds(String[] ids);

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowSummarizeById(String id);


    public WorkflowSummarize selectWorkflowSummarizeByInstanceId(String instanceId);
}
