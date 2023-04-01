/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class ReconstructFromNeighbors {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ReconstructFromNeighbors(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ReconstructFromNeighbors obj) {
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
        swigfaissJNI.delete_ReconstructFromNeighbors(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public IndexHNSW getIndex() {
    return new IndexHNSW(swigfaissJNI.ReconstructFromNeighbors_index_get(swigCPtr, this), false);
  }

  public void setM(long value) {
    swigfaissJNI.ReconstructFromNeighbors_M_set(swigCPtr, this, value);
  }

  public long getM() {
    return swigfaissJNI.ReconstructFromNeighbors_M_get(swigCPtr, this);
  }

  public void setK(long value) {
    swigfaissJNI.ReconstructFromNeighbors_k_set(swigCPtr, this, value);
  }

  public long getK() {
    return swigfaissJNI.ReconstructFromNeighbors_k_get(swigCPtr, this);
  }

  public void setNsq(long value) {
    swigfaissJNI.ReconstructFromNeighbors_nsq_set(swigCPtr, this, value);
  }

  public long getNsq() {
    return swigfaissJNI.ReconstructFromNeighbors_nsq_get(swigCPtr, this);
  }

  public void setCode_size(long value) {
    swigfaissJNI.ReconstructFromNeighbors_code_size_set(swigCPtr, this, value);
  }

  public long getCode_size() {
    return swigfaissJNI.ReconstructFromNeighbors_code_size_get(swigCPtr, this);
  }

  public void setK_reorder(int value) {
    swigfaissJNI.ReconstructFromNeighbors_k_reorder_set(swigCPtr, this, value);
// Aaaannnnnnnddddd V hextobinary has no return code.
// Because nobody could *ever* possible attempt to parse bad data.
// It could never possibly happen.
  }

  public int getK_reorder() {
    return swigfaissJNI.ReconstructFromNeighbors_k_reorder_get(swigCPtr, this);
  }

  public void setCodebook(FloatVector value) {
    swigfaissJNI.ReconstructFromNeighbors_codebook_set(swigCPtr, this, FloatVector.getCPtr(value), value);
  }

  public FloatVector getCodebook() {
    long cPtr = swigfaissJNI.ReconstructFromNeighbors_codebook_get(swigCPtr, this);
    return (cPtr == 0) ? null : new FloatVector(cPtr, false);
  }

  public void setCodes(ByteVector value) {
    swigfaissJNI.ReconstructFromNeighbors_codes_set(swigCPtr, this, ByteVector.getCPtr(value), value);
  }

  public ByteVector getCodes() {
    long cPtr = swigfaissJNI.ReconstructFromNeighbors_codes_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ByteVector(cPtr, false);
  }

  public void setNtotal(long value) {
    swigfaissJNI.ReconstructFromNeighbors_ntotal_set(swigCPtr, this, value);
  }

  public long getNtotal() {
    return swigfaissJNI.ReconstructFromNeighbors_ntotal_get(swigCPtr, this);
  }

  public void setD(long value) {
    swigfaissJNI.ReconstructFromNeighbors_d_set(swigCPtr, this, value);
  }

  public long getD() {
    return swigfaissJNI.ReconstructFromNeighbors_d_get(swigCPtr, this);
  }

  public void setDsub(long value) {
    swigfaissJNI.ReconstructFromNeighbors_dsub_set(swigCPtr, this, value);
  }

  public long getDsub() {
    return swigfaissJNI.ReconstructFromNeighbors_dsub_get(swigCPtr, this);
  }

  public ReconstructFromNeighbors(IndexHNSW index, long k, long nsq) {
    this(swigfaissJNI.new_ReconstructFromNeighbors__SWIG_0(IndexHNSW.getCPtr(index), index, k, nsq), true);
  }

  public ReconstructFromNeighbors(IndexHNSW index, long k) {
    this(swigfaissJNI.new_ReconstructFromNeighbors__SWIG_1(IndexHNSW.getCPtr(index), index, k), true);
  }

  public ReconstructFromNeighbors(IndexHNSW index) {
    this(swigfaissJNI.new_ReconstructFromNeighbors__SWIG_2(IndexHNSW.getCPtr(index), index), true);
  }

  public void add_codes(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.ReconstructFromNeighbors_add_codes(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public long compute_distances(long n, LongVector shortlist, SWIGTYPE_p_float query, SWIGTYPE_p_float distances) {
    return swigfaissJNI.ReconstructFromNeighbors_compute_distances(swigCPtr, this, n, SWIGTYPE_p_long_long.getCPtr(shortlist.data()), shortlist, SWIGTYPE_p_float.getCPtr(query), SWIGTYPE_p_float.getCPtr(distances));
  }

  public void estimate_code(SWIGTYPE_p_float x, int i, SWIGTYPE_p_unsigned_char code) {
    swigfaissJNI.ReconstructFromNeighbors_estimate_code(swigCPtr, this, SWIGTYPE_p_float.getCPtr(x), i, SWIGTYPE_p_unsigned_char.getCPtr(code));
  }

  public void reconstruct(int i, SWIGTYPE_p_float x, SWIGTYPE_p_float tmp) {
    swigfaissJNI.ReconstructFromNeighbors_reconstruct(swigCPtr, this, i, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_float.getCPtr(tmp));
  }

  public void reconstruct_n(int n0, int ni, SWIGTYPE_p_float x) {
    swigfaissJNI.ReconstructFromNeighbors_reconstruct_n(swigCPtr, this, n0, ni, SWIGTYPE_p_float.getCPtr(x));
  }

  public void get_neighbor_table(int i, SWIGTYPE_p_float out) {
    swigfaissJNI.ReconstructFromNeighbors_get_neighbor_table(swigCPtr, this, i, SWIGTYPE_p_float.getCPtr(out));
  }

}
