package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.Command;

public class CentreBack extends Player {

	CentreBack(LinkedList<Command> comandos, int a_index) {
		super(comandos, a_index);
	}
	
	void execute()
	{
		super.execute();
		
		if( !m_haveCommand )
		{
			
		}
	}
}
