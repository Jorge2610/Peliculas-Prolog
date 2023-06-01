import java.util.HashSet;
import java.util.Map;

import org.jpl7.*;

public class ConsultasProlog {

    public ConsultasProlog() {
        Query q1 = new Query(
                "consult",
                new Term[] { new Atom("E:\\U\\Semantica\\Peliculas-Prolog\\src\\prolog\\BaseConocimientos.pl") });
        q1.hasSolution();
        // System.out.println("consult " + (q1.hasSolution() ? "succeeded" : "failed"));
    }

    public Pelicula[] recomendarPelicula(String[] generos, String[] actores) {
        Variable X = new Variable("X");
        Variable Y = new Variable("Y");
        Variable Z = new Variable("Z");
        Variable A = new Variable("A");
        Query q4 = new Query(
                "recomendar_pelicula",
                new Term[] { X, Y, Z, A, Term.stringArrayToList(generos), Term.stringArrayToList(actores) });

        Map<String, Term>[] solutions = q4.allSolutions();
        HashSet<String> titulos = new HashSet<>();
        Pelicula[] peliculasDup = new Pelicula[solutions.length];
        int contadorUnicos = 0;
        int indiceUnicos = 0;
        for (int i = 0; i < solutions.length; i++) {
            if (titulos.add(getTitulo(solutions[i]))) {
                peliculasDup[indiceUnicos] = new Pelicula(
                        getTitulo(solutions[i]),
                        getCategoria(solutions[i]),
                        getDuracion(solutions[i]),
                        getGeneros(solutions[i]),
                        getActores(solutions[i]),
                        getSinopsis(solutions[i]));
                contadorUnicos++;
                indiceUnicos++;
            }
        }
        Pelicula[] peliculas = new Pelicula[contadorUnicos];
        for (int i = 0; i < contadorUnicos; i++) {
            peliculas[i] = peliculasDup[i];
        }
        return peliculas;
    }

    public Pelicula[] recomendarPorGenero(String[] generos){
        Variable X = new Variable("X");
        Variable Y = new Variable("Y");
        Variable Z = new Variable("Z");
        Variable A = new Variable("A");
        Query q4 = new Query(
                "recomendar_por_genero",
                new Term[] { X, Y, Z, A, Term.stringArrayToList(generos) });

        Map<String, Term>[] solutions = q4.allSolutions();
        HashSet<String> titulos = new HashSet<>();
        Pelicula[] peliculasDup = new Pelicula[solutions.length];
        int contadorUnicos = 0;
        int indiceUnicos = 0;
        for (int i = 0; i < solutions.length; i++) {
            if (titulos.add(getTitulo(solutions[i]))) {
                peliculasDup[indiceUnicos] = new Pelicula(
                        getTitulo(solutions[i]),
                        getCategoria(solutions[i]),
                        getDuracion(solutions[i]),
                        getGeneros(solutions[i]),
                        getActores(solutions[i]),
                        getSinopsis(solutions[i]));
                contadorUnicos++;
                indiceUnicos++;
            }
        }
        Pelicula[] peliculas = new Pelicula[contadorUnicos];
        for (int i = 0; i < contadorUnicos; i++) {
            peliculas[i] = peliculasDup[i];
        }
        return peliculas;
    }

    public Pelicula[] recomendarPorActor(String[] actores){
        Variable X = new Variable("X");
        Variable Y = new Variable("Y");
        Variable Z = new Variable("Z");
        Variable A = new Variable("A");
        Query q4 = new Query(
                "recomendar_por_actor",
                new Term[] { X, Y, Z, A, Term.stringArrayToList(actores) });

        Map<String, Term>[] solutions = q4.allSolutions();
        HashSet<String> titulos = new HashSet<>();
        Pelicula[] peliculasDup = new Pelicula[solutions.length];
        int contadorUnicos = 0;
        int indiceUnicos = 0;
        for (int i = 0; i < solutions.length; i++) {
            if (titulos.add(getTitulo(solutions[i]))) {
                peliculasDup[indiceUnicos] = new Pelicula(
                        getTitulo(solutions[i]),
                        getCategoria(solutions[i]),
                        getDuracion(solutions[i]),
                        getGeneros(solutions[i]),
                        getActores(solutions[i]),
                        getSinopsis(solutions[i]));
                contadorUnicos++;
                indiceUnicos++;
            }
        }
        Pelicula[] peliculas = new Pelicula[contadorUnicos];
        for (int i = 0; i < contadorUnicos; i++) {
            peliculas[i] = peliculasDup[i];
        }
        return peliculas;
    }

    private String getTitulo(Map<String, Term> resp) {
        return resp.get("X").toString().replace("'", "");
    }

    private String getCategoria(Map<String, Term> resp) {
        return resp.get("Z").args()[1].args()[0].toString().replace("'", "");
    }

    private String getDuracion(Map<String, Term> resp) {
        return resp.get("Z").args()[0].toString().replace("'", "");
    }

    private String[] getGeneros(Map<String, Term> resp) {
        String lista = resp.get("Y").toString().replace("[", "");
        lista = lista.replace("]", "").replace("'", "");
        String[] generos = lista.split(",");
        for (int i = 0; i < generos.length; i++) {
            generos[i] = generos[i].trim();
        }
        return generos;
    }

    private String[] getActores(Map<String, Term> resp) {
        String lista = resp.get("A").toString().replace("[", "");
        lista = lista.replace("]", "").replace("'", "");
        String[] actores = lista.split(",");
        for (int i = 0; i < actores.length; i++) {
            actores[i] = actores[i].trim();
        }
        return actores;
    }

    private String getSinopsis(Map<String, Term> resp) {
        return resp.get("Z").args()[1].args()[1].args()[0].toString().replace("'", "");
    }
}
