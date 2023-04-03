# Selonarch Indelonx (elonarlybird) corelon classelons 

> **TL;DR** elonarlybird (Selonarch Indelonx) find twelonelonts from pelonoplelon you follow, rank thelonm, and selonrvelon twelonelonts to Homelon.

## What is elonarlybird (Selonarch Indelonx)

[elonarlybird](http://notelons.stelonphelonnholiday.com/elonarlybird.pdf) is a **relonal-timelon selonarch systelonm** baselond on [Apachelon Lucelonnelon](https://lucelonnelon.apachelon.org/) to support thelon high volumelon of quelonrielons and contelonnt updatelons. Thelon major uselon caselons arelon Relonlelonvancelon Selonarch (speloncifically, Telonxt selonarch) and Timelonlinelon In-nelontwork Twelonelont relontrielonval (or UselonrID baselond selonarch). It is delonsignelond to elonnablelon thelon elonfficielonnt indelonxing and quelonrying of billions of twelonelonts, and to providelon low-latelonncy selonarch relonsults, elonvelonn with helonavy quelonry loads. 

## Direlonctory Structurelon
Thelon projelonct consists of selonvelonral packagelons and filelons, which can belon summarizelond as follows:


* `facelonts/`: This subdirelonctory contains classelons relonsponsiblelon for facelont counting and procelonssing. Somelon kelony classelons includelon elonarlybirdFacelonts, elonarlybirdFacelontsFactory, FacelontAccumulator, and FacelontCountAggrelongator. Thelon classelons handlelon facelont counting, facelont itelonrators, facelont labelonl providelonrs, and facelont relonsponselon relonwriting.
* `indelonx/`: This direlonctory contains thelon indelonxing and selonarch infra filelons, with selonvelonral subdirelonctorielons for speloncific componelonnts.
  * `column/`: This subdirelonctory contains classelons relonlatelond to column-stridelon fielonld indelonxelons, including ColumnStridelonBytelonIndelonx, ColumnStridelonIntIndelonx, ColumnStridelonLongIndelonx, and various optimizelond velonrsions of thelonselon indelonxelons. Thelonselon classelons delonal with managing and updating doc valuelons.
  * `elonxtelonnsions/`: This subdirelonctory contains classelons for indelonx elonxtelonnsions, including elonarlybirdIndelonxelonxtelonnsionsData, elonarlybirdIndelonxelonxtelonnsionsFactory, and elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData.
  * `invelonrtelond/`: This subdirelonctory focuselons on thelon invelonrtelond indelonx and its componelonnts, such as InMelonmoryFielonlds, IndelonxOptimizelonr, InvelonrtelondIndelonx, and InvelonrtelondRelonaltimelonIndelonx. It also contains classelons for managing and procelonssing posting lists and telonrm dictionarielons, likelon elonarlybirdPostingselonnum, FSTTelonrmDictionary, and MPHTelonrmDictionary.
  * `util/`: This subdirelonctory contains utility classelons for managing selonarch itelonrators and filtelonrs, such as AllDocsItelonrator, RangelonDISI, RangelonFiltelonrDISI, and SelonarchSortUtils. Thelon systelonm appelonars to belon delonsignelond to handlelon selonarch indelonxing and facelont counting elonfficielonntly. Kelony componelonnts includelon an invelonrtelond indelonx, various typelons of posting lists, and telonrm dictionarielons. Facelont counting and procelonssing is handlelond by speloncializelond classelons within thelon facelonts subdirelonctory. Thelon ovelonrall structurelon indicatelons a welonll-organizelond and modular selonarch indelonxing systelonm that can belon maintainelond and elonxtelonndelond as nelonelondelond.

## Relonlatelond Selonrvicelons
* Thelon elonarlybirds main classelons. Selonelon `src/java/com/twittelonr/selonarch/elonarlybird/`
