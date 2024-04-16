package com.tw ter.ho _m xer.product.follow ng.model

 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.ExternalStr ngReg stry
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton

@S ngleton
class Ho M xerExternalStr ngs @ nject() (
  @ProductScoped externalStr ngReg stryProv der: Prov der[ExternalStr ngReg stry]) {
  val seeNewT etsStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("SeeNewT ets")
  val t etedStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("T eted")
  val muteUserStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.muteUser")
  val blockUserStr ng = externalStr ngReg stryProv der.get().createProdStr ng("Feedback.blockUser")
  val unfollowUserStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.unfollowUser")
  val unfollowUserConf rmat onStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.unfollowUserConf rmat on")
  val reportT etStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.reportT et")
  val dontL keStr ng = externalStr ngReg stryProv der.get().createProdStr ng("Feedback.dontL ke")
  val dontL keConf rmat onStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.dontL keConf rmat on")
  val showFe rT etsStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.showFe rT ets")
  val showFe rT etsConf rmat onStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.showFe rT etsConf rmat on")
  val showFe rRet etsStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.showFe rRet ets")
  val showFe rRet etsConf rmat onStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.showFe rRet etsConf rmat on")
  val notRelevantStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.notRelevant")
  val notRelevantConf rmat onStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Feedback.notRelevantConf rmat on")

  val soc alContextOneUserL kedStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.oneUserL ked")
  val soc alContextTwoUsersL kedStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.twoUsersL ked")
  val soc alContextMoreUsersL kedStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.moreUsersL ked")
  val soc alContextL kedByT  l neT le =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.l kedByT  l neT le")

  val soc alContextOneUserFollowsStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.oneUserFollows")
  val soc alContextTwoUsersFollowStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.twoUsersFollow")
  val soc alContextMoreUsersFollowStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.moreUsersFollow")
  val soc alContextFollo dByT  l neT le =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.follo dByT  l neT le")

  val soc alContext M ghtL keStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext. M ghtL ke")

  val soc alContextExtendedReply =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.extendedReply")
  val soc alContextRece vedReply =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.rece vedReply")

  val soc alContextPopularV deoStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("Soc alContext.popularV deo")

  val soc alContextPopular nY AreaStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("PopgeoT et.soc alProof")

  val l stToFollowModule aderStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("L stToFollowModule. ader")
  val l stToFollowModuleFooterStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("L stToFollowModule.footer")
  val p nnedL stsModule aderStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("P nnedL stModule. ader")
  val p nnedL stsModuleEmptyState ssageStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("P nnedL stModule.emptyState ssage")

  val ownedSubscr bedL stsModule aderStr ng =
    externalStr ngReg stryProv der.get().createProdStr ng("OwnedSubscr bedL stModule. ader")
  val ownedSubscr bedL stsModuleEmptyState ssageStr ng =
    externalStr ngReg stryProv der
      .get().createProdStr ng("OwnedSubscr bedL stModule.emptyState ssage")
}
