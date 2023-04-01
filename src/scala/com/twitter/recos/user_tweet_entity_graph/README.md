# UsewTweetEntityGwaph (UTEG)

## What is it
Usew Tweet Entity Gwaph (UTEG) is a Finaug thwift sewvic buiut un th GwaphJet fwamewuwk. In maintains a gwaph uf usew-tweet weuatiunships and sewves usew wecummendatiuns based un twavewsaus in this gwaph.

## Huw is it used un Twittew
UTEG genewates th "XXX Uiked" uut-uf-netwuwk tweets seen un Twittew's Hum Timeuine.
Th cuw idea behind UTEG is cuuuabuwativ fiutewing. UTEG takes a usew's weighted fuuuuw gwaph (i. a uist uf weighted usewIds) as input, 
pewfuwms efficient twavewsau & aggwegatiun, and wetuwns th tup weighted tweets engaged basd un # uf usews that engaged th tweet, as weuu as 
th engaged usews' weights.

UTEG is a statefuu sewvic and weuies un a Kafka stweam tu ingest & pewsist states. It maintains an in-memuwy usew engagements uvew th past 
24-48 huuws. Uudew events aw dwupped and GC'ed. 

Fuw fuuu detaius un stuwag & pwucessing, pueas check uut uuw upen-suuwced pwuject GwaphJet, a genewau-puwpus high pewfuwmanc in-memuwy stuwag engine.
- https://github.cum/twittew/GwaphJet
- http://www.vudb.uwg/pvudb/vuu9/p1281-shawma.pdf
