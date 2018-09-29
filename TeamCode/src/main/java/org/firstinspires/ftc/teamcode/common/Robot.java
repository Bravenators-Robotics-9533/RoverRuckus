package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    public static final double     REV_COUNTS_PER_MOTOR_REV = 288;      // eg: Rev Side motor
    static final double            DRIVE_GEAR_REDUCTION    = 1.0 ;             // This is < 1.0 if geared UP
    static final double            WHEEL_DIAMETER_INCHES   = 3.543 ;           // For figuring circumference
    public static final double     COUNTS_PER_INCH = (REV_COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public DcMotor motorFrontLeft = null;
    public DcMotor motorFrontRight = null;
    public DcMotor motorBackLeft = null;
    public DcMotor motorBackRight = null;

    public DcMotor motorIntake = null;

    private final boolean fourWheelDrive;


//    public Servo servoIntakeLeft = null;
//    public Servo servoIntakeRight = null;
//
//    public DcMotor motorArmLift = null;
//    public DcMotor motorArmSlide = null;

    public Robot(HardwareMap hardwareMap, boolean fourWheelDrive) {
        motorFrontLeft = hardwareMap.dcMotor.get("Front_Left_Motor");
        motorFrontRight = hardwareMap.dcMotor.get("Front_Right_Motor");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        this.fourWheelDrive = fourWheelDrive;

        if(fourWheelDrive) {

            motorBackLeft = hardwareMap.dcMotor.get("Back_Left_Motor");
            motorBackRight = hardwareMap.dcMotor.get("Back_Right_Motor");
            motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        }

    }


    public Pair<Integer, Integer> getCurrentPosition() {
        return new Pair<>(motorFrontLeft.getCurrentPosition(), motorFrontRight.getCurrentPosition());
    }
    public Pair<Integer, Integer> calculateNewPositions(double leftInches, double rightInches ){
        int leftTarget = motorFrontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        int rightTarget = motorFrontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        return new Pair<>(leftTarget, rightTarget);
    }

    public Pair<Integer, Integer> setNewPosition(double inches) {
        return setNewPosition(inches, inches);
    }
    public Pair<Integer, Integer> setNewPosition(double leftInches, double rightInches) {
        Pair<Integer, Integer> target = calculateNewPositions(leftInches, rightInches);
        motorFrontLeft.setTargetPosition(target.getLeft());
        motorFrontRight.setTargetPosition(target.getRight());

        // Turn On RUN_TO_POSITION
        setRunToPosition();
        return target;
    }


    public boolean isBusy() {
        return  isBusy(true);
    }
    public boolean isBusy(boolean allMotors){
        if(allMotors) {
            return motorFrontLeft.isBusy() ||
                    motorFrontRight.isBusy();// ||
//                    motorBackLeft.isBusy() ||
//                    motorBackRight.isBusy();
        } else {
            return motorFrontLeft.isBusy() &&
                    motorFrontRight.isBusy(); // &&
                    //motorBackLeft.isBusy() &&
//                    motorBackRight.isBusy();
        }
    }

    public void setPower(double left, double right)
    {
        motorFrontLeft.setPower(left);
        motorFrontRight.setPower(right);
    }

    public void setRunUsingEncoders(){
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setRunToPosition() {
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setRunWithoutEncoders() {
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setMode(DcMotor.RunMode mode) {
        motorFrontLeft.setMode(mode);
        motorFrontRight.setMode(mode);
    }

    public void stop() {
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        if(this.fourWheelDrive) {
            motorBackLeft.setPower(0);
            motorBackRight.setPower(0);
        }
    }
}
