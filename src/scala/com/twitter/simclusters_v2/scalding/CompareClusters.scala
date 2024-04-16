package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.scald ng.{DateOps, DateParser, Execut on, Stat, TypedP pe, TypedTsv, Un que D}
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.s mclusters_v2.common.{Cluster d, User d}
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l.D str but on

object CompareClusters {
  def norm(a:  erable[Float]): Float = {
    math
      .sqrt(a.map { x => x * x }.sum).toFloat
  }

  def cos ne(a: Map[Long, Float], b: Map[Long, Float]): Float = {
    val  ntersect = a.toL st.collect {
      case ( d, score)  f b.conta ns( d) =>
        score * b( d)
    }
    val dot =  f ( ntersect.nonEmpty)  ntersect.sum else 0
    val aNorm = norm(a.values)
    val bNorm = norm(b.values)
     f (aNorm > 0 && bNorm > 0) {
      dot / aNorm / bNorm
    } else 0
  }

  /**
   * Compare two known-for data set, and generate change  n cluster ass gn nt stats
   */
  def compareClusterAss gn nts(
    newKnownFor: TypedP pe[(User d, L st[(Cluster d, Float)])],
    oldKnownFor: TypedP pe[(User d, L st[(Cluster d, Float)])]
  )(
     mpl c  un que D: Un que D
  ): Execut on[Str ng] = {

    val emptyToSo th ng = Stat("no_ass gn nt_to_so ")
    val so th ngToEmpty = Stat("so _ass gn nt_to_none")
    val emptyToEmpty = Stat("empty_to_empty")
    val sa Cluster = Stat("sa _cluster")
    val d ffCluster = Stat("d ff_cluster")

    val calculateStatExec = newKnownFor
      .outerJo n(oldKnownFor)
      .map {
        case (user d, (newKnownForL stOpt, oldKnownForL stOpt)) =>
          val newKnownFor = newKnownForL stOpt.getOrElse(N l)
          val oldKnownFor = oldKnownForL stOpt.getOrElse(N l)

           f (newKnownFor.nonEmpty && oldKnownFor. sEmpty) {
            emptyToSo th ng. nc()
          }
           f (newKnownFor. sEmpty && oldKnownFor.nonEmpty) {
            so th ngToEmpty. nc()
          }
           f (newKnownFor. sEmpty && oldKnownFor. sEmpty) {
            emptyToEmpty. nc()
          }

           f (newKnownFor.nonEmpty && oldKnownFor.nonEmpty) {
            val newCluster d = newKnownFor. ad._1
            val oldCluster d = oldKnownFor. ad._1

             f (newCluster d == oldCluster d) {
              sa Cluster. nc()
            } else {
              d ffCluster. nc()
            }
          }
          user d
      }
      .to erableExecut on

    Ut l.getCustomCountersStr ng(calculateStatExec)
  }

  /**
   * Compare two cluster ass gn nts  n terms of cos ne s m lar y of correspond ng clusters.
   * Excludes clusters wh ch are too small
   * @param knownForA
   * @param knownForB
   * @param m nS zeOfB ggerCluster Set to 10 or so  such.
   * @return
   */
  def compare(
    knownForA: TypedP pe[( nt, L st[(Long, Float)])],
    knownForB: TypedP pe[( nt, L st[(Long, Float)])],
    m nS zeOfB ggerCluster:  nt
  ): TypedP pe[( nt, Float)] = {
    knownForA
      .outerJo n(knownForB)
      .collect {
        case (cluster d, ( mbers nAOpt,  mbers nBOpt))
             f  mbers nAOpt.ex sts(_.s ze >= m nS zeOfB ggerCluster) ||  mbers nBOpt
              .ex sts(_.s ze >= m nS zeOfB ggerCluster) =>
          val  mbers nA =
             mbers nAOpt.map(_.toMap).getOrElse(Map.empty[Long, Float])
          val  mbers nB =
             mbers nBOpt.map(_.toMap).getOrElse(Map.empty[Long, Float])
          (cluster d, cos ne( mbers nA,  mbers nB))
      }
  }

  def summar ze(clusterToCos nes: TypedP pe[( nt, Float)]): Execut on[Opt on[D str but on]] = {
    clusterToCos nes.values.map(x => L st(x)).sum.toOpt onExecut on.map { l stOpt =>
      l stOpt.map { l st => Ut l.d str but onFromArray(l st.map(_.toDouble).toArray) }
    }
  }
}

object CompareClustersAdhoc extends Tw terExecut onApp {
   mpl c  val tz: java.ut l.T  Zone = DateOps.UTC
   mpl c  val dp = DateParser.default

  def job: Execut on[Un ] =
    Execut on.getConf gMode.flatMap {
      case (conf g, mode) =>
        Execut on.w h d {  mpl c  un que d =>
          val args = conf g.getArgs

          val knownForA = KnownForS ces.transpose(KnownForS ces.readKnownFor(args("knownForA")))
          val knownForB = KnownForS ces.transpose(KnownForS ces.readKnownFor(args("knownForB")))

          CompareClusters
            .compare(knownForA, knownForB, m nS zeOfB ggerCluster = 10)
            .map { case (c d, cos) => "%d\t%.2f".format(c d, cos) }
            .wr eExecut on(TypedTsv(args("outputD r")))
        }
    }
}
