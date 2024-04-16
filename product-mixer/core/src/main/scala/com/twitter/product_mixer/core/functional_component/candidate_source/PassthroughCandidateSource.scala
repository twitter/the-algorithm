package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Retr eve Cand dates from t  Query
 */
tra  Cand dateExtractor[-Request, +Cand date] {

  def apply(query: Request): Seq[Cand date]
}

/**
 *  dent y extractor for return ng t  Request as a Seq of cand dates
 */
case class  dent yCand dateExtractor[Request]() extends Cand dateExtractor[Request, Request] {

  def apply(cand date: Request): Seq[Request] = Seq(cand date)
}

/**
 * Retr eve Cand dates from a [[Feature]] on t  [[P pel neQuery]]'s FeatureMap. T  extractor
 * supports a transform  f t  Feature value and t  Seq of [[Cand date]] types do not match
 */
tra  QueryFeatureCand dateExtractor[-Query <: P pel neQuery, FeatureValue, +Cand date]
    extends Cand dateExtractor[Query, Cand date] {

  def feature: Feature[Query, FeatureValue]

  overr de def apply(query: Query): Seq[Cand date] =
    query.features.map(featureMap => transform(featureMap.get(feature))).getOrElse(Seq.empty)

  def transform(featureValue: FeatureValue): Seq[Cand date]
}

/**
 * Retr eve Cand dates from a [[Feature]] on t  [[P pel neQuery]]'s FeatureMap. T  extractor can
 * be used w h a s ngle [[Feature]]  f t  Feature value and t  Seq of [[Cand date]] types match.
 */
case class Cand dateQueryFeatureCand dateExtractor[-Query <: P pel neQuery, Cand date](
  overr de val feature: Feature[Query, Seq[Cand date]])
    extends QueryFeatureCand dateExtractor[Query, Seq[Cand date], Cand date] {

  overr de def transform(featureValue: Seq[Cand date]): Seq[Cand date] = featureValue
}

/**
 * A [[Cand dateS ce]] that retr eves cand dates from t  Request v a a [[Cand dateExtractor]]
 */
case class PassthroughCand dateS ce[-Request, +Cand date](
  overr de val  dent f er: Cand dateS ce dent f er,
  cand dateExtractor: Cand dateExtractor[Request, Cand date])
    extends Cand dateS ce[Request, Cand date] {

  def apply(query: Request): St ch[Seq[Cand date]] = St ch.value(cand dateExtractor(query))
}
