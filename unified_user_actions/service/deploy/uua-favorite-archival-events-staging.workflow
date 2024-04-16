{
  "role": "d scode",
  "na ": "uua-favor e-arch val-events-stag ng",
  "conf g-f les": [
    "uua-favor e-arch val-events.aurora"
  ],
  "bu ld": {
    "play": true,
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-favor e-arch val-events"
      },
      {
        "type": "packer",
        "na ": "uua-favor e-arch val-events-stag ng",
        "art fact": "./d st/uua-favor e-arch val-events.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-favor e-arch val-events-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-favor e-arch val-events"
        }
      ]
    }
  ]
}
