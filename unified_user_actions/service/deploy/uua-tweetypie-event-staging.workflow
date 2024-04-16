{
  "role": "d scode",
  "na ": "uua-t etyp e-event-stag ng",
  "conf g-f les": [
    "uua-t etyp e-event.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-t etyp e-event"
      },
      {
        "type": "packer",
        "na ": "uua-t etyp e-event-stag ng",
        "art fact": "./d st/uua-t etyp e-event.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-t etyp e-event-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-t etyp e-event"
        }
      ]
    }
  ]
}
