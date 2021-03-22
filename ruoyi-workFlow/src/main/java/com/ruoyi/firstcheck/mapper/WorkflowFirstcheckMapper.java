package com.ruoyi.firstcheck.mapper;

import com.ruoyi.firstcheck.domain.WorkflowFirstcheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkflowFirstcheckMapper {

    /**
     * 查询实施
     *
     * @param id 实施ID
     * @return 实施
     */
    public WorkflowFirstcheck selectWorkflowFirstcheckById(String id);
    /**
     * 查询实施
     *
     * @param instanceId 实施ID
     * @return 实施
     */
    public WorkflowFirstcheck selectWorkflowFirstcheckByInstanceId(String instanceId);

    /**
     * 查询实施列表根据部门编号和WorkflowFirstcheck
     *
     * @param workflowFirstcheck 实施
     * @return 实施集合
     */
    //public List<WorkflowFirstcheck> selectWorkflowFirstcheckListByWorkflowFirstcheckAndDeptId(@Param("workflowFirstcheck")WorkflowFirstcheck workflowFirstcheck, @Param("deptId") Long deptId);

    public List<WorkflowFirstcheck> selectWorkflowFirstcheckListByWorkflowFirstcheck(@Param("workflowFirstcheck")WorkflowFirstcheck workflowFirstcheck);
    /**
     * 查询实施列表
     *
     * @param workflowFirstcheck 实施
     * @return 实施集合
     */
    public List<WorkflowFirstcheck> selectWorkflowFirstcheckList(WorkflowFirstcheck workflowFirstcheck);


    /**
     * 新增实施
     *
     * @param workflowFirstcheck 实施
     * @return 结果
     */
    public int insertWorkflowFirstcheck(WorkflowFirstcheck workflowFirstcheck);

    /**
     * 修改实施
     *
     * @param workflowFirstcheck 实施
     * @return 结果
     */
    public int updateWorkflowFirstcheck(WorkflowFirstcheck workflowFirstcheck);

    /**
     * 删除实施
     *
     * @param id 实施ID
     * @return 结果
     */
    public int deleteWorkflowFirstcheckById(String id);

    /**
     * 批量删除实施
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkflowFirstcheckByIds(String[] ids);

}
