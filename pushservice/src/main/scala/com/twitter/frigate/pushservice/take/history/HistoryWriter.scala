package com.tw ter.fr gate.pushserv ce.take. tory

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common. tory. toryStoreKeyContext
 mport com.tw ter.fr gate.common. tory.PushServ ce toryStore
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.convers ons.Durat onOps._

class  toryWr er( toryStore: PushServ ce toryStore, stats: StatsRece ver) {
  pr vate lazy val  toryWr erStats = stats.scope(t .getClass.getS mpleNa )
  pr vate lazy val  toryWr eCounter =  toryWr erStats.counter(" tory_wr e_num")
  pr vate lazy val loggedOut toryWr eCounter =
     toryWr erStats.counter("logged_out_ tory_wr e_num")

  pr vate def wr eTtlFor tory(cand date: PushCand date): Durat on = {
     f (cand date.target. sLoggedOutUser) {
      60.days
    } else  f (RecTypes. sT etType(cand date.commonRecType)) {
      cand date.target.params(PushFeatureSw chParams.Fr gate toryT etNot f cat onWr eTtl)
    } else cand date.target.params(PushFeatureSw chParams.Fr gate toryOt rNot f cat onWr eTtl)
  }

  def wr eSendTo tory(
    cand date: PushCand date,
    fr gateNot f cat onForPers stence: Fr gateNot f cat on
  ): Future[Un ] = {
    val  toryStoreKeyContext =  toryStoreKeyContext(
      cand date.target.target d,
      cand date.target.pushContext.flatMap(_.use mcac For tory).getOrElse(false)
    )
     f (cand date.target. sLoggedOutUser) {
      loggedOut toryWr eCounter. ncr()
    } else {
       toryWr eCounter. ncr()
    }
     toryStore
      .put(
         toryStoreKeyContext,
        cand date.createdAt,
        fr gateNot f cat onForPers stence,
        So (wr eTtlFor tory(cand date))
      )
  }
}
