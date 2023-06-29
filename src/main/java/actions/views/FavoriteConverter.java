package actions.views;

import models.Favorite;


public class FavoriteConverter {


    public static Favorite toModel(FavoriteView fv) {
        return new Favorite(
                fv.getId(),
                fv.getReport(),
                fv.getEmployee());
    }


    public static FavoriteView toView(Favorite f) {
        return new FavoriteView(
                f.getId(),
                f.getReport(),
                f.getEmployee());
    }


    public static void copyViewToModel(Favorite f,FavoriteView fv) {
        f.setId(fv.getId());
        f.setReport(fv.getReport());
        f.setEmployee(fv.getEmployee());
    }
}