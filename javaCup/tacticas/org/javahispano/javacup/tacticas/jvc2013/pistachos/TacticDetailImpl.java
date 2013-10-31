package org.javahispano.javacup.tacticas.jvc2013.pistachos;


import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Los Pistachos";
        }

        public String getCountry() {
            return "Argentina";
        }

        public String getCoach() {
            return "Hernan Marzullo";
        }

        public Color getShirtColor() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor() {
            return new Color(51, 51, 255);
        }

        public Color getShirtLineColor() {
            return new Color(0, 51, 255);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 255);
        }

        public Color getGoalKeeper() {
            return new Color(51, 51, 255        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.FRANJA_HORIZONTAL;
        }

        public Color getShirtColor2() {
            return new Color(51, 51, 255);
        }

        public Color getShortsColor2() {
            return new Color(255, 255, 255);
        }

        public Color getShirtLineColor2() {
            return new Color(255, 255, 255);
        }

        public Color getSocksColor2() {
            return new Color(0, 51, 255);
        }

        public Color getGoalKeeper2() {
            return new Color(153, 153, 0        );
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.FRANJA_HORIZONTAL;
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
                new JugadorImpl("Monetti", 1, new Color(255,200,150), new Color(50,0,0),0.97d,0.5d,0.92d, true),
                new JugadorImpl("Schiavi", 2, new Color(255,200,150), new Color(50,0,0),0.5d,0.86d,0.5d, false),
                new JugadorImpl("Mac Allister", 3, new Color(255,200,150), new Color(50,0,0),0.95d,0.9d,0.72d, false),
                new JugadorImpl("Negro Ibarra", 4, new Color(0,0,0), new Color(50,0,0),0.97d,0.9d,0.79d, false),
                new JugadorImpl("Ferchu Gago", 5, new Color(255,200,150), new Color(50,0,0),0.85d,0.5d,0.98d, false),
                new JugadorImpl("Pirata Zornomas", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.59d,0.58d, false),
                new JugadorImpl("Stafuza", 7, new Color(255,200,150), new Color(50,0,0),0.88d,0.92d,0.5d, false),
                new JugadorImpl("Pepe Basualdo", 8, new Color(255,200,150), new Color(50,0,0),0.88d,0.9d,0.56d, false),
                new JugadorImpl("El Bati", 9, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Diegote", 10, new Color(255,200,150), new Color(50,0,0),0.88d,1.0d,1.0d, false),
                new JugadorImpl("Messi", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)
            };
        }
    }

    
    