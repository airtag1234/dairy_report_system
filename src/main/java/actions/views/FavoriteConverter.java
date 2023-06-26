package actions.views;

import constants.AttributeConst;
import constants.JpaConst;
import models.Favorite;


public class FavoriteConverter {


    public static Favorite toModel(FavoriteView fv) {
        return new Favorite(
                fv.getId(),
                fv.getReport(),
                fv.getEmployee(),
                fv.getFavoriteFlag() == null
                    ? null
                    : fv.getFavoriteFlag() == AttributeConst.FAV_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.FAV_REP_TRUE
                        : JpaConst.FAV_REP_FALSE,
                fv.getFavoriteAt());
    }


    public static FavoriteView toView(Favorite f) {
        return new FavoriteView(
                f.getId(),
                f.getReport(),
                f.getEmployee(),
                f.getFavoriteFlag() == null
                    ? null
                    : f.getFavoriteFlag() == JpaConst.FAV_REP_TRUE
                        ? AttributeConst.FAV_FLAG_TRUE.getIntegerValue()
                        : AttributeConst.FAV_FLAG_FALSE.getIntegerValue(),
                f.getFavoriteAt());
    }


    public static void copyViewToModel(Favorite f,FavoriteView fv) {
        f.setId(fv.getId());
        f.setReport(fv.getReport());
        f.setEmployee(fv.getEmployee());
        f.setFavoriteFlag(fv.getFavoriteFlag());
        f.setFavoriteAt(fv.getFavoriteAt());
    }
}