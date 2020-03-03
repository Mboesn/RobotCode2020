package frc.robot.autonomus;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.motion_profiling.AutoPath;
import frc.robot.motion_profiling.FollowPath;
import frc.robot.subsystems.intakeopener.IntakeAngle;
import frc.robot.subsystems.intakeopener.SetIntakeAngle;

public class OpenIntakeAndFollowPath extends ParallelCommandGroup {
    private static final double kWaitToOpenIntakeTime = 0.5;

    /**
     * Follows a path while opening the intake
     */
    public OpenIntakeAndFollowPath(AutoPath autoPath) {
        addCommands(
            sequence(
                new WaitCommand(kWaitToOpenIntakeTime),
                new SetIntakeAngle(IntakeAngle.OpenForIntake)
            ),
            new FollowPath(autoPath)
        );
    }
}
