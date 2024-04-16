package com.tw ter.ho _m xer.module

 mport com.google. nject.na .Na d
 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps.R chDurat on
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T etyp eStat cEnt  esCac 
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.shared_l brary. mcac d_cl ent. mcac dCl entBu lder
 mport com.tw ter.servo.cac .F nagle mcac 
 mport com.tw ter.servo.cac .KeyTransfor r
 mport com.tw ter.servo.cac .KeyValueTransform ngTtlCac 
 mport com.tw ter.servo.cac .ObservableTtlCac 
 mport com.tw ter.servo.cac .Ser al zer
 mport com.tw ter.servo.cac .Thr ftSer al zer
 mport com.tw ter.servo.cac .TtlCac 
 mport com.tw ter.t etyp e.{thr ftscala => tp}
 mport javax. nject.S ngleton
 mport org.apac .thr ft.protocol.TCompactProtocol

object T etyp eStat cEnt  esCac Cl entModule extends Tw terModule {

  pr vate val ScopeNa  = "T etyp eStat cEnt  es mcac "
  pr vate val ProdDest = "/srv#/prod/local/cac /t  l nescorer_t et_core_data:t mcac s"

  pr vate val t etsSer al zer: Ser al zer[tp.T et] = {
    new Thr ftSer al zer[tp.T et](tp.T et, new TCompactProtocol.Factory())
  }
  pr vate val keyTransfor r: KeyTransfor r[Long] = { t et d => t et d.toStr ng }

  @Prov des
  @S ngleton
  @Na d(T etyp eStat cEnt  esCac )
  def prov desT etyp eStat cEnt  esCac (
    statsRece ver: StatsRece ver,
    serv ce dent f er: Serv ce dent f er
  ): TtlCac [Long, tp.T et] = {
    val  mCac Cl ent =  mcac dCl entBu lder.bu ld mcac dCl ent(
      destNa  = ProdDest,
      numTr es = 1,
      numConnect ons = 1,
      requestT  out = 50.m ll seconds,
      globalT  out = 100.m ll seconds,
      connectT  out = 100.m ll seconds,
      acqu s  onT  out = 100.m ll seconds,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )
    mkCac (new F nagle mcac ( mCac Cl ent), statsRece ver)
  }

  pr vate def mkCac (
    f nagle mcac : F nagle mcac ,
    statsRece ver: StatsRece ver
  ): TtlCac [Long, tp.T et] = {
    val baseCac : KeyValueTransform ngTtlCac [Long, Str ng, tp.T et, Array[Byte]] =
      new KeyValueTransform ngTtlCac (
        underly ngCac  = f nagle mcac ,
        transfor r = t etsSer al zer,
        underly ngKey = keyTransfor r
      )
    ObservableTtlCac (
      underly ngCac  = baseCac ,
      statsRece ver = statsRece ver.scope(ScopeNa ),
      w ndowS ze = 1000,
      na  = ScopeNa 
    )
  }
}
