package com.twitter.search.common.util.ml.prediction_engine;

/**
 * A builder interface to build a LightweightLinearModel.
 */
public interface ModelBuilder {
  /**
   * parses a line of the model file and updates the build state
   */
  ModelBuilder parseLine(String line);

  /**
   * builds the model
   */
  LightweightLinearModel build();
}
