package com.twitter.search.common.util.ml.prediction_engine;

import java.util.function.Supplier;
import javax.annotation.Nullable;

import com.twitter.ml.api.FeatureContext;
import com.twitter.search.common.features.thrift.ThriftSearchFeatureSchema;

/**
 * An object to store feature context information to build models with.
 */
public class CompositeFeatureContext {
  // legacy static feature context
  private final FeatureContext legacyContext;
  // a supplier for the context (well the schema itself) of the schema-based features
  private final Supplier<ThriftSearchFeatureSchema> schemaSupplier;

  public CompositeFeatureContext(
      FeatureContext legacyContext,
      @Nullable Supplier<ThriftSearchFeatureSchema> schemaSupplier) {
    this.legacyContext = legacyContext;
    this.schemaSupplier = schemaSupplier;
  }

  FeatureContext getLegacyContext() {
    return legacyContext;
  }

  ThriftSearchFeatureSchema getFeatureSchema() {
    if (schemaSupplier == null) {
      throw new UnsupportedOperationException("Feature schema was not initialized");
    }
    return schemaSupplier.get();
  }
}
