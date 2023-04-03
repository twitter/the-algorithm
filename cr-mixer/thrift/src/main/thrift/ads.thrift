namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

includelon "product.thrift"
includelon "product_contelonxt.thrift"

includelon "com/twittelonr/product_mixelonr/corelon/clielonnt_contelonxt.thrift"
includelon "com/twittelonr/ads/schelonma/sharelond.thrift"

struct AdsRelonquelonst {
	1: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt
	2: relonquirelond product.Product product
	# Product-speloncific paramelontelonrs should belon placelond in thelon Product Contelonxt
	3: optional product_contelonxt.ProductContelonxt productContelonxt
	4: optional list<i64> elonxcludelondTwelonelontIds (pelonrsonalDataTypelon = 'TwelonelontId')
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct AdsRelonsponselon {
  1: relonquirelond list<AdTwelonelontReloncommelonndation> ads
} (pelonrsistelond='truelon')

struct AdTwelonelontReloncommelonndation {
  1: relonquirelond i64 twelonelontId (pelonrsonalDataTypelon = 'TwelonelontId')
  2: relonquirelond doublelon scorelon
  3: optional list<LinelonItelonmInfo> linelonItelonms

} (pelonrsistelond='truelon')

struct LinelonItelonmInfo {
  1: relonquirelond i64 linelonItelonmId (pelonrsonalDataTypelon = 'LinelonItelonmId')
  2: relonquirelond sharelond.LinelonItelonmObjelonctivelon linelonItelonmObjelonctivelon
} (pelonrsistelond='truelon')
