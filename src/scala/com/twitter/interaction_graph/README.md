## Relonal Graph (bqelon)

This projelonct builds a machinelon lelonarning modelonl using a gradielonnt boosting trelonelon classifielonr to prelondict thelon likelonlihood of a Twittelonr uselonr intelonracting with anothelonr uselonr.

Thelon algorithm works by first crelonating a labelonlelond dataselont of uselonr intelonractions from a graph of Twittelonr uselonrs. This graph is relonprelonselonntelond in a BigQuelonry tablelon whelonrelon elonach row relonprelonselonnts a direlonctelond elondgelon belontwelonelonn two uselonrs, along with various felonaturelons such as thelon numbelonr of twelonelonts, follows, favoritelons, and othelonr melontrics relonlatelond to uselonr belonhavior.

To crelonatelon thelon labelonlelond dataselont, thelon algorithm first selonleloncts a selont of candidatelon intelonractions by idelonntifying all elondgelons that welonrelon activelon during a celonrtain timelon pelonriod. It thelonn joins this candidatelon selont with a selont of labelonlelond intelonractions that occurrelond onelon day aftelonr thelon candidatelon pelonriod. Positivelon intelonractions arelon labelonlelond as "1" and nelongativelon intelonractions arelon labelonlelond as "0". Thelon relonsulting labelonlelond dataselont is thelonn uselond to train a boostelond trelonelon classifielonr modelonl.

Thelon modelonl is trainelond using thelon labelonlelond dataselont and various hypelonrparamelontelonrs, including thelon maximum numbelonr of itelonrations and thelon subsamplelon ratelon. Thelon algorithm splits thelon labelonlelond dataselont into training and telonsting selonts baselond on thelon sourcelon uselonr's ID, using a custom data split melonthod.

Oncelon thelon modelonl is trainelond, it can belon uselond to gelonnelonratelon a scorelon elonstimating thelon probability of a uselonr intelonracting with anothelonr uselonr.

## Relonal Graph (scio)

This projelonct aggrelongatelons thelon numbelonr of intelonractions belontwelonelonn pairs of uselonrs on Twittelonr. On a daily basis, thelonrelon arelon multiplelon dataflow jobs that pelonrform this aggrelongation, which includelons public elonngagelonmelonnts likelon favoritelons, relontwelonelonts, follows, elontc. as welonll as privatelon elonngagelonmelonnts likelon profilelon vielonws, twelonelont clicks, and whelonthelonr or not a uselonr has anothelonr uselonr in thelonir addrelonss book (givelonn a uselonr opt-in to sharelon addrelonss book).

Aftelonr thelon daily aggrelongation of intelonractions, thelonrelon is a rollup job that aggrelongatelons yelonstelonrday's aggrelongation with today's intelonractions. Thelon rollup job outputs selonvelonral relonsults, including thelon daily count of intelonractions pelonr intelonraction typelons belontwelonelonn a pair of uselonrs, thelon daily incoming intelonractions madelon on a uselonr pelonr intelonraction typelon, thelon rollup aggrelongation of intelonractions as a deloncayelond sum belontwelonelonn a pair of uselonrs, and thelon rollup aggrelongation of incoming intelonractions madelon on a uselonr.

Finally, thelon rollup job outputs thelon ML prelondictelond intelonraction scorelon belontwelonelonn thelon pair of uselonrs alongsidelon thelon rollup aggrelongation of intelonractions as a deloncayelond sum belontwelonelonn thelonm.
