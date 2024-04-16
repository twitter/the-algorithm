package com.tw ter.un f ed_user_act ons.adapter.ema l_not f cat on_event

 mport com.tw ter. b s.thr ftscala.Not f cat onScr be
 mport com.tw ter.logbase.thr ftscala.LogBase
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala.S ceL neage

object Ema lNot f cat onEventUt ls {

  /*
   * Extract T et d from Logbase.page,  re  s a sample page below
   * https://tw ter.com/ /events/1580827044245544962?cn=ZmxleGl bGVfcmVjcw%3D%3D&refsrc=ema l
   * */
  def extractT et d(path: Str ng): Opt on[Long] = {
    val ptn = raw".*/([0-9]+)\\??.*".r
    path match {
      case ptn(t et d) =>
        So (t et d.toLong)
      case _ =>
        None
    }
  }

  def extractT et d(logBase: LogBase): Opt on[Long] = logBase.page match {
    case So (path) => extractT et d(path)
    case None => None
  }

  def extractEvent taData(scr be: Not f cat onScr be): Event tadata =
    Event tadata(
      s ceT  stampMs = scr be.t  stamp,
      rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
      s ceL neage = S ceL neage.Ema lNot f cat onEvents,
      language = scr be.logBase.flatMap(_.language),
      countryCode = scr be.logBase.flatMap(_.country),
      cl entApp d = scr be.logBase.flatMap(_.cl entApp d),
    )
}
