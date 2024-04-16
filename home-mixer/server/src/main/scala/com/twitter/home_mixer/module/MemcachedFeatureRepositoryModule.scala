package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Ho AuthorFeaturesCac Cl ent
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.RealT   nteract onGraphUserVertexCl ent
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T  l nesRealT  AggregateCl ent
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Twh nAuthorFollowFeatureCac Cl ent
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.shared_l brary. mcac d_cl ent. mcac dCl entBu lder
 mport com.tw ter.servo.cac .F nagle mcac Factory
 mport com.tw ter.servo.cac . mcac 
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object  mcac dFeatureRepos oryModule extends Tw terModule {

  // T  must match t  respect ve para ter on t  wr e path. Note that servo sets a d fferent
  // has r by default. See [[com.tw ter.hash ng.KeyHas r]] for t  l st of ot r ava lable
  // has rs.
  pr vate val  mcac KeyHas r = "ketama"

  @Prov des
  @S ngleton
  @Na d(T  l nesRealT  AggregateCl ent)
  def prov desT  l nesRealT  AggregateCl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ):  mcac  = {
    val rawCl ent =  mcac dCl entBu lder.bu ldRaw mcac dCl ent(
      numTr es = 3,
      numConnect ons = 1,
      requestT  out = 100.m ll seconds,
      globalT  out = 300.m ll seconds,
      connectT  out = 200.m ll seconds,
      acqu s  onT  out = 200.m ll seconds,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )

    bu ld mcac Cl ent(rawCl ent, "/s/cac /t  l nes_real_t  _aggregates:t mcac s")
  }

  @Prov des
  @S ngleton
  @Na d(Ho AuthorFeaturesCac Cl ent)
  def prov desHo AuthorFeaturesCac Cl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ):  mcac  = {
    val cac Cl ent =  mcac dCl entBu lder.bu ldRaw mcac dCl ent(
      numTr es = 2,
      numConnect ons = 1,
      requestT  out = 150.m ll seconds,
      globalT  out = 300.m ll seconds,
      connectT  out = 200.m ll seconds,
      acqu s  onT  out = 200.m ll seconds,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )

    bu ld mcac Cl ent(cac Cl ent, "/s/cac /t  l nes_author_features:t mcac s")
  }

  @Prov des
  @S ngleton
  @Na d(Twh nAuthorFollowFeatureCac Cl ent)
  def prov desTwh nAuthorFollowFeatureCac Cl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ):  mcac  = {
    val cac Cl ent =  mcac dCl entBu lder.bu ldRaw mcac dCl ent(
      numTr es = 2,
      numConnect ons = 1,
      requestT  out = 150.m ll seconds,
      globalT  out = 300.m ll seconds,
      connectT  out = 200.m ll seconds,
      acqu s  onT  out = 200.m ll seconds,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )

    bu ld mcac Cl ent(cac Cl ent, "/s/cac /ho _twh n_author_features:t mcac s")
  }

  @Prov des
  @S ngleton
  @Na d(RealT   nteract onGraphUserVertexCl ent)
  def prov desRealT   nteract onGraphUserVertexCl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ):  mcac  = {
    val cac Cl ent =  mcac dCl entBu lder.bu ldRaw mcac dCl ent(
      numTr es = 2,
      numConnect ons = 1,
      requestT  out = 150.m ll seconds,
      globalT  out = 300.m ll seconds,
      connectT  out = 200.m ll seconds,
      acqu s  onT  out = 200.m ll seconds,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )

    bu ld mcac Cl ent(cac Cl ent, "/s/cac /realt  _ nteract ve_graph_prod_v2:t mcac s")
  }

  pr vate def bu ld mcac Cl ent(cac Cl ent:  mcac d.Cl ent, dest: Str ng):  mcac  =
    F nagle mcac Factory(
      cl ent = cac Cl ent,
      dest = dest,
      hashNa  =  mcac KeyHas r
    )()

}
