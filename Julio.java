package Unibratec;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.HitByBulletEvent;
import robocode.*;
import java.awt.*;

/**
 * Julio - a robot by (your name here)
 */
public class Julio extends Robot
{

	boolean alvo_acertado = false;
	boolean alvo_perdido = false;	

	public void run() {
		
		//Definem as cores do robô	
		setBodyColor(Color.black);
		setGunColor(Color.blue);
		setRadarColor(Color.red);
		setScanColor(Color.yellow);
	
		// Robot main loop
		while(true) {		
			
			turnRadarRight(360);
			if (alvo_perdido == true) {
				turnLeft(80);
				ahead(40);
				alvo_perdido = false;
			}
							
			turnGunRight(360);
		}
	}
	
    //Obtem as coordenadas para ajustar o canhão
    public void miraCanhao(double PosIni, double energiaIni, double minhaEnergia) {
       double Distancia = PosIni;
	   double Coordenadas = getHeading() + PosIni - getGunHeading();
	   double PontoQuarenta = (energiaIni / 4) + .1;
	   if (!(Coordenadas > -180 && Coordenadas <= 180)) {
	      while (Coordenadas <= -180) {
		     Coordenadas += 360;
		  }
		  while (Coordenadas > 180) {
		     Coordenadas -= 360;
		  }
	   }
	   turnGunRight(Coordenadas);
		
	   if (Distancia > 200 || minhaEnergia < 15 || energiaIni > minhaEnergia){
          fire(1);
       } else if (Distancia > 50 ) {
          fire(2);
       } else {
          fire(PontoQuarenta);
       }
    }
	
	// Quando é detectado outro robô
	public void onScannedRobot(ScannedRobotEvent e) {
			
	      double max = 100;
		
		  //Controla da energia que é gasta quanto à potência do tiro
	      if(e.getEnergy() < max){
	         max = e.getEnergy();
	         miraCanhao(e.getBearing(), max, getEnergy());
	      }else if(e.getEnergy() >= max){
	         max = e.getEnergy();
	         miraCanhao(e.getBearing(), max, getEnergy());
	      }else if(getOthers() == 1){
	         max = e.getEnergy();
	         miraCanhao(e.getBearing(), max, getEnergy());
	      }
	}
	
    //Quando o robô colide com outro robô
    public void onHitRobot(HitRobotEvent e) {
	   tiroFatal(e.getBearing(), e.getEnergy(), getEnergy());	
	
    }

    //Quando o robô é atingido 
	public void onHitByBullet(HitByBulletEvent e) {
	   turnLeft(90 - e.getBearing());
	   back(100); 
	}
	
	//Quando o robô se choca contra a parede
	public void onHitWall(HitWallEvent e) {		
		back(40);
		turnLeft(80);
		ahead(40);
	}
	
    // Quando o robô atinge outro robô
	public void onBulletHit(BulletHitEvent event) {
		alvo_acertado = true;
	}
	
	//Dança da vitória
	public void onWin(WinEvent e) {	
	      turnRight(72000);
	}
	
	public void tiroFatal(double PosIni, double energiaIni, double minhaEnergia) {
	      double Distancia = PosIni;
		  double Coordenadas = getHeading() + PosIni - getGunHeading();
		  double PontoQuarenta = (energiaIni / 4) + .1;
		  if (!(Coordenadas > -180 && Coordenadas <= 180)) {
		     while (Coordenadas <= -180) {
		        Coordenadas += 360;
			 }
			 while (Coordenadas > 180) {
		        Coordenadas -= 360;
		     }
		  }
		  turnGunRight(Coordenadas);
		  fire(PontoQuarenta);	       
	}	
}
