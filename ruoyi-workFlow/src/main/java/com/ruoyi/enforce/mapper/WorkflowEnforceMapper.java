package com.ruoyi.enforce.mapper;

import com.ruoyi.enforce.domain.WorkflowEnforce;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkflowEnforceMapper {

    /**
     * 查询实施
     *
     * @param id 实施ID
     * @return 实施
     */
    public WorkflowEnforce selectWorkflowEnforceById(String id);
    /**
     * 查询实施
     *
     * @param instanceId 实施ID
     * @return 实施
     */
    public WorkflowEnforce selectWorkflowEnforceByInstanceId(String instanceId);

    /**
     * 查询实施列表根据部门编号和WorkflowEnforce
     *
     * @param workflowEnforce 实施
     * @return 实施集合
     */
    //public List<WorkflowEnforce> selectWorkflowEnforceListByWorkflowEnforceAndDeptId(@Param("workflowEnforce")WorkflowEnforce workflowEnforce, @Param("deptId") Long deptId);

    public List<WorkflowEnforce> selectWorkflowEnforceListByWorkflowEnforce(@Param("workflowEnforce")WorkflowEnforce workflowEnforce);
    /**
     * 查询实施列表
     *
     * @param workflowEnforce 实施
     * @return 实施集合
     */
    public List<WorkflowEnforce> selectWorkflowEnforceList(WorkflowEnforce workflowEnforce);


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
     * 删除实施
     *
     * @param id 实施ID
     * @return 结果
     */
    public int deleteWorkflowEnforceById(String id);

    /**
     * 批量删除实施
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkflowEnforceByIds(String[] ids);

}
