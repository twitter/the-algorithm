package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.adapters

 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport com.tw ter.ut l.T  

/**
 * T  adapter m m cs UserRecentWTF mpress onsAndFollowsAdapter (for user) and
 * RecentWTF mpress onsFeatureAdapter (for cand date) for extract ng recent  mpress on
 * and follow features. T  adapter extracts user, cand date, and pa r-w se features.
 */
object PreFetc dFeatureAdapter
    extends  RecordOneToOneAdapter[
      (HasPreFetc dFeature, Cand dateUser)
    ] {

  //  mpress on features
  val USER_NUM_RECENT_ MPRESS ONS: Cont nuous = new Cont nuous(
    "user.prefetch.num_recent_ mpress ons"
  )
  val USER_LAST_ MPRESS ON_DURAT ON: Cont nuous = new Cont nuous(
    "user.prefetch.last_ mpress on_durat on"
  )
  val CAND DATE_NUM_RECENT_ MPRESS ONS: Cont nuous = new Cont nuous(
    "user-cand date.prefetch.num_recent_ mpress ons"
  )
  val CAND DATE_LAST_ MPRESS ON_DURAT ON: Cont nuous = new Cont nuous(
    "user-cand date.prefetch.last_ mpress on_durat on"
  )
  // follow features
  val USER_NUM_RECENT_FOLLOWERS: Cont nuous = new Cont nuous(
    "user.prefetch.num_recent_follo rs"
  )
  val USER_NUM_RECENT_FOLLOWED_BY: Cont nuous = new Cont nuous(
    "user.prefetch.num_recent_follo d_by"
  )
  val USER_NUM_RECENT_MUTUAL_FOLLOWS: Cont nuous = new Cont nuous(
    "user.prefetch.num_recent_mutual_follows"
  )
  //  mpress on + follow features
  val USER_NUM_RECENT_FOLLOWED_ MPRESS ONS: Cont nuous = new Cont nuous(
    "user.prefetch.num_recent_follo d_ mpress on"
  )
  val USER_LAST_FOLLOWED_ MPRESS ON_DURAT ON: Cont nuous = new Cont nuous(
    "user.prefetch.last_follo d_ mpress on_durat on"
  )

  overr de def adaptToDataRecord(
    record: (HasPreFetc dFeature, Cand dateUser)
  ): DataRecord = {
    val (target, cand date) = record
    val dr = new DataRecord()
    val t = T  .now
    // set  mpress on features for user, opt onally for cand date
    dr.setFeatureValue(USER_NUM_RECENT_ MPRESS ONS, target.numWtf mpress ons.toDouble)
    dr.setFeatureValue(
      USER_LAST_ MPRESS ON_DURAT ON,
      (t - target.latest mpress onT  ). nM ll s.toDouble)
    target.getCand date mpress onCounts(cand date. d).foreach { counts =>
      dr.setFeatureValue(CAND DATE_NUM_RECENT_ MPRESS ONS, counts.toDouble)
    }
    target.getCand dateLatestT  (cand date. d).foreach { latestT  : T   =>
      dr.setFeatureValue(CAND DATE_LAST_ MPRESS ON_DURAT ON, (t - latestT  ). nM ll s.toDouble)
    }
    // set recent follow features for user
    dr.setFeatureValue(USER_NUM_RECENT_FOLLOWERS, target.numRecentFollo dUser ds.toDouble)
    dr.setFeatureValue(USER_NUM_RECENT_FOLLOWED_BY, target.numRecentFollo dByUser ds.toDouble)
    dr.setFeatureValue(USER_NUM_RECENT_MUTUAL_FOLLOWS, target.numRecentMutualFollows.toDouble)
    dr.setFeatureValue(USER_NUM_RECENT_FOLLOWED_ MPRESS ONS, target.numFollo d mpress ons.toDouble)
    dr.setFeatureValue(
      USER_LAST_FOLLOWED_ MPRESS ON_DURAT ON,
      target.lastFollo d mpress onDurat onMs.getOrElse(Long.MaxValue).toDouble)
    dr
  }
  overr de def getFeatureContext: FeatureContext = new FeatureContext(
    USER_NUM_RECENT_ MPRESS ONS,
    USER_LAST_ MPRESS ON_DURAT ON,
    CAND DATE_NUM_RECENT_ MPRESS ONS,
    CAND DATE_LAST_ MPRESS ON_DURAT ON,
    USER_NUM_RECENT_FOLLOWERS,
    USER_NUM_RECENT_FOLLOWED_BY,
    USER_NUM_RECENT_MUTUAL_FOLLOWS,
    USER_NUM_RECENT_FOLLOWED_ MPRESS ONS,
    USER_LAST_FOLLOWED_ MPRESS ON_DURAT ON,
  )
}
