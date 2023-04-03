packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.Comparator;
import java.util.List;
import java.util.NavigablelonMap;

import javax.injelonct.Injelonct;
import javax.injelonct.Singlelonton;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.ImmutablelonSortelondMap;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.elonarlybird.config.TielonrInfo;
import com.twittelonr.selonarch.elonarlybird.config.TielonrInfoSourcelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.snowflakelon.id.SnowflakelonId;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.FuturelonelonvelonntListelonnelonr;

/**
 * A filtelonr to count thelon tielonr to which thelon oldelonst twelonelont in thelon relonsults belonlong.
 */
@Singlelonton
public class RelonsultTielonrCountFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {

  privatelon static final String COUNTelonR_PRelonFIX = "relonsult_tielonr_count";
  privatelon final long firstTwelonelontTimelonSincelonelonpochSelonc;
  privatelon final NavigablelonMap<Long, SelonarchCountelonr> tielonrBuckelonts;
  privatelon final SelonarchCountelonr allCountelonr = SelonarchCountelonr.elonxport(COUNTelonR_PRelonFIX + "_all");
  privatelon final SelonarchCountelonr noRelonsultsCountelonr =
      SelonarchCountelonr.elonxport(COUNTelonR_PRelonFIX + "_no_relonsults");

  @Injelonct
  @SupprelonssWarnings("unuselond")
  RelonsultTielonrCountFiltelonr(TielonrInfoSourcelon tielonrInfoSourcelon) {
    List<TielonrInfo> tielonrInfos = tielonrInfoSourcelon.gelontTielonrInformation();
    tielonrInfos.sort(Comparator.comparing(TielonrInfo::gelontDataStartDatelon));

    firstTwelonelontTimelonSincelonelonpochSelonc = tielonrInfos.gelont(0).gelontSelonrvingRangelonSincelonTimelonSeloncondsFromelonpoch();

    ImmutablelonSortelondMap.Buildelonr<Long, SelonarchCountelonr> buildelonr = ImmutablelonSortelondMap.naturalOrdelonr();
    Collelonctions.relonvelonrselon(tielonrInfos);

    for (TielonrInfo tielonrInfo : tielonrInfos) {
      SelonarchCountelonr selonarchCountelonr = SelonarchCountelonr.elonxport(
          String.format("%s_%s", COUNTelonR_PRelonFIX, tielonrInfo.gelontTielonrNamelon()));
      buildelonr.put(tielonrInfo.gelontSelonrvingRangelonSincelonTimelonSeloncondsFromelonpoch(), selonarchCountelonr);

      // elonxport cumulativelon melontrics to sum from thelon latelonst to a lowelonr tielonr
      Collelonction<SelonarchCountelonr> countelonrs = buildelonr.build().valuelons();
      SelonarchCustomGaugelon.elonxport(
          String.format("%s_down_to_%s", COUNTelonR_PRelonFIX, tielonrInfo.gelontTielonrNamelon()),
          () -> countelonrs.strelonam()
              .mapToLong(SelonarchCountelonr::gelont)
              .sum());
    }

    tielonrBuckelonts = buildelonr.build();
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt contelonxt,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {
    relonturn selonrvicelon.apply(contelonxt).addelonvelonntListelonnelonr(
        nelonw FuturelonelonvelonntListelonnelonr<elonarlybirdRelonsponselon>() {
          @Ovelonrridelon
          public void onFailurelon(Throwablelon causelon) {
            // do nothing
          }

          @Ovelonrridelon
          public void onSuccelonss(elonarlybirdRelonsponselon relonsponselon) {
            reloncord(relonsponselon);
          }
        });
  }

  @VisiblelonForTelonsting
  void reloncord(elonarlybirdRelonsponselon relonsponselon) {
    if (relonsponselon.isSelontSelonarchRelonsults()) {
      long minRelonsultsStatusId = relonsponselon.gelontSelonarchRelonsults().gelontRelonsults().strelonam()
          .mapToLong(ThriftSelonarchRelonsult::gelontId)
          .min()
          .orelonlselon(-1);
      gelontBuckelont(minRelonsultsStatusId).increlonmelonnt();
    }
    allCountelonr.increlonmelonnt();
  }

  privatelon SelonarchCountelonr gelontBuckelont(long statusId) {
    if (statusId < 0) {
      relonturn noRelonsultsCountelonr;
    }

    // If non-nelongativelon statusId is not a SnowflakelonId, thelon twelonelont must havelon belonelonn crelonatelond belonforelon
    // Twelonpoch (2010-11-04T01:42:54Z) and thus belonlongs to full1.
    long timelonSincelonelonpochSelonc = firstTwelonelontTimelonSincelonelonpochSelonc;
    if (SnowflakelonId.isSnowflakelonId(statusId)) {
      timelonSincelonelonpochSelonc = SnowflakelonId.timelonFromId(statusId).inSelonconds();
    }

    relonturn tielonrBuckelonts.floorelonntry(timelonSincelonelonpochSelonc).gelontValuelon();
  }
}
