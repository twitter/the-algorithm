package com.tw ter.t etyp e.ut l.logg ng

 mport ch.qos.logback.class c.Level
 mport ch.qos.logback.class c.sp . Logg ngEvent
 mport ch.qos.logback.core.f lter.F lter
 mport ch.qos.logback.core.sp .F lterReply

/**
 * T  class  s currently be ng used by logback to log state nts from t etyp e at one level and
 * log state nts from ot r packages at anot r.
 *
 * F lters do not change t  log levels of  nd v dual loggers. F lters f lter out spec f c  ssages
 * for spec f c appenders. T  allows us to have a log f le w h lots of  nformat on   w ll
 * mostly not need and a log f le w h only  mportant  nformat on. T  type of f lter ng cannot be
 * accompl s d by chang ng t  log levels of loggers, because t  logger levels are global.   want
 * to change t  semant cs for spec f c dest nat ons (appenders).
 */
class Only mportantLogsLogg ngF lter extends F lter[ Logg ngEvent] {
  pr vate[t ] def not mportant(loggerNa : Str ng): Boolean =
    !loggerNa .startsW h("com.tw ter.t etyp e")

  overr de def dec de(event:  Logg ngEvent): F lterReply =
     f (! sStarted || event.getLevel. sGreaterOrEqual(Level.WARN)) {
      F lterReply.NEUTRAL
    } else  f (not mportant(event.getLoggerNa ())) {
      F lterReply.DENY
    } else {
      F lterReply.NEUTRAL
    }
}
