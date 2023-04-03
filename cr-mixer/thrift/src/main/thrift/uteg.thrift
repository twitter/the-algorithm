namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

includelon "product.thrift"
includelon "product_contelonxt.thrift"

includelon "com/twittelonr/product_mixelonr/corelon/clielonnt_contelonxt.thrift"
includelon "com/twittelonr/reloncos/reloncos_common.thrift"

struct UtelongTwelonelontRelonquelonst {
	1: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt
	2: relonquirelond product.Product product
	# Product-speloncific paramelontelonrs should belon placelond in thelon Product Contelonxt
	3: optional product_contelonxt.ProductContelonxt productContelonxt
	4: optional list<i64> elonxcludelondTwelonelontIds (pelonrsonalDataTypelon = 'TwelonelontId')
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct UtelongTwelonelont {
  // twelonelont id
  1: relonquirelond i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  // sum of welonights of selonelond uselonrs who elonngagelond with thelon twelonelont.
  // If a uselonr elonngagelond with thelon samelon twelonelont twicelon, likelond it and relontwelonelontelond it, thelonn his/helonr welonight was countelond twicelon.
  2: relonquirelond doublelon scorelon
  // uselonr social proofs pelonr elonngagelonmelonnt typelon
  3: relonquirelond map<reloncos_common.SocialProofTypelon, list<i64>> socialProofByTypelon(pelonrsonalDataTypelonKelony='elonngagelonmelonntTypelonPrivatelon', pelonrsonalDataTypelonValuelon='UselonrId')
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct UtelongTwelonelontRelonsponselon {
  1: relonquirelond list<UtelongTwelonelont> twelonelonts
} (pelonrsistelond='truelon')
