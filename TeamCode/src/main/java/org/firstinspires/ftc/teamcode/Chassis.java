package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class Chassis {
    public DcMotorEx FR, FL, BR, BL;

    public Chassis(HardwareMap hm){
        FR = hm.get(DcMotorEx.class, "frontRight");
        FL = hm.get(DcMotorEx.class, "frontLeft");
        BR = hm.get(DcMotorEx.class, "backRight");
        BL = hm.get(DcMotorEx.class, "backLeft");

        MotorConfigurationType mct;

        mct = FR.getMotorType();
        mct.setAchieveableMaxRPMFraction(1.0);
        FR.setMotorType(mct);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        mct = FL.getMotorType();
        mct.setAchieveableMaxRPMFraction(1.0);
        FL.setMotorType(mct);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        mct = BR.getMotorType();
        mct.setAchieveableMaxRPMFraction(1.0);
        BR.setMotorType(mct);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        mct = BL.getMotorType();
        mct.setAchieveableMaxRPMFraction(1.0);
        BL.setMotorType(mct);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void update(double x, double y, double rot, boolean boost){
        if(!boost){
            x = x * 60 / 100.f;
            y = y * 60 / 100.f;
            rot = rot * 60 / 100.f;
        }

        double div = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(rot) , 1);

        FL.setPower((y + x + rot) / div);
        BL.setPower(-(y - x + rot) / div);
        FR.setPower(-(y - x - rot) / div);
        BR.setPower((y + x - rot) / div);

    }
}
