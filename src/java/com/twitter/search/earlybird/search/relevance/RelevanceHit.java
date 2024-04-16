package com.tw ter.search.earlyb rd.search.relevance;

 mport java.ut l.Comparator;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common_ nternal.collect ons.RandomAccessPr or yQueue;
 mport com.tw ter.search.common.relevance.features.T et ntegerSh ngleS gnature;
 mport com.tw ter.search.earlyb rd.search.H ;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.Scor ngFunct on;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;

publ c class RelevanceH  extends H 
     mple nts RandomAccessPr or yQueue.S gnatureProv der<T et ntegerSh ngleS gnature> {
  @Nullable
  pr vate T et ntegerSh ngleS gnature s gnature;

  publ c RelevanceH () {
    super(Long.MAX_VALUE, Long.MAX_VALUE);
  }

  publ c RelevanceH (long t  Sl ce D, long status D,
                      T et ntegerSh ngleS gnature s gnature,
                      Thr ftSearchResult tadata  tadata) {
    super(t  Sl ce D, status D);
    update(t  Sl ce D, status D, s gnature,  tadata);
  }

  /**
   * Updates t  data for t  relevance h .
   *
   * @param t  Sl ce D T  t  sl ce  D of t  seg nt that t  seg nt ca  from.
   * @param status D T  h 's t et  D.
   * @param t etS gnature T  t et s gnature generated for t  h .
   * @param  tadata T   tadata assoc ated w h t  h .
   */
  publ c vo d update(long t  Sl ce D, long status D, T et ntegerSh ngleS gnature t etS gnature,
      Thr ftSearchResult tadata  tadata) {
    t .status D = status D;
    t .t  Sl ce D = t  Sl ce D;
    t . tadata = Precond  ons.c ckNotNull( tadata);
    t .s gnature = Precond  ons.c ckNotNull(t etS gnature);
  }

  /**
   * Returns t  computed score for t  h .
   */
  publ c float getScore() {
     f ( tadata != null) {
      return (float)  tadata.getScore();
    } else {
      return Scor ngFunct on.SK P_H T;
    }
  }

  //   want t  score as a double (and not cast to a float) for COMPARATOR_BY_SCORE and
  // PQ_COMPARATOR_BY_SCORE so that t  results returned from Earlyb rds w ll be sorted based on t 
  // scores  n t  Thr ftSearchResult tadata objects (and w ll not lose prec s on by be ng cast to
  // floats). Thus, t  sorted order on Earlyb rds and Earlyb rd Roots w ll be cons stent.
  pr vate double getScoreDouble() {
     f ( tadata != null) {
      return  tadata.getScore();
    } else {
      return (double) Scor ngFunct on.SK P_H T;
    }
  }

  @Overr de @Nullable
  publ c T et ntegerSh ngleS gnature getS gnature() {
    return s gnature;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "RelevanceH [t et D=" + status D + ",t  Sl ce D=" + t  Sl ce D
        + ",score=" + ( tadata == null ? "null" :  tadata.getScore())
        + ",s gnature=" + (s gnature == null ? "null" : s gnature) + "]";
  }

  publ c stat c f nal Comparator<RelevanceH > COMPARATOR_BY_SCORE =
      (d1, d2) -> {
        //  f two docs have t  sa  score, t n t  f rst one (most recent) w ns
         f (d1.getScore() == d2.getScore()) {
          return Long.compare(d2.getStatus D(), d1.getStatus D());
        }
        return Double.compare(d2.getScoreDouble(), d1.getScoreDouble());
      };

  publ c stat c f nal Comparator<RelevanceH > PQ_COMPARATOR_BY_SCORE =
      (d1, d2) -> {
        // Reverse t  order
        return COMPARATOR_BY_SCORE.compare(d2, d1);
      };

  @Overr de
  publ c vo d clear() {
    t  Sl ce D = Long.MAX_VALUE;
    status D = Long.MAX_VALUE;
     tadata = null;
    s gnature = null;
  }
}
