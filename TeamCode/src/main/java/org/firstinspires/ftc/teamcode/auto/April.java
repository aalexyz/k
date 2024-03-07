package org.firstinspires.ftc.teamcode.auto;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
@Autonomous(name = "AUTO")
public class April extends LinearOpMode {

    int AprilTagID;
    double xpose, ypoxe, zpose,
           pitch, roll, yaw,
           range, bearing, elevation;

    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagProcessor aprilTagProcessor = new AprilTagProcessor.Builder()
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary()
                .setDrawAxes(true)
                .setDrawTagOutline(true)
                .build();


        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(aprilTagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "camera"))
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .build();

        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}

        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        exposureControl.setMode(ExposureControl.Mode.Manual);
        exposureControl.setExposure(15, TimeUnit.MICROSECONDS);

        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        gainControl.setGain(255);

        waitForStart();

        while (!isStopRequested() && opModeIsActive())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
            for (AprilTagDetection tag : currentDetections)
            {
                if (currentDetections.size() != 0)
                {
                    AprilTagID = tag.id;
                    telemetry.addData("tag id", tag.id);
                    telemetry.addData("exp", exposureControl.isExposureSupported()); //daca e false too bad
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f", tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.z));
                }
            }

        }
        telemetry.update();
    }
}
