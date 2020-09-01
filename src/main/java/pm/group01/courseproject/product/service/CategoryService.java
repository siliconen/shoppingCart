package pm.group01.courseproject.product.service;

import pm.group01.courseproject.product.model.Category;

import java.util.List;

/*
 * @author Battuguldur Ganbold (986874)
 */

public interface CategoryService extends BaseService<Category> {
    List<Category> getNameIsLike(String name);
}
