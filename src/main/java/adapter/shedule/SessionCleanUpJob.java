package adapter.shedule;

import adapter.db.DataStore;
import com.google.inject.Inject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class SessionCleanUpJob implements Job {

    private final DataStore dataStore;

    @Inject
    public SessionCleanUpJob(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Logger logger = LoggerFactory.getLogger(SessionCleanUpJob.class);
        logger.info("STARTED session CLEAN process!");

        dataStore.executeQuery("DELETE FROM bank.session WHERE expiration_time<?;", System.currentTimeMillis());

    }
}
