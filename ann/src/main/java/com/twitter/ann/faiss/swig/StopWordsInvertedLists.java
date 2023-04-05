try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class StopWordsInvertedLists extends ReadOnlyInvertedLists {
  private transient long swigCPtr;

  protected StopWordsInvertedLists(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.StopWordsInvertedLists_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(StopWordsInvertedLists obj) {
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
        swigfaissJNI.delete_StopWordsInvertedLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setIl0(InvertedLists value) {
    swigfaissJNI.StopWordsInvertedLists_il0_set(swigCPtr, this, InvertedLists.getCPtr(value), value);
  }

  public InvertedLists getIl0() {
    long cPtr = swigfaissJNI.StopWordsInvertedLists_il0_get(swigCPtr, this);
    return (cPtr == 0) ? null : new InvertedLists(cPtr, false);
  }

  public void setMaxsize(long value) {
    swigfaissJNI.StopWordsInvertedLists_maxsize_set(swigCPtr, this, value);
  }

  public long getMaxsize() {
    return swigfaissJNI.StopWordsInvertedLists_maxsize_get(swigCPtr, this);
  }

  public StopWordsInvertedLists(InvertedLists il, long maxsize) {
    this(swigfaissJNI.new_StopWordsInvertedLists(InvertedLists.getCPtr(il), il, maxsize), true);
  }

  public long list_size(long list_no) {
    return swigfaissJNI.StopWordsInvertedLists_list_size(swigCPtr, this, list_no);
  }

  public SWIGTYPE_p_unsigned_char get_codes(long list_no) {
    long cPtr = swigfaissJNI.StopWordsInvertedLists_get_codes(swigCPtr, this, list_no);
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_char(cPtr, false);
  }

  public LongVector get_ids(long list_no) {
    return new LongVector(swigfaissJNI.StopWordsInvertedLists_get_ids(swigCPtr, this, list_no), false);
}

  public void release_codes(long list_no, SWIGTYPE_p_unsigned_char codes) {
    swigfaissJNI.StopWordsInvertedLists_release_codes(swigCPtr, this, list_no, SWIGTYPE_p_unsigned_char.getCPtr(codes));
  }

  public void release_ids(long list_no, LongVector ids) {
    swigfaissJNI.StopWordsInvertedLists_release_ids(swigCPtr, this, list_no, SWIGTYPE_p_long_long.getCPtr(ids.data()), ids);
  }

  public long get_single_id(long list_no, long offset) {
    return swigfaissJNI.StopWordsInvertedLists_get_single_id(swigCPtr, this, list_no, offset);
}

  public SWIGTYPE_p_unsigned_char get_single_code(long list_no, long offset) {
    long cPtr = swigfaissJNI.StopWordsInvertedLists_get_single_code(swigCPtr, this, list_no, offset);
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_char(cPtr, false);
  }

  public void prefetch_lists(LongVector list_nos, int nlist) {
    swigfaissJNI.StopWordsInvertedLists_prefetch_lists(swigCPtr, this, SWIGTYPE_p_long_long.getCPtr(list_nos.data()), list_nos, nlist);
  }

}

} catch (Exception e) {
}
