packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.relonal_graph

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.relonalGraph.UselonrRelonalgraphOonV2ClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.wtf.candidatelon.thriftscala.CandidatelonSelonq
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonalGraphOonV2Sourcelon @Injelonct() (
  relonalGraphClielonntColumn: UselonrRelonalgraphOonV2ClielonntColumn)
    elonxtelonnds CandidatelonSourcelon[HasParams with HasClielonntContelonxt, CandidatelonUselonr] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    RelonalGraphOonV2Sourcelon.Idelonntifielonr

  ovelonrridelon delonf apply(relonquelonst: HasParams with HasClielonntContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    relonquelonst.gelontOptionalUselonrId
      .map { uselonrId =>
        relonalGraphClielonntColumn.felontchelonr
          .felontch(uselonrId)
          .map { relonsult =>
            relonsult.v
              .map { candidatelons => parselonStratoRelonsults(relonquelonst, candidatelons) }
              .gelontOrelonlselon(Nil)
              // relonturnelond candidatelons arelon sortelond by scorelon in delonscelonnding ordelonr
              .takelon(relonquelonst.params(RelonalGraphOonParams.MaxRelonsults))
              .map(_.withCandidatelonSourcelon(idelonntifielonr))
          }
      }.gelontOrelonlselon(Stitch(Selonq.elonmpty))
  }

  privatelon delonf parselonStratoRelonsults(
    relonquelonst: HasParams with HasClielonntContelonxt,
    candidatelonSelonqThrift: CandidatelonSelonq
  ): Selonq[CandidatelonUselonr] = {
    candidatelonSelonqThrift.candidatelons.collelonct {
      caselon candidatelon if candidatelon.scorelon >= relonquelonst.params(RelonalGraphOonParams.ScorelonThrelonshold) =>
        CandidatelonUselonr(
          candidatelon.uselonrId,
          Somelon(candidatelon.scorelon)
        )
    }
  }

}

objelonct RelonalGraphOonV2Sourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.RelonalGraphOonV2.toString
  )
}
