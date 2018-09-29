package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.FtcGamePad;
import org.firstinspires.ftc.teamcode.common.TankDrive;

@TeleOp(name = "TeleOp Teaching", group = "Tutorials")
@Disabled
public class Teleop_Teaching extends Teaching_BaseLinearOpMode implements FtcGamePad.ButtonHandler {

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize(hardwareMap, false);
        setDrive(new TankDrive(robot, driverGamePad));

        waitForStart();

        while(opModeIsActive())
        {
            //drive.handle();
        }

        robot.stop();

    }

    @Override
    public void gamepadButtonEvent(FtcGamePad gamepad, int button, boolean pressed) {




    }
}
