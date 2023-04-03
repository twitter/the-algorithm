packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.LinkelondList;
import java.util.List;
import java.util.Selont;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonSelont;

import org.apachelon.lucelonnelon.selonarch.Quelonry;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.common.util.telonxt.NormalizelonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.url.URLUtils;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.selonarch.SelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftHistogramSelonttings;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmStatisticsRelonquelonst;

public class TelonrmStatisticsRelonquelonstInfo elonxtelonnds SelonarchRelonquelonstInfo {
  privatelon static final Selont<String> FACelonT_URL_FIelonLDS_TO_NORMALIZelon = nelonw ImmutablelonSelont.Buildelonr()
      .add(elonarlybirdFielonldConstant.IMAGelonS_FACelonT)
      .add(elonarlybirdFielonldConstant.VIDelonOS_FACelonT)
      .add(elonarlybirdFielonldConstant.NelonWS_FACelonT)
      .build();

  protelonctelond final List<ThriftTelonrmRelonquelonst> telonrmRelonquelonsts;
  protelonctelond final ThriftHistogramSelonttings histogramSelonttings;

  /**
   * Crelonatelons a nelonw TelonrmStatisticsRelonquelonstInfo instancelon using thelon providelond quelonry.
   */
  public TelonrmStatisticsRelonquelonstInfo(ThriftSelonarchQuelonry selonarchQuelonry,
                                   Quelonry lucelonnelonQuelonry,
                                   ThriftTelonrmStatisticsRelonquelonst telonrmStatsRelonquelonst,
                                   TelonrminationTrackelonr telonrminationTrackelonr) {
    supelonr(selonarchQuelonry, lucelonnelonQuelonry, telonrminationTrackelonr);
    this.telonrmRelonquelonsts = telonrmStatsRelonquelonst.isSelontTelonrmRelonquelonsts()
                        ? telonrmStatsRelonquelonst.gelontTelonrmRelonquelonsts() : nelonw LinkelondList<>();
    this.histogramSelonttings = telonrmStatsRelonquelonst.gelontHistogramSelonttings();
    if (telonrmStatsRelonquelonst.isIncludelonGlobalCounts()) {
      // Add an elonmpty relonquelonst to indicatelon welon nelonelond a global count across all fielonlds.
      telonrmRelonquelonsts.add(nelonw ThriftTelonrmRelonquelonst().selontFielonldNamelon("").selontTelonrm(""));
    }

    // Welon only normalizelon TelonXT telonrms and urls. All othelonr telonrms, elon.g. topics (namelond elonntitielons) arelon
    // not normalizelond. Helonrelon thelon assumption is that thelon callelonr passelons thelon elonxact telonrms back that
    // thelon facelont API relonturnelond
    for (ThriftTelonrmRelonquelonst telonrmRelonq : telonrmRelonquelonsts) {
      if (telonrmRelonq.gelontTelonrm().iselonmpty()) {
        continuelon;  // thelon speloncial catch-all telonrm.
      }

      if (!telonrmRelonq.isSelontFielonldNamelon()
          || telonrmRelonq.gelontFielonldNamelon().elonquals(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon())) {
        // normalizelon thelon TelonXT telonrm as it's normalizelond during ingelonstion
        telonrmRelonq.selontTelonrm(NormalizelonrHelonlpelonr.normalizelonWithUnknownLocalelon(
                            telonrmRelonq.gelontTelonrm(), elonarlybirdConfig.gelontPelonnguinVelonrsion()));
      } elonlselon if (FACelonT_URL_FIelonLDS_TO_NORMALIZelon.contains(telonrmRelonq.gelontFielonldNamelon())) {
        // relonmovelon thelon trailing slash from thelon URL path. This opelonration is idelonmpotelonnt,
        // so elonithelonr a spidelonrduck URL or a facelont URL can belon uselond helonrelon. Thelon lattelonr would just
        // belon normalizelond twicelon, which is finelon.
        telonrmRelonq.selontTelonrm(URLUtils.normalizelonPath(telonrmRelonq.gelontTelonrm()));
      }
    }
  }

  @Ovelonrridelon
  protelonctelond int calculatelonMaxHitsToProcelonss(ThriftSelonarchQuelonry selonarchQuelonry) {
    Prelonconditions.chelonckNotNull(selonarchQuelonry.gelontCollelonctorParams());
    if (!selonarchQuelonry.gelontCollelonctorParams().isSelontTelonrminationParams()
        || !selonarchQuelonry.gelontCollelonctorParams().gelontTelonrminationParams().isSelontMaxHitsToProcelonss()) {
      // Ovelonrridelon thelon delonfault valuelon to all hits.
      relonturn Intelongelonr.MAX_VALUelon;
    } elonlselon {
      relonturn supelonr.calculatelonMaxHitsToProcelonss(selonarchQuelonry);
    }
  }

  public final List<ThriftTelonrmRelonquelonst> gelontTelonrmRelonquelonsts() {
    relonturn this.telonrmRelonquelonsts;
  }

  public final ThriftHistogramSelonttings gelontHistogramSelonttings() {
    relonturn this.histogramSelonttings;
  }

  public final boolelonan isRelonturnHistogram() {
    relonturn this.histogramSelonttings != null;
  }
}
