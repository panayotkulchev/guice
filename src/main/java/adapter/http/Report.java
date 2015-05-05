package adapter.http;

import adapter.db.PageProperties;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import core.FundsRepository;
import core.OperationHistory;
import core.SessionRepository;
import core.SidProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Panayot Kulchev on 15-4-29.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

@At("/report")
@Show("report.html")
public class Report {

    public Integer page = 1;
    public List<OperationHistory> histories;
    public Integer start;
    public Integer offset = 5;
    public Integer numOfPages = 0;
    public boolean nextButtonIsActive;
    public boolean previousButtonIsActive;

    private final SessionRepository sessionRepository;
    private final FundsRepository fundsRepository;
    private final Provider<HttpServletRequest> requestProvider;


    @Inject
    public Report(SessionRepository sessionRepository,
                  FundsRepository fundsRepository,
                  Provider<HttpServletRequest> requestProvider) {

        this.sessionRepository = sessionRepository;
        this.fundsRepository = fundsRepository;
        this.requestProvider = requestProvider;
    }

    @Get
    public void getPages() {

        HttpServletRequest request = requestProvider.get();
        String sid = SidProvider.getSid(request);
        Integer userId = sessionRepository.getUserIdBySid(sid);

        offset = PageProperties.get("recordsPerPage");

        start = (page - 1) * 5;

        histories = fundsRepository.getHistoryByPages(userId, start, offset);

        Integer numOfRecords = fundsRepository.countRecords(userId);

        numOfPages = numOfRecords / offset;
        if (numOfRecords % offset != 0) {
            numOfPages++;
        }

        nextButtonIsActive = page <= numOfPages - 1;
        previousButtonIsActive = page > 1;

    }
}
