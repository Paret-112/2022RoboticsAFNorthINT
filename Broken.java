package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Main Teleop v1", group="Linear Opmode")

public class HandlerMainG1 extends LinearOpMode {

	// Main drive train motors
	private DcMotorEx LA;
	private DcMotorEx LF;
	private DcMotorEx RA;
	private DcMotorEx RF;

	// Aux Motor outputs
	private DcMotor bridgeR;
	private DcMotor bridgeL;
	private DcMotor duckSpin;
	private DcMotor sucker;
	
	// Values
	private double motorVel = 600;
	private double motorVelo = -600;

	@Override
	public void runOpMode() { // Main OpMode
		
		telemetry.addData("Elapsed Time", "Initialized");
		telemetry.update();

		// Getting the configuration from Robot Controller
		LA  = hardwareMap.get(DcMotorEx.class, "moto0");
		LF  = hardwareMap.get(DcMotorEx.class, "moto1");
		RA  = hardwareMap.get(DcMotorEx.class, "moto2");
		RF  = hardwareMap.get(DcMotorEx.class, "moto3");
		// -----------------------------------------------//
		bridgeR  = hardwareMap.get(DcMotor.class, "moto0E");
		bridgeL  = hardwareMap.get(DcMotor.class, "moto1E");
		duckSpin  = hardwareMap.get(DcMotor.class, "moto2E");
		sucker  = hardwareMap.get(DcMotor.class, "moto3E");
		// -----------------------------------------------//
		LA.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
		LF.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
		RA.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
		RF.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

		bridgeL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		bridgeR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

		// Most robots need the motor on one side to be reversed to drive forward
		// Reverse the motor that runs backwards when connected directly to the battery
		LA.setDirection(DcMotorEx.Direction.REVERSE);
		LF.setDirection(DcMotorEx.Direction.REVERSE);
		RA.setDirection(DcMotorEx.Direction.FORWARD);
		RF.setDirection(DcMotorEx.Direction.FORWARD);
		// -----------------------------------------------//
		bridgeL.setDirection(DcMotor.Direction.REVERSE);
		bridgeR.setDirection(DcMotor.Direction.FORWARD);
		
		// Wait for the game to start
		waitForStart();

		// Runs until the end of the match
		while (opModeIsActive()) {

			if (gamepad1.left_bumper) { // Sets left side reverse when bumper is pressed
				LA.setPower(-1);
				LF.setPower(-1);
			}
			if (gamepad1.right_bumper) { // Sets right side reverse when bumper is pressed
				RA.setPower(-1);
				RF.setPower(-1);
			}

			// Omnidirectional Functions
			if (gamepad1.dpad_left){ // Left Omni Drive
				LA.setPower(1);
				LF.setPower(-1);
				RA.setPower(1);
				RF.setPower(-1);
			}
			if (gamepad1.dpad_right) { // Right Omni Drive
				LA.setPower(-1);
				LF.setPower(1);
				RA.setPower(1);
				RF.setPower(-1);
			}

			// Sets the motor power equal to the value outputed by pushing the triggers on the Controller, forward motion only
			LA.setPower(gamepad1.left_trigger);
			LF.setPower(gamepad1.left_trigger);
			RA.setPower(gamepad1.right_trigger);
			RF.setPower(gamepad1.right_trigger);


			// Aux inputs from Driver 2 to Aux motors, Gunner
			if (gamepad2.y) { // Spins the motor to get the duck when Y is pressed
				duckSpin.setPower(1);
			}
			if (gamepad2.left_bumper) {
				bridgeL.setPower(-1);
				bridgeR.setPower(-1);
			}
			if (gamepad2.right_bumper) {
				sucker.setPower(-1);
			}

			bridgeL.setPower(gamepad2.left_trigger);
			bridgeR.setPower(gamepad2.left_trigger);
			sucker.setPower(gamepad2.right_trigger);


			// Show the elapsed game time and wheel power. Also shows any power given to the aux motors
			telemetry.addData("LA", LA.getPower());
			telemetry.addData("LF", LF.getPower());
			telemetry.addData("RA", RA.getPower());
			telemetry.addData("RF", RF.getPower());
			telemetry.addData("bridgeL", bridgeL.getPower());
			telemetry.addData("bridgeR", bridgeR.getPower());
			telemetry.addData("Sucker", sucker.getPower());
			telemetry.addData("Duck Spin", duckSpin.getPower());
			telemetry.update();
		}
	}
}
