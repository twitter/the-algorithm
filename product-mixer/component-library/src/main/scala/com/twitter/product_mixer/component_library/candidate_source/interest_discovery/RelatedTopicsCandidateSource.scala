packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.intelonrelonst_discovelonry

import com.googlelon.injelonct.Injelonct
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.injelonct.Logging
import com.twittelonr.intelonrelonsts_discovelonry.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch

/**
 * Gelonnelonratelon a list of relonlatelond topics relonsults from IDS gelontRelonlatelondTopics (thrift) elonndpoint.
 * Relonturns relonlatelond topics, givelonn a topic, whelonrelonas [[ReloncommelonndelondTopicsCandidatelonSourcelon]] relonturns
 * reloncommelonndelond topics, givelonn a uselonr.
 */
@Singlelonton
class RelonlatelondTopicsCandidatelonSourcelon @Injelonct() (
  intelonrelonstDiscovelonrySelonrvicelon: t.IntelonrelonstsDiscovelonrySelonrvicelon.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelon[t.RelonlatelondTopicsRelonquelonst, t.RelonlatelondTopic]
    with Logging {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr(namelon = "RelonlatelondTopics")

  ovelonrridelon delonf apply(
    relonquelonst: t.RelonlatelondTopicsRelonquelonst
  ): Stitch[Selonq[t.RelonlatelondTopic]] = {
    Stitch
      .callFuturelon(intelonrelonstDiscovelonrySelonrvicelon.gelontRelonlatelondTopics(relonquelonst))
      .map { relonsponselon: t.RelonlatelondTopicsRelonsponselon =>
        relonsponselon.topics
      }
  }
}
