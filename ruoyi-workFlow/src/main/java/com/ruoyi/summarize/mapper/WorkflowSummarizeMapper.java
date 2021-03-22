package com.ruoyi.summarize.mapper;

import com.ruoyi.summarize.domain.WorkflowSummarize;
import org.apache.ibatis.annotations.Param;

import java.util.List;

    /**
     * 请假Mapper接口
     *
     * @author danny
     * @date 2020-10-28
     */
    public interface WorkflowSummarizeMapper
    {
        /**
         * 查询请假
         *
         * @param id 请假ID
         * @return 请假
         */
        public WorkflowSummarize selectWorkflowSummarizeById(String id);
        /**
         * 查询请假
         *
         * @param instanceId 请假ID
         * @return 请假
         */
        public WorkflowSummarize selectWorkflowSummarizeByInstanceId(String instanceId);

        /**
         * 查询请假列表根据部门编号和WorkflowSummarize
         *
         * @param workflowSummarize 请假
         * @return 请假集合
         */
        //public List<WorkflowSummarize> selectWorkflowSummarizeListByWorkflowSummarizeAndDeptId(@Param("workflowSummarize") WorkflowFirstcheck workflowSummarize, @Param("deptId") Long deptId);

        public List<WorkflowSummarize> selectWorkflowSummarizeListByWorkflowSummarize(@Param("workflowSummarize") WorkflowSummarize workflowSummarize);
        /**
         * 查询请假列表
         *
         * @param workflowSummarize 请假
         * @return 请假集合
         */
        public List<WorkflowSummarize> selectWorkflowSummarizeList(WorkflowSummarize workflowSummarize);


        /**
         * 新增请假
         *
         * @param workflowSummarize 请假
         * @return 结果
         */
        public int insertWorkflowSummarize(WorkflowSummarize workflowSummarize);

        /**
         * 修改请假
         *
         * @param workflowSummarize 请假
         * @return 结果
         */
        public int updateWorkflowSummarize(WorkflowSummarize workflowSummarize);

        /**
         * 删除请假
         *
         * @param id 请假ID
         * @return 结果
         */
        public int deleteWorkflowSummarizeById(String id);

        /**
         * 批量删除请假
         *
         * @param ids 需要删除的数据ID
         * @return 结果
         */
        public int deleteWorkflowSummarizeByIds(String[] ids);
    }




