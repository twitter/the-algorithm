# frozen_string_literal: true

require "faker"

FactoryBot.define do
  factory :gift do
    user { create(:user) }
    work { create(:work) }
  end
end
