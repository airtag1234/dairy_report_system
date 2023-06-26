package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.FavoriteService;
import services.ReportService;


public class TopAction extends ActionBase {

    private ReportService service;
    private FavoriteService favoriteService;

    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();
        favoriteService = new FavoriteService();

        invoke();

        service.close();
        favoriteService.close();
    }

    public void index() throws ServletException, IOException {


        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);


        int page = getPage();
        List<ReportView> reports = service.getMinePerPage(loginEmployee, page);



        long myReportsCount = service.countAllMine(loginEmployee);

        //リアクション数のリストを作成する
        List<Long> favorites = favoriteService.getAllCountFavoriteToReport(reports);


        putRequestScope(AttributeConst.REPORTS, reports);
        putRequestScope(AttributeConst.REP_COUNT, myReportsCount);
        putRequestScope(AttributeConst.PAGE, page);
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);
        putRequestScope(AttributeConst.FAVORITES, favorites);

        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }
        forward(ForwardConst.FW_TOP_INDEX);

    }
}
