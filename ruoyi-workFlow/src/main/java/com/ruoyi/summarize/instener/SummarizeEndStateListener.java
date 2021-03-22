package com.ruoyi.summarize.instener;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.summarize.domain.WorkflowSummarize;
import com.ruoyi.summarize.service.IWorkflowSummarizeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;


    public class SummarizeEndStateListener implements ExecutionListener {
        private Expression state;

        @Override
        public void notify(DelegateExecution delegateExecution) {
            WorkflowSummarize workflowSummarize = new WorkflowSummarize();
            workflowSummarize.setId(delegateExecution.getProcessInstanceBusinessKey());
            workflowSummarize.setState(state.getValue(delegateExecution).toString());
            SpringUtils.getBean(IWorkflowSummarizeService.class).updateWorkflowSummarize(workflowSummarize);
        }
    }

