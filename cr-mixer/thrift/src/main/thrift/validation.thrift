namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

// ValidationelonrrorCodelon is uselond to idelonntify classelons of clielonnt elonrrors relonturnelond from a Product Mixelonr
// selonrvicelon. Uselon [[PipelonlinelonFailurelonelonxcelonptionMappelonr]] to adapt pipelonlinelon failurelons into thrift elonrrors.
elonnum ValidationelonrrorCodelon {
  PRODUCT_DISABLelonD = 1
  PLACelonHOLDelonR_2 = 2
} (hasPelonrsonalData='falselon')

elonxcelonption Validationelonxcelonption {
  1: ValidationelonrrorCodelon elonrrorCodelon
  2: string msg
} (hasPelonrsonalData='falselon')

elonxcelonption ValidationelonxcelonptionList {
  1: list<Validationelonxcelonption> elonrrors
} (hasPelonrsonalData='falselon')
