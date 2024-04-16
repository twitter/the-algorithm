package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.{
  Long2B gEnd an,
  ScalaCompactThr ft,
  Str ngUtf8
}
 mport com.tw ter.recos.ent  es.thr ftscala.{
  Semant cCoreEnt yScoreL st,
  Semant cCoreEnt yW hLocale,
  User dW hLocale,
  UserScoreL st
}

object Semant cCoreEnt  es nject ons {

  f nal val Str ngToSemant cCoreEnt yScoreL st nject on: KeyVal nject on[
    Str ng,
    Semant cCoreEnt yScoreL st
  ] =
    KeyVal nject on(
      Str ngUtf8,
      ScalaCompactThr ft(Semant cCoreEnt yScoreL st)
    )

  f nal val LongToSemant cCoreEnt yScoreL st nject on: KeyVal nject on[
    Long,
    Semant cCoreEnt yScoreL st
  ] =
    KeyVal nject on(
      Long2B gEnd an,
      ScalaCompactThr ft(Semant cCoreEnt yScoreL st)
    )

  f nal val UserW hLocaleToSemant cCoreEnt yScoreL st nject on: KeyVal nject on[
    User dW hLocale,
    Semant cCoreEnt yScoreL st
  ] =
    KeyVal nject on(
      ScalaCompactThr ft(User dW hLocale),
      ScalaCompactThr ft(Semant cCoreEnt yScoreL st)
    )

  f nal val Semant cCoreEnt yW hLocaleToUsersScoreL st nject on: KeyVal nject on[
    Semant cCoreEnt yW hLocale,
    UserScoreL st
  ] =
    KeyVal nject on(
      ScalaCompactThr ft(Semant cCoreEnt yW hLocale),
      ScalaCompactThr ft(UserScoreL st)
    )
}
