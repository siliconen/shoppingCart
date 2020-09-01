package pm.group01.courseproject.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.product.model.Category;
import pm.group01.courseproject.product.model.CategoryFromUserDTO;
import pm.group01.courseproject.product.service.CategoryService;

import java.util.List;
import java.util.Optional;

/*
 * @author Battuguldur Ganbold (986874)
 */

@RestController
@RequestMapping("/categories/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{catid}")
    public ResponseEntity<Category> getById(@PathVariable("catid") Integer id){
        return Optional
                .ofNullable( categoryService.findById(id) )
                .map( cat -> ResponseEntity.ok().body(cat) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @PostMapping("/")
    public ResponseEntity<Category> create(@RequestBody Category category){
        Category res = categoryService.save(category);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @PutMapping("/")
    public ResponseEntity<Category> update(@RequestBody Category category){
        Integer id = category.getCategoryId();
        Category res = categoryService.findById(id);
        if(res == null)
            return new ResponseEntity<Category>(res, HttpStatus.NOT_FOUND);

        res = categoryService.save(category);
        return ResponseEntity.ok(res);
    }
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN','BRANDOWNER')")
    @DeleteMapping("/{catid}")
    public ResponseEntity<Category> delete(@PathVariable("catid") Integer id){
        Category res = categoryService.findById(id);
        if(res == null)
            return new ResponseEntity<Category>(res, HttpStatus.NOT_FOUND);

        categoryService.delete(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> getNameIsLike(
            @RequestParam(value = "name", required = true) String name
    ){
        return Optional
                .ofNullable( categoryService.getNameIsLike(name) )
                .map( cat -> ResponseEntity.ok().body(cat) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PostMapping("/createCategory")
    public ResponseEntity<?> createCategoryFromUserDTO(@RequestBody CategoryFromUserDTO categoryFromUserDTO){

        Category category = new Category();
        category.setCategoryId(categoryFromUserDTO.getCategoryId());
        category.setName(categoryFromUserDTO.getName());
        categoryService.save(category);

        return ResponseEntity.ok(categoryFromUserDTO);
    }

}
