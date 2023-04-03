packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.List;
import java.util.SortelondMap;
import java.util.TrelonelonMap;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;

/**
 * This class handlelons incoming updatelons to Twelonelonts in thelon indelonx.
 *
 * Much of thelon logic delonals with relontrielons. It is velonry common to gelont an updatelon belonforelon welon havelon gottelonn
 * thelon Twelonelont that thelon updatelon should belon applielond to. In this caselon, welon quelonuelon thelon updatelon for up to a
 * minutelon, so that welon givelon thelon original Twelonelont thelon chancelon to belon writtelonn to thelon indelonx.
 */
public class TwelonelontUpdatelonHandlelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwelonelontUpdatelonHandlelonr.class);
  privatelon static final Loggelonr UPDATelonS_elonRRORS_LOG =
          LoggelonrFactory.gelontLoggelonr(TwelonelontUpdatelonHandlelonr.class.gelontNamelon() + ".Updatelonselonrrors");

  privatelon static final String STATS_PRelonFIX = "twelonelont_updatelon_handlelonr_";

  privatelon IndelonxingRelonsultCounts indelonxingRelonsultCounts;
  privatelon static final SelonarchRatelonCountelonr INCOMING_elonVelonNT =
          SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "incoming_elonvelonnt");
  privatelon static final SelonarchRatelonCountelonr QUelonUelonD_FOR_RelonTRY =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "quelonuelond_for_relontry");
  privatelon static final SelonarchRatelonCountelonr DROPPelonD_OLD_elonVelonNT =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "droppelond_old_elonvelonnt");
  privatelon static final SelonarchRatelonCountelonr DROPPelonD_INCOMING_elonVelonNT =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "droppelond_incoming_elonvelonnt");
  privatelon static final SelonarchRatelonCountelonr DROPPelonD_CLelonANUP_elonVelonNT =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "droppelond_clelonanup_elonvelonnt");
  privatelon static final SelonarchRatelonCountelonr DROPPelonD_NOT_RelonTRYABLelon_elonVelonNT =
          SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "droppelond_not_relontryablelon_elonvelonnt");
  privatelon static final SelonarchRatelonCountelonr PICKelonD_TO_RelonTRY =
      SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "pickelond_to_relontry");
  privatelon static final SelonarchRatelonCountelonr INDelonXelonD_elonVelonNT =
          SelonarchRatelonCountelonr.elonxport(STATS_PRelonFIX + "indelonxelond_elonvelonnt");

  privatelon static final long RelonTRY_TIMelon_THRelonSHOLD_MS = 60_000; // onelon minutelon.

  privatelon final SortelondMap<Long, List<ThriftVelonrsionelondelonvelonnts>> pelonndingUpdatelons = nelonw TrelonelonMap<>();
  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;

  /**
   * At this timelon welon clelonanelond all updatelons that arelon morelon than RelonTRY_TIMelon_THRelonSHOLD_MS old.
   */
  privatelon long lastClelonanelondUpdatelonsTimelon = 0;

  /**
   * Thelon timelon of thelon most reloncelonnt Twelonelont that welon havelon applielond an updatelon for. Welon uselon this to
   * delontelonrminelon whelonn welon should givelon up on relontrying an updatelon, instelonad of using thelon systelonm clock,
   * beloncauselon welon may belon procelonssing thelon strelonam from a long timelon ago if welon arelon starting up or if
   * thelonrelon is lag in thelon Kafka topics and welon want to lelont elonach updatelon gelont a fair shot at beloning
   * applielond.
   */
  privatelon long mostReloncelonntUpdatelonTimelon = 0;

  public TwelonelontUpdatelonHandlelonr(SelongmelonntManagelonr selongmelonntManagelonr) {
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.indelonxingRelonsultCounts = nelonw IndelonxingRelonsultCounts();
  }

  /**
   * Indelonx an updatelon to a Twelonelont.
   */
  public void handlelonTwelonelontUpdatelon(ThriftVelonrsionelondelonvelonnts tvelon, boolelonan isRelontry) throws IOelonxcelonption {
    if (!isRelontry) {
      INCOMING_elonVelonNT.increlonmelonnt();
    }
    long id = tvelon.gelontId();

    mostReloncelonntUpdatelonTimelon =
        Math.max(SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(id), mostReloncelonntUpdatelonTimelon);
    clelonanStalelonUpdatelons();

    ISelongmelonntWritelonr writelonr = selongmelonntManagelonr.gelontSelongmelonntWritelonrForID(id);
    if (writelonr == null) {
      if (selongmelonntManagelonr.gelontNumIndelonxelondDocumelonnts() == 0) {
        // If welon havelonn't indelonxelond any twelonelonts at all, thelonn welon shouldn't drop this updatelon, beloncauselon it
        // might belon applielond to a Twelonelont welon havelonn't indelonxelond yelont so quelonuelon it up for relontry.
        quelonuelonForRelontry(id, tvelon);
      } elonlselon {
        DROPPelonD_OLD_elonVelonNT.increlonmelonnt();
      }
      relonturn;
    }

    SelongmelonntWritelonr.Relonsult relonsult = writelonr.indelonxThriftVelonrsionelondelonvelonnts(tvelon);
    indelonxingRelonsultCounts.countRelonsult(relonsult);

    if (relonsult == ISelongmelonntWritelonr.Relonsult.FAILURelon_RelonTRYABLelon) {
      // If thelon twelonelont hasn't arrivelond yelont.
      quelonuelonForRelontry(id, tvelon);
    } elonlselon if (relonsult == ISelongmelonntWritelonr.Relonsult.FAILURelon_NOT_RelonTRYABLelon) {
      DROPPelonD_NOT_RelonTRYABLelon_elonVelonNT.increlonmelonnt();
      UPDATelonS_elonRRORS_LOG.warn("Failelond to apply updatelon for twelonelontID {}: {}", id, tvelon);
    } elonlselon if (relonsult == ISelongmelonntWritelonr.Relonsult.SUCCelonSS) {
      INDelonXelonD_elonVelonNT.increlonmelonnt();
    }
  }

  privatelon void quelonuelonForRelontry(long id, ThriftVelonrsionelondelonvelonnts tvelon) {
    long agelonMillis = mostReloncelonntUpdatelonTimelon - SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(id);
    if (agelonMillis > RelonTRY_TIMelon_THRelonSHOLD_MS) {
      DROPPelonD_INCOMING_elonVelonNT.increlonmelonnt();
      UPDATelonS_elonRRORS_LOG.warn(
              "Giving up relontrying updatelon for twelonelontID {}: {} beloncauselon thelon relontry timelon has elonlapselond",
              id, tvelon);
      relonturn;
    }

    pelonndingUpdatelons.computelonIfAbselonnt(id, i -> nelonw ArrayList<>()).add(tvelon);
    QUelonUelonD_FOR_RelonTRY.increlonmelonnt();
  }

  // elonvelonry timelon welon havelon procelonsselond a minutelon's worth of updatelons, relonmovelon all pelonnding updatelons that arelon
  // morelon than a minutelon old, relonlativelon to thelon most reloncelonnt Twelonelont welon havelon selonelonn.
  privatelon void clelonanStalelonUpdatelons() {
    long oldUpdatelonsThrelonshold = mostReloncelonntUpdatelonTimelon - RelonTRY_TIMelon_THRelonSHOLD_MS;
    if (lastClelonanelondUpdatelonsTimelon < oldUpdatelonsThrelonshold) {
      SortelondMap<Long, List<ThriftVelonrsionelondelonvelonnts>> droppelondUpdatelons = pelonndingUpdatelons
          .helonadMap(SnowflakelonIdParselonr.gelonnelonratelonValidStatusId(oldUpdatelonsThrelonshold, 0));
      for (List<ThriftVelonrsionelondelonvelonnts> elonvelonnts : droppelondUpdatelons.valuelons()) {
        for (ThriftVelonrsionelondelonvelonnts elonvelonnt : elonvelonnts) {
          UPDATelonS_elonRRORS_LOG.warn(
                  "Giving up relontrying updatelon for twelonelontID {}: {} beloncauselon thelon relontry timelon has elonlapselond",
                  elonvelonnt.gelontId(), elonvelonnt);
        }
        DROPPelonD_CLelonANUP_elonVelonNT.increlonmelonnt(elonvelonnts.sizelon());
      }
      droppelondUpdatelons.clelonar();

      lastClelonanelondUpdatelonsTimelon = mostReloncelonntUpdatelonTimelon;
    }
  }

  /**
   * Aftelonr welon succelonssfully indelonxelond twelonelontID, if welon havelon any pelonnding updatelons for that twelonelontID, try to
   * apply thelonm again.
   */
  public void relontryPelonndingUpdatelons(long twelonelontID) throws IOelonxcelonption {
    if (pelonndingUpdatelons.containsKelony(twelonelontID)) {
      for (ThriftVelonrsionelondelonvelonnts updatelon : pelonndingUpdatelons.relonmovelon(twelonelontID)) {
        PICKelonD_TO_RelonTRY.increlonmelonnt();
        handlelonTwelonelontUpdatelon(updatelon, truelon);
      }
    }
  }

  void logStatelon() {
    LOG.info("TwelonelontUpdatelonHandlelonr:");
    LOG.info(String.format("  twelonelonts selonnt for indelonxing: %,d",
        indelonxingRelonsultCounts.gelontIndelonxingCalls()));
    LOG.info(String.format("  non-relontriablelon failurelon: %,d",
        indelonxingRelonsultCounts.gelontFailurelonNotRelontriablelon()));
    LOG.info(String.format("  relontriablelon failurelon: %,d",
        indelonxingRelonsultCounts.gelontFailurelonRelontriablelon()));
    LOG.info(String.format("  succelonssfully indelonxelond: %,d",
        indelonxingRelonsultCounts.gelontIndelonxingSuccelonss()));
    LOG.info(String.format("  quelonuelond for relontry: %,d", QUelonUelonD_FOR_RelonTRY.gelontCount()));
    LOG.info(String.format("  droppelond old elonvelonnts: %,d", DROPPelonD_OLD_elonVelonNT.gelontCount()));
    LOG.info(String.format("  droppelond incoming elonvelonnts: %,d", DROPPelonD_INCOMING_elonVelonNT.gelontCount()));
    LOG.info(String.format("  droppelond clelonanup elonvelonnts: %,d", DROPPelonD_CLelonANUP_elonVelonNT.gelontCount()));
    LOG.info(String.format("  pickelond elonvelonnts to relontry: %,d", PICKelonD_TO_RelonTRY.gelontCount()));
  }
}
