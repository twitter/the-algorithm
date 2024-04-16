package com.tw ter.v s b l y.features

 mport com.tw ter.content alth.tox creplyf lter.thr ftscala.F lterState
 mport com.tw ter.g zmoduck.thr ftscala.Label
 mport com.tw ter.search.common.constants.thr ftscala.Thr ftQueryS ce
 mport com.tw ter.tseng.w hhold ng.thr ftscala.TakedownReason
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport com.tw ter.v s b l y.models.T etDeleteReason.T etDeleteReason
 mport com.tw ter.v s b l y.models._

case object Author d extends Feature[Set[Long]]

case object V e r d extends Feature[Long]

case object Author sProtected extends Feature[Boolean]

case object Author sSuspended extends Feature[Boolean]

case object Author sUnava lable extends Feature[Boolean]

case object Author sDeact vated extends Feature[Boolean]

case object Author sErased extends Feature[Boolean]

case object Author sOffboarded extends Feature[Boolean]

case object Author sVer f ed extends Feature[Boolean]

case object Author sBlueVer f ed extends Feature[Boolean]

case object V e r sSuspended extends Feature[Boolean]

case object V e r sDeact vated extends Feature[Boolean]

case object AuthorFollowsV e r extends Feature[Boolean]

case object AuthorUserLabels extends Feature[Seq[Label]]

case object V e rFollowsAuthorOfV olat ngT et extends Feature[Boolean]

case object V e rDoesNotFollowAuthorOfV olat ngT et extends Feature[Boolean]

case object V e rFollowsAuthor extends Feature[Boolean]

case object V e rBlocksAuthor extends Feature[Boolean]

case object AuthorBlocksV e r extends Feature[Boolean]

case object AuthorMutesV e r extends Feature[Boolean]

case object V e rMutesAuthor extends Feature[Boolean]

case object AuthorReportsV e rAsSpam extends Feature[Boolean]

case object V e rReportsAuthorAsSpam extends Feature[Boolean]

case object V e rReportedT et extends Feature[Boolean]

case object V e rMutesRet etsFromAuthor extends Feature[Boolean]

case object V e rHasUn versalQual yF lterEnabled extends Feature[Boolean]

case object V e r sProtected extends Feature[Boolean]

case object V e r sSoftUser extends Feature[Boolean]

case object T etSafetyLabels extends Feature[Seq[T etSafetyLabel]]

case object SpaceSafetyLabels extends Feature[Seq[SpaceSafetyLabel]]

case object  d aSafetyLabels extends Feature[Seq[ d aSafetyLabel]]

case object T etTakedownReasons extends Feature[Seq[TakedownReason]]

case object AuthorTakedownReasons extends Feature[Seq[TakedownReason]]

case object Author sNsfwUser extends Feature[Boolean]

case object Author sNsfwAdm n extends Feature[Boolean]

case object T etHasNsfwUser extends Feature[Boolean]

case object T etHasNsfwAdm n extends Feature[Boolean]

case object T etHas d a extends Feature[Boolean]

case object CardHas d a extends Feature[Boolean]

case object T etHasCard extends Feature[Boolean]

case object V e rMutesKeyword nT etForHo T  l ne extends Feature[MutedKeyword]

case object V e rMutesKeyword nT etForT etRepl es extends Feature[MutedKeyword]

case object V e rMutesKeyword nT etForNot f cat ons extends Feature[MutedKeyword]

case object V e rMutesKeyword nSpaceT leForNot f cat ons extends Feature[MutedKeyword]

case object V e rMutesKeyword nT etForAllSurfaces extends Feature[MutedKeyword]

case object V e rUserLabels extends Feature[Seq[Label]]

case object RequestCountryCode extends Feature[Str ng]

case object Request sVer f edCrawler extends Feature[Boolean]

case object V e rCountryCode extends Feature[Str ng]

case object T et sSelfReply extends Feature[Boolean]

case object T et sNullcast extends Feature[Boolean]

case object T etT  stamp extends Feature[T  ]

case object T et s nnerQuotedT et extends Feature[Boolean]

case object T et sRet et extends Feature[Boolean]

case object T et sS ceT et extends Feature[Boolean]

case object T etDeleteReason extends Feature[T etDeleteReason]

case object T etReplyToParentT etDurat on extends Feature[Durat on]

case object T etReplyToRootT etDurat on extends Feature[Durat on]

case object T etHasCommun yConversat onControl extends Feature[Boolean]
case object T etHasBy nv at onConversat onControl extends Feature[Boolean]
case object T etHasFollo rsConversat onControl extends Feature[Boolean]
case object T etConversat onV e r s nv ed extends Feature[Boolean]
case object T etConversat onV e r s nv edV aReply nt on extends Feature[Boolean]
case object T etConversat onV e r sRootAuthor extends Feature[Boolean]
case object Conversat onRootAuthorFollowsV e r extends Feature[Boolean]
case object V e rFollowsConversat onRootAuthor extends Feature[Boolean]

case object T et sExclus veT et extends Feature[Boolean]
case object V e r sExclus veT etRootAuthor extends Feature[Boolean]
case object V e rSuperFollowsExclus veT etRootAuthor extends Feature[Boolean]

case object T et sCommun yT et extends Feature[Boolean]

case object Commun yT etCommun yNotFound extends Feature[Boolean]

case object Commun yT etCommun yDeleted extends Feature[Boolean]

case object Commun yT etCommun ySuspended extends Feature[Boolean]

case object Commun yT etCommun yV s ble extends Feature[Boolean]

case object Commun yT et sH dden extends Feature[Boolean]

case object V e r s nternalCommun  esAdm n extends Feature[Boolean]

case object V e r sCommun yAdm n extends Feature[Boolean]

case object V e r sCommun yModerator extends Feature[Boolean]

case object V e r sCommun y mber extends Feature[Boolean]

case object Commun yT etAuthor sRemoved extends Feature[Boolean]

case object Not f cat on sOnCommun yT et extends Feature[Boolean]

case object Not f cat on sOnUn nt onedV e r extends Feature[Boolean]

case object SearchResultsPageNumber extends Feature[ nt]

case object SearchCand dateCount extends Feature[ nt]

case object SearchQueryS ce extends Feature[Thr ftQueryS ce]

case object SearchQueryHasUser extends Feature[Boolean]

case object T etSemant cCoreAnnotat ons extends Feature[Seq[Semant cCoreAnnotat on]]

case object OuterAuthor d extends Feature[Long]

case object AuthorBlocksOuterAuthor extends Feature[Boolean]

case object OuterAuthorFollowsAuthor extends Feature[Boolean]

case object OuterAuthor s nnerAuthor extends Feature[Boolean]

case object T et sModerated extends Feature[Boolean]
case object FocalT et d extends Feature[Long]

case object T et d extends Feature[Long]

case object T etConversat on d extends Feature[Long]
case object T etParent d extends Feature[Long]
case object Conversat onRootAuthor sVer f ed extends Feature[Boolean]

case object V e rOpt nBlock ng extends Feature[Boolean]

case object V e rOpt nF lter ng extends Feature[Boolean]

case object V e rRoles extends Feature[Seq[Str ng]] {
  val EmployeeRole = "employee"
}

case object T etM s nformat onPol c es extends Feature[Seq[M s nformat onPol cy]]

case object T etEngl shM s nformat onPol c es extends Feature[Seq[M s nformat onPol cy]]

case object Has nnerC rcleOfFr endsRelat onsh p extends Feature[Boolean]

case object V e rAge extends Feature[UserAge]

case object HasDmca d aFeature extends Feature[Boolean]

case object  d aGeoRestr ct onsAllowL st extends Feature[Seq[Str ng]]
case object  d aGeoRestr ct onsDenyL st extends Feature[Seq[Str ng]]

case object T et sTrustedFr endT et extends Feature[Boolean]
case object V e r sTrustedFr endT etAuthor extends Feature[Boolean]
case object V e r sTrustedFr endOfT etAuthor extends Feature[Boolean]

case object DmConversat on sOneToOneConversat on extends Feature[Boolean]
case object DmConversat onHasEmptyT  l ne extends Feature[Boolean]
case object DmConversat onHasVal dLastReadableEvent d extends Feature[Boolean]
case object DmConversat on nfoEx sts extends Feature[Boolean]
case object DmConversat onT  l neEx sts extends Feature[Boolean]
case object V e r sDmConversat onPart c pant extends Feature[Boolean]

case object DmEvent s ssageCreateEvent extends Feature[Boolean]
case object DmEvent s lco  ssageCreateEvent extends Feature[Boolean]
case object DmEvent sLast ssageReadUpdateEvent extends Feature[Boolean]
case object DmEvent sDeleted extends Feature[Boolean]
case object DmEvent sH dden extends Feature[Boolean]
case object V e r sDmEvent n  at ngUser extends Feature[Boolean]
case object DmEvent nOneToOneConversat onW hUnava lableUser extends Feature[Boolean]
case object DmEvent sJo nConversat onEvent extends Feature[Boolean]
case object DmEvent sConversat onCreateEvent extends Feature[Boolean]
case object DmEvent nOneToOneConversat on extends Feature[Boolean]
case object DmEvent sTrustConversat onEvent extends Feature[Boolean]
case object DmEvent sCsFeedbackSubm ted extends Feature[Boolean]
case object DmEvent sCsFeedbackD sm ssed extends Feature[Boolean]
case object DmEvent sPerspect valJo nConversat onEvent extends Feature[Boolean]

case object DmEventOccurredBeforeLastClearedEvent extends Feature[Boolean]
case object DmEventOccurredBeforeJo nConversat onEvent extends Feature[Boolean]

case object CardUr Host extends Feature[Str ng]
case object Card sPoll extends Feature[Boolean]

case object T et sStaleT et extends Feature[Boolean]

case object T et sEd T et extends Feature[Boolean]

case object T et sLatestT et extends Feature[Boolean]

case object T et s n  alT et extends Feature[Boolean]

case object T et sCollab nv at onT et extends Feature[Boolean]

case object V e rSens  ve d aSett ngs extends Feature[UserSens  ve d aSett ngs]


case object Tox cReplyF lterState extends Feature[F lterState]


case object Tox cReplyF lterConversat onAuthor sV e r extends Feature[Boolean]

case object RawQuery extends Feature[Str ng]

case object AuthorScreenNa  extends Feature[Str ng]

case object T et s nternalPromotedContent extends Feature[Boolean]
