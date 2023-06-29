package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Table(name = JpaConst.TABLE_FAV)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_FAV_ALL_FAVORITE_COUNT_TO_REPORT,
            query = JpaConst.Q_FAV_ALL_FAVORITE_COUNT_TO_REPORT_DEF),
    @NamedQuery(
            name = JpaConst.Q_FAV_COUNT_CREATED_MINE_FAVORITE_DATA_TO_REPORT,
            query = JpaConst.Q_FAV_COUNT_CREATED_MINE_FAVORITE_DATA_TO_REPORT_DEF),
    @NamedQuery(
            name = JpaConst.Q_FAV_COUNT_MINE_FAVORITE_TO_REPORT,
            query = JpaConst.Q_FAV_COUNT_MINE_FAVORITE_TO_REPORT_DEF),
    @NamedQuery(
            name = JpaConst.Q_FAV_GET_MINE_FAVORITE_TO_REPORT,
            query = JpaConst.Q_FAV_GET_MINE_FAVORITE_TO_REPORT_DEF)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Favorite {


    @Id
    @Column(name = JpaConst.FAV_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


//     「いいね」をした日報

    @ManyToOne
    @JoinColumn(name = JpaConst.FAV_COL_REP,nullable = false)
    private Report report;


//     「いいね」をした従業員

    @ManyToOne
    @JoinColumn(name = JpaConst.FAV_COL_EMP,nullable = false)
    private Employee employee;





}