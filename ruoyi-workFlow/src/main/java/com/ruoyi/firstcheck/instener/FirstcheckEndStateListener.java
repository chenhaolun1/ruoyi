package com.ruoyi.firstcheck.instener;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.firstcheck.domain.WorkflowFirstcheck;
import com.ruoyi.firstcheck.service.IWorkflowFirstcheckService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

public class FirstcheckEndStateListener implements ExecutionListener {
    private Expression state;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        WorkflowFirstcheck workflowFirstcheck = new WorkflowFirstcheck();
        workflowFirstcheck.setId(delegateExecution.getProcessInstanceBusinessKey());
        workflowFirstcheck.setState(state.getValue(delegateExecution).toString());
        SpringUtils.getBean(IWorkflowFirstcheckService.class).updateWorkflowFirstcheck(workflowFirstcheck);
    }
}

