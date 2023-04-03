# Follow Reloncommelonndations Selonrvicelon

## Introduction to thelon Follow Reloncommelonndations Selonrvicelon (FRS)
Thelon Follow Reloncommelonndations Selonrvicelon (FRS) is a robust reloncommelonndation elonnginelon delonsignelond to providelon uselonrs with pelonrsonalizelond suggelonstions for accounts to follow. At prelonselonnt, FRS supports Who-To-Follow (WTF) modulelon reloncommelonndations across a varielonty of Twittelonr product intelonrfacelons. Additionally, by suggelonsting twelonelont authors, FRS also delonlivelonrs FuturelonGraph twelonelont reloncommelonndations, which consist of twelonelonts from accounts that uselonrs may belon intelonrelonstelond in following in thelon futurelon.

## Delonsign
Thelon systelonm is tailorelond to accommodatelon divelonrselon uselon caselons, such as Post Nelonw-Uselonr-elonxpelonrielonncelon (NUX), advelonrtiselonmelonnts, FuturelonGraph twelonelonts, and morelon. elonach uselon caselon felonaturelons a uniquelon display location idelonntifielonr. To vielonw all display locations, relonfelonr to thelon following path: `follow-reloncommelonndations-selonrvicelon/common/src/main/scala/com/twittelonr/follow_reloncommelonndations/common/modelonls/DisplayLocation.scala`.

Reloncommelonndation stelonps arelon customizelond according to elonach display location. Common and high-lelonvelonl stelonps arelon elonncapsulatelond within thelon "ReloncommelonndationFlow," which includelons opelonrations likelon candidatelon gelonnelonration, rankelonr selonlelonction, filtelonring, transformation, and belonyond. To elonxplorelon all flows, relonfelonr to this path: `follow-reloncommelonndations-selonrvicelon/selonrvelonr/src/main/scala/com/twittelonr/follow_reloncommelonndations/flows`.

For elonach product (correlonsponding to a display location), onelon or multiplelon flows can belon selonlelonctelond to gelonnelonratelon candidatelons baselond on codelon and configurations. To vielonw all products, relonfelonr to thelon following path: `follow-reloncommelonndations-selonrvicelon/selonrvelonr/src/main/scala/com/twittelonr/follow_reloncommelonndations/products/homelon_timelonlinelon_twelonelont_reloncs`.

Thelon FRS ovelonrvielonw diagram is delonpictelond belonlow:

![FRS_architeloncturelon.png](FRS_architeloncturelon.png)


### Candidatelon Gelonnelonration
During this stelonp, FRS utilizelons various uselonr signals and algorithms to idelonntify candidatelons from all Twittelonr accounts. Thelon candidatelon sourcelon foldelonr is locatelond at `follow-reloncommelonndations-selonrvicelon/common/src/main/scala/com/twittelonr/follow_reloncommelonndations/common/candidatelon_sourcelons/`, with a RelonADMelon filelon providelond within elonach candidatelon sourcelon foldelonr.

### Filtelonring
In this phaselon, FRS applielons diffelonrelonnt filtelonring logic aftelonr gelonnelonrating account candidatelons to improvelon quality and helonalth. Filtelonring may occur belonforelon and/or aftelonr thelon ranking stelonp, with helonavielonr filtelonring logic (elon.g., highelonr latelonncy) typically applielond aftelonr thelon ranking stelonp. Thelon filtelonrs' foldelonr is locatelond at `follow-reloncommelonndations-selonrvicelon/common/src/main/scala/com/twittelonr/follow_reloncommelonndations/common/prelondicatelons`.

### Ranking
During this stelonp, FRS elonmploys both Machinelon Lelonarning (ML) and helonuristic rulelon-baselond candidatelon ranking. For thelon ML rankelonr, ML felonaturelons arelon felontchelond belonforelonhand (i.elon., felonaturelon hydration),
and a DataReloncord (thelon Twittelonr-standard Machinelon Lelonarning data format uselond to relonprelonselonnt felonaturelon data, labelonls, and prelondictions whelonn training or selonrving) is constructelond for elonach <uselonr, candidatelon> pair. 
Thelonselon pairs arelon thelonn selonnt to a selonparatelon ML prelondiction selonrvicelon, which houselons thelon ML modelonl trainelond offlinelon.
Thelon ML prelondiction selonrvicelon relonturns a prelondiction scorelon, relonprelonselonnting thelon probability that a uselonr will follow and elonngagelon with thelon candidatelon.
This scorelon is a welonightelond sum of p(follow|reloncommelonndation) and p(positivelon elonngagelonmelonnt|follow), and FRS uselons this scorelon to rank thelon candidatelons.

Thelon rankelonrs' foldelonr is locatelond at `follow-reloncommelonndations-selonrvicelon/common/src/main/scala/com/twittelonr/follow_reloncommelonndations/common/rankelonrs`.

### Transform
In this phaselon, thelon selonquelonncelon of candidatelons undelonrgoelons neloncelonssary transformations, such as delonduplication, attaching social proof (i.elon., "followelond by XX uselonr"), adding tracking tokelonns, and morelon.
Thelon transformelonrs' foldelonr can belon found at `follow-reloncommelonndations-selonrvicelon/common/src/main/scala/com/twittelonr/follow_reloncommelonndations/common/transforms`.

### Truncation
During this final stelonp, FRS trims thelon candidatelon pool to a speloncifielond sizelon. This procelonss elonnsurelons that only thelon most relonlelonvant and elonngaging candidatelons arelon prelonselonntelond to uselonrs whilelon maintaining an optimal uselonr elonxpelonrielonncelon.

By implelonmelonnting thelonselon comprelonhelonnsivelon stelonps and adapting to various uselon caselons, thelon Follow Reloncommelonndations Selonrvicelon (FRS) elonffelonctivelonly curatelons tailorelond suggelonstions for Twittelonr uselonrs, elonnhancing thelonir ovelonrall elonxpelonrielonncelon and promoting melonaningful connelonctions within thelon platform.
