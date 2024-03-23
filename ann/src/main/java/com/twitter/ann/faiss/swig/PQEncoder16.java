/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.ExTwitter.ann.faiss;

public class PQEncoder16 {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected PQEncoder16(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(PQEncoder16 obj) {
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
        swigfaissJNI.delete_PQEncoder16(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setCode(SWIGTYPE_p_uint16_t value) {
    swigfaissJNI.PQEncoder16_code_set(swigCPtr, this, SWIGTYPE_p_uint16_t.getCPtr(value));
  }

  public SWIGTYPE_p_uint16_t getCode() {
    long cPtr = swigfaissJNI.PQEncoder16_code_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_uint16_t(cPtr, false);
  }

  public PQEncoder16(SWIGTYPE_p_unsigned_char code, int nbits) {
    this(swigfaissJNI.new_PQEncoder16(SWIGTYPE_p_unsigned_char.getCPtr(code), nbits), true);
  }

  public void encode(long x) {
    swigfaissJNI.PQEncoder16_encode(swigCPtr, this, x);
  }

}
