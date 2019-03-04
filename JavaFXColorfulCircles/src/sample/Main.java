package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import static java.lang.Math.random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        Group circles = new Group();
        for(int i=0; i<30; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }
        // 设置模糊效果
        circles.setEffect(new BoxBlur(10, 10, 3));
        // 设置线性渐变[stops是渐变值]
        Stop[] stops = new Stop[] {
                new Stop(0.00, Color.web("#f8bd55")),
                new Stop(0.14, Color.web("#c0fe56")),
                new Stop(0.28, Color.web("#5dfbc1")),
                new Stop(0.43, Color.web("#64c2f8")),
                new Stop(0.57, Color.web("#be4af7")),
                new Stop(0.71, Color.web("#ed5fc2")),
                new Stop(0.85, Color.web("#ef504c")),
                new Stop(1.00, Color.web("#f2660f")),
        };
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, stops));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());
        // 效果1. 添加渐变色, 先注释掉这2行
//        root.getChildren().add(colors);
//        root.getChildren().add(circles);

        // 效果2. 添加蒙版混合模式和动画效果
        Group blendModeGroup = new Group(new Group(
                new Rectangle(scene.getWidth(), scene.getHeight(),
                        Color.BLACK), circles), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);

        // 由时间轴来控制添加动画
        Timeline animationTimeLine = new Timeline();
        for (Node circle: circles.getChildren()) {
            // 在0和40s处添加2个关键帧，来移动和随机改变圆圈的位置
            animationTimeLine.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set animation at 0s
                        new KeyValue(circle.translateXProperty(), random() * 800),
                        new KeyValue(circle.translateYProperty(), random() * 600)
                    ),
                    new KeyFrame(new Duration(40 * 1000), // set animation at 40s
                            new KeyValue(circle.translateXProperty(), random() * 800),
                            new KeyValue(circle.translateYProperty(), random() * 600)
                    )
            );
        }
        animationTimeLine.play();

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
