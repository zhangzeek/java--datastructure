package com.zanna.AVL;

/**
 * Description：
 * Author: zker
 * Date: Created in 21:03 2019/7/3
 * Copyright: Copyright (c) 2018
 */
class AVLNode<T extends Comparable<T>>{
    private T data;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height; // 记录节点当前的高度值

    public AVLNode(T data, AVLNode<T> left, AVLNode<T> right, int height) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.height = height;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

/**
 * 1.AVL是不是一个二叉搜索树？
 * 2.AVL是不是一颗平衡数？
 * 3.树的平衡是什么？
 * 4.AVL 树怎么旋转的？
 * 5.AVL树最差，最好，平均增删查时间复杂度？
 * 6.AVL树在插入删除中，最差的情况下，需要旋转的次数是多少次？O（log 2 n）
 * 删除、插入最多两次
 * 7.AVL为了保证树的平衡，而旋转的次数过多，当数据量大的时候，AVL树的增加，删除时间花费越来越大
 *
 */
class AVL<T extends Comparable<T>> {
    private AVLNode<T> root;

    public AVL(){
        this.root = null;
    }
    /**
     * 递归实现AVL插入操作
     */
    public void insert(T data){
        this.root = insert(data,this.root);
    }
    /**
    * @param:data,root
    * @return:AVLNode<T></T>
     *
     *       40
     *    30
     * 20     10
    */
    private AVLNode<T> insert(T data,AVLNode<T> root){
        //判断根节点是否为空
        if (this.root == null){
            return new AVLNode<>(data,null,null,1);
        }
        if (root.getData().compareTo(data) > 0){
            root.setLeft(insert(data,root.getLeft()));
            //判断root节点是否失衡
            if (height(root.getLeft()) - height(root.getRight()) > 1){
                //左孩子的左子树太高
                if(height(root.getLeft().getLeft()) >= height(root.getLeft().getRight())){
                    root = rightRotate(root);
                }else {
                    //左孩子的右子树太高
                    if (height(root.getLeft().getRight()) >= height(root.getLeft().getLeft())){
                        root = leftBalance(root);
                    }
                }
            }
        }else if (root.getData().compareTo(data) < 0){
            root.setRight(insert(data,root.getRight()));
            if (height(root.getRight()) - height(root.getLeft()) > 1){
                //右孩子的右子树太高
                if (height(root.getRight().getRight()) >= height(root.getRight().getLeft())){
                    root = leftRotate(root);
                }else {
                    //右孩子的左子树太高
                    root = rightBalance(root);
                }
            }
        }else {
            //递归回溯时，更新节点的高度值
            root.setHeight(maxHeight(root.getLeft(),root.getRight()) + 1);
            }
        return root;
    }
    /**
     * 实现AVL树的递归删除
    * @param:data
    * @return:
    */
    public void remove(T data){
        this.root = remove(data,this.root);
    }
    private AVLNode<T> remove(T data,AVLNode<T> root){
        if (root == null){
            return null;
        }
        if (root != null){
            if (root.getData().compareTo(data) > 0){
                root.setLeft(remove(data,root.getLeft()));
                //
                if (height(root.getRight()) - height(root.getLeft()) > 1){
                    if (height(root.getRight().getRight()) >= height(root.getRight().getLeft())){
                        root = leftRotate(root);
                    }else {
                        root = rightBalance(root);
                    }
                }
            }else if (root.getData().compareTo(data) < 0){
                root.setRight(remove(data,root.getRight()));
                //
                if (height(root.getLeft()) - height(root.getRight()) > 1){
                    if (height(root.getLeft().getLeft()) >= height(root.getLeft().getRight())){
                        root = rightRotate(root);
                    }else {
                        root = leftBalance(root);
                    }
                }
            }else {
                //要删除的结点有两个孩子，用前驱覆盖
                //左右子树哪个高，删除哪个，为了防止删除带来的旋转操作
                if (root.getRight()!= null && root.getLeft() != null){
                    if (height(root.getLeft()) >= height(root.getRight())) {
                        //用前驱替换
                        AVLNode<T> pre = root.getLeft();
                        while (pre.getRight() != null) {
                            pre = pre.getRight();
                        }
                        root.setData(pre.getData());
                        root.setLeft(remove(pre.getData(),root.getLeft()));//删除前驱
                    }else {
                        //用后继替换
                        AVLNode<T> post = root.getRight();
                        while (post.getLeft() != null){
                            post = post.getLeft();
                        }
                        root.setData(post.getData());
                        root.setRight(remove(post.getData(),root.getRight()));//删除后继
                    }
                }else {
                    if (root.getLeft() != null){
                        return root.getLeft();
                    }else if (root.getRight() != null){
                        return root.getRight();
                    }else {
                        return null;
                    }
                }
            }
        }
        //更新节点的高度
        //递归回溯时，更新节点的高度值
        root.setHeight(maxHeight(root.getLeft(),root.getRight()) + 1);
        return root;
    }
    /**
     * 以参数node为根节点进行左旋操作，把旋转后的树的根节点返回
     * @param node
     * @return
     */
    private AVLNode<T> leftRotate(AVLNode<T> node){
        AVLNode<T>  child = node.getRight();
        node.setRight(child.getLeft());
        child.setLeft(node);
        node.setHeight(maxHeight(node.getLeft(),node.getRight()) + 1);
        child.setHeight(maxHeight(child.getLeft(),child.getRight()) + 1);
        return child;
    }

    /**
     * 以参数node为根节点进行右旋操作，把旋转后的树的根节点返回
     * @param node
     * @return
     *   40 (node)              30
     *  30 (child)          20     40
     * 20   ---->
     */
    private AVLNode<T> rightRotate(AVLNode<T> node){
        AVLNode<T>  child = node.getLeft();
        node.setLeft(child.getRight());
        child.setRight(node);
        node.setHeight(maxHeight(node.getLeft(),node.getRight()) + 1);
        child.setHeight(maxHeight(child.getLeft(),child.getRight()) + 1);
        return child;
    }

    public int height(AVLNode<T> node){
        return node == null ? 0 : node.getHeight();

    }
    public int maxHeight(AVLNode<T> node1,AVLNode<T> node2){
        int l = height(node1);
        int r = height(node2);
        return l > r ? l : r;
    }
    /**
     * 以参数node为根节点进行左平衡操作，把旋转后的树的根节点返回
     * @param node
     * @return
     */
    private AVLNode<T> leftBalance(AVLNode<T> node){
        node.setLeft(leftRotate(node.getLeft()));
        return rightRotate(node);
    }

    /**
     * 以参数node为根节点进行右平衡操作，把旋转后的树的根节点返回
     * @param node
     * @return
     */
    private AVLNode<T> rightBalance(AVLNode<T> node){
        node.setRight(rightRotate(node.getRight()));
        return leftRotate(node);
    }
}
public class AVLdemo0703 {
    public static void main(String[] args) {
        AVL<Integer> avl = new AVL<>();
        for (int i = 0; i < 10; i++) {
            avl.insert(i);
        }

    }
}
