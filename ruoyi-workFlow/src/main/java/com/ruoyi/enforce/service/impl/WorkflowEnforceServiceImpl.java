package com.ruoyi.enforce.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.enforce.domain.WorkflowEnforce;
import com.ruoyi.enforce.mapper.WorkflowEnforceMapper;
import com.ruoyi.enforce.service.IWorkflowEnforceService;
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
public class WorkflowEnforceServiceImpl implements IWorkflowEnforceService {

    @Autowired
    private WorkflowEnforceMapper workflowEnforceMapper;
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
    public WorkflowEnforce selectWorkflowEnforceById(String id) {
        return workflowEnforceMapper.selectWorkflowEnforceById(id);
    }

    /**
     * 查询请假列表
     *
     * @param workflowEnforce 请假
     * @return 请假
     */
    @Override
    public List<WorkflowEnforce> selectWorkflowEnforceList(WorkflowEnforce workflowEnforce) {
//        return workflowEnforceMapper.selectWorkflowEnforceListByWorkflowEnforceAndDeptId(workflowEnforce,SecurityUtils.getLoginUser().getUser().getDeptId());
        return workflowEnforceMapper.selectWorkflowEnforceListByWorkflowEnforce(workflowEnforce);
    }
    /**
     * 查询请假列表带任务状态
     *
     * @param workflowEnforce 请假
     * @return 请假
     */
    @Override
    public List<WorkflowEnforce> selectWorkflowEnforceAndTaskNameList(WorkflowEnforce workflowEnforce) {
        List<WorkflowEnforce> workflowEnforces = workflowEnforceMapper.selectWorkflowEnforceList(workflowEnforce);
        List<String> collect = workflowEnforces.parallelStream().map(wl -> wl.getInstanceId()).collect(Collectors.toList());
        if(collect!=null&&!collect.isEmpty()) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowEnforces.forEach(
                    wl->{
                        Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                        if (task != null) {
                            wl.setTaskName(task.getName());
                        }
                    }
            );
        }
        return workflowEnforces;
    }

    /**
     * 新增请假
     *
     * @param workflowEnforce 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowEnforce(WorkflowEnforce workflowEnforce) {

        String id = UUID.randomUUID().toString();
        workflowEnforce.setId(id);
        workflowEnforce.setCreateTime(DateUtils.getNowDate());
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se", SecurityUtils.getLoginUser().getUser().getDeptId()), ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("enforce")
                .withName(workflowEnforce.getTitle())
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        workflowEnforce.setInstanceId(processInstance.getId());
        workflowEnforce.setState("0");
        workflowEnforce.setCreateName(SecurityUtils.getNickName());
        workflowEnforce.setCreateBy(SecurityUtils.getUsername());
        workflowEnforce.setCreateTime(DateUtils.getNowDate());
        return workflowEnforceMapper.insertWorkflowEnforce(workflowEnforce);
    }

    /**
     * 修改请假
     *
     * @param workflowEnforce 请假
     * @return 结果
     */
    @Override
    public int updateWorkflowEnforce(WorkflowEnforce workflowEnforce) {
        workflowEnforce.setUpdateTime(DateUtils.getNowDate());
        return workflowEnforceMapper.updateWorkflowEnforce(workflowEnforce);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowEnforceByIds(String[] ids) {
        return workflowEnforceMapper.deleteWorkflowEnforceByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowEnforceById(String id) {
        return workflowEnforceMapper.deleteWorkflowEnforceById(id);
    }

    @Override
    public WorkflowEnforce selectWorkflowEnforceByInstanceId(String instanceId) {

        return workflowEnforceMapper.selectWorkflowEnforceByInstanceId(instanceId);
    }
}
