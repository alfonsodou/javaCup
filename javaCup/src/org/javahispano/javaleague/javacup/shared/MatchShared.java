/**
 * 
 */
package org.javahispano.javaleague.javacup.shared;

/**
 * @author adou
 *
 */
public class MatchShared {

	private byte[] match;
	private int goalsLocal;
	private int goalsVisiting;
	private double posessionLocal;
	private int state;
	
	public MatchShared() {
		this.match = null;
		this.goalsLocal = 0;
		this.goalsVisiting = 0;
		this.posessionLocal = 0;
		this.state = 0;
	}

	/**
	 * @return the match
	 */
	public byte[] getMatch() {
		return match;
	}

	/**
	 * @param match the match to set
	 */
	public void setMatch(byte[] match) {
		this.match = match;
	}

	/**
	 * @return the goalsLocal
	 */
	public int getGoalsLocal() {
		return goalsLocal;
	}

	/**
	 * @param goalsLocal the goalsLocal to set
	 */
	public void setGoalsLocal(int goalsLocal) {
		this.goalsLocal = goalsLocal;
	}

	/**
	 * @return the goalsVisiting
	 */
	public int getGoalsVisiting() {
		return goalsVisiting;
	}

	/**
	 * @param goalsVisiting the goalsVisiting to set
	 */
	public void setGoalsVisiting(int goalsVisiting) {
		this.goalsVisiting = goalsVisiting;
	}

	/**
	 * @return the posessionLocal
	 */
	public double getPosessionLocal() {
		return posessionLocal;
	}

	/**
	 * @param posessionLocal the posessionLocal to set
	 */
	public void setPosessionLocal(double posessionLocal) {
		this.posessionLocal = posessionLocal;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	
} 
