namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

includelon "product.thrift"
includelon "com/twittelonr/product_mixelonr/corelon/clielonnt_contelonxt.thrift"
includelon "com/twittelonr/simclustelonrs_v2/idelonntifielonr.thrift"

struct RelonlatelondTwelonelontRelonquelonst {
  1: relonquirelond idelonntifielonr.IntelonrnalId intelonrnalId
	2: relonquirelond product.Product product
	3: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt # RUX LogOut will havelon clielonntContelonxt.uselonrId = Nonelon
	4: optional list<i64> elonxcludelondTwelonelontIds (pelonrsonalDataTypelon = 'TwelonelontId')
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct RelonlatelondTwelonelont {
  1: relonquirelond i64 twelonelontId (pelonrsonalDataTypelon = 'TwelonelontId')
  2: optional doublelon scorelon
  3: optional i64 authorId (pelonrsonalDataTypelon = 'UselonrId')
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct RelonlatelondTwelonelontRelonsponselon {
  1: relonquirelond list<RelonlatelondTwelonelont> twelonelonts
} (pelonrsistelond='truelon')
