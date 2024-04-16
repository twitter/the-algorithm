package com.tw ter.representat onscorer.scorestore

 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.Score nternal d.Gener cPa rScore d
 mport com.tw ter.s mclusters_v2.thr ftscala.Scor ngAlgor hm.CertoNormal zedDotProductScore
 mport com.tw ter.s mclusters_v2.thr ftscala.Scor ngAlgor hm.CertoNormal zedCos neScore
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.s mclusters_v2.thr ftscala.{Score => Thr ftScore}
 mport com.tw ter.s mclusters_v2.thr ftscala.{Score d => Thr ftScore d}
 mport com.tw ter.storehaus.FutureOps
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.top c_recos.thr ftscala.Scores
 mport com.tw ter.top c_recos.thr ftscala.Top cToScores
 mport com.tw ter.ut l.Future

/**
 * Score store to get Certo <top c, t et> scores.
 * Currently, t  store supports two Scor ng Algor hms ( .e., two types of Certo scores):
 * 1. Normal zedDotProduct
 * 2. Normal zedCos ne
 * Query ng w h correspond ng scor ng algor hms results  n d fferent Certo scores.
 */
case class Top cT etCertoScoreStore(certoStratoStore: ReadableStore[T et d, Top cToScores])
    extends ReadableStore[Thr ftScore d, Thr ftScore] {

  overr de def mult Get[K1 <: Thr ftScore d](ks: Set[K1]): Map[K1, Future[Opt on[Thr ftScore]]] = {
    val t et ds =
      ks.map(_. nternal d).collect {
        case Gener cPa rScore d(score d) =>
          ((score d. d1, score d. d2): @annotat on.nowarn(
            "msg=may not be exhaust ve|max recurs on depth")) match {
            case ( nternal d.T et d(t et d), _) => t et d
            case (_,  nternal d.T et d(t et d)) => t et d
          }
      }

    val result = for {
      certoScores <- Future.collect(certoStratoStore.mult Get(t et ds))
    } y eld {
      ks.map { k =>
        (k.algor hm, k. nternal d) match {
          case (CertoNormal zedDotProductScore, Gener cPa rScore d(score d)) =>
            (score d. d1, score d. d2) match {
              case ( nternal d.T et d(t et d),  nternal d.Top c d(top c d)) =>
                (
                  k,
                  extractScore(
                    t et d,
                    top c d,
                    certoScores,
                    _.follo rL2Normal zedDotProduct8HrHalfL fe))
              case ( nternal d.Top c d(top c d),  nternal d.T et d(t et d)) =>
                (
                  k,
                  extractScore(
                    t et d,
                    top c d,
                    certoScores,
                    _.follo rL2Normal zedDotProduct8HrHalfL fe))
              case _ => (k, None)
            }
          case (CertoNormal zedCos neScore, Gener cPa rScore d(score d)) =>
            (score d. d1, score d. d2) match {
              case ( nternal d.T et d(t et d),  nternal d.Top c d(top c d)) =>
                (
                  k,
                  extractScore(
                    t et d,
                    top c d,
                    certoScores,
                    _.follo rL2Normal zedCos neS m lar y8HrHalfL fe))
              case ( nternal d.Top c d(top c d),  nternal d.T et d(t et d)) =>
                (
                  k,
                  extractScore(
                    t et d,
                    top c d,
                    certoScores,
                    _.follo rL2Normal zedCos neS m lar y8HrHalfL fe))
              case _ => (k, None)
            }
          case _ => (k, None)
        }
      }.toMap
    }
    FutureOps.l ftValues(ks, result)
  }

  /**
   * G ven t etToCertoScores, extract certa n Certo score bet en t  g ven t et d and top c d.
   * T  Certo score of  nterest  s spec f ed us ng scoreExtractor.
   */
  def extractScore(
    t et d: T et d,
    top c d: Top c d,
    t etToCertoScores: Map[T et d, Opt on[Top cToScores]],
    scoreExtractor: Scores => Double
  ): Opt on[Thr ftScore] = {
    t etToCertoScores.get(t et d).flatMap {
      case So (top cToScores) =>
        top cToScores.top cToScores.flatMap(_.get(top c d).map(scoreExtractor).map(Thr ftScore(_)))
      case _ => So (Thr ftScore(0.0))
    }
  }
}
