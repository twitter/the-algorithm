Then /^the share modal should contain social share buttons$/ do
  with_scope("#share") do
    expect(page).to have_css("li.twitter", text: "Twitter")
    expect(page).to have_css("li.tumblr", text: "Tumblr")
  end
end
