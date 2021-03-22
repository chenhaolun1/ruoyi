package com.ruoyi.requirement.mapper;

import com.ruoyi.requirement.domain.WorkflowRequirement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 请假Mapper接口
 * 
 * @author danny
 * @date 2020-10-28
 */
public interface WorkflowRequirementMapper
{
    /**
     * 查询请假
     * 
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowRequirement selectWorkflowRequirementById(String id);
    /**
     * 查询请假
     *
     * @param instanceId 请假ID
     * @return 请假
     */
    public WorkflowRequirement selectWorkflowRequirementByInstanceId(String instanceId);

    /**
     * 查询请假列表根据部门编号和WorkflowRequirement
     * 
     * @param workflowRequirement 请假
     * @return 请假集合
     */
    public List<WorkflowRequirement> selectWorkflowRequirementListByWorkflowRequirementAndDeptId(@Param("workflowRequirement")WorkflowRequirement workflowRequirement,@Param("deptId") Long deptId);

    public List<WorkflowRequirement> selectWorkflowRequirementListByWorkflowRequirement(@Param("workflowRequirement")WorkflowRequirement workflowRequirement);
    /**
     * 查询请假列表
     *
     * @param workflowRequirement 请假
     * @return 请假集合
     */
    public List<WorkflowRequirement> selectWorkflowRequirementList(WorkflowRequirement workflowRequirement);


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
     * 删除请假
     * 
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowRequirementById(String id);

    /**
     * 批量删除请假
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkflowRequirementByIds(String[] ids);
}
