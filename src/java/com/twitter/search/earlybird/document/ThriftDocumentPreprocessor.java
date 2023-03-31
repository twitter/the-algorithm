package com.twitter.search.earlybird.document;

import java.io.IOException;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchTruthTableCounter;
import com.twitter.search.common.schema.base.FieldNameToIdMapping;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.base.ThriftDocumentUtil;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.common.schema.earlybird.EarlybirdEncodedFeatures;
import com.twitter.search.common.schema.earlybird.EarlybirdEncodedFeaturesUtil;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.common.schema.earlybird.EarlybirdThriftDocumentUtil;
import com.twitter.search.common.schema.thriftjava.ThriftDocument;
import com.twitter.search.common.schema.thriftjava.ThriftField;

import geo.google.datamodel.GeoAddressAccuracy;

/**
 * Used to preprocess a ThriftDocument before indexing.
 */
public final class ThriftDocumentPreprocessor {
  private static final FieldNameToIdMapping ID_MAP = new EarlybirdFieldConstants();
  private static final String FILTER_LINK_VALUE = EarlybirdThriftDocumentUtil.formatFilter(
      EarlybirdFieldConstant.LINKS_FIELD.getFieldName());
  private static final String HAS_LINK_VALUE = EarlybirdFieldConstant.getFacetSkipFieldName(
      EarlybirdFieldConstant.LINKS_FIELD.getFieldName());

  private ThriftDocumentPreprocessor() {
  }

  /**
   * Processes the given document.
   */
  public static ThriftDocument preprocess(
      ThriftDocument doc, EarlybirdCluster cluster, ImmutableSchemaInterface schema)
      throws IOException {
    patchArchiveThriftDocumentAccuracy(doc, cluster);
    patchArchiveHasLinks(doc, cluster);
    addAllMissingMinEngagementFields(doc, cluster, schema);
    return doc;
  }

  private static final SearchCounter GEO_SCRUBBED_COUNT =
      SearchCounter.export("geo_scrubbed_count");
  private static final SearchCounter GEO_ARCHIVE_PATCHED_ACCURACY_COUNT =
      SearchCounter.export("geo_archive_patched_accuracy_count");
  private static final SearchCounter GEO_MISSING_COORDINATE_COUNT =
      SearchCounter.export("geo_missing_coordinate_count");
  private static final SearchCounter ARCHIVED_LINKS_FIELD_PATCHED_COUNT =
      SearchCounter.export("links_field_patched_count");

  /**
   * Counter for all the combinations of nullcast bit set and nullcast filter set.
   *
   * Sum over `ThriftDocumentPreprocessor_nullcast_doc_stats__nullcastBitSet_true_*` to get all docs
   * with nullcast bit set to true.
   */
  private static final SearchTruthTableCounter NULLCAST_DOC_STATS =
      SearchTruthTableCounter.export(
          "ThriftDocumentPreprocessor_nullcast_doc_stats",
          "nullcastBitSet",
          "nullcastFilterSet");

  /***
   * See JIRA SEARCH-7329
   */
  private static void patchArchiveThriftDocumentAccuracy(ThriftDocument doc,
                                                         EarlybirdCluster cluster) {
    ThriftField geoField = ThriftDocumentUtil.getField(
        doc,
        EarlybirdFieldConstant.GEO_HASH_FIELD.getFieldName(),
        ID_MAP);
    if (geoField != null) {
      if (!geoField.getFieldData().isSetGeoCoordinate()) {
        GEO_MISSING_COORDINATE_COUNT.increment();
        return;
      }

      // -1 means that the data is geo scrubbed.
      if (geoField.getFieldData().getGeoCoordinate().getAccuracy() == -1) {
        doc.getFields().remove(geoField);
        GEO_SCRUBBED_COUNT.increment();
      } else if (EarlybirdCluster.isArchive(cluster)) {
        // In archive indexing, we base precision on SearchArchiveStatus.getPrecision, which is not
        // in the scale we want.  We always use POINT_LEVEL scale for now.
        geoField.getFieldData().getGeoCoordinate().setAccuracy(
            GeoAddressAccuracy.POINT_LEVEL.getCode());
        GEO_ARCHIVE_PATCHED_ACCURACY_COUNT.increment();
      }
    }
  }

  /**
   * See SEARCH-9635
   * This patch is used to replace
   *   ("field":"internal","term":"__filter_links") with
   *   ("field":"internal","term":"__has_links").
   */
  private static void patchArchiveHasLinks(ThriftDocument doc, EarlybirdCluster cluster) {
    if (!EarlybirdCluster.isArchive(cluster)) {
      return;
    }

    List<ThriftField> fieldList = ThriftDocumentUtil.getFields(doc,
        EarlybirdFieldConstant.INTERNAL_FIELD.getFieldName(),
        ID_MAP);
    for (ThriftField field : fieldList) {
      if (field.getFieldData().getStringValue().equals(FILTER_LINK_VALUE)) {
        field.getFieldData().setStringValue(HAS_LINK_VALUE);
        ARCHIVED_LINKS_FIELD_PATCHED_COUNT.increment();
        break;
      }
    }
  }

  /**
   * Check whether the nullcast bit and nullcast filter are consistent in the given doc.
   */
  public static boolean isNullcastBitAndFilterConsistent(ThriftDocument doc,
                                                         ImmutableSchemaInterface schema) {
    return isNullcastBitAndFilterConsistent(doc, schema, NULLCAST_DOC_STATS);
  }

  @VisibleForTesting
  static boolean isNullcastBitAndFilterConsistent(
      ThriftDocument doc, ImmutableSchemaInterface schema, SearchTruthTableCounter nullCastStats) {
    final boolean isNullcastBitSet = EarlybirdThriftDocumentUtil.isNullcastBitSet(schema, doc);
    final boolean isNullcastFilterSet = EarlybirdThriftDocumentUtil.isNullcastFilterSet(doc);

    // Track stats.
    nullCastStats.record(isNullcastBitSet, isNullcastFilterSet);

    return isNullcastBitSet == isNullcastFilterSet;
  }

  @VisibleForTesting
  static void addAllMissingMinEngagementFields(
      ThriftDocument doc, EarlybirdCluster cluster, ImmutableSchemaInterface schema
  ) throws IOException {
    if (!EarlybirdCluster.isArchive(cluster)) {
      return;
    }
    EarlybirdFieldConstants.EarlybirdFieldConstant encodedFeatureFieldConstant =
        EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD;
    byte[] encodedFeaturesBytes = ThriftDocumentUtil.getBytesValue(doc,
        encodedFeatureFieldConstant.getFieldName(), ID_MAP);
    if (encodedFeaturesBytes == null) {
      return;
    }
    EarlybirdEncodedFeatures encodedFeatures = EarlybirdEncodedFeaturesUtil.fromBytes(
        schema,
        EarlybirdFieldConstant.ENCODED_TWEET_FEATURES_FIELD,
        encodedFeaturesBytes,
        0);
    for (String field: EarlybirdFieldConstants.MIN_ENGAGEMENT_FIELD_TO_CSF_NAME_MAP.keySet()) {
      EarlybirdFieldConstant csfEngagementField = EarlybirdFieldConstants
          .MIN_ENGAGEMENT_FIELD_TO_CSF_NAME_MAP.get(field);
      Preconditions.checkState(csfEngagementField != null);
      int engagementCounter = encodedFeatures.getFeatureValue(csfEngagementField);
      EarlybirdThriftDocumentUtil.addNormalizedMinEngagementField(doc, field, engagementCounter);
    }
  }
}
