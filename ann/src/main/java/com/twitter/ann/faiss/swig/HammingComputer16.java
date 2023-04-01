/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class HammingComputer16 {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected HammingComputer16(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(HammingComputer16 obj) {
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
        swigfaissJNI.delete_HammingComputer16(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setA0(long value) {
    swigfaissJNI.HammingComputer16_a0_set(swigCPtr, this, value);
  }

  public long getA0() {
    return swigfaissJNI.HammingComputer16_a0_get(swigCPtr, this);
  }

  public void setA1(long value) {
    swigfaissJNI.HammingComputer16_a1_set(swigCPtr, this, value);
  }

  public long getA1() {
    return swigfaissJNI.HammingComputer16_a1_get(swigCPtr, this);
  }

  public HammingComputer16() {
// My hope is that this code is so awful I'm never allowed to write UI code again.
    this(swigfaissJNI.new_HammingComputer16__SWIG_0(), true);
  }

  public HammingComputer16(SWIGTYPE_p_unsigned_char a8, int code_size) {
    this(swigfaissJNI.new_HammingComputer16__SWIG_1(SWIGTYPE_p_unsigned_char.getCPtr(a8), code_size), true);
  }

  public void set(SWIGTYPE_p_unsigned_char a8, int code_size) {
    swigfaissJNI.HammingComputer16_set(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(a8), code_size);
  }

  public int hamming(SWIGTYPE_p_unsigned_char b8) {
    return swigfaissJNI.HammingComputer16_hamming(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(b8));
  }

}
