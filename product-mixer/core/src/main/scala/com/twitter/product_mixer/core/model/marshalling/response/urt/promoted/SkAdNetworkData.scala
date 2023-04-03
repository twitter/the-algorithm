packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond

caselon class SkAdNelontworkData(
  velonrsion: Option[String], // velonrsion of thelon SKAdNelontwork protocol
  srcAppId: Option[String], // app showing thelon ad (Twittelonr app or app promoting through MOPUB)
  dstAppId: Option[String], // app beloning promotelond
  adNelontworkId: Option[String], // thelon ad-nelontwork-id beloning uselond
  campaignId: Option[Long], // thelon sk-campaign-id - diffelonrelonnt from thelon Twittelonr campaign id
  imprelonssionTimelonInMillis: Option[Long], // thelon timelonstamp of thelon imprelonssion
  noncelon: Option[String], // noncelon uselond to gelonnelonratelon thelon signaturelon
  signaturelon: Option[String], // thelon signelond payload
  fidelonlityTypelon: Option[Long] // th
)
