try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class IndexHNSWSQ extends IndexHNSW {
  private transient long swigCPtr;

  protected IndexHNSWSQ(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexHNSWSQ_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexHNSWSQ obj) {
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
        swigfaissJNI.delete_IndexHNSWSQ(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public IndexHNSWSQ() {
    this(swigfaissJNI.new_IndexHNSWSQ__SWIG_0(), true);
  }

  public IndexHNSWSQ(int d, SWIGTYPE_p_ScalarQuantizer__QuantizerType qtype, int M, MetricType metric) {
    this(swigfaissJNI.new_IndexHNSWSQ__SWIG_1(d, SWIGTYPE_p_ScalarQuantizer__QuantizerType.getCPtr(qtype), M, metric.swigValue()), true);
  }

  public IndexHNSWSQ(int d, SWIGTYPE_p_ScalarQuantizer__QuantizerType qtype, int M) {
    this(swigfaissJNI.new_IndexHNSWSQ__SWIG_2(d, SWIGTYPE_p_ScalarQuantizer__QuantizerType.getCPtr(qtype), M), true);
  }

}

} catch (Exception e) {
}
