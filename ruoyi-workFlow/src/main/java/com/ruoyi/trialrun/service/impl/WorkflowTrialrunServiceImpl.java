package com.ruoyi.trialrun.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.trialrun.domain.WorkflowTrialrun;
import com.ruoyi.trialrun.mapper.WorkflowTrialrunMapper;
import com.ruoyi.trialrun.service.IWorkflowTrialrunService;
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
public class WorkflowTrialrunServiceImpl implements IWorkflowTrialrunService {

    @Autowired
    private WorkflowTrialrunMapper workflowTrialrunMapper;
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
    public WorkflowTrialrun selectWorkflowTrialrunById(String id) {
        return workflowTrialrunMapper.selectWorkflowTrialrunById(id);
    }

    /**
     * 查询请假列表
     *
     * @param workflowTrialrun 请假
     * @return 请假
     */
    @Override
    public List<WorkflowTrialrun> selectWorkflowTrialrunList(WorkflowTrialrun workflowTrialrun) {
//        return workflowTrialrunMapper.selectWorkflowTrialrunListByWorkflowTrialrunAndDeptId(workflowTrialrun,SecurityUtils.getLoginUser().getUser().getDeptId());
        return workflowTrialrunMapper.selectWorkflowTrialrunListByWorkflowTrialrun(workflowTrialrun);
    }
    /**
     * 查询请假列表带任务状态
     *
     * @param workflowTrialrun 请假
     * @return 请假
     */
    @Override
    public List<WorkflowTrialrun> selectWorkflowTrialrunAndTaskNameList(WorkflowTrialrun workflowTrialrun) {
        List<WorkflowTrialrun> workflowTrialruns = workflowTrialrunMapper.selectWorkflowTrialrunList(workflowTrialrun);
        List<String> collect = workflowTrialruns.parallelStream().map(wl -> wl.getInstanceId()).collect(Collectors.toList());
        if(collect!=null&&!collect.isEmpty()) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowTrialruns.forEach(
                    wl->{
                        Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                        if (task != null) {
                            wl.setTaskName(task.getName());
                        }
                    }
            );
        }
        return workflowTrialruns;
    }

    /**
     * 新增请假
     *
     * @param workflowTrialrun 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowTrialrun(WorkflowTrialrun workflowTrialrun) {

        String id = UUID.randomUUID().toString();
        workflowTrialrun.setId(id);
        workflowTrialrun.setCreateTime(DateUtils.getNowDate());
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se", SecurityUtils.getLoginUser().getUser().getDeptId()), ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("trialrun")
                .withName(workflowTrialrun.getTitle())
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        workflowTrialrun.setInstanceId(processInstance.getId());
        workflowTrialrun.setState("0");
        workflowTrialrun.setCreateName(SecurityUtils.getNickName());
        workflowTrialrun.setCreateBy(SecurityUtils.getUsername());
        workflowTrialrun.setCreateTime(DateUtils.getNowDate());
        return workflowTrialrunMapper.insertWorkflowTrialrun(workflowTrialrun);
    }

    /**
     * 修改请假
     *
     * @param workflowTrialrun 请假
     * @return 结果
     */
    @Override
    public int updateWorkflowTrialrun(WorkflowTrialrun workflowTrialrun) {
        workflowTrialrun.setUpdateTime(DateUtils.getNowDate());
        return workflowTrialrunMapper.updateWorkflowTrialrun(workflowTrialrun);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowTrialrunByIds(String[] ids) {
        return workflowTrialrunMapper.deleteWorkflowTrialrunByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowTrialrunById(String id) {
        return workflowTrialrunMapper.deleteWorkflowTrialrunById(id);
    }

    @Override
    public WorkflowTrialrun selectWorkflowTrialrunByInstanceId(String instanceId) {

        return workflowTrialrunMapper.selectWorkflowTrialrunByInstanceId(instanceId);
    }
}
