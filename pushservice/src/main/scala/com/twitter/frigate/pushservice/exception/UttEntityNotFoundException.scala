package com.tw ter.fr gate.pushserv ce.except on

 mport scala.ut l.control.NoStackTrace

/**
 * Throw except on  f UttEnt y  s not found w re   m ght be a requ red data f eld
 *
 * @param  ssage Except on  ssage
 */
class UttEnt yNotFoundExcept on(pr vate val  ssage: Str ng)
    extends Except on( ssage)
    w h NoStackTrace
