(ns partitions.core)

(defn arff->instance 
  "Takes an ARFF database and produces a DATASOURCE."
  [arff]
  (def input-stream (new java.io.FileInputStream arff))
  (def instance (. weka.core.converters.ConverterUtils$DataSource read arff))
  (. instance setClassIndex (dec (. instance numAttributes)))
  instance)

(defn shuffle->instance!
  "Takes an INSTANCES object and produces a shuffled copy."
  [original]
  (def copy (new weka.core.Instances original))
  (. copy randomize (new java.util.Random 1))
  (when (.. copy classAttribute isNominal) 
    (. copy stratify 10))
  copy)

(defn save-csv!
  "Takes a WEKA DATA-INSTANCE and produces a CSV file whose name is
FILENAME."  
  [data-instance filename]
  (def saver (new weka.core.converters.CSVSaver))
  (. saver setInstances data-instance)
  (. saver setFile (new java.io.File filename))
  (. saver writeBatch))

(defn -main 
  "I'm so important people call me main.  Though they prefix me with
this ridiculous minus sign, which makes me feel lesser. :-("
  [& args]
  (println "Example: producing pairs (test, train) for iris.arff.")
  (def shuffled-copy (shuffle->instance! (arff->instance "iris.arff")))
  (dorun 
   (for [i (range 1 11)]
     (do 
       (save-csv! (. shuffled-copy testCV 10 (dec i)) (str "test" i ".csv"))
       (save-csv! (. shuffled-copy trainCV 10 (dec i)) (str "train" i ".csv"))
       (println (str "wrote (test,train) part " i))))))
