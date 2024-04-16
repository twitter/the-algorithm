{
  "role": "d scode",
  "na ": "uua-k ll-stag ng-serv ces",
  "conf g-f les": [],
  "bu ld": {
    "play": true,
    "tr gger": {
      "cron-sc dule": "0 17 * * 1"
    },
    "dependenc es": [],
    "steps": []
  },
  "targets": [
    {
      "type": "scr pt",
      "na ": "uua-k ll-stag ng-serv ces",
      "keytab": "/var/l b/tss/keys/fluffy/keytabs/cl ent/d scode.keytab",
      "repos ory": "s ce",
      "command": "bash un f ed_user_act ons/scr pts/k ll_stag ng.sh",
      "dependenc es": [{
         "vers on": "latest",
         "role": "aurora",
         "na ": "aurora"
      }],
      "t  out": "10.m nutes"
    }
  ],
  "subscr pt ons": [
   {
     "type": "SLACK",
     "rec p ents": [
       {
         "to": "un f ed_user_act ons_dev"
       }
     ],
     "events": ["WORKFLOW_SUCCESS"]
   },
   {
     "type": "SLACK",
     "rec p ents": [{
       "to": "un f ed_user_act ons_dev"
     }],
     "events": ["*FA LED"]
   }
  ]
}
