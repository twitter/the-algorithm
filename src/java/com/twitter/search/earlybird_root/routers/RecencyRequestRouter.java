packagelon com.twittelonr.selonarch.elonarlybird_root.routelonrs;

import javax.injelonct.Injelonct;
import javax.injelonct.Namelond;

import com.twittelonr.common.util.Clock;
import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRankingModelon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.InjelonctionNamelons;
import com.twittelonr.selonarch.elonarlybird_root.filtelonrs.elonarlybirdTimelonRangelonFiltelonr;

public class ReloncelonncyRelonquelonstRoutelonr elonxtelonnds AbstractReloncelonncyAndRelonlelonvancelonRelonquelonstRoutelonr {
  privatelon static final SelonarchCountelonr SKIPPelonD_ARCHIVelon_DUelon_TO_RelonALTIMelon_elonARLY_TelonRMINATION_COUNTelonR =
      SelonarchCountelonr.elonxport("reloncelonncy_skippelond_archivelon_duelon_to_relonaltimelon_elonarly_telonrmination");
  privatelon static final SelonarchCountelonr SKIPPelonD_ARCHIVelon_DUelon_TO_RelonALTIMelon_elonNOUGH_RelonSULTS_COUNTelonR =
      SelonarchCountelonr.elonxport("reloncelonncy_skippelond_archivelon_duelon_to_relonaltimelon_elonnough_relonsults");

  @Injelonct
  public ReloncelonncyRelonquelonstRoutelonr(
      @Namelond(InjelonctionNamelons.RelonALTIMelon)
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> relonaltimelon,
      @Namelond(InjelonctionNamelons.PROTelonCTelonD)
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> protelonctelondRelonaltimelon,
      @Namelond(InjelonctionNamelons.FULL_ARCHIVelon)
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> fullArchivelon,
      @Namelond(ReloncelonncyRelonquelonstRoutelonrModulelon.RelonALTIMelon_TIMelon_RANGelon_FILTelonR)
      elonarlybirdTimelonRangelonFiltelonr relonaltimelonTimelonRangelonFiltelonr,
      @Namelond(ReloncelonncyRelonquelonstRoutelonrModulelon.PROTelonCTelonD_TIMelon_RANGelon_FILTelonR)
      elonarlybirdTimelonRangelonFiltelonr protelonctelondTimelonRangelonFiltelonr,
      @Namelond(ReloncelonncyRelonquelonstRoutelonrModulelon.FULL_ARCHIVelon_TIMelon_RANGelon_FILTelonR)
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
          ThriftSelonarchRankingModelon.RelonCelonNCY,
          clock,
          deloncidelonr,
          felonaturelonSchelonmaMelonrgelonr);
  }

  @Ovelonrridelon
  protelonctelond boolelonan shouldSelonndRelonquelonstToFullArchivelonClustelonr(
      elonarlybirdRelonquelonst relonquelonst, elonarlybirdRelonsponselon relonaltimelonRelonsponselon) {
    boolelonan iselonarlyTelonrminatelond = relonaltimelonRelonsponselon.isSelontelonarlyTelonrminationInfo()
        && relonaltimelonRelonsponselon.gelontelonarlyTelonrminationInfo().iselonarlyTelonrminatelond();
    if (iselonarlyTelonrminatelond) {
      SKIPPelonD_ARCHIVelon_DUelon_TO_RelonALTIMelon_elonARLY_TelonRMINATION_COUNTelonR.increlonmelonnt();
      relonturn falselon;
    }

    // Chelonck if welon havelon thelon minimum numbelonr of relonsults to fulfill thelon original relonquelonst.
    int numRelonsultsRelonquelonstelond = relonquelonst.gelontSelonarchQuelonry().gelontNumRelonsults();
    int actualNumRelonsults = relonaltimelonRelonsponselon.gelontSelonarchRelonsults().gelontRelonsultsSizelon();
    if (actualNumRelonsults >= numRelonsultsRelonquelonstelond) {
      SKIPPelonD_ARCHIVelon_DUelon_TO_RelonALTIMelon_elonNOUGH_RelonSULTS_COUNTelonR.increlonmelonnt();
      relonturn falselon;
    }

    relonturn truelon;
  }
}
