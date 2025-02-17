Problem Statement: Practo Search page
Requirement: 
Doctor Search: By name, By speciality
Practice Search: By name, By speciality
Doctor Profile - add doctor info
Practice Profile- add clinic/hospital info

Step to set up ElasticSearch: 7.17.4 with openjdk version "17.0.14"

1. brew tap elastic/tap
2. cd /opt/homebrew/Library/Taps/elastic/homebrew-tap # For M1/M2
3. git fetch origin pull/144/head:patch-1
4. git checkout patch-1
5. brew install elastic/tap/elasticsearch-full
6. brew services restart elastic/tap/elasticsearch-full
7. echo $ES_JAVA_HOME  
/opt/homebrew/Cellar/openjdk@17/17.0.14/libexec/openjdk.jdk/Contents/Home
8. vim /opt/homebrew/Cellar/elasticsearch-full/7.17.4/homebrew.mxcl.elasticsearch-full.plist
<key>EnvironmentVariables</key>
<dict>
    <key>ES_JAVA_HOME</key>
    <string>/opt/homebrew/Cellar/openjdk@17/17.0.4/libexec/openjdk.jdk
</string>
</dict>
9. "\nxpack.ml.enabled: false\n" >> /opt/homebrew/etc/elasticsearch/elasticsearch.yml
10. nano ~/.zshrc 
11. elasticsearch 
12. curl -s http://localhost:9200
{
  "name" : "Apples-MacBook-Pro.local",
  "cluster_name" : "elasticsearch_supragya",
  "cluster_uuid" : "WjdWSF7sQOyS6j1FhdJdNg",
  "version" : {
    "number" : "7.17.4",
    "build_flavor" : "default",
    "build_type" : "tar",
    "build_hash" : "79878662c54c886ae89206c685d9f1051a9d6411",
    "build_date" : "2022-05-18T18:04:20.964345128Z",
    "build_snapshot" : false,
    "lucene_version" : "8.11.1",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}

Configure Elasticsearch in application.properties:

spring.elasticsearch.uris=http://localhost:9200
spring.elasticsearch.username=elastic


Elastic Search Response:
1. curl -X GET "http://localhost:9200/practo_search_index/_search"

2. curl -X GET "http://localhost:9200/practo_search_index/_search?q=cardiology&pretty" 


Github Link: https://github.com/Supragya22/PractoSearch

