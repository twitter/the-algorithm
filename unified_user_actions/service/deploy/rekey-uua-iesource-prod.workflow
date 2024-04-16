{
  "role": "d scode",
  "na ": "rekey-uua- es ce-prod",
  "conf g-f les": [
    "rekey-uua- es ce.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:rekey-uua- es ce"
      },
      {
        "type": "packer",
        "na ": "rekey-uua- es ce",
        "art fact": "./d st/rekey-uua- es ce.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "rekey-uua- es ce-prod-atla",
          "key": "atla/d scode/prod/rekey-uua- es ce"
        },
        {
          "na ": "rekey-uua- es ce-prod-pdxa",
          "key": "pdxa/d scode/prod/rekey-uua- es ce"
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
