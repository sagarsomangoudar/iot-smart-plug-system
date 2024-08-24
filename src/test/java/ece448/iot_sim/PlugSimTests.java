package ece448.iot_sim;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlugSimTests {

	@Test
	public void testInit() {
		PlugSim plug = new PlugSim("a");

		assertFalse(plug.isOn());
	}

	@Test
	public void testSwitchOn() {
		PlugSim plug = new PlugSim("a");

		plug.switchOn();

		assertTrue(plug.isOn());
	}

	@Test
	public void testSwitchOff() {
		PlugSim plug = new PlugSim("a");

		plug.switchOff();

		assertFalse(plug.isOn());
	}

	@Test
	public void testToggle() {
		PlugSim plug = new PlugSim("a");
		plug.switchOn();
		plug.toggle();
		assertFalse(plug.isOn());

	}

	@Test
	public void testgetName() {
		PlugSim plug = new PlugSim("a");
		
		assertTrue(plug.getName().equals("a"));
	}

	@Test
	public void testgetPower() {
		PlugSim plug = new PlugSim("a");
		plug.switchOn();
		plug.getPower();
	}

	@Test
	public void testmeasureZeroPower() {
		PlugSim plug = new PlugSim("a");
		plug.switchOff();
		plug.measurePower();	
	}

	@Test
	public void testmeasurePowerValue1() {
		PlugSim plug = new PlugSim("a");
		plug.switchOn();
		plug.updatePower(50);
		plug.measurePower(); 	
	}

	@Test
	public void testmeasurePowerValue2() {
		PlugSim plug = new PlugSim("a");
		plug.switchOn();
		plug.updatePower(200);
		plug.measurePower();
	}

	@Test
	public void testmeasurePowerValue3() {
		PlugSim plug = new PlugSim("a");
		plug.switchOn();
		plug.updatePower(400);
		plug.measurePower(); 	
	}

	@Test
	public void testname() {
		PlugSim plug = new PlugSim("xy.153");
		plug.switchOn();
		plug.measurePower(); 
	}
	
}
