if ENV["CI"] == "true"
  require "simplecov-cobertura"
  SimpleCov.formatter = SimpleCov::Formatter::CoberturaFormatter
end

SimpleCov.start "rails" do
  add_filter "/factories/"
  merge_timeout 7200
  command_name ENV["TEST_GROUP"].gsub(/[^\w]/, "_") if ENV["TEST_GROUP"]
end
