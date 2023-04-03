namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

includelon "product.thrift"
includelon "product_contelonxt.thrift"
includelon "com/twittelonr/product_mixelonr/corelon/clielonnt_contelonxt.thrift"

struct FrsTwelonelontRelonquelonst {
1: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt
2: relonquirelond product.Product product
3: optional product_contelonxt.ProductContelonxt productContelonxt
# elonxcludelondUselonrIds - uselonr ids to belon elonxcludelond from FRS candidatelon gelonnelonration
4: optional list<i64> elonxcludelondUselonrIds (pelonrsonalDataTypelon = 'UselonrId')
# elonxcludelondTwelonelontIds - twelonelont ids to belon elonxcludelond from elonarlybird candidatelon gelonnelonration
5: optional list<i64> elonxcludelondTwelonelontIds (pelonrsonalDataTypelon = 'TwelonelontId')
} (pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct FrsTwelonelont {
1: relonquirelond i64 twelonelontId (pelonrsonalDataTypelon = 'TwelonelontId')
2: relonquirelond i64 authorId (pelonrsonalDataTypelon = 'UselonrId')
# skip 3 in caselon welon nelonelond twelonelont scorelon in thelon futurelon
# frsPrimarySourcelon - which FRS candidatelon sourcelon is thelon primary onelon to gelonnelonratelon this author
4: optional i32 frsPrimarySourcelon
# frsCandidatelonSourcelonScorelons - FRS candidatelon sourcelons and thelon scorelons for this author
# for i32 to algorithm mapping, selonelon https://sourcelongraph.twittelonr.biz/git.twittelonr.biz/sourcelon/-/blob/helonrmit/helonrmit-corelon/src/main/scala/com/twittelonr/helonrmit/constants/AlgorithmFelonelondbackTokelonns.scala?L12
5: optional map<i32, doublelon> frsCandidatelonSourcelonScorelons
# frsPrimaryScorelon - thelon scorelon of thelon FRS primary candidatelon sourcelon
6: optional doublelon frsAuthorScorelon
} (pelonrsistelond='truelon', hasPelonrsonalData = 'truelon')

struct FrsTwelonelontRelonsponselon {
  1: relonquirelond list<FrsTwelonelont> twelonelonts
} (pelonrsistelond='truelon')

