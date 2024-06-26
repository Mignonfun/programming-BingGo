package com.bingo.data.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ALGraph {
    private static int MAXSIZE = 20; // 最大顶点数
    public VNode[] vexs; // 保存图中顶点
    public int vexNum; // 顶点数量
    public int edgeNum; // 边数量
    public ALGraph() {
        this.vexs = new VNode[MAXSIZE];
        this.vexNum = 0;
        this.edgeNum = 0;
    }
    //返回顶点存放在 vexs 中索引位置
    public int findNode(int id) {
        int index = -1;
        for(int i=0; i<vexNum; i++) {
            if(id == vexs[i].id) {
                index = i;
                break;
            }
        }
        return index;
    } //返回顶点存放在 vexs 中索引位置
    public int findNode(String info) {
        int index = -1;
        for(int i=0; i<vexNum; i++) {
            if(info.equals(vexs[i].info)) {
                index = i;
                break;
            }
        }
        return index;
    }
    //    判断两个节点是否相邻
    public boolean isAdjVertex(int u, int v) {
        if(u == v) return false;
        int uIndex = findNode(u);
        int vIndex = findNode(v);
        if(uIndex<0 || vIndex<0) return false;
        boolean yn = false;
        ArcNode p = vexs[uIndex].firstArc;
        // 以 u 为顶点，遍历边节点表
        while (p != null) {
            if(p.adjVex == vIndex) {
                yn = true;
                break;
            }
            p = p.nextArc;
        }
        return yn;
    }
    //    添加结点(自定义id)
    public boolean addNode(int id,String info) {
        if(findNode(id) >= 0) return false;
        VNode node = new VNode(id,info);
        vexs[vexNum++] = node;
        return true;
    }
    //    添加节点(id自增)
    public boolean addNode(String info) {
        int id=-1;
        for(int i=0;i<vexNum;i++){
            if(vexs[i]!=null&&vexs[i].id!=i){            //会出现v.id!=i的情况：这个位置的数据被删除过，出现了id空缺:
                id=i;
                break;
            }
        }
        if(id==-1){                 //说明没有空缺
            id=vexNum;
        }
        VNode node = new VNode(id,info);
        vexs[vexNum++] = node;
//        这里再添一个快排
        if(id!=vexNum-1)
            QuickSort(vexs,0,vexNum-1);
        return true;
    }

    //自增_快排
    private void QuickSort(VNode A[],int low,int high){
        int pivotpos;                               //枢纽位置
        if(low<high){
            pivotpos=Partition(A,low,high);	        //划分原表为左右两个部分
            QuickSort(A,pivotpos+1,high);
            QuickSort(A,low,pivotpos-1);
        }
    }
    //自增_划分原表为左右两个部分
    private int Partition(VNode A[],int low,int high){
        VNode pivot=A[low];							//第一个元素作为枢轴
        while(low<high){							//用low、high搜索枢轴的最终位置
            while(low<high&&A[high].id>pivot.id) high--;
            A[low]=A[high];
            while(low<high&&A[low].id<pivot.id) low++;
            A[high]=A[low];
        }
        A[low]=pivot;								//枢轴元素存放到最终位置
        return low;
    }

    //    根据b+树索引添加顶点
    public boolean addNode_byList(LinkedList<String> list){
        for(String s:list){
            if(!addNode(s)){
                return false;
            }
        }
        return true;
    }
    //    根据node.id添加边
    public boolean addEdge(int u,int v) {
        if(u == v) return false;
        int uIndex = findNode(u);
        int vIndex = findNode(v);
        if(uIndex<0 || vIndex<0) return false;
        if(isAdjVertex(u,v)) return false;
        // 添加边时，邻接表中的顶点表对应的两个顶点都需要增加相应的边结点
        // 请将代码补充完整

        //找到链表尾部
        VNode nu=vexs[findNode(u)];
        VNode nv=vexs[findNode(v)];
        ArcNode cur1=nu.firstArc;
        ArcNode cur2=nv.firstArc;

        if(cur1==null){                         //没有边结点
            nu.firstArc=new ArcNode(v);
        }
        else{                                   //有边结点：找到链表尾
            while(cur1.nextArc!=null){
                cur1=cur1.nextArc;
            }
            cur1.nextArc=new ArcNode(v);
        }

        if(cur2==null){
            nv.firstArc=new ArcNode(u);
        }
        else{
            while(cur2.nextArc!=null){
                cur2=cur2.nextArc;
            }
            cur2.nextArc=new ArcNode(u);
        }
        return true;
    }
    public boolean addEdge(String uInfo,String vInfo) {
        if(uInfo.equals(vInfo)) return false;
        int uIndex = findNode(uInfo);
        int vIndex = findNode(vInfo);
        if(uIndex<0 || vIndex<0) return false;
//       通过结点拿到id
        int u=vexs[uIndex].id;
        int v=vexs[vIndex].id;
        if(isAdjVertex(u,v)) return false;
        // 添加边时，邻接表中的顶点表对应的两个顶点都需要增加相应的边结点
        // 请将代码补充完整

        //找到链表尾部
        VNode nu=vexs[findNode(u)];
        VNode nv=vexs[findNode(v)];
        ArcNode cur1=nu.firstArc;
        ArcNode cur2=nv.firstArc;

        if(cur1==null){                         //没有边结点
            nu.firstArc=new ArcNode(v);
        }
        else{                                   //有边结点：找到链表尾
            while(cur1.nextArc!=null){
                cur1=cur1.nextArc;
            }
            cur1.nextArc=new ArcNode(v);
        }

        if(cur2==null){
            nv.firstArc=new ArcNode(u);
        }
        else{
            while(cur2.nextArc!=null){
                cur2=cur2.nextArc;
            }
            cur2.nextArc=new ArcNode(u);
        }
        return true;
    }
    //    根据node.id删除顶点
    public boolean removeNode(int u) {
        int uIndex = findNode(u);
        if(uIndex < 0) return false;

        // 顶点表中删除顶点结点 u
        for(int i=uIndex+1; i<vexNum; i++) {
            vexs[i-1] = vexs[i];
        }
        vexNum -- ;
        // 删除结点时，还需要在每个顶点的边结点链表中查找是否有该结点并删除
        // 请将代码补充完整
        for(int i=0;i<vexNum;i++){                          //遍历每一个结点
            VNode ni=vexs[i];

            ArcNode cur=ni.firstArc;
            if(cur==null){                         //没有边结点
                continue;
            }
            if(cur.adjVex==uIndex) ni.firstArc=cur.nextArc;
            else{                                   //有边结点
                while(cur.nextArc!=null&&cur.nextArc.adjVex!=uIndex){
                    cur=cur.nextArc;
                }
                if(cur.nextArc!=null) {
                    cur.nextArc = cur.nextArc.nextArc;
                }
            }
        }

        for(int i=0;i<vexNum;i++){                          //再次遍历，更新结点的索引位置
            VNode ni=vexs[i];
            ArcNode cur=ni.firstArc;
            if(cur==null){                         //没有边结点
                continue;
            }
            if(cur.adjVex>u)ni.firstArc.adjVex--;
            else{                                   //有边结点
                while(cur.nextArc!=null&&cur.nextArc.adjVex>u){
                    cur.nextArc.adjVex--;
                    cur=cur.nextArc;
                }
            }
        }
        return true;
    }
    //    根据node.id删除边
    public boolean removeEdge(int u,int v) {
        if(u == v) return false;
        int uIndex = findNode(u);
        int vIndex = findNode(v);
        if(uIndex<0 || vIndex<0) return false;

        ArcNode pre,p;
        // 顶点 u 的边表中的找到边结点 v 的前序结点
        pre = vexs[uIndex].firstArc;
        p = pre;
        while(p != null) {
            if(p.adjVex == vIndex)
                break;
            pre = p;
            p = p.nextArc;
        }
        if(p == null) return false;
        // 删除边结点
        if(p == vexs[uIndex].firstArc)
            vexs[uIndex].firstArc = p.nextArc;
        else
            pre.nextArc = p.nextArc;
        // 顶点 v 的边表中的找到边结点 u 的前序结点
        pre = vexs[vIndex].firstArc;
        p = pre;
        while(p != null) {
            if(p.adjVex == uIndex)
                break;
            pre = p;
            p = p.nextArc;
        }
        if(p == null) return false;
        // 删除边结点
        if(p == vexs[vIndex].firstArc)
            vexs[vIndex].firstArc = p.nextArc;
        else
            pre.nextArc = p.nextArc;
        edgeNum --;

        return true;
    }
    //    拿到一个顶点的所有边
    public LinkedList<String> getAllEdge(int u){
        if(u<0) return new LinkedList<>();          //返回一个空的list
        LinkedList<String> res=new LinkedList<>();
        ArcNode cur = vexs[u].firstArc;             //第一个边
        while (cur != null) {
            res.add(vexs[cur.adjVex].info);
            VNode v = vexs[cur.adjVex];
            cur = cur.nextArc;
        }
        return res;
    }
    //    拿到一个顶点边的总数
    public int getEdgeNum(int u){
        if(u<0) return -1;          //返回0
        int num=0;
        ArcNode cur = vexs[u].firstArc;             //第一个边
        while (cur != null) {
            num++;
            cur = cur.nextArc;
        }
        return num;

    }
    //    显示邻接表
    public void showGraph() {
        System.out.println("\n 无向图有"+vexNum+"顶点和"+edgeNum+"边\n");
        System.out.println("\t 结点\t 相邻边");

        ArcNode p;
        for (int i = 0; i < vexNum; i ++) {
            System.out.print("\t"+vexs[i].id+"("+vexs[i].info+")");
            p = vexs[i].firstArc;
            while (p != null) {
                VNode v = vexs[p.adjVex];
                System.out.print("\t"+v.id+"("+v.info+")");
                p = p.nextArc;
            }
            System.out.println();
        }
    }
    //    将邻接表生成为String：String[]的形式
    public Map<String,LinkedList<String>> getAdjacencyList() {
        Map<String,LinkedList<String>> map=new HashMap<>();
        ArcNode p;
        for (int i = 0; i < vexNum; i ++) {
            String key=vexs[i].info;
            LinkedList<String> list=new LinkedList<>();
//            System.out.print("\t"+vexs[i].id+"("+vexs[i].info+")");
            p = vexs[i].firstArc;
            while (p != null) {
                list.add(vexs[p.adjVex].info);
                VNode v = vexs[p.adjVex];
//                System.out.print("\t"+v.id+"("+v.info+")");
                p = p.nextArc;
            }
            map.put(key,list);
//            System.out.println();
        }
        return map;
    }
    public VNode[] getVexs() {
        return vexs;
    }
    public int getVexNum() {
        return vexNum;
    }
    public int getArcNum() {
        return edgeNum;
    }

}