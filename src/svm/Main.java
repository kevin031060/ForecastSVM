package svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class Main {
    public static void main(String[] args) throws IOException {
        // 定义训练集点a{10.0, 10.0} 和 点b{-10.0, -10.0}，对应lable为{1.0, -1.0}
        List<Double> label = new ArrayList<Double>();
        List<svm_node[]> nodeSet = new ArrayList<svm_node[]>();
        getSolarData(nodeSet, label, "file/solar.csv");

        int dataRange=nodeSet.get(0).length;
        svm_node[][] datas = new svm_node[nodeSet.size()][dataRange]; // 训练集的向量表
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < dataRange; j++) {
                datas[i][j] = nodeSet.get(i)[j];
            }
        }
        double[] lables = new double[label.size()]; // a,b 对应的lable
        for (int i = 0; i < lables.length; i++) {
            lables[i] = label.get(i);
        }
        for (int j = 0; j < dataRange; j++) {
            System.out.print(nodeSet.get(1000)[j].value);
            System.out.print("|");
        }
        System.out.println(" ");
        System.out.println(label.get(1000));
        // 定义svm_problem对象
        svm_problem problem = new svm_problem();
        problem.l = nodeSet.size(); // 向量个数
        problem.x = datas; // 训练集向量表
        problem.y = lables; // 对应的lable数组

        // 定义svm_parameter对象
        svm_parameter param = new svm_parameter();
        param.svm_type = svm_parameter.EPSILON_SVR;
        param.kernel_type = svm_parameter.RBF;
        param.cache_size = 1000;
        param.gamma = 0.2;	// 1/num_features
        param.eps = 1e-2;
        param.C = 10;
        // 训练SVM分类模型
        System.out.println(svm.svm_check_parameter(problem, param));
        // 如果参数没有问题，则svm.svm_check_parameter()函数返回null,否则返回error描述。
        svm_model model = svm.svm_train(problem, param);
        // svm.svm_train()训练出SVM分类模型
        svm.svm_save_model("file/svm1.model", model);
//        svm_model model = svm.svm_load_model("file/svm1.model");
        // 获取测试数据
        List<Double> testlabel = new ArrayList<Double>();
        List<svm_node[]> testnodeSet = new ArrayList<svm_node[]>();
        getSolarData(testnodeSet, testlabel, "file/solar_test.csv");

        svm_node[][] testdatas = new svm_node[testnodeSet.size()][dataRange]; // 训练集的向量表
        for (int i = 0; i < testdatas.length; i++) {
            for (int j = 0; j < dataRange; j++) {
                testdatas[i][j] = testnodeSet.get(i)[j];
            }
        }
        double[] testlables = new double[testlabel.size()]; // a,b 对应的lable
        for (int i = 0; i < testlables.length; i++) {
            testlables[i] = testlabel.get(i);
        }

        double[] prediction =  new double[testlabel.size()];
        // 预测测试数据的lable
        double err = 0.0;
        for (int i = 0; i < testdatas.length; i++) {
            double truevalue = testlables[i];
            System.out.print(truevalue + " ");
            double predictValue = svm.svm_predict(model, testdatas[i]);
            prediction[i] = predictValue;
            System.out.println(predictValue);
            err += Math.abs(predictValue - truevalue);
        }
        System.out.println("err=" + err / datas.length);
        //
        plot_utils.plot(prediction, testlables, 200);
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
            int max_row_nums = 3000;
            //  强制只读取1000个数据
            double[][] data = new double[max_row_nums][col_nums];
            //            读取数据存放到矩阵中
            while ((line = br.readLine()) != null && row_nums < max_row_nums) {

                String[] datas = line.split(",");
                for (int i = 0; i < datas.length; i++) {
                    data[row_nums][i] = Double.parseDouble(datas[i]);
                }
                row_nums = row_nums + 1;
            }
            // 归一化
            data = scaler.normalize4Scale(data);
            // 转换为svm模型的数据格式
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