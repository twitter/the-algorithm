package com.tw ter.fr gate.pushserv ce.scr ber

 mport com.tw ter.b ject on.Base64Str ng
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.core_workflows.user_model.thr ftscala.{UserState => Thr ftUserState}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.base.Cand dateResult
 mport com.tw ter.fr gate.common.base. nval d
 mport com.tw ter.fr gate.common.base.OK
 mport com.tw ter.fr gate.common.base.Result
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.data_p pel ne.features_common.PushQual yModelFeatureContext
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.scr be.thr ftscala.Cand dateF lteredOutStep
 mport com.tw ter.fr gate.scr be.thr ftscala.Cand dateRequest nfo
 mport com.tw ter.fr gate.scr be.thr ftscala.MrRequestScr be
 mport com.tw ter.fr gate.scr be.thr ftscala.TargetUser nfo
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.fr gate.thr ftscala.T etNot f cat on
 mport com.tw ter.fr gate.thr ftscala.{Soc alContextAct on => TSoc alContextAct on}
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureType
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.ap .ut l.ScalaToJavaDataRecordConvers ons
 mport com.tw ter.nrel. avyranker.PushPred ct on lper
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport java.ut l.UU D
 mport scala.collect on.mutable

class MrRequestScr beHandler(mrRequestScr berNode: Str ng, stats: StatsRece ver) {

  pr vate val mrRequestScr beLogger = Logger(mrRequestScr berNode)

  pr vate val mrRequestScr beTargetF lter ngStats =
    stats.counter("MrRequestScr beHandler_target_f lter ng")
  pr vate val mrRequestScr beCand dateF lter ngStats =
    stats.counter("MrRequestScr beHandler_cand date_f lter ng")
  pr vate val mrRequestScr be nval dStats =
    stats.counter("MrRequestScr beHandler_ nval d_f lter ng")
  pr vate val mrRequestScr beUnsupportedFeatureTypeStats =
    stats.counter("MrRequestScr beHandler_unsupported_feature_type")
  pr vate val mrRequestScr beNot ncludedFeatureStats =
    stats.counter("MrRequestScr beHandler_not_ ncluded_features")

  pr vate f nal val MrRequestScr be nject on:  nject on[MrRequestScr be, Str ng] = B naryScalaCodec(
    MrRequestScr be
  ) andT n  nject on.connect[Array[Byte], Base64Str ng, Str ng]

  /**
   *
   * @param target : Target user  d
   * @param result : Result for target f lter ng
   *
   * @return
   */
  def scr beForTargetF lter ng(target: Target, result: Result): Future[Opt on[MrRequestScr be]] = {
     f (target. sLoggedOutUser || !enableTargetF lter ngScr b ng(target)) {
      Future.None
    } else {
      val pred cate = result match {
        case  nval d(reason) => reason
        case _ =>
          mrRequestScr be nval dStats. ncr()
          throw new  llegalStateExcept on(" nval d reason for Target F lter ng " + result)
      }
      bu ldScr beThr ft(target, pred cate, None).map { targetF lteredScr be =>
        wr eAtTargetF lter ngStep(target, targetF lteredScr be)
        So (targetF lteredScr be)
      }
    }
  }

  /**
   *
   * @param target                       : Target user  d
   * @param hydratedCand dates           : Cand dates hydrated w h deta ls:  mpress on d, fr gateNot f cat on and s ce
   * @param preRank ngF lteredCand dates : Cand dates result f ltered out at preRank ng f lter ng step
   * @param rankedCand dates             : Sorted cand dates deta ls ranked by rank ng step
   * @param rerankedCand dates           : Sorted cand dates deta ls ranked by rerank ng step
   * @param restr ctF lteredCand dates   : Cand dates deta ls f ltered out at restr ct step
   * @param allTakeCand dateResults      : Cand dates results at take step,  nclude t  cand dates   take and t  cand dates f ltered out at take step [w h d fferent result]
   *
   * @return
   */
  def scr beForCand dateF lter ng(
    target: Target,
    hydratedCand dates: Seq[Cand dateDeta ls[PushCand date]],
    preRank ngF lteredCand dates: Seq[Cand dateResult[PushCand date, Result]],
    rankedCand dates: Seq[Cand dateDeta ls[PushCand date]],
    rerankedCand dates: Seq[Cand dateDeta ls[PushCand date]],
    restr ctF lteredCand dates: Seq[Cand dateDeta ls[PushCand date]],
    allTakeCand dateResults: Seq[Cand dateResult[PushCand date, Result]]
  ): Future[Seq[MrRequestScr be]] = {
     f (target. sLoggedOutUser || target. sEma lUser) {
      Future.N l
    } else  f (enableCand dateF lter ngScr b ng(target)) {
      val hydrateFeature =
        target.params(PushFeatureSw chParams.EnableMrRequestScr b ngW hFeatureHydrat ng) ||
          target.scr beFeatureForRequestScr be

      val cand dateRequest nfoSeq = generateCand datesScr be nfo(
        hydratedCand dates,
        preRank ngF lteredCand dates,
        rankedCand dates,
        rerankedCand dates,
        restr ctF lteredCand dates,
        allTakeCand dateResults,
         sFeatureHydrat ngEnabled = hydrateFeature
      )
      val flattenStructure =
        target.params(PushFeatureSw chParams.EnableFlattenMrRequestScr b ng) || hydrateFeature
      cand dateRequest nfoSeq.flatMap { cand dateRequest nfos =>
         f (flattenStructure) {
          Future.collect {
            cand dateRequest nfos.map { cand dateRequest nfo =>
              bu ldScr beThr ft(target, None, So (Seq(cand dateRequest nfo)))
                .map { mrRequestScr be =>
                  wr eAtCand dateF lter ngStep(target, mrRequestScr be)
                  mrRequestScr be
                }
            }
          }
        } else {
          bu ldScr beThr ft(target, None, So (cand dateRequest nfos))
            .map { mrRequestScr be =>
              wr eAtCand dateF lter ngStep(target, mrRequestScr be)
              Seq(mrRequestScr be)
            }
        }
      }
    } else Future.N l

  }

  pr vate def bu ldScr beThr ft(
    target: Target,
    targetF lteredOutPred cate: Opt on[Str ng],
    cand datesRequest nfo: Opt on[Seq[Cand dateRequest nfo]]
  ): Future[MrRequestScr be] = {
    Future
      .jo n(
        target.targetUserState,
        generateTargetFeatureScr be nfo(target),
        target.targetUser).map {
        case (userStateOpt on, targetFeatureOpt on, g zmoduckUserOpt) =>
          val userState = userStateOpt on.map(userState => Thr ftUserState(userState. d))
          val targetFeatures =
            targetFeatureOpt on.map(ScalaToJavaDataRecordConvers ons.javaDataRecord2ScalaDataRecord)
          val trace d = Trace. d.trace d.toLong

          MrRequestScr be(
            request d = UU D.randomUU D.toStr ng.replaceAll("-", ""),
            scr bedT  Ms = T  .now. nM ll seconds,
            targetUser d = target.target d,
            targetUser nfo = So (
              TargetUser nfo(
                userState,
                features = targetFeatures,
                userType = g zmoduckUserOpt.map(_.userType))
            ),
            targetF lteredOutPred cate = targetF lteredOutPred cate,
            cand dates = cand datesRequest nfo,
            trace d = So (trace d)
          )
      }
  }

  pr vate def generateTargetFeatureScr be nfo(
    target: Target
  ): Future[Opt on[DataRecord]] = {
    val featureL st =
      target.params(PushFeatureSw chParams.TargetLevelFeatureL stForMrRequestScr b ng)
     f (featureL st.nonEmpty) {
      PushPred ct on lper
        .getDataRecordFromTargetFeatureMap(
          target.target d,
          target.featureMap,
          stats
        ).map { dataRecord =>
          val r chRecord =
            new SR chDataRecord(dataRecord, PushQual yModelFeatureContext.featureContext)

          val selectedRecord =
            SR chDataRecord(new DataRecord(), PushQual yModelFeatureContext.featureContext)
          featureL st.map { featureNa  =>
            val feature: Feature[_] = {
              try {
                PushQual yModelFeatureContext.featureContext.getFeature(featureNa )
              } catch {
                case _: Except on =>
                  mrRequestScr beNot ncludedFeatureStats. ncr()
                  throw new  llegalStateExcept on(
                    "Scr b ng features not  ncluded  n FeatureContext: " + featureNa )
              }
            }

            r chRecord.getFeatureValueOpt(feature).foreach { featureVal =>
              feature.getFeatureType() match {
                case FeatureType.B NARY =>
                  selectedRecord.setFeatureValue(
                    feature.as nstanceOf[Feature[Boolean]],
                    featureVal.as nstanceOf[Boolean])
                case FeatureType.CONT NUOUS =>
                  selectedRecord.setFeatureValue(
                    feature.as nstanceOf[Feature[Double]],
                    featureVal.as nstanceOf[Double])
                case FeatureType.STR NG =>
                  selectedRecord.setFeatureValue(
                    feature.as nstanceOf[Feature[Str ng]],
                    featureVal.as nstanceOf[Str ng])
                case FeatureType.D SCRETE =>
                  selectedRecord.setFeatureValue(
                    feature.as nstanceOf[Feature[Long]],
                    featureVal.as nstanceOf[Long])
                case _ =>
                  mrRequestScr beUnsupportedFeatureTypeStats. ncr()
              }
            }
          }
          So (selectedRecord.getRecord)
        }
    } else Future.None
  }

  pr vate def generateCand datesScr be nfo(
    hydratedCand dates: Seq[Cand dateDeta ls[PushCand date]],
    preRank ngF lteredCand dates: Seq[Cand dateResult[PushCand date, Result]],
    rankedCand dates: Seq[Cand dateDeta ls[PushCand date]],
    rerankedCand dates: Seq[Cand dateDeta ls[PushCand date]],
    restr ctF lteredCand dates: Seq[Cand dateDeta ls[PushCand date]],
    allTakeCand dateResults: Seq[Cand dateResult[PushCand date, Result]],
     sFeatureHydrat ngEnabled: Boolean
  ): Future[Seq[Cand dateRequest nfo]] = {
    val cand datesMap = new mutable.HashMap[Str ng, Cand dateRequest nfo]

    hydratedCand dates.foreach { hydratedCand date =>
      val frgNot f = hydratedCand date.cand date.fr gateNot f cat on
      val s mpl f edT etNot f cat onOpt = frgNot f.t etNot f cat on.map { t etNot f cat on =>
        T etNot f cat on(
          t etNot f cat on.t et d,
          Seq.empty[TSoc alContextAct on],
          t etNot f cat on.t etAuthor d)
      }
      val s mpl f edFr gateNot f cat on = Fr gateNot f cat on(
        frgNot f.commonRecom ndat onType,
        frgNot f.not f cat onD splayLocat on,
        t etNot f cat on = s mpl f edT etNot f cat onOpt
      )
      cand datesMap(hydratedCand date.cand date. mpress on d) = Cand dateRequest nfo(
        cand date d = "",
        cand dateS ce = hydratedCand date.s ce.substr ng(
          0,
          Math.m n(6, hydratedCand date.s ce.length)
        ),
        fr gateNot f cat on = So (s mpl f edFr gateNot f cat on),
        modelScore = None,
        rankPos  on = None,
        rerankPos  on = None,
        features = None,
         sSent = So (false)
      )
    }

    preRank ngF lteredCand dates.foreach { preRank ngF lteredCand dateResult =>
      cand datesMap(preRank ngF lteredCand dateResult.cand date. mpress on d) =
        cand datesMap(preRank ngF lteredCand dateResult.cand date. mpress on d)
          .copy(
            cand dateF lteredOutPred cate = preRank ngF lteredCand dateResult.result match {
              case  nval d(reason) => reason
              case _ => {
                mrRequestScr be nval dStats. ncr()
                throw new  llegalStateExcept on(
                  " nval d reason for Cand date F lter ng " + preRank ngF lteredCand dateResult.result)
              }
            },
            cand dateF lteredOutStep = So (Cand dateF lteredOutStep.PreRankF lter ng)
          )
    }

    for {
      _ <- Future.collectToTry {
        rankedCand dates.z pW h ndex.map {
          case (rankedCand dateDeta l,  ndex) =>
            val modelScoresFut = {
              val crt = rankedCand dateDeta l.cand date.commonRecType
               f (RecTypes.notEl g bleForModelScoreTrack ng.conta ns(crt)) Future.None
              else rankedCand dateDeta l.cand date.modelScores.map(So (_))
            }

            modelScoresFut.map { modelScores =>
              cand datesMap(rankedCand dateDeta l.cand date. mpress on d) =
                cand datesMap(rankedCand dateDeta l.cand date. mpress on d).copy(
                  rankPos  on = So ( ndex),
                  modelScore = modelScores
                )
            }
        }
      }

      _ = rerankedCand dates.z pW h ndex.foreach {
        case (rerankedCand dateDeta l,  ndex) => {
          cand datesMap(rerankedCand dateDeta l.cand date. mpress on d) =
            cand datesMap(rerankedCand dateDeta l.cand date. mpress on d).copy(
              rerankPos  on = So ( ndex)
            )
        }
      }

      _ <- Future.collectToTry {
        rerankedCand dates.map { rerankedCand dateDeta l =>
           f ( sFeatureHydrat ngEnabled) {
            PushPred ct on lper
              .getDataRecord(
                rerankedCand dateDeta l.cand date.target.targetHydrat onContext,
                rerankedCand dateDeta l.cand date.target.featureMap,
                rerankedCand dateDeta l.cand date.cand dateHydrat onContext,
                rerankedCand dateDeta l.cand date.cand dateFeatureMap(),
                stats
              ).map { features =>
                cand datesMap(rerankedCand dateDeta l.cand date. mpress on d) =
                  cand datesMap(rerankedCand dateDeta l.cand date. mpress on d).copy(
                    features = So (
                      ScalaToJavaDataRecordConvers ons.javaDataRecord2ScalaDataRecord(features))
                  )
              }
          } else Future.Un 
        }
      }

      _ = restr ctF lteredCand dates.foreach { restr ctF lteredCand dateDetat l =>
        cand datesMap(restr ctF lteredCand dateDetat l.cand date. mpress on d) =
          cand datesMap(restr ctF lteredCand dateDetat l.cand date. mpress on d)
            .copy(cand dateF lteredOutStep = So (Cand dateF lteredOutStep.Restr ct))
      }

      _ = allTakeCand dateResults.foreach { allTakeCand dateResult =>
        allTakeCand dateResult.result match {
          case OK =>
            cand datesMap(allTakeCand dateResult.cand date. mpress on d) =
              cand datesMap(allTakeCand dateResult.cand date. mpress on d).copy( sSent = So (true))
          case  nval d(reason) =>
            cand datesMap(allTakeCand dateResult.cand date. mpress on d) =
              cand datesMap(allTakeCand dateResult.cand date. mpress on d).copy(
                cand dateF lteredOutPred cate = reason,
                cand dateF lteredOutStep = So (Cand dateF lteredOutStep.PostRankF lter ng))
          case _ =>
            mrRequestScr be nval dStats. ncr()
            throw new  llegalStateExcept on(
              " nval d reason for Cand date F lter ng " + allTakeCand dateResult.result)
        }
      }
    } y eld cand datesMap.values.toSeq
  }

  pr vate def enableTargetF lter ngScr b ng(target: Target): Boolean = {
    target.params(PushParams.EnableMrRequestScr b ng) && target.params(
      PushFeatureSw chParams.EnableMrRequestScr b ngForTargetF lter ng)
  }

  pr vate def enableCand dateF lter ngScr b ng(target: Target): Boolean = {
    target.params(PushParams.EnableMrRequestScr b ng) && target.params(
      PushFeatureSw chParams.EnableMrRequestScr b ngForCand dateF lter ng)
  }

  pr vate def wr eAtTargetF lter ngStep(target: Target, mrRequestScr be: MrRequestScr be) = {
    logToScr be(mrRequestScr be)
    mrRequestScr beTargetF lter ngStats. ncr()
  }

  pr vate def wr eAtCand dateF lter ngStep(target: Target, mrRequestScr be: MrRequestScr be) = {
    logToScr be(mrRequestScr be)
    mrRequestScr beCand dateF lter ngStats. ncr()
  }

  pr vate def logToScr be(mrRequestScr be: MrRequestScr be): Un  = {
    val logEntry: Str ng = MrRequestScr be nject on(mrRequestScr be)
    mrRequestScr beLogger. nfo(logEntry)
  }
}
