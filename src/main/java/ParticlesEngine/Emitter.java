package ParticlesEngine;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Emitter {
    private double x, y;
    private int particlesPerEmission;
    private int ttl;
    private List<Particle> particles;

    public Emitter(double x, double y, int particlesPerEmission, int ttl) {
        this.x = x;
        this.y = y;
        this.particlesPerEmission = particlesPerEmission;
        this.ttl = ttl;
        this.particles = new ArrayList<>();
    }

    public void emit() {
        for (int i = 0; i < particlesPerEmission; i++) {
            double speed = Math.random() * 5 + 1; // Random speed
            double angle = Math.toRadians(-15 + Math.random() * 30); // Cone spread
            Particle particle = new Particle(x, y, speed, angle, ttl, 2, Color.BLUE);
            particles.add(particle); // Add to internal particle list
        }
        System.out.println("Particles emitted: " + particles.size()); // Debugging
    }

    public List<Particle> getParticles() {
        return particles;
    }
}