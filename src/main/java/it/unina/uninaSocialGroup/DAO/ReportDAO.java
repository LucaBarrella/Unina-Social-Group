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
    public List<Report> getGroupsReport(User user, int Month){
        List<Report> dataList = new ArrayList<>();
        PostDAO post = new PostDAO();
        String PPL, PML, PPC, PMC;
        int NMP, i = 0;
        GroupDAO groupDAO = new GroupDAO();
        groupDAO.getAdminGroups(user);
        List<Group> groups = user.getGruppiCreati();
            while(i < groups.size()){
                PPL = post.getPostPlusLike(Month, groups.get(i).getIDGruppo());
                PML = post.getPostMinusLike(Month, groups.get(i).getIDGruppo());
                PPC = post.getPostPlusComments(Month, groups.get(i).getIDGruppo());
                PMC = post.getPostMinusComments(Month, groups.get(i).getIDGruppo());
                NMP = post.getAveragePost(Month, groups.get(i).getIDGruppo());
                Report report = new Report(groups.get(i).getNomeGruppo(), PPL, PML, PPC, PMC, NMP);
                dataList.add(report);
                i += 1;
            }
        return dataList;
    }
}
