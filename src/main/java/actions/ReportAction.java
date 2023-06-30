package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Employee;
import services.EmployeeService;
import services.FavoriteService;
import services.FollowService;
import services.ReportService;


public class ReportAction extends ActionBase {

    private ReportService reportService;
    private FavoriteService favoriteService;
    private FollowService followService;
    private EmployeeService empService;

    @Override
    public void process() throws ServletException, IOException {

        reportService = new ReportService();
        favoriteService = new FavoriteService();
        followService = new FollowService();
        empService = new EmployeeService();

        invoke();
        reportService.close();
        favoriteService.close();
        followService.close();
        empService.close();
        }

    public void entryNew() throws ServletException, IOException{

        putRequestScope(AttributeConst.TOKEN, getTokenId());

        ReportView rv = new ReportView();
        rv.setReportDate(LocalDate.now());
        putRequestScope(AttributeConst.REPORT, rv);

        forward(ForwardConst.FW_REP_NEW);
    }


    public void index() throws ServletException, IOException {



        int page = getPage();
        List<ReportView> reports = reportService.getAllPerPage(page);


        long reportsCount = reportService.countAll();

        List<Long> favorites = favoriteService.getAllCountFavoriteToReport(reports);


        putRequestScope(AttributeConst.REPORTS, reports);
        putRequestScope(AttributeConst.REP_COUNT, reportsCount);
        putRequestScope(AttributeConst.PAGE, page);
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);
        putRequestScope(AttributeConst.FAVORITES,favorites);


        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }


        forward(ForwardConst.FW_REP_INDEX);
    }

    public void create() throws ServletException, IOException {


        if (checkToken()) {


            LocalDate day = null;
            if (getRequestParam(AttributeConst.REP_DATE) == null
                    || getRequestParam(AttributeConst.REP_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.REP_DATE));
            }


            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);


            ReportView rv = new ReportView(
                    null,
                    ev,
                    day,
                    getRequestParam(AttributeConst.REP_TITLE),
                    getRequestParam(AttributeConst.REP_CONTENT),
                    null,
                    null);


            List<String> errors = reportService.create(rv);

            if (errors.size() > 0) {


                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rv);//入力された日報情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト



                forward(ForwardConst.FW_REP_NEW);

            } else {


                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());


                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
            }
        }
    }
    public void show() throws ServletException, IOException {


        ReportView rv = reportService.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        EmployeeView ev = getSessionScope(AttributeConst.LOGIN_EMP);

        if (rv == null) {

            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.REPORT, rv);

          //ログイン従業員を取得
            Employee loginEmp = EmployeeConverter.toModel(getSessionScope(AttributeConst.LOGIN_EMP));

            Employee targetEmp = empService.getEmp(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //フォローしているか確認
            boolean isFollow = followService.isFollow(loginEmp, targetEmp);


            if(favoriteService.countCreatedMineFavoriteDataToReport(rv, ev) != 1) {
                favoriteService.create(rv, ev);
            }

            long favoriteCount = favoriteService.countAllFavoriteToReport(rv);
            long myFavoriteCount = favoriteService.countMineFavoriteToReport(rv, ev);

            putRequestScope(AttributeConst.TOKEN,getTokenId());
            putRequestScope(AttributeConst.FAV_FAVORITE_COUNT,favoriteCount);                         //日報についたリアクション数
            putRequestScope(AttributeConst.FAV_MY_FAVORITE_COUNT,myFavoriteCount);                    //自分が日報につけたリアクションの数
            putRequestScope(AttributeConst.FOL_IS_FOLLOW, isFollow);

            forward(ForwardConst.FW_REP_SHOW);

        }
    }

    public void edit() throws ServletException, IOException {


        ReportView rv = reportService.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));


        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (rv == null || ev.getId() != rv.getEmployee().getId()) {

            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.REPORT, rv); //取得した日報データ


            forward(ForwardConst.FW_REP_EDIT);
        }

    }

    public void update() throws ServletException, IOException {


        if (checkToken()) {


            ReportView rv = reportService.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));


            rv.setReportDate(toLocalDate(getRequestParam(AttributeConst.REP_DATE)));
            rv.setTitle(getRequestParam(AttributeConst.REP_TITLE));
            rv.setContent(getRequestParam(AttributeConst.REP_CONTENT));


            List<String> errors = reportService.update(rv);

            if (errors.size() > 0) {


                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト


                forward(ForwardConst.FW_REP_EDIT);
            } else {

                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());


                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);

            }
        }
    }

    public void doFavorite() throws ServletException,IOException{

        //CSRF対策
        if(checkToken()) {
            int id = Integer.parseInt(getRequestParam(AttributeConst.REP_ID));
            ReportView rv = reportService.findOne(id);
            EmployeeView ev = getSessionScope(AttributeConst.LOGIN_EMP);

            //お気に入りを行い、完了した場合フラッシュメッセージを設定
            if(favoriteService.doFavorite(rv, ev)) {
                putRequestScope(AttributeConst.FLUSH,MessageConst.I_ADD_FAVORITE.getMessage());
            }

            //詳細画面の呼び出し処理
            show();
        }
    }


    public void deleteFavorite() throws ServletException,IOException{

        //CSRF対策
        if(checkToken()) {
            int id = Integer.parseInt(getRequestParam(AttributeConst.REP_ID));
            ReportView rv = reportService.findOne(id);
            EmployeeView ev = getSessionScope(AttributeConst.LOGIN_EMP);
            //お気に入りを取り消し、完了した場合フラッシュメッセージを設定
            if(favoriteService.deleteFavorite(rv, ev)) {
                putRequestScope(AttributeConst.FLUSH,MessageConst.I_SUB_FAVORITE.getMessage());
            }

            //詳細画面の呼び出し処理
            show();
        }
    }

    public void timeline() throws ServletException, IOException {

        int page = getPage();


        List<ReportView> reports = new ArrayList<ReportView>();
        long reportsCount = 0;

        putRequestScope(AttributeConst.REPORTS, reports);
        putRequestScope(AttributeConst.REP_COUNT, reportsCount);
        putRequestScope(AttributeConst.PAGE, page);
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);

        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        forward(ForwardConst.FW_REP_TIMELINE);

    }






}