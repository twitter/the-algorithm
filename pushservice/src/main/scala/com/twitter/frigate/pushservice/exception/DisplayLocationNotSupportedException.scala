package com.twitter.frigate.pushservice.exception

import scala.util.control.NoStackTrace

/**
 * Throw exception if DisplayLocation is not supported
 *
 * @param message Exception message
 */
class DisplayLocationNotSupportedException(private val message: String)
    extends Exception(message)
    with NoStackTrace
