FROM golang:1.23-alpine AS build
WORKDIR /src
COPY go.mod ./
COPY cmd ./cmd
COPY internal ./internal
RUN CGO_ENABLED=0 GOOS=linux go build -trimpath -ldflags="-s -w" -o /out/feed-api ./cmd/feed-api

FROM alpine:3.22
RUN apk add --no-cache ca-certificates wget && addgroup -S app && adduser -S -G app app
COPY --from=build /out/feed-api /usr/local/bin/feed-api
USER app
EXPOSE 8092
ENTRYPOINT ["/usr/local/bin/feed-api"]
