package ParticlesEngine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {
    private double x, y;
    private double dx, dy;
    private int ttl;
    private double size;
    private Color color;

    public Particle(double x, double y, double speed, double angle, int ttl, double size, Color color) {
        this.x = x;
        this.y = y;
        this.dx = speed * Math.cos(angle);
        this.dy = speed * Math.sin(angle);
        this.ttl = ttl;
        this.size = size;
        this.color = color;
    }

    public void update(double width, double height, Obstacle obstacle) {
        x += dx;
        y += dy;

        // Apply gravity
        dy += 0.01; // Lower gravity

        // Bounce off edges
        if (x < 0 || x > width) dx *= -1;
        if (y < 0 || y > height) {
            dy *= -0.8;
            y = Math.min(y, height); // Prevent sinking
        }

        // Check collision with the obstacle
        if (obstacle.isWithinField(x, y)) {
            double[] bounceVector = obstacle.getBounceVector(x, y, dx, dy);
            dx = bounceVector[0];
            dy = bounceVector[1];
        }

        // Decrease lifespan
        ttl--;
    }

    public boolean isAlive() {
        return ttl > 0;
    }

    public void draw(GraphicsContext gc) {
        double alpha = Math.max(0, (double) ttl / 300.0); // Fading effect, longer lifespan
        gc.setFill(color.deriveColor(0, 1, 1, alpha));
        gc.fillOval(x, y, size, size);
        System.out.println("Drawing particle at (" + x + ", " + y + ")"); // Debugging
    }
}