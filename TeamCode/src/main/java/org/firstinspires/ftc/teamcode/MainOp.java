package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "avocado \u0001\uF951")
public class MainOp extends LinearOpMode {
    public Telemetry tele = telemetry;
    OutTake outTake;
    Chassis chassis;
    Gamepad prevgm, currentgm;
    Servo claw1, claw2, plane;
    boolean prevA, prevLG, prevRG, prevY;
    boolean closeClaw1 = false, closeClaw2 = false;
    public static double closePosition1 = 0.3, closePosition2 = 0.3;
    @Override
    public void runOpMode() throws InterruptedException {
        outTake = new OutTake(hardwareMap);
        chassis = new Chassis(hardwareMap);
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        plane = hardwareMap.get(Servo.class, "plane");

        claw1.setPosition(0);
        claw2.setPosition(0);
        plane.setPosition(0);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()){
            if (!prevY && gamepad2.y)
            {
                plane.setPosition(1);
            }
            if(!prevA && gamepad2.a){
                outTake.setTakeMode();
            }
            if(!prevLG && gamepad1.left_bumper){
                closeClaw1 = !closeClaw1;
                claw1.setPosition(closePosition1);
            }
            if(!prevRG && gamepad1.right_bumper){
                closeClaw2 = !closeClaw2;
                claw2.setPosition(closePosition2);
            }

            outTake.update(-gamepad2.right_stick_y, -gamepad2.left_stick_y, 1, telemetry);

            // controale peppers
            chassis.update(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger, gamepad1.a);

            // controale normale
//            chassis.update(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

            prevA = gamepad2.a;
            prevLG = gamepad2.left_bumper;
            prevRG = gamepad2.right_bumper;
            telemetry.update();
        }
    }
}
