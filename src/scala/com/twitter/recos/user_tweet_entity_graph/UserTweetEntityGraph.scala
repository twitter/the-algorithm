packagelon com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph

import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.finaglelon.tracing.{Tracelon, TracelonId}
import com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph.thriftscala._
import com.twittelonr.util.Futurelon

objelonct UselonrTwelonelontelonntityGraph {
  delonf tracelonId: TracelonId = Tracelon.id
  delonf clielonntId: Option[ClielonntId] = ClielonntId.currelonnt
}

class UselonrTwelonelontelonntityGraph(
  reloncommelonndationHandlelonr: ReloncommelonndationHandlelonr,
  twelonelontSocialProofHandlelonr: TwelonelontSocialProofHandlelonr,
  socialProofHandlelonr: SocialProofHandlelonr)
    elonxtelonnds thriftscala.UselonrTwelonelontelonntityGraph.MelonthodPelonrelonndpoint {

  ovelonrridelon delonf reloncommelonndTwelonelonts(
    relonquelonst: ReloncommelonndTwelonelontelonntityRelonquelonst
  ): Futurelon[ReloncommelonndTwelonelontelonntityRelonsponselon] = reloncommelonndationHandlelonr(relonquelonst)

  /**
   * Givelonn a quelonry uselonr, its selonelond uselonrs, and a selont of input twelonelonts, relonturn thelon social proofs of
   * input twelonelonts if any.
   *
   * Currelonntly this supports clielonnts such as elonmail Reloncommelonndations, MagicReloncs, and HomelonTimelonlinelon.
   * In ordelonr to avoid helonavy migration work, welon arelon relontaining this elonndpoint.
   */
  ovelonrridelon delonf findTwelonelontSocialProofs(
    relonquelonst: SocialProofRelonquelonst
  ): Futurelon[SocialProofRelonsponselon] = twelonelontSocialProofHandlelonr(relonquelonst)

  /**
   * Find social proof for thelon speloncifielond ReloncommelonndationTypelon givelonn a selont of input ids of that typelon.
   * Only find social proofs from thelon speloncifielond selonelond uselonrs with thelon speloncifielond social proof typelons.
   *
   * Currelonntly this supports url social proof gelonnelonration for Guidelon.
   *
   * This elonndpoint is flelonxiblelon elonnough to support social proof gelonnelonration for all reloncommelonndation
   * typelons, and should belon uselond for all futurelon clielonnts of this selonrvicelon.
   */
  ovelonrridelon delonf findReloncommelonndationSocialProofs(
    relonquelonst: ReloncommelonndationSocialProofRelonquelonst
  ): Futurelon[ReloncommelonndationSocialProofRelonsponselon] = socialProofHandlelonr(relonquelonst)
}
