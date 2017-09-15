
public class Planet {
	private final String Planet = null;
	double myXPos;            // current x position
	double myYPos;            // current y position
	double myXVel;            // current velocity in x direction 
	double myYVel;            // current velocity in y direction
	double myMass;            // mass of planet
	String myFileName;        // file name (in images folder)
	
	public Planet(double xp, double yp, double xv, 
			double yv, double mass, String filename) {
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;

	}

	public Planet(Planet p) {
		this.myXPos = p.myXPos;
		this.myYPos = p.myYPos;
		this.myXVel = p.myXVel;
		this.myYVel = p.myYVel;
		this.myMass = p.myMass;
		this.myFileName = p.myFileName;
		
		
	}
	
	public void draw(){
		StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
	}

	public double calcDistance(Planet otherplanet) {
		double x1 = this.myXPos;
		double y1 = this.myYPos;
		double x2 = otherplanet.myXPos;
		double y2 = otherplanet.myYPos;

		double r = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		return r;
	}

	public double calcForceExertedBy(Planet otherplanet) {
		double G = 6.67 * Math.pow(10,-11);
		double m1 = this.myMass;
		double m2 = otherplanet.myMass;
		double r = this.calcDistance(otherplanet);

		double F = (G * m1 * m2) / Math.pow(r,2);
		return F;
	}

	public double calcForceExertedByX(Planet otherplanet) {
		double F = this.calcForceExertedBy(otherplanet);
		double r = this.calcDistance(otherplanet);
		double dx = otherplanet.myXPos - this.myXPos;

		double Fx = (F * dx) / r;
		return Fx;
	}

	public double calcForceExertedByY(Planet otherplanet) {
		double F = this.calcForceExertedBy(otherplanet);
		double r = this.calcDistance(otherplanet);
		double dy = otherplanet.myYPos - this.myYPos;

		double Fy = (F * dy) / r;
		return Fy;
	}

	public double calcNetForceExertedByX(Planet[] planets) {
		double sum = 0;
		for (Planet p : planets) {
			if (!p.equals(this)) {
				sum += calcForceExertedByX(p);
			}
		}
		return sum;
	}
	
	public double calcNetForceExertedByY(Planet[] planets) {
		double sum = 0;
		for (Planet p : planets) {
			if (!p.equals(this)) {
				sum += calcForceExertedByY(p);
			}
		}
		return sum;
	}

	public void update(double seconds, double xforce, double yforce) {
		double aX = xforce / this.myMass;
		double aY = yforce / this.myMass;

		this.myXVel = this.myXVel + (aX * seconds);
		this.myYVel = this.myYVel + (aY * seconds);

		this.myXPos = this.myXPos + (this.myXVel * seconds);
		this.myYPos = this.myYPos + (this.myYVel * seconds);
		
	}
	

}

