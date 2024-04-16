package com.tw ter.t etyp e.add  onalf elds

 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.scrooge.TF eldBlob
 mport com.tw ter.scrooge.Thr ftStructF eld

object Add  onalF elds {
  type F eld d = Short

  /** add  onal f elds really start at 100, be   are  gnor ng conversat on  d for now */
  val StartAdd  onal d = 101

  /** all known [[T et]] f eld  Ds */
  val Comp ledF eld ds: Seq[F eld d] = T et. taData.f elds.map(_. d)

  /** all known [[T et]] f elds  n t  "add  onal-f eld" range (excludes  d) */
  val Comp ledAdd  onalF eld taDatas: Seq[Thr ftStructF eld[T et]] =
    T et. taData.f elds.f lter(f =>  sAdd  onalF eld d(f. d))

  val Comp ledAdd  onalF eldsMap: Map[Short, Thr ftStructF eld[T et]] =
    Comp ledAdd  onalF eld taDatas.map(f eld => (f eld. d, f eld)).toMap

  /** all known [[T et]] f eld  Ds  n t  "add  onal-f eld" range */
  val Comp ledAdd  onalF eld ds: Seq[F eld d] =
    Comp ledAdd  onalF eldsMap.keys.toSeq

  /** all [[T et]] f eld  Ds wh ch should be rejected w n set as add  onal
   * f elds on v a PostT etRequest.add  onalF elds or Ret etRequest.add  onalF elds */
  val RejectedF eld ds: Seq[F eld d] = Seq(
    // Should be prov ded v a PostT etRequest.conversat onControl f eld. go/convocontrolsbackend
    T et.Conversat onControlF eld. d,
    // T  f eld should only be set based on w t r t  cl ent sets t  r ght commun y
    // t et annotat on.
    T et.Commun  esF eld. d,
    // T  f eld should not be set by cl ents and should opt for
    // [[PostT etRequest.Exclus veT etControlOpt ons]].
    // T  exclus veT etControl f eld requ res t  user d to be set
    // and   shouldn't trust t  cl ent to prov de t  r ght one.
    T et.Exclus veT etControlF eld. d,
    // T  f eld should not be set by cl ents and should opt for
    // [[PostT etRequest.TrustedFr endsControlOpt ons]].
    // T  trustedFr endsControl f eld requ res t  trustedFr endsL st d to be
    // set and   shouldn't trust t  cl ent to prov de t  r ght one.
    T et.TrustedFr endsControlF eld. d,
    // T  f eld should not be set by cl ents and should opt for
    // [[PostT etRequest.CollabControlOpt ons]].
    // T  collabControl f eld requ res a l st of Collaborators to be
    // set and   shouldn't trust t  cl ent to prov de t  r ght one.
    T et.CollabControlF eld. d
  )

  def  sAdd  onalF eld d(f eld d: F eld d): Boolean =
    f eld d >= StartAdd  onal d

  /**
   * Prov des a l st of all add  onal f eld  Ds on t  t et, wh ch  nclude all
   * t  comp led add  onal f elds and all t  prov ded passthrough f elds.  T   ncludes
   * comp led add  onal f elds w re t  value  s None.
   */
  def allAdd  onalF eld ds(t et: T et): Seq[F eld d] =
    Comp ledAdd  onalF eld ds ++ t et._passthroughF elds.keys

  /**
   * Prov des a l st of all f eld  Ds that have a value on t  t et wh ch are not known comp led
   * add  onal f elds (excludes [[T et. d]]).
   */
  def unsettableAdd  onalF eld ds(t et: T et): Seq[F eld d] =
    Comp ledF eld ds
      .f lter {  d =>
        ! sAdd  onalF eld d( d) &&  d != T et. dF eld. d && t et.getF eldBlob( d). sDef ned
      } ++
      t et._passthroughF elds.keys

  /**
   * Prov des a l st of all f eld  Ds that have a value on t  t et wh ch are expl c ly d sallo d
   * from be ng set v a PostT etRequest.add  onalF elds and Ret etRequest.add  onalF elds
   */
  def rejectedAdd  onalF eld ds(t et: T et): Seq[F eld d] =
    RejectedF eld ds
      .f lter {  d => t et.getF eldBlob( d). sDef ned }

  def unsettableAdd  onalF eld dsError ssage(unsettableF eld ds: Seq[F eld d]): Str ng =
    s"request may not conta n f elds: [${unsettableF eld ds.sorted.mkStr ng(", ")}]"

  /**
   * Prov des a l st of all add  onal f eld  Ds that have a value on t  t et,
   * comp led and passthrough (excludes T et. d).
   */
  def nonEmptyAdd  onalF eld ds(t et: T et): Seq[F eld d] =
    Comp ledAdd  onalF eld taDatas.collect {
      case f  f f.getValue(t et) != None => f. d
    } ++ t et._passthroughF elds.keys

  def add  onalF elds(t et: T et): Seq[TF eldBlob] =
    (t et.getF eldBlobs(Comp ledAdd  onalF eld ds) ++ t et._passthroughF elds).values.toSeq

  /**
   *  rge base t et w h add  onal f elds.
   * Non-add  onal f elds  n t  add  onal t et are  gnored.
   * @param base: a t et that conta ns bas c f elds
   * @param add  onal: a t et object that carr es add  onal f elds
   */
  def setAdd  onalF elds(base: T et, add  onal: T et): T et =
    setAdd  onalF elds(base, add  onalF elds(add  onal))

  def setAdd  onalF elds(base: T et, add  onal: Opt on[T et]): T et =
    add  onal.map(setAdd  onalF elds(base, _)).getOrElse(base)

  def setAdd  onalF elds(base: T et, add  onal: Traversable[TF eldBlob]): T et =
    add  onal.foldLeft(base) { case (t, f) => t.setF eld(f) }

  /**
   * Unsets t  spec f ed f elds on t  g ven t et.
   */
  def unsetF elds(t et: T et, f eld ds:  erable[F eld d]): T et = {
    t et.unsetF elds(f eld ds.toSet)
  }
}
