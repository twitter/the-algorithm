/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class IndexIVFPQ extends IndexIVF {
  private transient long swigCPtr;

  protected IndexIVFPQ(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexIVFPQ_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexIVFPQ obj) {
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
        swigfaissJNI.delete_IndexIVFPQ(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setBy_residual(boolean value) {
    swigfaissJNI.IndexIVFPQ_by_residual_set(swigCPtr, this, value);
  }

  public boolean getBy_residual() {
    return swigfaissJNI.IndexIVFPQ_by_residual_get(swigCPtr, this);
  }

  public void setPq(ProductQuantizer value) {
    swigfaissJNI.IndexIVFPQ_pq_set(swigCPtr, this, ProductQuantizer.getCPtr(value), value);
  }

  public ProductQuantizer getPq() {
    long cPtr = swigfaissJNI.IndexIVFPQ_pq_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ProductQuantizer(cPtr, false);
  }
// This is catastrophically bad, don't do this.
// Someone needs to fix this.

  public void setDo_polysemous_training(boolean value) {
    swigfaissJNI.IndexIVFPQ_do_polysemous_training_set(swigCPtr, this, value);
  }

  public boolean getDo_polysemous_training() {
    return swigfaissJNI.IndexIVFPQ_do_polysemous_training_get(swigCPtr, this);
  }

  public void setPolysemous_training(PolysemousTraining value) {
    swigfaissJNI.IndexIVFPQ_polysemous_training_set(swigCPtr, this, PolysemousTraining.getCPtr(value), value);
  }

  public PolysemousTraining getPolysemous_training() {
    long cPtr = swigfaissJNI.IndexIVFPQ_polysemous_training_get(swigCPtr, this);
    return (cPtr == 0) ? null : new PolysemousTraining(cPtr, false);
  }

  public void setScan_table_threshold(long value) {
    swigfaissJNI.IndexIVFPQ_scan_table_threshold_set(swigCPtr, this, value);
  }

  public long getScan_table_threshold() {
    return swigfaissJNI.IndexIVFPQ_scan_table_threshold_get(swigCPtr, this);
  }

  public void setPolysemous_ht(int value) {
    swigfaissJNI.IndexIVFPQ_polysemous_ht_set(swigCPtr, this, value);
  }

  public int getPolysemous_ht() {
    return swigfaissJNI.IndexIVFPQ_polysemous_ht_get(swigCPtr, this);
  }

  public void setUse_precomputed_table(int value) {
    swigfaissJNI.IndexIVFPQ_use_precomputed_table_set(swigCPtr, this, value);
  }

  public int getUse_precomputed_table() {
    return swigfaissJNI.IndexIVFPQ_use_precomputed_table_get(swigCPtr, this);
  }

  public void setPrecomputed_table(SWIGTYPE_p_AlignedTableT_float_t value) {
    swigfaissJNI.IndexIVFPQ_precomputed_table_set(swigCPtr, this, SWIGTYPE_p_AlignedTableT_float_t.getCPtr(value));
  }

  public SWIGTYPE_p_AlignedTableT_float_t getPrecomputed_table() {
    return new SWIGTYPE_p_AlignedTableT_float_t(swigfaissJNI.IndexIVFPQ_precomputed_table_get(swigCPtr, this), true);
  }

  public IndexIVFPQ(Index quantizer, long d, long nlist, long M, long nbits_per_idx, MetricType metric) {
    this(swigfaissJNI.new_IndexIVFPQ__SWIG_0(Index.getCPtr(quantizer), quantizer, d, nlist, M, nbits_per_idx, metric.swigValue()), true);
  }

  public IndexIVFPQ(Index quantizer, long d, long nlist, long M, long nbits_per_idx) {
    this(swigfaissJNI.new_IndexIVFPQ__SWIG_1(Index.getCPtr(quantizer), quantizer, d, nlist, M, nbits_per_idx), true);
  }

  public void encode_vectors(long n, SWIGTYPE_p_float x, LongVector list_nos, SWIGTYPE_p_unsigned_char codes, boolean include_listnos) {
    swigfaissJNI.IndexIVFPQ_encode_vectors__SWIG_0(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(list_nos.data()), list_nos, SWIGTYPE_p_unsigned_char.getCPtr(codes), include_listnos);
  }

  public void encode_vectors(long n, SWIGTYPE_p_float x, LongVector list_nos, SWIGTYPE_p_unsigned_char codes) {
    swigfaissJNI.IndexIVFPQ_encode_vectors__SWIG_1(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(list_nos.data()), list_nos, SWIGTYPE_p_unsigned_char.getCPtr(codes));
  }

  public void sa_decode(long n, SWIGTYPE_p_unsigned_char bytes, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexIVFPQ_sa_decode(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(bytes), SWIGTYPE_p_float.getCPtr(x));
  }

  public void add_core(long n, SWIGTYPE_p_float x, LongVector xids, LongVector precomputed_idx) {
    swigfaissJNI.IndexIVFPQ_add_core(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(xids.data()), xids, SWIGTYPE_p_long_long.getCPtr(precomputed_idx.data()), precomputed_idx);
  }

  public void add_core_o(long n, SWIGTYPE_p_float x, LongVector xids, SWIGTYPE_p_float residuals_2, LongVector precomputed_idx) {
    swigfaissJNI.IndexIVFPQ_add_core_o__SWIG_0(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(xids.data()), xids, SWIGTYPE_p_float.getCPtr(residuals_2), SWIGTYPE_p_long_long.getCPtr(precomputed_idx.data()), precomputed_idx);
  }

  public void add_core_o(long n, SWIGTYPE_p_float x, LongVector xids, SWIGTYPE_p_float residuals_2) {
    swigfaissJNI.IndexIVFPQ_add_core_o__SWIG_1(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(xids.data()), xids, SWIGTYPE_p_float.getCPtr(residuals_2));
  }

  public void train_residual(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexIVFPQ_train_residual(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void train_residual_o(long n, SWIGTYPE_p_float x, SWIGTYPE_p_float residuals_2) {
    swigfaissJNI.IndexIVFPQ_train_residual_o(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_float.getCPtr(residuals_2));
  }

  public void reconstruct_from_offset(long list_no, long offset, SWIGTYPE_p_float recons) {
    swigfaissJNI.IndexIVFPQ_reconstruct_from_offset(swigCPtr, this, list_no, offset, SWIGTYPE_p_float.getCPtr(recons));
  }

  public long find_duplicates(LongVector ids, SWIGTYPE_p_unsigned_long lims) {
    return swigfaissJNI.IndexIVFPQ_find_duplicates(swigCPtr, this, SWIGTYPE_p_long_long.getCPtr(ids.data()), ids, SWIGTYPE_p_unsigned_long.getCPtr(lims));
  }

  public void encode(long key, SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char code) {
    swigfaissJNI.IndexIVFPQ_encode(swigCPtr, this, key, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(code));
  }

  public void encode_multiple(long n, LongVector keys, SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char codes, boolean compute_keys) {
    swigfaissJNI.IndexIVFPQ_encode_multiple__SWIG_0(swigCPtr, this, n, SWIGTYPE_p_long_long.getCPtr(keys.data()), keys, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(codes), compute_keys);
  }

  public void encode_multiple(long n, LongVector keys, SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char codes) {
    swigfaissJNI.IndexIVFPQ_encode_multiple__SWIG_1(swigCPtr, this, n, SWIGTYPE_p_long_long.getCPtr(keys.data()), keys, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(codes));
  }

  public void decode_multiple(long n, LongVector keys, SWIGTYPE_p_unsigned_char xcodes, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexIVFPQ_decode_multiple(swigCPtr, this, n, SWIGTYPE_p_long_long.getCPtr(keys.data()), keys, SWIGTYPE_p_unsigned_char.getCPtr(xcodes), SWIGTYPE_p_float.getCPtr(x));
  }

  public SWIGTYPE_p_faiss__InvertedListScanner get_InvertedListScanner(boolean store_pairs) {
    long cPtr = swigfaissJNI.IndexIVFPQ_get_InvertedListScanner(swigCPtr, this, store_pairs);
    return (cPtr == 0) ? null : new SWIGTYPE_p_faiss__InvertedListScanner(cPtr, false);
  }

  public void precompute_table() {
    swigfaissJNI.IndexIVFPQ_precompute_table(swigCPtr, this);
  }

  public IndexIVFPQ() {
    this(swigfaissJNI.new_IndexIVFPQ__SWIG_2(), true);
  }

}
