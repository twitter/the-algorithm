package com.tw ter.v s b l y. nterfaces.conversat ons

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.Label
 mport com.tw ter.servo.repos ory.KeyValueResult
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabel
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelValue
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Stopwatch
 mport com.tw ter.ut l.Try
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.t ets.T et dFeatures
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.bu lder.Verd ctLogger
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.t ets.FosnrPefetc dLabelsRelat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.features.AuthorUserLabels
 mport com.tw ter.v s b l y.features.Conversat onRootAuthor sVer f ed
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.features.Has nnerC rcleOfFr endsRelat onsh p
 mport com.tw ter.v s b l y.features.T etConversat on d
 mport com.tw ter.v s b l y.features.T etParent d
 mport com.tw ter.v s b l y.logg ng.thr ftscala.VFL bType
 mport com.tw ter.v s b l y.models.Content d.T et d
 mport com.tw ter.v s b l y.models.SafetyLevel.T  l neConversat onsDownrank ng
 mport com.tw ter.v s b l y.models.SafetyLevel.T  l neConversat onsDownrank ngM n mal
 mport com.tw ter.v s b l y.models.SafetyLevel.toThr ft
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.T etSafetyLabel
 mport com.tw ter.v s b l y.models.Un OfD vers on

object T  l neConversat onsV s b l yL brary {
  type Type =
    T  l neConversat onsV s b l yRequest => St ch[T  l neConversat onsV s b l yResponse]

  def apply(
    v s b l yL brary: V s b l yL brary,
    batchSafetyLabelRepos ory: BatchSafetyLabelRepos ory,
    dec der: Dec der,
    userRelat onsh pS ce: UserRelat onsh pS ce = UserRelat onsh pS ce.empty,
    userS ce: UserS ce = UserS ce.empty
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val t et dFeatures = new T et dFeatures(
      statsRece ver = l braryStatsRece ver,
      enableSt chProf l ng = Gate.False
    )
    val t et dFeaturesM n mal = new T et dFeatures(
      statsRece ver = l braryStatsRece ver,
      enableSt chProf l ng = Gate.False
    )
    val vfLatencyOverallStat = l braryStatsRece ver.stat("vf_latency_overall")
    val vfLatencySt chBu ldStat = l braryStatsRece ver.stat("vf_latency_st ch_bu ld")
    val vfLatencySt chRunStat = l braryStatsRece ver.stat("vf_latency_st ch_run")

    val v s b l yDec derGates = V s b l yDec derGates(dec der)
    val verd ctLogger =
      createVerd ctLogger(
        v s b l yDec derGates.enableVerd ctLoggerTCVL,
        dec der,
        l braryStatsRece ver)

    request: T  l neConversat onsV s b l yRequest =>
      val elapsed = Stopwatch.start()
      var runSt chStartMs = 0L

      val future = request.prefetc dSafetyLabels match {
        case So (labels) => Future.value(labels)
        case _ =>
          batchSafetyLabelRepos ory((request.conversat on d, request.t et ds))
      }

      val fosnrPefetc dLabelsRelat onsh pFeatures =
        new FosnrPefetc dLabelsRelat onsh pFeatures(
          userRelat onsh pS ce = userRelat onsh pS ce,
          statsRece ver = l braryStatsRece ver)

      val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)

      St ch.callFuture(future).flatMap {
        kvr: KeyValueResult[Long, scala.collect on.Map[SafetyLabelType, SafetyLabel]] =>
          val featureMapProv der: (Content d, SafetyLevel) => FeatureMap = {
            case (T et d(t et d), safetyLevel) =>
              val constantT etSafetyLabels: Seq[T etSafetyLabel] =
                kvr.found.getOrElse(t et d, Map.empty).toSeq.map {
                  case (safetyLabelType, safetyLabel) =>
                    T etSafetyLabel.fromThr ft(SafetyLabelValue(safetyLabelType, safetyLabel))
                }

              val replyAuthor = request.t etAuthors.flatMap {
                _(t et d) match {
                  case Return(So (user d)) => So (user d)
                  case _ => None
                }
              }

              val fosnrPefetc dLabelsRelat onsh pFeatureConf = replyAuthor match {
                case So (author d)  f v s b l yL brary. sReleaseCand dateEnabled =>
                  fosnrPefetc dLabelsRelat onsh pFeatures
                    .forT etW hSafetyLabelsAndAuthor d(
                      safetyLabels = constantT etSafetyLabels,
                      author d = author d,
                      v e r d = request.v e rContext.user d)
                case _ => fosnrPefetc dLabelsRelat onsh pFeatures.forNonFosnr()
              }

              val authorFeatureConf = replyAuthor match {
                case So (author d)  f v s b l yL brary. sReleaseCand dateEnabled =>
                  authorFeatures.forAuthor d(author d)
                case _ => authorFeatures.forNoAuthor()
              }

              val baseBu lderArgu nts = (safetyLevel match {
                case T  l neConversat onsDownrank ng =>
                  Seq(t et dFeatures.forT et d(t et d, constantT etSafetyLabels))
                case T  l neConversat onsDownrank ngM n mal =>
                  Seq(t et dFeaturesM n mal.forT et d(t et d, constantT etSafetyLabels))
                case _ => N l
              }) :+ fosnrPefetc dLabelsRelat onsh pFeatureConf :+ authorFeatureConf

              val t etAuthorUserLabels: Opt on[Seq[Label]] =
                request.prefetc dT etAuthorUserLabels.flatMap {
                  _.apply(t et d) match {
                    case Return(So (labelMap)) =>
                      So (labelMap.values.toSeq)
                    case _ =>
                      None
                  }
                }

              val has nnerC rcleOfFr endsRelat onsh p: Boolean =
                request. nnerC rcleOfFr endsRelat onsh ps match {
                  case So (keyValueResult) =>
                    keyValueResult(t et d) match {
                      case Return(So (true)) => true
                      case _ => false
                    }
                  case None => false
                }

              val bu lderArgu nts: Seq[FeatureMapBu lder => FeatureMapBu lder] =
                t etAuthorUserLabels match {
                  case So (labels) =>
                    baseBu lderArgu nts :+ { (fmb: FeatureMapBu lder) =>
                      fmb.w hConstantFeature(AuthorUserLabels, labels)
                    }

                  case None =>
                    baseBu lderArgu nts :+ { (fmb: FeatureMapBu lder) =>
                      fmb.w hConstantFeature(AuthorUserLabels, Seq.empty)
                    }
                  case _ =>
                    baseBu lderArgu nts
                }

              val t etParent dOpt: Opt on[Long] =
                request.t etParent dMap.flatMap(t etParent dMap => t etParent dMap(t et d))

              v s b l yL brary.featureMapBu lder(bu lderArgu nts :+ { (fmb: FeatureMapBu lder) =>
                fmb.w hConstantFeature(
                  Has nnerC rcleOfFr endsRelat onsh p,
                  has nnerC rcleOfFr endsRelat onsh p)
                fmb.w hConstantFeature(T etConversat on d, request.conversat on d)
                fmb.w hConstantFeature(T etParent d, t etParent dOpt)
                fmb.w hConstantFeature(
                  Conversat onRootAuthor sVer f ed,
                  request.rootAuthor sVer f ed)
              })
            case _ =>
              v s b l yL brary.featureMapBu lder(N l)
          }
          val safetyLevel =
             f (request.m n malSect on ngOnly) T  l neConversat onsDownrank ngM n mal
            else T  l neConversat onsDownrank ng

          val evaluat onContextBu lder = v s b l yL brary
            .evaluat onContextBu lder(request.v e rContext)
            .w hUn OfD vers on(Un OfD vers on.Conversat on d(request.conversat on d))

          v s b l yL brary
            .runRuleEng neBatch(
              request.t et ds.map(T et d),
              featureMapProv der,
              evaluat onContextBu lder,
              safetyLevel
            )
            .map { results: Seq[Try[V s b l yResult]] =>
              val (succeededRequests, _) = results.part  on(_.ex sts(_.f n s d))
              val v s b l yResultMap = succeededRequests.flatMap {
                case Return(result) =>
                  scr beV s b l yVerd ct(
                    result,
                    v s b l yDec derGates.enableVerd ctScr b ngTCVL,
                    verd ctLogger,
                    request.v e rContext.user d,
                    safetyLevel)
                  result.content d match {
                    case T et d( d) => So (( d, result))
                    case _ => None
                  }
                case _ => None
              }.toMap
              val fa ledT et ds = request.t et ds d ff v s b l yResultMap.keys.toSeq
              val response = T  l neConversat onsV s b l yResponse(
                v s b l yResults = v s b l yResultMap,
                fa ledT et ds = fa ledT et ds
              )

              runSt chStartMs = elapsed(). nM ll seconds
              val bu ldSt chStatMs = elapsed(). nM ll seconds
              vfLatencySt chBu ldStat.add(bu ldSt chStatMs)

              response
            }
            .onSuccess(_ => {
              val overallStatMs = elapsed(). nM ll seconds
              vfLatencyOverallStat.add(overallStatMs)
              val runSt chEndMs = elapsed(). nM ll seconds
              vfLatencySt chRunStat.add(runSt chEndMs - runSt chStartMs)
            })
      }
  }

  def scr beV s b l yVerd ct(
    v s b l yResult: V s b l yResult,
    enableVerd ctScr b ng: Gate[Un ],
    verd ctLogger: Verd ctLogger,
    v e r d: Opt on[Long],
    safetyLevel: SafetyLevel
  ): Un  =  f (enableVerd ctScr b ng()) {
    verd ctLogger.scr beVerd ct(
      v s b l yResult = v s b l yResult,
      v e r d = v e r d,
      safetyLevel = toThr ft(safetyLevel),
      vfL bType = VFL bType.T  l neConversat onsV s b l yL brary)
  }

  def createVerd ctLogger(
    enableVerd ctLogger: Gate[Un ],
    dec der: Dec der,
    statsRece ver: StatsRece ver
  ): Verd ctLogger = {
     f (enableVerd ctLogger()) {
      Verd ctLogger(statsRece ver, dec der)
    } else {
      Verd ctLogger.Empty
    }
  }
}
