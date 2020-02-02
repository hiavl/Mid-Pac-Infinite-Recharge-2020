/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;
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

  private final PWMVictorSPX frontleft = new PWMVictorSPX(0);
  private final PWMVictorSPX rearleft = new PWMVictorSPX(1);
  private final SpeedControllerGroup leftDrive = new SpeedControllerGroup(rearleft, frontleft);

  private final PWMVictorSPX frontright = new PWMVictorSPX(2);
  private final PWMVictorSPX rearright = new PWMVictorSPX(3);
  private final SpeedControllerGroup rightDrive = new SpeedControllerGroup(rearright, frontright);

  private final PWMVictorSPX winchLeft = new PWMVictorSPX(5);
  private final PWMVictorSPX winchRight = new PWMVictorSPX(6);
  private final SpeedControllerGroup winchDrive = new SpeedControllerGroup(winchLeft, winchRight);

  private final PWMVictorSPX shooterLeft = new PWMVictorSPX(7);
  private final PWMVictorSPX shooterRight = new PWMVictorSPX(8);
  private final SpeedControllerGroup shooterDrive = new SpeedControllerGroup(shooterLeft, shooterRight);

  private final PWMVictorSPX intakeFeed = new PWMVictorSPX(9);
  private final PWMVictorSPX intakeArm = new PWMVictorSPX(10);

  private final PWMVictorSPX verticalFeed = new PWMVictorSPX(11);

  private final PWMVictorSPX ctrlPanelRoller = new PWMVictorSPX(12);
  
  private final PWMVictorSPX hangRollerLeft = new PWMVictorSPX(13);
  private final PWMVictorSPX hangRollerRight = new PWMVictorSPX(14);
  private final SpeedControllerGroup hangRoller = new SpeedControllerGroup(hangRollerLeft, hangRollerRight);

  private final DifferentialDrive robotDrive = new DifferentialDrive(leftDrive, rightDrive);

  private final XboxController blackController = new XboxController(0);
  private final XboxController clearController = new XboxController(1);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

//    m_rightDrive.setInverted(false);
//    m_leftDrive.setInverted(false);

    final UsbCamera usbCam = CameraServer.getInstance().startAutomaticCapture("USB cam", "/dev/video0");
    usbCam.setResolution(320, 240);
    usbCam.setFPS(60);
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

    robotDrive.setSafetyEnabled(false);

    robotDrive.arcadeDrive(-0.5, 0.0);
    Timer.delay(2.0);

    robotDrive.arcadeDrive(0.0, 0.0);
    Timer.delay(2.0);

    robotDrive.arcadeDrive(0.5, 0.0);
    Timer.delay(2.0);

    robotDrive.arcadeDrive(0.0, 0.0);
    Timer.delay(2.0);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
    robotDrive.setSafetyEnabled(false);
    
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
    robotDrive.tankDrive(clearController.getY(Hand.kLeft), clearController.getY(Hand.kRight));
    Timer.delay(0.005);

    if(clearController.getAButton()) {

    }
    if(clearController.getBButton()) {

    }
    if(clearController.getXButton()) {

    }
    if(clearController.getYButton()) {

    }
    if(clearController.getAButton()) {

    }
    if(clearController.getBackButton()) {
    }

    if(clearController.getStartButton()) {
    }

    
    if(blackController.getAButton()) {

    }
    if(blackController.getBButton()) {
    }

    if(blackController.getXButton()) {

    }
    if(blackController.getYButton()) {

    }
    if(blackController.getAButton()) {

    }
    if(blackController.getBackButton()) {
    }

    if(blackController.getStartButton()) {

    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
