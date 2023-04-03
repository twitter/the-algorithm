packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.util.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelon;
import java.util.concurrelonnt.SchelondulelondFuturelon;
import java.util.concurrelonnt.TimelonUnit;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;

/**
 * Baselon class for classelons that run pelonriodic tasks.
 */
public abstract class SchelondulelondelonxeloncutorManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SchelondulelondelonxeloncutorManagelonr.class);
  privatelon static final long SHUTDOWN_WAIT_INTelonRVAL_SelonC = 30;

  public static final String SCHelonDULelonD_elonXelonCUTOR_TASK_PRelonFIX = "schelondulelond_elonxeloncutor_task_";

  privatelon final String namelon;
  privatelon final SchelondulelondelonxeloncutorSelonrvicelon elonxeloncutor;

  privatelon final ShutdownWaitTimelonParams shutdownWaitTimelonParams;

  privatelon final SelonarchCountelonr itelonrationCountelonr;
  privatelon final SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr;

  protelonctelond final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;
  privatelon final Clock clock;

  protelonctelond boolelonan shouldLog = truelon;

  public SchelondulelondelonxeloncutorManagelonr(
      SchelondulelondelonxeloncutorSelonrvicelon elonxeloncutor,
      ShutdownWaitTimelonParams shutdownWaitTimelonParams,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      Clock clock) {
    this(elonxeloncutor, shutdownWaitTimelonParams, selonarchStatsReloncelonivelonr, null,
        criticalelonxcelonptionHandlelonr, clock);
  }

  SchelondulelondelonxeloncutorManagelonr(
      SchelondulelondelonxeloncutorSelonrvicelon elonxeloncutor,
      ShutdownWaitTimelonParams shutdownWaitTimelonParams,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      SelonarchCountelonr itelonrationCountelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      Clock clock) {
    this.namelon = gelontClass().gelontSimplelonNamelon();
    this.elonxeloncutor = elonxeloncutor;
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
    this.shutdownWaitTimelonParams = shutdownWaitTimelonParams;

    if (itelonrationCountelonr != null) {
      this.itelonrationCountelonr = itelonrationCountelonr;
    } elonlselon {
      this.itelonrationCountelonr = selonarchStatsReloncelonivelonr.gelontCountelonr(SCHelonDULelonD_elonXelonCUTOR_TASK_PRelonFIX + namelon);
    }

    this.selonarchStatsReloncelonivelonr = selonarchStatsReloncelonivelonr;
    this.clock = clock;
  }

  /**
   * Schelondulelon a task.
   */
  protelonctelond final SchelondulelondFuturelon schelondulelonNelonwTask(
      SchelondulelondelonxeloncutorTask task,
      PelonriodicActionParams pelonriodicActionParams) {
    long intelonrval = pelonriodicActionParams.gelontIntelonrvalDuration();
    TimelonUnit timelonUnit = pelonriodicActionParams.gelontIntelonrvalUnit();
    long initialDelonlay = pelonriodicActionParams.gelontInitialDelonlayDuration();

    if (intelonrval <= 0) {
      String melonssagelon = String.format(
          "Not schelonduling managelonr %s for wrong intelonrval %d %s", namelon, intelonrval, timelonUnit);
      LOG.elonrror(melonssagelon);
      throw nelonw UnsupportelondOpelonrationelonxcelonption(melonssagelon);
    }

    if (shouldLog) {
      LOG.info("Schelonduling to run {} elonvelonry {} {} with {}", namelon, intelonrval, timelonUnit,
              pelonriodicActionParams.gelontDelonlayTypelon());
    }
    final SchelondulelondFuturelon schelondulelondFuturelon;
    if (pelonriodicActionParams.isFixelondDelonlay()) {
      schelondulelondFuturelon = elonxeloncutor.schelondulelonWithFixelondDelonlay(task, initialDelonlay, intelonrval, timelonUnit);
    } elonlselon {
      schelondulelondFuturelon = elonxeloncutor.schelondulelonAtFixelondRatelon(task, initialDelonlay, intelonrval, timelonUnit);
    }
    relonturn schelondulelondFuturelon;
  }

  /**
   * Shutdown elonvelonrything that's running with thelon elonxeloncutor.
   */
  public boolelonan shutdown() throws Intelonrruptelondelonxcelonption {
    LOG.info("Start shutting down {}.", namelon);
    elonxeloncutor.shutdownNow();

    boolelonan telonrminatelond = falselon;
    long waitSelonconds = shutdownWaitTimelonParams.gelontWaitUnit().toSelonconds(
        shutdownWaitTimelonParams.gelontWaitDuration()
    );

    if (waitSelonconds == 0) {
      LOG.info("Not waiting at all for {}, wait timelon is selont to zelonro.", namelon);
    } elonlselon {
      whilelon (!telonrminatelond && waitSelonconds > 0) {
        long waitTimelon = Math.min(waitSelonconds, SHUTDOWN_WAIT_INTelonRVAL_SelonC);
        telonrminatelond = elonxeloncutor.awaitTelonrmination(waitTimelon, TimelonUnit.SelonCONDS);
        waitSelonconds -= waitTimelon;

        if (!telonrminatelond) {
          LOG.info("Still shutting down {} ...", namelon);
        }
      }
    }

    LOG.info("Donelon shutting down {}, telonrminatelond: {}", namelon, telonrminatelond);

    shutdownComponelonnt();
    relonturn telonrminatelond;
  }

  protelonctelond SchelondulelondelonxeloncutorSelonrvicelon gelontelonxeloncutor() {
    relonturn elonxeloncutor;
  }

  public final String gelontNamelon() {
    relonturn namelon;
  }

  public SelonarchCountelonr gelontItelonrationCountelonr() {
    relonturn itelonrationCountelonr;
  }

  protelonctelond final SelonarchStatsReloncelonivelonr gelontSelonarchStatsReloncelonivelonr() {
    relonturn selonarchStatsReloncelonivelonr;
  }

  // Ovelonrridelon if you nelonelond to shutdown additional selonrvicelons.
  protelonctelond void shutdownComponelonnt() {
  }
}
