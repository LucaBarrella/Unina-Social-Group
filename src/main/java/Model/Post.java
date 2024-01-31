package Model;

public class Post {
    private String Categoria;
    private String MessaggioTestuale;

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getMessaggioTestuale() {
        return MessaggioTestuale;
    }

    public void setMessaggioTestuale(String messaggioTestuale) {
        MessaggioTestuale = messaggioTestuale;
    }

    public Post(String categoria, String messaggioTestuale) {
        Categoria = categoria;
        MessaggioTestuale = messaggioTestuale;
    }

    @Override
    public String toString() {
        return "Post{" +
                "Categoria='" + Categoria + '\'' +
                ", MessaggioTestuale='" + MessaggioTestuale + '\'' +
                '}';
    }
}
