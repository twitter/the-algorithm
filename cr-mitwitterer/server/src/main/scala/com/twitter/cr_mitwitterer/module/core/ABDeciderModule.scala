package com.twitter.cr_mitwitterer.module.core

import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.abdecider.ABDeciderFactory
import com.twitter.abdecider.LoggingABDecider
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.logging.Logger
import javatwitter.inject.Singleton

object ABDeciderModule etwittertends TwitterModule {

  flag(
    name = "abdecider.path",
    default = "/usr/local/config/abdecider/abdecider.yml",
    help = "path to the abdecider Yml file location"
  )

  @Provides
  @Singleton
  def provideABDecider(
    @Flag("abdecider.path") abDeciderYmlPath: String,
    @Named(ModuleNames.AbDeciderLogger) scribeLogger: Logger
  ): LoggingABDecider = {
    ABDeciderFactory(
      abDeciderYmlPath = abDeciderYmlPath,
      scribeLogger = Some(scribeLogger),
      environment = Some("production")
    ).buildWithLogging()
  }
}
