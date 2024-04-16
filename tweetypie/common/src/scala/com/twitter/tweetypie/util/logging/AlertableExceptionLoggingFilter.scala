package com.tw ter.t etyp e.ut l.logg ng

 mport ch.qos.logback.class c.sp . Logg ngEvent
 mport ch.qos.logback.class c.sp .ThrowableProxy
 mport ch.qos.logback.core.f lter.F lter
 mport ch.qos.logback.core.sp .F lterReply
 mport com.tw ter.t etyp e.serverut l.Except onCounter. sAlertable

/**
 * T  class  s currently be ng used by logback to log alertable except ons to a seperate f le.
 *
 * F lters do not change t  log levels of  nd v dual loggers. F lters f lter out spec f c  ssages
 * for spec f c appenders. T  allows us to have a log f le w h lots of  nformat on   w ll
 * mostly not need and a log f le w h only  mportant  nformat on. T  type of f lter ng cannot be
 * accompl s d by chang ng t  log levels of loggers, because t  logger levels are global.   want
 * to change t  semant cs for spec f c dest nat ons (appenders).
 */
class AlertableExcept onLogg ngF lter extends F lter[ Logg ngEvent] {
  pr vate[t ] val  gnorableLoggers: Set[Str ng] =
    Set(
      "com.g hub.benmanes.caffe ne.cac .BoundedLocalCac ",
      "abdec der",
      "org.apac .kafka.common.network.SaslChannelBu lder",
      "com.tw ter.f nagle.netty4.channel.ChannelStatsHandler$"
    )

  def  nclude(proxy: ThrowableProxy, event:  Logg ngEvent): Boolean =
     sAlertable(proxy.getThrowable()) && ! gnorableLoggers(event.getLoggerNa )

  overr de def dec de(event:  Logg ngEvent): F lterReply =
     f (! sStarted) {
      F lterReply.NEUTRAL
    } else {
      event.getThrowableProxy() match {
        case proxy: ThrowableProxy  f  nclude(proxy, event) =>
          F lterReply.NEUTRAL
        case _ =>
          F lterReply.DENY
      }
    }
}
