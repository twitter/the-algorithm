package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.Aggregator
 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.commons.s ce.Vers onedKeyValS ce
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.NormsAndCountsF xedPathS ce
 mport com.tw ter.s mclusters_v2.hdfs_s ces.ProducerNormsAndCountsScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2 nterested nScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserAndNe ghborsF xedPathS ce
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserUserNormal zedGraphScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.B part eClusterEvaluat onClasses._
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.B part eClusterQual y
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.Ne ghborW h  ghts
 mport com.tw ter.s mclusters_v2.thr ftscala.NormsAndCounts
 mport com.tw ter.s mclusters_v2.thr ftscala.UserAndNe ghbors
 mport scala.collect on.JavaConverters._

object B part eClusterEvaluat on extends Tw terExecut onApp {

   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default

  pr vate def getClusterL2Norms(
    knownFor: TypedP pe[(Long, Array[( nt, Float)])]
  ): Execut on[Map[ nt, Float]] = {
    knownFor
      .flatMap {
        case (_, clusterArray) =>
          clusterArray.map {
            case (cluster d, score) =>
              Map(cluster d -> score * score)
          }
      }
      .sum
      .getExecut on
      .map(_.mapValues { x => math.sqrt(x).toFloat })
  }

  def l2Normal zeKnownFor(
    knownFor: TypedP pe[(Long, Array[( nt, Float)])]
  ): Execut on[TypedP pe[(Long, Array[( nt, Float)])]] = {
    getClusterL2Norms(knownFor).map { clusterToNorms =>
      knownFor.mapValues { clusterScoresArray =>
        clusterScoresArray.map {
          case (cluster d, score) =>
            (cluster d, score / clusterToNorms(cluster d))
        }
      }
    }
  }

  /**
   * ./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng:bp_cluster_evaluat on && \
   * oscar hdfs --user fr gate --host hadoopnest2.atla.tw ter.com --bundle bp_cluster_evaluat on \
   * --tool com.tw ter.s mclusters_v2.scald ng.B part eClusterEvaluat on --screen --screen-detac d \
   * --tee logs/newBpQual y_updateUnnormal zedScores_ nterested nUs ng20190329Graph_evaluatedOn20190329Graph_run2 \
   * -- --normsAndCountsD r /user/fr gate/y _ldap/producerNormsAndCounts_20190330 \
   * --graph nputD r /user/fr gate/y _ldap/user_user_normal zed_graph_cop edFromAtlaProc_20190329 \
   * --knownForD r /user/fr gate/y _ldap/d rFor_updatedKnownFor20M_145K_dec11_us ngS ms20190127_unnormal zed nputScores/knownFor \
   * -- nterested nD r /user/fr gate/y _ldap/d rFor_updatedKnownFor20M_145K_dec11_us ngS ms20190127_unnormal zed nputScores/ nterested nUs ng20190329Graph \
   * --outgo ngVolu sResultsD r /user/fr gate/y _ldap/d rFor_updatedKnownFor20M_145K_dec11_us ngS ms20190127_unnormal zed nputScores/bpQual yFor nterested nUs ng20190329On20190329Graph_outgo ngVolu s \
   * -- ncom ngVolu sResultsD r /user/fr gate/y _ldap/d rFor_updatedKnownFor20M_145K_dec11_us ngS ms20190127_unnormal zed nputScores/bpQual yFor nterested nUs ng20190329On20190329Graph_ ncom ngVolu s \
   * --outputD r /user/fr gate/y _ldap/d rFor_updatedKnownFor20M_145K_dec11_us ngS ms20190127_unnormal zed nputScores/bpQual yFor nterested nUs ng20190329On20190329Graph_perCluster \
   * --toEma lAddress y _ldap@tw ter.com --modelVers on 20M_145K_updated
   */
  overr de def job: Execut on[Un ] = Execut on.getConf gMode.flatMap {
    case (conf g, mode) =>
      Execut on.w h d {  mpl c  un que d =>
        val args = conf g.getArgs

        val  nterested n = args.opt onal(" nterested nD r") match {
          case So (d r) =>
            TypedP pe
              .from(AdhocKeyValS ces. nterested nS ce(args(" nterested nD r")))
          case None =>
            DAL
              .readMostRecentSnapshotNoOlderThan(
                S mclustersV2 nterested nScalaDataset,
                Days(20)
              )
              .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
              .toTypedP pe
              .map {
                case KeyVal(key, value) => (key, value)
              }
        }

        val  nputKnownFor = args
          .opt onal("knownForD r")
          .map { locat on => KnownForS ces.readKnownFor(locat on) }
          .getOrElse(KnownForS ces.knownFor_20M_Dec11_145K)

        val modelVers on =
          args.opt onal("modelVers on").getOrElse("20M_145K_dec11")

        val useLogFav  ghts = args.boolean("useLogFav  ghts")

        val shouldL2Normal zeKnownFor = args.boolean("l2Normal zeKnownFor")

        val toEma lAddressOpt = args.opt onal("toEma lAddress")

        val knownForExec =  f (shouldL2Normal zeKnownFor) {
          l2Normal zeKnownFor( nputKnownFor)
        } else {
          Execut on.from( nputKnownFor)
        }

        val f nalExec = knownForExec.flatMap { knownFor =>
          val graph = args.opt onal("graph nputD r") match {
            case So (d r) =>
              TypedP pe.from(UserAndNe ghborsF xedPathS ce(d r))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(20))
                .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
                .toTypedP pe
          }

          val producerNormsAndCounts = args.opt onal("normsAndCountsD r") match {
            case So (d r) =>
              TypedP pe.from(NormsAndCountsF xedPathS ce(args(d r)))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(ProducerNormsAndCountsScalaDataset, Days(20))
                .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
                .toTypedP pe
          }

          val cluster ncom ngVolu sExec = loadOrMake(
            computeCluster ncom ngVolu s(knownFor, producerNormsAndCounts, useLogFav  ghts),
            modelVers on,
            args(" ncom ngVolu sResultsD r")
          )

          val resultsW hOutgo ngVolu sExec = loadOrMake(
            getResultsW hOutgo ngVolu s(graph,  nterested n, useLogFav  ghts),
            modelVers on,
            args("outgo ngVolu sResultsD r")
          )

          val f nalPerClusterResultsExec =
            f nalPerClusterResults(
              knownFor,
               nterested n,
              resultsW hOutgo ngVolu sExec,
              cluster ncom ngVolu sExec)
              .flatMap { p pe => loadOrMake(p pe, modelVers on, args("outputD r")) }

          f nalPerClusterResultsExec.flatMap { f nalPerClusterResults =>
            val perClusterResults = f nalPerClusterResults.values
            val d str but onResultsExec = getClusterResultsSummary(perClusterResults).map {
              case So (summary) =>
                "Summary of results across clusters: \n" +
                  Ut l.prettyJsonMapper.wr eValueAsStr ng(summary)
              case _ =>
                "No summary of results! T  cluster level results p pe must be empty!"
            }

            val overallResultsExec = perClusterResults.sum.toOpt onExecut on.map {
              case So (overallQual y) =>
                "Overall Qual y: \n" +
                  Ut l.prettyJsonMapper.wr eValueAsStr ng(
                    pr ntableB part eQual y(overallQual y)
                  )
              case _ =>
                "No overall qual y! T  cluster level results p pe must be empty!"
            }

            Execut on.z p(d str but onResultsExec, overallResultsExec).map {
              case (d stResults, overallResults) =>
                toEma lAddressOpt.foreach { address =>
                  Ut l.sendEma l(
                    d stResults + "\n" + overallResults,
                    "B part e cluster qual y for " + modelVers on,
                    address
                  )
                }
                pr ntln(d stResults + "\n" + overallResults)
            }
          }
        }
        Ut l.pr ntCounters(f nalExec)
      }
  }

  def getResultsW hOutgo ngVolu s(
    graph: TypedP pe[UserAndNe ghbors],
     nterested n: TypedP pe[(Long, ClustersUser s nterested n)],
    useLogFav  ghts: Boolean
  ): TypedP pe[( nt, B part eClusterQual y)] = {
    graph
      .map { un => (un.user d, un.ne ghbors) }
      // should t  be a leftJo n? For now, leav ng   as an  nner jo n.  f  n t  future,
      //   want to compare two approac s w h very d fferent coverages on  nterested n, t 
      // could beco  a problem.
      .jo n( nterested n)
      .w hReducers(4000)
      .flatMap {
        case (user d, (ne ghbors, clusters)) =>
          getB ResultsFromS ngleUser(user d, ne ghbors, clusters, useLogFav  ghts)
      }
      .sumByKey
      .w hReducers(600)
      .map {
        case (cluster d, b r) =>
          (
            cluster d,
            B part eClusterQual y(
               nClusterFollowEdges = So (b r. nCluster  ghts. sFollowEdge),
               nClusterFavEdges = So (b r. nCluster  ghts. sFavEdge),
              favWtSumOf nClusterFollowEdges = So (b r. nCluster  ghts.favWt fFollowEdge),
              favWtSumOf nClusterFavEdges = So (b r. nCluster  ghts.favWt fFavEdge),
              outgo ngFollowEdges = So (b r.totalOutgo ngVolu s. sFollowEdge),
              outgo ngFavEdges = So (b r.totalOutgo ngVolu s. sFavEdge),
              favWtSumOfOutgo ngFollowEdges = So (b r.totalOutgo ngVolu s.favWt fFollowEdge),
              favWtSumOfOutgo ngFavEdges = So (b r.totalOutgo ngVolu s.favWt fFavEdge),
               nterested nS ze = So (b r. nterested nS ze),
              sampledEdges = So (
                b r.edgeSample
                  . erator()
                  .asScala
                  .toSeq
                  .map {
                    case (edge, data) => makeThr ftSampledEdge(edge, data)
                  }
              )
            )
          )
      }
  }

  def getB ResultsFromS ngleUser(
    user d: Long,
    ne ghbors: Seq[Ne ghborW h  ghts],
    clusters: ClustersUser s nterested n,
    useLogFavScores: Boolean
  ): L st[( nt, B part e nter d ateResults)] = {
    val ne ghborsTo  ghts = ne ghbors.map { ne ghborAnd  ghts =>
      val  sFollowEdge = ne ghborAnd  ghts. sFollo d match {
        case So (true) => 1.0
        case _ => 0.0
      }
      val favScore =  f (useLogFavScores) {
        ne ghborAnd  ghts.logFavScore.getOrElse(0.0)
      } else ne ghborAnd  ghts.favScoreHalfL fe100Days.getOrElse(0.0)
      val  sFavEdge = math.m n(1, math.ce l(favScore))
      ne ghborAnd  ghts.ne ghbor d ->   ghts(
         sFollowEdge,
         sFavEdge,
        favScore *  sFollowEdge,
        favScore
      )
    }.toMap

    val outgo ngVolu s = Mono d.sum(ne ghborsTo  ghts.values)(  ghtsMono d)

    clusters.cluster dToScores.toL st.map {
      case (cluster d, scoresStruct) =>
        val  nClusterNe ghbors =
          (scoresStruct.usersBe ngFollo d.getOrElse(N l) ++
            scoresStruct.usersThat reFaved.getOrElse(N l)).toSet
        val edgesForSampl ng =  nClusterNe ghbors.flatMap { ne ghbor d =>
           f (ne ghborsTo  ghts.conta ns(ne ghbor d)) {
            So (
              (user d, ne ghbor d),
              SampledEdgeData(
                ne ghborsTo  ghts(ne ghbor d).favWt fFollowEdge,
                ne ghborsTo  ghts(ne ghbor d).favWt fFavEdge,
                scoresStruct.followScore.getOrElse(0.0),
                scoresStruct.favScore.getOrElse(0.0)
              )
            )
          } else {
            None
          }
        }

        val  nCluster  ghts =
          Mono d.sum(ne ghborsTo  ghts.f lterKeys( nClusterNe ghbors).values)(  ghtsMono d)

        (
          cluster d,
          B part e nter d ateResults(
             nCluster  ghts,
            outgo ngVolu s,
            1,
            samplerMono d.bu ld(edgesForSampl ng)
          ))
    }
  }

  def computeCluster ncom ngVolu s(
    knownFor: TypedP pe[(Long, Array[( nt, Float)])],
    producerNormsAndCounts: TypedP pe[NormsAndCounts],
    useLogFav  ghts: Boolean
  ): TypedP pe[( nt, B part eClusterQual y)] = {
    producerNormsAndCounts
      .map { x => (x.user d, x) }
      .jo n(knownFor)
      .w hReducers(100)
      .flatMap {
        case (user d, (normsAndCounts, clusters)) =>
          clusters.map {
            case (cluster d, _) =>
              val follo rCount =
                normsAndCounts.follo rCount.getOrElse(0L).toDouble
              val faverCount = normsAndCounts.faverCount.getOrElse(0L).toDouble
              val favWtSumOf ncom ngFollows =  f (useLogFav  ghts) {
                normsAndCounts.logFav  ghtsOnFollowEdgesSum.getOrElse(0.0)
              } else {
                normsAndCounts.fav  ghtsOnFollowEdgesSum.getOrElse(0.0)
              }
              val favWtSumOf ncom ngFavs =  f (useLogFav  ghts) {
                normsAndCounts.logFav  ghtsOnFavEdgesSum.getOrElse(0.0)
              } else {
                normsAndCounts.fav  ghtsOnFavEdgesSum.getOrElse(0.0)
              }
              (
                cluster d,
                B part eClusterQual y(
                   ncom ngFollowEdges = So (follo rCount),
                   ncom ngFavEdges = So (faverCount),
                  favWtSumOf ncom ngFollowEdges = So (favWtSumOf ncom ngFollows),
                  favWtSumOf ncom ngFavEdges = So (favWtSumOf ncom ngFavs)
                ))
          }
      }
      .sumByKey
      .toTypedP pe
  }

  def loadOrMake(
    p pe: TypedP pe[( nt, B part eClusterQual y)],
    modelVers on: Str ng,
    path: Str ng
  ): Execut on[TypedP pe[( nt, B part eClusterQual y)]] = {
    val mapped = p pe.map {
      case (cluster d, struct) => ((modelVers on, cluster d), struct)
    }
    makeForKeyValS ce(mapped, AdhocKeyValS ces.b part eQual yS ce(path), path).map { p pe =>
      // d scard model vers on
      p pe.map { case ((_, cluster d), struct) => (cluster d, struct) }
    }
  }

  def makeForKeyValS ce[K, V](
    p pe: TypedP pe[(K, V)],
    dest: Vers onedKeyValS ce[K, V],
    path: Str ng
  ): Execut on[TypedP pe[(K, V)]] =
    Execut on.getMode.flatMap { mode =>
       f (dest.res ceEx sts(mode)) {
        pr ntln(s"val dated path $path")
        Execut on.from(TypedP pe.from(dest))
      } else {
        pr ntln(s"Could not load from $path")
        p pe.wr eThrough(dest)
      }
    }

  def prec s onOfWholeGraph(
    knownFor: TypedP pe[(Long, Array[( nt, Float)])],
     nterested n: TypedP pe[(Long, ClustersUser s nterested n)],
    cluster ncom ngVolu sExec: Execut on[TypedP pe[( nt, B part eClusterQual y)]]
  ): Execut on[Opt on[Double]] = {
    val knownForS zeExec = knownFor.aggregate(Aggregator.s ze).toOpt onExecut on
    val  nterested nS zeExec =
       nterested n.aggregate(Aggregator.s ze).toOpt onExecut on
    val numExec = cluster ncom ngVolu sExec.flatMap { volu s =>
      volu s.values.flatMap(_.favWtSumOf ncom ngFavEdges).sum.toOpt onExecut on
    }
    Execut on.z p(numExec,  nterested nS zeExec, knownForS zeExec).map {
      case (So (num), So ( nterested nS ze), So (knownForS ze)) =>
        So (num /  nterested nS ze / knownForS ze)
      case x @ _ =>
        pr ntln("Prec s on of whole graph z p: " + x)
        None
    }
  }

  def f nalPerClusterResults(
    knownFor: TypedP pe[(Long, Array[( nt, Float)])],
     nterested n: TypedP pe[(Long, ClustersUser s nterested n)],
    resultsW hOutgo ngVolu sExec: Execut on[TypedP pe[( nt, B part eClusterQual y)]],
     ncom ngVolu sExec: Execut on[TypedP pe[( nt, B part eClusterQual y)]]
  ): Execut on[TypedP pe[( nt, B part eClusterQual y)]] = {
    val knownForTranspose = KnownForS ces.transpose(knownFor)

    val prec s onOfWholeGraphExec =
      prec s onOfWholeGraph(knownFor,  nterested n,  ncom ngVolu sExec)

    Execut on
      .z p(resultsW hOutgo ngVolu sExec,  ncom ngVolu sExec, prec s onOfWholeGraphExec)
      .map {
        case (resultsW hOutgo ngVolu s, cluster ncom ngVolu s, prec s onOfWholeGraph) =>
          pr ntln("Prec s on of whole graph " + prec s onOfWholeGraph)
          resultsW hOutgo ngVolu s
            .jo n(knownForTranspose)
            .leftJo n(cluster ncom ngVolu s)
            .w hReducers(500)
            .map {
              case (cluster d, ((outgo ngVolu Qual y, knownForL st),  ncom ngVolu sOpt)) =>
                val  ncom ngVolu s =
                   ncom ngVolu sOpt.getOrElse(B part eClusterQual y())
                val knownForMap = knownForL st.toMap
                (
                  cluster d,
                  getFullQual y(
                    outgo ngVolu Qual y,
                     ncom ngVolu s,
                    knownForMap,
                    prec s onOfWholeGraph))
            }
      }
  }

  def getFullQual y(
    qual yW hOutgo ngVolu s: B part eClusterQual y,
     ncom ngVolu s: B part eClusterQual y,
    knownFor: Map[Long, Float],
    prec s onOfWholeGraph: Opt on[Double]
  ): B part eClusterQual y = {
    val newSampledEdges = qual yW hOutgo ngVolu s.sampledEdges.map { sampledEdges =>
      sampledEdges.map { sampledEdge =>
        val knownForScore = knownFor.getOrElse(sampledEdge.follo e d, 0.0f)
        sampledEdge.copy(
          pred ctedFollowScore = sampledEdge.followScoreToCluster.map { x => x * knownForScore },
          pred ctedFavScore = sampledEdge.favScoreToCluster.map { x => x * knownForScore }
        )
      }
    }
    val correlat onOfFavWt fFollow = newSampledEdges.map { samples =>
      val pa rs = samples.map { s =>
        (s.pred ctedFollowScore.getOrElse(0.0), s.favWt fFollowEdge.getOrElse(0.0))
      }
      Ut l.computeCorrelat on(pa rs. erator)
    }
    val correlat onOfFavWt fFav = newSampledEdges.map { samples =>
      val pa rs = samples.map { s =>
        (s.pred ctedFavScore.getOrElse(0.0), s.favWt fFavEdge.getOrElse(0.0))
      }
      Ut l.computeCorrelat on(pa rs. erator)
    }
    val relat vePrec s onNum = {
       f (qual yW hOutgo ngVolu s. nterested nS ze.ex sts(_ > 0) && knownFor.nonEmpty) {
        qual yW hOutgo ngVolu s.favWtSumOf nClusterFavEdges
          .getOrElse(0.0) / qual yW hOutgo ngVolu s. nterested nS ze.get / knownFor.s ze
      } else 0.0
    }
    val relat vePrec s on =  f (prec s onOfWholeGraph.ex sts(_ > 0.0)) {
      So (relat vePrec s onNum / prec s onOfWholeGraph.get)
    } else None
    qual yW hOutgo ngVolu s.copy(
       ncom ngFollowEdges =  ncom ngVolu s. ncom ngFollowEdges,
       ncom ngFavEdges =  ncom ngVolu s. ncom ngFavEdges,
      favWtSumOf ncom ngFollowEdges =  ncom ngVolu s.favWtSumOf ncom ngFollowEdges,
      favWtSumOf ncom ngFavEdges =  ncom ngVolu s.favWtSumOf ncom ngFavEdges,
      knownForS ze = So (knownFor.s ze),
      correlat onOfFavWt fFollowW hPred ctedFollow = correlat onOfFavWt fFollow,
      correlat onOfFavWt fFavW hPred ctedFav = correlat onOfFavWt fFav,
      sampledEdges = newSampledEdges,
      relat vePrec s onUs ngFavWt fFav = relat vePrec s on,
      averagePrec s onOfWholeGraphUs ngFavWt fFav = prec s onOfWholeGraph
    )
  }
}

object DumpBpQual y extends Tw terExecut onApp {
  def job: Execut on[Un ] = Execut on.getConf gMode.flatMap {
    case (conf g, mode) =>
      Execut on.w h d {  mpl c  un que d =>
        val args = conf g.getArgs
        val  nputD r = args(" nputD r")

        val clusters = args.l st("clusters").map(_.to nt).toSet
        val  nput =
          TypedP pe
            .from(AdhocKeyValS ces.b part eQual yS ce( nputD r))
            .map {
              case ((modelVers on, cluster d), qual y) =>
                (
                  (modelVers on, cluster d),
                  B part eClusterEvaluat onClasses
                    .pr ntableB part eQual y(qual y))
            }

         f (clusters. sEmpty) {
           nput.pr ntSummary("B part e qual y")
        } else {
           nput
            .collect {
              case rec @ ((_, cluster d), qual y)  f clusters(cluster d) =>
                Ut l.prettyJsonMapper
                  .wr eValueAsStr ng(rec)
                  .replaceAll("\n", " ")
            }
            .to erableExecut on
            .map { str ngs => pr ntln(str ngs.mkStr ng("\n")) }
        }
      }
  }
}
