package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.common.TankDrive;

@Autonomous(name="Teaching: Autonomous", group="Linear OpMode")
//@Disabled
public class Autonomous_Teaching extends Teaching_BaseLinearOpMode {

    enum TurnDirection {
        CLOCKWISE,
        COUNTERCLOCKWISE
    }


    private static final long pauseTimeBetweenSteps = 100;


    @Override
    public void runOpMode() throws InterruptedException {
        Initialize(hardwareMap, false);
        setDrive(new TankDrive(robot, driverGamePad));

        double speed = 0.85;

//        Context context = hardwareMap.appContext;
//
//        Config config = new Config(context);

        waitForStart();

        Silver(speed);

    }

    void Blue(double speed) {

    }
    void Red(double speed) {

    }


    void Silver(double speed) {
        driveStraight(speed, 14, 5);
        pause();

        //turn left
        turn90(TurnDirection.COUNTERCLOCKWISE, speed);
        pause();

        driveStraight(speed, 44, 5);
        pause();

        turn45(TurnDirection.COUNTERCLOCKWISE, speed);
        pause();

        driveStraight(speed, 57, 5);
        pause();

        driveStraight(1, -86, 20);
        pause();


    }
    void Gold(double speed) {

        //drive out from lander
        driveStraight(speed, 14, 5);
        pause();

        //turn left
        turn90(TurnDirection.COUNTERCLOCKWISE, speed);
        pause();

        //drive forward 24 inches
        driveStraight(speed, 24, 5);
        pause();

        //turn to face wall
        turn45(TurnDirection.CLOCKWISE, speed);
        pause();

        //drive to wall, stopping at least 10 inches short
        driveStraight(speed, 15, 5);
        pause();

        turn90(TurnDirection.CLOCKWISE, speed);
        pause();

        driveStraight(1, 48, 20);
        pause();

        driveStraight(1, -76, 20);
        pause();

//        turn90(TurnDirection.CLOCKWISE, speed);
//        pause();
//

    }


    void driveStraight(double speed, double inches, double timeoutSeconds) {
        encoderDrive(speed, inches, inches, timeoutSeconds, false);
    }

    void pause() {
        waitForTick(pauseTimeBetweenSteps);
    }

    public void waitForTick(long periodMs) {
        sleep(periodMs);
    }


}
