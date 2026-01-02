package se.maxm.temporallab.controller;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.WorkflowIdReusePolicy;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.maxm.temporallab.temporal.CloseMeetingDetailWorkflow;

import static se.maxm.temporallab.temporal.CloseMeetingDetailWorkflow.TASK_QUEUE_CLOSE_MEETING;

@RestController()
@RequestMapping("/workflow")
public class WorkflowController {

    private final WorkflowClient workflowClient;

    public WorkflowController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @PostMapping("/closemeeting/{meetingDetailsId}")
    public void closeMeetingWorkflow(@PathVariable Long meetingDetailsId) {
        final WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setTaskQueue(TASK_QUEUE_CLOSE_MEETING)
                .setWorkflowId("CLOSE_MEETING_" + meetingDetailsId.toString())
                .setWorkflowIdReusePolicy(WorkflowIdReusePolicy.WORKFLOW_ID_REUSE_POLICY_REJECT_DUPLICATE)
                .build();

        CloseMeetingDetailWorkflow workflow = workflowClient.newWorkflowStub(CloseMeetingDetailWorkflow.class, workflowOptions);
        WorkflowExecution taskExecution = WorkflowClient.start(workflow::closeMeeting, meetingDetailsId);
        System.out.println("Workflow started, id:" + taskExecution.getWorkflowId());
    }

}
