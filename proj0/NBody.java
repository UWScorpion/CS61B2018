public class NBody{

	public static double readRadius(String text){
		In in = new In(text);
		in.readInt();
		return in.readDouble();
	}
	public static Planet[] readPlanets(String text){
		In in = new In(text);
		int len = in.readInt();
		in.readDouble();
		Planet[] Allplanets = new Planet[len];
		for (int i = 0 ; i < len; i++){
			Allplanets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
								in.readDouble(), in.readDouble(), in.readString());
		}
		return Allplanets;
	}
	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] Allplanets = readPlanets(filename);
		String imageToDraw = "images/starfield.jpg";
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, imageToDraw);
		for(Planet p: Allplanets){
			p.draw();
		}
		StdDraw.show();
		StdDraw.enableDoubleBuffering();
		double t = 0;
		int len = Allplanets.length;
		double[] xForces = new double[len];
		double[] yForces = new double[len];
		while(t <= T){
			for (int i = 0; i < len; i++){
				xForces[i] = Allplanets[i].calcNetForceExertedByX(Allplanets);
				yForces[i] = Allplanets[i].calcNetForceExertedByY(Allplanets);
			}
			for (int i = 0; i < len; i++){
				Allplanets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet p : Allplanets) {
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			t += dt;
		}
		StdOut.printf("%d\n", len);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < len; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  Allplanets[i].xxPos, Allplanets[i].yyPos, Allplanets[i].xxVel,
                  Allplanets[i].yyVel, Allplanets[i].mass, Allplanets[i].imgFileName);   
		}
	}
}