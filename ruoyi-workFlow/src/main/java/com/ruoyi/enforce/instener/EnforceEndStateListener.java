package com.ruoyi.enforce.instener;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.enforce.domain.WorkflowEnforce;
import com.ruoyi.enforce.service.IWorkflowEnforceService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

public class EnforceEndStateListener implements ExecutionListener {
    private Expression state;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        WorkflowEnforce workflowEnforce = new WorkflowEnforce();
        workflowEnforce.setId(delegateExecution.getProcessInstanceBusinessKey());
        workflowEnforce.setState(state.getValue(delegateExecution).toString());
        SpringUtils.getBean(IWorkflowEnforceService.class).updateWorkflowEnforce(workflowEnforce);
    }
}

