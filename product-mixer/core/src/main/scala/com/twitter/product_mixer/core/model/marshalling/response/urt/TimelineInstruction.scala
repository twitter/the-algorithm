package com.tw ter.product_m xer.core.model.marshall ng.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Conta nsFeedbackAct on nfos
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.HasFeedbackAct on nfo

sealed tra  T  l ne nstruct on

case class AddEntr esT  l ne nstruct on(entr es: Seq[T  l neEntry])
    extends T  l ne nstruct on
    w h Conta nsFeedbackAct on nfos {
  overr de def feedbackAct on nfos: Seq[Opt on[FeedbackAct on nfo]] =
    entr es.flatMap {
      // Order  s  mportant, as entr es that  mple nt both Conta nsFeedbackAct on nfos and
      // HasFeedbackAct on nfo are expected to  nclude both w n  mple nt ng Conta nsFeedbackAct on nfos
      case conta nsFeedbackAct on nfos: Conta nsFeedbackAct on nfos =>
        conta nsFeedbackAct on nfos.feedbackAct on nfos
      case hasFeedbackAct on nfo: HasFeedbackAct on nfo =>
        Seq(hasFeedbackAct on nfo.feedbackAct on nfo)
      case _ => Seq.empty
    }
}

case class ReplaceEntryT  l ne nstruct on(entry: T  l neEntry)
    extends T  l ne nstruct on
    w h Conta nsFeedbackAct on nfos {
  overr de def feedbackAct on nfos: Seq[Opt on[FeedbackAct on nfo]] =
    entry match {
      // Order  s  mportant, as entr es that  mple nt both Conta nsFeedbackAct on nfos and
      // HasFeedbackAct on nfo are expected to  nclude both w n  mple nt ng Conta nsFeedbackAct on nfos
      case conta nsFeedbackAct on nfos: Conta nsFeedbackAct on nfos =>
        conta nsFeedbackAct on nfos.feedbackAct on nfos
      case hasFeedbackAct on nfo: HasFeedbackAct on nfo =>
        Seq(hasFeedbackAct on nfo.feedbackAct on nfo)
      case _ => Seq.empty
    }
}

case class AddToModuleT  l ne nstruct on(
  module ems: Seq[Module em],
  moduleEntry d: Str ng,
  module emEntry d: Opt on[Str ng],
  prepend: Opt on[Boolean])
    extends T  l ne nstruct on
    w h Conta nsFeedbackAct on nfos {
  overr de def feedbackAct on nfos: Seq[Opt on[FeedbackAct on nfo]] =
    module ems.map(_. em.feedbackAct on nfo)
}

case class P nEntryT  l ne nstruct on(entry: T  l neEntry) extends T  l ne nstruct on

case class MarkEntr esUnread nstruct on(entry ds: Seq[Str ng]) extends T  l ne nstruct on

case class ClearCac T  l ne nstruct on() extends T  l ne nstruct on

sealed tra  T  l neTerm nat onD rect on
case object TopTerm nat on extends T  l neTerm nat onD rect on
case object BottomTerm nat on extends T  l neTerm nat onD rect on
case object TopAndBottomTerm nat on extends T  l neTerm nat onD rect on
case class Term nateT  l ne nstruct on(term nateT  l neD rect on: T  l neTerm nat onD rect on)
    extends T  l ne nstruct on

case class ShowCover nstruct on(cover: Cover) extends T  l ne nstruct on

case class ShowAlert nstruct on(showAlert: ShowAlert) extends T  l ne nstruct on
