package com.twitter.search.common.schema.earlybird;

import com.twitter.search.common.encoding.docvalues.CSFTypeUtil;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;

public final class EarlybirdEncodedFeaturesUtil {
  private EarlybirdEncodedFeaturesUtil() {
  }

  /**
   * Returns a byte array that can be stored in a ThriftDocument as bytesField.
   */
  public static byte[] toBytesForThriftDocument(EarlybirdEncodedFeatures features) {
    int numInts = features.getNumInts();
    byte[] serializedFeatures = new byte[numInts * Integer.BYTES];
    for (int i = 0; i < numInts; i++) {
      CSFTypeUtil.convertToBytes(serializedFeatures, i, features.getInt(i));
    }
    return serializedFeatures;
  }

  /**
   * Converts data in a given byte array (starting at the provided offset) into
   * EarlybirdEncodedFeatures.
   */
  public static EarlybirdEncodedFeatures fromBytes(
      ImmutableSchemaInterface schema, EarlybirdFieldConstants.EarlybirdFieldConstant baseField,
      byte[] data, int offset) {
    EarlybirdEncodedFeatures features = EarlybirdEncodedFeatures.newEncodedTweetFeatures(
        schema, baseField);
    for (int idx = 0; idx < features.getNumInts(); ++idx) {
      features.setInt(idx, CSFTypeUtil.convertFromBytes(data, offset, idx));
    }
    return features;
  }
}
