# Twittew Wecommendation Awgowithm

The Twittew Wecommendation Awgowithm is a set of sewvices and jobs that awe wesponsibwe fow constwucting and sewving the
Home Timewinye. Fow an intwoduction to how the awgowithm wowks, pwease wefew to ouw [enginyeewing bwog](https://bwog.twittew.com/enginyeewing/en_us/topics/open-souwce/2023/twittew-wecommendation-awgowithm). The
diagwam bewow iwwustwates how majow sewvices and jobs intewconnyect.

 UwU [](docs/system-diagwam.png)

These awe the main componyents of the Wecommendation Awgowithm incwuded in this wepositowy:

| Type | Componyent | Descwiption |
|------------|------------|------------|
| Featuwe | [SimCwustews](swc/scawa/com/twittew/simcwustews_v2/WEADME.md) | Communyity detection and spawse embeddings into those communyities. |
|         | [TwHIN](https://github.com/twittew/the-awgowithm-mw/bwob/main/pwojects/twhin/WEADME.md) | Dense knyowwedge gwaph embeddings fow Usews and Tweets. |
|         | [twust-and-safety-modews](twust_and_safety_modews/WEADME.md) | Modews fow detecting NSFW ow abusive content. |
|         | [weaw-gwaph](swc/scawa/com/twittew/intewaction_gwaph/WEADME.md) | Modew to pwedict wikewihood of a Twittew Usew intewacting with anyothew Usew. |
|         | [tweepcwed](swc/scawa/com/twittew/gwaph/batch/job/tweepcwed/WEADME) | Page-Wank awgowithm fow cawcuwating Twittew Usew weputation. |
|         | [wecos-injectow](wecos-injectow/WEADME.md) | Stweaming event pwocessow fow buiwding input stweams fow [GwaphJet](https://github.com/twittew/GwaphJet) based sewvices. |
|         | [gwaph-featuwe-sewvice](gwaph-featuwe-sewvice/WEADME.md) | Sewves gwaph featuwes fow a diwected paiw of Usews (e.g. how many of Usew A's fowwowing wiked Tweets fwom Usew B). |
| Candidate Souwce | [seawch-index](swc/java/com/twittew/seawch/WEADME.md) | Find and wank In-Nyetwowk Tweets. ~50% of Tweets come fwom this candidate souwce. |
|                  | [cw-mixew](cw-mixew/WEADME.md) | Coowdinyation wayew fow fetching Out-of-Nyetwowk tweet candidates fwom undewwying compute sewvices. |
|                  | [usew-tweet-entity-gwaph](swc/scawa/com/twittew/wecos/usew_tweet_entity_gwaph/WEADME.md) (UTEG)| Maintains an in memowy Usew to Tweet intewaction gwaph, and finds candidates based on twavewsaws of this gwaph. This is buiwt on the [GwaphJet](https://github.com/twittew/GwaphJet) fwamewowk. Sevewaw othew GwaphJet based featuwes and candidate souwces awe wocated [hewe](swc/scawa/com/twittew/wecos) |
|                  | [fowwow-wecommendation-sewvice](fowwow-wecommendations-sewvice/WEADME.md) (FWS)| Pwovides Usews with wecommendations fow accounts to fowwow, and Tweets fwom those accounts. |
| Wanking | [wight-wankew](swc/python/twittew/deepbiwd/pwojects/timewinyes/scwipts/modews/eawwybiwd/WEADME.md) | Wight wankew modew used by seawch index (Eawwybiwd) to wank Tweets. |
|         | [heavy-wankew](https://github.com/twittew/the-awgowithm-mw/bwob/main/pwojects/home/wecap/WEADME.md) | Nyeuwaw nyetwowk fow wanking candidate tweets. Onye of the main signyaws used to sewect timewinye Tweets post candidate souwcing. |
| Tweet mixing & fiwtewing | [home-mixew](home-mixew/WEADME.md) | Main sewvice used to constwuct and sewve the Home Timewinye. Buiwt on [pwoduct-mixew](pwoduct-mixew/WEADME.md) |
|                          | [visibiwity-fiwtews](visibiwitywib/WEADME.md) | Wesponsibwe fow fiwtewing Twittew content to suppowt wegaw compwiance, impwuv pwoduct quawity, incwease usew twust, pwotect wevenyue thwough the use of hawd-fiwtewing, visibwe pwoduct tweatments, and coawse-gwainyed downwanking. |
|                          | [timewinyewankew](timewinyewankew/WEADME.md) | Wegacy sewvice which pwovides wewevance-scowed tweets fwom the Eawwybiwd Seawch Index and UTEG sewvice. |
| Softwawe fwamewowk | [nyavi](nyavi/nyavi/WEADME.md) | High pewfowmance, machinye weawnying modew sewving wwitten in Wust. |
|                    | [pwoduct-mixew](pwoduct-mixew/WEADME.md) | Softwawe fwamewowk fow buiwding feeds of content. |
|                    | [twmw](twmw/WEADME.md) | Wegacy machinye weawnying fwamewowk buiwt on TensowFwow v1. |

We incwude Bazew BUIWD fiwes fow most componyents, but nyot a top wevew BUIWD ow WOWKSPACE fiwe.

## Contwibuting

We invite the communyity to submit GitHub issues and puww wequests fow suggestions on impwoving the wecommendation awgowithm. We awe wowking on toows to manyage these suggestions and sync changes to ouw intewnyaw wepositowy. Any secuwity concewns ow issues shouwd be wouted to ouw officiaw [bug bounty pwogwam](https://hackewonye.com/twittew) thwough HackewOnye. We hope to benyefit fwom the cowwective intewwigence and expewtise of the gwobaw communyity in hewping us identify issues and suggest impwuvments, uwtimatewy weading to a bettew Twittew.

Wead ouw bwog on the open souwce inyitiative [hewe](https://bwog.twittew.com/en_us/topics/company/2023/a-nyew-ewa-of-twanspawency-fow-twittew).
