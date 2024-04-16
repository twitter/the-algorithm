package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ads.ent  es.db.{thr ftscala => ae}
 mport com.tw ter.g zmoduck.{thr ftscala => gt}
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sBlueVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sProtectedFeature
 mport com.tw ter.ho _m xer.model.Ho Features.From nNetworkS ceFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sSupportAccountReplyFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.snowflake. d.Snowflake d
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class G zmoduckAuthorFeatureHydrator @ nject() (g zmoduck: gt.UserServ ce. thodPerEndpo nt)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("G zmoduckAuthor")

  overr de val features: Set[Feature[_, _]] =
    Set(Author sBlueVer f edFeature, Author sProtectedFeature,  sSupportAccountReplyFeature)

  pr vate val queryF elds: Set[gt.QueryF elds] =
    Set(gt.QueryF elds.Advert serAccount, gt.QueryF elds.Prof le, gt.QueryF elds.Safety)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val author ds = cand dates.flatMap(_.features.getOrElse(Author dFeature, None))

    val response = g zmoduck.get(
      user ds = author ds.d st nct,
      queryF elds = queryF elds,
      context = gt.LookupContext()
    )

    response.map { hydratedAuthors =>
      val user tadataMap = hydratedAuthors
        .collect {
          case userResult  f userResult.user. sDef ned =>
            val user = userResult.user.get
            val blueVer f ed = user.safety.flatMap(_. sBlueVer f ed).getOrElse(false)
            val  sProtected = user.safety.ex sts(_. sProtected)
            (user. d, (blueVer f ed,  sProtected))
        }.toMap.w hDefaultValue((false, false))

      cand dates.map { cand date =>
        val author d = cand date.features.get(Author dFeature).get
        val ( sBlueVer f ed,  sProtected) = user tadataMap(author d)

        // So  accounts run promot ons on Tw ter and send repl es automat cally.
        //   assu  that a reply that took more than one m nute  s not an auto-reply.
        //  f t   d fference doesn't ex st, t   ans that one of t  t ets was
        // not snowflake and t refore much older, and t refore OK as an extended reply.
        val t  D fference = cand date.features.getOrElse( nReplyToT et dFeature, None).map {
          Snowflake d.t  From d(cand date.cand date. d) - Snowflake d.t  From d(_)
        }
        val  sAutoReply = t  D fference.ex sts(_ < 1.m nute)

        FeatureMapBu lder()
          .add(Author sBlueVer f edFeature,  sBlueVer f ed)
          .add(Author sProtectedFeature,  sProtected)
          .add( sSupportAccountReplyFeature,  sAutoReply)
          .bu ld()
      }
    }
  }
}
