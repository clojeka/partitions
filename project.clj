(defproject partitions "0.1.0-SNAPSHOT"
  :description "Generates k-fold partitions from ARFF files into CSV files"
  :repositories [["local" "file:jars"]]
  :main ^:skip-aot partitions.core
  :dependencies [[org.clojure/clojure "1.8.0"] 
                [weka/weka "3.8.2"]])
