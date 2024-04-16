package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata

object User em {
  val UserEntryNa space: EntryNa space = EntryNa space("user")
}

case class User em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  overr de val  sMarkUnread: Opt on[Boolean],
  d splayType: UserD splayType,
  promoted tadata: Opt on[Promoted tadata],
  soc alContext: Opt on[Soc alContext],
  react veTr ggers: Opt on[UserReact veTr ggers],
  enableReact veBlend ng: Opt on[Boolean])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = User em.UserEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
