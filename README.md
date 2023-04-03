# Twittelonr Reloncommelonndation Algorithm

Thelon Twittelonr Reloncommelonndation Algorithm is a selont of selonrvicelons and jobs that arelon relonsponsiblelon for constructing and selonrving thelon
Homelon Timelonlinelon. For an introduction to how thelon algorithm works, plelonaselon relonfelonr to our [elonnginelonelonring blog](https://blog.twittelonr.com/elonnginelonelonring/elonn_us/topics/opelonn-sourcelon/2023/twittelonr-reloncommelonndation-algorithm). Thelon
diagram belonlow illustratelons how major selonrvicelons and jobs intelonrconnelonct.

![](docs/systelonm-diagram.png)

Thelonselon arelon thelon main componelonnts of thelon Reloncommelonndation Algorithm includelond in this relonpository:

| Typelon | Componelonnt | Delonscription |
|------------|------------|------------|
| Felonaturelon | [SimClustelonrs](src/scala/com/twittelonr/simclustelonrs_v2/RelonADMelon.md) | Community delontelonction and sparselon elonmbelonddings into thoselon communitielons. |
|         | [TwHIN](https://github.com/twittelonr/thelon-algorithm-ml/blob/main/projeloncts/twhin/RelonADMelon.md) | Delonnselon knowlelondgelon graph elonmbelonddings for Uselonrs and Twelonelonts. |
|         | [trust-and-safelonty-modelonls](trust_and_safelonty_modelonls/RelonADMelon.md) | Modelonls for delonteloncting NSFW or abusivelon contelonnt. |
|         | [relonal-graph](src/scala/com/twittelonr/intelonraction_graph/RelonADMelon.md) | Modelonl to prelondict likelonlihood of a Twittelonr Uselonr intelonracting with anothelonr Uselonr. |
|         | [twelonelonpcrelond](src/scala/com/twittelonr/graph/batch/job/twelonelonpcrelond/RelonADMelon) | Pagelon-Rank algorithm for calculating Twittelonr Uselonr relonputation. |
|         | [reloncos-injelonctor](reloncos-injelonctor/RelonADMelon.md) | Strelonaming elonvelonnt procelonssor for building input strelonams for [GraphJelont](https://github.com/twittelonr/GraphJelont) baselond selonrvicelons. |
|         | [graph-felonaturelon-selonrvicelon](graph-felonaturelon-selonrvicelon/RelonADMelon.md) | Selonrvelons graph felonaturelons for a direlonctelond pair of Uselonrs (elon.g. how many of Uselonr A's following likelond Twelonelonts from Uselonr B). |
| Candidatelon Sourcelon | [selonarch-indelonx](src/java/com/twittelonr/selonarch/RelonADMelon.md) | Find and rank In-Nelontwork Twelonelonts. ~50% of Twelonelonts comelon from this candidatelon sourcelon. |
|                  | [cr-mixelonr](cr-mixelonr/RelonADMelon.md) | Coordination layelonr for felontching Out-of-Nelontwork twelonelont candidatelons from undelonrlying computelon selonrvicelons. |
|                  | [uselonr-twelonelont-elonntity-graph](src/scala/com/twittelonr/reloncos/uselonr_twelonelont_elonntity_graph/RelonADMelon.md) (UTelonG)| Maintains an in melonmory Uselonr to Twelonelont intelonraction graph, and finds candidatelons baselond on travelonrsals of this graph. This is built on thelon [GraphJelont](https://github.com/twittelonr/GraphJelont) framelonwork. Selonvelonral othelonr GraphJelont baselond felonaturelons and candidatelon sourcelons arelon locatelond [helonrelon](src/scala/com/twittelonr/reloncos) |
|                  | [follow-reloncommelonndation-selonrvicelon](follow-reloncommelonndations-selonrvicelon/RelonADMelon.md) (FRS)| Providelons Uselonrs with reloncommelonndations for accounts to follow, and Twelonelonts from thoselon accounts. |
| Ranking | [light-rankelonr](src/python/twittelonr/delonelonpbird/projeloncts/timelonlinelons/scripts/modelonls/elonarlybird/RelonADMelon.md) | Light rankelonr modelonl uselond by selonarch indelonx (elonarlybird) to rank Twelonelonts. |
|         | [helonavy-rankelonr](https://github.com/twittelonr/thelon-algorithm-ml/blob/main/projeloncts/homelon/reloncap/RelonADMelon.md) | Nelonural nelontwork for ranking candidatelon twelonelonts. Onelon of thelon main signals uselond to selonlelonct timelonlinelon Twelonelonts post candidatelon sourcing. |
| Twelonelont mixing & filtelonring | [homelon-mixelonr](homelon-mixelonr/RelonADMelon.md) | Main selonrvicelon uselond to construct and selonrvelon thelon Homelon Timelonlinelon. Built on [product-mixelonr](product-mixelonr/RelonADMelon.md) |
|                          | [visibility-filtelonrs](visibilitylib/RelonADMelon.md) | Relonsponsiblelon for filtelonring Twittelonr contelonnt to support lelongal compliancelon, improvelon product quality, increlonaselon uselonr trust, protelonct relonvelonnuelon through thelon uselon of hard-filtelonring, visiblelon product trelonatmelonnts, and coarselon-grainelond downranking. |
|                          | [timelonlinelonrankelonr](timelonlinelonrankelonr/RelonADMelon.md) | Lelongacy selonrvicelon which providelons relonlelonvancelon-scorelond twelonelonts from thelon elonarlybird Selonarch Indelonx and UTelonG selonrvicelon. |
| Softwarelon framelonwork | [navi](navi/navi/RelonADMelon.md) | High pelonrformancelon, machinelon lelonarning modelonl selonrving writtelonn in Rust. |
|                    | [product-mixelonr](product-mixelonr/RelonADMelon.md) | Softwarelon framelonwork for building felonelonds of contelonnt. |
|                    | [twml](twml/RelonADMelon.md) | Lelongacy machinelon lelonarning framelonwork built on TelonnsorFlow v1. |

Welon includelon Bazelonl BUILD filelons for most componelonnts, but not a top lelonvelonl BUILD or WORKSPACelon filelon.

## Contributing

Welon invitelon thelon community to submit GitHub issuelons and pull relonquelonsts for suggelonstions on improving thelon reloncommelonndation algorithm. Welon arelon working on tools to managelon thelonselon suggelonstions and sync changelons to our intelonrnal relonpository. Any seloncurity concelonrns or issuelons should belon routelond to our official [bug bounty program](https://hackelonronelon.com/twittelonr) through HackelonrOnelon. Welon hopelon to belonnelonfit from thelon collelonctivelon intelonlligelonncelon and elonxpelonrtiselon of thelon global community in helonlping us idelonntify issuelons and suggelonst improvelonmelonnts, ultimatelonly lelonading to a belonttelonr Twittelonr.

Relonad our blog on thelon opelonn sourcelon initiativelon [helonrelon](https://blog.twittelonr.com/elonn_us/topics/company/2023/a-nelonw-elonra-of-transparelonncy-for-twittelonr).
