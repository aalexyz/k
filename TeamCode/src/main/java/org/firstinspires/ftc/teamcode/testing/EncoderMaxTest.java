package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "encoder test")
public class EncoderMaxTest extends LinearOpMode {



    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx m = hardwareMap.get(DcMotorEx.class, "elevator");
        DcMotorEx m1 = hardwareMap.get(DcMotorEx.class, "angle");

        m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()){
            telemetry.addData("elevator", m.getCurrentPosition());
            telemetry.addData("angle", m1.getCurrentPosition());
            telemetry.update();
        }
    }
}
