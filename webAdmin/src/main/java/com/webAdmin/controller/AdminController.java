package com.webAdmin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webAdmin.contants.Contants;
import com.webAdmin.dto.AccountsDto;
import com.webAdmin.dto.CategoriesDto;
import com.webAdmin.dto.ProductsDto;
import com.webAdmin.dto.RoleDto;
import com.webAdmin.dto.SubCategoriesDto;
import com.webAdmin.dto.VipsDto;
import com.webAdmin.entity.UserDetailCustom;
import com.webAdmin.service.AccountsService;
import com.webAdmin.service.CategoriesService;
import com.webAdmin.service.IImageService;
import com.webAdmin.service.ProductsService;
import com.webAdmin.service.RoleService;
import com.webAdmin.service.SubCategoriesService;
import com.webAdmin.service.VipsService;

@Controller
@RequestMapping("/adminPage/")
public class AdminController {
	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private VipsService vipsService;

	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private SubCategoriesService subCategoriesService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private IImageService imageService;

	@GetMapping("categories")
	public String categories(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {
		List<CategoriesDto> listCategories = categoriesService.getAll();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Categories");
		
		return "admin/categories";
	}

	@GetMapping("categories/addCategory")
	public String addCategory(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {

		CategoriesDto category = new CategoriesDto();
		model.addAttribute("category", category);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("status", Contants.ADD_STATUS);
		model.addAttribute("title", "Add Category");
		model.addAttribute("action", "Add Category");
		return "admin/saveCategories";
	}

	@PostMapping("categories/addCategory")
	public String addedCategory(Model model, @AuthenticationPrincipal UserDetailCustom userInfo,
			@Validated CategoriesDto categoriesDto) {
		String imageUrl = null;
		Map<String, String> fileInfo;
		try {
			fileInfo = imageService.save(categoriesDto.getMultipartFiles());
			imageUrl = fileInfo.get("dowloadUrl");
		} catch (IOException e) {

			e.printStackTrace();
		}
		categoriesDto.setThumbnail(imageUrl);
		categoriesService.addCategory(categoriesDto);

		return "redirect:/adminPage/categories";
	}

	@GetMapping("categories/update")
	public String updateCategory(Model model, @AuthenticationPrincipal UserDetailCustom userInfo,
			@RequestParam("id") int id) {
		CategoriesDto categoriesDto = categoriesService.getById(id);
		model.addAttribute("category", categoriesDto);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Update Category");
		model.addAttribute("status", Contants.UPDATE_STATUS);
		model.addAttribute("action", "Update Category");
		return "admin/saveCategories";
	}

	@PostMapping("categories/update")
	public String updatedCategory(Model model, @AuthenticationPrincipal UserDetailCustom userInfo,
			@Validated CategoriesDto categoriesDto) {
		String imageUrl = null;
		Map<String, String> fileInfo;
		try {
			fileInfo = imageService.save(categoriesDto.getMultipartFiles());
			imageUrl = fileInfo.get("dowloadUrl");
		} catch (IOException e) {

			e.printStackTrace();
		}
		categoriesDto.setThumbnail(imageUrl);
		categoriesService.update(categoriesDto);
		return "redirect:/adminPage/categories";
	}

	@GetMapping("categories/delete")
	public String deleteCategory(@RequestParam("id") int id) {

		categoriesService.deleteById(id);
		return "redirect:/adminPage/categories";
	}

	@GetMapping("subcategories")
	public String subcategories(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {
		List<SubCategoriesDto> subCategoriesDtos = subCategoriesService.getAll();
		model.addAttribute("listSubcategories", subCategoriesDtos);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Sub Categories");
		return "admin/subcategories";
	}

	@GetMapping("subcategories/addSubcategory")
	public String addSubCategory(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {
		List<CategoriesDto> categoriesDtos = categoriesService.getAll();
		SubCategoriesDto subCategoriesDto = new SubCategoriesDto();
		model.addAttribute("listCategories", categoriesDtos);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Add SubCategory");
		model.addAttribute("status", Contants.ADD_STATUS);
		model.addAttribute("action", "Add SubCategory");
		model.addAttribute("subcategory", subCategoriesDto);

		return "admin/saveSubCategories";
	}

	@PostMapping("subcategories/addSubcategory")
	public String addedSubcategory(@Validated SubCategoriesDto subCategoriesDto)

	{
		String imageUrl = null;
		Map<String, String> fileInfo;
		try {
			fileInfo = imageService.save(subCategoriesDto.getMultipartFiles());
			imageUrl = fileInfo.get("dowloadUrl");
		} catch (IOException e) {

			e.printStackTrace();
		}
		subCategoriesDto.setThumbnail(imageUrl);
		subCategoriesService.add(subCategoriesDto);
		return "redirect:/adminPage/subcategories";
	}

	@GetMapping("subcategories/update")
	public String updateSubCategory(Model model, @AuthenticationPrincipal UserDetailCustom userInfo,
			@RequestParam("id") int id) {
		List<CategoriesDto> categoriesDtos = categoriesService.getAll();
		SubCategoriesDto subCategoriesDto = subCategoriesService.getById(id);
		model.addAttribute("listCategories", categoriesDtos);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Update SubCategory");
		model.addAttribute("status", Contants.UPDATE_STATUS);
		model.addAttribute("action", "Update SubCategory");
		model.addAttribute("subcategory", subCategoriesDto);

		return "admin/saveSubCategories";
	}

	@PostMapping("subcategories/update")
	public String updatedSubcategory(@Validated SubCategoriesDto subCategoriesDto)

	{
		String imageUrl = null;
		Map<String, String> fileInfo;
		try {
			fileInfo = imageService.save(subCategoriesDto.getMultipartFiles());
			imageUrl = fileInfo.get("dowloadUrl");
		} catch (IOException e) {

			e.printStackTrace();
		}
		subCategoriesDto.setThumbnail(imageUrl);
		subCategoriesService.update(subCategoriesDto);
		return "redirect:/adminPage/subcategories";
	}

	@GetMapping("subcategories/delete")
	public String deleteSubcategory(@RequestParam("id") int id) {
		subCategoriesService.deleteById(id);
		return "redirect:/adminPage/subcategories";
	}

	@GetMapping("products")
	public String products(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {
		List<ProductsDto> listProductsDtos = productsService.getAll();
		model.addAttribute("listProducts", listProductsDtos);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Products");
		return "admin/products";
	}

	@GetMapping("products/addProduct")
	public String addProduct(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {
		List<SubCategoriesDto> subcategoriesDtos = subCategoriesService.getAll();
		ProductsDto productDto = new ProductsDto();
		model.addAttribute("listsSubcategoriesDtos", subcategoriesDtos);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Add Product");
		model.addAttribute("status", Contants.ADD_STATUS);
		model.addAttribute("action", "Add Product");
		model.addAttribute("product", productDto);

		return "admin/saveProducts";
	}

	@PostMapping("products/addProduct")
	public String addedProduct(@Validated ProductsDto productsDto)

	{
		String imageUrl = null;
		Map<String, String> fileInfo;
		try {
			fileInfo = imageService.save(productsDto.getMultipartFiles());
			imageUrl = fileInfo.get("dowloadUrl");
		} catch (IOException e) {

			e.printStackTrace();
		}
		productsDto.setThumbnail(imageUrl);
		productsService.addProduct(productsDto);
		return "redirect:/adminPage/products";
	}

	@GetMapping("products/update")
	public String updateProduct(Model model, @AuthenticationPrincipal UserDetailCustom userInfo,
			@RequestParam("id") int id) {
		List<SubCategoriesDto> subcategoriesDtos = subCategoriesService.getAll();
		ProductsDto productsDto = productsService.getById(id);
		model.addAttribute("listsSubcategoriesDtos", subcategoriesDtos);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Update Product");
		model.addAttribute("status", Contants.UPDATE_STATUS);
		model.addAttribute("action", "Update Product");
		model.addAttribute("product", productsDto);

		return "admin/saveProducts";
	}

	@PostMapping("products/update")
	public String updatedProduct(@Validated ProductsDto productsDto)

	{
		String imageUrl = null;
		Map<String, String> fileInfo;
		try {
			fileInfo = imageService.save(productsDto.getMultipartFiles());
			imageUrl = fileInfo.get("dowloadUrl");
		} catch (IOException e) {

			e.printStackTrace();
		}
		productsDto.setThumbnail(imageUrl);
		productsService.update(productsDto);
		return "redirect:/adminPage/products";
	}

	@GetMapping("products/delete")
	public String deleteProduct(@RequestParam("id") int id) {
		productsService.deleteById(id);
		return "redirect:/adminPage/products";
	}

	@GetMapping("role")
	public String role(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {
		List<RoleDto> listRoles = roleService.getAll();
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Role");
		return "admin/role";
	}

	@GetMapping("role/addRole")
	public String addRole(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {

		RoleDto roleDto = new RoleDto();

		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Add Role");
		model.addAttribute("status", Contants.ADD_STATUS);
		model.addAttribute("action", "Add Role");
		model.addAttribute("roleDto", roleDto);

		return "admin/saveRole";
	}

	@PostMapping("role/addRole")
	public String addedProduct(@Validated RoleDto roleDto)

	{
		roleService.addRole(roleDto);
		return "redirect:/adminPage/role";
	}

	@GetMapping("role/update")
	public String updateRole(Model model, @AuthenticationPrincipal UserDetailCustom userInfo,
			@RequestParam("id") int id) {

		RoleDto roleDto = roleService.getById(id);

		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Update Role");
		model.addAttribute("status", Contants.UPDATE_STATUS);
		model.addAttribute("action", "Update Role");
		model.addAttribute("roleDto", roleDto);

		return "admin/saveRole";
	}

	@PostMapping("role/update")
	public String updatedProduct(@Validated RoleDto roleDto)

	{
		roleService.update(roleDto);
		return "redirect:/adminPage/role";
	}

	@GetMapping("role/delete")
	public String deleteRole(@RequestParam("id") int id) {
		roleService.deleteById(id);
		return "redirect:/adminPage/role";
	}

	@GetMapping("accounts")
	public String accounts(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {
		List<AccountsDto> listAccounts = accountsService.getAll();
		model.addAttribute("listAccounts", listAccounts);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Accounts");
		return "admin/accounts";
	}

	@GetMapping("accounts/addAccount")
	public String addAccount(Model model, @AuthenticationPrincipal UserDetailCustom userInfo) {
		List<RoleDto> listRoles = roleService.getAll();
		List<VipsDto> listVips = vipsService.getAll();
		AccountsDto accountsDto = new AccountsDto();
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Add Account");
		model.addAttribute("status", Contants.ADD_STATUS);
		model.addAttribute("action", "Add Account");
		model.addAttribute("account", accountsDto);
		model.addAttribute("listVips", listVips);

		return "admin/saveAccount";
	}

	@PostMapping("accounts/addAccount")
	public String addedAccount(@Validated AccountsDto accountsDto)

	{
		String imageUrl = null;
		Map<String, String> fileInfo;
		try {
			fileInfo = imageService.save(accountsDto.getMultipartFiles());
			imageUrl = fileInfo.get("dowloadUrl");
		} catch (IOException e) {

			e.printStackTrace();
		}
		accountsDto.setAvatar(imageUrl);
		accountsService.addAccount(accountsDto);
		return "redirect:/adminPage/accounts";
	}

	@GetMapping("accounts/update")
	public String updateAccount(Model model, @AuthenticationPrincipal UserDetailCustom userInfo,
			@RequestParam("id") int id) {
		List<RoleDto> listRoles = roleService.getAll();
		List<VipsDto> listVips = vipsService.getAll();
		AccountsDto accountsDto = accountsService.getById(id);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Update Account");
		model.addAttribute("status", Contants.UPDATE_STATUS);
		model.addAttribute("action", "Update Account");
		model.addAttribute("account", accountsDto);
		model.addAttribute("listVips", listVips);
		
		return "admin/saveAccount";
	}

	@PostMapping("accounts/update")
	public String updatedAccount(@Validated AccountsDto accountsDto)

	{
		String imageUrl = null;
		Map<String, String> fileInfo;
		try {
			fileInfo = imageService.save(accountsDto.getMultipartFiles());
			imageUrl = fileInfo.get("dowloadUrl");
		} catch (IOException e) {

			e.printStackTrace();
		}
		accountsDto.setAvatar(imageUrl);
		accountsService.updateAccount(accountsDto);
		return "redirect:/adminPage/accounts";
	}

	@GetMapping("accounts/delete")
	public String deleteAccount(@RequestParam("id") int id) {
		accountsService.deleteById(id);
		return "redirect:/adminPage/accounts";
	}
}
