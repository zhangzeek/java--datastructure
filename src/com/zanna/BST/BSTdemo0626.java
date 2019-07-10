package com.zanna.BST;

/**
 * Description：BST   Binary Search Tree   二分搜索树
 * Author: zker
 * Date: Created in 18:58 2019/6/26
 * Copyright: Copyright (c) 2018
 */

import java.util.ArrayList;

/**
 * BST树的节点类型
 * @param <T>
 */
class BSTNode<T extends Comparable<T>>{
    private T data; // 数据域
    private BSTNode<T> left; // 左孩子域
    private BSTNode<T> right; // 右孩子域

    public BSTNode(T data, BSTNode<T> left, BSTNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BSTNode<T> getLeft() {
        return left;
    }

    public void setLeft(BSTNode<T> left) {
        this.left = left;
    }

    public BSTNode<T> getRight() {
        return right;
    }

    public void setRight(BSTNode<T> right) {
        this.right = right;
    }
}


/**
 * BST树的实现
 * @param <T>
 */
class BST<T extends Comparable<T>> {
    public BSTNode<T> root; // 指向根节点

    /**
     * BST树的初始化
     */
    public BST() {
        this.root = null;
    }

    /**
     * BST树的插入操作
     *
     * @param data
     */
    public void non_insert(T data) {
        //1. 判断树是否为空，即根节点是否为空;为空，直接生成根节点，让root指向，结束；
        //
        //2. 不为空，从根节点开始搜索一个合适的位置，放入新节点；
        //3. 生成新节点，把节点的地址写入父节点相应的地址。

        if (this.root == null) {
            this.root = new BSTNode<>(data, null, null);//生成根节点
            return;
        } else {
            //不为空，对根节点的数据进行比较，
            // 大于根节点：向左查找，比较是否大于根节点的左孩子；
            // 小于根节点：向右查找，比较是否大于根节点的右孩子；
            BSTNode<T> current = this.root;
            BSTNode<T> parent = null;
            while (current != null) {
                if (current.getData().compareTo(data) > 0) {

                    parent = current;
                    current = current.getLeft();
                } else if (current.getData().compareTo(data) < 0) {
                    parent = current;
                    current = current.getRight();
                } else {
                    return;
                }
            }
            if (parent.getData().compareTo(data) > 0) {
                parent.setLeft(new BSTNode<>(data, null, null));
            } else {
                parent.setRight(new BSTNode<>(data, null, null));
            }
        }
    }

    /**
     * 非递归实现BST树的删除操作
     *
     * @param: data
     * @return:
     */
    public void non_remove(T data) {
        if (this.root == null) {
            return;
        }
        // 1. 先搜索BST树，找到待删除的节点
        BSTNode<T> current = this.root;
        BSTNode<T> parent = null;
        while (current != null) {
            if (current.getData().compareTo(data) > 0) {
                parent = current;
                current = current.getLeft();
            } else if (current.getData().compareTo(data) < 0) {
                parent = current;
                current = current.getRight();
            } else {
                break;
            }
            if (current == null) {
                return;
            }
            // 2. 判断删除节点是否有两个孩子，如果有，用前驱的值代替，直接删除前驱
            if (current.getLeft() != null && current.getRight() != null) {
                BSTNode<T> old = current;
                parent = current;

                current = current.getLeft();
                while (current.getRight() != null) {
                    parent = current;
                    current = current.getRight();
                }
                old.setData(current.getData());//前驱节点的值覆盖待删结点的值
            }
            // 3. 删除有一个孩子的节点，或者没有孩子的节点（看作有一个孩子，孩子是null）
            BSTNode<T> child = current.getLeft();
            if (child == null) {
                child = current.getRight();
            }
            if (parent == null) {
                this.root = child;
            } else if (parent.getLeft() == current) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
    }

    /**
     * 在BST树中，搜索值为data的结点，找到返回true，找不到返回false
     *
     * @param:data
     * @return:
     */
    public boolean non_query(T data) {
        if (this.root == null) {
            return false;
        }
        BSTNode<T> current = this.root;
        while (current != null) {
            if (current.getData().compareTo(data) > 0) {
                current = current.getLeft();
            } else if (current.getData().compareTo(data) < 0) {
                current = current.getRight();
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 递归实现
     * 前序遍历BST树的API接口
     */
    public void preOrder() {
        System.out.println("递归前序遍历：");
        preOrder(this.root);
        System.out.println();
    }

    private void preOrder(BSTNode<T> root) {
        if (root != null) {
            System.out.print(root.getData() + " ");
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }
    }

    /**
     * LVR
     * 中序遍历BST树的API接口
     */
    public void inOrder() {
        System.out.println("中序遍历：");
        inOrder(this.root);
        System.out.println();
    }

    private void inOrder(BSTNode<T> root) {
        if (root != null) {
            inOrder(root.getLeft());
            System.out.print(root.getData() + " ");
            inOrder(root.getRight());
        }
    }

    /**
     * LRV
     * 后序遍历BST树的API接口
     */
    public void postOrder() {
        System.out.println("后序遍历：");
        postOrder(this.root);
        System.out.println();
    }

    private void postOrder(BSTNode<T> root) {
        if (root != null) {
            postOrder(root.getLeft());
            postOrder(root.getRight());
            System.out.print(root.getData() + " ");
        }
    }

    /**
     * 返回BST树中所有结点个数的API接口
     */
    public int number() {
        return number(this.root);
    }

    private int number(BSTNode<T> root) {
        if (root == null) { //递归的结束条件
            return 0;
        } else {
            return number(root.getRight()) + number(root.getLeft()) + 1; //递归的实现
        }
    }

    /**
     * 返回BST树的高度/层数的API
     */
    public int level() {
        return level(this.root);
    }

    private int level(BSTNode<T> root) {
        if (root == null) {
            return 0;
        } else {
            int left = level(root.getLeft());
            int right = level(root.getRight());
            return left > right ? left + 1 : right + 1;
        }
    }

    /**
     * 层序遍历BST树API接口
     * VLR
     */
    public void levelOrder() {
        int hight = level(this.root);
        for (int i = 0; i <= hight; i++) {
            levelOrder(this.root, i);
        }
        System.out.println();
    }

    private void levelOrder(BSTNode<T> root, int hight) {
        if (root != null) {
            if (hight == 0) {
                System.out.print(root.getData() + " ");
                return;
            }
            levelOrder(root.getLeft(), hight - 1);
            levelOrder(root.getRight(), hight - 1);
        }
    }

    /*
     * BST树的镜像反转*/
    public void mirror() {
        mirror(this.root);
    }

    private void mirror(BSTNode<T> root) {
        if (root == null) {
            return;
        }
        //交换左右孩子
        BSTNode<T> tmp = root.getLeft();
        root.setLeft(root.getRight());
        root.setLeft(tmp);
        //遍历
        mirror(root.getRight());
        mirror(root.getLeft());
    }

    /**
     * (tecent)面试题：把BST树中，满足[begin,end]区间的所有元素打印出来
     */
    public void printAreaDates(T begin, T end) {
        printAreaDates(this.root, begin, end);
    }

    private void printAreaDates(BSTNode<T> root, T begin, T end) {
        if (root != null) {
            return;
        }
        // 如果当前节点的值小于begin，就不用再递归节点的左子树了
        if (root.getData().compareTo(begin) > 0) {
            printAreaDates(root.getLeft(), begin, end);
        }
        //但是要查看右子树数据域大小
        printAreaDates(root.getRight(), begin, end);
        if (root.getData().compareTo(begin) >= 0 && root.getData().compareTo(end) <= 0) {
            System.out.println(root.getData() + " ");
        }
        // 当前节点的值小于end，才有必要继续访问当前节点的右子树
        if (root.getData().compareTo(end) < 0) {
            printAreaDates(root.getRight(), begin, end);
        }
    }

    /**
     * 判断一个二叉树是否是BST树，是则返回true，否则返回false
     */
    public boolean isBSTree() {
        T value = null;
        return isBSTree(this.root, value);
    }

    /**
     * value 记录的是中序遍历上一个元素的值
     *
     * @param:root
     * @return:value
     */
    private boolean isBSTree(BSTNode<T> root, T value) {
        if (root == null) {
            return true;
        }
        //左子树已经不能满足BST树性质了，直接返回，不用继续
        if (!isBSTree(root.getLeft(), value)) {
            return false;
        }
        if (value != null && value.compareTo(root.getData()) > 0) {
            return false;
        }
        // 注意当前节点判断完成后，需要更新一下value值
        value = root.getData();

        return isBSTree(root.getRight(), value);
    }
    /*private boolean isBSTree(BSTNode<T> root){
        if (root == null){
            return true;
        }
        if (root.getData().compareTo(root.getLeft().getData()) > 0){
            isBSTree(root.getLeft());
        }
        if(root.getLeft() != null
                && root.getLeft().getData().compareTo(root.getData()) > 0){
            return false;
        }
        if(root.getRight() != null
                && root.getData().compareTo(root.getRight().getData()) > 0){
            return false;
        }
        return isBSTree(root.getLeft()) && isBSTree(root.getRight());
    }*/

    /**
     * 返回两个节点的最近公共祖先节点
     *
     * @param data1
     * @param data2
     * @return
     */
    /*                   58
     *                 /      \
     *               23       82
     *             /   \    /   \
     *          12    35   69   87
     *           \     \    \    \
     *          18    47   74   95
     * */
    public T getLCA(T data1, T data2) {
        return getLCA(this.root, data1, data2);
    }

    private T getLCA(BSTNode<T> root, T data1, T data2) {
        if (root == null) {
            return null;
        }
        //根节点的值与data1，data2比较，大于0 的话，根节点大，与左节点的值比较
        if (root.getData().compareTo(data1) > 0
                && root.getData().compareTo(data2) > 0) {
            return getLCA(root.getLeft(), data1, data2);
        } else if (root.getData().compareTo(data1) < 0
                && root.getData().compareTo(data2) < 0) {
            return getLCA(root.getRight(), data1, data2);
        } else {
            return root.getData();
        }
    }

    /**
     * 返回中序遍历倒数第k个节点的值
     * 从后遍历
     *
     * @param k
     * @return
     */
    public void getOrderValue(int k) {
        System.out.println(getOrder(k, this.root));
    }

    public void inOrder(ArrayList<BSTNode> arrayList, BSTNode<T> root) {
        if (root == null) {
            return;
        }
        inOrder(arrayList, root.getLeft());
        arrayList.add(root);
        inOrder(arrayList, root.getRight());
    }

    public T getOrder(int k, BSTNode<T> root) {
        ArrayList<BSTNode> arrayList = new ArrayList<BSTNode>();
        inOrder(arrayList, root);
        return (T) arrayList.get(arrayList.size() - k).getData();
    }

    public T getINOrderValue(int k) {
        //int num = number();  // num - k    1 2 3 4 5 6 7   k=3   7-3=4
        //return getINOrderValue(this.root, num - k);
        return getINOrderValue(this.root, k);
    }

    private int i = 1;

    private T getINOrderValue(BSTNode<T> root, int k) {
        if (root == null) {
            return null;
        }

        T val = getINOrderValue(root.getRight(), k);
        if (val != null) {
            return val;
        }

        if (i++ == k) {
            return root.getData();
        }

        return getINOrderValue(root.getLeft(), k);
    }

    /*private int i=0;
    private T getOrderValue(BSTNode<T> root, int k) {
        if(root == null){
            return null;
        }

        T val = getOrderValue(root.getLeft(), k);
        if(val != null){
            return val;
        }

        if(i++ == k)
        {
            return root.getData();
        }

        return getOrderValue(root.getRight(), k);
    }*/


    /**
     * 判断参数tree是否是当前BST树的一颗子树
     */
    private boolean isChildTree(BST<T> tree) {
        //判断当前BST树根节点是否为空
        //遍历BST树，看当前BST 树中书否存在子树的根节点
        //再逐次遍历主树和子树左右结点是否相等
        if (this.root == null) {
            return false;
        }
        BSTNode<T> current = this.root;
        while (current != null) {
            if (current.getData().compareTo(tree.root.getData()) > 0) {
                current = current.getLeft();
            } else if (current.getData().compareTo(tree.root.getData()) < 0) {
                current = current.getRight();
            } else {
                break;
            }
            if (current == null) {
                return false;
            }
        }
        return isChildTree(current, tree.root);
    }

    private boolean isChildTree(BSTNode<T> p, BSTNode<T> c) {
        if (p == null || c == null) {
            return false;
        }
        if (p == null) {
            return false;
        }
        if (c == null) {
            return true;
        }
        if (p.getData().compareTo(c.getData()) != 0) {
            return false;
        }
        return isChildTree(p.getLeft(), c.getLeft()) && isChildTree(p.getRight(), c.getRight());
    }

    /**
     * 根据参数传入的pre和in数组，重建二叉树
     *
     * @param pre
     * @param in
     */
    public void rebuild(T[] pre, T[] in) {
        this.root = rebuild(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    /**
     * pre前序数组的[i,j]数据范围，in中序数组的[m,n]数据范围，
     * 利用BST的前序遍历和中序遍历重建BST树
     * 创建一颗二叉树，并把树的根节点进行返回
     * @param pre
     * @param i   位置下标
     * @param j
     * @param in
     * @param m
     * @param n
     * @return
     */
    private BSTNode<T> rebuild(T[] pre, int i, int j, T[] in, int m, int n) {
        if (i > j || m > n) {
            return null;
        }
        BSTNode<T> node = new BSTNode<T>(pre[i], null, null);//创建根节点，前序遍历
        for (int k = m; k <= n; ++k) {
            if (pre[i].compareTo(in[k]) == 0) { //查找到树的根节点
                node.setLeft(rebuild(pre, i + 1, i + (k - m), in, m, k - 1));//重构左孩子和右孩子
                node.setRight(rebuild(pre, i + (k - m) + 1, j, in, k + 1, n));
                break;
            }
        }
        return node;
    }

    /*
     * 递归实现BST增加节点
     * */
    public void insert(T data) {
        this.root = insert(data, this.root);
    }

    private BSTNode<T> insert(T data, BSTNode<T> root) {
        //如果BST树的根节点为空，将data作为数据域作为新创建的结点赋给root
        if (this.root == null) {
            this.root = new BSTNode<>(data, null, null);
        }
        //如果根节点不为空的话，遍历BST，找到合适的位置
        BSTNode<T> current = this.root;
        while (current != null) {
            if (current.getData().compareTo(data) > 0) {
                current.setLeft(insert(data, current.getLeft()));
            } else if (current.getData().compareTo(data) < 0) {
                current.setRight(insert(data, current.getRight()));
            } else {
                break;
            }
        }
        return current; //将当前结点返回到父节点的函数调用中
    }

    /*
     * 递归实现BST删除操作
     */
    public void remove(T data) {
        this.root = remove(this.root, data);
    }

    /**
     * 以root为根节点，找值为data的结点进行删除，并把删除的结点的孩子进行返回
     *
     * @param:[root，data]
     * @return:
     */
    private BSTNode<T> remove(BSTNode<T> root, T data) {
        if (root == null) {
            return null;
        }
        while (root != null) {
            if (root.getData().compareTo(data) > 0) {
                remove(root.getLeft(), data);
            } else if (root.getData().compareTo(data) < 0) {
                remove(root.getRight(), data);
            } else {
                //判断该结点是否有两个孩子，若有，用前驱将该节点覆盖  左节点-》
                if (root.getLeft() != null && root.getRight() != null) {
                    BSTNode<T> pre = null;
                    pre = pre.getLeft();
                    while (pre.getRight() != null) {
                        pre = pre.getRight();
                    }
                    root.setData(pre.getData());
                    root.setLeft(remove(root.getLeft(), pre.getData()));
                } else {
                    if (root.getLeft() != null) {
                        return root.getLeft();
                    } else if (root.getRight() != null) {
                        return root.getRight();
                    } else {
                        return null;
                    }
                }
            }
        }
        return root;
    }

    /**
     * 判断以参数root为根节点的BST树是否是一个平衡树
    * @param:
    * @return: boolean
    */
    /*
    * 属性：
    * 它是一 棵空树或它的左右两个子树的高度差的绝对值不超过1，
    * 并且左右两个子树都是一棵平衡二叉树。
    * 顺序遍历二叉树的每一个节点，分别计算左右子树的深度，判断深度是否相差1；
    * 如果不是，直接返回false，否则递归判断该节点的左子节点和右子节点。
    * 但是该算法有一个问题，从上往下递归遍历计算每一个节点的深度时，
    * 有很多节点会发生重复遍历，越深层次的重复遍历次数越高，因此会影响性能。
    * 返回BST树的高度/层数的API     level()*/
    public boolean isAVL(BSTNode<T> root){
        //是否是平衡树  ====》 节点的左右子树的高度差不大于1
        if (root == null){
            return true;
        }
        int leftlevel = level(root.getLeft());
        int rightlevel = level(root.getRight());
        int differ = leftlevel - rightlevel;
        if (differ < -1 || differ > 1){
            return false;
        }
        return isAVL(root.getRight()) && isAVL(root.getLeft());
    }
}
public class BSTdemo0626 {
    public static void test04(){
        Integer[] pre = {58, 23, 12, 18, 35, 47, 82, 69, 74, 87, 95};//前序遍历BST树的结果
        Integer[] in = {12, 18, 23, 35, 47, 58, 69, 74, 82, 87, 95};//中序遍历BST树的结果
        BST<Integer> bst = new BST<>();
        bst.rebuild(pre, in);

        bst.preOrder();
        bst.inOrder();
        bst.postOrder();
    }
    public static void main(String[] args) {
        test04();
        BST<Integer> bst = new BST<>();
        int[] ar = {58,23,82,12,35,69,87,18,47,74,95};
        /*                   58
       *                 /      \
       *               23       82
       *             /   \    /   \
       *          12    35   69   87
       *           \     \    \    \
       *          18    47   74   95
       *
       *                   i  i+1             i+(k-m)+1            j
       * Integer[] pre = {58, 23, 12, 18, 35, 47, 82, 69, 74, 87, 95};//前序遍历BST树的结果
       *                 m                   k                    n
        Integer[] in = {12, 18, 23, 35, 47, 58, 69, 74, 82, 87, 95};//中序遍历BST树的结果
        * */
        for (int val : ar) {
            bst.non_insert(val);
        }
        System.out.println(bst.level());
        bst.preOrder();
        bst.inOrder();
        //中序遍历倒数第K个结点的值
        bst.getOrderValue(5);

    }
}
