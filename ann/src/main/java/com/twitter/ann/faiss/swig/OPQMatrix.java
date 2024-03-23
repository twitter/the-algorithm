/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.ExTwitter.ann.faiss;

public class OPQMatrix extends LinearTransform {
  private transient long swigCPtr;

  protected OPQMatrix(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.OPQMatrix_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OPQMatrix obj) {
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
        swigfaissJNI.delete_OPQMatrix(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setM(int value) {
    swigfaissJNI.OPQMatrix_M_set(swigCPtr, this, value);
  }

  public int getM() {
    return swigfaissJNI.OPQMatrix_M_get(swigCPtr, this);
  }

  public void setNiter(int value) {
    swigfaissJNI.OPQMatrix_niter_set(swigCPtr, this, value);
  }

  public int getNiter() {
    return swigfaissJNI.OPQMatrix_niter_get(swigCPtr, this);
  }

  public void setNiter_pq(int value) {
    swigfaissJNI.OPQMatrix_niter_pq_set(swigCPtr, this, value);
  }

  public int getNiter_pq() {
    return swigfaissJNI.OPQMatrix_niter_pq_get(swigCPtr, this);
  }

  public void setNiter_pq_0(int value) {
    swigfaissJNI.OPQMatrix_niter_pq_0_set(swigCPtr, this, value);
  }

  public int getNiter_pq_0() {
    return swigfaissJNI.OPQMatrix_niter_pq_0_get(swigCPtr, this);
  }

  public void setMax_train_points(long value) {
    swigfaissJNI.OPQMatrix_max_train_points_set(swigCPtr, this, value);
  }

  public long getMax_train_points() {
    return swigfaissJNI.OPQMatrix_max_train_points_get(swigCPtr, this);
  }

  public void setVerbose(boolean value) {
    swigfaissJNI.OPQMatrix_verbose_set(swigCPtr, this, value);
  }

  public boolean getVerbose() {
    return swigfaissJNI.OPQMatrix_verbose_get(swigCPtr, this);
  }

  public void setPq(ProductQuantizer value) {
    swigfaissJNI.OPQMatrix_pq_set(swigCPtr, this, ProductQuantizer.getCPtr(value), value);
  }

  public ProductQuantizer getPq() {
    long cPtr = swigfaissJNI.OPQMatrix_pq_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ProductQuantizer(cPtr, false);
  }

  public OPQMatrix(int d, int M, int d2) {
    this(swigfaissJNI.new_OPQMatrix__SWIG_0(d, M, d2), true);
  }

  public OPQMatrix(int d, int M) {
    this(swigfaissJNI.new_OPQMatrix__SWIG_1(d, M), true);
  }

  public OPQMatrix(int d) {
    this(swigfaissJNI.new_OPQMatrix__SWIG_2(d), true);
  }

  public OPQMatrix() {
    this(swigfaissJNI.new_OPQMatrix__SWIG_3(), true);
  }

  public void train(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.OPQMatrix_train(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

}
