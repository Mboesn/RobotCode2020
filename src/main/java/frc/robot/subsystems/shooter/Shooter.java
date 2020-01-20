package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.MoveableSubsystem;
import frc.robot.utils.DriverStationLogger;

import static frc.robot.Robot.robotConstants;

/**
 * This subsystem handles shooting power cells into the outer and inner ports.
 */
public class Shooter extends SubsystemBase implements MoveableSubsystem {
    private WPI_TalonFX leftTalon;
    private WPI_TalonFX rightTalon;
    private boolean isTuning;

    public Shooter() {
        //setting up the talon fx
        leftTalon = new WPI_TalonFX(robotConstants.can.LEFT_SHOOTER_TALON_FX);
        leftTalon.setNeutralMode(NeutralMode.Coast);
        leftTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
        leftTalon.config_kP(0, robotConstants.controlConstants.leftShooterSettings.getKP());
        leftTalon.config_kI(0, robotConstants.controlConstants.leftShooterSettings.getKI());
        leftTalon.config_kD(0, robotConstants.controlConstants.leftShooterSettings.getKD());
        leftTalon.config_kF(0, robotConstants.shooterConstants.LEFT_KF);
        leftTalon.selectProfileSlot(0, 0);
        leftTalon.setInverted(robotConstants.shooterConstants.IS_LEFT_MOTOR_INVERTED);
        leftTalon.setSensorPhase(robotConstants.shooterConstants.IS_LEFT_ENCODER_INVERTED);

        rightTalon = new WPI_TalonFX(robotConstants.can.RIGHT_SHOOTER_TALON_FX);
        rightTalon.setNeutralMode(NeutralMode.Coast);
        rightTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
        rightTalon.config_kP(0, robotConstants.controlConstants.rightShooterSettings.getKP());
        rightTalon.config_kI(0, robotConstants.controlConstants.rightShooterSettings.getKI());
        rightTalon.config_kD(0, robotConstants.controlConstants.rightShooterSettings.getKD());
        rightTalon.config_kF(0, robotConstants.shooterConstants.RIGHT_KF);
        rightTalon.selectProfileSlot(0, 0);
        rightTalon.setInverted(robotConstants.shooterConstants.IS_RIGHT_MOTOR_INVERTED);
        rightTalon.setSensorPhase(robotConstants.shooterConstants.IS_RIGHT_ENCODER_INVERTED);
        resetEncoders();
    }

    /**
     * @param power The power to set the talons in open loop. Value should be between -1.0 and 1.0.
     */
    @Override
    public void move(double power) {
        leftTalon.set(power);
        rightTalon.set(power);
    }

    public void startPID() {
        startPID(ShooterVelocity.kDefault.getVelocity());
    }

    /**
     * Starts using velocity PID instead of open-loop.
     *
     * @param velocitySetpoint velocity to set the talons.
     */
    public void startPID(double velocitySetpoint) {
        double leftVelocityInTalonUnits = velocitySetpoint * robotConstants.shooterConstants.LEFT_UNITS_PER_ROTATION
                / 600;
        double rightVelocityInTalonUnits = velocitySetpoint * robotConstants.shooterConstants.RIGHT_UNITS_PER_ROTATION
                / 600;
        leftTalon.set(TalonFXControlMode.Velocity, leftVelocityInTalonUnits);
        rightTalon.set(TalonFXControlMode.Velocity, rightVelocityInTalonUnits);
    }


    public void enableTuning() {
        DriverStationLogger.logToDS("Shooter tuning enabled");
        isTuning = true;
        // left shooter gains
        SmartDashboard.putNumber("PID/LeftShooter/kP", 0);
        SmartDashboard.putNumber("PID/LeftShooter/kI", 0);
        SmartDashboard.putNumber("PID/LeftShooter/kD", 0);
        SmartDashboard.putNumber("PID/LeftShooter/kF", 0);
        // right shooter gains
        SmartDashboard.putNumber("PID/RightShooter/kP", 0);
        SmartDashboard.putNumber("PID/RightShooter/kI", 0);
        SmartDashboard.putNumber("PID/RightShooter/kD", 0);
        SmartDashboard.putNumber("PID/RightShooter/kF", 0);
    }

    public void disableTuning() {
        isTuning = false;
    }

    /**
     * @return the speed of the shooter in RPM.
     */
    public double getAverageSpeed() {
        return (getLeftSpeed() + getRightSpeed()) / 2;
    }

    /**
     * @return the speed of the left shooter in RPM.
     */
    public double getLeftSpeed() {
        return leftTalon.getSelectedSensorVelocity() * 600.0
                / robotConstants.shooterConstants.LEFT_UNITS_PER_ROTATION;
    }

    /**
     * @return the speed of the right shooter in RPM.
     */
    public double getRightSpeed() {
        return rightTalon.getSelectedSensorVelocity() * 600.0
                / robotConstants.shooterConstants.LEFT_UNITS_PER_ROTATION;
    }

    public void resetEncoders() {
        leftTalon.setSelectedSensorPosition(0);
        rightTalon.setSelectedSensorPosition(0);
    }

    @Override
    public void periodic() {
        if (isTuning) {
            leftTalon.config_kP(0, SmartDashboard.getNumber(
                    "PID/LeftShooter/kP", 0), 0);
            leftTalon.config_kI(0, SmartDashboard.getNumber(
                    "PID/LeftShooter/kI", 0), 0);
            leftTalon.config_kD(0, SmartDashboard.getNumber(
                    "PID/LeftShooter/kD", 0), 0);
            leftTalon.config_kF(0, SmartDashboard.getNumber(
                    "PID/LeftShooter/kF", 0), 0);
            rightTalon.config_kP(0, SmartDashboard.getNumber(
                    "PID/RightShooter/kP", 0), 0);
            rightTalon.config_kI(0, SmartDashboard.getNumber(
                    "PID/RightShooter/kI", 0), 0);
            rightTalon.config_kD(0, SmartDashboard.getNumber(
                    "PID/RightShooter/kD", 0), 0);
            rightTalon.config_kF(0, SmartDashboard.getNumber(
                    "PID/RightShooter/kF", 0), 0);

        }
    }

    /**
     * @param RPM Revolution per minute
     * @return RPM in meter per second
     */
    public static double rpmToMeterPerSecond(double RPM) {
        return Units.rotationsPerMinuteToRadiansPerSecond(RPM) * robotConstants.shooterConstants.WHEEL_RADIUS;
    }

    /**
     * @param meterPerSecond speed to be converted
     * @return velocity in revolution per minute
     */
    public static double meterPerSecondToRPM(double meterPerSecond) {
        return Units.radiansPerSecondToRotationsPerMinute(meterPerSecond / robotConstants.shooterConstants.WHEEL_RADIUS);
    }
}
