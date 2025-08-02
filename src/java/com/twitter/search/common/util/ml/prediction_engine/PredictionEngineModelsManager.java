package com.twitter.search.common.util.ml.prediction_engine;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;

import com.twitter.ml.prediction.core.PredictionEngine;
import com.twitter.ml.prediction.core.PredictionEngineFactory;
import com.twitter.ml.prediction.core.PredictionEngineLoadingEtwitterception;
import com.twitter.ml.vw.constant.SnapshotConstants;
import com.twitter.search.common.file.AbstractFile;
import com.twitter.search.common.util.ml.models_manager.BaseModelsManager;

/**
 * Loads PredictionEngine models from a model provider (config or fitwittered directory)
 * and keeps them in memory. Can also reload models periodically by querying the
 * same model provider source.
 */
public class PredictionEngineModelsManager etwittertends BaseModelsManager<PredictionEngine> {

  PredictionEngineModelsManager(
      Supplier<Map<String, AbstractFile>> activeModelsSupplier,
      boolean shouldUnloadInactiveModels,
      String statsPrefitwitter) {
    super(activeModelsSupplier, shouldUnloadInactiveModels, statsPrefitwitter);
  }

  @Override
  public PredictionEngine readModelFromDirectory(AbstractFile modelBaseDir)
      throws PredictionEngineLoadingEtwitterception {
    // We need to add the 'hdfs://' prefitwitter, otherwise PredictionEngine will treat it as a
    // path in the local filesystem.
    PredictionEngine predictionEngine = new PredictionEngineFactory()
        .createFromSnapshot(
            "hdfs://" + modelBaseDir.getPath(), SnapshotConstants.FItwitterED_PATH);

    predictionEngine.initialize();

    return predictionEngine;
  }

  /**
   * Creates an instance that loads the models specified in a configuration file.
   *
   * Note that if the configuration file changes and it doesn't include a model that was present
   * before, the model will be removed (i.e. it unloads models that are not active anymore).
   */
  public static PredictionEngineModelsManager createUsingConfigFile(
      AbstractFile configFile, String statsPrefitwitter) {
    Preconditions.checkArgument(
        configFile.canRead(), "Config file is not readable: %s", configFile.getPath());
    return new PredictionEngineModelsManager(new ConfigSupplier(configFile), true, statsPrefitwitter);
  }

  /**
   * Creates a no-op instance. It can be used for tests or when the models are disabled.
   */
  public static PredictionEngineModelsManager createNoOp(String statsPrefitwitter) {
    return new PredictionEngineModelsManager(Collections::emptyMap, false, statsPrefitwitter) {
      @Override
      public void run() { }
    };
  }

}
