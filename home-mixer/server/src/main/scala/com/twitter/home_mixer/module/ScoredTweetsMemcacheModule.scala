package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.shared_l brary. mcac d_cl ent. mcac dCl entBu lder
 mport com.tw ter.servo.cac .F nagle mcac 
 mport com.tw ter.servo.cac .KeyTransfor r
 mport com.tw ter.servo.cac .KeyValueTransform ngTtlCac 
 mport com.tw ter.servo.cac .Ser al zer
 mport com.tw ter.servo.cac .Thr ftSer al zer
 mport com.tw ter.servo.cac .TtlCac 
 mport com.tw ter.t  l nes.model.User d
 mport org.apac .thr ft.protocol.TCompactProtocol

 mport javax. nject.S ngleton

object ScoredT ets mcac Module extends Tw terModule {

  pr vate val ScopeNa  = "ScoredT etsCac "
  pr vate val ProdDestNa  = "/srv#/prod/local/cac /ho _scored_t ets:t mcac s"
  pr vate val Stag ngDestNa  = "/srv#/test/local/cac /t mcac _ho _scored_t ets:t mcac s"
  pr vate val scoredT etsSer al zer: Ser al zer[t.ScoredT etsResponse] =
    new Thr ftSer al zer[t.ScoredT etsResponse](
      t.ScoredT etsResponse,
      new TCompactProtocol.Factory())
  pr vate val user dKeyTransfor r: KeyTransfor r[User d] = (user d: User d) => user d.toStr ng

  @S ngleton
  @Prov des
  def prov desScoredT etsCac (
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): TtlCac [User d, t.ScoredT etsResponse] = {
    val destNa  = serv ce dent f er.env ron nt.toLo rCase match {
      case "prod" => ProdDestNa 
      case _ => Stag ngDestNa 
    }
    val cl ent =  mcac dCl entBu lder.bu ld mcac dCl ent(
      destNa  = destNa ,
      numTr es = 2,
      numConnect ons = 1,
      requestT  out = 200.m ll seconds,
      globalT  out = 400.m ll seconds,
      connectT  out = 100.m ll seconds,
      acqu s  onT  out = 100.m ll seconds,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver.scope(ScopeNa )
    )
    val underly ngCac  = new F nagle mcac (cl ent)

    new KeyValueTransform ngTtlCac (
      underly ngCac  = underly ngCac ,
      transfor r = scoredT etsSer al zer,
      underly ngKey = user dKeyTransfor r
    )
  }
}
