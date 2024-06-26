package com.bingo.data.fileUtil;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


import com.bingo.data.graph.ALGraph;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FileProcessor {
    public void createFolder(String target){

        // 创建File对象
        File targetF = new File(target);

        // 使用mkdirs()方法尝试创建多级文件夹
        boolean areDirectoriesCreated = targetF.mkdirs();

        // 检查文件夹是否创建成功
        if (areDirectoriesCreated) {
//            System.out.println("Directories created successfully!");
        } else {
//            System.out.println("Failed to create directories.");
        }
    }
    public void folderCut(String source,String target) throws IOException {
        // 创建源File对象
        Path sourceP = Paths.get(source);
        File sourceF = new File(source);
        // 创建目标File对象
        Path targetP = Paths.get(target);
        File targetF = new File(target);

        if(sourceF.isDirectory()){
            if(!targetF.exists())
                createFolder(target);
        }
        Files.move(sourceP, targetP, StandardCopyOption.REPLACE_EXISTING);


//        System.out.println("Directory cut successfully.");
    }
    public void folderCut_RemainBag(String source,String target) throws IOException {
        Path sourcePath = Paths.get(source);
        Path targetPath = Paths.get(target);

        File sourceFile=new File(source);
        File[] filesAndDirs = sourceFile.listFiles();               //拿到source下的所有子文件和子文件夹，将它们剪切到target
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        for(File f:filesAndDirs){
            String sf = f.getAbsolutePath();                        //拿到文件的绝对路径

            Path parentPath = Paths.get(f.getParent());             //拿到上一级的绝对路径，从而拿到相对路径
            Path relativize = parentPath.relativize(Paths.get(f.getAbsolutePath()));

            folderCut(sf,target+"\\"+relativize.toString());
        }

//        System.out.println("Directory copy successfully.");
    }
    public void folderRemove(String target) throws IOException {

        File directory=new File(target);

        // 检查文件夹是否存在
        if (directory.exists()) {
            // 检查文件夹是否是一个目录
            if (directory.isDirectory()) {
                // 获取文件夹中的所有文件和子目录
                File[] files = directory.listFiles();
                if (files != null) {
                    // 递归删除所有文件和子目录
                    for (File file : files) {
                        folderRemove(file.getAbsolutePath());
                    }
                }
            }
            // 最后删除文件夹本身
            boolean success = directory.delete();
            if (success) {
//                System.out.println("Directory deleted: " + directory.getAbsolutePath());
            } else {
//                System.out.println("Failed to delete directory: " + directory.getAbsolutePath());
            }
        } else {
//            System.out.println("Directory does not exist: " + directory.getAbsolutePath());
        }

//        Path targetP=Paths.get(target);
//        if(new File(target).exists())
//            Files.delete(targetP);
    }
    public void folderRename(String path,String newName){
        // 创建File对象表示当前文件夹
        File currentDirectory = new File(path);

        // 检查当前文件夹是否存在且是一个目录
        if (currentDirectory.exists() && currentDirectory.isDirectory()) {
            // 创建新的File对象表示新的文件夹路径
            File newDirectory = new File(currentDirectory.getParent(), newName);
            // 重命名文件夹
            boolean success = currentDirectory.renameTo(newDirectory);
        } else {
//            System.out.println("The specified directory does not exist or is not a directory.");
        }
    }
    //    拿到keys.txt中所有的关键字数据
    public LinkedList<String> getContext(String path,String type){
//        File file=new File(path+"\\keys.txt");
//        if(!Files.exists(Paths.get("keys.txt"))) return new String[0];
//        return file.list();


        // 指定要读取的文本文件路径
        String filePath = path+"\\"+type;

        // 创建File对象
        File file = new File(filePath);

        // 创建一个List来存储文件中的所有字符串
        LinkedList<String> strings = new LinkedList<>();

        try {
            // 创建Scanner对象来读取文件
            Scanner scanner = new Scanner(file);

            // 循环读取文件中的每一行
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // 将读取到的每一行添加到List中
                strings.add(line);
            }

            // 关闭Scanner
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strings;
    }
    //    拿到所有的children子文件,排除data文件
    public LinkedList<String> getChildren(String path){
        // 创建File对象
        File directory = new File(path);
        LinkedList<String> children=new LinkedList<>();

        // 检查指定的File对象是否是一个目录
        if (directory.isDirectory()) {
            // 获取目录下的所有文件和子目录
            File[] filesAndDirs = directory.listFiles();

            // 如果目录不为空，则遍历所有文件和子目录
            if (filesAndDirs != null) {
                for (File fileOrDir : filesAndDirs) {
                    // 使用isDirectory()方法判断是否为子目录
                    if (fileOrDir.isDirectory()) {
                        Path parentPath = Paths.get(fileOrDir.getParent());
                        Path relativize = parentPath.relativize(Paths.get(fileOrDir.getAbsolutePath()));
                        if(!relativize.toString().equals("data"))
                            children.add(relativize.toString());
                    }
                }
            }
        } else {
//            System.out.println(path + " is not a directory.");
        }
        return children;
    }
    //    拿到所有的文件，排除文件夹
    public LinkedList<String> getChildFile(String path){
        // 创建File对象
        File directory = new File(path);
        LinkedList<String> children=new LinkedList<>();

        // 检查指定的File对象是否是一个目录
        if (directory.isDirectory()) {
            // 获取目录下的所有文件和子目录
            File[] filesAndDirs = directory.listFiles();

            // 如果目录不为空，则遍历所有文件和子目录
            if (filesAndDirs != null) {
                for (File fileOrDir : filesAndDirs) {
                    // 使用isDirectory()方法判断是否为子目录
                    if (!fileOrDir.isDirectory()) {
                        Path parentPath = Paths.get(fileOrDir.getParent());
                        Path relativize = parentPath.relativize(Paths.get(fileOrDir.getAbsolutePath()));
                        if(!relativize.toString().equals("data"))
                            children.add(relativize.toString());
                    }
                }
            }
        } else {
//            System.out.println(path + " is not a directory.");
        }
        return children;
    }
    //    在路径上创建空的txt文件
    public void txtCreate(String apath,String type){
        Path path = Paths.get(apath+"\\"+type);

        try {
            // 使用Files.createFile方法创建文件
            if (!Files.exists(path)) {
                Files.createFile(path);
//                System.out.println("txt文件创建成功！");
            } else {
//                System.out.println("txt文件已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //    在txt文件中插入值
    public void txtInsert(String path,int index,String data,String type,boolean ifRep) throws FileNotFoundException {
        LinkedList<String> keys=getContext(path,type);                         //拿到所有的key值
//        ---------------------------------偷来的，密封-------------------------------------
        File files = new File(path);
        files.mkdirs();
        FileOutputStream fos = new FileOutputStream(path + File.separator + type);
        // 逐行写入
        PrintWriter pw = new PrintWriter(fos);
//        ---------------------------------------------------------------------------------
        int i=0;
        for(String s:keys){                 //重新写入，当到data的索引时，写入data
            if(i==index&&(!data.equals(s)||ifRep)){
                pw.println(data);
            }
            pw.println(s);
            i++;
        }
        if(i<=index)                        //处理index刚好在最后一个的情况
            pw.println(data);
        pw.close();
    }
    //    追加数据
    public void txtInsertBack(String path,String datas[],String type) throws FileNotFoundException {
        LinkedList<String> keys=getContext(path,type);                         //拿到所有的key值
//        ---------------------------------偷来的，密封-------------------------------------
        File files = new File(path);
        files.mkdirs();
        FileOutputStream fos = new FileOutputStream(path + File.separator + type);
        // 逐行写入
        PrintWriter pw = new PrintWriter(fos);
//        ---------------------------------------------------------------------------------
        for(String s:keys){
            if (s.equals(datas[0])){
                break;
            }
            pw.println(s);
        }
        for(String data:datas){
            pw.println(data);
        }
        pw.close();
    }
    public void txtWrite(String path,String datas[],String type) throws FileNotFoundException {
        LinkedList<String> keys=getContext(path,type);                         //拿到所有的key值
//        ---------------------------------偷来的，密封-------------------------------------
        File files = new File(path);
        files.mkdirs();
        FileOutputStream fos = new FileOutputStream(path + File.separator + type);
        // 逐行写入
        PrintWriter pw = new PrintWriter(fos);
//        ---------------------------------------------------------------------------------
        for(String data:datas){
            pw.println(data);
        }
        pw.close();
    }
    //    删除txt文件中对应的值,如果传入的data为空:则自动删除并返回最后一个值
    public String txtDataRemove(String path,String data,String type) throws FileNotFoundException {
        LinkedList<String> keys=getContext(path,type);                         //拿到所有的key值
//        ---------------------------------偷来的，密封-------------------------------------
        File files = new File(path);
        files.mkdirs();
        FileOutputStream fos = new FileOutputStream(path + File.separator + type);
        // 逐行写入
        PrintWriter pw = new PrintWriter(fos);
//        ---------------------------------------------------------------------------------
        String last=null;
        if(data==null)
            last = keys.removeLast();
        for(String s:keys){                 //重新写入，当到data的索引时，写入data
            if(!s.equals(data)){
                pw.println(s);
            }
            else
                continue;
        }
        pw.close();
        return last;
    }
    //    删除txt文件中对应的index对应的值，并返回该值
    public String txtIndexRemove(String path,int index,String type) throws FileNotFoundException {
        LinkedList<String> keys=getContext(path,type);                         //拿到所有的key值
//        ---------------------------------偷来的，密封-------------------------------------
        File files = new File(path);
        files.mkdirs();
        FileOutputStream fos = new FileOutputStream(path + File.separator + type);
        // 逐行写入
        PrintWriter pw = new PrintWriter(fos);
//        ---------------------------------------------------------------------------------
        String target=null;
        int i=0;
        for(String s:keys){                 //重新写入，当到data的索引时，写入data
            if(i++==index){
                target=s;
            }
            else{
                pw.println(s);
            }
        }
        pw.close();
        return target;
    }
    //    替换txt文件中的值:将d1换成d2
    public void txtUpdate(String path,String d1,String d2,String type) throws FileNotFoundException {
        LinkedList<String> keys=getContext(path,type);                         //拿到所有的key值
//        ---------------------------------偷来的，密封-------------------------------------
        File files = new File(path);
        files.mkdirs();
        FileOutputStream fos = new FileOutputStream(path + File.separator + type);
        // 逐行写入
        PrintWriter pw = new PrintWriter(fos);
//        ---------------------------------------------------------------------------------
        for(String s:keys){                 //重新写入，当到data的索引时，写入data
            if(s.equals(d1)){
                pw.println(d2);
            }
            else
                pw.println(s);

        }
        pw.close();
    }
    //    删除txt文件
    public void txtRemove(String apath,String type) throws IOException {
        Path path = Paths.get(apath + "\\" + type);
        if(exist(apath + "\\" + type))
            Files.delete(path);
    }
    public int findIndexByData(String path,String data,String type){
        try (BufferedReader reader = new BufferedReader(new FileReader(path+"\\"+type))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                if (line.equals(data)) {
                    return lineNumber;
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return -1; // 返回-1表示没有找到目标数据
    }
    public String findDatabyIndex(String path,int index,String type){
        try (BufferedReader reader = new BufferedReader(new FileReader(path+"\\"+type))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                if (lineNumber==index) {
                    return line;
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return null; // 返回null表示没有找到目标数据
    }
    public boolean exist(String apath){
        Path path=Paths.get(apath);
        return Files.exists(path);
    }
    public static void writeDataToCSVFile(LinkedList<LinkedList<String>> data, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
//            输出表头

//            输出数据
            for (LinkedList<String> row : data) {
                for (int i = 0; i < row.size(); i++) {
                    if (i == row.size() - 1) {
                        bw.write(row.get(i));
                    } else {
                        bw.write(row.get(i) + ",");
                    }
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //    generateCSV的子函数，用于读csv文件
    public static LinkedList<LinkedList<String>> readCsv(String filePath) {
        LinkedList<LinkedList<String>> data = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // 假设CSV文件使用逗号分隔
                LinkedList<String> row = new LinkedList<>();

                for (String value : values) {
                    if(value.compareTo("0")>0&&value.compareTo("999")<0){
                        value=String.format("%03d", Integer.parseInt(value));
                    }
                    row.add(value);
                }

                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
    //    将邻接表写入Json
    public void saveToFileJson(ALGraph graph, String filePath) {

        // 使用Gson将邻接表序列化为JSON字符串
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(graph.getAdjacencyList());

        // 将JSON字符串写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(json);
            System.out.println("邻接表已保存到文件: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //    从Json拿到邻接表
    public ALGraph getFromFileJson(String filePath){

        ALGraph graph=new ALGraph();
        // 使用Gson从JSON文件中读取邻接表
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, LinkedList<String>>>() {}.getType();
            Map<String, LinkedList<String>> map = gson.fromJson(reader, type);
//            先添加结点
            Set<String> keySet = map.keySet();
            for(String key:keySet){
                graph.addNode(key);
            }
//            int i=0;
//            for (Map.Entry<String, LinkedList<String>> entry : map.entrySet()) {
//                String node = entry.getKey();
////                System.out.print(node+": ");
//                graph.addNode(i++,node);
////                System.out.println();
//            }
//            再添加边
            for (Map.Entry<String, LinkedList<String>> entry : map.entrySet()) {
                String node = entry.getKey();
                for (String neighbor : entry.getValue()) {
//                    System.out.print(neighbor+" ");
                    graph.addEdge(node,neighbor);              //根据info添加边
                }
//                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
        }

        return graph;
    }
}