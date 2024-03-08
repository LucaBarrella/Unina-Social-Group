package it.unina.uninaSocialGroup;

import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.controller.LogicalController;
import it.unina.uninaSocialGroup.controller.VboxController;

import java.util.Date;

public class Esperimento {

    public static void main(String[] args) {
        LogicalController controller = new LogicalController();
        Date currentDate = new Date(); // Assuming CURRENT_DATE is supposed to be the current date
        controller.setGroup(new Group("1", "Nome Gruppo1", currentDate, "Categoria1"));
        System.out.println(controller.getGroupName() + " nulla, tra");
        Group group = controller.getGroup();
        controller.setGroup(new Group("2", "Nome Gruppo2", currentDate, "Categoria2"));
        System.out.println(group.getNomeGruppo() + " " + controller.getGroupName());
        controller.setGroup(new Group("3", "Nome Gruppo3", currentDate, "Categoria3"));
        System.out.println(group.getNomeGruppo() + " " + controller.getGroupName());
    }

    public static void main2(String[] args) {

    }
}