package org.javahispano.javacup.tacticas.jvc2013.espinete;

import org.javahispano.javacup.model.util.Position;

class Alineaciones {
    
    private int alinActual;
    
    private static final Position[] sacaElContrario=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(6.895104895104895,-34.44570135746606),
        new Position(-0.23776223776223776,-31.357466063348415),
        new Position(12.601398601398602,-35.8710407239819),
        new Position(-16.88111888111888,-35.63348416289593),
        new Position(-5.706293706293707,-32.07013574660634),
        new Position(-19.734265734265733,-1.1877828054298643),
        new Position(-12.748251748251748,-1.0),
        new Position(0,-9.2500002),
        new Position(12.174825174825173,-1.0)
    };
    
    private static final  Position[] sacarDeCentro=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-6.895104895104895,-34.68325791855204),
        new Position(5.944055944055944,-33.970588235294116),
        new Position(-0.23776223776223776,-30.16968325791855),
        new Position(-9.272727272727272,-22.092760180995477),
        new Position(-1.902097902097902,-2.8506787330316743),
        new Position(0.951048951048951,-18.529411764705884),
        new Position(-11.412587412587413,-0.47511312217194573),
        new Position(2.6153846153846154,-2.8506787330316743),
        new Position(4.9930069930069925,-9.027149321266968),
        new Position(0.23776223776223776,-0.56)
    };

    private static final Position[] estatico=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.174825174825173,-30.15837104072398),
        new Position(11.412587412587413,-29.68325791855204),
        new Position(-0.4755244755244755,-26.119909502262445),
        new Position(0.7132867132867133,-24.092760180995477),
        new Position(7.608391608391608,13.778280542986426),
        new Position(-15.454545454545453,23.28054298642534),
        new Position(0.23776223776223776,-12.352941176470589),
        new Position(0.4755244755244755,27.556561085972852),
        new Position(25.874125874125873,-7.4253393665158371),
        new Position(-25.20979020979021,-7.9389140271493215)
    };
    
    private static final Position[] masAdelantados=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-13.076923076923078,-24.230769230769234),
        new Position(0.2595419847328244,-31.082089552238806),
        new Position(12.363636363636363,-27.794117647058822),
        new Position(4.27972027972028,-15.678733031674208),
        new Position(-10.6993006993007,-7.601809954751132),
        new Position(11.888111888111888,-2.1380090497737556),
        new Position(-7.846153846153847,18.054298642533936),
        new Position(-17.356643356643357,28.269230769230766),
        new Position(4.9930069930069925,21.61764705882353),
        new Position(14.503496503496503,31.357466063348415)
    };
    
    private static final Position[] masAdelantados2=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-13.076923076923078,-14.230769230769234),
        new Position(0.2595419847328244,-21.082089552238806),
        new Position(12.363636363636363,-17.794117647058822),
        new Position(4.27972027972028,-15.678733031674208),
        new Position(-10.6993006993007,-7.601809954751132),
        new Position(11.888111888111888,-2.1380090497737556),
        new Position(-7.846153846153847,18.054298642533936),
        new Position(-17.356643356643357,28.269230769230766),
        new Position(4.9930069930069925,21.61764705882353),
        new Position(14.503496503496503,31.357466063348415)
    };
    
    private static final Position[] masAdelantados3=new Position[]{
        new Position(0.2595419847328244,-45.41044776119403),
        new Position(-13.076923076923078,-4.230769230769234),
        new Position(0.2595419847328244,-11.082089552238806),
        new Position(12.363636363636363,-7.794117647058822),
        new Position(4.27972027972028,-5.678733031674208),
        new Position(-10.6993006993007,-4.601809954751132),
        new Position(11.888111888111888,2.1380090497737556),
        new Position(-7.846153846153847,28.054298642533936),
        new Position(-17.356643356643357,30.269230769230766),
        new Position(4.9930069930069925,26.61764705882353),
        new Position(14.503496503496503,35.357466063348415)
    };
    
    private static final Position[] masAdelantados4=new Position[]{
        new Position(0.2595419847328244,-40.41044776119403),
        new Position(-13.076923076923078,-1.230769230769234),
        new Position(0.2595419847328244,-5.082089552238806),
        new Position(12.363636363636363,-2.794117647058822),
        new Position(4.27972027972028,-0.678733031674208),
        new Position(-10.6993006993007,1.601809954751132),
        new Position(11.888111888111888,4.1380090497737556),
        new Position(-7.846153846153847,18.054298642533936),
        new Position(-17.356643356643357,32.269230769230766),
        new Position(4.9930069930069925,29.61764705882353),
        new Position(14.503496503496503,25.357466063348415)
    };
    
    private static final Position[][] alinRoundRobin = {sacaElContrario, sacarDeCentro, 
                                                        estatico, masAdelantados, 
                                                        masAdelantados2, masAdelantados3, masAdelantados4};
    
    public Alineaciones() {
        alinActual = 0;
    }
    
    public Position[] sacanEllos() {
        alinActual = 0;
        return alinRoundRobin[0];
    }
    
    public Position[] sacoYo() {
        alinActual = 1;
        return alinRoundRobin[1];
    }
    
    public Position[] dameAlineacion(int iteraciones, int tirosPuerta, int difGoles, boolean golContrario) {
        if ((iteraciones % 240 == 0) && (tirosPuerta < (iteraciones / 240)) && 
            (alinActual < (alinRoundRobin.length - 1)) && (difGoles <= 1)) {
            
            alinActual++;
            System.out.println("alineacion: " + alinActual);
        } else if ((iteraciones % 240 == 0) && (tirosPuerta < (iteraciones / 240)) && 
            (alinActual == (alinRoundRobin.length - 1))) {
            alinActual = 3;
        } else if (golContrario && (difGoles <= 0)) {
            alinActual++;
            System.out.println("alineacion: " + alinActual);
        } 
        
        if ((alinActual >= alinRoundRobin.length) || (alinActual < 2)) {
            alinActual = 2;
            System.out.println("alineacion: " + alinActual);
        }
        
        return alinRoundRobin[alinActual];
        
    }
    
    
}
