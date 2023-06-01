import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;

public class InterfazGrafica extends JFrame {

    private JPanel panelInicio;
    private JPanel panelGenero;
    private JPanel panelActores;
    private JPanel panelPeliculas;

    private DefaultTableModel generos;
    private DefaultTableModel actores;
    private DefaultTableModel peliculas;

    private String[] listaActores;
    private String[] listaGeneros;

    private ConsultasProlog consultor;
    private Pelicula[] pelicula;

    public InterfazGrafica() {
        consultor = new ConsultasProlog();
        setTitle("Recomendador de Peliculas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        initFrame();
        pack();
    }

    private void initFrame() {
        Dimension tamVentana = new Dimension(850, 480);
        setPreferredSize(tamVentana);
        initPanelInicio();
        initPanelGenero();
        initPanelActores();
        setContentPane(panelInicio);
    }

    private void initPanelInicio() {
        panelInicio = new JPanel();
        panelInicio.setLayout(null);

        JLabel titulo = new JLabel("<html>Sistema experto de recomendacion de peliculas</html>");
        titulo.setBounds(510, 20, 300, 130);
        titulo.setFont(new Font("sansserif", Font.PLAIN, 35));
        panelInicio.add(titulo);

        JButton pedirRecomendacion = new JButton("Consultar");
        pedirRecomendacion.setFont(new Font("sansserif", Font.PLAIN, 15));
        pedirRecomendacion.setBounds(659, 165, 150, 50);
        pedirRecomendacion.addActionListener(e -> {
            setContentPane(panelGenero);
            pack();
        });
        panelInicio.add(pedirRecomendacion);

        JLabel fondo = new JLabel();
        fondo.setBounds(0, 0, 834, 441);
        ImageIcon imagen = new ImageIcon(getClass().getResource("img/cine.jpg"));
        Icon icono = new ImageIcon(
                imagen.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), Image.SCALE_DEFAULT));
        fondo.setIcon(icono);
        panelInicio.add(fondo);
    }

    private void initPanelGenero() {
        panelGenero = new JPanel(null);

        JButton siguiente = new JButton("Siguiente");
        siguiente.setBounds(659, 25, 150, 50);
        siguiente.setFont(new Font("sansserif", Font.PLAIN, 15));
        siguiente.addActionListener(e -> {
            guardarGeneros();
            setContentPane(panelActores);
            pack();
        });
        panelGenero.add(siguiente);

        JButton atras = new JButton("Atras");
        atras.setBounds(659, 100, 150, 50);
        atras.setFont(new Font("sansserif", Font.PLAIN, 15));
        atras.addActionListener(e -> {
            setContentPane(panelInicio);
            pack();
        });
        panelGenero.add(atras);

        String[] nombresColumnas = { "Genero", "Favorito" };
        Object[][] datos = {
                { "Accion", false },
                { "Ciencia ficcion", false },
                { "Fantasia", false },
                { "Drama", false },
                { "Crimen", false },
                { "Cine bélico", false },
                { "Cine biográfico", false } };
        ordenarDatos(datos);
        generos = new DefaultTableModel(datos, nombresColumnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1) {
                    return true;
                }
                return false;
            }
        };

        JTable tabla = new JTable(generos) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(generos);

        JLabel buscadorLabel = new JLabel("Buscar genero:");
        buscadorLabel.setBounds(25, 25, 100, 25);
        buscadorLabel.setFont(new Font("sansserif", Font.PLAIN, 15));
        panelGenero.add(buscadorLabel);

        JTextField buscador = new JTextField();
        buscador.setBounds(130, 25, 504, 25);
        buscador.setFont(new Font("sansserif", Font.PLAIN, 15));
        buscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sorter.sort();
            }
        });
        panelGenero.add(buscador);

        RowFilter<Object, Object> startsWithAFilter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                String row = entry.getStringValue(0).toUpperCase();
                return row.contains(buscador.getText().toUpperCase());
            }
        };
        sorter.setRowFilter(startsWithAFilter);
        tabla.setRowSorter(sorter);

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(495);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(102);
        tabla.setSelectionMode(0);

        tabla.setFont(new Font("sansserif", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(25, 75, 609, 341);
        panelGenero.add(scrollPane);

        JLabel fondo = new JLabel();
        fondo.setBounds(0, 0, 834, 441);
        ImageIcon imagen = new ImageIcon(getClass().getResource("img/generos.jpg"));
        Icon icono = new ImageIcon(
                imagen.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), Image.SCALE_DEFAULT));
        fondo.setIcon(icono);
        panelGenero.add(fondo);
    }

    private void initPanelActores() {
        panelActores = new JPanel(null);

        JButton siguiente = new JButton("Siguiente");
        siguiente.setBounds(659, 25, 150, 50);
        siguiente.addActionListener(e -> {
            guardarActores();
            if (listaGeneros.length == 0 || listaActores.length == 0) {
                JOptionPane.showMessageDialog(this, "Por favor seleccione informacion valida.", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                pelicula = consultor.recomendarPelicula(listaGeneros, listaActores);
                if (pelicula.length == 0) {
                    JOptionPane.showMessageDialog(this, "No hay recomendaciones, pruebe con otros datos.", "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    initPanelPeliculas();
                    setContentPane(panelPeliculas);
                    pack();
                }
            }
        });
        panelActores.add(siguiente);

        JButton atras = new JButton("Atras");
        atras.setBounds(659, 100, 150, 50);
        atras.addActionListener(e -> {
            setContentPane(panelGenero);
            pack();
        });
        panelActores.add(atras);

        String[] nombresColumnas = { "Actor/Actriz", "Favorito" };
        Object[][] datos = {
                { "Hugh Jackman", false },
                { "Patrick Stewart", false },
                { "Dafne Keen", false },
                { "Gerard Butler", false },
                { "Lena Headey", false },
                { "David Wenham", false },
                { "Tim Robbins", false },
                { "Morgan Freeman", false },
                { "Bob Gunton", false },
                { "Marlon Brando", false },
                { "Al Pacino", false },
                { "Robert Duvall", false },
                { "Christian Bale", false },
                { "Michael Caine", false },
                { "Heath Ledger", false },
                { "Liam Neeson", false },
                { "Ralph Fiennes", false },
                { "Ben Kingsley", false } };
        ordenarDatos(datos);
        actores = new DefaultTableModel(datos, nombresColumnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1) {
                    return true;
                }
                return false;
            }
        };

        JTable tabla = new JTable(actores) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(actores);

        JLabel buscadorLabel = new JLabel("Buscar actor/actriz:");
        buscadorLabel.setBounds(25, 25, 135, 25);
        buscadorLabel.setFont(new Font("sansserif", Font.PLAIN, 15));
        panelActores.add(buscadorLabel);

        JTextField buscador = new JTextField();
        buscador.setBounds(165, 25, 469, 25);
        buscador.setFont(new Font("sansserif", Font.PLAIN, 15));
        buscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sorter.sort();
            }
        });
        panelActores.add(buscador);

        RowFilter<Object, Object> startsWithAFilter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                String row = entry.getStringValue(0).toUpperCase();
                return row.contains(buscador.getText().toUpperCase());
            }
        };
        sorter.setRowFilter(startsWithAFilter);
        tabla.setRowSorter(sorter);

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(495);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(102);
        tabla.setSelectionMode(0);

        tabla.setFont(new Font("sansserif", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(25, 75, 609, 341);
        panelActores.add(scrollPane);

        JLabel fondo = new JLabel();
        fondo.setBounds(0, 0, 834, 441);
        ImageIcon imagen = new ImageIcon(getClass().getResource("img/generos.jpg"));
        Icon icono = new ImageIcon(
                imagen.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), Image.SCALE_DEFAULT));
        fondo.setIcon(icono);
        panelActores.add(fondo);
    }

    private void initPanelPeliculas() {
        panelPeliculas = new JPanel(null);

        JButton siguiente = new JButton("Inicio");
        siguiente.setFont(new Font("sansserif", Font.PLAIN, 15));
        siguiente.setBounds(484, 366, 150, 50);
        siguiente.addActionListener(e -> {
            setContentPane(panelInicio);
            pack();
        });
        panelPeliculas.add(siguiente);

        JButton atras = new JButton("Salir");
        atras.setBounds(659, 366, 150, 50);
        atras.setFont(new Font("sansserif", Font.PLAIN, 15));
        atras.addActionListener(e -> {
            System.exit(0);
        });
        panelPeliculas.add(atras);

        String[] nombresColumnas = { "Nº", "Pelicula" };
        Object[][] datos = new Object[pelicula.length][2];
        for (int i = 0; i < pelicula.length; i++) {
            datos[i][0] = i + 1;
            datos[i][1] = pelicula[i].getTitulo();
        }
        ordenarDatos(datos);
        peliculas = new DefaultTableModel(datos, nombresColumnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(peliculas) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(peliculas);

        JLabel buscadorLabel = new JLabel("Buscar:");
        buscadorLabel.setBounds(25, 25, 75, 25);
        buscadorLabel.setFont(new Font("sansserif", Font.PLAIN, 15));
        panelPeliculas.add(buscadorLabel);

        JTextField buscador = new JTextField();
        buscador.setBounds(90, 25, 212, 25);
        buscador.setFont(new Font("sansserif", Font.PLAIN, 15));
        buscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sorter.sort();
            }
        });
        panelPeliculas.add(buscador);

        RowFilter<Object, Object> startsWithAFilter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                String row = entry.getStringValue(1).toUpperCase();
                return row.contains(buscador.getText().toUpperCase());
            }
        };
        sorter.setRowFilter(startsWithAFilter);
        tabla.setRowSorter(sorter);

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(225);
        tabla.setSelectionMode(0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        tabla.setFont(new Font("sansserif", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(25, 75, 277, 341);
        panelPeliculas.add(scrollPane);

        JLabel portada = new JLabel();
        portada.setBounds(327, 25, 159, 225);
        ImageIcon imagenP = new ImageIcon(getClass().getResource("img/interrogacion.jpg"));
        Icon iconoP = new ImageIcon(
                imagenP.getImage().getScaledInstance(portada.getWidth(), portada.getHeight(), Image.SCALE_DEFAULT));
        portada.setIcon(iconoP);
        panelPeliculas.add(portada);

        JLabel titulo = new JLabel("Titulo:");
        titulo.setFont(new Font("sansserif", Font.PLAIN, 15));
        titulo.setBounds(506, 25, 328, 25);
        panelPeliculas.add(titulo);

        JLabel duracion = new JLabel("Duracion:");
        duracion.setFont(new Font("sansserif", Font.PLAIN, 15));
        duracion.setBounds(506, 70, 328, 25);
        panelPeliculas.add(duracion);

        JLabel categoria = new JLabel("Titulo:");
        categoria.setFont(new Font("sansserif", Font.PLAIN, 15));
        categoria.setBounds(506, 115, 328, 25);
        panelPeliculas.add(categoria);

        JLabel actores = new JLabel("Actores:");
        actores.setFont(new Font("sansserif", Font.PLAIN, 15));
        actores.setBounds(506, 160, 328, 100);
        panelPeliculas.add(actores);

        JLabel generos = new JLabel("Generos:");
        generos.setFont(new Font("sansserif", Font.PLAIN, 15));
        generos.setBounds(676, 160, 328, 100);
        panelPeliculas.add(generos);

        JTextArea sinopsis = new JTextArea("Sinopsis:");
        sinopsis.setLineWrap(true);
        sinopsis.setWrapStyleWord(true);
        sinopsis.setEditable(false);
        sinopsis.setFont(new Font("sansserif", Font.PLAIN, 15));

        JScrollPane sinopsisScroll = new JScrollPane(sinopsis);
        sinopsisScroll.setBounds(327, 270, 482, 86);
        panelPeliculas.add(sinopsisScroll);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                mostrarInfoPelicula(tabla.convertRowIndexToModel(tabla.getSelectedRow()), portada, titulo, duracion,
                        categoria, actores, sinopsis, generos);
            }
        });

        JLabel fondo = new JLabel();
        fondo.setBounds(0, 0, 834, 441);
        ImageIcon imagen = new ImageIcon(getClass().getResource("img/cine.jpg"));
        Icon icono = new ImageIcon(
                imagen.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), Image.SCALE_DEFAULT));
        fondo.setIcon(icono);
        panelPeliculas.add(fondo);

        mostrarInfoPelicula(0, portada, titulo, duracion, categoria, actores, sinopsis, generos);
    }

    private void guardarGeneros() {
        int nroGeneros = 0;
        for (int i = 0; i < generos.getRowCount(); i++) {
            if ((boolean) generos.getValueAt(i, 1)) {
                nroGeneros++;
            }
        }

        listaGeneros = new String[nroGeneros];
        int indiceLista = 0;
        for (int i = 0; i < generos.getRowCount(); i++) {
            if ((boolean) generos.getValueAt(i, 1)) {
                listaGeneros[indiceLista] = (String) generos.getValueAt(i, 0);
                indiceLista++;
                generos.setValueAt(false, i, 1);
            }
        }
    }

    private void guardarActores() {
        int nroActores = 0;
        for (int i = 0; i < actores.getRowCount(); i++) {
            if ((boolean) actores.getValueAt(i, 1)) {
                nroActores++;
            }
        }

        listaActores = new String[nroActores];
        int indiceLista = 0;
        for (int i = 0; i < actores.getRowCount(); i++) {
            if ((boolean) actores.getValueAt(i, 1)) {
                listaActores[indiceLista] = (String) actores.getValueAt(i, 0);
                indiceLista++;
                actores.setValueAt(false, i, 1);
            }
        }
    }

    private void ordenarDatos(Object[][] datos) {
        Object[] aux;
        for (int i = 0; i < datos.length - 1; i++) {
            for (int j = 0; j < datos.length - i - 1; j++) {
                if (datos[j][0].toString().compareTo(datos[j + 1][0].toString()) > 0) {
                    aux = datos[j];
                    datos[j] = datos[j + 1];
                    datos[j + 1] = aux;
                }
            }
        }
    }

    private void mostrarInfoPelicula(int i, JLabel portada, JLabel titulo, JLabel duracion, JLabel categoria,
            JLabel actores, JTextArea sinopsis, JLabel generos) {
        ImageIcon imagenP = new ImageIcon(getClass().getResource("img/" + pelicula[i].getTitulo() + ".jpg"));
        Icon iconoP = new ImageIcon(
                imagenP.getImage().getScaledInstance(portada.getWidth(), portada.getHeight(), Image.SCALE_DEFAULT));
        portada.setIcon(iconoP);
        titulo.setText("Titulo: " + pelicula[i].getTitulo());
        duracion.setText("Duracion: " + pelicula[i].getDuracion() + " minutos");
        categoria.setText("Categoria: " + pelicula[i].getCategoria());
        String actoresTexto = "";
        for (int j = 0; j < pelicula[i].getActores().length; j++) {
            actoresTexto += pelicula[i].getActores()[j] + "<br>";
        }
        actores.setText("<html>Actores:<br>" + actoresTexto + "</html>");
        sinopsis.setText("Sinopsis: " + pelicula[i].getSinopsis());
        String generosTexto = "";
        for (int j = 0; j < pelicula[i].getGeneros().length; j++) {
            generosTexto += pelicula[i].getGeneros()[j] + "<br>";
        }
        generos.setText("<html>Generos:<br>" + generosTexto + "</html>");
    }
}