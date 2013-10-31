package org.javahispano.javacup.tacticas.jvc2013.enavas;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class DatosPartido {
	
	public boolean tengoPelota(GameSituations sp){
		for(int i:sp.iterationsToKick()){
        	if(i!=0)return true;
		}	
		return false;
	}
	
	public boolean rivalTienePelota(GameSituations sp){
		for(int i:sp.rivalIterationsToKick()){
        	if(i!=0)return true;
		}	
		return false;
	}
	
	public boolean sacamos(GameSituations sp){
		if(sp.ballPosition().getX()==0&&sp.ballPosition().getY()==0&&sp.isStarts()){
			return true;
		}
		return false;
	}
	
	public boolean sacan(GameSituations sp){
		if(sp.ballPosition().getX()==0&&sp.ballPosition().getY()==0&&sp.isRivalStarts()){
			return true;
		}
		return false;
	}
	
	public boolean estaEnCampoPropio(GameSituations sp){
		if(sp.ballPosition().getX()<=0){
			return true;
		}
		return false;
	}
	
	public boolean estaEnCampoContrario(GameSituations sp){
		if(sp.ballPosition().getX()>0){
			return true;
		}
		return false;
	}

	public double calculaPotencia(GameSituations sp, Position origen, Position destino) {
		double distancia = origen.distance(destino);
		double potencia;
		if(distancia>35){
			potencia= 1;
		}else if(distancia<=35&&distancia>=29.9){
			potencia= 0.80;
		}else if(distancia<29.9&&distancia>=24.8){
			potencia=0.70;
		}else if(distancia<24.8&&distancia>=19.7){
			potencia=0.60;
		}else if(distancia<19.7&&distancia>=14.6){
			potencia=0.50;
		}else if(distancia<14.6&&distancia>=9.5){
			potencia=0.40;
		}else if(distancia<9.5&&distancia>=4.4){
			potencia=0.30;
		}else if(distancia<4.4&&distancia>=0.3){
			potencia=0.20;
		}else{
			potencia=0.10;
		}
		
		if(sp.isStarts()&&potencia<0.40){
			return potencia*2;
		}else{
			return potencia;
		}
		
	}
	
	public Position posicionarPortero(GameSituations sp) {
		// 
        Position posBalon = sp.ballPosition();
                
        Position posPortero;
        if((posBalon.getX()>=(-Constants.ANCHO_AREA_CHICA)/2)&&(posBalon.getX()<=(Constants.ANCHO_AREA_CHICA)/2)&&(posBalon.getY()<=((-Constants.LARGO_CAMPO_JUEGO/2)+Constants.LARGO_AREA_CHICA))){
        	posPortero=new Position(posBalon.getX(), -Constants.LARGO_CAMPO_JUEGO/2);
        	posPortero=posBalon;
        }else if((posBalon.getX()>=(-Constants.ANCHO_AREA_GRANDE)/2)&&(posBalon.getX()<=(Constants.ANCHO_AREA_GRANDE)/2)&&(posBalon.getY()<=((-Constants.LARGO_CAMPO_JUEGO/2)+Constants.LARGO_AREA_GRANDE))){
        	posPortero=new Position((posBalon.getX()*Constants.ANCHO_AREA_GRANDE/2)/(Constants.ANCHO_AREA_GRANDE/2), -Constants.LARGO_CAMPO_JUEGO/2);
        }else if(posBalon.getX()<-15){
        	posPortero=sp.ballPosition();
        }else{
        	posPortero=new Position((posBalon.getX()*Constants.LARGO_ARCO/2)/(Constants.ANCHO_CAMPO_JUEGO/2), -Constants.LARGO_CAMPO_JUEGO/2);
        }
        return posPortero;
	}
	
}
