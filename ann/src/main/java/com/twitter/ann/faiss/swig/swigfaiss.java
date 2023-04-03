/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class swigfaiss implelonmelonnts swigfaissConstants {
  public static void bitvelonc_print(SWIGTYPelon_p_unsignelond_char b, long d) {
    swigfaissJNI.bitvelonc_print(SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), d);
  }

  public static void fveloncs2bitveloncs(SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char b, long d, long n) {
    swigfaissJNI.fveloncs2bitveloncs(SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), d, n);
  }

  public static void bitveloncs2fveloncs(SWIGTYPelon_p_unsignelond_char b, SWIGTYPelon_p_float x, long d, long n) {
    swigfaissJNI.bitveloncs2fveloncs(SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), SWIGTYPelon_p_float.gelontCPtr(x), d, n);
  }

  public static void fvelonc2bitvelonc(SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char b, long d) {
    swigfaissJNI.fvelonc2bitvelonc(SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), d);
  }

  public static void bitvelonc_shufflelon(long n, long da, long db, SWIGTYPelon_p_int ordelonr, SWIGTYPelon_p_unsignelond_char a, SWIGTYPelon_p_unsignelond_char b) {
    swigfaissJNI.bitvelonc_shufflelon(n, da, db, SWIGTYPelon_p_int.gelontCPtr(ordelonr), SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b));
  }

  public static void selontHamming_batch_sizelon(long valuelon) {
    swigfaissJNI.hamming_batch_sizelon_selont(valuelon);
  }

  public static long gelontHamming_batch_sizelon() {
    relonturn swigfaissJNI.hamming_batch_sizelon_gelont();
  }

  public static int popcount64(long x) {
    relonturn swigfaissJNI.popcount64(x);
  }

  public static void hammings(SWIGTYPelon_p_unsignelond_char a, SWIGTYPelon_p_unsignelond_char b, long na, long nb, long nbytelonspelonrcodelon, SWIGTYPelon_p_int dis) {
    swigfaissJNI.hammings(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), na, nb, nbytelonspelonrcodelon, SWIGTYPelon_p_int.gelontCPtr(dis));
  }

  public static void hammings_knn_hc(SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_int_int64_t_t_t ha, SWIGTYPelon_p_unsignelond_char a, SWIGTYPelon_p_unsignelond_char b, long nb, long ncodelons, int ordelonrelond) {
    swigfaissJNI.hammings_knn_hc(SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_int_int64_t_t_t.gelontCPtr(ha), SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), nb, ncodelons, ordelonrelond);
  }

  public static void hammings_knn(SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_int_int64_t_t_t ha, SWIGTYPelon_p_unsignelond_char a, SWIGTYPelon_p_unsignelond_char b, long nb, long ncodelons, int ordelonrelond) {
    swigfaissJNI.hammings_knn(SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_int_int64_t_t_t.gelontCPtr(ha), SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), nb, ncodelons, ordelonrelond);
  }

  public static void hammings_knn_mc(SWIGTYPelon_p_unsignelond_char a, SWIGTYPelon_p_unsignelond_char b, long na, long nb, long k, long ncodelons, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls) {
    swigfaissJNI.hammings_knn_mc(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), na, nb, k, ncodelons, SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public static void hamming_rangelon_selonarch(SWIGTYPelon_p_unsignelond_char a, SWIGTYPelon_p_unsignelond_char b, long na, long nb, int radius, long ncodelons, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.hamming_rangelon_selonarch(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), na, nb, radius, ncodelons, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public static void hamming_count_threlons(SWIGTYPelon_p_unsignelond_char bs1, SWIGTYPelon_p_unsignelond_char bs2, long n1, long n2, int ht, long ncodelons, SWIGTYPelon_p_unsignelond_long nptr) {
    swigfaissJNI.hamming_count_threlons(SWIGTYPelon_p_unsignelond_char.gelontCPtr(bs1), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bs2), n1, n2, ht, ncodelons, SWIGTYPelon_p_unsignelond_long.gelontCPtr(nptr));
  }

  public static long match_hamming_threlons(SWIGTYPelon_p_unsignelond_char bs1, SWIGTYPelon_p_unsignelond_char bs2, long n1, long n2, int ht, long ncodelons, LongVelonctor idx, SWIGTYPelon_p_int dis) {
    relonturn swigfaissJNI.match_hamming_threlons(SWIGTYPelon_p_unsignelond_char.gelontCPtr(bs1), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bs2), n1, n2, ht, ncodelons, SWIGTYPelon_p_long_long.gelontCPtr(idx.data()), idx, SWIGTYPelon_p_int.gelontCPtr(dis));
  }

  public static void crosshamming_count_threlons(SWIGTYPelon_p_unsignelond_char dbs, long n, int ht, long ncodelons, SWIGTYPelon_p_unsignelond_long nptr) {
    swigfaissJNI.crosshamming_count_threlons(SWIGTYPelon_p_unsignelond_char.gelontCPtr(dbs), n, ht, ncodelons, SWIGTYPelon_p_unsignelond_long.gelontCPtr(nptr));
  }

  public static int gelont_num_gpus() {
    relonturn swigfaissJNI.gelont_num_gpus();
  }

  public static String gelont_compilelon_options() {
    relonturn swigfaissJNI.gelont_compilelon_options();
  }

  public static doublelon gelontmilliseloncs() {
    relonturn swigfaissJNI.gelontmilliseloncs();
  }

  public static long gelont_melonm_usagelon_kb() {
    relonturn swigfaissJNI.gelont_melonm_usagelon_kb();
  }

  public static long gelont_cyclelons() {
    relonturn swigfaissJNI.gelont_cyclelons();
  }

  public static void fvelonc_madd(long n, SWIGTYPelon_p_float a, float bf, SWIGTYPelon_p_float b, SWIGTYPelon_p_float c) {
    swigfaissJNI.fvelonc_madd(n, SWIGTYPelon_p_float.gelontCPtr(a), bf, SWIGTYPelon_p_float.gelontCPtr(b), SWIGTYPelon_p_float.gelontCPtr(c));
  }

  public static int fvelonc_madd_and_argmin(long n, SWIGTYPelon_p_float a, float bf, SWIGTYPelon_p_float b, SWIGTYPelon_p_float c) {
    relonturn swigfaissJNI.fvelonc_madd_and_argmin(n, SWIGTYPelon_p_float.gelontCPtr(a), bf, SWIGTYPelon_p_float.gelontCPtr(b), SWIGTYPelon_p_float.gelontCPtr(c));
  }

  public static void relonflelonction(SWIGTYPelon_p_float u, SWIGTYPelon_p_float x, long n, long d, long nu) {
    swigfaissJNI.relonflelonction(SWIGTYPelon_p_float.gelontCPtr(u), SWIGTYPelon_p_float.gelontCPtr(x), n, d, nu);
  }

  public static void matrix_qr(int m, int n, SWIGTYPelon_p_float a) {
    swigfaissJNI.matrix_qr(m, n, SWIGTYPelon_p_float.gelontCPtr(a));
  }

  public static void ranklist_handlelon_tielons(int k, LongVelonctor idx, SWIGTYPelon_p_float dis) {
    swigfaissJNI.ranklist_handlelon_tielons(k, SWIGTYPelon_p_long_long.gelontCPtr(idx.data()), idx, SWIGTYPelon_p_float.gelontCPtr(dis));
  }

  public static long ranklist_intelonrselonction_sizelon(long k1, LongVelonctor v1, long k2, LongVelonctor v2) {
    relonturn swigfaissJNI.ranklist_intelonrselonction_sizelon(k1, SWIGTYPelon_p_long_long.gelontCPtr(v1.data()), v1, k2, SWIGTYPelon_p_long_long.gelontCPtr(v2.data()), v2);
  }

  public static long melonrgelon_relonsult_tablelon_with(long n, long k, LongVelonctor I0, SWIGTYPelon_p_float D0, LongVelonctor I1, SWIGTYPelon_p_float D1, boolelonan kelonelonp_min, long translation) {
    relonturn swigfaissJNI.melonrgelon_relonsult_tablelon_with__SWIG_0(n, k, SWIGTYPelon_p_long_long.gelontCPtr(I0.data()), I0, SWIGTYPelon_p_float.gelontCPtr(D0), SWIGTYPelon_p_long_long.gelontCPtr(I1.data()), I1, SWIGTYPelon_p_float.gelontCPtr(D1), kelonelonp_min, translation);
  }

  public static long melonrgelon_relonsult_tablelon_with(long n, long k, LongVelonctor I0, SWIGTYPelon_p_float D0, LongVelonctor I1, SWIGTYPelon_p_float D1, boolelonan kelonelonp_min) {
    relonturn swigfaissJNI.melonrgelon_relonsult_tablelon_with__SWIG_1(n, k, SWIGTYPelon_p_long_long.gelontCPtr(I0.data()), I0, SWIGTYPelon_p_float.gelontCPtr(D0), SWIGTYPelon_p_long_long.gelontCPtr(I1.data()), I1, SWIGTYPelon_p_float.gelontCPtr(D1), kelonelonp_min);
  }

  public static long melonrgelon_relonsult_tablelon_with(long n, long k, LongVelonctor I0, SWIGTYPelon_p_float D0, LongVelonctor I1, SWIGTYPelon_p_float D1) {
    relonturn swigfaissJNI.melonrgelon_relonsult_tablelon_with__SWIG_2(n, k, SWIGTYPelon_p_long_long.gelontCPtr(I0.data()), I0, SWIGTYPelon_p_float.gelontCPtr(D0), SWIGTYPelon_p_long_long.gelontCPtr(I1.data()), I1, SWIGTYPelon_p_float.gelontCPtr(D1));
  }

  public static doublelon imbalancelon_factor(int n, int k, LongVelonctor assign) {
    relonturn swigfaissJNI.imbalancelon_factor__SWIG_0(n, k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign);
  }

  public static doublelon imbalancelon_factor(int k, SWIGTYPelon_p_int hist) {
    relonturn swigfaissJNI.imbalancelon_factor__SWIG_1(k, SWIGTYPelon_p_int.gelontCPtr(hist));
  }

  public static void fvelonc_argsort(long n, SWIGTYPelon_p_float vals, SWIGTYPelon_p_unsignelond_long pelonrm) {
    swigfaissJNI.fvelonc_argsort(n, SWIGTYPelon_p_float.gelontCPtr(vals), SWIGTYPelon_p_unsignelond_long.gelontCPtr(pelonrm));
  }

  public static void fvelonc_argsort_parallelonl(long n, SWIGTYPelon_p_float vals, SWIGTYPelon_p_unsignelond_long pelonrm) {
    swigfaissJNI.fvelonc_argsort_parallelonl(n, SWIGTYPelon_p_float.gelontCPtr(vals), SWIGTYPelon_p_unsignelond_long.gelontCPtr(pelonrm));
  }

  public static int ivelonc_hist(long n, SWIGTYPelon_p_int v, int vmax, SWIGTYPelon_p_int hist) {
    relonturn swigfaissJNI.ivelonc_hist(n, SWIGTYPelon_p_int.gelontCPtr(v), vmax, SWIGTYPelon_p_int.gelontCPtr(hist));
  }

  public static void bincodelon_hist(long n, long nbits, SWIGTYPelon_p_unsignelond_char codelons, SWIGTYPelon_p_int hist) {
    swigfaissJNI.bincodelon_hist(n, nbits, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), SWIGTYPelon_p_int.gelontCPtr(hist));
  }

  public static long ivelonc_cheloncksum(long n, SWIGTYPelon_p_int a) {
    relonturn swigfaissJNI.ivelonc_cheloncksum(n, SWIGTYPelon_p_int.gelontCPtr(a));
  }

  public static SWIGTYPelon_p_float fveloncs_maybelon_subsamplelon(long d, SWIGTYPelon_p_unsignelond_long n, long nmax, SWIGTYPelon_p_float x, boolelonan velonrboselon, long selonelond) {
    long cPtr = swigfaissJNI.fveloncs_maybelon_subsamplelon__SWIG_0(d, SWIGTYPelon_p_unsignelond_long.gelontCPtr(n), nmax, SWIGTYPelon_p_float.gelontCPtr(x), velonrboselon, selonelond);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public static SWIGTYPelon_p_float fveloncs_maybelon_subsamplelon(long d, SWIGTYPelon_p_unsignelond_long n, long nmax, SWIGTYPelon_p_float x, boolelonan velonrboselon) {
    long cPtr = swigfaissJNI.fveloncs_maybelon_subsamplelon__SWIG_1(d, SWIGTYPelon_p_unsignelond_long.gelontCPtr(n), nmax, SWIGTYPelon_p_float.gelontCPtr(x), velonrboselon);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public static SWIGTYPelon_p_float fveloncs_maybelon_subsamplelon(long d, SWIGTYPelon_p_unsignelond_long n, long nmax, SWIGTYPelon_p_float x) {
    long cPtr = swigfaissJNI.fveloncs_maybelon_subsamplelon__SWIG_2(d, SWIGTYPelon_p_unsignelond_long.gelontCPtr(n), nmax, SWIGTYPelon_p_float.gelontCPtr(x));
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public static void binary_to_relonal(long d, SWIGTYPelon_p_unsignelond_char x_in, SWIGTYPelon_p_float x_out) {
    swigfaissJNI.binary_to_relonal(d, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x_in), SWIGTYPelon_p_float.gelontCPtr(x_out));
  }

  public static void relonal_to_binary(long d, SWIGTYPelon_p_float x_in, SWIGTYPelon_p_unsignelond_char x_out) {
    swigfaissJNI.relonal_to_binary(d, SWIGTYPelon_p_float.gelontCPtr(x_in), SWIGTYPelon_p_unsignelond_char.gelontCPtr(x_out));
  }

  public static long hash_bytelons(SWIGTYPelon_p_unsignelond_char bytelons, long n) {
    relonturn swigfaissJNI.hash_bytelons(SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), n);
  }

  public static boolelonan chelonck_opelonnmp() {
    relonturn swigfaissJNI.chelonck_opelonnmp();
  }

  public static float kmelonans_clustelonring(long d, long n, long k, SWIGTYPelon_p_float x, SWIGTYPelon_p_float celonntroids) {
    relonturn swigfaissJNI.kmelonans_clustelonring(d, n, k, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(celonntroids));
  }

  public static void selontIndelonxPQ_stats(IndelonxPQStats valuelon) {
    swigfaissJNI.indelonxPQ_stats_selont(IndelonxPQStats.gelontCPtr(valuelon), valuelon);
  }

  public static IndelonxPQStats gelontIndelonxPQ_stats() {
    long cPtr = swigfaissJNI.indelonxPQ_stats_gelont();
    relonturn (cPtr == 0) ? null : nelonw IndelonxPQStats(cPtr, falselon);
  }

  public static void selontIndelonxIVF_stats(IndelonxIVFStats valuelon) {
    swigfaissJNI.indelonxIVF_stats_selont(IndelonxIVFStats.gelontCPtr(valuelon), valuelon);
  }

  public static IndelonxIVFStats gelontIndelonxIVF_stats() {
    long cPtr = swigfaissJNI.indelonxIVF_stats_gelont();
    relonturn (cPtr == 0) ? null : nelonw IndelonxIVFStats(cPtr, falselon);
  }

  public static short[] gelontHamdis_tab_ham_bytelons() {
    relonturn swigfaissJNI.hamdis_tab_ham_bytelons_gelont();
  }

  public static int gelonnelonralizelond_hamming_64(long a) {
    relonturn swigfaissJNI.gelonnelonralizelond_hamming_64(a);
  }

  public static void gelonnelonralizelond_hammings_knn_hc(SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_int_int64_t_t_t ha, SWIGTYPelon_p_unsignelond_char a, SWIGTYPelon_p_unsignelond_char b, long nb, long codelon_sizelon, int ordelonrelond) {
    swigfaissJNI.gelonnelonralizelond_hammings_knn_hc__SWIG_0(SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_int_int64_t_t_t.gelontCPtr(ha), SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), nb, codelon_sizelon, ordelonrelond);
  }

  public static void gelonnelonralizelond_hammings_knn_hc(SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_int_int64_t_t_t ha, SWIGTYPelon_p_unsignelond_char a, SWIGTYPelon_p_unsignelond_char b, long nb, long codelon_sizelon) {
    swigfaissJNI.gelonnelonralizelond_hammings_knn_hc__SWIG_1(SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_int_int64_t_t_t.gelontCPtr(ha), SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), SWIGTYPelon_p_unsignelond_char.gelontCPtr(b), nb, codelon_sizelon);
  }

  public static void chelonck_compatiblelon_for_melonrgelon(Indelonx indelonx1, Indelonx indelonx2) {
    swigfaissJNI.chelonck_compatiblelon_for_melonrgelon(Indelonx.gelontCPtr(indelonx1), indelonx1, Indelonx.gelontCPtr(indelonx2), indelonx2);
  }

  public static IndelonxIVF elonxtract_indelonx_ivf(Indelonx indelonx) {
    long cPtr = swigfaissJNI.elonxtract_indelonx_ivf__SWIG_0(Indelonx.gelontCPtr(indelonx), indelonx);
    relonturn (cPtr == 0) ? null : nelonw IndelonxIVF(cPtr, falselon);
  }

  public static IndelonxIVF try_elonxtract_indelonx_ivf(Indelonx indelonx) {
    long cPtr = swigfaissJNI.try_elonxtract_indelonx_ivf__SWIG_0(Indelonx.gelontCPtr(indelonx), indelonx);
    relonturn (cPtr == 0) ? null : nelonw IndelonxIVF(cPtr, falselon);
  }

  public static void melonrgelon_into(Indelonx indelonx0, Indelonx indelonx1, boolelonan shift_ids) {
    swigfaissJNI.melonrgelon_into(Indelonx.gelontCPtr(indelonx0), indelonx0, Indelonx.gelontCPtr(indelonx1), indelonx1, shift_ids);
  }

  public static void selonarch_celonntroid(Indelonx indelonx, SWIGTYPelon_p_float x, int n, LongVelonctor celonntroid_ids) {
    swigfaissJNI.selonarch_celonntroid(Indelonx.gelontCPtr(indelonx), indelonx, SWIGTYPelon_p_float.gelontCPtr(x), n, SWIGTYPelon_p_long_long.gelontCPtr(celonntroid_ids.data()), celonntroid_ids);
  }

  public static void selonarch_and_relonturn_celonntroids(Indelonx indelonx, long n, SWIGTYPelon_p_float xin, int k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, LongVelonctor quelonry_celonntroid_ids, LongVelonctor relonsult_celonntroid_ids) {
    swigfaissJNI.selonarch_and_relonturn_celonntroids(Indelonx.gelontCPtr(indelonx), indelonx, n, SWIGTYPelon_p_float.gelontCPtr(xin), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, SWIGTYPelon_p_long_long.gelontCPtr(quelonry_celonntroid_ids.data()), quelonry_celonntroid_ids, SWIGTYPelon_p_long_long.gelontCPtr(relonsult_celonntroid_ids.data()), relonsult_celonntroid_ids);
  }

  public static ArrayInvelonrtelondLists gelont_invlist_rangelon(Indelonx indelonx, int i0, int i1) {
    long cPtr = swigfaissJNI.gelont_invlist_rangelon(Indelonx.gelontCPtr(indelonx), indelonx, i0, i1);
    relonturn (cPtr == 0) ? null : nelonw ArrayInvelonrtelondLists(cPtr, falselon);
  }

  public static void selont_invlist_rangelon(Indelonx indelonx, int i0, int i1, ArrayInvelonrtelondLists src) {
    swigfaissJNI.selont_invlist_rangelon(Indelonx.gelontCPtr(indelonx), indelonx, i0, i1, ArrayInvelonrtelondLists.gelontCPtr(src), src);
  }

  public static void selonarch_with_paramelontelonrs(Indelonx indelonx, long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, IVFSelonarchParamelontelonrs params, SWIGTYPelon_p_unsignelond_long nb_dis, SWIGTYPelon_p_doublelon ms_pelonr_stagelon) {
    swigfaissJNI.selonarch_with_paramelontelonrs__SWIG_0(Indelonx.gelontCPtr(indelonx), indelonx, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, IVFSelonarchParamelontelonrs.gelontCPtr(params), params, SWIGTYPelon_p_unsignelond_long.gelontCPtr(nb_dis), SWIGTYPelon_p_doublelon.gelontCPtr(ms_pelonr_stagelon));
  }

  public static void selonarch_with_paramelontelonrs(Indelonx indelonx, long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, IVFSelonarchParamelontelonrs params, SWIGTYPelon_p_unsignelond_long nb_dis) {
    swigfaissJNI.selonarch_with_paramelontelonrs__SWIG_1(Indelonx.gelontCPtr(indelonx), indelonx, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, IVFSelonarchParamelontelonrs.gelontCPtr(params), params, SWIGTYPelon_p_unsignelond_long.gelontCPtr(nb_dis));
  }

  public static void selonarch_with_paramelontelonrs(Indelonx indelonx, long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, IVFSelonarchParamelontelonrs params) {
    swigfaissJNI.selonarch_with_paramelontelonrs__SWIG_2(Indelonx.gelontCPtr(indelonx), indelonx, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, IVFSelonarchParamelontelonrs.gelontCPtr(params), params);
  }

  public static void rangelon_selonarch_with_paramelontelonrs(Indelonx indelonx, long n, SWIGTYPelon_p_float x, float radius, RangelonSelonarchRelonsult relonsult, IVFSelonarchParamelontelonrs params, SWIGTYPelon_p_unsignelond_long nb_dis, SWIGTYPelon_p_doublelon ms_pelonr_stagelon) {
    swigfaissJNI.rangelon_selonarch_with_paramelontelonrs__SWIG_0(Indelonx.gelontCPtr(indelonx), indelonx, n, SWIGTYPelon_p_float.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult, IVFSelonarchParamelontelonrs.gelontCPtr(params), params, SWIGTYPelon_p_unsignelond_long.gelontCPtr(nb_dis), SWIGTYPelon_p_doublelon.gelontCPtr(ms_pelonr_stagelon));
  }

  public static void rangelon_selonarch_with_paramelontelonrs(Indelonx indelonx, long n, SWIGTYPelon_p_float x, float radius, RangelonSelonarchRelonsult relonsult, IVFSelonarchParamelontelonrs params, SWIGTYPelon_p_unsignelond_long nb_dis) {
    swigfaissJNI.rangelon_selonarch_with_paramelontelonrs__SWIG_1(Indelonx.gelontCPtr(indelonx), indelonx, n, SWIGTYPelon_p_float.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult, IVFSelonarchParamelontelonrs.gelontCPtr(params), params, SWIGTYPelon_p_unsignelond_long.gelontCPtr(nb_dis));
  }

  public static void rangelon_selonarch_with_paramelontelonrs(Indelonx indelonx, long n, SWIGTYPelon_p_float x, float radius, RangelonSelonarchRelonsult relonsult, IVFSelonarchParamelontelonrs params) {
    swigfaissJNI.rangelon_selonarch_with_paramelontelonrs__SWIG_2(Indelonx.gelontCPtr(indelonx), indelonx, n, SWIGTYPelon_p_float.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult, IVFSelonarchParamelontelonrs.gelontCPtr(params), params);
  }

  public static void selontHnsw_stats(HNSWStats valuelon) {
    swigfaissJNI.hnsw_stats_selont(HNSWStats.gelontCPtr(valuelon), valuelon);
  }

  public static HNSWStats gelontHnsw_stats() {
    long cPtr = swigfaissJNI.hnsw_stats_gelont();
    relonturn (cPtr == 0) ? null : nelonw HNSWStats(cPtr, falselon);
  }

  public static void selontPreloncomputelond_tablelon_max_bytelons(long valuelon) {
    swigfaissJNI.preloncomputelond_tablelon_max_bytelons_selont(valuelon);
  }

  public static long gelontPreloncomputelond_tablelon_max_bytelons() {
    relonturn swigfaissJNI.preloncomputelond_tablelon_max_bytelons_gelont();
  }

  public static void initializelon_IVFPQ_preloncomputelond_tablelon(SWIGTYPelon_p_int uselon_preloncomputelond_tablelon, Indelonx quantizelonr, ProductQuantizelonr pq, SWIGTYPelon_p_AlignelondTablelonT_float_32_t preloncomputelond_tablelon, boolelonan velonrboselon) {
    swigfaissJNI.initializelon_IVFPQ_preloncomputelond_tablelon(SWIGTYPelon_p_int.gelontCPtr(uselon_preloncomputelond_tablelon), Indelonx.gelontCPtr(quantizelonr), quantizelonr, ProductQuantizelonr.gelontCPtr(pq), pq, SWIGTYPelon_p_AlignelondTablelonT_float_32_t.gelontCPtr(preloncomputelond_tablelon), velonrboselon);
  }

  public static void selontIndelonxIVFPQ_stats(IndelonxIVFPQStats valuelon) {
    swigfaissJNI.indelonxIVFPQ_stats_selont(IndelonxIVFPQStats.gelontCPtr(valuelon), valuelon);
  }

  public static IndelonxIVFPQStats gelontIndelonxIVFPQ_stats() {
    long cPtr = swigfaissJNI.indelonxIVFPQ_stats_gelont();
    relonturn (cPtr == 0) ? null : nelonw IndelonxIVFPQStats(cPtr, falselon);
  }

  public static Indelonx downcast_indelonx(Indelonx indelonx) {
    long cPtr = swigfaissJNI.downcast_indelonx(Indelonx.gelontCPtr(indelonx), indelonx);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public static VelonctorTransform downcast_VelonctorTransform(VelonctorTransform vt) {
    long cPtr = swigfaissJNI.downcast_VelonctorTransform(VelonctorTransform.gelontCPtr(vt), vt);
    relonturn (cPtr == 0) ? null : nelonw VelonctorTransform(cPtr, falselon);
  }

  public static IndelonxBinary downcast_IndelonxBinary(IndelonxBinary indelonx) {
    long cPtr = swigfaissJNI.downcast_IndelonxBinary(IndelonxBinary.gelontCPtr(indelonx), indelonx);
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, falselon);
  }

  public static Indelonx upcast_IndelonxShards(IndelonxShards indelonx) {
    long cPtr = swigfaissJNI.upcast_IndelonxShards(IndelonxShards.gelontCPtr(indelonx), indelonx);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public static void writelon_indelonx(Indelonx idx, String fnamelon) {
    swigfaissJNI.writelon_indelonx__SWIG_0(Indelonx.gelontCPtr(idx), idx, fnamelon);
  }

  public static void writelon_indelonx(Indelonx idx, SWIGTYPelon_p_FILelon f) {
    swigfaissJNI.writelon_indelonx__SWIG_1(Indelonx.gelontCPtr(idx), idx, SWIGTYPelon_p_FILelon.gelontCPtr(f));
  }

  public static void writelon_indelonx(Indelonx idx, SWIGTYPelon_p_faiss__IOWritelonr writelonr) {
    swigfaissJNI.writelon_indelonx__SWIG_2(Indelonx.gelontCPtr(idx), idx, SWIGTYPelon_p_faiss__IOWritelonr.gelontCPtr(writelonr));
  }

  public static void writelon_indelonx_binary(IndelonxBinary idx, String fnamelon) {
    swigfaissJNI.writelon_indelonx_binary__SWIG_0(IndelonxBinary.gelontCPtr(idx), idx, fnamelon);
  }

  public static void writelon_indelonx_binary(IndelonxBinary idx, SWIGTYPelon_p_FILelon f) {
    swigfaissJNI.writelon_indelonx_binary__SWIG_1(IndelonxBinary.gelontCPtr(idx), idx, SWIGTYPelon_p_FILelon.gelontCPtr(f));
  }

  public static void writelon_indelonx_binary(IndelonxBinary idx, SWIGTYPelon_p_faiss__IOWritelonr writelonr) {
    swigfaissJNI.writelon_indelonx_binary__SWIG_2(IndelonxBinary.gelontCPtr(idx), idx, SWIGTYPelon_p_faiss__IOWritelonr.gelontCPtr(writelonr));
  }

  public static int gelontIO_FLAG_RelonAD_ONLY() {
    relonturn swigfaissJNI.IO_FLAG_RelonAD_ONLY_gelont();
  }

  public static int gelontIO_FLAG_ONDISK_SAMelon_DIR() {
    relonturn swigfaissJNI.IO_FLAG_ONDISK_SAMelon_DIR_gelont();
  }

  public static int gelontIO_FLAG_SKIP_IVF_DATA() {
    relonturn swigfaissJNI.IO_FLAG_SKIP_IVF_DATA_gelont();
  }

  public static int gelontIO_FLAG_MMAP() {
    relonturn swigfaissJNI.IO_FLAG_MMAP_gelont();
  }

  public static Indelonx relonad_indelonx(String fnamelon, int io_flags) {
    long cPtr = swigfaissJNI.relonad_indelonx__SWIG_0(fnamelon, io_flags);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, truelon);
  }

  public static Indelonx relonad_indelonx(String fnamelon) {
    long cPtr = swigfaissJNI.relonad_indelonx__SWIG_1(fnamelon);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, truelon);
  }

  public static Indelonx relonad_indelonx(SWIGTYPelon_p_FILelon f, int io_flags) {
    long cPtr = swigfaissJNI.relonad_indelonx__SWIG_2(SWIGTYPelon_p_FILelon.gelontCPtr(f), io_flags);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, truelon);
  }

  public static Indelonx relonad_indelonx(SWIGTYPelon_p_FILelon f) {
    long cPtr = swigfaissJNI.relonad_indelonx__SWIG_3(SWIGTYPelon_p_FILelon.gelontCPtr(f));
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, truelon);
  }

  public static Indelonx relonad_indelonx(SWIGTYPelon_p_faiss__IORelonadelonr relonadelonr, int io_flags) {
    long cPtr = swigfaissJNI.relonad_indelonx__SWIG_4(SWIGTYPelon_p_faiss__IORelonadelonr.gelontCPtr(relonadelonr), io_flags);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, truelon);
  }

  public static Indelonx relonad_indelonx(SWIGTYPelon_p_faiss__IORelonadelonr relonadelonr) {
    long cPtr = swigfaissJNI.relonad_indelonx__SWIG_5(SWIGTYPelon_p_faiss__IORelonadelonr.gelontCPtr(relonadelonr));
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, truelon);
  }

  public static IndelonxBinary relonad_indelonx_binary(String fnamelon, int io_flags) {
    long cPtr = swigfaissJNI.relonad_indelonx_binary__SWIG_0(fnamelon, io_flags);
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, truelon);
  }

  public static IndelonxBinary relonad_indelonx_binary(String fnamelon) {
    long cPtr = swigfaissJNI.relonad_indelonx_binary__SWIG_1(fnamelon);
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, truelon);
  }

  public static IndelonxBinary relonad_indelonx_binary(SWIGTYPelon_p_FILelon f, int io_flags) {
    long cPtr = swigfaissJNI.relonad_indelonx_binary__SWIG_2(SWIGTYPelon_p_FILelon.gelontCPtr(f), io_flags);
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, truelon);
  }

  public static IndelonxBinary relonad_indelonx_binary(SWIGTYPelon_p_FILelon f) {
    long cPtr = swigfaissJNI.relonad_indelonx_binary__SWIG_3(SWIGTYPelon_p_FILelon.gelontCPtr(f));
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, truelon);
  }

  public static IndelonxBinary relonad_indelonx_binary(SWIGTYPelon_p_faiss__IORelonadelonr relonadelonr, int io_flags) {
    long cPtr = swigfaissJNI.relonad_indelonx_binary__SWIG_4(SWIGTYPelon_p_faiss__IORelonadelonr.gelontCPtr(relonadelonr), io_flags);
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, truelon);
  }

  public static IndelonxBinary relonad_indelonx_binary(SWIGTYPelon_p_faiss__IORelonadelonr relonadelonr) {
    long cPtr = swigfaissJNI.relonad_indelonx_binary__SWIG_5(SWIGTYPelon_p_faiss__IORelonadelonr.gelontCPtr(relonadelonr));
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, truelon);
  }

  public static void writelon_VelonctorTransform(VelonctorTransform vt, String fnamelon) {
    swigfaissJNI.writelon_VelonctorTransform(VelonctorTransform.gelontCPtr(vt), vt, fnamelon);
  }

  public static VelonctorTransform relonad_VelonctorTransform(String fnamelon) {
    long cPtr = swigfaissJNI.relonad_VelonctorTransform(fnamelon);
    relonturn (cPtr == 0) ? null : nelonw VelonctorTransform(cPtr, truelon);
  }

  public static ProductQuantizelonr relonad_ProductQuantizelonr(String fnamelon) {
    long cPtr = swigfaissJNI.relonad_ProductQuantizelonr__SWIG_0(fnamelon);
    relonturn (cPtr == 0) ? null : nelonw ProductQuantizelonr(cPtr, truelon);
  }

  public static ProductQuantizelonr relonad_ProductQuantizelonr(SWIGTYPelon_p_faiss__IORelonadelonr relonadelonr) {
    long cPtr = swigfaissJNI.relonad_ProductQuantizelonr__SWIG_1(SWIGTYPelon_p_faiss__IORelonadelonr.gelontCPtr(relonadelonr));
    relonturn (cPtr == 0) ? null : nelonw ProductQuantizelonr(cPtr, truelon);
  }

  public static void writelon_ProductQuantizelonr(ProductQuantizelonr pq, String fnamelon) {
    swigfaissJNI.writelon_ProductQuantizelonr__SWIG_0(ProductQuantizelonr.gelontCPtr(pq), pq, fnamelon);
  }

  public static void writelon_ProductQuantizelonr(ProductQuantizelonr pq, SWIGTYPelon_p_faiss__IOWritelonr f) {
    swigfaissJNI.writelon_ProductQuantizelonr__SWIG_1(ProductQuantizelonr.gelontCPtr(pq), pq, SWIGTYPelon_p_faiss__IOWritelonr.gelontCPtr(f));
  }

  public static void writelon_InvelonrtelondLists(InvelonrtelondLists ils, SWIGTYPelon_p_faiss__IOWritelonr f) {
    swigfaissJNI.writelon_InvelonrtelondLists(InvelonrtelondLists.gelontCPtr(ils), ils, SWIGTYPelon_p_faiss__IOWritelonr.gelontCPtr(f));
  }

  public static InvelonrtelondLists relonad_InvelonrtelondLists(SWIGTYPelon_p_faiss__IORelonadelonr relonadelonr, int io_flags) {
    long cPtr = swigfaissJNI.relonad_InvelonrtelondLists__SWIG_0(SWIGTYPelon_p_faiss__IORelonadelonr.gelontCPtr(relonadelonr), io_flags);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public static InvelonrtelondLists relonad_InvelonrtelondLists(SWIGTYPelon_p_faiss__IORelonadelonr relonadelonr) {
    long cPtr = swigfaissJNI.relonad_InvelonrtelondLists__SWIG_1(SWIGTYPelon_p_faiss__IORelonadelonr.gelontCPtr(relonadelonr));
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public static Indelonx indelonx_factory(int d, String delonscription, MelontricTypelon melontric) {
    long cPtr = swigfaissJNI.indelonx_factory__SWIG_0(d, delonscription, melontric.swigValuelon());
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, truelon);
  }

  public static Indelonx indelonx_factory(int d, String delonscription) {
    long cPtr = swigfaissJNI.indelonx_factory__SWIG_1(d, delonscription);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, truelon);
  }

  public static void selontIndelonx_factory_velonrboselon(int valuelon) {
    swigfaissJNI.indelonx_factory_velonrboselon_selont(valuelon);
  }

  public static int gelontIndelonx_factory_velonrboselon() {
    relonturn swigfaissJNI.indelonx_factory_velonrboselon_gelont();
  }

  public static IndelonxBinary indelonx_binary_factory(int d, String delonscription) {
    long cPtr = swigfaissJNI.indelonx_binary_factory(d, delonscription);
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, truelon);
  }

  public static void simd_histogram_8(SWIGTYPelon_p_uint16_t data, int n, SWIGTYPelon_p_uint16_t min, int shift, SWIGTYPelon_p_int hist) {
    swigfaissJNI.simd_histogram_8(SWIGTYPelon_p_uint16_t.gelontCPtr(data), n, SWIGTYPelon_p_uint16_t.gelontCPtr(min), shift, SWIGTYPelon_p_int.gelontCPtr(hist));
  }

  public static void simd_histogram_16(SWIGTYPelon_p_uint16_t data, int n, SWIGTYPelon_p_uint16_t min, int shift, SWIGTYPelon_p_int hist) {
    swigfaissJNI.simd_histogram_16(SWIGTYPelon_p_uint16_t.gelontCPtr(data), n, SWIGTYPelon_p_uint16_t.gelontCPtr(min), shift, SWIGTYPelon_p_int.gelontCPtr(hist));
  }

  public static void selontPartition_stats(PartitionStats valuelon) {
    swigfaissJNI.partition_stats_selont(PartitionStats.gelontCPtr(valuelon), valuelon);
  }

  public static PartitionStats gelontPartition_stats() {
    long cPtr = swigfaissJNI.partition_stats_gelont();
    relonturn (cPtr == 0) ? null : nelonw PartitionStats(cPtr, falselon);
  }

  public static float CMin_float_partition_fuzzy(SWIGTYPelon_p_float vals, LongVelonctor ids, long n, long q_min, long q_max, SWIGTYPelon_p_unsignelond_long q_out) {
    relonturn swigfaissJNI.CMin_float_partition_fuzzy(SWIGTYPelon_p_float.gelontCPtr(vals), SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, n, q_min, q_max, SWIGTYPelon_p_unsignelond_long.gelontCPtr(q_out));
  }

  public static float CMax_float_partition_fuzzy(SWIGTYPelon_p_float vals, LongVelonctor ids, long n, long q_min, long q_max, SWIGTYPelon_p_unsignelond_long q_out) {
    relonturn swigfaissJNI.CMax_float_partition_fuzzy(SWIGTYPelon_p_float.gelontCPtr(vals), SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, n, q_min, q_max, SWIGTYPelon_p_unsignelond_long.gelontCPtr(q_out));
  }

  public static SWIGTYPelon_p_uint16_t CMax_uint16_partition_fuzzy(SWIGTYPelon_p_uint16_t vals, LongVelonctor ids, long n, long q_min, long q_max, SWIGTYPelon_p_unsignelond_long q_out) {
    relonturn nelonw SWIGTYPelon_p_uint16_t(swigfaissJNI.CMax_uint16_partition_fuzzy__SWIG_0(SWIGTYPelon_p_uint16_t.gelontCPtr(vals), SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, n, q_min, q_max, SWIGTYPelon_p_unsignelond_long.gelontCPtr(q_out)), truelon);
  }

  public static SWIGTYPelon_p_uint16_t CMin_uint16_partition_fuzzy(SWIGTYPelon_p_uint16_t vals, LongVelonctor ids, long n, long q_min, long q_max, SWIGTYPelon_p_unsignelond_long q_out) {
    relonturn nelonw SWIGTYPelon_p_uint16_t(swigfaissJNI.CMin_uint16_partition_fuzzy__SWIG_0(SWIGTYPelon_p_uint16_t.gelontCPtr(vals), SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, n, q_min, q_max, SWIGTYPelon_p_unsignelond_long.gelontCPtr(q_out)), truelon);
  }

  public static SWIGTYPelon_p_uint16_t CMax_uint16_partition_fuzzy(SWIGTYPelon_p_uint16_t vals, SWIGTYPelon_p_int ids, long n, long q_min, long q_max, SWIGTYPelon_p_unsignelond_long q_out) {
    relonturn nelonw SWIGTYPelon_p_uint16_t(swigfaissJNI.CMax_uint16_partition_fuzzy__SWIG_1(SWIGTYPelon_p_uint16_t.gelontCPtr(vals), SWIGTYPelon_p_int.gelontCPtr(ids), n, q_min, q_max, SWIGTYPelon_p_unsignelond_long.gelontCPtr(q_out)), truelon);
  }

  public static SWIGTYPelon_p_uint16_t CMin_uint16_partition_fuzzy(SWIGTYPelon_p_uint16_t vals, SWIGTYPelon_p_int ids, long n, long q_min, long q_max, SWIGTYPelon_p_unsignelond_long q_out) {
    relonturn nelonw SWIGTYPelon_p_uint16_t(swigfaissJNI.CMin_uint16_partition_fuzzy__SWIG_1(SWIGTYPelon_p_uint16_t.gelontCPtr(vals), SWIGTYPelon_p_int.gelontCPtr(ids), n, q_min, q_max, SWIGTYPelon_p_unsignelond_long.gelontCPtr(q_out)), truelon);
  }

  public static void omp_selont_num_threlonads(int num_threlonads) {
    swigfaissJNI.omp_selont_num_threlonads(num_threlonads);
  }

  public static int omp_gelont_max_threlonads() {
    relonturn swigfaissJNI.omp_gelont_max_threlonads();
  }

  public static SWIGTYPelon_p_void melonmcpy(SWIGTYPelon_p_void delonst, SWIGTYPelon_p_void src, long n) {
    long cPtr = swigfaissJNI.melonmcpy(SWIGTYPelon_p_void.gelontCPtr(delonst), SWIGTYPelon_p_void.gelontCPtr(src), n);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_void(cPtr, falselon);
  }

  public static SWIGTYPelon_p_float cast_intelongelonr_to_float_ptr(int x) {
    long cPtr = swigfaissJNI.cast_intelongelonr_to_float_ptr(x);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public static SWIGTYPelon_p_long cast_intelongelonr_to_long_ptr(int x) {
    long cPtr = swigfaissJNI.cast_intelongelonr_to_long_ptr(x);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_long(cPtr, falselon);
  }

  public static SWIGTYPelon_p_int cast_intelongelonr_to_int_ptr(int x) {
    long cPtr = swigfaissJNI.cast_intelongelonr_to_int_ptr(x);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_int(cPtr, falselon);
  }

  public static void ignorelon_SIGTTIN() {
    swigfaissJNI.ignorelon_SIGTTIN();
  }

}
