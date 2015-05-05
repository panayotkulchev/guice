package adapter.shedule;

import adapter.db.SessionProperties;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Panayot Kulchev on 15-4-7.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public class SessionCleanUpScheduler {

  public void start () throws SchedulerException {

    Scheduler scheduler=new StdSchedulerFactory().getScheduler();

    JobDetail job = newJob(SessionCleanUpJob.class)
            .withIdentity("job1", "group1")
            .build();

    Trigger trigger = newTrigger()
            .withIdentity("trigger1", "group1")
            .startNow()
            .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(SessionProperties.get("sessionCleanUpRate"))
                    .repeatForever())
            .build();

    scheduler.scheduleJob(job, trigger);

    scheduler.start();

  }


}
