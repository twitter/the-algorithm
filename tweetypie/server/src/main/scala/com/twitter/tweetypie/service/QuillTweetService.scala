package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.qu ll.capture.Qu llCapture
 mport com.tw ter.t etyp e.thr ftscala._
 mport org.apac .thr ft.transport.T moryBuffer
 mport com.tw ter.f nagle.thr ft.Protocols
 mport com.tw ter.qu ll.capture.Payloads
 mport com.tw ter.t etyp e.serv ce.Qu llT etServ ce.createThr ftB naryRequest
 mport org.apac .thr ft.protocol.T ssage
 mport org.apac .thr ft.protocol.T ssageType
 mport org.apac .thr ft.protocol.TProtocol

object Qu llT etServ ce {
  // Construct t  byte stream for a b nary thr ft request
  def createThr ftB naryRequest( thod_na : Str ng, wr e_args: TProtocol => Un ): Array[Byte] = {
    val buf = new T moryBuffer(512)
    val oprot = Protocols.b naryFactory().getProtocol(buf)

    oprot.wr e ssageBeg n(new T ssage( thod_na , T ssageType.CALL, 0))
    wr e_args(oprot)
    oprot.wr e ssageEnd()

    // Return bytes
    java.ut l.Arrays.copyOfRange(buf.getArray, 0, buf.length)
  }
}

/**
 * Wraps an underly ng T etServ ce, logg ng so  requests.
 */
class Qu llT etServ ce(qu llCapture: Qu llCapture, protected val underly ng: Thr ftT etServ ce)
    extends T etServ ceProxy {

  overr de def postT et(request: PostT etRequest): Future[PostT etResult] = {
    val requestBytes = createThr ftB naryRequest(
      T etServ ce.PostT et.na ,
      T etServ ce.PostT et.Args(request).wr e)
    qu llCapture.storeServerRecv(Payloads.fromThr ft ssageBytes(requestBytes))
    underly ng.postT et(request)
  }

  overr de def deleteT ets(request: DeleteT etsRequest): Future[Seq[DeleteT etResult]] = {
    val requestBytes = createThr ftB naryRequest(
      T etServ ce.DeleteT ets.na ,
      T etServ ce.DeleteT ets.Args(request).wr e)
    qu llCapture.storeServerRecv(Payloads.fromThr ft ssageBytes(requestBytes))
    underly ng.deleteT ets(request)
  }

  overr de def postRet et(request: Ret etRequest): Future[PostT etResult] = {
    val requestBytes = createThr ftB naryRequest(
      T etServ ce.PostRet et.na ,
      T etServ ce.PostRet et.Args(request).wr e)
    qu llCapture.storeServerRecv(Payloads.fromThr ft ssageBytes(requestBytes))
    underly ng.postRet et(request)
  }

  overr de def unret et(request: Unret etRequest): Future[Unret etResult] = {
    val requestBytes = createThr ftB naryRequest(
      T etServ ce.Unret et.na ,
      T etServ ce.Unret et.Args(request).wr e)
    qu llCapture.storeServerRecv(Payloads.fromThr ft ssageBytes(requestBytes))
    underly ng.unret et(request)
  }

  overr de def cascadedDeleteT et(request: CascadedDeleteT etRequest): Future[Un ] = {
    val requestBytes = createThr ftB naryRequest(
      T etServ ce nternal.CascadedDeleteT et.na ,
      T etServ ce nternal.CascadedDeleteT et.Args(request).wr e)
    qu llCapture.storeServerRecv(Payloads.fromThr ft ssageBytes(requestBytes))
    underly ng.cascadedDeleteT et(request)
  }

}
