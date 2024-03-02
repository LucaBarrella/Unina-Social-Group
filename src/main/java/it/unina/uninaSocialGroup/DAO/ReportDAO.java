package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.Report;
import it.unina.uninaSocialGroup.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    /**
     * getGroupsReport
     * Restituisce i dati report mensili di ogni gruppo creato da un utente
     * @param user
     * @param Month
     * @return List<Report> dataList
     */
    public void getGroupsReport(User user, int Month){
        List<Report> dataList = new ArrayList<>();
        PostDAO post = new PostDAO();
        int i = 0;
        GroupDAO groupDAO = new GroupDAO();
        groupDAO.getAdminGroups(user);
        List<Group> groups = user.getGruppiCreati();
        while(i < groups.size()){
            Report report = new Report();
            report.setNomeGruppo(groups.get(i).getNomeGruppo());
            report.setPostPiuLike(post.getPostPlusLike(Month, groups.get(i).getIDGruppo()));
            report.setPostMenoLike(post.getPostMinusLike(Month, groups.get(i).getIDGruppo()));
            report.setPostPiuCommenti(post.getPostPlusComments(Month, groups.get(i).getIDGruppo()));
            report.setPostMenoCommenti(post.getPostMinusComments(Month, groups.get(i).getIDGruppo()));
            report.setNumMedioPost(post.getAveragePost(Month, groups.get(i).getIDGruppo()));
            dataList.add(report);
            i += 1;
        }
        user.setReportMensili(dataList);
    }

}
