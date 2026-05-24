package com.twitter.frigate.pushservice.exception

import scala.util.control.NoStackTrace

/**
 * Exception for CRT not expected in the scope
 * @param message Exception message to log the UnsupportedCrt
 */
class UnsupportedCrtException(private val message: String)
    extends Exception(message)
    with NoStackTrace
