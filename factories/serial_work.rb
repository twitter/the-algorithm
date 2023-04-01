require 'faker'

FactoryBot.define do
  factory :serial_work do
    work_id { FactoryBot.create(:work).id }
  end
end
