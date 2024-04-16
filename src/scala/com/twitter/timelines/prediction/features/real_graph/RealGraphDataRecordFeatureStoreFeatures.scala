package com.tw ter.t  l nes.pred ct on.features.real_graph

 mport com.tw ter.ml.featurestore.catalog.ent  es.core.UserAuthor
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.RealGraph
 mport com.tw ter.ml.featurestore.l b.EdgeEnt y d
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeatureSet
 mport com.tw ter.ml.featurestore.l b.feature.Feature
 mport com.tw ter.ml.featurestore.l b.feature.FeatureSet

object RealGraphDataRecordFeatureStoreFeatures {
  val boundUserAuthorfeatureSet: BoundFeatureSet = FeatureSet(
    RealGraph.Dest d,
    RealGraph.AddressBookEma l.DaysS nceLast,
    RealGraph.AddressBookEma l.ElapsedDays,
    RealGraph.AddressBookEma l.Ewma,
    RealGraph.AddressBookEma l. sM ss ng,
    RealGraph.AddressBookEma l. an,
    RealGraph.AddressBookEma l.NonZeroDays,
    RealGraph.AddressBookEma l.Var ance,
    RealGraph.AddressBook nBoth.DaysS nceLast,
    RealGraph.AddressBook nBoth.ElapsedDays,
    RealGraph.AddressBook nBoth.Ewma,
    RealGraph.AddressBook nBoth. sM ss ng,
    RealGraph.AddressBook nBoth. an,
    RealGraph.AddressBook nBoth.NonZeroDays,
    RealGraph.AddressBook nBoth.Var ance,
    RealGraph.AddressBookMutualEdgeEma l.DaysS nceLast,
    RealGraph.AddressBookMutualEdgeEma l.ElapsedDays,
    RealGraph.AddressBookMutualEdgeEma l.Ewma,
    RealGraph.AddressBookMutualEdgeEma l. sM ss ng,
    RealGraph.AddressBookMutualEdgeEma l. an,
    RealGraph.AddressBookMutualEdgeEma l.NonZeroDays,
    RealGraph.AddressBookMutualEdgeEma l.Var ance,
    RealGraph.AddressBookMutualEdge nBoth.DaysS nceLast,
    RealGraph.AddressBookMutualEdge nBoth.ElapsedDays,
    RealGraph.AddressBookMutualEdge nBoth.Ewma,
    RealGraph.AddressBookMutualEdge nBoth. sM ss ng,
    RealGraph.AddressBookMutualEdge nBoth. an,
    RealGraph.AddressBookMutualEdge nBoth.NonZeroDays,
    RealGraph.AddressBookMutualEdge nBoth.Var ance,
    RealGraph.AddressBookMutualEdgePhone.DaysS nceLast,
    RealGraph.AddressBookMutualEdgePhone.ElapsedDays,
    RealGraph.AddressBookMutualEdgePhone.Ewma,
    RealGraph.AddressBookMutualEdgePhone. sM ss ng,
    RealGraph.AddressBookMutualEdgePhone. an,
    RealGraph.AddressBookMutualEdgePhone.NonZeroDays,
    RealGraph.AddressBookMutualEdgePhone.Var ance,
    RealGraph.AddressBookPhone.DaysS nceLast,
    RealGraph.AddressBookPhone.ElapsedDays,
    RealGraph.AddressBookPhone.Ewma,
    RealGraph.AddressBookPhone. sM ss ng,
    RealGraph.AddressBookPhone. an,
    RealGraph.AddressBookPhone.NonZeroDays,
    RealGraph.AddressBookPhone.Var ance,
    RealGraph.D rect ssages.DaysS nceLast,
    RealGraph.D rect ssages.ElapsedDays,
    RealGraph.D rect ssages.Ewma,
    RealGraph.D rect ssages. sM ss ng,
    RealGraph.D rect ssages. an,
    RealGraph.D rect ssages.NonZeroDays,
    RealGraph.D rect ssages.Var ance,
    RealGraph.D llT  .DaysS nceLast,
    RealGraph.D llT  .ElapsedDays,
    RealGraph.D llT  .Ewma,
    RealGraph.D llT  . sM ss ng,
    RealGraph.D llT  . an,
    RealGraph.D llT  .NonZeroDays,
    RealGraph.D llT  .Var ance,
    RealGraph.Follow.DaysS nceLast,
    RealGraph.Follow.ElapsedDays,
    RealGraph.Follow.Ewma,
    RealGraph.Follow. sM ss ng,
    RealGraph.Follow. an,
    RealGraph.Follow.NonZeroDays,
    RealGraph.Follow.Var ance,
    RealGraph. nspectedStatuses.DaysS nceLast,
    RealGraph. nspectedStatuses.ElapsedDays,
    RealGraph. nspectedStatuses.Ewma,
    RealGraph. nspectedStatuses. sM ss ng,
    RealGraph. nspectedStatuses. an,
    RealGraph. nspectedStatuses.NonZeroDays,
    RealGraph. nspectedStatuses.Var ance,
    RealGraph.L kes.DaysS nceLast,
    RealGraph.L kes.ElapsedDays,
    RealGraph.L kes.Ewma,
    RealGraph.L kes. sM ss ng,
    RealGraph.L kes. an,
    RealGraph.L kes.NonZeroDays,
    RealGraph.L kes.Var ance,
    RealGraph.L nkCl cks.DaysS nceLast,
    RealGraph.L nkCl cks.ElapsedDays,
    RealGraph.L nkCl cks.Ewma,
    RealGraph.L nkCl cks. sM ss ng,
    RealGraph.L nkCl cks. an,
    RealGraph.L nkCl cks.NonZeroDays,
    RealGraph.L nkCl cks.Var ance,
    RealGraph. nt ons.DaysS nceLast,
    RealGraph. nt ons.ElapsedDays,
    RealGraph. nt ons.Ewma,
    RealGraph. nt ons. sM ss ng,
    RealGraph. nt ons. an,
    RealGraph. nt ons.NonZeroDays,
    RealGraph. nt ons.Var ance,
    RealGraph.MutualFollow.DaysS nceLast,
    RealGraph.MutualFollow.ElapsedDays,
    RealGraph.MutualFollow.Ewma,
    RealGraph.MutualFollow. sM ss ng,
    RealGraph.MutualFollow. an,
    RealGraph.MutualFollow.NonZeroDays,
    RealGraph.MutualFollow.Var ance,
    RealGraph.NumT etQuotes.DaysS nceLast,
    RealGraph.NumT etQuotes.ElapsedDays,
    RealGraph.NumT etQuotes.Ewma,
    RealGraph.NumT etQuotes. sM ss ng,
    RealGraph.NumT etQuotes. an,
    RealGraph.NumT etQuotes.NonZeroDays,
    RealGraph.NumT etQuotes.Var ance,
    RealGraph.PhotoTags.DaysS nceLast,
    RealGraph.PhotoTags.ElapsedDays,
    RealGraph.PhotoTags.Ewma,
    RealGraph.PhotoTags. sM ss ng,
    RealGraph.PhotoTags. an,
    RealGraph.PhotoTags.NonZeroDays,
    RealGraph.PhotoTags.Var ance,
    RealGraph.Prof leV ews.DaysS nceLast,
    RealGraph.Prof leV ews.ElapsedDays,
    RealGraph.Prof leV ews.Ewma,
    RealGraph.Prof leV ews. sM ss ng,
    RealGraph.Prof leV ews. an,
    RealGraph.Prof leV ews.NonZeroDays,
    RealGraph.Prof leV ews.Var ance,
    RealGraph.Ret ets.DaysS nceLast,
    RealGraph.Ret ets.ElapsedDays,
    RealGraph.Ret ets.Ewma,
    RealGraph.Ret ets. sM ss ng,
    RealGraph.Ret ets. an,
    RealGraph.Ret ets.NonZeroDays,
    RealGraph.Ret ets.Var ance,
    RealGraph.SmsFollow.DaysS nceLast,
    RealGraph.SmsFollow.ElapsedDays,
    RealGraph.SmsFollow.Ewma,
    RealGraph.SmsFollow. sM ss ng,
    RealGraph.SmsFollow. an,
    RealGraph.SmsFollow.NonZeroDays,
    RealGraph.SmsFollow.Var ance,
    RealGraph.T etCl cks.DaysS nceLast,
    RealGraph.T etCl cks.ElapsedDays,
    RealGraph.T etCl cks.Ewma,
    RealGraph.T etCl cks. sM ss ng,
    RealGraph.T etCl cks. an,
    RealGraph.T etCl cks.NonZeroDays,
    RealGraph.T etCl cks.Var ance,
    RealGraph.  ght
  ).b nd(UserAuthor)

  pr vate[t ] val edgeFeatures: Seq[RealGraph.EdgeFeature] = Seq(
    RealGraph.AddressBookEma l,
    RealGraph.AddressBook nBoth,
    RealGraph.AddressBookMutualEdgeEma l,
    RealGraph.AddressBookMutualEdge nBoth,
    RealGraph.AddressBookMutualEdgePhone,
    RealGraph.AddressBookPhone,
    RealGraph.D rect ssages,
    RealGraph.D llT  ,
    RealGraph.Follow,
    RealGraph. nspectedStatuses,
    RealGraph.L kes,
    RealGraph.L nkCl cks,
    RealGraph. nt ons,
    RealGraph.MutualFollow,
    RealGraph.PhotoTags,
    RealGraph.Prof leV ews,
    RealGraph.Ret ets,
    RealGraph.SmsFollow,
    RealGraph.T etCl cks
  )

  val htlDoubleFeatures: Set[Feature[EdgeEnt y d[User d, User d], Double]] = {
    val features = edgeFeatures.flatMap { ef =>
      Seq(ef.Ewma, ef. an, ef.Var ance)
    } ++ Seq(RealGraph.  ght)
    features.toSet
  }

  val htlLongFeatures: Set[Feature[EdgeEnt y d[User d, User d], Long]] = {
    val features = edgeFeatures.flatMap { ef =>
      Seq(ef.DaysS nceLast, ef.ElapsedDays, ef.NonZeroDays)
    }
    features.toSet
  }

  pr vate val edgeFeatureToLegacyNa  = Map(
    RealGraph.AddressBookEma l -> "num_address_book_ema l",
    RealGraph.AddressBook nBoth -> "num_address_book_ n_both",
    RealGraph.AddressBookMutualEdgeEma l -> "num_address_book_mutual_edge_ema l",
    RealGraph.AddressBookMutualEdge nBoth -> "num_address_book_mutual_edge_ n_both",
    RealGraph.AddressBookMutualEdgePhone -> "num_address_book_mutual_edge_phone",
    RealGraph.AddressBookPhone -> "num_address_book_phone",
    RealGraph.D rect ssages -> "d rect_ ssages",
    RealGraph.D llT   -> "total_d ll_t  ",
    RealGraph.Follow -> "num_follow",
    RealGraph. nspectedStatuses -> "num_ nspected_t ets",
    RealGraph.L kes -> "num_favor es",
    RealGraph.L nkCl cks -> "num_l nk_cl cks",
    RealGraph. nt ons -> "num_ nt ons",
    RealGraph.MutualFollow -> "num_mutual_follow",
    RealGraph.PhotoTags -> "num_photo_tags",
    RealGraph.Prof leV ews -> "num_prof le_v ews",
    RealGraph.Ret ets -> "num_ret ets",
    RealGraph.SmsFollow -> "num_sms_follow",
    RealGraph.T etCl cks -> "num_t et_cl cks",
  )

  def convertFeatureToLegacyNa (
    pref x: Str ng,
    var ance: Str ng = "var ance"
  ): Map[Feature[EdgeEnt y d[User d, User d], _ >: Long w h Double <: AnyVal], Str ng] =
    edgeFeatureToLegacyNa .flatMap {
      case (k, v) =>
        Seq(
          k.NonZeroDays -> s"${pref x}.${v}.non_zero_days",
          k.DaysS nceLast -> s"${pref x}.${v}.days_s nce_last",
          k.ElapsedDays -> s"${pref x}.${v}.elapsed_days",
          k.Ewma -> s"${pref x}.${v}.ewma",
          k. an -> s"${pref x}.${v}. an",
          k.Var ance -> s"${pref x}.${v}.${var ance}",
        )
    } ++ Map(
      RealGraph.  ght -> (pref x + ".  ght")
    )
}
