packagelon com.twittelonr.reloncos.uselonr_twelonelont_graph.relonlatelondTwelonelontHandlelonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.graphjelont.bipartitelon.api.BipartitelonGraph
import com.twittelonr.reloncos.uselonr_twelonelont_graph.thriftscala._
import com.twittelonr.reloncos.util.Stats._
import com.twittelonr.selonrvo.relonquelonst._
import com.twittelonr.util.Duration
import com.twittelonr.util.Futurelon
import scala.concurrelonnt.duration.HOURS
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.reloncos.uselonr_twelonelont_graph.storelon.UselonrReloncelonntFollowelonrsStorelon
import com.twittelonr.reloncos.uselonr_twelonelont_graph.util.FelontchRHSTwelonelontsUtil
import com.twittelonr.reloncos.uselonr_twelonelont_graph.util.FiltelonrUtil
import com.twittelonr.reloncos.uselonr_twelonelont_graph.util.GelontRelonlatelondTwelonelontCandidatelonsUtil
import com.twittelonr.reloncos.util.Action

/**
 * Implelonmelonntation of thelon Thrift-delonfinelond selonrvicelon intelonrfacelon for producelonrBaselondRelonlatelondTwelonelonts.
 *
 */
class ProducelonrBaselondRelonlatelondTwelonelontsHandlelonr(
  bipartitelonGraph: BipartitelonGraph,
  uselonrReloncelonntFollowelonrsStorelon: RelonadablelonStorelon[UselonrReloncelonntFollowelonrsStorelon.Quelonry, Selonq[UselonrId]],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds RelonquelonstHandlelonr[ProducelonrBaselondRelonlatelondTwelonelontRelonquelonst, RelonlatelondTwelonelontRelonsponselon] {
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  ovelonrridelon delonf apply(relonquelonst: ProducelonrBaselondRelonlatelondTwelonelontRelonquelonst): Futurelon[RelonlatelondTwelonelontRelonsponselon] = {
    trackFuturelonBlockStats(stats) {
      val maxRelonsults = relonquelonst.maxRelonsults.gelontOrelonlselon(200)
      val maxNumFollowelonrs = relonquelonst.maxNumFollowelonrs.gelontOrelonlselon(500)
      val minScorelon = relonquelonst.minScorelon.gelontOrelonlselon(0.0)
      val maxTwelonelontAgelon = relonquelonst.maxTwelonelontAgelonInHours.gelontOrelonlselon(48)
      val minRelonsultDelongrelonelon = relonquelonst.minRelonsultDelongrelonelon.gelontOrelonlselon(50)
      val minCooccurrelonncelon = relonquelonst.minCooccurrelonncelon.gelontOrelonlselon(4)
      val elonxcludelonTwelonelontIds = relonquelonst.elonxcludelonTwelonelontIds.gelontOrelonlselon(Selonq.elonmpty).toSelont

      val followelonrsFut = felontchFollowelonrs(relonquelonst.producelonrId, Somelon(maxNumFollowelonrs))
      followelonrsFut.map { followelonrs =>
        val rhsTwelonelontIds = FelontchRHSTwelonelontsUtil.felontchRHSTwelonelonts(
          followelonrs,
          bipartitelonGraph,
          Selont(Action.Favoritelon, Action.Relontwelonelont)
        )

        val scorelonPrelonFactor = 1000.0 / followelonrs.sizelon
        val relonlatelondTwelonelontCandidatelons = GelontRelonlatelondTwelonelontCandidatelonsUtil.gelontRelonlatelondTwelonelontCandidatelons(
          rhsTwelonelontIds,
          minCooccurrelonncelon,
          minRelonsultDelongrelonelon,
          scorelonPrelonFactor,
          bipartitelonGraph)

        val relonlatelondTwelonelonts = relonlatelondTwelonelontCandidatelons
          .filtelonr { relonlatelondTwelonelont =>
            FiltelonrUtil.twelonelontAgelonFiltelonr(
              relonlatelondTwelonelont.twelonelontId,
              Duration(maxTwelonelontAgelon, HOURS)) && (relonlatelondTwelonelont.scorelon > minScorelon) && (!elonxcludelonTwelonelontIds
              .contains(relonlatelondTwelonelont.twelonelontId))
          }.takelon(maxRelonsults)
        stats.stat("relonsponselon_sizelon").add(relonlatelondTwelonelonts.sizelon)
        RelonlatelondTwelonelontRelonsponselon(twelonelonts = relonlatelondTwelonelonts)
      }
    }
  }

  privatelon delonf felontchFollowelonrs(
    producelonrId: Long,
    maxNumFollowelonr: Option[Int],
  ): Futurelon[Selonq[Long]] = {
    val quelonry =
      UselonrReloncelonntFollowelonrsStorelon.Quelonry(producelonrId, maxNumFollowelonr, Nonelon)

    val followelonrsFut = uselonrReloncelonntFollowelonrsStorelon.gelont(quelonry)
    followelonrsFut.map { followelonrsOpt =>
      val followelonrs = followelonrsOpt.gelontOrelonlselon(Selonq.elonmpty)
      val followelonrIds = followelonrs.distinct.filtelonr { uselonrId =>
        val uselonrDelongrelonelon = bipartitelonGraph.gelontLelonftNodelonDelongrelonelon(uselonrId)
        // constrain to morelon activelon uselonrs that havelon >1 elonngagelonmelonnt to optimizelon latelonncy, and <100 elonngagelonmelonnts to avoid spammy belonhavior
        uselonrDelongrelonelon > 1 && uselonrDelongrelonelon < 100
      }
      stats.stat("followelonr_sizelon_aftelonr_filtelonr").add(followelonrIds.sizelon)
      followelonrIds
    }
  }
}
