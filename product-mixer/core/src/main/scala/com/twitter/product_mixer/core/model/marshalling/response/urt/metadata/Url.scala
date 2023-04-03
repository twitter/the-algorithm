packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RelonfelonrelonncelonObjelonct

selonalelond trait UrlTypelon
caselon objelonct elonxtelonrnalUrl elonxtelonnds UrlTypelon
caselon objelonct DelonelonpLink elonxtelonnds UrlTypelon
caselon objelonct Urtelonndpoint elonxtelonnds UrlTypelon

caselon class UrtelonndpointOptions(
  relonquelonstParams: Option[Map[String, String]],
  titlelon: Option[String],
  cachelonId: Option[String],
  subtitlelon: Option[String])

caselon class Url(urlTypelon: UrlTypelon, url: String, urtelonndpointOptions: Option[UrtelonndpointOptions] = Nonelon)
    elonxtelonnds RelonfelonrelonncelonObjelonct
