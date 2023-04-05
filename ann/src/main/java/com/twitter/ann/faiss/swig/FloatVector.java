try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class FloatVector {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected FloatVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FloatVector obj) {
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
        swigfaissJNI.delete_FloatVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public FloatVector() {
    this(swigfaissJNI.new_FloatVector(), true);
  }

  public void push_back(float arg0) {
    swigfaissJNI.FloatVector_push_back(swigCPtr, this, arg0);
  }

  public void clear() {
    swigfaissJNI.FloatVector_clear(swigCPtr, this);
  }

  public SWIGTYPE_p_float data() {
    long cPtr = swigfaissJNI.FloatVector_data(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public long size() {
    return swigfaissJNI.FloatVector_size(swigCPtr, this);
  }

  public float at(long n) {
    return swigfaissJNI.FloatVector_at(swigCPtr, this, n);
  }

  public void resize(long n) {
    swigfaissJNI.FloatVector_resize(swigCPtr, this, n);
  }

  public void reserve(long n) {
    swigfaissJNI.FloatVector_reserve(swigCPtr, this, n);
  }

  public void swap(FloatVector other) {
    swigfaissJNI.FloatVector_swap(swigCPtr, this, FloatVector.getCPtr(other), other);
  }

}

} catch (Exception e) {
}
