packagelon com.twittelonr.cr_mixelonr.candidatelon_gelonnelonration

import com.twittelonr.contelonntreloncommelonndelonr.thriftscala.TwelonelontInfo
import com.twittelonr.cr_mixelonr.modelonl.CandidatelonGelonnelonrationInfo
import com.twittelonr.cr_mixelonr.modelonl.GraphSourcelonInfo
import com.twittelonr.cr_mixelonr.modelonl.InitialCandidatelon
import com.twittelonr.cr_mixelonr.modelonl.ModelonlConfig
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.cr_mixelonr.modelonl.SimilarityelonnginelonInfo
import com.twittelonr.cr_mixelonr.modelonl.SourcelonInfo
import com.twittelonr.cr_mixelonr.modelonl.TripTwelonelontWithScorelon
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithCandidatelonGelonnelonrationInfo
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithScorelon
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithScorelonAndSocialProof
import com.twittelonr.cr_mixelonr.param.ConsumelonrBaselondWalsParams
import com.twittelonr.cr_mixelonr.param.ConsumelonrelonmbelonddingBaselondCandidatelonGelonnelonrationParams
import com.twittelonr.cr_mixelonr.param.ConsumelonrsBaselondUselonrVidelonoGraphParams
import com.twittelonr.cr_mixelonr.param.GlobalParams
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.ConsumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.ConsumelonrBaselondWalsSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.ConsumelonrelonmbelonddingBaselondTripSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.ConsumelonrelonmbelonddingBaselondTwHINSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.ConsumelonrelonmbelonddingBaselondTwoTowelonrSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.elonnginelonQuelonry
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.FiltelonrUtil
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.HnswANNelonnginelonQuelonry
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.HnswANNSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.ProducelonrBaselondUnifielondSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.StandardSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.TripelonnginelonQuelonry
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.TwelonelontBaselondUnifielondSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.similarity_elonnginelon.UselonrTwelonelontelonntityGraphSimilarityelonnginelon
import com.twittelonr.cr_mixelonr.thriftscala.SimilarityelonnginelonTypelon
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.timelonlinelons.configapi
import com.twittelonr.util.Futurelon
import javax.injelonct.Injelonct
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

/**
 * Routelon thelon SourcelonInfo to thelon associatelond Candidatelon elonnginelons.
 */
@Singlelonton
caselon class CandidatelonSourcelonsRoutelonr @Injelonct() (
  customizelondRelontrielonvalCandidatelonGelonnelonration: CustomizelondRelontrielonvalCandidatelonGelonnelonration,
  simClustelonrsIntelonrelonstelondInCandidatelonGelonnelonration: SimClustelonrsIntelonrelonstelondInCandidatelonGelonnelonration,
  @Namelond(ModulelonNamelons.TwelonelontBaselondUnifielondSimilarityelonnginelon)
  twelonelontBaselondUnifielondSimilarityelonnginelon: StandardSimilarityelonnginelon[
    TwelonelontBaselondUnifielondSimilarityelonnginelon.Quelonry,
    TwelonelontWithCandidatelonGelonnelonrationInfo
  ],
  @Namelond(ModulelonNamelons.ProducelonrBaselondUnifielondSimilarityelonnginelon)
  producelonrBaselondUnifielondSimilarityelonnginelon: StandardSimilarityelonnginelon[
    ProducelonrBaselondUnifielondSimilarityelonnginelon.Quelonry,
    TwelonelontWithCandidatelonGelonnelonrationInfo
  ],
  @Namelond(ModulelonNamelons.ConsumelonrelonmbelonddingBaselondTripSimilarityelonnginelon)
  consumelonrelonmbelonddingBaselondTripSimilarityelonnginelon: StandardSimilarityelonnginelon[
    TripelonnginelonQuelonry,
    TripTwelonelontWithScorelon
  ],
  @Namelond(ModulelonNamelons.ConsumelonrelonmbelonddingBaselondTwHINANNSimilarityelonnginelon)
  consumelonrBaselondTwHINANNSimilarityelonnginelon: HnswANNSimilarityelonnginelon,
  @Namelond(ModulelonNamelons.ConsumelonrelonmbelonddingBaselondTwoTowelonrANNSimilarityelonnginelon)
  consumelonrBaselondTwoTowelonrSimilarityelonnginelon: HnswANNSimilarityelonnginelon,
  @Namelond(ModulelonNamelons.ConsumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelon)
  consumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelon: StandardSimilarityelonnginelon[
    ConsumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelon
  ],
  @Namelond(ModulelonNamelons.UselonrTwelonelontelonntityGraphSimilarityelonnginelon) uselonrTwelonelontelonntityGraphSimilarityelonnginelon: StandardSimilarityelonnginelon[
    UselonrTwelonelontelonntityGraphSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelonAndSocialProof
  ],
  @Namelond(ModulelonNamelons.ConsumelonrBaselondWalsSimilarityelonnginelon)
  consumelonrBaselondWalsSimilarityelonnginelon: StandardSimilarityelonnginelon[
    ConsumelonrBaselondWalsSimilarityelonnginelon.Quelonry,
    TwelonelontWithScorelon
  ],
  twelonelontInfoStorelon: RelonadablelonStorelon[TwelonelontId, TwelonelontInfo],
  globalStats: StatsReloncelonivelonr,
) {

  import CandidatelonSourcelonsRoutelonr._
  val stats: StatsReloncelonivelonr = globalStats.scopelon(this.gelontClass.gelontSimplelonNamelon)

  delonf felontchCandidatelons(
    relonquelonstUselonrId: UselonrId,
    sourcelonSignals: Selont[SourcelonInfo],
    sourcelonGraphs: Map[String, Option[GraphSourcelonInfo]],
    params: configapi.Params,
  ): Futurelon[Selonq[Selonq[InitialCandidatelon]]] = {

    val twelonelontBaselondCandidatelonsFuturelon = gelontCandidatelons(
      gelontTwelonelontBaselondSourcelonInfo(sourcelonSignals),
      params,
      TwelonelontBaselondUnifielondSimilarityelonnginelon.fromParams,
      twelonelontBaselondUnifielondSimilarityelonnginelon.gelontCandidatelons)

    val producelonrBaselondCandidatelonsFuturelon =
      gelontCandidatelons(
        gelontProducelonrBaselondSourcelonInfo(sourcelonSignals),
        params,
        ProducelonrBaselondUnifielondSimilarityelonnginelon.fromParams(_, _),
        producelonrBaselondUnifielondSimilarityelonnginelon.gelontCandidatelons
      )

    val simClustelonrsIntelonrelonstelondInBaselondCandidatelonsFuturelon =
      gelontCandidatelonsPelonrSimilarityelonnginelonModelonl(
        relonquelonstUselonrId,
        params,
        SimClustelonrsIntelonrelonstelondInCandidatelonGelonnelonration.fromParams,
        simClustelonrsIntelonrelonstelondInCandidatelonGelonnelonration.gelont)

    val consumelonrelonmbelonddingBaselondLogFavBaselondTripCandidatelonsFuturelon =
      if (params(
          ConsumelonrelonmbelonddingBaselondCandidatelonGelonnelonrationParams.elonnablelonLogFavBaselondSimClustelonrsTripParam)) {
        gelontSimClustelonrsTripCandidatelons(
          params,
          ConsumelonrelonmbelonddingBaselondTripSimilarityelonnginelon.fromParams(
            ModelonlConfig.ConsumelonrLogFavBaselondIntelonrelonstelondInelonmbelondding,
            IntelonrnalId.UselonrId(relonquelonstUselonrId),
            params
          ),
          consumelonrelonmbelonddingBaselondTripSimilarityelonnginelon
        ).map {
          Selonq(_)
        }
      } elonlselon
        Futurelon.Nil

    val consumelonrsBaselondUvgRelonalGraphInCandidatelonsFuturelon =
      if (params(ConsumelonrsBaselondUselonrVidelonoGraphParams.elonnablelonSourcelonParam)) {
        val relonalGraphInGraphSourcelonInfoOpt =
          gelontGraphSourcelonInfoBySourcelonTypelon(SourcelonTypelon.RelonalGraphIn.namelon, sourcelonGraphs)

        gelontGraphBaselondCandidatelons(
          params,
          ConsumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelon
            .fromParamsForRelonalGraphIn(
              relonalGraphInGraphSourcelonInfoOpt
                .map { graphSourcelonInfo => graphSourcelonInfo.selonelondWithScorelons }.gelontOrelonlselon(Map.elonmpty),
              params),
          consumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelon,
          ConsumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelon.toSimilarityelonnginelonInfo,
          relonalGraphInGraphSourcelonInfoOpt
        ).map {
          Selonq(_)
        }
      } elonlselon Futurelon.Nil

    val consumelonrelonmbelonddingBaselondFollowBaselondTripCandidatelonsFuturelon =
      if (params(
          ConsumelonrelonmbelonddingBaselondCandidatelonGelonnelonrationParams.elonnablelonFollowBaselondSimClustelonrsTripParam)) {
        gelontSimClustelonrsTripCandidatelons(
          params,
          ConsumelonrelonmbelonddingBaselondTripSimilarityelonnginelon.fromParams(
            ModelonlConfig.ConsumelonrFollowBaselondIntelonrelonstelondInelonmbelondding,
            IntelonrnalId.UselonrId(relonquelonstUselonrId),
            params
          ),
          consumelonrelonmbelonddingBaselondTripSimilarityelonnginelon
        ).map {
          Selonq(_)
        }
      } elonlselon
        Futurelon.Nil

    val consumelonrBaselondWalsCandidatelonsFuturelon =
      if (params(
          ConsumelonrBaselondWalsParams.elonnablelonSourcelonParam
        )) {
        gelontConsumelonrBaselondWalsCandidatelons(sourcelonSignals, params)
      }.map { Selonq(_) }
      elonlselon Futurelon.Nil

    val consumelonrelonmbelonddingBaselondTwHINCandidatelonsFuturelon =
      if (params(ConsumelonrelonmbelonddingBaselondCandidatelonGelonnelonrationParams.elonnablelonTwHINParam)) {
        gelontHnswCandidatelons(
          params,
          ConsumelonrelonmbelonddingBaselondTwHINSimilarityelonnginelon.fromParams(
            IntelonrnalId.UselonrId(relonquelonstUselonrId),
            params),
          consumelonrBaselondTwHINANNSimilarityelonnginelon
        ).map { Selonq(_) }
      } elonlselon Futurelon.Nil

    val consumelonrelonmbelonddingBaselondTwoTowelonrCandidatelonsFuturelon =
      if (params(ConsumelonrelonmbelonddingBaselondCandidatelonGelonnelonrationParams.elonnablelonTwoTowelonrParam)) {
        gelontHnswCandidatelons(
          params,
          ConsumelonrelonmbelonddingBaselondTwoTowelonrSimilarityelonnginelon.fromParams(
            IntelonrnalId.UselonrId(relonquelonstUselonrId),
            params),
          consumelonrBaselondTwoTowelonrSimilarityelonnginelon
        ).map {
          Selonq(_)
        }
      } elonlselon Futurelon.Nil

    val customizelondRelontrielonvalBaselondCandidatelonsFuturelon =
      gelontCandidatelonsPelonrSimilarityelonnginelonModelonl(
        relonquelonstUselonrId,
        params,
        CustomizelondRelontrielonvalCandidatelonGelonnelonration.fromParams,
        customizelondRelontrielonvalCandidatelonGelonnelonration.gelont)

    Futurelon
      .collelonct(
        Selonq(
          twelonelontBaselondCandidatelonsFuturelon,
          producelonrBaselondCandidatelonsFuturelon,
          simClustelonrsIntelonrelonstelondInBaselondCandidatelonsFuturelon,
          consumelonrBaselondWalsCandidatelonsFuturelon,
          consumelonrelonmbelonddingBaselondLogFavBaselondTripCandidatelonsFuturelon,
          consumelonrelonmbelonddingBaselondFollowBaselondTripCandidatelonsFuturelon,
          consumelonrelonmbelonddingBaselondTwHINCandidatelonsFuturelon,
          consumelonrelonmbelonddingBaselondTwoTowelonrCandidatelonsFuturelon,
          consumelonrsBaselondUvgRelonalGraphInCandidatelonsFuturelon,
          customizelondRelontrielonvalBaselondCandidatelonsFuturelon
        )).map { candidatelonsList =>
        // relonmovelon elonmpty innelonrSelonq
        val relonsult = candidatelonsList.flattelonn.filtelonr(_.nonelonmpty)
        stats.stat("numOfSelonquelonncelons").add(relonsult.sizelon)
        stats.stat("flattelonnCandidatelonsWithDup").add(relonsult.flattelonn.sizelon)

        relonsult
      }
  }

  privatelon delonf gelontGraphBaselondCandidatelons[QuelonryTypelon](
    params: configapi.Params,
    quelonry: elonnginelonQuelonry[QuelonryTypelon],
    elonnginelon: StandardSimilarityelonnginelon[QuelonryTypelon, TwelonelontWithScorelon],
    toSimilarityelonnginelonInfo: Doublelon => SimilarityelonnginelonInfo,
    graphSourcelonInfoOpt: Option[GraphSourcelonInfo] = Nonelon
  ): Futurelon[Selonq[InitialCandidatelon]] = {
    val candidatelonsOptFut = elonnginelon.gelontCandidatelons(quelonry)
    val twelonelontsWithCandidatelonGelonnelonrationInfoOptFut = candidatelonsOptFut.map {
      _.map { twelonelontsWithScorelons =>
        val sortelondCandidatelons = twelonelontsWithScorelons.sortBy(-_.scorelon)
        elonnginelon.gelontScopelondStats.stat("sortelondCandidatelons_sizelon").add(sortelondCandidatelons.sizelon)
        val twelonelontsWithCandidatelonGelonnelonrationInfo = sortelondCandidatelons.map { twelonelontWithScorelon =>
          {
            val similarityelonnginelonInfo = toSimilarityelonnginelonInfo(twelonelontWithScorelon.scorelon)
            val sourcelonInfo = graphSourcelonInfoOpt.map { graphSourcelonInfo =>
              // Thelon intelonrnalId is a placelonholdelonr valuelon. Welon do not plan to storelon thelon full selonelondUselonrId selont.
              SourcelonInfo(
                sourcelonTypelon = graphSourcelonInfo.sourcelonTypelon,
                intelonrnalId = IntelonrnalId.UselonrId(0L),
                sourcelonelonvelonntTimelon = Nonelon
              )
            }
            TwelonelontWithCandidatelonGelonnelonrationInfo(
              twelonelontWithScorelon.twelonelontId,
              CandidatelonGelonnelonrationInfo(
                sourcelonInfo,
                similarityelonnginelonInfo,
                Selonq.elonmpty // Atomic Similarity elonnginelon. Helonncelon it has no contributing Selons
              )
            )
          }
        }
        val maxCandidatelonNum = params(GlobalParams.MaxCandidatelonNumPelonrSourcelonKelonyParam)
        twelonelontsWithCandidatelonGelonnelonrationInfo.takelon(maxCandidatelonNum)
      }
    }
    for {
      twelonelontsWithCandidatelonGelonnelonrationInfoOpt <- twelonelontsWithCandidatelonGelonnelonrationInfoOptFut
      initialCandidatelons <- convelonrtToInitialCandidatelons(
        twelonelontsWithCandidatelonGelonnelonrationInfoOpt.toSelonq.flattelonn)
    } yielonld initialCandidatelons
  }

  privatelon delonf gelontCandidatelons[QuelonryTypelon](
    sourcelonSignals: Selont[SourcelonInfo],
    params: configapi.Params,
    fromParams: (SourcelonInfo, configapi.Params) => QuelonryTypelon,
    gelontFunc: QuelonryTypelon => Futurelon[Option[Selonq[TwelonelontWithCandidatelonGelonnelonrationInfo]]]
  ): Futurelon[Selonq[Selonq[InitialCandidatelon]]] = {
    val quelonrielons = sourcelonSignals.map { sourcelonInfo =>
      fromParams(sourcelonInfo, params)
    }.toSelonq

    Futurelon
      .collelonct {
        quelonrielons.map { quelonry =>
          for {
            candidatelons <- gelontFunc(quelonry)
            prelonfiltelonrCandidatelons <- convelonrtToInitialCandidatelons(candidatelons.toSelonq.flattelonn)
          } yielonld {
            prelonfiltelonrCandidatelons
          }
        }
      }
  }

  privatelon delonf gelontConsumelonrBaselondWalsCandidatelons(
    sourcelonSignals: Selont[SourcelonInfo],
    params: configapi.Params
  ): Futurelon[Selonq[InitialCandidatelon]] = {
    // Felontch sourcelon signals and filtelonr thelonm baselond on agelon.
    val signals = FiltelonrUtil.twelonelontSourcelonAgelonFiltelonr(
      gelontConsumelonrBaselondWalsSourcelonInfo(sourcelonSignals).toSelonq,
      params(ConsumelonrBaselondWalsParams.MaxTwelonelontSignalAgelonHoursParam))

    val candidatelonsOptFut = consumelonrBaselondWalsSimilarityelonnginelon.gelontCandidatelons(
      ConsumelonrBaselondWalsSimilarityelonnginelon.fromParams(signals, params)
    )
    val twelonelontsWithCandidatelonGelonnelonrationInfoOptFut = candidatelonsOptFut.map {
      _.map { twelonelontsWithScorelons =>
        val sortelondCandidatelons = twelonelontsWithScorelons.sortBy(-_.scorelon)
        val filtelonrelondCandidatelons =
          FiltelonrUtil.twelonelontAgelonFiltelonr(sortelondCandidatelons, params(GlobalParams.MaxTwelonelontAgelonHoursParam))
        consumelonrBaselondWalsSimilarityelonnginelon.gelontScopelondStats
          .stat("filtelonrelondCandidatelons_sizelon").add(filtelonrelondCandidatelons.sizelon)

        val twelonelontsWithCandidatelonGelonnelonrationInfo = filtelonrelondCandidatelons.map { twelonelontWithScorelon =>
          {
            val similarityelonnginelonInfo =
              ConsumelonrBaselondWalsSimilarityelonnginelon.toSimilarityelonnginelonInfo(twelonelontWithScorelon.scorelon)
            TwelonelontWithCandidatelonGelonnelonrationInfo(
              twelonelontWithScorelon.twelonelontId,
              CandidatelonGelonnelonrationInfo(
                Nonelon,
                similarityelonnginelonInfo,
                Selonq.elonmpty // Atomic Similarity elonnginelon. Helonncelon it has no contributing Selons
              )
            )
          }
        }
        val maxCandidatelonNum = params(GlobalParams.MaxCandidatelonNumPelonrSourcelonKelonyParam)
        twelonelontsWithCandidatelonGelonnelonrationInfo.takelon(maxCandidatelonNum)
      }
    }
    for {
      twelonelontsWithCandidatelonGelonnelonrationInfoOpt <- twelonelontsWithCandidatelonGelonnelonrationInfoOptFut
      initialCandidatelons <- convelonrtToInitialCandidatelons(
        twelonelontsWithCandidatelonGelonnelonrationInfoOpt.toSelonq.flattelonn)
    } yielonld initialCandidatelons
  }

  privatelon delonf gelontSimClustelonrsTripCandidatelons(
    params: configapi.Params,
    quelonry: TripelonnginelonQuelonry,
    elonnginelon: StandardSimilarityelonnginelon[
      TripelonnginelonQuelonry,
      TripTwelonelontWithScorelon
    ],
  ): Futurelon[Selonq[InitialCandidatelon]] = {
    val twelonelontsWithCandidatelonsGelonnelonrationInfoOptFut =
      elonnginelon.gelontCandidatelons(elonnginelonQuelonry(quelonry, params)).map {
        _.map {
          _.map { twelonelontWithScorelon =>
            // delonfinelon filtelonrs
            TwelonelontWithCandidatelonGelonnelonrationInfo(
              twelonelontWithScorelon.twelonelontId,
              CandidatelonGelonnelonrationInfo(
                Nonelon,
                SimilarityelonnginelonInfo(
                  SimilarityelonnginelonTypelon.elonxplorelonTripOfflinelonSimClustelonrsTwelonelonts,
                  Nonelon,
                  Somelon(twelonelontWithScorelon.scorelon)),
                Selonq.elonmpty
              )
            )
          }
        }
      }
    for {
      twelonelontsWithCandidatelonGelonnelonrationInfoOpt <- twelonelontsWithCandidatelonsGelonnelonrationInfoOptFut
      initialCandidatelons <- convelonrtToInitialCandidatelons(
        twelonelontsWithCandidatelonGelonnelonrationInfoOpt.toSelonq.flattelonn)
    } yielonld initialCandidatelons
  }

  privatelon delonf gelontHnswCandidatelons(
    params: configapi.Params,
    quelonry: HnswANNelonnginelonQuelonry,
    elonnginelon: HnswANNSimilarityelonnginelon,
  ): Futurelon[Selonq[InitialCandidatelon]] = {
    val candidatelonsOptFut = elonnginelon.gelontCandidatelons(quelonry)
    val twelonelontsWithCandidatelonGelonnelonrationInfoOptFut = candidatelonsOptFut.map {
      _.map { twelonelontsWithScorelons =>
        val sortelondCandidatelons = twelonelontsWithScorelons.sortBy(-_.scorelon)
        val filtelonrelondCandidatelons =
          FiltelonrUtil.twelonelontAgelonFiltelonr(sortelondCandidatelons, params(GlobalParams.MaxTwelonelontAgelonHoursParam))
        elonnginelon.gelontScopelondStats.stat("filtelonrelondCandidatelons_sizelon").add(filtelonrelondCandidatelons.sizelon)
        val twelonelontsWithCandidatelonGelonnelonrationInfo = filtelonrelondCandidatelons.map { twelonelontWithScorelon =>
          {
            val similarityelonnginelonInfo =
              elonnginelon.toSimilarityelonnginelonInfo(quelonry, twelonelontWithScorelon.scorelon)
            TwelonelontWithCandidatelonGelonnelonrationInfo(
              twelonelontWithScorelon.twelonelontId,
              CandidatelonGelonnelonrationInfo(
                Nonelon,
                similarityelonnginelonInfo,
                Selonq.elonmpty // Atomic Similarity elonnginelon. Helonncelon it has no contributing Selons
              )
            )
          }
        }
        val maxCandidatelonNum = params(GlobalParams.MaxCandidatelonNumPelonrSourcelonKelonyParam)
        twelonelontsWithCandidatelonGelonnelonrationInfo.takelon(maxCandidatelonNum)
      }
    }
    for {
      twelonelontsWithCandidatelonGelonnelonrationInfoOpt <- twelonelontsWithCandidatelonGelonnelonrationInfoOptFut
      initialCandidatelons <- convelonrtToInitialCandidatelons(
        twelonelontsWithCandidatelonGelonnelonrationInfoOpt.toSelonq.flattelonn)
    } yielonld initialCandidatelons
  }

  /**
   * Relonturns candidatelons from elonach similarity elonnginelon selonparatelonly.
   * For 1 relonquelonstUselonrId, it will felontch relonsults from elonach similarity elonnginelon elon_i,
   * and relonturns Selonq[Selonq[TwelonelontCandidatelon]].
   */
  privatelon delonf gelontCandidatelonsPelonrSimilarityelonnginelonModelonl[QuelonryTypelon](
    relonquelonstUselonrId: UselonrId,
    params: configapi.Params,
    fromParams: (IntelonrnalId, configapi.Params) => QuelonryTypelon,
    gelontFunc: QuelonryTypelon => Futurelon[
      Option[Selonq[Selonq[TwelonelontWithCandidatelonGelonnelonrationInfo]]]
    ]
  ): Futurelon[Selonq[Selonq[InitialCandidatelon]]] = {
    val quelonry = fromParams(IntelonrnalId.UselonrId(relonquelonstUselonrId), params)
    gelontFunc(quelonry).flatMap { candidatelonsPelonrSimilarityelonnginelonModelonlOpt =>
      val candidatelonsPelonrSimilarityelonnginelonModelonl = candidatelonsPelonrSimilarityelonnginelonModelonlOpt.toSelonq.flattelonn
      Futurelon.collelonct {
        candidatelonsPelonrSimilarityelonnginelonModelonl.map(convelonrtToInitialCandidatelons)
      }
    }
  }

  privatelon[candidatelon_gelonnelonration] delonf convelonrtToInitialCandidatelons(
    candidatelons: Selonq[TwelonelontWithCandidatelonGelonnelonrationInfo],
  ): Futurelon[Selonq[InitialCandidatelon]] = {
    val twelonelontIds = candidatelons.map(_.twelonelontId).toSelont
    Futurelon.collelonct(twelonelontInfoStorelon.multiGelont(twelonelontIds)).map { twelonelontInfos =>
      /***
       * If twelonelontInfo doelons not elonxist, welon will filtelonr out this twelonelont candidatelon.
       */
      candidatelons.collelonct {
        caselon candidatelon if twelonelontInfos.gelontOrelonlselon(candidatelon.twelonelontId, Nonelon).isDelonfinelond =>
          val twelonelontInfo = twelonelontInfos(candidatelon.twelonelontId)
            .gelontOrelonlselon(throw nelonw IllelongalStatelonelonxcelonption("Chelonck prelonvious linelon's condition"))

          InitialCandidatelon(
            twelonelontId = candidatelon.twelonelontId,
            twelonelontInfo = twelonelontInfo,
            candidatelon.candidatelonGelonnelonrationInfo
          )
      }
    }
  }
}

objelonct CandidatelonSourcelonsRoutelonr {
  delonf gelontGraphSourcelonInfoBySourcelonTypelon(
    sourcelonTypelonStr: String,
    sourcelonGraphs: Map[String, Option[GraphSourcelonInfo]]
  ): Option[GraphSourcelonInfo] = {
    sourcelonGraphs.gelontOrelonlselon(sourcelonTypelonStr, Nonelon)
  }

  delonf gelontTwelonelontBaselondSourcelonInfo(
    sourcelonSignals: Selont[SourcelonInfo]
  ): Selont[SourcelonInfo] = {
    sourcelonSignals.collelonct {
      caselon sourcelonInfo
          if AllowelondSourcelonTypelonsForTwelonelontBaselondUnifielondSelon.contains(sourcelonInfo.sourcelonTypelon.valuelon) =>
        sourcelonInfo
    }
  }

  delonf gelontProducelonrBaselondSourcelonInfo(
    sourcelonSignals: Selont[SourcelonInfo]
  ): Selont[SourcelonInfo] = {
    sourcelonSignals.collelonct {
      caselon sourcelonInfo
          if AllowelondSourcelonTypelonsForProducelonrBaselondUnifielondSelon.contains(sourcelonInfo.sourcelonTypelon.valuelon) =>
        sourcelonInfo
    }
  }

  delonf gelontConsumelonrBaselondWalsSourcelonInfo(
    sourcelonSignals: Selont[SourcelonInfo]
  ): Selont[SourcelonInfo] = {
    sourcelonSignals.collelonct {
      caselon sourcelonInfo
          if AllowelondSourcelonTypelonsForConsumelonrBaselondWalsSelon.contains(sourcelonInfo.sourcelonTypelon.valuelon) =>
        sourcelonInfo
    }
  }

  /***
   * Signal funnelonling should not elonxist in CG or elonvelonn in any Similarityelonnginelon.
   * Thelony will belon in Routelonr, or elonvelonntually, in CrCandidatelonGelonnelonrator.
   */
  val AllowelondSourcelonTypelonsForConsumelonrBaselondWalsSelon = Selont(
    SourcelonTypelon.TwelonelontFavoritelon.valuelon,
    SourcelonTypelon.Relontwelonelont.valuelon,
    SourcelonTypelon.TwelonelontDontLikelon.valuelon, //currelonntly no-op
    SourcelonTypelon.TwelonelontRelonport.valuelon, //currelonntly no-op
    SourcelonTypelon.AccountMutelon.valuelon, //currelonntly no-op
    SourcelonTypelon.AccountBlock.valuelon //currelonntly no-op
  )
  val AllowelondSourcelonTypelonsForTwelonelontBaselondUnifielondSelon = Selont(
    SourcelonTypelon.TwelonelontFavoritelon.valuelon,
    SourcelonTypelon.Relontwelonelont.valuelon,
    SourcelonTypelon.OriginalTwelonelont.valuelon,
    SourcelonTypelon.Relonply.valuelon,
    SourcelonTypelon.TwelonelontSharelon.valuelon,
    SourcelonTypelon.NotificationClick.valuelon,
    SourcelonTypelon.GoodTwelonelontClick.valuelon,
    SourcelonTypelon.VidelonoTwelonelontQualityVielonw.valuelon,
    SourcelonTypelon.VidelonoTwelonelontPlayback50.valuelon,
    SourcelonTypelon.TwelonelontAggrelongation.valuelon,
  )
  val AllowelondSourcelonTypelonsForProducelonrBaselondUnifielondSelon = Selont(
    SourcelonTypelon.UselonrFollow.valuelon,
    SourcelonTypelon.UselonrRelonpelonatelondProfilelonVisit.valuelon,
    SourcelonTypelon.RelonalGraphOon.valuelon,
    SourcelonTypelon.FollowReloncommelonndation.valuelon,
    SourcelonTypelon.UselonrTrafficAttributionProfilelonVisit.valuelon,
    SourcelonTypelon.GoodProfilelonClick.valuelon,
    SourcelonTypelon.ProducelonrAggrelongation.valuelon,
  )
}
