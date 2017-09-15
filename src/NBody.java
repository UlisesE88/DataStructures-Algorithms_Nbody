import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NBody {

	public static void main(String[] args){
		double totalTime = 157788000.0;
		double dt = 25000.0;
		String pfile = "data/planets.txt";

		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			pfile = args[2];
		}

		String fname= "./data/planets.txt";
		Planet[] planets = readPlanets(fname);
		double radius = readRadius(fname);

//		System.out.printf("%d\n", planets.length);
//		System.out.printf("%.2e\n", radius);
//		for (int i = 0; i < planets.length; i++) {
//			System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
//					planets[i].myXPos, planets[i].myYPos, 
//					planets[i].myXVel, planets[i].myYVel, 
//					planets[i].myMass, planets[i].myFileName);	
//		}

		StdDraw.setScale(-radius, radius); 
		StdDraw.picture(0,0,"images/starfield.jpg");
		for (int i = 0; i<planets.length; i++) {
			planets[i].draw();
		}

		//StdAudio.play("audio/2001 A Space Odyssey Theme song.wav");
		for(double t = 0.0; t < totalTime; t += dt) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for(int planet_index = 0; planet_index < planets.length; planet_index++) {
				double xForceofplanet = planets[planet_index].calcNetForceExertedByX(planets);
				double yForceofplanet = planets[planet_index].calcNetForceExertedByY(planets);
				xForces[planet_index] = xForceofplanet;
				yForces[planet_index] = yForceofplanet;
			}
			for(int updatingvalues_index = 0; updatingvalues_index< planets.length; updatingvalues_index++) {
				planets[updatingvalues_index].update(dt, xForces[updatingvalues_index], yForces[updatingvalues_index]);
			}
			StdDraw.picture(0,0,"images/starfield.jpg");
			for (int i = 0; i < planets.length; i++) {
				planets[i].draw();
			}
			StdDraw.show(10);
		}
		
		System.out.printf("%d\n", planets.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					planets[i].myXPos, planets[i].myYPos, 
					planets[i].myXVel, planets[i].myYVel, 
					planets[i].myMass, planets[i].myFileName);	
		}
	}

	public static double readRadius(String fname) {
		double radius = 0;
		try {
			Scanner scan = new Scanner(new File(fname));
			radius = scan.nextDouble();
			radius = scan.nextDouble();
			scan.close();

		}
		catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " + e.getMessage());
		}
		return radius;
	}

	public static Planet[] readPlanets(String fname) { 
		Planet[] myplanetarray = new Planet[0];
		try {
			Scanner scan = new Scanner(new File(fname));
			int num_planets = scan.nextInt();
			myplanetarray = new Planet[num_planets];			
			scan.nextLine();
			scan.nextLine();
			for (int planet_number=0; planet_number<num_planets; planet_number++) {
				double xp = scan.nextDouble();
				double yp = scan.nextDouble();
				double xv = scan.nextDouble();
				double yv = scan.nextDouble();
				double mass = scan.nextDouble();
				String filename = scan.next();
				Planet my_planet = new Planet(xp, yp, xv, yv, mass, filename);
				myplanetarray[planet_number] = my_planet;
			}
			scan.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " + e.getMessage());
		}

		return myplanetarray;
	}
}

