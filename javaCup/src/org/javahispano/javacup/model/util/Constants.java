package org.javahispano.javacup.model.util;

/**Constants*/
public final class Constants {

    //##############################################################################//
    //###### Dimensiones del campo de juego (en metros) #######//
    //###################################################//
    /**Largo del campo donde se realiza el juego*/
    public static final double LARGO_CAMPO_JUEGO = 105;
    /**Ancho del campo donde se realiza el juego*/
    public static final double ANCHO_CAMPO_JUEGO = 68;
    /**Largo total campo (cesped)*/
    public static final double LARGO_CAMPO = 113;
    /**Ancho total campo (cesped)*/
    public static final double ANCHO_CAMPO = 73;
    /**Amplificacion del  tamaÃ±o arco*/
    public static final double AMP_ARCO = 1.2;
    /**Largo del arco*/
    public static final double LARGO_ARCO = 7.32 * AMP_ARCO;
    /**Altura del arco*/
    public static final double ALTO_ARCO = 5;
    /**Largo del area grande*/
    public static final double LARGO_AREA_GRANDE = 40.32;
    /**Ancho del area grande*/
    public static final double ANCHO_AREA_GRANDE = 16.5;
    /**Distancia desde el punto penal al arco*/
    public static final double DISTANCIA_PENAL = 11;
    /**Radio del circulo penal que sale fuera del area grande*/
    public static final double RADIO_PENAL = 9.15;
    /**Largo del ara chica*/
    public static final double LARGO_AREA_CHICA = 18.32;
    /**Ancho del area chica*/
    public static final double ANCHO_AREA_CHICA = 5.5;
    /**Radio del circulo central*/
    public static final double RADIO_CIRCULO_CENTRAL = 9.15;
    //##############################################################################//
    //#### Otras Dimesiones ####//
    //########################//
    /**Separacion minima entre jugadores*/
    public static final double JUGADORES_SEPARACION = 0.75;
    /**Altura maxima en que  los jugadores pueden controlar el balon,
     * solo el portero puede controlar hasta ALTO_ARCO*/
    public static final double ALTURA_CONTROL_BALON = 3;
    /**Distancia maxima en que  los jugadores pueden controlar el balon*/
    public static final double DISTANCIA_CONTROL_BALON = 1;
    /**Distancia maxima en que el portero puede controlar el balon*/
    public static final double DISTANCIA_CONTROL_BALON_PORTERO = 1.2;
    //#############################################################################//
    //##### Otros Parametros  ###//
    //########################//
    /**Escala por defecto en pixeles por metro*/
    public static final double ESCALA = 15;
    /**Cantidad de iteraciones que se realizan durante un partido*/
    public static final int ITERACIONES = 3600;
    /**Creditos iniciales por equipo*/
    public static final double CREDITOS_INICIALES = 27.5;
    /**Frames por segundo por defecto*/
    public static final int FPS = 20;
    /**Delay entre frames*/
    public static final int DELAY = (int) (1000d / (double) FPS);
    //##############################################################################################
    /**Velocidad manima de remate (metros/iteracion)*/
    public static final double REMATE_VELOCIDAD_MIN = 1.2;
    /**Velocidad maxima de remate (metros/iteracion)*/
    public static final double REMATE_VELOCIDAD_MAX = 2.4;
    /**Velocidad minima de un jugador (metros/iteracion)*/
    public static final double VELOCIDAD_MIN = 0.25;
    /**Velocidad maxima de un jugador (metros/iteracion)*/
    public static final double VELOCIDAD_MAX = 0.5;
    /**Porcentaje de error angular minimo de un jugador*/
    public static final double ERROR_MIN = 0.1;
    /**Porcentaje de error angular maximo de un jugador*/
    public static final double ERROR_MAX = 0.3;
    /**Tasa de incremento y decremento de la energia de un jugador**/
    public  static final double ENERGIA_DIFF = 0.00001;
    /**Nivel minimo al que puede bajar la energia de un jugador**/
    public  static final double ENERGIA_MIN = 0.55;
    /**Factor de influencia de la energia en la potencia de disparo**/
    public static final double ENERGIA_DISPARO = 1.25;
    /**Aceleración minima de un jugador al hacer un cambio de direccion en el eje Y**/
    public static final double ACELERACION_MINIMA_Y = 0.70;
    /**Aceleración minima de un jugador al hacer un cambio de direccion en el eje X**/
    public static final double ACELERACION_MINIMA_X = 0.9;
    /**Incremento de la aceleracion en cada iteraccion si no se cambia de direccion**/
    public static final double ACELERACION_INCR = 0.04;
    /**Incremento de la velocidad en sprint**/
    public static final double SPRINT_ACEL = 1.2;
    /**Energia minima para poder ir en sprint**/
    public static final double SPRINT_ENERGIA_MIN = 0.8;
    /**Energia extra consumida por ir en sprint**/
    public static final double SPRINT_ENERGIA_EXTRA = 0.02;
    //##############################################################################################
    /**Amplificacion de la velocidad entregada por la trayectoria*/
    public static final double AMPLIFICA_VEL_TRAYECTORIA = 20;
    /**angle por defecto usado al rematar por alto*/
    public static final double ANGULO_VERTICAL = 30;
    /**angle vertical maximo*/
    public static final double ANGULO_VERTICAL_MAX = 60;
    @Deprecated
    /**No se usa*/
    public static final double FACTOR_DISMINUCION_VEL_BALON_AIRE = 0.97;
    /**No se usa*/
    @Deprecated
    public static final double FACTOR_DISMINUCION_VEL_BALON_SUELO = 0.93;
    /**No se usa*/
    @Deprecated
    public static final double FACTOR_DISMINUCION_ALTURA_BALON_REBOTE = 0.6;
    /**No se usa*/
    @Deprecated
    public static final double G = 0.12;
    //#######################################################
    /**Parametro para configurar como la camara sigue la posicion del balon, mientra mayor sea mas suave seran
     * los movimientos de la camara*/
    public static final double SEGUIMIENTO_CAMARA = 20;
    /**Cantidad de iteraciones que tienen que pasar para que un mismo jugador vuelva a rematar*/
    public static final int ITERACIONES_GOLPEAR_BALON = 10;
    /**Cantidad maxima de iteraciones para sacar, si se sobrepasan el equipo contrario sacara*/
    public static final int ITERACIONES_SAQUE = 100;
    /**radio del balon, para calcular los rebotes en el poste*/
    public static final double RADIO_BALON = .3;
    /**Distancia minima que puede estar un jugador rival mientras se realiza un saque*/
    public static final double DISTANCIA_SAQUE = 15;
    //##############################//
    //###########   Posiciones   #######//
    //##############################//
    /**esquina superior izquierda del circulo central*/
    public static final Position esqSupIzqCircCentral = new Position(-Constants.RADIO_CIRCULO_CENTRAL, -Constants.RADIO_CIRCULO_CENTRAL);
    /**Esquina superior izquierda del campo (zona de pasto)*/
    public static final Position esqSupIzqCampo = new Position(-Constants.ANCHO_CAMPO / 2.0, -Constants.LARGO_CAMPO / 2.0);
    /**Dimensiones del campo (zona de pasto)*/
    public static final Position dimCampo = new Position(Constants.ANCHO_CAMPO, Constants.LARGO_CAMPO);
    /**Esquina superior izquierda del campo de juego*/
    public static final Position esqSupIzqCampoJuego = new Position(-Constants.ANCHO_CAMPO_JUEGO / 2.0, -Constants.LARGO_CAMPO_JUEGO / 2.0);
    /**Dimensiones del campo de juego*/
    public static final Position dimCampoJuego = new Position(Constants.ANCHO_CAMPO_JUEGO, Constants.LARGO_CAMPO_JUEGO);
    /**Esquina superior izquierda del area grande superior*/
    public static final Position esqSupIzqAreaGrandeSup = new Position(-Constants.LARGO_AREA_GRANDE / 2, -Constants.LARGO_CAMPO_JUEGO / 2);
    /**Esquina superior izquierda del area grande inferior*/
    public static final Position esqSupIzqAreaGrandeInf = new Position(-Constants.LARGO_AREA_GRANDE / 2, Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_GRANDE);
    /**dimensiones del area grande*/
    public static final Position dimAreaGrande = new Position(Constants.LARGO_AREA_GRANDE, Constants.ANCHO_AREA_GRANDE);
    /**Esquina superior izquierda del area chica superior*/
    public static final Position esqSupIzqAreaChicaSup = new Position(-Constants.LARGO_AREA_CHICA / 2, -Constants.LARGO_CAMPO_JUEGO / 2);
    /**Esquina superior izquierda del area chica inferior*/
    public static final Position esqSupIzqAreaChicaInf = new Position(-Constants.LARGO_AREA_CHICA / 2, Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_CHICA);
    /**Dimensiones del area chica*/
    public static final Position dimAreaChica = new Position(Constants.LARGO_AREA_CHICA, Constants.ANCHO_AREA_CHICA);
    /**Position del punto penal superior*/
    public static final Position penalInf = new Position(0, -Constants.LARGO_CAMPO_JUEGO / 2 + Constants.DISTANCIA_PENAL);
    /**Position del punto penal Inferior*/
    public static final Position penalSup = penalInf.getInvertedPosition();
    /**Esquina superior izquierda del circulo penal superior*/
    public static final Position esqSupIzqPenalSup = new Position(-Constants.RADIO_PENAL, -Constants.LARGO_CAMPO_JUEGO / 2 + Constants.DISTANCIA_PENAL - Constants.RADIO_PENAL);
    /**Esquina superior izquierda del circulo penal inferior*/
    public static final Position esqSupDerPenalInf = new Position(-Constants.RADIO_PENAL, Constants.LARGO_CAMPO_JUEGO / 2 - Constants.DISTANCIA_PENAL - Constants.RADIO_PENAL);
    /**Poste izquierdo del arco superior*/
    public static final Position posteIzqArcoInf = new Position(-Constants.LARGO_ARCO / 2, -Constants.LARGO_CAMPO_JUEGO / 2);
    /**Poste izquierdo del arco inferior*/
    public static final Position posteIzqArcoSup = new Position(-Constants.LARGO_ARCO / 2, Constants.LARGO_CAMPO_JUEGO / 2);
    /**Poste derecho del arco superior*/
    public static final Position posteDerArcoInf = new Position(Constants.LARGO_ARCO / 2, -Constants.LARGO_CAMPO_JUEGO / 2);
    /**Poste derecho del arco inferior*/
    public static final Position posteDerArcoSup = new Position(Constants.LARGO_ARCO / 2, Constants.LARGO_CAMPO_JUEGO / 2);
    /**Position que indica el centro de la cancha*/
    public static final Position centroCampoJuego = new Position(0, 0);
    /**Position que indica el centro del arco superior*/
    public static final Position centroArcoInf = new Position(0, -Constants.LARGO_CAMPO_JUEGO / 2);
    /**Position que indica el centro del arco inferior*/
    public static final Position centroArcoSup = new Position(0, Constants.LARGO_CAMPO_JUEGO / 2);
    /**Corner superior izquierdo*/
    public static final Position cornerInfIzq = new Position(esqSupIzqCampoJuego);
    /**Corner superior derecho*/
    public static final Position cornerInfDer = esqSupIzqCampoJuego.movePosition(Constants.ANCHO_CAMPO_JUEGO, 0);
    /**Corner inferior izquierdo*/
    public static final Position cornerSupIzq = esqSupIzqCampoJuego.movePosition(0, Constants.LARGO_CAMPO_JUEGO);
    /**Corner inferior derecho*/
    public static final Position cornerSupDer = cornerSupIzq.movePosition(Constants.ANCHO_CAMPO_JUEGO, 0);
    //################################################################################
    /**Diferencia entre maxima y mininima velocidad de remate*/
    public static double DELTA_REMATE_VELOCIDAD = REMATE_VELOCIDAD_MAX - REMATE_VELOCIDAD_MIN;
    /**Diferencia entre maxima y mininima velocidad de jugadores*/
    private static final double DELTA_VELOCIDAD = VELOCIDAD_MAX - VELOCIDAD_MIN;
    /**Diferencia entre maximo y mininimo error angular*/
    private static final double DELTA_ERROR = ERROR_MAX - ERROR_MIN;

    /**Retorna la velocidad de remate (en [metros/iteracion]) dado un factor[0..1];*/
    public static double getVelocidadRemate(double factor) {
        return REMATE_VELOCIDAD_MIN + (DELTA_REMATE_VELOCIDAD) * factor;
    }

    /**Retorna la velocidad de un jugador (en [metros/iteracion]) dado un factor[0..1];*/
    public static double getVelocidad(double factor) {
        return VELOCIDAD_MIN + (DELTA_VELOCIDAD) * factor;
    }

    /**Retorna el error angular (en [radianes]) dado un factor[0..1];*/
    public static double getErrorAngular(double factor) {
        return ERROR_MAX - (DELTA_ERROR) * factor;
    }
}
