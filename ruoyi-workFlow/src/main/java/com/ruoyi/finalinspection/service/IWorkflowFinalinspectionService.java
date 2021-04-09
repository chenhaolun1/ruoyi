package com.ruoyi.finalinspection.service;

import com.ruoyi.finalinspection.domain.WorkflowFinalinspection;

import java.util.List;

/**
 * 请假Service接口
 *
 * @author danny
 * @date 2020-10-28
 */
public interface IWorkflowFinalinspectionService
{
    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowFinalinspection selectWorkflowFinalinspectionById(String id);

    /**
     * 查询请假列表
     *
     * @param workflowFinalinspection 请假
     * @return 请假集合
     */
    public List<WorkflowFinalinspection> selectWorkflowFinalinspectionList(WorkflowFinalinspection workflowFinalinspection);

    /**
     * 查询请假列表
     *
     * @param workflowFinalinspection 请假
     * @return 请假集合
     */
    public List<WorkflowFinalinspection> selectWorkflowFinalinspectionAndTaskNameList(WorkflowFinalinspection workflowFinalinspection);

    /**
     * 新增请假
     *
     * @param workflowFinalinspection 请假
     * @return 结果
     */
    public int insertWorkflowFinalinspection(WorkflowFinalinspection workflowFinalinspection);

    /**
     * 修改请假
     *
     * @param workflowFinalinspection 请假
     * @return 结果
     */
    public int updateWorkflowFinalinspection(WorkflowFinalinspection workflowFinalinspection);

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    public int deleteWorkflowFinalinspectionByIds(String[] ids);

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowFinalinspectionById(String id);


    public WorkflowFinalinspection selectWorkflowFinalinspectionByInstanceId(String instanceId);
}
