packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.felonaturelons.AuthorScrelonelonnNamelon
import com.twittelonr.visibility.felonaturelons.Felonaturelon
import com.twittelonr.visibility.felonaturelons.FelonaturelonMap
import com.twittelonr.visibility.felonaturelons.RawQuelonry
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl._

objelonct RulelonBaselon {

  val DelonpreloncatelondFelonaturelons: Selonq[Felonaturelon[_]] =
    Selonq(AuthorScrelonelonnNamelon, RawQuelonry)

  val RulelonMap: Map[SafelontyLelonvelonl, VisibilityPolicy] = Map(
    AccelonssIntelonrnalPromotelondContelonnt -> IntelonrnalPromotelondContelonntPolicy,
    AllSubscribelondLists -> AllSubscribelondListsPolicy,
    AdsBusinelonssSelonttings -> AdsBusinelonssSelonttingsPolicy,
    AdsCampaign -> AdsCampaignPolicy,
    AdsManagelonr -> AdsManagelonrPolicy,
    AdsRelonportingDashboard -> AdsRelonportingDashboardPolicy,
    Appelonals -> AppelonalsPolicy,
    ArticlelonTwelonelontTimelonlinelon -> ArticlelonTwelonelontTimelonlinelonPolicy,
    BaselonQig -> BaselonQigPolicy,
    BirdwatchNotelonAuthor -> BirdwatchNotelonAuthorPolicy,
    BirdwatchNotelonTwelonelontsTimelonlinelon -> BirdwatchNotelonTwelonelontsTimelonlinelonPolicy,
    BirdwatchNelonelondsYourHelonlpNotifications -> BirdwatchNelonelondsYourHelonlpNotificationsPolicy,
    BlockMutelonUselonrsTimelonlinelon -> BlockMutelonUselonrsTimelonlinelonPolicy,
    BrandSafelonty -> BrandSafelontyPolicy,
    CardPollVoting -> CardPollVotingPolicy,
    CardsSelonrvicelon -> CardsSelonrvicelonPolicy,
    Communitielons -> CommunitielonsPolicy,
    ContelonntControlToolInstall -> ContelonntControlToolInstallPolicy,
    ConvelonrsationFocalPrelonhydration -> ConvelonrsationFocalPrelonhydrationPolicy,
    ConvelonrsationFocalTwelonelont -> ConvelonrsationFocalTwelonelontPolicy,
    ConvelonrsationInjelonctelondTwelonelont -> ConvelonrsationInjelonctelondTwelonelontPolicy,
    ConvelonrsationRelonply -> ConvelonrsationRelonplyPolicy,
    CuratelondTrelonndsRelonprelonselonntativelonTwelonelont -> CuratelondTrelonndsRelonprelonselonntativelonTwelonelontPolicy,
    CurationPolicyViolations -> CurationPolicyViolationsPolicy,
    FollowingAndFollowelonrsUselonrList -> FollowingAndFollowelonrsUselonrListPolicy,
    DelonpreloncatelondSafelontyLelonvelonl -> FiltelonrNonelonPolicy,
    DelonvPlatformGelontListTwelonelonts -> DelonvPlatformGelontListTwelonelontsPolicy,
    DelonsFollowingAndFollowelonrsUselonrList -> FollowingAndFollowelonrsUselonrListPolicy,
    DelonsHomelonTimelonlinelon -> DelonSHomelonTimelonlinelonPolicy,
    DelonsQuotelonTwelonelontTimelonlinelon -> DelonsQuotelonTwelonelontTimelonlinelonPolicy,
    DelonsRelonaltimelon -> DelonSRelonaltimelonPolicy,
    DelonsRelonaltimelonSpamelonnrichmelonnt -> DelonSRelonaltimelonSpamelonnrichmelonntPolicy,
    DelonsRelonaltimelonTwelonelontFiltelonr -> DelonSRelonaltimelonSpamelonnrichmelonntPolicy,
    DelonsRelontwelonelontingUselonrs -> DelonSRelontwelonelontingUselonrsPolicy,
    DelonsTwelonelontDelontail -> DelonsTwelonelontDelontailPolicy,
    DelonsTwelonelontLikingUselonrs -> DelonSTwelonelontLikingUselonrsPolicy,
    DelonsUselonrBookmarks -> DelonSUselonrBookmarksPolicy,
    DelonsUselonrLikelondTwelonelonts -> DelonSUselonrLikelondTwelonelontsPolicy,
    DelonsUselonrMelonntions -> DelonSUselonrMelonntionsPolicy,
    DelonsUselonrTwelonelonts -> DelonSUselonrTwelonelontsPolicy,
    DelonvPlatformCompliancelonStrelonam -> DelonvPlatformCompliancelonStrelonamPolicy,
    DirelonctMelonssagelons -> DirelonctMelonssagelonsPolicy,
    DirelonctMelonssagelonsConvelonrsationList -> DirelonctMelonssagelonsConvelonrsationListPolicy,
    DirelonctMelonssagelonsConvelonrsationTimelonlinelon -> DirelonctMelonssagelonsConvelonrsationTimelonlinelonPolicy,
    DirelonctMelonssagelonsInbox -> DirelonctMelonssagelonsInboxPolicy,
    DirelonctMelonssagelonsMutelondUselonrs -> DirelonctMelonssagelonsMutelondUselonrsPolicy,
    DirelonctMelonssagelonsPinnelond -> DirelonctMelonssagelonsPinnelondPolicy,
    DirelonctMelonssagelonsSelonarch -> DirelonctMelonssagelonsSelonarchPolicy,
    elonditHistoryTimelonlinelon -> elonditHistoryTimelonlinelonPolicy,
    elonlelonvatelondQuotelonTwelonelontTimelonlinelon -> elonlelonvatelondQuotelonTwelonelontTimelonlinelonPolicy,
    elonmbelonddelondTwelonelont -> elonmbelonddelondTwelonelontsPolicy,
    elonmbelondsPublicIntelonrelonstNoticelon -> elonmbelondsPublicIntelonrelonstNoticelonPolicy,
    elonmbelondTwelonelontMarkup -> elonmbelondTwelonelontMarkupPolicy,
    WritelonPathLimitelondActionselonnforcelonmelonnt -> WritelonPathLimitelondActionselonnforcelonmelonntPolicy,
    FiltelonrAll -> FiltelonrAllPolicy,
    FiltelonrAllPlacelonholdelonr -> FiltelonrAllPolicy,
    FiltelonrNonelon -> FiltelonrNonelonPolicy,
    FiltelonrDelonfault -> FiltelonrDelonfaultPolicy,
    FollowelondTopicsTimelonlinelon -> FollowelondTopicsTimelonlinelonPolicy,
    FollowelonrConnelonctions -> FollowelonrConnelonctionsPolicy,
    ForDelonvelonlopmelonntOnly -> ForDelonvelonlopmelonntOnlyPolicy,
    FrielonndsFollowingList -> FrielonndsFollowingListPolicy,
    GraphqlDelonfault -> GraphqlDelonfaultPolicy,
    GryphonDeloncksAndColumns -> GryphonDeloncksAndColumnsSharingPolicy,
    HumanizationNudgelon -> HumanizationNudgelonPolicy,
    KitchelonnSinkDelonvelonlopmelonnt -> KitchelonnSinkDelonvelonlopmelonntPolicy,
    ListHelonadelonr -> ListHelonadelonrPolicy,
    ListMelonmbelonrships -> ListMelonmbelonrshipsPolicy,
    ListOwnelonrships -> ListOwnelonrshipsPolicy,
    ListReloncommelonndations -> ListReloncommelonndationsPolicy,
    ListSelonarch -> ListSelonarchPolicy,
    ListSubscriptions -> ListSubscriptionsPolicy,
    LivelonPipelonlinelonelonngagelonmelonntCounts -> LivelonPipelonlinelonelonngagelonmelonntCountsPolicy,
    LivelonVidelonoTimelonlinelon -> LivelonVidelonoTimelonlinelonPolicy,
    MagicReloncs -> MagicReloncsPolicy,
    MagicReloncsAggrelonssivelon -> MagicReloncsAggrelonssivelonPolicy,
    MagicReloncsAggrelonssivelonV2 -> MagicReloncsAggrelonssivelonV2Policy,
    MagicReloncsV2 -> MagicReloncsV2Policy,
    Minimal -> MinimalPolicy,
    ModelonratelondTwelonelontsTimelonlinelon -> ModelonratelondTwelonelontsTimelonlinelonPolicy,
    Momelonnts -> MomelonntsPolicy,
    NelonarbyTimelonlinelon -> NelonarbyTimelonlinelonPolicy,
    NelonwUselonrelonxpelonrielonncelon -> NelonwUselonrelonxpelonrielonncelonPolicy,
    NotificationsIbis -> NotificationsIbisPolicy,
    NotificationsPlatform -> NotificationsPlatformPolicy,
    NotificationsPlatformPush -> NotificationsPlatformPushPolicy,
    NotificationsQig -> NotificationsQigPolicy,
    NotificationsRelonad -> NotificationsRelonadPolicy,
    NotificationsTimelonlinelonDelonvicelonFollow -> NotificationsTimelonlinelonDelonvicelonFollowPolicy,
    NotificationsWritelon -> NotificationsWritelonPolicy,
    NotificationsWritelonrV2 -> NotificationsWritelonrV2Policy,
    NotificationsWritelonrTwelonelontHydrator -> NotificationsWritelonrTwelonelontHydratorPolicy,
    ProfilelonMixelonrMelondia -> ProfilelonMixelonrMelondiaPolicy,
    ProfilelonMixelonrFavoritelons -> ProfilelonMixelonrFavoritelonsPolicy,
    QuickPromotelonTwelonelontelonligibility -> QuickPromotelonTwelonelontelonligibilityPolicy,
    QuotelonTwelonelontTimelonlinelon -> QuotelonTwelonelontTimelonlinelonPolicy,
    QuotelondTwelonelontRulelons -> QuotelondTwelonelontRulelonsPolicy,
    Reloncommelonndations -> ReloncommelonndationsPolicy,
    ReloncosVidelono -> ReloncosVidelonoPolicy,
    ReloncosWritelonPath -> ReloncosWritelonPathPolicy,
    RelonplielonsGrouping -> RelonplielonsGroupingPolicy,
    RelonportCelonntelonr -> RelonportCelonntelonrPolicy,
    RelonturningUselonrelonxpelonrielonncelon -> RelonturningUselonrelonxpelonrielonncelonPolicy,
    RelonturningUselonrelonxpelonrielonncelonFocalTwelonelont -> RelonturningUselonrelonxpelonrielonncelonFocalTwelonelontPolicy,
    Relonvelonnuelon -> RelonvelonnuelonPolicy,
    RitoActionelondTwelonelontTimelonlinelon -> RitoActionelondTwelonelontTimelonlinelonPolicy,
    SelonarchHydration -> SelonarchHydrationPolicy,
    SelonarchMixelonrSrpMinimal -> SelonarchMixelonrSrpMinimalPolicy,
    SelonarchMixelonrSrpStrict -> SelonarchMixelonrSrpStrictPolicy,
    SelonarchLatelonst -> SelonarchLatelonstPolicy,
    SelonarchPelonoplelonSrp -> SelonarchPelonoplelonSrpPolicy,
    SelonarchPelonoplelonTypelonahelonad -> SelonarchPelonoplelonTypelonahelonadPolicy,
    SelonarchPhoto -> SelonarchPhotoPolicy,
    SelonarchTrelonndTakelonovelonrPromotelondTwelonelont -> SelonarchTrelonndTakelonovelonrPromotelondTwelonelontPolicy,
    SelonarchTop -> SelonarchTopPolicy,
    SelonarchTopQig -> SelonarchTopQigPolicy,
    SelonarchVidelono -> SelonarchVidelonoPolicy,
    SelonarchBlelonndelonrUselonrRulelons -> SelonarchBlelonndelonrUselonrRulelonsPolicy,
    SelonarchLatelonstUselonrRulelons -> SelonarchLatelonstUselonrRulelonsPolicy,
    ShoppingManagelonrSpyModelon -> ShoppingManagelonrSpyModelonPolicy,
    SignalsRelonactions -> SignalsRelonactionsPolicy,
    SignalsTwelonelontRelonactingUselonrs -> SignalsTwelonelontRelonactingUselonrsPolicy,
    SocialProof -> SocialProofPolicy,
    SoftIntelonrvelonntionPivot -> SoftIntelonrvelonntionPivotPolicy,
    SpacelonFlelonelontlinelon -> SpacelonFlelonelontlinelonPolicy,
    SpacelonHomelonTimelonlinelonUpranking -> SpacelonHomelonTimelonlinelonUprankingPolicy,
    SpacelonJoinScrelonelonn -> SpacelonJoinScrelonelonnPolicy,
    SpacelonNotifications -> SpacelonNotificationsPolicy,
    Spacelons -> SpacelonsPolicy,
    SpacelonsParticipants -> SpacelonsParticipantsPolicy,
    SpacelonsSelonllelonrApplicationStatus -> SpacelonsSelonllelonrApplicationStatusPolicy,
    SpacelonsSharing -> SpacelonsSharingPolicy,
    SpacelonTwelonelontAvatarHomelonTimelonlinelon -> SpacelonTwelonelontAvatarHomelonTimelonlinelonPolicy,
    StickelonrsTimelonlinelon -> StickelonrsTimelonlinelonPolicy,
    StratoelonxtLimitelondelonngagelonmelonnts -> StratoelonxtLimitelondelonngagelonmelonntsPolicy,
    StrelonamSelonrvicelons -> StrelonamSelonrvicelonsPolicy,
    SupelonrFollowelonrConnelonctions -> SupelonrFollowelonrConnelonctionsPolicy,
    SupelonrLikelon -> SupelonrLikelonPolicy,
    Telonst -> TelonstPolicy,
    TimelonlinelonContelonntControls -> TimelonlinelonContelonntControlsPolicy,
    TimelonlinelonConvelonrsations -> TimelonlinelonConvelonrsationsPolicy,
    TimelonlinelonConvelonrsationsDownranking -> TimelonlinelonConvelonrsationsDownrankingPolicy,
    TimelonlinelonConvelonrsationsDownrankingMinimal -> TimelonlinelonConvelonrsationsDownrankingMinimalPolicy,
    TimelonlinelonFollowingActivity -> TimelonlinelonFollowingActivityPolicy,
    TimelonlinelonHomelon -> TimelonlinelonHomelonPolicy,
    TimelonlinelonHomelonCommunitielons -> TimelonlinelonHomelonCommunitielonsPolicy,
    TimelonlinelonHomelonHydration -> TimelonlinelonHomelonHydrationPolicy,
    TimelonlinelonHomelonPromotelondHydration -> TimelonlinelonHomelonPromotelondHydrationPolicy,
    TimelonlinelonHomelonReloncommelonndations -> TimelonlinelonHomelonReloncommelonndationsPolicy,
    TimelonlinelonHomelonTopicFollowReloncommelonndations -> TimelonlinelonHomelonTopicFollowReloncommelonndationsPolicy,
    TimelonlinelonScorelonr -> TimelonlinelonScorelonrPolicy,
    TopicsLandingPagelonTopicReloncommelonndations -> TopicsLandingPagelonTopicReloncommelonndationsPolicy,
    elonxplorelonReloncommelonndations -> elonxplorelonReloncommelonndationsPolicy,
    TimelonlinelonInjelonction -> TimelonlinelonInjelonctionPolicy,
    TimelonlinelonMelonntions -> TimelonlinelonMelonntionsPolicy,
    TimelonlinelonModelonratelondTwelonelontsHydration -> TimelonlinelonModelonratelondTwelonelontsHydrationPolicy,
    TimelonlinelonHomelonLatelonst -> TimelonlinelonHomelonLatelonstPolicy,
    TimelonlinelonLikelondBy -> TimelonlinelonLikelondByPolicy,
    TimelonlinelonRelontwelonelontelondBy -> TimelonlinelonRelontwelonelontelondByPolicy,
    TimelonlinelonSupelonrLikelondBy -> TimelonlinelonSupelonrLikelondByPolicy,
    TimelonlinelonBookmark -> TimelonlinelonBookmarkPolicy,
    TimelonlinelonMelondia -> TimelonlinelonMelondiaPolicy,
    TimelonlinelonRelonactivelonBlelonnding -> TimelonlinelonRelonactivelonBlelonndingPolicy,
    TimelonlinelonFavoritelons -> TimelonlinelonFavoritelonsPolicy,
    TimelonlinelonFavoritelonsSelonlfVielonw -> TimelonlinelonFavoritelonsSelonlfVielonwPolicy,
    TimelonlinelonLists -> TimelonlinelonListsPolicy,
    TimelonlinelonProfilelon -> TimelonlinelonProfilelonPolicy,
    TimelonlinelonProfilelonAll -> TimelonlinelonProfilelonAllPolicy,
    TimelonlinelonProfilelonSpacelons -> TimelonlinelonProfilelonSpacelonsPolicy,
    TimelonlinelonProfilelonSupelonrFollows -> TimelonlinelonProfilelonSupelonrFollowsPolicy,
    TimelonlinelonFocalTwelonelont -> TimelonlinelonFocalTwelonelontPolicy,
    Tombstoning -> TombstoningPolicy,
    TopicReloncommelonndations -> TopicReloncommelonndationsPolicy,
    TrelonndsRelonprelonselonntativelonTwelonelont -> TrelonndsRelonprelonselonntativelonTwelonelontPolicy,
    TrustelondFrielonndsUselonrList -> TrustelondFrielonndsUselonrListPolicy,
    TwelonelontDelontail -> TwelonelontDelontailPolicy,
    TwelonelontDelontailNonToo -> TwelonelontDelontailNonTooPolicy,
    TwelonelontDelontailWithInjelonctionsHydration -> TwelonelontDelontailWithInjelonctionsHydrationPolicy,
    Twelonelontelonngagelonrs -> TwelonelontelonngagelonrsPolicy,
    TwelonelontRelonplyNudgelon -> TwelonelontRelonplyNudgelonPolicy,
    TwelonelontScopelondTimelonlinelon -> TwelonelontScopelondTimelonlinelonPolicy,
    TwelonelontWritelonsApi -> TwelonelontWritelonsApiPolicy,
    TwittelonrArticlelonComposelon -> TwittelonrArticlelonComposelonPolicy,
    TwittelonrArticlelonProfilelonTab -> TwittelonrArticlelonProfilelonTabPolicy,
    TwittelonrArticlelonRelonad -> TwittelonrArticlelonRelonadPolicy,
    UselonrMilelonstonelonReloncommelonndation -> UselonrMilelonstonelonReloncommelonndationPolicy,
    UselonrProfilelonHelonadelonr -> UselonrProfilelonHelonadelonrPolicy,
    UselonrScopelondTimelonlinelon -> UselonrScopelondTimelonlinelonPolicy,
    UselonrSelonarchSrp -> UselonrSelonarchSrpPolicy,
    UselonrSelonarchTypelonahelonad -> UselonrSelonarchTypelonahelonadPolicy,
    UselonrSelonlfVielonwOnly -> UselonrSelonlfVielonwOnlyPolicy,
    UselonrSelonttings -> UselonrSelonttingsPolicy,
    VidelonoAds -> VidelonoAdsPolicy,
    ZipbirdConsumelonrArchivelons -> ZipbirdConsumelonrArchivelonsPolicy,
    TwelonelontAward -> TwelonelontAwardPolicy,
  )

  delonf relonmovelonUnuselondFelonaturelonsFromFelonaturelonMap(
    felonaturelonMap: FelonaturelonMap,
    rulelons: Selonq[Rulelon],
  ): FelonaturelonMap = {
    val felonaturelonsInSafelontyLelonvelonl: Selont[Felonaturelon[_]] =
      RulelonBaselon.gelontFelonaturelonsForRulelons(rulelons)
    val filtelonrelondMap = felonaturelonMap.map.filtelonrKelonys(felonaturelonsInSafelontyLelonvelonl.contains(_))

    nelonw FelonaturelonMap(filtelonrelondMap, felonaturelonMap.constantMap)
  }

  delonf gelontFelonaturelonsForRulelons(rulelons: Selonq[Rulelon]): Selont[Felonaturelon[_]] = {
    rulelons.flatMap { r: Rulelon =>
      r.felonaturelonDelonpelonndelonncielons ++ r.optionalFelonaturelonDelonpelonndelonncielons
    }.toSelont
  }

  delonf hasTwelonelontRulelons(safelontyLelonvelonl: SafelontyLelonvelonl): Boolelonan = RulelonMap(safelontyLelonvelonl).twelonelontRulelons.nonelonmpty
  delonf hasUselonrRulelons(safelontyLelonvelonl: SafelontyLelonvelonl): Boolelonan = RulelonMap(safelontyLelonvelonl).uselonrRulelons.nonelonmpty
  delonf hasCardRulelons(safelontyLelonvelonl: SafelontyLelonvelonl): Boolelonan = RulelonMap(safelontyLelonvelonl).cardRulelons.nonelonmpty
  delonf hasDmRulelons(safelontyLelonvelonl: SafelontyLelonvelonl): Boolelonan = RulelonMap(safelontyLelonvelonl).dmRulelons.nonelonmpty
  delonf hasDmConvelonrsationRulelons(safelontyLelonvelonl: SafelontyLelonvelonl): Boolelonan = RulelonMap(
    safelontyLelonvelonl).dmConvelonrsationRulelons.nonelonmpty
  delonf hasDmelonvelonntRulelons(safelontyLelonvelonl: SafelontyLelonvelonl): Boolelonan = RulelonMap(
    safelontyLelonvelonl).dmelonvelonntRulelons.nonelonmpty
}
