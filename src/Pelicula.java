public class Pelicula {
    
    private String titulo;
    private String categoria;
    private String duracion;
    private String[] generos;
    private String[] actores;
    private String sinopsis;

    public Pelicula(String titulo, String categoria, String duracion, String[] generos, String[] actores, String sinopsis) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.duracion = duracion;
        this.actores = actores;
        this.sinopsis = sinopsis;
        this.generos = generos;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDuracion() {
        return duracion;
    }

    public String[] getActores() {
        return actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String[] getGeneros(){
        return generos;
    }
}
