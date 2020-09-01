package pm.group01.courseproject.product.service;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

public interface BaseService<T> {

    T save(T t);

    boolean delete(Integer id);

    T update(T t);

    List<T> findAll();

    T findById(Integer id);

}
