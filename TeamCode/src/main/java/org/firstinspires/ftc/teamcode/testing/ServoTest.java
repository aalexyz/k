package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoTest")
@Config
public class ServoTest extends LinearOpMode {

    public static double posServo1 = 0, posServo2 = 0, pivotPos = 0.5;
    public static Servo.Direction claw1dir = Servo.Direction.FORWARD, claw2dir = Servo.Direction.FORWARD, pivotDir = Servo.Direction.REVERSE;
    @Override
    public void runOpMode() throws InterruptedException {
        Servo claw1 = hardwareMap.get(Servo.class, "claw1");
        Servo claw2 = hardwareMap.get(Servo.class, "claw2");
        Servo pivot = hardwareMap.get(Servo.class, "pivot");
        claw1.setPosition(0);
        claw2.setPosition(0);
        pivot.setPosition(0.5);

        claw1.setDirection(claw1dir);
        claw2.setDirection(claw2dir);
        pivot.setDirection(pivotDir);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()){
            claw1.setPosition(posServo1);
            claw2.setPosition(posServo2);
            pivot.setPosition(pivotPos);

            claw1.setDirection(claw1dir);
            claw2.setDirection(claw2dir);
            pivot.setDirection(pivotDir);
        }
    }
}
