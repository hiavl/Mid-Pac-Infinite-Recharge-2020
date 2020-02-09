/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.cscore.UsbCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Sets up left and right drive motors
  private final PWMVictorSPX m_leftMotor = new PWMVictorSPX(1);
  private final PWMVictorSPX m_rightMotor = new PWMVictorSPX(2);

  // Sets up winch motor
  private final PWMVictorSPX winchMotor = new PWMVictorSPX(3);

  // Sets up shooter motor
  private final PWMVictorSPX shooterMotor = new PWMVictorSPX(4);

  // Sets up intake feed & intake arm motor
  private final PWMVictorSPX intakeFeed = new PWMVictorSPX(5);
  private final PWMVictorSPX intakeArm = new PWMVictorSPX(6);

  // Sets up vertical feed motor
  private final PWMVictorSPX verticalFeed = new PWMVictorSPX(7);

  // Sets up control panel roller motor
  private final PWMVictorSPX ctrlPanelRoller = new PWMVictorSPX(8);

  // Combines left and right drive motor into a single variable (DifferentialDrive)
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  //Differentiates XboxControllers
  private final XboxController clearController = new XboxController(0);
  private final XboxController blackController = new XboxController(1);

  //Sets up limit switches on winch
  private final DigitalInput botSwitch = new DigitalInput(0);
  private final DigitalInput topSwitch = new DigitalInput(1);

  //Sets up autonomous switches
  private final DigitalInput autoOne = new DigitalInput(6);
  private final DigitalInput autoTwo = new DigitalInput(7);
  private final DigitalInput autoThree = new DigitalInput(8);
  private final DigitalInput autoFour = new DigitalInput(9);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // Inverts the 
    m_leftMotor.setInverted(true);
    m_rightMotor.setInverted(true);

    final UsbCamera usbCam = CameraServer.getInstance().startAutomaticCapture("USB cam", "/dev/video0");
    usbCam.setResolution(320, 240);
    usbCam.setFPS(60);

//    winchMotor.setNeutralMode(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    m_robotDrive.setSafetyEnabled(false);

    if(!autoOne.get()) { //first auto
      
      shooterMotor.set(1.0);
      Timer.delay(2.0);
      verticalFeed.set(1.0);
      Timer.delay(5.0);
      shooterMotor.set(0.0);
      verticalFeed.set(0.0);
      Timer.delay(0.5);

      m_robotDrive.tankDrive(-0.5, 0.5);
      Timer.delay(1.0);
      m_robotDrive.tankDrive(0.0, 0.0);
      Timer.delay(1.0);

      m_robotDrive.tankDrive(0.5, 0.5);
      Timer.delay(4.0);
      m_robotDrive.tankDrive(0.0, 0.0);
      Timer.delay(0.5);

    } else if(!autoTwo.get()) { //second auto
      
      shooterMotor.set(1.0);
      Timer.delay(2.0);
      verticalFeed.set(1.0);
      Timer.delay(5.0);
      shooterMotor.set(0.0);
      verticalFeed.set(0.0);
      Timer.delay(0.5);

      m_robotDrive.tankDrive(0.5, 0.5);
      Timer.delay(4.0);
      m_robotDrive.tankDrive(0.0, 0.0);
      Timer.delay(0.5);

    } else if(!autoThree.get()) {
      m_robotDrive.tankDrive(-0.5, -0.5);
      Timer.delay(1.0);
      m_robotDrive.tankDrive(0.0, 0.0);
      Timer.delay(1.0);

      m_robotDrive.tankDrive(-0.5, 0.5);
      Timer.delay(0.8);
      m_robotDrive.tankDrive(0.0, 0.0);
      Timer.delay(1.0);

      shooterMotor.set(1.0);
      Timer.delay(2.0);
      verticalFeed.set(1.0);
      Timer.delay(5.0);
      shooterMotor.set(0.0);
      verticalFeed.set(0.0);
      Timer.delay(0.5);

      m_robotDrive.tankDrive(0.5, -0.5);
      Timer.delay(0.9);
      m_robotDrive.tankDrive(0.0, 0.0);
      Timer.delay(1.0);

      m_robotDrive.tankDrive(0.5, 0.5);
      Timer.delay(6.0);
      m_robotDrive.tankDrive(0.0, 0.0);
      Timer.delay(0.5);

    } else if(!autoFour.get()) {

    } else { //if we don't want to shoot
      m_robotDrive.tankDrive(0.5, 0.5);
      Timer.delay(5.0);
    }

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
    m_robotDrive.setSafetyEnabled(false);
    
    switch (m_autoSelected) {
      case kCustomAuto:
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    // drive
    m_robotDrive.tankDrive(clearController.getY(Hand.kLeft), clearController.getY(Hand.kRight));

    
    // winch
    if(clearController.getAButton() && topSwitch.get()) {
      winchMotor.set(-1.0);
    } else if(clearController.getBButton() && botSwitch.get()) {
      winchMotor.set(1.0);
    } else {
      winchMotor.set(0.0);
    }

    // intake arm
    if(clearController.getYButton()) {
      intakeArm.set(-1.0);
    } else if(clearController.getXButton()) {
      intakeArm.set(1.0);
    } else {
      intakeArm.set(0.0);
    }

    // intake feed
    if(clearController.getBumper(Hand.kLeft)) {
      intakeFeed.set(0.6);
    } else if(clearController.getBumper(Hand.kRight)) {
      intakeFeed.set(-0.6);
    } else {
      intakeFeed.set(0.0);
    }

    // vertical feed
    if(blackController.getAButton()) {
      verticalFeed.set(-1.0);
    } else if(blackController.getBButton()) {
      verticalFeed.set(1.0);
    } else {
      verticalFeed.set(0.0);
    }

    // control panel roller
    if(blackController.getXButton()) {
      ctrlPanelRoller.set(-1.0);
    } else if(blackController.getYButton()) {
      ctrlPanelRoller.set(1.0);
    } else {
      ctrlPanelRoller.set(0.0);
    }

    // shooter
    if(blackController.getBumper(Hand.kLeft)) {
      shooterMotor.set(1.0);
    } else {
      shooterMotor.set(0.0);
    }
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
