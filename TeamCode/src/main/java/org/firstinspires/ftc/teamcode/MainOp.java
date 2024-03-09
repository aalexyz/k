package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "avocado \uD83E\uDD51")
@Config
public class MainOp extends LinearOpMode {
    OutTake outTake;
    Chassis chassis;
    Servo claw1, claw2, plane;
    private boolean closeClaw1 = false, closeClaw2 = false;
    private boolean lPress = false, rPress = false, yPress = false;
    public static double closePosition1 = 0.25, closePosition2 = 0.35;
    @Override
    public void runOpMode() throws InterruptedException {
        outTake = new OutTake(hardwareMap);
        chassis = new Chassis(hardwareMap);
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        plane = hardwareMap.get(Servo.class, "plane");
        plane.setPosition(0);

        claw2.setDirection(Servo.Direction.REVERSE);

        claw1.setPosition(0);
        claw2.setPosition(0);
        ElapsedTime time = new ElapsedTime();

        waitForStart();
        time.reset();

        while (opModeIsActive() && !isStopRequested()){
            claw1.setPosition(closeClaw1 ? closePosition1 : 0);
            claw2.setPosition(closeClaw2 ? closePosition2 : 0);

            if(gamepad1.right_bumper && !rPress){
                rPress = true;
                closeClaw1 = !closeClaw1;
            } else if(rPress && !gamepad1.right_bumper) rPress = false;
            if(gamepad1.left_bumper && !lPress){
                lPress = true;
                closeClaw2 = !closeClaw2;
            } else if(lPress && !gamepad1.left_bumper) lPress = false;

            if(gamepad1.y && !yPress){
                yPress = true;
                plane.setPosition(0.7);
            } else if(yPress && !gamepad1.y) {
                yPress = false;
                plane.setPosition(0);
            }


            outTake.update(-gamepad2.right_stick_y, -gamepad2.left_stick_y, 1, telemetry);

            // controale peppers
            chassis.update(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger, gamepad1.a);

            // controale normale
//            chassis.update(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

            telemetry.addData("rb", closeClaw1);
            telemetry.addData("lb", closeClaw2);
            telemetry.addData("hz", 1 / time.seconds());
            time.reset();
            telemetry.update();
        }
    }
}
