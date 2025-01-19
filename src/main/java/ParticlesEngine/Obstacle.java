package ParticlesEngine;
import javafx.scene.canvas.GraphicsContext;

public class Obstacle {
    private double x, y, radius;

    public Obstacle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean isWithinField(double px, double py) {
        double dx = px - x;
        double dy = py - y;
        return (dx * dx + dy * dy) <= (radius * radius);
    }

    public double[] getBounceVector(double px, double py, double vx, double vy) {
        double dx = px - x;
        double dy = py - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize the normal vector
        dx /= distance;
        dy /= distance;

        // Reflect the velocity vector using the normal vector
        double dotProduct = vx * dx + vy * dy;
        vx -= 2 * dotProduct * dx;
        vy -= 2 * dotProduct * dy;

        return new double[] {vx, vy};
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(javafx.scene.paint.Color.BLACK);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}
