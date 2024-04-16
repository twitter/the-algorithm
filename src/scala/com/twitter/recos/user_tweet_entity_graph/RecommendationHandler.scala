package com.tw ter.recos.user_t et_ent y_graph

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.graphjet.algor hms.Recom ndat onType
 mport com.tw ter.graphjet.algor hms.count ng.t et.T et tadataRecom ndat on nfo
 mport com.tw ter.graphjet.algor hms.count ng.t et.T etRecom ndat on nfo
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala._
 mport com.tw ter.recos.ut l.Stats
 mport com.tw ter.servo.request._
 mport com.tw ter.ut l.Future

/**
 *  mple ntat on of t  Thr ft-def ned serv ce  nterface.
 *
* A wrapper of mag cRecsRunner.
 */
class Recom ndat onHandler(
  t etRecsRunner: T etRecom ndat onsRunner,
  statsRece ver: StatsRece ver)
    extends RequestHandler[Recom ndT etEnt yRequest, Recom ndT etEnt yResponse] {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val soc alProofHydrator = new Soc alProofHydrator(stats)

  overr de def apply(request: Recom ndT etEnt yRequest): Future[Recom ndT etEnt yResponse] = {
    val scopedStats: StatsRece ver = stats.scope(request.d splayLocat on.toStr ng)

    StatsUt l.trackBlockStats(scopedStats) {
      val cand datesFuture = t etRecsRunner.apply(request)

      cand datesFuture.map { cand dates =>
         f (cand dates. sEmpty) scopedStats.counter(Stats.EmptyResult). ncr()
        else scopedStats.counter(Stats.Served). ncr(cand dates.s ze)

        Recom ndT etEnt yResponse(cand dates.flatMap {
          _ match {
            case t etRec: T etRecom ndat on nfo =>
              So (
                UserT etEnt yRecom ndat onUn on.T etRec(
                  T etRecom ndat on(
                    t etRec.getRecom ndat on,
                    t etRec.get  ght,
                    soc alProofHydrator.addT etSoc alProofByType(t etRec),
                    soc alProofHydrator.addT etSoc alProofs(t etRec)
                  )
                )
              )
            case t et tadataRec: T et tadataRecom ndat on nfo =>
               f (t et tadataRec.getRecom ndat onType == Recom ndat onType.HASHTAG) {
                So (
                  UserT etEnt yRecom ndat onUn on.HashtagRec(
                    HashtagRecom ndat on(
                      t et tadataRec.getRecom ndat on,
                      t et tadataRec.get  ght,
                      soc alProofHydrator.add tadataSoc alProofByType(t et tadataRec)
                    )
                  )
                )
              } else  f (t et tadataRec.getRecom ndat onType == Recom ndat onType.URL) {
                So (
                  UserT etEnt yRecom ndat onUn on.UrlRec(
                    UrlRecom ndat on(
                      t et tadataRec.getRecom ndat on,
                      t et tadataRec.get  ght,
                      soc alProofHydrator.add tadataSoc alProofByType(t et tadataRec)
                    )
                  )
                )
              } else {
                None: Opt on[UserT etEnt yRecom ndat onUn on]
              }
            case _ => None
          }
        })
      }
    }
  }
}
