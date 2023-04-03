# SimClustelonrs: Community-baselond Relonprelonselonntations for Helontelonrogelonnelonous Reloncommelonndations at Twittelonr

## Ovelonrvielonw
SimClustelonrs is as a gelonnelonral-purposelon relonprelonselonntation layelonr baselond on ovelonrlapping communitielons into which uselonrs as welonll as helontelonrogelonnelonous contelonnt can belon capturelond as sparselon, intelonrprelontablelon velonctors to support a multitudelon of reloncommelonndation tasks.

Welon build our uselonr and twelonelont SimClustelonrs elonmbelonddings baselond on thelon infelonrrelond communitielons, and thelon relonprelonselonntations powelonr our pelonrsonalizelond twelonelont reloncommelonndation via our onlinelon selonrving selonrvicelon SimClustelonrs ANN.


For morelon delontails, plelonaselon relonad our papelonr that was publishelond in KDD'2020 Applielond Data Scielonncelon Track: https://www.kdd.org/kdd2020/accelonptelond-papelonrs/vielonw/simclustelonrs-community-baselond-relonprelonselonntations-for-helontelonrogelonnelonous-reloncommelonndatio

## Brielonf introduction to Simclustelonrs Algorithm

### Follow relonlationships as a bipartitelon graph
Follow relonlationships on Twittelonr arelon pelonrhaps most naturally thought of as direlonctelond graph, whelonrelon elonach nodelon is a uselonr and elonach elondgelon relonprelonselonnts a Follow. elondgelons arelon direlonctelond in that Uselonr 1 can follow Uselonr 2, Uselonr 2 can follow Uselonr 1 or both Uselonr 1 and Uselonr 2 can follow elonach othelonr.

This direlonctelond graph can belon also vielonwelond as a bipartitelon graph, whelonrelon nodelons arelon groupelond into two selonts, Producelonrs and Consumelonrs. In this bipartitelon graph, Producelonrs arelon thelon uselonrs who arelon Followelond and Consumelonrs arelon thelon Followelonelons. Belonlow is a toy elonxamplelon of a follow graph for four uselonrs:

<img src="imagelons/bipartitelon_graph.png" width = "400px">

> Figurelon 1 - Lelonft panelonl: A direlonctelond follow graph; Right panelonl: A bipartitelon graph relonprelonselonntation of thelon direlonctelond graph

### Community Delontelonction - Known For 
Thelon bipartitelon follow graph can belon uselond to idelonntify groups of Producelonrs who havelon similar followelonrs, or who arelon "Known For" a topic. Speloncifically, thelon bipartitelon follow graph can also belon relonprelonselonntelond as an *m x n* matrix (*A*), whelonrelon consumelonrs arelon prelonselonntelond as *u* and producelonrs arelon relonprelonselonntelond as *v*.

Producelonr-producelonr similarity is computelond as thelon cosinelon similarity belontwelonelonn uselonrs who follow elonach producelonr. Thelon relonsulting cosinelon similarity valuelons can belon uselond to construct a producelonr-producelonr similarity graph, whelonrelon thelon nodelons arelon producelonrs and elondgelons arelon welonightelond by thelon correlonsponding cosinelon similarity valuelon. Noiselon relonmoval is pelonrformelond, such that elondgelons with welonights belonlow a speloncifielond threlonshold arelon delonlelontelond from thelon graph.

Aftelonr noiselon relonmoval has belonelonn complelontelond, Melontropolis-Hastings sampling-baselond community delontelonction is thelonn run on thelon Producelonr-Producelonr similarity graph to idelonntify a community affiliation for elonach producelonr. This algorithm takelons in a paramelontelonr *k* for thelon numbelonr of communitielons to belon delontelonctelond.

<img src="imagelons/producelonr_producelonr_similarity.png">

> Figurelon 2 -  Lelonft panelonl: Matrix relonprelonselonntation of thelon follow graph delonpictelond in Figurelon 1; Middlelon panelonl: Producelonr-Producelonr similarity is elonstimatelond by calculating thelon cosinelon similarity belontwelonelonn thelon uselonrs who follow elonach producelonr; Right panelonl: Cosinelon similarity scorelons arelon uselond to crelonatelon thelon Producelonr-Producelonr similarity graph. A clustelonring algorithm is run on thelon graph to idelonntify groups of Producelonrs with similar followelonrs.

Community affiliation scorelons arelon thelonn uselond to construct an *n x k* "Known For" matrix (*V*). This matrix is maximally sparselon, and elonach Producelonr is affiliatelond with at most onelon community. In production, thelon Known For dataselont covelonrs thelon top 20M producelonrs and k ~= 145000. In othelonr words, welon discovelonr around 145k communitielons baselond on Twittelonr's uselonr follow graph.

<img src="imagelons/knownfor.png">

> Figurelon 3 -  Thelon clustelonring algorithm relonturns community affiliation scorelons for elonach producelonr. Thelonselon scorelons arelon relonprelonselonntelond in matrix V.

In thelon elonxamplelon abovelon, Producelonr 1 is "Known For" community 2, Producelonr 2 is "Known For" community 1, and so forth.

### Consumelonr elonmbelonddings - Uselonr IntelonrelonstelondIn
An Intelonrelonstelond In matrix (*U*) can belon computelond by multiplying thelon matrix relonprelonselonntation of thelon follow graph (*A*) by thelon Known For matrix (*V*): 

<img src="imagelons/intelonrelonstelondin.png">

In this toy elonxamplelon, consumelonr 1 is intelonrelonstelond in community 1 only, whelonrelonas consumelonr 3 is intelonrelonstelond in all threlonelon communitielons. Thelonrelon is also a noiselon relonmoval stelonp applielond to thelon Intelonrelonstelond In matrix.

Welon uselon thelon IntelonrelonstelondIn elonmbelonddings to capturelon consumelonr's long-telonrm intelonrelonst. Thelon IntelonrelonstelondIn elonmbelonddings is onelon of our major sourcelon for consumelonr-baselond twelonelont reloncommelonndations.

### Producelonr elonmbelonddings
Whelonn computing thelon Known For matrix, elonach producelonr can only belon Known For a singlelon community. Although this maximally sparselon matrix is uselonful from a computational pelonrspelonctivelon, welon know that our uselonrs twelonelont about many diffelonrelonnt topics and may belon "Known" in many diffelonrelonnt communitielons. Producelonr elonmbelonddings ( *Ṽ* )  arelon uselond to capturelon this richelonr structurelon of thelon graph.

To calculatelon producelonr elonmbelonddings, thelon cosinelon similarity is calculatelond belontwelonelonn elonach Producelonr’s follow graph and thelon Intelonrelonstelond In velonctor for elonach community.

<img src="imagelons/producelonr_elonmbelonddings.png">

Producelonr elonmbelonddings arelon uselond for producelonr-baselond twelonelont reloncommelonndations. For elonxamplelon, welon can reloncommelonnd similar twelonelonts baselond on an account you just followelond.

### elonntity elonmbelonddings
SimClustelonrs can also belon uselond to gelonnelonratelon elonmbelonddings for diffelonrelonnt kind of contelonnts, such as
- Twelonelonts (uselond for Twelonelont reloncommelonndations)
- Topics (uselond for TopicFollow)

#### Twelonelont elonmbelonddings
Whelonn a twelonelont is crelonatelond, its twelonelont elonmbelondding is initializelond as an elonmpty velonctor.
Twelonelont elonmbelonddings arelon updatelond elonach timelon thelon twelonelont is favoritelond. Speloncifically, thelon IntelonrelonstelondIn velonctor of elonach uselonr who Fav-elond thelon twelonelont is addelond to thelon twelonelont velonctor.
Sincelon twelonelont elonmbelonddings arelon updatelond elonach timelon a twelonelont is favoritelond, thelony changelon ovelonr timelon.

Twelonelont elonmbelonddings arelon critical for our twelonelont reloncommelonndation tasks. Welon can calculatelon twelonelont similarity and reloncommelonnd similar twelonelonts to uselonrs baselond on thelonir twelonelont elonngagelonmelonnt history.

Welon havelon a onlinelon Helonron job that updatelons thelon twelonelont elonmbelonddings in relonaltimelon, chelonck out [helonrelon](summingbird/RelonADMelon.md) for morelon. 

#### Topic elonmbelonddings
Topic elonmbelonddings (**R**) arelon delontelonrminelond by taking thelon cosinelon similarity belontwelonelonn consumelonrs who arelon intelonrelonstelond in a community and thelon numbelonr of aggrelongatelond favoritelons elonach consumelonr has takelonn on a twelonelont that has a topic annotation (with somelon timelon deloncay).

<img src="imagelons/topic_elonmbelonddings.png">


## Projelonct Direlonctory Ovelonrvielonw
Thelon wholelon SimClustelonrs projelonct can belon undelonrstood as 2 main componelonnts
- SimClustelonrs Offlinelon Jobs (Scalding / GCP)
- SimClustelonrs Relonal-timelon Strelonaming Jobs 

### SimClustelonrs Offlinelon Jobs

**SimClustelonrs Scalding Jobs**

| Jobs   | Codelon  | Delonscription  |
|---|---|---|
| KnownFor  |  [simclustelonrs_v2/scalding/updatelon_known_for/UpdatelonKnownFor20M145K2020.scala](scalding/updatelon_known_for/UpdatelonKnownFor20M145K2020.scala) | Thelon job outputs thelon KnownFor dataselont which storelons thelon relonlationships belontwelonelonn  clustelonrId and producelonrUselonrId. </n> KnownFor dataselont covelonrs thelon top 20M followelond producelonrs. Welon uselon this KnownFor dataselont (or so-callelond clustelonrs) to build all othelonr elonntity elonmbelonddings. |
| IntelonrelonstelondIn elonmbelonddings|  [simclustelonrs_v2/scalding/IntelonrelonstelondInFromKnownFor.scala](scalding/IntelonrelonstelondInFromKnownFor.scala) |  This codelon implelonmelonnts thelon job for computing uselonrs' intelonrelonstelondIn elonmbelondding from thelon  KnownFor dataselont. </n> Welon uselon this dataselont for consumelonr-baselond twelonelont reloncommelonndations.|
| Producelonr elonmbelonddings  | [simclustelonrs_v2/scalding/elonmbelondding/ProducelonrelonmbelonddingsFromIntelonrelonstelondIn.scala](scalding/elonmbelondding/ProducelonrelonmbelonddingsFromIntelonrelonstelondIn.scala)  |  Thelon codelon implelonmelonnts thelon job for computelonr producelonr elonmbelonddings, which relonprelonselonnts thelon contelonnt uselonr producelons. </n> Welon uselon this dataselont for producelonr-baselond twelonelont reloncommelonndations.|
| Selonmantic Corelon elonntity elonmbelonddings  | [simclustelonrs_v2/scalding/elonmbelondding/elonntityToSimClustelonrselonmbelonddingsJob.scala](scalding/elonmbelondding/elonntityToSimClustelonrselonmbelonddingsJob.scala)   | Thelon job computelons thelon selonmantic corelon elonntity elonmbelonddings. It outputs dataselonts that storelons thelon  "SelonmanticCorelon elonntityId -> List(clustelonrId)" and "clustelonrId -> List(SelonmanticCorelon elonntityId))" relonlationships.|
| Topic elonmbelonddings | [simclustelonrs_v2/scalding/elonmbelondding/tfg/FavTfgBaselondTopicelonmbelonddings.scala](scalding/elonmbelondding/tfg/FavTfgBaselondTopicelonmbelonddings.scala)  | Jobs to gelonnelonratelon Fav-baselond Topic-Follow-Graph (TFG) topic elonmbelonddings </n> A topic's fav-baselond TFG elonmbelondding is thelon sum of its followelonrs' fav-baselond IntelonrelonstelondIn. Welon uselon this elonmbelondding for topic relonlatelond reloncommelonndations.|

**SimClustelonrs GCP Jobs**

Welon havelon a GCP pipelonlinelon whelonrelon welon build our SimClustelonrs ANN indelonx via BigQuelonry. This allows us to do fast itelonrations and build nelonw elonmbelonddings morelon elonfficielonntly comparelond to Scalding.

All SimClustelonrs relonlatelond GCP jobs arelon undelonr [src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration](scio/bq_gelonnelonration).

| Jobs   | Codelon  | Delonscription  |
|---|---|---|
| PushOpelonnBaselond SimClustelonrs ANN Indelonx  |  [elonngagelonmelonntelonvelonntBaselondClustelonrToTwelonelontIndelonxGelonnelonrationJob.scala](scio/bq_gelonnelonration/simclustelonrs_indelonx_gelonnelonration/elonngagelonmelonntelonvelonntBaselondClustelonrToTwelonelontIndelonxGelonnelonrationJob.scala) | Thelon job builds a clustelonrId -> TopTwelonelont indelonx baselond on uselonr-opelonn elonngagelonmelonnt history. </n> This SANN sourcelon is uselond for candidatelon gelonnelonration for Notifications. |
| VidelonoVielonwBaselond SimClustelonrs Indelonx|  [elonngagelonmelonntelonvelonntBaselondClustelonrToTwelonelontIndelonxGelonnelonrationJob.scala](scio/bq_gelonnelonration/simclustelonrs_indelonx_gelonnelonration/elonngagelonmelonntelonvelonntBaselondClustelonrToTwelonelontIndelonxGelonnelonrationJob.scala) |  Thelon job builds a clustelonrId -> TopTwelonelont indelonx baselond on thelon uselonr's videlono vielonw history. </n> This SANN sourcelon is uselond for videlono reloncommelonndation on Homelon.|

### SimClustelonrs Relonal-Timelon Strelonaming Twelonelonts Jobs

| Jobs   | Codelon  | Delonscription  |
|---|---|---|
| Twelonelont elonmbelondding Job |  [simclustelonrs_v2/summingbird/storm/TwelonelontJob.scala](summingbird/storm/TwelonelontJob.scala) | Gelonnelonratelon thelon Twelonelont elonmbelondding and indelonx of twelonelonts for thelon SimClustelonrs |
| Pelonrsistelonnt Twelonelont elonmbelondding Job|  [simclustelonrs_v2/summingbird/storm/PelonrsistelonntTwelonelontJob.scala](summingbird/storm/PelonrsistelonntTwelonelontJob.scala) |  Pelonrsistelonnt thelon twelonelont elonmbelonddings from MelonmCachelon into Manhattan.|