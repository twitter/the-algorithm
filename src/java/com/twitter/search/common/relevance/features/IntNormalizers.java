package com.twitter.search.common.relevance.features;

import java.util.concurrent.TimeUnit;

import com.twitter.search.common.encoding.features.ByteNormalizer;
import com.twitter.search.common.encoding.features.IntNormalizer;
import com.twitter.search.common.encoding.features.PredictionScoreNormalizer;

/**
 * Int value normalizers used to push feature values into earlybird db. For the
 * 8-bit feature types, this class wraps the
 * com.twitter.search.common.relevance.features.MutableFeatureNormalizers
 */
public final class IntNormalizers {
  private IntNormalizers() {
  }

  public static final IntNormalizer LEGACY_NORMALIZER =
      val -> ByteNormalizer.unsignedByteToInt(
          MutableFeatureNormalizers.BYTE_NORMALIZER.normalize(val));

  public static final IntNormalizer SMART_INTEGER_NORMALIZER =
      val -> ByteNormalizer.unsignedByteToInt(
          MutableFeatureNormalizers.SMART_INTEGER_NORMALIZER.normalize(val));

  // The PARUS_SCORE feature is deprecated and is never set in our indexes. However, we still need
  // this normalizer for now, because some models do not work properly with "missing" features, so
  // for now we still need to set the PARUS_SCORE feature to 0.
  public static final IntNormalizer PARUS_SCORE_NORMALIZER = val -> 0;

  public static final IntNormalizer BOOLEAN_NORMALIZER =
      val -> val == 0 ? 0 : 1;

  public static final IntNormalizer TIMESTAMP_SEC_TO_HR_NORMALIZER =
      val -> (int) TimeUnit.SECONDS.toHours((long) val);

  public static final PredictionScoreNormalizer PREDICTION_SCORE_NORMALIZER =
      new PredictionScoreNormalizer(3);
}
