package com.ruoyi.firstcheck.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.firstcheck.domain.WorkflowFirstcheck;
import com.ruoyi.firstcheck.mapper.WorkflowFirstcheckMapper;
import com.ruoyi.service.ISysUserService;
import com.ruoyi.firstcheck.service.IWorkflowFirstcheckService;
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
public class WorkflowFirstcheckServiceImpl implements IWorkflowFirstcheckService {

    @Autowired
    private WorkflowFirstcheckMapper workflowFirstcheckMapper;
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
    public WorkflowFirstcheck selectWorkflowFirstcheckById(String id) {
        return workflowFirstcheckMapper.selectWorkflowFirstcheckById(id);
    }

    /**
     * 查询请假列表
     *
     * @param workflowFirstcheck 请假
     * @return 请假
     */
    @Override
    public List<WorkflowFirstcheck> selectWorkflowFirstcheckList(WorkflowFirstcheck workflowFirstcheck) {
//        return workflowFirstcheckMapper.selectWorkflowFirstcheckListByWorkflowFirstcheckAndDeptId(workflowFirstcheck,SecurityUtils.getLoginUser().getUser().getDeptId());
        return workflowFirstcheckMapper.selectWorkflowFirstcheckListByWorkflowFirstcheck(workflowFirstcheck);
    }
    /**
     * 查询请假列表带任务状态
     *
     * @param workflowFirstcheck 请假
     * @return 请假
     */
    @Override
    public List<WorkflowFirstcheck> selectWorkflowFirstcheckAndTaskNameList(WorkflowFirstcheck workflowFirstcheck) {
        List<WorkflowFirstcheck> workflowFirstchecks = workflowFirstcheckMapper.selectWorkflowFirstcheckList(workflowFirstcheck);
        List<String> collect = workflowFirstchecks.parallelStream().map(wl -> wl.getInstanceId()).collect(Collectors.toList());
        if(collect!=null&&!collect.isEmpty()) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowFirstchecks.forEach(
                    wl->{
                        Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                        if (task != null) {
                            wl.setTaskName(task.getName());
                        }
                    }
            );
        }
        return workflowFirstchecks;
    }

    /**
     * 新增请假
     *
     * @param workflowFirstcheck 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowFirstcheck(WorkflowFirstcheck workflowFirstcheck) {

        String id = UUID.randomUUID().toString();
        workflowFirstcheck.setId(id);
        workflowFirstcheck.setCreateTime(DateUtils.getNowDate());
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se", SecurityUtils.getLoginUser().getUser().getDeptId()), ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("firstcheck")
                .withName(workflowFirstcheck.getTitle())
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        workflowFirstcheck.setInstanceId(processInstance.getId());
        workflowFirstcheck.setState("0");
        workflowFirstcheck.setCreateName(SecurityUtils.getNickName());
        workflowFirstcheck.setCreateBy(SecurityUtils.getUsername());
        workflowFirstcheck.setCreateTime(DateUtils.getNowDate());
        return workflowFirstcheckMapper.insertWorkflowFirstcheck(workflowFirstcheck);
    }

    /**
     * 修改请假
     *
     * @param workflowFirstcheck 请假
     * @return 结果
     */
    @Override
    public int updateWorkflowFirstcheck(WorkflowFirstcheck workflowFirstcheck) {
        workflowFirstcheck.setUpdateTime(DateUtils.getNowDate());
        return workflowFirstcheckMapper.updateWorkflowFirstcheck(workflowFirstcheck);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowFirstcheckByIds(String[] ids) {
        return workflowFirstcheckMapper.deleteWorkflowFirstcheckByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowFirstcheckById(String id) {
        return workflowFirstcheckMapper.deleteWorkflowFirstcheckById(id);
    }

    @Override
    public WorkflowFirstcheck selectWorkflowFirstcheckByInstanceId(String instanceId) {

        return workflowFirstcheckMapper.selectWorkflowFirstcheckByInstanceId(instanceId);
    }
}
