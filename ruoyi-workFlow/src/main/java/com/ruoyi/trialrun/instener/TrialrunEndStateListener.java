package com.ruoyi.trialrun.instener;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.trialrun.domain.WorkflowTrialrun;
import com.ruoyi.trialrun.service.IWorkflowTrialrunService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;


    public class TrialrunEndStateListener implements ExecutionListener {
        private Expression state;

        @Override
        public void notify(DelegateExecution delegateExecution) {
            WorkflowTrialrun workflowTrialrun = new WorkflowTrialrun();
            workflowTrialrun.setId(delegateExecution.getProcessInstanceBusinessKey());
            workflowTrialrun.setState(state.getValue(delegateExecution).toString());
            SpringUtils.getBean(IWorkflowTrialrunService.class).updateWorkflowTrialrun(workflowTrialrun);
        }
    }

