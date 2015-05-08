package adapter.http;

import adapter.db.ConfigurationProperties;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import core.*;

import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@At("/report")
@Show("report.html")
public class ReportPage {

    public Integer page = 1;
    public Integer numOfPages;
    public boolean nextButtonIsActive;
    public boolean previousButtonIsActive;
    public List<OperationHistory> histories;

    private final UserRepository userRepository;
    private final FundsHistoryRepository fundsHistoryRepository;
    private final SidFetcher sidFetcher;
    private final ConfigurationProperties properties;


    @Inject
    public ReportPage(UserRepository userRepository,
                      FundsHistoryRepository fundsHistoryRepository,
                      SidFetcher sidFetcher,
                      ConfigurationProperties properties) {

        this.userRepository = userRepository;
        this.fundsHistoryRepository = fundsHistoryRepository;
        this.sidFetcher = sidFetcher;
        this.properties = properties;
    }

    @Get
    public void getPages() {

        String sid = sidFetcher.fetch();

        Integer userId = userRepository.getBySid(sid).id;

        Integer offset = properties.get("recordsPerPage");

        Integer start = (page - 1) * 5;

        histories = fundsHistoryRepository.getHistoryByPages(userId, start, offset);

        Integer numOfRecords = fundsHistoryRepository.countRecords(userId);

        numOfPages = numOfRecords / offset;
        if (numOfRecords % offset != 0) {
            numOfPages++;
        }

        nextButtonIsActive = page <= numOfPages - 1;
        previousButtonIsActive = page > 1;

    }
}
