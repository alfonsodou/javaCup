package org.javahispano.javacup.tacticas.jvc2013.novena;

import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author yoemny
 */
public class Alineaciones {

    public Alineaciones() {
    }

    private static Alineacion alineaciones[] = new Alineacion[]
        {new Alineacion(new Position[]{
                new Position(-19.97202797202797,-31.83257918552036),
                new Position(-6.895104895104895,-31.357466063348415),
                new Position(6.6573426573426575,-30.6447963800905),
                new Position(19.984732824427482,-31.6044776119403),
                new Position(0.23776223776223776,-51.51131221719457),
                new Position(-3.034965034965035,-12.165158371040723),
                new Position(3.034965034965035,-12.165158371040723),
                new Position(0,10.877828054298643),
                new Position(-28.531468531468533,15.893665158371043),
                new Position(26.62937062937063,15.418552036199095),
                new Position(0,25.119909502262445)
            }),
            new Alineacion(new Position[]{
                    new Position(-15.692307692307693,-31.119909502262445),
                    new Position(-6.419580419580419,-30.88235294117647),
                    new Position(6.895104895104895,-30.407239819004527),
                    new Position(17.832167832167833,-31.59502262443439),
                    new Position(0.4755244755244755,-48.699095022624434),
                    new Position(-3.804195804195804,-16.391402714932127),
                    new Position(4.5174825174825175,-16.866515837104075),
                    new Position(0.0,-0.9502262443438915),
                    new Position(-31.384615384615387,-5.6628959276018098),
                    new Position(31.146853146853147,-5.4253393665158371),
                    new Position(-3.3286713286713288,-1.4253393665158371)
                }),
                    new Alineacion(new Position[]{
                            new Position(-15.454545454545453,-30.6447963800905),
                            new Position(-6.6573426573426575,-30.6447963800905),
                            new Position(6.6573426573426575,-30.407239819004527),
                            new Position(15.216783216783217,-30.6447963800905),
                            new Position(0.23776223776223776,-49.17420814479638),
                            new Position(-2.377622377622378,-14.96606334841629),
                            new Position(3.3286713286713288,-14.96606334841629),
                            new Position(0,-9.1500001),
                            new Position(-30.93006993006993,-5.98868778280543),
                            new Position(30.74125874125874,-5.513574660633484),
                            new Position(0,-2.16)
                        })
        };

    public static Alineacion getAlineacion(int indice){
        if (indice < alineaciones.length)
            return alineaciones[indice];
        else
            return null;
    }

}
