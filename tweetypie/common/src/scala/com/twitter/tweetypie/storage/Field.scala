package com.tw ter.t etyp e.storage

 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala.StoredT et
 mport com.tw ter.t etyp e.thr ftscala.{T et => TpT et}

/**
 * A f eld of t  stored vers on of a t et to read, update, or delete.
 *
 * T re  s not a one-to-one correspondence bet en t  f elds  ds of
 * [[com.tw ter.t etyp e.thr ftscala.T et]] and
 * [[com.tw ter.t etyp e.storage_ nternal.thr ftscala.StoredT et]]. For example,  n StoredT et,
 * t  nsfwUser property  s f eld 11;  n T et,    s a property of t  coreData struct  n f eld 2.
 * To c rcumvent t  confus on of us ng one set of f eld  ds or t  ot r, callers use  nstances of
 * [[F eld]] to reference t  part of t  object to mod fy.
 */
class F eld pr vate[storage] (val  d: Short) extends AnyVal {
  overr de def toStr ng: Str ng =  d.toStr ng
}

/**
 * NOTE: Make sure `AllUpdatableComp ledF elds`  s kept up to date w n add ng any new f eld
 */
object F eld {
   mport Add  onalF elds. sAdd  onalF eld d
  val Geo: F eld = new F eld(StoredT et.GeoF eld. d)
  val HasTakedown: F eld = new F eld(StoredT et.HasTakedownF eld. d)
  val NsfwUser: F eld = new F eld(StoredT et.NsfwUserF eld. d)
  val NsfwAdm n: F eld = new F eld(StoredT et.NsfwAdm nF eld. d)
  val T etyp eOnlyTakedownCountryCodes: F eld =
    new F eld(TpT et.T etyp eOnlyTakedownCountryCodesF eld. d)
  val T etyp eOnlyTakedownReasons: F eld =
    new F eld(TpT et.T etyp eOnlyTakedownReasonsF eld. d)

  val AllUpdatableComp ledF elds: Set[F eld] = Set(Geo, HasTakedown, NsfwUser, NsfwAdm n)

  def add  onalF eld( d: Short): F eld = {
    requ re( sAdd  onalF eld d( d), "f eld  d must be  n t  add  onal f eld range")
    new F eld( d)
  }
}
