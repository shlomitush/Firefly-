package frc.robot;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Climb.ClimbDownCommand;
import frc.robot.commands.Climb.ClimbUpCommand;
import frc.robot.commands.Drive.*;
import frc.robot.commands.Intake.FeederIn;
import frc.robot.commands.Intake.FloorIn;
import frc.robot.commands.ThrowWheel.BackALittle;
import frc.robot.commands.ThrowWheel.StopThrow;
import frc.robot.commands.ThrowWheel.Throw;
import frc.robot.commands.ThrowWheel.ThrowAMP;
import frc.robot.subsystems.*;

//hey
public class RobotContainer {


  //  private final Intake m_intake = new Intake();
  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final DriveTrain m_driveTrain = new DriveTrain();
  private final FlyWheel flyWheel = new FlyWheel();
  private final FloorIntake floorIntake = new FloorIntake();
  private final Climb climb = new Climb();
  private final PollyIntake pollyIntake = new PollyIntake();

//  private final driveTrainCommand m_driveTrainCommand = new driveTrainCommand(m_driveTrain, m_driverController::getLeftY, ()-> -m_driverController.getRawAxis(2));
//  private final IntakeCommand m_intakeCommand = new IntakeCommand(m_Floor_intake);

  private final DriveTrainCommand m_driveTrainCommand = new DriveTrainCommand(m_driveTrain, m_driverController::getLeftY, ()-> -m_driverController.getRawAxis(4));
  private final ClimbUpCommand climbUpCommand = new ClimbUpCommand(climb);
  private final ClimbDownCommand climbDownCommand = new ClimbDownCommand(climb);

  private final FeederIn feederIn = new FeederIn(pollyIntake, flyWheel);


  private final FloorIn floorIn = new FloorIn(pollyIntake, floorIntake);
  private final Throw aThrow = new Throw(pollyIntake, flyWheel);
  private final BackALittle backALittle = new BackALittle(pollyIntake, flyWheel);
  private final DownOut downOut = new DownOut(pollyIntake, flyWheel, floorIntake);
  private final UpOut upOut = new UpOut(pollyIntake, flyWheel, floorIntake);
  private final SlowUpOut slowUpOut = new SlowUpOut(pollyIntake, flyWheel, floorIntake);
  private final StopThrow stopThrow = new StopThrow(pollyIntake, flyWheel);
  private final AlignToNote alignToNote = new AlignToNote(m_driveTrain);
  private final DriveToNote driveToNote = new DriveToNote(m_driveTrain, floorIntake);
  private final ThrowAMP throwAMP = new ThrowAMP(pollyIntake, flyWheel);
  private final DriveXCentim driveXCentim = new DriveXCentim(m_driveTrain, 10);







  public RobotContainer() {
    configureBindings();
//    SmartDashboard.putData(m_driveTrainCommand);
  }


  private void configureBindings() {
//    m_driveTrain.setDefaultCommand(new driveTrainCommand(m_driveTrain, m_driverController::getLeftY, ()-> -m_driverController.getRawAxis(2)));
    m_driverController.rightBumper().whileTrue(backALittle.withTimeout(0.5).andThen(aThrow));
    m_driverController.rightBumper().onFalse(stopThrow);

    m_driverController.y().whileTrue(floorIn);
    m_driverController.x().whileTrue(feederIn);
    m_driverController.b().whileTrue(throwAMP);
    m_driverController.rightTrigger().whileTrue(upOut);
    m_driverController.leftTrigger().whileTrue(downOut);
    m_driverController.leftBumper().whileTrue(driveXCentim);
//    m_driverController.rightTrigger().onTrue(climbUpCommand);
//    m_driverController.leftTrigger().onTrue(climbDownCommand);

    m_driveTrain.setDefaultCommand(m_driveTrainCommand);

    m_driverController.a().whileTrue(alignToNote.repeatedly().withTimeout(1).andThen(driveToNote));
//    m_driverController.a().whileTrue(alignToNote.withTimeout(1).andThen(driveToNote));

//    m_driverController.a().onTrue(alignToNote.andThen(driveToNote).withTimeout(2));

  }




  public Command getAutonomousCommand() {

//    return Autos.exampleAuto(m_exampleSubsystem);
    return new TurnToAngle(m_driveTrain, 90);
//    return new FlyWheelCommand(flyWheel);
  }
}
