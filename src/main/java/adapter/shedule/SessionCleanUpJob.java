package adapter.shedule;

import adapter.db.DataStore;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Panayot Kulchev on 15-4-7.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public class SessionCleanUpJob implements Job {

    DataStore dataStore = new DataStore(new SchedulerConnectionProvider());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Logger logger = LoggerFactory.getLogger(SessionCleanUpJob.class);
        logger.info("STARTED session CLEAN process!");

        dataStore.executeQery("DELETE FROM bank.session WHERE expiration_time<?;", System.currentTimeMillis());

    }
}
