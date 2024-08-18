package com.cg.fms.api;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cg.fms.exception.ProductException;
import com.cg.fms.model.ProductModel;
import com.cg.fms.service.IProductService;
@CrossOrigin("*")
@RestController
@RequestMapping(path="/products")
public class ProductAPI {
	
	@Autowired
	private IProductService productService;
	
	/* Adding the Product details into the database */
	@PostMapping("/addproduct")
	public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel product) throws ProductException {
		product = productService.addProduct(product);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}
	
	/* Update the Product details */
	@PutMapping("/updateproduct/{productId}")
	public ResponseEntity<ProductModel> updateProduct(@RequestBody ProductModel product) throws ProductException {
		product = productService.updateProduct(product);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	
	/* Display all the Products details present in the database */
	@GetMapping("/getallproducts")
	public ResponseEntity<List<ProductModel>> getAll() throws ProductException{
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	/* Display the particular Product details present in the database using productId*/
	@GetMapping("/getProductByProductId/{productId}")
	public ResponseEntity<ProductModel> getProductByProductId(@PathVariable("productId") String productId) throws ProductException{
		return ResponseEntity.ok(productService.getProductByProductId(productId));
	}
	
	/* Delete the particular Product details present in the database using productId*/
	@DeleteMapping("/deleteproduct/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") String productId) throws ProductException {
		ResponseEntity<String> response = null;
		ProductModel product =productService.getProductByProductId(productId);
		if (product == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			productService.deleteProduct(productId);
			response = new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
		}
		return response;
	}

	
	
	
	
//	@GetMapping("/getProductByContractNumber/{contractNumber}")
//	public ResponseEntity<List<ProductModel>> getProductByContractNumber(@PathVariable("contractNumber") String contractNumber) throws ProductException{
//		return ResponseEntity.ok(productService.getProductByContractNumber(contractNumber));
//	}

}
