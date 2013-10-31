package org.javahispano.javacup.tacticas.jvc2013.txami;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class TacticDetailImpl implements TacticDetail {

    public String getTacticName() {
        return "The Txami";
    }

    public String getCountry() {
        return "Jamaica";
    }

    public String getCoach() {
        return "Iker";
    }

    public Color getShirtColor() {
        return new Color(153, 0, 0);
    }

    public Color getShortsColor() {
        return new Color(0, 0, 0);
    }

    public Color getShirtLineColor() {
        return new Color(255, 255, 255);
    }

    public Color getSocksColor() {
        return new Color(153, 153, 153);
    }

    public Color getGoalKeeper() {
        return new Color(255, 255, 51        );
    }

    public EstiloUniforme getStyle() {
        return EstiloUniforme.LINEAS_VERTICALES;
    }

    public Color getShirtColor2() {
        return new Color(0, 153, 0);
    }

    public Color getShortsColor2() {
        return new Color(255, 255, 255);
    }

    public Color getShirtLineColor2() {
        return new Color(153, 153, 153);
    }

    public Color getSocksColor2() {
        return new Color(153, 0, 0);
    }

    public Color getGoalKeeper2() {
        return new Color(255, 255, 0        );
    }

    public EstiloUniforme getStyle2() {
        return EstiloUniforme.SIN_ESTILO;
    }

    class JugadorImpl implements PlayerDetail {

        String nombre;
        int numero;
        Color piel, pelo;
        double velocidad, remate, presicion;
        boolean portero;
        Position Position;

        public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                double velocidad, double remate, double presicion, boolean portero) {
            this.nombre=nombre;
            this.numero=numero;
            this.piel=piel;
            this.pelo=pelo;
            this.velocidad=velocidad;
            this.remate=remate;
            this.presicion=presicion;
            this.portero=portero;
        }

        public String getPlayerName() {
            return nombre;
        }

        public Color getSkinColor() {
            return piel;
        }

        public Color getHairColor() {
            return pelo;
        }

        public int getNumber() {
            return numero;
        }

        public boolean isGoalKeeper() {
            return portero;
        }

        public double getSpeed() {
            return velocidad;
        }

        public double getPower() {
            return remate;
        }

        public double getPrecision() {
            return presicion;
        }

    }

    public PlayerDetail[] getPlayers() {
        return new PlayerDetail[]{
            new JugadorImpl("Sagu", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
            new JugadorImpl("Bombo", 2, new Color(255,200,150), new Color(50,0,0),0.85d,0.75d,0.5d, false),
            new JugadorImpl("Pepi", 3, new Color(255,200,150), new Color(50,0,0),0.85d,0.75d,0.5d, false),
            new JugadorImpl("Llano", 4, new Color(255,200,150), new Color(50,0,0),0.8d,0.75d,0.9d, false),
            new JugadorImpl("Pitxe", 5, new Color(255,200,150), new Color(50,0,0),0.8d,0.75d,0.9d, false),
            new JugadorImpl("Piti", 6, new Color(255,200,150), new Color(50,0,0),0.7d,0.5d,1.0d, false),
            new JugadorImpl("Piki", 7, new Color(255,200,150), new Color(50,0,0),0.7d,0.5d,1.0d, false),
            new JugadorImpl("Gutxo", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,1.0d, false),
            new JugadorImpl("Saez", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,1.0d, false),
            new JugadorImpl("Kuik", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
            new JugadorImpl("Largui", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1d,1.0d, true)
        };
    }
}

