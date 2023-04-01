require "faker"

FactoryBot.define do
  sequence(:login) do |n|
    "#{Faker::Lorem.characters(number: 8)}#{n}"
  end

  sequence :email do |n|
    Faker::Internet.email(name: "#{Faker::Name.first_name}_#{n}")
  end

  factory :role do
    sequence(:name) { |n| "#{Faker::Company.profession}_#{n}" }
  end

  factory :user do
    login { generate(:login) }
    password { "password" }
    age_over_13 { "1" }
    terms_of_service { "1" }
    password_confirmation(&:password)
    email { generate(:email) }

    # By default, create activated users who can log in, since we use
    # devise :confirmable.
    confirmed_at { Faker::Time.backward }

    trait :unconfirmed do
      confirmed_at { nil }
    end

    # Roles

    factory :archivist do
      roles { [Role.find_or_create_by(name: "archivist")] }
    end

    factory :opendoors_user do
      roles { [Role.find_or_create_by(name: "opendoors")] }
    end

    factory :tag_wrangler do
      roles { [Role.find_or_create_by(name: "tag_wrangler")] }
    end

    factory :official_user do
      roles { [Role.find_or_create_by(name: "official")] }
    end
  end
end
