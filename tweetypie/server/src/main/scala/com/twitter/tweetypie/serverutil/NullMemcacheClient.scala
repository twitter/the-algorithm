package com.tw ter.t etyp e.serverut l

 mport com.tw ter.f nagle. mcac d
 mport com.tw ter.f nagle. mcac d.CasResult
 mport com.tw ter. o.Buf
 mport com.tw ter.t etyp e.Future
 mport com.tw ter.t etyp e.T  
 mport java.lang

/**
 * T  w ll be used dur ng C  test runs,  n t  no-cac  scenar os for both DCs.
 *   are treat ng t  as cac  of  nstantaneous exp ry. MockCl ent uses an  n- mory map as
 * an underly ng data-store,   extend   and prevent any wr es to t  map - thus mak ng sure
 *  's always empty.
 */
class Null mcac Cl ent extends  mcac d.MockCl ent {
  overr de def set(key: Str ng, flags:  nt, exp ry: T  , value: Buf): Future[Un ] = Future.Done

  overr de def add(key: Str ng, flags:  nt, exp ry: T  , value: Buf): Future[lang.Boolean] =
    Future.value(true)

  overr de def append(key: Str ng, flags:  nt, exp ry: T  , value: Buf): Future[lang.Boolean] =
    Future.value(false)

  overr de def prepend(key: Str ng, flags:  nt, exp ry: T  , value: Buf): Future[lang.Boolean] =
    Future.value(false)

  overr de def replace(key: Str ng, flags:  nt, exp ry: T  , value: Buf): Future[lang.Boolean] =
    Future.value(false)

  overr de def c ckAndSet(
    key: Str ng,
    flags:  nt,
    exp ry: T  ,
    value: Buf,
    casUn que: Buf
  ): Future[CasResult] = Future.value(CasResult.NotFound)

  overr de def delete(key: Str ng): Future[lang.Boolean] = Future.value(false)

  overr de def  ncr(key: Str ng, delta: Long): Future[Opt on[lang.Long]] =
    Future.value(None)

  overr de def decr(key: Str ng, delta: Long): Future[Opt on[lang.Long]] =
    Future.value(None)
}
