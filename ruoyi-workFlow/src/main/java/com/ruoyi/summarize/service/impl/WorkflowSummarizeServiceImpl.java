package com.ruoyi.summarize.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.summarize.domain.WorkflowSummarize;
import com.ruoyi.summarize.mapper.WorkflowSummarizeMapper;
import com.ruoyi.summarize.service.IWorkflowSummarizeService;
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
public class WorkflowSummarizeServiceImpl implements IWorkflowSummarizeService {

    @Autowired
    private WorkflowSummarizeMapper workflowSummarizeMapper;
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
    public WorkflowSummarize selectWorkflowSummarizeById(String id) {
        return workflowSummarizeMapper.selectWorkflowSummarizeById(id);
    }

    /**
     * 查询请假列表
     *
     * @param workflowSummarize 请假
     * @return 请假
     */
    @Override
    public List<WorkflowSummarize> selectWorkflowSummarizeList(WorkflowSummarize workflowSummarize) {
//        return workflowSummarizeMapper.selectWorkflowSummarizeListByWorkflowSummarizeAndDeptId(workflowSummarize,SecurityUtils.getLoginUser().getUser().getDeptId());
        return workflowSummarizeMapper.selectWorkflowSummarizeListByWorkflowSummarize(workflowSummarize);
    }
    /**
     * 查询请假列表带任务状态
     *
     * @param workflowSummarize 请假
     * @return 请假
     */
    @Override
    public List<WorkflowSummarize> selectWorkflowSummarizeAndTaskNameList(WorkflowSummarize workflowSummarize) {
        List<WorkflowSummarize> workflowSummarizes = workflowSummarizeMapper.selectWorkflowSummarizeList(workflowSummarize);
        List<String> collect = workflowSummarizes.parallelStream().map(wl -> wl.getInstanceId()).collect(Collectors.toList());
        if(collect!=null&&!collect.isEmpty()) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowSummarizes.forEach(
                    wl->{
                        Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                        if (task != null) {
                            wl.setTaskName(task.getName());
                        }
                    }
            );
        }
        return workflowSummarizes;
    }

    /**
     * 新增请假
     *
     * @param workflowSummarize 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowSummarize(WorkflowSummarize workflowSummarize) {

        String id = UUID.randomUUID().toString();
        workflowSummarize.setId(id);
        workflowSummarize.setCreateTime(DateUtils.getNowDate());
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se", SecurityUtils.getLoginUser().getUser().getDeptId()), ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("summarize")
                .withName(workflowSummarize.getTitle())
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        workflowSummarize.setInstanceId(processInstance.getId());
        workflowSummarize.setState("0");
        workflowSummarize.setCreateName(SecurityUtils.getNickName());
        workflowSummarize.setCreateBy(SecurityUtils.getUsername());
        workflowSummarize.setCreateTime(DateUtils.getNowDate());
        return workflowSummarizeMapper.insertWorkflowSummarize(workflowSummarize);
    }

    /**
     * 修改请假
     *
     * @param workflowSummarize 请假
     * @return 结果
     */
    @Override
    public int updateWorkflowSummarize(WorkflowSummarize workflowSummarize) {
        workflowSummarize.setUpdateTime(DateUtils.getNowDate());
        return workflowSummarizeMapper.updateWorkflowSummarize(workflowSummarize);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowSummarizeByIds(String[] ids) {
        return workflowSummarizeMapper.deleteWorkflowSummarizeByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowSummarizeById(String id) {
        return workflowSummarizeMapper.deleteWorkflowSummarizeById(id);
    }

    @Override
    public WorkflowSummarize selectWorkflowSummarizeByInstanceId(String instanceId) {

        return workflowSummarizeMapper.selectWorkflowSummarizeByInstanceId(instanceId);
    }
}
