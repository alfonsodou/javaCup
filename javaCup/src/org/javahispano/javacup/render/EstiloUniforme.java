package org.javahispano.javacup.render;

/**Enumeracion de estilos de vestimenta de jugadores, uso interno*/
public enum EstiloUniforme {

    /**Polera con una franja Ancha Horizontal*/
    FRANJA_HORIZONTAL(1,"Franja Horizontal"),
    /**Polera con una franja Ancha Vertical*/
    FRANJA_VERTICAL(2,"Franja Vertical"),
    /**Polera con una franja Diagonal*/
    FRANJA_DIAGONAL(3,"Franja Diagonal"),
    /**Polera con lineas horizontales*/
    LINEAS_HORIZONTALES(4,"Lineas Horizontales"),
    /**Polera con lineas verticales*/
    LINEAS_VERTICALES(5,"Lineas Verticales"),
    /**Polera de un solo color*/
    SIN_ESTILO(6,"Sin Estilo");
    int numero;
    String nombre;

    /**esblece el numero y nombre del estilo*/
    EstiloUniforme(int numero,String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    /**Retorna el nombre del estilo*/
    @Override
    public String toString() {
        return nombre;
    }

    /**Retorna el numero asociado, para cargar los recursos*/
    public int getNumero() {
        return numero;
    }
}

