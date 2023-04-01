Then /^I should see the inspiring parent work link$/ do
  parent = Work.find_by(title: "Worldbuilding")
  inspired_link = "<a href=\"#{work_url(parent)}\">#{parent.title.html_safe}</a>"
  page.body.should =~ /Inspired by #{Regexp.escape(inspired_link)}/m
end

Then /^I should see the external inspiring work link$/ do
  parent = ExternalWork.find_by(title: "Example External")
  inspired_link = "<a href=\"#{external_work_url(parent)}\">#{parent.title.html_safe}</a>"
  page.body.should =~ /Inspired by #{Regexp.escape(inspired_link)}/m
end

Then /^I should receive a file of type "(.*?)"$/ do |filetype|
  mime_type = filetype == "azw3" ? "application/x-mobi8-ebook" : MIME::Types.type_for("foo.#{filetype}").first
  expect(page.response_headers['Content-Disposition']).to match(/filename=.+\.#{filetype}/)
  expect(page.response_headers['Content-Length'].to_i).to be_positive
  expect(page.response_headers['Content-Type']).to eq(mime_type)
end

Then /^I should be able to download all versions of "(.*?)"$/ do |title|
  (ArchiveConfig.DOWNLOAD_FORMATS - ['html']).each do |filetype|
    step %{I should be able to download the #{filetype} version of "#{title}"}
  end
end

Then /^I should be able to download the (\w+) version of "(.*?)"$/ do |filetype, title|
  work = Work.find_by_title(title)
  visit work_url(work)
  step %{I follow "#{filetype.upcase}"}

  download = Download.new(work, format: filetype)
  filename = "#{download.file_name}.#{download.file_type}"
  mime_type = filetype == "azw3" ? "application/x-mobi8-ebook" : MIME::Types.type_for(filename).first
  expect(page.response_headers['Content-Disposition']).to match(/filename="#{filename}"/)
  expect(page.response_headers['Content-Length'].to_i).to be_positive
  expect(page.response_headers['Content-Type']).to eq(mime_type)
end
