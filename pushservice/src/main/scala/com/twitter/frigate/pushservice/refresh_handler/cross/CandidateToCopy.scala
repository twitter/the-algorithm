package com.tw ter.fr gate.pushserv ce.refresh_handler.cross

 mport com.tw ter.fr gate.common.ut l.MrNtabCopyObjects
 mport com.tw ter.fr gate.common.ut l.MrPushCopyObjects
 mport com.tw ter.fr gate.common.ut l._
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType._

object Cand dateToCopy {

  // Stat c map from a CommonRecom ndat onType to set of el g ble push not f cat on cop es
  pr vate[cross] val rectypeToPushCopy: Map[CommonRecom ndat onType, Set[
    MRPushCopy
  ]] =
    Map[CommonRecom ndat onType, Set[MRPushCopy]](
      F1F rstdegreeT et -> Set(
        MrPushCopyObjects.F rstDegreeJustT etedBoldT le
      ),
      F1F rstdegreePhoto -> Set(
        MrPushCopyObjects.F rstDegreePhotoJustT etedBoldT le
      ),
      F1F rstdegreeV deo -> Set(
        MrPushCopyObjects.F rstDegreeV deoJustT etedBoldT le
      ),
      T etRet et -> Set(
        MrPushCopyObjects.T etRet etW hOneD splaySoc alContextsW hText,
        MrPushCopyObjects.T etRet etW hTwoD splaySoc alContextsW hText,
        MrPushCopyObjects.T etRet etW hOneD splayAndKOt rSoc alContextsW hText
      ),
      T etRet etPhoto -> Set(
        MrPushCopyObjects.T etRet etPhotoW hOneD splaySoc alContextW hText,
        MrPushCopyObjects.T etRet etPhotoW hTwoD splaySoc alContextsW hText,
        MrPushCopyObjects.T etRet etPhotoW hOneD splayAndKOt rSoc alContextsW hText
      ),
      T etRet etV deo -> Set(
        MrPushCopyObjects.T etRet etV deoW hOneD splaySoc alContextW hText,
        MrPushCopyObjects.T etRet etV deoW hTwoD splaySoc alContextsW hText,
        MrPushCopyObjects.T etRet etV deoW hOneD splayAndKOt rSoc alContextsW hText
      ),
      T etFavor e -> Set(
        MrPushCopyObjects.T etL keOneSoc alContextW hText,
        MrPushCopyObjects.T etL keTwoSoc alContextW hText,
        MrPushCopyObjects.T etL keMult pleSoc alContextW hText
      ),
      T etFavor ePhoto -> Set(
        MrPushCopyObjects.T etL kePhotoOneSoc alContextW hText,
        MrPushCopyObjects.T etL kePhotoTwoSoc alContextW hText,
        MrPushCopyObjects.T etL kePhotoMult pleSoc alContextW hText
      ),
      T etFavor eV deo -> Set(
        MrPushCopyObjects.T etL keV deoOneSoc alContextW hText,
        MrPushCopyObjects.T etL keV deoTwoSoc alContextW hText,
        MrPushCopyObjects.T etL keV deoMult pleSoc alContextW hText
      ),
      UnreadBadgeCount -> Set(MrPushCopyObjects.UnreadBadgeCount),
       nterestBasedT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
       nterestBasedPhoto -> Set(MrPushCopyObjects.Recom ndedFor Photo),
       nterestBasedV deo -> Set(MrPushCopyObjects.Recom ndedFor V deo),
      UserFollow -> Set(
        MrPushCopyObjects.UserFollowW hOneSoc alContext,
        MrPushCopyObjects.UserFollowW hTwoSoc alContext,
        MrPushCopyObjects.UserFollowOneD splayAndKOt rSoc alContext
      ),
       rm User -> Set(
        MrPushCopyObjects. rm UserW hOneSoc alContext,
        MrPushCopyObjects. rm UserW hTwoSoc alContext,
        MrPushCopyObjects. rm UserW hOneD splayAndKOt rSoc alContexts
      ),
      Tr angularLoopUser -> Set(
        MrPushCopyObjects.Tr angularLoopUserW hOneSoc alContext,
        MrPushCopyObjects.Tr angularLoopUserW hTwoSoc alContexts,
        MrPushCopyObjects.Tr angularLoopUserOneD splayAndKot rSoc alContext
      ),
      ForwardAddressbookUserFollow -> Set(MrPushCopyObjects.ForwardAddressBookUserFollow),
      NewsArt cleNewsLand ng -> Set(MrPushCopyObjects.NewsArt cleNewsLand ngCopy),
      Top cProofT et -> Set(MrPushCopyObjects.Top cProofT et),
      User nterest nT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      User nterest nPhoto -> Set(MrPushCopyObjects.Recom ndedFor Photo),
      User nterest nV deo -> Set(MrPushCopyObjects.Recom ndedFor V deo),
      Tw stlyT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      Tw stlyPhoto -> Set(MrPushCopyObjects.Recom ndedFor Photo),
      Tw stlyV deo -> Set(MrPushCopyObjects.Recom ndedFor V deo),
      Elast cT  l neT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      Elast cT  l nePhoto -> Set(MrPushCopyObjects.Recom ndedFor Photo),
      Elast cT  l neV deo -> Set(MrPushCopyObjects.Recom ndedFor V deo),
      ExploreV deoT et -> Set(MrPushCopyObjects.ExploreV deoT et),
      L st -> Set(MrPushCopyObjects.L stRecom ndat on),
       nterestBasedUserFollow -> Set(MrPushCopyObjects.UserFollow nterestBasedCopy),
      PastEma lEngage ntT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      PastEma lEngage ntPhoto -> Set(MrPushCopyObjects.Recom ndedFor Photo),
      PastEma lEngage ntV deo -> Set(MrPushCopyObjects.Recom ndedFor V deo),
      ExplorePush -> Set(MrPushCopyObjects.ExplorePush),
      ConnectTabPush -> Set(MrPushCopyObjects.ConnectTabPush),
      ConnectTabW hUserPush -> Set(MrPushCopyObjects.ConnectTabW hUserPush),
      AddressBookUploadPush -> Set(MrPushCopyObjects.AddressBookPush),
       nterestP ckerPush -> Set(MrPushCopyObjects. nterestP ckerPush),
      CompleteOnboard ngPush -> Set(MrPushCopyObjects.CompleteOnboard ngPush),
      GeoPopT et -> Set(MrPushCopyObjects.GeoPopPushCopy),
      TagSpaceT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      FrsT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      Twh nT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      MrModel ngBasedT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      Detop cT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      T et mpress ons -> Set(MrPushCopyObjects.TopT et mpress ons),
      TrendT et -> Set(MrPushCopyObjects.TrendT et),
      ReverseAddressbookT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      ForwardAddressbookT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      Space nNetwork -> Set(MrPushCopyObjects.SpaceHost),
      SpaceOutOfNetwork -> Set(MrPushCopyObjects.SpaceHost),
      Subscr bedSearch -> Set(MrPushCopyObjects.Subscr bedSearchT et),
      Tr pGeoT et -> Set(MrPushCopyObjects.Tr pGeoT etPushCopy),
      CrowdSearchT et -> Set(MrPushCopyObjects.Recom ndedFor T et),
      D gest -> Set(MrPushCopyObjects.D gest),
      Tr pHqT et -> Set(MrPushCopyObjects.Tr pHqT etPushCopy)
    )

  // Stat c map from a push copy to set of el g ble ntab cop es
  pr vate[cross] val pushcopyToNtabcopy: Map[MRPushCopy, Set[MRNtabCopy]] =
    Map[MRPushCopy, Set[MRNtabCopy]](
      MrPushCopyObjects.F rstDegreeJustT etedBoldT le -> Set(
        MrNtabCopyObjects.F rstDegreeT etRecent),
      MrPushCopyObjects.F rstDegreePhotoJustT etedBoldT le -> Set(
        MrNtabCopyObjects.F rstDegreeT etRecent
      ),
      MrPushCopyObjects.F rstDegreeV deoJustT etedBoldT le -> Set(
        MrNtabCopyObjects.F rstDegreeT etRecent
      ),
      MrPushCopyObjects.T etRet etW hOneD splaySoc alContextsW hText -> Set(
        MrNtabCopyObjects.T etRet etW hOneD splaySoc alContext
      ),
      MrPushCopyObjects.T etRet etW hTwoD splaySoc alContextsW hText -> Set(
        MrNtabCopyObjects.T etRet etW hTwoD splaySoc alContexts
      ),
      MrPushCopyObjects.T etRet etW hOneD splayAndKOt rSoc alContextsW hText -> Set(
        MrNtabCopyObjects.T etRet etW hOneD splayAndKOt rSoc alContexts
      ),
      MrPushCopyObjects.T etRet etPhotoW hOneD splaySoc alContextW hText -> Set(
        MrNtabCopyObjects.T etRet etPhotoW hOneD splaySoc alContext
      ),
      MrPushCopyObjects.T etRet etPhotoW hTwoD splaySoc alContextsW hText -> Set(
        MrNtabCopyObjects.T etRet etPhotoW hTwoD splaySoc alContexts
      ),
      MrPushCopyObjects.T etRet etPhotoW hOneD splayAndKOt rSoc alContextsW hText -> Set(
        MrNtabCopyObjects.T etRet etPhotoW hOneD splayAndKOt rSoc alContexts
      ),
      MrPushCopyObjects.T etRet etV deoW hOneD splaySoc alContextW hText -> Set(
        MrNtabCopyObjects.T etRet etV deoW hOneD splaySoc alContext
      ),
      MrPushCopyObjects.T etRet etV deoW hTwoD splaySoc alContextsW hText -> Set(
        MrNtabCopyObjects.T etRet etV deoW hTwoD splaySoc alContexts
      ),
      MrPushCopyObjects.T etRet etV deoW hOneD splayAndKOt rSoc alContextsW hText -> Set(
        MrNtabCopyObjects.T etRet etV deoW hOneD splayAndKOt rSoc alContexts
      ),
      MrPushCopyObjects.T etL keOneSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL keW hOneD splaySoc alContext
      ),
      MrPushCopyObjects.T etL keTwoSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL keW hTwoD splaySoc alContexts
      ),
      MrPushCopyObjects.T etL keMult pleSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL keW hOneD splayAndKOt rSoc alContexts
      ),
      MrPushCopyObjects.T etL kePhotoOneSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL kePhotoW hOneD splaySoc alContext
      ),
      MrPushCopyObjects.T etL kePhotoTwoSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL kePhotoW hTwoD splaySoc alContexts
      ),
      MrPushCopyObjects.T etL kePhotoMult pleSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL kePhotoW hOneD splayAndKOt rSoc alContexts
      ),
      MrPushCopyObjects.T etL keV deoOneSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL keV deoW hOneD splaySoc alContext
      ),
      MrPushCopyObjects.T etL keV deoTwoSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL keV deoW hTwoD splaySoc alContexts
      ),
      MrPushCopyObjects.T etL keV deoMult pleSoc alContextW hText -> Set(
        MrNtabCopyObjects.T etL keV deoW hOneD splayAndKOt rSoc alContexts
      ),
      MrPushCopyObjects.UnreadBadgeCount -> Set.empty[MRNtabCopy],
      MrPushCopyObjects.Recom ndedFor T et -> Set(MrNtabCopyObjects.Recom ndedFor Copy),
      MrPushCopyObjects.Recom ndedFor Photo -> Set(MrNtabCopyObjects.Recom ndedFor Copy),
      MrPushCopyObjects.Recom ndedFor V deo -> Set(MrNtabCopyObjects.Recom ndedFor Copy),
      MrPushCopyObjects.GeoPopPushCopy -> Set(MrNtabCopyObjects.Recom ndedFor Copy),
      MrPushCopyObjects.UserFollowW hOneSoc alContext -> Set(
        MrNtabCopyObjects.UserFollowW hOneD splaySoc alContext
      ),
      MrPushCopyObjects.UserFollowW hTwoSoc alContext -> Set(
        MrNtabCopyObjects.UserFollowW hTwoD splaySoc alContexts
      ),
      MrPushCopyObjects.UserFollowOneD splayAndKOt rSoc alContext -> Set(
        MrNtabCopyObjects.UserFollowW hOneD splayAndKOt rSoc alContexts
      ),
      MrPushCopyObjects. rm UserW hOneSoc alContext -> Set(
        MrNtabCopyObjects.UserFollowW hOneD splaySoc alContext
      ),
      MrPushCopyObjects. rm UserW hTwoSoc alContext -> Set(
        MrNtabCopyObjects.UserFollowW hTwoD splaySoc alContexts
      ),
      MrPushCopyObjects. rm UserW hOneD splayAndKOt rSoc alContexts -> Set(
        MrNtabCopyObjects.UserFollowW hOneD splayAndKOt rSoc alContexts
      ),
      MrPushCopyObjects.Tr angularLoopUserW hOneSoc alContext -> Set(
        MrNtabCopyObjects.Tr angularLoopUserW hOneSoc alContext
      ),
      MrPushCopyObjects.Tr angularLoopUserW hTwoSoc alContexts -> Set(
        MrNtabCopyObjects.Tr angularLoopUserW hTwoSoc alContexts
      ),
      MrPushCopyObjects.Tr angularLoopUserOneD splayAndKot rSoc alContext -> Set(
        MrNtabCopyObjects.Tr angularLoopUserOneD splayAndKOt rSoc alContext
      ),
      MrPushCopyObjects.NewsArt cleNewsLand ngCopy -> Set(
        MrNtabCopyObjects.NewsArt cleNewsLand ngCopy
      ),
      MrPushCopyObjects.UserFollow nterestBasedCopy -> Set(
        MrNtabCopyObjects.UserFollow nterestBasedCopy
      ),
      MrPushCopyObjects.ForwardAddressBookUserFollow -> Set(
        MrNtabCopyObjects.ForwardAddressBookUserFollow),
      MrPushCopyObjects.ConnectTabPush -> Set(
        MrNtabCopyObjects.ConnectTabPush
      ),
      MrPushCopyObjects.ExplorePush -> Set.empty[MRNtabCopy],
      MrPushCopyObjects.ConnectTabW hUserPush -> Set(
        MrNtabCopyObjects.UserFollow nterestBasedCopy),
      MrPushCopyObjects.AddressBookPush -> Set(MrNtabCopyObjects.AddressBook),
      MrPushCopyObjects. nterestP ckerPush -> Set(MrNtabCopyObjects. nterestP cker),
      MrPushCopyObjects.CompleteOnboard ngPush -> Set(MrNtabCopyObjects.CompleteOnboard ng),
      MrPushCopyObjects.Top cProofT et -> Set(MrNtabCopyObjects.Top cProofT et),
      MrPushCopyObjects.TopT et mpress ons -> Set(MrNtabCopyObjects.TopT et mpress ons),
      MrPushCopyObjects.TrendT et -> Set(MrNtabCopyObjects.TrendT et),
      MrPushCopyObjects.SpaceHost -> Set(MrNtabCopyObjects.SpaceHost),
      MrPushCopyObjects.Subscr bedSearchT et -> Set(MrNtabCopyObjects.Subscr bedSearchT et),
      MrPushCopyObjects.Tr pGeoT etPushCopy -> Set(MrNtabCopyObjects.Recom ndedFor Copy),
      MrPushCopyObjects.D gest -> Set(MrNtabCopyObjects.D gest),
      MrPushCopyObjects.Tr pHqT etPushCopy -> Set(MrNtabCopyObjects.H ghQual yT et),
      MrPushCopyObjects.ExploreV deoT et -> Set(MrNtabCopyObjects.ExploreV deoT et),
      MrPushCopyObjects.L stRecom ndat on -> Set(MrNtabCopyObjects.L stRecom ndat on),
      MrPushCopyObjects.Mag cFanoutCreatorSubscr pt on -> Set(
        MrNtabCopyObjects.Mag cFanoutCreatorSubscr pt on),
      MrPushCopyObjects.Mag cFanoutNewCreator -> Set(MrNtabCopyObjects.Mag cFanoutNewCreator)
    )

  /**
   *
   * @param crt - [[CommonRecom ndat onType]] used for a fr gate push not f cat on
   *
   * @return - Set of [[MRPushCopy]] objects represent ng push cop es el g b le for a
   *         [[CommonRecom ndat onType]]
   */
  def getPushCop esFromRectype(crt: CommonRecom ndat onType): Opt on[Set[MRPushCopy]] =
    rectypeToPushCopy.get(crt)

  /**
   *
   * @param pushcopy - [[MRPushCopy]] object represent ng a push not f cat on copy
   * @return - Set of [[MRNtabCopy]] objects that can be pa red w h a g ven [[MRPushCopy]]
   */
  def getNtabcop esFromPushcopy(pushcopy: MRPushCopy): Opt on[Set[MRNtabCopy]] =
    pushcopyToNtabcopy.get(pushcopy)
}
