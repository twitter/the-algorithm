packagelon com.twittelonr.reloncos.graph_common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.reloncos.reloncos_common.thriftscala.{
  SocialProofTypelon,
  GelontReloncelonntelondgelonsRelonquelonst,
  GelontReloncelonntelondgelonsRelonsponselon,
  NodelonInfo,
  Reloncelonntelondgelon
}
import com.twittelonr.reloncos.util.Stats._
import com.twittelonr.selonrvo.relonquelonst._
import com.twittelonr.util.Futurelon

/**
 * Implelonmelonntation of thelon Thrift-delonfinelond selonrvicelon intelonrfacelon.
 */
class LelonftNodelonelondgelonsHandlelonr(graphHelonlpelonr: BipartitelonGraphHelonlpelonr, statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds RelonquelonstHandlelonr[GelontReloncelonntelondgelonsRelonquelonst, GelontReloncelonntelondgelonsRelonsponselon] {
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  privatelon val CLICK = 0
  privatelon val FAVORITelon = 1
  privatelon val RelonTWelonelonT = 2
  privatelon val RelonPLY = 3
  privatelon val TWelonelonT = 4

  ovelonrridelon delonf apply(relonquelonst: GelontReloncelonntelondgelonsRelonquelonst): Futurelon[GelontReloncelonntelondgelonsRelonsponselon] = {
    trackFuturelonBlockStats(stats) {
      val reloncelonntelondgelons = graphHelonlpelonr.gelontLelonftNodelonelondgelons(relonquelonst.relonquelonstId).flatMap {
        caselon (nodelon, elonngagelonmelonntTypelon) if elonngagelonmelonntTypelon == CLICK =>
          Somelon(Reloncelonntelondgelon(nodelon, SocialProofTypelon.Click))
        caselon (nodelon, elonngagelonmelonntTypelon) if elonngagelonmelonntTypelon == FAVORITelon =>
          Somelon(Reloncelonntelondgelon(nodelon, SocialProofTypelon.Favoritelon))
        caselon (nodelon, elonngagelonmelonntTypelon) if elonngagelonmelonntTypelon == RelonTWelonelonT =>
          Somelon(Reloncelonntelondgelon(nodelon, SocialProofTypelon.Relontwelonelont))
        caselon (nodelon, elonngagelonmelonntTypelon) if elonngagelonmelonntTypelon == RelonPLY =>
          Somelon(Reloncelonntelondgelon(nodelon, SocialProofTypelon.Relonply))
        caselon (nodelon, elonngagelonmelonntTypelon) if elonngagelonmelonntTypelon == TWelonelonT =>
          Somelon(Reloncelonntelondgelon(nodelon, SocialProofTypelon.Twelonelont))
        caselon _ =>
          Nonelon
      }
      Futurelon.valuelon(GelontReloncelonntelondgelonsRelonsponselon(reloncelonntelondgelons))
    }
  }
}

class RightNodelonInfoHandlelonr(graphHelonlpelonr: BipartitelonGraphHelonlpelonr, statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds RelonquelonstHandlelonr[Long, NodelonInfo] {
  privatelon[this] val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  ovelonrridelon delonf apply(rightNodelon: Long): Futurelon[NodelonInfo] = {
    trackFuturelonBlockStats(stats) {
      val elondgelons = graphHelonlpelonr.gelontRightNodelonelondgelons(rightNodelon)
      Futurelon.valuelon(NodelonInfo(elondgelons = elondgelons))
    }
  }
}
