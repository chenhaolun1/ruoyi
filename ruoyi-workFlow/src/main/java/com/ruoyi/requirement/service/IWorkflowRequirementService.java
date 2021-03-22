package com.ruoyi.requirement.service;

import com.ruoyi.requirement.domain.WorkflowRequirement;

import java.util.List;

/**
 * 请假Service接口
 * 
 * @author danny
 * @date 2020-10-28
 */
public interface IWorkflowRequirementService
{
    /**
     * 查询请假
     * 
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowRequirement selectWorkflowRequirementById(String id);

    /**
     * 查询请假列表
     * 
     * @param workflowRequirement 请假
     * @return 请假集合
     */
    public List<WorkflowRequirement> selectWorkflowRequirementList(WorkflowRequirement workflowRequirement);

    /**
     * 查询请假列表
     *
     * @param workflowRequirement 请假
     * @return 请假集合
     */
    public List<WorkflowRequirement> selectWorkflowRequirementAndTaskNameList(WorkflowRequirement workflowRequirement);

    /**
     * 新增请假
     * 
     * @param workflowRequirement 请假
     * @return 结果
     */
    public int insertWorkflowRequirement(WorkflowRequirement workflowRequirement);

    /**
     * 修改请假
     * 
     * @param workflowRequirement 请假
     * @return 结果
     */
    public int updateWorkflowRequirement(WorkflowRequirement workflowRequirement);

    /**
     * 批量删除请假
     * 
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    public int deleteWorkflowRequirementByIds(String[] ids);

    /**
     * 删除请假信息
     * 
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowRequirementById(String id);


    public WorkflowRequirement selectWorkflowRequirementByInstanceId(String instanceId);
}
