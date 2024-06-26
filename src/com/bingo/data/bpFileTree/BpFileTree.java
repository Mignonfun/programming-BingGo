package com.bingo.data.bpFileTree;



import com.bingo.data.fileUtil.FileProcessor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class BpFileTree {
    String root;
    FileProcessor filp=new FileProcessor();
    int level;
    public BpFileTree(int level, String root){
        this.level=level;
        this.root=root;
        if(!filp.exist(root+"\\keys.txt"))                   //说明已经创建过了
            filp.createFolder(root+"\\data");
        filp.txtCreate(root,"keys.txt");
    }

    private int findSuitidx(String path,String data){              //寻找适合插入的下标
        LinkedList<String> children = filp.getContext(path,"keys.txt");
        int index=0;
        for(String s : children){
            if(data.compareTo(s)<=0) break;
            index++;
        }
        return index;
    }
    private String findSuitLeaf(String path,String data){               //寻找合适的叶子文件夹
        if(filp.exist(path+"\\data"))return path;                   //如果是叶子结点，直接返回
        else{
            LinkedList<String> children=filp.getChildren(path);
            int index=findSuitidx(path,data);          //找到合适的下一跳
            String newPath=path+"\\"+children.get(index);                 //下一跳的地址
            return findSuitLeaf(newPath,data);
        }
    }
    private void addData(String path,String data) throws IOException {  //添加数据
        int index = findSuitidx(path, data);                                    //拿到要插入到的下标
        filp.txtInsert(path,index,data,"keys.txt",true);                               //插入keys文件

//        判断是否分割文件夹
        LinkedList<String> keys = filp.getContext(path,"keys.txt");
        int ksize=keys.size();
        if(ksize==level) {
            int mid = level / 2 + level % 2 - 1;
            String lchild = keys.get(0);
            String rchild = keys.get(mid);
//        是根目录：原文件作为mid，并在此文件下创建lchild和rchild
            if (path.equals(root)) {
                boolean flag=false;
                if(!filp.exist(path+"\\data")&&!filp.exist(path + "\\" + lchild)){//是叶子结点，同时没有lchild or rchild文件夹
                    flag=true;
                }
                LinkedList<String> children = filp.getChildren(path);
                filp.createFolder(path + "\\" + lchild + "\\data");                 //不管是不是叶子结点，先创建data文件夹
                filp.createFolder(path + "\\" + rchild + "\\data");                 //在处理中间节点时再删除
                //case1:如果是中间节点：处理孩子
                if(!filp.exist(path+"\\data")){
                    rchild=keys.get(mid);
                    //lchild
                    if(!flag)//如果是在两层以上就不用cut了
                        filp.folderCut_RemainBag(path+"\\"+lchild,path+"\\"+lchild+"\\"+lchild);    //先复制会引起冲突的文件夹
                    for(int i=0;i<=mid;i++){
                        String child=children.get(i);
                        if(!child.equals(lchild)){
                            filp.folderCut(path+"\\"+child,path+"\\"+lchild+"\\"+child);        //如果文件夹不冲突，执行剪切操作
                        }
                    }
                    filp.folderRemove(path+"\\"+lchild+"\\data");                                       //移除data文件夹

                    if(!flag)//如果是在两层以上就不用cut了                                                                        //rchild
                        filp.folderCut_RemainBag(path+"\\"+rchild,path+"\\"+rchild+"\\"+rchild);    //先复制会引起冲突的文件夹
                    for(int i=mid+1;i<children.size();i++){
                        String child=children.get(i);
                        if(!child.equals(rchild)){
                            filp.folderCut(path+"\\"+child,path+"\\"+rchild+"\\"+child);        //如果文件夹不冲突，执行剪切操作
                        }
                    }
                    filp.folderRemove(path+"\\"+rchild+"\\data");                                       //移除data文件夹
                }
//                case2:如果这个根节点是个叶子结点
                filp.createFolder(path + "\\" + lchild);        //lchild
                filp.createFolder(path + "\\" + rchild);        //rchild
                filp.txtRemove(path + "\\" + lchild, "keys.txt");           //重建txt
                filp.txtRemove(path + "\\" + rchild, "keys.txt");
                filp.txtCreate(path + "\\" + lchild, "keys.txt");
                filp.txtCreate(path + "\\" + rchild, "keys.txt");
//                filp.txtCreate(path + "\\" + lchild, "children.txt");
//                filp.txtCreate(path + "\\" + rchild, "children.txt");
                index = 0;
                int index2 = 0;
                for (String s : keys) {                                 //lchild继承key值
                    if (index < mid){
                        filp.txtInsert(path + "\\" + lchild, index, s, "keys.txt",true);
                        if(filp.exist(path+"\\data")){           //如果是叶子文件夹，要移动data中的数据
                            if(!s.equals(data))
                                filp.folderCut(path+"\\data\\"+s+".txt",path+"\\"+lchild+"\\data\\"+s+".txt");
                        }
                    }
                    else{
                        if(!filp.exist(path+"\\data")&&index++==mid)continue;
                        filp.txtInsert(path + "\\" + rchild, index2++, s, "keys.txt",true);
                        if(filp.exist(path+"\\data")){           //如果是叶子文件夹，要移动data中的数据
                            if(!s.equals(data))
                                filp.folderCut(path+"\\data\\"+s+".txt",path+"\\"+rchild+"\\data\\"+s+".txt");
                        }
                    }
                    index++;
                }
                filp.txtRemove(path, "keys.txt");       //重新创建txt
                filp.txtCreate(path, "keys.txt");
                filp.txtInsert(path, 0, keys.get(mid), "keys.txt",true);
//                filp.txtInsert(path, 0, lchild, "children.txt");
//                filp.txtInsert(path, 1, rchild, "children.txt");
                if(filp.exist(path+"\\data")){                               //原来是叶子结点，分裂后不是叶子结点的，移除data
                    filp.folderRemove(path + "//data");
//                    改名
                    filp.folderRename(path+"\\"+rchild,keys.get(mid));
                }else{
                    filp.folderRename(path+"\\"+rchild,keys.get(mid+1));
                }
                filp.folderRename(path+"\\"+lchild,keys.get(0));
            }

//        不是根目录的情况：原文件作为lchild,创建一个文件作为rchild,将mid作为关键字放到原文件的keys
            else {
                File file = new File(path);
                String parent = file.getParent();
                                /*
                接下来要处理lchild、rchild的孩子的问题：
                现在要处理的是：分裂之后：
                    已知：原来的文件作为lchild，新文件作为rchild,与lchild并列
                    要从移走多余的child，并放入rchild
                */
                LinkedList<String> children = filp.getChildren(path);                   //拿到lchild的所有孩子
                if(!filp.exist(path+"\\data")){
                    rchild=keys.get(mid+1);
                    index=0;
                    for(String child:children){
                        if(index>mid){
                            filp.folderCut(path+"\\"+child,parent+"\\"+rchild+"\\"+child);
                        }
                        index++;
                    }
                }

                filp.txtRemove(path, "keys.txt");                //重建lchild的keys
                filp.txtCreate(path, "keys.txt");
                filp.createFolder(parent + "\\" + rchild);        //rchild,其实这里已经创建了，这句代码无用，这里就不改了
                filp.txtCreate(parent + "\\" + rchild,"keys.txt");
                if(filp.exist(path+"\\data")){                       //如果分裂前的结点存在data,那么分裂出的结点也应该有data
                    filp.createFolder(parent + "\\" + rchild+"\\data");
                }
                index = 0;
                int index2 = 0;
                for (String s : keys) {                                 //lchild、rchild继承key值
                    if (index < mid){
                        filp.txtInsert(path, index, s, "keys.txt",true);
                    }
                    else{
                        if(!filp.exist(path+"\\data")&&index++==mid)continue;
                        filp.txtInsert(parent + "\\" + rchild, index2++, s, "keys.txt",true);if(filp.exist(path+"\\data")){           //如果是叶子文件夹，要移动data中的数据
                            if(!s.equals(data))
                                filp.folderCut(path+"\\data\\"+s+".txt",parent+"\\"+rchild+"\\data\\"+s+".txt");
                        }
                    }
                    index++;
                }
                if(filp.exist(path+"\\data")){                       //如果分裂前的结点存在data,那么分裂出的结点也应该有data
                    filp.folderRename(parent+"\\"+rchild,keys.get(mid));
//
                }else{
                    filp.folderRename(parent+"\\"+rchild,keys.get(mid+1));      //如果分裂前不存在data,说明是中间节点，要给rchild改名
                }

                addData(parent, keys.get(mid));                                 //向上传递
            }
        }

        else if (index==0&&!path.equals(root)){
            filp.folderRename(path,keys.get(0));
        }
    }
    //    public void insert(String data) throws IOException {
//        String suitLeaf = findSuitLeaf(root,data);
//        addData(suitLeaf,data);
//    }
    public void insert(String data,String[] datas) throws IOException {
        String Leaf = findSuitLeaf_remove(root, data, new String[2])[1];
//        先查看对应file文件是否存在
        if(filp.exist(Leaf+"\\data"+"\\"+data+".txt")){
//        存在，那么覆盖这个文件
            filp.txtWrite(Leaf+"\\data",datas,data+".txt");
        }

//        不存在，那么创建，主键作为data：
        String suitLeaf=findSuitLeaf(root,data);
        addData(suitLeaf,data);
        Leaf = findSuitLeaf_remove(root, data, new String[2])[1];       //增加了键值后路径可能改变，重新拿到路径
//        在path中添加需要添加的数据文件file:
        filp.txtCreate(Leaf+"\\data",data+".txt");
        filp.txtWrite(Leaf+"\\data",datas,data+".txt");
    }
    public boolean update(String data,String[] datas) throws IOException {
        String Leaf = findSuitLeaf_remove(root, data, new String[2])[1];
//        先查看对应file文件是否存在
        if(filp.exist(Leaf+"\\data"+"\\"+data+".txt")){
//        存在，那么覆盖这个文件
            filp.txtWrite(Leaf+"\\data",datas,data+".txt");
            return true;
        }
        return false;
    }
    //    找到data所在的叶子文件夹
    public String find(String data){
        String[] paths = findSuitLeaf_remove(root,data,new String[2]);
        String suitLeaf=paths[1];
//        在suitLeaf的字文件夹data中查找需要的txt文件
        LinkedList<String> keys = filp.getContext(suitLeaf, "keys.txt");
        int index=0;
        for(String key:keys){
            if(data.compareTo(key)<=0)break;
            index++;
        }
        if(keys.size()==index||!keys.get(index).equals(data))return null;              //说明找不到这个data
        else return suitLeaf;
    }
    public boolean remove(String data) throws IOException {
        String[] paths = findSuitLeaf_remove(root, data,new String[2]);
//        找data对应的下标index、和后继index+1
        LinkedList<String> keys = filp.getContext(paths[1],"keys.txt");
        int index0=0;
        for(String s : keys){
            if(data.compareTo(s)<=0) break;
            index0++;
        }
//        说明数据不存在
        if(keys.size()==index0||!keys.get(index0).equals(data))return false;
//        删除叶子keys中的数据
        int indexByData = filp.findIndexByData(paths[1], data, "keys.txt");
        filp.txtIndexRemove(paths[1],indexByData,"keys.txt");
//        内部文件更新keys
        if(paths[0]!=null)
            filp.txtUpdate(paths[0],data,keys.get(index0+1),"keys.txt");
//        删除叶子文件中对应的data中的txt文件
        filp.txtRemove(paths[1]+"//data",data+".txt");

//        step2、判断是否需要借数据or合并
        if(keys.size()-1<level/2) {
            borrowOrMerge(paths,data);
        }else{
//        更改包名：如果没有进入borrowOrMerge，通过此步修改包名
            if(indexByData==0){                                 //本地文件
                filp.folderRename(paths[1],keys.get(1));
            }
            if(paths[0]!=null&&index0!=0){
                filp.folderRename(paths[0],keys.get(index0+1));
            }
        }
        return true;
    }
    //    返回值：
//      0:内部文件夹中含data的文件夹的path
//      1:含data的叶子文件夹
    private String[] findSuitLeaf_remove(String path,String data,String[] s){               //寻找合适的叶子文件夹
        if(filp.exist(path+"\\data")){                               //如果是叶子结点，直接返回
            s[1]=path;
            return s;
        }
        else{
            LinkedList<String> children=filp.getChildren(path);
            String newPath;
            int index=findSuitidx(path,data);          //找到合适的下一跳

            //在内部文件夹中找到keys中含data的文件夹
            if(s[0]==null){
                LinkedList<String> keys = filp.getContext(path, "keys.txt");
                for(String key:keys){                                       //如果匹配，记录
                    if(key.equals(data)){
                        s[0]=path;
                        index++;                                            //含data的文件夹的下一跳比较特殊
                    }
                }
            }

            newPath=path+"\\"+children.get(index);                 //下一跳的地址
            return findSuitLeaf_remove(newPath,data,s);
        }
    }
    private void borrowOrMerge(String[] paths,String data) throws IOException {
        File file = new File(paths[1]);
        String parent = file.getParent();
        LinkedList<String> children = filp.getChildren(parent);             //拿到所有的child,以便找到兄弟文件夹
        int index = findSuitidx(parent, data);                                  //找到本地文件夹的下标
        if (parent.equals(paths[0])) index++;                                 //如果parent结点包含data，下标要++

        String child = null;
        int cindex;
        if (index != 0) {                                                       //左孩子合法
            cindex = index - 1;
            child = children.get(cindex);                                    //获取左孩子
        } else {                                                              //左孩子不合法，那么右孩子一定合法
            cindex = index + 1;
            child = children.get(cindex);
        }
        //拿到兄弟的keys
        LinkedList<String> broKeys = filp.getContext(parent + "\\" + child, "keys.txt");
        if (broKeys.size() > level / 2) {                                         //数量够借
//            借
            borrowFromBro(parent + "\\" + child, paths[1], parent, index, 0);
        } else {                                                                //这种情况是左孩子不够借，或者左孩子不合法、右孩子不够借
            LinkedList<String> rbroKeys=new LinkedList<>();
            if (cindex == index - 1&&index!=children.size()-1) {              //说明是左孩子且右孩子合法，考虑使用右孩子
                String rchild = children.get(index + 1);
                rbroKeys = filp.getContext(parent + "\\" + rchild, "keys.txt");
                if (rbroKeys.size() > level / 2) {                                    //右孩子数量够
//                借
                    borrowFromBro(paths[1], parent + "\\" + rchild, parent, index, 1);
                }

            }
            if(broKeys.size()<=level/2&&rbroKeys.size()<=level/2){              //说明左右孩子数量都不够
                //此时：左合法但不够借，或者右孩子合法，但不够借
                String p=parent;
                if (cindex == index + 1) {                                            //如果child是右孩子，左右交换再传参
                    p=paths[1];
                    paths[1]=parent+"\\"+child;
                    index++;
                }
                else{
                    p+="\\"+child;
                }
//            合并
                mergeWithBro(p,paths[1],parent,paths,index,data);
            }
        }
    }
    private void borrowFromBro(String lp,String rp,String parent,int index,int dir) throws IOException {
        if(dir==0){ //向左借
//            首先转移孩子:将左兄弟的最后一个孩子放到右边
            LinkedList<String> children = filp.getChildren(lp);
            if(children.size()!=0){
                String lastChild=children.getLast();
                filp.folderCut(lp+"\\"+lastChild,rp+"\\"+lastChild);
            }
//              lchild的最后一个给parent.index，parent.index给rchild第一个
            String last = filp.txtDataRemove(lp, null, "keys.txt");                 //拿到lp的最后一个
            String pkey = filp.txtIndexRemove(parent, index - 1, "keys.txt");       //拿到parent要替换的值
            filp.txtInsert(parent,index-1,last,"keys.txt",true);              //parent值替换
            filp.txtInsert(rp,0,last,"keys.txt",true);                      //将parent的值放到rp第一个
//            如果是叶子文件：转移txt文件（左边最后一个放到右边）
            if(filp.exist(lp+"\\data")){
                filp.folderCut(lp+"\\data\\"+last+".txt",rp+"\\data\\"+last+".txt");
            }

            filp.folderRename(rp,pkey);                                             //左文件改名
//            更新之后parent对应的包名可能也要改变
            if(index-1==0){
                filp.folderRename(parent,last);
            }

        }else{      //向右借:右边第一个key放到左边最后
//            首先转移孩子：右边第一个孩子放到左边
            LinkedList<String> children = filp.getChildren(rp);
            if(children.size()!=0){
                String fristChild=children.get(0);
                filp.folderCut(rp+"\\"+fristChild,lp+"\\"+fristChild);
            }
//            这里借值
            String pkey = filp.txtIndexRemove(parent, index, "keys.txt");
            filp.txtInsertBack(lp,new String[]{pkey},"keys.txt");
//            这里修改parent
            LinkedList<String> rk = filp.getContext(rp, "keys.txt");
            LinkedList<String> lk = filp.getContext(lp, "keys.txt");
            filp.txtInsert(parent,index,rk.get(0),"keys.txt",false);
//            右边删除第一个值
            filp.txtDataRemove(rp,rk.get(0),"keys.txt");
//            如果是叶子文件：转移txt文件（右边第一个放左边）
            if(filp.exist(lp+"\\data")){
                filp.folderCut(rp+"\\data\\"+rk.get(0)+".txt",lp+"\\data\\"+rk.get(0)+".txt");
            }


            filp.folderRename(lp,lk.get(0));                                        //左文件改名
            filp.folderRename(rp,rk.get(1));                                        //右文件改名
            if(index==0){
                filp.folderRename(parent,rk.get(0));
            }
        }
    }
    //    向左合并，index对应:rp的index
    private void mergeWithBro(String lp,String rp,String parent,String[] paths,int index,String data) throws IOException {
//      将右边的孩子放到左边
        LinkedList<String> rchildren = filp.getChildren(rp);
        if(rchildren.size()!=0){
            for (String rchild:rchildren){
                filp.folderCut(rp+"\\"+rchild,lp+"\\"+rchild);
            }
        }

//      拿到parent对应的key,插入到要合并到的文件夹下的key
        LinkedList<String> keys = filp.getContext(parent, "keys.txt");              //拿到parent所有的key
        filp.txtInsertBack(lp,new String[]{keys.get(index-1)},"keys.txt");                   //将parent中index-1对应的key插入到lp
        filp.txtDataRemove(parent,keys.get(index-1),"keys.txt");
//      将右边的key全都交给左边
        LinkedList<String> rkeys = filp.getContext(rp, "keys.txt");
        filp.txtInsertBack(lp,rkeys.toArray(new String[0]), "keys.txt");
//        右边的data/txt拳打欧交给左边
        for(String key:rkeys){
            filp.folderCut(rp+"\\data\\"+key+".txt",lp+"\\data\\"+key+".txt");
        }
//        合并后文件夹改名
        LinkedList<String> lks = filp.getContext(lp, "keys.txt");
        filp.folderRename(lp,lks.get(0));


//        删除右边文件夹
        filp.folderRemove(rp);
//        向上传递
        paths[1]=parent;
        if(keys.size()-1<level/2){
            if(!parent.equals(root))
                borrowOrMerge(paths,data);
            else{
                if(keys.size()-1==0){
                    root=lp;
                }
            }

        }
//        可能会改变parent的文件夹名
        if(index-1==0){                                                                     //说明第一个key被借走
            filp.folderRename(parent,keys.get(1));
        }
    }
    //    输出树
    public void printTree(String path){
        if(path==null)return;
        LinkedList<String> keys = filp.getContext(path,"keys.txt");
        for(String s:keys){
            System.out.print(s+" ");
        }
        System.out.println();
        if(filp.exist(path+"\\data"))return;
        LinkedList<String> children = filp.getChildren(path);
        for (String child:children){
            printTree(path+"\\"+child);
        }
    }
    //    遍历:拿到所有叶子结点的地址
    public LinkedList<String> getAllLeaves(String path){
        LinkedList<String> paths = new LinkedList<>();
        _getLeaves(path,paths);
//        for(String path1:paths){
//            System.out.println(path1);
//        }
        return paths;
    }
    //    getAllLeaves的子函数
    public void _getLeaves(String path,LinkedList<String> paths){
        if(path==null)return;
        if(filp.exist(path+"\\data")){
            LinkedList<String> keys = filp.getContext(path,"keys.txt");
            paths.add(path);
//            for(String s:keys){
//                System.out.print(s+" ");
//            }
//            System.out.println();
        }
        if(filp.exist(path+"\\data"))return;
        LinkedList<String> children = filp.getChildren(path);
        for (String child:children){
            _getLeaves(path+"\\"+child,paths);
        }
    }
}
