{
  "role": "d scode",
  "na ": "uua-user-mod f cat on-prod",
  "conf g-f les": [
    "uua-user-mod f cat on.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-user-mod f cat on"
      },
      {
        "type": "packer",
        "na ": "uua-user-mod f cat on",
        "art fact": "./d st/uua-user-mod f cat on.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "uua-user-mod f cat on-prod-atla",
          "key": "atla/d scode/prod/uua-user-mod f cat on"
        },
        {
          "na ": "uua-user-mod f cat on-prod-pdxa",
          "key": "pdxa/d scode/prod/uua-user-mod f cat on"
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
