packagelon com.twittelonr.selonarch.elonarlybird_root.routelonrs;

import java.util.concurrelonnt.TimelonUnit;

import javax.injelonct.Injelonct;
import javax.injelonct.Namelond;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;
import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorTelonrminationParams;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRankingModelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.InjelonctionNamelons;
import com.twittelonr.selonarch.elonarlybird_root.filtelonrs.elonarlybirdTimelonRangelonFiltelonr;

public class RelonlelonvancelonRelonquelonstRoutelonr elonxtelonnds AbstractReloncelonncyAndRelonlelonvancelonRelonquelonstRoutelonr {
  privatelon static final long MILLIS_IN_ONelon_DAY = TimelonUnit.DAYS.toMillis(1);

  @Injelonct
  public RelonlelonvancelonRelonquelonstRoutelonr(
      @Namelond(InjelonctionNamelons.RelonALTIMelon)
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> relonaltimelon,
      @Namelond(InjelonctionNamelons.PROTelonCTelonD)
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> protelonctelondRelonaltimelon,
      @Namelond(InjelonctionNamelons.FULL_ARCHIVelon)
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> fullArchivelon,
      @Namelond(RelonlelonvancelonRelonquelonstRoutelonrModulelon.RelonALTIMelon_TIMelon_RANGelon_FILTelonR)
      elonarlybirdTimelonRangelonFiltelonr relonaltimelonTimelonRangelonFiltelonr,
      @Namelond(RelonlelonvancelonRelonquelonstRoutelonrModulelon.PROTelonCTelonD_TIMelon_RANGelon_FILTelonR)
      elonarlybirdTimelonRangelonFiltelonr protelonctelondTimelonRangelonFiltelonr,
      @Namelond(RelonlelonvancelonRelonquelonstRoutelonrModulelon.FULL_ARCHIVelon_TIMelon_RANGelon_FILTelonR)
      elonarlybirdTimelonRangelonFiltelonr fullArchivelonTimelonRangelonFiltelonr,
      Clock clock,
      SelonarchDeloncidelonr deloncidelonr,
      elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr) {
    supelonr(relonaltimelon,
          protelonctelondRelonaltimelon,
          fullArchivelon,
          relonaltimelonTimelonRangelonFiltelonr,
          protelonctelondTimelonRangelonFiltelonr,
          fullArchivelonTimelonRangelonFiltelonr,
          ThriftSelonarchRankingModelon.RelonLelonVANCelon,
          clock,
          deloncidelonr,
          felonaturelonSchelonmaMelonrgelonr);
  }

  @Ovelonrridelon
  protelonctelond boolelonan shouldSelonndRelonquelonstToFullArchivelonClustelonr(
      elonarlybirdRelonquelonst relonquelonst, elonarlybirdRelonsponselon relonaltimelonRelonsponselon) {
    int numRelonsultsRelonquelonstelond = relonquelonst.gelontSelonarchQuelonry().gelontNumRelonsults();
    int numHitsProcelonsselond = relonaltimelonRelonsponselon.gelontSelonarchRelonsults().isSelontNumHitsProcelonsselond()
        ? relonaltimelonRelonsponselon.gelontSelonarchRelonsults().gelontNumHitsProcelonsselond()
        : -1;
    if (numHitsProcelonsselond < numRelonsultsRelonquelonstelond) {
      // Selonnd quelonry to thelon full archivelon clustelonr, if welon welonnt through felonwelonr hits in thelon relonaltimelon
      // clustelonr than thelon relonquelonstelond numbelonr of relonsults.
      relonturn truelon;
    }

    // If welon havelon elonnough hits, don't quelonry thelon full archivelon clustelonr yelont.
    int numSuccelonssfulPartitions = relonaltimelonRelonsponselon.gelontNumSuccelonssfulPartitions();
    CollelonctorTelonrminationParams telonrminationParams =
        relonquelonst.gelontSelonarchQuelonry().gelontCollelonctorParams().gelontTelonrminationParams();

    Prelonconditions.chelonckArgumelonnt(telonrminationParams.isSelontMaxHitsToProcelonss());
    int maxHits = telonrminationParams.gelontMaxHitsToProcelonss() * numSuccelonssfulPartitions;

    if (numHitsProcelonsselond >= maxHits) {
      relonturn falselon;
    }

    // Chelonck if thelonrelon is a gap belontwelonelonn thelon last relonsult and thelon min status ID of currelonnt selonarch.
    // If thelon diffelonrelonncelon is largelonr than onelon day, thelonn welon can still gelont morelon twelonelonts from thelon relonaltimelon
    // clustelonr, so thelonrelon's no nelonelond to quelonry thelon full archivelon clustelonr just yelont. If welon don't chelonck
    // this, thelonn welon might elonnd up with a big gap in thelon relonturnelond relonsults.
    int numRelonturnelondRelonsults = relonaltimelonRelonsponselon.gelontSelonarchRelonsults().gelontRelonsultsSizelon();
    if (numRelonturnelondRelonsults > 0) {
      ThriftSelonarchRelonsult lastRelonsult =
          relonaltimelonRelonsponselon.gelontSelonarchRelonsults().gelontRelonsults().gelont(numRelonturnelondRelonsults - 1);
      long lastRelonsultTimelonMillis = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(lastRelonsult.gelontId());
      long minSelonarchelondStatusID = relonaltimelonRelonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID();
      long minSelonarchelondStatusIDTimelonMillis =
          SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(minSelonarchelondStatusID);
      if (lastRelonsultTimelonMillis - minSelonarchelondStatusIDTimelonMillis > MILLIS_IN_ONelon_DAY) {
        relonturn falselon;
      }
    }

    relonturn truelon;
  }
}
