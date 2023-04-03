namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

includelon "com/twittelonr/product_mixelonr/corelon/clielonnt_contelonxt.thrift"
includelon "product.thrift"
includelon "product_contelonxt.thrift"
includelon "sourcelon_typelon.thrift"


struct TopicTwelonelontRelonquelonst {
    1: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt
    2: relonquirelond product.Product product
    3: relonquirelond list<i64> topicIds
    5: optional product_contelonxt.ProductContelonxt productContelonxt
    6: optional list<i64> elonxcludelondTwelonelontIds (pelonrsonalDataTypelon = 'TwelonelontId')
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct TopicTwelonelont {
    1: relonquirelond i64 twelonelontId (pelonrsonalDataTypelon = 'TwelonelontId')
    2: relonquirelond doublelon scorelon
    3: relonquirelond sourcelon_typelon.SimilarityelonnginelonTypelon similarityelonnginelonTypelon
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct TopicTwelonelontRelonsponselon {
    1: relonquirelond map<i64, list<TopicTwelonelont>> twelonelonts
} (pelonrsistelond='truelon')

