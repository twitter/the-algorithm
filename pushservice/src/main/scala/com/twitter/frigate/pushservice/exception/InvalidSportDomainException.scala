package com.twitter.frigate.pushservice.exception

import scala.util.control.NoStackTrace

/**
 * Throw exception if the sport domain is not supported by MagicFanoutSports
 *
 * @param message Exception message
 */
class InvalidSportDomainException(private val message: String)
    extends Exception(message)
    with NoStackTrace
