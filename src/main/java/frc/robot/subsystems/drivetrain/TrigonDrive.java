package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.constants.RobotConstants.TrigonDriveConstants;

/**
 * This class includes all the methods from differential drive plus a few of our
 * own based on our drivers request
 */
public class TrigonDrive extends DifferentialDrive {
    private static final double kYLinearThreshold = 0.25;
    private static final double kYLinearCoefficient = 2;
    private static final double kYLinearOffset = 0.06;
    private static final double kXDeadband = 0.085;
    private static final double kXLinearCoefficient = 0.75;
    private double sensitivity;
    private double threshold;

    public TrigonDrive(SpeedController leftMotor, SpeedController rightMotor) {
        super(leftMotor, rightMotor);
        this.sensitivity = TrigonDriveConstants.kSensitivity;
        this.threshold = TrigonDriveConstants.kThreshold;
    }

    /**
     * sets the sensitivity of the joystick to multiply it by the input given later.
     *
     * @param sensitivity the sensitivity to of the joystick.
     */
    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getThreshold() {
        return threshold;
    }

    /**
     * Gets the Y axis joystick input and performs calculations to it according to
     * the driver requests.
     */
    public double yInputCalculation(double value) {
        boolean isLinear = Math.abs(value) <= kYLinearThreshold;
        return isLinear ? kYLinearCoefficient * value + Math.signum(value) * kYLinearOffset : Math.signum(value) * Math.sqrt(Math.abs(value));
    }

    /**
     * Gets the X axis joystick input and performs calculations to it according to
     * the driver requests.
     */
    public double xInputCalculation(double value) {
        value = value * kXLinearCoefficient;
        return Math.abs(value) < kXDeadband ? 0 : value;
    }

    public void trigonCurvatureDrive(double xInput, double yInput) {
        double x = xInputCalculation(xInput);
        double y = yInputCalculation(yInput);
        curvatureDrive(sensitivity * y, sensitivity * x,
            Math.sqrt(y * y + x * x) < threshold || Math.abs(y) < Math.abs(x));
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Sensitivity", this::getSensitivity, this::setSensitivity);
        builder.addDoubleProperty("Threshold", this::getThreshold, this::setThreshold);
    }
}
