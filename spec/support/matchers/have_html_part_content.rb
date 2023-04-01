RSpec::Matchers.define :have_html_part_content do |expected_content|
  match do |email|
    expect(email.html_part.decoded).to include(expected_content)
  end

  failure_message do |email|
    "expected to find text \"#{expected_content}\" in
    #{email.html_part.decoded}"
  end

  description do
    "have text \"#{expected_content}\" in HTML email part"
  end
end
