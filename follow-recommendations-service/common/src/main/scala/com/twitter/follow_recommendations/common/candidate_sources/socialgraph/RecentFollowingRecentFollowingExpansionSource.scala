packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.socialgraph

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon.TwoHopelonxpansionCandidatelonSourcelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.ReloncelonntelondgelonsQuelonry
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.SocialGraphClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.AccountProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FollowProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Relonason
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.injelonct.Logging
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.socialgraph.thriftscala.RelonlationshipTypelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * This candidatelon sourcelon is a two hop elonxpansion ovelonr thelon follow graph. Thelon candidatelons relonturnelond from this sourcelon is thelon uselonrs that gelont followelond by thelon targelont uselonr's reloncelonnt followings. It will call SocialGraph `n` + 1 timelons whelonrelon `n` is thelon numbelonr of reloncelonnt followings of thelon targelont uselonr to belon considelonrelond.
 */
@Singlelonton
class ReloncelonntFollowingReloncelonntFollowingelonxpansionSourcelon @Injelonct() (
  socialGraphClielonnt: SocialGraphClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds TwoHopelonxpansionCandidatelonSourcelon[
      HasParams with HasReloncelonntFollowelondUselonrIds,
      Long,
      Long,
      CandidatelonUselonr
    ]
    with Logging {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    ReloncelonntFollowingReloncelonntFollowingelonxpansionSourcelon.Idelonntifielonr

  val stats = statsReloncelonivelonr.scopelon(idelonntifielonr.namelon)

  ovelonrridelon delonf firstDelongrelonelonNodelons(
    targelont: HasParams with HasReloncelonntFollowelondUselonrIds
  ): Stitch[Selonq[Long]] = Stitch.valuelon(
    targelont.reloncelonntFollowelondUselonrIds
      .gelontOrelonlselon(Nil).takelon(
        ReloncelonntFollowingReloncelonntFollowingelonxpansionSourcelon.NumFirstDelongrelonelonNodelonsToRelontrielonvelon)
  )

  ovelonrridelon delonf seloncondaryDelongrelonelonNodelons(
    targelont: HasParams with HasReloncelonntFollowelondUselonrIds,
    nodelon: Long
  ): Stitch[Selonq[Long]] = socialGraphClielonnt
    .gelontReloncelonntelondgelonsCachelond(
      ReloncelonntelondgelonsQuelonry(
        nodelon,
        Selonq(RelonlationshipTypelon.Following),
        Somelon(ReloncelonntFollowingReloncelonntFollowingelonxpansionSourcelon.NumSeloncondDelongrelonelonNodelonsToRelontrielonvelon)),
      uselonCachelondStratoColumn =
        targelont.params(ReloncelonntFollowingReloncelonntFollowingelonxpansionSourcelonParams.CallSgsCachelondColumn)
    ).map(
      _.takelon(ReloncelonntFollowingReloncelonntFollowingelonxpansionSourcelon.NumSeloncondDelongrelonelonNodelonsToRelontrielonvelon)).relonscuelon {
      caselon elonxcelonption: elonxcelonption =>
        loggelonr.warn(
          s"${this.gelontClass} fails to relontrielonvelon seloncond delongrelonelon nodelons for first delongrelonelon nodelon $nodelon",
          elonxcelonption)
        stats.countelonr("seloncond_delongrelonelon_elonxpansion_elonrror").incr()
        Stitch.Nil
    }

  ovelonrridelon delonf aggrelongatelonAndScorelon(
    targelont: HasParams with HasReloncelonntFollowelondUselonrIds,
    firstDelongrelonelonToSeloncondDelongrelonelonNodelonsMap: Map[Long, Selonq[Long]]
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    val zippelond = firstDelongrelonelonToSeloncondDelongrelonelonNodelonsMap.toSelonq.flatMap {
      caselon (firstDelongrelonelonId, seloncondDelongrelonelonIds) =>
        seloncondDelongrelonelonIds.map(seloncondDelongrelonelonId => firstDelongrelonelonId -> seloncondDelongrelonelonId)
    }
    val candidatelonAndConnelonctions = zippelond
      .groupBy { caselon (_, seloncondDelongrelonelonId) => seloncondDelongrelonelonId }
      .mapValuelons { v => v.map { caselon (firstDelongrelonelonId, _) => firstDelongrelonelonId } }
      .toSelonq
      .sortBy { caselon (_, connelonctions) => -connelonctions.sizelon }
      .map {
        caselon (candidatelonId, connelonctions) =>
          CandidatelonUselonr(
            id = candidatelonId,
            scorelon = Somelon(CandidatelonUselonr.DelonfaultCandidatelonScorelon),
            relonason = Somelon(
              Relonason(
                Somelon(AccountProof(followProof = Somelon(FollowProof(connelonctions, connelonctions.sizelon))))))
          ).withCandidatelonSourcelon(idelonntifielonr)
      }
    Stitch.valuelon(candidatelonAndConnelonctions)
  }
}

objelonct ReloncelonntFollowingReloncelonntFollowingelonxpansionSourcelon {
  val Idelonntifielonr = CandidatelonSourcelonIdelonntifielonr(Algorithm.NelonwFollowingNelonwFollowingelonxpansion.toString)

  val NumFirstDelongrelonelonNodelonsToRelontrielonvelon = 5
  val NumSeloncondDelongrelonelonNodelonsToRelontrielonvelon = 20
}
