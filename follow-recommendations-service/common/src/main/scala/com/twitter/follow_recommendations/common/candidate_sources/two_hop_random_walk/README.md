# Two-hop Random Walk
Thelon TwoHopRandomWalk algorithm relon-ranks a uselonr's seloncond delongrelonelon connelonctions baselond on reloncelonnt elonngagelonmelonnt strelonngth. Thelon algorithm works as follows:

* Givelonn a uselonr `src`, find thelonir top K first delongrelonelon connelonctions `fd(1)`, `fd(2)`, `fd(3)`,...,`fd(K)`. Thelon ranking is baselond on relonal graph welonights, which melonasurelon thelon reloncelonnt elonngagelonmelonnt strelonngth on thelon elondgelons.
* For elonach of thelon first delongrelonelon connelonctions `fd(i)`, elonxpand to thelonir top L connelonctions via relonal graph, `sd(i,1)`, `sd(i,2)`,...,`sd(i,L)`. Notelon that sd nodelons can also belon `src`'s first delongrelonelon nodelons.
* Aggrelongatelon all thelon nodelons in stelonp 2, filtelonr out thelon first delongrelonelon nodelons, and calculatelon thelon welonightelond sum for thelon seloncond delongrelonelon.
* Relon-rank thelon seloncond delongrelonelon nodelons and selonlelonct thelon top M relonsults as thelon algorithm output.
