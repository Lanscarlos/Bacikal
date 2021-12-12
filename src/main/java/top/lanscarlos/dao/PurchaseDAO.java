package top.lanscarlos.dao;

import org.apache.ibatis.annotations.Param;
import top.lanscarlos.pojo.Purchase;

import java.util.List;

public interface PurchaseDAO {

    /**
     * 获取用户的所有订单
     * */
    List<Purchase> getAllPurchase(@Param("uid") String uid);

    /**
     * 获取指定的订单
     * */
    Purchase getPurchase(@Param("pid") String pid);

    /**
     * 添加订单
     * */
    int addPurchase(Purchase purchase);

    /**
     * 删除订单
     * */
    int deletePurchase(@Param("pid") String pid);

}
