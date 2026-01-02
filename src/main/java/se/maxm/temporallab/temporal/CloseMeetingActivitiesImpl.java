package se.maxm.temporallab.temporal;


import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

import static se.maxm.temporallab.temporal.CloseMeetingDetailWorkflow.TASK_QUEUE_CLOSE_MEETING;

@Component
@ActivityImpl(taskQueues = TASK_QUEUE_CLOSE_MEETING)
public class CloseMeetingActivitiesImpl implements CloseMeetingActivities {
    @Override
    public void sendMailToCustomer(MeetingDetail meetingDetail) {
        System.out.println("starting sendMailToCustomer");
        sov(3000);
        System.out.println("finished sendMailToCustomer");
    }

    @Override
    public void createPdf(MeetingDetail meetingDetail) {
        System.out.println("starting createPdf");
        sov(10000);
        System.out.println("finished createPdf");
    }

    @Override
    public void lockSuitability(MeetingDetail meetingDetail) {
        System.out.println("starting lockSuitability");
        sov(5000);
        System.out.println("finished lockSuitability");
    }

    @Override
    public void sendAmlMail(MeetingDetail meetingDetail) {
        System.out.println("starting sendAmlMail");
        sov(3000);
        System.out.println("finished sendAmlMail");
    }

    @Override
    public void createFactSheet(MeetingDetail meetingDetail) {
        sov(10000);
        System.out.println("logging createFactSheet");
    }

    private static void sov(long millis) {
        try {
             Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
