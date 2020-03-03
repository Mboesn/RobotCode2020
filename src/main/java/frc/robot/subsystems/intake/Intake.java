package frc.robot.subsystems.intake;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import frc.robot.constants.RobotConstants.IntakeConstants;
import frc.robot.constants.RobotMap;
import frc.robot.subsystems.OverridableSubsystem;

/**
 * Intake collects POWER CELLS into the robot using one motor rolling strips.
 */
public class Intake extends OverridableSubsystem { // implements Loggable {
    private CANSparkMax sparkMax;
    private CANEncoder encoder;

    public Intake() {
        sparkMax = new CANSparkMax(RobotMap.kCellIntakeSparkMax, MotorType.kBrushless);
        sparkMax.setInverted(IntakeConstants.kIsInverted);
        sparkMax.setIdleMode(IdleMode.kCoast);
        sparkMax.setSmartCurrentLimit(IntakeConstants.kCurrentLimit);
        sparkMax.getEncoder();
        sparkMax.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 65534);
        sparkMax.burnFlash();
        
        encoder = sparkMax.getEncoder();
    }

    /**
     * Overrides the cell collector motor.
     */
    @Override
    public void overriddenMove(double power) {
        sparkMax.set(power);
    }

    //@Log(name = "Intake/Output Current")
    public double getOutputCurrent() {
        return sparkMax.getOutputCurrent();
    }

    public boolean getIsInStall() {
        return getOutputCurrent() > IntakeConstants.kOnStallLimit;
    }

    public double getPosition() {
        return encoder.getPosition();
    }
}
