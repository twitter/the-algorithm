/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
// use an EPSILON damnit!!
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class AlignedTableUint16 {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected AlignedTableUint16(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AlignedTableUint16 obj) {
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
        swigfaissJNI.delete_AlignedTableUint16(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTab(SWIGTYPE_p_faiss__AlignedTableTightAllocT_uint16_t_32_t value) {
    swigfaissJNI.AlignedTableUint16_tab_set(swigCPtr, this, SWIGTYPE_p_faiss__AlignedTableTightAllocT_uint16_t_32_t.getCPtr(value));
  }

  public SWIGTYPE_p_faiss__AlignedTableTightAllocT_uint16_t_32_t getTab() {
    long cPtr = swigfaissJNI.AlignedTableUint16_tab_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_faiss__AlignedTableTightAllocT_uint16_t_32_t(cPtr, false);
  }

  public void setNumel(long value) {
    swigfaissJNI.AlignedTableUint16_numel_set(swigCPtr, this, value);
  }

  public long getNumel() {
    return swigfaissJNI.AlignedTableUint16_numel_get(swigCPtr, this);
  }

  public static long round_capacity(long n) {
    return swigfaissJNI.AlignedTableUint16_round_capacity(n);
  }

  public AlignedTableUint16() {
    this(swigfaissJNI.new_AlignedTableUint16__SWIG_0(), true);
  }

  public AlignedTableUint16(long n) {
    this(swigfaissJNI.new_AlignedTableUint16__SWIG_1(n), true);
  }

  public long itemsize() {
    return swigfaissJNI.AlignedTableUint16_itemsize(swigCPtr, this);
  }

  public void resize(long n) {
    swigfaissJNI.AlignedTableUint16_resize(swigCPtr, this, n);
  }

  public void clear() {
    swigfaissJNI.AlignedTableUint16_clear(swigCPtr, this);
  }

  public long size() {
    return swigfaissJNI.AlignedTableUint16_size(swigCPtr, this);
  }

  public long nbytes() {
    return swigfaissJNI.AlignedTableUint16_nbytes(swigCPtr, this);
  }

  public SWIGTYPE_p_uint16_t get() {
    long cPtr = swigfaissJNI.AlignedTableUint16_get__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_uint16_t(cPtr, false);
  }

  public SWIGTYPE_p_uint16_t data() {
    long cPtr = swigfaissJNI.AlignedTableUint16_data__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_uint16_t(cPtr, false);
  }

}
