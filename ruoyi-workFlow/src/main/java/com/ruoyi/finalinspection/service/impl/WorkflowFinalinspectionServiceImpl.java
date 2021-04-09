package com.ruoyi.finalinspection.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.finalinspection.domain.WorkflowFinalinspection;
import com.ruoyi.finalinspection.mapper.WorkflowFinalinspectionMapper;
import com.ruoyi.finalinspection.service.IWorkflowFinalinspectionService;
import com.ruoyi.service.ISysUserService;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 请假Service业务层处理
 *
 * @author danny
 * @date 2020-10-28
 */
@Service
public class WorkflowFinalinspectionServiceImpl implements IWorkflowFinalinspectionService {

    @Autowired
    private WorkflowFinalinspectionMapper workflowFinalinspectionMapper;
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private TaskService taskService;


    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    @Override
    public WorkflowFinalinspection selectWorkflowFinalinspectionById(String id) {
        return workflowFinalinspectionMapper.selectWorkflowFinalinspectionById(id);
    }

    /**
     * 查询请假列表
     *
     * @param workflowFinalinspection 请假
     * @return 请假
     */
    @Override
    public List<WorkflowFinalinspection> selectWorkflowFinalinspectionList(WorkflowFinalinspection workflowFinalinspection) {
//        return workflowFinalinspectionMapper.selectWorkflowFinalinspectionListByWorkflowFinalinspectionAndDeptId(workflowFinalinspection,SecurityUtils.getLoginUser().getUser().getDeptId());
        return workflowFinalinspectionMapper.selectWorkflowFinalinspectionListByWorkflowFinalinspection(workflowFinalinspection);
    }
    /**
     * 查询请假列表带任务状态
     *
     * @param workflowFinalinspection 请假
     * @return 请假
     */
    @Override
    public List<WorkflowFinalinspection> selectWorkflowFinalinspectionAndTaskNameList(WorkflowFinalinspection workflowFinalinspection) {
        List<WorkflowFinalinspection> workflowFinalinspections = workflowFinalinspectionMapper.selectWorkflowFinalinspectionList(workflowFinalinspection);
        List<String> collect = workflowFinalinspections.parallelStream().map(wl -> wl.getInstanceId()).collect(Collectors.toList());
        if(collect!=null&&!collect.isEmpty()) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowFinalinspections.forEach(
                    wl->{
                        Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                        if (task != null) {
                            wl.setTaskName(task.getName());
                        }
                    }
            );
        }
        return workflowFinalinspections;
    }

    /**
     * 新增请假
     *
     * @param workflowFinalinspection 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowFinalinspection(WorkflowFinalinspection workflowFinalinspection) {

        String id = UUID.randomUUID().toString();
        workflowFinalinspection.setId(id);
        workflowFinalinspection.setCreateTime(DateUtils.getNowDate());
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se", SecurityUtils.getLoginUser().getUser().getDeptId()), ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("finalinspection")
                .withName(workflowFinalinspection.getTitle())
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        workflowFinalinspection.setInstanceId(processInstance.getId());
        workflowFinalinspection.setState("0");
        workflowFinalinspection.setCreateName(SecurityUtils.getNickName());
        workflowFinalinspection.setCreateBy(SecurityUtils.getUsername());
        workflowFinalinspection.setCreateTime(DateUtils.getNowDate());
        return workflowFinalinspectionMapper.insertWorkflowFinalinspection(workflowFinalinspection);
    }

    /**
     * 修改请假
     *
     * @param workflowFinalinspection 请假
     * @return 结果
     */
    @Override
    public int updateWorkflowFinalinspection(WorkflowFinalinspection workflowFinalinspection) {
        workflowFinalinspection.setUpdateTime(DateUtils.getNowDate());
        return workflowFinalinspectionMapper.updateWorkflowFinalinspection(workflowFinalinspection);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowFinalinspectionByIds(String[] ids) {
        return workflowFinalinspectionMapper.deleteWorkflowFinalinspectionByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowFinalinspectionById(String id) {
        return workflowFinalinspectionMapper.deleteWorkflowFinalinspectionById(id);
    }

    @Override
    public WorkflowFinalinspection selectWorkflowFinalinspectionByInstanceId(String instanceId) {

        return workflowFinalinspectionMapper.selectWorkflowFinalinspectionByInstanceId(instanceId);
    }
}
