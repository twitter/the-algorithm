packagelon com.twittelonr.follow_reloncommelonndations.common.transforms.tracking_tokelonn

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Transform
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Selonssion
import com.twittelonr.follow_reloncommelonndations.common.modelonls.TrackingTokelonn
import com.twittelonr.helonrmit.constants.AlgorithmFelonelondbackTokelonns.AlgorithmToFelonelondbackTokelonnMap
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.util.logging.Logging

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * This transform adds thelon tracking tokelonn for all candidatelons
 * Sincelon this happelonns in thelon samelon relonquelonst, welon uselon thelon samelon tracelon-id for all candidatelons
 * Thelonrelon arelon no RPC calls in this transform so it's safelon to chain it with `andThelonn` at thelon elonnd of
 * all othelonr product-speloncific transforms
 */
@Singlelonton
class TrackingTokelonnTransform @Injelonct() (baselonStatsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds Transform[HasDisplayLocation with HasClielonntContelonxt, CandidatelonUselonr]
    with Logging {

  delonf profilelonRelonsults(
    targelont: HasDisplayLocation with HasClielonntContelonxt,
    candidatelons: Selonq[CandidatelonUselonr]
  ) = {
    // Melontrics to track # relonsults pelonr candidatelon sourcelon
    val stats = baselonStatsReloncelonivelonr.scopelon(targelont.displayLocation.toString + "/final_relonsults")
    stats.stat("total").add(candidatelons.sizelon)

    stats.countelonr(targelont.displayLocation.toString).incr()

    val flattelonnelondCandidatelons: Selonq[(CandidatelonSourcelonIdelonntifielonr, CandidatelonUselonr)] = for {
      candidatelon <- candidatelons
      idelonntifielonr <- candidatelon.gelontPrimaryCandidatelonSourcelon
    } yielonld (idelonntifielonr, candidatelon)
    val candidatelonsGroupelondBySourcelon: Map[CandidatelonSourcelonIdelonntifielonr, Selonq[CandidatelonUselonr]] =
      flattelonnelondCandidatelons.groupBy(_._1).mapValuelons(_.map(_._2))
    candidatelonsGroupelondBySourcelon map {
      caselon (sourcelon, candidatelons) => stats.stat(sourcelon.namelon).add(candidatelons.sizelon)
    }
  }

  ovelonrridelon delonf transform(
    targelont: HasDisplayLocation with HasClielonntContelonxt,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    profilelonRelonsults(targelont, candidatelons)

    Stitch.valuelon(
      targelont.gelontOptionalUselonrId
        .map { _ =>
          candidatelons.map {
            candidatelon =>
              val tokelonn = Somelon(TrackingTokelonn(
                selonssionId = Selonssion.gelontSelonssionId,
                displayLocation = Somelon(targelont.displayLocation),
                controllelonrData = Nonelon,
                algorithmId = candidatelon.uselonrCandidatelonSourcelonDelontails.flatMap(_.primaryCandidatelonSourcelon
                  .flatMap { idelonntifielonr =>
                    Algorithm.withNamelonOpt(idelonntifielonr.namelon).flatMap(AlgorithmToFelonelondbackTokelonnMap.gelont)
                  })
              ))
              candidatelon.copy(trackingTokelonn = tokelonn)
          }
        }.gelontOrelonlselon(candidatelons))

  }
}
