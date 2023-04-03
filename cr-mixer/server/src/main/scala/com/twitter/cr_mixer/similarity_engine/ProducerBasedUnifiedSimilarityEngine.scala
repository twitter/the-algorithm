packagelon com.twittelonr.cr_mixelonr.similarity_elonnginelon

import com.twittelonr.cr_mixelonr.modelonl.CandidatelonGelonnelonrationInfo
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.cr_mixelonr.modelonl.SimilarityelonnginelonInfo
import com.twittelonr.cr_mixelonr.modelonl.SourcelonInfo
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithCandidatelonGelonnelonrationInfo
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithScorelon
import com.twittelonr.cr_mixelonr.param.GlobalParams
import com.twittelonr.cr_mixelonr.param.ProducelonrBaselondCandidatelonGelonnelonrationParams
import com.twittelonr.cr_mixelonr.param.UnifielondSelonTwelonelontCombinationMelonthod
import com.twittelonr.cr_mixelonr.param.RelonlatelondTwelonelontProducelonrBaselondParams
import com.twittelonr.cr_mixelonr.param.SimClustelonrsANNParams
import com.twittelonr.cr_mixelonr.thriftscala.SimilarityelonnginelonTypelon
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.cr_mixelonr.util.IntelonrlelonavelonUtil
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.util.StatsUtil
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.timelonlinelons.configapi
import com.twittelonr.util.Duration
import com.twittelonr.util.Futurelon
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton
import scala.collelonction.mutablelon.ArrayBuffelonr

/**
 * This storelon looks for similar twelonelonts from UselonrTwelonelontGraph for a Sourcelon ProducelonrId
 * For a quelonry producelonrId,Uselonr Twelonelont Graph (UTG),
 * lelonts us find out which twelonelonts thelon quelonry producelonr's followelonrs co-elonngagelond
 */
@Singlelonton
caselon class ProducelonrBaselondUnifielondSimilarityelonnginelon(
  @Namelond(ModulelonNamelons.ProducelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelon)
  producelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelon: StandardSimilarityelonnginelon[
    ProducelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelon
  ],
  simClustelonrsANNSimilarityelonnginelon: StandardSimilarityelonnginelon[
    SimClustelonrsANNSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelon
  ],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds RelonadablelonStorelon[ProducelonrBaselondUnifielondSimilarityelonnginelon.Quelonry, Selonq[
      TwelonelontWithCandidatelonGelonnelonrationInfo
    ]] {

  import ProducelonrBaselondUnifielondSimilarityelonnginelon._
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val felontchCandidatelonsStat = stats.scopelon("felontchCandidatelons")

  ovelonrridelon delonf gelont(
    quelonry: Quelonry
  ): Futurelon[Option[Selonq[TwelonelontWithCandidatelonGelonnelonrationInfo]]] = {
    quelonry.sourcelonInfo.intelonrnalId match {
      caselon _: IntelonrnalId.UselonrId =>
        StatsUtil.trackOptionItelonmsStats(felontchCandidatelonsStat) {
          val sannCandidatelonsFut = if (quelonry.elonnablelonSimClustelonrsANN) {
            simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.simClustelonrsANNQuelonry)
          } elonlselon Futurelon.Nonelon

          val sann1CandidatelonsFut =
            if (quelonry.elonnablelonSimClustelonrsANN1) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.simClustelonrsANN1Quelonry)
            } elonlselon Futurelon.Nonelon

          val sann2CandidatelonsFut =
            if (quelonry.elonnablelonSimClustelonrsANN2) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.simClustelonrsANN2Quelonry)
            } elonlselon Futurelon.Nonelon

          val sann3CandidatelonsFut =
            if (quelonry.elonnablelonSimClustelonrsANN3) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.simClustelonrsANN3Quelonry)
            } elonlselon Futurelon.Nonelon

          val sann4CandidatelonsFut =
            if (quelonry.elonnablelonSimClustelonrsANN4) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.simClustelonrsANN4Quelonry)
            } elonlselon Futurelon.Nonelon

          val sann5CandidatelonsFut =
            if (quelonry.elonnablelonSimClustelonrsANN5) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.simClustelonrsANN5Quelonry)
            } elonlselon Futurelon.Nonelon

          val elonxpelonrimelonntalSANNCandidatelonsFut =
            if (quelonry.elonnablelonelonxpelonrimelonntalSimClustelonrsANN) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.elonxpelonrimelonntalSimClustelonrsANNQuelonry)
            } elonlselon Futurelon.Nonelon

          val utgCandidatelonsFut = if (quelonry.elonnablelonUtg) {
            producelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelon.gelontCandidatelons(quelonry.utgQuelonry)
          } elonlselon Futurelon.Nonelon

          Futurelon
            .join(
              sannCandidatelonsFut,
              sann1CandidatelonsFut,
              sann2CandidatelonsFut,
              sann3CandidatelonsFut,
              sann4CandidatelonsFut,
              sann5CandidatelonsFut,
              elonxpelonrimelonntalSANNCandidatelonsFut,
              utgCandidatelonsFut
            ).map {
              caselon (
                    simClustelonrsAnnCandidatelons,
                    simClustelonrsAnn1Candidatelons,
                    simClustelonrsAnn2Candidatelons,
                    simClustelonrsAnn3Candidatelons,
                    simClustelonrsAnn4Candidatelons,
                    simClustelonrsAnn5Candidatelons,
                    elonxpelonrimelonntalSANNCandidatelons,
                    uselonrTwelonelontGraphCandidatelons) =>
                val filtelonrelondSANNTwelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsAnnCandidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANNQuelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondelonxpelonrimelonntalSANNTwelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  elonxpelonrimelonntalSANNCandidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.elonxpelonrimelonntalSimClustelonrsANNQuelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN1Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsAnn1Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN1Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN2Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsAnn2Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN2Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN3Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsAnn3Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN3Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN4Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsAnn4Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN4Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN5Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsAnn5Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN5Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondUTGTwelonelonts =
                  uselonrTwelonelontGraphFiltelonr(uselonrTwelonelontGraphCandidatelons.toSelonq.flattelonn)

                val sannTwelonelontsWithCGInfo = filtelonrelondSANNTwelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = SimClustelonrsANNSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(quelonry.simClustelonrsANNQuelonry, twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }
                val sann1TwelonelontsWithCGInfo = filtelonrelondSANN1Twelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = SimClustelonrsANNSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(quelonry.simClustelonrsANN1Quelonry, twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }
                val sann2TwelonelontsWithCGInfo = filtelonrelondSANN2Twelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = SimClustelonrsANNSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(quelonry.simClustelonrsANN2Quelonry, twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }

                val sann3TwelonelontsWithCGInfo = filtelonrelondSANN3Twelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = SimClustelonrsANNSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(quelonry.simClustelonrsANN3Quelonry, twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }

                val sann4TwelonelontsWithCGInfo = filtelonrelondSANN4Twelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = SimClustelonrsANNSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(quelonry.simClustelonrsANN4Quelonry, twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }

                val sann5TwelonelontsWithCGInfo = filtelonrelondSANN5Twelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = SimClustelonrsANNSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(quelonry.simClustelonrsANN5Quelonry, twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }

                val elonxpelonrimelonntalSANNTwelonelontsWithCGInfo = filtelonrelondelonxpelonrimelonntalSANNTwelonelonts.map {
                  twelonelontWithScorelon =>
                    val similarityelonnginelonInfo = SimClustelonrsANNSimilarityelonnginelon
                      .toSimilarityelonnginelonInfo(
                        quelonry.elonxpelonrimelonntalSimClustelonrsANNQuelonry,
                        twelonelontWithScorelon.scorelon)
                    TwelonelontWithCandidatelonGelonnelonrationInfo(
                      twelonelontWithScorelon.twelonelontId,
                      CandidatelonGelonnelonrationInfo(
                        Somelon(quelonry.sourcelonInfo),
                        similarityelonnginelonInfo,
                        Selonq(similarityelonnginelonInfo)
                      ))
                }
                val utgTwelonelontsWithCGInfo = filtelonrelondUTGTwelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo =
                    ProducelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelon
                      .toSimilarityelonnginelonInfo(twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }

                val candidatelonSourcelonsToBelonIntelonrlelonavelond =
                  ArrayBuffelonr[Selonq[TwelonelontWithCandidatelonGelonnelonrationInfo]](
                    sannTwelonelontsWithCGInfo,
                    sann1TwelonelontsWithCGInfo,
                    sann2TwelonelontsWithCGInfo,
                    sann3TwelonelontsWithCGInfo,
                    sann4TwelonelontsWithCGInfo,
                    sann5TwelonelontsWithCGInfo,
                    elonxpelonrimelonntalSANNTwelonelontsWithCGInfo,
                  )

                if (quelonry.utgCombinationMelonthod == UnifielondSelonTwelonelontCombinationMelonthod.Intelonrlelonavelon) {
                  candidatelonSourcelonsToBelonIntelonrlelonavelond += utgTwelonelontsWithCGInfo
                }

                val intelonrlelonavelondCandidatelons =
                  IntelonrlelonavelonUtil.intelonrlelonavelon(candidatelonSourcelonsToBelonIntelonrlelonavelond)

                val candidatelonSourcelonsToBelonOrdelonrelond =
                  ArrayBuffelonr[Selonq[TwelonelontWithCandidatelonGelonnelonrationInfo]](intelonrlelonavelondCandidatelons)

                if (quelonry.utgCombinationMelonthod == UnifielondSelonTwelonelontCombinationMelonthod.Frontload)
                  candidatelonSourcelonsToBelonOrdelonrelond.prelonpelonnd(utgTwelonelontsWithCGInfo)

                val candidatelonsFromGivelonnOrdelonrCombination =
                  SimilaritySourcelonOrdelonringUtil.kelonelonpGivelonnOrdelonr(candidatelonSourcelonsToBelonOrdelonrelond)

                val unifielondCandidatelonsWithUnifielondCGInfo = candidatelonsFromGivelonnOrdelonrCombination.map {
                  candidatelon =>
                    /***
                     * whelonn a candidatelon was madelon by intelonrlelonavelon/kelonelonpGivelonnOrdelonr,
                     * thelonn welon apply gelontProducelonrBaselondUnifielondCGInfo() to ovelonrridelon with thelon unifielond CGInfo
                     *
                     * in contributingSelon list for intelonrlelonavelon. Welon only havelon thelon choselonn Selon availablelon.
                     * This is hard to add for intelonrlelonavelon, and welon plan to add it latelonr aftelonr abstraction improvelonmelonnt.
                     */
                    TwelonelontWithCandidatelonGelonnelonrationInfo(
                      twelonelontId = candidatelon.twelonelontId,
                      candidatelonGelonnelonrationInfo = gelontProducelonrBaselondUnifielondCGInfo(
                        candidatelon.candidatelonGelonnelonrationInfo.sourcelonInfoOpt,
                        candidatelon.gelontSimilarityScorelon,
                        candidatelon.candidatelonGelonnelonrationInfo.contributingSimilarityelonnginelons
                      ) // gelontSimilarityScorelon comelons from elonithelonr unifielondScorelon or singlelon scorelon
                    )
                }
                stats.stat("unifielond_candidatelon_sizelon").add(unifielondCandidatelonsWithUnifielondCGInfo.sizelon)
                val truncatelondCandidatelons =
                  unifielondCandidatelonsWithUnifielondCGInfo.takelon(quelonry.maxCandidatelonNumPelonrSourcelonKelony)
                stats.stat("truncatelondCandidatelons_sizelon").add(truncatelondCandidatelons.sizelon)

                Somelon(truncatelondCandidatelons)

            }
        }

      caselon _ =>
        stats.countelonr("sourcelonId_is_not_uselonrId_cnt").incr()
        Futurelon.Nonelon
    }
  }

  privatelon delonf simClustelonrsCandidatelonMinScorelonFiltelonr(
    simClustelonrsAnnCandidatelons: Selonq[TwelonelontWithScorelon],
    simClustelonrsMinScorelon: Doublelon,
    simClustelonrsANNConfigId: String
  ): Selonq[TwelonelontWithScorelon] = {
    val filtelonrelondCandidatelons = simClustelonrsAnnCandidatelons
      .filtelonr { candidatelon =>
        candidatelon.scorelon > simClustelonrsMinScorelon
      }

    stats.stat(simClustelonrsANNConfigId, "simClustelonrsAnnCandidatelons_sizelon").add(filtelonrelondCandidatelons.sizelon)
    stats.countelonr(simClustelonrsANNConfigId, "simClustelonrsAnnRelonquelonsts").incr()
    if (filtelonrelondCandidatelons.iselonmpty)
      stats.countelonr(simClustelonrsANNConfigId, "elonmptyFiltelonrelondSimClustelonrsAnnCandidatelons").incr()

    filtelonrelondCandidatelons.map { candidatelon =>
      TwelonelontWithScorelon(candidatelon.twelonelontId, candidatelon.scorelon)
    }
  }

  /** A no-op filtelonr as UTG filtelonr alrelonady happelonnelond at UTG selonrvicelon sidelon */
  privatelon delonf uselonrTwelonelontGraphFiltelonr(
    uselonrTwelonelontGraphCandidatelons: Selonq[TwelonelontWithScorelon]
  ): Selonq[TwelonelontWithScorelon] = {
    val filtelonrelondCandidatelons = uselonrTwelonelontGraphCandidatelons

    stats.stat("uselonrTwelonelontGraphCandidatelons_sizelon").add(uselonrTwelonelontGraphCandidatelons.sizelon)
    if (filtelonrelondCandidatelons.iselonmpty) stats.countelonr("elonmptyFiltelonrelondUselonrTwelonelontGraphCandidatelons").incr()

    filtelonrelondCandidatelons.map { candidatelon =>
      TwelonelontWithScorelon(candidatelon.twelonelontId, candidatelon.scorelon)
    }
  }

}
objelonct ProducelonrBaselondUnifielondSimilarityelonnginelon {

  /***
   * elonvelonry candidatelon will havelon thelon CG Info with ProducelonrBaselondUnifielondSimilarityelonnginelon
   * as thelony arelon gelonnelonratelond by a compositelon of Similarity elonnginelons.
   * Additionally, welon storelon thelon contributing Selons (elong., SANN, UTG).
   */
  privatelon delonf gelontProducelonrBaselondUnifielondCGInfo(
    sourcelonInfoOpt: Option[SourcelonInfo],
    unifielondScorelon: Doublelon,
    contributingSimilarityelonnginelons: Selonq[SimilarityelonnginelonInfo]
  ): CandidatelonGelonnelonrationInfo = {
    CandidatelonGelonnelonrationInfo(
      sourcelonInfoOpt,
      SimilarityelonnginelonInfo(
        similarityelonnginelonTypelon = SimilarityelonnginelonTypelon.ProducelonrBaselondUnifielondSimilarityelonnginelon,
        modelonlId = Nonelon, // Welon do not assign modelonlId for a unifielond similarity elonnginelon
        scorelon = Somelon(unifielondScorelon)
      ),
      contributingSimilarityelonnginelons
    )
  }

  caselon class Quelonry(
    sourcelonInfo: SourcelonInfo,
    maxCandidatelonNumPelonrSourcelonKelony: Int,
    maxTwelonelontAgelonHours: Duration,
    // SimClustelonrs
    elonnablelonSimClustelonrsANN: Boolelonan,
    simClustelonrsANNQuelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonelonxpelonrimelonntalSimClustelonrsANN: Boolelonan,
    elonxpelonrimelonntalSimClustelonrsANNQuelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN1: Boolelonan,
    simClustelonrsANN1Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN2: Boolelonan,
    simClustelonrsANN2Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN4: Boolelonan,
    simClustelonrsANN4Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN3: Boolelonan,
    simClustelonrsANN3Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN5: Boolelonan,
    simClustelonrsANN5Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    simClustelonrsMinScorelon: Doublelon,
    // UTG
    elonnablelonUtg: Boolelonan,
    utgCombinationMelonthod: UnifielondSelonTwelonelontCombinationMelonthod.Valuelon,
    utgQuelonry: elonnginelonQuelonry[ProducelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelon.Quelonry])

  delonf fromParams(
    sourcelonInfo: SourcelonInfo,
    params: configapi.Params,
  ): elonnginelonQuelonry[Quelonry] = {
    val maxCandidatelonNumPelonrSourcelonKelony = params(GlobalParams.MaxCandidatelonNumPelonrSourcelonKelonyParam)
    val maxTwelonelontAgelonHours = params(GlobalParams.MaxTwelonelontAgelonHoursParam)
    // SimClustelonrs
    val elonnablelonSimClustelonrsANN = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANNParam)
    val simClustelonrsModelonlVelonrsion =
      ModelonlVelonrsions.elonnum.elonnumToSimClustelonrsModelonlVelonrsionMap(params(GlobalParams.ModelonlVelonrsionParam))
    val simClustelonrsANNConfigId = params(SimClustelonrsANNParams.SimClustelonrsANNConfigId)
    // SimClustelonrs - elonxpelonrimelonntal SANN Similarity elonnginelon
    val elonnablelonelonxpelonrimelonntalSimClustelonrsANN = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.elonnablelonelonxpelonrimelonntalSimClustelonrsANNParam)
    val elonxpelonrimelonntalSimClustelonrsANNConfigId = params(
      SimClustelonrsANNParams.elonxpelonrimelonntalSimClustelonrsANNConfigId)
    // SimClustelonrs - SANN clustelonr 1 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN1 = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN1Param)
    val simClustelonrsANN1ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN1ConfigId)
    // SimClustelonrs - SANN clustelonr 2 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN2 = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN2Param)
    val simClustelonrsANN2ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN2ConfigId)
    // SimClustelonrs - SANN clustelonr 3 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN3 = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN3Param)
    val simClustelonrsANN3ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN3ConfigId)
    // SimClustelonrs - SANN clustelonr 5 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN5 = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN5Param)
    val simClustelonrsANN5ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN5ConfigId)
    val elonnablelonSimClustelonrsANN4 = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN4Param)
    val simClustelonrsANN4ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN4ConfigId)

    val simClustelonrsMinScorelon = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.SimClustelonrsMinScorelonParam)

    // SimClustelonrs ANN Quelonry
    val simClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANNConfigId,
      params
    )
    val elonxpelonrimelonntalSimClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      elonxpelonrimelonntalSimClustelonrsANNConfigId,
      params
    )
    val simClustelonrsANN1Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN1ConfigId,
      params
    )
    val simClustelonrsANN2Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN2ConfigId,
      params
    )
    val simClustelonrsANN3Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN3ConfigId,
      params
    )
    val simClustelonrsANN5Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN5ConfigId,
      params
    )
    val simClustelonrsANN4Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN4ConfigId,
      params
    )
    // UTG
    val elonnablelonUtg = params(ProducelonrBaselondCandidatelonGelonnelonrationParams.elonnablelonUTGParam)
    val utgCombinationMelonthod = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.UtgCombinationMelonthodParam)

    elonnginelonQuelonry(
      Quelonry(
        sourcelonInfo = sourcelonInfo,
        maxCandidatelonNumPelonrSourcelonKelony = maxCandidatelonNumPelonrSourcelonKelony,
        maxTwelonelontAgelonHours = maxTwelonelontAgelonHours,
        elonnablelonSimClustelonrsANN = elonnablelonSimClustelonrsANN,
        simClustelonrsANNQuelonry = simClustelonrsANNQuelonry,
        elonnablelonelonxpelonrimelonntalSimClustelonrsANN = elonnablelonelonxpelonrimelonntalSimClustelonrsANN,
        elonxpelonrimelonntalSimClustelonrsANNQuelonry = elonxpelonrimelonntalSimClustelonrsANNQuelonry,
        elonnablelonSimClustelonrsANN1 = elonnablelonSimClustelonrsANN1,
        simClustelonrsANN1Quelonry = simClustelonrsANN1Quelonry,
        elonnablelonSimClustelonrsANN2 = elonnablelonSimClustelonrsANN2,
        simClustelonrsANN2Quelonry = simClustelonrsANN2Quelonry,
        elonnablelonSimClustelonrsANN3 = elonnablelonSimClustelonrsANN3,
        simClustelonrsANN3Quelonry = simClustelonrsANN3Quelonry,
        elonnablelonSimClustelonrsANN5 = elonnablelonSimClustelonrsANN5,
        simClustelonrsANN5Quelonry = simClustelonrsANN5Quelonry,
        elonnablelonSimClustelonrsANN4 = elonnablelonSimClustelonrsANN4,
        simClustelonrsANN4Quelonry = simClustelonrsANN4Quelonry,
        simClustelonrsMinScorelon = simClustelonrsMinScorelon,
        elonnablelonUtg = elonnablelonUtg,
        utgCombinationMelonthod = utgCombinationMelonthod,
        utgQuelonry = ProducelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelon
          .fromParams(sourcelonInfo.intelonrnalId, params)
      ),
      params
    )
  }

  delonf fromParamsForRelonlatelondTwelonelont(
    intelonrnalId: IntelonrnalId,
    params: configapi.Params
  ): elonnginelonQuelonry[Quelonry] = {
    val maxCandidatelonNumPelonrSourcelonKelony = params(GlobalParams.MaxCandidatelonNumPelonrSourcelonKelonyParam)
    val maxTwelonelontAgelonHours = params(GlobalParams.MaxTwelonelontAgelonHoursParam)
    // SimClustelonrs
    val elonnablelonSimClustelonrsANN = params(RelonlatelondTwelonelontProducelonrBaselondParams.elonnablelonSimClustelonrsANNParam)
    val simClustelonrsModelonlVelonrsion =
      ModelonlVelonrsions.elonnum.elonnumToSimClustelonrsModelonlVelonrsionMap(params(GlobalParams.ModelonlVelonrsionParam))
    val simClustelonrsANNConfigId = params(SimClustelonrsANNParams.SimClustelonrsANNConfigId)
    val simClustelonrsMinScorelon =
      params(RelonlatelondTwelonelontProducelonrBaselondParams.SimClustelonrsMinScorelonParam)
    // SimClustelonrs - elonxpelonrimelonntal SANN Similarity elonnginelon
    val elonnablelonelonxpelonrimelonntalSimClustelonrsANN = params(
      RelonlatelondTwelonelontProducelonrBaselondParams.elonnablelonelonxpelonrimelonntalSimClustelonrsANNParam)
    val elonxpelonrimelonntalSimClustelonrsANNConfigId = params(
      SimClustelonrsANNParams.elonxpelonrimelonntalSimClustelonrsANNConfigId)
    // SimClustelonrs - SANN clustelonr 1 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN1 = params(RelonlatelondTwelonelontProducelonrBaselondParams.elonnablelonSimClustelonrsANN1Param)
    val simClustelonrsANN1ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN1ConfigId)
    // SimClustelonrs - SANN clustelonr 2 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN2 = params(RelonlatelondTwelonelontProducelonrBaselondParams.elonnablelonSimClustelonrsANN2Param)
    val simClustelonrsANN2ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN2ConfigId)
    // SimClustelonrs - SANN clustelonr 3 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN3 = params(RelonlatelondTwelonelontProducelonrBaselondParams.elonnablelonSimClustelonrsANN3Param)
    val simClustelonrsANN3ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN3ConfigId)
    // SimClustelonrs - SANN clustelonr 5 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN5 = params(RelonlatelondTwelonelontProducelonrBaselondParams.elonnablelonSimClustelonrsANN5Param)
    val simClustelonrsANN5ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN5ConfigId)

    val elonnablelonSimClustelonrsANN4 = params(RelonlatelondTwelonelontProducelonrBaselondParams.elonnablelonSimClustelonrsANN4Param)
    val simClustelonrsANN4ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN4ConfigId)
    // Build SANN Quelonry
    val simClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANNConfigId,
      params
    )
    val elonxpelonrimelonntalSimClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      elonxpelonrimelonntalSimClustelonrsANNConfigId,
      params
    )
    val simClustelonrsANN1Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN1ConfigId,
      params
    )
    val simClustelonrsANN2Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN2ConfigId,
      params
    )
    val simClustelonrsANN3Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN3ConfigId,
      params
    )
    val simClustelonrsANN5Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN5ConfigId,
      params
    )
    val simClustelonrsANN4Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.FavBaselondProducelonr,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN4ConfigId,
      params
    )
    // UTG
    val elonnablelonUtg = params(RelonlatelondTwelonelontProducelonrBaselondParams.elonnablelonUTGParam)
    val utgCombinationMelonthod = params(
      ProducelonrBaselondCandidatelonGelonnelonrationParams.UtgCombinationMelonthodParam)

    // SourcelonTypelon.RelonquelonstUselonrId is a placelonholdelonr.
    val sourcelonInfo = SourcelonInfo(SourcelonTypelon.RelonquelonstUselonrId, intelonrnalId, Nonelon)

    elonnginelonQuelonry(
      Quelonry(
        sourcelonInfo = sourcelonInfo,
        maxCandidatelonNumPelonrSourcelonKelony = maxCandidatelonNumPelonrSourcelonKelony,
        maxTwelonelontAgelonHours = maxTwelonelontAgelonHours,
        elonnablelonSimClustelonrsANN = elonnablelonSimClustelonrsANN,
        simClustelonrsANNQuelonry = simClustelonrsANNQuelonry,
        elonnablelonelonxpelonrimelonntalSimClustelonrsANN = elonnablelonelonxpelonrimelonntalSimClustelonrsANN,
        elonxpelonrimelonntalSimClustelonrsANNQuelonry = elonxpelonrimelonntalSimClustelonrsANNQuelonry,
        elonnablelonSimClustelonrsANN1 = elonnablelonSimClustelonrsANN1,
        simClustelonrsANN1Quelonry = simClustelonrsANN1Quelonry,
        elonnablelonSimClustelonrsANN2 = elonnablelonSimClustelonrsANN2,
        simClustelonrsANN2Quelonry = simClustelonrsANN2Quelonry,
        elonnablelonSimClustelonrsANN3 = elonnablelonSimClustelonrsANN3,
        simClustelonrsANN3Quelonry = simClustelonrsANN3Quelonry,
        elonnablelonSimClustelonrsANN5 = elonnablelonSimClustelonrsANN5,
        simClustelonrsANN5Quelonry = simClustelonrsANN5Quelonry,
        elonnablelonSimClustelonrsANN4 = elonnablelonSimClustelonrsANN4,
        simClustelonrsANN4Quelonry = simClustelonrsANN4Quelonry,
        simClustelonrsMinScorelon = simClustelonrsMinScorelon,
        elonnablelonUtg = elonnablelonUtg,
        utgQuelonry = ProducelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelon.fromParams(intelonrnalId, params),
        utgCombinationMelonthod = utgCombinationMelonthod
      ),
      params
    )
  }

}
