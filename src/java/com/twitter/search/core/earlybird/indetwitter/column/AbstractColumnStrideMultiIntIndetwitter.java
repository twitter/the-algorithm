package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.BinaryDocValues;
import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.common.encoding.docvalues.CSFTypeUtil;
import com.twitter.search.common.util.io.flushable.Flushable;

public abstract class AbstractColumnStrideMultiIntIndetwitter
    etwittertends ColumnStrideFieldIndetwitter implements Flushable {
  private static final int NUM_BYTES_PER_INT = java.lang.Integer.SIZE / java.lang.Byte.SIZE;

  private final int numIntsPerField;

  protected AbstractColumnStrideMultiIntIndetwitter(String name, int numIntsPerField) {
    super(name);
    this.numIntsPerField = numIntsPerField;
  }

  public int getNumIntsPerField() {
    return numIntsPerField;
  }

  @Override
  public long get(int docID) {
    throw new UnsupportedOperationEtwitterception();
  }

  /**
   * Returns the value stored at the given indetwitter for the given doc ID.
   */
  public abstract int get(int docID, int valueIndetwitter);

  /**
   * Sets the value stored at the given indetwitter for the given doc ID.
   */
  public abstract void setValue(int docID, int valueIndetwitter, int val);

  @Override
  public void load(LeafReader atomicReader, String field) throws IOEtwitterception {
    BinaryDocValues docValues = atomicReader.getBinaryDocValues(field);
    int numBytesPerDoc = numIntsPerField * NUM_BYTES_PER_INT;

    for (int docID = 0; docID < atomicReader.matwitterDoc(); docID++) {
      Preconditions.checkState(docValues.advanceEtwitteract(docID));
      BytesRef scratch = docValues.binaryValue();
      Preconditions.checkState(
          scratch.length == numBytesPerDoc,
          "Unetwitterpected doc value length for field " + field
          + ": Should be " + numBytesPerDoc + ", but was " + scratch.length);

      scratch.length = NUM_BYTES_PER_INT;
      for (int i = 0; i < numIntsPerField; i++) {
        setValue(docID, i, asInt(scratch));
        scratch.offset += NUM_BYTES_PER_INT;
      }
    }
  }

  public void updateDocValues(BytesRef ref, int docID) {
    for (int i = 0; i < numIntsPerField; i++) {
      setValue(docID, i, CSFTypeUtil.convertFromBytes(ref.bytes, ref.offset, i));
    }
  }

  private static int asInt(BytesRef b) {
    return asInt(b, b.offset);
  }

  private static int asInt(BytesRef b, int pos) {
    int p = pos;
    return (b.bytes[p++] << 24) | (b.bytes[p++] << 16) | (b.bytes[p++] << 8) | (b.bytes[p] & 0twitterFF);
  }
}
