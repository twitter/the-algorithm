packagelon com.twittelonr.cr_mixelonr.similarity_elonnginelon

import com.twittelonr.cr_mixelonr.modelonl.CandidatelonGelonnelonrationInfo
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.cr_mixelonr.modelonl.SimilarityelonnginelonInfo
import com.twittelonr.cr_mixelonr.modelonl.SourcelonInfo
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithCandidatelonGelonnelonrationInfo
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithScorelon
import com.twittelonr.cr_mixelonr.param.GlobalParams
import com.twittelonr.cr_mixelonr.param.RelonlatelondTwelonelontTwelonelontBaselondParams
import com.twittelonr.cr_mixelonr.param.RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams
import com.twittelonr.cr_mixelonr.param.SimClustelonrsANNParams
import com.twittelonr.cr_mixelonr.param.TwelonelontBaselondCandidatelonGelonnelonrationParams
import com.twittelonr.cr_mixelonr.param.TwelonelontBaselondTwHINParams
import com.twittelonr.cr_mixelonr.thriftscala.SimilarityelonnginelonTypelon
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.cr_mixelonr.util.IntelonrlelonavelonUtil
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.util.StatsUtil
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.timelonlinelons.configapi
import com.twittelonr.util.Duration
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Timelon
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton
import scala.collelonction.mutablelon.ArrayBuffelonr

/**
 * This storelon felontchelons similar twelonelonts from multiplelon twelonelont baselond candidatelon sourcelons
 * and combinelons thelonm using diffelonrelonnt melonthods obtainelond from quelonry params
 */
@Singlelonton
caselon class TwelonelontBaselondUnifielondSimilarityelonnginelon(
  @Namelond(ModulelonNamelons.TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon)
  twelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon: StandardSimilarityelonnginelon[
    TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelon
  ],
  @Namelond(ModulelonNamelons.TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon)
  twelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon: StandardSimilarityelonnginelon[
    TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelon
  ],
  simClustelonrsANNSimilarityelonnginelon: StandardSimilarityelonnginelon[
    SimClustelonrsANNSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelon
  ],
  @Namelond(ModulelonNamelons.TwelonelontBaselondQigSimilarityelonnginelon)
  twelonelontBaselondQigSimilarTwelonelontsSimilarityelonnginelon: StandardSimilarityelonnginelon[
    TwelonelontBaselondQigSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelon
  ],
  @Namelond(ModulelonNamelons.TwelonelontBaselondTwHINANNSimilarityelonnginelon)
  twelonelontBaselondTwHINANNSimilarityelonnginelon: HnswANNSimilarityelonnginelon,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds RelonadablelonStorelon[
      TwelonelontBaselondUnifielondSimilarityelonnginelon.Quelonry,
      Selonq[TwelonelontWithCandidatelonGelonnelonrationInfo]
    ] {

  import TwelonelontBaselondUnifielondSimilarityelonnginelon._
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val felontchCandidatelonsStat = stats.scopelon("felontchCandidatelons")

  ovelonrridelon delonf gelont(
    quelonry: Quelonry
  ): Futurelon[Option[Selonq[TwelonelontWithCandidatelonGelonnelonrationInfo]]] = {

    quelonry.sourcelonInfo.intelonrnalId match {
      caselon _: IntelonrnalId.TwelonelontId =>
        StatsUtil.trackOptionItelonmsStats(felontchCandidatelonsStat) {
          val twhinQuelonry =
            HnswANNelonnginelonQuelonry(
              sourcelonId = quelonry.sourcelonInfo.intelonrnalId,
              modelonlId = quelonry.twhinModelonlId,
              params = quelonry.params)
          val utgCandidatelonsFut =
            if (quelonry.elonnablelonUtg)
              twelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon.gelontCandidatelons(quelonry.utgQuelonry)
            elonlselon Futurelon.Nonelon

          val uvgCandidatelonsFut =
            if (quelonry.elonnablelonUvg)
              twelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon.gelontCandidatelons(quelonry.uvgQuelonry)
            elonlselon Futurelon.Nonelon

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

          val sann5CandidatelonsFut =
            if (quelonry.elonnablelonSimClustelonrsANN5) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.simClustelonrsANN5Quelonry)
            } elonlselon Futurelon.Nonelon

          val sann4CandidatelonsFut =
            if (quelonry.elonnablelonSimClustelonrsANN4) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.simClustelonrsANN4Quelonry)
            } elonlselon Futurelon.Nonelon

          val elonxpelonrimelonntalSANNCandidatelonsFut =
            if (quelonry.elonnablelonelonxpelonrimelonntalSimClustelonrsANN) {
              simClustelonrsANNSimilarityelonnginelon.gelontCandidatelons(quelonry.elonxpelonrimelonntalSimClustelonrsANNQuelonry)
            } elonlselon Futurelon.Nonelon

          val qigCandidatelonsFut =
            if (quelonry.elonnablelonQig)
              twelonelontBaselondQigSimilarTwelonelontsSimilarityelonnginelon.gelontCandidatelons(quelonry.qigQuelonry)
            elonlselon Futurelon.Nonelon

          val twHINCandidatelonFut = if (quelonry.elonnablelonTwHIN) {
            twelonelontBaselondTwHINANNSimilarityelonnginelon.gelontCandidatelons(twhinQuelonry)
          } elonlselon Futurelon.Nonelon

          Futurelon
            .join(
              utgCandidatelonsFut,
              sannCandidatelonsFut,
              sann1CandidatelonsFut,
              sann2CandidatelonsFut,
              sann3CandidatelonsFut,
              sann5CandidatelonsFut,
              sann4CandidatelonsFut,
              elonxpelonrimelonntalSANNCandidatelonsFut,
              qigCandidatelonsFut,
              twHINCandidatelonFut,
              uvgCandidatelonsFut
            ).map {
              caselon (
                    uselonrTwelonelontGraphCandidatelons,
                    simClustelonrsANNCandidatelons,
                    simClustelonrsANN1Candidatelons,
                    simClustelonrsANN2Candidatelons,
                    simClustelonrsANN3Candidatelons,
                    simClustelonrsANN5Candidatelons,
                    simClustelonrsANN4Candidatelons,
                    elonxpelonrimelonntalSANNCandidatelons,
                    qigSimilarTwelonelontsCandidatelons,
                    twhinCandidatelons,
                    uselonrVidelonoGraphCandidatelons) =>
                val filtelonrelondUTGTwelonelonts =
                  uselonrTwelonelontGraphFiltelonr(uselonrTwelonelontGraphCandidatelons.toSelonq.flattelonn)
                val filtelonrelondUVGTwelonelonts =
                  uselonrVidelonoGraphFiltelonr(uselonrVidelonoGraphCandidatelons.toSelonq.flattelonn)
                val filtelonrelondSANNTwelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsANNCandidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANNQuelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN1Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsANN1Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN1Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN2Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsANN2Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN2Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN3Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsANN3Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN3Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN4Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsANN4Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN4Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondSANN5Twelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  simClustelonrsANN5Candidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsMinScorelon,
                  quelonry.simClustelonrsANN5Quelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondelonxpelonrimelonntalSANNTwelonelonts = simClustelonrsCandidatelonMinScorelonFiltelonr(
                  elonxpelonrimelonntalSANNCandidatelons.toSelonq.flattelonn,
                  quelonry.simClustelonrsVidelonoBaselondMinScorelon,
                  quelonry.elonxpelonrimelonntalSimClustelonrsANNQuelonry.storelonQuelonry.simClustelonrsANNConfigId)

                val filtelonrelondQigTwelonelonts = qigSimilarTwelonelontsFiltelonr(
                  qigSimilarTwelonelontsCandidatelons.toSelonq.flattelonn,
                  quelonry.qigMaxTwelonelontAgelonHours,
                  quelonry.qigMaxNumSimilarTwelonelonts
                )

                val filtelonrelondTwHINTwelonelonts = twhinFiltelonr(
                  twhinCandidatelons.toSelonq.flattelonn.sortBy(-_.scorelon),
                  quelonry.twhinMaxTwelonelontAgelonHours,
                  twelonelontBaselondTwHINANNSimilarityelonnginelon.gelontScopelondStats
                )
                val utgTwelonelontsWithCGInfo = filtelonrelondUTGTwelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }

                val uvgTwelonelontsWithCGInfo = filtelonrelondUVGTwelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }
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
                val qigTwelonelontsWithCGInfo = filtelonrelondQigTwelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = TwelonelontBaselondQigSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(twelonelontWithScorelon.scorelon)
                  TwelonelontWithCandidatelonGelonnelonrationInfo(
                    twelonelontWithScorelon.twelonelontId,
                    CandidatelonGelonnelonrationInfo(
                      Somelon(quelonry.sourcelonInfo),
                      similarityelonnginelonInfo,
                      Selonq(similarityelonnginelonInfo)
                    ))
                }

                val twHINTwelonelontsWithCGInfo = filtelonrelondTwHINTwelonelonts.map { twelonelontWithScorelon =>
                  val similarityelonnginelonInfo = twelonelontBaselondTwHINANNSimilarityelonnginelon
                    .toSimilarityelonnginelonInfo(twhinQuelonry, twelonelontWithScorelon.scorelon)
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
                    elonxpelonrimelonntalSANNTwelonelontsWithCGInfo,
                    sann1TwelonelontsWithCGInfo,
                    sann2TwelonelontsWithCGInfo,
                    sann3TwelonelontsWithCGInfo,
                    sann5TwelonelontsWithCGInfo,
                    sann4TwelonelontsWithCGInfo,
                    qigTwelonelontsWithCGInfo,
                    uvgTwelonelontsWithCGInfo,
                    utgTwelonelontsWithCGInfo,
                    twHINTwelonelontsWithCGInfo
                  )

                val intelonrlelonavelondCandidatelons =
                  IntelonrlelonavelonUtil.intelonrlelonavelon(candidatelonSourcelonsToBelonIntelonrlelonavelond)

                val unifielondCandidatelonsWithUnifielondCGInfo =
                  intelonrlelonavelondCandidatelons.map { candidatelon =>
                    /***
                     * whelonn a candidatelon was madelon by intelonrlelonavelon/kelonelonpGivelonnOrdelonr,
                     * thelonn welon apply gelontTwelonelontBaselondUnifielondCGInfo() to ovelonrridelon with thelon unifielond CGInfo
                     *
                     * welon'll not havelon ALL Selons that gelonnelonratelond thelon twelonelont
                     * in contributingSelon list for intelonrlelonavelon. Welon only havelon thelon choselonn Selon availablelon.
                     */
                    TwelonelontWithCandidatelonGelonnelonrationInfo(
                      twelonelontId = candidatelon.twelonelontId,
                      candidatelonGelonnelonrationInfo = gelontTwelonelontBaselondUnifielondCGInfo(
                        candidatelon.candidatelonGelonnelonrationInfo.sourcelonInfoOpt,
                        candidatelon.gelontSimilarityScorelon,
                        candidatelon.candidatelonGelonnelonrationInfo.contributingSimilarityelonnginelons
                      ) // gelontSimilarityScorelon comelons from elonithelonr unifielondScorelon or singlelon scorelon
                    )
                  }
                stats
                  .stat("unifielond_candidatelon_sizelon").add(unifielondCandidatelonsWithUnifielondCGInfo.sizelon)

                val truncatelondCandidatelons =
                  unifielondCandidatelonsWithUnifielondCGInfo.takelon(quelonry.maxCandidatelonNumPelonrSourcelonKelony)
                stats.stat("truncatelondCandidatelons_sizelon").add(truncatelondCandidatelons.sizelon)

                Somelon(truncatelondCandidatelons)
            }
        }

      caselon _ =>
        stats.countelonr("sourcelonId_is_not_twelonelontId_cnt").incr()
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

  /** Relonturns a list of twelonelonts that arelon gelonnelonratelond lelonss than `maxTwelonelontAgelonHours` hours ago */
  privatelon delonf twelonelontAgelonFiltelonr(
    candidatelons: Selonq[TwelonelontWithScorelon],
    maxTwelonelontAgelonHours: Duration
  ): Selonq[TwelonelontWithScorelon] = {
    // Twelonelont IDs arelon approximatelonly chronological (selonelon http://go/snowflakelon),
    // so welon arelon building thelon elonarlielonst twelonelont id oncelon
    // Thelon pelonr-candidatelon logic helonrelon thelonn belon candidatelon.twelonelontId > elonarlielonstPelonrmittelondTwelonelontId, which is far chelonapelonr.
    val elonarlielonstTwelonelontId = SnowflakelonId.firstIdFor(Timelon.now - maxTwelonelontAgelonHours)
    candidatelons.filtelonr { candidatelon => candidatelon.twelonelontId >= elonarlielonstTwelonelontId }
  }

  privatelon delonf twhinFiltelonr(
    twhinCandidatelons: Selonq[TwelonelontWithScorelon],
    twhinMaxTwelonelontAgelonHours: Duration,
    simelonnginelonStats: StatsReloncelonivelonr
  ): Selonq[TwelonelontWithScorelon] = {
    simelonnginelonStats.stat("twhinCandidatelons_sizelon").add(twhinCandidatelons.sizelon)
    val candidatelons = twhinCandidatelons.map { candidatelon =>
      TwelonelontWithScorelon(candidatelon.twelonelontId, candidatelon.scorelon)
    }

    val filtelonrelondCandidatelons = twelonelontAgelonFiltelonr(candidatelons, twhinMaxTwelonelontAgelonHours)
    simelonnginelonStats.stat("filtelonrelondTwhinCandidatelons_sizelon").add(filtelonrelondCandidatelons.sizelon)
    if (filtelonrelondCandidatelons.iselonmpty) simelonnginelonStats.countelonr("elonmptyFiltelonrelondTwhinCandidatelons").incr()

    filtelonrelondCandidatelons
  }

  /** A no-op filtelonr as UTG filtelonring alrelonady happelonns on UTG selonrvicelon sidelon */
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

  /** A no-op filtelonr as UVG filtelonring alrelonady happelonns on UVG selonrvicelon sidelon */
  privatelon delonf uselonrVidelonoGraphFiltelonr(
    uselonrVidelonoGraphCandidatelons: Selonq[TwelonelontWithScorelon]
  ): Selonq[TwelonelontWithScorelon] = {
    val filtelonrelondCandidatelons = uselonrVidelonoGraphCandidatelons

    stats.stat("uselonrVidelonoGraphCandidatelons_sizelon").add(uselonrVidelonoGraphCandidatelons.sizelon)
    if (filtelonrelondCandidatelons.iselonmpty) stats.countelonr("elonmptyFiltelonrelondUselonrVidelonoGraphCandidatelons").incr()

    filtelonrelondCandidatelons.map { candidatelon =>
      TwelonelontWithScorelon(candidatelon.twelonelontId, candidatelon.scorelon)
    }
  }
  privatelon delonf qigSimilarTwelonelontsFiltelonr(
    qigSimilarTwelonelontsCandidatelons: Selonq[TwelonelontWithScorelon],
    qigMaxTwelonelontAgelonHours: Duration,
    qigMaxNumSimilarTwelonelonts: Int
  ): Selonq[TwelonelontWithScorelon] = {
    val agelonFiltelonrelondCandidatelons = twelonelontAgelonFiltelonr(qigSimilarTwelonelontsCandidatelons, qigMaxTwelonelontAgelonHours)
    stats.stat("agelonFiltelonrelondQigSimilarTwelonelontsCandidatelons_sizelon").add(agelonFiltelonrelondCandidatelons.sizelon)

    val filtelonrelondCandidatelons = agelonFiltelonrelondCandidatelons.takelon(qigMaxNumSimilarTwelonelonts)
    if (filtelonrelondCandidatelons.iselonmpty) stats.countelonr("elonmptyFiltelonrelondQigSimilarTwelonelontsCandidatelons").incr()

    filtelonrelondCandidatelons
  }

  /***
   * elonvelonry candidatelon will havelon thelon CG Info with TwelonelontBaselondUnifielondSimilarityelonnginelon
   * as thelony arelon gelonnelonratelond by a compositelon of Similarity elonnginelons.
   * Additionally, welon storelon thelon contributing Selons (elong., SANN, UTG).
   */
  privatelon delonf gelontTwelonelontBaselondUnifielondCGInfo(
    sourcelonInfoOpt: Option[SourcelonInfo],
    unifielondScorelon: Doublelon,
    contributingSimilarityelonnginelons: Selonq[SimilarityelonnginelonInfo]
  ): CandidatelonGelonnelonrationInfo = {
    CandidatelonGelonnelonrationInfo(
      sourcelonInfoOpt,
      SimilarityelonnginelonInfo(
        similarityelonnginelonTypelon = SimilarityelonnginelonTypelon.TwelonelontBaselondUnifielondSimilarityelonnginelon,
        modelonlId = Nonelon, // Welon do not assign modelonlId for a unifielond similarity elonnginelon
        scorelon = Somelon(unifielondScorelon)
      ),
      contributingSimilarityelonnginelons
    )
  }
}

objelonct TwelonelontBaselondUnifielondSimilarityelonnginelon {

  caselon class Quelonry(
    sourcelonInfo: SourcelonInfo,
    maxCandidatelonNumPelonrSourcelonKelony: Int,
    elonnablelonSimClustelonrsANN: Boolelonan,
    simClustelonrsANNQuelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonelonxpelonrimelonntalSimClustelonrsANN: Boolelonan,
    elonxpelonrimelonntalSimClustelonrsANNQuelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN1: Boolelonan,
    simClustelonrsANN1Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN2: Boolelonan,
    simClustelonrsANN2Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN3: Boolelonan,
    simClustelonrsANN3Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN5: Boolelonan,
    simClustelonrsANN5Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    elonnablelonSimClustelonrsANN4: Boolelonan,
    simClustelonrsANN4Quelonry: elonnginelonQuelonry[SimClustelonrsANNSimilarityelonnginelon.Quelonry],
    simClustelonrsMinScorelon: Doublelon,
    simClustelonrsVidelonoBaselondMinScorelon: Doublelon,
    twhinModelonlId: String,
    elonnablelonTwHIN: Boolelonan,
    twhinMaxTwelonelontAgelonHours: Duration,
    qigMaxTwelonelontAgelonHours: Duration,
    qigMaxNumSimilarTwelonelonts: Int,
    elonnablelonUtg: Boolelonan,
    utgQuelonry: elonnginelonQuelonry[TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon.Quelonry],
    elonnablelonUvg: Boolelonan,
    uvgQuelonry: elonnginelonQuelonry[TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon.Quelonry],
    elonnablelonQig: Boolelonan,
    qigQuelonry: elonnginelonQuelonry[TwelonelontBaselondQigSimilarityelonnginelon.Quelonry],
    params: configapi.Params)

  delonf fromParams(
    sourcelonInfo: SourcelonInfo,
    params: configapi.Params,
  ): elonnginelonQuelonry[Quelonry] = {
    // SimClustelonrs
    val elonnablelonSimClustelonrsANN =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANNParam)

    val simClustelonrsModelonlVelonrsion =
      ModelonlVelonrsions.elonnum.elonnumToSimClustelonrsModelonlVelonrsionMap(params(GlobalParams.ModelonlVelonrsionParam))
    val simClustelonrsMinScorelon = params(TwelonelontBaselondCandidatelonGelonnelonrationParams.SimClustelonrsMinScorelonParam)
    val simClustelonrsVidelonoBaselondMinScorelon = params(
      TwelonelontBaselondCandidatelonGelonnelonrationParams.SimClustelonrsVidelonoBaselondMinScorelonParam)
    val simClustelonrsANNConfigId = params(SimClustelonrsANNParams.SimClustelonrsANNConfigId)
    // SimClustelonrs - elonxpelonrimelonntal SANN Similarity elonnginelon (Videlono baselond Selon)
    val elonnablelonelonxpelonrimelonntalSimClustelonrsANN =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonelonxpelonrimelonntalSimClustelonrsANNParam)

    val elonxpelonrimelonntalSimClustelonrsANNConfigId = params(
      SimClustelonrsANNParams.elonxpelonrimelonntalSimClustelonrsANNConfigId)
    // SimClustelonrs - SANN clustelonr 1 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN1 =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN1Param)

    val simClustelonrsANN1ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN1ConfigId)
    // SimClustelonrs - SANN clustelonr 2 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN2 =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN2Param)
    val simClustelonrsANN2ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN2ConfigId)
    // SimClustelonrs - SANN clustelonr 3 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN3 =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN3Param)
    val simClustelonrsANN3ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN3ConfigId)
    // SimClustelonrs - SANN clustelonr 5 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN5 =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN5Param)
    val simClustelonrsANN5ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN5ConfigId)
    // SimClustelonrs - SANN clustelonr 4 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN4 =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonSimClustelonrsANN4Param)
    val simClustelonrsANN4ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN4ConfigId)
    // SimClustelonrs ANN Quelonrielons for diffelonrelonnt SANN clustelonrs
    val simClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANNConfigId,
      params
    )
    val elonxpelonrimelonntalSimClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      elonxpelonrimelonntalSimClustelonrsANNConfigId,
      params
    )
    val simClustelonrsANN1Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN1ConfigId,
      params
    )
    val simClustelonrsANN2Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN2ConfigId,
      params
    )
    val simClustelonrsANN3Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN3ConfigId,
      params
    )
    val simClustelonrsANN5Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN5ConfigId,
      params
    )
    val simClustelonrsANN4Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      sourcelonInfo.intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN4ConfigId,
      params
    )
    // TwelonelontBaselondCandidatelonGelonnelonration
    val maxCandidatelonNumPelonrSourcelonKelony = params(GlobalParams.MaxCandidatelonNumPelonrSourcelonKelonyParam)
    // TwHIN
    val twhinModelonlId = params(TwelonelontBaselondTwHINParams.ModelonlIdParam)
    val elonnablelonTwHIN =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonTwHINParam)

    val twhinMaxTwelonelontAgelonHours = params(GlobalParams.MaxTwelonelontAgelonHoursParam)

    // QIG
    val elonnablelonQig =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonQigSimilarTwelonelontsParam)
    val qigMaxTwelonelontAgelonHours = params(GlobalParams.MaxTwelonelontAgelonHoursParam)
    val qigMaxNumSimilarTwelonelonts = params(
      TwelonelontBaselondCandidatelonGelonnelonrationParams.QigMaxNumSimilarTwelonelontsParam)

    // UTG
    val elonnablelonUtg =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonUTGParam)
    // UVG
    val elonnablelonUvg =
      params(TwelonelontBaselondCandidatelonGelonnelonrationParams.elonnablelonUVGParam)
    elonnginelonQuelonry(
      Quelonry(
        sourcelonInfo = sourcelonInfo,
        maxCandidatelonNumPelonrSourcelonKelony = maxCandidatelonNumPelonrSourcelonKelony,
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
        simClustelonrsVidelonoBaselondMinScorelon = simClustelonrsVidelonoBaselondMinScorelon,
        twhinModelonlId = twhinModelonlId,
        elonnablelonTwHIN = elonnablelonTwHIN,
        twhinMaxTwelonelontAgelonHours = twhinMaxTwelonelontAgelonHours,
        qigMaxTwelonelontAgelonHours = qigMaxTwelonelontAgelonHours,
        qigMaxNumSimilarTwelonelonts = qigMaxNumSimilarTwelonelonts,
        elonnablelonUtg = elonnablelonUtg,
        utgQuelonry = TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon
          .fromParams(sourcelonInfo.intelonrnalId, params),
        elonnablelonQig = elonnablelonQig,
        qigQuelonry = TwelonelontBaselondQigSimilarityelonnginelon.fromParams(sourcelonInfo.intelonrnalId, params),
        elonnablelonUvg = elonnablelonUvg,
        uvgQuelonry =
          TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon.fromParams(sourcelonInfo.intelonrnalId, params),
        params = params
      ),
      params
    )
  }

  delonf fromParamsForRelonlatelondTwelonelont(
    intelonrnalId: IntelonrnalId,
    params: configapi.Params,
  ): elonnginelonQuelonry[Quelonry] = {
    // SimClustelonrs
    val elonnablelonSimClustelonrsANN = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANNParam)
    val simClustelonrsModelonlVelonrsion =
      ModelonlVelonrsions.elonnum.elonnumToSimClustelonrsModelonlVelonrsionMap(params(GlobalParams.ModelonlVelonrsionParam))
    val simClustelonrsMinScorelon = params(RelonlatelondTwelonelontTwelonelontBaselondParams.SimClustelonrsMinScorelonParam)
    val simClustelonrsANNConfigId = params(SimClustelonrsANNParams.SimClustelonrsANNConfigId)
    val elonnablelonelonxpelonrimelonntalSimClustelonrsANN =
      params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonelonxpelonrimelonntalSimClustelonrsANNParam)
    val elonxpelonrimelonntalSimClustelonrsANNConfigId = params(
      SimClustelonrsANNParams.elonxpelonrimelonntalSimClustelonrsANNConfigId)
    // SimClustelonrs - SANN clustelonr 1 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN1 = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN1Param)
    val simClustelonrsANN1ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN1ConfigId)
    // SimClustelonrs - SANN clustelonr 2 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN2 = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN2Param)
    val simClustelonrsANN2ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN2ConfigId)
    // SimClustelonrs - SANN clustelonr 3 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN3 = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN3Param)
    val simClustelonrsANN3ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN3ConfigId)
    // SimClustelonrs - SANN clustelonr 5 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN5 = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN5Param)
    val simClustelonrsANN5ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN5ConfigId)
    // SimClustelonrs - SANN clustelonr 4 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN4 = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN4Param)
    val simClustelonrsANN4ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN4ConfigId)
    // SimClustelonrs ANN Quelonrielons for diffelonrelonnt SANN clustelonrs
    val simClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANNConfigId,
      params
    )
    val elonxpelonrimelonntalSimClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      elonxpelonrimelonntalSimClustelonrsANNConfigId,
      params
    )
    val simClustelonrsANN1Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN1ConfigId,
      params
    )
    val simClustelonrsANN2Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN2ConfigId,
      params
    )
    val simClustelonrsANN3Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN3ConfigId,
      params
    )
    val simClustelonrsANN5Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN5ConfigId,
      params
    )
    val simClustelonrsANN4Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN4ConfigId,
      params
    )
    // TwelonelontBaselondCandidatelonGelonnelonration
    val maxCandidatelonNumPelonrSourcelonKelony = params(GlobalParams.MaxCandidatelonNumPelonrSourcelonKelonyParam)
    // TwHIN
    val twhinModelonlId = params(TwelonelontBaselondTwHINParams.ModelonlIdParam)
    val elonnablelonTwHIN = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonTwHINParam)
    val twhinMaxTwelonelontAgelonHours = params(GlobalParams.MaxTwelonelontAgelonHoursParam)
    // QIG
    val elonnablelonQig = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonQigSimilarTwelonelontsParam)
    val qigMaxTwelonelontAgelonHours = params(GlobalParams.MaxTwelonelontAgelonHoursParam)
    val qigMaxNumSimilarTwelonelonts = params(
      TwelonelontBaselondCandidatelonGelonnelonrationParams.QigMaxNumSimilarTwelonelontsParam)
    // UTG
    val elonnablelonUtg = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonUTGParam)
    // UVG
    val elonnablelonUvg = params(RelonlatelondTwelonelontTwelonelontBaselondParams.elonnablelonUVGParam)
    // SourcelonTypelon.RelonquelonstTwelonelontId is a placelonholdelonr.
    val sourcelonInfo = SourcelonInfo(SourcelonTypelon.RelonquelonstTwelonelontId, intelonrnalId, Nonelon)

    elonnginelonQuelonry(
      Quelonry(
        sourcelonInfo = sourcelonInfo,
        maxCandidatelonNumPelonrSourcelonKelony = maxCandidatelonNumPelonrSourcelonKelony,
        elonnablelonSimClustelonrsANN = elonnablelonSimClustelonrsANN,
        simClustelonrsMinScorelon = simClustelonrsMinScorelon,
        simClustelonrsVidelonoBaselondMinScorelon = simClustelonrsMinScorelon,
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
        twhinModelonlId = twhinModelonlId,
        elonnablelonTwHIN = elonnablelonTwHIN,
        twhinMaxTwelonelontAgelonHours = twhinMaxTwelonelontAgelonHours,
        qigMaxTwelonelontAgelonHours = qigMaxTwelonelontAgelonHours,
        qigMaxNumSimilarTwelonelonts = qigMaxNumSimilarTwelonelonts,
        elonnablelonUtg = elonnablelonUtg,
        utgQuelonry = TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon
          .fromParams(sourcelonInfo.intelonrnalId, params),
        elonnablelonQig = elonnablelonQig,
        qigQuelonry = TwelonelontBaselondQigSimilarityelonnginelon.fromParams(sourcelonInfo.intelonrnalId, params),
        elonnablelonUvg = elonnablelonUvg,
        uvgQuelonry =
          TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon.fromParams(sourcelonInfo.intelonrnalId, params),
        params = params,
      ),
      params
    )
  }
  delonf fromParamsForRelonlatelondVidelonoTwelonelont(
    intelonrnalId: IntelonrnalId,
    params: configapi.Params,
  ): elonnginelonQuelonry[Quelonry] = {
    // SimClustelonrs
    val elonnablelonSimClustelonrsANN = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANNParam)
    val simClustelonrsModelonlVelonrsion =
      ModelonlVelonrsions.elonnum.elonnumToSimClustelonrsModelonlVelonrsionMap(params(GlobalParams.ModelonlVelonrsionParam))
    val simClustelonrsMinScorelon = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.SimClustelonrsMinScorelonParam)
    val simClustelonrsANNConfigId = params(SimClustelonrsANNParams.SimClustelonrsANNConfigId)
    val elonnablelonelonxpelonrimelonntalSimClustelonrsANN = params(
      RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonelonxpelonrimelonntalSimClustelonrsANNParam)
    val elonxpelonrimelonntalSimClustelonrsANNConfigId = params(
      SimClustelonrsANNParams.elonxpelonrimelonntalSimClustelonrsANNConfigId)
    // SimClustelonrs - SANN clustelonr 1 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN1 = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN1Param)
    val simClustelonrsANN1ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN1ConfigId)
    // SimClustelonrs - SANN clustelonr 2 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN2 = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN2Param)
    val simClustelonrsANN2ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN2ConfigId)
    // SimClustelonrs - SANN clustelonr 3 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN3 = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN3Param)
    val simClustelonrsANN3ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN3ConfigId)
    // SimClustelonrs - SANN clustelonr 5 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN5 = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN5Param)
    val simClustelonrsANN5ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN5ConfigId)

    // SimClustelonrs - SANN clustelonr 4 Similarity elonnginelon
    val elonnablelonSimClustelonrsANN4 = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonSimClustelonrsANN4Param)
    val simClustelonrsANN4ConfigId = params(SimClustelonrsANNParams.SimClustelonrsANN4ConfigId)
    // SimClustelonrs ANN Quelonrielons for diffelonrelonnt SANN clustelonrs
    val simClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANNConfigId,
      params
    )
    val elonxpelonrimelonntalSimClustelonrsANNQuelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      elonxpelonrimelonntalSimClustelonrsANNConfigId,
      params
    )
    val simClustelonrsANN1Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN1ConfigId,
      params
    )
    val simClustelonrsANN2Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN2ConfigId,
      params
    )
    val simClustelonrsANN3Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN3ConfigId,
      params
    )
    val simClustelonrsANN5Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN5ConfigId,
      params
    )

    val simClustelonrsANN4Quelonry = SimClustelonrsANNSimilarityelonnginelon.fromParams(
      intelonrnalId,
      elonmbelonddingTypelon.LogFavLongelonstL2elonmbelonddingTwelonelont,
      simClustelonrsModelonlVelonrsion,
      simClustelonrsANN4ConfigId,
      params
    )
    // TwelonelontBaselondCandidatelonGelonnelonration
    val maxCandidatelonNumPelonrSourcelonKelony = params(GlobalParams.MaxCandidatelonNumPelonrSourcelonKelonyParam)
    // TwHIN
    val twhinModelonlId = params(TwelonelontBaselondTwHINParams.ModelonlIdParam)
    val elonnablelonTwHIN = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonTwHINParam)
    val twhinMaxTwelonelontAgelonHours = params(GlobalParams.MaxTwelonelontAgelonHoursParam)
    // QIG
    val elonnablelonQig = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonQigSimilarTwelonelontsParam)
    val qigMaxTwelonelontAgelonHours = params(GlobalParams.MaxTwelonelontAgelonHoursParam)
    val qigMaxNumSimilarTwelonelonts = params(
      TwelonelontBaselondCandidatelonGelonnelonrationParams.QigMaxNumSimilarTwelonelontsParam)
    // UTG
    val elonnablelonUtg = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonUTGParam)

    // SourcelonTypelon.RelonquelonstTwelonelontId is a placelonholdelonr.
    val sourcelonInfo = SourcelonInfo(SourcelonTypelon.RelonquelonstTwelonelontId, intelonrnalId, Nonelon)

    val elonnablelonUvg = params(RelonlatelondVidelonoTwelonelontTwelonelontBaselondParams.elonnablelonUVGParam)
    elonnginelonQuelonry(
      Quelonry(
        sourcelonInfo = sourcelonInfo,
        maxCandidatelonNumPelonrSourcelonKelony = maxCandidatelonNumPelonrSourcelonKelony,
        elonnablelonSimClustelonrsANN = elonnablelonSimClustelonrsANN,
        simClustelonrsMinScorelon = simClustelonrsMinScorelon,
        simClustelonrsVidelonoBaselondMinScorelon = simClustelonrsMinScorelon,
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
        twhinModelonlId = twhinModelonlId,
        elonnablelonTwHIN = elonnablelonTwHIN,
        twhinMaxTwelonelontAgelonHours = twhinMaxTwelonelontAgelonHours,
        qigMaxTwelonelontAgelonHours = qigMaxTwelonelontAgelonHours,
        qigMaxNumSimilarTwelonelonts = qigMaxNumSimilarTwelonelonts,
        elonnablelonUtg = elonnablelonUtg,
        utgQuelonry = TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelon
          .fromParams(sourcelonInfo.intelonrnalId, params),
        elonnablelonUvg = elonnablelonUvg,
        uvgQuelonry =
          TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelon.fromParams(sourcelonInfo.intelonrnalId, params),
        elonnablelonQig = elonnablelonQig,
        qigQuelonry = TwelonelontBaselondQigSimilarityelonnginelon.fromParams(sourcelonInfo.intelonrnalId, params),
        params = params
      ),
      params
    )
  }
}
