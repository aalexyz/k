package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OutTake {
    public enum State{
        TAKE_PIXELS,
        DOWN_TRANSITION,
        PLACE_PIXELS;
        public static double angle = 0, extendo = 0;
    }
    public State state;
    public DcMotorEx angle;
    public DcMotorEx elevator;
    public Servo pivot;
    public static Servo.Direction dir = Servo.Direction.FORWARD;
    public static int midPoint = 100;
    public OutTake(HardwareMap hm){
        angle = hm.get(DcMotorEx.class, "angle");
        pivot = hm.get(Servo.class, "pivot");
        elevator = hm.get(DcMotorEx.class, "elevator");
        state = State.TAKE_PIXELS;
        State.angle = 0;
        State.extendo = 0;

        MotorConfigurationType mct = angle.getMotorType();
        mct.setAchieveableMaxRPMFraction(1.0);
        angle.setMotorType(mct);

        mct = elevator.getMotorType();
        mct.setAchieveableMaxRPMFraction(1.0);
        elevator.setMotorType(mct);

        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(10, 2, 0.3, 0));
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(0);
        elevator.setPower(1);

        angle.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        angle.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        angle.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(7, 4, 1, 0));
        angle.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        angle.setTargetPosition(0);
        angle.setPower(1);


//        angle.setTargetPosition(midPoint);
        pivot.setDirection(dir);
        pivot.setPosition(0);
    }

    public static final double maxExtension = 1150, r = 16, b = 235; // r and b in mm
    public void setTakeMode(){
        state = State.DOWN_TRANSITION;
    }

    public double ticksToDegrees(double a){
        return 360 / 384.5 * a;
    }
    public double degreesToTicks(double a){
        return  (2786.2 / 360) * a;
    }
    public double mmToTicks(double a){
        return 537.7 / (Math.PI * 2 * r) * a;
    }

    public void update(double angleIncrement, double extendoIncrement, double multiplier, Telemetry tele){
        switch (state){
            case TAKE_PIXELS:

                State.extendo += 1 * extendoIncrement * multiplier;
                State.angle += 1 * angleIncrement * multiplier;
                if(State.extendo < 0) State.extendo = 0;
                if(State.extendo > maxExtension) State.extendo = maxExtension;

                if(State.angle < 0) State.angle = 0;
                if(State.angle > 60) State.angle = 60;
                elevator.setTargetPosition((int) mmToTicks(State.extendo));
                angle.setTargetPosition((int) degreesToTicks(State.angle));

//                if(angleIncrement > 0){
//                    state = State.PLACE_PIXELS;
//                }

                break;
            case DOWN_TRANSITION:
                angle.setTargetPosition(0);
                elevator.setTargetPosition(0);

                pivot.setPosition(0);

                if(angle.getCurrentPosition() < 4){
                    state = State.TAKE_PIXELS;
                }

                break;
            case PLACE_PIXELS:
                if(State.angle > 10)
                    pivot.setPosition(60 / 270.f);
                else pivot.setPosition(0);
                State.angle += angleIncrement * 0.1 * multiplier;

                if(State.angle > 60) State.angle = 60;
                if(State.angle < 0) State.angle = 0;

                angle.setTargetPosition((int) degreesToTicks(State.angle));

                double alpha = 60 - State.angle;

//                if(State.angle == 60) State.extendo = (2 * Math.PI * r) / 587.7 * maxExtension;
//                else if(State.angle == 0) State.extendo = 0;
//                else State.extendo = Math.sqrt(3)/2.0 * b/(2 * Math.sin(alpha));

                elevator.setTargetPosition((int) mmToTicks(State.extendo));

                if(State.angle == 0){ state = State.DOWN_TRANSITION; }

                break;

        }
//        angle.setPower(1);
//        elevator.setPower(1);
        tele.addData("state", state.toString());
        tele.addData("angle", State.angle);
        tele.addData("extendo", State.extendo);
        tele.addData("angle pos", angle.getCurrentPosition());

    }
}
