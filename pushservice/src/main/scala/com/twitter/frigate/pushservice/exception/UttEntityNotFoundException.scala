package com.twitter.frigate.pushservice.exception

import scala.util.control.NoStackTrace

/**
 * Throw exception if UttEntity is not found where it might be a required data field
 *
 * @param message Exception message
 */
class UttEntityNotFoundException(private val message: String)
    extends Exception(message)
    with NoStackTrace
