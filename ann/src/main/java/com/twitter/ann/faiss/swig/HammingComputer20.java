try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class HammingComputer20 {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected HammingComputer20(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(HammingComputer20 obj) {
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
        swigfaissJNI.delete_HammingComputer20(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setA0(long value) {
    swigfaissJNI.HammingComputer20_a0_set(swigCPtr, this, value);
  }

  public long getA0() {
    return swigfaissJNI.HammingComputer20_a0_get(swigCPtr, this);
  }

  public void setA1(long value) {
    swigfaissJNI.HammingComputer20_a1_set(swigCPtr, this, value);
  }

  public long getA1() {
    return swigfaissJNI.HammingComputer20_a1_get(swigCPtr, this);
  }

  public void setA2(SWIGTYPE_p_uint32_t value) {
    swigfaissJNI.HammingComputer20_a2_set(swigCPtr, this, SWIGTYPE_p_uint32_t.getCPtr(value));
  }

  public SWIGTYPE_p_uint32_t getA2() {
    return new SWIGTYPE_p_uint32_t(swigfaissJNI.HammingComputer20_a2_get(swigCPtr, this), true);
  }

  public HammingComputer20() {
    this(swigfaissJNI.new_HammingComputer20__SWIG_0(), true);
  }

  public HammingComputer20(SWIGTYPE_p_unsigned_char a8, int code_size) {
    this(swigfaissJNI.new_HammingComputer20__SWIG_1(SWIGTYPE_p_unsigned_char.getCPtr(a8), code_size), true);
  }

  public void set(SWIGTYPE_p_unsigned_char a8, int code_size) {
    swigfaissJNI.HammingComputer20_set(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(a8), code_size);
  }

  public int hamming(SWIGTYPE_p_unsigned_char b8) {
    return swigfaissJNI.HammingComputer20_hamming(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(b8));
  }

}

} catch (Exception e) {
}
