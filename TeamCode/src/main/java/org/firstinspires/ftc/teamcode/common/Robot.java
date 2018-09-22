package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    public DcMotor motorLeft = null;
    public DcMotor motorRight = null;

    public DcMotor motorIntake = null;

    public Servo servoIntakeLeft = null;
    public Servo servoIntakeRight = null;

    public DcMotor motorArmLift = null;
    public DcMotor motorArmSlide = null;

    public Robot(HardwareMap hardwareMap) {
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
    }


    public void stop() {
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}
