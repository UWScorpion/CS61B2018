import java.lang.Math;

public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	static final double G = 6.67 * Math.pow(10, -11);
	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	public double calcDistance(Planet supply){
		double distance = Math.sqrt((supply.xxPos - this.xxPos) * (supply.xxPos - this.xxPos) 
						+ (supply.yyPos - this.yyPos) * (supply.yyPos - this. yyPos));	
		return distance;
	}
	public double calcForceExertedBy(Planet given){
		double force = (G * given.mass * this.mass)/(this.calcDistance(given) * this.calcDistance(given));
		return force;
	}
	public double calcForceExertedByX(Planet given){
		double forceX = this.calcForceExertedBy(given) * (given.xxPos - this.xxPos) / this.calcDistance(given);
		return forceX;
	}
	public double calcForceExertedByY(Planet given){
		double forceY = this.calcForceExertedBy(given) * (given.yyPos - this.yyPos) / this.calcDistance(given);
		return forceY;
	}
	public double calcNetForceExertedByX(Planet[] allPlanets){
		double forceX = 0;
		for (Planet item: allPlanets){
			if (!this.equals(item)){
				forceX += this.calcForceExertedByX(item);
			}
		}
		return forceX;
	}
	public double calcNetForceExertedByY(Planet[] allPlanets){
		double forceY = 0;
		for (Planet item: allPlanets){
			if (!this.equals(item)){
				forceY += this.calcForceExertedByY(item);
			}
		}
		return forceY;
	}
	public void update (double t, double forceX, double forceY){
		double aX = forceX / this.mass;
		double aY = forceY / this.mass;
		this.xxVel += t * aX;
		this.yyVel += t * aY;
		this.xxPos += t * this.xxVel;
		this.yyPos += t * this.yyVel;
	}
	public void draw(){
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
		StdDraw.show();
	}

}