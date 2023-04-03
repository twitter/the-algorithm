packagelon com.twittelonr.selonarch.elonarlybird_root;

import org.apachelon.thrift.Telonxcelonption;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.root.ValidationBelonhavior;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdDelonbugInfo;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;

public class elonarlybirdSelonrvicelonValidationBelonhavior
    elonxtelonnds ValidationBelonhavior.DelonfaultValidationBelonhavior<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(elonarlybirdSelonrvicelonValidationBelonhavior.class);

  privatelon static final elonarlybirdDelonbugInfo elonARLYBIRD_DelonBUG_INFO =
          nelonw elonarlybirdDelonbugInfo().selontHost("elonarlybird_root");

  privatelon static final SelonarchCountelonr INVALID_SUCCelonSS_RelonSPONSelon_THRelonSHOLD_TOO_LOW =
      SelonarchCountelonr.elonxport("invalid_succelonss_relonsponselon_threlonshold_too_low");
  privatelon static final SelonarchCountelonr INVALID_SUCCelonSS_RelonSPONSelon_THRelonSHOLD_TOO_HIGH =
      SelonarchCountelonr.elonxport("invalid_succelonss_relonsponselon_threlonshold_too_high");

  protelonctelond elonarlybirdRelonsponselon crelonatelonelonrrorRelonsponselon(String elonrrorMsg) {
    elonarlybirdRelonsponselon relonsponselon = nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.CLIelonNT_elonRROR, 0);

    // Welon'relon changing somelon elonRROR logs to WARN on our sidelon, so welon want to elonnsurelon
    // that thelon relonsponselon contains thelon delonbug information thelon clielonnt nelonelonds to
    // relonsolvelon thelon problelonm.
    relonsponselon.selontDelonbugInfo(elonARLYBIRD_DelonBUG_INFO);
    relonsponselon.selontDelonbugString(elonrrorMsg);

    relonturn relonsponselon;
  }

  @Ovelonrridelon
  public elonarlybirdRelonsponselon gelontRelonsponselonIfInvalidRelonquelonst(elonarlybirdRelonquelonst relonquelonst) {
    // First, fix up thelon quelonry.
    elonarlybirdRelonquelonstUtil.chelonckAndSelontCollelonctorParams(relonquelonst);
    elonarlybirdRelonquelonstUtil.logAndFixelonxcelonssivelonValuelons(relonquelonst);

    try {
      relonquelonst.validatelon();
    } catch (Telonxcelonption elon) {
      String elonrrorMsg = "Invalid elonarlybirdRelonquelonst. " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    if (relonquelonst.isSelontSelonarchSelongmelonntId() && relonquelonst.gelontSelonarchSelongmelonntId() <= 0) {
      String elonrrorMsg = "Bad timelon slicelon ID: " + relonquelonst.gelontSelonarchSelongmelonntId();
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    if (relonquelonst.isSelontTelonrmStatisticsRelonquelonst()
        && relonquelonst.gelontTelonrmStatisticsRelonquelonst().isSelontHistogramSelonttings()
        && relonquelonst.gelontTelonrmStatisticsRelonquelonst().gelontHistogramSelonttings().gelontNumBins() == 0) {

      String elonrrorMsg = "numBins for telonrm statistics histograms relonquelonst cannot belon zelonro: " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    if (!relonquelonst.isSelontSelonarchQuelonry()
        || relonquelonst.gelontSelonarchQuelonry() == null) {
      String elonrrorMsg = "Invalid elonarlybirdRelonquelonst, no ThriftSelonarchQuelonry speloncifielond. " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();

    if (!selonarchQuelonry.gelontCollelonctorParams().isSelontNumRelonsultsToRelonturn()) {
      String elonrrorMsg = "ThriftSelonarchQuelonry.numRelonsultsToRelonturn not selont. " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    if (selonarchQuelonry.gelontCollelonctorParams().gelontNumRelonsultsToRelonturn() < 0) {
      String elonrrorMsg = "Invalid ThriftSelonarchQuelonry.collelonctorParams.numRelonsultsToRelonturn: "
          + selonarchQuelonry.gelontCollelonctorParams().gelontNumRelonsultsToRelonturn() + ". " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    if (relonquelonst.isSelontSuccelonssfulRelonsponselonThrelonshold()) {
      doublelon succelonssfulRelonsponselonThrelonshold = relonquelonst.gelontSuccelonssfulRelonsponselonThrelonshold();
      if (succelonssfulRelonsponselonThrelonshold <= 0) {
        String elonrrorMsg = "Succelonss relonsponselon threlonshold is belonlow or elonqual to 0: "
            + succelonssfulRelonsponselonThrelonshold + " relonquelonst: " + relonquelonst;
        LOG.warn(elonrrorMsg);
        INVALID_SUCCelonSS_RelonSPONSelon_THRelonSHOLD_TOO_LOW.increlonmelonnt();
        relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
      } elonlselon if (succelonssfulRelonsponselonThrelonshold > 1) {
        String elonrrorMsg = "Succelonss relonsponselon threlonshold is abovelon 1: " + succelonssfulRelonsponselonThrelonshold
            + " relonquelonst: " + relonquelonst;
        LOG.warn(elonrrorMsg);
        INVALID_SUCCelonSS_RelonSPONSelon_THRelonSHOLD_TOO_HIGH.increlonmelonnt();
        relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
      }
    }

    relonturn null;
  }
}
