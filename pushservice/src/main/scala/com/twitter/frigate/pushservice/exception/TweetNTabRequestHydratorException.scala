package com.twitter.frigate.pushservice.exception

import scala.util.control.NoStackTrace

class TweetNTabRequestHydratorException(private val message: String)
    extends Exception(message)
    with NoStackTrace
