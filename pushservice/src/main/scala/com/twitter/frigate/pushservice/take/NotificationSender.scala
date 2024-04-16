package com.tw ter.fr gate.pushserv ce.take

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Stats.track
 mport com.tw ter.fr gate.common.store. b sResponse
 mport com.tw ter.fr gate.common.store.Sent
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.take.sender. b s2Sender
 mport com.tw ter.fr gate.pushserv ce.take.sender.NtabSender
 mport com.tw ter.fr gate.scr be.thr ftscala.Not f cat onScr be
 mport com.tw ter.ut l.Future
 mport com.tw ter.fr gate.thr ftscala.ChannelNa 

/**
 * Not f cat onSender wraps up all t  not f cat on  nfra send log c, and serves as an abstract layer
 * bet en Cand dateNot f er and t  respect ve senders  nclud ng ntab,  b s, wh ch  s be ng
 * gated w h both a dec der/feature sw ch
 */
class Not f cat onSender(
   b s2Sender:  b s2Sender,
  ntabSender: NtabSender,
  statsRece ver: StatsRece ver,
  not f cat onScr be: Not f cat onScr be => Un ) {

  pr vate val not f cat onNot f erStats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val  b s2SendLatency = not f cat onNot f erStats.scope(" b s2_send")
  pr vate val loggedOut b s2SendLatency = not f cat onNot f erStats.scope("logged_out_ b s2_send")
  pr vate val ntabSendLatency = not f cat onNot f erStats.scope("ntab_send")

  pr vate val ntabWr eT nSk pPushCounter =
    not f cat onNot f erStats.counter("ntab_wr e_t n_sk p_push")
  pr vate val ntabWr eT n b sSendCounter =
    not f cat onNot f erStats.counter("ntab_wr e_t n_ b s_send")
  not f cat onNot f erStats.counter(" ns_dark_traff c_send")

  pr vate val ntabOnlyChannelSenderV3Counter =
    not f cat onNot f erStats.counter("ntab_only_channel_send_v3")

  def send b sDarkWr e(cand date: PushCand date): Future[ b sResponse] = {
     b s2Sender.sendAsDarkWr e(cand date)
  }

  pr vate def  sNtabOnlySend(
    channels: Seq[ChannelNa ]
  ): Future[Boolean] = {
    val  sNtabOnlyChannel = channels.conta ns(ChannelNa .NtabOnly)
     f ( sNtabOnlyChannel) ntabOnlyChannelSenderV3Counter. ncr()

    Future.value( sNtabOnlyChannel)
  }

  pr vate def  sPushOnly(channels: Seq[ChannelNa ], cand date: PushCand date): Future[Boolean] = {
    Future.value(channels.conta ns(ChannelNa .PushOnly))
  }

  def not fy(
    channels: Seq[ChannelNa ],
    cand date: PushCand date
  ): Future[ b sResponse] = {
    Future
      .jo n( sPushOnly(channels, cand date),  sNtabOnlySend(channels)).map {
        case ( sPushOnly,  sNtabOnly) =>
           f ( sPushOnly) {
            track( b s2SendLatency)( b s2Sender.send(channels, cand date, not f cat onScr be, None))
          } else {
            track(ntabSendLatency)(
              ntabSender
                .send(cand date,  sNtabOnly))
              .flatMap { ntabResponse =>
                 f ( sNtabOnly) {
                  ntabWr eT nSk pPushCounter. ncr()
                  cand date
                    .scr beData(channels = channels).foreach(not f cat onScr be).map(_ =>
                       b sResponse(Sent))
                } else {
                  ntabWr eT n b sSendCounter. ncr()
                  track( b s2SendLatency)(
                     b s2Sender.send(channels, cand date, not f cat onScr be, ntabResponse))
                }
              }

          }
      }.flatten
  }

  def loggedOutNot fy(
    cand date: PushCand date
  ): Future[ b sResponse] = {
    val  b sResponse = {
      track(loggedOut b s2SendLatency)(
         b s2Sender.send(Seq(ChannelNa .PushNtab), cand date, not f cat onScr be, None))
    }
     b sResponse
  }
}
