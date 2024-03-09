package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OutTake;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Autonomous(name = "Blue Close")
public class BlueClose extends LinearOpMode {
    Servo claw1, claw2;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        OutTake ot = new OutTake(hardwareMap);
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        claw2.setDirection(Servo.Direction.REVERSE);

        claw1.setPosition(0);
        claw2.setPosition(0);

        ot.state = OutTake.State.DOWN_TRANSITION;
        Pose2d startPose = new Pose2d();
        Trajectory t1 = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(22,19), Math.toRadians(90))
                .addTemporalMarker(2, () -> {
                    claw1.setPosition(1);
                })
                .forward(15)
                .build();
        Trajectory t2 = drive.trajectoryBuilder(t1.end())
                .strafeLeft(36)
                .addTemporalMarker(2, () -> {
                    claw1.setPosition(0);
                    claw2.setPosition(0);
                    ot.state = OutTake.State.TAKE_PIXELS;
                })
                .forward(16)
                .build();
        ElapsedTime timer = new ElapsedTime();
        timer.startTime();
        waitForStart();
        while (opModeIsActive() && !isStopRequested())
        {
            drive.followTrajectory(t1);
            ot.state = OutTake.State.PLACE_PIXELS;
            claw2.setPosition(1);
            drive.followTrajectory(t2);
            telemetry.addData("timer", timer.seconds());
            timer.reset();
            telemetry.update();
            timer.reset();
        }

    }
}
