package com.tw ter.fr gate.pushserv ce.take

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.thr ftscala.ChannelNa 
 mport com.tw ter.ut l.Future
 mport java.ut l.concurrent.ConcurrentHashMap
 mport scala.collect on.concurrent
 mport scala.collect on.convert.decorateAsScala._

/**
 * A class to save all t  channel related  nformat on
 */
tra  ChannelForCand date {
  self: PushCand date =>

  // Cac  of channel select on result
  pr vate[t ] val selectedChannels: concurrent.Map[Str ng, Future[Seq[ChannelNa ]]] =
    new ConcurrentHashMap[Str ng, Future[Seq[ChannelNa ]]]().asScala

  // Returns t  channel  nformat on from all ChannelSelectors.
  def getChannels(): Future[Seq[ChannelNa ]] = {
    Future.collect(selectedChannels.values.toSeq).map { c => c.flatten }
  }
}
