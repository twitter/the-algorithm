package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

abstract class BaseFeedbackSubm Cl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {

  overr de def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] = {
    logEvent.eventNa space.flatMap(_.page).flatMap {
      case "search" =>
        val search nfoUt l = new Search nfoUt ls(ce em)
        search nfoUt l.getQueryOptFrom em(logEvent).flatMap { query =>
          val  sRelevant: Boolean = logEvent.eventNa space
            .flatMap(_.ele nt)
            .conta ns(" s_relevant")
          logEvent.eventNa space.flatMap(_.component).flatMap {
            case "relevance_prompt_module" =>
              for (act onT et d <- ce em. d)
                y eld  em.FeedbackPrompt nfo(
                  FeedbackPrompt nfo(
                    feedbackPromptAct on nfo = FeedbackPromptAct on nfo.T etRelevantToSearch(
                      T etRelevantToSearch(
                        searchQuery = query,
                        t et d = act onT et d,
                         sRelevant = So ( sRelevant)))))
            case "d d_ _f nd_ _module" =>
              So (
                 em.FeedbackPrompt nfo(FeedbackPrompt nfo(feedbackPromptAct on nfo =
                  FeedbackPromptAct on nfo.D d F nd Search(
                    D d F nd Search(searchQuery = query,  sRelevant = So ( sRelevant))))))
          }
        }
      case _ => None
    }

  }

  overr de def  s emTypeVal d( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeF lterPred cates. s emTypeForSearchResultsPageFeedbackSubm ( emTypeOpt)
}
