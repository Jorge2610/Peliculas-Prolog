pelicula('Logan',['Accion','Ciencia ficcion'], ['137','R','Han pasado los años y los mutantes están en declive. Logan, débil y cansado, vive alejado de todos hasta que acepta una última misión de Charles Xavier: la de proteger a una joven especial, de nombre Laura Kinney pero conocida como X-23, la última esperanza de la raza mutante.'],['Hugh Jackman', 'Patrick Stewart', 'Dafne Keen']).

pelicula('300',['Accion', 'Fantasia'],['117','R','En el año 480 antes de Cristo, existe un estado de guerra entre Persia, dirigidos por el Rey Xerxes (Rodrigo Santoro), y Grecia. En la Batalla de Thermopylae, Leonidas (Gerard Butler), rey de la ciudad griega de Esparta, encabeza a sus autonombrados guerreros en contra del numeroso ejército persa. A pesar de que la muerte aguarda a los espartanos, su sacrificio inspira a toda Grecia para unirla en contra de su enemigo común.'],['Gerard Butler','Lena Headey','David Wenham']).

pelicula('Sueño de fuga',['Drama','Crimen'], ['142','R','Andrew Dufresne es un hombre inocente que es acusado del asesinato de su mujer. Tras ser condenado a cadena perpetua, es enviado a la cárcel de Shawshank, en Maine. Con el paso de los años, Andrew conseguirá ganarse la confianza del director del centro y el respeto de los otros convictos, especialmente de Red, el jefe de la mafia.'],['Tim Robbins','Morgan Freeman','Bob Gunton']).

pelicula('El padrino',['Drama','Crimen'],['177','R','Don Vito Corleone es el respetado y temido jefe de una de las cinco familias de la mafia de Nueva York en los años 40. El hombre tiene cuatro hijos: Connie, Sonny, Fredo y Michael, que no quiere saber nada de los negocios sucios de su padre. Cuando otro capo, Sollozzo, intenta asesinar a Corleone, empieza una cruenta lucha entre los distintos clanes.'],['Marlon Brando','Al Pacino','Robert Duvall']).

pelicula('Batman el caballero de la noche',['Acción','Crimen','Drama'],['152','PG-13','Con la ayuda del teniente Jim Gordon y del Fiscal del Distrito Harvey Dent, Batman mantiene a raya el crimen organizado en Gotham. Todo cambia cuando aparece el Joker, un nuevo criminal que desencadena el caos y tiene aterrados a los ciudadanos.'],['Christian Bale','Michael Caine','Heath Ledger']).

pelicula('La lista de Schindler',['Cine bélico','Cine biográfico','Drama'],['195','S/D','El empresario alemán Oskar Schindler, miembro del Partido Nazi, pone en marcha un elaborado, costoso y arriesgado plan para salvar a más de mil judíos del Holocausto.'],['Liam Neeson','Ralph Fiennes','Ben Kingsley']).

pelicula('El señor de los anillos El retorno del rey',['Aventuras','Fantasía épica'],['263','PG-13','La batalla por la Tierra Media ha empezado. Las fuerzas de Saruman han sido destruidas y por primera vez parece que hay una pequeña esperanza. Sin embargo, el poder de Sauron crece y su interés se centra en Gondor, el último reducto de los hombres, cuyo heredero es Aragorn. Mientras, Frodo y Sam, guiados por Gollum, siguen su peligrosa misión a través de Mordor para destruir el anillo.'],['Elijah Wood','Ian McKellen','Liv Tyler']).

pelicula('Pulp Fiction',['Comedia negra','Crimen'],['154','R','La vida de un boxeador, dos sicarios, la esposa de un gánster y dos bandidos se entrelaza en una historia de violencia y redención.'],['John Travolta','Samuel L. Jackson','Uma Thurman']).

pelicula('El señor de los anillos La comunidad del anillo',['Aventuras','Fantasía épica'],['228','PG-13','En la Tierra Media, el Señor Oscuro Sauron forjó los Grandes Anillos del Poder y creó uno con el poder de esclavizar a toda la Tierra Media. Frodo Bolsón es un hobbit al que su tío Bilbo hace portador del poderoso Anillo Único con la misión de destruirlo. Acompañado de sus amigos, Frodo emprende un viaje hacia Mordor, el único lugar donde el anillo puede ser destruido. Sin embargo, Sauron ordena la persecución del grupo para recuperar el anillo y acabar con la Tierra Media.'],['Elijah Wood','Ian McKellen','Liv Tyler']).

pelicula('Forrest Gump',['Comedia','Drama'],['142','S/D','Sentado en un banco en Savannah, Georgia, Forrest Gump espera al autobús. Mientras éste tarda en llegar, el joven cuenta su vida a las personas que se sientan a esperar con él. Aunque sufre un pequeño retraso mental, esto no le impide hacer cosas maravillosas. Sin entender del todo lo que sucede a su alrededor, Forrest toma partido en los eventos más importantes de la historia de los Estados Unidos.'],['Tom Hanks','Robin Wright','Gary Sinise']).

pelicula('El club de la lucha',['Drama','Comedia negra','Suspenso'],['139','R','Un empleado de oficina insomne, harto de su vida, se cruza con un vendedor peculiar. Ambos crean un club de lucha clandestino como forma de terapia y, poco a poco, la organización crece y sus objetivos toman otro rumbo.'],['Edward Norton','Brad Pitt','Helena Bonham Carter']).

recomendar_pelicula(X,Y,Z,A,GU,AU):-pelicula(X,Y,Z,A),pelicula_por_genero(X,GU,AP),pelicula_por_actor(AP,AU).

recomendar_por_genero(X,Y,Z,A,GU):-pelicula(X,Y,Z,A),pelicula_por_genero(X,GU,_).

recomendar_por_actor(X,Y,Z,A,AU):-pelicula(X,Y,Z,A),pelicula_por_actor(A,AU).

pelicula_por_genero(X,Y,A):-pelicula(X,Z,_,A),buscar_genero_lista(Y,Z).

buscar_genero_lista([H|T],Z):-buscar_genero(H,Z);buscar_genero_lista(T,Z).

buscar_genero(X,[H|T]):-X==H;buscar_genero(X,T).

pelicula_por_actor([H|T],AU):-buscar_actor(H,AU);pelicula_por_actor(T,AU).

buscar_actor(X,[H|T]):-X==H;buscar_actor(X,T).


