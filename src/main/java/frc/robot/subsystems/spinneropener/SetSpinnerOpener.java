package frc.robot.subsystems.spinneropener;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.RobotConstants;

import static frc.robot.Robot.spinnerOpener;

public class SetSpinnerOpener extends CommandBase {
  private boolean open;
  private double startTime;

  /**
   * This command opens/closes the Spinner Opener Subsystem.
   * 
   * @param open if true it will open the spinner opener if false it will close it
   */
  public SetSpinnerOpener(boolean open) {
    addRequirements(spinnerOpener);
    this.open = open;
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {
    if (open)
      spinnerOpener.move(RobotConstants.SpinnerOpener.kDefaultOpenPower);
    else
      spinnerOpener.move(-RobotConstants.SpinnerOpener.kDefaultOpenPower);
  }

  @Override
  public void end(boolean interrupted) {
    spinnerOpener.move(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean timeLimit = Timer.getFPGATimestamp() >= startTime + RobotConstants.SpinnerOpener.kTimeLimit;
    if (open)
      return spinnerOpener.isTopSwitchPressed() || timeLimit;
    else
      return spinnerOpener.isBottomSwitchPressed() || timeLimit;
  }
}
