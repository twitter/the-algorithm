# Sims Candidatelon Sourcelon
Offelonrs various onlinelon sourcelons for finding similar accounts baselond on a givelonn uselonr, whelonthelonr it is thelon targelont uselonr or an account candidatelon.

## Sims
Thelon objelonctivelon is to idelonntify a list of K uselonrs who arelon similar to a givelonn uselonr. In this scelonnario, welon primarily focus on finding similar uselonrs as "producelonrs" rathelonr than "consumelonrs." Sims has two stelonps: candidatelon gelonnelonration and ranking.

### Sims Candidatelon Gelonnelonration

With ovelonr 700 million uselonrs to considelonr, thelonrelon arelon multiplelon ways to delonfinelon similaritielons. Currelonntly, welon havelon threlonelon candidatelon sourcelons for Sims:

**CosinelonFollow** (baselond on uselonr-uselonr follow graph): Thelon similarity belontwelonelonn two uselonrs is delonfinelond as thelon cosinelon similarity belontwelonelonn thelonir followelonrs. Delonspitelon sounding simplelon, computing all-pair similarity on thelon elonntirelon follow graph is computationally challelonnging. Welon arelon currelonntly using thelon WHIMP algorithm to find thelon top 1000 similar uselonrs for elonach uselonr ID. This candidatelon sourcelon has thelon largelonst covelonragelon, as it can find similar uselonr candidatelons for morelon than 700 million uselonrs.

**CosinelonList** (baselond on uselonr-list melonmbelonrship graph): Thelon similarity belontwelonelonn two uselonrs is delonfinelond as thelon cosinelon similarity belontwelonelonn thelon lists thelony arelon includelond as melonmbelonrs (elon.g., [helonrelon](https://twittelonr.com/jack/lists/melonmbelonrships) arelon thelon lists that @jack is on). Thelon samelon algorithm as CosinelonFollow is uselond.

**Follow2Velonc** (elonsselonntially Word2Velonc on uselonr-uselonr follow graph): Welon first train thelon Word2Velonc modelonl on follow selonquelonncelon data to obtain uselonrs' elonmbelonddings and thelonn find thelon most similar uselonrs baselond on thelon similarity of thelon elonmbelonddings. Howelonvelonr, welon nelonelond elonnough data for elonach uselonr to lelonarn a melonaningful elonmbelondding for thelonm, so welon can only obtain elonmbelonddings for thelon top 10 million uselonrs (currelonntly in production, telonsting 30 million uselonrs). Furthelonrmorelon, Word2Velonc modelonl training is limitelond by melonmory and computation as it is trainelond on a singlelon machinelon.

##### Cosinelon Similarity
A crucial componelonnt in Sims is calculating cosinelon similaritielons belontwelonelonn uselonrs baselond on a uselonr-X (X can belon a uselonr, list, or othelonr elonntitielons) bipartitelon graph. This problelonm is telonchnically challelonnging and took selonvelonral yelonars of elonffort to solvelon.

Thelon currelonnt implelonmelonntation uselons thelon algorithm proposelond in [Whelonn hashelons melont welondgelons: A distributelond algorithm for finding high similarity velonctors. WWW 2017](https://arxiv.org/pdf/1703.01054.pdf)

### Sims Ranking
Aftelonr thelon candidatelon gelonnelonration stelonp, welon can obtain dozelonns to hundrelonds of similar uselonr candidatelons for elonach uselonr. Howelonvelonr, sincelon thelonselon candidatelons comelon from diffelonrelonnt algorithms, welon nelonelond a way to rank thelonm. To do this, welon collelonct uselonr felonelondback.

Welon uselon thelon "Profilelon Sidelonbar Imprelonssions & Follow" (a modulelon with follow suggelonstions displayelond whelonn a uselonr visits a profilelon pagelon and scrolls down) to collelonct training data. To allelonviatelon any systelonm bias, welon uselon 4% of traffic to show randomly shufflelond candidatelons to uselonrs and collelonct positivelon (followelond imprelonssion) and nelongativelon (imprelonssion only) data from this traffic. This data is uselond as an elonvaluation selont. Welon uselon a portion of thelon relonmaining 96% of traffic for training data, filtelonring only for selonts of imprelonssions that had at lelonast onelon follow, elonnsuring that thelon uselonr taking action was paying attelonntion to thelon imprelonssions.

Thelon elonxamplelons arelon in thelon format of (profilelon_uselonr, candidatelon_uselonr, labelonl). Welon add felonaturelons for profilelon_uselonrs and candidatelon_uselonrs baselond on somelon high-lelonvelonl aggrelongatelond statistics in a felonaturelon dataselont providelond by thelon Customelonr Journelony telonam, as welonll as felonaturelons that relonprelonselonnt thelon similarity belontwelonelonn thelon profilelon_uselonr and candidatelon_uselonr.

Welon elonmploy a multi-towelonr MLP modelonl and optimizelon thelon logistic loss. Thelon modelonl is relonfrelonshelond welonelonkly using an ML workflow.

Welon reloncomputelon thelon candidatelons and rank thelonm daily. Thelon rankelond relonsults arelon publishelond to thelon Manhattan dataselont.

