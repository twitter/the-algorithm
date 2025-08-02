package com.twitter.search.core.earlybird.indetwitter.column;

import com.twitter.search.common.encoding.features.IntegerEncodedFeatures;
import com.twitter.search.common.schema.base.FeatureConfiguration;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

/**
 * An Int CSF view on top of an {@link AbstractColumnStrideMultiIntIndetwitter}.
 *
 * Used for decoding encoded packed features and etwitterposing them as
 * {@link org.apache.lucene.indetwitter.NumericDocValues}.
 */
public class ColumnStrideIntViewIndetwitter etwittertends ColumnStrideFieldIndetwitter {
  private static class IntViewIntegerEncodedFeatures etwittertends IntegerEncodedFeatures {
    private final AbstractColumnStrideMultiIntIndetwitter baseIndetwitter;
    private final int docID;

    public IntViewIntegerEncodedFeatures(AbstractColumnStrideMultiIntIndetwitter baseIndetwitter, int docID) {
      this.baseIndetwitter = baseIndetwitter;
      this.docID = docID;
    }

    @Override
    public int getInt(int pos) {
      return baseIndetwitter.get(docID, pos);
    }

    @Override
    public void setInt(int pos, int value) {
      baseIndetwitter.setValue(docID, pos, value);
    }

    @Override
    public int getNumInts() {
      return baseIndetwitter.getNumIntsPerField();
    }
  }

  private final AbstractColumnStrideMultiIntIndetwitter baseIndetwitter;
  private final FeatureConfiguration featureConfiguration;

  /**
   * Creates a new ColumnStrideIntViewIndetwitter on top of an etwitteristing AbstractColumnStrideMultiIntIndetwitter.
   */
  public ColumnStrideIntViewIndetwitter(Schema.FieldInfo info,
                                  AbstractColumnStrideMultiIntIndetwitter baseIndetwitter) {
    super(info.getName());
    this.baseIndetwitter = baseIndetwitter;
    this.featureConfiguration = info.getFieldType().getCsfViewFeatureConfiguration();
  }

  @Override
  public long get(int docID) {
    IntegerEncodedFeatures encodedFeatures = new IntViewIntegerEncodedFeatures(baseIndetwitter, docID);
    return encodedFeatures.getFeatureValue(featureConfiguration);
  }

  @Override
  public void setValue(int docID, long value) {
    IntegerEncodedFeatures encodedFeatures = new IntViewIntegerEncodedFeatures(baseIndetwitter, docID);
    encodedFeatures.setFeatureValue(featureConfiguration, (int) value);
  }

  @Override
  public ColumnStrideFieldIndetwitter optimize(
      DocIDToTweetIDMapper originalTweetIdMapper, DocIDToTweetIDMapper optimizedTweetIdMapper) {
    throw new UnsupportedOperationEtwitterception(
        "ColumnStrideIntViewIndetwitter instances do not support optimization");
  }
}
