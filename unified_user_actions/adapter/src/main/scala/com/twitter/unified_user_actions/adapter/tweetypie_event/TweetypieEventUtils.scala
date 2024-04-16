package com.tw ter.un f ed_user_act ons.adapter.t etyp e_event

 mport com.tw ter.t etyp e.thr ftscala.Ed Control
 mport com.tw ter.t etyp e.thr ftscala.Ed ControlEd 
 mport com.tw ter.t etyp e.thr ftscala.T et

sealed tra  T etyp eT etType
object T etTypeDefault extends T etyp eT etType
object T etTypeReply extends T etyp eT etType
object T etTypeRet et extends T etyp eT etType
object T etTypeQuote extends T etyp eT etType
object T etTypeEd  extends T etyp eT etType

object T etyp eEventUt ls {
  def ed edT et dFromT et(t et: T et): Opt on[Long] = t et.ed Control.flatMap {
    case Ed Control.Ed (Ed ControlEd ( n  alT et d, _)) => So ( n  alT et d)
    case _ => None
  }

  def t etTypeFromT et(t et: T et): Opt on[T etyp eT etType] = {
    val data = t et.coreData
    val  nReply ngToStatus dOpt = data.flatMap(_.reply).flatMap(_. nReplyToStatus d)
    val shareOpt = data.flatMap(_.share)
    val quotedT etOpt = t et.quotedT et
    val ed edT et dOpt = ed edT et dFromT et(t et)

    ( nReply ngToStatus dOpt, shareOpt, quotedT etOpt, ed edT et dOpt) match {
      // Reply
      case (So (_), None, _, None) =>
        So (T etTypeReply)
      // For any k nd of ret et (be   ret et of quote t et or ret et of a regular t et)
      //   only need to look at t  `share` f eld
      // https://confluence.tw ter.b z/pages/v ewpage.act on?spaceKey=CSVC&t le=T etyP e+FAQ#T etyp eFAQ-Howdo ell faT et saRet et
      case (None, So (_), _, None) =>
        So (T etTypeRet et)
      // quote
      case (None, None, So (_), None) =>
        So (T etTypeQuote)
      // create
      case (None, None, None, None) =>
        So (T etTypeDefault)
      // ed 
      case (None, None, _, So (_)) =>
        So (T etTypeEd )
      // reply and ret et shouldn't be present at t  sa  t  
      case (So (_), So (_), _, _) =>
        None
      // reply and ed  / ret et and ed  shouldn't be present at t  sa  t  
      case (So (_), None, _, So (_)) | (None, So (_), _, So (_)) =>
        None
    }
  }

}
