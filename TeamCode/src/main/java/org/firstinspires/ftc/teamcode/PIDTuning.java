package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
@TeleOp(name = "h")
@Config
public class PIDTuning extends LinearOpMode {
public static PIDFCoefficients pidfCoefficients = new PIDFCoefficients(10, 2, 0.3, 0);
public static int pos = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = FtcDashboard.getInstance().getTelemetry();
        pos = 0;
        DcMotorEx motorEx = hardwareMap.get(DcMotorEx.class, "elevator");
        motorEx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorEx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorEx.setTargetPosition(0);
        motorEx.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorEx.setPower(1);
        waitForStart();
        while (opModeIsActive() && !isStopRequested())
        {
            motorEx.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
            motorEx.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorEx.setTargetPosition(pos);
            telemetry.addData("current position", motorEx.getCurrentPosition());
            telemetry.addData("target pos", pos);
            telemetry.update();
        }
    }
}
