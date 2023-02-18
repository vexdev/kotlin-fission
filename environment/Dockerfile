FROM golang:1.16-buster

WORKDIR /binary
COPY *.go /binary/

RUN go mod init github.com/fission/environments/binary
RUN go mod tidy

RUN go build -o server .

FROM debian:stable-20230208

WORKDIR /app

RUN apt-get update
RUN apt-get install -y coreutils binutils findutils grep

COPY --from=0 /binary/server /app/server

EXPOSE 8888
ENTRYPOINT ["./server"]
