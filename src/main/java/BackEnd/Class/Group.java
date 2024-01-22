package BackEnd.Class;

import java.util.Date;
import java.util.List;

public class Group {
   public String NomeGruppo;
   public Date DataDiCreazione;
   public String CategoriaGruppo;
   private List<User> ListaPartecipanti;

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

    public Group(String nomeGruppo, Date dataDiCreazione, String categoriaGruppo) {
        NomeGruppo = nomeGruppo;
        DataDiCreazione = dataDiCreazione;
        CategoriaGruppo = categoriaGruppo;
    }
}