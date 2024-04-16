package com.tw ter.fr gate.pushserv ce.rank

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.base.T etAuthor
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.storehaus.FutureOps
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

class Subscr pt onCreatorRanker(
  superFollowEl g b l yUserStore: ReadableStore[Long, Boolean],
  statsRece ver: StatsRece ver) {

  pr vate val scopedStats = statsRece ver.scope("Subscr pt onCreatorRanker")
  pr vate val boostStats = scopedStats.scope("boostSubscr pt onCreator")
  pr vate val softUprankStats = scopedStats.scope("boostByScoreFactor")
  pr vate val boostTotalCand dates = boostStats.stat("total_ nput_cand dates")
  pr vate val softRankTotalCand dates = softUprankStats.stat("total_ nput_cand dates")
  pr vate val softRankNumCand datesCreators = softUprankStats.counter("cand dates_from_creators")
  pr vate val softRankNumCand datesNonCreators =
    softUprankStats.counter("cand dates_not_from_creators")
  pr vate val boostNumCand datesCreators = boostStats.counter("cand dates_from_creators")
  pr vate val boostNumCand datesNonCreators =
    boostStats.counter("cand dates_not_from_creators")

  def boostSubscr pt onCreator(
     nputCand datesFut: Future[Seq[Cand dateDeta ls[PushCand date]]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {

     nputCand datesFut.flatMap {  nputCand dates =>
      boostTotalCand dates.add( nputCand dates.s ze)
      val t etAuthor ds =  nputCand dates.flatMap {
        case Cand dateDeta ls(cand date: T etCand date w h T etAuthor, s) =>
          cand date.author d
        case _ => None
      }.toSet

      FutureOps
        .mapCollect(superFollowEl g b l yUserStore.mult Get(t etAuthor ds))
        .map { creatorAuthorMap =>
          val (upRankedCand dates, ot rCand dates) =  nputCand dates.part  on {
            case Cand dateDeta ls(cand date: T etCand date w h T etAuthor, s) =>
              cand date.author d match {
                case So (author d) =>
                  creatorAuthorMap(author d).getOrElse(false)
                case _ => false
              }
            case _ => false
          }
          boostNumCand datesCreators. ncr(upRankedCand dates.s ze)
          boostNumCand datesNonCreators. ncr(ot rCand dates.s ze)
          upRankedCand dates ++ ot rCand dates
        }
    }
  }

  def boostByScoreFactor(
     nputCand datesFut: Future[Seq[Cand dateDeta ls[PushCand date]]],
    factor: Double = 1.0,
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {

     nputCand datesFut.flatMap {  nputCand dates =>
      softRankTotalCand dates.add( nputCand dates.s ze)
      val t etAuthor ds =  nputCand dates.flatMap {
        case Cand dateDeta ls(cand date: T etCand date w h T etAuthor, s) =>
          cand date.author d
        case _ => None
      }.toSet

      FutureOps
        .mapCollect(superFollowEl g b l yUserStore.mult Get(t etAuthor ds))
        .flatMap { creatorAuthorMap =>
          val (upRankedCand dates, ot rCand dates) =  nputCand dates.part  on {
            case Cand dateDeta ls(cand date: T etCand date w h T etAuthor, s) =>
              cand date.author d match {
                case So (author d) =>
                  creatorAuthorMap(author d).getOrElse(false)
                case _ => false
              }
            case _ => false
          }
          softRankNumCand datesCreators. ncr(upRankedCand dates.s ze)
          softRankNumCand datesNonCreators. ncr(ot rCand dates.s ze)

          ModelBasedRanker.rankBySpec f edScore(
             nputCand dates,
            cand date => {
              val  sFromCreator = cand date match {
                case cand date: T etCand date w h T etAuthor =>
                  cand date.author d match {
                    case So (author d) =>
                      creatorAuthorMap(author d).getOrElse(false)
                    case _ => false
                  }
                case _ => false
              }
              cand date.mr  ghtedOpenOrNtabCl ckRank ngProbab l y.map {
                case So (score) =>
                   f ( sFromCreator) So (score * factor)
                  else So (score)
                case _ => None
              }
            }
          )
        }
    }
  }
}
