/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Handler Main Teleop", group="Linear Opmode")

public class HandlerMainTB extends LinearOpMode {

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
        bridgeR  = hardwareMap.get(DcMotor.class, "moto0E");
        bridgeL  = hardwareMap.get(DcMotor.class, "moto1E");
        duckSpin  = hardwareMap.get(DcMotor.class, "moto2E");
        sucker  = hardwareMap.get(DcMotor.class, "moto3E");
        // -----------------------------------------------//
        LA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        LA.setDirection(DcMotor.Direction.FORWARD);
        LF.setDirection(DcMotor.Direction.FORWARD);
        RA.setDirection(DcMotor.Direction.REVERSE);
        RF.setDirection(DcMotor.Direction.REVERSE);
        // -----------------------------------------------//
        bridgeR.setDirecetion(DcMotor.Direction.REVERSE);

        // Wait for the game to start
        waitForStart();
        runtime.reset();

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
            // Sets the motor power equal to the value outputed by pushing the triggers on the Controller, forward motion only
            LA.setPower(gamepad1.left_trigger);
            LF.setPower(gamepad1.left_trigger);
            RA.setPower(gamepad1.right_trigger);
            RF.setPower(gamepad1.right_trigger);


            // Aux inputs from Driver 2 to Aux motors, Gunner
            if (gamepad2.y) { // Spins the motor to get the duck when Y is pressed
                duckSpin.setPower(1);
            }
            if (gampad2.left_bumper) {
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
            telemetry.addData("Status", "Run Time: " + runtime.toString());
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
