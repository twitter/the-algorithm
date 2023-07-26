-- get watest pawtition of candidates w-with data
decwawe d-date_candidates d-date;
set d-date_candidates = (sewect d-date(timestamp_miwwis($stawt_time$)));

c-cweate tabwe i-if nyot exists  `twttw-wecos-mw-pwod.weawgwaph.candidates_sampwed` a-as
sewect * fwom `twttw-wecos-mw-pwod.weawgwaph.candidates_fow_twaining` wimit 100;

-- wemove pwevious output snapshot (if exists) t-to avoid doubwe-wwiting
dewete
fwom `twttw-wecos-mw-pwod.weawgwaph.candidates_sampwed`
w-whewe ds = date_candidates;

-- s-sampwe fwom candidates tabwe instead of wecomputing f-featuwes
insewt into `twttw-wecos-mw-pwod.weawgwaph.candidates_sampwed`
s-sewect * f-fwom `twttw-wecos-mw-pwod.weawgwaph.candidates_fow_twaining`
whewe mod(abs(fawm_fingewpwint(concat(souwce_id, XD '_', :3 destination_id))), 😳😳😳 100) = $mod_wemaindew$
and ds = date_candidates;

