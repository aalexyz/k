package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoZero extends LinearOpMode {

    Servo servo1, servo2, pivot;
    Gamepad prevgm, currentgm;

    public void runOpMode() throws InterruptedException {

        servo1 = hardwareMap.get(Servo.class, "claw1");
        servo2 = hardwareMap.get(Servo.class, "claw2");
        pivot = hardwareMap.get(Servo.class, "pivot");
        servo1.setPosition(0);
        servo2.setPosition(0);
        pivot.setPosition(0);
        waitForStart();

        while (opModeIsActive()&&!isStopRequested())
        {
            prevgm = currentgm;
            currentgm = gamepad1;
            if (gamepad1.a&&!prevgm.a) {
                servo1.setPosition(1);
            }
            else {
                servo1.setPosition(0);
            }

            if (gamepad1.b&&!prevgm.b)
            {

                servo2.setPosition(1);
            }
            else {
                servo2.setPosition(0);
            }
            if (gamepad1.y&&!prevgm.y)
            {
                pivot.setPosition(1);
            }
            else {
                pivot.setPosition(0);
            }
            if (gamepad1.x && !prevgm.x)
            {
                servo1.setPosition(1);
                servo2.setPosition(1);
            }
        }
    }
}
