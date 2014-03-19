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
	private byte[] matchBin;
	private int goalsLocal;
	private int goalsVisiting;
	private double posessionLocal;
	private int state;
	private long[] timeLocal;
	private long[] timeVisita;
	
	public MatchShared() {
		this.match = null;
		this.matchBin = null;
		this.goalsLocal = 0;
		this.goalsVisiting = 0;
		this.posessionLocal = 0;
		this.state = 0;
		this.timeLocal = null;
		this.timeVisita = null;
	}

	/**
	 * @return the timeLocal
	 */
	public long[] getTimeLocal() {
		return timeLocal;
	}

	/**
	 * @param timeLocal the timeLocal to set
	 */
	public void setTimeLocal(long[] timeLocal) {
		this.timeLocal = timeLocal;
	}

	/**
	 * @return the timeVisita
	 */
	public long[] getTimeVisita() {
		return timeVisita;
	}

	/**
	 * @param timeVisita the timeVisita to set
	 */
	public void setTimeVisita(long[] timeVisita) {
		this.timeVisita = timeVisita;
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

	/**
	 * @return the matchBin
	 */
	public byte[] getMatchBin() {
		return matchBin;
	}

	/**
	 * @param matchBin the matchBin to set
	 */
	public void setMatchBin(byte[] matchBin) {
		this.matchBin = matchBin;
	}
	
	
} 
