package frc.robot.commands.command_groups;


import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.constants.RobotConstants.ClimbConstants;
import frc.robot.constants.RobotConstants.DrivetrainConstants;
import frc.robot.subsystems.climb.MoveClimbAndHook;
import frc.robot.subsystems.climb.SetHookHeight;
import frc.robot.subsystems.drivetrain.DriveWithXbox;
import java.util.function.BooleanSupplier;

import static frc.robot.Robot.*;

public class AutoClimb extends SequentialCommandGroup {

    public static final double kJoystickDeadband = 0.08;
    public static final double kDrivetrainMovementTime = 0.1;

    public AutoClimb(BooleanSupplier goDownButton) {
        addCommands(
            deadline(
                sequence(
                    new SetHookHeight().withInterrupt(() ->
                        Math.abs(oi.getDriverXboxController().getY(Hand.kRight)) > kJoystickDeadband),
                    new MoveClimbAndHook(() -> oi.getDriverXboxController().getY(Hand.kRight),
                        () -> oi.getDriverXboxController().getAButton() ? ClimbConstants.kDefaultClimbPower : 0)
                ),
                new DriveWithXbox(() -> oi.getDriverXboxController().getX(Hand.kLeft),
                    () -> oi.getDriverXboxController().getDeltaTriggers())
            ).withInterrupt(goDownButton),
            deadline(
                new SetHookHeight(0),
                sequence(
                    new WaitUntilCommand(climb::isHookInStall),
                    new RunCommand(() -> drivetrain.tankDrive(DrivetrainConstants.kMoveWhenClimbingPower,
                        DrivetrainConstants.kMoveWhenClimbingPower)).
                        withTimeout(kDrivetrainMovementTime).andThen(drivetrain::stopMove)
                )
            )
        );
    }
}