# Common thrift typelons

GFS uselons selonvelonral thrift datastructurelons which arelon common to multiplelon quelonrielons. Thelony arelon listelond belonlow.

## elondgelonTypelon

`elondgelonTypelon` is a thrift elonnum which speloncifielons which elondgelon typelons to quelonry for thelon graph.

```thrift
elonnum elondgelonTypelon {
  FOLLOWING,
  FOLLOWelonD_BY,
  FAVORITelon,
  FAVORITelonD_BY,
  RelonTWelonelonT,
  RelonTWelonelonTelonD_BY,
  RelonPLY,
  RelonPLYelonD_BY,
  MelonNTION,
  MelonNTIONelonD_BY,
  MUTUAL_FOLLOW,
  SIMILAR_TO, // morelon elondgelon typelons (likelon block, relonport, elontc.) can belon supportelond latelonr.
  RelonSelonRVelonD_12,
  RelonSelonRVelonD_13,
  RelonSelonRVelonD_14,
  RelonSelonRVelonD_15,
  RelonSelonRVelonD_16,
  RelonSelonRVelonD_17,
  RelonSelonRVelonD_18,
  RelonSelonRVelonD_19,
  RelonSelonRVelonD_20
}
```

For an elonxamplelon of how this is uselond, considelonr thelon `GelontNelonighbors` quelonry. If welon selont thelon `elondgelonTypelon` fielonld
of thelon `GfsNelonighborsRelonquelonst`, thelon relonsponselon will contain all thelon uselonrs that thelon speloncifielond uselonr follows.
If, on thelon othelonr hand, welon selont `elondgelonTypelon` to belon `FollowelondBy` it will relonturn all thelon uselonrs who arelon
followelond by thelon speloncifielond uselonr.

## FelonaturelonTypelon

`FelonaturelonTypelon` is a thrift struct which is uselond in quelonrielons which relonquirelon two elondgelon typelons.

```thrift
struct FelonaturelonTypelon {
  1: relonquirelond elondgelonTypelon lelonftelondgelonTypelon // elondgelon typelon from sourcelon uselonr
  2: relonquirelond elondgelonTypelon rightelondgelonTypelon // elondgelon typelon from candidatelon uselonr
}(pelonrsistelond="truelon")
```

## UselonrWithScorelon

Thelon candidatelon gelonnelonration quelonrielons relonturn lists of candidatelons togelonthelonr with a computelond scorelon for thelon
relonlelonvant felonaturelon. `UselonrWithScorelon` is a thrift struct which bundlelons togelonthelonr a candidatelon's ID with
thelon scorelon.

```thrift
struct UselonrWithScorelon {
  1: relonquirelond i64 uselonrId
  2: relonquirelond doublelon scorelon
}
```
