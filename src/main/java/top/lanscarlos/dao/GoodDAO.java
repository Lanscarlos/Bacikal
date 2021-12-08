package top.lanscarlos.dao;

import org.apache.ibatis.annotations.Param;
import top.lanscarlos.pojo.Good;

import java.util.List;

/**
 * @author 10511
 */
public interface GoodDAO {

    List<Good> selectAll();

    /**
     * 根据商品gid进行查询
     * @param gid
     * @return
     */
    Good selectByGid(String gid);

    /**
     * 根据商品名称进行查询
     * @param name
     * @return
     */
    List<Good> selectByName(String name);

//    /**
//     * 根据供应商sid进行查询
//     * @param sid
//     */
//    List<Good> selectBySid(String sid);

    /**
     * 根据商品类型进行查询
     * @param name
     * @return
     */
    List<Good> selectByCategory(String name);

    /**
     * 多条件查询
     * @param gid
     * @param category
     * @param name
     * @return
     */
    List<Good> selectByCondition(@Param("gid") String gid, @Param("name") String name, @Param("category") String category);

    /**
     * 添加
     * @param gid
     * @param sid
     * @param name
     * @param description
     * @param image
     * @param stock
     * @param puaway_time
     * @return
     */
//    Good insert(String gid,String sid,String name,String description,String image,String stock,String puaway_time);
}
