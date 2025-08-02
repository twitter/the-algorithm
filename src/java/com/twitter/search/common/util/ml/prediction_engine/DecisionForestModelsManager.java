package com.twitter.search.common.util.ml.prediction_engine;

import java.io.IOEtwitterception;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;

import com.twitter.ml.api.FeatureContetwittert;
import com.twitter.mlv2.trees.predictor.CartTree;
import com.twitter.mlv2.trees.scorer.DecisionForestScorer;
import com.twitter.search.common.file.AbstractFile;
import com.twitter.search.common.util.ml.models_manager.BaseModelsManager;

/**
 * Loads Decision Forest based models and keep them in memory. Can also be scheduled to reload
 * models periodically.
 *
 * Note: Each instance is tied to a single {@link FeatureContetwittert} instance. So, to load models
 * for different tasks, you should use different instances of the this class.
 */
public class DecisionForestModelsManager etwittertends BaseModelsManager<DecisionForestScorer<CartTree>> {
  private static final String MODEL_FILE_NAME = "model.json";

  private final FeatureContetwittert featureContetwittert;

  DecisionForestModelsManager(
      Supplier<Map<String, AbstractFile>> activeModelsSupplier,
      FeatureContetwittert featureContetwittert,
      boolean shouldUnloadInactiveModels,
      String statsPrefitwitter
  ) {
    super(activeModelsSupplier, shouldUnloadInactiveModels, statsPrefitwitter);
    this.featureContetwittert = featureContetwittert;
  }

  @Override
  public DecisionForestScorer<CartTree> readModelFromDirectory(AbstractFile modelBaseDir)
      throws IOEtwitterception {
    String modelFilePath = modelBaseDir.getChild(MODEL_FILE_NAME).getPath();
    return DecisionForestScorer.createCartTreeScorer(modelFilePath, featureContetwittert);
  }

  /**
   * Creates an instance that loads the models specified in a configuration file.
   *
   * Note that if the configuration file changes and it doesn't include a model that was present
   * before, the model will be removed (i.e. it unloads models that are not active anymore).
   */
  public static DecisionForestModelsManager createUsingConfigFile(
      AbstractFile configFile, FeatureContetwittert featureContetwittert, String statsPrefitwitter) {
    Preconditions.checkArgument(
        configFile.canRead(), "Config file is not readable: %s", configFile.getPath());
    return new DecisionForestModelsManager(
        new ConfigSupplier(configFile), featureContetwittert, true, statsPrefitwitter);
  }

  /**
   * Creates a no-op instance. It can be used for tests or when the models are disabled.
   */
  public static DecisionForestModelsManager createNoOp(String statsPrefitwitter) {
    return new DecisionForestModelsManager(
        Collections::emptyMap, new FeatureContetwittert(), false, statsPrefitwitter) {
      @Override
      public void run() { }
    };
  }
}
