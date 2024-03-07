package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoTest")
public class ServoTest extends LinearOpMode {

    public static double posServo1 = 0, posServo2 = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        Servo claw1 = hardwareMap.get(Servo.class, "claw1");
        Servo claw2 = hardwareMap.get(Servo.class, "claw2");
        Servo pivot = hardwareMap.get(Servo.class, "pivot");
        claw1.setPosition(0);
        claw2.setPosition(0);
        pivot.setPosition(0);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()){
            claw1.setPosition(posServo1);
            claw2.setPosition(posServo2);
            pivot.setPosition(0);
        }
    }
}
