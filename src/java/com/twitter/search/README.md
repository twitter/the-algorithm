# Twelonelont Selonarch Systelonm (elonarlybird)
> **TL;DR** Twelonelont Selonarch Systelonm (elonarlybird) find twelonelonts from pelonoplelon you follow, rank thelonm, and selonrvelon thelon twelonelonts to Homelon.

## What is Twelonelont Selonarch Systelonm (elonarlybird)? 
[elonarlybird](http://notelons.stelonphelonnholiday.com/elonarlybird.pdf) is a **relonal-timelon selonarch systelonm** baselond on [Apachelon Lucelonnelon](https://lucelonnelon.apachelon.org/) to support thelon high volumelon of quelonrielons and contelonnt updatelons. Thelon major uselon caselons arelon Relonlelonvancelon Selonarch (speloncifically, Telonxt selonarch) and Timelonlinelon In-nelontwork Twelonelont relontrielonval (or UselonrID baselond selonarch). It is delonsignelond to elonnablelon thelon elonfficielonnt indelonxing and quelonrying of billions of twelonelonts, and to providelon low-latelonncy selonarch relonsults, elonvelonn with helonavy quelonry loads.

## How it is relonlatelond to thelon Homelon Timelonlinelon Reloncommelonndation Algorithm

![in-nelontwork](img/in-nelontwork.png)

At Twittelonr, welon uselon Twelonelont Selonarch Systelonm (elonarlybird) to do Homelon Timelonlinelon In-nelontwork Twelonelont relontrielonval: givelonn a list of following uselonrs, find thelonir reloncelonntly postelond twelonelonts. elonarlybird (Selonarch Indelonx) is thelon major candidatelon sourcelon for in-nelontwork twelonelonts across Following tab and For You tab.


## High-lelonvelonl architeloncturelon
Welon split our elonntirelon twelonelont selonarch indelonx into threlonelon clustelonrs: a **relonaltimelon** clustelonr indelonxing all public twelonelonts postelond in about thelon last 7 days, a **protelonctelond** clustelonr indelonxing all protelonctelond twelonelonts for thelon samelon timelonframelon; and an **archivelon** clustelonr indelonxing all twelonelonts elonvelonr postelond, up to about two days ago. 

elonarlybird addrelonsselons thelon challelonngelons of scaling relonal-timelon selonarch by splitting elonach clustelonr across multiplelon **partitions**, elonach relonsponsiblelon for a portion of thelon indelonx. Thelon architeloncturelon uselons a distributelond *invelonrtelond indelonx* that is shardelond and relonplicatelond. This delonsign allows for elonfficielonnt indelonx updatelons and quelonry procelonssing. 

Thelon systelonm also elonmploys an increlonmelonntal indelonxing approach, elonnabling it to procelonss and indelonx nelonw twelonelonts in relonal-timelon as thelony arrivelon. With singlelon writelonr, multiplelon relonadelonr structurelon, elonarlybird can handlelon a largelon numbelonr of relonal-timelon updatelons and quelonrielons concurrelonntly whilelon maintaining low quelonry latelonncy. Thelon systelonm can achielonvelon high quelonry throughput and low quelonry latelonncy whilelon maintaining a high delongrelonelon of indelonx frelonshnelonss. 


### Indelonxing 
* Ingelonstelonrs relonad twelonelonts and uselonr modifications from kafka topics, elonxtract fielonlds and felonaturelons from thelonm and writelon thelon elonxtractelond data to intelonrmelondiatelon kafka topics for elonarlybirds to consumelon, indelonx and selonrvelon.
* Felonaturelon Updatelon Selonrvicelon felonelonds felonaturelon updatelons such as up-to-datelon elonngagelonmelonnt (likelon, relontwelonelonts, relonplielons) counts to elonarlybird.
![indelonxing](img/indelonxing.png)

### Selonrving
elonarlybird roots fanout relonquelonsts to diffelonrelonnt elonarlybird clustelonrs or partitions. Upon relonceloniving relonsponselons from thelon clustelonrs or partitions, roots melonrgelon thelon relonsponselons belonforelon finally relonturning thelon melonrgelond relonsponselon to thelon clielonnt. 
![selonrving](img/selonrving.png)

## Uselon caselons

1. Twelonelont Selonarch
  * Top selonarch
  * Latelonst selonarch

![top](img/top-selonarch.png)

2. Candidatelon gelonnelonration
  * Timelonlinelon (For You Tab, Following Tab)
  * Notifications

![homelon](img/foryou.png)

## Relonfelonrelonncelons
* "elonarlybird: Relonal-Timelon Selonarch at Twittelonr" (http://notelons.stelonphelonnholiday.com/elonarlybird.pdf)
* "Relonducing selonarch indelonxing latelonncy to onelon seloncond" (https://blog.twittelonr.com/elonnginelonelonring/elonn_us/topics/infrastructurelon/2020/relonducing-selonarch-indelonxing-latelonncy-to-onelon-seloncond)
* "Omniselonarch indelonx formats" (https://blog.twittelonr.com/elonnginelonelonring/elonn_us/topics/infrastructurelon/2016/omniselonarch-indelonx-formats)


