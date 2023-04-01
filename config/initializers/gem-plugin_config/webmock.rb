if Rails.env.test?
  WebMock.allow_net_connect!
  WebMock.enable!
end
