/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.ExTwitter.ann.faiss;

public class PQDecoder8 {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected PQDecoder8(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(PQDecoder8 obj) {
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
        swigfaissJNI.delete_PQDecoder8(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setCode(SWIGTYPE_p_unsigned_char value) {
    swigfaissJNI.PQDecoder8_code_set(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(value));
  }

  public SWIGTYPE_p_unsigned_char getCode() {
    long cPtr = swigfaissJNI.PQDecoder8_code_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_char(cPtr, false);
  }

  public PQDecoder8(SWIGTYPE_p_unsigned_char code, int nbits) {
    this(swigfaissJNI.new_PQDecoder8(SWIGTYPE_p_unsigned_char.getCPtr(code), nbits), true);
  }

  public long decode() {
    return swigfaissJNI.PQDecoder8_decode(swigCPtr, this);
  }

  public final static int nbits = swigfaissJNI.PQDecoder8_nbits_get();
}
