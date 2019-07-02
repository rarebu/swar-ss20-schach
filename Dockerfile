FROM bigtruedata/sbt

WORKDIR /schach

ADD . /schach

ENTRYPOINT ["/usr/local/bin/sbt"]

CMD  ["run"]
