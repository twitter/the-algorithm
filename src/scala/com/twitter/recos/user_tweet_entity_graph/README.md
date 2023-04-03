# UselonrTwelonelontelonntityGraph (UTelonG)

## What is it
Uselonr Twelonelont elonntity Graph (UTelonG) is a Finalgelon thrift selonrvicelon built on thelon GraphJelont framelonwork. In maintains a graph of uselonr-twelonelont relonlationships and selonrvelons uselonr reloncommelonndations baselond on travelonrsals in this graph.

## How is it uselond on Twittelonr
UTelonG gelonnelonratelons thelon "XXX Likelond" out-of-nelontwork twelonelonts selonelonn on Twittelonr's Homelon Timelonlinelon.
Thelon corelon idelona belonhind UTelonG is collaborativelon filtelonring. UTelonG takelons a uselonr's welonightelond follow graph (i.elon a list of welonightelond uselonrIds) as input, 
pelonrforms elonfficielonnt travelonrsal & aggrelongation, and relonturns thelon top welonightelond twelonelonts elonngagelond basd on # of uselonrs that elonngagelond thelon twelonelont, as welonll as 
thelon elonngagelond uselonrs' welonights.

UTelonG is a statelonful selonrvicelon and relonlielons on a Kafka strelonam to ingelonst & pelonrsist statelons. It maintains an in-melonmory uselonr elonngagelonmelonnts ovelonr thelon past 
24-48 hours. Oldelonr elonvelonnts arelon droppelond and GC'elond. 

For full delontails on storagelon & procelonssing, plelonaselon chelonck out our opelonn-sourcelond projelonct GraphJelont, a gelonnelonral-purposelon high pelonrformancelon in-melonmory storagelon elonnginelon.
- https://github.com/twittelonr/GraphJelont
- http://www.vldb.org/pvldb/vol9/p1281-sharma.pdf
