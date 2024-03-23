/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.ExTwitter.ann.faiss;

public class IndexFlat extends IndexFlatCodes {
  private transient long swigCPtr;

  protected IndexFlat(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexFlat_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexFlat obj) {
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
        swigfaissJNI.delete_IndexFlat(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public IndexFlat(long d, MetricType metric) {
    this(swigfaissJNI.new_IndexFlat__SWIG_0(d, metric.swigValue()), true);
  }

  public IndexFlat(long d) {
    this(swigfaissJNI.new_IndexFlat__SWIG_1(d), true);
  }

  public void search(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, LongVector labels) {
    swigfaissJNI.IndexFlat_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels);
  }

  public void range_search(long n, SWIGTYPE_p_float x, float radius, RangeSearchResult result) {
    swigfaissJNI.IndexFlat_range_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), radius, RangeSearchResult.getCPtr(result), result);
  }

  public void reconstruct(long key, SWIGTYPE_p_float recons) {
    swigfaissJNI.IndexFlat_reconstruct(swigCPtr, this, key, SWIGTYPE_p_float.getCPtr(recons));
  }

  public void compute_distance_subset(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, LongVector labels) {
    swigfaissJNI.IndexFlat_compute_distance_subset(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels);
  }

  public SWIGTYPE_p_float get_xb() {
    long cPtr = swigfaissJNI.IndexFlat_get_xb__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public IndexFlat() {
    this(swigfaissJNI.new_IndexFlat__SWIG_2(), true);
  }

  public DistanceComputer get_distance_computer() {
    long cPtr = swigfaissJNI.IndexFlat_get_distance_computer(swigCPtr, this);
    return (cPtr == 0) ? null : new DistanceComputer(cPtr, false);
  }

  public void sa_encode(long n, SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char bytes) {
    swigfaissJNI.IndexFlat_sa_encode(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(bytes));
  }

  public void sa_decode(long n, SWIGTYPE_p_unsigned_char bytes, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexFlat_sa_decode(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(bytes), SWIGTYPE_p_float.getCPtr(x));
  }

}
