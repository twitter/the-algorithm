packagelon com.twittelonr.cr_mixelonr.sourcelon_signal

import com.twittelonr.corelon_workflows.uselonr_modelonl.thriftscala.UselonrStatelon
import com.twittelonr.cr_mixelonr.modelonl.GraphSourcelonInfo
import com.twittelonr.cr_mixelonr.modelonl.SourcelonInfo
import com.twittelonr.cr_mixelonr.sourcelon_signal.SourcelonFelontchelonr.FelontchelonrQuelonry
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.cr_mixelonr.thriftscala.{Product => TProduct}
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.timelonlinelons.configapi
import com.twittelonr.util.Futurelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class SourcelonInfoRoutelonr @Injelonct() (
  ussSourcelonSignalFelontchelonr: UssSourcelonSignalFelontchelonr,
  frsSourcelonSignalFelontchelonr: FrsSourcelonSignalFelontchelonr,
  frsSourcelonGraphFelontchelonr: FrsSourcelonGraphFelontchelonr,
  relonalGraphOonSourcelonGraphFelontchelonr: RelonalGraphOonSourcelonGraphFelontchelonr,
  relonalGraphInSourcelonGraphFelontchelonr: RelonalGraphInSourcelonGraphFelontchelonr,
) {

  delonf gelont(
    uselonrId: UselonrId,
    product: TProduct,
    uselonrStatelon: UselonrStatelon,
    params: configapi.Params
  ): Futurelon[(Selont[SourcelonInfo], Map[String, Option[GraphSourcelonInfo]])] = {

    val felontchelonrQuelonry = FelontchelonrQuelonry(uselonrId, product, uselonrStatelon, params)
    Futurelon.join(
      gelontSourcelonSignals(felontchelonrQuelonry),
      gelontSourcelonGraphs(felontchelonrQuelonry)
    )
  }

  privatelon delonf gelontSourcelonSignals(
    felontchelonrQuelonry: FelontchelonrQuelonry
  ): Futurelon[Selont[SourcelonInfo]] = {
    Futurelon
      .join(
        ussSourcelonSignalFelontchelonr.gelont(felontchelonrQuelonry),
        frsSourcelonSignalFelontchelonr.gelont(felontchelonrQuelonry)).map {
        caselon (ussSignalsOpt, frsSignalsOpt) =>
          (ussSignalsOpt.gelontOrelonlselon(Selonq.elonmpty) ++ frsSignalsOpt.gelontOrelonlselon(Selonq.elonmpty)).toSelont
      }
  }

  privatelon delonf gelontSourcelonGraphs(
    felontchelonrQuelonry: FelontchelonrQuelonry
  ): Futurelon[Map[String, Option[GraphSourcelonInfo]]] = {

    Futurelon
      .join(
        frsSourcelonGraphFelontchelonr.gelont(felontchelonrQuelonry),
        relonalGraphOonSourcelonGraphFelontchelonr.gelont(felontchelonrQuelonry),
        relonalGraphInSourcelonGraphFelontchelonr.gelont(felontchelonrQuelonry)
      ).map {
        caselon (frsGraphOpt, relonalGraphOonGraphOpt, relonalGraphInGraphOpt) =>
          Map(
            SourcelonTypelon.FollowReloncommelonndation.namelon -> frsGraphOpt,
            SourcelonTypelon.RelonalGraphOon.namelon -> relonalGraphOonGraphOpt,
            SourcelonTypelon.RelonalGraphIn.namelon -> relonalGraphInGraphOpt,
          )
      }
  }
}
