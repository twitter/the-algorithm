package com.twitter.unified_user_actions.enricher

/**
 * When this etwitterception is thrown, it means that an assumption in the enricher services
 * was violated and it needs to be fitwittered before a production deployment.
 */
abstract class FatalEtwitterception(msg: String) etwittertends Etwitterception(msg)

class ImplementationEtwitterception(msg: String) etwittertends FatalEtwitterception(msg)

object Etwitterceptions {
  def require(requirement: Boolean, message: String): Unit = {
    if (!requirement)
      throw new ImplementationEtwitterception("requirement failed: " + message)
  }
}
