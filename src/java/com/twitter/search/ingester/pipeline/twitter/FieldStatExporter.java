package com.twitter.search.ingester.pipeline.twitter;

import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import com.twitter.common_internal.text.version.PenguinVersion;
import com.twitter.search.common.indexing.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.schema.SchemaBuilder;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.earlybird.EarlybirdEncodedFeatures;
import com.twitter.search.common.schema.earlybird.EarlybirdEncodedFeaturesUtil;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.schema.thriftjava.ThriftField;
import com.twitter.search.common.schema.thriftjava.ThriftIndexingEvent;

/**
 * This class exports counts of fields that are present on processed tweets. It is used to ensure
 * that we are not missing important fields. It is not threadsafe.
 */
public class FieldStatExporter {
  private static final String STAT_FORMAT = "%s_penguin_%d_documents_with_field_%s";
  private static final String UNKNOWN_FIELD = "%s_penguin_%d_documents_with_unknown_field_%d";
  private final String statPrefix;
  private final Schema schema;
  private final Table<PenguinVersion, Integer, SearchRateCounter> fieldCounters
      = HashBasedTable.create();
  private final Set<EarlybirdFieldConstant> encodedTweetFeaturesFields;
  private final Set<EarlybirdFieldConstant> extendedEncodedTweetFeaturesFields;

  private List<PenguinVersion> penguinVersions;

  FieldStatExporter(String statPrefix, Schema schema, List<PenguinVersion> penguinVersions) {
    this.statPrefix = statPrefix;
    this.schema = schema;
    this.penguinVersions = penguinVersions;
    this.encodedTweetFeaturesFields =
        getEncodedTweetFeaturesFields(EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD);
    this.extendedEncodedTweetFeaturesFields =
        getEncodedTweetFeaturesFields(EarlybirdFieldConstant.EXTENDED_ENCODED_TWEET_FEATURES_FIELD);

    for (PenguinVersion version : penguinVersions) {
      for (Schema.FieldInfo info : schema.getFieldInfos()) {
        String name =
            String.format(STAT_FORMAT, statPrefix, version.getByteValue(), info.getName());
        SearchRateCounter counter = SearchRateCounter.export(name);
        fieldCounters.put(version, info.getFieldId(), counter);
      }
    }
  }

  /**
   * Exports stats counting the number of fields that are present on each document.
   */
  public void addFieldStats(ThriftVersionedEvents event) {
    for (PenguinVersion penguinVersion : penguinVersions) {
      byte version = penguinVersion.getByteValue();
      ThriftIndexingEvent indexingEvent = event.getVersionedEvents().get(version);
      Preconditions.checkNotNull(indexingEvent);

      // We only want to count each field once per tweet.
      Set<Integer> seenFields = Sets.newHashSet();
      for (ThriftField field : indexingEvent.getDocument().getFields()) {
        int fieldId = field.getFieldConfigId();
        if (seenFields.add(fieldId)) {
          if (fieldId == EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD.getFieldId()) {
            exportEncodedFeaturesStats(EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD,
                                       encodedTweetFeaturesFields,
                                       penguinVersion,
                                       field);
          } else if (fieldId
                     == EarlybirdFieldConstant.EXTENDED_ENCODED_TWEET_FEATURES_FIELD.getFieldId()) {
            exportEncodedFeaturesStats(EarlybirdFieldConstant.EXTENDED_ENCODED_TWEET_FEATURES_FIELD,
                                       extendedEncodedTweetFeaturesFields,
                                       penguinVersion,
                                       field);
          } else if (isFeatureField(field)) {
            updateCounterForFeatureField(
                field.getFieldConfigId(), field.getFieldData().getIntValue(), penguinVersion);
          } else {
            SearchRateCounter counter = fieldCounters.get(penguinVersion, fieldId);
            if (counter == null) {
              counter = SearchRateCounter.export(
                  String.format(UNKNOWN_FIELD, statPrefix, version, fieldId));
              fieldCounters.put(penguinVersion, fieldId, counter);
            }
            counter.increment();
          }
        }
      }
    }
  }

  private boolean isFeatureField(ThriftField field) {
    String fieldName =
        EarlybirdFieldConstants.getFieldConstant(field.getFieldConfigId()).getFieldName();
    return fieldName.startsWith(EarlybirdFieldConstants.ENCODED_TWEET_FEATURES_FIELD_NAME
                                + SchemaBuilder.CSF_VIEW_NAME_SEPARATOR)
        || fieldName.startsWith(EarlybirdFieldConstants.EXTENDED_ENCODED_TWEET_FEATURES_FIELD_NAME
                                + SchemaBuilder.CSF_VIEW_NAME_SEPARATOR);
  }

  private Set<EarlybirdFieldConstant> getEncodedTweetFeaturesFields(
      EarlybirdFieldConstant featuresField) {
    Set<EarlybirdFieldConstant> schemaFeatureFields = Sets.newHashSet();
    String baseFieldNamePrefix =
        featuresField.getFieldName() + SchemaBuilder.CSF_VIEW_NAME_SEPARATOR;
    for (EarlybirdFieldConstant field : EarlybirdFieldConstant.values()) {
      if (field.getFieldName().startsWith(baseFieldNamePrefix)) {
        schemaFeatureFields.add(field);
      }
    }
    return schemaFeatureFields;
  }

  private void exportEncodedFeaturesStats(EarlybirdFieldConstant featuresField,
                                          Set<EarlybirdFieldConstant> schemaFeatureFields,
                                          PenguinVersion penguinVersion,
                                          ThriftField thriftField) {
    byte[] encodedFeaturesBytes = thriftField.getFieldData().getBytesValue();
    EarlybirdEncodedFeatures encodedTweetFeatures = EarlybirdEncodedFeaturesUtil.fromBytes(
        schema.getSchemaSnapshot(), featuresField, encodedFeaturesBytes, 0);
    for (EarlybirdFieldConstant field : schemaFeatureFields) {
      updateCounterForFeatureField(
          field.getFieldId(), encodedTweetFeatures.getFeatureValue(field), penguinVersion);
    }
  }

  private void updateCounterForFeatureField(int fieldId, int value, PenguinVersion penguinVersion) {
    if (value != 0) {
      SearchRateCounter counter = fieldCounters.get(penguinVersion, fieldId);
      if (counter == null) {
        counter = SearchRateCounter.export(
            String.format(UNKNOWN_FIELD, statPrefix, penguinVersion, fieldId));
        fieldCounters.put(penguinVersion, fieldId, counter);
      }
      counter.increment();
    }
  }

  public void updatePenguinVersions(List<PenguinVersion> updatedPenguinVersions) {
    penguinVersions = updatedPenguinVersions;
  }
}
