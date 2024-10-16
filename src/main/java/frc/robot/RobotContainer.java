// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.AngleConstants.*;
import static frc.robot.Constants.OperatorConstants.*;
import static frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.MoveFlipperToPosition;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.DriveSystem;
import frc.robot.subsystems.Flipper;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private Joystick joy;
  private ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private CommandXboxController m_driverController;
  private MoveFlipperToPosition moveToStart;
  private MoveFlipperToPosition moveToFeed;
  private MoveFlipperToPosition moveToAmp;
  private XboxController operator;
  private POVButton startingPosition;
  private POVButton feed;
  private POVButton amp;

  private Flipper flipper;
  private double angle;

  private DriveSystem mecanumDrive;
  private DriveWithJoystick driveWithJoystick;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    joy = new Joystick(JOYSTICK_PORT);
    operator = new XboxController(OPERATOR_PORT);
    mecanumDrive = new DriveSystem();

    flipper = new Flipper();
    moveToStart = new MoveFlipperToPosition(STARTING_ANGLE, flipper);
    moveToFeed = new MoveFlipperToPosition(FEED_ANGLE, flipper);
    moveToAmp = new MoveFlipperToPosition(AMP_ANGLE, flipper);

    

    driveWithJoystick = new DriveWithJoystick(mecanumDrive, joy);
    mecanumDrive.setDefaultCommand(driveWithJoystick);
    

    

    startingPosition = new POVButton(operator, 0);
    feed = new POVButton(operator, 90);
    amp = new POVButton(operator, 180);

    configureBindings();
  }
 
  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition).onTrue(new ExampleCommand(m_exampleSubsystem));
    //startingPosition.onTrue(Flipper.flip);
   // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    startingPosition.onTrue(moveToStart);
    feed.onTrue(moveToFeed);
    amp.onTrue(moveToAmp);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(mecanumDrive);
  }

  public Joystick getJoy() {
    
    return joy;
  }
}
