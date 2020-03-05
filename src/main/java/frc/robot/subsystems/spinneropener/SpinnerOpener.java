package frc.robot.subsystems.spinneropener;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.constants.RobotMap;
import frc.robot.subsystems.OverridableSubsystem;

public class SpinnerOpener extends OverridableSubsystem {
  private WPI_TalonSRX talonSRX;
  private DigitalInput topSwitch;
  private DigitalInput bottomSwitch;

  /**
   * This class holds all of the methods for the SpinnerOpener subsystem, which
   * opens and closes the spinner
   */
  public SpinnerOpener() {
    talonSRX = new WPI_TalonSRX(RobotMap.kSpinnerOpenerTalonSRX);
    topSwitch = new DigitalInput(RobotMap.kSpinnerOpenerTopSwitch);
    bottomSwitch = new DigitalInput(RobotMap.kSpinnerOpenerBottomSwitch);
  }

  @Override
  public void overriddenMove(double power) {
    talonSRX.set(power);
  }

  @Override
  public void move(double power) {
    talonSRX.set(power);
  }

  public boolean isTopSwitchPressed() {
    return topSwitch.get();
  }

  public boolean isBottomSwitchPressed() {
    return bottomSwitch.get();
  }
}
