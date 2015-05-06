package adapter.http;

import adapter.db.PageProperties;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import core.*;

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
    public Integer numOfPages;
    public boolean nextButtonIsActive;
    public boolean previousButtonIsActive;
    public List<OperationHistory> histories;

    private final UserRepository userRepository;
    private final FundsRepository fundsRepository;
    private final Provider<HttpServletRequest> requestProvider;


    @Inject
    public Report(UserRepository userRepository,
                  FundsRepository fundsRepository,
                  Provider<HttpServletRequest> requestProvider) {

        this.userRepository = userRepository;
        this.fundsRepository = fundsRepository;
        this.requestProvider = requestProvider;
    }

    @Get
    public void getPages() {

        HttpServletRequest request = requestProvider.get();
        String sid = SidProvider.getSid(request);
        Integer userId = userRepository.getBySid(sid).id;

        Integer offset = PageProperties.get("recordsPerPage");

        Integer start = (page - 1) * 5;

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
