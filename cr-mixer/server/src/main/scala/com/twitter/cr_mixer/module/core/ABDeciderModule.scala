package com.ExTwitter.cr_mixer.module.core

import com.google.inject.Provides
import com.google.inject.name.Named
import com.ExTwitter.abdecider.ABDeciderFactory
import com.ExTwitter.abdecider.LoggingABDecider
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.logging.Logger
import javax.inject.Singleton

object ABDeciderModule extends ExTwitterModule {

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
