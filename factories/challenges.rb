require 'faker'
FactoryBot.define do
  factory :challenge_assignment do
    collection { create(:collection, challenge: create(:gift_exchange)) }

    after(:build) do |assignment|
      assignment.request_signup = create(:challenge_signup, collection: assignment.collection)
      assignment.offer_signup = create(:challenge_signup, collection: assignment.collection)
    end
  end

  factory :challenge_signup, aliases: [:gift_exchange_signup] do
    pseud { create(:user).default_pseud }
    collection { create(:collection, challenge: create(:gift_exchange)) }
    requests_attributes { [attributes_for(:request)] }
    offers_attributes { [attributes_for(:offer)] }
  end

  factory :prompt_meme_signup, class: ChallengeSignup do
    pseud { create(:user).default_pseud }
    collection { create(:collection, challenge: create(:prompt_meme)) }
    requests_attributes { [attributes_for(:request)] }
  end

  factory :potential_match do
    collection { create(:collection, challenge: create(:gift_exchange)) }

    after(:build) do |potential_match|
      potential_match.offer_signup = create(:challenge_signup, collection: potential_match.collection)
      potential_match.request_signup = create(:challenge_signup, collection: potential_match.collection)
    end
  end

  factory :gift_exchange do
    association :offer_restriction, factory: :prompt_restriction
    association :request_restriction, factory: :prompt_restriction

    trait :open do
      signups_open_at { Time.now - 1.day }
      signups_close_at { Time.now + 1.day }
      signup_open { true }
    end

    trait :closed do
      signups_open_at { Time.now - 2.days }
      signups_close_at { Time.now - 1.day }
      signup_open { false }
    end
  end

  factory :prompt_meme do
    association :request_restriction, factory: :prompt_restriction
  end
end
