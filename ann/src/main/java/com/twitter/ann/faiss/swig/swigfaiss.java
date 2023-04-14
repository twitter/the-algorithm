try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class swigfaiss implements swigfaissConstants {
  public static void bitvec_print(SWIGTYPE_p_unsigned_char b, long d) {
    swigfaissJNI.bitvec_print(SWIGTYPE_p_unsigned_char.getCPtr(b), d);
  }

  public static void fvecs2bitvecs(SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char b, long d, long n) {
    swigfaissJNI.fvecs2bitvecs(SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(b), d, n);
  }

  public static void bitvecs2fvecs(SWIGTYPE_p_unsigned_char b, SWIGTYPE_p_float x, long d, long n) {
    swigfaissJNI.bitvecs2fvecs(SWIGTYPE_p_unsigned_char.getCPtr(b), SWIGTYPE_p_float.getCPtr(x), d, n);
  }

  public static void fvec2bitvec(SWIGTYPE_p_float x, SWIGTYPE_p_unsigned_char b, long d) {
    swigfaissJNI.fvec2bitvec(SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_unsigned_char.getCPtr(b), d);
  }

  public static void bitvec_shuffle(long n, long da, long db, SWIGTYPE_p_int order, SWIGTYPE_p_unsigned_char a, SWIGTYPE_p_unsigned_char b) {
    swigfaissJNI.bitvec_shuffle(n, da, db, SWIGTYPE_p_int.getCPtr(order), SWIGTYPE_p_unsigned_char.getCPtr(a), SWIGTYPE_p_unsigned_char.getCPtr(b));
  }

  public static void setHamming_batch_size(long value) {
    swigfaissJNI.hamming_batch_size_set(value);
  }

  public static long getHamming_batch_size() {
    return swigfaissJNI.hamming_batch_size_get();
  }

  public static int popcount64(long x) {
    return swigfaissJNI.popcount64(x);
  }

  public static void hammings(SWIGTYPE_p_unsigned_char a, SWIGTYPE_p_unsigned_char b, long na, long nb, long nbytespercode, SWIGTYPE_p_int dis) {
    swigfaissJNI.hammings(SWIGTYPE_p_unsigned_char.getCPtr(a), SWIGTYPE_p_unsigned_char.getCPtr(b), na, nb, nbytespercode, SWIGTYPE_p_int.getCPtr(dis));
  }

  public static void hammings_knn_hc(SWIGTYPE_p_faiss__HeapArrayT_faiss__CMaxT_int_int64_t_t_t ha, SWIGTYPE_p_unsigned_char a, SWIGTYPE_p_unsigned_char b, long nb, long ncodes, int ordered) {
    swigfaissJNI.hammings_knn_hc(SWIGTYPE_p_faiss__HeapArrayT_faiss__CMaxT_int_int64_t_t_t.getCPtr(ha), SWIGTYPE_p_unsigned_char.getCPtr(a), SWIGTYPE_p_unsigned_char.getCPtr(b), nb, ncodes, ordered);
  }

  public static void hammings_knn(SWIGTYPE_p_faiss__HeapArrayT_faiss__CMaxT_int_int64_t_t_t ha, SWIGTYPE_p_unsigned_char a, SWIGTYPE_p_unsigned_char b, long nb, long ncodes, int ordered) {
    swigfaissJNI.hammings_knn(SWIGTYPE_p_faiss__HeapArrayT_faiss__CMaxT_int_int64_t_t_t.getCPtr(ha), SWIGTYPE_p_unsigned_char.getCPtr(a), SWIGTYPE_p_unsigned_char.getCPtr(b), nb, ncodes, ordered);
  }

  public static void hammings_knn_mc(SWIGTYPE_p_unsigned_char a, SWIGTYPE_p_unsigned_char b, long na, long nb, long k, long ncodes, SWIGTYPE_p_int distances, LongVector labels) {
    swigfaissJNI.hammings_knn_mc(SWIGTYPE_p_unsigned_char.getCPtr(a), SWIGTYPE_p_unsigned_char.getCPtr(b), na, nb, k, ncodes, SWIGTYPE_p_int.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels);
  }

  public static void hamming_range_search(SWIGTYPE_p_unsigned_char a, SWIGTYPE_p_unsigned_char b, long na, long nb, int radius, long ncodes, RangeSearchResult result) {
    swigfaissJNI.hamming_range_search(SWIGTYPE_p_unsigned_char.getCPtr(a), SWIGTYPE_p_unsigned_char.getCPtr(b), na, nb, radius, ncodes, RangeSearchResult.getCPtr(result), result);
  }

  public static void hamming_count_thres(SWIGTYPE_p_unsigned_char bs1, SWIGTYPE_p_unsigned_char bs2, long n1, long n2, int ht, long ncodes, SWIGTYPE_p_unsigned_long nptr) {
    swigfaissJNI.hamming_count_thres(SWIGTYPE_p_unsigned_char.getCPtr(bs1), SWIGTYPE_p_unsigned_char.getCPtr(bs2), n1, n2, ht, ncodes, SWIGTYPE_p_unsigned_long.getCPtr(nptr));
  }

  public static long match_hamming_thres(SWIGTYPE_p_unsigned_char bs1, SWIGTYPE_p_unsigned_char bs2, long n1, long n2, int ht, long ncodes, LongVector idx, SWIGTYPE_p_int dis) {
    return swigfaissJNI.match_hamming_thres(SWIGTYPE_p_unsigned_char.getCPtr(bs1), SWIGTYPE_p_unsigned_char.getCPtr(bs2), n1, n2, ht, ncodes, SWIGTYPE_p_long_long.getCPtr(idx.data()), idx, SWIGTYPE_p_int.getCPtr(dis));
  }

  public static void crosshamming_count_thres(SWIGTYPE_p_unsigned_char dbs, long n, int ht, long ncodes, SWIGTYPE_p_unsigned_long nptr) {
    swigfaissJNI.crosshamming_count_thres(SWIGTYPE_p_unsigned_char.getCPtr(dbs), n, ht, ncodes, SWIGTYPE_p_unsigned_long.getCPtr(nptr));
  }

  public static int get_num_gpus() {
    return swigfaissJNI.get_num_gpus();
  }

  public static String get_compile_options() {
    return swigfaissJNI.get_compile_options();
  }

  public static double getmillisecs() {
    return swigfaissJNI.getmillisecs();
  }

  public static long get_mem_usage_kb() {
    return swigfaissJNI.get_mem_usage_kb();
  }

  public static long get_cycles() {
    return swigfaissJNI.get_cycles();
  }

  public static void fvec_madd(long n, SWIGTYPE_p_float a, float bf, SWIGTYPE_p_float b, SWIGTYPE_p_float c) {
    swigfaissJNI.fvec_madd(n, SWIGTYPE_p_float.getCPtr(a), bf, SWIGTYPE_p_float.getCPtr(b), SWIGTYPE_p_float.getCPtr(c));
  }

  public static int fvec_madd_and_argmin(long n, SWIGTYPE_p_float a, float bf, SWIGTYPE_p_float b, SWIGTYPE_p_float c) {
    return swigfaissJNI.fvec_madd_and_argmin(n, SWIGTYPE_p_float.getCPtr(a), bf, SWIGTYPE_p_float.getCPtr(b), SWIGTYPE_p_float.getCPtr(c));
  }

  public static void reflection(SWIGTYPE_p_float u, SWIGTYPE_p_float x, long n, long d, long nu) {
    swigfaissJNI.reflection(SWIGTYPE_p_float.getCPtr(u), SWIGTYPE_p_float.getCPtr(x), n, d, nu);
  }

  public static void matrix_qr(int m, int n, SWIGTYPE_p_float a) {
    swigfaissJNI.matrix_qr(m, n, SWIGTYPE_p_float.getCPtr(a));
  }

  public static void ranklist_handle_ties(int k, LongVector idx, SWIGTYPE_p_float dis) {
    swigfaissJNI.ranklist_handle_ties(k, SWIGTYPE_p_long_long.getCPtr(idx.data()), idx, SWIGTYPE_p_float.getCPtr(dis));
  }

  public static long ranklist_intersection_size(long k1, LongVector v1, long k2, LongVector v2) {
    return swigfaissJNI.ranklist_intersection_size(k1, SWIGTYPE_p_long_long.getCPtr(v1.data()), v1, k2, SWIGTYPE_p_long_long.getCPtr(v2.data()), v2);
  }

  public static long merge_result_table_with(long n, long k, LongVector I0, SWIGTYPE_p_float D0, LongVector I1, SWIGTYPE_p_float D1, boolean keep_min, long translation) {
    return swigfaissJNI.merge_result_table_with__SWIG_0(n, k, SWIGTYPE_p_long_long.getCPtr(I0.data()), I0, SWIGTYPE_p_float.getCPtr(D0), SWIGTYPE_p_long_long.getCPtr(I1.data()), I1, SWIGTYPE_p_float.getCPtr(D1), keep_min, translation);
  }

  public static long merge_result_table_with(long n, long k, LongVector I0, SWIGTYPE_p_float D0, LongVector I1, SWIGTYPE_p_float D1, boolean keep_min) {
    return swigfaissJNI.merge_result_table_with__SWIG_1(n, k, SWIGTYPE_p_long_long.getCPtr(I0.data()), I0, SWIGTYPE_p_float.getCPtr(D0), SWIGTYPE_p_long_long.getCPtr(I1.data()), I1, SWIGTYPE_p_float.getCPtr(D1), keep_min);
  }

  public static long merge_result_table_with(long n, long k, LongVector I0, SWIGTYPE_p_float D0, LongVector I1, SWIGTYPE_p_float D1) {
    return swigfaissJNI.merge_result_table_with__SWIG_2(n, k, SWIGTYPE_p_long_long.getCPtr(I0.data()), I0, SWIGTYPE_p_float.getCPtr(D0), SWIGTYPE_p_long_long.getCPtr(I1.data()), I1, SWIGTYPE_p_float.getCPtr(D1));
  }

  public static double imbalance_factor(int n, int k, LongVector assign) {
    return swigfaissJNI.imbalance_factor__SWIG_0(n, k, SWIGTYPE_p_long_long.getCPtr(assign.data()), assign);
  }

  public static double imbalance_factor(int k, SWIGTYPE_p_int hist) {
    return swigfaissJNI.imbalance_factor__SWIG_1(k, SWIGTYPE_p_int.getCPtr(hist));
  }

  public static void fvec_argsort(long n, SWIGTYPE_p_float vals, SWIGTYPE_p_unsigned_long perm) {
    swigfaissJNI.fvec_argsort(n, SWIGTYPE_p_float.getCPtr(vals), SWIGTYPE_p_unsigned_long.getCPtr(perm));
  }

  public static void fvec_argsort_parallel(long n, SWIGTYPE_p_float vals, SWIGTYPE_p_unsigned_long perm) {
    swigfaissJNI.fvec_argsort_parallel(n, SWIGTYPE_p_float.getCPtr(vals), SWIGTYPE_p_unsigned_long.getCPtr(perm));
  }

  public static int ivec_hist(long n, SWIGTYPE_p_int v, int vmax, SWIGTYPE_p_int hist) {
    return swigfaissJNI.ivec_hist(n, SWIGTYPE_p_int.getCPtr(v), vmax, SWIGTYPE_p_int.getCPtr(hist));
  }

  public static void bincode_hist(long n, long nbits, SWIGTYPE_p_unsigned_char codes, SWIGTYPE_p_int hist) {
    swigfaissJNI.bincode_hist(n, nbits, SWIGTYPE_p_unsigned_char.getCPtr(codes), SWIGTYPE_p_int.getCPtr(hist));
  }

  public static long ivec_checksum(long n, SWIGTYPE_p_int a) {
    return swigfaissJNI.ivec_checksum(n, SWIGTYPE_p_int.getCPtr(a));
  }

  public static SWIGTYPE_p_float fvecs_maybe_subsample(long d, SWIGTYPE_p_unsigned_long n, long nmax, SWIGTYPE_p_float x, boolean verbose, long seed) {
    long cPtr = swigfaissJNI.fvecs_maybe_subsample__SWIG_0(d, SWIGTYPE_p_unsigned_long.getCPtr(n), nmax, SWIGTYPE_p_float.getCPtr(x), verbose, seed);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public static SWIGTYPE_p_float fvecs_maybe_subsample(long d, SWIGTYPE_p_unsigned_long n, long nmax, SWIGTYPE_p_float x, boolean verbose) {
    long cPtr = swigfaissJNI.fvecs_maybe_subsample__SWIG_1(d, SWIGTYPE_p_unsigned_long.getCPtr(n), nmax, SWIGTYPE_p_float.getCPtr(x), verbose);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public static SWIGTYPE_p_float fvecs_maybe_subsample(long d, SWIGTYPE_p_unsigned_long n, long nmax, SWIGTYPE_p_float x) {
    long cPtr = swigfaissJNI.fvecs_maybe_subsample__SWIG_2(d, SWIGTYPE_p_unsigned_long.getCPtr(n), nmax, SWIGTYPE_p_float.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public static void binary_to_real(long d, SWIGTYPE_p_unsigned_char x_in, SWIGTYPE_p_float x_out) {
    swigfaissJNI.binary_to_real(d, SWIGTYPE_p_unsigned_char.getCPtr(x_in), SWIGTYPE_p_float.getCPtr(x_out));
  }

  public static void real_to_binary(long d, SWIGTYPE_p_float x_in, SWIGTYPE_p_unsigned_char x_out) {
    swigfaissJNI.real_to_binary(d, SWIGTYPE_p_float.getCPtr(x_in), SWIGTYPE_p_unsigned_char.getCPtr(x_out));
  }

  public static long hash_bytes(SWIGTYPE_p_unsigned_char bytes, long n) {
    return swigfaissJNI.hash_bytes(SWIGTYPE_p_unsigned_char.getCPtr(bytes), n);
  }

  public static boolean check_openmp() {
    return swigfaissJNI.check_openmp();
  }

  public static float kmeans_clustering(long d, long n, long k, SWIGTYPE_p_float x, SWIGTYPE_p_float centroids) {
    return swigfaissJNI.kmeans_clustering(d, n, k, SWIGTYPE_p_float.getCPtr(x), SWIGTYPE_p_float.getCPtr(centroids));
  }

  public static void setIndexPQ_stats(IndexPQStats value) {
    swigfaissJNI.indexPQ_stats_set(IndexPQStats.getCPtr(value), value);
  }

  public static IndexPQStats getIndexPQ_stats() {
    long cPtr = swigfaissJNI.indexPQ_stats_get();
    return (cPtr == 0) ? null : new IndexPQStats(cPtr, false);
  }

  public static void setIndexIVF_stats(IndexIVFStats value) {
    swigfaissJNI.indexIVF_stats_set(IndexIVFStats.getCPtr(value), value);
  }

  public static IndexIVFStats getIndexIVF_stats() {
    long cPtr = swigfaissJNI.indexIVF_stats_get();
    return (cPtr == 0) ? null : new IndexIVFStats(cPtr, false);
  }

  public static short[] getHamdis_tab_ham_bytes() {
    return swigfaissJNI.hamdis_tab_ham_bytes_get();
  }

  public static int generalized_hamming_64(long a) {
    return swigfaissJNI.generalized_hamming_64(a);
  }

  public static void generalized_hammings_knn_hc(SWIGTYPE_p_faiss__HeapArrayT_faiss__CMaxT_int_int64_t_t_t ha, SWIGTYPE_p_unsigned_char a, SWIGTYPE_p_unsigned_char b, long nb, long code_size, int ordered) {
    swigfaissJNI.generalized_hammings_knn_hc__SWIG_0(SWIGTYPE_p_faiss__HeapArrayT_faiss__CMaxT_int_int64_t_t_t.getCPtr(ha), SWIGTYPE_p_unsigned_char.getCPtr(a), SWIGTYPE_p_unsigned_char.getCPtr(b), nb, code_size, ordered);
  }

  public static void generalized_hammings_knn_hc(SWIGTYPE_p_faiss__HeapArrayT_faiss__CMaxT_int_int64_t_t_t ha, SWIGTYPE_p_unsigned_char a, SWIGTYPE_p_unsigned_char b, long nb, long code_size) {
    swigfaissJNI.generalized_hammings_knn_hc__SWIG_1(SWIGTYPE_p_faiss__HeapArrayT_faiss__CMaxT_int_int64_t_t_t.getCPtr(ha), SWIGTYPE_p_unsigned_char.getCPtr(a), SWIGTYPE_p_unsigned_char.getCPtr(b), nb, code_size);
  }

  public static void check_compatible_for_merge(Index index1, Index index2) {
    swigfaissJNI.check_compatible_for_merge(Index.getCPtr(index1), index1, Index.getCPtr(index2), index2);
  }

  public static IndexIVF extract_index_ivf(Index index) {
    long cPtr = swigfaissJNI.extract_index_ivf__SWIG_0(Index.getCPtr(index), index);
    return (cPtr == 0) ? null : new IndexIVF(cPtr, false);
  }

  public static IndexIVF try_extract_index_ivf(Index index) {
    long cPtr = swigfaissJNI.try_extract_index_ivf__SWIG_0(Index.getCPtr(index), index);
    return (cPtr == 0) ? null : new IndexIVF(cPtr, false);
  }

  public static void merge_into(Index index0, Index index1, boolean shift_ids) {
    swigfaissJNI.merge_into(Index.getCPtr(index0), index0, Index.getCPtr(index1), index1, shift_ids);
  }

  public static void search_centroid(Index index, SWIGTYPE_p_float x, int n, LongVector centroid_ids) {
    swigfaissJNI.search_centroid(Index.getCPtr(index), index, SWIGTYPE_p_float.getCPtr(x), n, SWIGTYPE_p_long_long.getCPtr(centroid_ids.data()), centroid_ids);
  }

  public static void search_and_return_centroids(Index index, long n, SWIGTYPE_p_float xin, int k, SWIGTYPE_p_float distances, LongVector labels, LongVector query_centroid_ids, LongVector result_centroid_ids) {
    swigfaissJNI.search_and_return_centroids(Index.getCPtr(index), index, n, SWIGTYPE_p_float.getCPtr(xin), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels, SWIGTYPE_p_long_long.getCPtr(query_centroid_ids.data()), query_centroid_ids, SWIGTYPE_p_long_long.getCPtr(result_centroid_ids.data()), result_centroid_ids);
  }

  public static ArrayInvertedLists get_invlist_range(Index index, int i0, int i1) {
    long cPtr = swigfaissJNI.get_invlist_range(Index.getCPtr(index), index, i0, i1);
    return (cPtr == 0) ? null : new ArrayInvertedLists(cPtr, false);
  }

  public static void set_invlist_range(Index index, int i0, int i1, ArrayInvertedLists src) {
    swigfaissJNI.set_invlist_range(Index.getCPtr(index), index, i0, i1, ArrayInvertedLists.getCPtr(src), src);
  }

  public static void search_with_parameters(Index index, long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, LongVector labels, IVFSearchParameters params, SWIGTYPE_p_unsigned_long nb_dis, SWIGTYPE_p_double ms_per_stage) {
    swigfaissJNI.search_with_parameters__SWIG_0(Index.getCPtr(index), index, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels, IVFSearchParameters.getCPtr(params), params, SWIGTYPE_p_unsigned_long.getCPtr(nb_dis), SWIGTYPE_p_double.getCPtr(ms_per_stage));
  }

  public static void search_with_parameters(Index index, long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, LongVector labels, IVFSearchParameters params, SWIGTYPE_p_unsigned_long nb_dis) {
    swigfaissJNI.search_with_parameters__SWIG_1(Index.getCPtr(index), index, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels, IVFSearchParameters.getCPtr(params), params, SWIGTYPE_p_unsigned_long.getCPtr(nb_dis));
  }

  public static void search_with_parameters(Index index, long n, SWIGTYPE_p_float x, long k, SWIGTYPE_p_float distances, LongVector labels, IVFSearchParameters params) {
    swigfaissJNI.search_with_parameters__SWIG_2(Index.getCPtr(index), index, n, SWIGTYPE_p_float.getCPtr(x), k, SWIGTYPE_p_float.getCPtr(distances), SWIGTYPE_p_long_long.getCPtr(labels.data()), labels, IVFSearchParameters.getCPtr(params), params);
  }

  public static void range_search_with_parameters(Index index, long n, SWIGTYPE_p_float x, float radius, RangeSearchResult result, IVFSearchParameters params, SWIGTYPE_p_unsigned_long nb_dis, SWIGTYPE_p_double ms_per_stage) {
    swigfaissJNI.range_search_with_parameters__SWIG_0(Index.getCPtr(index), index, n, SWIGTYPE_p_float.getCPtr(x), radius, RangeSearchResult.getCPtr(result), result, IVFSearchParameters.getCPtr(params), params, SWIGTYPE_p_unsigned_long.getCPtr(nb_dis), SWIGTYPE_p_double.getCPtr(ms_per_stage));
  }

  public static void range_search_with_parameters(Index index, long n, SWIGTYPE_p_float x, float radius, RangeSearchResult result, IVFSearchParameters params, SWIGTYPE_p_unsigned_long nb_dis) {
    swigfaissJNI.range_search_with_parameters__SWIG_1(Index.getCPtr(index), index, n, SWIGTYPE_p_float.getCPtr(x), radius, RangeSearchResult.getCPtr(result), result, IVFSearchParameters.getCPtr(params), params, SWIGTYPE_p_unsigned_long.getCPtr(nb_dis));
  }

  public static void range_search_with_parameters(Index index, long n, SWIGTYPE_p_float x, float radius, RangeSearchResult result, IVFSearchParameters params) {
    swigfaissJNI.range_search_with_parameters__SWIG_2(Index.getCPtr(index), index, n, SWIGTYPE_p_float.getCPtr(x), radius, RangeSearchResult.getCPtr(result), result, IVFSearchParameters.getCPtr(params), params);
  }

  public static void setHnsw_stats(HNSWStats value) {
    swigfaissJNI.hnsw_stats_set(HNSWStats.getCPtr(value), value);
  }

  public static HNSWStats getHnsw_stats() {
    long cPtr = swigfaissJNI.hnsw_stats_get();
    return (cPtr == 0) ? null : new HNSWStats(cPtr, false);
  }

  public static void setPrecomputed_table_max_bytes(long value) {
    swigfaissJNI.precomputed_table_max_bytes_set(value);
  }

  public static long getPrecomputed_table_max_bytes() {
    return swigfaissJNI.precomputed_table_max_bytes_get();
  }

  public static void initialize_IVFPQ_precomputed_table(SWIGTYPE_p_int use_precomputed_table, Index quantizer, ProductQuantizer pq, SWIGTYPE_p_AlignedTableT_float_32_t precomputed_table, boolean verbose) {
    swigfaissJNI.initialize_IVFPQ_precomputed_table(SWIGTYPE_p_int.getCPtr(use_precomputed_table), Index.getCPtr(quantizer), quantizer, ProductQuantizer.getCPtr(pq), pq, SWIGTYPE_p_AlignedTableT_float_32_t.getCPtr(precomputed_table), verbose);
  }

  public static void setIndexIVFPQ_stats(IndexIVFPQStats value) {
    swigfaissJNI.indexIVFPQ_stats_set(IndexIVFPQStats.getCPtr(value), value);
  }

  public static IndexIVFPQStats getIndexIVFPQ_stats() {
    long cPtr = swigfaissJNI.indexIVFPQ_stats_get();
    return (cPtr == 0) ? null : new IndexIVFPQStats(cPtr, false);
  }

  public static Index downcast_index(Index index) {
    long cPtr = swigfaissJNI.downcast_index(Index.getCPtr(index), index);
    return (cPtr == 0) ? null : new Index(cPtr, false);
  }

  public static VectorTransform downcast_VectorTransform(VectorTransform vt) {
    long cPtr = swigfaissJNI.downcast_VectorTransform(VectorTransform.getCPtr(vt), vt);
    return (cPtr == 0) ? null : new VectorTransform(cPtr, false);
  }

  public static IndexBinary downcast_IndexBinary(IndexBinary index) {
    long cPtr = swigfaissJNI.downcast_IndexBinary(IndexBinary.getCPtr(index), index);
    return (cPtr == 0) ? null : new IndexBinary(cPtr, false);
  }

  public static Index upcast_IndexShards(IndexShards index) {
    long cPtr = swigfaissJNI.upcast_IndexShards(IndexShards.getCPtr(index), index);
    return (cPtr == 0) ? null : new Index(cPtr, false);
  }

  public static void write_index(Index idx, String fname) {
    swigfaissJNI.write_index__SWIG_0(Index.getCPtr(idx), idx, fname);
  }

  public static void write_index(Index idx, SWIGTYPE_p_FILE f) {
    swigfaissJNI.write_index__SWIG_1(Index.getCPtr(idx), idx, SWIGTYPE_p_FILE.getCPtr(f));
  }

  public static void write_index(Index idx, SWIGTYPE_p_faiss__IOWriter writer) {
    swigfaissJNI.write_index__SWIG_2(Index.getCPtr(idx), idx, SWIGTYPE_p_faiss__IOWriter.getCPtr(writer));
  }

  public static void write_index_binary(IndexBinary idx, String fname) {
    swigfaissJNI.write_index_binary__SWIG_0(IndexBinary.getCPtr(idx), idx, fname);
  }

  public static void write_index_binary(IndexBinary idx, SWIGTYPE_p_FILE f) {
    swigfaissJNI.write_index_binary__SWIG_1(IndexBinary.getCPtr(idx), idx, SWIGTYPE_p_FILE.getCPtr(f));
  }

  public static void write_index_binary(IndexBinary idx, SWIGTYPE_p_faiss__IOWriter writer) {
    swigfaissJNI.write_index_binary__SWIG_2(IndexBinary.getCPtr(idx), idx, SWIGTYPE_p_faiss__IOWriter.getCPtr(writer));
  }

  public static int getIO_FLAG_READ_ONLY() {
    return swigfaissJNI.IO_FLAG_READ_ONLY_get();
  }

  public static int getIO_FLAG_ONDISK_SAME_DIR() {
    return swigfaissJNI.IO_FLAG_ONDISK_SAME_DIR_get();
  }

  public static int getIO_FLAG_SKIP_IVF_DATA() {
    return swigfaissJNI.IO_FLAG_SKIP_IVF_DATA_get();
  }

  public static int getIO_FLAG_MMAP() {
    return swigfaissJNI.IO_FLAG_MMAP_get();
  }

  public static Index read_index(String fname, int io_flags) {
    long cPtr = swigfaissJNI.read_index__SWIG_0(fname, io_flags);
    return (cPtr == 0) ? null : new Index(cPtr, true);
  }

  public static Index read_index(String fname) {
    long cPtr = swigfaissJNI.read_index__SWIG_1(fname);
    return (cPtr == 0) ? null : new Index(cPtr, true);
  }

  public static Index read_index(SWIGTYPE_p_FILE f, int io_flags) {
    long cPtr = swigfaissJNI.read_index__SWIG_2(SWIGTYPE_p_FILE.getCPtr(f), io_flags);
    return (cPtr == 0) ? null : new Index(cPtr, true);
  }

  public static Index read_index(SWIGTYPE_p_FILE f) {
    long cPtr = swigfaissJNI.read_index__SWIG_3(SWIGTYPE_p_FILE.getCPtr(f));
    return (cPtr == 0) ? null : new Index(cPtr, true);
  }

  public static Index read_index(SWIGTYPE_p_faiss__IOReader reader, int io_flags) {
    long cPtr = swigfaissJNI.read_index__SWIG_4(SWIGTYPE_p_faiss__IOReader.getCPtr(reader), io_flags);
    return (cPtr == 0) ? null : new Index(cPtr, true);
  }

  public static Index read_index(SWIGTYPE_p_faiss__IOReader reader) {
    long cPtr = swigfaissJNI.read_index__SWIG_5(SWIGTYPE_p_faiss__IOReader.getCPtr(reader));
    return (cPtr == 0) ? null : new Index(cPtr, true);
  }

  public static IndexBinary read_index_binary(String fname, int io_flags) {
    long cPtr = swigfaissJNI.read_index_binary__SWIG_0(fname, io_flags);
    return (cPtr == 0) ? null : new IndexBinary(cPtr, true);
  }

  public static IndexBinary read_index_binary(String fname) {
    long cPtr = swigfaissJNI.read_index_binary__SWIG_1(fname);
    return (cPtr == 0) ? null : new IndexBinary(cPtr, true);
  }

  public static IndexBinary read_index_binary(SWIGTYPE_p_FILE f, int io_flags) {
    long cPtr = swigfaissJNI.read_index_binary__SWIG_2(SWIGTYPE_p_FILE.getCPtr(f), io_flags);
    return (cPtr == 0) ? null : new IndexBinary(cPtr, true);
  }

  public static IndexBinary read_index_binary(SWIGTYPE_p_FILE f) {
    long cPtr = swigfaissJNI.read_index_binary__SWIG_3(SWIGTYPE_p_FILE.getCPtr(f));
    return (cPtr == 0) ? null : new IndexBinary(cPtr, true);
  }

  public static IndexBinary read_index_binary(SWIGTYPE_p_faiss__IOReader reader, int io_flags) {
    long cPtr = swigfaissJNI.read_index_binary__SWIG_4(SWIGTYPE_p_faiss__IOReader.getCPtr(reader), io_flags);
    return (cPtr == 0) ? null : new IndexBinary(cPtr, true);
  }

  public static IndexBinary read_index_binary(SWIGTYPE_p_faiss__IOReader reader) {
    long cPtr = swigfaissJNI.read_index_binary__SWIG_5(SWIGTYPE_p_faiss__IOReader.getCPtr(reader));
    return (cPtr == 0) ? null : new IndexBinary(cPtr, true);
  }

  public static void write_VectorTransform(VectorTransform vt, String fname) {
    swigfaissJNI.write_VectorTransform(VectorTransform.getCPtr(vt), vt, fname);
  }

  public static VectorTransform read_VectorTransform(String fname) {
    long cPtr = swigfaissJNI.read_VectorTransform(fname);
    return (cPtr == 0) ? null : new VectorTransform(cPtr, true);
  }

  public static ProductQuantizer read_ProductQuantizer(String fname) {
    long cPtr = swigfaissJNI.read_ProductQuantizer__SWIG_0(fname);
    return (cPtr == 0) ? null : new ProductQuantizer(cPtr, true);
  }

  public static ProductQuantizer read_ProductQuantizer(SWIGTYPE_p_faiss__IOReader reader) {
    long cPtr = swigfaissJNI.read_ProductQuantizer__SWIG_1(SWIGTYPE_p_faiss__IOReader.getCPtr(reader));
    return (cPtr == 0) ? null : new ProductQuantizer(cPtr, true);
  }

  public static void write_ProductQuantizer(ProductQuantizer pq, String fname) {
    swigfaissJNI.write_ProductQuantizer__SWIG_0(ProductQuantizer.getCPtr(pq), pq, fname);
  }

  public static void write_ProductQuantizer(ProductQuantizer pq, SWIGTYPE_p_faiss__IOWriter f) {
    swigfaissJNI.write_ProductQuantizer__SWIG_1(ProductQuantizer.getCPtr(pq), pq, SWIGTYPE_p_faiss__IOWriter.getCPtr(f));
  }

  public static void write_InvertedLists(InvertedLists ils, SWIGTYPE_p_faiss__IOWriter f) {
    swigfaissJNI.write_InvertedLists(InvertedLists.getCPtr(ils), ils, SWIGTYPE_p_faiss__IOWriter.getCPtr(f));
  }

  public static InvertedLists read_InvertedLists(SWIGTYPE_p_faiss__IOReader reader, int io_flags) {
    long cPtr = swigfaissJNI.read_InvertedLists__SWIG_0(SWIGTYPE_p_faiss__IOReader.getCPtr(reader), io_flags);
    return (cPtr == 0) ? null : new InvertedLists(cPtr, false);
  }

  public static InvertedLists read_InvertedLists(SWIGTYPE_p_faiss__IOReader reader) {
    long cPtr = swigfaissJNI.read_InvertedLists__SWIG_1(SWIGTYPE_p_faiss__IOReader.getCPtr(reader));
    return (cPtr == 0) ? null : new InvertedLists(cPtr, false);
  }

  public static Index index_factory(int d, String description, MetricType metric) {
    long cPtr = swigfaissJNI.index_factory__SWIG_0(d, description, metric.swigValue());
    return (cPtr == 0) ? null : new Index(cPtr, true);
  }

  public static Index index_factory(int d, String description) {
    long cPtr = swigfaissJNI.index_factory__SWIG_1(d, description);
    return (cPtr == 0) ? null : new Index(cPtr, true);
  }

  public static void setIndex_factory_verbose(int value) {
    swigfaissJNI.index_factory_verbose_set(value);
  }

  public static int getIndex_factory_verbose() {
    return swigfaissJNI.index_factory_verbose_get();
  }

  public static IndexBinary index_binary_factory(int d, String description) {
    long cPtr = swigfaissJNI.index_binary_factory(d, description);
    return (cPtr == 0) ? null : new IndexBinary(cPtr, true);
  }

  public static void simd_histogram_8(SWIGTYPE_p_uint16_t data, int n, SWIGTYPE_p_uint16_t min, int shift, SWIGTYPE_p_int hist) {
    swigfaissJNI.simd_histogram_8(SWIGTYPE_p_uint16_t.getCPtr(data), n, SWIGTYPE_p_uint16_t.getCPtr(min), shift, SWIGTYPE_p_int.getCPtr(hist));
  }

  public static void simd_histogram_16(SWIGTYPE_p_uint16_t data, int n, SWIGTYPE_p_uint16_t min, int shift, SWIGTYPE_p_int hist) {
    swigfaissJNI.simd_histogram_16(SWIGTYPE_p_uint16_t.getCPtr(data), n, SWIGTYPE_p_uint16_t.getCPtr(min), shift, SWIGTYPE_p_int.getCPtr(hist));
  }

  public static void setPartition_stats(PartitionStats value) {
    swigfaissJNI.partition_stats_set(PartitionStats.getCPtr(value), value);
  }

  public static PartitionStats getPartition_stats() {
    long cPtr = swigfaissJNI.partition_stats_get();
    return (cPtr == 0) ? null : new PartitionStats(cPtr, false);
  }

  public static float CMin_float_partition_fuzzy(SWIGTYPE_p_float vals, LongVector ids, long n, long q_min, long q_max, SWIGTYPE_p_unsigned_long q_out) {
    return swigfaissJNI.CMin_float_partition_fuzzy(SWIGTYPE_p_float.getCPtr(vals), SWIGTYPE_p_long_long.getCPtr(ids.data()), ids, n, q_min, q_max, SWIGTYPE_p_unsigned_long.getCPtr(q_out));
  }

  public static float CMax_float_partition_fuzzy(SWIGTYPE_p_float vals, LongVector ids, long n, long q_min, long q_max, SWIGTYPE_p_unsigned_long q_out) {
    return swigfaissJNI.CMax_float_partition_fuzzy(SWIGTYPE_p_float.getCPtr(vals), SWIGTYPE_p_long_long.getCPtr(ids.data()), ids, n, q_min, q_max, SWIGTYPE_p_unsigned_long.getCPtr(q_out));
  }

  public static SWIGTYPE_p_uint16_t CMax_uint16_partition_fuzzy(SWIGTYPE_p_uint16_t vals, LongVector ids, long n, long q_min, long q_max, SWIGTYPE_p_unsigned_long q_out) {
    return new SWIGTYPE_p_uint16_t(swigfaissJNI.CMax_uint16_partition_fuzzy__SWIG_0(SWIGTYPE_p_uint16_t.getCPtr(vals), SWIGTYPE_p_long_long.getCPtr(ids.data()), ids, n, q_min, q_max, SWIGTYPE_p_unsigned_long.getCPtr(q_out)), true);
  }

  public static SWIGTYPE_p_uint16_t CMin_uint16_partition_fuzzy(SWIGTYPE_p_uint16_t vals, LongVector ids, long n, long q_min, long q_max, SWIGTYPE_p_unsigned_long q_out) {
    return new SWIGTYPE_p_uint16_t(swigfaissJNI.CMin_uint16_partition_fuzzy__SWIG_0(SWIGTYPE_p_uint16_t.getCPtr(vals), SWIGTYPE_p_long_long.getCPtr(ids.data()), ids, n, q_min, q_max, SWIGTYPE_p_unsigned_long.getCPtr(q_out)), true);
  }

  public static SWIGTYPE_p_uint16_t CMax_uint16_partition_fuzzy(SWIGTYPE_p_uint16_t vals, SWIGTYPE_p_int ids, long n, long q_min, long q_max, SWIGTYPE_p_unsigned_long q_out) {
    return new SWIGTYPE_p_uint16_t(swigfaissJNI.CMax_uint16_partition_fuzzy__SWIG_1(SWIGTYPE_p_uint16_t.getCPtr(vals), SWIGTYPE_p_int.getCPtr(ids), n, q_min, q_max, SWIGTYPE_p_unsigned_long.getCPtr(q_out)), true);
  }

  public static SWIGTYPE_p_uint16_t CMin_uint16_partition_fuzzy(SWIGTYPE_p_uint16_t vals, SWIGTYPE_p_int ids, long n, long q_min, long q_max, SWIGTYPE_p_unsigned_long q_out) {
    return new SWIGTYPE_p_uint16_t(swigfaissJNI.CMin_uint16_partition_fuzzy__SWIG_1(SWIGTYPE_p_uint16_t.getCPtr(vals), SWIGTYPE_p_int.getCPtr(ids), n, q_min, q_max, SWIGTYPE_p_unsigned_long.getCPtr(q_out)), true);
  }

  public static void omp_set_num_threads(int num_threads) {
    swigfaissJNI.omp_set_num_threads(num_threads);
  }

  public static int omp_get_max_threads() {
    return swigfaissJNI.omp_get_max_threads();
  }

  public static SWIGTYPE_p_void memcpy(SWIGTYPE_p_void dest, SWIGTYPE_p_void src, long n) {
    long cPtr = swigfaissJNI.memcpy(SWIGTYPE_p_void.getCPtr(dest), SWIGTYPE_p_void.getCPtr(src), n);
    return (cPtr == 0) ? null : new SWIGTYPE_p_void(cPtr, false);
  }

  public static SWIGTYPE_p_float cast_integer_to_float_ptr(int x) {
    long cPtr = swigfaissJNI.cast_integer_to_float_ptr(x);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public static SWIGTYPE_p_long cast_integer_to_long_ptr(int x) {
    long cPtr = swigfaissJNI.cast_integer_to_long_ptr(x);
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public static SWIGTYPE_p_int cast_integer_to_int_ptr(int x) {
    long cPtr = swigfaissJNI.cast_integer_to_int_ptr(x);
    return (cPtr == 0) ? null : new SWIGTYPE_p_int(cPtr, false);
  }

  public static void ignore_SIGTTIN() {
    swigfaissJNI.ignore_SIGTTIN();
  }

}

} catch (Exception e) {
}
