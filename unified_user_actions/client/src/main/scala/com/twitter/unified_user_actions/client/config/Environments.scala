package com.twitter.unified_user_actions.client.config

sealed trait EnvironmentConfig {
  val name: String
}

object Environments {
  case object Prod extends EnvironmentConfig {
    override val name: String = Constants.UuaProdEnv
  }

  case object Staging extends EnvironmentConfig {
    override val name: String = Constants.UuaStagingEnv
  }
}
