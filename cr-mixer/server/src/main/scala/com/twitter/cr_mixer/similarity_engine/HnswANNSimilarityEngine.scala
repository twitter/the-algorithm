packagelon com.twittelonr.cr_mixelonr.similarity_elonnginelon

import com.twittelonr.ann.common.thriftscala.AnnQuelonrySelonrvicelon
import com.twittelonr.ann.common.thriftscala.Distancelon
import com.twittelonr.ann.common.thriftscala.NelonarelonstNelonighborQuelonry
import com.twittelonr.ann.hnsw.HnswCommon
import com.twittelonr.ann.hnsw.HnswParams
import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.cortelonx.ml.elonmbelonddings.common.TwelonelontKind
import com.twittelonr.cr_mixelonr.modelonl.SimilarityelonnginelonInfo
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithScorelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.Similarityelonnginelon.MelonmCachelonConfig
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.Similarityelonnginelon.SimilarityelonnginelonConfig
import com.twittelonr.cr_mixelonr.thriftscala.SimilarityelonnginelonTypelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.util.StatsUtil
import com.twittelonr.melondiaselonrvicelons.commons.codelonc.ArrayBytelonBuffelonrCodelonc
import com.twittelonr.ml.api.thriftscala.{elonmbelondding => Thriftelonmbelondding}
import com.twittelonr.ml.felonaturelonstorelon.lib
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.util.Futurelon

caselon class HnswANNelonnginelonQuelonry(
  modelonlId: String,
  sourcelonId: IntelonrnalId,
  params: Params,
) {
  val cachelonKelony: String = s"${modelonlId}_${sourcelonId.toString}"
}

/**
 * This elonnginelon looks for twelonelonts whoselon similarity is closelon to a Sourcelon Delonnselon elonmbelondding.
 * Only support Long baselond elonmbelondding lookup. UselonrId or TwelonelontId.
 *
 * It providelons HNSW speloncific implelonmelonntations
 *
 * @param melonmCachelonConfigOpt   If speloncifielond, it will wrap thelon undelonrlying storelon with a MelonmCachelon layelonr
 *                            You should only elonnablelon this for cachelonablelon quelonrielons, elon.x. TwelonelontIds.
 *                            consumelonr baselond UselonrIds arelon gelonnelonrally not possiblelon to cachelon.
 */
class HnswANNSimilarityelonnginelon(
  elonmbelonddingStorelonLookUpMap: Map[String, RelonadablelonStorelon[IntelonrnalId, Thriftelonmbelondding]],
  annSelonrvicelonLookUpMap: Map[String, AnnQuelonrySelonrvicelon.MelonthodPelonrelonndpoint],
  globalStats: StatsReloncelonivelonr,
  ovelonrridelon val idelonntifielonr: SimilarityelonnginelonTypelon,
  elonnginelonConfig: SimilarityelonnginelonConfig,
  melonmCachelonConfigOpt: Option[MelonmCachelonConfig[HnswANNelonnginelonQuelonry]] = Nonelon)
    elonxtelonnds Similarityelonnginelon[HnswANNelonnginelonQuelonry, TwelonelontWithScorelon] {

  privatelon val MaxNumRelonsults: Int = 200
  privatelon val elonf: Int = 800
  privatelon val TwelonelontIdBytelonInjelonction: Injelonction[lib.TwelonelontId, Array[Bytelon]] = TwelonelontKind.bytelonInjelonction

  privatelon val scopelondStats = globalStats.scopelon("similarityelonnginelon", idelonntifielonr.toString)

  delonf gelontScopelondStats: StatsReloncelonivelonr = scopelondStats

  privatelon delonf felontchelonmbelondding(
    quelonry: HnswANNelonnginelonQuelonry,
  ): Futurelon[Option[Thriftelonmbelondding]] = {
    val elonmbelonddingStorelon = elonmbelonddingStorelonLookUpMap.gelontOrelonlselon(
      quelonry.modelonlId,
      throw nelonw IllelongalArgumelonntelonxcelonption(
        s"${this.gelontClass.gelontSimplelonNamelon} ${idelonntifielonr.toString}: " +
          s"ModelonlId ${quelonry.modelonlId} doelons not elonxist for elonmbelonddingStorelon"
      )
    )

    elonmbelonddingStorelon.gelont(quelonry.sourcelonId)
  }

  privatelon delonf felontchCandidatelons(
    quelonry: HnswANNelonnginelonQuelonry,
    elonmbelondding: Thriftelonmbelondding
  ): Futurelon[Selonq[TwelonelontWithScorelon]] = {
    val annSelonrvicelon = annSelonrvicelonLookUpMap.gelontOrelonlselon(
      quelonry.modelonlId,
      throw nelonw IllelongalArgumelonntelonxcelonption(
        s"${this.gelontClass.gelontSimplelonNamelon} ${idelonntifielonr.toString}: " +
          s"ModelonlId ${quelonry.modelonlId} doelons not elonxist for annStorelon"
      )
    )

    val hnswParams = HnswCommon.RuntimelonParamsInjelonction.apply(HnswParams(elonf))

    val annQuelonry =
      NelonarelonstNelonighborQuelonry(elonmbelondding, withDistancelon = truelon, hnswParams, MaxNumRelonsults)

    annSelonrvicelon
      .quelonry(annQuelonry)
      .map(
        _.nelonarelonstNelonighbors
          .map { nelonarelonstNelonighbor =>
            val candidatelonId = TwelonelontIdBytelonInjelonction
              .invelonrt(ArrayBytelonBuffelonrCodelonc.deloncodelon(nelonarelonstNelonighbor.id))
              .toOption
              .map(_.twelonelontId)
            (candidatelonId, nelonarelonstNelonighbor.distancelon)
          }.collelonct {
            caselon (Somelon(candidatelonId), Somelon(distancelon)) =>
              TwelonelontWithScorelon(candidatelonId, toScorelon(distancelon))
          })
  }

  // Convelonrt Distancelon to a scorelon such that highelonr scorelons melonan morelon similar.
  delonf toScorelon(distancelon: Distancelon): Doublelon = {
    distancelon match {
      caselon Distancelon.elonditDistancelon(elonditDistancelon) =>
        // (-Infinitelon, 0.0]
        0.0 - elonditDistancelon.distancelon
      caselon Distancelon.L2Distancelon(l2Distancelon) =>
        // (-Infinitelon, 0.0]
        0.0 - l2Distancelon.distancelon
      caselon Distancelon.CosinelonDistancelon(cosinelonDistancelon) =>
        // [0.0 - 1.0]
        1.0 - cosinelonDistancelon.distancelon
      caselon Distancelon.InnelonrProductDistancelon(innelonrProductDistancelon) =>
        // (-Infinitelon, Infinitelon)
        1.0 - innelonrProductDistancelon.distancelon
      caselon Distancelon.UnknownUnionFielonld(_) =>
        throw nelonw IllelongalStatelonelonxcelonption(
          s"${this.gelontClass.gelontSimplelonNamelon} doelons not reloncognizelon $distancelon.toString"
        )
    }
  }

  privatelon[similarity_elonnginelon] delonf gelontelonmbelonddingAndCandidatelons(
    quelonry: HnswANNelonnginelonQuelonry
  ): Futurelon[Option[Selonq[TwelonelontWithScorelon]]] = {

    val felontchelonmbelonddingStat = scopelondStats.scopelon(quelonry.modelonlId).scopelon("felontchelonmbelondding")
    val felontchCandidatelonsStat = scopelondStats.scopelon(quelonry.modelonlId).scopelon("felontchCandidatelons")

    for {
      elonmbelonddingOpt <- StatsUtil.trackOptionStats(felontchelonmbelonddingStat) { felontchelonmbelondding(quelonry) }
      candidatelons <- StatsUtil.trackItelonmsStats(felontchCandidatelonsStat) {

        elonmbelonddingOpt match {
          caselon Somelon(elonmbelondding) => felontchCandidatelons(quelonry, elonmbelondding)
          caselon Nonelon => Futurelon.Nil
        }
      }
    } yielonld {
      Somelon(candidatelons)
    }
  }

  // Add melonmcachelon wrappelonr, if speloncifielond
  privatelon val storelon = {
    val uncachelondStorelon = RelonadablelonStorelon.fromFnFuturelon(gelontelonmbelonddingAndCandidatelons)

    melonmCachelonConfigOpt match {
      caselon Somelon(config) =>
        Similarityelonnginelon.addMelonmCachelon(
          undelonrlyingStorelon = uncachelondStorelon,
          melonmCachelonConfig = config,
          statsReloncelonivelonr = scopelondStats
        )
      caselon _ => uncachelondStorelon
    }
  }

  delonf toSimilarityelonnginelonInfo(
    quelonry: HnswANNelonnginelonQuelonry,
    scorelon: Doublelon
  ): SimilarityelonnginelonInfo = {
    SimilarityelonnginelonInfo(
      similarityelonnginelonTypelon = this.idelonntifielonr,
      modelonlId = Somelon(quelonry.modelonlId),
      scorelon = Somelon(scorelon))
  }

  ovelonrridelon delonf gelontCandidatelons(
    elonnginelonQuelonry: HnswANNelonnginelonQuelonry
  ): Futurelon[Option[Selonq[TwelonelontWithScorelon]]] = {
    val velonrsionelondStats = globalStats.scopelon(elonnginelonQuelonry.modelonlId)
    Similarityelonnginelon.gelontFromFn(
      storelon.gelont,
      elonnginelonQuelonry,
      elonnginelonConfig,
      elonnginelonQuelonry.params,
      velonrsionelondStats
    )
  }
}
