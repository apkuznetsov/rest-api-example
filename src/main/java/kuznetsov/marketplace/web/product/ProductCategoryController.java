package kuznetsov.marketplace.web.product;

import java.net.URI;
import kuznetsov.marketplace.services.product.ProductCategoryService;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDto;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDtoPage;
import kuznetsov.marketplace.services.security.ModeratorPermission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryController {

  public final String CATEGORY_URL = "/api/v1/categories";

  private final ProductCategoryService categoryService;

  @PostMapping(path = CATEGORY_URL)
  @ModeratorPermission
  public ResponseEntity<ProductCategoryDto> addCategory(@RequestBody ProductCategoryDto category) {
    log.info("Add new category {}", category.getName());
    ProductCategoryDto addedCategory = categoryService.addCategory(category);

    URI addedCategoryUri = URI.create(CATEGORY_URL + "/" + addedCategory.getId());
    return ResponseEntity.created(addedCategoryUri).body(addedCategory);
  }

  @PutMapping(path = CATEGORY_URL + "/{id}")
  @ModeratorPermission
  public ResponseEntity<ProductCategoryDto> updateCategory(
      @PathVariable long id, @RequestBody ProductCategoryDto categoryDto) {

    log.info("Update category {} id by new values {}", id, categoryDto.getName());
    ProductCategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);

    return ResponseEntity.ok(updatedCategory);
  }

  @GetMapping(path = CATEGORY_URL + "/{id}")
  public ResponseEntity<ProductCategoryDto> getCategoryById(@RequestParam long id) {
    log.info("Someone tries to get category with {} id.", id);
    ProductCategoryDto category = categoryService.getCategoryById(id);

    return ResponseEntity.ok(category);
  }

  @GetMapping(path = CATEGORY_URL)
  public ResponseEntity<ProductCategoryDtoPage> getPagedCategories(
      @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum) {

    log.info("Someone tries to get paged categories page number {}.", pageNum);
    ProductCategoryDtoPage pagedCategories = categoryService.getPagedCategories(pageNum);

    return ResponseEntity.ok(pagedCategories);
  }

}
