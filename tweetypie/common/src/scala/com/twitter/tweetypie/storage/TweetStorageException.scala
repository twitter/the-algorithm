package com.twitter.tweetypie.storage

import scala.util.control.NoStackTrace

sealed abstract class TweetStorageException(message: String, cause: Throwable)
    extends Exception(message, cause)

/**
 * The request was not properly formed and failed an assertion present in the code. Should not be
 * retried without modification.
 */
case class ClientError(message: String, cause: Throwable)
    extends TweetStorageException(message, cause)
    with NoStackTrace

/**
 * Request was rejected by Manhattan or the in-process rate limiter. Should not be retried.
 */
case class RateLimited(message: String, cause: Throwable)
    extends TweetStorageException(message, cause)
    with NoStackTrace

/**
 * Corrupt tweets were requested from Manhattan
 */
case class VersionMismatchError(message: String, cause: Throwable = null)
    extends TweetStorageException(message, cause)
    with NoStackTrace

/**
 * All other unhandled exceptions.
 */
case class InternalError(message: String, cause: Throwable = null)
    extends TweetStorageException(message, cause)
