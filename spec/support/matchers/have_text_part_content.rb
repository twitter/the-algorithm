RSpec::Matchers.define :have_text_part_content do |expected_content|
  match do |email|
    expect(email.text_part.decoded).to include(expected_content)
  end

  failure_message do |email|
    "expected to find text \"#{expected_content}\" in
    #{email.text_part.decoded}"
  end

  description do
    "have text \"#{expected_content}\" in plain text email part"
  end
end
