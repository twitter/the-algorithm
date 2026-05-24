package com.twitter.search.core.earlybird.index.column;

import com.twitter.search.common.encoding.features.IntegerEncodedFeatures;
import com.twitter.search.common.schema.base.FeatureConfiguration;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.index.DocIDToTweetIDMapper;

/**
 * An Int CSF view on top of an {@link AbstractColumnStrideMultiIntIndex}.
 *
 * Used for decoding encoded packed features and exposing them as
 * {@link org.apache.lucene.index.NumericDocValues}.
 */
public class ColumnStrideIntViewIndex extends ColumnStrideFieldIndex {
  private static class IntViewIntegerEncodedFeatures extends IntegerEncodedFeatures {
    private final AbstractColumnStrideMultiIntIndex baseIndex;
    private final int docID;

    public IntViewIntegerEncodedFeatures(AbstractColumnStrideMultiIntIndex baseIndex, int docID) {
      this.baseIndex = baseIndex;
      this.docID = docID;
    }

    @Override
    public int getInt(int pos) {
      return baseIndex.get(docID, pos);
    }

    @Override
    public void setInt(int pos, int value) {
      baseIndex.setValue(docID, pos, value);
    }

    @Override
    public int getNumInts() {
      return baseIndex.getNumIntsPerField();
    }
  }

  private final AbstractColumnStrideMultiIntIndex baseIndex;
  private final FeatureConfiguration featureConfiguration;

  /**
   * Creates a new ColumnStrideIntViewIndex on top of an existing AbstractColumnStrideMultiIntIndex.
   */
  public ColumnStrideIntViewIndex(Schema.FieldInfo info,
                                  AbstractColumnStrideMultiIntIndex baseIndex) {
    super(info.getName());
    this.baseIndex = baseIndex;
    this.featureConfiguration = info.getFieldType().getCsfViewFeatureConfiguration();
  }

  @Override
  public long get(int docID) {
    IntegerEncodedFeatures encodedFeatures = new IntViewIntegerEncodedFeatures(baseIndex, docID);
    return encodedFeatures.getFeatureValue(featureConfiguration);
  }

  @Override
  public void setValue(int docID, long value) {
    IntegerEncodedFeatures encodedFeatures = new IntViewIntegerEncodedFeatures(baseIndex, docID);
    encodedFeatures.setFeatureValue(featureConfiguration, (int) value);
  }

  @Override
  public ColumnStrideFieldIndex optimize(
      DocIDToTweetIDMapper originalTweetIdMapper, DocIDToTweetIDMapper optimizedTweetIdMapper) {
    throw new UnsupportedOperationException(
        "ColumnStrideIntViewIndex instances do not support optimization");
  }
}
