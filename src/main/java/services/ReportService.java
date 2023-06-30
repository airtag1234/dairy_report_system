package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Employee;
import models.Report;
import models.validators.ReportValidator;


public class ReportService extends ServiceBase {


    public List<ReportView> getMinePerPage(EmployeeView employee, int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_MINE, Report.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }


    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }


    public List<ReportView> getAllPerPage(int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL, Report.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    public List<ReportView> getAllTimelinePerPage(Employee loginEmp, int page){
        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_FOLOWEE_REPORT, Report.class)
                                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, loginEmp)
                                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                                .setMaxResults(JpaConst.ROW_PER_PAGE)
                                .getResultList();
        return ReportConverter.toViewList(reports);
    }



    public long countAll() {
        long reports_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT, Long.class)
                .getSingleResult();
        return reports_count;
    }


    public long countAllTimeline(Employee loginEmp) {
        long reports_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_FOLOWEE_REPORT, Long.class)
                                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, loginEmp)
                                .getSingleResult();
        return reports_count;
    }



    public ReportView findOne(int id) {
        return ReportConverter.toView(findOneInternal(id));
    }


    public List<String> create(ReportView rv) {
        List<String> errors = ReportValidator.validate(rv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            rv.setCreatedAt(ldt);
            rv.setUpdatedAt(ldt);
            createInternal(rv);
        }


        return errors;
    }


    public List<String> update(ReportView rv) {


        List<String> errors = ReportValidator.validate(rv);

        if (errors.size() == 0) {


            LocalDateTime ldt = LocalDateTime.now();
            rv.setUpdatedAt(ldt);

            updateInternal(rv);
        }


        return errors;
    }


    private Report findOneInternal(int id) {
        return em.find(Report.class, id);
    }

//    public Employee getEmp(int reportId) {
//        Employee emp = em.createNamedQuery(JpaConst.Q_EMP_GET_EMP_BY_REP_ID, Employee.class)
//                        .setParameter(JpaConst.JPQL_PARM_REPORT_ID, reportId)
//                        .getSingleResult();
//        return emp;
//    }



    private void createInternal(ReportView rv) {

        em.getTransaction().begin();
        em.persist(ReportConverter.toModel(rv));
        em.getTransaction().commit();

    }


    private void updateInternal(ReportView rv) {

        em.getTransaction().begin();
        Report r = findOneInternal(rv.getId());
        ReportConverter.copyViewToModel(r, rv);
        em.getTransaction().commit();

    }

}