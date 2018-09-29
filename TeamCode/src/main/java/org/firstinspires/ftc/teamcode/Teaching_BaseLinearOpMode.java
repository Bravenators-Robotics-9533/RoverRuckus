package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Easing;
import org.firstinspires.ftc.teamcode.common.FtcGamePad;
import org.firstinspires.ftc.teamcode.common.IDrive;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.TankDrive;

public abstract class Teaching_BaseLinearOpMode extends LinearOpMode {


    public Robot robot;
    public IDrive drive;
    public FtcGamePad driverGamePad;
    public FtcGamePad operatorGamePad;

    private boolean fourWheelDrive;


    public void Initialize(HardwareMap hardwareMap, boolean fourWheelDrive) {

        this.fourWheelDrive = fourWheelDrive;
        robot = new Robot(hardwareMap, false);
        driverGamePad = new FtcGamePad("driver", gamepad1);
        operatorGamePad = new FtcGamePad("operator", gamepad2);

    }

    public void setDrive(IDrive drive){
        this.drive = drive;
    }


    public void turnDegrees(Autonomous_Teaching.TurnDirection direction, int degrees, double speed) {
        final double onedegreeticks = 3.4777;
        double turnInches = (3.51 * 3.1415) * ( (onedegreeticks * degrees) / robot.REV_COUNTS_PER_MOTOR_REV);
        if(direction == Autonomous_Teaching.TurnDirection.CLOCKWISE) {
            //maneuver build for counter-clockwise, so reverse
            turnInches = -turnInches;
        }
        //updateStep("Turning 90 degrees");
        encoderDrive(0.5, -turnInches, turnInches, 10.0, false);
    }
    public void turnDegrees(Autonomous_Teaching.TurnDirection direction, int degrees) {
        turnDegrees(direction, degrees, 0.5);
    }


    public void turn90(Autonomous_Teaching.TurnDirection direction, double speed){
        turnDegrees(direction, 90, speed);
    }
    public void turn90(Autonomous_Teaching.TurnDirection direction) {
        turn90(direction, 0.5);
    }

    public void turn45(Autonomous_Teaching.TurnDirection direction, double speed) {
        turnDegrees(direction, 45, speed);
    }

    public void turn45(Autonomous_Teaching.TurnDirection direction) {
        turn45(direction, 0.5);
    }


    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS, boolean holdPosition) {


        int targetLeft, targetRight, currentRight, currentLeft;
        int differenceLeft, differenceRight;

        boolean maxed = false;
        ElapsedTime runtime = new ElapsedTime();

        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            robot.setNewPosition(leftInches, rightInches);

            currentLeft = robot.motorFrontLeft.getCurrentPosition();
            int newLeftTarget = currentLeft + (int)(leftInches * robot.COUNTS_PER_INCH);

            int scale = newLeftTarget - currentLeft;


            // reset the timeout time and start motion.
            runtime.reset();

            double currentSpeed = 0;
            double multiplier = 0;


            robot.setPower(currentSpeed , currentSpeed);


            int lastPosition = 0;
            double lastRuntime = 0;

            int slowdownTick = 150;
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.isBusy())) {
                targetLeft = robot.motorFrontLeft.getTargetPosition();
                targetRight =robot.motorFrontRight.getTargetPosition();
                currentLeft = robot.motorFrontLeft.getCurrentPosition();
                currentRight = robot.motorFrontRight.getCurrentPosition();
                differenceLeft = Math.abs(Math.abs(targetLeft) - Math.abs(currentLeft));

                if(maxed) {
                    double newSpeed = Easing.Interpolate(1 - (differenceLeft / scale), Easing.Functions.QuinticEaseIn);
                    currentSpeed = newSpeed;
                    if(currentSpeed < 0.2) {
                        currentSpeed = 0.2;
                    }
                }
                else if(currentSpeed < speed) {
                    multiplier = Easing.Interpolate(runtime.seconds() * 4, Easing.Functions.CubicEaseOut);
                    currentSpeed = speed * multiplier;
                }


                telemetry.addLine()
                        .addData("Multiplier", "%7f", multiplier)
                        .addData("Speed", "%7f", currentSpeed);

                //telemetry.update();

                if(currentSpeed >= speed) {
                    currentSpeed = speed;
                    maxed = true;
                }
                robot.setPower(currentSpeed, currentSpeed);


                // Display it for the driver.
                telemetry.addLine().addData("Target",  "Running to %7d :%7d",
                        targetLeft,
                        targetRight);
                telemetry.addLine().addData("Current",  "Running at %7d :%7d",
                        currentLeft,
                        currentRight);
                telemetry.update();
            }

            if(holdPosition==false) {
                // Stop all motion;
                robot.stop();

                // Turn off RUN_TO_POSITION
                robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }

}
