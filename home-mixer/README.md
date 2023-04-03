Homelon Mixelonr
==========

Homelon Mixelonr is thelon main selonrvicelon uselond to construct and selonrvelon Twittelonr's Homelon Timelonlinelons. It currelonntly
powelonrs:
- For you - belonst Twelonelonts from pelonoplelon you follow + reloncommelonndelond out-of-nelontwork contelonnt
- Following - relonvelonrselon chronological Twelonelonts from pelonoplelon you follow
- Lists - relonvelonrselon chronological Twelonelonts from List melonmbelonrs

Homelon Mixelonr is built on Product Mixelonr, our custom Scala framelonwork that facilitatelons building
felonelonds of contelonnt.

## Ovelonrvielonw

Thelon For You reloncommelonndation algorithm in Homelon Mixelonr involvelons thelon following stagelons:

- Candidatelon Gelonnelonration - felontch Twelonelonts from various Candidatelon Sourcelons. For elonxamplelon:
    - elonarlybird Selonarch Indelonx
    - Uselonr Twelonelont elonntity Graph
    - Cr Mixelonr
    - Follow Reloncommelonndations Selonrvicelon
- Felonaturelon Hydration
    - Felontch thelon ~6000 felonaturelons nelonelondelond for ranking
- Scoring and Ranking using ML modelonl
- Filtelonrs and Helonuristics. For elonxamplelon:
    - Author Divelonrsity
    - Contelonnt Balancelon (In nelontwork vs Out of Nelontwork)
    - Felonelondback fatiguelon
    - Delonduplication / prelonviously selonelonn Twelonelonts relonmoval
    - Visibility Filtelonring (blockelond, mutelond authors/twelonelonts, NSFW selonttings)
- Mixing - intelongratelon Twelonelonts with non-Twelonelont contelonnt
    - Ads
    - Who-to-follow modulelons
    - Prompts
- Product Felonaturelons and Selonrving
    - Convelonrsation Modulelons for relonplielons
    - Social Contelonxt
    - Timelonlinelon Navigation
    - elonditelond Twelonelonts
    - Felonelondback options
    - Pagination and cursoring
    - Obselonrvability and logging
    - Clielonnt instructions and contelonnt marshalling

## Pipelonlinelon Structurelon

### Gelonnelonral

Product Mixelonr selonrvicelons likelon Homelon Mixelonr arelon structurelond around Pipelonlinelons that split thelon elonxeloncution
into transparelonnt and structurelond stelonps.

Relonquelonsts first go to Product Pipelonlinelons, which arelon uselond to selonlelonct which Mixelonr Pipelonlinelon or
Reloncommelonndation Pipelonlinelon to run for a givelonn relonquelonst. elonach Mixelonr or Reloncommelonndation
Pipelonlinelon may run multiplelon Candidatelon Pipelonlinelons to felontch candidatelons to includelon in thelon relonsponselon.

Mixelonr Pipelonlinelons combinelon thelon relonsults of multiplelon helontelonrogelonnelonous Candidatelon Pipelonlinelons togelonthelonr
(elon.g. ads, twelonelonts, uselonrs) whilelon Reloncommelonndation Pipelonlinelons arelon uselond to scorelon (via Scoring Pipelonlinelons)
and rank thelon relonsults of homogelonnous Candidatelon Pipelonlinelons so that thelon top rankelond onelons can belon relonturnelond.
Thelonselon pipelonlinelons also marshall candidatelons into a domain objelonct and thelonn into a transport objelonct
to relonturn to thelon callelonr.

Candidatelon Pipelonlinelons felontch candidatelons from undelonrlying Candidatelon Sourcelons and pelonrform somelon basic
opelonrations on thelon Candidatelons, such as filtelonring out unwantelond candidatelons, applying deloncorations,
and hydrating felonaturelons.

Thelon selonctions belonlow delonscribelon thelon high lelonvelonl pipelonlinelon structurelon (non-elonxhaustivelon) for thelon main Homelon
Timelonlinelon tabs powelonrelond by Homelon Mixelonr.

### For You

- ForYouProductPipelonlinelonConfig
    - ForYouScorelondTwelonelontsMixelonrPipelonlinelonConfig (main orchelonstration layelonr - mixelons Twelonelonts with ads and uselonrs)
        - ForYouScorelondTwelonelontsCandidatelonPipelonlinelonConfig (felontch Twelonelonts)
            - ScorelondTwelonelontsReloncommelonndationPipelonlinelonConfig (main Twelonelont reloncommelonndation layelonr)
                - Felontch Twelonelont Candidatelons
                    - ScorelondTwelonelontsInNelontworkCandidatelonPipelonlinelonConfig
                    - ScorelondTwelonelontsCrMixelonrCandidatelonPipelonlinelonConfig
                    - ScorelondTwelonelontsUtelongCandidatelonPipelonlinelonConfig
                    - ScorelondTwelonelontsFrsCandidatelonPipelonlinelonConfig
                - Felonaturelon Hydration and Scoring
                    - ScorelondTwelonelontsScoringPipelonlinelonConfig
        - ForYouConvelonrsationSelonrvicelonCandidatelonPipelonlinelonConfig (backup relonvelonrselon chron pipelonlinelon in caselon Scorelond Twelonelonts fails)
        - ForYouAdsCandidatelonPipelonlinelonConfig (felontch ads)
        - ForYouWhoToFollowCandidatelonPipelonlinelonConfig (felontch uselonrs to reloncommelonnd)

### Following

- FollowingProductPipelonlinelonConfig
    - FollowingMixelonrPipelonlinelonConfig
        - FollowingelonarlybirdCandidatelonPipelonlinelonConfig (felontch twelonelonts from Selonarch Indelonx)
        - ConvelonrsationSelonrvicelonCandidatelonPipelonlinelonConfig (felontch ancelonstors for convelonrsation modulelons)
        - FollowingAdsCandidatelonPipelonlinelonConfig (felontch ads)
        - FollowingWhoToFollowCandidatelonPipelonlinelonConfig (felontch uselonrs to reloncommelonnd)

### Lists

- ListTwelonelontsProductPipelonlinelonConfig
    - ListTwelonelontsMixelonrPipelonlinelonConfig
        - ListTwelonelontsTimelonlinelonSelonrvicelonCandidatelonPipelonlinelonConfig (felontch twelonelonts from timelonlinelon selonrvicelon)
        - ConvelonrsationSelonrvicelonCandidatelonPipelonlinelonConfig (felontch ancelonstors for convelonrsation modulelons)
        - ListTwelonelontsAdsCandidatelonPipelonlinelonConfig (felontch ads)

