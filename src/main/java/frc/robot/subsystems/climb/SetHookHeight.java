package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.RobotConstants.ClimbConstants;
import java.util.function.DoubleSupplier;

import static frc.robot.Robot.climb;


public class SetHookHeight extends CommandBase {

    private final DoubleSupplier heightSupplier;

    /**
     * Moves the hook to be in the shield generator switch height
     * aka {@link ClimbConstants#kDesiredHookHeight}
     */
    public SetHookHeight(){
        this(ClimbConstants.kDesiredHookHeight);
    }

    public SetHookHeight(double height) {
        this(() -> height);
    }
    public SetHookHeight(DoubleSupplier heightSupplier) {
        addRequirements(climb);
        this.heightSupplier = heightSupplier;
    }

    @Override
    public void execute() {
        double error = getError();
        double output = Math.copySign(
            error < ClimbConstants.kCloseToHeightError ?
                ClimbConstants.kCloseToHeightHookPower : ClimbConstants.kHookPower,
            getError());
        climb.setHookPower(output);
    }

    @Override
    public boolean isFinished() {
        return
            Math.abs(getError()) < ClimbConstants.kHookTolerance;
    }

    @Override
    public void end(boolean interrupted) {
        climb.setHookPower(0);
    }

    private double getError() {
        return heightSupplier.getAsDouble() - climb.getHookRotations();
    }
}
