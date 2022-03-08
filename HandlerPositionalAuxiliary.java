package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Handler Main Teleop", group="Linear Opmode")

public class HandlerPositionalAuxiliary extends LinearOpMode {

    // Main drive train motors
    private DcMotor LA;
    private DcMotor LF;
    private DcMotor RA;
    private DcMotor RF;

    // Aux Motor outputs
    private DcMotor bridgeR;
    private DcMotor bridgeL;
    private DcMotor duckSpin;
    private DcMotor sucker;

    private void drive(double power, int LAD, int LFD, int RAD, int RFD) {

        LA.setTargetPosition(LA.getCurrentPosition()+LAD);
        LF.setTargetPosition(LF.getCurrentPosition()+LFD);
        RA.setTargetPosition(RA.getCurrentPosition()+RAD);
        RF.setTargetPosition(RF.getCurrentPosition()+RFD);
        LA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LA.setPower(power);
        LF.setPower(power);
        RA.setPower(power);
        RF.setPower(power);

        while (opModeIsActive() && (LA.isBusy() || LF.isBusy() || RA.isBusy() || RF.isBusy())) {
        }
        
        // set motor power back to 0
        LA.setPower(0);
        LF.setPower(0);
        RA.setPower(0);
        RF.setPower(0);
    }

    private void driveAux(double power, int LAD, int LFD, int RAD, int RFD) {
        //driveAux(-1, -1000, -1000, -1000, -1000);
        LA.setTargetPosition(LA.getCurrentPosition()+LAD);
        LF.setTargetPosition(LF.getCurrentPosition()-LFD);
        RA.setTargetPosition(RA.getCurrentPosition()-RAD);
        RF.setTargetPosition(RF.getCurrentPosition()+RFD);
        LA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LA.setPower(power);
        LF.setPower(-power);
        RA.setPower(-power);
        RF.setPower(power);

        while (opModeIsActive() && (LA.isBusy() || LF.isBusy() || RA.isBusy() || RF.isBusy())) {
        }
        
        // set motor power back to 0
        LA.setPower(0);
        LF.setPower(0);
        RA.setPower(0);
        RF.setPower(0);
    }

    private void bridge(double power,int LeftDraw,int RightDraw) {
        BridgeL.setTargetPosition(BridgeL.getCurrentPosition()+LeftDraw);
        BridgeR.setTargetPosition(BridgeR.getCurrentPosition()+RightDraw);
        BridgeL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BridgeR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BridgeL.setPower(power);
        BridgeR.setPower(power);
    }


    @Override
    public void runOpMode() { // Main OpMode
        telemetry.addData("Elapsed Time", "Initialized");
        telemetry.update();

        // Getting the configuration from Robot Controller
        LA  = hardwareMap.get(DcMotor.class, "moto0");
        LF  = hardwareMap.get(DcMotor.class, "moto1");
        RA  = hardwareMap.get(DcMotor.class, "moto2");
        RF  = hardwareMap.get(DcMotor.class, "moto3");
        // -----------------------------------------------//
        LA.setDirection(DcMotor.Direction.REVERSE);
        LF.setDirection(DcMotor.Direction.REVERSE);
        RA.setDirection(DcMotor.Direction.FORWARD);
        RF.setDirection(DcMotor.Direction.FORWARD);
        // -----------------------------------------------//
        bridgeR  = hardwareMap.get(DcMotor.class, "moto0E");
        bridgeL  = hardwareMap.get(DcMotor.class, "moto1E");
        duckSpin  = hardwareMap.get(DcMotor.class, "moto2E");
        sucker  = hardwareMap.get(DcMotor.class, "moto3E");
        // -----------------------------------------------//
        bridgeR.setDirection(DcMotor.Direction.REVERSE);
        bridgeL.setDirection(DcMotor.Direction.FORWARD);

        LA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        

        while (opModeIsActive()) { // Start of the OpMode. Runs until the end of the match
            if (gamepad1.dpad_left && gamepad1.left_bumper) {
                drive(1, -1000, -1000, 1000, 1000);
            }
            if (gamepad1.dpad_right && gamepad1.left_bumper) {
                drive(1,1000, 1000, -1000, -1000);
            }
            
            if (gamepad1.dpad_up) {
                drive(1, 1000, 1000, 1000, 1000);
            }
            if (gamepad1.dpad_right) {
                driveAux(1, 1000, 1000, 1000, 1000);
            }
            if (gamepad1.dpad_left) {
                driveAux(-1, -1000, -1000, -1000, -1000);
            }
            if (gamepad1.dpad_down) {
                drive(-1, -1000, -1000, -1000, -1000);
            }
            if (gamepad2.dpad_up) {
                bridge(1, 100, 100);
            }
            if (gamepad2.dpad_down) {
                bridge(-1, -100, -100);
            }

            telemetry.addData("Power", LA.getPower());
            telemetry.addData("Power", LF.getPower());
            telemetry.addData("Power", RA.getPower());
            telemetry.addData("Power", RF.getPower());

            telemetry.addData("position", LA.getCurrentPosition());
            telemetry.addData("position", LF.getCurrentPosition());
            telemetry.addData("position", RA.getCurrentPosition());
            telemetry.addData("position", RF.getCurrentPosition());

            telemetry.addData("is at target", !LA.isBusy());
            telemetry.addData("is at target", !LF.isBusy());
            telemetry.addData("is at target", !RA.isBusy());
            telemetry.addData("is at target", !RF.isBusy());

            telemetry.addData("bridgeL", bridgeL.getPower());
            telemetry.addData("bridgeR", bridgeR.getPower());

            telemetry.addData("is at target", !bridgeL.isBusy());
            telemetry.addData("is at target", !bridgeR.isBusy());

            telemetry.addData("Sucker", sucker.getPower());
            telemetry.addData("Duck Spin", duckSpin.getPower());
            telemetry.update();
        }
    }
}
