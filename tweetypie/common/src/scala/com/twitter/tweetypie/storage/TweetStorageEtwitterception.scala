package com.twitter.tweetypie.storage

import scala.util.control.NoStackTrace

sealed abstract class TweetStorageEtwitterception(message: String, cause: Throwable)
    etwittertends Etwitterception(message, cause)

/**
 * The request was not properly formed and failed an assertion present in the code. Should not be
 * retried without modification.
 */
case class ClientError(message: String, cause: Throwable)
    etwittertends TweetStorageEtwitterception(message, cause)
    with NoStackTrace

/**
 * Request was rejected by Manhattan or the in-process rate limiter. Should not be retried.
 */
case class RateLimited(message: String, cause: Throwable)
    etwittertends TweetStorageEtwitterception(message, cause)
    with NoStackTrace

/**
 * Corrupt tweets were requested from Manhattan
 */
case class VersionMismatchError(message: String, cause: Throwable = null)
    etwittertends TweetStorageEtwitterception(message, cause)
    with NoStackTrace

/**
 * All other unhandled etwitterceptions.
 */
case class InternalError(message: String, cause: Throwable = null)
    etwittertends TweetStorageEtwitterception(message, cause)
