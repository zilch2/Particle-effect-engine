package ParticlesEngine;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystemManager {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final List<Particle> particles;
    private final Emitter emitter;
    private final Obstacle obstacle;
    private boolean parallelMode;

    public ParticleSystemManager(Pane root, double width, double height) {
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        this.particles = new ArrayList<>();
        this.emitter = new Emitter(width / 2, height / 2, 10, 300); // Longer TTL
        this.obstacle = new Obstacle(width * 0.75, height / 2.0, 50); // Invisible field
        this.parallelMode = false;

        root.getChildren().add(canvas);
    }

    public void start() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (parallelMode) {
                    updateParticlesParallel();
                } else {
                    updateParticlesSequential();
                }
                drawParticles();
            }
        };
        timer.start();
    }

    private void updateParticlesSequential() {
        emitter.emit();
        particles.addAll(emitter.getParticles());
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update(canvas.getWidth(), canvas.getHeight(), obstacle);
            if (!particle.isAlive()) {
                iterator.remove();
            }
        }
    }

    private void updateParticlesParallel() {
        emitter.emit();
        particles.addAll(emitter.getParticles());
        particles.parallelStream().forEach(p -> p.update(canvas.getWidth(), canvas.getHeight(), obstacle));
        particles.removeIf(p -> !p.isAlive());
    }

    private void drawParticles() {
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw each particle
        System.out.println("Drawing " + particles.size() + " particles.");
        for (Particle particle : particles) {
            particle.draw(gc);
        }

        // Draw the obstacle
        obstacle.draw(gc);
    }

    public void resizeCanvas(double width, double height) {
        canvas.setWidth(width);
        canvas.setHeight(height);
    }
}
