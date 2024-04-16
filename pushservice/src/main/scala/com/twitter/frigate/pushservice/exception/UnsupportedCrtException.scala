package com.tw ter.fr gate.pushserv ce.except on

 mport scala.ut l.control.NoStackTrace

/**
 * Except on for CRT not expected  n t  scope
 * @param  ssage Except on  ssage to log t  UnsupportedCrt
 */
class UnsupportedCrtExcept on(pr vate val  ssage: Str ng)
    extends Except on( ssage)
    w h NoStackTrace
