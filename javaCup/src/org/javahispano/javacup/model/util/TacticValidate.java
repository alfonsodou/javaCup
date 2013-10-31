package org.javahispano.javacup.model.util;

import java.util.HashSet;

import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.render.EstiloUniforme;

/**Clase usada para validar una tactica*/
public final class TacticValidate {

    /**Clase para Validar implementaciones tactica*/
    public static void validateDetail(String name, TacticDetail t) throws Exception {
        if (t == null) {
            throw new Exception(name + "TacticDetail null");
        }
        if (t.getSocksColor() == null ||
                t.getShirtColor() == null ||
                t.getShirtLineColor() == null ||
                t.getShortsColor() == null ||
                t.getGoalKeeper() == null) {
            throw new Exception(name + "TacticDetail: ColorCalcetas , ColorCamiseta , ColorFranja , ColorPantalon o ColorPortero es nulo");
        }
        if (t.getStyle() == null) {
            throw new Exception(name + "TacticDetail: estilo es nulo");
        }
        if (t.getCoach() == null || t.getTacticName() == null || t.getCountry() == null) {
            throw new Exception(name + "TacticDetail: Entrenador, Nombre o Pais nulo");
        }
        if (t.getCoach().trim().length() == 0 ||
                t.getTacticName().trim().length() == 0 ||
                t.getCountry().trim().length() == 0) {
            throw new Exception(name + "TacticDetail: Entrenador, Nombre o Pais vacio");
        }
        if (t.getPlayers() == null) {
            throw new Exception(name + "TacticDetail: Jugadores nulo");
        }

        if (t.getPlayers().length != 11) {
            throw new Exception(name + "TacticDetail: Cantidad de Jugadores distinto de 11");
        }

        int porteros = 0;
        double creditos = 0;
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < 11; i++) {
            if (t.getPlayers()[i] == null) {
                throw new Exception(name + "TacticDetail: Jugador[" + i + "] nulo");
            }
            if (t.getPlayers()[i].getHairColor() == null || t.getPlayers()[i].getSkinColor() == null) {
                throw new Exception(name + "TacticDetail: Jugador[" + i + "] ColorPiel o ColorPelo nulo");
            }
            if (t.getPlayers()[i].getPlayerName() == null) {
                throw new Exception(name + "TacticDetail: Jugador[" + i + "] Nombre nulo");
            }
            if (t.getPlayers()[i].getNumber() <= 0 || t.getPlayers()[i].getNumber() > 99) {
                throw new Exception(name + "TacticDetail: Jugador[" + i + "] Numero fuera del rango [1,99]");
            }
            if (set.contains(t.getPlayers()[i].getNumber())) {
                throw new Exception(name + "TacticDetail: Numero de jugador " + t.getPlayers()[i].getNumber() + " repetido");
            }
            set.add(t.getPlayers()[i].getNumber());
            if (t.getPlayers()[i].getPrecision() < 0 || t.getPlayers()[i].getPrecision() > 1) {
                throw new Exception(name + "TacticDetail: Jugador[" + i + "] con presicion fuera del rango [0,1]");
            }
            if (t.getPlayers()[i].getPower() < 0 || t.getPlayers()[i].getPower() > 1) {
                throw new Exception(name + "TacticDetail: Jugador[" + i + "] con remate fuera del rango [0,1]");
            }
            if (t.getPlayers()[i].getSpeed() < 0 || t.getPlayers()[i].getSpeed() > 1) {
                throw new Exception(name + "TacticDetail: Jugador[" + i + "] con velocidad fuera del rango [0,1]");
            }
            creditos = creditos + t.getPlayers()[i].getPrecision();
            creditos = creditos + t.getPlayers()[i].getSpeed();
            creditos = creditos + t.getPlayers()[i].getPower();
            if (t.getPlayers()[i].isGoalKeeper()) {
                porteros++;
            }
        }
        creditos = (int) (creditos * 10000) / 10000d;
        if (porteros != 1) {
            throw new Exception(name + "TacticDetail: Cantidad porteros distinto de 1");
        }
        if (creditos > Constants.CREDITOS_INICIALES) {
            throw new Exception(name + "TacticDetail: Uso " + creditos + " creditos, pero son permitidos " + Constants.CREDITOS_INICIALES);
        }
        if (equalsColors(t)) {
            //throw new Exception(nombre + "TacticDetail: modificar colores de la camiseta, muy parecidos al uniforme alternativo ");
        }
    }

    /**Valida una alineacion para el saque y para la recepcion*/
    public static Position[][] validatePositions(String name, Position[] starts, Position[] noStarts) throws Exception {
        Position[] recibe0=new Position[11],saca0=new Position[11];
        for(int i=0;i<11;i++){
            if (noStarts[i] == null) {
                throw new Exception("Alineacion Recibe[" + i + "] nulo");
            }
            if (starts[i] == null) {
                throw new Exception("Alineacion Saca[" + i + "] nulo");
            }
            recibe0[i]=new Position(noStarts[i]);
            saca0[i]=new Position(starts[i]);
        }
        if (noStarts == null) {
            throw new Exception("Alineacion Recibe nulo");
        }
        if (starts == null) {
            throw new Exception("Alineacion Recibe nulo");
        }
        if (noStarts.length != 11) {
            throw new Exception("Alineacion Recibe tamaño distinto de 11");
        }
        if (starts.length != 11) {
            throw new Exception("Alineacion Saca tamaño distinto de 11");
        }
        for (int i = 0; i < 11; i++) {
            if (saca0[i].getY() > 0) {
                saca0[i] = new Position(saca0[i].getX(), 0);
            }
            if (recibe0[i].getY() > 0) {
                recibe0[i] = new Position(recibe0[i].getX(), 0);
            }
            if (recibe0[i].distance(Constants.centroCampoJuego) <= Constants.RADIO_CIRCULO_CENTRAL) {
                double ang = Constants.centroCampoJuego.angle(recibe0[i]);
                recibe0[i] = Constants.centroCampoJuego;
                recibe0[i] = recibe0[i].moveAngle(ang, Constants.RADIO_CIRCULO_CENTRAL + 1);
            }
        }
        return new Position[][]{saca0, recibe0};
    }

    private static double distancia(Color c1, Color c2) {
        return Math.sqrt((c1.getRed() - c2.getRed())*(c1.getRed() - c2.getRed()) + (c1.getGreen() - c2.getGreen())*(c1.getGreen() - c2.getGreen()) + (c1.getBlue() - c2.getBlue())*(c1.getBlue() - c2.getBlue()));
    }

    private static Color mesclarColor(Color c1, Color c2, double p1) {
        double p2 = 1d - p1;
        return new Color((int) (c1.getRed() * p1 + c2.getRed() * p2), (int) (c1.getGreen() * p1 + c2.getGreen() * p2), (int) (c1.getBlue() * p1 + c2.getBlue() * p2));
    }

    private static double getP1(EstiloUniforme est) {
        switch (est) {
            case LINEAS_HORIZONTALES:
                return .5;
            case LINEAS_VERTICALES:
                return .5;
            case FRANJA_VERTICAL:
                return .8;
            case FRANJA_HORIZONTAL:
                return .8;
            case FRANJA_DIAGONAL:
                return .8;
            case SIN_ESTILO:
                return 1;
        }
        return 0;
    }
    private static double umbral = 150;

    /**Indica true si es necesario que el equipo visita cambie a su uniforme alternativo*/
    public static boolean useAlternativeColors(TacticDetail local, TacticDetail visita) {
        Color cl1, cv1, cv2;
        cl1 = mesclarColor(local.getShirtColor(), local.getShirtLineColor(), getP1(local.getStyle()));
        cv1 = mesclarColor(visita.getShirtColor(), visita.getShirtLineColor(), getP1(visita.getStyle()));
        cv2 = mesclarColor(visita.getShirtColor2(), visita.getShirtLineColor2(), getP1(visita.getStyle2()));
        double d1=distancia(cl1, cv1);
        double d2=distancia(cl1, cv2);
        return d1 < umbral && d2 > d1;
    }

    /**Indica true si los dos unifermes de una tactica son muy parecidos*/
    public static boolean equalsColors(TacticDetail local) {
        Color cl1, cl2;
        cl1 = mesclarColor(local.getShirtColor(), local.getShirtLineColor(), getP1(local.getStyle()));
        cl2 = mesclarColor(local.getShirtColor2(), local.getShirtLineColor2(), getP1(local.getStyle2()));
        double d = distancia(cl1, cl2);
        return d < umbral;
    }
}
