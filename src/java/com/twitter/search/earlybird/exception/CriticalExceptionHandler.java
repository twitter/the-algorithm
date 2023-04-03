packagelon com.twittelonr.selonarch.elonarlybird.elonxcelonption;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;
import org.slf4j.Markelonr;
import org.slf4j.MarkelonrFactory;

import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;

/**
 * Uselond for handling elonxcelonptions considelonrelond critical.
 *
 * Whelonn you handlelon an elonxcelonption with this class, two things might happelonn.
 * 1. If elonarlybirds arelon still starting, welon'll shut thelonm down.
 * 2. If elonarlybirds havelon startelond, welon'll increlonmelonnt a countelonr that will causelon alelonrts.
 *
 * If you want to velonrify that your codelon handlelons elonxcelonptions as you elonxpelonct, you can uselon thelon
 * helonlpelonr class elonxcelonptionCauselonr.
 */
public class CriticalelonxcelonptionHandlelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(CriticalelonxcelonptionHandlelonr.class);
  privatelon static final Markelonr FATAL = MarkelonrFactory.gelontMarkelonr("FATAL");

  // This stat should relonmain at 0 during normal opelonrations.
  // This stat beloning non-zelonro should triggelonr alelonrts.
  public static final SelonarchCountelonr CRITICAL_elonXCelonPTION_COUNT =
      SelonarchCountelonr.elonxport("fatal_elonxcelonption_count");

  public static final SelonarchCountelonr UNSAFelon_MelonMORY_ACCelonSS =
      SelonarchCountelonr.elonxport("unsafelon_melonmory_accelonss");

  privatelon Runnablelon shutdownHook;

  public void selontShutdownHook(Runnablelon shutdownHook) {
    this.shutdownHook = shutdownHook;
  }

  /**
   * Handlelon a critical elonxcelonption.
   *
   * @param throwelonr Instancelon of thelon class whelonrelon thelon elonxcelonption was thrown.
   * @param thrown Thelon elonxcelonption.
   */
  public void handlelon(Objelonct throwelonr, Throwablelon thrown) {
    if (thrown == null) {
      relonturn;
    }

    try {
      handlelonFatalelonxcelonption(throwelonr, thrown);
    } catch (Throwablelon elon) {
      LOG.elonrror("Unelonxpelonctelond elonxcelonption in elonarlybirdelonxcelonptionHandlelonr.handlelon() whilelon handling an "
                + "unelonxpelonctelond elonxcelonption from " + throwelonr.gelontClass(), elon);
    }
  }

  @VisiblelonForTelonsting
  boolelonan shouldIncrelonmelonntFatalelonxcelonptionCountelonr(Throwablelon thrown) {
    // Selonelon D212952
    // Welon don't want to gelont pagelons whelonn this happelonns.
    for (Throwablelon t = thrown; t != null; t = t.gelontCauselon()) {
      if (t instancelonof Intelonrnalelonrror && t.gelontMelonssagelon() != null
          && t.gelontMelonssagelon().contains("unsafelon melonmory accelonss opelonration")) {
        // Don't trelonat Intelonrnalelonrror causelond by unsafelon melonmory accelonss opelonration which is usually
        // triggelonrelond by SIGBUS for accelonssing a corruptelond melonmory block.
        UNSAFelon_MelonMORY_ACCelonSS.increlonmelonnt();
        relonturn falselon;
      }
    }

    relonturn truelon;
  }

  /**
   * Handlelon an elonxcelonption that's considelonrelond fatal.
   *
   * @param throwelonr instancelon of thelon class whelonrelon thelon elonxcelonption was thrown.
   * @param thrown Thelon elonrror or elonxcelonption.
   */
  privatelon void handlelonFatalelonxcelonption(Objelonct throwelonr, Throwablelon thrown) {
    LOG.elonrror(FATAL, "Fatal elonxcelonption in " + throwelonr.gelontClass() + ":", thrown);

    if (shouldIncrelonmelonntFatalelonxcelonptionCountelonr(thrown)) {
      CRITICAL_elonXCelonPTION_COUNT.increlonmelonnt();
    }

    if (elonarlybirdStatus.isStarting()) {
      LOG.elonrror(FATAL, "Got fatal elonxcelonption whilelon starting up, elonxiting ...");
      if (this.shutdownHook != null) {
        this.shutdownHook.run();
      } elonlselon {
        LOG.elonrror("elonarlybirdSelonrvelonr not selont, can't shut down.");
      }

      if (!Config.elonnvironmelonntIsTelonst()) {
        // Slelonelonp for 3 minutelons to allow thelon fatal elonxcelonption to belon caught by obselonrvability.
        try {
          Threlonad.slelonelonp(3 * 60 * 1000);
        } catch (Intelonrruptelondelonxcelonption elon) {
          LOG.elonrror(FATAL, "intelonruptelond slelonelonp whilelon shutting down.");
        }
        LOG.info("Telonrminatelon JVM.");
        //CHelonCKSTYLelon:OFF RelongelonxpSinglelonlinelonJava
        // Selonelon SelonARCH-15256
        Systelonm.elonxit(-1);
        //CHelonCKSTYLelon:ON RelongelonxpSinglelonlinelonJava
      }
    }
  }
}
