package svm;

import libsvm.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test_io {
    public static void main(String[] args) throws IOException {
        // 定义训练集点a{10.0, 10.0} 和 点b{-10.0, -10.0}，对应lable为{1.0, -1.0}
        List<Double> label = new ArrayList<Double>();
        List<svm_node[]> nodeSet = new ArrayList<svm_node[]>();
        getSolarData(nodeSet, label, "file/solar.csv");
        int dataRange=nodeSet.get(0).length;
        svm_node[][] datas = new svm_node[nodeSet.size()][dataRange]; // 训练集的向量表
        for (int i = 0; i < datas.length; i++) {
            System.out.println(label.get(i));
            for (int j = 0; j < dataRange; j++) {
                System.out.print(nodeSet.get(i)[j].value);
                System.out.print(",");
            }
            System.out.println(" ");
        }

        double[][] points = {{2, 5, 7}, {3, 1, 5}, {0, 27, 11}, {109, 6, 1}};
        double[][] p1 = scaler.normalize4Scale(points);
        double[][] p2 = scaler.normalize4ZScore(points);

    }

    public static void getSolarData(List<svm_node[]> nodeSet, List<Double> label,
                                    String filename) {
        try {

            FileReader fr = new FileReader(new File(filename));
            BufferedReader br = new BufferedReader(fr);
            //  读取第一行，列名
            String line = null;
            line = br.readLine();
            String[] col_names = line.split(",");
            int col_nums = col_names.length;
            int row_nums = 0;
            int max_row_nums = 1000;
            //  强制只读取1000个数据
            double[][] data = new double[max_row_nums][col_nums];

            while ((line = br.readLine()) != null || row_nums < max_row_nums) {

                String[] datas = line.split(",");
                for (int i = 0; i < datas.length; i++) {
                    data[row_nums][i] = Double.parseDouble(datas[i]);
                }
                row_nums = row_nums + 1;
            }
            for (int i = 0; i < row_nums; i++) {
                svm_node[] vector = new svm_node[col_nums - 1];
                for (int j = 0; j < col_nums - 1; j++) {
                    svm_node node = new svm_node();
                    node.index = j + 1;
                    node.value = data[i][j];
                    vector[j] = node;
                }
                nodeSet.add(vector);
                double lablevalue = data[i][col_nums - 1];
                label.add(lablevalue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
