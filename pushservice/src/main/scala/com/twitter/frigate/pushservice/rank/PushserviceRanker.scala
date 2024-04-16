package com.tw ter.fr gate.pushserv ce.rank

 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.base.Ranker
 mport com.tw ter.ut l.Future

tra  Pushserv ceRanker[T, C] extends Ranker[T, C] {

  /**
   *  n  al Rank ng of  nput cand dates
   */
  def  n  alRank(target: T, cand dates: Seq[Cand dateDeta ls[C]]): Future[Seq[Cand dateDeta ls[C]]]

  /**
   * Re-ranks  nput ranked cand dates. Useful w n a subset of cand dates are ranked
   * by a d fferent log c, wh le preserv ng t   n  al rank ng for t  rest
   */
  def reRank(
    target: T,
    rankedCand dates: Seq[Cand dateDeta ls[C]]
  ): Future[Seq[Cand dateDeta ls[C]]]

  /**
   * F nal rank ng that does  n  al + Rerank
   */
  overr de f nal def rank(target: T, cand dates: Seq[Cand dateDeta ls[C]]): (
    Future[Seq[Cand dateDeta ls[C]]]
  ) = {
     n  alRank(target, cand dates).flatMap { rankedCand dates => reRank(target, rankedCand dates) }
  }
}
