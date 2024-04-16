package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport java.n o.ByteBuffer

object T etQuery {

  /**
   * Parent tra  that  nd cates what tr ggered t  t et query.
   */
  sealed tra  Cause {
     mport Cause._

    /**
     *  s t  t et query hydrat ng t  spec f ed t et for t  purposes of a wr e?
     */
    def wr  ng(t et d: T et d): Boolean =
      t  match {
        case w: Wr e  f w.t et d == t et d => true
        case _ => false
      }

    /**
     *  s t  t et query perform ng a regular read for any t et?  f t  cause  s
     * a wr e on a d fferent t et, t n any ot r t et that  s read  n support of t  wr e
     *  s cons dered a normal read, and  s subject to read-path hydrat on.
     */
    def read ng(t et d: T et d): Boolean =
      !wr  ng(t et d)

    /**
     * Are   perform ng an  nsert after create on t  spec f ed t et?  An undelete operat on
     * performs an  nsert, but  s not cons dered an  n  al  nsert.
     */
    def  n  al nsert(t et d: T et d): Boolean =
      t  match {
        case  nsert(`t et d`) => true
        case _ => false
      }
  }

  object Cause {
    case object Read extends Cause
    tra  Wr e extends Cause {
      val t et d: T et d
    }
    case class  nsert(t et d: T et d) extends Wr e
    case class Undelete(t et d: T et d) extends Wr e
  }

  /**
   * Opt ons for T etQuery.
   *
   * @param  nclude  nd cates wh ch opt onally hydrated f elds on each t et should be
   *   hydrated and  ncluded.
   * @param enforceV s b l yF lter ng w t r T etyp e v s b l y hydrators should be run to
   *   f lter protected t ets, blocked quote t ets, contr butor data, etc. T  does not affect
   *   V s b l y L brary (http://go/vf) based f lter ng.
   * @param cause  nd cates what tr ggered t  read: a normal read, or a wr e operat on.
   * @param forExternalConsumpt on w n true, t  t et  s be ng read for render ng to an external
   *   cl ent such as t   Phone Tw ter app and  s subject to be ng Dropped to prevent serv ng
   *   "bad" text to cl ents that m ght crash t  r OS. W n false, t  t et  s be ng read for  nternal
   *   non-cl ent purposes and should never be Dropped.
   * @param  s nnerQuotedT et Set by [[com.tw ter.t etyp e.hydrator.QuotedT etHydrator]],
   *   to be used by [[com.tw ter.v s b l y. nterfaces.t ets.T etV s b l yL brary]]
   *   so V s b l yF lter ng l brary can execute  nterst  al log c on  nner quoted t ets.
   * @param fetchStoredT ets Set by GetStoredT etsHandler.  f set to true, t  Manhattan storage
   *   layer w ll fetch and construct T ets regardless of what state t y're  n.
   */
  case class Opt ons(
     nclude: T etQuery. nclude,
    cac Control: Cac Control = Cac Control.ReadWr eCac ,
    cardsPlatformKey: Opt on[Str ng] = None,
    excludeReported: Boolean = false,
    enforceV s b l yF lter ng: Boolean = false,
    safetyLevel: SafetyLevel = SafetyLevel.F lterNone,
    forUser d: Opt on[User d] = None,
    languageTag: Str ng = "en",
    extens onsArgs: Opt on[ByteBuffer] = None,
    cause: Cause = Cause.Read,
    scrubUnrequestedF elds: Boolean = true,
    requ reS ceT et: Boolean = true,
    forExternalConsumpt on: Boolean = false,
    s mpleQuotedT et: Boolean = false,
     s nnerQuotedT et: Boolean = false,
    fetchStoredT ets: Boolean = false,
     sS ceT et: Boolean = false,
    enableEd ControlHydrat on: Boolean = true)

  case class  nclude(
    t etF elds: Set[F eld d] = Set.empty,
    countsF elds: Set[F eld d] = Set.empty,
     d aF elds: Set[F eld d] = Set.empty,
    quotedT et: Boolean = false,
    pasted d a: Boolean = false) {

    /**
     * Accumulates add  onal (rat r than replaces) f eld  ds.
     */
    def also(
      t etF elds: Traversable[F eld d] = N l,
      countsF elds: Traversable[F eld d] = N l,
       d aF elds: Traversable[F eld d] = N l,
      quotedT et: Opt on[Boolean] = None,
      pasted d a: Opt on[Boolean] = None
    ):  nclude =
      copy(
        t etF elds = t .t etF elds ++ t etF elds,
        countsF elds = t .countsF elds ++ countsF elds,
         d aF elds = t . d aF elds ++  d aF elds,
        quotedT et = quotedT et.getOrElse(t .quotedT et),
        pasted d a = pasted d a.getOrElse(t .pasted d a)
      )

    /**
     * Removes f eld  ds.
     */
    def exclude(
      t etF elds: Traversable[F eld d] = N l,
      countsF elds: Traversable[F eld d] = N l,
       d aF elds: Traversable[F eld d] = N l
    ):  nclude =
      copy(
        t etF elds = t .t etF elds -- t etF elds,
        countsF elds = t .countsF elds -- countsF elds,
         d aF elds = t . d aF elds --  d aF elds
      )

    def ++(that:  nclude):  nclude =
      copy(
        t etF elds = t .t etF elds ++ that.t etF elds,
        countsF elds = t .countsF elds ++ that.countsF elds,
         d aF elds = t . d aF elds ++ that. d aF elds,
        quotedT et = t .quotedT et || that.quotedT et,
        pasted d a = t .pasted d a || that.pasted d a
      )
  }
}

sealed case class Cac Control(wr eToCac : Boolean, readFromCac : Boolean)

object Cac Control {
  val NoCac : Cac Control = Cac Control(false, false)
  val ReadOnlyCac : Cac Control = Cac Control(false, true)
  val ReadWr eCac : Cac Control = Cac Control(true, true)
}
