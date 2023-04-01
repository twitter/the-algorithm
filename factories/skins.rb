require 'faker'
FactoryBot.define do
  sequence :skin_title do |n|
    "#{Faker::Lorem.word} #{n}"
  end

  factory :skin do
    author_id { FactoryBot.create(:user).id }
    title { generate(:skin_title) }

    trait :public do
      add_attribute(:public) { true }
      official { true }
    end
  end

  factory :work_skin do
    author_id { FactoryBot.create(:user).id }
    title { generate(:skin_title) }

    trait :private do
      add_attribute(:public) { false }
    end

    trait :public do
      add_attribute(:public) { true }
      official { true }
    end
  end
end
