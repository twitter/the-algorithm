package com.tw ter.fr gate.pushserv ce.except on

 mport scala.ut l.control.NoStackTrace

/**
 * Throw except on  f t  sport doma n  s not supported by Mag cFanoutSports
 *
 * @param  ssage Except on  ssage
 */
class  nval dSportDoma nExcept on(pr vate val  ssage: Str ng)
    extends Except on( ssage)
    w h NoStackTrace
