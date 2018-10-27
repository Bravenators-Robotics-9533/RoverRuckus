package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.FtcGamePad;
import org.firstinspires.ftc.teamcode.common.GTADrive;
import org.firstinspires.ftc.teamcode.common.TankDrive;
import org.firstinspires.ftc.teamcode.common.TankDriveFourWheel;

@TeleOp(name = "TeleOp Teaching", group = "Tutorials")

public class Teleop_Teaching extends Teaching_BaseLinearOpMode implements FtcGamePad.ButtonHandler {

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize(hardwareMap, true);
        setDrive(new GTADrive(robot, driverGamePad));

        waitForStart();

        while(opModeIsActive())
        {
            drive.handle();
        }

        robot.stop();

    }

    @Override
    public void gamepadButtonEvent(FtcGamePad gamepad, int button, boolean pressed) {




    }
}
