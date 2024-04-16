package com.tw ter.t etyp e.storage

/**
 * Respons ble for encod ng/decod ng T et records to/from Manhattan keys
 *
 * K/V Sc  :
 * -----------
 *      [T et d]
 *           / tadata
 *               /delete_state (a.k.a. hard delete)
 *               /soft_delete_state
 *               /bounce_delete_state
 *               /undelete_state
 *               /force_added_state
 *               /scrubbed_f elds/
 *                    /[ScrubbedF eld d_1]
 *                     ..
 *                   /[ScrubbedF eld d_M]
 *          /f elds
 *             / nternal
 *                 /1
 *                 /9
 *                 ..
 *                 /99
 *             /external
 *                 /100
 *                 ..
 *
 *  MPORTANT NOTE:
 * 1) F eld  ds 2 to 8  n T et thr ft struct are cons dered "core f elds" are 'packed' toget r
 *     nto a TF eldBlob and stored under f eld  d 1 ( .e [DatasetNa ]/[T et d]/f elds/ nternal/1).
 *    T   s why   do not see keys from [DatasetNa ]/[T et d]/f elds/ nternal/2 to [DatasetNa ]/
 *    [T et d]/f elds/ nternal/8)
 *
 * 2) Also, t  t et  d (wh ch  s t  f eld  d 1  n T et thr ft structure)  s not expl c ly stored
 *     n Manhattan. T re  s no need to expl c ly store   s nce    s a part of t  Pkey
 */
case class T etKey(t et d: T et d, lKey: T etKey.LKey) {
  overr de def toStr ng: Str ng =
    s"/${ManhattanOperat ons.Pkey nject on(t et d)}/${ManhattanOperat ons.Lkey nject on(lKey)}"
}

object T etKey {
  // Manhattan uses lex cograph cal order for keys. To make sure lex cograph cal order matc s t 
  // nu r cal order,   should pad both t et  d and f eld  ds w h lead ng zeros.
  // S nce t et  d  s long and f eld  d  s a short, t  max w dth of each can be obta ned by do ng
  // Long.MaxValue.toStr ng.length and Short.MaxValue.toStr ng.length respect vely
  pr vate val T et dFormatStr = s"%0${Long.MaxValue.toStr ng.length}d"
  pr vate val F eld dFormatStr = s"%0${Short.MaxValue.toStr ng.length}d"
  pr vate[storage] def padT et dStr(t et d: Long): Str ng = T et dFormatStr.format(t et d)
  pr vate[storage] def padF eld dStr(f eld d: Short): Str ng = F eld dFormatStr.format(f eld d)

  def coreF eldsKey(t et d: T et d): T etKey = T etKey(t et d, LKey.CoreF eldsKey)
  def hardDelet onStateKey(t et d: T et d): T etKey =
    T etKey(t et d, LKey.HardDelet onStateKey)
  def softDelet onStateKey(t et d: T et d): T etKey =
    T etKey(t et d, LKey.SoftDelet onStateKey)
  def bounceDelet onStateKey(t et d: T et d): T etKey =
    T etKey(t et d, LKey.BounceDelet onStateKey)
  def unDelet onStateKey(t et d: T et d): T etKey = T etKey(t et d, LKey.UnDelet onStateKey)
  def forceAddedStateKey(t et d: T et d): T etKey = T etKey(t et d, LKey.ForceAddedStateKey)
  def scrubbedGeoF eldKey(t et d: T et d): T etKey = T etKey(t et d, LKey.ScrubbedGeoF eldKey)
  def f eldKey(t et d: T et d, f eld d: F eld d): T etKey =
    T etKey(t et d, LKey.F eldKey(f eld d))
  def  nternalF eldsKey(t et d: T et d, f eld d: F eld d): T etKey =
    T etKey(t et d, LKey. nternalF eldsKey(f eld d))
  def add  onalF eldsKey(t et d: T et d, f eld d: F eld d): T etKey =
    T etKey(t et d, LKey.Add  onalF eldsKey(f eld d))
  def scrubbedF eldKey(t et d: T et d, f eld d: F eld d): T etKey =
    T etKey(t et d, LKey.ScrubbedF eldKey(f eld d))

  // AllF eldsKeyPref x:       f elds
  // CoreF eldsKey:            f elds/ nternal/1  (Stores subset of StoredT et f elds wh ch are
  //                             "packed"  nto a s ngle CoreF elds record)
  // HardDelet onStateKey:      tadata/delete_state
  // SoftDelet onStateKey:      tadata/soft_delete_state
  // BounceDelet onStateKey:    tadata/bounce_delete_state
  // UnDelet onStateKey:        tadata/undelete_state
  // ForceAddedStateKey:        tadata/force_added_state
  // F eldKey:                 f elds/<group_na >/<padded_f eld_ d> (w re <group_na >
  //                              s ' nternal' for f eld  ds < 100 and 'external' for all ot r
  //                             f elds  ds)
  //  nternalF eldsKeyPref x:  f elds/ nternal
  // PKey:                     <empty str ng>
  // ScrubbedF eldKey:          tadata/scrubbed_f elds/<padded_f eld_ d>
  // ScrubbedF eldKeyPref x:    tadata/scrubbed_f elds
  sealed abstract class LKey(overr de val toStr ng: Str ng)
  object LKey {
    pr vate val HardDelet onRecordL eral = "delete_state"
    pr vate val SoftDelet onRecordL eral = "soft_delete_state"
    pr vate val BounceDelet onRecordL eral = "bounce_delete_state"
    pr vate val UnDelet onRecordL eral = "undelete_state"
    pr vate val ForceAddRecordL eral = "force_added_state"
    pr vate val ScrubbedF eldsGroup = "scrubbed_f elds"
    pr vate val  nternalF eldsGroup = " nternal"
    pr vate val ExternalF eldsGroup = "external"
    pr vate val  tadataCategory = " tadata"
    pr vate val F eldsCategory = "f elds"
    pr vate val  nternalF eldsKeyPref x = s"$F eldsCategory/$ nternalF eldsGroup/"
    pr vate val ExternalF eldsKeyPref x = s"$F eldsCategory/$ExternalF eldsGroup/"
    pr vate val ScrubbedF eldsKeyPref x = s"$ tadataCategory/$ScrubbedF eldsGroup/"

    sealed abstract class  tadataKey( tadataType: Str ng)
        extends LKey(s"$ tadataCategory/$ tadataType")
    sealed abstract class StateKey(stateType: Str ng) extends  tadataKey(stateType)
    case object HardDelet onStateKey extends StateKey(s"$HardDelet onRecordL eral")
    case object SoftDelet onStateKey extends StateKey(s"$SoftDelet onRecordL eral")
    case object BounceDelet onStateKey extends StateKey(s"$BounceDelet onRecordL eral")
    case object UnDelet onStateKey extends StateKey(s"$UnDelet onRecordL eral")
    case object ForceAddedStateKey extends StateKey(s"$ForceAddRecordL eral")

    case class ScrubbedF eldKey(f eld d: F eld d)
        extends  tadataKey(s"$ScrubbedF eldsGroup/${padF eld dStr(f eld d)}")
    val ScrubbedGeoF eldKey: LKey.ScrubbedF eldKey = ScrubbedF eldKey(T etF elds.geoF eld d)

    /**
     * LKey that has one of many poss ble f elds  d. T  general ze over
     *  nternal and add  onal f elds key.
     */
    sealed abstract class F eldKey(pref x: Str ng) extends LKey(toStr ng) {
      def f eld d: F eld d
      overr de val toStr ng: Str ng = pref x + padF eld dStr(f eld d)
    }
    object F eldKey {
      def apply(f eld d: F eld d): F eldKey =
        f eld d match {
          case  d  f  d < T etF elds.f rstAdd  onalF eld d =>  nternalF eldsKey(f eld d)
          case _ => Add  onalF eldsKey(f eld d)
        }
    }

    case class  nternalF eldsKey(f eld d: F eld d) extends F eldKey( nternalF eldsKeyPref x) {
      assert(f eld d < T etF elds.f rstAdd  onalF eld d)
    }
    case class Add  onalF eldsKey(f eld d: F eld d) extends F eldKey(ExternalF eldsKeyPref x) {
      assert(f eld d >= T etF elds.f rstAdd  onalF eld d)
    }
    val CoreF eldsKey: LKey. nternalF eldsKey =  nternalF eldsKey(T etF elds.rootCoreF eld d)

    case class Unknown pr vate (str: Str ng) extends LKey(str)

    def fromStr ng(str: Str ng): LKey = {
      def extractF eld d(pref x: Str ng): F eld d =
        str.sl ce(pref x.length, str.length).toShort

      str match {
        case CoreF eldsKey.toStr ng => CoreF eldsKey
        case HardDelet onStateKey.toStr ng => HardDelet onStateKey
        case SoftDelet onStateKey.toStr ng => SoftDelet onStateKey
        case BounceDelet onStateKey.toStr ng => BounceDelet onStateKey
        case UnDelet onStateKey.toStr ng => UnDelet onStateKey
        case ForceAddedStateKey.toStr ng => ForceAddedStateKey
        case ScrubbedGeoF eldKey.toStr ng => ScrubbedGeoF eldKey
        case _  f str.startsW h( nternalF eldsKeyPref x) =>
           nternalF eldsKey(extractF eld d( nternalF eldsKeyPref x))
        case _  f str.startsW h(ExternalF eldsKeyPref x) =>
          Add  onalF eldsKey(extractF eld d(ExternalF eldsKeyPref x))
        case _  f str.startsW h(ScrubbedF eldsKeyPref x) =>
          ScrubbedF eldKey(extractF eld d(ScrubbedF eldsKeyPref x))
        case _ => Unknown(str)
      }
    }
  }
}
