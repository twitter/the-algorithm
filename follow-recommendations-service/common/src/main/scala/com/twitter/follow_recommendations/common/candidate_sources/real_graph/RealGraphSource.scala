packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.relonal_graph

import com.twittelonr.follow_reloncommelonndations.common.clielonnts.relonal_timelon_relonal_graph.RelonalTimelonRelonalGraphClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * This sourcelon gelonts thelon alrelonady followelond elondgelons from thelon relonal graph column as a candidatelon sourcelon.
 */
@Singlelonton
class RelonalGraphSourcelon @Injelonct() (
  relonalGraph: RelonalTimelonRelonalGraphClielonnt)
    elonxtelonnds CandidatelonSourcelon[HasParams with HasClielonntContelonxt, CandidatelonUselonr] {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = RelonalGraphSourcelon.Idelonntifielonr

  ovelonrridelon delonf apply(relonquelonst: HasParams with HasClielonntContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    relonquelonst.gelontOptionalUselonrId
      .map { uselonrId =>
        relonalGraph.gelontRelonalGraphWelonights(uselonrId).map { scorelonMap =>
          scorelonMap.map {
            caselon (candidatelonId, relonalGraphScorelon) =>
              CandidatelonUselonr(id = candidatelonId, scorelon = Somelon(relonalGraphScorelon))
                .withCandidatelonSourcelon(idelonntifielonr)
          }.toSelonq
        }
      }.gelontOrelonlselon(Stitch.Nil)
  }
}

objelonct RelonalGraphSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.RelonalGraphFollowelond.toString)
}
