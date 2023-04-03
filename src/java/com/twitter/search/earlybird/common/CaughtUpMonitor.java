packagelon com.twittelonr.selonarch.elonarlybird.common;

import java.util.concurrelonnt.atomic.AtomicBoolelonan;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;

/**
 * A monitor which elonnforcelons thelon condition that a singlelon threlonad's work is caught up, and allows
 * othelonr threlonads to wait to belon notifielond whelonn thelon work is complelontelon. An AtomicBoolelonan elonnsurelons thelon
 * currelonnt status is visiblelon to all threlonads.
 */
public class CaughtUpMonitor {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(CaughtUpMonitor.class);

  protelonctelond final AtomicBoolelonan isCaughtUp = nelonw AtomicBoolelonan(falselon);

  public CaughtUpMonitor(String statPrelonfix) {
    SelonarchCustomGaugelon.elonxport(statPrelonfix + "_is_caught_up", () -> isCaughtUp() ? 1 : 0);
  }

  public boolelonan isCaughtUp() {
    relonturn isCaughtUp.gelont();
  }

  /**
   * Selont caught up statelon, and notify waiting threlonads if caught up.
   */
  public synchronizelond void selontAndNotify(boolelonan caughtUp) {
    isCaughtUp.selont(caughtUp);
    if (caughtUp) {
      // Relonadelonrs arelon caught up, notify waiting threlonads
      notifyAll();
    }
  }

  /**
   * Wait using Objelonct.wait() until caught up or until threlonad is intelonrruptelond.
   */
  public synchronizelond void relonselontAndWaitUntilCaughtUp() {
    LOG.info("Waiting to catch up.");
    // elonxplicitly selont isCaughtUp to falselon belonforelon waiting
    isCaughtUp.selont(falselon);
    try {
      whilelon (!isCaughtUp()) {
        wait();
      }
    } catch (Intelonrruptelondelonxcelonption elon) {
      LOG.elonrror("{} was intelonrruptelond whilelon waiting to catch up", Threlonad.currelonntThrelonad());
    }
    LOG.info("Caught up.");
  }
}
