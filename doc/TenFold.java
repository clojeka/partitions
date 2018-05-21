package kfold;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;

public class TenFold {

    public Instances data;

    public void loadDataset(String fileName) throws FileNotFoundException {
        // loads data and set class index
        InputStream inputStream = new FileInputStream(fileName);
       try {
            data = DataSource.read(inputStream);
            data.setClassIndex(data.numAttributes() - 1); //O atributo
classe é o ultimo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFolds(String root, String name, int k, int seed)
throws IOException {
        // randomize data
        Random rand = new Random(seed);
        Instances randData = new Instances(data);
        randData.randomize(rand);

        if (randData.classAttribute().isNominal()) {
            randData.stratify(k);
        }

        for (int i = 0; i < k; i++) {
            Instances test = randData.testCV(k, i);
            Instances train = randData.trainCV(k, i);

            CSVSaver saver = new CSVSaver();

            saver.setInstances(test);
            saver.setFile(new File(root+name+ "/test" + (i + 1) + ".csv"));
            saver.writeBatch();

            saver.setInstances(train);
            saver.setFile(new File(root+name+ "/train" + (i + 1) + ".csv"));
            saver.writeBatch();
        }
    }

    public static void all() throws Exception {
        String [] all = new String[] {"iris"};
        String root = "./";
        for (String d : all) {
            TenFold obj = new TenFold();
            obj.loadDataset(root + d + "/" + d + ".arff");
            obj.getFolds(root, d, 10, 1);
        }

    }

    public static void main(String[] args) throws Exception {
        all();
    }
}
