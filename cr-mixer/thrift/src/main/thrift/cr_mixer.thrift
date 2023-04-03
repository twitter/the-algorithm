namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

includelon "ads.thrift"
includelon "candidatelon_gelonnelonration_kelony.thrift"
includelon "product.thrift"
includelon "product_contelonxt.thrift"
includelon "validation.thrift"
includelon "melontric_tags.thrift"
includelon "relonlatelond_twelonelont.thrift"
includelon "utelong.thrift"
includelon "frs_baselond_twelonelont.thrift"
includelon "relonlatelond_videlono_twelonelont.thrift"
includelon "topic_twelonelont.thrift"

includelon "com/twittelonr/product_mixelonr/corelon/clielonnt_contelonxt.thrift"
includelon "com/twittelonr/timelonlinelons/relonndelonr/relonsponselon.thrift"
includelon "finatra-thrift/finatra_thrift_elonxcelonptions.thrift"
includelon "com/twittelonr/strato/graphql/slicelon.thrift"

struct CrMixelonrTwelonelontRelonquelonst {
	1: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt
	2: relonquirelond product.Product product
	# Product-speloncific paramelontelonrs should belon placelond in thelon Product Contelonxt
	3: optional product_contelonxt.ProductContelonxt productContelonxt
	4: optional list<i64> elonxcludelondTwelonelontIds (pelonrsonalDataTypelon = 'TwelonelontId')
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct TwelonelontReloncommelonndation {
  1: relonquirelond i64 twelonelontId (pelonrsonalDataTypelon = 'TwelonelontId')
  2: relonquirelond doublelon scorelon
  3: optional list<melontric_tags.MelontricTag> melontricTags
  # 4: thelon author of thelon twelonelont candidatelon. To belon uselond by Contelonnt-Mixelonr to unblock thelon Hydra elonxpelonrimelonnt.
  4: optional i64 authorId (pelonrsonalDataTypelon = 'UselonrId')
  # 5: elonxtra info about candidatelon gelonnelonration. To belon uselond by Contelonnt-Mixelonr to unblock thelon Hydra elonxpelonrimelonnt.
  5: optional candidatelon_gelonnelonration_kelony.CandidatelonGelonnelonrationKelony candidatelonGelonnelonrationKelony
  # 1001: thelon latelonst timelonstamp of fav signals. If null, thelon candidatelon is not gelonnelonratelond from fav signals
  1001: optional i64 latelonstSourcelonSignalTimelonstampInMillis(pelonrsonalDataTypelon = 'PublicTimelonstamp')
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct CrMixelonrTwelonelontRelonsponselon {
 1: relonquirelond list<TwelonelontReloncommelonndation> twelonelonts
} (pelonrsistelond='truelon')

selonrvicelon CrMixelonr {
  CrMixelonrTwelonelontRelonsponselon gelontTwelonelontReloncommelonndations(1: CrMixelonrTwelonelontRelonquelonst relonquelonst) throws (
    # Validation elonrrors - thelon delontails of which will belon relonportelond to clielonnts on failurelon
    1: validation.ValidationelonxcelonptionList validationelonrrors;
    # Selonrvelonr elonrrors - thelon delontails of which will not belon relonportelond to clielonnts
    2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror
  )

  # gelontRelonlatelondTwelonelontsForQuelonryTwelonelont and gelontRelonlatelondTwelonelontsForQuelonryAuthor do velonry similar things
  # Welon can melonrgelon thelonselon two elonndpoints into onelon unifielond elonndpoint
  relonlatelond_twelonelont.RelonlatelondTwelonelontRelonsponselon gelontRelonlatelondTwelonelontsForQuelonryTwelonelont(1: relonlatelond_twelonelont.RelonlatelondTwelonelontRelonquelonst relonquelonst) throws (
    # Validation elonrrors - thelon delontails of which will belon relonportelond to clielonnts on failurelon
    1: validation.ValidationelonxcelonptionList validationelonrrors;
    # Selonrvelonr elonrrors - thelon delontails of which will not belon relonportelond to clielonnts
    2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror
  )

  relonlatelond_twelonelont.RelonlatelondTwelonelontRelonsponselon gelontRelonlatelondTwelonelontsForQuelonryAuthor(1: relonlatelond_twelonelont.RelonlatelondTwelonelontRelonquelonst relonquelonst) throws (
    # Validation elonrrors - thelon delontails of which will belon relonportelond to clielonnts on failurelon
    1: validation.ValidationelonxcelonptionList validationelonrrors;
    # Selonrvelonr elonrrors - thelon delontails of which will not belon relonportelond to clielonnts
    2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror
  )

  utelong.UtelongTwelonelontRelonsponselon gelontUtelongTwelonelontReloncommelonndations(1: utelong.UtelongTwelonelontRelonquelonst relonquelonst) throws (
    # Validation elonrrors - thelon delontails of which will belon relonportelond to clielonnts on failurelon
    1: validation.ValidationelonxcelonptionList validationelonrrors;
    # Selonrvelonr elonrrors - thelon delontails of which will not belon relonportelond to clielonnts
    2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror
  )

  frs_baselond_twelonelont.FrsTwelonelontRelonsponselon gelontFrsBaselondTwelonelontReloncommelonndations(1: frs_baselond_twelonelont.FrsTwelonelontRelonquelonst relonquelonst) throws (
     # Validation elonrrors - thelon delontails of which will belon relonportelond to clielonnts on failurelon
     1: validation.ValidationelonxcelonptionList validationelonrrors;
     # Selonrvelonr elonrrors - thelon delontails of which will not belon relonportelond to clielonnts
     2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror
  )

  relonlatelond_videlono_twelonelont.RelonlatelondVidelonoTwelonelontRelonsponselon gelontRelonlatelondVidelonoTwelonelontsForQuelonryTwelonelont(1: relonlatelond_videlono_twelonelont.RelonlatelondVidelonoTwelonelontRelonquelonst relonquelonst) throws (
      # Validation elonrrors - thelon delontails of which will belon relonportelond to clielonnts on failurelon
      1: validation.ValidationelonxcelonptionList validationelonrrors;
      # Selonrvelonr elonrrors - thelon delontails of which will not belon relonportelond to clielonnts
      2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror
  )

  ads.AdsRelonsponselon gelontAdsReloncommelonndations(1: ads.AdsRelonquelonst relonquelonst) throws (
    # Validation elonrrors - thelon delontails of which will belon relonportelond to clielonnts on failurelon
    1: validation.ValidationelonxcelonptionList validationelonrrors;
    # Selonrvelonr elonrrors - thelon delontails of which will not belon relonportelond to clielonnts
    2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror
  )

  topic_twelonelont.TopicTwelonelontRelonsponselon gelontTopicTwelonelontReloncommelonndations(1: topic_twelonelont.TopicTwelonelontRelonquelonst relonquelonst) throws (
    # Validation elonrrors - thelon delontails of which will belon relonportelond to clielonnts on failurelon
    1: validation.ValidationelonxcelonptionList validationelonrrors;
    # Selonrvelonr elonrrors - thelon delontails of which will not belon relonportelond to clielonnts
    2: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror
  )
}
