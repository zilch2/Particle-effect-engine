package ParticlesEngine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class ParticleEffectApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        int width = 800;
        int height = 600;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Emitter emitter = new Emitter(width / 2.0, height / 2.0, 10, 300); // Longer TTL
        Obstacle obstacle = new Obstacle(width * 0.75, height / 2.0, 50); // Invisible field

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, width, height, Color.BLACK);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, width, height);

                // Emit and update particles
                emitter.emit();
                emitter.getParticles().removeIf(p -> {
                    p.update(width, height, obstacle);
                    return !p.isAlive();
                });

                // Draw particles
                for (Particle p : emitter.getParticles()) {
                    p.draw(gc);
                }

                // Draw the obstacle
                obstacle.draw(gc);
            }
        }.start();

        primaryStage.setTitle("Particle Effect Engine with Obstacle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}