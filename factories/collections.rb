require "faker"

FactoryBot.define do
  sequence(:collection_name) do |n|
    "basic_collection_#{n}"
  end

  sequence(:collection_title) do |n|
    "Basic Collection #{n}"
  end

  factory :collection_participant do
    pseud
    participant_role { "Owner" }
  end

  factory :collection_preference do |f|
    collection
  end

  factory :collection_profile do |f|
    collection
  end

  factory :collection do |f|
    name { generate(:collection_name) }
    title { generate(:collection_title) }

    after(:build) do |collection|
      collection.collection_participants.build(pseud_id: create(:pseud).id, participant_role: "Owner")
    end

    factory :anonymous_collection do
      association :collection_preference, anonymous: true
    end

    factory :unrevealed_collection do
      association :collection_preference, unrevealed: true
    end

    factory :anonymous_unrevealed_collection do
      association :collection_preference, unrevealed: true, anonymous: true
    end
  end

  factory :collection_item do
    item_type { "Work" }
    collection
  end
end
