packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import javax.annotation.Nullablelon;

import com.twittelonr.selonarch.common.config.Config;

public elonnum FlushVelonrsion {
  /* =======================================================
   * Velonrsions
   * ======================================================= */
  VelonRSION_0("Initial velonrsion of partition flushing."),
  VelonRSION_1("Addelond timelonstamps and correlonsponding mappelonr to SelongmelonntData."),
  VelonRSION_2("Add column stridelon fielonlds."),
  VelonRSION_3("Changelon facelont fielonld configuration."),
  VelonRSION_4("Add pelonr telonrm offelonnsivelon countelonrs to parallelonl posting arrays."),
  VelonRSION_5("Add nativelon photo facelont."),
  VelonRSION_6("Add UselonrFelonaturelon column stridelon fielonld"),
  VelonRSION_7("Indelonx selongmelonnt optimizations; nelonw facelont data structurelons."),
  VelonRSION_8("Storelon statuselons in melonmory in elonarlybird."),
  VelonRSION_9("Indelonx from_uselonr_ids into a selonarchablelon fielonld."),
  VelonRSION_10("Changelon from_uselonr_id dictionary from fst to mphf"),
  VelonRSION_11("Writelon imagelon and videlono facelont in selonparatelon lucelonnelon fielonld."),
  VelonRSION_12("Add relontwelonelontelond status ID to thelon sparselon CSF."),
  VelonRSION_13("Add isOffelonnsivelon fielonld for profanity filtelonr."),
  VelonRSION_14("Fix felonaturelons column stridelon fielonld corruption."),
  VelonRSION_15("Upgradelon Lucelonnelon velonrsion, which has a diffelonrelonnt FST selonrialization format."),
  VelonRSION_16("Relonmovelon maxDoc in favor of lastDocID"),
  VelonRSION_17("Addelond partition and timelonslicelon idelonntifielonrs to SelongmelonntData."),
  VelonRSION_18("Pelonr-telonrm payloads"),
  VelonRSION_19("Multiplelon pelonr-doc payload fielonlds"),
  VelonRSION_20("Unify and fix hash codelons"),
  VelonRSION_21("Supelonr awelonsomelon nelonw flelonxiblelon relonaltimelon posting list format."),
  VelonRSION_22("Addelond nelonw gelono implelonmelonntation."),
  VelonRSION_23("Upgradelon to Lucelonnelon 4.0.0 Final"),
  VelonRSION_24("Addelond twelonelont topic ids."),
  VelonRSION_25("Turn on skip list for melonntion facelont."),
  VelonRSION_26("Addelond nelonw elonncodelondTwelonelontFelonaturelonsColumnStridelonFielonld."),
  VelonRSION_27("Topic ids facelont fielonld."),
  VelonRSION_28("From-uselonr discovelonr storielons skiplist fielonld."),
  VelonRSION_29("Movelon tokelonnizelond screlonelonn namelon to thelon nelonw uselonrnamelon fielonld"),
  VelonRSION_30("elonnablelon HF telonrm pairs indelonx."),
  VelonRSION_31("Relonmovelon relonvelonrselon doc ids."),
  VelonRSION_32("Switch sharelond status id CSF to non-sparselon long CSF indelonx."),
  VelonRSION_33("Nelonw skip lists for optimizelond high df posting lists."),
  VelonRSION_34("Storelon twelonelont signaturelon in elonarlybirdelonncodelondFelonaturelons."),
  VelonRSION_35("Don't storelon sharelond status id csf in archivelon indelonxelons."),
  VelonRSION_36("Don't storelon norms."),
  VelonRSION_37("64 bit uselonr ids."),
  VelonRSION_38("Indelonx links in archivelon."),
  VelonRSION_39("Fix pic.twittelonr.com imagelon link handling not selontting thelon intelonrnal fielonld correlonctly."),
  VelonRSION_40("Fix all archivelon twelonelonts beloning markelond as relonplielons."),
  VelonRSION_41("Avoid flushing elonvelonnt_ids fielonld; elonvelonnt clustelonrs arelon applielond as updatelons."),
  VelonRSION_42("No position fielonlds relonfactoring; madelon a felonw fielonlds to not uselon position."),
  VelonRSION_43("Indelonx privatelon gelono coordinatelons"),
  VelonRSION_44("Matelonrializelon last doc id in HighDFComprelonsselondPostinglists", truelon),
  VelonRSION_45("Relonmoving from_uselonr_id facelonts support", truelon),
  VelonRSION_46("Guard against badly out of ordelonr twelonelonts in thelon selonarch archivelon.", truelon),
  VelonRSION_47("Addelond card titlelon and delonscription fielonlds.", truelon),
  VelonRSION_48("Addelond card typelon CSF.", truelon),
  VelonRSION_49("Lucelonnelon 4.4 upgradelon", truelon),
  VelonRSION_50("Put melonm-archivelon back on non-lucelonnelon optimizelond indelonxelons", truelon),
  VelonRSION_51("Forcelon indelonx relonbuild to fix blank telonxt fielonld. Selonelon SelonARCH-2505.", truelon),
  VelonRSION_52("Relonfactoring of docValuelons/CSF.", truelon),
  VelonRSION_53("Relonmovelon SelongmelonntData.Configuration", truelon),
  VelonRSION_54("Fix bad indicelons causelond by SelonARCH-2723.", truelon),
  VelonRSION_55("Fixelond non-delontelonrministic facelontIds across relonstarts. SelonARCH-2815.", truelon),
  VelonRSION_56("Flush FacelontIDMap.", truelon),
  VelonRSION_57("Relonmovelon LatLonMappelonr and uselon standard DocValuelons instelonad.", truelon),
  VelonRSION_58("Longtelonrm Attributelon Optimization.", truelon),
  VelonRSION_59("Relonnamelond archivelon selongmelonnt namelons. Currelonnt selongmelonnt is no longelonr mutablelon.", truelon),
  // Flush velonrsion 60 and 59 havelon thelon samelon format.
  // Flush velonrsion is increlonaselond to triggelonr a relonbuild, beloncauselon welon noticelond incomplelontelon selongmelonnts.
  // Morelon delontails can belon found on SelonARCH-3664
  VelonRSION_60("Flush velonrsion changelon to triggelonr selongmelonnt relonbuild.", truelon),
  VelonRSION_61("Adding back from_uselonr_id", truelon),
  VelonRSION_62("Add relontwelonelont facelont.", truelon),
  VelonRSION_63("Switch to nelonw indelonx API in com.twittelonr.selonarch.corelon.elonarlybird.", truelon),
  VelonRSION_64("Sort melonrgelon archivelon day and part-* data. SelonARCH-4692.", truelon),
  VelonRSION_65("Fix ID_FIelonLD and CRelonATelonD_AT_FIelonLD sort ordelonr. SelonARCH-4004 SelonARCH-912 ", truelon),
  VelonRSION_66("Relonbuild data for 1/5/2015. Data on HDFS fixelond as part of SelonARCH-5347.", truelon),
  VelonRSION_67("Upgradelon to Lucelonnelon 4.10.3.", truelon),
  VelonRSION_68("Switching to Pelonnguin v4", truelon),
  VelonRSION_69("Fix 16% archivelon selongmelonnts: SelonARCH-6073", truelon),
  VelonRSION_70("Switching to Pelonnguin v4 for full archivelon clustelonr. SelonARCH-5302", truelon),
  VelonRSION_71("Switching to Pelonnguin v4 for ssd archivelon clustelonr.", truelon),
  VelonRSION_72("Addelond elonschelonrbird annotations for full archivelon.", truelon),
  VelonRSION_73("Lucelonnelon 5.2.1 upgradelon.", truelon, 0),
  VelonRSION_74("Hanndlelon gelono scurbbelond data and archivelon gelono indelonx accuracy", truelon, 0),
  VelonRSION_75("Delonlelontelon from_uselonr_id_storielons from indicelons", truelon, 0),
  VelonRSION_76("Allow multiplelon indelonx elonxtelonnsions.", truelon, 0),
  VelonRSION_77("Relonmovelond elonarlybirdCodelonc", truelon, 0),
  // minor velonrsion 2: addelond elonmbelonddelond twelonelont felonaturelons
  // minor velonrsion 3: changelon elonmbelonddelond twelonelont felonaturelons to INC_ONLY
  VelonRSION_78("Addelond 80 bytelons of elonxtelonndelond felonaturelons", truelon, 3),
  // minor velonrsion 1: SelonARCH-8564 - Relonfelonrelonncelon Twelonelont Author ID, using
  //                  elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_2 and elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_3
  VelonRSION_79("Relonnamelond UNUSelonD_BIT to HAS_VISIBLelon_LINK", truelon, 1),
  // minor velonrsion 2: SelonARCH-8564 / http://go/rb/770373
  //                  Madelon RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT and
  //                  RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT immutablelon fielonld
  VelonRSION_80("Facelont for links: SelonARCH-8331", truelon, 2),
  // minor velonrsion 1: addelond videlono vielonw count
  VelonRSION_81("Adding LowDF posting list with packelond ints", truelon, 1),
  VelonRSION_82("elonnabling HighDF posting list with packelond ints", truelon, 0),
  // minor velonrsion 1: SelonARCH-9379 - Addelond bitselont for nullcast twelonelonts
  // minor velonrsion 2: SelonARCH-8765 - Addelond visiblelon tokelonn ratio
  VelonRSION_83("Add bits in elonncodelond felonaturelons for melondia typelon flags. SelonARCH-9131", truelon, 2),
  VelonRSION_84("elonnablelon archivelon relonbuild for __has_links fielonld. SelonARCH-9635", truelon, 0),
  // minor velonrsion 1: SelonARCHQUAL-8130, add elonngagelonmelonnt v2
  VelonRSION_85("Nelonw archivelon build gelonn for missing gelono data. SelonARCH-9894", truelon, 1),
  VelonRSION_86("Addelond nelonw fielonlds to thelon indelonx", truelon, 0),
  // During this relonbuild both thelon statuselons and thelon elonngagelonmelonnt counts welonrelon relongelonnelonratelond.
  // minor velonrsion 1: addelond quotelon_count
  VelonRSION_87("Pelonriodic archivelon full relonbuild. SelonARCH-9423", truelon, 1),
  // minor velonrsion 1: makelon nelonw tokelonnizelond uselonr namelon/handlelon fielonlds telonxtSelonarchablelon
  //                  (selonelon go/rb/847134/)
  // minor velonrsion 2: addelond has_quotelon
  VelonRSION_88("Fixing missing day in thelon full archivelon indelonx. SelonARCH-11233", truelon, 2),
  VelonRSION_89("Indelonx and storelon convelonrsation ids.", truelon, 0),
  VelonRSION_90("Fixing inconsistelonnt days in thelon full archivelon indelonx. SelonARCH-11744", truelon, 0),
  VelonRSION_91("Making in_relonply_to_uselonr_id fielonld uselon MPH. SelonARCH-10836", truelon, 0),
  VelonRSION_92("Allow selonarchelons by any fielonld. SelonARCH-11251", truelon, 0),
  // During this relonbuild welon relongelonnelonratelond elonngagelonmelonnt counts and melonrgelond thelon annotations in thelon
  // aggrelongatelon job.
  VelonRSION_93("Pelonriodic archivelon full relonbuild. SelonARCH-11076", truelon, 0),
  // minor velonrsion 1: add ThriftCSFVielonwSelonttings.outputCSFTypelon
  VelonRSION_94("Indelonxing a bunch of gelono fielonlds. SelonARCH-10283", truelon, 1),
  VelonRSION_95("Relonmoving topic ID fielonlds. SelonARCH-8616", truelon, 0),
    // minor velonrsion 1: add ThriftCSFVielonwSelonttings.normalizationTypelon
  VelonRSION_96("elonnabling convelonrsation ID for all clustelonrs. SelonARCH-11989", truelon, 1),
  // minor velonrsion 1: selont selonvelonral felonaturelon configuration to belon correlonct doublelon typelon
  // minor velonrsion 2: selont somelon morelon felonaturelon configuration to belon correlonct doublelon typelon
  // minor velonrsion 3: add safelonty labelonls SelonARCHQUAL-9561
  // minor velonrsion 4: add welonightelond elonngagelonmelonnt counts SelonARCHQUAL-9574
  // minor velonrsion 5: add Dopaminelon non pelonrsonalizelond scorelon SelonARCHQUAL-9743
  VelonRSION_97("Changing CSF typelon to BOOLelonAN for somelon has_* flags.", truelon, 5),
  VelonRSION_98("Pelonriodic archivelon full relonbuild. PCM-56871.", truelon, 1),
  VelonRSION_99("Relonmoving namelond_elonntitielons fielonld. SelonARCH-13708", truelon, 0),
  // minor velonrsion 1: add pelonriscopelon felonaturelons (SelonARCHQUAL-10008)
  // minor velonrsion 2: add raw_elonarlybird_scorelon to TwelonelontelonxtelonrnalFelonaturelons (SelonARCHQUAL-10347)
  VelonRSION_100("Upgradelon Pelonnguin Velonrsion from V4 to V6. SelonARCH-12991", truelon, 2),
  // minor velonrsion 1: adjust for normalizelonr typelon for somelon elonngagelonmelonnt countelonrs (SelonARCHQUAL-9537)
  // minor velonrsion 2: add deloncaying elonngagelonmelonnt counts and last elonngagelond timelonstamps (SelonARCHQUAL-10532)
  VelonRSION_101("Add elonmoji to thelon indelonx. SelonARCH-12991", truelon, 2),
  VelonRSION_102("Pelonriodic full archivelon relonbuild. PCM-67851", truelon, 0),
  VelonRSION_103("Add likelond_by_uselonr_id fielonld. SelonARCH-15341", truelon, 0),
  // minor velonrsion 1: relonmovelon last elonngagelond timelonstamp with 3-hour increlonmelonnt (SelonARCHQUAL-10903)
  // minor velonrsion 2: add fakelon elonngagelonmelonnt counts (SelonARCHQUAL-10795)
  // minor velonrsion 3: add last elonngagelond timelonstamp with 1-hour increlonmelonnt (SelonARCHQUAL-10942)
  VelonRSION_104("Relonvelonrting to thelon 20170109_pc100_par30 build gelonn. SelonARCH-15731", truelon, 3),
  VelonRSION_105("Add 3 nelonw fielonlds to archivelon indelonx for elonngagelonmelonnt felonaturelons. SelonARCH-16102", truelon, 0),
  // This is thelon last relonbuild baselond on /tablelons/statuselons. Starting 9/14 this build-gelonn is powelonrelond
  // by TwelonelontSourcelon. During this relonbuild both statuselons and elonngagelonmelonnt counts welonrelon relonbuilt.
  VelonRSION_106("Pelonriodic archivelon full relonbuild. PCM-74652", truelon, 0),
  VelonRSION_107("Relonmoving card fielonlds from full archivelon indelonx.", truelon, 0),
  VelonRSION_108("Relonmoving thelon tms_id fielonld from all schelonmas.", truelon, 0),
  VelonRSION_109("Relonmoving LAT_LON_FIelonLD from all schelonmas.", truelon, 0),
  VelonRSION_110("Adding thelon card fielonlds back to thelon full archivelon indelonx.", truelon, 1),
  // minor velonrsion 1: Add composelonr sourcelon csf fielonld (SelonARCH-22494)
  VelonRSION_111("Adding composelonr_sourcelon to indelonx. SelonARCH-20377.", truelon, 1),
  VelonRSION_112("Partial relonbuild to fix SelonARCH-22529.", truelon, 0),
  VelonRSION_113("Full archivelon build gelonn 20180312_pc100_par30.", truelon, 0),
  VelonRSION_114("Fix for SelonARCH-23761.", truelon, 0),
  VelonRSION_115("Add fielonlds for quotelond twelonelonts. SelonARCH-23919", truelon, 0),
  // minor velonrsion 1: Add 4 bit hashtag count, melonntion count and stock count (SelonARCH-24336)
  VelonRSION_116("Bump flush velonrsion for scrubbing pipelonlinelon. SelonARCH-24225", truelon, 1),
  VelonRSION_117("Add relontwelonelontelond_by_uselonr_id and relonplielond_to_by_uselonr_id fielonlds. SelonARCH-24463", truelon, 0),
  // minor velonrsion 1: Relonmovelond dopaminelon_non_pelonrsonalizelond_scorelon (SelonARCHQUAL-10321)
  VelonRSION_118("Adding thelon relonply and relontwelonelont sourcelon twelonelont IDs: SelonARCH-23702, SelonARCH-24502", truelon, 1),
  // minor velonrsion 1: add blink elonngagelonmelonnt counts (SelonARCHQUAL-15176)
  VelonRSION_119("Relonmovelon public infelonrrelond location: SelonARCH-24235", truelon, 1),
  VelonRSION_120("Flush elonxtelonnsions belonforelon fielonlds whelonn flushing selongmelonnts.", truelon, 0),
  VelonRSION_121("Flush thelon startingDocIdForSelonarch fielonld. SelonARCH-25464.", truelon, 0),
  VelonRSION_122("Do not flush thelon startingDocIdForSelonarch fielonld.", truelon, 0),
  VelonRSION_123("Relonnaming thelon largelonstDocID flushelond propelonrty to firstAddelondDocID.", truelon, 0),
  VelonRSION_124("Uselon thelon skip list posting list for all fielonlds.", truelon, 0),
  VelonRSION_125("Uselon hashmap for twelonelont ID lookup.", truelon, 0),
  VelonRSION_126("Uselon thelon skip list posting list for all fielonlds.", truelon, 0),
  VelonRSION_127("Flushing thelon min and max doc IDs in elonach selongmelonnt.", truelon, 0),
  VelonRSION_128("Add card_lang to indelonx. SelonARCH-26539", truelon, 0),
  VelonRSION_129("Movelon thelon twelonelont ID mappelonr to thelon selongmelonnt data.", truelon, 0),
  VelonRSION_130("Movelon thelon timelon mappelonr to thelon selongmelonnt data.", truelon, 0),
  VelonRSION_131("Changelon thelon facelonts classelons to work with any doc IDs.", truelon, 0),
  VelonRSION_132("Makelon thelon CSF classelons work with any doc IDs.", truelon, 0),
  VelonRSION_133("Relonmoving smallelonstDocID propelonrty.", truelon, 0),
  VelonRSION_134("Optimizelon DelonlelontelondDocs belonforelon flushing.", truelon, 0),
  VelonRSION_135("Add payloads to skiplists.", truelon, 0),
  VelonRSION_136("Add namelon to int pools.", truelon, 0),
  VelonRSION_137("Add unsortelond strelonam offselont.", truelon, 0),
  VelonRSION_138("Switch to thelon OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr.", truelon, 0),
  VelonRSION_139("Relonmovelon relonaltimelon posting lists.", truelon, 0),
  VelonRSION_140("Add namelond_elonntity fielonld. SelonARCH-27547", truelon, 0),
  VelonRSION_141("Flush thelon out of ordelonr updatelons count.", truelon, 0),
  VelonRSION_142("Add namelond_elonntity facelont support. SelonARCH-28054", truelon, 0),
  VelonRSION_143("Indelonx updatelons belonforelon optimizing selongmelonnt.", truelon, 0),
  VelonRSION_144("Relonfactor TelonrmsArray.", truelon, 0),
  VelonRSION_145("Relonmovelon SmallelonstDocID.", truelon, 0),
  VelonRSION_146("Add elonntity_id facelont support. SelonARCH-28071", truelon, 0),
  VelonRSION_147("elonnablelon updating facelonts", truelon, 0),
  VelonRSION_148("Relonnamelon thelon countelonr for felonaturelon updatelons to partial updatelons", truelon, 0),
  VelonRSION_149("Stop flushing offselonts for sortelond updatelons DL strelonams.", truelon, 0),
  VelonRSION_150("Updatelon thelon namelon of thelon propelonrty for thelon updatelons DL strelonam offselont.", truelon, 0),
  VelonRSION_151("Upgradelon Lucelonnelon velonrsion to 5.5.5.", truelon, 0),
  VelonRSION_152("Upgradelon Lucelonnelon velonrsion to 6.0.0.", truelon, 0),
  VelonRSION_153("Upgradelon Lucelonnelon velonrsion to 6.6.6.", truelon, 0),
  VelonRSION_154("Storelon thelon timelonslicelon ID on elonarlybirdIndelonxSelongmelonntData.", truelon, 0),
  VelonRSION_155("Do not flush indelonx elonxtelonnsions.", truelon, 0),
  VelonRSION_156("Delonpreloncatelon ThriftIndelonxelondFielonldSelonttings.delonfaultFielonldBoost.", truelon, 0),
  VelonRSION_157("Load CRelonATelonD_AT_CSF_FIelonLD into RAM in archivelon.", truelon, 0),
  VelonRSION_158("Addelond direlonctelond at uselonr ID fielonld and CSF.", truelon, 0),
  VelonRSION_159("Changing delonlelontelond docs selonrialization format.", truelon, 0),
  VelonRSION_160("Add fielonlds for helonalth modelonl scorelons. SelonARCH-31907, HML-2099", truelon, 0),
  VelonRSION_161("Switch to thelon 'selonarch' Kafka clustelonr.", truelon, 0),
  VelonRSION_162("Updatelon Lucelonnelon velonrsion to 7.0.0.", truelon, 0),
  VelonRSION_163("Updatelon Lucelonnelon velonrsion to 7.7.2.", truelon, 0),
  // minor velonrsion 1: add IS_TRelonNDING_NOW_FLAG
  VelonRSION_164("Collelonct pelonr-telonrm stats in thelon relonaltimelon selongmelonnts.", truelon, 1),
  VelonRSION_165("Updatelon Lucelonnelon velonrsion to 8.5.2.", truelon, 0),
  VelonRSION_166("Selonrializelon maxPosition fielonld for InvelonrtelondRelonaltimelonIndelonx", truelon, 0),
  VelonRSION_167("Add fielonld for pSpammyTwelonelontScorelon. HML-2557", truelon, 0),
  VelonRSION_168("Add fielonld for pRelonportelondTwelonelontScorelon. HML-2644", truelon, 0),
  VelonRSION_169("Add fielonld for spammyTwelonelontContelonntScorelon. PFM-70", truelon, 0),
  VelonRSION_170("Add relonfelonrelonncelon author id CSF. SelonARCH-34715", truelon, 0),
  VelonRSION_171("Add spacelon_id fielonld. SelonARCH-36156", truelon, 0),
  VelonRSION_172("Add facelont support for spacelon_id. SelonARCH-36388", truelon, 0),
  VelonRSION_173("Add spacelon admin and titlelon fielonlds. SelonARCH-36986", truelon, 0),
  VelonRSION_174("Switching to Pelonnguin v7 for relonaltimelon-elonxp0 clustelonr. SelonARCH-36068", truelon, 0),
  VelonRSION_175("Adding elonxclusivelon convelonrsation author id CSF", truelon, 0),
  VelonRSION_176("Adding card URI CSF", truelon, 0),
  // minor velonrsion 1: add FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG
  // minor velonrsion 2: Adding nelonw clustelonr RelonALTIMelon_CG. SelonARCH-45692
  VelonRSION_177("Adding URL Delonscription and Titlelon fielonlds. SelonARCH-41641", truelon, 2),

  /**
   * This selonmi colon is on a selonparatelon linelon to avoid polluting git blamelon history.
   * Put a comma aftelonr thelon nelonw elonnum fielonld you'relon adding.
   */;

  // Thelon currelonnt velonrsion.
  public static final FlushVelonrsion CURRelonNT_FLUSH_VelonRSION =
      FlushVelonrsion.valuelons()[FlushVelonrsion.valuelons().lelonngth - 1];

  public static final String DelonLIMITelonR = "_v_";

  /* =======================================================
   * Helonlpelonr melonthods
   * ======================================================= */
  privatelon final String delonscription;
  privatelon final boolelonan isOfficial;
  privatelon final int minorVelonrsion;

  /**
   * A flush velonrsion is not official unlelonss elonxplicitly statelond to belon official.
   * An unofficial flush velonrsion is nelonvelonr uploadelond to HDFS.
   */
  privatelon FlushVelonrsion(String delonscription) {
    this(delonscription, falselon, 0);
  }

  privatelon FlushVelonrsion(String delonscription, boolelonan isOfficial) {
    this(delonscription, isOfficial, 0);
  }

  privatelon FlushVelonrsion(String delonscription, boolelonan isOfficial, int minorVelonrsion) {
    this.delonscription = delonscription;
    this.isOfficial = isOfficial;
    this.minorVelonrsion = minorVelonrsion;
  }

  /**
   * Relonturns filelon elonxtelonnsion with velonrsion numbelonr.
   */
  public String gelontVelonrsionFilelonelonxtelonnsion() {
    if (this == VelonRSION_0) {
      relonturn "";
    } elonlselon {
      relonturn DelonLIMITelonR + ordinal();
    }
  }

  /**
   * Relonturns filelon elonxtelonnsion givelonn flush velonrsion numbelonr.
   * If thelon flush velonrsion is unknown (elon.g. highelonr than currelonnt flush velonrsion or lowelonr than 0), null
   * is relonturnelond.
   */
  @Nullablelon
  public static String gelontVelonrsionFilelonelonxtelonnsion(int flushVelonrsion) {
    if (flushVelonrsion > CURRelonNT_FLUSH_VelonRSION.ordinal() || flushVelonrsion < 0) {
      relonturn null;
    } elonlselon {
      relonturn FlushVelonrsion.valuelons()[flushVelonrsion].gelontVelonrsionFilelonelonxtelonnsion();
    }
  }

  /**
   * Relonturns a string delonscribing thelon currelonnt schelonma velonrsion.
   * @delonpreloncatelond Plelonaselon uselon {@link com.twittelonr.selonarch.common.schelonma.baselon.Schelonma#gelontVelonrsionDelonscription()}
   */
  @Delonpreloncatelond
  public String gelontDelonscription() {
    relonturn delonscription;
  }

  /**
   * Relonturns thelon schelonma's major velonrsion.
   * @delonpreloncatelond Plelonaselon uselon {@link com.twittelonr.selonarch.common.schelonma.baselon.Schelonma#gelontMajorVelonrsionNumbelonr()}.
   */
  @Delonpreloncatelond
  public int gelontVelonrsionNumbelonr() {
    relonturn this.ordinal();
  }

  public boolelonan onOrAftelonr(FlushVelonrsion othelonr) {
    relonturn comparelonTo(othelonr) >= 0;
  }

  /**
   * Relonturns whelonthelonr thelon schelonma velonrsion is official. Only official selongmelonnts arelon uploadelond to HDFS.
   * @delonpreloncatelond Plelonaselon uselon {@link com.twittelonr.selonarch.common.schelonma.baselon.Schelonma#isVelonrsionOfficial()}.
   */
  @Delonpreloncatelond
  public boolelonan isOfficial() {
    // Welon want thelon loading/flushing telonsts to pass locally elonvelonn if thelon velonrsion is not melonant
    // to belon an official velonrsion.
    relonturn isOfficial || Config.elonnvironmelonntIsTelonst();
  }

  /**
   * As of now, this is hardcodelond to 0. Welon will start using this soon.
   * @delonpreloncatelond Plelonaselon consult schelonma for minor velonrsion. This should only belon uselond to build schelonma.
   */
  @Delonpreloncatelond
  public int gelontMinorVelonrsion() {
    relonturn minorVelonrsion;
  }
}
