package adapter.http;

import adapter.db.ConfigurationProperites;
import adapter.db.DatabaseMetadata;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import core.*;

import javax.servlet.http.HttpServletRequest;
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
    private final Provider<HttpServletRequest> requestProvider;


    @Inject
    public ReportPage(UserRepository userRepository,
                      FundsHistoryRepository fundsHistoryRepository,
                      Provider<HttpServletRequest> requestProvider) {

        this.userRepository = userRepository;
        this.fundsHistoryRepository = fundsHistoryRepository;
        this.requestProvider = requestProvider;
    }

    @Get
    public void getPages() {

        HttpServletRequest request = requestProvider.get();
        String sid = SidProvider.getSid(request);
        Integer userId = userRepository.getBySid(sid).id;

        Integer offset = ConfigurationProperites.get("recordsPerPage");

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
