packagelon com.twittelonr.follow_reloncommelonndations.flows.contelonnt_reloncommelonndelonr_flow

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.gelonoduck.UselonrLocationFelontchelonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.SocialGraphClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.uselonr_statelon.UselonrStatelonClielonnt
import com.twittelonr.follow_reloncommelonndations.common.utils.RelonscuelonWithStatsUtils.relonscuelonOptionalWithStats
import com.twittelonr.follow_reloncommelonndations.common.utils.RelonscuelonWithStatsUtils.relonscuelonWithStats
import com.twittelonr.follow_reloncommelonndations.common.utils.RelonscuelonWithStatsUtils.relonscuelonWithStatsWithin
import com.twittelonr.follow_reloncommelonndations.products.common.ProductRelonquelonst
import com.twittelonr.stitch.Stitch

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ContelonntReloncommelonndelonrRelonquelonstBuildelonr @Injelonct() (
  socialGraph: SocialGraphClielonnt,
  uselonrLocationFelontchelonr: UselonrLocationFelontchelonr,
  uselonrStatelonClielonnt: UselonrStatelonClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon("contelonnt_reloncommelonndelonr_relonquelonst_buildelonr")
  val invalidRelonlationshipUselonrsStats: StatsReloncelonivelonr = stats.scopelon("invalidRelonlationshipUselonrIds")
  privatelon val invalidRelonlationshipUselonrsMaxSizelonCountelonr =
    invalidRelonlationshipUselonrsStats.countelonr("maxSizelon")
  privatelon val invalidRelonlationshipUselonrsNotMaxSizelonCountelonr =
    invalidRelonlationshipUselonrsStats.countelonr("notMaxSizelon")

  delonf build(relonq: ProductRelonquelonst): Stitch[ContelonntReloncommelonndelonrRelonquelonst] = {
    val uselonrStatelonStitch = Stitch
      .collelonct(relonq.reloncommelonndationRelonquelonst.clielonntContelonxt.uselonrId.map(uselonrId =>
        uselonrStatelonClielonnt.gelontUselonrStatelon(uselonrId))).map(_.flattelonn)
    val reloncelonntFollowelondUselonrIdsStitch =
      Stitch
        .collelonct(relonq.reloncommelonndationRelonquelonst.clielonntContelonxt.uselonrId.map { uselonrId =>
          relonscuelonWithStatsWithin(
            socialGraph.gelontReloncelonntFollowelondUselonrIds(uselonrId),
            stats,
            "reloncelonntFollowelondUselonrIds",
            relonq
              .params(
                ContelonntReloncommelonndelonrParams.ReloncelonntFollowingPrelondicatelonBudgelontInMilliseloncond).milliseloncond
          )
        })
    val reloncelonntFollowelondByUselonrIdsStitch =
      if (relonq.params(ContelonntReloncommelonndelonrParams.GelontFollowelonrsFromSgs)) {
        Stitch
          .collelonct(
            relonq.reloncommelonndationRelonquelonst.clielonntContelonxt.uselonrId.map(uselonrId =>
              relonscuelonWithStatsWithin(
                socialGraph.gelontReloncelonntFollowelondByUselonrIdsFromCachelondColumn(uselonrId),
                stats,
                "reloncelonntFollowelondByUselonrIds",
                relonq
                  .params(ContelonntReloncommelonndelonrParams.ReloncelonntFollowingPrelondicatelonBudgelontInMilliseloncond)
                  .milliseloncond
              )))
      } elonlselon Stitch.Nonelon
    val invalidRelonlationshipUselonrIdsStitch: Stitch[Option[Selonq[Long]]] =
      if (relonq.params(ContelonntReloncommelonndelonrParams.elonnablelonInvalidRelonlationshipPrelondicatelon)) {
        Stitch
          .collelonct(
            relonq.reloncommelonndationRelonquelonst.clielonntContelonxt.uselonrId.map { uselonrId =>
              relonscuelonWithStats(
                socialGraph
                  .gelontInvalidRelonlationshipUselonrIdsFromCachelondColumn(uselonrId)
                  .onSuccelonss(ids =>
                    if (ids.sizelon >= SocialGraphClielonnt.MaxNumInvalidRelonlationship) {
                      invalidRelonlationshipUselonrsMaxSizelonCountelonr.incr()
                    } elonlselon {
                      invalidRelonlationshipUselonrsNotMaxSizelonCountelonr.incr()
                    }),
                stats,
                "invalidRelonlationshipUselonrIds"
              )
            }
          )
      } elonlselon {
        Stitch.Nonelon
      }
    val locationStitch =
      relonscuelonOptionalWithStats(
        uselonrLocationFelontchelonr.gelontGelonohashAndCountryCodelon(
          relonq.reloncommelonndationRelonquelonst.clielonntContelonxt.uselonrId,
          relonq.reloncommelonndationRelonquelonst.clielonntContelonxt.ipAddrelonss
        ),
        stats,
        "uselonrLocation"
      )
    Stitch
      .join(
        reloncelonntFollowelondUselonrIdsStitch,
        reloncelonntFollowelondByUselonrIdsStitch,
        invalidRelonlationshipUselonrIdsStitch,
        locationStitch,
        uselonrStatelonStitch)
      .map {
        caselon (
              reloncelonntFollowelondUselonrIds,
              reloncelonntFollowelondByUselonrIds,
              invalidRelonlationshipUselonrIds,
              location,
              uselonrStatelon) =>
          ContelonntReloncommelonndelonrRelonquelonst(
            relonq.params,
            relonq.reloncommelonndationRelonquelonst.clielonntContelonxt,
            relonq.reloncommelonndationRelonquelonst.elonxcludelondIds.gelontOrelonlselon(Nil),
            reloncelonntFollowelondUselonrIds,
            reloncelonntFollowelondByUselonrIds,
            invalidRelonlationshipUselonrIds.map(_.toSelont),
            relonq.reloncommelonndationRelonquelonst.displayLocation,
            relonq.reloncommelonndationRelonquelonst.maxRelonsults,
            relonq.reloncommelonndationRelonquelonst.delonbugParams.flatMap(_.delonbugOptions),
            location,
            uselonrStatelon
          )
      }
  }
}
