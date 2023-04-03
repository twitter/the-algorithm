packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.SkAdNelontworkData
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class SkAdNelontworkDataMarshallelonr @Injelonct() () {

  delonf apply(skAdNelontworkData: SkAdNelontworkData): urt.SkAdNelontworkData =
    urt.SkAdNelontworkData(
      velonrsion = skAdNelontworkData.velonrsion,
      srcAppId = skAdNelontworkData.srcAppId,
      dstAppId = skAdNelontworkData.dstAppId,
      adNelontworkId = skAdNelontworkData.adNelontworkId,
      campaignId = skAdNelontworkData.campaignId,
      imprelonssionTimelonInMillis = skAdNelontworkData.imprelonssionTimelonInMillis,
      noncelon = skAdNelontworkData.noncelon,
      signaturelon = skAdNelontworkData.signaturelon,
      fidelonlityTypelon = skAdNelontworkData.fidelonlityTypelon
    )
}
