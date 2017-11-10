package nl.ordina.jtech.arjava.commands;

import org.junit.Test;

import static org.junit.Assert.*;

public class ATCommandTest {
    @Test
    public void testCalibrateCommand() throws Exception {
        ATCommand command1 = new CalibrateCommand(CalibrationDevice.Magnetometer);
        command1.setSequenceNumber(1);
        ATCommand command2 = new CalibrateCommand(CalibrationDevice.Magnetometer);
        command2.setSequenceNumber(234987);
        ATCommand command3 = new CalibrateCommand(CalibrationDevice.Magnetometer);
        command3.setSequenceNumber(-1233);

        assertEquals("AT*CALIB=1,0\r", command1.getCommandString());
        assertEquals("AT*CALIB=234987,0\r", command2.getCommandString());
        assertEquals("AT*CALIB=-1233,0\r", command3.getCommandString());
    }

    @Test
    public void testConfigurationCommand() throws Exception {
        ATCommand command1 = new ConfigurationCommand("configoption1", "configoption2");
        command1.setSequenceNumber(1);
        ATCommand command2 = new ConfigurationCommand("abc_123", "gs");
        command2.setSequenceNumber(2);

        assertEquals("AT*CONFIG=1,\"configoption1\",\"configoption2\"\r", command1.getCommandString());
        assertEquals("AT*CONFIG=2,\"abc_123\",\"gs\"\r", command2.getCommandString());
    }

    @Test
    public void testConfigurationIdCommand() throws Exception {
        ATCommand command1 = new ConfigurationIdCommand("1", "1", "1");
        command1.setSequenceNumber(1);
        ATCommand command2 = new ConfigurationIdCommand("2203", "owner", "250");
        command2.setSequenceNumber(3);

        assertEquals("AT*CONFIG_IDS=1,\"1\",\"1\",\"1\"\r", command1.getCommandString());
        assertEquals("AT*CONFIG_IDS=3,\"2203\",\"owner\",\"250\"\r", command2.getCommandString());
    }

    @Test
    public void testEmergencyCommand() throws Exception {
        ATCommand command1 = new EmergencyCommand();
        command1.setSequenceNumber(1);
        ATCommand command2 = new EmergencyCommand();
        command2.setSequenceNumber(2);
        ATCommand command3 = new EmergencyCommand();
        command3.setSequenceNumber(-25);

        assertEquals("AT*REF=1,290717952\r", command1.getCommandString());
        assertEquals("AT*REF=2,290717952\r", command2.getCommandString());
        assertEquals("AT*REF=-25,290717952\r", command3.getCommandString());
    }

    @Test
    public void testFlatTrimCommand() throws Exception {
        ATCommand command1 = new FlatTrimCommand();
        command1.setSequenceNumber(1);
        ATCommand command2 = new FlatTrimCommand();
        command2.setSequenceNumber(123334);
        ATCommand command3 = new FlatTrimCommand();
        command3.setSequenceNumber(-120938);

        assertEquals("AT*FTRIM=1\r", command1.getCommandString());
        assertEquals("AT*FTRIM=123334\r", command2.getCommandString());
        assertEquals("AT*FTRIM=-120938\r", command3.getCommandString());
    }

    @Test
    public void testLandCommand() throws Exception {
        ATCommand command1 = new LandCommand();
        command1.setSequenceNumber(1);
        ATCommand command2 = new LandCommand();
        command2.setSequenceNumber(2);
        ATCommand command3 = new LandCommand();
        command3.setSequenceNumber(3);

        assertEquals("AT*REF=1,290717696\r", command1.getCommandString());
        assertEquals("AT*REF=2,290717696\r", command2.getCommandString());
        assertEquals("AT*REF=3,290717696\r", command3.getCommandString());
    }

    @Test
    public void testProgressiveCommand() throws Exception {
        ATCommand command1 = new ProgressiveCommand(ControlMode.Hover,
                0.1F, 0, 0, 0);
        command1.setSequenceNumber(1);
        ATCommand command2 = new ProgressiveCommand(ControlMode.Progressive,
                0, 0.2F, 0, 0);
        command2.setSequenceNumber(2);
        ATCommand command3 = new ProgressiveCommand(ControlMode.CombinedYaw,
                0, 0, -0.3F, 0);
        command3.setSequenceNumber(3);
        ATCommand command4 = new ProgressiveCommand(ControlMode.AbsoluteControl,
                0, 0, 0, 0.4F);
        command4.setSequenceNumber(4);

        assertEquals("AT*PCMD=1,0,1036831949,0,0,0\r", command1.getCommandString());
        assertEquals("AT*PCMD=2,1,0,1045220557,0,0\r", command2.getCommandString());
        assertEquals("AT*PCMD=3,2,0,0,-1097229926,0\r", command3.getCommandString());
        assertEquals("AT*PCMD=4,4,0,0,0,1053609165\r", command4.getCommandString());

    }

    @Test
    public void testProgressiveMagCommand() throws Exception {
        ATCommand command1 = new ProgressiveMagCommand(ControlMode.Progressive,
                0, 0, 0, 0, 0.1F, 0.5F);
        command1.setSequenceNumber(1);
        ATCommand command2 = new ProgressiveMagCommand(ControlMode.Progressive,
                0, 0, 0, 0, -0.3F, -0.5F);
        command2.setSequenceNumber(1);

        assertEquals("AT*PCMD_MAG=1,1,0,0,0,0,1036831949,1056964608\r", command1.getCommandString());
        assertEquals("AT*PCMD_MAG=1,1,0,0,0,0,-1097229926,-1090519040\r", command2.getCommandString());
    }

    @Test
    public void testTakeOffCommand() throws Exception {
        ATCommand command1 = new TakeOffCommand();
        command1.setSequenceNumber(1);
        ATCommand command2 = new TakeOffCommand();
        command2.setSequenceNumber(2);
        ATCommand command3 = new TakeOffCommand();
        command3.setSequenceNumber(3);

        assertEquals("AT*REF=1,290718208\r", command1.getCommandString());
        assertEquals("AT*REF=2,290718208\r", command2.getCommandString());
        assertEquals("AT*REF=3,290718208\r", command3.getCommandString());
    }

    @Test
    public void testWatchdogCommand() throws Exception {
        ATCommand command1 = new WatchdogCommand();
        command1.setSequenceNumber(1);
        ATCommand command2 = new WatchdogCommand();
        command2.setSequenceNumber(994);
        ATCommand command3 = new WatchdogCommand();
        command3.setSequenceNumber(-654);

        assertEquals("AT*COMWDG=1\r", command1.getCommandString());
        assertEquals("AT*COMWDG=994\r", command2.getCommandString());
        assertEquals("AT*COMWDG=-654\r", command3.getCommandString());
    }
}