package com.tw ter. nteract on_graph.sc o.agg_not f cat ons

 mport com.spot fy.sc o.Sc o tr cs
 mport com.tw ter.cl entapp.thr ftscala.EventNa space
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 

object  nteract onGraphNot f cat onUt l {

  val PUSH_OPEN_ACT ONS = Set("open", "background_open")
  val NTAB_CL CK_ACT ONS = Set("nav gate", "cl ck")
  val STATUS_ D_REGEX = "^tw ter:\\/\\/t et\\?status_ d=([0-9]+).*".r
  val TWEET_ D_REGEX = "^tw ter:\\/\\/t et. d=([0-9]+).*".r

  def extractT et dFromUrl(url: Str ng): Opt on[Long] = url match {
    case STATUS_ D_REGEX(status d) =>
      Sc o tr cs.counter("regex match ng", "status_ d="). nc()
      So (status d.toLong)
    case TWEET_ D_REGEX(t et d) =>
      Sc o tr cs.counter("regex match ng", "t et? d="). nc()
      So (t et d.toLong)
    case _ => None
  }

  def getPushNtabEvents(e: LogEvent): Seq[(Long, (Long, FeatureNa ))] = {
    for {
      logBase <- e.logBase.toSeq
      user d <- logBase.user d.toSeq
      na space <- e.eventNa space.toSeq
      (t et d, featureNa ) <- na space match {
        case EventNa space(_, _, _, _, _, So (act on))  f PUSH_OPEN_ACT ONS.conta ns(act on) =>
          (for {
            deta ls <- e.eventDeta ls
            url <- deta ls.url
            t et d <- extractT et dFromUrl(url)
          } y eld {
            Sc o tr cs.counter("event type", "push open"). nc()
            (t et d, FeatureNa .NumPushOpens)
          }).toSeq
        case EventNa space(_, So ("ntab"), _, _, _, So ("nav gate")) =>
          val t et ds = for {
            deta ls <- e.eventDeta ls.toSeq
             ems <- deta ls. ems.toSeq
             em <-  ems
            ntabDeta ls <-  em.not f cat onTabDeta ls.toSeq
            cl entEvent tadata <- ntabDeta ls.cl entEvent tadata.toSeq
            t et ds <- cl entEvent tadata.t et ds.toSeq
            t et d <- t et ds
          } y eld {
            Sc o tr cs.counter("event type", "ntab nav gate"). nc()
            t et d
          }
          t et ds.map((_, FeatureNa .NumNtabCl cks))
        case EventNa space(_, So ("ntab"), _, _, _, So ("cl ck")) =>
          val t et ds = for {
            deta ls <- e.eventDeta ls.toSeq
             ems <- deta ls. ems.toSeq
             em <-  ems
            t et d <-  em. d
          } y eld {
            Sc o tr cs.counter("event type", "ntab cl ck"). nc()
            t et d
          }
          t et ds.map((_, FeatureNa .NumNtabCl cks))
        case _ => N l
      }
    } y eld (t et d, (user d, featureNa ))
  }

  /**
   * Returns events correspond ng to ntab cl cks.   have t  t et  d from ntab cl cks and can jo n
   * those w h publ c t ets.
   */
  def getNtabEvents(e: LogEvent): Seq[(Long, (Long, FeatureNa ))] = {
    for {
      logBase <- e.logBase.toSeq
      user d <- logBase.user d.toSeq
      na space <- e.eventNa space.toSeq
      (t et d, featureNa ) <- na space match {
        case EventNa space(_, So ("ntab"), _, _, _, So ("nav gate")) =>
          val t et ds = for {
            deta ls <- e.eventDeta ls.toSeq
             ems <- deta ls. ems.toSeq
             em <-  ems
            ntabDeta ls <-  em.not f cat onTabDeta ls.toSeq
            cl entEvent tadata <- ntabDeta ls.cl entEvent tadata.toSeq
            t et ds <- cl entEvent tadata.t et ds.toSeq
            t et d <- t et ds
          } y eld {
            Sc o tr cs.counter("event type", "ntab nav gate"). nc()
            t et d
          }
          t et ds.map((_, FeatureNa .NumNtabCl cks))
        case EventNa space(_, So ("ntab"), _, _, _, So ("cl ck")) =>
          val t et ds = for {
            deta ls <- e.eventDeta ls.toSeq
             ems <- deta ls. ems.toSeq
             em <-  ems
            t et d <-  em. d
          } y eld {
            Sc o tr cs.counter("event type", "ntab cl ck"). nc()
            t et d
          }
          t et ds.map((_, FeatureNa .NumNtabCl cks))
        case _ => N l
      }
    } y eld (t et d, (user d, featureNa ))
  }

  /**
   * get push open events, keyed by  mpress on d (as t  cl ent event does not always have t  t et d nor t  author d)
   */
  def getPushOpenEvents(e: LogEvent): Seq[(Str ng, (Long, FeatureNa ))] = {
    for {
      logBase <- e.logBase.toSeq
      user d <- logBase.user d.toSeq
      na space <- e.eventNa space.toSeq
      (t et d, featureNa ) <- na space match {
        case EventNa space(_, _, _, _, _, So (act on))  f PUSH_OPEN_ACT ONS.conta ns(act on) =>
          val  mpress on dOpt = for {
            deta ls <- e.not f cat onDeta ls
             mpress on d <- deta ls. mpress on d
          } y eld {
            Sc o tr cs.counter("event type", "push open"). nc()
             mpress on d
          }
           mpress on dOpt.map((_, FeatureNa .NumPushOpens)).toSeq
        case _ => N l
      }
    } y eld (t et d, (user d, featureNa ))
  }
}
