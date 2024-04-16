package com.tw ter.representat onscorer.modules

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.representat on_manager.conf g.Cl entConf g
 mport com.tw ter.representat on_manager.conf g.Enabled n moryCac Params
 mport com.tw ter.representat on_manager.conf g. n moryCac Params
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType._
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on._
 mport javax. nject.S ngleton

object RMSConf gModule extends Tw terModule {
  def getCac Na (embed ngType: Embedd ngType, modelVers on: ModelVers on): Str ng =
    s"${embed ngType.na }_${modelVers on.na }_ n_ m_cac "

  @S ngleton
  @Prov des
  def prov desRMSCl entConf g: Cl entConf g = {
    val cac ParamsMap: Map[
      (Embedd ngType, ModelVers on),
       n moryCac Params
    ] = Map(
      // T et Embedd ngs
      (LogFavBasedT et, Model20m145k2020) -> Enabled n moryCac Params(
        ttl = 10.m nutes,
        maxKeys = 1048575, // 800MB
        cac Na  = getCac Na (LogFavBasedT et, Model20m145k2020)),
      (LogFavLongestL2Embedd ngT et, Model20m145k2020) -> Enabled n moryCac Params(
        ttl = 5.m nute,
        maxKeys = 1048575, // 800MB
        cac Na  = getCac Na (LogFavLongestL2Embedd ngT et, Model20m145k2020)),
      // User - KnownFor Embedd ngs
      (FavBasedProducer, Model20m145k2020) -> Enabled n moryCac Params(
        ttl = 1.day,
        maxKeys = 500000, // 400MB
        cac Na  = getCac Na (FavBasedProducer, Model20m145k2020)),
      // User -  nterested n Embedd ngs
      (LogFavBasedUser nterested nFromAPE, Model20m145k2020) -> Enabled n moryCac Params(
        ttl = 6.h s,
        maxKeys = 262143,
        cac Na  = getCac Na (LogFavBasedUser nterested nFromAPE, Model20m145k2020)),
      (FavBasedUser nterested n, Model20m145k2020) -> Enabled n moryCac Params(
        ttl = 6.h s,
        maxKeys = 262143,
        cac Na  = getCac Na (FavBasedUser nterested n, Model20m145k2020)),
      // Top c Embedd ngs
      (FavTfgTop c, Model20m145k2020) -> Enabled n moryCac Params(
        ttl = 12.h s,
        maxKeys = 262143, // 200MB
        cac Na  = getCac Na (FavTfgTop c, Model20m145k2020)),
      (LogFavBasedKgoApeTop c, Model20m145k2020) -> Enabled n moryCac Params(
        ttl = 6.h s,
        maxKeys = 262143,
        cac Na  = getCac Na (LogFavBasedKgoApeTop c, Model20m145k2020)),
    )

    new Cl entConf g( n mCac ParamsOverr des = cac ParamsMap)
  }

}
