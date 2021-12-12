package top.lanscarlos.dao;

import org.apache.ibatis.annotations.Param;
import top.lanscarlos.pojo.Cart;

import java.util.List;

public interface CartDAO {

    /**
     * 根据用户查看信息
     * @param uid
     * @return
     */
    List<Cart> selectByUid(@Param("uid") String uid);

    /*isEmpty和selectAmountByGid效果一样*/

    /**
     * 判断增加或者插入
     * @param gid
     * @return
     */
    Cart select(@Param("uid") String uid, @Param("gid") String gid);

    /**
     * 根据uid和gid在购物页面进行批量删除
     * @param uid
     * @param gids
     * @return
     */
    int deleteByIds(@Param("uid") String uid, @Param("gids") String[] gids);

    /**
     * 插入信息并且封装
     * @param cart
     * @return
     */
    int insertById(Cart cart);

    /**
     * 更新
     * @param cart
     * @return
     */
    int updateById(Cart cart);

//    Cart updateByIds(String uid,String[]);

    
}
