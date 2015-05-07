package adapter.shedule;

import adapter.db.ConfigurationProperites;
import adapter.db.DatabaseMetadata;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class SessionCleanUpScheduler {

    public void start() throws SchedulerException {

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        Integer sessionCleanUpRate = ConfigurationProperites.get("sessionCleanUpRate");


        JobDetail job = newJob(SessionCleanUpJob.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(sessionCleanUpRate)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(job, trigger);

        scheduler.start();

    }


}
