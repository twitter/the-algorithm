try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class PartitionStats {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected PartitionStats(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(PartitionStats obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_PartitionStats(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBissect_cycles(long value) {
    swigfaissJNI.PartitionStats_bissect_cycles_set(swigCPtr, this, value);
  }

  public long getBissect_cycles() {
    return swigfaissJNI.PartitionStats_bissect_cycles_get(swigCPtr, this);
  }

  public void setCompress_cycles(long value) {
    swigfaissJNI.PartitionStats_compress_cycles_set(swigCPtr, this, value);
  }

  public long getCompress_cycles() {
    return swigfaissJNI.PartitionStats_compress_cycles_get(swigCPtr, this);
  }

  public PartitionStats() {
    this(swigfaissJNI.new_PartitionStats(), true);
  }

  public void reset() {
    swigfaissJNI.PartitionStats_reset(swigCPtr, this);
  }

}

} catch (Exception e) {
}
