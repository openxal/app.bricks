/**
 * TestScenario.java
 *
 * @author Christopher K. Allen
 * @since  Nov 9, 2011
 *
 */

/**
 * TestScenario.java
 *
 * @author  Christopher K. Allen
 * @since	Nov 9, 2011
 */
package xal.sim.scenario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import xal.model.IAlgorithm;
import xal.model.ModelException;
import xal.model.alg.EnvTrackerAdapt;
import xal.model.alg.EnvelopeTracker;
import xal.model.alg.Tracker;
import xal.model.alg.TrackerAdaptive;
import xal.model.probe.EnvelopeProbe;
import xal.model.probe.ProbeFactory;
import xal.smf.Accelerator;
import xal.smf.AcceleratorSeq;
import xal.smf.data.XMLDataManager;
import xal.tools.beam.CovarianceMatrix;

/**
 * Testing scenario generation for the Open XAL online model.
 *
 * @author Christopher K. Allen
 * @since   Nov 9, 2011
 */
public class TestScenario {

    
    /** URL to the accelerator configuration file */
    public static final String      STR_URL_ACCL_CFG = "core/test/resources/config/main.xal";

    /** Accelerator sequence used for testing */
    public static final String     STR_ACCL_SEQ_ID = "MEBT";
    
    
    
    /**
     *
     * @throws java.lang.Exception
     *
     * @author Christopher K. Allen
     * @since  Nov 9, 2011
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * xal.sim.scenario
     *
     * @author Christopher K. Allen
     * @since  Nov 9, 2011
     *
     */

    /**
     *
     * @throws java.lang.Exception
     *
     * @author Christopher K. Allen
     * @since  Nov 9, 2011
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link xal.sim.scenario.Scenario#newScenarioFor(xal.smf.AcceleratorSeq)}.
     */
    @Test
    public void testNewScenarioForAcceleratorSeq() {

        Accelerator     accel = XMLDataManager.acceleratorWithPath(STR_URL_ACCL_CFG);
        AcceleratorSeq  seq   = accel.getSequence(STR_ACCL_SEQ_ID);
        
        try {
            Scenario        model = Scenario.newScenarioFor(seq);
            
        } catch (ModelException e) {

            fail("Unable to create Scenario");
            e.printStackTrace();
        }
        
    }

    /**
     * Test method for {@link xal.sim.scenario.Scenario#run()}.
     */
    @Test
    public void testRunViaNewFromEditContext() {
        Accelerator     accel = XMLDataManager.acceleratorWithPath(STR_URL_ACCL_CFG);
        AcceleratorSeq  seq   = accel.getSequence(STR_ACCL_SEQ_ID);
        
        try {
            Scenario        model = Scenario.newScenarioFor(seq);
            IAlgorithm      algor = Tracker.newFromEditContext(seq);
            EnvelopeProbe   probe = ProbeFactory.getEnvelopeProbe(seq, algor);
            
            probe.initialize();
            model.setProbe( probe );
            model.resync();
            
            model.run();
            
        } catch (ModelException e) {

            fail("Unable to run Scenario");
            e.printStackTrace();
        }
        
    }

    /**
     * Test method for {@link xal.sim.scenario.Scenario#run()}.
     */
    @Test
    public void testRunViaLoad() {
        Accelerator     accel = XMLDataManager.acceleratorWithPath(STR_URL_ACCL_CFG);
        AcceleratorSeq  seq   = accel.getSequence(STR_ACCL_SEQ_ID);
        
        try {
            Scenario        model = Scenario.newScenarioFor(seq);

            IAlgorithm      algor = new EnvTrackerAdapt();
            algor.load(seq.getEntranceID(), accel.editContext());
            
            EnvelopeProbe   probe = ProbeFactory.getEnvelopeProbe(seq, algor);
            probe.initialize();
            
            model.setProbe( probe );
            model.resync();
            model.run();
            
        } catch (ModelException e) {

            fail("Unable to run Scenario");
            e.printStackTrace();
        }
        
    }

    /**
     * Test method for {@link xal.sim.scenario.Scenario#getProbe()}.
     */
    @Test
    public void testGetProbe() {

    }

}
