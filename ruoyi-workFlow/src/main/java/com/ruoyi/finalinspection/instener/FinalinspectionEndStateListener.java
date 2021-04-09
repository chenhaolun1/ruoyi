package com.ruoyi.finalinspection.instener;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.finalinspection.domain.WorkflowFinalinspection;
import com.ruoyi.finalinspection.service.IWorkflowFinalinspectionService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;


    public class FinalinspectionEndStateListener implements ExecutionListener {
        private Expression state;

        @Override
        public void notify(DelegateExecution delegateExecution) {
            WorkflowFinalinspection workflowFinalinspection = new WorkflowFinalinspection();
            workflowFinalinspection.setId(delegateExecution.getProcessInstanceBusinessKey());
            workflowFinalinspection.setState(state.getValue(delegateExecution).toString());
            SpringUtils.getBean(IWorkflowFinalinspectionService.class).updateWorkflowFinalinspection(workflowFinalinspection);
        }
    }

