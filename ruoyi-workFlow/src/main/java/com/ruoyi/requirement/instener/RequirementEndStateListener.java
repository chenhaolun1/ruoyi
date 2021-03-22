package com.ruoyi.requirement.instener;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.requirement.domain.WorkflowRequirement;
import com.ruoyi.requirement.service.IWorkflowRequirementService;
import com.ruoyi.requirement.service.IWorkflowRequirementService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;


public class RequirementEndStateListener implements ExecutionListener {
    private Expression state;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        WorkflowRequirement workflowRequirement = new WorkflowRequirement();
        workflowRequirement.setId(delegateExecution.getProcessInstanceBusinessKey());
        workflowRequirement.setState(state.getValue(delegateExecution).toString());
        SpringUtils.getBean(IWorkflowRequirementService.class).updateWorkflowRequirement(workflowRequirement);
    }
}
