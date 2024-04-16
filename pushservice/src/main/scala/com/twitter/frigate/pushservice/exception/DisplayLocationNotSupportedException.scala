package com.tw ter.fr gate.pushserv ce.except on

 mport scala.ut l.control.NoStackTrace

/**
 * Throw except on  f D splayLocat on  s not supported
 *
 * @param  ssage Except on  ssage
 */
class D splayLocat onNotSupportedExcept on(pr vate val  ssage: Str ng)
    extends Except on( ssage)
    w h NoStackTrace
