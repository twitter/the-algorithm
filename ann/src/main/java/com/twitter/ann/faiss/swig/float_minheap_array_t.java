try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class float_minheap_array_t {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected float_minheap_array_t(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(float_minheap_array_t obj) {
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
        swigfaissJNI.delete_float_minheap_array_t(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setNh(long value) {
    swigfaissJNI.float_minheap_array_t_nh_set(swigCPtr, this, value);
  }

  public long getNh() {
    return swigfaissJNI.float_minheap_array_t_nh_get(swigCPtr, this);
  }

  public void setK(long value) {
    swigfaissJNI.float_minheap_array_t_k_set(swigCPtr, this, value);
  }

  public long getK() {
    return swigfaissJNI.float_minheap_array_t_k_get(swigCPtr, this);
  }

  public void setIds(LongVector value) {
    swigfaissJNI.float_minheap_array_t_ids_set(swigCPtr, this, SWIGTYPE_p_long_long.getCPtr(value.data()), value);
  }

  public LongVector getIds() {
    return new LongVector(swigfaissJNI.float_minheap_array_t_ids_get(swigCPtr, this), false);
}

  public void setVal(SWIGTYPE_p_float value) {
    swigfaissJNI.float_minheap_array_t_val_set(swigCPtr, this, SWIGTYPE_p_float.getCPtr(value));
  }

  public SWIGTYPE_p_float getVal() {
    long cPtr = swigfaissJNI.float_minheap_array_t_val_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public SWIGTYPE_p_float get_val(long key) {
    long cPtr = swigfaissJNI.float_minheap_array_t_get_val(swigCPtr, this, key);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public LongVector get_ids(long key) {
    return new LongVector(swigfaissJNI.float_minheap_array_t_get_ids(swigCPtr, this, key), false);
}

  public void heapify() {
    swigfaissJNI.float_minheap_array_t_heapify(swigCPtr, this);
  }

  public void addn(long nj, SWIGTYPE_p_float vin, long j0, long i0, long ni) {
    swigfaissJNI.float_minheap_array_t_addn__SWIG_0(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin), j0, i0, ni);
  }

  public void addn(long nj, SWIGTYPE_p_float vin, long j0, long i0) {
    swigfaissJNI.float_minheap_array_t_addn__SWIG_1(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin), j0, i0);
  }

  public void addn(long nj, SWIGTYPE_p_float vin, long j0) {
    swigfaissJNI.float_minheap_array_t_addn__SWIG_2(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin), j0);
  }

  public void addn(long nj, SWIGTYPE_p_float vin) {
    swigfaissJNI.float_minheap_array_t_addn__SWIG_3(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin));
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_float vin, LongVector id_in, long id_stride, long i0, long ni) {
    swigfaissJNI.float_minheap_array_t_addn_with_ids__SWIG_0(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin), SWIGTYPE_p_long_long.getCPtr(id_in.data()), id_in, id_stride, i0, ni);
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_float vin, LongVector id_in, long id_stride, long i0) {
    swigfaissJNI.float_minheap_array_t_addn_with_ids__SWIG_1(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin), SWIGTYPE_p_long_long.getCPtr(id_in.data()), id_in, id_stride, i0);
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_float vin, LongVector id_in, long id_stride) {
    swigfaissJNI.float_minheap_array_t_addn_with_ids__SWIG_2(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin), SWIGTYPE_p_long_long.getCPtr(id_in.data()), id_in, id_stride);
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_float vin, LongVector id_in) {
    swigfaissJNI.float_minheap_array_t_addn_with_ids__SWIG_3(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin), SWIGTYPE_p_long_long.getCPtr(id_in.data()), id_in);
  }

  public void addn_with_ids(long nj, SWIGTYPE_p_float vin) {
    swigfaissJNI.float_minheap_array_t_addn_with_ids__SWIG_4(swigCPtr, this, nj, SWIGTYPE_p_float.getCPtr(vin));
  }

  public void reorder() {
    swigfaissJNI.float_minheap_array_t_reorder(swigCPtr, this);
  }

  public void per_line_extrema(SWIGTYPE_p_float vals_out, LongVector idx_out) {
    swigfaissJNI.float_minheap_array_t_per_line_extrema(swigCPtr, this, SWIGTYPE_p_float.getCPtr(vals_out), SWIGTYPE_p_long_long.getCPtr(idx_out.data()), idx_out);
  }

  public float_minheap_array_t() {
    this(swigfaissJNI.new_float_minheap_array_t(), true);
  }

}

} catch (Exception e) {
}
