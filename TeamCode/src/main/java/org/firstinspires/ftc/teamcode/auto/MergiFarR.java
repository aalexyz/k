package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Autonomous(name = "MergiFarR")
public class MergiFarR extends LinearOpMode {

    DcMotorEx frontLeftMotor;
    DcMotorEx frontRightMotor;
    DcMotorEx backLeftMotor;
    DcMotorEx backRightMotor;

    public static int milisecondsToParkLeftRedFar = 8000;
    public static int milisecondsToParkLeftRedStrafe = 2000;


    boolean crashed = false;

    public void runOpMode() throws InterruptedException {
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRight");

       // frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
         // backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ArrayList<DcMotorEx> motorListDrive = new ArrayList<>();
        motorListDrive.add(frontLeftMotor);
        motorListDrive.add(frontRightMotor);
        motorListDrive.add(backLeftMotor);
        motorListDrive.add(backRightMotor);

        for(DcMotorEx motor: motorListDrive) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        waitForStart();
        timer.reset();
        while(opModeIsActive())
        {
            if(timer.time(TimeUnit.MILLISECONDS)<milisecondsToParkLeftRedStrafe)
            {
                frontLeftMotor.setPower(0);
                frontRightMotor.setPower(1);
                backLeftMotor.setPower(1);
                backRightMotor.setPower(0);
            }
            else if(timer.time(TimeUnit.MILLISECONDS)==milisecondsToParkLeftRedStrafe && (timer.time(TimeUnit.MILLISECONDS)<milisecondsToParkLeftRedFar))
            {
                frontLeftMotor.setPower(0.5);
                frontRightMotor.setPower(0.5);
                backLeftMotor.setPower(0.5);
                backRightMotor.setPower(0.5);
            }
            else
            {
                frontLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backLeftMotor.setPower(0);
                backRightMotor.setPower(0);
            }
        }
    }
}