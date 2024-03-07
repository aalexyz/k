package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class ServoZero extends LinearOpMode {

    Servo servo1, servo2, pivot;
    Gamepad prevgm, currentgm;
    public static double value1 = 0.6, value2 = 0.5;
    boolean c1 = true, c2 = true, p = true, both = true;

    public void runOpMode() throws InterruptedException {

        servo1 = hardwareMap.get(Servo.class, "claw1");
        servo2 = hardwareMap.get(Servo.class, "claw2");
        pivot = hardwareMap.get(Servo.class, "pivot");
        servo1.setPosition(0);
        servo2.setPosition(0);
        pivot.setPosition(0);
        currentgm = new Gamepad();
        prevgm = new Gamepad();
        waitForStart();

        while (opModeIsActive()&&!isStopRequested())
        {
            currentgm.copy(gamepad1);
            prevgm.copy(currentgm);
            if (gamepad1.a&&!prevgm.a) {
                if(c1)
                {
                    servo1.setPosition(value1);
                    c1 = false;
                }
                else {
                    servo1.setPosition(0);
                    c1 = true;
                }
            }

            if (gamepad1.b&&!prevgm.b) {
                if (c2) {

                    servo2.setPosition(value1);
                    c2 = false;
                } else {
                    servo2.setPosition(0);
                    c2 = true;
                }
            }

            if (gamepad1.y&&!prevgm.y) {
                if (p) {
                    pivot.setPosition(value2);
                    p = false;
                } else {
                    pivot.setPosition(0);
                    p = true;
                }
            }

            if (gamepad1.x && !prevgm.x) {
                if (both) {
                    servo1.setPosition(value1);
                    servo2.setPosition(value1);
                    both = false;
                } else {
                    servo1.setPosition(0);
                    servo2.setPosition(0);
                    both = true;
                }
            }
        }
    }
}
