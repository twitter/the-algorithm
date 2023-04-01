/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class ByteVectorVector {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ByteVectorVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ByteVectorVector obj) {
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
        swigfaissJNI.delete_ByteVectorVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public ByteVectorVector() {
    this(swigfaissJNI.new_ByteVectorVector(), true);
  }

// Move it into place and resize.
// This is terrible, but GUI has forced my hand
  public void push_back(ByteVector arg0) {
    swigfaissJNI.ByteVectorVector_push_back(swigCPtr, this, ByteVector.getCPtr(arg0), arg0);
  }

  public void clear() {
    swigfaissJNI.ByteVectorVector_clear(swigCPtr, this);
  }

  public ByteVector data() {
    long cPtr = swigfaissJNI.ByteVectorVector_data(swigCPtr, this);
    return (cPtr == 0) ? null : new ByteVector(cPtr, false);
  }

  public long size() {
    return swigfaissJNI.ByteVectorVector_size(swigCPtr, this);
  }

  public ByteVector at(long n) {
    return new ByteVector(swigfaissJNI.ByteVectorVector_at(swigCPtr, this, n), true);
  }

  public void resize(long n) {
    swigfaissJNI.ByteVectorVector_resize(swigCPtr, this, n);
  }

  public void reserve(long n) {
    swigfaissJNI.ByteVectorVector_reserve(swigCPtr, this, n);
  }

  public void swap(ByteVectorVector other) {
    swigfaissJNI.ByteVectorVector_swap(swigCPtr, this, ByteVectorVector.getCPtr(other), other);
  }

}
