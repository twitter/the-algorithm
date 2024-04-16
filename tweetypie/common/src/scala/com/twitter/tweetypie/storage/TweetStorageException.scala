package com.tw ter.t etyp e.storage

 mport scala.ut l.control.NoStackTrace

sealed abstract class T etStorageExcept on( ssage: Str ng, cause: Throwable)
    extends Except on( ssage, cause)

/**
 * T  request was not properly for d and fa led an assert on present  n t  code. Should not be
 * retr ed w hout mod f cat on.
 */
case class Cl entError( ssage: Str ng, cause: Throwable)
    extends T etStorageExcept on( ssage, cause)
    w h NoStackTrace

/**
 * Request was rejected by Manhattan or t   n-process rate l m er. Should not be retr ed.
 */
case class RateL m ed( ssage: Str ng, cause: Throwable)
    extends T etStorageExcept on( ssage, cause)
    w h NoStackTrace

/**
 * Corrupt t ets  re requested from Manhattan
 */
case class Vers onM smatchError( ssage: Str ng, cause: Throwable = null)
    extends T etStorageExcept on( ssage, cause)
    w h NoStackTrace

/**
 * All ot r unhandled except ons.
 */
case class  nternalError( ssage: Str ng, cause: Throwable = null)
    extends T etStorageExcept on( ssage, cause)
