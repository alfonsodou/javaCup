package org.javahispano.javacup.tacticas.jvc2013.romedal;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public interface Alineaciones {

	Position		alineacion0[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-23, -32),//
			new Position(9, -36),//
			new Position(24, -33),//
			new Position(-1, -26),//
			new Position(-9, -36),//
			new Position(-17, -20),//
			new Position(14, -22),//
			new Position(-4, -2),//
			new Position(3, -13),//
			new Position(8, 10)		//
										};

	Position		alineacion1[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-23, -22),//
			new Position(9, -26),//
			new Position(24, -23),//
			new Position(-1, -16),//
			new Position(-9, -26),//
			new Position(-17, -10),//
			new Position(14, -12),//
			new Position(-4, 8),//
			new Position(3, -3),//
			new Position(8, 20)		//
										};

	Position		alineacion2[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-23, -13),//
			new Position(9, -17),//
			new Position(24, -12),//
			new Position(-0, -8),//
			new Position(-9, -17),//
			new Position(-17, -2),//
			new Position(14, -2),//
			new Position(-6, 15),//
			new Position(2, 5),//
			new Position(7, 27)		//
										};																										//

	Position		alineacion3[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-22, -4),//
			new Position(6, -10),//
			new Position(22, -5),//
			new Position(-2, 3),//
			new Position(-9, -8),//
			new Position(-17, 8),//
			new Position(14, 7),//
			new Position(-9, 23),//
			new Position(1, 15),//
			new Position(7, 32)		//
										};																										//

	Position		alineacion4[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-23, 4),//
			new Position(9, 1),//
			new Position(23, 4),//
			new Position(-1, 11),//
			new Position(-9, 1),//
			new Position(-17, 19),//
			new Position(14, 18),//
			new Position(-9, 30),//
			new Position(1, 24),//
			new Position(6, 40)		//
										};																										//

	Position		alineacion5[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-24, 4),//
			new Position(9, 1),//
			new Position(23, 4),//
			new Position(-0, 17),//
			new Position(-9, 4),//
			new Position(-17, 24),//
			new Position(14, 25),//
			new Position(-9, 39),//
			new Position(-0, 32),//
			new Position(3, 48)		//
										};																										//

	Position		alineacionSaca[]	= new Position[] {//

										new Position(0, -50),//
			new Position(-23, -32),//
			new Position(8, -34),//
			new Position(23, -32),//
			new Position(-1, -22),//
			new Position(-9, -35),//
			new Position(-15, -19),//
			new Position(15, -18),//
			new Position(-4, 2),//
			new Position(0, 0),//
			new Position(8, 13)		//
										};

	Position		alineacionRecibe[]	= new Position[] { //
										//
			new Position(0, -50),//
			new Position(-23, -32),//
			new Position(8, -34),//
			new Position(23, -32),//
			new Position(-1, -22),//
			new Position(-9, -35),//
			new Position(-15, -19),//
			new Position(15, -18),//
			new Position(-4, 2),//
			new Position(0, -1.01),//
			new Position(8, 13)		//
										};																										//

	Position[][]	alineaciones		= new Position[][] {/* alineacion0,*/ alineacion1, alineacion2, alineacion3, alineacion4, alineacion5 };

	int				seccion				= (int) Constants.LARGO_CAMPO / alineaciones.length;

}
