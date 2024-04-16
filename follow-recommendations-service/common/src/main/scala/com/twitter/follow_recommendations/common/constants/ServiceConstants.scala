package com.tw ter.follow_recom ndat ons.common.constants

 mport com.tw ter.convers ons.StorageUn Ops._

object Serv ceConstants {

  /** thr ft cl ent response s ze l m s
   *  t se  re est mated us ng mon or ng dashboard
   *  3MB network usage per second / 25 rps ~ 120KB/req << 1MB
   *    g ve so  buffer  re  n case so  requests requ re more data than ot rs
   */
  val Str ngLengthL m : Long =
    10. gabyte. nBytes
  val Conta nerLengthL m : Long = 1. gabyte. nBytes
}
