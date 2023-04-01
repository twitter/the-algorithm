### THEN

Then /^"(.+)" should be notified by email about the deletion of "(.+)"$/ do |user, title|
  step %{1 email should be delivered to "#{user}"}
  step %{the email should contain "Your work"}
  step %{the email should contain "#{title}"}
  step %{the email should contain "was deleted at your request"}
  step %{the email should contain "If you have questions, please"}
  step %{the email should link to the support page}
  step %{the email should contain "Attached is a copy of your work for your reference."}
end
