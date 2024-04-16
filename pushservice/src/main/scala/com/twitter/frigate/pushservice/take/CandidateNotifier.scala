package com.tw ter.fr gate.pushserv ce.take

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Stats.track
 mport com.tw ter.fr gate.common.logger.MRLogger
 mport com.tw ter.fr gate.common.store.Fa l
 mport com.tw ter.fr gate.common.store. b sResponse
 mport com.tw ter.fr gate.common.store. nval dConf gurat on
 mport com.tw ter.fr gate.common.store.NoRequest
 mport com.tw ter.fr gate.common.store.Sent
 mport com.tw ter.fr gate.common.ut l.CasLock
 mport com.tw ter.fr gate.common.ut l.PushServ ceUt l. nval dConf gResponse
 mport com.tw ter.fr gate.common.ut l.PushServ ceUt l.NtabWr eOnlyResponse
 mport com.tw ter.fr gate.common.ut l.PushServ ceUt l.SendFa ledResponse
 mport com.tw ter.fr gate.common.ut l.PushServ ceUt l.SentResponse
 mport com.tw ter.fr gate.pushserv ce.pred cate.CasLockPred cate
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.take. tory._
 mport com.tw ter.fr gate.pushserv ce.ut l.CopyUt l
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushResponse
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushStatus
 mport com.tw ter.fr gate.pushserv ce.ut l.Overr deNot f cat onUt l
 mport com.tw ter.fr gate.thr ftscala.ChannelNa 
 mport com.tw ter.ut l.Future

class Cand dateNot f er(
  not f cat onSender: Not f cat onSender,
  casLock: CasLock,
   toryWr er:  toryWr er,
  eventBusWr er: EventBusWr er,
  ntabOnlyChannelSelector: NtabOnlyChannelSelector
)(
   mpl c  statsRece ver: StatsRece ver) {

  pr vate lazy val casLockPred cate =
    CasLockPred cate(casLock, exp ryDurat on = 10.m nutes)(statsRece ver)
  pr vate val cand dateNot f erStats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val  toryWr eCounter =
    cand dateNot f erStats.counter("s mply_not f er_ tory_wr e_num")
  pr vate val loggedOut toryWr eCounter =
    cand dateNot f erStats.counter("logged_out_s mply_not f er_ tory_wr e_num")
  pr vate val not f cat onSenderLatency =
    cand dateNot f erStats.scope("not f cat on_sender_send")
  pr vate val log = MRLogger("Cand dateNot f er")

  pr vate def map b sResponse( b sResponse:  b sResponse): PushResponse = {
     b sResponse match {
      case  b sResponse(Sent, _) => SentResponse
      case  b sResponse(Fa l, _) => SendFa ledResponse
      case  b sResponse( nval dConf gurat on, _) =>  nval dConf gResponse
      case  b sResponse(NoRequest, _) => NtabWr eOnlyResponse
    }
  }

  /**
   * - wr e to  tory store
   * - send t  not f cat on
   * - scr be t  not f cat on
   *
   * f nal mod f er  s to s gnal that t  funct on cannot be overr den. T re's so  cr  cal log c
   *  n t  funct on, and  's  lpful to know that no sub-class overr des  .
   */
  f nal def not fy(
    cand date: PushCand date,
  ): Future[PushResponse] = {
     f (cand date.target. sDarkWr e) {
      not f cat onSender.send b sDarkWr e(cand date).map(map b sResponse)
    } else {
      casLockPred cate(Seq(cand date)).flatMap { casLockResults =>
         f (casLockResults. ad || cand date.target.pushContext
            .ex sts(_.sk pF lters.conta ns(true))) {
          Future
            .jo n(
              cand date.target. sS lentPush,
              Overr deNot f cat onUt l
                .getOverr de nfo(cand date, cand dateNot f erStats),
              CopyUt l.getCopyFeatures(cand date, cand dateNot f erStats)
            ).flatMap {
              case ( sS lentPush, overr de nfoOpt, copyFeaturesMap) =>
                val channels = ntabOnlyChannelSelector.selectChannel(cand date)
                channels.flatMap { channels =>
                  cand date
                    .fr gateNot f cat onForPers stence(
                      channels,
                       sS lentPush,
                      overr de nfoOpt,
                      copyFeaturesMap.keySet).flatMap { fr gateNot f cat onForPers stence =>
                      val result =  f (cand date.target. sDarkWr e) {
                        cand dateNot f erStats.counter("dark_wr e"). ncr()
                        Future.Un 
                      } else {
                         toryWr eCounter. ncr()
                         toryWr er
                          .wr eSendTo tory(cand date, fr gateNot f cat onForPers stence)
                      }
                      result.flatMap { _ =>
                        track(not f cat onSenderLatency)(
                          not f cat onSender
                            .not fy(channels, cand date)
                            .map {  b sResponse =>
                              eventBusWr er
                                .wr eToEventBus(cand date, fr gateNot f cat onForPers stence)
                              map b sResponse( b sResponse)
                            })
                      }
                    }
                }
            }
        } else {
          cand dateNot f erStats.counter("f ltered_by_cas_lock"). ncr()
          Future.value(PushResponse(PushStatus.F ltered, So (casLockPred cate.na )))
        }
      }
    }
  }

  f nal def loggedOutNot fy(
    cand date: PushCand date,
  ): Future[PushResponse] = {
     f (cand date.target. sDarkWr e) {
      not f cat onSender.send b sDarkWr e(cand date).map(map b sResponse)
    } else {
      casLockPred cate(Seq(cand date)).flatMap { casLockResults =>
         f (casLockResults. ad || cand date.target.pushContext
            .ex sts(_.sk pF lters.conta ns(true))) {
          val response = cand date.target. sS lentPush.flatMap {  sS lentPush =>
            cand date
              .fr gateNot f cat onForPers stence(
                Seq(ChannelNa .PushNtab),
                 sS lentPush,
                None,
                Set.empty).flatMap { fr gateNot f cat onForPers stence =>
                val result =  f (cand date.target. sDarkWr e) {
                  cand dateNot f erStats.counter("logged_out_dark_wr e"). ncr()
                  Future.Un 
                } else {
                  loggedOut toryWr eCounter. ncr()
                   toryWr er.wr eSendTo tory(cand date, fr gateNot f cat onForPers stence)
                }

                result.flatMap { _ =>
                  track(not f cat onSenderLatency)(
                    not f cat onSender
                      .loggedOutNot fy(cand date)
                      .map {  b sResponse =>
                        map b sResponse( b sResponse)
                      })
                }
              }
          }
          response
        } else {
          cand dateNot f erStats.counter("f ltered_by_cas_lock"). ncr()
          Future.value(PushResponse(PushStatus.F ltered, So (casLockPred cate.na )))
        }
      }
    }
  }
}
