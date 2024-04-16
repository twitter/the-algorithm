package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.Opt onMono d
 mport com.tw ter.algeb rd.QTree
 mport com.tw ter.algeb rd.QTreeSem group
 mport com.tw ter.algeb rd.Sem group
 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.pluck.s ce.cassowary.Follow ngsCos neS m lar  esManhattanS ce
 mport com.tw ter.pluck.s ce.cassowary.S msCand datesS ce
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.job.analyt cs_batch._
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.users ce.snapshot.flat.Users ceFlatScalaDataset
 mport com.tw ter.users ce.snapshot.flat.thr ftscala.FlatUser

object ClusterDeta lsJob {
  case class Scores(followScore: Double, favScore: Double, logFavScore: Double)

  case class  nter d ateDeta ls(
    numUsersW hAnyNonZeroScore:  nt,
    numUsersW hNonZeroFollowScore:  nt,
    numUsersW hNonZeroFavScore:  nt,
    favQTree: Opt on[QTree[Double]],
    followQTree: Opt on[QTree[Double]],
    logFavQTree: Opt on[QTree[Double]],
    sumOfSquares: Scores,
    sum: Scores,
    m n: Scores,
    max: Scores)

  case class  nfoFromUserS ce(
    fract onMarkedNSFWUser: Double,
    languageToFract onDev ceLanguage: Map[Str ng, Double],
    countryCodeToFract onKnownForW hCountryCode: Map[Str ng, Double],
    languageToFract on nferredLanguage: Map[Str ng, Double])

  def pos  veM n(a: Double, b: Double) = {
     f (math.m n(a, b) == 0.0) math.max(a, b) else math.m n(a, b)
  }

  case class ClusterDeta lsSem group( mpl c  qtreeSem group: Sem group[QTree[Double]])
      extends Sem group[ nter d ateDeta ls] {
    val opt onMono d: Opt onMono d[QTree[Double]] = new Opt onMono d[QTree[Double]]()
    overr de def plus(
      left:  nter d ateDeta ls,
      r ght:  nter d ateDeta ls
    ):  nter d ateDeta ls = {
       nter d ateDeta ls(
        left.numUsersW hAnyNonZeroScore + r ght.numUsersW hAnyNonZeroScore,
        left.numUsersW hNonZeroFollowScore + r ght.numUsersW hNonZeroFollowScore,
        left.numUsersW hNonZeroFavScore + r ght.numUsersW hNonZeroFavScore,
        opt onMono d.plus(left.favQTree, r ght.favQTree),
        opt onMono d.plus(left.followQTree, r ght.followQTree),
        opt onMono d.plus(left.logFavQTree, r ght.logFavQTree),
        Scores(
          left.sumOfSquares.followScore + r ght.sumOfSquares.followScore,
          left.sumOfSquares.favScore + r ght.sumOfSquares.favScore,
          left.sumOfSquares.logFavScore + r ght.sumOfSquares.logFavScore
        ),
        Scores(
          left.sum.followScore + r ght.sum.followScore,
          left.sum.favScore + r ght.sum.favScore,
          left.sum.logFavScore + r ght.sum.logFavScore
        ),
        Scores(
          pos  veM n(left.m n.followScore, r ght.m n.followScore),
          pos  veM n(left.m n.favScore, r ght.m n.favScore),
          pos  veM n(left.m n.logFavScore, r ght.m n.logFavScore)
        ),
        Scores(
          math.max(left.max.followScore, r ght.max.followScore),
          math.max(left.max.favScore, r ght.max.favScore),
          math.max(left.max.logFavScore, r ght.max.logFavScore)
        )
      )
    }
  }

  def  nter d ateDeta lsP pe(
     nput: TypedP pe[(Long, ClustersUser s nterested n)],
    qtreeSem groupKPara ter:  nt
  ): TypedP pe[( nt,  nter d ateDeta ls)] = {
     mpl c  val qtSg: Sem group[QTree[Double]] =
      new QTreeSem group[Double](qtreeSem groupKPara ter)
     mpl c  val cdSg: Sem group[ nter d ateDeta ls] = ClusterDeta lsSem group()
     nput
      .flatMap {
        case (user d, clusterScoresStruct) =>
          val clusterScoresArray = clusterScoresStruct.cluster dToScores.toArray
          clusterScoresArray.map {
            case (cluster d, scoresStruct) =>
              val followScore = scoresStruct.followScore.getOrElse(0.0)
              val favScore = scoresStruct.favScore.getOrElse(0.0)
              val logFavScore = scoresStruct.logFavScore.getOrElse(0.0)
              (
                cluster d,
                 nter d ateDeta ls(
                  numUsersW hAnyNonZeroScore = 1,
                  numUsersW hNonZeroFollowScore =  f (followScore > 0) 1 else 0,
                  numUsersW hNonZeroFavScore =  f (favScore > 0) 1 else 0,
                  favQTree =  f (favScore > 0) So (QTree(favScore)) else None,
                  followQTree =  f (followScore > 0) So (QTree(followScore)) else None,
                  logFavQTree =  f (logFavScore > 0) So (QTree(logFavScore)) else None,
                  sumOfSquares = Scores(
                    followScore * followScore,
                    favScore * favScore,
                    logFavScore * logFavScore),
                  sum = Scores(followScore, favScore, logFavScore),
                  m n = Scores(followScore, favScore, logFavScore),
                  max = Scores(followScore, favScore, logFavScore)
                )
              )
          }
      }
      .sumByKey
      // Uncom nt for adhoc job
      //.w hReducers(100)
      .toTypedP pe
  }

  pr vate def safeGetDoubleOpt(x: Opt on[Double]): Double = {
    x.map { y =>  f (y. sNaN) 0 else y }.getOrElse(0)
  }

  pr vate def getS m lar  esForAllPa rs(
     nput: TypedP pe[(Long, ClustersUser s nterested n)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(( nt,  nt), Scores)] = {
    val allClusterPa rsBeforeSumByKey = Stat("all_cluster_pa rs_before_sum_by_key")
    val clusterPa rsW h n10Rat o = Stat("cluster_pa rs_w h n_10_rat o")
    val clusterPa rsBeforeTopK = Stat("cluster_pa rs_before_threshold ng")

     nput
      .flatMap {
        case (user d, clusterScoresStruct) =>
          val clusterScoresArray = clusterScoresStruct.cluster dToScores.toArray
          (0 unt l clusterScoresArray.length).flatMap {   =>
            (0 unt l clusterScoresArray.length).map { j =>
              val (cluster , scores ) = clusterScoresArray( )
              val (clusterJ, scoresJ) = clusterScoresArray(j)
              val rat oOfS zes =
                scores .numUsers nterested nT ClusterUpperBound.getOrElse(1).toDouble /
                  scoresJ.numUsers nterested nT ClusterUpperBound.getOrElse(1).toDouble
              allClusterPa rsBeforeSumByKey. nc()
               f (rat oOfS zes > 0.1 && rat oOfS zes < 10) {
                clusterPa rsW h n10Rat o. nc()
              }
              val follow  = safeGetDoubleOpt(scores .followScoreClusterNormal zedOnly)
              val followJ = safeGetDoubleOpt(scoresJ.followScoreClusterNormal zedOnly)
              val follow = follow  * followJ
              val fav  = safeGetDoubleOpt(scores .favScoreClusterNormal zedOnly)
              val favJ = safeGetDoubleOpt(scoresJ.favScoreClusterNormal zedOnly)
              val fav = fav  * favJ
              val logFav  = safeGetDoubleOpt(scores .logFavScoreClusterNormal zedOnly)
              val logFavJ = safeGetDoubleOpt(scoresJ.logFavScoreClusterNormal zedOnly)
              val logFav = logFav  * logFavJ
              ((cluster , clusterJ), (follow, fav, logFav))
            }
          }
      }
      .sumByKey
      // Uncom nt for adhoc job
      //.w hReducers(600)
      .map {
        case (key, (follow, fav, logFav)) =>
          clusterPa rsBeforeTopK. nc()
          (key, Scores(follow, fav, logFav))
      }
  }

  pr vate def keepTopNe ghbors(
    allPa rs: TypedP pe[(( nt,  nt), Scores)],
    cos neThreshold: Double
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[( nt, L st[ClusterNe ghbor])] = {
    val clusterPa rsMoreThanThreshold = Stat("cluster_pa rs_cos ne_gt_" + cos neThreshold)
    val clusterPa rsAfterTopK = Stat("cluster_pa rs_after_topk")
    val clustersW hFewNe ghbors = Stat(s"clusters_w h_fe r_than_100_ne ghbors")
    val clustersW hManyNe ghbors = Stat(s"clusters_w h_more_than_100_ne ghbors")

    allPa rs
      .flatMap {
        case ((c , cJ), Scores(followScore, favScore, logFavScore)) =>
           f (followScore > cos neThreshold || logFavScore > cos neThreshold || favScore > cos neThreshold) {
            clusterPa rsMoreThanThreshold. nc()
            So ((c , ClusterNe ghbor(cJ, So (followScore), So (favScore), So (logFavScore))))
          } else None
      }
      .group
      .toL st
      // Uncom nt for adhoc job
      //.w hReducers(40)
      .map {
        case (key, seq) =>
          val f nalS ze = seq.s ze
          clusterPa rsAfterTopK. ncBy(f nalS ze)
           f (f nalS ze < 100) {
            clustersW hFewNe ghbors. nc()
          } else {
            clustersW hManyNe ghbors. nc()
          }
          (
            key,
            seq.sortBy {
              case cn: ClusterNe ghbor =>
                -(cn.followCos neS m lar y.getOrElse(0.0) + cn.logFavCos neS m lar y.getOrElse(
                  0.0)) / 2
            })
      }
  }

  def getTopS m larClustersW hCos ne(
     nput: TypedP pe[(Long, ClustersUser s nterested n)],
    cos neThreshold: Double
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[( nt, L st[ClusterNe ghbor])] = {
    keepTopNe ghbors(getS m lar  esForAllPa rs( nput), cos neThreshold)
  }

  def getD str but onDeta ls(
    qtree: QTree[Double],
    sum: Double,
    sumOfSquares: Double,
    m n: Double,
    max: Double,
    fullS ze:  nt
  ): D str but onDeta ls = {
    val  an = sum / fullS ze
    // note that t  below  s t  na ve calculat on, and not t  sample standard dev formula
    // that d v des by n-1.   don't th nk   makes a d fference at   scale w t r   use n or n-1
    // and  'd rat r use t  s mpler one.
    val stdDev = math.sqrt(sumOfSquares / fullS ze -  an *  an)

    def getQB(percent le: Double): Quant leBounds = {
      val (lb, ub) = qtree.quant leBounds(percent le)
      Quant leBounds(lb, ub)
    }

    D str but onDeta ls(
       an =  an,
      standardDev at on = So (stdDev),
      m n = So (m n),
      p25 = So (getQB(0.25)),
      p50 = So (getQB(0.5)),
      p75 = So (getQB(0.75)),
      p95 = So (getQB(0.95)),
      max = So (max)
    )
  }

  def keepCorrectModel(
     nput: TypedP pe[(Long, ClustersUser s nterested n)],
    modelVers onToKeep: Str ng
  )(
     mpl c  un q d: Un que D
  ): TypedP pe[(Long, ClustersUser s nterested n)] = {
    val allRecords = Stat("all_ nput_records")
    val w hCorrectVers on = Stat("w h_correct_vers on")
     nput.f lter {
      case (_, clusterScoresStruct) =>
        //  allRecords. nc()
        val result = clusterScoresStruct.knownForModelVers on == modelVers onToKeep
        //   f (result) w hCorrectVers on. nc()
        result
    }
  }

  def get nfoFromUserS ce(
    knownFor: TypedP pe[( nt, L st[(Long, Float)])],
    users ce: TypedP pe[FlatUser],
     nferredLanguages: TypedP pe[(Long, Seq[(Str ng, Double)])]
  )(
     mpl c  un q d: Un que D
  ): TypedP pe[( nt,  nfoFromUserS ce)] = {
    val knownForUsers = knownFor.flatMap {
      case (cluster d, userScoreL st) =>
        userScoreL st.map {
          case (user d, _) =>
            (user d, cluster d)
        }
    }

    users ce
      .collect {
        case fuser: FlatUser  f fuser. d. sDef ned =>
          (
            fuser. d.get,
            (
              fuser.accountCountryCode.getOrElse(""),
              fuser.language.getOrElse(""),
              fuser.nsfwUser.getOrElse(false)
            ))
      }
      .jo n(knownForUsers)
      .leftJo n( nferredLanguages)
      .map {
        case (_, (((countryCode, language, nsfw), cluster d),  nferredLangsOpt)) =>
          val nsfw nt =  f (nsfw) 1 else 0
          (
            cluster d,
            (
              1,
              nsfw nt,
              Map(language -> 1),
              Map(countryCode -> 1),
               nferredLangsOpt.getOrElse(Seq(("", 1.0))).toMap
            )
          )
      }
      .sumByKey
      .mapValues {
        case (
              denom nator,
              nsfwNu rator,
              languageNu ratorsMap,
              countryNu ratorsMap,
               nferredLangsNu ratorsMap) =>
           nfoFromUserS ce(
            nsfwNu rator * 1.0 / denom nator,
            languageNu ratorsMap.mapValues { x => x * 1.0 / denom nator },
            countryNu ratorsMap.mapValues { x => x * 1.0 / denom nator },
             nferredLangsNu ratorsMap.mapValues { x => x * 1.0 / denom nator }
          )
      }
  }

  /**
   * Run t  cluster deta ls job and return t  deta ls for each cluster
   * @param  nput  nterested n data
   * @param qtreeSem groupKPara ter para ter for calculat ng percent les us ng qtree mono d (set to a small number, usually < 7)
   * @param modelVers onToKeep wh ch modelVers on to use from  nterested n dataset
   * @param knownFor cluster d -> users known for t  cluster and t  r scores
   * @param knownForTranspose user d -> clusters t  user  s known for and t  r scores
   * @param users ce -> user s ce
   * @param s msGraph -> s ms graph  n t  form of user d -> adjacency l st
   * @param cos neThreshold -> cos ne threshold to  nclude a cluster  n t  l st of s m lar clusters for a g ven cluster
   * @param un q d
   * @return p pe w h (modelVers on, cluster d) as t  key and ClusterDeta ls struct as t  value.
   */
  def run(
     nput: TypedP pe[(Long, ClustersUser s nterested n)],
    qtreeSem groupKPara ter:  nt,
    modelVers onToKeep: Str ng,
    knownFor: TypedP pe[( nt, L st[(Long, Float)])],
    knownForTranspose: TypedP pe[(Long, Array[( nt, Float)])],
    users ce: Opt on[TypedP pe[FlatUser]],
     nferredLanguageS ce: Opt on[TypedP pe[(Long, Seq[(Str ng, Double)])]],
    s msGraph: Opt on[TypedP pe[(Long, Map[Long, Float])]],
    cos neThreshold: Double
  )(
     mpl c  un q d: Un que D
  ): Execut on[TypedP pe[((Str ng,  nt), ClusterDeta ls)]] = {
    val topS m larClusters = getTopS m larClustersW hCos ne( nput, cos neThreshold)
    val  nfoFromUserS ce: TypedP pe[( nt,  nfoFromUserS ce)] = (for {
      us <- users ce
       nferredLanguages <-  nferredLanguageS ce
    } y eld get nfoFromUserS ce(knownFor, us,  nferredLanguages)).getOrElse(TypedP pe.empty)

    val clusterEvaluat onExec = s msGraph match {
      case So (sg) =>
        ClusterEvaluat on.clusterLevelEvaluat on(sg, knownForTranspose, "eval")
      case None =>
        val dum P pe: TypedP pe[( nt, ( nt, ClusterQual y))] = TypedP pe.empty
        Execut on.from(dum P pe)
    }

    clusterEvaluat onExec
      .map { cluster dToS zesAndQual  es =>
        val clusterQual  es: TypedP pe[( nt, ClusterQual y)] =
          cluster dToS zesAndQual  es.mapValues(_._2)
         nter d ateDeta lsP pe(
          keepCorrectModel( nput, modelVers onToKeep),
          qtreeSem groupKPara ter)
          .leftJo n(topS m larClusters)
          .leftJo n( nfoFromUserS ce)
          .leftJo n(clusterQual  es)
          .jo n(knownFor)
          .map {
            case (
                  cluster d,
                  (
                    (
                      (( nter d ateDeta ls, topS m larNe ghborsOpt), userS ce nfoOpt),
                      qual yOpt),
                    knownForUsers)
                ) =>
              val knownForSorted = knownForUsers.sortBy(-_._2).map {
                case (user d, score) =>
                  UserW hScore(user d, score)
              }
              (modelVers onToKeep, cluster d) ->
                ClusterDeta ls(
                  numUsersW hAnyNonZeroScore =  nter d ateDeta ls.numUsersW hAnyNonZeroScore,
                  numUsersW hNonZeroFavScore =  nter d ateDeta ls.numUsersW hNonZeroFavScore,
                  numUsersW hNonZeroFollowScore =
                     nter d ateDeta ls.numUsersW hNonZeroFollowScore,
                  favScoreD str but onDeta ls =  nter d ateDeta ls.favQTree.map { qt =>
                    getD str but onDeta ls(
                      qtree = qt,
                      sum =  nter d ateDeta ls.sum.favScore,
                      sumOfSquares =  nter d ateDeta ls.sumOfSquares.favScore,
                      m n =  nter d ateDeta ls.m n.favScore,
                      max =  nter d ateDeta ls.max.favScore,
                      fullS ze =  nter d ateDeta ls.numUsersW hNonZeroFavScore
                    )
                  },
                  followScoreD str but onDeta ls =  nter d ateDeta ls.followQTree.map { qt =>
                    getD str but onDeta ls(
                      qtree = qt,
                      sum =  nter d ateDeta ls.sum.followScore,
                      sumOfSquares =  nter d ateDeta ls.sumOfSquares.followScore,
                      m n =  nter d ateDeta ls.m n.followScore,
                      max =  nter d ateDeta ls.max.followScore,
                      fullS ze =  nter d ateDeta ls.numUsersW hNonZeroFollowScore
                    )
                  },
                  logFavScoreD str but onDeta ls =  nter d ateDeta ls.logFavQTree.map { qt =>
                    getD str but onDeta ls(
                      qtree = qt,
                      sum =  nter d ateDeta ls.sum.logFavScore,
                      sumOfSquares =  nter d ateDeta ls.sumOfSquares.logFavScore,
                      m n =  nter d ateDeta ls.m n.logFavScore,
                      max =  nter d ateDeta ls.max.logFavScore,
                      // note: user has non-zero fav score  ff a user has non-zero log-fav score
                      fullS ze =  nter d ateDeta ls.numUsersW hNonZeroFavScore
                    )
                  },
                  knownForUsersAndScores = So (knownForSorted),
                  ne ghborClusters = topS m larNe ghborsOpt,
                  fract onKnownForMarkedNSFWUser = userS ce nfoOpt.map(_.fract onMarkedNSFWUser),
                  languageToFract onDev ceLanguage =
                    userS ce nfoOpt.map(_.languageToFract onDev ceLanguage),
                  countryCodeToFract onKnownForW hCountryCode =
                    userS ce nfoOpt.map(_.countryCodeToFract onKnownForW hCountryCode),
                  qual y asuredOnS msGraph = qual yOpt,
                  languageToFract on nferredLanguage =
                    userS ce nfoOpt.map(_.languageToFract on nferredLanguage),
                )
          }
      }
  }

  def getTruncatedS ms(
    s ms: TypedP pe[Cand dates],
    maxNe ghbors:  nt
  ): TypedP pe[(Long, Map[Long, Float])] = {
    s ms.map { cands =>
      (
        cands.user d,
        // T se cand dates are already sorted, but leav ng    n just  n case t  behav or changes upstream
        cands.cand dates
          .map { c => (c.user d, c.score.toFloat) }.sortBy(-_._2).take(maxNe ghbors).toMap
      )
    }
  }
}

/**
 scald ng remote run  --ma n-class com.tw ter.s mclusters_v2.scald ng.ClusterDeta lsAdhoc \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng:cluster_deta ls-adhoc \
  --hadoop-propert es "scald ng.w h.reducers.set.expl c ly=true mapreduce.job.reduces=4000" \
  --user recos-platform -- \
  --date 2020-06-25 \
  --dateForUserS ce 2020-06-25 \
  -- ncludeUserS ce \
  --outputD r /user/recos-platform/adhoc/y _ldap/cluster_deta ls_ nferred_lang
 */
object ClusterDeta lsAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val date = DateRange.parse(args("dateForUserS ce"))
          val (knownFor, knownForTranspose) =
            args
              .opt onal("knownForD r").map { locat on =>
                (
                  KnownForS ces.transpose(KnownForS ces.readKnownFor(locat on)),
                  KnownForS ces.readKnownFor(locat on)
                )
              }.getOrElse(
                (
                  KnownForS ces.clusterToKnownFor_20M_145K_updated,
                  KnownForS ces.knownFor_20M_145K_updated
                )
              )

          val  nterested n = args
            .opt onal(" nputD r").map {  nterested n nputD r =>
              TypedP pe.from(AdhocKeyValS ces. nterested nS ce( nterested n nputD r))
            }.getOrElse(
              DAL
                .readMostRecentSnapshotNoOlderThan(
                  S mclustersV2 nterested n20M145KUpdatedScalaDataset,
                  Days(14))
                .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
                .toTypedP pe
                .map {
                  case KeyVal(user d, clustersUser s nterested n) =>
                    (user d, clustersUser s nterested n)
                }
            )

          val userS ceOpt =  f (args.boolean(" ncludeUserS ce")) {
            So (DAL.readMostRecentSnapshot(Users ceFlatScalaDataset, date).toTypedP pe)
          } else None

          val  nferredLanguagesOpt =  f (args.boolean(" ncludeUserS ce")) {
            So (ExternalDataS ces. nferredUserProducedLanguageS ce)
          } else None

          val s msGraphOpt = args.opt onal("s msForEval nputD r").map { sgD r =>
            ClusterDeta lsJob.getTruncatedS ms(
              TypedP pe.from(WTFCand datesS ce(sgD r)),
              args. nt("maxS msNe ghborsForEval", 20)
            )
          }

          Ut l.pr ntCounters(
            ClusterDeta lsJob
              .run(
                 nterested n,
                args. nt("qtreeSem groupKPara ter", 3),
                args.getOrElse("modelVers on", "20M_145K_updated"),
                knownFor,
                knownForTranspose,
                userS ceOpt,
                 nferredLanguagesOpt,
                s msGraphOpt,
                cos neThreshold = args.double("cos neThreshold", 0.01)
              ).flatMap(
                _.wr eExecut on(AdhocKeyValS ces.clusterDeta lsS ce(args("outputD r"))))
          )
        }
    }
}

tra  ClusterDeta lsBatchTra  extends Tw terSc duledExecut onApp {
   mpl c  val tz = DateOps.UTC
   mpl c  val parser = DateParser.default

  def f rstT  : Str ng
  def batch ncre nt: Durat on
  def manhattanOutputPath: Str ng
  def clusterDeta lsL eOutputPath: Str ng
  def modelVers on: Str ng
  def knownForDataset: KeyValDALDataset[KeyVal[Long, ClustersUser sKnownFor]]
  def  nterested nDataset: KeyValDALDataset[KeyVal[Long, ClustersUser s nterested n]]
  def outputDataset: KeyValDALDataset[KeyVal[(Str ng,  nt), ClusterDeta ls]]
  def clusterDeta lsL eOutputDataset: SnapshotDALDataset[ClusterDeta lsL e]

  pr vate lazy val execArgs = Analyt csBatchExecut onArgs(
    batchDesc = BatchDescr pt on(t .getClass.getNa .replace("$", "")),
    f rstT   = BatchF rstT  (R chDate(f rstT  )),
    lastT   = None,
    batch ncre nt = Batch ncre nt(batch ncre nt)
  )

  overr de def sc duledJob: Execut on[Un ] = Analyt csBatchExecut on(execArgs) {
     mpl c  dateRange =>
      Execut on.w h d {  mpl c  un que d =>
        Execut on.w hArgs { args =>
          val qtreeSem groupKPara ter = args. nt("qtreeSem groupKPara ter", 5)
          val maxS msNe ghborsForEval = args. nt("maxS msNe ghborsForEval", 20)
          val knownForTranspose =
            KnownForS ces.fromKeyVal(
              DAL.readMostRecentSnapshot(knownForDataset, dateRange.extend(Days(7))).toTypedP pe,
              modelVers on)
          val knownFor = KnownForS ces.transpose(knownForTranspose)
          val cos neThreshold = args.double("cos neThreshold", 0.01)
          val  nterested n =
            DAL
              .readMostRecentSnapshot( nterested nDataset, dateRange.extend(Days(7)))
              .toTypedP pe
              .map {
                case KeyVal(user d, clustersUser s nterested n) =>
                  (user d, clustersUser s nterested n)
              }
          val s ms =  f (modelVers on == ModelVers ons.Model20M145K2020) {
            // T  model vers on 20m_145k_2020 uses approx mate_cos ne_follow as t   nput s ms graph
            // to cluster users. T  sa  graph  s used to evaluate t  clusters
            TypedP pe
              .from(Follow ngsCos neS m lar  esManhattanS ce())
              .map(_._2)
          } else {
            TypedP pe.from(
              S msCand datesS ce()(
                dateRange = dateRange,
                suff xPath = "/class f ed_cand dates_rollup"
              ))
          }
          val resultExec = ClusterDeta lsJob
            .run(
               nterested n,
              qtreeSem groupKPara ter,
              modelVers on,
              knownFor,
              knownForTranspose,
              So (DAL.readMostRecentSnapshot(Users ceFlatScalaDataset, dateRange).toTypedP pe),
              So (ExternalDataS ces. nferredUserProducedLanguageS ce),
              So (
                ClusterDeta lsJob.getTruncatedS ms(s ms, maxNe ghbors = maxS msNe ghborsForEval)),
              cos neThreshold
            ).flatMap { resultUnmapped =>
              val clusterDeta lsExec = resultUnmapped
                .map {
                  case (clusterKey, deta ls) =>
                    KeyVal(clusterKey, deta ls)
                }.wr eDALVers onedKeyValExecut on(
                  outputDataset,
                  D.Suff x(manhattanOutputPath)
                )

              val clusterDeta lsL eExec =
                resultUnmapped
                  .map {
                    case ((_, cluster d), deta ls)
                         f modelVers on == ModelVers ons.Model20M145KDec11 =>
                      ClusterDeta lsL e(
                        FullCluster d(ModelVers on.Model20m145kDec11, cluster d),
                        deta ls.numUsersW hAnyNonZeroScore,
                        deta ls.numUsersW hNonZeroFollowScore,
                        deta ls.numUsersW hNonZeroFavScore,
                        deta ls.knownForUsersAndScores.getOrElse(N l)
                      )
                    case ((_, cluster d), deta ls)
                         f modelVers on == ModelVers ons.Model20M145KUpdated =>
                      ClusterDeta lsL e(
                        FullCluster d(ModelVers on.Model20m145kUpdated, cluster d),
                        deta ls.numUsersW hAnyNonZeroScore,
                        deta ls.numUsersW hNonZeroFollowScore,
                        deta ls.numUsersW hNonZeroFavScore,
                        deta ls.knownForUsersAndScores.getOrElse(N l)
                      )
                    case ((_, cluster d), deta ls)
                         f modelVers on == ModelVers ons.Model20M145K2020 =>
                      ClusterDeta lsL e(
                        FullCluster d(ModelVers on.Model20m145k2020, cluster d),
                        deta ls.numUsersW hAnyNonZeroScore,
                        deta ls.numUsersW hNonZeroFollowScore,
                        deta ls.numUsersW hNonZeroFavScore,
                        deta ls.knownForUsersAndScores.getOrElse(N l)
                      )
                  }.wr eDALSnapshotExecut on(
                    clusterDeta lsL eOutputDataset,
                    D.Da ly,
                    D.Suff x(clusterDeta lsL eOutputPath),
                    D.EBLzo(),
                    dateRange.end)

              Execut on.z p(clusterDeta lsExec, clusterDeta lsL eExec)
            }

          Ut l.pr ntCounters(resultExec)
        }
      }
  }

}

object ClusterDeta lsBatch extends ClusterDeta lsBatchTra  {
  overr de val f rstT  : Str ng = "2018-07-28"
  overr de val batch ncre nt: Durat on = Days(7)

  overr de val manhattanOutputPath: Str ng =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_cluster_deta ls"

  overr de val clusterDeta lsL eOutputPath: Str ng =
    "/user/cassowary/processed/s mclusters_v2_cluster_deta ls_l e"

  overr de val modelVers on: Str ng = ModelVers ons.Model20M145KDec11
  overr de val knownForDataset = S mclustersV2KnownFor20M145KDec11ScalaDataset
  overr de val  nterested nDataset = S mclustersV2 nterested nScalaDataset
  overr de val outputDataset = S mclustersV2ClusterDeta lsScalaDataset
  overr de val clusterDeta lsL eOutputDataset =
    S mclustersV2ClusterDeta lsL eScalaDataset
}

object ClusterDeta ls20M145KUpdated extends ClusterDeta lsBatchTra  {
  overr de val f rstT  : Str ng = "2019-06-16"
  overr de val batch ncre nt: Durat on = Days(7)

  overr de val manhattanOutputPath: Str ng =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_cluster_deta ls_20m_145k_updated"

  overr de val clusterDeta lsL eOutputPath: Str ng =
    "/user/cassowary/processed/s mclusters_v2_cluster_deta ls_l e_20m_145k_updated"

  overr de val modelVers on: Str ng = ModelVers ons.Model20M145KUpdated
  overr de val knownForDataset = S mclustersV2KnownFor20M145KUpdatedScalaDataset
  overr de val  nterested nDataset = S mclustersV2 nterested n20M145KUpdatedScalaDataset
  overr de val outputDataset = S mclustersV2ClusterDeta ls20M145KUpdatedScalaDataset
  overr de val clusterDeta lsL eOutputDataset =
    S mclustersV2ClusterDeta lsL e20M145KUpdatedScalaDataset
}

/**
 * capesospy-v2 update --bu ld_locally --start_cron cluster_deta ls_20m_145k_2020 \
 * src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object ClusterDeta ls20M145K2020 extends ClusterDeta lsBatchTra  {
  overr de val f rstT  : Str ng = "2020-10-15"
  overr de val batch ncre nt: Durat on = Days(7)

  overr de val manhattanOutputPath: Str ng =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_cluster_deta ls_20m_145k_2020"

  overr de val clusterDeta lsL eOutputPath: Str ng =
    "/user/cassowary/processed/s mclusters_v2_cluster_deta ls_l e_20m_145k_2020"

  overr de val modelVers on: Str ng = ModelVers ons.Model20M145K2020
  overr de val knownForDataset = S mclustersV2KnownFor20M145K2020ScalaDataset
  overr de val  nterested nDataset = S mclustersV2 nterested n20M145K2020ScalaDataset
  overr de val outputDataset = S mclustersV2ClusterDeta ls20M145K2020ScalaDataset
  overr de val clusterDeta lsL eOutputDataset =
    S mclustersV2ClusterDeta lsL e20M145K2020ScalaDataset
}

/**
scald ng remote run  --ma n-class com.tw ter.s mclusters_v2.scald ng.DumpClusterDeta lsAdhoc \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng:cluster_deta ls-dump \
  --user recos-platform -- \
  --date 2020-06-25 \
  --cluster ds 5542 129677 48645 \
  -- nputD r /user/recos-platform/adhoc/y _ldap/cluster_deta ls_ nferred_lang
 */
object DumpClusterDeta lsAdhoc extends Tw terExecut onApp {
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          val clusters = args.l st("cluster ds").map(_.to nt).toSet //(1 to 2500).toSet //
          TypedP pe
            .from(AdhocKeyValS ces.clusterDeta lsS ce(args(" nputD r")))
            .f lter { case ((modelVers on, cluster d), deta ls) => clusters.conta ns(cluster d) }
            .to erableExecut on
            .map {  er =>
               er.foreach { x => pr ntln(Ut l.prettyJsonMapper.wr eValueAsStr ng(x)) }
            }
        }
    }
}

/**
 * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:cluster_deta ls && \
 * oscar hdfs --user cassowary --host hadoopnest2.atla.tw ter.com --bundle cluster_deta ls \
 * --tool com.tw ter.s mclusters_v2.scald ng.DumpClusterS m lar  esAdhoc --screen --screen-detac d \
 * --tee y _ldap/dumpClusterS m lar  es_20200103 -- \
 * -- nputD r /user/cassowary/manhattan_sequence_f les/s mclusters_v2_cluster_deta ls_20m_145k_updated/ \
 * --outputD r adhoc/y _ldap
 */
object DumpClusterS m lar  esAdhoc extends Tw terExecut onApp {
  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs
          TypedP pe
            .from(AdhocKeyValS ces.clusterDeta lsS ce(args(" nputD r")))
            .flatMap {
              case ((_, cluster d), deta ls) =>
                deta ls.ne ghborClusters.getOrElse(N l).map { ne ghbor =>
                  val compos eScore = (ne ghbor.followCos neS m lar y
                    .getOrElse(0.0) + ne ghbor.favCos neS m lar y.getOrElse(0.0)) / 2
                  (
                    cluster d,
                    ne ghbor.cluster d,
                    "%.4f".format(compos eScore)
                  )
                }
            }.wr eExecut on(TypedTsv(args("outputD r")))
        }
    }
}
