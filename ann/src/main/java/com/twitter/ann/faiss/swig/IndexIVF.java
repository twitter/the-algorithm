try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class IndexIVF extends Index {
  private transient long swigCPtr;

  protected IndexIVF(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.IndexIVF_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IndexIVF obj) {
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
        swigfaissJNI.delete_IndexIVF(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setInvlists(InvertedLists value) {
    swigfaissJNI.IndexIVF_invlists_set(swigCPtr, this, InvertedLists.getCPtr(value), value);
  }

  public InvertedLists getInvlists() {
    long cPtr = swigfaissJNI.IndexIVF_invlists_get(swigCPtr, this);
    return (cPtr == 0) ? null : new InvertedLists(cPtr, false);
  }

  public void setOwn_invlists(boolean value) {
    swigfaissJNI.IndexIVF_own_invlists_set(swigCPtr, this, value);
  }

  public boolean getOwn_invlists() {
    return swigfaissJNI.IndexIVF_own_invlists_get(swigCPtr, this);
  }

  public void setCode_size(long value) {
    swigfaissJNI.IndexIVF_code_size_set(swigCPtr, this, value);
  }

  public long getCode_size() {
    return swigfaissJNI.IndexIVF_code_size_get(swigCPtr, this);
  }

  public void setNprobe(long value) {
    swigfaissJNI.IndexIVF_nprobe_set(swigCPtr, this, value);
  }

  public long getNprobe() {
    return swigfaissJNI.IndexIVF_nprobe_get(swigCPtr, this);
  }

  public void setMax_codes(long value) {
    swigfaissJNI.IndexIVF_max_codes_set(swigCPtr, this, value);
  }

  public long getMax_codes() {
    return swigfaissJNI.IndexIVF_max_codes_get(swigCPtr, this);
  }

  public void setParallel_mode(int value) {
    swigfaissJNI.IndexIVF_parallel_mode_set(swigCPtr, this, value);
  }

  public int getParallel_mode() {
    return swigfaissJNI.IndexIVF_parallel_mode_get(swigCPtr, this);
  }

  public int getPARALLEL_MODE_NO_HEAP_INIT() {
    return swigfaissJNI.IndexIVF_PARALLEL_MODE_NO_HEAP_INIT_get(swigCPtr, this);
  }

  public void setDirect_map(SWIGTYPE_p_DirectMap value) {
    swigfaissJNI.IndexIVF_direct_map_set(swigCPtr, this, SWIGTYPE_p_DirectMap.getCPtr(value));
  }

  public SWIGTYPE_p_DirectMap getDirect_map() {
    return new SWIGTYPE_p_DirectMap(swigfaissJNI.IndexIVF_direct_map_get(swigCPtr, this), true);
  }

  public void reset() {
    swigfaissJNI.IndexIVF_reset(swigCPtr, this);
  }

  public void train(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexIVF_train(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void add(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexIVF_add(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void add_with_ids(long n, SWIGTYPE_p_float x, LongVector xids) {
    swigfaissJNI.IndexIVF_add_with_ids(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(xids.data()), xids);
  }

  public void add_core(long n, SWIGTYPE_p_float x, LongVector xids, LongVector precomputed_idx) {
    swigfaissJNI.IndexIVF_add_core(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(xids.data()), xids, SWIGTYPE_p_long_long.getCPtr(precomputed_idx.data()), precomputed_idx);
  }

  public void encode_vectors(long n, SWIGTYPE_p_float x, LongVector list_nos, SWIGTYPE_p_unsigned_char codes, boolean include_listno) {
    swigfaissJNI.IndexIVF_encode_vectors__SWIG_0(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(list_nos.data()), list_nos, SWIGTYPE_p_unsigned_char.getCPtr(codes), include_listno);
  }

  public void encode_vectors(long n, SWIGTYPE_p_float x, LongVector list_nos, SWIGTYPE_p_unsigned_char codes) {
    swigfaissJNI.IndexIVF_encode_vectors__SWIG_1(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_long_long.getCPtr(list_nos.data()), list_nos, SWIGTYPE_p_unsigned_char.getCPtr(codes));
  }

  public void add_sa_codes(long n, SWIGTYPE_p_unsigned_char codes, LongVector xids) {
    swigfaissJNI.IndexIVF_add_sa_codes(swigCPtr, this, n, SWIGTYPE_p_unsigned_char.getCPtr(codes), SWIGTYPE_p_long_long.getCPtr(xids.data()), xids);
  }

  public void train_residual(long n, SWIGTYPE_p_float x) {
    swigfaissJNI.IndexIVF_train_residual(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x));
  }

  public void search_preassigned(long n, SWIGTYPE_p_float x, long k, LongVector assign, SWIGTYPE_p_float centroid_dis, SWIGTYPE_p_float distances, LongVector labels, boolean store_pairs, IVFSearchParameters params, IndexIVFStats stats) {
    swigfaissJNI.IndexIVF_search_preassigned__SWIG_0(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_long_long.getCPtr(assign.data()), assign, SWIGTYPE_p_float.getCPtr(centroid_dis), SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels, store_pairs, IVFSearchParameters.getCPtr(params), params, IndexIVFStats.getCPtr(stats), stats);
  }

  public void search_preassigned(long n, SWIGTYPE_p_float x, long k, LongVector assign, SWIGTYPE_p_float centroid_dis, SWIGTYPE_p_float distances, LongVector labels, boolean store_pairs, IVFSearchParameters params) {
    swigfaissJNI.IndexIVF_search_preassigned__SWIG_1(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_long_long.getCPtr(assign.data()), assign, SWIGTYPE_p_float.getCPtr(centroid_dis), SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels, store_pairs, IVFSearchParameters.getCPtr(params), params);
  }

  public void search_preassigned(long n, SWIGTYPE_p_float x, long k, LongVector assign, SWIGTYPE_p_float centroid_dis, SWIGTYPE_p_float distances, LongVector labels, boolean store_pairs) {
    swigfaissJNI.IndexIVF_search_preassigned__SWIG_2(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_long_long.getCPtr(assign.data()), assign, SWIGTYPE_p_float.getCPtr(centroid_dis), SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels, store_pairs);
  }

  public void search(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, LongVector labels) {
    swigfaissJNI.IndexIVF_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels);
  }

  public void range_search(long n, SWIGTYPE_p_float x, float radius, RangeSearchResult result) {
    swigfaissJNI.IndexIVF_range_search(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), radius, RangeSearchResult.getCPtr(result), result);
  }

  public void range_search_preassigned(long nx, SWIGTYPE_p_float x, float radius, LongVector keys, SWIGTYPE_p_float coarse_dis, RangeSearchResult result, boolean store_pairs, IVFSearchParameters params, IndexIVFStats stats) {
    swigfaissJNI.IndexIVF_range_search_preassigned__SWIG_0(swigCPtr, this, nx, SWIGTYPE_p_float.getCPtr(x), radius, SWIGTYPE_p_long_long.getCPtr(keys.data()), keys, SWIGTYPE_p_float.getCPtr(coarse_dis), RangeSearchResult.getCPtr(result), result, store_pairs, IVFSearchParameters.getCPtr(params), params, IndexIVFStats.getCPtr(stats), stats);
  }

  public void range_search_preassigned(long nx, SWIGTYPE_p_float x, float radius, LongVector keys, SWIGTYPE_p_float coarse_dis, RangeSearchResult result, boolean store_pairs, IVFSearchParameters params) {
    swigfaissJNI.IndexIVF_range_search_preassigned__SWIG_1(swigCPtr, this, nx, SWIGTYPE_p_float.getCPtr(x), radius, SWIGTYPE_p_long_long.getCPtr(keys.data()), keys, SWIGTYPE_p_float.getCPtr(coarse_dis), RangeSearchResult.getCPtr(result), result, store_pairs, IVFSearchParameters.getCPtr(params), params);
  }

  public void range_search_preassigned(long nx, SWIGTYPE_p_float x, float radius, LongVector keys, SWIGTYPE_p_float coarse_dis, RangeSearchResult result, boolean store_pairs) {
    swigfaissJNI.IndexIVF_range_search_preassigned__SWIG_2(swigCPtr, this, nx, SWIGTYPE_p_float.getCPtr(x), radius, SWIGTYPE_p_long_long.getCPtr(keys.data()), keys, SWIGTYPE_p_float.getCPtr(coarse_dis), RangeSearchResult.getCPtr(result), result, store_pairs);
  }

  public void range_search_preassigned(long nx, SWIGTYPE_p_float x, float radius, LongVector keys, SWIGTYPE_p_float coarse_dis, RangeSearchResult result) {
    swigfaissJNI.IndexIVF_range_search_preassigned__SWIG_3(swigCPtr, this, nx, SWIGTYPE_p_float.getCPtr(x), radius, SWIGTYPE_p_long_long.getCPtr(keys.data()), keys, SWIGTYPE_p_float.getCPtr(coarse_dis), RangeSearchResult.getCPtr(result), result);
  }

  public SWIGTYPE_p_faiss__InvertedListScanner get_InvertedListScanner(boolean store_pairs) {
    long cPtr = swigfaissJNI.IndexIVF_get_InvertedListScanner__SWIG_0(swigCPtr, this, store_pairs);
    return (cPtr == 0) ? null : new SWIGTYPE_p_faiss__InvertedListScanner(cPtr, false);
  }

  public SWIGTYPE_p_faiss__InvertedListScanner get_InvertedListScanner() {
    long cPtr = swigfaissJNI.IndexIVF_get_InvertedListScanner__SWIG_1(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_faiss__InvertedListScanner(cPtr, false);
  }

  public void reconstruct(long key, SWIGTYPE_p_float recons) {
    swigfaissJNI.IndexIVF_reconstruct(swigCPtr, this, key, SWIGTYPE_p_float.getCPtr(recons));
  }

  public void update_vectors(int nv, LongVector idx, SWIGTYPE_p_float v) {
    swigfaissJNI.IndexIVF_update_vectors(swigCPtr, this, nv, SWIGTYPE_p_long_long.getCPtr(idx.data()), idx, SWIGTYPE_p_float.getCPtr(v));
  }

  public void reconstruct_n(long i0, long ni, SWIGTYPE_p_float recons) {
    swigfaissJNI.IndexIVF_reconstruct_n(swigCPtr, this, i0, ni, SWIGTYPE_p_float.getCPtr(recons));
  }

  public void search_and_reconstruct(long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, LongVector labels, SWIGTYPE_p_float recons) {
    swigfaissJNI.IndexIVF_search_and_reconstruct(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels, SWIGTYPE_p_float.getCPtr(recons));
  }

  public void reconstruct_from_offset(long list_no, long offset, SWIGTYPE_p_float recons) {
    swigfaissJNI.IndexIVF_reconstruct_from_offset(swigCPtr, this, list_no, offset, SWIGTYPE_p_float.getCPtr(recons));
  }

  public long remove_ids(IDSelector sel) {
    return swigfaissJNI.IndexIVF_remove_ids(swigCPtr, this, IDSelector.getCPtr(sel), sel);
  }

  public void check_compatible_for_merge(IndexIVF other) {
    swigfaissJNI.IndexIVF_check_compatible_for_merge(swigCPtr, this, IndexIVF.getCPtr(other), other);
  }

  public void merge_from(IndexIVF other, long add_id) {
    swigfaissJNI.IndexIVF_merge_from(swigCPtr, this, IndexIVF.getCPtr(other), other, add_id);
  }

  public void copy_subset_to(IndexIVF other, int subset_type, long a1, long a2) {
    swigfaissJNI.IndexIVF_copy_subset_to(swigCPtr, this, IndexIVF.getCPtr(other), other, subset_type, a1, a2);
  }

  public long get_list_size(long list_no) {
    return swigfaissJNI.IndexIVF_get_list_size(swigCPtr, this, list_no);
  }

  public void make_direct_map(boolean new_maintain_direct_map) {
    swigfaissJNI.IndexIVF_make_direct_map__SWIG_0(swigCPtr, this, new_maintain_direct_map);
  }

  public void make_direct_map() {
    swigfaissJNI.IndexIVF_make_direct_map__SWIG_1(swigCPtr, this);
  }

  public void set_direct_map_type(SWIGTYPE_p_DirectMap__Type type) {
    swigfaissJNI.IndexIVF_set_direct_map_type(swigCPtr, this, SWIGTYPE_p_DirectMap__Type.getCPtr(type));
  }

  public void replace_invlists(InvertedLists il, boolean own) {
    swigfaissJNI.IndexIVF_replace_invlists__SWIG_0(swigCPtr, this, InvertedLists.getCPtr(il), il, own);
  }

  public void replace_invlists(InvertedLists il) {
    swigfaissJNI.IndexIVF_replace_invlists__SWIG_1(swigCPtr, this, InvertedLists.getCPtr(il), il);
  }

  public long sa_code_size() {
    return swigfaissJNI.IndexIVF_sa_code_size(swigCPtr, this);
  }

  public void sa_encode(long n, SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char bytes) {
    swigfaissJNI.IndexIVF_sa_encode(swigCPtr, this, n, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(bytes));
  }

}

} catch (Exception e) {
}
