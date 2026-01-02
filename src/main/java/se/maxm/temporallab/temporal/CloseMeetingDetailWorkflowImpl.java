package se.maxm.temporallab.temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

import java.time.Duration;

import static se.maxm.temporallab.temporal.CloseMeetingDetailWorkflow.TASK_QUEUE_CLOSE_MEETING;

@WorkflowImpl(taskQueues = TASK_QUEUE_CLOSE_MEETING)
public class CloseMeetingDetailWorkflowImpl implements CloseMeetingDetailWorkflow {
    private String status;

    private final CloseMeetingActivities closeMeetingActivities =
            Workflow.newActivityStub(
                    CloseMeetingActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(2)
                                    .build())
                            .build());
    @Override
    public void closeMeeting(Long meetingDetailsId) {

        //-- fetch meeting
        MeetingDetail meetingDetail = new MeetingDetail(meetingDetailsId, "Meeting no " + meetingDetailsId);


        //-- close suitability
        status = "lockSuitability";
        closeMeetingActivities.lockSuitability(meetingDetail);

        //-- createFactSheet
        status = "createFactSheet";
        closeMeetingActivities.createFactSheet(meetingDetail);

        //-- create pdf
        status = "createPdf";
        closeMeetingActivities.createPdf(meetingDetail);

        //-- send mail to customer
        status = "sendMailToCustomer";
        closeMeetingActivities.sendMailToCustomer(meetingDetail);

        //-- sendAmlMail
        status = "sendAmlMail";
        closeMeetingActivities.sendAmlMail(meetingDetail);

        status = "done";

    }

    @Override
    public String getStatus() {
        return status;
    }
}
