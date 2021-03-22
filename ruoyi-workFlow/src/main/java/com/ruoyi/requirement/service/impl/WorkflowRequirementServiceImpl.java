package com.ruoyi.requirement.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.requirement.domain.WorkflowRequirement;
import com.ruoyi.requirement.mapper.WorkflowRequirementMapper;
import com.ruoyi.requirement.service.IWorkflowRequirementService;
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
public class WorkflowRequirementServiceImpl implements IWorkflowRequirementService {

    @Autowired
    private WorkflowRequirementMapper workflowRequirementMapper;
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
    public WorkflowRequirement selectWorkflowRequirementById(String id) {
        return workflowRequirementMapper.selectWorkflowRequirementById(id);
    }

    /**
     * 查询请假列表
     *
     * @param workflowRequirement 请假
     * @return 请假
     */
    @Override
    public List<WorkflowRequirement> selectWorkflowRequirementList(WorkflowRequirement workflowRequirement) {
//        return workflowRequirementMapper.selectWorkflowRequirementListByWorkflowRequirementAndDeptId(workflowRequirement,SecurityUtils.getLoginUser().getUser().getDeptId());
        return workflowRequirementMapper.selectWorkflowRequirementListByWorkflowRequirement(workflowRequirement);
    }
    /**
     * 查询请假列表带任务状态
     *
     * @param workflowRequirement 请假
     * @return 请假
     */
    @Override
    public List<WorkflowRequirement> selectWorkflowRequirementAndTaskNameList(WorkflowRequirement workflowRequirement) {
        List<WorkflowRequirement> workflowRequirements = workflowRequirementMapper.selectWorkflowRequirementList(workflowRequirement);
        List<String> collect = workflowRequirements.parallelStream().map(wl -> wl.getInstanceId()).collect(Collectors.toList());
        if(collect!=null&&!collect.isEmpty()) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowRequirements.forEach(
                    wl->{
                        Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                        if (task != null) {
                            wl.setTaskName(task.getName());
                        }
                    }
            );
        }
        return workflowRequirements;
    }

    /**
     * 新增请假
     *
     * @param workflowRequirement 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowRequirement(WorkflowRequirement workflowRequirement) {

        String id = UUID.randomUUID().toString();
        workflowRequirement.setId(id);
        workflowRequirement.setCreateTime(DateUtils.getNowDate());
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se", SecurityUtils.getLoginUser().getUser().getDeptId()), ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("requirement")
                .withName(workflowRequirement.getTitle())
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        workflowRequirement.setInstanceId(processInstance.getId());
        workflowRequirement.setState("0");
        workflowRequirement.setCreateName(SecurityUtils.getNickName());
        workflowRequirement.setCreateBy(SecurityUtils.getUsername());
        workflowRequirement.setCreateTime(DateUtils.getNowDate());
        return workflowRequirementMapper.insertWorkflowRequirement(workflowRequirement);
    }

    /**
     * 修改请假
     *
     * @param workflowRequirement 请假
     * @return 结果
     */
    @Override
    public int updateWorkflowRequirement(WorkflowRequirement workflowRequirement) {
        workflowRequirement.setUpdateTime(DateUtils.getNowDate());
        return workflowRequirementMapper.updateWorkflowRequirement(workflowRequirement);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowRequirementByIds(String[] ids) {
        return workflowRequirementMapper.deleteWorkflowRequirementByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowRequirementById(String id) {
        return workflowRequirementMapper.deleteWorkflowRequirementById(id);
    }

    @Override
    public WorkflowRequirement selectWorkflowRequirementByInstanceId(String instanceId) {

        return workflowRequirementMapper.selectWorkflowRequirementByInstanceId(instanceId);
    }
}
