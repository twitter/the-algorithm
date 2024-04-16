package com.tw ter.v s b l y.conf gap .conf gs

 mport com.tw ter.dec der.Rec p ent
 mport com.tw ter.dec der.S mpleRec p ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .BaseRequestContext
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.conf gap .W hGuest d
 mport com.tw ter.t  l nes.conf gap .W hUser d
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derSw chOverr deValue
 mport com.tw ter.t  l nes.conf gap .dec der.GuestRec p ent
 mport com.tw ter.t  l nes.conf gap .dec der.Rec p entBu lder
 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.conf gap .params.T  l neConversat onsDownrank ngSpec f cParams
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.SafetyLevel._

pr vate[v s b l y] object V s b l yDec ders {
  val SafetyLevelToDec derMap: Map[SafetyLevel, Dec derKey.Value] = Map(
    AllSubscr bedL sts -> Dec derKey.EnableAllSubscr bedL stsSafetyLevel,
    Access nternalPromotedContent -> Dec derKey.EnableAccess nternalPromotedContentSafetyLevel,
    AdsBus nessSett ngs -> Dec derKey.EnableAdsBus nessSett ngsSafetyLevel,
    AdsCampa gn -> Dec derKey.EnableAdsCampa gnSafetyLevel,
    AdsManager -> Dec derKey.EnableAdsManagerSafetyLevel,
    AdsReport ngDashboard -> Dec derKey.EnableAdsReport ngDashboardSafetyLevel,
    Appeals -> Dec derKey.EnableAppealsSafetyLevel,
    Art cleT etT  l ne -> Dec derKey.EnableArt cleT etT  l neSafetyLevel,
    BaseQ g -> Dec derKey.EnableBaseQ g,
    B rdwatchNoteAuthor -> Dec derKey.EnableB rdwatchNoteAuthorSafetyLevel,
    B rdwatchNoteT etsT  l ne -> Dec derKey.EnableB rdwatchNoteT etsT  l neSafetyLevel,
    B rdwatchNeedsY  lpNot f cat ons -> Dec derKey.EnableB rdwatchNeedsY  lpNot f cat onsSafetyLevel,
    BlockMuteUsersT  l ne -> Dec derKey.EnableBlockMuteUsersT  l neSafetyLevel,
    BrandSafety -> Dec derKey.EnableBrandSafetySafetyLevel,
    CardPollVot ng -> Dec derKey.EnableCardPollVot ngSafetyLevel,
    CardsServ ce -> Dec derKey.EnableCardsServ ceSafetyLevel,
    Commun  es -> Dec derKey.EnableCommun  esSafetyLevel,
    ContentControlTool nstall -> Dec derKey.EnableContentControlTool nstallSafetyLevel,
    Conversat onFocalPrehydrat on -> Dec derKey.EnableConversat onFocalPrehydrat onSafetyLevel,
    Conversat onFocalT et -> Dec derKey.EnableConversat onFocalT etSafetyLevel,
    Conversat on njectedT et -> Dec derKey.EnableConversat on njectedT etSafetyLevel,
    Conversat onReply -> Dec derKey.EnableConversat onReplySafetyLevel,
    CuratedTrendsRepresentat veT et -> Dec derKey.EnableCuratedTrendsRepresentat veT et,
    Curat onPol cyV olat ons -> Dec derKey.EnableCurat onPol cyV olat ons,
    DeprecatedSafetyLevel -> Dec derKey.EnableDeprecatedSafetyLevelSafetyLevel,
    DevPlatformGetL stT ets -> Dec derKey.EnableDevPlatformGetL stT etsSafetyLevel,
    DesFollow ngAndFollo rsUserL st -> Dec derKey.EnableDesFollow ngAndFollo rsUserL stSafetyLevel,
    DesHo T  l ne -> Dec derKey.EnableDesHo T  l neSafetyLevel,
    DesQuoteT etT  l ne -> Dec derKey.EnableDesQuoteT etT  l neSafetyLevel,
    DesRealt   -> Dec derKey.EnableDesRealt  SafetyLevel,
    DesRealt  SpamEnr ch nt -> Dec derKey.EnableDesRealt  SpamEnr ch ntSafetyLevel,
    DesRealt  T etF lter -> Dec derKey.EnableDesRealt  T etF lterSafetyLevel,
    DesRet et ngUsers -> Dec derKey.EnableDesRet et ngUsersSafetyLevel,
    DesT etDeta l -> Dec derKey.EnableDesT etDeta lSafetyLevel,
    DesT etL k ngUsers -> Dec derKey.EnableDesT etL k ngUsersSafetyLevel,
    DesUserBookmarks -> Dec derKey.EnableDesUserBookmarksSafetyLevel,
    DesUserL kedT ets -> Dec derKey.EnableDesUserL kedT etsSafetyLevel,
    DesUser nt ons -> Dec derKey.EnableDesUser nt onsSafetyLevel,
    DesUserT ets -> Dec derKey.EnableDesUserT etsSafetyLevel,
    DevPlatformCompl anceStream -> Dec derKey.EnableDevPlatformCompl anceStreamSafetyLevel,
    D rect ssages -> Dec derKey.EnableD rect ssagesSafetyLevel,
    D rect ssagesConversat onL st -> Dec derKey.EnableD rect ssagesConversat onL stSafetyLevel,
    D rect ssagesConversat onT  l ne -> Dec derKey.EnableD rect ssagesConversat onT  l neSafetyLevel,
    D rect ssages nbox -> Dec derKey.EnableD rect ssages nboxSafetyLevel,
    D rect ssagesMutedUsers -> Dec derKey.EnableD rect ssagesMutedUsersSafetyLevel,
    D rect ssagesP nned -> Dec derKey.EnableD rect ssagesP nnedSafetyLevel,
    D rect ssagesSearch -> Dec derKey.EnableD rect ssagesSearchSafetyLevel,
    Ed  toryT  l ne -> Dec derKey.EnableEd  toryT  l neSafetyLevel,
    ElevatedQuoteT etT  l ne -> Dec derKey.EnableElevatedQuoteT etT  l neSafetyLevel,
    EmbeddedT et -> Dec derKey.EnableEmbeddedT etSafetyLevel,
    EmbedsPubl c nterestNot ce -> Dec derKey.EnableEmbedsPubl c nterestNot ceSafetyLevel,
    EmbedT etMarkup -> Dec derKey.EnableEmbedT etMarkupSafetyLevel,
    F lterAll -> Dec derKey.EnableF lterAllSafetyLevel,
    F lterAllPlaceholder -> Dec derKey.EnableF lterAllPlaceholderSafetyLevel,
    F lterNone -> Dec derKey.EnableF lterNoneSafetyLevel,
    F lterDefault -> Dec derKey.EnableF lterDefaultSafetyLevel,
    Follo dTop csT  l ne -> Dec derKey.EnableFollo dTop csT  l neSafetyLevel,
    Follo rConnect ons -> Dec derKey.EnableFollo rConnect onsSafetyLevel,
    Follow ngAndFollo rsUserL st -> Dec derKey.EnableFollow ngAndFollo rsUserL stSafetyLevel,
    ForDevelop ntOnly -> Dec derKey.EnableForDevelop ntOnlySafetyLevel,
    Fr endsFollow ngL st -> Dec derKey.EnableFr endsFollow ngL stSafetyLevel,
    GraphqlDefault -> Dec derKey.EnableGraphqlDefaultSafetyLevel,
    GryphonDecksAndColumns -> Dec derKey.EnableGryphonDecksAndColumnsSafetyLevel,
    Human zat onNudge -> Dec derKey.EnableHuman zat onNudgeSafetyLevel,
    K c nS nkDevelop nt -> Dec derKey.EnableK c nS nkDevelop ntSafetyLevel,
    L st ader -> Dec derKey.EnableL st aderSafetyLevel,
    L st mbersh ps -> Dec derKey.EnableL st mbersh psSafetyLevel,
    L stOwnersh ps -> Dec derKey.EnableL stOwnersh psSafetyLevel,
    L stRecom ndat ons -> Dec derKey.EnableL stRecom ndat onsSafetyLevel,
    L stSearch -> Dec derKey.EnableL stSearchSafetyLevel,
    L stSubscr pt ons -> Dec derKey.EnableL stSubscr pt onsSafetyLevel,
    L veV deoT  l ne -> Dec derKey.EnableL veV deoT  l neSafetyLevel,
    L veP pel neEngage ntCounts -> Dec derKey.EnableL veP pel neEngage ntCountsSafetyLevel,
    Mag cRecs -> Dec derKey.EnableMag cRecsSafetyLevel,
    Mag cRecsAggress ve -> Dec derKey.EnableMag cRecsAggress veSafetyLevel,
    Mag cRecsAggress veV2 -> Dec derKey.EnableMag cRecsAggress veV2SafetyLevel,
    Mag cRecsV2 -> Dec derKey.EnableMag cRecsV2SafetyLevel,
    M n mal -> Dec derKey.EnableM n malSafetyLevel,
    ModeratedT etsT  l ne -> Dec derKey.EnableModeratedT etsT  l neSafetyLevel,
    Mo nts -> Dec derKey.EnableMo ntsSafetyLevel,
    NearbyT  l ne -> Dec derKey.EnableNearbyT  l neSafetyLevel,
    NewUserExper ence -> Dec derKey.EnableNewUserExper enceSafetyLevel,
    Not f cat ons b s -> Dec derKey.EnableNot f cat ons b sSafetyLevel,
    Not f cat onsPlatform -> Dec derKey.EnableNot f cat onsPlatformSafetyLevel,
    Not f cat onsPlatformPush -> Dec derKey.EnableNot f cat onsPlatformPushSafetyLevel,
    Not f cat onsQ g -> Dec derKey.EnableNot f cat onsQ g,
    Not f cat onsRead -> Dec derKey.EnableNot f cat onsReadSafetyLevel,
    Not f cat onsT  l neDev ceFollow -> Dec derKey.EnableNot f cat onsT  l neDev ceFollowSafetyLevel,
    Not f cat onsWr e -> Dec derKey.EnableNot f cat onsWr eSafetyLevel,
    Not f cat onsWr erV2 -> Dec derKey.EnableNot f cat onsWr erV2SafetyLevel,
    Not f cat onsWr erT etHydrator -> Dec derKey.EnableNot f cat onsWr erT etHydratorSafetyLevel,
    Prof leM xer d a -> Dec derKey.EnableProf leM xe d aSafetyLevel,
    Prof leM xerFavor es -> Dec derKey.EnableProf leM xerFavor esSafetyLevel,
    Qu ckPromoteT etEl g b l y -> Dec derKey.EnableQu ckPromoteT etEl g b l ySafetyLevel,
    QuoteT etT  l ne -> Dec derKey.EnableQuoteT etT  l neSafetyLevel,
    QuotedT etRules -> Dec derKey.EnableQuotedT etRulesSafetyLevel,
    Recom ndat ons -> Dec derKey.EnableRecom ndat onsSafetyLevel,
    RecosV deo -> Dec derKey.EnableRecosV deoSafetyLevel,
    RecosWr ePath -> Dec derKey.EnableRecosWr ePathSafetyLevel,
    Repl esGroup ng -> Dec derKey.EnableRepl esGroup ngSafetyLevel,
    ReportCenter -> Dec derKey.EnableReportCenterSafetyLevel,
    Return ngUserExper ence -> Dec derKey.EnableReturn ngUserExper enceSafetyLevel,
    Return ngUserExper enceFocalT et -> Dec derKey.EnableReturn ngUserExper enceFocalT etSafetyLevel,
    Revenue -> Dec derKey.EnableRevenueSafetyLevel,
    R oAct onedT etT  l ne -> Dec derKey.EnableR oAct onedT etT  l neSafetyLevel,
    SafeSearchM n mal -> Dec derKey.EnableSafeSearchM n malSafetyLevel,
    SafeSearchStr ct -> Dec derKey.EnableSafeSearchStr ctSafetyLevel,
    SearchM xerSrpM n mal -> Dec derKey.EnableSearchM xerSrpM n malSafetyLevel,
    SearchM xerSrpStr ct -> Dec derKey.EnableSearchM xerSrpStr ctSafetyLevel,
    SearchHydrat on -> Dec derKey.EnableSearchHydrat on,
    SearchLatest -> Dec derKey.EnableSearchLatest,
    SearchPeopleSrp -> Dec derKey.EnableSearchPeopleSrp,
    SearchPeopleTypea ad -> Dec derKey.EnableSearchPeopleTypea ad,
    SearchPhoto -> Dec derKey.EnableSearchPhoto,
    SearchTrendTakeoverPromotedT et -> Dec derKey.EnableSearchTrendTakeoverPromotedT et,
    SearchTop -> Dec derKey.EnableSearchTop,
    SearchTopQ g -> Dec derKey.EnableSearchTopQ g,
    SearchV deo -> Dec derKey.EnableSearchV deo,
    SearchBlenderUserRules -> Dec derKey.EnableSearchLatestUserRules,
    SearchLatestUserRules -> Dec derKey.EnableSearchLatestUserRules,
    Shopp ngManagerSpyMode -> Dec derKey.EnableShopp ngManagerSpyModeSafetyLevel,
    S gnalsReact ons -> Dec derKey.EnableS gnalsReact ons,
    S gnalsT etReact ngUsers -> Dec derKey.EnableS gnalsT etReact ngUsers,
    Soc alProof -> Dec derKey.EnableSoc alProof,
    Soft ntervent onP vot -> Dec derKey.EnableSoft ntervent onP vot,
    SpaceFleetl ne -> Dec derKey.EnableSpaceFleetl neSafetyLevel,
    SpaceHo T  l neUprank ng -> Dec derKey.EnableSpaceHo T  l neUprank ngSafetyLevel,
    SpaceJo nScreen -> Dec derKey.EnableSpaceJo nScreenSafetyLevel,
    SpaceNot f cat ons -> Dec derKey.EnableSpaceNot f cat onsSafetyLevel,
    Spaces -> Dec derKey.EnableSpacesSafetyLevel,
    SpacesPart c pants -> Dec derKey.EnableSpacesPart c pantsSafetyLevel,
    SpacesSellerAppl cat onStatus -> Dec derKey.EnableSpacesSellerAppl cat onStatus,
    SpacesShar ng -> Dec derKey.EnableSpacesShar ngSafetyLevel,
    SpaceT etAvatarHo T  l ne -> Dec derKey.EnableSpaceT etAvatarHo T  l neSafetyLevel,
    St ckersT  l ne -> Dec derKey.EnableSt ckersT  l neSafetyLevel,
    StratoExtL m edEngage nts -> Dec derKey.EnableStratoExtL m edEngage ntsSafetyLevel,
    StreamServ ces -> Dec derKey.EnableStreamServ cesSafetyLevel,
    SuperFollo rConnect ons -> Dec derKey.EnableSuperFollo rConnect onsSafetyLevel,
    SuperL ke -> Dec derKey.EnableSuperL keSafetyLevel,
    Test -> Dec derKey.EnableTestSafetyLevel,
    T  l neContentControls -> Dec derKey.EnableT  l neContentControlsSafetyLevel,
    T  l neConversat ons -> Dec derKey.EnableT  l neConversat onsSafetyLevel,
    T  l neConversat onsDownrank ng -> Dec derKey.EnableT  l neConversat onsDownrank ngSafetyLevel,
    T  l neConversat onsDownrank ngM n mal -> Dec derKey.EnableT  l neConversat onsDownrank ngM n malSafetyLevel,
    T  l neFollow ngAct v y -> Dec derKey.EnableT  l neFollow ngAct v ySafetyLevel,
    T  l neHo  -> Dec derKey.EnableT  l neHo SafetyLevel,
    T  l neHo Commun  es -> Dec derKey.EnableT  l neHo Commun  esSafetyLevel,
    T  l neHo Hydrat on -> Dec derKey.EnableT  l neHo Hydrat onSafetyLevel,
    T  l neHo PromotedHydrat on -> Dec derKey.EnableT  l neHo PromotedHydrat onSafetyLevel,
    T  l neHo Recom ndat ons -> Dec derKey.EnableT  l neHo Recom ndat onsSafetyLevel,
    T  l neHo Top cFollowRecom ndat ons -> Dec derKey.EnableT  l neHo Top cFollowRecom ndat onsSafetyLevel,
    T  l neScorer -> Dec derKey.EnableT  l neScorerSafetyLevel,
    Top csLand ngPageTop cRecom ndat ons -> Dec derKey.EnableTop csLand ngPageTop cRecom ndat onsSafetyLevel,
    ExploreRecom ndat ons -> Dec derKey.EnableExploreRecom ndat onsSafetyLevel,
    T  l ne nject on -> Dec derKey.EnableT  l ne nject onSafetyLevel,
    T  l ne nt ons -> Dec derKey.EnableT  l ne nt onsSafetyLevel,
    T  l neModeratedT etsHydrat on -> Dec derKey.EnableT  l neModeratedT etsHydrat onSafetyLevel,
    T  l neHo Latest -> Dec derKey.EnableT  l neHo LatestSafetyLevel,
    T  l neL kedBy -> Dec derKey.EnableT  l neL kedBySafetyLevel,
    T  l neRet etedBy -> Dec derKey.EnableT  l neRet etedBySafetyLevel,
    T  l neSuperL kedBy -> Dec derKey.EnableT  l neSuperL kedBySafetyLevel,
    T  l neBookmark -> Dec derKey.EnableT  l neBookmarkSafetyLevel,
    T  l ne d a -> Dec derKey.EnableT  l ne d aSafetyLevel,
    T  l neReact veBlend ng -> Dec derKey.EnableT  l neReact veBlend ngSafetyLevel,
    T  l neFavor es -> Dec derKey.EnableT  l neFavor esSafetyLevel,
    T  l neFavor esSelfV ew -> Dec derKey.EnableSelfV ewT  l neFavor esSafetyLevel,
    T  l neL sts -> Dec derKey.EnableT  l neL stsSafetyLevel,
    T  l neProf le -> Dec derKey.EnableT  l neProf leSafetyLevel,
    T  l neProf leAll -> Dec derKey.EnableT  l neProf leAllSafetyLevel,
    T  l neProf leSpaces -> Dec derKey.EnableT  l neProf leSpacesSafetyLevel,
    T  l neProf leSuperFollows -> Dec derKey.EnableT  l neProf leSuperFollowsSafetyLevel,
    T  l neFocalT et -> Dec derKey.EnableT etT  l neFocalT etSafetyLevel,
    T etDeta lW h nject onsHydrat on -> Dec derKey.EnableT etDeta lW h nject onsHydrat onSafetyLevel,
    Tombston ng -> Dec derKey.EnableTombston ngSafetyLevel,
    Top cRecom ndat ons -> Dec derKey.EnableTop cRecom ndat onsSafetyLevel,
    TrendsRepresentat veT et -> Dec derKey.EnableTrendsRepresentat veT etSafetyLevel,
    TrustedFr endsUserL st -> Dec derKey.EnableTrustedFr endsUserL stSafetyLevel,
    Tw terDelegateUserL st -> Dec derKey.EnableTw terDelegateUserL stSafetyLevel,
    T etDeta l -> Dec derKey.EnableT etDeta lSafetyLevel,
    T etDeta lNonToo -> Dec derKey.EnableT etDeta lNonTooSafetyLevel,
    T etEngagers -> Dec derKey.EnableT etEngagersSafetyLevel,
    T etReplyNudge -> Dec derKey.EnableT etReplyNudgeSafetyLevel,
    T etScopedT  l ne -> Dec derKey.EnableT etScopedT  l neSafetyLevel,
    T etWr esAp  -> Dec derKey.EnableT etWr esAp SafetyLevel,
    Tw terArt cleCompose -> Dec derKey.EnableTw terArt cleComposeSafetyLevel,
    Tw terArt cleProf leTab -> Dec derKey.EnableTw terArt cleProf leTabSafetyLevel,
    Tw terArt cleRead -> Dec derKey.EnableTw terArt cleReadSafetyLevel,
    UserProf le ader -> Dec derKey.EnableUserProf le aderSafetyLevel,
    UserM lestoneRecom ndat on -> Dec derKey.EnableUserM lestoneRecom ndat onSafetyLevel,
    UserScopedT  l ne -> Dec derKey.EnableUserScopedT  l neSafetyLevel,
    UserSearchSrp -> Dec derKey.EnableUserSearchSrpSafetyLevel,
    UserSearchTypea ad -> Dec derKey.EnableUserSearchTypea adSafetyLevel,
    UserSelfV ewOnly -> Dec derKey.EnableUserSelfV ewOnlySafetyLevel,
    UserSett ngs -> Dec derKey.EnableUserSett ngsSafetyLevel,
    V deoAds -> Dec derKey.EnableV deoAdsSafetyLevel,
    Wr ePathL m edAct onsEnforce nt -> Dec derKey.EnableWr ePathL m edAct onsEnforce ntSafetyLevel,
    Z pb rdConsu rArch ves -> Dec derKey.EnableZ pb rdConsu rArch vesSafetyLevel,
    T etAward -> Dec derKey.EnableT etAwardSafetyLevel,
  )

  val BoolToDec derMap: Map[Param[Boolean], Dec derKey.Value] = Map(
    RuleParams.T etConversat onControlEnabledParam ->
      Dec derKey.EnableT etConversat onControlRules,
    RuleParams.Commun yT etsEnabledParam ->
      Dec derKey.EnableCommun yT etsControlRules,
    RuleParams.DropCommun yT etW hUndef nedCommun yRuleEnabledParam ->
      Dec derKey.EnableDropCommun yT etW hUndef nedCommun yRule,
    T  l neConversat onsDownrank ngSpec f cParams.EnablePSpam T etDownrankConvosLowQual yParam ->
      Dec derKey.EnablePSpam T etDownrankConvosLowQual y,
    RuleParams.EnableH ghPSpam T etScoreSearchT etLabelDropRuleParam ->
      Dec derKey.EnableH ghPSpam T etScoreSearchT etLabelDropRule,
    T  l neConversat onsDownrank ngSpec f cParams.EnableR oAct onedT etDownrankConvosLowQual yParam ->
      Dec derKey.EnableR oAct onedT etDownrankConvosLowQual y,
    RuleParams.EnableS teSpamT etRuleParam ->
      Dec derKey.EnableS teSpamT etRule,
    RuleParams.EnableH ghSpam T etContentScoreSearchLatestT etLabelDropRuleParam ->
      Dec derKey.EnableH ghSpam T etContentScoreSearchLatestT etLabelDropRule,
    RuleParams.EnableH ghSpam T etContentScoreSearchTopT etLabelDropRuleParam ->
      Dec derKey.EnableH ghSpam T etContentScoreSearchTopT etLabelDropRule,
    RuleParams.EnableH ghSpam T etContentScoreTrendsTopT etLabelDropRuleParam ->
      Dec derKey.EnableH ghSpam T etContentScoreTrendsTopT etLabelDropRule,
    RuleParams.EnableH ghSpam T etContentScoreTrendsLatestT etLabelDropRuleParam ->
      Dec derKey.EnableH ghSpam T etContentScoreTrendsLatestT etLabelDropRule,
    T  l neConversat onsDownrank ngSpec f cParams.EnableH ghSpam T etContentScoreConvoDownrankAbus veQual yRuleParam ->
      Dec derKey.EnableH ghSpam T etContentScoreConvoDownrankAbus veQual yRule,
    T  l neConversat onsDownrank ngSpec f cParams.EnableH ghCryptospamScoreConvoDownrankAbus veQual yRuleParam ->
      Dec derKey.EnableH ghCryptospamScoreConvoDownrankAbus veQual yRule,
    RuleParams.EnableGoreAndV olenceTop cH ghRecallT etLabelRule ->
      Dec derKey.EnableGoreAndV olenceTop cH ghRecallT etLabelRule,
    RuleParams.EnableL m Repl esFollo rsConversat onRule ->
      Dec derKey.EnableL m Repl esFollo rsConversat onRule,
    RuleParams.EnableSearchBas cBlockMuteRulesParam -> Dec derKey.EnableSearchBas cBlockMuteRules,
    RuleParams.EnableBl nkBadDownrank ngRuleParam ->
      Dec derKey.EnableBl nkBadDownrank ngRule,
    RuleParams.EnableBl nkWorstDownrank ngRuleParam ->
      Dec derKey.EnableBl nkWorstDownrank ngRule,
    RuleParams.EnableCopypastaSpamDownrankConvosAbus veQual yRule ->
      Dec derKey.EnableCopypastaSpamDownrankConvosAbus veQual yRule,
    RuleParams.EnableCopypastaSpamSearchDropRule ->
      Dec derKey.EnableCopypastaSpamSearchDropRule,
    RuleParams.EnableSpam UserModelT etDropRuleParam ->
      Dec derKey.EnableSpam UserModelH ghPrec s onDropT etRule,
    RuleParams.EnableAvo dNsfwRulesParam ->
      Dec derKey.EnableAvo dNsfwRules,
    RuleParams.EnableReportedT et nterst  alRule ->
      Dec derKey.EnableReportedT et nterst  alRule,
    RuleParams.EnableReportedT et nterst  alSearchRule ->
      Dec derKey.EnableReportedT et nterst  alSearchRule,
    RuleParams.EnableDropExclus veT etContentRule ->
      Dec derKey.EnableDropExclus veT etContentRule,
    RuleParams.EnableDropExclus veT etContentRuleFa lClosed ->
      Dec derKey.EnableDropExclus veT etContentRuleFa lClosed,
    RuleParams.EnableTombstoneExclus veQtProf leT  l neParam ->
      Dec derKey.EnableTombstoneExclus veQtProf leT  l neParam,
    RuleParams.EnableDropAllExclus veT etsRuleParam -> Dec derKey.EnableDropAllExclus veT etsRule,
    RuleParams.EnableDropAllExclus veT etsRuleFa lClosedParam -> Dec derKey.EnableDropAllExclus veT etsRuleFa lClosed,
    RuleParams.EnableDownrankSpamReplySect on ngRuleParam ->
      Dec derKey.EnableDownrankSpamReplySect on ngRule,
    RuleParams.EnableNsfwTextSect on ngRuleParam ->
      Dec derKey.EnableNsfwTextSect on ngRule,
    RuleParams.EnableSearch p SafeSearchW houtUser nQueryDropRule -> Dec derKey.EnableSearch p SafeSearchW houtUser nQueryDropRule,
    RuleParams.EnableT  l neHo PromotedT et althEnforce ntRules -> Dec derKey.EnableT  l neHo PromotedT et althEnforce ntRules,
    RuleParams.EnableMutedKeywordF lter ngSpaceT leNot f cat onsRuleParam -> Dec derKey.EnableMutedKeywordF lter ngSpaceT leNot f cat onsRule,
    RuleParams.EnableDropT etsW hGeoRestr cted d aRuleParam -> Dec derKey.EnableDropT etsW hGeoRestr cted d aRule,
    RuleParams.EnableDropAllTrustedFr endsT etsRuleParam -> Dec derKey.EnableDropAllTrustedFr endsT etsRule,
    RuleParams.EnableDropTrustedFr endsT etContentRuleParam -> Dec derKey.EnableDropTrustedFr endsT etContentRule,
    RuleParams.EnableDropAllCollab nv at onT etsRuleParam -> Dec derKey.EnableDropCollab nv at onT etsRule,
    RuleParams.EnableNsfwTextH ghPrec s onDropRuleParam -> Dec derKey.EnableNsfwTextH ghPrec s onDropRule,
    RuleParams.EnableL kely vsUserLabelDropRule -> Dec derKey.EnableL kely vsUserLabelDropRule,
    RuleParams.EnableCardUr RootDoma nCardDenyl stRule -> Dec derKey.EnableCardUr RootDoma nDenyl stRule,
    RuleParams.EnableCommun yNon mberPollCardRule -> Dec derKey.EnableCommun yNon mberPollCardRule,
    RuleParams.EnableCommun yNon mberPollCardRuleFa lClosed -> Dec derKey.EnableCommun yNon mberPollCardRuleFa lClosed,
    RuleParams.EnableExper  ntalNudgeEnabledParam -> Dec derKey.EnableExper  ntalNudgeLabelRule,
    RuleParams.NsfwH ghPrec s onUserLabelAvo dT etRuleEnabledParam -> Dec derKey.NsfwH ghPrec s onUserLabelAvo dT etRuleEnabledParam,
    RuleParams.EnableNewAdAvo danceRulesParam -> Dec derKey.EnableNewAdAvo danceRules,
    RuleParams.EnableNsfaH ghRecallAdAvo danceParam -> Dec derKey.EnableNsfaH ghRecallAdAvo danceParam,
    RuleParams.EnableNsfaKeywordsH ghPrec s onAdAvo danceParam -> Dec derKey.EnableNsfaKeywordsH ghPrec s onAdAvo danceParam,
    RuleParams.EnableStaleT etDropRuleParam -> Dec derKey.EnableStaleT etDropRuleParam,
    RuleParams.EnableStaleT etDropRuleFa lClosedParam -> Dec derKey.EnableStaleT etDropRuleFa lClosedParam,
    RuleParams.EnableDeleteStateT etRulesParam -> Dec derKey.EnableDeleteStateT etRules,
    RuleParams.EnableSpacesShar ngNsfwDropRulesParam -> Dec derKey.EnableSpacesShar ngNsfwDropRulesParam,
    RuleParams.EnableV e r sSoftUserDropRuleParam -> Dec derKey.EnableV e r sSoftUserDropRuleParam,
    RuleParams.EnablePdnaQuotedT etTombstoneRuleParam -> Dec derKey.EnablePdnaQuotedT etTombstoneRule,
    RuleParams.EnableSpamQuotedT etTombstoneRuleParam -> Dec derKey.EnableSpamQuotedT etTombstoneRule,
    RuleParams.EnableNsfwHpQuotedT etDropRuleParam -> Dec derKey.EnableNsfwHpQuotedT etDropRule,
    RuleParams.EnableNsfwHpQuotedT etTombstoneRuleParam -> Dec derKey.EnableNsfwHpQuotedT etTombstoneRule,
    RuleParams.Enable nnerQuotedT etV e rBlocksAuthor nterst  alRuleParam -> Dec derKey.Enable nnerQuotedT etV e rBlocksAuthor nterst  alRule,
    RuleParams.Enable nnerQuotedT etV e rMutesAuthor nterst  alRuleParam -> Dec derKey.Enable nnerQuotedT etV e rMutesAuthor nterst  alRule,
    RuleParams.EnableTox cReplyF lter ngConversat onRulesParam -> Dec derKey.V s b l yL braryEnableTox cReplyF lterConversat on,
    RuleParams.EnableTox cReplyF lter ngNot f cat onsRulesParam -> Dec derKey.V s b l yL braryEnableTox cReplyF lterNot f cat ons,
    RuleParams.EnableLegacySens  ve d aHo T  l neRulesParam -> Dec derKey.EnableLegacySens  ve d aRulesHo T  l ne,
    RuleParams.EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam -> Dec derKey.EnableNewSens  ve d aSett ngs nterst  alRulesHo T  l ne,
    RuleParams.EnableLegacySens  ve d aConversat onRulesParam -> Dec derKey.EnableLegacySens  ve d aRulesConversat on,
    RuleParams.EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam -> Dec derKey.EnableNewSens  ve d aSett ngs nterst  alRulesConversat on,
    RuleParams.EnableLegacySens  ve d aProf leT  l neRulesParam -> Dec derKey.EnableLegacySens  ve d aRulesProf leT  l ne,
    RuleParams.EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam -> Dec derKey.EnableNewSens  ve d aSett ngs nterst  alRulesProf leT  l ne,
    RuleParams.EnableLegacySens  ve d aT etDeta lRulesParam -> Dec derKey.EnableLegacySens  ve d aRulesT etDeta l,
    RuleParams.EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam -> Dec derKey.EnableNewSens  ve d aSett ngs nterst  alRulesT etDeta l,
    RuleParams.EnableLegacySens  ve d aD rect ssagesRulesParam -> Dec derKey.EnableLegacySens  ve d aRulesD rect ssages,
    RuleParams.EnableAbus veBehav orDropRuleParam -> Dec derKey.EnableAbus veBehav orDropRule,
    RuleParams.EnableAbus veBehav or nterst  alRuleParam -> Dec derKey.EnableAbus veBehav or nterst  alRule,
    RuleParams.EnableAbus veBehav orL m edEngage ntsRuleParam -> Dec derKey.EnableAbus veBehav orL m edEngage ntsRule,
    RuleParams.EnableNotGraduatedDownrankConvosAbus veQual yRuleParam -> Dec derKey.EnableNotGraduatedDownrankConvosAbus veQual yRule,
    RuleParams.EnableNotGraduatedSearchDropRuleParam -> Dec derKey.EnableNotGraduatedSearchDropRule,
    RuleParams.EnableNotGraduatedDropRuleParam -> Dec derKey.EnableNotGraduatedDropRule,
    RuleParams.EnableFosnrRuleParam -> Dec derKey.EnableFosnrRules,
    RuleParams.EnableAuthorBlocksV e rDropRuleParam -> Dec derKey.EnableAuthorBlocksV e rDropRule
  )

  def conf g(
    dec derGateBu lder: Dec derGateBu lder,
    logger: Logger,
    statsRece ver: StatsRece ver,
    SafetyLevel: SafetyLevel
  ): Conf g = {

    object UserOrGuestOrRequest extends Rec p entBu lder {
      pr vate val scopedStats = statsRece ver.scope("dec der_rec p ent")
      pr vate val user dDef nedCounter = scopedStats.counter("user_ d_def ned")
      pr vate val user dNotDef nedCounter = scopedStats.counter("user_ d_undef ned")
      pr vate val guest dDef nedCounter = scopedStats.counter("guest_ d_def ned")
      pr vate val guest dNotDef nedCounter = scopedStats.counter("guest_ d_undef ned")
      pr vate val no dCounter = scopedStats.counter("no_ d_def ned")

      def apply(requestContext: BaseRequestContext): Opt on[Rec p ent] = requestContext match {
        case c: W hUser d  f c.user d. sDef ned =>
          user dDef nedCounter. ncr()
          c.user d.map(S mpleRec p ent)
        case c: W hGuest d  f c.guest d. sDef ned =>
          guest dDef nedCounter. ncr()
          c.guest d.map(GuestRec p ent)
        case c: W hGuest d =>
          guest dNotDef nedCounter. ncr()
          Rec p entBu lder.Request(c)
        case _: W hUser d =>
          user dNotDef nedCounter. ncr()
          None
        case _ =>
          logger.warn ng("Request Context w h no user or guest  d tra  found: " + requestContext)
          no dCounter. ncr()
          None
      }
    }

    val boolOverr des = BoolToDec derMap.map {
      case (param, dec derKey) =>
        param.opt onalOverr deValue(
          Dec derSw chOverr deValue(
            feature = dec derGateBu lder.keyToFeature(dec derKey),
            enabledValue = true,
            d sabledValueOpt on = So (false),
            rec p entBu lder = UserOrGuestOrRequest
          )
        )
    }.toSeq

    val safetyLevelOverr de = SafetyLevel.enabledParam.opt onalOverr deValue(
      Dec derSw chOverr deValue(
        feature = dec derGateBu lder.keyToFeature(SafetyLevelToDec derMap(SafetyLevel)),
        enabledValue = true,
        rec p entBu lder = UserOrGuestOrRequest
      )
    )

    BaseConf gBu lder(boolOverr des :+ safetyLevelOverr de).bu ld("V s b l yDec ders")
  }
}
