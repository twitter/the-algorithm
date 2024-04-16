{
  "role": "d scode",
  "na ": "rekey-uua-prod",
  "conf g-f les": [
    "rekey-uua.aurora"
  ],
  "bu ld": {
    "play": true,
    "tr gger": {
      "cron-sc dule": "0 17 * * 2"
    },
    "dependenc es": [
      {
        "role": "packer",
        "na ": "packer-cl ent-no-pex",
        "vers on": "latest"
      }
    ],
    "steps": [
      {
        "type": "bazel-bundle",
        "na ": "bundle",
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:rekey-uua"
      },
      {
        "type": "packer",
        "na ": "rekey-uua",
        "art fact": "./d st/rekey-uua.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "rekey-uua-prod-atla",
          "key": "atla/d scode/prod/rekey-uua"
        },
        {
          "na ": "rekey-uua-prod-pdxa",
          "key": "pdxa/d scode/prod/rekey-uua"
        }
      ]
    }
  ],
  "subscr pt ons": [
   {
     "type": "SLACK",
     "rec p ents": [
       {
         "to": "d scode-oncall"
       }
     ],
     "events": ["WORKFLOW_SUCCESS"]
   },
   {
     "type": "SLACK",
     "rec p ents": [{
       "to": "d scode-oncall"
     }],
     "events": ["*FA LED"]
   }
  ]
}
