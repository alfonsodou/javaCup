package org.javahispano.javacup.applet;
/**
*
* @author Viruz
*/
public class CronometroThread implements Runnable {
   private Thread hiloCronometro;
   private boolean go,live;
   private int segundos;
   private int hr;
   private int min;
   private int seg;

   public CronometroThread(int seg) {
       segundos = seg;
   }

   public void run() {
       try {
           while (isLive()) {
               synchronized(this) {
                   while (!isGo())
                       wait();
               }
               Thread.sleep(1000);
               segundos--;
               actualizarThread();
           }
       } catch (InterruptedException e) {}
   }

   public void createThread() {
       hiloCronometro = new Thread(this);
       hiloCronometro.start();
   }

   private void actualizarThread() {
       if (isLive() == true) {
           hr= segundos/3600;
           min =(segundos-hr*3600)/60;
           seg = segundos-hr*3600-min*60;
       } else {
           segundos = 0;
       }
   }

   public void suspenderThread() {
       setGo(false);
   }

   public synchronized void continuarThread() {
       setGo(true);
       notify();
   }

   //********** MÉTODOS SET Y GET DE LAS VARIABLES DE TIPO BOOLEAN e INT ************
   /**
    * @return the live
    */
   public boolean isLive() {
       return live;
   }

   /**
    * @param live the live to set
    */
   public void setLive(boolean live) {
       this.live = live;
   }

   /**
    * @return the go
    */
   public boolean isGo() {
       return go;
   }

   /**
    * @param go the go to set
    */
   public void setGo(boolean go) {
       this.go = go;
   }

   /**
    * @return the segundos
    */
   public int getSegundos() {
       return segundos;
   }

   /**
    * @param segundos the segundos to set
    */
   public void setSegundos(int segundos) {
       this.segundos = segundos;
       System.out.println("Valor de SEgundos:" + this.segundos);
   }

public int getHr() {
	return hr;
}

public void setHr(int hr) {
	this.hr = hr;
}

public int getMin() {
	return min;
}

public void setMin(int min) {
	this.min = min;
}

public int getSeg() {
	return seg;
}

public void setSeg(int seg) {
	this.seg = seg;
}
   
   
}
