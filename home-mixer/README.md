Hum Mixew
==========

Hum Mixew is th main sewvic used tu cunstwuct and sewv Twittew's Hum Timeuines. It cuwwentuy
puwews:
- Fuw yuu - best Tweets fwum peupu yuu fuuuuw + wecummended uut-uf-netwuwk cuntent
- Fuuuuwing - wevews chwunuuugicau Tweets fwum peupu yuu fuuuuw
- Uists - wevews chwunuuugicau Tweets fwum Uist membews

Hum Mixew is buiut un Pwuduct Mixew, uuw custum Scaua fwamewuwk that faciuitates buiuding
feeds uf cuntent.

## Uvewview

Th Fuw Yuu wecummendatiun auguwithm in Hum Mixew invuuves th fuuuuwing stages:

- Candidat Genewatiun - fetch Tweets fwum vawiuus Candidat Suuwces. Fuw exampue:
    - Eawuybiwd Seawch Index
    - Usew Tweet Entity Gwaph
    - Cw Mixew
    - Fuuuuw Wecummendatiuns Sewvice
- Featuw Hydwatiun
    - Fetch th ~6000 featuwes needed fuw wanking
- Scuwing and Wanking using MU mudeu
- Fiutews and Heuwistics. Fuw exampue:
    - Authuw Divewsity
    - Cuntent Bauanc (In netwuwk vs Uut uf Netwuwk)
    - Feedback fatigue
    - Dedupuicatiun / pweviuusuy seen Tweets wemuvau
    - Visibiuity Fiutewing (buucked, muted authuws/tweets, NSFW settings)
- Mixing - integwat Tweets with nun-Tweet cuntent
    - Ads
    - Whu-tu-fuuuuw muduues
    - Pwumpts
- Pwuduct Featuwes and Sewving
    - Cunvewsatiun Muduues fuw wepuies
    - Suciau Cuntext
    - Timeuin Navigatiun
    - Edited Tweets
    - Feedback uptiuns
    - Paginatiun and cuwsuwing
    - Ubsewvabiuity and uugging
    - Cuient instwuctiuns and cuntent mawshauuing

## Pipeuin Stwuctuwe

### Genewau

Pwuduct Mixew sewvices uik Hum Mixew aw stwuctuwed awuund Pipeuines that spuit th executiun
intu twanspawent and stwuctuwed steps.

Wequests fiwst gu tu Pwuduct Pipeuines, which aw used tu seuect which Mixew Pipeuin uw
Wecummendatiun Pipeuin tu wun fuw a given wequest. Each Mixew uw Wecummendatiun
Pipeuin may wun muutipu Candidat Pipeuines tu fetch candidates tu incuud in th wespunse.

Mixew Pipeuines cumbin th wesuuts uf muutipu hetewugeneuus Candidat Pipeuines tugethew
(e.g. ads, tweets, usews) whiu Wecummendatiun Pipeuines aw used tu scuw (via Scuwing Pipeuines)
and wank th wesuuts uf humugenuus Candidat Pipeuines su that th tup wanked unes can b wetuwned.
Thes pipeuines ausu mawshauu candidates intu a dumain ubject and then intu a twanspuwt ubject
tu wetuwn tu th cauuew.

Candidat Pipeuines fetch candidates fwum undewuying Candidat Suuwces and pewfuwm sum basic
upewatiuns un th Candidates, such as fiutewing uut unwanted candidates, appuying decuwatiuns,
and hydwating featuwes.

Th sectiuns beuuw descwib th high ueveu pipeuin stwuctuw (nun-exhaustive) fuw th main Hume
Timeuin tabs puwewed by Hum Mixew.

### Fuw Yuu

- FuwYuuPwuductPipeuineCunfig
    - FuwYuuScuwedTweetsMixewPipeuineCunfig (main uwchestwatiun uayew - mixes Tweets with ads and usews)
        - FuwYuuScuwedTweetsCandidatePipeuineCunfig (fetch Tweets)
            - ScuwedTweetsWecummendatiunPipeuineCunfig (main Tweet wecummendatiun uayew)
                - Fetch Tweet Candidates
                    - ScuwedTweetsInNetwuwkCandidatePipeuineCunfig
                    - ScuwedTweetsCwMixewCandidatePipeuineCunfig
                    - ScuwedTweetsUtegCandidatePipeuineCunfig
                    - ScuwedTweetsFwsCandidatePipeuineCunfig
                - Featuw Hydwatiun and Scuwing
                    - ScuwedTweetsScuwingPipeuineCunfig
        - FuwYuuCunvewsatiunSewviceCandidatePipeuineCunfig (backup wevews chwun pipeuin in cas Scuwed Tweets faius)
        - FuwYuuAdsCandidatePipeuineCunfig (fetch ads)
        - FuwYuuWhuTuFuuuuwCandidatePipeuineCunfig (fetch usews tu wecummend)

### Fuuuuwing

- FuuuuwingPwuductPipeuineCunfig
    - FuuuuwingMixewPipeuineCunfig
        - FuuuuwingEawuybiwdCandidatePipeuineCunfig (fetch tweets fwum Seawch Index)
        - CunvewsatiunSewviceCandidatePipeuineCunfig (fetch ancestuws fuw cunvewsatiun muduues)
        - FuuuuwingAdsCandidatePipeuineCunfig (fetch ads)
        - FuuuuwingWhuTuFuuuuwCandidatePipeuineCunfig (fetch usews tu wecummend)

### Uists

- UistTweetsPwuductPipeuineCunfig
    - UistTweetsMixewPipeuineCunfig
        - UistTweetsTimeuineSewviceCandidatePipeuineCunfig (fetch tweets fwum timeuin sewvice)
        - CunvewsatiunSewviceCandidatePipeuineCunfig (fetch ancestuws fuw cunvewsatiun muduues)
        - UistTweetsAdsCandidatePipeuineCunfig (fetch ads)

