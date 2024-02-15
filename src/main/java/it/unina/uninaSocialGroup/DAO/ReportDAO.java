package it.unina.uninaSocialGroup.DAO;

import it.unina.uninaSocialGroup.Model.DatabaseConnectionManager;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    public List<Report> getGroupsReport(String matricola, int Month){
        List<Report> dataList = new ArrayList<>();
        PostDAO post = new PostDAO();
        String PPL, PML, PPC, PMC;
        int NMP, i = 0;
        GroupDAO group = new GroupDAO();
        List<Group> groups = group.getAdminGroups(matricola);
            while(!groups.isEmpty()){
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
