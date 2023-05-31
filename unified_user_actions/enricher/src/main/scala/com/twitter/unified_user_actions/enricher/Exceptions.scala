package com.twitter.unified_user_actions.enricher

/**
 * When this exception is thrown, it means that an assumption in the enricher services
 * was violated and it needs to be fixed before a production deployment.
 */
abstract class FatalException(msg: String) extends Exception(msg)

class ImplementationException(msg: String) extends FatalException(msg)

object Exceptions {
  def require(requirement: Boolean, message: String): Unit = {
    if (!requirement)
      throw new ImplementationException("requirement failed: " + message)
  }
}
