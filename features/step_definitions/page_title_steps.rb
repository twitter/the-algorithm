Then "I should see the page title {string}" do |text|
  expect(page.title).to include(text)
end

Then "the page title should include {string}" do |text|
  expect(page.title).to include(text)
end

Then "the page title should not include {string}" do |text|
  expect(page.title).not_to include(text)
end
