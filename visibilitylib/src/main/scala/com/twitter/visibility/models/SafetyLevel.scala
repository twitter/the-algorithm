package com.tw ter.v s b l y.models

 mport com.tw ter.spam.rtf.thr ftscala.{SafetyLevel => Thr ftSafetyLevel}
 mport com.tw ter.v s b l y.conf gap .params.SafetyLevelParam
 mport com.tw ter.v s b l y.conf gap .params.SafetyLevelParams._

sealed tra  SafetyLevel {
  val na : Str ng = t .getClass.getS mpleNa .dropR ght(1)
  def enabledParam: SafetyLevelParam
}

object SafetyLevel {
  pr vate lazy val na ToSafetyLevelMap: Map[Str ng, SafetyLevel] =
    SafetyLevel.L st.map(s => s.na .toLo rCase -> s).toMap
  def fromNa (na : Str ng): Opt on[SafetyLevel] = {
    na ToSafetyLevelMap.get(na .toLo rCase)
  }

  pr vate val DeprecatedEnumValue = -1

  pr vate lazy val thr ftToModelMap: Map[Thr ftSafetyLevel, SafetyLevel] = Map(
    Thr ftSafetyLevel.Access nternalPromotedContent -> Access nternalPromotedContent,
    Thr ftSafetyLevel.AdsBus nessSett ngs -> AdsBus nessSett ngs,
    Thr ftSafetyLevel.AdsCampa gn -> AdsCampa gn,
    Thr ftSafetyLevel.AdsManager -> AdsManager,
    Thr ftSafetyLevel.AdsReport ngDashboard -> AdsReport ngDashboard,
    Thr ftSafetyLevel.AllSubscr bedL sts -> AllSubscr bedL sts,
    Thr ftSafetyLevel.Appeals -> Appeals,
    Thr ftSafetyLevel.Art cleT etT  l ne -> Art cleT etT  l ne,
    Thr ftSafetyLevel.BaseQ g -> BaseQ g,
    Thr ftSafetyLevel.B rdwatchNoteAuthor -> B rdwatchNoteAuthor,
    Thr ftSafetyLevel.B rdwatchNoteT etsT  l ne -> B rdwatchNoteT etsT  l ne,
    Thr ftSafetyLevel.B rdwatchNeedsY  lpNot f cat ons -> B rdwatchNeedsY  lpNot f cat ons,
    Thr ftSafetyLevel.BlockMuteUsersT  l ne -> BlockMuteUsersT  l ne,
    Thr ftSafetyLevel.BrandSafety -> BrandSafety,
    Thr ftSafetyLevel.CardPollVot ng -> CardPollVot ng,
    Thr ftSafetyLevel.CardsServ ce -> CardsServ ce,
    Thr ftSafetyLevel.Commun  es -> Commun  es,
    Thr ftSafetyLevel.ContentControlTool nstall -> ContentControlTool nstall,
    Thr ftSafetyLevel.Conversat onFocalPrehydrat on -> Conversat onFocalPrehydrat on,
    Thr ftSafetyLevel.Conversat onFocalT et -> Conversat onFocalT et,
    Thr ftSafetyLevel.Conversat on njectedT et -> Conversat on njectedT et,
    Thr ftSafetyLevel.Conversat onReply -> Conversat onReply,
    Thr ftSafetyLevel.CuratedTrendsRepresentat veT et -> CuratedTrendsRepresentat veT et,
    Thr ftSafetyLevel.Curat onPol cyV olat ons -> Curat onPol cyV olat ons,
    Thr ftSafetyLevel.DevPlatformGetL stT ets -> DevPlatformGetL stT ets,
    Thr ftSafetyLevel.DesFollow ngAndFollo rsUserL st -> DesFollow ngAndFollo rsUserL st,
    Thr ftSafetyLevel.DesHo T  l ne -> DesHo T  l ne,
    Thr ftSafetyLevel.DesQuoteT etT  l ne -> DesQuoteT etT  l ne,
    Thr ftSafetyLevel.DesRealt   -> DesRealt  ,
    Thr ftSafetyLevel.DesRealt  SpamEnr ch nt -> DesRealt  SpamEnr ch nt,
    Thr ftSafetyLevel.DesRealt  T etF lter -> DesRealt  T etF lter,
    Thr ftSafetyLevel.DesRet et ngUsers -> DesRet et ngUsers,
    Thr ftSafetyLevel.DesT etDeta l -> DesT etDeta l,
    Thr ftSafetyLevel.DesT etL k ngUsers -> DesT etL k ngUsers,
    Thr ftSafetyLevel.DesUserBookmarks -> DesUserBookmarks,
    Thr ftSafetyLevel.DesUserL kedT ets -> DesUserL kedT ets,
    Thr ftSafetyLevel.DesUser nt ons -> DesUser nt ons,
    Thr ftSafetyLevel.DesUserT ets -> DesUserT ets,
    Thr ftSafetyLevel.DevPlatformCompl anceStream -> DevPlatformCompl anceStream,
    Thr ftSafetyLevel.D rect ssages -> D rect ssages,
    Thr ftSafetyLevel.D rect ssagesConversat onL st -> D rect ssagesConversat onL st,
    Thr ftSafetyLevel.D rect ssagesConversat onT  l ne -> D rect ssagesConversat onT  l ne,
    Thr ftSafetyLevel.D rect ssages nbox -> D rect ssages nbox,
    Thr ftSafetyLevel.D rect ssagesMutedUsers -> D rect ssagesMutedUsers,
    Thr ftSafetyLevel.D rect ssagesP nned -> D rect ssagesP nned,
    Thr ftSafetyLevel.D rect ssagesSearch -> D rect ssagesSearch,
    Thr ftSafetyLevel.Ed  toryT  l ne -> Ed  toryT  l ne,
    Thr ftSafetyLevel.ElevatedQuoteT etT  l ne -> ElevatedQuoteT etT  l ne,
    Thr ftSafetyLevel.EmbeddedT et -> EmbeddedT et,
    Thr ftSafetyLevel.EmbedsPubl c nterestNot ce -> EmbedsPubl c nterestNot ce,
    Thr ftSafetyLevel.EmbedT etMarkup -> EmbedT etMarkup,
    Thr ftSafetyLevel.ExploreRecom ndat ons -> ExploreRecom ndat ons,
    Thr ftSafetyLevel.Wr ePathL m edAct onsEnforce nt -> Wr ePathL m edAct onsEnforce nt,
    Thr ftSafetyLevel.F lterAll -> F lterAll,
    Thr ftSafetyLevel.F lterAllPlaceholder -> F lterAllPlaceholder,
    Thr ftSafetyLevel.F lterDefault -> F lterDefault,
    Thr ftSafetyLevel.F lterNone -> F lterNone,
    Thr ftSafetyLevel.Follo dTop csT  l ne -> Follo dTop csT  l ne,
    Thr ftSafetyLevel.Follo rConnect ons -> Follo rConnect ons,
    Thr ftSafetyLevel.Follow ngAndFollo rsUserL st -> Follow ngAndFollo rsUserL st,
    Thr ftSafetyLevel.ForDevelop ntOnly -> ForDevelop ntOnly,
    Thr ftSafetyLevel.Fr endsFollow ngL st -> Fr endsFollow ngL st,
    Thr ftSafetyLevel.GraphqlDefault -> GraphqlDefault,
    Thr ftSafetyLevel.Human zat onNudge -> Human zat onNudge,
    Thr ftSafetyLevel.K c nS nkDevelop nt -> K c nS nkDevelop nt,
    Thr ftSafetyLevel.L st ader -> L st ader,
    Thr ftSafetyLevel.L st mbersh ps -> L st mbersh ps,
    Thr ftSafetyLevel.L stOwnersh ps -> L stOwnersh ps,
    Thr ftSafetyLevel.L stRecom ndat ons -> L stRecom ndat ons,
    Thr ftSafetyLevel.L stSearch -> L stSearch,
    Thr ftSafetyLevel.L stSubscr pt ons -> L stSubscr pt ons,
    Thr ftSafetyLevel.L veP pel neEngage ntCounts -> L veP pel neEngage ntCounts,
    Thr ftSafetyLevel.L veV deoT  l ne -> L veV deoT  l ne,
    Thr ftSafetyLevel.Mag cRecs -> Mag cRecs,
    Thr ftSafetyLevel.Mag cRecsV2 -> Mag cRecsV2,
    Thr ftSafetyLevel.Mag cRecsAggress ve -> Mag cRecsAggress ve,
    Thr ftSafetyLevel.Mag cRecsAggress veV2 -> Mag cRecsAggress veV2,
    Thr ftSafetyLevel.M n mal -> M n mal,
    Thr ftSafetyLevel.ModeratedT etsT  l ne -> ModeratedT etsT  l ne,
    Thr ftSafetyLevel.Mo nts -> Mo nts,
    Thr ftSafetyLevel.NearbyT  l ne -> NearbyT  l ne,
    Thr ftSafetyLevel.NewUserExper ence -> NewUserExper ence,
    Thr ftSafetyLevel.Not f cat ons b s -> Not f cat ons b s,
    Thr ftSafetyLevel.Not f cat onsPlatform -> Not f cat onsPlatform,
    Thr ftSafetyLevel.Not f cat onsPlatformPush -> Not f cat onsPlatformPush,
    Thr ftSafetyLevel.Not f cat onsQ g -> Not f cat onsQ g,
    Thr ftSafetyLevel.Not f cat onsRead -> Not f cat onsRead,
    Thr ftSafetyLevel.Not f cat onsT  l neDev ceFollow -> Not f cat onsT  l neDev ceFollow,
    Thr ftSafetyLevel.Not f cat onsWr e -> Not f cat onsWr e,
    Thr ftSafetyLevel.Not f cat onsWr erT etHydrator -> Not f cat onsWr erT etHydrator,
    Thr ftSafetyLevel.Not f cat onsWr erV2 -> Not f cat onsWr erV2,
    Thr ftSafetyLevel.Prof leM xer d a -> Prof leM xer d a,
    Thr ftSafetyLevel.Prof leM xerFavor es -> Prof leM xerFavor es,
    Thr ftSafetyLevel.Qu ckPromoteT etEl g b l y -> Qu ckPromoteT etEl g b l y,
    Thr ftSafetyLevel.QuoteT etT  l ne -> QuoteT etT  l ne,
    Thr ftSafetyLevel.QuotedT etRules -> QuotedT etRules,
    Thr ftSafetyLevel.Recom ndat ons -> Recom ndat ons,
    Thr ftSafetyLevel.RecosV deo -> RecosV deo,
    Thr ftSafetyLevel.RecosWr ePath -> RecosWr ePath,
    Thr ftSafetyLevel.Repl esGroup ng -> Repl esGroup ng,
    Thr ftSafetyLevel.ReportCenter -> ReportCenter,
    Thr ftSafetyLevel.Return ngUserExper ence -> Return ngUserExper ence,
    Thr ftSafetyLevel.Return ngUserExper enceFocalT et -> Return ngUserExper enceFocalT et,
    Thr ftSafetyLevel.Revenue -> Revenue,
    Thr ftSafetyLevel.R oAct onedT etT  l ne -> R oAct onedT etT  l ne,
    Thr ftSafetyLevel.SafeSearchM n mal -> SafeSearchM n mal,
    Thr ftSafetyLevel.SafeSearchStr ct -> SafeSearchStr ct,
    Thr ftSafetyLevel.SearchHydrat on -> SearchHydrat on,
    Thr ftSafetyLevel.SearchLatest -> SearchLatest,
    Thr ftSafetyLevel.SearchTop -> SearchTop,
    Thr ftSafetyLevel.SearchTopQ g -> SearchTopQ g,
    Thr ftSafetyLevel.SearchM xerSrpM n mal -> SearchM xerSrpM n mal,
    Thr ftSafetyLevel.SearchM xerSrpStr ct -> SearchM xerSrpStr ct,
    Thr ftSafetyLevel.SearchPeopleSrp -> SearchPeopleSrp,
    Thr ftSafetyLevel.SearchPeopleTypea ad -> SearchPeopleTypea ad,
    Thr ftSafetyLevel.SearchPhoto -> SearchPhoto,
    Thr ftSafetyLevel.SearchTrendTakeoverPromotedT et -> SearchTrendTakeoverPromotedT et,
    Thr ftSafetyLevel.SearchV deo -> SearchV deo,
    Thr ftSafetyLevel.SearchBlenderUserRules -> SearchBlenderUserRules,
    Thr ftSafetyLevel.SearchLatestUserRules -> SearchLatestUserRules,
    Thr ftSafetyLevel.Shopp ngManagerSpyMode -> Shopp ngManagerSpyMode,
    Thr ftSafetyLevel.S gnalsReact ons -> S gnalsReact ons,
    Thr ftSafetyLevel.S gnalsT etReact ngUsers -> S gnalsT etReact ngUsers,
    Thr ftSafetyLevel.Soc alProof -> Soc alProof,
    Thr ftSafetyLevel.Soft ntervent onP vot -> Soft ntervent onP vot,
    Thr ftSafetyLevel.SpaceFleetl ne -> SpaceFleetl ne,
    Thr ftSafetyLevel.SpaceHo T  l neUprank ng -> SpaceHo T  l neUprank ng,
    Thr ftSafetyLevel.SpaceJo nScreen -> SpaceJo nScreen,
    Thr ftSafetyLevel.SpaceNot f cat ons -> SpaceNot f cat ons,
    Thr ftSafetyLevel.Spaces -> Spaces,
    Thr ftSafetyLevel.SpacesPart c pants -> SpacesPart c pants,
    Thr ftSafetyLevel.SpacesSellerAppl cat onStatus -> SpacesSellerAppl cat onStatus,
    Thr ftSafetyLevel.SpacesShar ng -> SpacesShar ng,
    Thr ftSafetyLevel.SpaceT etAvatarHo T  l ne -> SpaceT etAvatarHo T  l ne,
    Thr ftSafetyLevel.St ckersT  l ne -> St ckersT  l ne,
    Thr ftSafetyLevel.StratoExtL m edEngage nts -> StratoExtL m edEngage nts,
    Thr ftSafetyLevel.StreamServ ces -> StreamServ ces,
    Thr ftSafetyLevel.SuperFollo rConnect ons -> SuperFollo rConnect ons,
    Thr ftSafetyLevel.SuperL ke -> SuperL ke,
    Thr ftSafetyLevel.Test -> Test,
    Thr ftSafetyLevel.T  l neBookmark -> T  l neBookmark,
    Thr ftSafetyLevel.T  l neContentControls -> T  l neContentControls,
    Thr ftSafetyLevel.T  l neConversat ons -> T  l neConversat ons,
    Thr ftSafetyLevel.T  l neConversat onsDownrank ng -> T  l neConversat onsDownrank ng,
    Thr ftSafetyLevel.T  l neConversat onsDownrank ngM n mal -> T  l neConversat onsDownrank ngM n mal,
    Thr ftSafetyLevel.T  l neFavor es -> T  l neFavor es,
    Thr ftSafetyLevel.T  l neFavor esSelfV ew -> T  l neFavor esSelfV ew,
    Thr ftSafetyLevel.T  l neFocalT et -> T  l neFocalT et,
    Thr ftSafetyLevel.T  l neFollow ngAct v y -> T  l neFollow ngAct v y,
    Thr ftSafetyLevel.T  l neHo  -> T  l neHo ,
    Thr ftSafetyLevel.T  l neHo Commun  es -> T  l neHo Commun  es,
    Thr ftSafetyLevel.T  l neHo Hydrat on -> T  l neHo Hydrat on,
    Thr ftSafetyLevel.T  l neHo Latest -> T  l neHo Latest,
    Thr ftSafetyLevel.T  l neHo PromotedHydrat on -> T  l neHo PromotedHydrat on,
    Thr ftSafetyLevel.T  l neHo Recom ndat ons -> T  l neHo Recom ndat ons,
    Thr ftSafetyLevel.T  l neHo Top cFollowRecom ndat ons -> T  l neHo Top cFollowRecom ndat ons,
    Thr ftSafetyLevel.T  l neScorer -> T  l neScorer,
    Thr ftSafetyLevel.T  l ne nject on -> T  l ne nject on,
    Thr ftSafetyLevel.T  l neL kedBy -> T  l neL kedBy,
    Thr ftSafetyLevel.T  l neL sts -> T  l neL sts,
    Thr ftSafetyLevel.T  l ne d a -> T  l ne d a,
    Thr ftSafetyLevel.T  l ne nt ons -> T  l ne nt ons,
    Thr ftSafetyLevel.T  l neModeratedT etsHydrat on -> T  l neModeratedT etsHydrat on,
    Thr ftSafetyLevel.T  l neProf le -> T  l neProf le,
    Thr ftSafetyLevel.T  l neProf leAll -> T  l neProf leAll,
    Thr ftSafetyLevel.T  l neProf leSpaces -> T  l neProf leSpaces,
    Thr ftSafetyLevel.T  l neProf leSuperFollows -> T  l neProf leSuperFollows,
    Thr ftSafetyLevel.T  l neReact veBlend ng -> T  l neReact veBlend ng,
    Thr ftSafetyLevel.T  l neRet etedBy -> T  l neRet etedBy,
    Thr ftSafetyLevel.T  l neSuperL kedBy -> T  l neSuperL kedBy,
    Thr ftSafetyLevel.Tombston ng -> Tombston ng,
    Thr ftSafetyLevel.Top cRecom ndat ons -> Top cRecom ndat ons,
    Thr ftSafetyLevel.Top csLand ngPageTop cRecom ndat ons -> Top csLand ngPageTop cRecom ndat ons,
    Thr ftSafetyLevel.TrendsRepresentat veT et -> TrendsRepresentat veT et,
    Thr ftSafetyLevel.TrustedFr endsUserL st -> TrustedFr endsUserL st,
    Thr ftSafetyLevel.Tw terDelegateUserL st -> Tw terDelegateUserL st,
    Thr ftSafetyLevel.GryphonDecksAndColumns -> GryphonDecksAndColumns,
    Thr ftSafetyLevel.T etDeta l -> T etDeta l,
    Thr ftSafetyLevel.T etDeta lNonToo -> T etDeta lNonToo,
    Thr ftSafetyLevel.T etDeta lW h nject onsHydrat on -> T etDeta lW h nject onsHydrat on,
    Thr ftSafetyLevel.T etEngagers -> T etEngagers,
    Thr ftSafetyLevel.T etReplyNudge -> T etReplyNudge,
    Thr ftSafetyLevel.T etScopedT  l ne -> T etScopedT  l ne,
    Thr ftSafetyLevel.T etWr esAp  -> T etWr esAp ,
    Thr ftSafetyLevel.Tw terArt cleCompose -> Tw terArt cleCompose,
    Thr ftSafetyLevel.Tw terArt cleProf leTab -> Tw terArt cleProf leTab,
    Thr ftSafetyLevel.Tw terArt cleRead -> Tw terArt cleRead,
    Thr ftSafetyLevel.UserProf le ader -> UserProf le ader,
    Thr ftSafetyLevel.UserM lestoneRecom ndat on -> UserM lestoneRecom ndat on,
    Thr ftSafetyLevel.UserScopedT  l ne -> UserScopedT  l ne,
    Thr ftSafetyLevel.UserSearchSrp -> UserSearchSrp,
    Thr ftSafetyLevel.UserSearchTypea ad -> UserSearchTypea ad,
    Thr ftSafetyLevel.UserSelfV ewOnly -> UserSelfV ewOnly,
    Thr ftSafetyLevel.UserSett ngs -> UserSett ngs,
    Thr ftSafetyLevel.V deoAds -> V deoAds,
    Thr ftSafetyLevel.Z pb rdConsu rArch ves -> Z pb rdConsu rArch ves,
    Thr ftSafetyLevel.T etAward -> T etAward,
  )

  pr vate lazy val modelToThr ftMap: Map[SafetyLevel, Thr ftSafetyLevel] =
    for ((k, v) <- thr ftToModelMap) y eld (v, k)

  case object AdsBus nessSett ngs extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableAdsBus nessSett ngsSafetyLevelParam
  }
  case object AdsCampa gn extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableAdsCampa gnSafetyLevelParam
  }
  case object AdsManager extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableAdsManagerSafetyLevelParam
  }
  case object AdsReport ngDashboard extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableAdsReport ngDashboardSafetyLevelParam
  }
  case object AllSubscr bedL sts extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableAllSubscr bedL stsSafetyLevelParam
  }
  case object Appeals extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableAppealsSafetyLevelParam
  }
  case object Art cleT etT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableArt cleT etT  l neSafetyLevelParam
  }
  case object BaseQ g extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableBaseQ gSafetyLevelParam
  }
  case object B rdwatchNoteAuthor extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableB rdwatchNoteAuthorSafetyLevel
  }
  case object B rdwatchNoteT etsT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableB rdwatchNoteT etsT  l neSafetyLevel
  }
  case object B rdwatchNeedsY  lpNot f cat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableB rdwatchNeedsY  lpNot f cat onsSafetyLevel
  }
  case object BlockMuteUsersT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableBlockMuteUsersT  l neSafetyLevelParam
  }
  case object BrandSafety extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableBrandSafetySafetyLevelParam
  }
  case object CardPollVot ng extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableCardPollVot ngSafetyLevelParam
  }
  case object CardsServ ce extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableCardsServ ceSafetyLevelParam
  }
  case object Commun  es extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableCommun  esSafetyLevelParam
  }
  case object ContentControlTool nstall extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableContentControlTool nstallSafetyLevelParam
  }
  case object Conversat onFocalPrehydrat on extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableConversat onFocalPrehydrat onSafetyLevelParam
  }
  case object Conversat onFocalT et extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableConversat onFocalT etSafetyLevelParam
  }
  case object Conversat on njectedT et extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableConversat on njectedT etSafetyLevelParam
  }
  case object Conversat onReply extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableConversat onReplySafetyLevelParam
  }
  case object Access nternalPromotedContent extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableAccess nternalPromotedContentSafetyLevelParam
  }
  case object CuratedTrendsRepresentat veT et extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableCuratedTrendsRepresentat veT et
  }
  case object Curat onPol cyV olat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableCurat onPol cyV olat ons
  }
  case object DevPlatformGetL stT ets extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDevPlatformGetL stT etsSafetyLevelParam
  }
  case object DesFollow ngAndFollo rsUserL st extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableDESFollow ngAndFollo rsUserL stSafetyLevelParam
  }
  case object DesHo T  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESHo T  l neSafetyLevelParam
  }
  case object DesQuoteT etT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESQuoteT etT  l neSafetyLevelParam
  }
  case object DesRealt   extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESRealt  SafetyLevelParam
  }
  case object DesRealt  SpamEnr ch nt extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESRealt  SpamEnr ch ntSafetyLevelParam
  }
  case object DesRealt  T etF lter extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESRealt  T etF lterSafetyLevelParam
  }
  case object DesRet et ngUsers extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESRet et ngUsersSafetyLevelParam
  }
  case object DesT etDeta l extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDesT etDeta lSafetyLevelParam
  }
  case object DesT etL k ngUsers extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDEST etL k ngUsersSafetyLevelParam
  }
  case object DesUserBookmarks extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESUserBookmarksSafetyLevelParam
  }
  case object DesUserL kedT ets extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESUserL kedT etSafetyLevelParam
  }
  case object DesUser nt ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESUser nt onsSafetyLevelParam
  }
  case object DesUserT ets extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDESUserT etsSafetyLevelParam
  }
  case object DevPlatformCompl anceStream extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDevPlatformCompl anceStreamSafetyLevelParam
  }
  case object D rect ssages extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableD rect ssagesSafetyLevelParam
  }
  case object D rect ssagesConversat onL st extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableD rect ssagesConversat onL stSafetyLevelParam
  }
  case object D rect ssagesConversat onT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableD rect ssagesConversat onT  l neSafetyLevelParam
  }
  case object D rect ssages nbox extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableD rect ssages nboxSafetyLevelParam
  }
  case object D rect ssagesMutedUsers extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableD rect ssagesMutedUsersSafetyLevelParam
  }
  case object D rect ssagesP nned extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableD rect ssagesP nnedSafetyLevelParam
  }
  case object D rect ssagesSearch extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableD rect ssagesSearchSafetyLevelParam
  }
  case object Ed  toryT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableEd  toryT  l neSafetyLevelParam
  }
  case object ElevatedQuoteT etT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableElevatedQuoteT etT  l neSafetyLevelParam
  }
  case object EmbeddedT et extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableEmbeddedT etSafetyLevelParam
  }
  case object EmbedsPubl c nterestNot ce extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableEmbedsPubl c nterestNot ceSafetyLevelParam
  }
  case object EmbedT etMarkup extends SafetyLevel {
    overr de def enabledParam: SafetyLevelParam = EnableEmbedT etMarkupSafetyLevelParam
  }
  case object Wr ePathL m edAct onsEnforce nt extends SafetyLevel {
    overr de def enabledParam: SafetyLevelParam =
      EnableWr ePathL m edAct onsEnforce ntSafetyLevelParam
  }
  case object F lterNone extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableF lterNoneSafetyLevelParam
  }
  case object F lterAll extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableF lterAllSafetyLevelParam
  }
  case object F lterAllPlaceholder extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableF lterDefaultSafetyLevelParam
  }
  case object F lterDefault extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableF lterDefaultSafetyLevelParam
  }
  case object Follo dTop csT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableFollo dTop csT  l neSafetyLevelParam
  }
  case object Follo rConnect ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableFollo rConnect onsSafetyLevelParam
  }
  case object Follow ngAndFollo rsUserL st extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableFollow ngAndFollo rsUserL stSafetyLevelParam
  }
  case object ForDevelop ntOnly extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableForDevelop ntOnlySafetyLevelParam
  }
  case object Fr endsFollow ngL st extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableFr endsFollow ngL stSafetyLevelParam
  }
  case object GraphqlDefault extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableGraphqlDefaultSafetyLevelParam
  }
  case object GryphonDecksAndColumns extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableGryphonDecksAndColumnsSafetyLevelParam
  }
  case object Human zat onNudge extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableHuman zat onNudgeSafetyLevelParam
  }
  case object K c nS nkDevelop nt extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableK c nS nkDevelop ntSafetyLevelParam
  }
  case object L st ader extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableL st aderSafetyLevelParam
  }
  case object L st mbersh ps extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableL st mbersh psSafetyLevelParam
  }
  case object L stOwnersh ps extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableL stOwnersh psSafetyLevelParam
  }
  case object L stRecom ndat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableL stRecom ndat onsSafetyLevelParam
  }
  case object L stSearch extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableL stSearchSafetyLevelParam
  }
  case object L stSubscr pt ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableL stSubscr pt onsSafetyLevelParam
  }
  case object L veP pel neEngage ntCounts extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableL veP pel neEngage ntCountsSafetyLevelParam
  }
  case object L veV deoT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableL veV deoT  l neSafetyLevelParam
  }
  case object Mag cRecs extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableMag cRecsSafetyLevelParam
  }
  case object Mag cRecsAggress ve extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableMag cRecsAggress veSafetyLevelParam
  }
  case object Mag cRecsAggress veV2 extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableMag cRecsAggress veV2SafetyLevelParam
  }
  case object Mag cRecsV2 extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableMag cRecsV2SafetyLevelParam
  }
  case object M n mal extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableM n malSafetyLevelParam
  }
  case object ModeratedT etsT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableModeratedT etsT  l neSafetyLevelParam
  }
  case object Mo nts extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableMo ntsSafetyLevelParam
  }
  case object NearbyT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNearbySafetyLevelParam
  }
  case object NewUserExper ence extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNewUserExper enceSafetyLevelParam
  }
  case object Not f cat ons b s extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNot f cat ons b sSafetyLevelParam
  }
  case object Not f cat onsPlatform extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNot f cat onsPlatformSafetyLevelParam
  }
  case object Not f cat onsPlatformPush extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNot f cat onsPlatformPushSafetyLevelParam
  }
  case object Not f cat onsQ g extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNot f cat onsQ gSafetyLevelParam
  }
  case object Not f cat onsRead extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNot f cat onsReadSafetyLevelParam
  }
  case object Not f cat onsT  l neDev ceFollow extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableNot f cat onsT  l neDev ceFollowSafetyLevelParam
  }
  case object Not f cat onsWr e extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNot f cat onsWr eSafetyLevelParam
  }
  case object Not f cat onsWr erV2 extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableNot f cat onsWr erV2SafetyLevelParam
  }
  case object Not f cat onsWr erT etHydrator extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableNot f cat onsWr erT etHydratorSafetyLevelParam
  }
  case object Prof leM xer d a extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableProf leM xer d aSafetyLevelParam
  }
  case object Prof leM xerFavor es extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableProf leM xerFavor esSafetyLevelParam
  }
  case object Qu ckPromoteT etEl g b l y extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableQu ckPromoteT etEl g b l ySafetyLevelParam
  }
  case object QuoteT etT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableQuoteT etT  l neSafetyLevelParam
  }
  case object QuotedT etRules extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableQuotedT etRulesParam
  }
  case object Recom ndat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableRecom ndat onsSafetyLevelParam
  }
  case object RecosV deo extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableRecosV deoSafetyLevelParam
  }
  case object RecosWr ePath extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableRecosWr ePathSafetyLevelParam
  }
  case object Repl esGroup ng extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableRepl esGroup ngSafetyLevelParam
  }
  case object ReportCenter extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableReportCenterSafetyLevelParam
  }
  case object Return ngUserExper ence extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableReturn ngUserExper enceSafetyLevelParam
  }
  case object Return ngUserExper enceFocalT et extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableReturn ngUserExper enceFocalT etSafetyLevelParam
  }
  case object Revenue extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableRevenueSafetyLevelParam
  }
  case object R oAct onedT etT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableR oAct onedT etT  l neParam
  }
  case object SafeSearchM n mal extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSafeSearchM n malSafetyLevelParam
  }
  case object SafeSearchStr ct extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSafeSearchStr ctSafetyLevelParam
  }
  case object SearchHydrat on extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchHydrat onSafetyLevelParam
  }
  case object SearchLatest extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchLatestSafetyLevelParam
  }
  case object SearchM xerSrpM n mal extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchM xerSrpM n malSafetyLevelParam
  }
  case object SearchM xerSrpStr ct extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchM xerSrpStr ctSafetyLevelParam
  }
  case object SearchPeopleSrp extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchPeopleSearchResultPageSafetyLevelParam
  }
  case object SearchPeopleTypea ad extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchPeopleTypea adSafetyLevelParam
  }
  case object SearchPhoto extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchPhotoSafetyLevelParam
  }
  case object Shopp ngManagerSpyMode extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableShopp ngManagerSpyModeSafetyLevelParam
  }
  case object StratoExtL m edEngage nts extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableStratoExtL m edEngage ntsSafetyLevelParam
  }
  case object SearchTop extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchTopSafetyLevelParam
  }
  case object SearchTopQ g extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchTopQ gSafetyLevelParam
  }
  case object SearchTrendTakeoverPromotedT et extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = SearchTrendTakeoverPromotedT etSafetyLevelParam
  }
  case object SearchV deo extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchV deoSafetyLevelParam
  }
  case object SearchBlenderUserRules extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchBlenderUserRulesSafetyLevelParam
  }
  case object SearchLatestUserRules extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSearchLatestUserRulesSafetyLevelParam
  }
  case object S gnalsReact ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableS gnalsReact onsSafetyLevelParam
  }
  case object S gnalsT etReact ngUsers extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableS gnalsT etReact ngUsersSafetyLevelParam
  }
  case object Soc alProof extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSoc alProofSafetyLevelParam
  }
  case object Soft ntervent onP vot extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSoft ntervent onP votSafetyLevelParam
  }
  case object SpaceFleetl ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSpaceFleetl neSafetyLevelParam
  }
  case object SpaceHo T  l neUprank ng extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSpaceHo T  l neUprank ngSafetyLevelParam
  }
  case object SpaceJo nScreen extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSpaceJo nScreenSafetyLevelParam
  }
  case object SpaceNot f cat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSpaceNot f cat onSafetyLevelParam
  }
  case object Spaces extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSpacesSafetyLevelParam
  }
  case object SpacesPart c pants extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSpacesPart c pantsSafetyLevelParam
  }
  case object SpacesSellerAppl cat onStatus extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableSpacesSellerAppl cat onStatusSafetyLevelParam
  }
  case object SpacesShar ng extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSpacesShar ngSafetyLevelParam
  }
  case object SpaceT etAvatarHo T  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSpaceT etAvatarHo T  l neSafetyLevelParam
  }
  case object St ckersT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSt ckersT  l neSafetyLevelParam
  }
  case object StreamServ ces extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableStreamServ cesSafetyLevelParam
  }
  case object SuperFollo rConnect ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSuperFollo rConnect onsSafetyLevelParam
  }
  case object SuperL ke extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableSuperL keSafetyLevelParam
  }
  case object Test extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTestSafetyLevelParam
  }
  case object T  l neConversat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neConversat onsSafetyLevelParam
  }
  case object T  l neConversat onsDownrank ng extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableT  l neConversat onsDownrank ngSafetyLevelParam
  }
  case object T  l neConversat onsDownrank ngM n mal extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableT  l neConversat onsDownrank ngM n malSafetyLevelParam
  }
  case object T  l neFollow ngAct v y extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neFollow ngAct v ySafetyLevelParam
  }
  case object T  l neHo  extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neHo SafetyLevelParam
  }
  case object T  l neHo Commun  es extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neHo Commun  esSafetyLevelParam
  }
  case object T  l neHo Hydrat on extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neHo Hydrat onSafetyLevelParam
  }
  case object T  l neHo PromotedHydrat on extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableT  l neHo PromotedHydrat onSafetyLevelParam
  }
  case object T  l neHo Recom ndat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neHo Recom ndat onsSafetyLevelParam
  }
  case object T  l neHo Top cFollowRecom ndat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableT  l neHo Top cFollowRecom ndat onsSafetyLevelParam
  }
  case object T  l neScorer extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableT  l neScorerSafetyLevelParam
  }
  case object Top csLand ngPageTop cRecom ndat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableTop csLand ngPageTop cRecom ndat onsSafetyLevelParam
  }
  case object ExploreRecom ndat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableExploreRecom ndat onsSafetyLevelParam
  }
  case object T  l neModeratedT etsHydrat on extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableT  l neModeratedT etsHydrat onSafetyLevelParam
  }
  case object T  l ne nject on extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l ne nject onSafetyLevelParam
  }
  case object T  l ne nt ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l ne nt onsSafetyLevelParam
  }
  case object T  l neHo Latest extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neHo LatestSafetyLevelParam
  }
  case object T  l neL kedBy extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neL kedBySafetyLevelParam
  }
  case object T  l neRet etedBy extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neRet etedBySafetyLevelParam
  }
  case object T  l neSuperL kedBy extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neL kedBySafetyLevelParam
  }
  case object T  l neBookmark extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neBookmarkSafetyLevelParam
  }
  case object T  l neContentControls extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neContentControlsSafetyLevelParam
  }
  case object T  l ne d a extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l ne d aSafetyLevelParam
  }
  case object T  l neReact veBlend ng extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neReact veBlend ngSafetyLevelParam
  }
  case object T  l neFavor es extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neFavor esSafetyLevelParam
  }
  case object T  l neFavor esSelfV ew extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neFavor esSelfV ewSafetyLevelParam
  }
  case object T  l neL sts extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neL stsSafetyLevelParam
  }
  case object T  l neProf le extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neProf leSafetyLevelParam
  }
  case object T  l neProf leAll extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neProf leAllSafetyLevelParam
  }

  case object T  l neProf leSpaces extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neProf leSpacesSafetyLevelParam
  }

  case object T  l neProf leSuperFollows extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neProf leSuperFollowsSafetyLevelParam
  }
  case object T  l neFocalT et extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT  l neFocalT etSafetyLevelParam
  }
  case object Tombston ng extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTombston ngSafetyLevelParam
  }
  case object Top cRecom ndat ons extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTop cRecom ndat onsSafetyLevelParam
  }
  case object TrendsRepresentat veT et extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTrendsRepresentat veT etSafetyLevelParam
  }
  case object TrustedFr endsUserL st extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTrustedFr endsUserL stSafetyLevelParam
  }
  case object Tw terDelegateUserL st extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTw terDelegateUserL stSafetyLevelParam
  }
  case object T etDeta l extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT etDeta lSafetyLevelParam
  }
  case object T etDeta lNonToo extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT etDeta lNonTooSafetyLevelParam
  }
  case object T etDeta lW h nject onsHydrat on extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam =
      EnableT etDeta lW h nject onsHydrat onSafetyLevelParam
  }
  case object T etEngagers extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT etEngagersSafetyLevelParam
  }
  case object T etReplyNudge extends SafetyLevel {
    overr de def enabledParam: SafetyLevelParam = EnableT etReplyNudgeParam
  }
  case object T etScopedT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT etScopedT  l neSafetyLevelParam
  }
  case object T etWr esAp  extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT etWr esAp SafetyLevelParam
  }
  case object Tw terArt cleCompose extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTw terArt cleComposeSafetyLevelParam
  }
  case object Tw terArt cleProf leTab extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTw terArt cleProf leTabSafetyLevelParam
  }
  case object Tw terArt cleRead extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableTw terArt cleReadSafetyLevelParam
  }
  case object UserProf le ader extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableUserProf le aderSafetyLevelParam
  }
  case object UserM lestoneRecom ndat on extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableUserM lestoneRecom ndat onSafetyLevelParam
  }
  case object UserScopedT  l ne extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableUserScopedT  l neSafetyLevelParam
  }
  case object UserSearchSrp extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableUserSearchSrpSafetyLevelParam
  }
  case object UserSearchTypea ad extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableUserSearchTypea adSafetyLevelParam
  }
  case object UserSelfV ewOnly extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableUserSelfV ewOnlySafetyLevelParam
  }
  case object UserSett ngs extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableUserSett ngsSafetyLevelParam
  }
  case object V deoAds extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableV deoAdsSafetyLevelParam
  }
  case object Z pb rdConsu rArch ves extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableZ pb rdConsu rArch vesSafetyLevelParam
  }
  case object T etAward extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableT etAwardSafetyLevelParam
  }

  case object DeprecatedSafetyLevel extends SafetyLevel {
    overr de val enabledParam: SafetyLevelParam = EnableDeprecatedSafetyLevel
  }


  def fromThr ft(safetyLevel: Thr ftSafetyLevel): SafetyLevel =
    thr ftToModelMap.get(safetyLevel).getOrElse(DeprecatedSafetyLevel)

  def toThr ft(safetyLevel: SafetyLevel): Thr ftSafetyLevel =
    modelToThr ftMap
      .get(safetyLevel).getOrElse(Thr ftSafetyLevel.EnumUnknownSafetyLevel(DeprecatedEnumValue))

  val L st: Seq[SafetyLevel] =
    Thr ftSafetyLevel.l st.map(fromThr ft).f lter(_ != DeprecatedSafetyLevel)
}
