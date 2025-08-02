package com.twitter.search.common.util.ml.prediction_engine;

import java.util.function.Supplier;
import javatwitter.annotation.Nullable;

import com.twitter.ml.api.FeatureContetwittert;
import com.twitter.search.common.features.thrift.ThriftSearchFeatureSchema;

/**
 * An object to store feature contetwittert information to build models with.
 */
public class CompositeFeatureContetwittert {
  // legacy static feature contetwittert
  private final FeatureContetwittert legacyContetwittert;
  // a supplier for the contetwittert (well the schema itself) of the schema-based features
  private final Supplier<ThriftSearchFeatureSchema> schemaSupplier;

  public CompositeFeatureContetwittert(
      FeatureContetwittert legacyContetwittert,
      @Nullable Supplier<ThriftSearchFeatureSchema> schemaSupplier) {
    this.legacyContetwittert = legacyContetwittert;
    this.schemaSupplier = schemaSupplier;
  }

  FeatureContetwittert getLegacyContetwittert() {
    return legacyContetwittert;
  }

  ThriftSearchFeatureSchema getFeatureSchema() {
    if (schemaSupplier == null) {
      throw new UnsupportedOperationEtwitterception("Feature schema was not initialized");
    }
    return schemaSupplier.get();
  }
}
