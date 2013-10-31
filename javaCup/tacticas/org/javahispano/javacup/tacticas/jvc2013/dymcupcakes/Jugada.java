/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2013.dymcupcakes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author Mauricio
 */
public class Jugada {

    Position alineacion1[] = new Position[]{
        new Position(0.951048951048951, -49.64932126696832),
        new Position(-19.020979020979023, -31.59502262443439),
        new Position(0.7132867132867133, -28.50678733031674),
        new Position(19.25874125874126, -31.59502262443439),
        new Position(1.6643356643356644, -7.126696832579185),
        new Position(-15.692307692307693, -7.364253393665159),
        new Position(-23.3006993006993, 11.877828054298643),
        new Position(17.594405594405593, 12.115384615384617),
        new Position(-26.867132867132867, 35.8710407239819),
        new Position(-5.468531468531468, 16.628959276018097),
        new Position(-1.188811188811189, 40.38461538461539)
    };
    
    Position alineacion2[] = new Position[]{
        new Position(0.951048951048951, -49.64932126696832),
        new Position(-19.020979020979023, -31.59502262443439),
        new Position(0.7132867132867133, -28.50678733031674),
        new Position(19.25874125874126, -31.59502262443439),
        new Position(1.6643356643356644, -7.126696832579185),
        new Position(-15.692307692307693, -7.364253393665159),
        new Position(-23.3006993006993, 11.877828054298643),
        new Position(17.594405594405593, 12.115384615384617),
        new Position(-26.867132867132867, 35.8710407239819),
        new Position(-5.468531468531468, 16.628959276018097),
        new Position(-1.188811188811189, 40.38461538461539)
    };
    
    public double isProtecting(Position p){
        double toArco=Constants.centroArcoInf.distance(p);
        double toBaloon=baloon().distance(p);
        double total=Constants.centroArcoInf.distance(baloon());
        return total<50?toBaloon*-0.4:0;
    }
    
    
    
    public List<Position> createOffenssivePositions(){
        List<Position> l=new ArrayList<Position>();
        double total=Constants.centroArcoSup.distance(baloon());
        if(total<60) {
            l.add(Position.medium(baloon(),Constants.centroArcoSup));
            l.add(Position.medium(baloon(),Constants.posteDerArcoSup));
            l.add(Position.medium(baloon(),Constants.posteIzqArcoSup));
        }
        return l;
    }
    
    public List<Position> createDefensivePositions(){
        List<Position> l=new ArrayList<Position>();
        double total=Constants.centroArcoInf.distance(baloon());
        if(total<40) {
            l.add(Position.medium(baloon(),Constants.centroArcoInf));
            l.add(Position.medium(baloon(),Constants.posteDerArcoInf));
            l.add(Position.medium(baloon(),Constants.posteIzqArcoInf));
        }
        return l;
    }
    
    public double evaluate(Position pos){
        double e=0d;
        double ar= Constants.centroArcoSup.distance(pos);
        double ap= Constants.centroArcoInf.distance(pos);
        
        Position baloon=baloon();
        
        for(int i=0;i<11;i++){
            
            Position j=position(i);
            Position r=rival(i);
            
            
            double dr=r.distance(pos);
            double br=baloon.distance(r);
            double dj=j.distance(pos);
            double lineality=(dr+br-baloon.distance(pos));
            double sr=sp.getRivalPlayerSpeed(i);
            double sj=sp.getMyPlayerSpeed(i);
            e=e+(0.2*sj*dj)-(0.3*sr*dr)+0.1*emptyness(pos)+(0.25*(lineality>0?lineality:0));
        }
        return e-0.5*ar+0.3*ap+0.5*pos.getY();
    }
    
    public Position best(List<Position> alt){
        Position best=null;
        for(Position p:alt){
            if(best!=null){
                if(evaluate(p)>evaluate(best)) best=p;
            }else{
                best=p;
            }
        }
        return best;
    }
    
    
    
    protected List<Command> cmds = new ArrayList<Command>();
    protected List<Integer> involved = new ArrayList<Integer>();
    protected GameSituations sp;

    public boolean involve(int j) {
        return involved.contains(j);
    }

    public void addPlayer(int j) {
        involved.add(j);
    }

    public void update(GameSituations sp) {
        this.sp = sp;
        trayectoria();
        status=new int[11];
    }

    public boolean apply() {
        return true;
    }

    public List<Command> commands() {
        return cmds;
    }

    private Position[] ballp=new Position[100];
    private double[] ballz=new double[100];
    
    public void trayectoria(){
        
        for(int i=0;i<100;i++){
            double[]t=sp.getTrajectory(i);
            ballp[i]=new Position(t[0],t[1]);
            ballz[i]=t[2];
        }
        
    }
    
   
    public void kick(int i) {
        List<Position> posiciones=new ArrayList<Position>();
        posiciones.addAll(Arrays.asList(alineacion1));
        posiciones.addAll(Arrays.asList(alineacion2));
        
        
        posiciones.add(Constants.centroCampoJuego);
        posiciones.add(Constants.cornerSupDer);
        posiciones.add(Constants.cornerSupIzq);
        
        posiciones.add(Constants.cornerInfDer);
        posiciones.add(Constants.cornerInfIzq);
        
        posiciones.add(Constants.posteDerArcoSup);
        posiciones.add(Constants.posteIzqArcoSup);
                
        posiciones.add(Constants.posteDerArcoInf);
        posiciones.add(Constants.posteIzqArcoInf);
        
        posiciones.addAll(createDefensivePositions());
        posiciones.addAll(createOffenssivePositions());
        
        posiciones.addAll(tri(posiciones));
        
        posiciones.addAll(Arrays.asList(sp.myPlayers()));
        posiciones.addAll(Arrays.asList(sp.rivalPlayers()));
        
        
        kick(i,best(posiciones));
        
         
    }

    public List<Position> tri(List<Position> pos){
        List<Position> tri=new ArrayList<Position>();
        
        for(Position x:pos){
            Position r=null;
            Position p=null;    
            for(int i=0;i<11;i++){
                Position pp=position(i);
                Position rr=rival(i);
                
                if(r!=null){
                    if(r.distance(x)>rr.distance(x))r=rr;
                }else{
                    r=rr;
                }
                if(p!=null){
                    if(p.distance(x)>pp.distance(x))p=pp;
                }else{
                    p=pp;
                }
                
                
            }
            double px=(2*p.getX()+r.getX()+x.getX())/4d;
            double py=(2*p.getY()+r.getY()+x.getY())/4d;
            tri.add(new Position(px,py));
        }
        return tri;
    }
    
    public double distBalon(int j) {
        return position(j).distance(baloon());
    }

    public void tiroAlArco(int i) {
        double f=position(i).distance(Constants.centroArcoSup)/20l;
        cmds.add(new CommandHitBall(i, Constants.centroArcoSup, f, 30d));
    }
    
    public void kick(int i, Position dest){
        if(dest==null) return;
        Position source = position(i);
        
        
        double power = source.distance(dest) / 25d;
        System.out.println("power:" + power);
        cmds.add(new CommandHitBall(i, dest, power, power>2.5?true:false));
    }

    public void tiro(int i, Position dest){
        kick(i,dest);
    }
    
    public void pase(int i, int j) {
        
        Position dest = position(j);
        kick(i,dest);
        
    }

    public Position baloon() {
        return sp.ballPosition();
    }

    public void followBalon(int i) {
        Position p=baloon();
        double control=i==0?Constants.ALTO_ARCO:Constants.ALTURA_CONTROL_BALON;
        for(int j=0;j<100;j++){
            if(ballz[j]<=control) {
                p=ballp[j];
                break;
                
            }
        }
        
        cmds.add(new CommandMoveTo(i, p));
    }
    
    public void followPlayer(int i, int j){
        if(position(j).getY()>-30){
            move(i,position(j).movePosition(0, -10));
        }else{
            move(i,position(j));
        }
    }

    public void backPosition(int i) {
        move(i,alineacion1[i]);
    }
    
    public Position position(int i){
        return sp.myPlayers()[i];
    }
    
    public Position rival(int i){
        return sp.rivalPlayers()[i];
    }
    
    public void move(int i, Position dest){
        if(isProtecting(dest)>40)
            cmds.add(new CommandMoveTo(i, dest,true));
           else
            cmds.add(new CommandMoveTo(i, dest,true));
    }

    public boolean canKick(int i) { 
        double control=i==0?Constants.ALTO_ARCO:Constants.ALTURA_CONTROL_BALON;
        if (position(i).distance(baloon()) < Constants.DISTANCIA_CONTROL_BALON && sp.ballAltitude()<=control) {
            return true;
        }
        return false;
    }
    
    private int status[]=new int[11];
    
    public boolean evaluate(int i){
        
        double threshold=i==0?30:20;
        boolean c=distBalon(i)<threshold;
        if(c) status[i]=1;
        return c;
    }
    
    public int rivalNear(int j){
        int p=0;
        for(int i=0;i<11;i++){
            if(rival(i).distance(position(j))<rival(p).distance(position(j))) p=j;
        }
        return p;
    }
    
    
    public int near(Position pos){
        int p=10;
        for(int j:involved){
            if(j==0)continue;
            if(pos.distance(position(j))<pos.distance(position(p)))p=j;
        }
        return p;
    }
    public void checkStatus(){
        boolean ok=false;
        for(int j:involved){
            if(status[j]==1){
                ok=true;
                break;
            }
        }
        if(!ok){
            move(near(baloon()),baloon());
        }
        
        
        
        
    }

    public void move(int i) {
        if (evaluate(i)) {
            followBalon(i);
        } 
        else {
            move(i,evaluateMove(i));
            
            
        }
    }
    
    public boolean marcado(int i){
        for(int j=0;j<11;j++){
            if(position(i).distance(rival(j))<15){
                return true;
            }
        }
        return false;
    }
    
    public double emptyness(Position p){
        double d=1;
        for(int j=0;j<11;j++){
            d+=rival(j).distance(p);
            d+=position(j).distance(p);
        }
        return d;
    }
    
    public double evaluateMove(Position p,int j){
        double d=0;
        d=d+0.01*emptyness(p);
        d=d-0.6*baloon().distance(p);
        d=d+0.3*p.distance(alineacion1[j])*(j+1);
        
        d=d+0.3*(isProtecting(p)*j);
        
        boolean result=(sp.myGoals()-sp.rivalGoals())<0;
        d=d+0.2*(result?p.distance(Constants.centroArcoSup):0);
        
        double f=rival(rivalNear(j)).distance(Constants.centroArcoInf)>50?-1.5:1;
        d=d+0.4*f*rival(rivalNear(j)).distance(p);
        d=d-2*(j==0?p.distance(Constants.centroArcoInf):0);
        return d;
    }
    
    
    public Position bestMove(List<Position> alt,int j){
        Position best=null;
        for(Position p:alt){
            if(best!=null){
                if(evaluateMove(p,j)>evaluateMove(best,j)) best=p;
            }else{
                best=p;
            }
        }
        return best;
    }
    
    public Position evaluateMove(int i){
        List<Position> pos=new ArrayList<Position>();
        Position p=rival(rivalNear(i));
        Position b=baloon();
        pos.add(p);
        pos.add(b);
        pos.addAll(Arrays.asList(alineacion1));
        
        pos.addAll(createDefensivePositions());
        
        pos.addAll(createOffenssivePositions());
        
        pos.add(Constants.centroCampoJuego);
        pos.add(Constants.cornerSupDer);
        pos.add(Constants.cornerSupIzq);
        
        pos.add(Constants.cornerInfDer);
        pos.add(Constants.cornerInfIzq);
        
        pos.add(Constants.posteDerArcoSup);
        pos.add(Constants.posteIzqArcoSup);
                
        pos.add(Constants.posteDerArcoInf);
        pos.add(Constants.posteIzqArcoInf);
        pos.addAll(Arrays.asList(alineacion2));
        pos.addAll(tri(pos));
        pos.addAll(Arrays.asList(ballp));
        
        return bestMove(pos,i);
    }
    
    
    
    public void desmarca(int i){
        Position pos=rival(0);
        for(int j=0;j<11;j++){
            if(pos.distance(position(i))>rival(j).distance(position(i))){
                pos=rival(j);
            }
        }
        move(i,pos.movePosition(5, 20));
    }
    
    public void marca(int i,int j){
        move(i,sp.rivalPlayers()[j]);
    }
    
    

    
    public static class Default extends Jugada {

        @Override
        public List<Command> commands() {
            cmds.clear();

            for (int i : involved) {
                if (canKick(i)) {
                    kick(i);
                } else {
                    move(i);
                }
            }
            checkStatus();

            return cmds;
        }
    }

    
}
