require "faker"

FactoryBot.define do
  sequence(:locale_iso) do |n|
    "en-WAT#{n}"
  end

  sequence(:locale_name) do |n|
    "Locale #{n}"
  end

  factory :locale do
    language { Language.default }
    iso { generate(:locale_iso) }
    name { generate(:locale_name) }
  end
end
