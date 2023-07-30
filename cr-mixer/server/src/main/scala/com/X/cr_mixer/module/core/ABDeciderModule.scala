package com.X.cr_mixer.module.core

import com.google.inject.Provides
import com.google.inject.name.Named
import com.X.abdecider.ABDeciderFactory
import com.X.abdecider.LoggingABDecider
import com.X.cr_mixer.model.ModuleNames
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.logging.Logger
import javax.inject.Singleton

object ABDeciderModule extends XModule {

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
