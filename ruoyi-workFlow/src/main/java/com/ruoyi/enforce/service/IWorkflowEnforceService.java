package com.ruoyi.enforce.service;

import com.ruoyi.enforce.domain.WorkflowEnforce;

import java.util.List;

/**
 * 实施Service接口
 *
 * @author danny
 * @date 2020-10-28
 */
public interface IWorkflowEnforceService
{
    /**
     * 查询实施
     *
     * @param id 实施ID
     * @return 实施
     */
    public WorkflowEnforce selectWorkflowEnforceById(String id);

    /**
     * 查询实施列表
     *
     * @param workflowEnforce 实施
     * @return 实施集合
     */
    public List<WorkflowEnforce> selectWorkflowEnforceList(WorkflowEnforce workflowEnforce);

    /**
     * 查询实施列表
     *
     * @param workflowEnforce 实施
     * @return 实施集合
     */
    public List<WorkflowEnforce> selectWorkflowEnforceAndTaskNameList(WorkflowEnforce workflowEnforce);

    /**
     * 新增实施
     *
     * @param workflowEnforce 实施
     * @return 结果
     */
    public int insertWorkflowEnforce(WorkflowEnforce workflowEnforce);

    /**
     * 修改实施
     *
     * @param workflowEnforce 实施
     * @return 结果
     */
    public int updateWorkflowEnforce(WorkflowEnforce workflowEnforce);

    /**
     * 批量删除实施
     *
     * @param ids 需要删除的实施ID
     * @return 结果
     */
    public int deleteWorkflowEnforceByIds(String[] ids);

    /**
     * 删除实施信息
     *
     * @param id 实施ID
     * @return 结果
     */
    public int deleteWorkflowEnforceById(String id);


    public WorkflowEnforce selectWorkflowEnforceByInstanceId(String instanceId);
}
