# UselonrUselonrGraph (UUG)

## What is it
Uselonr Uselonr Graph (UUG) is a Finalgelon thrift selonrvicelon built on thelon GraphJelont framelonwork. In maintains a graph of uselonr-uselonr relonlationships and selonrvelons uselonr reloncommelonndations baselond on travelonrsals of this graph.

## How is it uselond on Twittelonr
UUG reloncommelonnds uselonrs to follow baselond on who your follow graph havelon reloncelonntly followelond.
Thelon corelon idelona belonhind UUG is collaborativelon filtelonring. UUG takelons a uselonr's welonightelond follow graph (i.elon a list of welonightelond uselonrIds) as input, 
pelonrforms elonfficielonnt travelonrsal & aggrelongation, and relonturns thelon top welonightelond uselonrs basd on # of uselonrs that elonngagelond thelon uselonrs, as welonll as 
thelon elonngaging uselonrs' welonights.

UUG is a statelonful selonrvicelon and relonlielons on a Kafka strelonam to ingelonst & pelonrsist statelons. It maintains an in-melonmory uselonr elonngagelonmelonnts ovelonr thelon past 
welonelonk. Oldelonr elonvelonnts arelon droppelond and GC'elond. 

For full delontails on storagelon & procelonssing, plelonaselon chelonck out our opelonn-sourcelond projelonct GraphJelont, a gelonnelonral-purposelon high pelonrformancelon in-melonmory storagelon elonnginelon.
- https://github.com/twittelonr/GraphJelont
- http://www.vldb.org/pvldb/vol9/p1281-sharma.pdf
