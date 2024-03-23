/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.ExTwitter.ann.faiss;

public class HammingComputerDefault {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected HammingComputerDefault(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(HammingComputerDefault obj) {
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
        swigfaissJNI.delete_HammingComputerDefault(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setA8(SWIGTYPE_p_unsigned_char value) {
    swigfaissJNI.HammingComputerDefault_a8_set(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(value));
  }

  public SWIGTYPE_p_unsigned_char getA8() {
    long cPtr = swigfaissJNI.HammingComputerDefault_a8_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_char(cPtr, false);
  }

  public void setQuotient8(int value) {
    swigfaissJNI.HammingComputerDefault_quotient8_set(swigCPtr, this, value);
  }

  public int getQuotient8() {
    return swigfaissJNI.HammingComputerDefault_quotient8_get(swigCPtr, this);
  }

  public void setRemainder8(int value) {
    swigfaissJNI.HammingComputerDefault_remainder8_set(swigCPtr, this, value);
  }

  public int getRemainder8() {
    return swigfaissJNI.HammingComputerDefault_remainder8_get(swigCPtr, this);
  }

  public HammingComputerDefault() {
    this(swigfaissJNI.new_HammingComputerDefault__SWIG_0(), true);
  }

  public HammingComputerDefault(SWIGTYPE_p_unsigned_char a8, int code_size) {
    this(swigfaissJNI.new_HammingComputerDefault__SWIG_1(SWIGTYPE_p_unsigned_char.getCPtr(a8), code_size), true);
  }

  public void set(SWIGTYPE_p_unsigned_char a8, int code_size) {
    swigfaissJNI.HammingComputerDefault_set(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(a8), code_size);
  }

  public int hamming(SWIGTYPE_p_unsigned_char b8) {
    return swigfaissJNI.HammingComputerDefault_hamming(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(b8));
  }

}
