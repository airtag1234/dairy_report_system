package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FavoriteConverter;
import actions.views.FavoriteView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.JpaConst;
import models.Favorite;


public class FavoriteService extends ServiceBase {

    /**
     * 日報のリアクションフラグをtrueにする
     * @param rv 日報データ
     * @param ev 従業員データ
     * @return 処理が完了した場合true
     */
    public boolean doFavorite(ReportView rv,EmployeeView ev) {
        try {
            Favorite f = em.createNamedQuery(JpaConst.Q_FAV_GET_MINE_FAVORITE_TO_REPORT,Favorite.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev))
                .getSingleResult();
            em.getTransaction().begin();
            f.setFavoriteFlag(JpaConst.FAV_REP_TRUE);
            em.getTransaction().commit();

            return true;
        }catch(NoResultException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 日報のリアクションフラグをfalseにする
     * @param rv 日報データ
     * @param ev 従業員データ
     * @return 処理が完了した場合true
     */
    public boolean deleteFavorite(ReportView rv,EmployeeView ev) {
        try {
            Favorite f = em.createNamedQuery(JpaConst.Q_FAV_GET_MINE_FAVORITE_TO_REPORT,Favorite.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev))
                .getSingleResult();
            em.getTransaction().begin();
            f.setFavoriteFlag(JpaConst.FAV_REP_FALSE);
            em.getTransaction().commit();

            return true;
        }catch(NoResultException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 指定した日報についたリアクションの件数を取得し、返却する
     * @param rv ReportView
     * @return 「いいね」の件数
     */
    public long countAllFavoriteToReport(ReportView rv) {

        long count = (long)em.createNamedQuery(JpaConst.Q_FAV_ALL_FAVORITE_COUNT_TO_REPORT,Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .getSingleResult();

        return count;
    }

    /**
     * 日報のリストをもとにリアクション数のリストを作成し、返却する
     * @param reportViewList 日報のリスト
     * @return リアクション数のリスト
     */
    public List<Long> getAllCountFavoriteToReport(List<ReportView> reportViewList){
        List<Long> favoriteCount = new ArrayList<Long>();
        for(ReportView rv : reportViewList){
            favoriteCount.add(countAllFavoriteToReport(rv));
        }
        return favoriteCount;


    }


    public long countCreatedMineFavoriteDataToReport(ReportView rv,EmployeeView ev) {

        long count = (long)em.createNamedQuery(JpaConst.Q_FAV_COUNT_CREATED_MINE_FAVORITE_DATA_TO_REPORT,Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev))
                .getSingleResult();

        return count;
    }


    public long countMineFavoriteToReport(ReportView rv,EmployeeView ev) {

        long count = (long)em.createNamedQuery(JpaConst.Q_FAV_COUNT_MINE_FAVORITE_TO_REPORT,Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev))
                .getSingleResult();

        return count;
    }

    /**
     * 従業員用の日報リアクションテーブルを作成する(既にある場合は作成しない)
     * @param rv 日報データ
     * @param ev 従業員データ
     */
    public void create(ReportView rv,EmployeeView ev) {
        if(countCreatedMineFavoriteDataToReport(rv,ev) == 0) {
            FavoriteView favoriteView = new FavoriteView(
                    null,
                    ReportConverter.toModel(rv),
                    EmployeeConverter.toModel(ev),
                    AttributeConst.FAV_FLAG_FALSE.getIntegerValue());
            createInternal(FavoriteConverter.toModel(favoriteView));
        }
    }


    private void createInternal(Favorite f) {
        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
    }

}