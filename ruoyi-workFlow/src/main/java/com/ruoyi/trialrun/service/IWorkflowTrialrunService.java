package com.ruoyi.trialrun.service;

import com.ruoyi.trialrun.domain.WorkflowTrialrun;

import java.util.List;

/**
 * 请假Service接口
 *
 * @author danny
 * @date 2020-10-28
 */
public interface IWorkflowTrialrunService
{
    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowTrialrun selectWorkflowTrialrunById(String id);

    /**
     * 查询请假列表
     *
     * @param workflowTrialrun 请假
     * @return 请假集合
     */
    public List<WorkflowTrialrun> selectWorkflowTrialrunList(WorkflowTrialrun workflowTrialrun);

    /**
     * 查询请假列表
     *
     * @param workflowTrialrun 请假
     * @return 请假集合
     */
    public List<WorkflowTrialrun> selectWorkflowTrialrunAndTaskNameList(WorkflowTrialrun workflowTrialrun);

    /**
     * 新增请假
     *
     * @param workflowTrialrun 请假
     * @return 结果
     */
    public int insertWorkflowTrialrun(WorkflowTrialrun workflowTrialrun);

    /**
     * 修改请假
     *
     * @param workflowTrialrun 请假
     * @return 结果
     */
    public int updateWorkflowTrialrun(WorkflowTrialrun workflowTrialrun);

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    public int deleteWorkflowTrialrunByIds(String[] ids);

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowTrialrunById(String id);


    public WorkflowTrialrun selectWorkflowTrialrunByInstanceId(String instanceId);
}
