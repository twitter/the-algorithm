package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.Ampl fyDeta ls
 mport com.tw ter.cl entapp.thr ftscala. d aDeta ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etV deoWatch
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etAct on nfo
 mport com.tw ter.v deo.analyt cs.thr ftscala. d a dent f er

object V deoCl entEventUt ls {

  /**
   * For T ets w h mult ple v deos, f nd t   d of t  v deo that generated t  cl ent-event
   */
  def v deo dFrom d a dent f er( d a dent f er:  d a dent f er): Opt on[Str ng] =
     d a dent f er match {
      case  d a dent f er. d aPlatform dent f er( d aPlatform dent f er) =>
         d aPlatform dent f er. d a d.map(_.toStr ng)
      case _ => None
    }

  /**
   * G ven:
   * 1. t   d of t  v deo (` d a d`)
   * 2. deta ls about all t   d a  ems  n t  T et (` d a ems`),
   *  erate over t  ` d a ems` to lookup t   tadata about t  v deo w h  d ` d a d`.
   */
  def getV deo tadata(
     d a d: Str ng,
     d a ems: Seq[ d aDeta ls],
    ampl fyDeta ls: Opt on[Ampl fyDeta ls]
  ): Opt on[T etAct on nfo] = {
     d a ems.collectF rst {
      case  d a  f  d a.content d.conta ns( d a d) =>
        T etAct on nfo.T etV deoWatch(
          T etV deoWatch(
             d aType =  d a. d aType,
             sMonet zable =  d a.dynam cAds,
            v deoType = ampl fyDeta ls.flatMap(_.v deoType)
          ))
    }
  }
}
