package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.ContextualT etRef
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.conversat on_annotat on.Conversat onAnnotat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.ForwardP vot
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.Tombstone nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Badge
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Preroll tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url

object T et em {
  val T etEntryNa space = EntryNa space("t et")
  val PromotedT etEntryNa space = EntryNa space("promoted-t et")
}

case class T et em(
  overr de val  d: Long,
  overr de val entryNa space: EntryNa space,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  overr de val  sP nned: Opt on[Boolean],
  overr de val entry dToReplace: Opt on[Str ng],
  soc alContext: Opt on[Soc alContext],
  h ghl ghts: Opt on[T etH ghl ghts],
  d splayType: T etD splayType,
   nnerTombstone nfo: Opt on[Tombstone nfo],
  t  l nesScore nfo: Opt on[T  l nesScore nfo],
  hasModeratedRepl es: Opt on[Boolean],
  forwardP vot: Opt on[ForwardP vot],
   nnerForwardP vot: Opt on[ForwardP vot],
  promoted tadata: Opt on[Promoted tadata],
  conversat onAnnotat on: Opt on[Conversat onAnnotat on],
  contextualT etRef: Opt on[ContextualT etRef],
  preroll tadata: Opt on[Preroll tadata],
  replyBadge: Opt on[Badge],
  dest nat on: Opt on[Url])
    extends T  l ne em {

  /**
   * Promoted t ets need to  nclude t   mpress on  D  n t  entry  D s nce so  cl ents have
   * cl ent-s de log c that dedupl cates ads  mpress on callbacks based on a comb nat on of t 
   * t et and  mpress on  Ds. Not  nclud ng t   mpress on  D w ll lead to over dedupl cat on.
   */
  overr de lazy val entry dent f er: Str ng = promoted tadata
    .map {  tadata =>
      val  mpress on d =  tadata. mpress onStr ng match {
        case So ( mpress onStr ng)  f  mpress onStr ng.nonEmpty =>  mpress onStr ng
        case _ => throw new  llegalStateExcept on(s"Promoted T et $ d m ss ng  mpress on  D")
      }
      s"$entryNa space-$ d-$ mpress on d"
    }.getOrElse(s"$entryNa space-$ d")

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
