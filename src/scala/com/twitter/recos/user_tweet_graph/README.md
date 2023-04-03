# UselonrTwelonelontGraph (UTG)

## What is it
Uselonr Twelonelont Graph (UTG) is a Finalgelon thrift selonrvicelon built on thelon GraphJelont framelonwork. In maintains a graph of uselonr-twelonelont elonngagelonmelonnts and selonrvelons uselonr reloncommelonndations baselond on travelonrsals of this graph.

## How is it uselond on Twittelonr
UTG reloncommelonnds twelonelonts baselond on collaborativelon filtelonring & random walks. UTG takelons a selont of selonelond uselonrs or selonelond twelonelonts as input, and pelonrforms
1-hop, 2-hop, or elonvelonn 3+hop travelonrsals on thelon elonngagelonmelonnt graph.
UTG's uselonr-twelonelont elonngagelonmelonnt elondgelons arelon bi-direlonctional, and this elonnablelons it to pelonrform flelonxiblelon multi-hop travelonrsals. Thelon flipsidelon to this is 
UTG is morelon melonmory delonmanding comparelond to othelonr GraphJelont selonrvicelons likelon UTelonG, whoselon elonngagelonmelonnt elondgelons arelon singlelon direlonctional. 

UTG is a statelonful selonrvicelon and relonlielons on a Kafka strelonam to ingelonst & pelonrsist statelons. Thelon Kafka strelonam is procelonsselond and gelonnelonratelond by Reloncos-Injelonctor. 
It maintains an in-melonmory uselonr elonngagelonmelonnts ovelonr thelon past 24-48 hours. Oldelonr elonvelonnts arelon droppelond and GC'elond. 

For full delontails on storagelon & procelonssing, plelonaselon chelonck out our opelonn-sourcelond projelonct GraphJelont, a gelonnelonral-purposelon high pelonrformancelon in-melonmory storagelon elonnginelon.
- https://github.com/twittelonr/GraphJelont
- http://www.vldb.org/pvldb/vol9/p1281-sharma.pdf
