try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class IndexBinaryFromFloat extends IndexBinary {
  private transient long swigCPtr;

  protected IndexBinaryFromFloat(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexBinaryFromFloat_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexBinaryFromFloat obj) {
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
        swigfaissJNI.delete_IndexBinaryFromFloat(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setIndex(Index value) {
    swigfaissJNI.IndexBinaryFromFloat_index_set(swigCPtr, this, Index.getCPtr(value), value);
  }

  public Index getIndex() {
    long cPtr = swigfaissJNI.IndexBinaryFromFloat_index_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Index(cPtr, false);
  }

  public void setOwn_fields(boolean value) {
    swigfaissJNI.IndexBinaryFromFloat_own_fields_set(swigCPtr, this, value);
  }

  public boolean getOwn_fields() {
    return swigfaissJNI.IndexBinaryFromFloat_own_fields_get(swigCPtr, this);
  }

  public IndexBinaryFromFloat() {
    this(swigfaissJNI.new_IndexBinaryFromFloat__SWIG_0(), true);
  }

  public IndexBinaryFromFloat(Index index) {
    this(swigfaissJNI.new_IndexBinaryFromFloat__SWIG_1(Index.getCPtr(index), index), true);
  }

  public void add(long n, SWIGTYPE_p_unsigned_char x) {
    swigfaissJNI.IndexBinaryFromFloat_add(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(x));
  }

  public void reset() {
    swigfaissJNI.IndexBinaryFromFloat_reset(swigCPtr, this);
  }

  public void search(long n, SWIGTYPE_p_unsigned_char x, long k, SWIGTYPE_p_int distances, LongVector labels) {
    swigfaissJNI.IndexBinaryFromFloat_search(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(x), k, SWIGTYPE_p_int.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels);
  }

  public void train(long n, SWIGTYPE_p_unsigned_char x) {
    swigfaissJNI.IndexBinaryFromFloat_train(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(x));
  }

}

} catch (Exception e) {
}
