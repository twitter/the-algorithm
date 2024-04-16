package com.tw ter.product_m xer.shared_l brary.http_cl ent

 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Durat on

object F nagleHttpCl entBu lder {

  /**
   * Bu ld a F nagle HTTP cl ent w h S2S Auth / Mutual TLS
   *
   * @param requestT  out     HTTP cl ent request t  out
   * @param connectT  out     HTTP cl ent transport connect t  out
   * @param acqu s  onT  out HTTP cl ent sess on acqu s  on t  out
   * @param serv ce dent f er  Serv ce  D used to S2S Auth
   * @param statsRece ver      Stats
   *
   * @return F nagle HTTP Cl ent w h S2S Auth / Mutual TLS
   */
  def bu ldF nagleHttpCl entMutualTls(
    requestT  out: Durat on,
    connectT  out: Durat on,
    acqu s  onT  out: Durat on,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Http.Cl ent =
    bu ldF nagleHttpCl ent(
      requestT  out = requestT  out,
      connectT  out = connectT  out,
      acqu s  onT  out = acqu s  onT  out,
      statsRece ver = statsRece ver
    ).w hMutualTls(serv ce dent f er)

  /**
   * Bu ld a F nagle HTTP cl ent
   *
   * @param requestT  out     HTTP cl ent request t  out
   * @param connectT  out     HTTP cl ent transport connect t  out
   * @param acqu s  onT  out HTTP cl ent sess on acqu s  on t  out
   * @param statsRece ver      stats
   *
   * @return F nagle HTTP Cl ent
   */
  def bu ldF nagleHttpCl ent(
    requestT  out: Durat on,
    connectT  out: Durat on,
    acqu s  onT  out: Durat on,
    statsRece ver: StatsRece ver,
  ): Http.Cl ent =
    Http.cl ent
      .w hStatsRece ver(statsRece ver)
      .w hRequestT  out(requestT  out)
      .w hTransport.connectT  out(connectT  out)
      .w hSess on.acqu s  onT  out(acqu s  onT  out)
}
