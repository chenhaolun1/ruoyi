package com.ruoyi.trialrun.mapper;

import com.ruoyi.trialrun.domain.WorkflowTrialrun;
import org.apache.ibatis.annotations.Param;

import java.util.List;

    /**
     * 请假Mapper接口
     *
     * @author danny
     * @date 2020-10-28
     */
    public interface WorkflowTrialrunMapper
    {
        /**
         * 查询请假
         *
         * @param id 请假ID
         * @return 请假
         */
        public WorkflowTrialrun selectWorkflowTrialrunById(String id);
        /**
         * 查询请假
         *
         * @param instanceId 请假ID
         * @return 请假
         */
        public WorkflowTrialrun selectWorkflowTrialrunByInstanceId(String instanceId);

        /**
         * 查询请假列表根据部门编号和WorkflowTrialrun
         *
         * @param workflowTrialrun 请假
         * @return 请假集合
         */
        //public List<WorkflowTrialrun> selectWorkflowTrialrunListByWorkflowTrialrunAndDeptId(@Param("workflowTrialrun") WorkflowFirstcheck workflowTrialrun, @Param("deptId") Long deptId);

        public List<WorkflowTrialrun> selectWorkflowTrialrunListByWorkflowTrialrun(@Param("workflowTrialrun") WorkflowTrialrun workflowTrialrun);
        /**
         * 查询请假列表
         *
         * @param workflowTrialrun 请假
         * @return 请假集合
         */
        public List<WorkflowTrialrun> selectWorkflowTrialrunList(WorkflowTrialrun workflowTrialrun);


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
         * 删除请假
         *
         * @param id 请假ID
         * @return 结果
         */
        public int deleteWorkflowSTrialrunById(String id);

        /**
         * 批量删除请假
         *
         * @param ids 需要删除的数据ID
         * @return 结果
         */
        public int deleteWorkflowTrialrunByIds(String[] ids);

        int deleteWorkflowTrialrunById(String id);
    }




