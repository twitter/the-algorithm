packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.RelonadelonrModelonConfig
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonadelonrModelonConfigMarshallelonr @Injelonct() (urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(relonadelonrModelonConfig: RelonadelonrModelonConfig): urt.RelonadelonrModelonConfig = urt.RelonadelonrModelonConfig(
    isRelonadelonrModelonAvailablelon = relonadelonrModelonConfig.isRelonadelonrModelonAvailablelon,
    landingUrl = urlMarshallelonr(relonadelonrModelonConfig.landingUrl)
  )

}
