packagelon com.twittelonr.cr_mixelonr.similarity_elonnginelon

import com.twittelonr.cr_mixelonr.modelonl.SimilarityelonnginelonInfo
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithScorelon
import com.twittelonr.cr_mixelonr.thriftscala.SimilarityelonnginelonTypelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.baselon.Stats
import com.twittelonr.product_mixelonr.corelon.thriftscala.ClielonntContelonxt
import com.twittelonr.qig_rankelonr.thriftscala.Product
import com.twittelonr.qig_rankelonr.thriftscala.ProductContelonxt
import com.twittelonr.qig_rankelonr.thriftscala.QigRankelonr
import com.twittelonr.qig_rankelonr.thriftscala.QigRankelonrProductRelonsponselon
import com.twittelonr.qig_rankelonr.thriftscala.QigRankelonrRelonquelonst
import com.twittelonr.qig_rankelonr.thriftscala.QigRankelonrRelonsponselon
import com.twittelonr.qig_rankelonr.thriftscala.TwistlySimilarTwelonelontsProductContelonxt
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.timelonlinelons.configapi
import com.twittelonr.util.Futurelon
import javax.injelonct.Singlelonton

/**
 * This storelon looks for similar twelonelonts from QuelonryIntelonractionGraph (QIG) for a sourcelon twelonelont id.
 * For a givelonn quelonry twelonelont, QIG relonturns us thelon similar twelonelonts that havelon an ovelonrlap of elonngagelonmelonnts
 * (with thelon quelonry twelonelont) on diffelonrelonnt selonarch quelonrielons
 */
@Singlelonton
caselon class TwelonelontBaselondQigSimilarityelonnginelon(
  qigRankelonr: QigRankelonr.MelonthodPelonrelonndpoint,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds RelonadablelonStorelon[
      TwelonelontBaselondQigSimilarityelonnginelon.Quelonry,
      Selonq[TwelonelontWithScorelon]
    ] {

  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val felontchCandidatelonsStat = stats.scopelon("felontchCandidatelons")

  ovelonrridelon delonf gelont(
    quelonry: TwelonelontBaselondQigSimilarityelonnginelon.Quelonry
  ): Futurelon[Option[Selonq[TwelonelontWithScorelon]]] = {
    quelonry.sourcelonId match {
      caselon IntelonrnalId.TwelonelontId(twelonelontId) =>
        val qigSimilarTwelonelontsRelonquelonst = gelontQigSimilarTwelonelontsRelonquelonst(twelonelontId)

        Stats.trackOption(felontchCandidatelonsStat) {
          qigRankelonr
            .gelontSimilarCandidatelons(qigSimilarTwelonelontsRelonquelonst)
            .map { qigSimilarTwelonelontsRelonsponselon =>
              gelontCandidatelonsFromQigRelonsponselon(qigSimilarTwelonelontsRelonsponselon)
            }
        }
      caselon _ =>
        Futurelon.valuelon(Nonelon)
    }
  }

  privatelon delonf gelontQigSimilarTwelonelontsRelonquelonst(
    twelonelontId: Long
  ): QigRankelonrRelonquelonst = {
    // Notelon: QigRankelonr nelonelonds a non-elonmpty uselonrId to belon passelond to relonturn relonsults.
    // Welon arelon passing in a dummy uselonrId until welon fix this on QigRankelonr sidelon
    val clielonntContelonxt = ClielonntContelonxt(uselonrId = Somelon(0L))
    val productContelonxt = ProductContelonxt.TwistlySimilarTwelonelontsProductContelonxt(
      TwistlySimilarTwelonelontsProductContelonxt(twelonelontId = twelonelontId))

    QigRankelonrRelonquelonst(
      clielonntContelonxt = clielonntContelonxt,
      product = Product.TwistlySimilarTwelonelonts,
      productContelonxt = Somelon(productContelonxt),
    )
  }

  privatelon delonf gelontCandidatelonsFromQigRelonsponselon(
    qigSimilarTwelonelontsRelonsponselon: QigRankelonrRelonsponselon
  ): Option[Selonq[TwelonelontWithScorelon]] = {
    qigSimilarTwelonelontsRelonsponselon.productRelonsponselon match {
      caselon QigRankelonrProductRelonsponselon
            .TwistlySimilarTwelonelontCandidatelonsRelonsponselon(relonsponselon) =>
        val twelonelontsWithScorelon = relonsponselon.similarTwelonelonts
          .map { similarTwelonelontRelonsult =>
            TwelonelontWithScorelon(
              similarTwelonelontRelonsult.twelonelontRelonsult.twelonelontId,
              similarTwelonelontRelonsult.twelonelontRelonsult.scorelon.gelontOrelonlselon(0L))
          }
        Somelon(twelonelontsWithScorelon)

      caselon _ => Nonelon
    }
  }
}

objelonct TwelonelontBaselondQigSimilarityelonnginelon {

  delonf toSimilarityelonnginelonInfo(scorelon: Doublelon): SimilarityelonnginelonInfo = {
    SimilarityelonnginelonInfo(
      similarityelonnginelonTypelon = SimilarityelonnginelonTypelon.Qig,
      modelonlId = Nonelon,
      scorelon = Somelon(scorelon))
  }

  caselon class Quelonry(sourcelonId: IntelonrnalId)

  delonf fromParams(
    sourcelonId: IntelonrnalId,
    params: configapi.Params,
  ): elonnginelonQuelonry[Quelonry] = {
    elonnginelonQuelonry(
      Quelonry(sourcelonId = sourcelonId),
      params
    )
  }

}
