{
  "role": "d scode",
  "na ": "uua-ads-callback-engage nts-prod",
  "conf g-f les": [
    "uua-ads-callback-engage nts.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-ads-callback-engage nts"
      },
      {
        "type": "packer",
        "na ": "uua-ads-callback-engage nts",
        "art fact": "./d st/uua-ads-callback-engage nts.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "prod",
      "targets": [
        {
          "na ": "uua-ads-callback-engage nts-prod-atla",
          "key": "atla/d scode/prod/uua-ads-callback-engage nts"
        },
        {
          "na ": "uua-ads-callback-engage nts-prod-pdxa",
          "key": "pdxa/d scode/prod/uua-ads-callback-engage nts"
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
