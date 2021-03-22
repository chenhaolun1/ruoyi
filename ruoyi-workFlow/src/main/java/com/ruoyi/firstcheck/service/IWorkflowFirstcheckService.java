package com.ruoyi.firstcheck.service;

import com.ruoyi.firstcheck.domain.WorkflowFirstcheck;

import java.util.List;

/**
 * 请假Service接口
 *
 * @author danny
 * @date 2020-10-28
 */
public interface IWorkflowFirstcheckService
{
    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowFirstcheck selectWorkflowFirstcheckById(String id);

    /**
     * 查询请假列表
     *
     * @param workflowFirstcheck 请假
     * @return 请假集合
     */
    public List<WorkflowFirstcheck> selectWorkflowFirstcheckList(WorkflowFirstcheck workflowFirstcheck);

    /**
     * 查询请假列表
     *
     * @param workflowFirstcheck 请假
     * @return 请假集合
     */
    public List<WorkflowFirstcheck> selectWorkflowFirstcheckAndTaskNameList(WorkflowFirstcheck workflowFirstcheck);

    /**
     * 新增请假
     *
     * @param workflowFirstcheck 请假
     * @return 结果
     */
    public int insertWorkflowFirstcheck(WorkflowFirstcheck workflowFirstcheck);

    /**
     * 修改请假
     *
     * @param workflowFirstcheck 请假
     * @return 结果
     */
    public int updateWorkflowFirstcheck(WorkflowFirstcheck workflowFirstcheck);

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    public int deleteWorkflowFirstcheckByIds(String[] ids);

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowFirstcheckById(String id);


    public WorkflowFirstcheck selectWorkflowFirstcheckByInstanceId(String instanceId);
}
