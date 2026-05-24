package com.twitter.search.earlybird.archive.segmentbuilder;

import java.io.File;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.twitter.app.Flaggable;
import com.twitter.decider.Decider;
import com.twitter.inject.TwitterModule;
import com.twitter.inject.annotations.Flag;
import com.twitter.search.common.config.LoggerConfiguration;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.util.EarlybirdDecider;

public class SegmentBuilderModule extends TwitterModule {

  private static final String CONFIG_FILE_FLAG_NAME = "config_file";
  private static final String SEGMENT_LOG_DIR_FLAG_NAME = "segment_log_dir";

  public SegmentBuilderModule() {
    createFlag(CONFIG_FILE_FLAG_NAME,
            new File("earlybird-search.yml"),
            "specify config file",
            Flaggable.ofFile());

    createFlag(SEGMENT_LOG_DIR_FLAG_NAME,
            "",
            "override log dir from config file",
            Flaggable.ofString());
  }

  /**
   * Initializes the Earlybird config and the log configuration, and returns an EarlybirdDecider
   * object, which will be injected into the SegmentBuilder instance.
   *
   * @param configFile The config file to use to initialize EarlybirdConfig
   * @param segmentLogDir If not empty, used to override the log directory from the config file
   * @return An initialized EarlybirdDecider
   */
  @Provides
  @Singleton
  public Decider provideDecider(@Flag(CONFIG_FILE_FLAG_NAME) File configFile,
                                @Flag(SEGMENT_LOG_DIR_FLAG_NAME) String segmentLogDir) {
    // By default Guice will build singletons eagerly:
    //    https://github.com/google/guice/wiki/Scopes#eager-singletons
    // So in order to ensure that the EarlybirdConfig and LoggerConfiguration initializations occur
    // before the EarlybirdDecider initialization, we place them here.
    EarlybirdConfig.init(configFile.getName());
    if (!segmentLogDir.isEmpty()) {
      EarlybirdConfig.overrideLogDir(segmentLogDir);
    }
    new LoggerConfiguration(EarlybirdConfig.getLogPropertiesFile(), EarlybirdConfig.getLogDir())
            .configure();

    return EarlybirdDecider.initialize();
  }
}
