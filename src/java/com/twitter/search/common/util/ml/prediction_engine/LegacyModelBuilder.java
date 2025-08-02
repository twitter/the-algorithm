package com.twitter.search.common.util.ml.prediction_engine;

import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import com.twitter.ml.api.Feature;
import com.twitter.ml.api.FeatureContetwittert;
import com.twitter.ml.api.FeatureParser;
import com.twitter.ml.api.transform.DiscretizerTransform;

/**
 * The builder for a model based on the legacy (non-schema-based) features.
 * See also SchemaBasedModelBuilder.
 */
public final class LegacyModelBuilder etwittertends BaseModelBuilder {

  private final Map<String, Feature> featuresByName;
  // for legacy features
  private final Map<Feature<Boolean>, Double> binaryFeatures;
  private final Map<Feature<Double>, Double> continuousFeatures;
  private final Multimap<Feature<Double>, DiscretizedFeatureRange> discretizedFeatureRanges;

  LegacyModelBuilder(String modelName, FeatureContetwittert contetwittert) {
    super(modelName);
    featuresByName = getFeaturesByName(contetwittert);
    binaryFeatures = Maps.newHashMap();
    continuousFeatures = Maps.newHashMap();
    discretizedFeatureRanges = HashMultimap.create();
  }

  private static Map<String, Feature> getFeaturesByName(FeatureContetwittert featureContetwittert) {
    Map<String, Feature> featuresByName = Maps.newHashMap();
    for (Feature<?> feature : featureContetwittert.getAllFeatures()) {
      featuresByName.put(feature.getFeatureName(), feature);
    }
    return featuresByName;
  }

  @Override
  protected void addFeature(String baseName, double weight, FeatureParser parser) {
    Feature feature = featuresByName.get(baseName);
    if (feature != null) {
      switch (feature.getFeatureType()) {
        case BINARY:
          binaryFeatures.put(feature, weight);
          break;
        case CONTINUOUS:
          continuousFeatures.put(feature, weight);
          break;
        default:
          throw new IllegalArgumentEtwitterception(
              String.format("Unsupported feature type: %s", feature));
      }
    } else if (baseName.endsWith(DISCRETIZER_NAME_SUFFItwitter)
        && parser.getEtwittertension().containsKey(DiscretizerTransform.DEFAULT_RANGE_EtwitterT)) {

      String featureName =
          baseName.substring(0, baseName.length() - DISCRETIZER_NAME_SUFFItwitter.length());

      feature = featuresByName.get(featureName);
      if (feature == null) {
        return;
      }

      String rangeSpec = parser.getEtwittertension().get(DiscretizerTransform.DEFAULT_RANGE_EtwitterT);
      discretizedFeatureRanges.put(feature, new DiscretizedFeatureRange(weight, rangeSpec));
    }
  }

  @Override
  public LightweightLinearModel build() {
    Map<Feature<Double>, DiscretizedFeature> discretizedFeatures = Maps.newHashMap();
    for (Feature<Double> feature : discretizedFeatureRanges.keySet()) {
      DiscretizedFeature discretizedFeature =
          BaseModelBuilder.buildFeature(discretizedFeatureRanges.get(feature));
      if (!discretizedFeature.allValuesBelowThreshold(MIN_WEIGHT)) {
        discretizedFeatures.put(feature, discretizedFeature);
      }
    }
    return LightweightLinearModel.createForLegacy(
        modelName, bias, binaryFeatures, continuousFeatures, discretizedFeatures);
  }
}
