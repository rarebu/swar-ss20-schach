FROM bigtruedata/sbt

# install Xvfb
#RUN apt-get -y update && apt-get install -y xvfb
#RUN apt-get -y update && apt-get install -y libxrender1
#RUN apt-get -y update && apt-get install -y libxi6

WORKDIR /schach

ADD . /schach

ENTRYPOINT ["/usr/local/bin/sbt"]
CMD  ["run"]