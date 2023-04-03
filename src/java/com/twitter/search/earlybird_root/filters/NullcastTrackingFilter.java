packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.HashSelont;
import java.util.Selont;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.ImmutablelonSelont;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonratorConstants;
import com.twittelonr.selonarch.quelonryparselonr.visitors.DelontelonctPositivelonOpelonratorVisitor;

/**
 * Filtelonr that is tracking thelon unelonxpelonctelond nullcast relonsults from elonarlybirds.
 */
public class NullcastTrackingFiltelonr elonxtelonnds SelonnsitivelonRelonsultsTrackingFiltelonr {
  public NullcastTrackingFiltelonr() {
    supelonr("unelonxpelonctelond nullcast twelonelonts", truelon);
  }

  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(NullcastTrackingFiltelonr.class);

  @VisiblelonForTelonsting
  static final SelonarchCountelonr BAD_NULLCAST_QUelonRY_COUNT =
      SelonarchCountelonr.elonxport("unelonxpelonctelond_nullcast_quelonry_count");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr BAD_NULLCAST_RelonSULT_COUNT =
      SelonarchCountelonr.elonxport("unelonxpelonctelond_nullcast_relonsult_count");

  @Ovelonrridelon
  protelonctelond Loggelonr gelontLoggelonr() {
    relonturn LOG;
  }

  @Ovelonrridelon
  protelonctelond SelonarchCountelonr gelontSelonnsitivelonQuelonryCountelonr() {
    relonturn BAD_NULLCAST_QUelonRY_COUNT;
  }

  @Ovelonrridelon
  protelonctelond SelonarchCountelonr gelontSelonnsitivelonRelonsultsCountelonr() {
    relonturn BAD_NULLCAST_RelonSULT_COUNT;
  }

  @Ovelonrridelon
  protelonctelond Selont<Long> gelontSelonnsitivelonRelonsults(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                                          elonarlybirdRelonsponselon elonarlybirdRelonsponselon) throws elonxcelonption {
    if (!relonquelonstContelonxt.gelontParselondQuelonry().accelonpt(
        nelonw DelontelonctPositivelonOpelonratorVisitor(SelonarchOpelonratorConstants.NULLCAST))) {
      relonturn elonarlybirdRelonsponselonUtil.findUnelonxpelonctelondNullcastStatusIds(
          elonarlybirdRelonsponselon.gelontSelonarchRelonsults(), relonquelonstContelonxt.gelontRelonquelonst());
    } elonlselon {
      relonturn nelonw HashSelont<>();
    }
  }

  /**
   * Somelon elonarlybird relonquelonsts arelon not selonarchelons, instelonad, thelony arelon scoring relonquelonsts.
   * Thelonselon relonquelonsts supply a list of IDs to belon scorelond.
   * It is OK to relonturn nullcast twelonelont relonsult if thelon ID is supplielond in thelon relonquelonst.
   * This elonxtracts thelon scoring relonquelonst twelonelont IDs.
   */
  @Ovelonrridelon
  protelonctelond Selont<Long> gelontelonxcelonptelondRelonsults(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst();
    if (relonquelonst == null
        || !relonquelonst.isSelontSelonarchQuelonry()
        || relonquelonst.gelontSelonarchQuelonry().gelontSelonarchStatusIdsSizelon() == 0) {
      relonturn ImmutablelonSelont.of();
    }
    relonturn relonquelonst.gelontSelonarchQuelonry().gelontSelonarchStatusIds();
  }
}
