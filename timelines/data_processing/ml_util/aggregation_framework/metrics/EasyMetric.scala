package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ml.ap ._

/**
 * A "human-readable"  tr c that can be appl ed to features of mult ple
 * d fferent types. Wrapper around Aggregat on tr c used as syntact c sugar
 * for eas er conf g.
 */
tra  Easy tr c extends Ser al zable {
  /*
   * G ven a feature type, fetc s t  corrrect underly ng Aggregat on tr c
   * to perform t  operat on over t  g ven feature type,  f any.  f no such
   *  tr c  s ava lable, returns None. For example, MEAN cannot be appl ed
   * to FeatureType.Str ng and would return None.
   *
   * @param featureType Type of feature to fetch  tr c for
   * @param useF xedDecay Param to control w t r t   tr c should use f xed decay
   *   log c ( f appropr ate)
   * @return Strongly typed aggregat on  tr c to use for t  feature type
   *
   * For example,  f t  Easy tr c  s MEAN and t  featureType  s
   * FeatureType.Cont nuous, t  underly ng Aggregat on tr c should be a
   * scalar  an.  f t  Easy tr c  s MEAN and t  featureType  s
   * FeatureType.SparseCont nuous, t  Aggregat on tr c returned could be a
   * "vector"  an that averages sparse maps. Us ng t  s ngle log cal na 
   * MEAN for both  s n ce syntact c sugar mak ng for an eas er to read top
   * level conf g, though d fferent underly ng operators are used underneath
   * for t  actual  mple ntat on.
   */
  def forFeatureType[T](
    featureType: FeatureType,
  ): Opt on[Aggregat on tr c[T, _]]
}
