package ftc.nd;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Administrator on 9/17/2018.
 */

@TeleOp(name = "TeleopMain")
public class TeleopMain extends OpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor belt = null;
    @Override
    public void init() {

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        belt.setDirection(DcMotor.Direction.FORWARD);

    }

    @Override
    public void loop() {
        double leftPower;
        double rightPower;
        double beltPower;

        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;
        double convert = gamepad2.left_stick_x;
        leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
        beltPower = Range.clip(convert,-1.0,1.0);

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        belt.setPower(beltPower);


        // Show the elapsed game time and wheel power.
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    @Override
    public void stop(){

    }

}
