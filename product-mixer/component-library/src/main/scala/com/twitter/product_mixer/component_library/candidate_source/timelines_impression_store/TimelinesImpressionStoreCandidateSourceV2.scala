packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.timelonlinelons_imprelonssion_storelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyFelontchelonrSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.timelonlinelons.imprelonssion_storelon.TwelonelontImprelonssionStorelonManhattanV2OnUselonrClielonntColumn
import com.twittelonr.timelonlinelons.imprelonssion.thriftscala.TwelonelontImprelonssionselonntrielons
import com.twittelonr.timelonlinelons.imprelonssion.{thriftscala => t}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonsImprelonssionStorelonCandidatelonSourcelonV2 @Injelonct() (
  clielonnt: TwelonelontImprelonssionStorelonManhattanV2OnUselonrClielonntColumn)
    elonxtelonnds StratoKelonyFelontchelonrSourcelon[
      Long,
      t.TwelonelontImprelonssionselonntrielons,
      t.TwelonelontImprelonssionselonntry
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    "TimelonlinelonsImprelonssionStorelon")

  ovelonrridelon val felontchelonr: Felontchelonr[Long, Unit, TwelonelontImprelonssionselonntrielons] = clielonnt.felontchelonr

  ovelonrridelon delonf stratoRelonsultTransformelonr(
    stratoRelonsult: t.TwelonelontImprelonssionselonntrielons
  ): Selonq[t.TwelonelontImprelonssionselonntry] =
    stratoRelonsult.elonntrielons
}
