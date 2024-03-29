package it.unina.uninaSocialGroup.Model;

import java.util.Date;
import java.util.List;

public class Group {
    private String IDGruppo;
   private String NomeGruppo;
   private Date DataDiCreazione;
   private String CategoriaGruppo;
   private List<User> ListaPartecipanti;
   private List<Post> PostPubblicati;

    public String getIDGruppo() {
        return IDGruppo;
    }

    public void setIDGruppo(String IDGruppo) {
        this.IDGruppo = IDGruppo;
    }

    public String getNomeGruppo() {
        return NomeGruppo;
    }

    public void setNomeGruppo(String nomeGruppo) {
        NomeGruppo = nomeGruppo;
    }

    public Date getDataDiCreazione() {
        return DataDiCreazione;
    }

    public void setDataDiCreazione(Date dataDiCreazione) {
        DataDiCreazione = dataDiCreazione;
    }

    public String getCategoriaGruppo() {
        return CategoriaGruppo;
    }

    public void setCategoriaGruppo(String categoriaGruppo) {
        CategoriaGruppo = categoriaGruppo;
    }

    public List<User> getListaPartecipanti() {
        return ListaPartecipanti;
    }

    public void setListaPartecipanti(List<User> listaPartecipanti) {
        ListaPartecipanti = listaPartecipanti;
    }

    public List<Post> getPostPubblicati() {
        return PostPubblicati;
    }

    public void setPostPubblicati(List<Post> postPubblicati) {
        PostPubblicati = postPubblicati;
    }

    public Group(String IDGruppo, String nomeGruppo, Date dataDiCreazione, String categoriaGruppo) {
        this.IDGruppo = IDGruppo;
        NomeGruppo = nomeGruppo;
        DataDiCreazione = dataDiCreazione;
        CategoriaGruppo = categoriaGruppo;
    }

    @Override
    public String toString() {
        return "Group{" +
                "IDGruppo='" + IDGruppo + '\'' +
                ", NomeGruppo='" + NomeGruppo + '\'' +
                ", DataDiCreazione=" + DataDiCreazione +
                ", CategoriaGruppo='" + CategoriaGruppo + '\'' +
                '}';
    }
}