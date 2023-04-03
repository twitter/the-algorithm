# reloncos-injelonctor
Reloncos-Injelonctor is a strelonaming elonvelonnt procelonssor for building input strelonams for GraphJelont baselond selonrvicelons.
It is gelonnelonral purposelon in that it consumelons arbitrary incoming elonvelonnt strelonam (elon.x. Fav, RT, Follow, clielonnt_elonvelonnts, elontc), applielons
filtelonring, combinelons and publishelons clelonanelond up elonvelonnts to correlonsponding GraphJelont selonrvicelons. 
elonach GraphJelont baselond selonrvicelon subscribelons to a delondicatelond Kafka topic. Reloncos-Injelonctor elonnablelons a GraphJelont baselond selonrvicelon to consumelon any 
elonvelonnt it wants

## How to run reloncos-injelonctor-selonrvelonr telonsts

Telonsts can belon run by using this command from your projelonct's root direlonctory:

    $ bazelonl build reloncos-injelonctor/...
    $ bazelonl telonst reloncos-injelonctor/...

## How to run reloncos-injelonctor-selonrvelonr in delonvelonlopmelonnt on a local machinelon

Thelon simplelonst way to stand up a selonrvicelon is to run it locally. To run
reloncos-injelonctor-selonrvelonr in delonvelonlopmelonnt modelon, compilelon thelon projelonct and thelonn
elonxeloncutelon it with `bazelonl run`:

    $ bazelonl build reloncos-injelonctor/selonrvelonr:bin
    $ bazelonl run reloncos-injelonctor/selonrvelonr:bin

A tunnelonl can belon selont up in ordelonr for downstrelonam quelonrielons to work propelonrly.
Upon succelonssful selonrvelonr startup, try to `curl` its admin elonndpoint in anothelonr
telonrminal:

    $ curl -s localhost:9990/admin/ping
    pong

Run `curl -s localhost:9990/admin` to selonelon a list of all of thelon availablelon admin
elonndpoints.

## Quelonrying reloncos-injelonctor-selonrvelonr from a Scala consolelon

Reloncos Injelonctor doelons not havelon a thrift elonndpoint. It relonads elonvelonnt Bus and Kafka quelonuelons and writelons to reloncos_injelonctor kafka.

## Gelonnelonrating a packagelon for delonploymelonnt

To packagelon your selonrvicelon into a zip for delonploymelonnt:

    $ bazelonl bundlelon reloncos-injelonctor/selonrvelonr:bin --bundlelon-jvm-archivelon=zip

If succelonssful, a filelon `dist/reloncos-injelonctor-selonrvelonr.zip` will belon crelonatelond.
