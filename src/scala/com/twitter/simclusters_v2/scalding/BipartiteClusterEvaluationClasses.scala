package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.algeb rd.{Mono d, Opt onMono d, Sem group}
 mport com.tw ter.algeb rd.mutable.Pr or yQueueMono d
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l.D str but on
 mport com.tw ter.s mclusters_v2.thr ftscala.{B part eClusterQual y, SampledEdge}
 mport java.ut l.Pr or yQueue
 mport scala.collect on.JavaConverters._

object B part eClusterEvaluat onClasses {
  case class   ghts(
     sFollowEdge: Double,
     sFavEdge: Double,
    favWt fFollowEdge: Double,
    favWt fFavEdge: Double)

  object   ghtsMono d extends Mono d[  ghts] {
    overr de def zero =   ghts(0.0, 0.0, 0.0, 0.0)

    overr de def plus(l:   ghts, r:   ghts):   ghts = {
        ghts(
        l. sFollowEdge + r. sFollowEdge,
        l. sFavEdge + r. sFavEdge,
        l.favWt fFollowEdge + r.favWt fFollowEdge,
        l.favWt fFavEdge + r.favWt fFavEdge
      )
    }
  }

   mpl c  val wm: Mono d[  ghts] =   ghtsMono d

  case class SampledEdgeData(
    favWt fFollowEdge: Double,
    favWt fFavEdge: Double,
    followScoreToCluster: Double,
    favScoreToCluster: Double)

   mpl c  val samplerMono d: Pr or yQueueMono d[((Long, Long), SampledEdgeData)] =
    Ut l.reservo rSamplerMono dForPa rs[(Long, Long), SampledEdgeData](2000)(Ut l.edgeOrder ng)

   mpl c  val sampledEdgesMono d: Pr or yQueueMono d[SampledEdge] =
    Ut l.reservo rSamplerMono d(
      10000,
      { sampledEdge: SampledEdge => (sampledEdge.follo r d, sampledEdge.follo e d) }
    )(Ut l.edgeOrder ng)

  case class B part e nter d ateResults(
     nCluster  ghts:   ghts,
    totalOutgo ngVolu s:   ghts,
     nterested nS ze:  nt,
    edgeSample: Pr or yQueue[((Long, Long), SampledEdgeData)]) {
    overr de def toStr ng: Str ng = {
      "BCR(%s, %s, %d, %s)".format(
         nCluster  ghts,
        totalOutgo ngVolu s,
         nterested nS ze,
        edgeSample. erator().asScala.toSeq.toStr ng()
      )
    }
  }

  object B RMono d extends Mono d[B part e nter d ateResults] {
    overr de def zero =
      B part e nter d ateResults(  ghtsMono d.zero,   ghtsMono d.zero, 0, samplerMono d.zero)

    overr de def plus(
      l: B part e nter d ateResults,
      r: B part e nter d ateResults
    ): B part e nter d ateResults = {
      B part e nter d ateResults(
          ghtsMono d.plus(l. nCluster  ghts, r. nCluster  ghts),
          ghtsMono d.plus(l.totalOutgo ngVolu s, r.totalOutgo ngVolu s),
        l. nterested nS ze + r. nterested nS ze,
        samplerMono d.plus(l.edgeSample, r.edgeSample)
      )
    }
  }

   mpl c  val b RMono d: Mono d[B part e nter d ateResults] = B RMono d

  def makeThr ftSampledEdge(edge: (Long, Long), data: SampledEdgeData): SampledEdge = {
    val (follo r d, follo e d) = edge
    SampledEdge(
      follo r d = follo r d,
      follo e d = follo e d,
      favWt fFollowEdge = So (data.favWt fFollowEdge),
      favWt fFavEdge = So (data.favWt fFavEdge),
      followScoreToCluster = So (data.followScoreToCluster),
      favScoreToCluster = So (data.favScoreToCluster)
    )
  }

  object ClusterQual ySem group extends Sem group[B part eClusterQual y] {
    val doubleOM: Mono d[Opt on[Double]] = new Opt onMono d[Double]
    val  ntOM: Mono d[Opt on[ nt]] = new Opt onMono d[ nt]
    val longOM: Mono d[Opt on[Long]] = new Opt onMono d[Long]

    overr de def plus(l: B part eClusterQual y, r: B part eClusterQual y) =
      B part eClusterQual y(
         nClusterFollowEdges = doubleOM.plus(l. nClusterFollowEdges, r. nClusterFollowEdges),
         nClusterFavEdges = doubleOM.plus(l. nClusterFavEdges, r. nClusterFavEdges),
        favWtSumOf nClusterFollowEdges = doubleOM
          .plus(l.favWtSumOf nClusterFollowEdges, r.favWtSumOf nClusterFollowEdges),
        favWtSumOf nClusterFavEdges = doubleOM
          .plus(l.favWtSumOf nClusterFavEdges, r.favWtSumOf nClusterFavEdges),
        outgo ngFollowEdges = doubleOM.plus(l.outgo ngFollowEdges, r.outgo ngFollowEdges),
        outgo ngFavEdges = doubleOM.plus(l.outgo ngFavEdges, r.outgo ngFavEdges),
        favWtSumOfOutgo ngFollowEdges = doubleOM
          .plus(l.favWtSumOfOutgo ngFollowEdges, r.favWtSumOfOutgo ngFollowEdges),
        favWtSumOfOutgo ngFavEdges = doubleOM
          .plus(l.favWtSumOfOutgo ngFavEdges, r.favWtSumOfOutgo ngFavEdges),
         ncom ngFollowEdges = doubleOM.plus(l. ncom ngFollowEdges, r. ncom ngFollowEdges),
         ncom ngFavEdges = doubleOM.plus(l. ncom ngFavEdges, r. ncom ngFavEdges),
        favWtSumOf ncom ngFollowEdges = doubleOM
          .plus(l.favWtSumOf ncom ngFollowEdges, r.favWtSumOf ncom ngFollowEdges),
        favWtSumOf ncom ngFavEdges = doubleOM
          .plus(l.favWtSumOf ncom ngFavEdges, r.favWtSumOf ncom ngFavEdges),
         nterested nS ze = None,
        sampledEdges = So (
          sampledEdgesMono d
            .plus(
              sampledEdgesMono d.bu ld(l.sampledEdges.getOrElse(N l)),
              sampledEdgesMono d.bu ld(r.sampledEdges.getOrElse(N l))
            )
            . erator()
            .asScala
            .toSeq),
        knownForS ze =  ntOM.plus(l.knownForS ze, r.knownForS ze),
        correlat onOfFavWt fFollowW hPred ctedFollow = None,
        correlat onOfFavWt fFavW hPred ctedFav = None,
        relat vePrec s onUs ngFavWt fFav = None,
        averagePrec s onOfWholeGraphUs ngFavWt fFav = l.averagePrec s onOfWholeGraphUs ngFavWt fFav
      )
  }

   mpl c  val bcqSem group: Sem group[B part eClusterQual y] =
    ClusterQual ySem group

  case class Pr ntableB part eQual y(
     ncom ngFollowUn  ghtedRecall: Str ng,
     ncom ngFavUn  ghtedRecall: Str ng,
     ncom ngFollow  ghtedRecall: Str ng,
     ncom ngFav  ghtedRecall: Str ng,
    outgo ngFollowUn  ghtedRecall: Str ng,
    outgo ngFavUn  ghtedRecall: Str ng,
    outgo ngFollow  ghtedRecall: Str ng,
    outgo ngFav  ghtedRecall: Str ng,
     ncom ngFollowEdges: Str ng,
     ncom ngFavEdges: Str ng,
    favWtSumOf ncom ngFollowEdges: Str ng,
    favWtSumOf ncom ngFavEdges: Str ng,
    outgo ngFollowEdges: Str ng,
    outgo ngFavEdges: Str ng,
    favWtSumOfOutgo ngFollowEdges: Str ng,
    favWtSumOfOutgo ngFavEdges: Str ng,
    correlat onOfFavWt fFollow: Str ng,
    correlat onOfFavWt fFav: Str ng,
    relat vePrec s onUs ngFavWt: Str ng,
    averagePrec s onOfWholeGraphUs ngFavWt: Str ng,
     nterested nS ze: Str ng,
    knownForS ze: Str ng)

  def pr ntableB part eQual y( n: B part eClusterQual y): Pr ntableB part eQual y = {
    def getRat o(numOpt: Opt on[Double], denOpt: Opt on[Double]): Str ng = {
      val r =  f (denOpt.ex sts(_ > 0)) {
        numOpt.getOrElse(0.0) / denOpt.get
      } else 0.0
      "%.3f".format(r)
    }

    val formatter = new java.text.Dec malFormat("###,###.#")

    def denStr ng(denOpt: Opt on[Double]): Str ng =
      formatter.format(denOpt.getOrElse(0.0))

    val correlat onOfFavWt fFollow =
       n.correlat onOfFavWt fFollowW hPred ctedFollow match {
        case None =>
           n.sampledEdges.map { samples =>
            val pa rs = samples.map { s =>
              (s.pred ctedFollowScore.getOrElse(0.0), s.favWt fFollowEdge.getOrElse(0.0))
            }
            Ut l.computeCorrelat on(pa rs. erator)
          }
        case x @ _ => x
      }

    val correlat onOfFavWt fFav =
       n.correlat onOfFavWt fFavW hPred ctedFav match {
        case None =>
           n.sampledEdges.map { samples =>
            val pa rs = samples.map { s =>
              (s.pred ctedFavScore.getOrElse(0.0), s.favWt fFavEdge.getOrElse(0.0))
            }
            Ut l.computeCorrelat on(pa rs. erator)
          }
        case x @ _ => x
      }

    Pr ntableB part eQual y(
       ncom ngFollowUn  ghtedRecall = getRat o( n. nClusterFollowEdges,  n. ncom ngFollowEdges),
       ncom ngFavUn  ghtedRecall = getRat o( n. nClusterFavEdges,  n. ncom ngFavEdges),
       ncom ngFollow  ghtedRecall =
        getRat o( n.favWtSumOf nClusterFollowEdges,  n.favWtSumOf ncom ngFollowEdges),
       ncom ngFav  ghtedRecall =
        getRat o( n.favWtSumOf nClusterFavEdges,  n.favWtSumOf ncom ngFavEdges),
      outgo ngFollowUn  ghtedRecall = getRat o( n. nClusterFollowEdges,  n.outgo ngFollowEdges),
      outgo ngFavUn  ghtedRecall = getRat o( n. nClusterFavEdges,  n.outgo ngFavEdges),
      outgo ngFollow  ghtedRecall =
        getRat o( n.favWtSumOf nClusterFollowEdges,  n.favWtSumOfOutgo ngFollowEdges),
      outgo ngFav  ghtedRecall =
        getRat o( n.favWtSumOf nClusterFavEdges,  n.favWtSumOfOutgo ngFavEdges),
       ncom ngFollowEdges = denStr ng( n. ncom ngFollowEdges),
       ncom ngFavEdges = denStr ng( n. ncom ngFavEdges),
      favWtSumOf ncom ngFollowEdges = denStr ng( n.favWtSumOf ncom ngFollowEdges),
      favWtSumOf ncom ngFavEdges = denStr ng( n.favWtSumOf ncom ngFavEdges),
      outgo ngFollowEdges = denStr ng( n.outgo ngFollowEdges),
      outgo ngFavEdges = denStr ng( n.outgo ngFavEdges),
      favWtSumOfOutgo ngFollowEdges = denStr ng( n.favWtSumOfOutgo ngFollowEdges),
      favWtSumOfOutgo ngFavEdges = denStr ng( n.favWtSumOfOutgo ngFavEdges),
      correlat onOfFavWt fFollow = "%.3f"
        .format(correlat onOfFavWt fFollow.getOrElse(0.0)),
      correlat onOfFavWt fFav = "%.3f"
        .format(correlat onOfFavWt fFav.getOrElse(0.0)),
      relat vePrec s onUs ngFavWt =
        "%.2g".format( n.relat vePrec s onUs ngFavWt fFav.getOrElse(0.0)),
      averagePrec s onOfWholeGraphUs ngFavWt =
        "%.2g".format( n.averagePrec s onOfWholeGraphUs ngFavWt fFav.getOrElse(0.0)),
       nterested nS ze =  n. nterested nS ze.getOrElse(0).toStr ng,
      knownForS ze =  n.knownForS ze.getOrElse(0).toStr ng
    )
  }

  case class ClusterResultsSummary(
    numClustersW hZero nterested n:  nt,
    numClustersW hZeroFollowWtRecall:  nt,
    numClustersW hZeroFavWtRecall:  nt,
    numClustersW hZeroFollowAndFavWtRecall:  nt,
     nterested nS zeD st: D str but on,
    outgo ngFollowWtRecallD st: D str but on,
    outgo ngFavWtRecallD st: D str but on,
     ncom ngFollowWtRecallD st: D str but on,
     ncom ngFavWtRecallD st: D str but on,
    followCorrelat onD st: D str but on,
    favCorrelat onD st: D str but on,
    relat vePrec s onD st: D str but on)

  def getClusterResultsSummary(
    perClusterResults: TypedP pe[B part eClusterQual y]
  ): Execut on[Opt on[ClusterResultsSummary]] = {
    perClusterResults
      .map { clusterQual y =>
        val pr ntableQual y = pr ntableB part eQual y(clusterQual y)
        val  sFollowRecallZero =
           f (!clusterQual y.favWtSumOf nClusterFollowEdges
              .ex sts(_ > 0)) 1
          else 0
        val  sFavRecallZero =
           f (!clusterQual y.favWtSumOf nClusterFavEdges.ex sts(_ > 0)) 1
          else 0
        (
           f (!clusterQual y. nterested nS ze.ex sts(_ > 0)) 1 else 0,
           sFollowRecallZero,
           sFavRecallZero,
           sFavRecallZero *  sFollowRecallZero,
          clusterQual y. nterested nS ze.toL st.map(_.toDouble),
          L st(pr ntableQual y.outgo ngFollow  ghtedRecall.toDouble),
          L st(pr ntableQual y.outgo ngFav  ghtedRecall.toDouble),
          L st(pr ntableQual y. ncom ngFollow  ghtedRecall.toDouble),
          L st(pr ntableQual y. ncom ngFav  ghtedRecall.toDouble),
          L st(pr ntableQual y.correlat onOfFavWt fFollow.toDouble),
          L st(pr ntableQual y.correlat onOfFavWt fFav.toDouble),
          L st(pr ntableQual y.relat vePrec s onUs ngFavWt.toDouble)
        )
      }
      .sum
      .toOpt onExecut on
      .map { opt =>
        opt.map {
          case (
                zero nterested n,
                zeroFollowRecall,
                zeroFavRecall,
                zeroFollowAndFavRecall,
                 nterested nS zeL st,
                outgo ngFollowWtRecallL st,
                outgo ngFavWtRecallL st,
                 ncom ngFollowWtRecallL st,
                 ncom ngFavWtRecallL st,
                followCorrelat onL st,
                favCorrelat onL st,
                relat vePrec s onL st
              ) =>
            ClusterResultsSummary(
              numClustersW hZero nterested n = zero nterested n,
              numClustersW hZeroFollowWtRecall = zeroFollowRecall,
              numClustersW hZeroFavWtRecall = zeroFavRecall,
              numClustersW hZeroFollowAndFavWtRecall = zeroFollowAndFavRecall,
               nterested nS zeD st = Ut l.d str but onFromArray( nterested nS zeL st.toArray),
              outgo ngFollowWtRecallD st = Ut l
                .d str but onFromArray(outgo ngFollowWtRecallL st.toArray),
              outgo ngFavWtRecallD st = Ut l.d str but onFromArray(outgo ngFavWtRecallL st.toArray),
               ncom ngFollowWtRecallD st = Ut l
                .d str but onFromArray( ncom ngFollowWtRecallL st.toArray),
               ncom ngFavWtRecallD st = Ut l.d str but onFromArray( ncom ngFavWtRecallL st.toArray),
              followCorrelat onD st = Ut l.d str but onFromArray(followCorrelat onL st.toArray),
              favCorrelat onD st = Ut l.d str but onFromArray(favCorrelat onL st.toArray),
              relat vePrec s onD st = Ut l.d str but onFromArray(relat vePrec s onL st.toArray)
            )
        }
      }
  }
}
