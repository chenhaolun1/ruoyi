package com.ruoyi.finalinspection.mapper;

import com.ruoyi.finalinspection.domain.WorkflowFinalinspection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

    /**
     * 请假Mapper接口
     *
     * @author danny
     * @date 2020-10-28
     */
    public interface WorkflowFinalinspectionMapper
    {
        /**
         * 查询请假
         *
         * @param id 请假ID
         * @return 请假
         */
        public WorkflowFinalinspection selectWorkflowFinalinspectionById(String id);
        /**
         * 查询请假
         *
         * @param instanceId 请假ID
         * @return 请假
         */
        public WorkflowFinalinspection selectWorkflowFinalinspectionByInstanceId(String instanceId);

        /**
         * 查询请假列表根据部门编号和WorkflowFinalinspection
         *
         * @param workflowFinalinspection 请假
         * @return 请假集合
         */
        //public List<WorkflowFinalinspection> selectWorkflowFinalinspectionListByWorkflowFinalinspectionAndDeptId(@Param("workflowFinalinspection") WorkflowFirstcheck workflowFinalinspection, @Param("deptId") Long deptId);

        public List<WorkflowFinalinspection> selectWorkflowFinalinspectionListByWorkflowFinalinspection(@Param("workflowFinalinspection") WorkflowFinalinspection workflowFinalinspection);
        /**
         * 查询请假列表
         *
         * @param workflowFinalinspection 请假
         * @return 请假集合
         */
        public List<WorkflowFinalinspection> selectWorkflowFinalinspectionList(WorkflowFinalinspection workflowFinalinspection);


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
         * 删除请假
         *
         * @param id 请假ID
         * @return 结果
         */
        public int deleteWorkflowFinalinspectionById(String id);

        /**
         * 批量删除请假
         *
         * @param ids 需要删除的数据ID
         * @return 结果
         */
        public int deleteWorkflowFinalinspectionByIds(String[] ids);
    }




