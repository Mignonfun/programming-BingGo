package com.bingo.menu.home;


import com.bingo.business.management.Platform;
import com.bingo.business.management.strategy.DefaultPaymentStrategy;
import com.bingo.commons.constants.BingoConstants;
import com.bingo.commons.constants.CollConstants;
import com.bingo.commons.enums.ActivityType;
import com.bingo.commons.pojo.Cart;
import com.bingo.commons.pojo.Goods;
import com.bingo.commons.pojo.identity.Purchaser;
import com.bingo.commons.pojo.identity.User;
import com.bingo.commons.utils.DBUtils;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.utils.InputUtil;
import com.bingo.commons.vo.ResultVO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nia
 * @description 普通用户主界面
 * @Date 2024/6/6
 */
public class PurchaserHome {

    public static ResultVO<Purchaser> purchaserHome(Purchaser user) {
        List<String> purchaser = CollConstants.PURCHASER;
        GlobalFormatUtil.roleWelcome(user.getName());
        while (true) {
            GlobalFormatUtil.commands(purchaser);
            int inputChar = InputUtil.inputChar(purchaser.size());

            switch (inputChar) {
                case 1 -> viewGoods(user);
                case 2 -> myCart(user);
                case 3 -> interaction(user);
                case 4 -> self(user);
                case 5 -> {
                    return ResultVO.success("用户" + user.getName() + "退出登录");
                }
            }
        }

    }

    /**
     * 查看商品界面
     *
     * @param user
     */
    private static void viewGoods(Purchaser user) {
        List<Goods> allGoods = DBUtils.getAllGoods();
        if (allGoods == null || allGoods.isEmpty()) {
            System.out.println("暂无商品");
            return;
        }
        int size = allGoods.size();
        GlobalFormatUtil.showGoods(allGoods, 0, size);
        System.out.println("加入购物车请选择商品前的编号，退出(q)");
        int i = InputUtil.inputChar(size);
        if (i == 0) {
            return;
        }
        Goods goods = allGoods.get(i - 1);

        DBUtils.insertGoods2Cart(goods, user);
        System.out.println("加入购物车成功！");

    }

    private static void myCart(Purchaser user) {
        Cart cart = DBUtils.getCart(user.getPId());
        List<Goods> goodsList = cart.getGoodsList();
        GlobalFormatUtil.showGoods(goodsList, 0, goodsList.size());
        System.out.println("共" + cart.getCount() + "件商品,总价" + cart.getTotal());
        System.out.println("付款请输入商品前的序号");
        int i = InputUtil.inputChar(goodsList.size());
        if (i == 0) {
            return;
        }
        Goods goods = goodsList.get(i - 1);
        Double balance = user.getBalance();

        System.out.println("您的余额为：" + balance);
        if (goods.getPrice() > balance) {
            System.out.println("余额不足，即将退出...");
            return;
        }
        String s = InputUtil.inputString("是否付款？(Y)");
        if ("y".equalsIgnoreCase(s)) {
            payment(user, goods);
            System.out.println("付款成功！");
        }
    }

    /**
     * 个人界面
     *
     * @param user
     */
    private static void self(Purchaser user) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(BingoConstants.USERNAME, user.getName());
        map.put(BingoConstants.BALANCE, user.getBalance());

        map.put(BingoConstants.FOLLOWERS, DBUtils.getFollowers(user.getPId()));
        map.put(BingoConstants.FOLLOWING, DBUtils.getFollowings(user.getPId()));

        GlobalFormatUtil.list(map);

    }

    private static void interaction(Purchaser user) {
        System.out.println("互动中心");
        //todo 互动中心


    }

    private static void payment(Purchaser user, Goods goods) {
        Double price = goods.getPrice();
        price = Platform.getInstance().paymentCalc(price, ActivityType.DEFAULT);
        user.setBalance(user.getBalance() - price);
        DBUtils.updatePurchaser(user);
        DBUtils.rmGoodsFromCart(user.getPId(), goods.getgId());

    }
}
