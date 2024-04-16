package com.tw ter.t etyp e
package handler

 mport com.tw ter.t etyp e.thr ftscala.Reply
 mport com.tw ter.t etyp e.thr ftscala.SelfThread tadata
 mport org.apac .thr ft.protocol.TF eld

tra  SelfThreadBu lder {
  def requ redReplyS ceF elds: Set[TF eld] =
    Set(
      T et.CoreDataF eld, // for Reply and Conversat on d
      T et.SelfThread tadataF eld // for cont nu ng ex st ng self-threads
    )

  def bu ld(authorUser d: User d, replyS ceT et: T et): Opt on[SelfThread tadata]
}

/**
 * SelfThreadBu lder  s used to bu ld  tadata for self-threads (t etstorms).
 *
 * T  bu lder  s  nvoked from ReplyBu lder on t ets that pass  n a  nReplyToStatus d and create
 * a Reply.  T   nvocat on  s done  ns de ReplyBu lder as ReplyBu lder has already loaded t 
 * "reply s ce t et" wh ch has all t   nformat on needed to determ ne t  self-thread  tadata.
 *
 * Note that T et.SelfThread tadata sc ma supports represent ng two types of self-threads:
 * 1. root self-thread : self-thread that beg ns alone and does not start w h reply ng to anot r
 *                       t et.  T  self-thread has a self-thread  D equal to t  conversat on  D.
 * 2. reply self-thread : self-thread that beg ns as a reply to anot r user's t et.
 *                        T  self-thread has a self-thread  D equal to t  f rst t et  n t 
 *                        current self-reply cha n wh ch w ll not equal t  conversat on  D.
 *
 * Currently only type #1 "root self-thread"  s handled.
 */
object SelfThreadBu lder {

  def apply(stats: StatsRece ver): SelfThreadBu lder = {
    //   want to keep open t  poss b l y for d fferent at on bet en root
    // self-threads (current funct onal y) and reply self-threads (poss ble
    // future funct onal y).
    val rootThreadStats = stats.scope("root_thread")

    // A t et beco s a root of a self-thread only after t  f rst self-reply
    //  s created. root_thread/start  s  ncr()d dur ng t  wr e-path of t 
    // self-reply t et, w n    s known that t  f rst/root t et has not
    // yet been ass gned a SelfThread tadata. T  wr e-path of t  second
    // t et does not add t  SelfThread tadata to t  f rst t et - that
    // happens asynchronously by t  SelfThreadDaemon.
    val rootThreadStartCounter = rootThreadStats.counter("start")

    // root_thread/cont nue prov des v s b l y  nto t  frequency of
    // cont nuat on t ets off leaf t ets  n a t et storm. Also  ncr()d  n
    // t  spec al case of a reply to t  root t et, wh ch does not yet have a
    // SelfThread tadata( sLeaf=true).
    val rootThreadCont nueCounter = rootThreadStats.counter("cont nue")

    // root_thread/branch prov des v s b l y  nto how frequently self-threads
    // get branc d - that  s, w n t  author self-repl es to a non-leaf t et
    //  n an ex st ng thread. Know ng t  frequency of branch ng w ll  lp us
    // determ ne t  pr or y of account ng for branch ng  n var ous
    // t et-delete use cases. Currently   do not f x up t  root t et's
    // SelfThread tadata w n  s reply t ets are deleted.
    val rootThreadBranchCounter = rootThreadStats.counter("branch")

    def observeSelfThread tr cs(replyS ceSTM: Opt on[SelfThread tadata]): Un  = {
      replyS ceSTM match {
        case So (SelfThread tadata(_,  sLeaf)) =>
           f ( sLeaf) rootThreadCont nueCounter. ncr()
          else rootThreadBranchCounter. ncr()
        case None =>
          rootThreadStartCounter. ncr()
      }
    }

    new SelfThreadBu lder {

      overr de def bu ld(
        authorUser d: User d,
        replyS ceT et: T et
      ): Opt on[SelfThread tadata] = {
        // t  "reply s ce t et"'s author must match t  current author
         f (getUser d(replyS ceT et) == authorUser d) {
          val replyS ceSTM = getSelfThread tadata(replyS ceT et)

          observeSelfThread tr cs(replyS ceSTM)

          // determ ne  f replyS ceT et stands alone (non-reply)
          getReply(replyS ceT et) match {
            case None | So (Reply(None, _, _)) =>
              // 'replyS ceT et' started a new self-thread that stands alone
              // wh ch happens w n t re's no Reply or t  Reply does not have
              //  nReplyToStatus d (d rected-at user)

              // requ redReplyS ceF elds requ res coreData and conversat on d
              //  s requ red so t  would have prev ously thrown an except on
              //  n ReplyBu lder  f t  read was part al
              val convo d = replyS ceT et.coreData.get.conversat on d.get
              So (SelfThread tadata( d = convo d,  sLeaf = true))

            case _ =>
              // 'replyS ceT et' was also a reply-to-t et, so cont nue any
              // self-thread by  n r  ng any SelfThread tadata   has
              // (though always sett ng  sLeaf to true)
              replyS ceSTM.map(_.copy( sLeaf = true))
          }
        } else {
          // Reply ng to a d fferent user currently never creates a self-thread
          // as all self-threads must start at t  root (and match conversat on
          //  D).
          //
          //  n t  future reply ng to a d fferent user *m ght* be part of a
          // self-thread but   wouldn't mark   as such unt l t  *next* t et
          //  s created (at wh ch t   t  self_thread daemon goes back and
          // marks t  f rst t et as  n t  self-thread.
          None
        }
      }
    }
  }
}
