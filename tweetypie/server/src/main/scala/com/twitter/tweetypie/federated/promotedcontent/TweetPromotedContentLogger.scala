package com.tw ter.t etyp e
package federated
package promotedcontent

 mport com.tw ter.ads.callback.thr ftscala.Engage ntRequest
 mport com.tw ter.ads. nternal.pcl.serv ce.CallbackPromotedContentLogger
 mport com.tw ter.ads. nternal.pcl.strato_adaptor.PromotedContent nputProv der
 mport com.tw ter.ads. nternal.pcl.thr ftscala.PromotedContent nput
 mport com.tw ter.adserver.thr ftscala.Engage ntType
 mport com.tw ter.ut l.Future

object T etPromotedContentLogger {
  sealed abstract class T etEngage ntType(val engage ntType: Engage ntType)
  case object T etEngage nt extends T etEngage ntType(Engage ntType.Send)
  case object ReplyEngage nt extends T etEngage ntType(Engage ntType.Reply)
  case object Ret etEngage nt extends T etEngage ntType(Engage ntType.Ret et)

  type Type = (Engage ntRequest, T etEngage ntType, Boolean) => Future[Un ]

  pr vate[t ] val Tw terContext =
    com.tw ter.context.Tw terContext(com.tw ter.t etyp e.Tw terContextPerm )

  def apply(callbackPromotedContentLogger: CallbackPromotedContentLogger): Type =
    (
      engage ntRequest: Engage ntRequest,
      t etEngage ntType: T etEngage ntType,
       sDark: Boolean
    ) => {
      val pc : PromotedContent nput =
        PromotedContent nputProv der(Tw terContext, engage ntRequest)

      // T  real logg ng  s f re-and-forget, so   can create t  Future and  gnore return ng  .
      Future.w n(! sDark) {
        callbackPromotedContentLogger.logNonTrendEngage nt(
          pc ,
          t etEngage ntType.engage ntType,
          pc . mpress on d)
      }
    }
}
