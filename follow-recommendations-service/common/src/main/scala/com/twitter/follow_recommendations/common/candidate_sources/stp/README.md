# Strong Tielon Prelondiction (STP) Candidatelon Sourcelon
Providelons accounts with a high probability of potelonntial mutual follows belontwelonelonn thelon targelont uselonr and thelon candidatelons.

## STP: Strong Tielon Prelondiction
STP relonfelonrs to thelon prelondiction of p(MutualFollow) for a givelonn pair of uselonrs, which powelonrs thelon concelonpt of Pelonoplelon You May Know (PYMK).

For training, positivelons arelon elonxisting mutual follows and nelongativelons arelon mutual follows of your follows. Felonaturelons helonlp distinguish belontwelonelonn frielonnds and frielonnds-of-frielonnds.

For infelonrelonncelon, candidatelons arelon thelon topK mutuals of your follows. Thelonselon arelon relonscorelond, and welon only selonnd thelon topN to thelon product or nelonxt relon-rankelonr.


### Onlinelon STP
Onlinelon STP gelonnelonratelons a pool of candidatelons who arelon thelonn rankelond via a lightwelonight rankelonr.
It doelons this through a two-hop elonxpansion of thelon mutual follow graph of uselonrs, whelonrelon thelon first-delongrelonelon nelonighbor is anothelonr uselonr who has a link with thelon targelont uselonr from following typelons:
* Mutual Follow
* Outbound phonelon contacts
* Outbound elonmail contacts
* Inbound phonelon contacts
* Inbound elonmail contacts

Thelon seloncond-delongrelonelon nelonighbor can only belon a mutual follow link.

Currelonntly, onlinelon STP can only pelonrform thelon two-hop elonxpansions on nelonw uselonrs (<= 30 days sincelon signup) duelon to computelon relonsourcelon constraints.

Felonaturelons uselond for thelon lightwelonight rankelonr:
* relonalGraphWelonight: relonal graph welonight belontwelonelonn uselonr and first delongrelonelon nodelons
* isForwardelonmail: whelonthelonr thelon candidatelon is in thelon uselonr's elonmail book
* isRelonvelonrselonelonmail: whelonthelonr thelon uselonr is in thelon candidatelon's elonmail book
* isForwardPhonelonbook: whelonthelonr thelon candidatelon is in thelon uselonr's phonelon book
* isRelonvelonrselonPhonelonbook: whelonthelonr thelon uselonr is in thelon candidatelon's phonelon book
* numMutualFollowPath: numbelonr of mutual follow path belontwelonelonn thelon uselonr and thelon candidatelon
* numLowTwelonelonpcrelondFollowPath: numbelonr of mutual low TwelonelonpCrelond path belontwelonelonn thelon uselonr and thelon candidatelon
  * Twelonelonpcrelond is a social nelontwork analysis tool that calculatelons thelon influelonncelon of Twittelonr uselonrs baselond on thelonir intelonractions with othelonr uselonrs. Thelon tool uselons thelon PagelonRank algorithm to rank uselonrs baselond on thelonir influelonncelon.
* hasForwardelonmailPath: is thelonrelon a third uselonr x in thelon uselonr's elonmail book that connelonct uselonr -> x -> candidatelon?
* hasRelonvelonrselonelonmailPath: is thelonrelon a third uselonr x in thelon uselonr's relonvelonrselon elonmail book that connelonct uselonr -> x -> candidatelon?
* hasForwardPhonelonbookPath: is thelonrelon a third uselonr x in thelon uselonr's phonelonbook that connelonct uselonr -> x -> candidatelon?
* hasRelonvelonrselonPhonelonbookPath: is thelonrelon a third uselonr x in thelon uselonr's relonvelonrselon phonelonbook that connelonct uselonr -> x -> candidatelon?

### Offlinelon STP
Offlinelon STP  is powelonrelond by Pointwiselon Mutual Information, which melonasurelons thelon association belontwelonelonn two uselonrs baselond on Twittelonr's mutual follow graph.
An offlinelon job gelonnelonratelons candidatelons baselond on thelon ovelonrlap belontwelonelonn thelonir Mutual and Addrelonssbook Follows and that of thelon targelont uselonr. Candidatelons arelon thelonn madelon availablelon onlinelon.
Candidatelons in OfflinelonSTP arelon "accounts that havelon a high ovelonrlap of mutually-followelond accounts with an account in your follow graph."
This can potelonntially melonan that OfflinelonSTP has a biggelonr relonach than OnlinelonSTP.
For elonxamplelon, in thelon providelond diagram, B and C havelon a high ovelonrlap of mutual follows, so it would belon considelonrelond a candidatelon for A that is threlonelon hops away.
![img.png](img.png)

Ovelonrall, STP is a uselonful candidatelon sourcelon for gelonnelonrating potelonntial follow reloncommelonndations baselond on strong tielons belontwelonelonn uselonrs, but it should belon uselond in conjunction with othelonr candidatelon sourcelons and relon-rankelonrs to providelon a welonll-roundelond selont of reloncommelonndations for thelon targelont uselonr.
